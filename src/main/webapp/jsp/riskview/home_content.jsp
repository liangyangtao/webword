<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>知识创享网-首页</title>
	<link rel="stylesheet" type="text/css" href="common/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="common/css/index.css"/>	
	<script type="text/javascript" src="common/js/jquery-1.8.3.min.js"></script>	
	<script type="text/javascript" src="common/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="common/js/common.js"></script>
</head>

<body>
<jsp:include page="header.jsp"></jsp:include>
<!-- body -->
<div class="i_banner"></div>
<div class="i_box_content">
<c:forEach items="${riskWithModuleList}" var="riskWithModule" varStatus="status">
	<div class="i_box">
		<h4><a class="ha" >${riskWithModule.module.mname} </a><span class="more"><a target="_blank" href="risk/view/getNewsByModuleId.do?parentModuleId=${riskWithModule.module.mid}&page=1&pageSize=10">更多>></a></span></h4>
		<div class="box_banner"><img src="common/img/i_img${status.count%7+1}.jpg"/></div>
		<ul class="bul">
		
		<c:forEach items="${riskWithModule.riskList}" var="risk">
			<li title="${risk.title}"><img class="i_point" src="common/img/i_point.jpg"/><a target="_blank" href="news_content.html">${risk.title}</a></li>
		</c:forEach>
		</ul>	
	</div>
</c:forEach>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>	
