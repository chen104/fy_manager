package com.chen.fy.controller.base;

import java.io.File;
import java.net.URL;

import com.chen.fy.controller.BaseController;

public class TempletController extends BaseController {
	public void index() {
		render("list.html");

	}

	public void list() {
		render("list.html");
	}

	public void download() {
		String filename = getPara("file");
		String downloadName = getPara("filename");
		System.out.println(filename + "  " + downloadName);
		URL url = this.getClass().getClassLoader().getResource("templet/upload/" + filename);
		System.out.println(url.getFile());
		if (url != null && url.getFile() != null) {
			File file = new File(url.getFile());
			if (file.exists()) {
				renderFile(file, downloadName);
			} else {
				System.out.println("文件不存在");
				renderText("文件不存在");
			}

		} else {
			System.out.println("url为空");
			renderText("文件不存在");
		}

	}

}
