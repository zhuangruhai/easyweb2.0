package com.hichlink.easyweb.attachment.util;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class ZipUtil {

	/**
	 * 
	 * @param entryNames
	 * @param entryFiles
	 * @param out
	 * @throws IOException
	 */
	public static void zipFiles(String[] entryNames, File[] entryFiles, OutputStream out) throws IOException {
		ZipOutputStream zip=new ZipOutputStream(out);
		zip.setEncoding("gbk");
		for(int i=0; i<entryNames.length; i++){
			ZipEntry entry = new ZipEntry(entryNames[i]);
			zip.putNextEntry(entry);
			writeZip(entryFiles[i], zip);
			zip.closeEntry();
		}
		zip.finish();
	}

	private static void writeZip(File file, ZipOutputStream zip) throws IOException {
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[5000];
		int byteCount = in.read(buffer);
		while(byteCount>0){
			zip.write(buffer, 0, byteCount);
			byteCount = in.read(buffer);
		}
		in.close();
	}
}
