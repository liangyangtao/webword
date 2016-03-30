/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: GetZipDocFiles.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-19
 * @version 1.0
 */
package com.web.homePage.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipOutputStream;


import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * <p>Title: GetZipDocFiles</p>
 * <p>Description: TODO</p>
 * @author knight
 * @date 2015-5-19
 */
public class GetZipDocFiles {
	
	private static final Logger logger = Logger.getLogger(GetZipDocFiles.class);
	
	private static final int BYTE_LENGTH=1024;
	
	public GetZipDocFiles(){}
	
	public static List<File> getFiles(String zipFilePath,String unZipFilePath){
		String newZipPath=unZipFilePath+File.separator+ System.currentTimeMillis();
		unZip(zipFilePath, newZipPath);
		return getFiles(new File(newZipPath), "doc");
	}
	
	public static void zip(String sourceDir, String zipFile) {
		OutputStream os;
		try {
			os = new FileOutputStream(zipFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			ZipOutputStream zos = new ZipOutputStream(bos);
			File file = new File(sourceDir);
			String basePath = null;
			if (file.isDirectory()) {
				basePath = file.getPath();
			} else {
				basePath = file.getParent();
			}
			zipFile(file, basePath, zos);
			zos.closeEntry();
			zos.close();
		} catch (Exception e) {
			logger.error(e);
		}
	}


	private static void zipFile(File source, String basePath,
	ZipOutputStream zos) {
		File[] files = new File[0];
		if (source.isDirectory()) {
			files = source.listFiles();
		} else {
			files = new File[1];
			files[0] = source;
		}
		String pathName;
		byte[] buf = new byte[1024];
		int length = 0;
		try {
			for (File file : files) {
				if (file.isDirectory()) {
					pathName = file.getPath().substring(basePath.length() + 1)
					+ "/";
					zos.putNextEntry(new ZipEntry(pathName));
					zipFile(file, basePath, zos);
				} else {
					pathName = file.getPath().substring(basePath.length() + 1);
					InputStream is = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(is);
					zos.putNextEntry(new ZipEntry(pathName));
					while ((length = bis.read(buf)) > 0) {
						zos.write(buf, 0, length);
					}
					is.close();
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

	private static void unZip(String zipfile, String destDir) {
		String fileDir = destDir.endsWith("\\") ? destDir : destDir + "\\";
		byte b[] = new byte[BYTE_LENGTH];
		OutputStream outputStream = null;
		InputStream inputStream = null;
		int length;
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(new File(zipfile));
			Enumeration enumeration = zipFile.getEntries();
			ZipEntry zipEntry = null;
			while (enumeration.hasMoreElements()) {
				zipEntry = (ZipEntry) enumeration.nextElement();
				File loadFile = new File(fileDir + zipEntry.getName());
				if (zipEntry.isDirectory()) {
					loadFile.mkdirs();
				} else {
					if (!loadFile.getParentFile().exists())
						loadFile.getParentFile().mkdirs();
						outputStream = new FileOutputStream(loadFile);
						inputStream = zipFile.getInputStream(zipEntry);
					while ((length = inputStream.read(b)) > 0)
						outputStream.write(b, 0, length);
				}
			}
		} catch (IOException e) {
			logger.error(e);
		}finally{
			try {
				outputStream.close();
				inputStream.close();
			} catch (IOException e) {
				logger.error(e);
			}
			
		}
	}
	
	private static List<File> getFiles(File fileDir, String fileType) {
        List<File> lfile = new ArrayList<File>();
        File[] fs = fileDir.listFiles();
        for (File f : fs) {
            if (f.isFile()) {
                if ("doc".equals(f.getName().substring(f.getName().lastIndexOf(".") + 1,f.getName().length()))
                		|| "docx".equals(f.getName().substring(f.getName().lastIndexOf(".") + 1,f.getName().length())))
                    lfile.add(f);
            } else {
                List<File> ftemps = getFiles(f,fileType);
                lfile.addAll(ftemps);
            }
        }
        return lfile;
    }
}
