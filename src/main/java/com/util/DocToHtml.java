package com.util;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import jxl.common.Logger;
import rmi.model.Doc2HtmlEntity;
import rmi.service.Doc2HtmlService;

/**
 * doc文档转html文件
 */
public class DocToHtml {
	
	public static final Logger logger = Logger.getLogger(DocToHtml.class);

	public static boolean docToHtml(String filePath,String fileName){
		boolean flag = false;
		List<Doc2HtmlEntity> doc2htmlList = null;
		try {
			System.out.println("rmi://"+DoProperties.properties.getProperty("docToHtmlServicePath"));
			Doc2HtmlService doc2htmlService=(Doc2HtmlService)Naming.lookup("rmi://"+DoProperties.properties.getProperty("docToHtmlServicePath"));
			System.out.println("\\\\"+DoProperties.properties.getProperty("docPath")+"\\"+filePath+"\\"+fileName);
			doc2htmlList=doc2htmlService.GetList("\\\\"+DoProperties.properties.getProperty("docPath")+"\\"+filePath+"\\"+fileName);
		} catch (MalformedURLException e) {
			logger.error("1="+e);
		} catch (RemoteException e) {
			logger.error("2="+e);
		} catch (NotBoundException e) {
			logger.error("3="+e);
		}
		if(doc2htmlList != null){
			flag = true;
		}
		return flag;
	}
	public static void main(String[] args) {
		docToHtml("doc","article1075.doc");
	}
}
