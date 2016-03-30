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
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>知识创享网-用户栏目编辑</title>
<link rel="stylesheet" type="text/css" href="word/css/news.css?v=<%=System.currentTimeMillis()%>">
<script type="text/javascript" src="word/js/global.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="apps/global/drag.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/alert.js?v=<%=System.currentTimeMillis()%>"></script>

</head>
<style>
.topBar .bg .hideUl{right:7px; top:35px;}
</style>
<body>
	<!-- header -->
	<jsp:include page="header.jsp"></jsp:include>
	<!-- header_end -->
	<!-- menu  -->
	<jsp:include page="menu.jsp"></jsp:include>
	<div class="userCont">

		<div class="clearfix listBody">
			<div class="listLeft">
				<h1 id="myMenuH" class="current">
					<p onclick="treeDisplay(0)">我的栏目</p>
				</h1>
				<ul id="treeUl" class="listAll"></ul>
			</div>
			<div class="listRight" id="columnList">
				<p class="navigation">
					<em></em>我的栏目&nbsp;&gt;&nbsp;<span id="treePath"></span><span
						class="drag">点击增删，拖动排序</span>
				</p>
				<div class="clearfix" id="displayUl1">
					<ul class="list_ul2" id="displayUl">
					</ul>
					<a class="addChannel" href="javascript:;" onclick="showPlatAdd();"><span>+</span>&nbsp;添加栏目</a>
				</div>
				<div class="clearfix lanmuTuijian" id="hotColumn">
					<h3>
						栏目推荐<em></em>
					</h3>
					<c:forEach items="${hotGroups}" var="group">
						<c:forEach items="${group.columns}" var="col1">
							<div class="clearfix" id="s${col1.id}">
								<h4>${col1.columnName}</h4>
								<ul class="list_ul2 clearfix">
									<c:forEach items="${col1.columns}" var="col2">
										<li class="li_sty" id="display_414"><a href="news/view/plate.do?columnId=${col2.id}"><img
												src="upload/${col2.picPath}">
										</a>
											<div>
												<p title="${col2.columnName}">
													${fn:substring(col2.columnName,0,6)} <span class="span1"
														onclick="getColumn(${col2.id},${col2.addFlag == null ? 0 : col2.addFlag})"><em></em>
													</span>
												</p>
											</div> <c:if test="${col2.addFlag == 1}">
												<span class="span2 finish_1"></span>
											</c:if> <c:if test="${col2.addFlag != 1}">
												<span class="span2 add_1" onclick="plateAddHot(${col2.id});"></span>
											</c:if></li>
									</c:forEach>
								</ul>
								<p class="more">
									<em></em>展开更多
								</p>
							</div>
						</c:forEach>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<div style="display: none;" class="fixMenuBox">
		<div class="fixMenuColumn">
			<span class="closeBt"><em>x</em>&nbsp;关闭</span>
			<div>
				<ul>
					<c:forEach items="${hotGroups}" var="group">
						<c:forEach items="${group.columns}" var="col">
							<li><a href="news/column/myPlateEdit.do#s${col.id}">${col.columnName}</a>
							</li>
						</c:forEach>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="fixMenu_hide"></div>
	</div>
	<!-- body_end -->
	<!-- footer -->
	<jsp:include page="footer.jsp"></jsp:include>
	<!-- footer_end -->
	<!-- 回到顶部按钮  -->
   
	<!-- 回到顶部按钮-end  -->
	<div name="遮挡背景" class="ui_back2">
		<div name="遮挡背景" class="ui_back"></div>
	</div>
	<div class="box_content">
		<div class="pop_div2 exit_pop">
			<span class="pop_closeBt"></span>
			<h3>提示</h3>
			<p class='tips'>确认退出?</p>
			<div class="sb_button">
				<a onclick="logout()" class="a1">确认</a><a class="a2"
					onclick="closeDiv()">取消</a>
			</div>
		</div>
		<div id="plateMenuDiv" name="添加栏目" class="pop_div2 add_channel">
			<span class="pop_closeBt" onclick="closeColumnDiv();"></span>
			<h3>添加栏目</h3>
			<div class="sb_middle">
				<div>
					<span style="width:50px">栏目名称</span>： <input id="plateName"
						type="text" value="" style="width:314px" maxlength="18" /> <input
						id="plateId" type="hidden" value="0" />
				</div>
				<div>
					<ul class="tabul">
						<li class="current">标签设置</li>
						<li>关键词设置</li>
					</ul>
				</div>

				<ul class="tabBox">
					<li class="tabDiv">
						<div class="clearfix">
							<p>
								<span class="span1">包含以下的全部标签</span>： <input id="mustTagNames"
									type="text" value="" />
							</p>
							<p>
								<span class="span1">包含以下任意一个标签</span>： <input
									id="shouldTagNames" type="text" value="" />
							</p>
							<p>
								<span class="span1">不包含以下标签</span>： <input id="mustNotTagNames"
									type="text" value="" />
							</p>
						</div>
					</li>
					<li class="tabDiv" style="display:none">
						<div class="clearfix">
							<p>
								<span class="span1">包含以下的全部关键词</span>： <input id="mustWordNames"
									type="text" value="" />
							</p>
							<p>
								<span class="span1">包含以下任意一个关键词</span>： <input
									id="shouldWordNames" type="text" value="" />
							</p>
							<p>
								<span class="span1">不包含以下关键词</span>： <input
									id="mustNotWordNames" type="text" value="" />
							</p>
							<p>
								<span class="span1">关键词位于</span>： <input type="radio" name="key"
									id="word_con" value="0" /><label for="word_con">全文</label> <input
									type="radio" name="key" id="word_tit" value="1"
									checked="checked" /><label for="word_tit">标题</label>
							</p>
						</div>
					</li>
				</ul>
				<div>
					<span style="width:50px">选择新闻源</span>： <input id="select_sources"
						type="text" value="" style="width:302px" />
				</div>
                <div style="margin-top: 0px; padding-left: 87px;line-height:20px;">（不选择时，默认全部新闻源）</div>
			</div>
			<div class="sb_button">
				<a class="a5" onclick="platePreview(2,1);">预览</a> <a class="a1"
					onclick="plateButton();">确认</a> <a class="a2"
					onclick="closeColumnDiv();">取消</a>
			</div>
		</div>
		<div name="查看栏目设置" class="pop_div2 modifi_channel">
			<span class="pop_closeBt" onclick="closeColumnDiv();"></span>
			<h3>查看栏目设置</h3>
			<div class="sb_middle">
				<div style="display:none">
					<span style="width:50px">栏目名称</span>： <input id="columnName"
						type="text" value="" style="width:320px" maxlength="18" /> <input
						id="columnId2" type="hidden" value="" />
				</div>
				<div>
					<span style="width:50px">栏目名称</span>： <span id="columnName2">发生大幅度释放</span>
				</div>
				<div>
					<ul class="tabul">
						<li class="current">标签设置</li>
						<li>关键词设置</li>
					</ul>
				</div>
				<div class="tabBox">
					<ul>
						<li class="tabDiv">
							<div class="clearfix">
								<p>
									<span class="span1">包含以下的全部标签</span>：<span id="mustTagNames2"></span>
								</p>
								<p>
									<span class="span1">包含以下任意一个标签</span>：<span
										id="shouldTagNames2"></span>
								</p>
								<p>
									<span class="span1">不包含以下标签</span>：<span id="mustNotTagNames2"></span>
								</p>
							</div>
						</li>
						<li class="tabDiv" style="display:none">
							<div class="clearfix">
								<p>
									<span class="span1">包含以下的全部关键词</span>： <span
										id="mustWordNames2" title=""></span>
								</p>
								<p>
									<span class="span1">包含以下任意一个关键词</span>： <span
										id="shouldWordNames2"></span>
								</p>
								<p>
									<span class="span1">不包含以下关键词</span>：<span
										id="mustNotWordNames2"></span>
								</p>
								<p>
									<span class="span1">关键词位于</span>： <span id="wordFlag"></span>
								</p>
							</div>
						</li>
					</ul>
				</div>
				<div>
					<span style="width:50px">选择新闻源</span>： <span id="select_sources2"
						class="selected_sour_p"></span>
				</div>
			</div>
			<div class="sb_button">
				<a class="a5" onclick="platePreview(2,2);">预览</a> <a class="a2"
					onclick="closeColumnDiv();">取消</a>
			</div>
		</div>

		<div name="预览" class="pop_div2 previ_channel">
			<span class="pop_closeBt_s"></span>
			<h3>新闻预览</h3>
			<div class="sb_middle">
				<div class="clearfix listBody" id="paging">
					<div class="list_box clearfix">
						<ul style="display: block;" class="list_feiqikan clearfix"
							id="feiqikan">
						</ul>
					</div>
				</div>
			</div>
			<div class="sb_button" id="recPlate">
				<a class="sub1" id="sub" style="display:none"
					onclick="plateAddHot();"></a> <a class="a12" id="sub2"
					onclick="plateDelHot();">取消订阅</a>
			</div>
		</div>

		<div name="全部 我的新闻源" class="pop_div2 news_sourse">
			<span class="pop_closeBt_s"></span>
			<h3>选择新闻源</h3>
			<h3 style="width:45%;">已选新闻源</h3>
			<div class="sb_middle">
				<div class="div1" id="LeSeaBox">
					网站名称： <input class="input i1 w70" type="text" /> <input
						class="input i2 w70" type="text" style="display:none" /> <a
						id="LeSearch" onselectstart="return false;">搜索</a><br> <span
						class="span2"> <input type="checkbox" /> 热门新闻源：</span><br>
					<!--  -->
					<span class="over_scroll"> </span>
				</div>
				<div class="divm">
					<img class="add" src="word/img/remove-2.png" /><br> <img
						class="remove" src="word/img/remove.png" />
				</div>
				<div class="div2" id="RiSeaBox">
					网站名称： <input class="input i1 w70" type="text" /> <input
						class="input i2 w70" type="text" style="display:none" /> <a
						id="RiSearch" onselectstart="return false;">搜索</a><br> <span
						class="span2"> <input type="checkbox" /> 全选：</span><br>
					<!--  -->
					<span class="over_scroll"> </span>
				</div>
			</div>
			<div class="sb_button">
				<a name="确认" class="a1">确认</a><a class="a2">关闭</a>
			</div>
		</div>

		<div name="已选新闻源" class="pop_div2 selected_sourse">
			<span class="pop_closeBt_s"></span>
			<h3>已选新闻源</h3>
			<div class="sb_middle">
				<div class="div2" id="RiSeaBox2">
					网站名称： <input class="input i1 w70" type="text" /> <input
						class="input i2 w70" type="text" style="display:none" /> <a
						id="RiSearch2" onselectstart="return false;">搜索</a><br> 
					<span class="over_scroll"> </span>
				</div>
			</div>
			<div class="sb_button">
				<a class="a2">关闭</a>
			</div>
		</div>

	</div>
	<input id="source" value="" type="hidden">
	<input id="addFlag" value="1" type="hidden">
	<input id="startPage" value="1" type="hidden">
	<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="apps/news/plate.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/jquery.SuperSlide.2.1.1.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="<%=basePath%>word/js/news.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>