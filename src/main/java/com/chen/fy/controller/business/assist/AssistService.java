package com.chen.fy.controller.business.assist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessAssist;
import com.chen.fy.model.FyBusinessPay;
import com.jfinal.club.common.kit.CommonKit;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class AssistService {
	public final static AssistService me = new AssistService();
	private static final Logger logger = LogManager.getLogger(AssistService.class);

	public Page<Record> findPage(int currentPage, int pageSize, String condition, String keys) {
		Page<Record> modelPage = null;
		String select = "";
		String from = "";
		String where = "";
		String desc = "";
		Page<Record> list = Db.paginate(currentPage, pageSize, select, from + where + desc);
		return modelPage;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Record findModel(Integer id) {
		return null;

	}

	/**
	 * 批量生成应付单
	 * 新建应付单，根据外协单据数据、新建应付单，
	 * @return
	 */
	public Ret createPay(String[] assistId) throws Exception {
		Ret ret = null;
		StringBuilder idsb = new StringBuilder();
		SqlKit.joinIds(assistId, idsb);
		List<FyBusinessAssist> modelList = FyBusinessAssist.dao.find(
				"select a.*,o.quantity quantity from fy_business_assist a LEFT JOIN  fy_business_order o on o.id = a.order_id where a.id in "
						+ idsb.toString());
		List<FyBusinessPay> createModel = new ArrayList<>();
		Date date = new Date();
		for (FyBusinessAssist e : modelList) {
			FyBusinessPay item = new FyBusinessPay();
			item.setParentId(e.getId());
			item.setIsPurchase(false);// 不是采购单;
			item.setPurchaseDate(e.getAssistDate());// 采购时间
			item.setCheckResult(e.getCheckResult());
			item.setOrderNo(e.getAssistNo());

			item.setPurchaseCost(e.getAssistCost());// 单价
			item.setPurchaseAmount(e.getTatolAmount());// 总额
			item.setShouldPay(e.getTatolAmount());// 应付金额

			item.setOrderId(e.getOrderId());// 订单id
			item.setPurchaseNo(e.getAssistNo());// 采购编号
			item.setPurchaseName(e.getAssistProcess());// 加工工序，采购名称
			item.setInFrom("外协");
			Integer quantity = e.getInt("quantity");
			item.setPurchaseQuantity(quantity);// 外协数量
			item.setPayQuantity(quantity);// 应付数量
			item.setSupplierId(e.getAssistSupplierId());// 采购厂商id

			item.setHangDate(date);
			item.setCreateTime(date);// 生成时间
			item.setCheckTime(e.getBacktime());
			Date payDate = DateUtils.addMonths(date, 2);
			item.setPayDate(payDate);
			item.setInWarehouseTime(e.getBacktime());// 回厂时间，为入库时间
			createModel.add(item);//
		}
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				int[] re = Db.batchSave(createModel, createModel.size());
				List<Integer> createId = new ArrayList<Integer>();
				for (FyBusinessPay e : createModel) {
					createId.add(e.getId());
				}
				// StringBuilder idsb = new StringBuilder();
				// SqlKit.joinIds(assistId, idsb);
				// int updateNum = Db.update(sql + idsb);
				/**
				 * 更新数量与新建数量不一致，撤回
				 */
				return createModel.size() == CommonKit.totalInt(re);
			}
		});

		if (re) {
			/*
			 * 更新是否已生成应付单
			 */
			String sql = "update fy_business_assist a LEFT JOIN fy_business_pay p\r\n"
					+ " on a.id = p.parent_id and p.is_purchase = 0 \r\n" + " set a.is_create_pay = 1 \r\n"
					+ " where   is_purchase = 0 AND p.parent_id = a.id\r\n";
			Db.update(sql);

			ret = Ret.ok().set("msg", "新建应付单完成");
		} else {
			ret = Ret.ok().set("msg", "新建应付单失败");
		}
		return ret;

	}
}
