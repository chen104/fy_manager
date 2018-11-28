package com.chen.fy.controller.base;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.Account;
import com.chen.fy.model.Fyfile;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

public class FileController extends BaseController {
	private static final Logger logger = LogManager.getLogger(FileController.class);

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
		File fileDir = new File(PathKit.getWebRootPath(), "map");
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}
		try {
			for (UploadFile uploadfile : uploadFiles) {
				File ufile = uploadfile.getFile();

				File toFile = new File(PathKit.getWebRootPath() + "/map", uploadfile.getFileName());

				Fyfile file = new Fyfile();
				file.setOriginalFileName(uploadfile.getOriginalFileName());
				if (toFile.exists()) {
					String filename = uploadfile.getFileName();
					int l = filename.lastIndexOf(".");
					String hou = filename.substring(l);
					toFile = new File(PathKit.getWebRootPath() + "/map", UUID.randomUUID().toString() + hou);
				}
				file.setFilename(uploadfile.getFileName());

				file.setFilepath(fileDir.getAbsolutePath());
				file.setCreateTime(new Date());
				file.setUpdateTime(file.getCreateTime());
				file.setCreateBy(account.getId());
				try {
					FileUtils.moveFile(ufile, toFile);
				} catch (FileExistsException e1) {
					logger.info("文件重名");

				} catch (Exception e) {
					// TODO: handle exception
					logger.warn(e.getMessage());
				}

				file.save();
				ufile.delete();
				// FileUtils.moveFileToDirectory(uploadfile.getFile(), fileDir, true);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
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
			File file = new File(PathKit.getWebRootPath() + "/map", fyfile.getFilename());
			renderFile(file, fyfile.getOriginalFileName());
		}
	}

	public void viewFile() {
		Integer fileId = getParaToInt("id");
		Fyfile fyfile = Fyfile.dao.findById(fileId);
		String filename = fyfile.getOriginalFileName();
		try {
			if (filename.endsWith(".pdf")) {

				HttpServletResponse response = getResponse();

				File f = new File(PathKit.getWebRootPath() + File.separator + "map", fyfile.getOriginalFileName());
				if (!f.exists()) {
					renderText("未找到文件 ： " + filename);
					return;
				}
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));

				response.reset(); // 非常重要

				response.setContentType("application/pdf");
				// renderFile(f);
				OutputStream out = response.getOutputStream();
				byte[] buffer = new byte[1024];
				int length = -1;
				while ((length = in.read(buffer)) != -1) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.close();

				return;

			} else if (filename.endsWith(".jpg")) {
				setAttr("fileName", fyfile.getOriginalFileName());
				setAttr("fileUrl", "/map/" + fyfile.getFilename());
				render("showJpg.html");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());

		}
	}

	public void viewFileJpg() {

		HttpServletResponse response = getResponse();

		try {
			File f = new File(PathKit.getWebRootPath() + File.separator + "map", "fyl_logo.jpg");
			if (!f.exists()) {
				renderError(404);
				return;
			}

			response.reset(); // 非常重要

			response.setContentType("application/jpeg");
			renderFile(f);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());

		}
	}

	public void viewjpg() {

	}

	public static void main(String[] args) {
		String filename = "sdfsadf.jpg";
		int l = filename.lastIndexOf(".");
		String hou = filename.substring(l);
		System.out.println(hou);
	}

}
