<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter,java.util.*,java.io.*" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	Properties pro = new Properties();
	String realpath = application.getRealPath("/WEB-INF/classes");
	try {
		//读取配置文件
		FileInputStream in = new FileInputStream(realpath
				+ "/resources.properties");
		pro.load(in);
	} catch (FileNotFoundException e) {
		out.println(e);
	} catch (IOException e) {
		out.println(e);
	}

	//通过key获取配置文件
	request.setCharacterEncoding("utf-8");
	response.setHeader("Content-Type", "text/html");
	String rootPath = application.getRealPath("/");
	//读配置文件
	rootPath = pro.getProperty("nginxPic"); 
	out.write(new ActionEnter(request, rootPath).exec());
%>