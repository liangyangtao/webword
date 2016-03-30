/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: XmlUtils.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-8
 * @version 1.0
 */
package com.web.homePage.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * <p>Title: XmlUtils</p>
 * <p>Description: TODO</p>
 * @author knight
 * @date 2015-5-8
 */
public class XmlUtils {
	private XmlUtils(){}
	static Logger logger = Logger.getLogger(XmlUtils.class);

	private static XmlUtils xmlUtil;
	protected static Document document = null;
	
	public static synchronized XmlUtils getInstance() {
		try {
			if (xmlUtil == null) {
				xmlUtil = new XmlUtils();
			}
//			String pathxml = XmlUtils.class.getResource("./../").toURI().getPath();
			String pathxml = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
			File file = new File(pathxml+"specialCharacters.xml");
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(file);
			return xmlUtil;
		} catch (DocumentException e) {
			logger.error(e);
			return null;
		}
	}
	
	public static synchronized List<Map<String,Object>> getSpecialCharactersConfig(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		Element root = document.getRootElement();
		Iterator<Element> noderoot = root.elementIterator();
		while(noderoot.hasNext()){
			Map<String,Object> map = new HashMap<String,Object>();
			Element subroot = noderoot.next();
			Iterator<Element> subnoderoot = subroot.elementIterator();
			while(subnoderoot.hasNext()){
				Element subsubroot = subnoderoot.next();
				map.put(subsubroot.getName(), subsubroot.getText());
			}
			list.add(map);
//			System.out.println(subroot.getName()+"=====22222=====");
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		XmlUtils.getInstance();
		XmlUtils.getSpecialCharactersConfig();
	}
}
