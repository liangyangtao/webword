<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String basePath = request.getContextPath() + "/";
%>
<input type="hidden" id="path" value="<%=basePath%>">
<input type="hidden" id="userId" value="${user.userId}">
<input type="hidden" id="type"
	value="<c:if test="${empty type}">document</c:if><c:if test="${not empty type}">${type}</c:if>" />
<!--顶部小条：begin-->
<div class="topBar">
	<div class="bg">
		<span class="h_left"><a href="view/index.do">首页</a> <span>|</span>
			<a>客户端</a> <span>|</span> 客服热线：010-63368810</span> 
			
			<span class="h_right">您好，欢迎来到知识创享网！
			<div id="username"  onMouseOver="showHeadUl();" onmouseout="hideHeadUl();">
			<c:if test="${user!=null}">
				<a style="display:" href="user/journal/index.do" class='username'
					><c:if test="${empty user.userName}">创享网用户</c:if>
					<c:if test="${not empty user.userName}">${user.userName}</c:if> </a>
			</c:if> <c:if test="${user==null}">
				<a style="display:" href="javascript:;" onClick="show_login();">请登录</a>
			</c:if> 
						<c:if test="${user!=null}">
			<ul class="hideUl hideUl1" onMouseLeave="hideHeadUl();"
				onClick="hideHeadUl();">
				<li class="line"><a href="user/journal/index.do">我的文库</a></li>
				<li class="line"><a href="user/recharge.do">我的账户</a></li>
				<li><a href="javascript:;" onClick="show_exit();">退出</a></li>
			</ul>
		</c:if>		
			</div>

			</span>

	</div>
</div>
<!--顶部小条：end-->

<!--header:begin-->
<div name="banner" class="banner">
	<a href="news/view/index.do"><img class="logo img"
		src="<%=basePath%>word/img/header1.png"><span class="news_logo">新闻</span>
	</a>
	<ul class="ul">
		<li class="li2"><input class="search_input" type="text"
			value="${keyword}" placeholder="这里搜索您关注的资讯"
			onfocus="this.placeholder=''"
			onblur="if(this.value==''){this.placeholder='这里搜索您关注的资讯'}">
			<span><a class="search">搜&nbsp;&nbsp;索</a> </span>
		</li>
		<li class="li3"><label><input class="input1" type="radio"
				name="searchType" value="ALL" id="radioAll">&nbsp;新闻全文</label> <label><input
				type="radio" name="searchType" value="TITLE" id="radioTitle">&nbsp;新闻标题</label>|
			<label class="search_menu"><span id="articleFormat"><script
						type="text/javascript">
				if ("${articleFormat}" == "SCORE") {
					document.write("按焦点排序");

				} else {
					document.write("按时间排序");
				}
			</script> </span><em></em> </label></li>

	</ul>
	<ul id="searchType" class="hideUl">
		<span class="icon"></span>
		<li id="title">按时间排序</li>
		<li id="content">按焦点排序</li>
	</ul>
	<ul class="tabUl">
		<li class="current"><a href="news/view/index.do">新闻</a></li>
		<li><a href="view/home.do">文档</a>
		</li>
		<li>数据</li>
		<li>视频</li>
	</ul>
</div>
<!--header:end-->

