<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
  	<meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>	
	<meta name="keywords" content=""/>
	<meta name="description" content=""/>
    <title>首页</title>
	<link rel="stylesheet" type="text/css" href="common/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="common/css/index.css"/>	
	<script type="text/javascript" src="common/js/jquery-1.8.3.min.js"></script>	
	<script type="text/javascript" src="common/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="common/js/common.js"></script>
</head>

<body>
<!--  -->
<div name="遮挡背景" class="ui_back2"></div>
<div name="遮挡背景" class="ui_back"></div>
<div class="box_content">
<!-- 定制我的风险预警信息库 -->
		<div name="定制我的风险预警信息库" class="sbox_ industry_options">
			<h3>定制我的风险预警信息库<img src="common/img/s_exit.png" /></h3>
			<div class="sb_middle">
				<div class="stype stype1">					
					<dl>
						<!-- 一级 -->
						<h5><span><i></i><input type="checkbox" checked="checked"/><img src="common/img/choose2.png" />一级农业</span></h5>
						<!-- 二级 -->
						<dt><span><i></i><input type="checkbox"/><img src="common/img/choose2.png" />小麦、玉米、大豆</span></dt>
						<!-- 三级 -->
						<dd><span><input type="checkbox"/><img src="common/img/choose2.png" />农小麦</span></dd>
						<dd><span><input type="checkbox"/><img src="common/img/choose2.png" />农小麦</span></dd>
						<dt><span><i></i><input type="checkbox"/><img src="common/img/choose2.png" />小麦、玉米、大豆</span></dt>
						<dd><span><input type="checkbox"/><img src="common/img/choose2.png" />农小麦</span></dd>
					</dl>
				</div>
				<div class="stype stype2">	
						<p> 为了更高效的推送风险信息，请您完善以下信息：</p>
						<div class="s1">您的岗位：<input class="jobs " type="text"/><input value="" type="hidden"/></div>
						<div class="s2">岗位职责：<input class="responsibility" type="text"/><input value="" type="hidden"/></div>
						<div class="s3">关注的行业：<input class="industry" type="text"/><input value="" type="hidden"/></div>
						<div class="s4">我的关键词：<input class="keywords" type="text"/><input value="" type="hidden"/></div>
						<p class="tip2">关键词之间以一个空格分隔</p>
				</div>
			</div>
			<div class="sb_button  sb1"><a class="a1">关闭</a><a class="a2">确认</a><a class="a3">下一步</a><a class="a4">上一步</a></div>
		</div>	

</div>
<div class="header">
	<div class="middle">
		<span class="logo"><a href="index.html"><img src="common/img/logo.png"/></a></span>
		<!-- 登录前 -->
		<span class="right"><img src="common/img/tel.png"/>010-63368810<a target="_blank" href="login1.html">登录</a><a target="_blank" href="regist.html">注册</a></span>
		<!-- 登陆后 -->
		<!-- <span class="right login"><img src="common/img/tel.png"/>010-63368810<a>张三</a><a class="down2 menu2_down2"></a></span> -->
		<ul class="udown_menu"><li><a>知识创享平台</a></li><li><a href="user.html">账号设置</a></li><li class="ast"><a target="_blank" href="index.html">退出登录</a></li></ul>
	</div>
</div>
<div class="menu img_change">
	<div class="middle">
		<ul>
			<li><a onclick="ser_box()">我的风险预警</a></li>
			<!-- 点击“我的风险预警”，如未登录，跳转登录页面；如果已登录，之前没有设置过栏目，则跳出栏目设置弹框，若之前设置过，则跳转我的风险预警页面 调用：ser_box() -->
			
			<c:forEach items="${rootModuleList}" var="module">
				<c:if test="${module.sortnumber == 1}">
   					<li><a class="active" href="index.html">${module.mname}</a></li>
				</c:if>
				<c:if test="${module.sortnumber != 1}">
   					<li><a  href="index.html">${module.mname}</a></li>
				</c:if>
			 </c:forEach>
		</ul>
	</div>
</div>
<!-- body -->
<div class="i_banner"></div>
<div class="i_box_content">
	<c:forEach items="${riskWithModuleList}" var="riskWithModule">
	<div class="i_box_content">
	<div class="i_box">
		<h4><a class="ha" >${riskWithModule.module.mname} </a><span class="more"><a target="_blank" href="menu2.html">更多>></a></span></h4>
		<div class="box_banner"><img src="common/img/i_img1.jpg"/></div>
		<ul class="bul">
		
		<c:forEach items="${riskWithModule.riskList}" var="risk">
			<li title="${risk.title}"><img class="i_point" src="common/img/i_point.jpg"/><a target="_blank" href="news_content.html">${risk.title}</a></li>
		</c:forEach>
		</ul>
	</div>
</div>

</c:forEach>
</div>

<!-- footer -->
<div class="footer">
	<p class="p">
		公司地址：北京市西城区广安门外大街248号17层<br>
		北京银联信投资顾问有限责任公司 版权所有 | 京ICP备 05058919号| Copyright 2009, All right reserved.
	</p>
</div>
</body>
</html>
