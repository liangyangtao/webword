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
            <h3 class="user_name"><em></em>${user.userName}</h3>
            <img src="word/img/tou.gif" class="user_img" /></div>
    </div>
    <div class="clearfix userBody">
        <jsp:include page="user-left.jsp"></jsp:include>
        <div class="userRight">
            <h1>我的收藏<span class="dele"  onClick="delpre();">删除</span></h1>
            <div class="userR_ news_">
                <h2>
                    <input type="checkbox" class="allCheck">
                    <span>类型</span>
                    <span style="width:588px; padding-left:116px">标题</span> 
                    <span>收藏时间</span></h2>
                <ul class="news_ul">
                	<c:forEach items="${page.list}" var="content">
                    	<li>
	                        <input type="checkbox"  name="contentIds" value="${content.contentId}" >
	                        <span class="type_ type_5"></span> 
	                        <a target="_blank" href="user/news.do?newsId=${content.contentId}" class="w680" title="${content.contentName}">
	                        	<c:if test="${fn:length(content.contentName)>30}">${fn:substring(content.contentName,0,30)}...</c:if>
		                        <c:if test="${fn:length(content.contentName)<=30}">${content.contentName}</c:if>
	                       	</a>
	                        
	                        <span class="time"><fmt:formatDate value="${content.updateTime}" pattern="yyyy-MM-dd HH:mm"/></span> 
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
	var url = $("#path").val()+"user/content.do";
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
	if($("input[type=checkbox][name=contentIds]:checked").size()==0){
		new altInfo({
			text:"请选中要删除的文件！"	
		});
	}else{
		//showDiv(4);
		show_user_dele();//20160122
	}
}
//删除新闻
function del(){
	var contentIds = [];
 	$.each($("input[type=checkbox][name=contentIds]:checked"),function(){
 		contentIds.push($(this).val());
 	});
 	$.ajax({
        type: "post",
        url: "user/delContent.do",
        data:{contentIds:contentIds.join("_")},
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data && data.result==0){
        		new altInfo({
					text:data.msg
				});
        	}else{
        		window.location = $("#path").val()+"user/content.do"+($("#status").val()?("?status="+$("#status").val()):"");
        	}
        }
    });
}
//-->
</script>
</html>
