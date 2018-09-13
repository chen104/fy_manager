package com.jfinal.number;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator;
import org.apache.commons.compress.archivers.zip.ScatterZipOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntryRequest;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.parallel.InputStreamSupplier;

public class ScatterSample {

	ParallelScatterZipCreator scatterZipCreator = new ParallelScatterZipCreator();
	ScatterZipOutputStream dirs = ScatterZipOutputStream.fileBased(File.createTempFile("scatter-dirs", "tmp"));

	public ScatterSample() throws IOException {
	}

	public void addEntry(ZipArchiveEntry zipArchiveEntry, InputStreamSupplier streamSupplier) throws IOException {
		if (zipArchiveEntry.isDirectory() && !zipArchiveEntry.isUnixSymlink())
			dirs.addArchiveEntry(ZipArchiveEntryRequest.createZipArchiveEntryRequest(zipArchiveEntry, streamSupplier));
		else
			scatterZipCreator.addArchiveEntry(zipArchiveEntry, streamSupplier);
	}

	public void writeTo(ZipArchiveOutputStream zipArchiveOutputStream)
			throws IOException, ExecutionException, InterruptedException {
		dirs.writeTo(zipArchiveOutputStream);
		dirs.close();
		scatterZipCreator.writeTo(zipArchiveOutputStream);
	}

	public static void main(String[] args) throws IOException {
		ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(new File("test.zip"));
		ScatterSample scatter = new ScatterSample();

	}
}