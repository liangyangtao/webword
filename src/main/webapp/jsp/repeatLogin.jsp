<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<head>
<!-- base href="http://10.21.8.14:8080/webword/" -->
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>知识创享网-重复登录</title>
<link rel="stylesheet" type="text/css" href="../word/css/base.css?v=<%=System.currentTimeMillis()%>">
<link rel="stylesheet" type="text/css" href="../word/css/index.css?v=<%=System.currentTimeMillis()%>">
</head>
<body>
<!-- body -->
<div class="repeatLogin">
    <dl>
        <dt><img src="../word/img/repeatLogin.jpg"></dt>
        <dd>
            <h1>该用户在其他地点登陆，本次登陆已失效！</h1>
            <h3>如非本人操作，登录密码可能已泄露，<br>请立即回到首页重新登录修改吧！</h3>
            <%
				String wwwName = request.getServerName();
				String homeUrl = "../view/index.do";
				if(wwwName.indexOf("bankrisk.") > -1){
				    homeUrl = "../risk/view/home.do"; 
				}
            %>
          <a href="<%=homeUrl%>" class="goHome">回到首页</a>  </dd>
    </dl>
</div>

<!-- body_end --> 
</body>
</html>