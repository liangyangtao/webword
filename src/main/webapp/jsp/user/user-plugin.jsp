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
<title>知识创享网-我的插件</title>
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
            <h1>我的插件<span class="dele"  onClick="delpre();">删除</span></h1>
            <div class="userR_ news_">
                <h2>
                    <input type="checkbox" class="allCheck">
                    <span>类型</span><span style="width:590px; padding-left:116px">标题</span><span>创建时间</span></h2>
                <ul class="news_ul">
               	 	<c:forEach items="${page.list}" var="plugin">
                    	<li>
	                        <input type="checkbox" name="pluginUserIds" value="${plugin.pluginUserId}">
	                        <span class="type_ type_1"></span> 
	                        <a href="javascript:void(0);" title="${plugin.myPluginName}" class="w680" onclick="showPlugDiv('${plugin.pluginId}','${plugin.myPluginName}');return false;">
	                        	<c:if test="${fn:length(plugin.myPluginName)>30}">${fn:substring(plugin.myPluginName,0,30)}...</c:if>
		                        <c:if test="${fn:length(plugin.myPluginName)<=30}">${plugin.myPluginName}</c:if>
	                        </a> 
	                        <span class="time"><fmt:formatDate value="${plugin.insertTime}" pattern="yyyy-MM-dd HH:mm"/></span> 
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
	var url = $("#path").val()+"user/plugin.do";
	if(pageNo>1){
		url+= "?pageNo="+pageNo;
	}
	if(startPage>1){
		url+= (url.indexOf("?")>0?"&":"?")+"startPage="+startPage;
	}
	window.location = url;
}
function delpre(){
	if($("input[type=checkbox][name=pluginUserIds]:checked").size()==0){
		new altInfo({
			text:"请选中要删除的文件！"	
		});
	}else{
		//showDiv(4);
		show_user_dele();//20160122
	}
}
//删除插件
function del(){
	var pluginUserIds = [];
 	$.each($("input[type=checkbox][name=pluginUserIds]:checked"),function(){
 		pluginUserIds.push($(this).val());
 	});
 	$.ajax({
        type: "post",
        url: "user/delPlugin.do",
        data:{pluginUserIds:pluginUserIds.join("_")},
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data && data.result==0){
        		new altInfo({
					text:data.msg
				});
        	}else{
        		window.location = $("#path").val()+"user/plugin.do";
        	}
        }
    });
}
//-->
</script>
</html>
