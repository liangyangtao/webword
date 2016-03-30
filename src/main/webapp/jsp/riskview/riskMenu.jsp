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

 <title>风险信息列表页</title>
	<link rel="stylesheet" type="text/css" href="common/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="common/css/menu2.css"/>	
	<script type="text/javascript" src="common/js/jquery-1.8.3.min.js"></script>	
	<script type="text/javascript" src="common/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="common/js/common.js"></script>
</head>

<body>

<!-- header -->
<jsp:include page="header.jsp"></jsp:include>
<div name="遮挡背景" class="ui_back2"></div>
<div name="遮挡背景" class="ui_back"></div>
<div class="box_content">
<!-- 定制我的风险预警信息库 -->
		<div name="定制我的风险预警信息库" class="sbox_ industry_options">
			<h3>定制我的风险预警信息库<img src="common/img/s_exit.png" /></h3>
			<div class="sb_middle">
				<div class="stype stype1">	
					<dl>
						<!-- 一级 -->
						<h5><span><i></i><input type="checkbox" checked="checked"/><img src="common/img/choose2.png" />一级农业</span></h5>
						<!-- 二级 -->
						<dt><span><i></i><input type="checkbox"/><img src="common/img/choose2.png" />小麦、玉米、大豆</span></dt>
						<!-- 三级 -->
						<dd><span><input type="checkbox"/><img src="common/img/choose2.png" />农小麦</span></dd>
						<dd><span><input type="checkbox"/><img src="common/img/choose2.png" />农小麦</span></dd>
						<dt><span><i></i><input type="checkbox"/><img src="common/img/choose2.png" />小麦、玉米、大豆</span></dt>
						<dd><span><input type="checkbox"/><img src="common/img/choose2.png" />农小麦</span></dd>
					</dl>
				</div>
				<div class="stype stype2">	
						<p> 为了更高效的推送风险信息，请您完善以下信息：</p>
						<div class="s1">您的岗位：<input class="jobs " type="text"/><input value="" type="hidden"/></div>
						<div class="s2">岗位职责：<input class="responsibility" type="text"/><input value="" type="hidden"/></div>
						<div class="s3">关注的行业：<input class="industry" type="text"/><input value="" type="hidden"/></div>
						<div class="s4">我的关键词：<input class="keywords" type="text"/><input value="" type="hidden"/></div>
						<p class="tip2">关键词之间以一个空格分隔</p>
				</div>
			</div>
			<div class="sb_button  sb1"><a class="a1">关闭</a><a class="a2">确认</a><a class="a3">下一步</a><a class="a4">上一步</a></div>
		</div>	

