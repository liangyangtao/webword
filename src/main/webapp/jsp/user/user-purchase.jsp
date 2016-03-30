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
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>知识创享网-购买记录</title>
<script type="text/javascript" src="word/js/laydate/laydate.js?v=<%=System.currentTimeMillis()%>"></script>
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
             <h3 class="user_name"><em></em>${user.userName}</h3>
            <img src="word/img/tou.gif" class="user_img" /></div>
    </div>
    <div class="clearfix userBody">
       <jsp:include page="user-left.jsp"></jsp:include>
        <div class="userRight">
            <h1>购买记录</h1>
            <h2 class="purch_search"><span>记录时间</span>
                <input id="startdate" type="text" value="${startdate}" readonly="readonly"  class="time" onclick="laydate;this.placeholder=''" /> 
                <span>至</span>
                <input id="enddate" type="text" value="${enddate}" readonly="readonly" class="time" onclick="laydate;this.placeholder=''">
                <a class="searchBt" onclick="timesearch()">搜　索</a></h2>
            <div class="purch_ clearfix">
             <c:forEach items="${page.list}" var="list"> 
            	<c:if test="${list.resourceType=='期刊'}">
	                <ul class="item-content clearfix">
	                    <li class="td item-title">
	                        <p class="p1"><fmt:formatDate value="${list.createTime}" type="date" pattern="yyyy/MM/dd HH:mm:ss"/></p>
	                        <p class="p2">类　型</p>
	                        <p class="p3">购买方式</p>
	                        <p class="p4">价　格</p>
	                    </li>
	                    <li class="td td-item td-item2">
	                        <div class="td-inner">
	                            <div class="item-pic"><a target="_blank" href="view/preview.do?journalId=${list.journalId}" title="${list.journalName}"><img src="${list.cover }" width="74" height="85"><span class="icon icon1"><em>${list.type }</em> </span></a></div>
	                            <div class="item-info"> <a target="_blank" href="view/preview.do?journalId=${list.journalId }"> 
	                                <h3  title="${list.journalName}"><c:if
test="${fn:length(list.journalName)>14}">${fn:substring(list.journalName,0,14)}...</c:if>
											<c:if test="${fn:length(list.journalName)<=14}">${list.journalName}</c:if></h3>
	                                 <p>${list.journalType}</p> 
	                                </a> </div>
	                        </div>
	                    </li>
	                    <li class="td td-amount">
	                        <div class="td-inner"><em class="price-original">期刊</em></div>
	                    </li>
	                    <li class="td td-pat">
	                        <div class="td-inner"><a href="javascript:;">${list.buyType}</a></div>
	                    </li>
	                    <li class="td td-price td-price2">
	                        <div class="td-inner"><em class="price-original">${list.price}创享币</em></div>
	                    </li>
	                </ul>
                </c:if>
                <c:if test="${list.resourceType=='期刊文档'}">
	                <ul class="item-content clearfix">            	
	                    <li class="td item-title">
	                        <p class="p1"><fmt:formatDate value="${list.createTime}" type="date" pattern="yyyy/MM/dd HH:mm:ss"/></p>
	                        <p class="p2">类　型</p>
	                        <p class="p3">购买方式</p>
	                        <p class="p4">价　格</p>
	                    </li>
	                    <li class="td td-item td-item2">
	                        <div class="td-inner">
	                            <div class="item-pic"><a target="_blank" href="view/preview.do?articleId=${list.articleId }" title="${list.journalName}"><img src="${list.cover }" width="74" height="85"><span class="icon icon1"><em>${list.type }</em> </span></a></div>
	                            <div class="item-info"> <a target="_blank" href="view/preview.do?articleId=${list.articleId }">
	                                <h3 title="${list.journalName}"><c:if
