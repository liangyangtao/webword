<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";

%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>知识创享网-我的资源库</title>
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>">
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>">
</head>
<body>
	<!-- header -->
	<jsp:include page="../myCenter.jsp"></jsp:include>
	<!-- header_end -->

	<!-- body -->
	<div class="userCont">
		<div class="userTopBg">
			<div class="userTop">
				<h3 class="user_name">
					<em></em>${user.userName}
				</h3>
				<img src="word/img/tou.gif" class="user_img" />
			</div>
		</div>
		<div class="clearfix userBody">
			<jsp:include page="user-left.jsp"></jsp:include>
			<div class="userRight">
				<h1>我的资源库</h1>
				<h2 class="qikanSea_h2 clearfix" style="padding:15px 0 0 20px;"><p style="margin:0;">
					<input type="text" value="${keyword}" id="search_keyword" class="search_input">
					<em class="searchBt" onclick="searchKeyword()">搜索</em>
				</p></h2>
				<div class="purch_ clearfix"><br>
				<ul class="item-content item-content5 clearfix">
								<li class="td item-title">
								<p class="p5">名称</p>
								<p class="p6">类型</p>
								<p class="p7">更新时间</p>
								<p class="p8">订阅至</p></li></ul>
				<c:forEach items="${page.list}" var="list"> 								
					<c:if test="${list.resourceType=='期刊'}">
						<ul class="item-content  item-content4 clearfix">						
							<li class="td td-item td-item3">
								<div class="td-inner">
									<div class="item-pic">
										<a title="${list.journalName}" href="view/preview.do?journalId=${list.journalId}"
											target="_blank"><img width="74" height="85"
											src="${list.cover }"> <span class="icon icon1"><em>${list.journalType}</em>
										</span></a>
									</div>
									<div class="item-info" style="width:330px;overflow:hidden">
										<a title="${list.journalName}" href="view/preview.do?journalId=${list.journalId}"
											target="_blank">
											<h3 style="height:30px;line-height:30px;overflow:hidden;"><c:if
test="${fn:length(list.journalName)>18}">${fn:substring(list.journalName,0,18)}...</c:if>
											<c:if test="${fn:length(list.journalName)<=18}">${list.journalName}</c:if></h3>
											<p>${list.journalType}</p> </a>
									</div>
								</div>
							</li>
							<li class="td td-item td-item4">
								<div class="td-inner">${list.resourceType }</div></li>
							<li class="td td-time">
								<div class="td-inner"><fmt:formatDate value="${list.updateTime}" type="date"/></div></li>
							<li class="td td-term">
								<div class="td-inner"><fmt:formatDate value="${list.endTime}" type="date"/></div></li>
						</ul>
					</c:if>
					<c:if test="${list.resourceType=='非期刊文档'}">
						<ul class="item-content item-content3 clearfix">
							
							<li class="td td-item td-item3">
								<div class="td-inner">
									<div class="item-info">
										<a href="view/preview.do?articleId=${list.articleId}" target="_blank">
											<span class="ic-h ic-h-${list.articleFormat}"></span><h3 title="${list.articleName}"><c:if
												test="${fn:length(list.articleName)>22}">${fn:substring(list.articleName,0,22)}...</c:if>
											<c:if test="${fn:length(list.articleName)<=22}">${list.articleName}</c:if>
											</h3> </a>
									</div>
								</div>
							</li>
							<li class="td td-item td-item4">
								<div class="td-inner">${list.resourceType}</div></li>
							<li class="td td-time">
								<div class="td-inner"><fmt:formatDate value="${list.updateTime}" type="date"/></div></li>
							<li class="td td-term">
								<div class="td-inner">——</div></li>
						</ul>
					</c:if>
					<c:if test="${list.resourceType=='期刊文档'}">
						<ul class="item-content  item-content4 clearfix">
							
							<li class="td td-item td-item3">
								<div class="td-inner">
									<div class="item-pic">
										<a title="${list.journalName}" href="view/preview.do?articleId=${list.articleId}"
											target="_blank"><img width="74" height="85"
											src="${list.cover }"> <span class="icon icon1"><em>${list.journalType}</em>
										</span></a>
									</div>
									<div class="item-info" style="width:330px;overflow:hidden">
										<a href="view/preview.do?articleId=${list.articleId}" target="_blank">
											<h3 title="${list.journalName}" style="height:30px;line-height:30px;overflow:hidden;"><c:if
test="${fn:length(list.journalName)>18}">${fn:substring(list.journalName,0,18)}...</c:if>
											<c:if test="${fn:length(list.journalName)<=18}">${list.journalName}</c:if></h3></a>
											<a href="view/preview.do?articleId=${list.articleId}" target="_blank"><p title="${list.articleName}"><span class="ic-h ic-h-${list.articleFormat }" style="margin:0 5px 0 0;"></span><c:if
												test="${fn:length(list.articleName)>20}">${fn:substring(list.articleName,0,20)}...</c:if>
											<c:if test="${fn:length(list.articleName)<=20}">${list.articleName}</c:if></p> </a>
									</div>
								</div>
							</li>
							<li class="td td-item td-item4">
								<div class="td-inner">${list.resourceType }</div></li>
							<li class="td td-time">
								<div class="td-inner"><fmt:formatDate value="${list.updateTime}" type="date"/></div></li>
							<li class="td td-term">
								<div class="td-inner">——</div></li>
						</ul>
						</c:if>
					</c:forEach>
				</div>
				<jsp:include page="../pager.jsp"></jsp:include>
			</div>
		</div>
	</div>

	<!-- body_end -->
	<!-- footer -->
	<jsp:include page="../footer.jsp"></jsp:include>
	<!-- footer_end -->
	<iframe src="" name="uploadIframe" id="uploadIframe"
		style="display:none"></iframe>
	<jsp:include page="user-window.jsp"></jsp:include>
	<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/ajaxfileupload.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/alert.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/onSearch.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
<script type="text/javascript">
	//分页跳转
	function goPage(pageNo, startPage) {
		var keyword=document.getElementById("search_keyword").value;
		var url = $("#path").val() + "user/resource.do?keyword="+keyword;
		if (pageNo > 1) {
			url += (url.indexOf("?") > 0 ? "&" : "?") + "pageNo=" + pageNo;
		}
		if (startPage > 1) {
			url += (url.indexOf("?") > 0 ? "&" : "?") + "startPage="
					+ startPage;
		}
		window.location = url;
	}
	function searchKeyword(){
	var keyword=document.getElementById("search_keyword").value;
	window.location = "user/resource.do?keyword="+keyword;
	}
	$("#search_keyword").keydown(function(e){
	if(e.keyCode==13){
		goPage();
	}
});
	
</script>
</html>