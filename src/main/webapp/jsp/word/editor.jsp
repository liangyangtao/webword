<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'word.jsp' starting page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="chrome=1" >
    <!--<meta http-equiv="X-UA-Compatible" content="chrome=1"/>-->
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    word页面 <br>
    <p>单个文件</p>
    ${article.articleName}<br>
    <p>遍历文件</p>
    <c:forEach items="${articleList}" var="node" varStatus="idxx">
    ${idxx} -- ${node.articleName}
    </c:forEach>
    <p>文档内容</p>
    ${content.nodeContent}
  </body>
</html>
