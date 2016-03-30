<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>购买成功</title>
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>">
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>">
</head>
<body>
<!-- header -->
	<jsp:include page="../header.jsp"></jsp:include>
<!-- header_end --> 

<!-- body -->
<div class="paySuccess">
    <dl>
        <dt><img src="word/img/pay_success.png"></dt>
        <dd>
            支付成功！已支付<span>${map.total}</span>创享币！
        </dd>
    </dl>
</div>
<div class="body_div2_b" name="区域1底部">
    <h3 class="alsoBuy">购买该文档的用户还购买了</h3>
    <ul class="lastArea">
    <c:forEach items="${map.journals}" var="journal">
        <li>
            <div> <a title="${journal.name}" href="view/preview.do?journalId=${journal.id}" target="_blank"><img src="${journal.cover}"></a><span class="icon icon1"><em>${journal.type}</em> </span>
                <p class="time" style="line-height: 24px; top: 170px;"> <span>更新<fmt:formatDate value="${journal.updateTime}"  type="date"/></span></p>
            </div>
            <h5> <a title="${journal.name}" href="view/preview.do?journalId=${journal.id}" target="_blank">
            	<c:if test="${fn:length(journal.name)<=23}">${journal.name}</c:if>
                <c:if test="${fn:length(journal.name)>23}">${fn:substring(journal.name,0,23)}...</c:if>
                </a> </h5>
        </li>
    </c:forEach>
    </ul>
    <div class="lastArea2">
        <ul>
        	<c:forEach items="${map.articles}" var="article" begin="0" end="4">	
            <li>
                <p><em class="middot"></em> <span class="ic-h ic-h-"></span> <a href="view/preview.do?articleId=${article.articleId}" target="_blank" title="${article.articleName}"> 
                <c:if test="${fn:length(article.articleName)<=23}">${article.articleName}</c:if>
                <c:if test="${fn:length(article.articleName)>23}">${fn:substring(article.articleName,0,23)}...</c:if>
                </a> </p>
                <h5><fmt:formatDate value="${article.passTime}"  type="date"/></h5>
            </li>
            </c:forEach>
        </ul>
        <ul>
        	<c:if test="${fn:length(map.articles)>4}">
        	<c:forEach items="${map.articles}" var="article" begin="5" end="9">	
            <li>
                <p><em class="middot"></em> <span class="ic-h ic-h-"></span> <a href="view/preview.do?articleId=${article.articleId}" target="_blank" title="${article.articleName}"> 
                <c:if test="${fn:length(article.articleName)<=23}">${article.articleName}</c:if>
                <c:if test="${fn:length(article.articleName)>23}">${fn:substring(article.articleName,0,23)}...</c:if>
                </a> </p>
                <h5><fmt:formatDate value="${article.passTime}"  type="date"/></h5>
            </li>
            </c:forEach>
            </c:if>
        </ul>
    </div>
</div>
<!-- body_end --> 
<!-- footer -->
<jsp:include page="../footer.jsp"></jsp:include>
<!-- footer_end -->
<div name="遮挡背景" class="ui_back2">
		<div name="遮挡背景" class="ui_back"></div>
	</div>
	<div class="box_content">
		<div class="pop_div2 exit_pop">
			<span class="pop_closeBt"></span>
			<h3>提示</h3>
			<p class='tips'>确认退出?</p>
			<div class="sb_button">
				<a onclick="logout()" class="a1">确认</a><a class="a2"
					onclick="closeDiv()">取消</a>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/alert.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script> 
</body>
</html>