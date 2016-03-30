<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>doc/article${articleId}/html5/">
<meta charset="utf-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<style type="text/css">
.more-btn-banner{background:#fff;border:1px solid #ccc;cursor:pointer;height:58px;margin:0 auto;overflow:hidden;text-align:center;width:725px}
.more-btn-banner .banner-more-btn{width:247px;padding-top:12px;font-size:16px;line-height:16px;margin:5px auto 0;color:#b6b6b6}
.more-btn-banner .goBtn{cursor:pointer}.more-btn-banner .fc2e{color:#2eae84}
.more-btn-banner .down-arrow{width:15px;height:12px;margin:10px auto 0;background:url("<%=basePath%>word/img/pre_load.png") no-repeat;background-position:0 0px;}
.more-btn-banner:hover{
 	background: -webkit-gradient(linear, 0 0, 0 100%, from(#f8fbf9), to(#f4fef6));   
    background: -webkit-linear-gradient(top, #f8fbf9, #f4fef6);   
    background: -moz-linear-gradient(top, #f8fbf9, #f4fef6);   
    background: -o-linear-gradient(top, #f8fbf9, #f4fef6);   
    background: -ms-linear-gradient(top, #f8fbf9, #f4fef6);   
    background: linear-gradient(top, #f8fbf9, #f4fef6);   
    filter: progid:DXImageTransform.Microsoft.gradient(GradientType = 0, startColorstr = #f8fbf9, endColorstr = #f4fef6);   
}
.more-btn-banner:hover .banner-more-btn span{color:#2eae84;text-decoration:underline;}
.more-btn-banner:hover .down-arrow{background-position:0 -20px;}

.last_load{ text-align:center; background:#f5fdf7; border:1px solid #c2e1d7;height:30px;line-height:30px; margin-top:10px;}
.last_load .banner-more-btn{width:auto; display:inline-block;margin:0;padding:0;font-size:14px;}
.last_load .banner-more-btn span{ color:#2eae84; }
.last_load .down-arrow{ display:inline-block; width:15px; height:10px;background:url("<%=basePath%>word/img/pre_load.png") no-repeat; background-position:0 -40px; }
.last_load:hover{  
	background: -webkit-gradient(linear, 0 0, 0 100%, from(#f4fdf6), to(#e7fdeb));   
    background: -webkit-linear-gradient(top, #f4fdf6, #e7fdeb);   
    background: -moz-linear-gradient(top, #f4fdf6, #e7fdeb);   
    background: -o-linear-gradient(top, #f4fdf6, #e7fdeb);   
    background: -ms-linear-gradient(top, #f4fdf6, #e7fdeb);   
    background: linear-gradient(top, #f4fdf6, #e7fdeb);   
    filter: progid:DXImageTransform.Microsoft.gradient(GradientType = 0, startColorstr = #f4fdf6, endColorstr = #e7fdeb);   
}
.last_load:hover .banner-more-btn span{text-decoration:none;}
.last_load:hover .down-arrow{background-position:0 -40px;}
</style>
${styles}
<title></title>
</head>
<body>
<div id="sidebar">
<div id="outline">
</div>
</div>
<div id="page-container" >
${contents}
</div>
</body>
</html>
