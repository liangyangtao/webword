<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	response.setContentType("application/msword;charset=UTF-8");
	response.setHeader("Content-Disposition", "attachment;filename="
			+ java.net.URLEncoder.encode("${filename}.doc", "UTF-8"));
				
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:v="urn:schemas-microsoft-com:vml"
	xmlns:o="urn:schemas-microsoft-com:office:office"
	xmlns:w="urn:schemas-microsoft-com:office:word"
	xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
	xmlns:st1="urn:schemas-microsoft-com:office:smarttags"
	xmlns="http://www.w3.org/TR/REC-html40">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="ProgId" content="Word.Document">
		<meta name="Generator" content="Microsoft Word 12">
		<meta name="Originator" content="Microsoft Word 12">
    	<base href="<%=basePath%>">    
   	 	<title>导成word文件</title>    
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
