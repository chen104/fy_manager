package com.jfinal.club.common.kit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator;
import org.apache.commons.compress.archivers.zip.ScatterZipOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntryRequest;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.parallel.InputStreamSupplier;

public class ZipKit {

	ParallelScatterZipCreator scatterZipCreator = new ParallelScatterZipCreator();
	ScatterZipOutputStream dirs;
	ZipArchiveOutputStream zipArchiveOutputStream;

	public ZipKit(File zip) throws FileNotFoundException, IOException {
		dirs = ScatterZipOutputStream.fileBased(File.createTempFile("scatter-dirs", "tmp"));
		zipArchiveOutputStream = new ZipArchiveOutputStream(zip);
	}

	public void addEntry(ZipArchiveEntry zipArchiveEntry, String file) throws FileNotFoundException, IOException {
		addEntry(zipArchiveEntry, new File(file));

	}

	public void addEntry(ZipArchiveEntry zipArchiveEntry, File file) throws FileNotFoundException, IOException {
		addEntry(zipArchiveEntry, new FileInputStream(file));
	}

	public void addEntry(ZipArchiveEntry zipArchiveEntry, InputStream input) throws IOException {
		InputStreamSupplier streamSupplier = new InputStreamSupplier() {

			@Override
			public InputStream get() {
				// TODO Auto-generated method stub
				return input;
			}
		};
		if (zipArchiveEntry.isDirectory() && !zipArchiveEntry.isUnixSymlink())
			dirs.addArchiveEntry(ZipArchiveEntryRequest.createZipArchiveEntryRequest(zipArchiveEntry, streamSupplier));
		else
			scatterZipCreator.addArchiveEntry(zipArchiveEntry, streamSupplier);
	}

	public void addDir(File dir) throws FileNotFoundException, IOException {
		File[] files = dir.listFiles();
		for (File e : files) {
			ZipArchiveEntry zipEntry = new ZipArchiveEntry(e.getName());
			zipEntry.setMethod(ZipArchiveEntry.DEFLATED);
			addEntry(zipEntry, e);
		}
	}

	public void close() throws IOException, InterruptedException, ExecutionException {
		dirs.writeTo(zipArchiveOutputStream);
		dirs.close();
		scatterZipCreator.writeTo(zipArchiveOutputStream);
		zipArchiveOutputStream.close();

	}
}
