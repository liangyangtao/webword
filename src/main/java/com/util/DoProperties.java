package com.util;

import java.io.FileInputStream;
import java.util.Properties;

public class DoProperties {

	
	public static Properties properties;
	
	static{
		if(properties == null){
			DoProperties.getService();
		}
	}
	
	private static void getService(){
		DoProperties.properties = new Properties();
		try {
			String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
			FileInputStream fis = new FileInputStream(path+"configInfo.properties");
			DoProperties.properties.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
