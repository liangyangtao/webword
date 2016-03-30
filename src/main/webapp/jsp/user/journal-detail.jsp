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
<title>知识创享网-我的期刊</title>
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>" />

</head>
<body>
	<!-- header -->
	<jsp:include page="../myCenter.jsp"></jsp:include>
	<!-- header_end -->
	<input type="hidden" id="journalId" value="${journalId}">
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
				<div class="qikanCont clearfix">
					<div class="qikanPic">
						<div>
							<img src="${journal.cover}"><span class="icon icon1"><em>${journal.type}</em></span>
							<p>
								<span>更新：<fmt:formatDate value="${journal.submitTime}" pattern="yyyy-MM-dd"/></span>
							</p>
						</div>
						
					</div>
					<div class="qikanDetail">
						<h2 class="clearfix">
							<p class="title">期刊详情</p>
							<p class="right">
								<span class="modify cur" onClick="qikanDetail_edit();">修改</span><span
									class="cancel" onClick="qikanDetail_exitEdit();">取消</span><span
									class="submit cur" onClick="qikanDetail_exitEdit2();">提交审核</span>
							</p>
						</h2>
						<ul class="detailUl clearfix" id="old_Detail">
							<li><span class="span1">期刊名称:</span>
								<p id="old_Detail_name">${journal.name}</p> </li>
							<li><span class="span1" style="vertical-align:top;">期刊简介:</span>
								<div class="brief">
									<p id="old_Detail_skip">${journal.skip}
									</p>
									<span class="btn">详情</span>
								</div>
								
							</li>
							<li><span class="span1">出刊频率:</span>
								<p>${journal.type}</p> 
							</li>
							<li><span class="span1">期刊标签:</span>
								<p class="sign" id="old_Detail_label">
									${journal.label}
								</p> </li>
							<li><span class="span1">期刊关键词:</span>
								<p id="old_Detail_keyword">${journal.keyword}</p> </li>
							<li><span class="span1">期刊价格:</span>
								<p id="old_Detail_price">${journal.price}创享币/年</p> </li>	
						</ul>
						<form id="journalDetail">
						<input type="hidden" name="id" value="${journal.id}">
						<ul class="detailUl clearfix" id="modi_Detail">
							<li><span class="span1">期刊名称:</span>
								<input class="det_inp" id="modi_name"
								type="text" value="${journal.name}" name="name"></li>
							<li><span class="span1" style="vertical-align:top;">期刊简介:</span>
								 <textarea class="brief" id="modi_Brief" name="skip">${journal.skip}
								</textarea>
							</li>
							<li><span class="span1">出刊频率:</span>
								 <p>${journal.type}</p>
							</li>
							<li><span class="span1" style="vertical-align:top;">期刊标签:</span>
								<textarea class="sign" id="modi_label" name="label">${journal.label}
								</textarea></li>
							<li><span class="span1">期刊关键词:</span>
								<input class="det_inp" type="text" id="modi_keyword" name="keyword" value="${journal.keyword}"></li>
							<li><span class="span1">期刊价格:</span>
								<input class="det_inp" type="text" id="modi_price" name="price" value="${journal.price}">创享币/年</li>
						</ul>
						</form>
					</div>
				</div>
				<div class="qikanSearch clearfix">
					<h2 class="qikanSea_h2 clearfix">
						文档列表
						<p>
							<input type="text" value="" class="search_input"  id="search_keyword">
							<em class="searchBt" onClick="goPage();">搜索</em>
						</p>
						<span>返回</span>
					</h2>
					<div class="userR_ news_">
						<h2>
							<span>类型</span><span style="width:345px; padding-left:116px">标题</span>
							<span style="width:110px;">价格(创享币)</span>
							<span style="width:95px;text-align:center;">作者</span>
							<span style="width:154px;text-align:center;">创建时间</span>
						</h2>
						<ul class="news_ul">
							<c:forEach items="${page.list}" var="article">
								<li><span class="ic-h-${article.articleFormat}"></span> 
								<a target="_blank" class="w450" title="${article.articleName}" href="view/preview.do?articleId=${article.articleId}">
								<c:if test="${fn:length(article.articleName)>30}">${fn:substring(article.articleName,0,30)}...</c:if>
								<c:if test="${fn:length(article.articleName)<=30}">${article.articleName}</c:if></a>
								<span class="price" style="width:100px">${article.articlePrice}</span>
								<span class="state" style="width:163px">${article.articleUser}</span> 
								<span class="time">
								<script type="text/javascript">
                         if("${article.passTime}"){
                        	var long = parseInt("${article.passTime}");
                        	var date = new Date(long);
                        	document.write(date.getFullYear()+"-"+(date.getMonth()<9?("0"+(date.getMonth()+1)):(date.getMonth()+1))+"-"+(date.getDate()<10?("0"+date.getDate()):date.getDate())+" "+(date.getHours()<10?("0"+date.getHours()):date.getHours())+":"+(date.getMinutes()<10?("0"+date.getMinutes()):date.getMinutes())+":"+(date.getSeconds()<10?("0"+date.getSeconds()):date.getSeconds()));
                         }
                        </script></span></li>
							</c:forEach>
							<c:if test="${page.count<=0}"><p style="padding:15px 0;text-align:center;">暂无记录</p></c:if>
						</ul>
					</div>
					<jsp:include page="../pager.jsp"></jsp:include>
				</div>
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
$("#search_keyword").keydown(function(e){
	if(e.keyCode==13){
		goPage();
	}
});
	//分页跳转
	function goPage(pageNo,startPage){
		pageNo = pageNo||1;
		startPage = startPage ||1;
		$.ajax({
	        type: "post",
	        url: "user/journal/searchByJournal.do",
	        data:{keyword:$.trim($("#search_keyword").val()),pageNo:pageNo,startPage:startPage,journalId:$("#journalId").val()},
	        async:false,
	        dataType:"json",
	        success: function(data) {
	        	$(".page_p").remove();
				$(".news_ul").empty();
	        	if(data && data.count>0){
	        		for(var i=0;i<data.list.length;i++){
	        			var article = data.list[i];
	        			var date = new Date(article.passTime);
	        			var name = article.articleName;
	        			if(name && name.length>30){
	        				name = name.substring(0,30)+"...";
	        			}
	        			$(".news_ul").append('<li><span class="ic-h-'+article.articleFormat+'"></span> <a class="w450" title="'+article.articleName+'" href="view/preview.do?articleId='+article.articleId+'">'+name+' </a><span class="price" style="width:100px">'+article.articlePrice+'</span><span class="state" style="width:163px">'+article.articleUser+' </span> <span class="time">'+(date.getFullYear()+"-"+(date.getMonth()<9?("0"+(date.getMonth()+1)):(date.getMonth()+1))+"-"+(date.getDate()<10?("0"+date.getDate()):date.getDate())+" "+(date.getHours()<10?("0"+date.getHours()):date.getHours())+":"+(date.getMinutes()<10?("0"+date.getMinutes()):date.getMinutes())+":"+(date.getSeconds()<10?("0"+date.getSeconds()):date.getSeconds()))+'</span></li>');
	        		}
	        		if(data.pageCount>1){
	        			var page = '<div class="page_p"><p>';
		        		if(data.pageNo<=1){
		        			page+='<a>首页</a><a>&lt;上一页</a>';
		        		}
		        		if(data.pageNo>1){
		        			page+='<a href="javascript:goPage(1);">首页</a><a href="javascript:goPage('+(data.pageNo-1)+','+(data.startPage-1)+');">&lt;上一页</a>';
		        		}
		        		for(var c =data.startPage;c<= data.endPage;c++){
		        			if(c==data.pageNo){
		        				page+='<a href="javascript:goPage('+c+','+data.startPage+');" class="onPage">'+c+'</a>';
		        			}else{
		        				page+='<a href="javascript:goPage('+c+','+data.startPage+');">'+c+'</a>';
		        			}
		        		}
		        		if(data.pageNo<data.pageCount){
		        			if(data.pageNo==data.endPage){
		        				page+='<a  href="javascript:goPage('+(data.pageNo+1)+','+(data.startPage+1)+');">下一页&gt;</a>';
		        			}else{
		        				page+='<a  href="javascript:goPage('+(data.pageNo+1)+');">下一页&gt;</a>';
		        			}
		        		}
		        		if(data.pageNo>=data.pageCount){
		        			page+='<a >下一页&gt;</a><a>尾页</a>';
		        		}
		        		page+='</p></div>';
	        			$(".qikanSearch").append(page);
	        		}
	        	}else{
	        		$(".news_ul").html('<p style="padding:15px 0;text-align:center;">暂无记录</p>');
	        	}
	        }
	    });
	}
	function qikanDetail_exitEdit2(){
		if ($("#modi_name").val().length <= 0 || $("#modi_name").val() == null) {
	        new altInfo({text:"请填写期刊名称"});
	        return;
	    }
	    if ($.trim($("#modi_name").val()).length >40) {
	        new altInfo({text:"期刊名称不能超过40字符"});
	        return;
	    }
	    if ($("#modi_Brief").val().length <= 0 || $("#modi_Brief").val() == null) {
	        new altInfo({text:"请填写期刊简介"});
	        return;
	    }
	    if ($("#modi_Brief").val().length >1000) {
	        new altInfo({text:"期刊简介不能大于1000个字符"});
	        return;
	    }
	    var bq = $.trim($("#modi_label").val());
	    if (bq.length <= 0 || bq == null) {
	        new altInfo({text:"请填写期刊标签"});
	        return;
	    }
	    var tems = bq.split(" ");
	    var bqs = [];
	    for(var i = 0;i<tems.length;i++){
	    	if($.trim(tems[i]).length>=0 && $.trim(tems[i])!=""){
		    	bqs.push($.trim(tems[i]));
		    }
	    }
	    for(var i = 0;i<bqs.length;i++){
	    	if($.trim(bqs[i]).length>40){
	    		new altInfo({text:"每个标签最多为40个字"});
	    		return;
	    		break;
	    	}
	    	for(var j = 0;j<bqs.length;j++){
	    		if($.trim(bqs[i])==$.trim(bqs[j])  && i!=j){
	    			new altInfo({text:"标签重复"});
	    			return;
	    			break;
	    		}
	    	}
	    }
	    var kw = $.trim($("#modi_keyword").val());
	    if (kw.length <= 0 || kw == null) {
	        new altInfo({text:"请填写期刊关键词"});
	        return;
	    }
	    var tems = kw.split(" ");
	    var kws = [];
	    for(var i = 0;i<tems.length;i++){
	    	if($.trim(tems[i]).length>=0 && $.trim(tems[i])!=""){
		    	kws.push($.trim(tems[i]));
		    }
	    }
	    for(var i = 0;i<kws.length;i++){
	    	if($.trim(kws[i]).length>8){
	    		new altInfo({text:"每个关键词最多为8个字"});
	    		return;
	    		break;
	    	}
	    	for(var j = 0;j<bqs.length;j++){
	    		if($.trim(bqs[i])==$.trim(bqs[j]) && i!=j){
	    			new altInfo({text:"关键词重复"});
	    			return;
	    			break;
	    		}
	    	}
	    }
	    if (kws.length>15) {
	        new altInfo({text:"最多15个关键词"});
	        return;
	    }
	    if($.trim($("#modi_price").val()).length <=0 || $.trim($("#modi_price").val())==null) {
	        new altInfo({text:"请填写期刊价格"});
	        return;
	    }
	    if(isNaN($.trim($("#modi_price").val()))) {
	        new altInfo({text:"价格格式错误"});
	        return;
	    }
		$.ajax({
	        type: "post",
	        url: "user/journal/edit.do",
	        data:$("#journalDetail").serialize(),
	        async:false,
	        dataType:"json",
	        success: function(data) {
	        	if(data && data.result==1){
	        		qikanDetail_exitEdit();
	        		$("#old_Detail_name").text($("#modi_name").val());
	        		$("#old_Detail_skip").text($("#modi_Brief").val());
	        		$("#old_Detail_label").text($("#modi_label").val());
	        		$("#old_Detail_keyword").text($("#modi_keyword").val());
	        		$("#old_Detail_price").text($("#modi_price").val());
	        		new altInfo({
						text:"提交成功！"	
					});
	        	}else{
	        		new altInfo({
						text:"保存失败！"	
					});
	        	}
	        }
	    });
	}
//-->
</script>
</html>