</div>
<!-- body -->
<div class="direct">当前位置：
<c:forEach items = "${modulePathList}" var="module" varStatus="status">
<a class="d">${module.mname}</a>
<c:if test="${!status.last}">&gt;</c:if>
</c:forEach>
<!--  <a class="s">国家风险</a>-->
</div>
<div class="content">
	<div class="left_list">
		<ul>
			<c:forEach items="${moduleList}" var="module">
			<c:if test="${module.mid==currentModuleId}">
				<c:set value="${module.mname}" var="currentModuleName" scope="page"/>
				<li class="active" title="${module.mname}"><a href="risk/view/getNewsByModuleId.do?parentModuleId=${parentModuleId}&moduleId=${ module.mid}&page=1&id=${id}">${module.mname} </a></li>
			</c:if>
			<c:if test="${module.mid!=currentModuleId}">
				<li  title="${module.mname}"><a href="risk/view/getNewsByModuleId.do?parentModuleId=${parentModuleId}&moduleId=${ module.mid}&page=1&id=${id}">${module.mname} </a></li>
			</c:if>
			</c:forEach>
		</ul>
	</div>
	<!--  -->
	<div class="right ractive">
		<h4 class="title"><c:out value="${currentModuleName}"/></h4>
		<div class="list">
			<div class="list1">
			<c:forEach items="${searchRiskData.data}" var="risk">
				<h5><img class="point" src="common/img/point.png"/>
				<a class="title" target="_blank" href="risk/view/getNewsById.do?esId=${risk.esId}&riskId=${risk.crawl_id}&categoryId=${currentModuleId}&id=${id}" title="${risk.title} ">${risk.title}</a><span class="time">
				<fmt:formatDate value="${risk.newDate}" pattern="yyyy-MM-dd HH:mm"/>
				</span></h5>
				<p class="contents">
					${risk.content}
				</p>
				<div class="choise"><ul>
				<c:set var="keywords" value="${fn:split(risk.keyWords, ' ')}" />
				<c:forEach items="${keywords}" var="keyword" begin="1" end="7" varStatus="status">
				<li class="link${status.count}"><a target="_blank" href="risk/view/searchallbyword.do?keyword=${keyword}" >${keyword}</a></li>
				</c:forEach>
			</div>
			</c:forEach>
		</div>
		<div class="page_list"><dl>
		
		<c:set var="tagPageNum" value="${totalPageNum-4}" />
		
			<c:if test="${currentPageNum>1}">
				<a href="risk/view/getNewsByModuleId.do?parentModuleId=${parentModuleId}&moduleId=${ currentModuleId}&page=1&id=${id}"><dt>首页</dt></a>
				<a href="risk/view/getNewsByModuleId.do?parentModuleId=${parentModuleId}&moduleId=${ currentModuleId}&page=${currentPageNum-1}&id=${id}"><dt> 上一页 </dt></a>
			</c:if>
		
		<c:forEach  begin="1" end="5" varStatus="status">
			<c:if test="${totalPageNum<=5}">
			<c:if test="${status.count<=totalPageNum}">
				<c:if test="${currentPageNum==status.count}">
						<a href="risk/view/getNewsByModuleId.do?parentModuleId=${parentModuleId}&moduleId=${ currentModuleId}&page=${status.count}&id=${id}">
						<dd class="active">${status.count}</dd>
					</a>
					</c:if>
					<c:if test="${currentPageNum!=status.count}">
						<a href="risk/view/getNewsByModuleId.do?parentModuleId=${parentModuleId}&moduleId=${ currentModuleId}&page=${status.count}&id=${id}">
						<dd >${status.count}</dd>
					</a>
					</c:if>
					</c:if>
			</c:if>
		
		<c:if test="${totalPageNum>5}">
			<c:if test="${currentPageNum<=totalPageNum}">
			
				<c:if test="${currentPageNum<=tagPageNum}">
					<c:if test="${currentPageNum==currentPageNum+status.count-1}">
						<a href="risk/view/getNewsByModuleId.do?parentModuleId=${parentModuleId}&moduleId=${ currentModuleId}&page=${currentPageNum+status.count-1}&id=${id}">
						<dd class="active">${currentPageNum+status.count-1}</dd>
					</a>
					</c:if>
					<c:if test="${currentPageNum!=currentPageNum+status.count-1}">
						<a href="risk/view/getNewsByModuleId.do?parentModuleId=${parentModuleId}&moduleId=${ currentModuleId}&page=${currentPageNum+status.count-1}&id=${id}">
						<dd >${currentPageNum+status.count-1}</dd>
					</a>
					</c:if>
				</c:if>
				
				<c:if test="${currentPageNum>tagPageNum}">
					<c:if test="${currentPageNum==tagPageNum+status.count-1}">
						<a href="risk/view/getNewsByModuleId.do?parentModuleId=${parentModuleId}&moduleId=${ currentModuleId}&page=${tagPageNum+status.count-1}&id=${id}">
						<dd  class="active">${tagPageNum+status.count-1}</dd>
					</a>
					</c:if>
					<c:if test="${currentPageNum!=tagPageNum+status.count-1}">
						<a href="risk/view/getNewsByModuleId.do?parentModuleId=${parentModuleId}&moduleId=${ currentModuleId}&page=${tagPageNum+status.count-1}&id=${id}">
						<dd >${tagPageNum+status.count-1}</dd>
					</a>
					</c:if>
				</c:if>
				
			</c:if>
			</c:if>
		</c:forEach>
		<c:if test="${currentPageNum!=totalPageNum}">
				<a href="risk/view/getNewsByModuleId.do?parentModuleId=${parentModuleId}&moduleId=${ currentModuleId}&page=${currentPageNum+1}&id=${id}"><dt>下一页></dt></a>
				<a href="risk/view/getNewsByModuleId.do?parentModuleId=${parentModuleId}&moduleId=${ currentModuleId}&page=${totalPageNum}&id=${id}"><dt>尾页 </dt></a>
		</c:if>
		</dl></div>
	</div>
</div>
</div>
<!-- footer -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>	
