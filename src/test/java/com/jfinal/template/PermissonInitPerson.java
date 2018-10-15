package com.jfinal.template;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;

import com.chen.fy.model.ColPermision;
import com.jfinal.String.TestString;
import com.jfinal.config.Constants;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.plugin.IPlugin;

public class PermissonInitPerson {
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

	public void initPermisson() throws IOException {
		InputStream input = TestString.class.getClassLoader().getResourceAsStream("permission/colperm");

		BufferedReader read = new BufferedReader(new InputStreamReader(input));
		String line = read.readLine();

		while (line != null) {
			System.out.println(line);
			if (StringUtils.isEmpty(line)) {
				line = read.readLine();
				continue;
			}
			String[] p = line.split(",");
			ColPermision col = new ColPermision();
			col.setTitle(p[0]);
			col.setCkey(p[1]);
			col.setCtable(p[2]);
			boolean re = col.save();
			System.out.println(p[0] + "  " + re);
			line = read.readLine();
		}
		if (input != null) {
			input.close();
		}
	}

	public void readFormDBPermisson() throws IOException {
		String parent = TestString.class.getClassLoader().getResource("").getFile();
		File init = new File(parent, "order.txt");
		if (!init.exists()) {
			init.createNewFile();
		}
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(init)));
		ColPermision dao = new ColPermision().dao();
		List<ColPermision> list = dao.find("select * from col_permision");
		for (ColPermision e : list) {

			writer.write(e.getTitle() + "," + e.getCkey() + "," + e.getCtable() + "\n");

		}
		writer.close();
		System.out.println(init.getAbsolutePath());

	}

}
