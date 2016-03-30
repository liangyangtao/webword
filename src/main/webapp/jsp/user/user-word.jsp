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
<title>知识创享网-我的文库</title>
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
            <h1>我的文档<span class="dele"  onClick="delpre();">删除</span></h1>
            <div class="userR_ news_">
                <h2>
                    <input type="checkbox" class="allCheck">
                    <span>类型</span><span style="width:245px; padding-left:116px">标题</span>
                    <span style="width:145px;">
	                    <span>状态</span>
	                    <select id="status" onchange="goPage(1);" style="display:inline-block">
	                        <option <c:if test="${page.status==null or page.status==''}">selected</c:if> value="">全部</option>
	                        <option <c:if test="${page.status!=null and page.status=='SAVED'}">selected</c:if> value="SAVED">私有</option>
	                        <option <c:if test="${page.status!=null and page.status=='SUBMITTED'}">selected</c:if> value="SUBMITTED">审核中</option>
	                        <option <c:if test="${page.status!=null and page.status=='PASSED'}">selected</c:if> value="PASSED">公开</option>
	                        <option <c:if test="${page.status!=null and page.status=='FAILED'}">selected</c:if> value="FAILED">审核不通过</option>
	                    </select>
                    </span>
                    <span>
                    	<span>是否为期刊</span>
                    	<!-- <select id="source">
	                        <option>全部</option>
	                        <option>新建</option>
	                        <option>收藏</option>                        
                   		</select> -->
                    </span>
                    <span style="padding-left:70px">创建时间</span></h2>
                <ul class="news_ul">
	                <c:forEach items="${page.list}" var="article">
	                	<li>
		                	<input type="checkbox" name="articleIds" value="${article.articleId}">
	                        <span class="type_ type_3"></span> 
	                        <a target="_blank" <c:if test="${article.passType=='PASSED'}">href="view/preview.do?articleId=${article.articleId}"</c:if><c:if test="${article.passType!='PASSED'}">href="word.do?articleId=${article.articleId}" </c:if> title="${article.articleName}" class="w380">
	                        	<c:if test="${fn:length(article.articleName)>24}">${fn:substring(article.articleName,0,24)}...</c:if>
		                        <c:if test="${fn:length(article.articleName)<=24}">${article.articleName}</c:if>
	                        </a>
	                        <span class="state">
	                        	<c:if test="${article.passType=='SAVED'}">私有</c:if>
	                        	<c:if test="${article.passType=='SUBMITTED'}">审核中</c:if>
	                        	<c:if test="${article.passType=='PASSED'}">公开</c:if>
	                        	<c:if test="${article.passType=='FAILED'}">审核不通过<a href="javascript:info('${article.articleId}')">&nbsp;查看原因 </a></c:if>
	                        </span>
	                        <span class="source"><c:if test="${empty article.articleJournalId or article.articleJournalId==0}">非期刊文档</c:if><c:if test="${article.articleJournalId>0}">期刊文档</c:if></span> 
	                        <span class="time"><fmt:formatDate value="${article.updateTime}" pattern="yyyy-MM-dd HH:mm"/></span> 
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
	var url = $("#path").val()+"user/article.do";
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
function delpre(){
	if($("input[type=checkbox][name=articleIds]:checked").size()==0){
		new altInfo({
			text:"请选中要删除的文件！"	
		});
	}else{
		//showDiv(4);
		show_user_dele();//20160122
	}
}
//删除文档
function del(){
	var articleIds = [];
 	$.each($("input[type=checkbox][name=articleIds]:checked"),function(){
 		articleIds.push($(this).val());
 	});
 	$.ajax({
        type: "post",
        url: "user/delArticle.do",
        data:{articleIds:articleIds.join("_")},
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data && data.result==0){
        		new altInfo({
					text:data.msg
				});
        	}else{
        		window.location = $("#path").val()+"user/article.do"+($("#status").val()?("?status="+$("#status").val()):"");
        	}
        }
    });
}
//拒绝原因
function info(articleId){
	$.ajax({
        type: "post",
        url: "view/reason.do",
        data:{articleId:articleId,type:"document"},
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data){
        		$(".no_reason .sb_middle").html(data.reason);
				//showDiv(3);
				show_no_reason();//20160122
        	}else{
	        	new altInfo({
					text:"系统错误！"	
				});
        	}
        }
    });
}
//-->
</script>
</html>
