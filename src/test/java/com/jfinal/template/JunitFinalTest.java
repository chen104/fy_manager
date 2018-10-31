package com.jfinal.template;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import com.chen.fy.model.Permission;
import com.jfinal.config.Constants;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * jfinal单元测试
 * 
 * [@author](https://my.oschina.net/arthor) roseboy
 *
 */
public class JunitFinalTest {
	private Constants constants;
	private Plugins plugins;

	/**
	 * 通过配置类启动jfinal插件等
	 */
	@Before
	public void initConfig() {
		try {
			String configClass = "com.jfinal.club.common.JFinalClubConfig";
			Class<?> clazz = Class.forName(configClass);
			JFinalConfig jfinalConfig = (JFinalConfig) clazz.newInstance();
			constants = new Constants();
			jfinalConfig.configConstant(constants);
			plugins = new Plugins();
			jfinalConfig.configPlugin(plugins);
			for (IPlugin plug : plugins.getPluginList()) {
				plug.start();
			}
			jfinalConfig.afterJFinalStart();
			System.out.println("\n==JunitFinalTest Start==================\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void totest() {
		List<Record> list = Db.find("select * from fy_business_purchase ");
		if (list.size() > 0) {
			Date l = list.get(0).getDate("purchase_date");

			System.out.println(l.getTime());
		}
		String sql = Db.getSql("order.updateGetpayInfo");
		System.out.println(sql);
	}

	/**
	 * 停止jfinal插件
	 */
	@After
	public void endConfig() {
		System.out.println("\n==JunitFinalTest End====================");
		try {
			if (plugins != null) {
				for (IPlugin plug : plugins.getPluginList()) {
					plug.stop();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void dotest() {
		String item[][] = {
				{ "/fy/admin/base/department/update", "com.chen.fy.controller.base.DepartmentController", "修改部门" },
				{ "/fy/admin/base/department/save", "com.chen.fy.controller.base.DepartmentController", "添加部门" },
				{ "/fy/admin/base/department/delete", "com.chen.fy.controller.base.DepartmentController", "删除部门" },

				{ "/fy/admin/base/customer", "com.chen.fy.controller.base.CustomerController", "客户首页" },
				{ "/fy/admin/base/customer/update", "com.chen.fy.controller.base.CustomerController", "修改客户" },
				{ "/fy/admin/base/customer/save", "com.chen.fy.controller.base.CustomerController", "添加客户" },
				{ "/fy/admin/base/customer/delete", "com.chen.fy.controller.base.CustomerController", "删除客户" },

				{ "/fy/admin/base/category", "com.chen.fy.controller.base.CategoryController", "分类首页" },
				{ "/fy/admin/base/category/update", "com.chen.fy.controller.base.CategoryController", "修改分类" },
				{ "/fy/admin/base/category/save", "com.chen.fy.controller.base.CategoryController", "添加分类" },
				{ "/fy/admin/base/category/delete", "com.chen.fy.controller.base.CategoryController", "删除" },

				{ "/fy/admin/base/file", "com.chen.fy.controller.base.FileController", "文件首页" },
				{ "/fy/admin/base/file/update", "com.chen.fy.controller.base.FileController", "新增文件" },
				{ "/fy/admin/base/file/save", "com.chen.fy.controller.base.FileController", "删除文件" },

				{ "/fy/admin/base/person", "com.chen.fy.controller.base.PersonController", "人员首页" },
				{ "/fy/admin/base/person/update", "com.chen.fy.controller.base.PersonController", "修改人员" },
				{ "/fy/admin/base/person/save", "com.chen.fy.controller.base.PersonController", "添加人员" },
				{ "/fy/admin/base/person/delete", "com.chen.fy.controller.base.PersonController", "删除人员" },

				{ "/fy/admin/base/supplier", "com.chen.fy.controller.base.SupplierController", "厂商首页" },
				{ "/fy/admin/base/supplier/update", "com.chen.fy.controller.base.SupplierController", "修改厂商" },
				{ "/fy/admin/base/supplier/save", "com.chen.fy.controller.base.SupplierController", "添加厂商" },
				{ "/fy/admin/base/supplier/delete", "com.chen.fy.controller.base.SupplierController", "删除厂商" },

				{ "/fy/admin/base/supplier/delete", "com.chen.fy.controller.base.TaxRateController", "税率首页" },
				{ "/fy/admin/base/supplier/delete", "com.chen.fy.controller.base.TaxRateController", "税率首页" },
				{ "/fy/admin/base/supplier/delete", "com.chen.fy.controller.base.TaxRateController", "税率首页" },
				{ "/fy/admin/base/supplier/delete", "com.chen.fy.controller.base.TaxRateController", "税率首页" },

				{ "/fy/admin/base/unit", "com.chen.fy.controller.base.UnitController", "单位首页" },
				{ "/fy/admin/base/unit/update", "com.chen.fy.controller.base.UnitController", "修改单位" },
				{ "/fy/admin/base/unit/save", "com.chen.fy.controller.base.UnitController", "添加单位" },
				{ "/fy/admin/base/unit/delete", "com.chen.fy.controller.base.UnitController", "删除单位" },

		};
		Permission p = new Permission();
		for (int i = 0; i < item.length; i++) {
			p.setActionKey(item[i][0]);
			p.setController(item[i][1]);
			p.setRemark(item[i][2]);
			// p.setIsfy(true);
			p.save();
			p.setId(null);
		}
		System.out.println("finish");

	}

	public void initPermisson() {

	}
}