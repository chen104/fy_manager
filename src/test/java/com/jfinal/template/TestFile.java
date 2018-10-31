package com.jfinal.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class TestFile {
	@Test
	public void dotest() throws IOException {
		InputStream input = PermissonInit.class.getClassLoader()
				.getResourceAsStream("permission/addpermi");//
		if (input == null) {
			return;
		}
		BufferedReader read = new BufferedReader(new InputStreamReader(input));
		String line = read.readLine();

		while (line != null) {

			if (StringUtils.isEmpty(line)) {
			line = read.readLine();
				continue;
			}
			System.out.println(line);
			String[] item = line.split(",");
			String e = item[4];
			line = read.readLine();

		}
		read.close();
	}
}
