package com.chen.fy.controller.business.assist;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Db;
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
}
