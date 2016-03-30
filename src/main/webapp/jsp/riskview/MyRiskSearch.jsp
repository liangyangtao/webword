<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
  	<meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>	
	<title>搜索结果</title>
	<link rel="stylesheet" type="text/css" href="common/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="common/css/ser_result.css"/>	
	<script type="text/javascript" src="common/js/jquery-1.8.3.min.js"></script>	
	<script type="text/javascript" src="common/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="common/js/common.js"></script>
	<script type="text/javascript" src="common/js/riskmy.js"></script>
</head>
<body>
<script type="text/javascript">
//分页跳转
function goPage(pageNo,startPage){
	var url = $("#path").val()+"risk/view/searchallbyword.do?keyword=${keyword}";
	if(pageNo>1){
		url+= (url.indexOf("?")>0?"&":"?")+"curNumber="+pageNo;
	}
	if(startPage>1){
		url+= (url.indexOf("?")>0?"&":"?")+"startPage="+startPage;
	}
	window.location = url;
}

function goPageKeyword(pageNo,startPage,ckeyword){
var url = $("#path").val()+"risk/view/searchallbyword.do?keyword="+ckeyword+"";
	if(pageNo>1){
		url+= (url.indexOf("?")>0?"&":"?")+"pageNo="+pageNo;
	}
	if(startPage>1){
		url+= (url.indexOf("?")>0?"&":"?")+"startPage="+startPage;
	}
	window.location = url;

}
</script>
<!-- header -->
<jsp:include page="header.jsp"></jsp:include>
<input type="hidden" id="path" value="<%=basePath%>">
<!-- body -->
<div class="content">
	<!--  -->
	<div class="right">
		<h4 class="title">共搜索到<span class="color">${list.count}</span>条相关信息</h4>
		<div class="list">	
	
		<c:forEach items="${list.data}" var="doc">
		<div class="list1">
		<h5 title="${fn:replace(fn:replace(doc.title,"<span style='color:red;'>",""),"</span>","")}"><img class='point' src='common/img/point.png'/><a class='title'  target='_blank' href="risk/view/getNewsById.do?riskId=${doc.crawl_id}&categoryId=${fn:split(doc.categoryId,' ')[0]}&esId=${doc.esId}&id=0">${doc.title}</a><span class='time'>
		
		    <script type="text/javascript">
                         if("${doc.newsDate}"){
                        	var long = parseInt("${doc.newsDate}");
                        	var date = new Date(long);
                        	document.write(date.getFullYear()+"-"+(date.getMonth()<9?("0"+(date.getMonth()+1)):(date.getMonth()+1))+"-"+(date.getDate()<10?("0"+date.getDate()):date.getDate())+" "+(date.getHours()<10?("0"+date.getHours()):date.getHours())+":"+(date.getMinutes()<10?("0"+date.getMinutes()):date.getMinutes())+":"+(date.getSeconds()<10?("0"+date.getSeconds()):date.getSeconds()));
                         }
                        </script>
		</span>
		</h5>
	
		<p class='contents'>${doc.content}</p>
		
		<div class="choise"><ul>
		<c:forEach items="${fn:split(doc.keyWords,' ')}" var = "keyword" varStatus="status">
		<c:if test="${status.index<8}">
		<c:if test="${keyword!='<p>'}">
		<li class='link${status.index+1}'><a href='javascript:;' onclick='goPageKeyword(1,1,"${keyword}")'>${keyword}</a></li>
		</c:if>
		</c:if>
		</c:forEach>
		</ul></div>
		</div>
		</c:forEach>
		</div>	
	</div>
	<c:if test="${not empty list.data}">
	 <div class="page_list">
 	<dl>
     	
     	<c:if test="${page.pageNo>1}">
     		<dt  onclick='goPage(${page.pageNo-1},${page.startPage-1})'>&lt;上一页</dt>
     	</c:if>
     	<c:forEach var="c" begin="${page.startPage}" end="${page.endPage}">
     		<dd onclick="goPage(${c},${page.startPage})"<c:if test="${c==page.pageNo}">class="active"</c:if>>${c}</dd>
     	</c:forEach>
     	<c:if test="${page.pageNo<page.pageCount}">
     		<dt  onclick="goPage(${page.pageNo+1}<c:if test="${page.pageNo==page.endPage}">,${page.startPage+1}</c:if>)">下一页&gt;</dt>
     	</c:if>
     	<c:if test="${page.pageNo>=page.pageCount}">
     		<dt>下一页&gt;</dt>
     	</c:if>
     </dl>
 </div>
 </c:if>
		
		
		
	
	<!--  -->	
	
</div>
<!-- footer -->
<jsp:include page="footer.jsp"></jsp:include>
</body>

</html>