test="${fn:length(list.journalName)>14}">${fn:substring(list.journalName,0,14)}...</c:if>
											<c:if test="${fn:length(list.journalName)<=14}">${list.journalName}</c:if></h3></a>
	                                <a target="_blank" href="view/preview.do?articleId=${list.articleId }"><p title="${list.articleName}"><c:if
												test="${fn:length(list.articleName)>16}"><span class="ic-h ic-h-${list.articleFormat }" style="margin:0 5px 0 0;"></span>${fn:substring(list.articleName,0,16)}...</c:if>
											<c:if test="${fn:length(list.articleName)<=16}"><span class="ic-h ic-h-${list.articleFormat }" style="margin:0 5px 0 0;"></span>${list.articleName}</c:if></p></a>
	                                 </div>
	                        </div>
	                    </li>
	                    <li class="td td-amount">
	                        <div class="td-inner"><em class="price-original">期刊文档</em></div>
	                    </li>
	                    <li class="td td-pat">
	                        <div class="td-inner"><a href="javascript:;">${list.buyType}</a></div>
	                    </li>
	                    <li class="td td-price td-price2">
	                        <div class="td-inner"><em class="price-original">${list.price}创享币</em></div>
	                    </li>
	                </ul>
                </c:if>
                <c:if test="${list.resourceType=='非期刊文档'}">
	                <ul class="item-content item-content2 clearfix">
	                    <li class="td item-title">
	                        <p class="p1"><fmt:formatDate value="${list.createTime}" type="date" pattern="yyyy/MM/dd HH:mm:ss"/></p>
	                        <p class="p2">类　型</p>
	                        <p class="p3">购买方式</p>
	                        <p class="p4">价　格</p>
	                    </li>
	                    <li class="td td-item td-item2">
	                        <div class="td-inner">                            
	                            <div class="item-info"> <a target="_blank" href="view/preview.do?articleId=${list.articleId }">
	                                <h3 title="${list.articleName}"><span class="ic-h ic-h-${list.articleFormat }"></span><c:if
												test="${fn:length(list.articleName)>14}">${fn:substring(list.articleName,0,14)}...</c:if>
											<c:if test="${fn:length(list.articleName)<=14}">${list.articleName}</c:if></h3>                                
	                                </a></div>
	                        </div>
	                    </li>
	                    <li class="td td-amount">
	                        <div class="td-inner"><em class="price-original">非期刊文档</em></div>
	                    </li>
	                    <li class="td td-pat">
	                        <div class="td-inner"><a href="javascript:;">${list.buyType}</a></div>
	                    </li>
	                    <li class="td td-price td-price2">
	                        <div class="td-inner"><em class="price-original">${list.price}创享币</em></div>
	                    </li>
	                </ul>
                </c:if>
			</c:forEach>
                <div class="pur_total_num">共<em>${page.count}</em>笔</div>
                <jsp:include page="../pager.jsp"></jsp:include>
            </div>
        </div>
    </div>
</div>

<!-- body_end --> 
<!-- footer -->
<jsp:include page="../footer.jsp"></jsp:include>
<!-- footer_end -->
<iframe src="" name="uploadIframe" id="uploadIframe" style="display:none"></iframe>
<div name="遮挡背景" class="ui_back2">
    <div name="遮挡背景" class="ui_back"></div>
</div>
<div class="box_content">
    <div class="pop_div pop_div2 exit_pop"> <span class="pop_closeBt" onclick="closeDiv();"></span>
        <p>提示</p>
        <h3>确认退出?</h3>
        <div class="sb_button"> <a onclick="logout();" class="a1"></a><a class="a2" onclick="closeDiv();"></a> </div>
    </div>
</div>
<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script> 
<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script> 

</body>
<script type="text/javascript">
	//分页跳转
	function goPage(pageNo, startPage) {
		var startdate=document.getElementById("startdate").value;
		var enddate=document.getElementById("enddate").value;
		var url = $("#path").val() + "user/purchase.do?startdate="+startdate+"&enddate="+enddate;	
		if (pageNo > 1) {
			url += (url.indexOf("?") > 0 ? "&" : "?") + "pageNo=" + pageNo;
		}
		if (startPage > 1) {
			url += (url.indexOf("?") > 0 ? "&" : "?") + "startPage="
					+ startPage;
		}
		window.location = url;
	}
	function timesearch(){
	var startdate=document.getElementById("startdate").value;
	var enddate=document.getElementById("enddate").value;
	//	var url = $("#path").val();
		window.location = "user/purchase.do?startdate="+startdate+"&enddate="+enddate;
	}
	var start = {
	    elem: '#startdate',
	    istoday: false,
	    choose: function(datas){
	         end.min = datas; //开始日选好后，重置结束日的最小日期
	         end.start = datas //将结束日的初始值设定为开始日
	    }
	};
	var end = {
	    elem: '#enddate',
	    istoday: false,
	    choose: function(datas){
	        start.max = datas; //结束日选好后，重置开始日的最大日期
	    }
	};
	laydate(start);
	laydate(end);
		
	
</script>
</html>