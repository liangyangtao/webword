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
<title>风险预警-首页</title>
	<link rel="stylesheet" type="text/css" href="common/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="common/css/index.css"/>	
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

<div name="遮挡背景" class="ui_back"></div>
<div class="box_content">
<!--  试用到期 温馨提示1  -->
	<div  class="sbox_ day_tips day_tips1">
			<h3>温馨提示<img src="common/img/s_exit.png" /></h3>
			<div class="sb_middle">
				<p class="p1">尊敬的:${user.userName}</p>
				<p class="p2"> &nbsp;&nbsp;&nbsp;&nbsp;您的免费试用已到期，为不影响您的正常使用，请您及时联系客服进行充值缴费。<br>&nbsp;&nbsp;&nbsp;&nbsp;联系电话: 010-63368810</p>
			</div>
			<div class="sb_button" ><a class="a1">知道了</a></div>
		</div>
</div>
<div class="i_banner"></div>
<div class="i_box_content">
<c:forEach items="${riskWithModuleList}" var="riskWithModule" varStatus="status">
	<c:if test="${riskWithModule.riskList.size()>-1}">
	<div class="i_box">
		<h4><a class="ha" >${riskWithModule.module.mname} </a><span class="more"><a target="_blank" href="risk/view/getNewsByModuleId.do?parentModuleId=${riskWithModule.module.mid}&page=1&id=${id}">更多>></a></span></h4>
		<div class="box_banner"><img src="common/img/img${id}_${status.index+1}.jpg"/></div>
		<ul class="bul">
		
		<c:forEach items="${riskWithModule.riskList}" var="risk">
			<li title="${risk.title}"><img class="i_point" src="common/img/i_point.jpg"/><a target="_blank" href="risk/view/getNewsById.do?riskId=${risk.crawl_id}&categoryId=${riskWithModule.module.mid}&id=${id}&esId=${risk.esId}">${risk.title}</a></li>
		</c:forEach>
		</ul>	
	</div>
	</c:if>
</c:forEach>
</div>

<!-- footer -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>	