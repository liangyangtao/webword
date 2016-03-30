<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String basePath = request.getContextPath() + "/";
%>
<input type="hidden" id="path" value="<%=basePath%>">
<input type="hidden" id="userId" value="${user.userId}">
<input type="hidden" id="type"
	value="<c:if test="${empty type}">document</c:if><c:if test="${not empty type}">${type}</c:if>" />
<div name="header" class="header">
	<div class="bg">
		<span class="h_left"><a href="view/index.do">首页</a> <span>|</span>
			<a>客户端</a> <span>|</span> 客服热线：010-63368810</span> <span class="h_right">您好，欢迎来到知识创享网！
			<c:if test="${user!=null}">
				<a style="display:" href="user/journal/index.do" class='username'
					onMouseOver="showHeadUl();"><c:if test="${empty user.userName}">创享网用户</c:if> <c:if test="${not empty user.userName}">${user.userName}</c:if></a>
				<a class="shop_car" id="topShopCar" href="cart/index.do">购物车<span id="cartCount">（${cartCount}）</span></a>	
			</c:if> <c:if test="${user==null}">
				<a style="display:" href="javascript:;" onClick="show_login()">请登录</a>
			</c:if> </span>
		<c:if test="${user!=null}">
			<ul class="hideUl" onMouseLeave="hideHeadUl();"
				onClick="hideHeadUl();">
				<li class="line"><a target="_blank" href="user/journal/index.do">我的文库</a>
				</li>
				<li class="line"><a target="_blank"  href="user/recharge.do">我的账户</a>
				</li>
				<li><a href="javascript:;" onClick="show_exit();">退出</a>
				</li>
			</ul>
		</c:if>
	</div>
</div>
<div name="header2" class="header2">
	<a href="view/home.do">
	<img class="logo img"src="word/img/header1.png" />
	<span class="word_logo">文档</span>
	</a>
	<ul class="ul">
		<li class="li1" style="display:none"><a
			<c:if test="${'template' != type}">class="current" </c:if>
			name="document">文档</a> <a
			<c:if test="${'template' == type}">class="current"</c:if>
			name="template">模板</a>
		</li>
		<li class="li2"><input class="search_input" type="text"
			value="${keyword}" placeholder="请输入要查找的内容"
			onfocus="this.placeholder=''"
			onblur="if(this.value==''){this.placeholder='请输入要查找的内容'}" /> <span><a
				class="search">搜&nbsp;&nbsp;索</a>
		</span></li>
		<li class="li3" style="display:;"><input class="input1"
			type="radio" name="articleFormat" checked="checked" id="all" value="all" /> 全部 <input
			type="radio" name="articleFormat" id="doc" value="doc"/> DOC <input type="radio" name="articleFormat" id="ppt" value="ppt"/>
			PPT <!-- <input type="radio" name="radio1" /> TXT --> <input type="radio"
			name="articleFormat" id="pdf" value="pdf"/> PDF <!-- <input type="radio" name="radio1" /> XLS -->
			<script type="text/javascript">
				var articleFormat="${articleFormat}";
				if(articleFormat){		
					document.getElementById('all').checked=false;
					document.getElementById(articleFormat).checked=true;
				}
			</script>
			&nbsp;&nbsp;|<span><script type="text/javascript">				
						var searchType="${searchType}";
						if(searchType=="content"){
							a="按全文搜索";
						}else{
							a="按标题搜索";
						}
						document.write(a);					
				</script></span><em></em>
		</li>
	</ul>
	<ul id="searchType" class="hideUl"><span class="icon"></span>
		<li id="title" onclick='liclick(this)'>按标题搜索</li>
		<li id="content" onclick='liclick(this)'>按全文搜索</li>
	</ul>
	<ul class="tabUl">
		<li class="current"><a href="view/home.do">文档</a></li>
		<li><a href="news/view/index.do">新闻</a></li>
		<li>数据</li>
		<li>视频</li>
	</ul>
	<a href="wordNew.do" onclick="return isLogin();" target="_blank"><img
		class="upload img" src="word/img/header3.png" />
	</a>
</div>
<div name="菜单" class="menu">
	<div class="menu_bg">
		<a class="a0" href="view/home.do">首页</a>
		<c:forEach items="${list}" var="plate" varStatus="status">
    		<a class="a1 <c:if test="${plateId==plate.plateId or (not empty pid and pid.pid==plate.plateId)}">current</c:if>" href="view/plate.do?plateId=${plate.plateId}" >${plate.plateName}</a>  
		</c:forEach>
		<!-- 
		<a class="a1 " href="view/plate.do?plateId=374">农业农业农业</a> <a
			class="a2 " href="view/plate.do?plateId=395">工业</a> <a class="a3 "
			href="view/plate.do?plateId=396">商业</a> <a class="a4 "
			href="view/plate.do?plateId=397">银行</a> <a class="a5 "
			href="view/plate.do?plateId=394">农业</a> <a class="a6 "
			href="view/plate.do?plateId=395">工业</a> <a class="a7 "
			href="view/plate.do?plateId=396">商业</a> <a class="a8 "
			href="view/plate.do?plateId=397">银行</a> <a class="a9"
			href="global/myPlateEdit.do" onclick="return isLogin();"><img
			class="add" src="word/img/add.png" />
		</a> -->
		<a class="a9"
			href="global/myPlateEdit.do" onclick="return isLogin();"><img
			class="add" src="word/img/add.png" /></a>
	</div>

	<div class="second_menuBg"><ul class="second_menu">
	<!--
		<li><a href="javascript">农业</a><a href="javascript">农业</a><a href="javascript">农业</a>
		</li>
		-->
		<c:forEach items="${list}" var="plate" varStatus="status">
		<li><p>
			<c:forEach items="${plate.subs}" var="sub"  varStatus="status1">
				<a href="view/plate.do?plateId=${sub.plateId}">${sub.plateName}</a>
			</c:forEach></p>
		</li>
		</c:forEach>		
	</ul></div>
</div>