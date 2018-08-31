package com.chen.fy.task;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.cron4j.ITask;

public class DeleteFileTask implements ITask {
	private static final Logger logger = LogManager.getLogger(DeleteFileTask.class);

	@Override
	public void run() {
		String filename = PathKit.getWebRootPath() + File.separator + "download/excel";
		File parent = new File(filename);
		if (parent.exists()) {
			// System.out.println("下载目录");
		}
		File[] files = parent.listFiles();
		try {
			if (files != null) {
				for (File e : files) {
					logger.info("task delete file " + e.getAbsolutePath());
					FileUtils.forceDelete(e);

				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 清空上传
		filename = PathKit.getWebRootPath() + File.separator + "upload";
		parent = new File(filename);
		if (parent.exists()) {
			// System.out.println("上载目录");

		}
		files = parent.listFiles();
		try {
			if (files != null) {
				for (File e : files) {
					logger.info("task delete file " + e.getAbsolutePath());
					FileUtils.forceDelete(e);
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void stop() {

	}

}
