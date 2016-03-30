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
<title>找回密码</title>
	<link rel="stylesheet" type="text/css" href="common/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="common/css/index.css"/>	
	<link rel="stylesheet" type="text/css" href="common/css/fond_password2.css"/>
	<script type="text/javascript" src="common/js/jquery-1.8.3.min.js"></script>	
	<script type="text/javascript" src="common/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="common/js/common.js"></script>
</head>
  
<body>
<!-- header -->
<jsp:include page="header.jsp"></jsp:include>
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
<!-- body -->
<div class="content">
<form id="fond2_form" name="fond2_form" method="post">

	<h1><img src="common/img/fond_password2.png"/></h1>	
	<div class="form_bx">
		<div class="margin">我们已向您<span class="color">${fond1userPhone}</span>手机发送验证码！</div>
		<div >验证码：<input type="text" id="password" name="password" class="email_new" placeholder="输入验证码"  onfocus="this.placeholder=''" onblur="this.placeholder='输入验证码'"  /><a class="get_a">重新获取</a></div>
		<div id="text_tip"></div>
		<div><a class="abtn">下一步</a></div>
	</div>
	
</form>
</div>
<!-- footer -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>	