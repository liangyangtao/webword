<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String basePath = request.getContextPath() + "/";
%>
<input type="hidden" id="path" value="<%=basePath%>">
<input type="hidden" id="userId" value="${user.userId}">
<input type="hidden" id="type"
	value="<c:if test="${empty type}">document</c:if><c:if test="${not empty type}">${type}</c:if>" />
<!--个人中心头部  ：begin-->
<div class="topBar">
	<div class="bg">
		<span class="h_left"><a href="view/index.do">首页</a> <span>|</span>
			<a>客户端</a> <span>|</span> 客服热线：010-63368810</span>
		<span class="h_right">
			您好，欢迎来到知识创享网！

			<div id="username" onMouseOver="showHeadUl_newC();"
				onmouseout="hideHeadUl_newC();">
				<c:if test="${user!=null}">
					<a style="display:" href="user/journal/index.do" class='username'><c:if
							test="${empty user.userName}">创享网用户</c:if> <c:if
							test="${not empty user.userName}">${user.userName}</c:if> </a>
				</c:if>
				<c:if test="${user==null}">
					<a style="display:" href="javascript:;" onClick="show_login();">请登录</a>
				</c:if>
				<c:if test="${user!=null}">
					<ul class="hideUl" onMouseLeave="hideHeadUl_newC();"
						onClick="hideHeadUl();">
						<li class="line"><a target="_self" href="user/journal/index.do">我的文库</a></li>
						<li class="line"><a target="_self" href="user/recharge.do">我的账户</a></li>
						<li><a href="javascript:;" onClick="show_exit();">退出</a></li>
					</ul>
				</c:if>
			</div>
			<a class="shop_car" id="topShopCar" href="cart/index.do">购物车<span
				id="cartCount">（${cartCount}）</span>
			</a>
		</span>
	</div>
</div>
<div name="banner" class="banner">
	<a href="user/journal/index.do"><img class="logo img"
		src="/webword/word/img/header1.png"><span class="news_logo">个人中心</span>
	</a>
	<ul class="tabUl">
		<li class="current"><a href="news/view/index.do">新闻</a>
		</li>
		<li><a href="view/home.do">文档</a>
		</li>
		<li>数据</li>
		<li>视频</li>
	</ul>
</div>
<!--end-->



