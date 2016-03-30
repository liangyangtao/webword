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
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>知识创享网-我的收藏</title>
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>" />

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
				<h1>
					我的收藏<span class="dele" onClick="delpre();">删除</span>
				</h1>
				<div class="userR_ news_">
					<h2>
						<input type="checkbox" class="allCheck"> <span>类型</span><span
							style="width:588px; padding-left:116px">标题</span> <span>收藏时间</span>
					</h2>
					<ul class="news_ul">
						<c:forEach items="${page.list}" var="content">
							<li><input type="checkbox" name="contentIds"
								value="${content.docId}"> <span class="type_ type_5"></span>
								<a target="_blank"
								<c:if test="${content.doType=='news'}">href="news/view/showNewsDetails.do?newsId=${content.docId}"</c:if>
								<c:if test="${content.doType!='news'}">href="word.do?id=${content.id}" </c:if>
								title="${content.newsTitle}" class="w680"> <c:if
										test="${fn:length(content.newsTitle)>24}">${fn:substring(content.newsTitle,0,24)}...</c:if>
									<c:if test="${fn:length(content.newsTitle)<=24}">${content.newsTitle}</c:if>
							</a> <span class="time"><fmt:formatDate
										value="${content.favTime}" pattern="yyyy-MM-dd HH:mm" /> </span>
							</li>
						</c:forEach>
						<c:if test="${empty page.list}">
							<li>暂无记录</li>
						</c:if>
					</ul>
				</div>
				<jsp:include page="../pager.jsp"></jsp:include>
			</div>
		</div>
	</div>


	<!-- footer -->
	<jsp:include page="../footer.jsp"></jsp:include>
	<jsp:include page="user-window.jsp"></jsp:include>

	<%-- <script src="<%=basePath%>word/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>word/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="<%=basePath%>word/js/login.js"></script>
	<script type="text/javascript" src="<%=basePath%>jsp/news/js/news.js"></script>
	<script type="text/javascript" src="word/js/alert.js"></script> --%>
	
	<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/alert.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
<!-- body_end -->

<script type="text/javascript">
	//分页跳转
	function goPage(pageNo, startPage) {
		var url = $("#path").val() + "user/myfavorite.do";

		if (pageNo > 1) {
			url += (url.indexOf("?") > 0 ? "&" : "?") + "pageNo=" + pageNo;
		}
		if (startPage > 1) {
			url += (url.indexOf("?") > 0 ? "&" : "?") + "startPage="
					+ startPage;
		}
		window.location = url;
	}
	function delpre() {
		if ($("input[type=checkbox][name=contentIds]:checked").size() == 0) {
			new altInfo({
				text : "请选中要删除的新闻！"
			});
		} else {
			show_user_dele();
		}
	}
	//删除新闻
	function del() {

		var contIds = [];
		$.each($("input[type=checkbox][name=contentIds]:checked"), function() {
			contIds.push($(this).val());
		});

		$.ajax({
			type : "post",
			url : "user/delAllContent.do",
			data : {
				contentIds : contIds.join("_")
			},
			async : false,
			dataType : "json",
			success : function(data) {
				if (data && data.result == 0) {
					new altInfo({
						text : data.msg
					});
				} else {
					window.location = $("#path").val() + "user/myfavorite.do";
				}
			}
		});
	}
</script>
</html>
