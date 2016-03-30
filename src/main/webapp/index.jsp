<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%

String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
String wwwName = request.getServerName();
//System.out.println(wwwName.indexOf("bankrisk.unbank.info"));
if(wwwName.indexOf("bankrisk.unbank.info")>-1){
	//System.out.println("---");
	response.sendRedirect(url+"/webword/risk/view/home.do");
}else{
	//System.out.println("++++");
	response.sendRedirect(url+"/webword/view/home.do");
}
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
  	<meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=8"><!-- 放在前面 默认为IE8 -->
	<meta http-equiv="Expires" content="0">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-control" content="no-cache">
	<meta http-equiv="Cache" content="no-cache">
    <title>创享堂</title>
	   
  </head>

<body>
</body>
</html>
