package com.chen.fy.controller.base;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.Fyfile;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.Account;
import com.jfinal.kit.PathKit;
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
		File fileDir = new File(PathKit.getWebRootPath(), "upload/map");
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}
		try {
			for (UploadFile uploadfile : uploadFiles) {
				Fyfile file = new Fyfile();
				file.setFilename(uploadfile.getFileName());
				file.setOriginalFileName(uploadfile.getOriginalFileName());
				file.setFilepath(fileDir.getAbsolutePath());
				file.setCreateTime(new Date());
				file.setUpdateTime(file.getCreateTime());
				file.setCreateBy(account.getId());
				file.save();
				FileUtils.moveFileToDirectory(uploadfile.getFile(), fileDir, true);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Ret ret = null;

		ret = Ret.ok("msg", "新建  " + " 信息成功");

		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("id");
		Fyfile fyfile = Fyfile.dao.findById(id);
		File file = new File(fyfile.getFilepath(), fyfile.getFilename());
		if (file.exists()) {
			file.delete();
		}
		boolean re = Fyfile.dao.deleteById(id);

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

	public void searchFileJson() {

		String key = getPara("keyWord");
		Page<Fyfile> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = Fyfile.dao.paginate(getParaToInt("p", 1), 10, "select f.* ",
					"from fy_base_fyfile f   order by f.id desc");

		} else {
			modelPage = Fyfile.dao.paginate(getParaToInt("p", 1), 10, "select f.* ",
					"from fy_base_fyfile  f where originalFileName like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("path", PathKit.getWebRootPath());
		renderJson(modelPage);
	}

	public void download() {
		Integer integer = getParaToInt("fileId");
		Fyfile fyfile = Fyfile.dao.findById(integer);
		if (fyfile == null) {
			renderText("没有找到文件");

		} else {
			File file = new File(fyfile.getFilepath(), fyfile.getFilename());
			renderFile(file, fyfile.getOriginalFileName());
		}
	}

}
