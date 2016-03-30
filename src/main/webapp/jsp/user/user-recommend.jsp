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
<title>知识创享网-推荐模板</title>
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
            <h3 class="user_name"><em></em>${user.userName}</h3>
            <img src="word/img/tou.gif" class="user_img" /></div>
    </div>
    <div class="clearfix userBody">
        <jsp:include page="user-left.jsp"></jsp:include>
        <div class="userRight">
            <div class="userR_ news_">
                <h1>推荐模板</h1>
                <h2>
                    <span>类型</span><span style="width:320px; padding-left:116px">标题</span><span style="width:190px;padding-left:60px"><span>状态</span>
                    </span><span>创建时间</span></h2>
                <ul class="news_ul">
	                <c:forEach items="${page.list}" var="article">
	                	<li>
		                	<input type="checkbox" name="articleIds"  value="${article.articleId}">
	                        <span class="type_ type_2"></span> 
	                        <a href="javascript:articleConfirm('${article.articleId}')"  title="${article.articleName}" class="w450">
	                        	<c:if test="${fn:length(article.articleName)>30}">${fn:substring(article.articleName,0,30)}...</c:if>
		                        <c:if test="${fn:length(article.articleName)<=30}">${article.articleName}</c:if>
	                        </a>
	                        <span class="state">
	                        	<c:if test="${article.passType=='SAVED'}">私有</c:if>
	                        	<c:if test="${article.passType=='SUBMITTED'}">审核中</c:if>
	                        	<c:if test="${article.passType=='PASSED'}">公开</c:if>
	                        	<c:if test="${article.passType=='FAILED'}">审核不通过<a href="javascript:info('${article.articleId}');">&nbsp;查看原因 </a></c:if>
	                        </span> 
	                        <span class="time">
	                        <script type="text/javascript">
	                         if("${article.updateTime}"){
	                        	var long = parseInt("${article.updateTime}");
	                        	var date = new Date(long);
	                        	document.write(date.getFullYear()+"-"+(date.getMonth()<9?("0"+(date.getMonth()+1)):(date.getMonth()+1))+"-"+(date.getDate()<10?("0"+date.getDate()):date.getDate())+" "+(date.getHours()<10?("0"+date.getHours()):date.getHours())+":"+(date.getMinutes()<10?("0"+date.getMinutes()):date.getMinutes())+":"+(date.getSeconds()<10?("0"+date.getSeconds()):date.getSeconds()));
	                         }
	                        </script>
                        </span> 
                        </li>
	                </c:forEach>
	                <c:if test="${empty page.list}"><li>暂无记录</li></c:if>
                </ul>
            </div>
            <jsp:include page="../pager.jsp"></jsp:include>
        </div>
    </div>
</div>

<!-- body_end --> 
<!-- footer -->
<jsp:include page="../footer.jsp"></jsp:include>
<!-- footer_end -->
<jsp:include page="user-window.jsp"></jsp:include>
<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/alert.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
<script type="text/javascript">
<!--
//分页跳转
function goPage(pageNo,startPage){
	var url = $("#path").val()+"user/recommend.do";
	if($("#status").val()){
		url+="?status="+$("#status").val();
	}
	if(pageNo>1){
		url+= (url.indexOf("?")>0?"&":"?")+"pageNo="+pageNo;
	}
	if(startPage>1){
		url+= (url.indexOf("?")>0?"&":"?")+"startPage="+startPage;
	}
	window.location = url;
}

function articleConfirm(articleId){
	$.ajax({
        type: "get",
        url: "word/checkArticleName.do?articleId="+articleId +"&articleName=&flag=2&documentType=2",
        async:false,
        dataType:"json",
        success: function(data) {
       		if(data.status=="success"){
				window.location = $("#path").val()+"word.do?articleId="+data.articleId;
			}else{
				new altInfo({
					text:date.info	
				});
			}
        }
    });
}
//-->
</script>
</html>