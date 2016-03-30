package com.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import com.model.param.BaseParam;

/**
 * 
 * @author zile
 * 
 */
public class HighchartsFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5871650840868160612L;

	private String suffix;

	private byte[] bytes;

	public HighchartsFile(File file) throws FileNotFoundException {
		String path = file.getAbsolutePath();
		System.out.println("---------------------");
		if (path.contains("resources")) {
			suffix = path.substring(path.indexOf("resources") - 1);
		}

		FileInputStream fis = null;
		try {
			try {
				fis = new FileInputStream(file);
				bytes = new byte[fis.available()];
				fis.read(bytes);
			} finally {
				if (fis != null) {
					fis.close();
				}
			}
		} catch (FileNotFoundException e) {
			throw new PluginModelException("no image file found!", e);
		} catch (IOException e) {
			throw new PluginModelException("IO error", e);
		}

	}

	public String link() {
		return replace();
	}

	public String link(String prefix) {
		if (prefix == null) {
			return link();
		} else {
			return prefix + link();
		}
	}

	public String link(BaseParam p) {
		if (p == null) {
			return link();
		} else {
			return link(p.getLinkPrefix());
		}
	}

	public String getSuffix() {
		return suffix;
	}

	public byte[] getBytes() {
		return bytes;
	}

	private String replace() {
		return suffix.replaceAll("\\\\", "/");
	}

}
