package com.jfinal.number;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.zip.ZipEntry;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.parallel.InputStreamSupplier;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Test;

public class ScatterSampleTest {

	@Test
	public void testSample() throws Exception {
		// final File result = File.createTempFile("testSample", "fe");
		final File result = new File("test.zip");
		createFile(result);
		// checkFile(result);
	}

	private void createFile(final File result) throws IOException, ExecutionException, InterruptedException {
		final ScatterSample scatterSample = new ScatterSample();
		final ZipArchiveEntry archiveEntry = new ZipArchiveEntry("MP-10000.pdf");
		archiveEntry.setMethod(ZipEntry.DEFLATED);
		final FileInputStream in = new FileInputStream(new File(
				"E:\\jfinalPlace\\jfinal-fy\\src\\main\\webapp\\download\\excel\\报目表2018-09-12\\MP-10000.pdf"));
		final InputStreamSupplier supp = new InputStreamSupplier() {
			@Override
			public InputStream get() {
				// return new ByteArrayInputStream("Hello".getBytes());
				return in;
			}
		};

		scatterSample.addEntry(archiveEntry, supp);
		// ZipArchiveEntry archiveEntry1 = new ZipArchiveEntry("订单2018-09-12.xlsx");
		// scatterSample.addEntry(archiveEntry, supp);

		final ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(result);
		scatterSample.writeTo(zipArchiveOutputStream);
		zipArchiveOutputStream.close();
	}

	private void checkFile(final File result) throws IOException {
		String re = result.getAbsolutePath();
		System.out.println(re);
		final ZipFile zf = new ZipFile(result);
		final ZipArchiveEntry archiveEntry1 = zf.getEntries().nextElement();
		assertEquals("test1.xml", archiveEntry1.getName());
		final InputStream inputStream = zf.getInputStream(archiveEntry1);
		final byte[] b = new byte[6];
		final int i = IOUtils.readFully(inputStream, b);
		assertEquals(5, i);
		assertEquals('H', b[0]);
		assertEquals('o', b[4]);
		zf.close();
		// result.delete();
	}
}