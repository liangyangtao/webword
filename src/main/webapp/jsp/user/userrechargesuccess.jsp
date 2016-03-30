<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<title>知识创享网-支付成功</title>
<link rel="stylesheet" type="text/css" href="common/css/cxcss/base.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" type="text/css" href="common/css/cxcss/index.css?v=<%=System.currentTimeMillis()%>" />
</head>
<body>
<!-- header -->
	<jsp:include page="../myCenter.jsp"></jsp:include>
<!-- header_end --> 
<!-- body -->
	<div class="paySuccess" style="margin-top:0px;">
		<dl style="height:auto">
			<dt>
				<img src="common/img/cximg/pay_success.png">
			</dt>
			<dd style="line-height:65px;">
				充值成功！已充值<span>${count}</span>创享币！
			</dd>

		</dl>
		<p class="btn">
			<a href="user/recharge.do" class="goHome">继续充值</a><a href="user/capital.do" class="goHome">充值记录</a><a
						href="view/home.do" class="goHome">回到首页</a>
		</p>
	</div>
	
	<!-- body_end -->
	<!-- footer -->
<jsp:include page="../footer.jsp"></jsp:include>
<!-- footer_end -->
<div name="遮挡背景" class="ui_back2">
		<div name="遮挡背景" class="ui_back"></div>
	</div>
	<div class="box_content">
		<div class="pop_div pop_div2 exit_pop">
			<span class="pop_closeBt" onclick="closeDiv();"></span>
			<p>提示</p>
			<h3>确认退出?</h3>
			<div class="sb_button">
				<a onclick="logout()" class="a1"></a><a class="a2"
					onclick="closeDiv()"></a>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/alert.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script> 
</body>
</html>
