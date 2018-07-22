package com.chen.fy.controller.base;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.Customer;
import com.chen.fy.model.Fyfile;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.Account;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

public class FileController extends BaseController {
	public void index() {
		String key = getPara("keyWord");
		Page<Fyfile> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = Fyfile.dao.paginate(getParaToInt("p", 1), 10, "select f.*,a.nickName nickName ",
					"from fy_base_fyfile f left join account a   on f.create_by = a.id  order by f.id desc");
		} else {
			modelPage = Fyfile.dao.paginate(getParaToInt("p", 1), 10, "select f.* ,a.nickName nickName ",
					"from fy_base_fyfile  f left join account a  on f.create_by = a.id where originalFileName like ? order by id desc",
					"%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("list.html");
	}

	public void uploadFile() {
		List<UploadFile> uploadFiles = getFiles();
		Account account = getSessionAttr("account");
		for (UploadFile uploadfile : uploadFiles) {
			Fyfile file = new Fyfile();
			file.setFilename(uploadfile.getFileName());
			file.setOriginalFileName(uploadfile.getOriginalFileName());
			file.setFilepath(uploadfile.getUploadPath());
			file.setCreateTime(new Date());
			file.setUpdateTime(file.getCreateTime());
			file.setCreateBy(account.getId());
			file.save();
		}
		Ret ret = null;

		ret = Ret.ok("msg", "新建  " + " 信息成功");

		renderJson(ret);
	}

	public void download() {
		Integer id = getParaToInt("id");

		boolean re = Customer.dao.deleteById(id);

		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void goview() {
		render("upload.html");
	}

}
