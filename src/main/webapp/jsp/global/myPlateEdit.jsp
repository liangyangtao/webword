<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>知识创享网-用户栏目编辑</title>
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>" />
<script type="text/javascript" src="common/js/global.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="apps/global/plate.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="apps/global/drag.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/alert.js?v=<%=System.currentTimeMillis()%>"></script>
</head>
<body>
	<!-- header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- header_end -->

	<!-- body -->
	<div class="userCont">

		<div class="clearfix listBody">
			<div class="listLeft">
				<h1 id="myMenuH" class="current">
					<p onclick="treeDisplay(0)">我的栏目</p>
				</h1>
				<ul id="treeUl" class="listAll"></ul>
			</div>
			<div class="listRight"  id="columnList">
				<p class="navigation">
					<em></em>我的栏目&nbsp;&gt;&nbsp;<span id="treePath"></span><span
						class="drag">点击增删，拖动排序</span>
				</p>
				<div class="clearfix" id="displayUl1">
					<ul class="list_ul2" id="displayUl">
	
					</ul>
					<a class="addChannel" href="javascript:;"
						onClick="showPlatAdd();"><span>+</span>&nbsp;添加栏目</a>
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
										<li class="li_sty"
											id="display_414"><img src="word/img/banner5.png">
											<div>
												<p title="${col2.columnName}">
													${col2.columnName}
													<span class="span1" onclick="getColumn(${col2.id},${col2.addFlag == null ? 0 : col2.addFlag})"><em></em></span> 
												</p>
											</div> 
											<c:if test="${col2.addFlag == 1}"><span class="span2 finish_1"></span></c:if>
										 	<c:if test="${col2.addFlag != 1}"><span class="span2 add_1" onclick="plateAddHot(${col2.id});"></span></c:if>
										</li>	
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
	<div class="fixMenuBox">
		<div class="fixMenuColumn">
			<span class="closeBt"><em>x</em>&nbsp;关闭</span>
			<div>
				<ul>
					<c:forEach items="${hotGroups}" var="group">
						<c:forEach items="${group.columns}" var="col">
							<li><a href="global/myPlateEdit.do#s${col.id}">${col.columnName}</a></li>
						</c:forEach>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="fixMenu_hide"></div>
	</div>	
	<!-- body_end -->
	<!-- footer -->
	<jsp:include page="../footer.jsp"></jsp:include>
	<!-- footer_end -->
	<div name="遮挡背景" class="ui_back2">
		<div name="遮挡背景" class="ui_back"></div>
	</div>
	<div class="box_content">
		<div class="pop_div2 exit_pop">
			<span class="pop_closeBt" ></span>
			<h3>提示</h3>
			<p class='tips'>确认退出?</p>
			<div class="sb_button">
				<a onclick="logout()" class="a1">确认</a><a class="a2"
					onclick="closeDiv()">取消</a>
			</div>
		</div>
		<div id="plateMenuDiv" name="添加栏目"
			class="pop_div2 add_channel">
			<span class="pop_closeBt" onclick="closeColumnDiv();"></span>
			<h3>添加栏目</h3>
			<div class="sb_middle">
				<div>
					<span style="width:50px">栏目名称</span>：
					 <input id="plateName" type="text" value="" style="width:320px" maxlength="6" /> 
					 <input id="plateId" type="hidden" value="0" />
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
			</div>
			<div class="sb_button">
				<a class="a5" onclick="platePreview(2,1,1);">预览</a>
				<a class="a1" onclick="plateButton();">确认</a> 
				<a class="a2" onclick="closeColumnDiv();">取消</a> 
			</div>
		</div>
		<div name="查看栏目设置" class="pop_div2 modifi_channel">
			<span class="pop_closeBt" onclick="closeColumnDiv();"></span>
			<h3>查看栏目设置</h3>
			<div class="sb_middle">
				<div style="display:none">
					<span style="width:50px">栏目名称</span>： 
					<input id="columnName" type="text" value="" style="width:320px" maxlength="6" /> 
					<input id="columnId2" type="hidden" value="" />
				</div>
				<div>
					<span style="width:50px">栏目名称</span>： <span id="columnName2"></span>
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
									<span class="span1">包含以下任意一个标签</span>：<span id="shouldTagNames2"></span>
								</p>
								<p>
									<span class="span1">不包含以下标签</span>：<span id="mustNotTagNames2"></span>
								</p>
							</div>
						</li>
						<li class="tabDiv" style="display:none">
							<div class="clearfix">
								<p>
									<span class="span1">包含以下的全部关键词</span>： <span id="mustWordNames2"></span>
								</p>
								<p>
									<span class="span1">包含以下任意一个关键词</span>： <span id="shouldWordNames2"></span>
								</p>
								<p>
									<span class="span1">不包含以下关键词</span>：<span id="mustNotWordNames2"></span>
								</p>
								<p>
									<span class="span1">关键词位于</span>： <span id="wordFlag"></span>
								</p>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="sb_button">
				<a class="a5" onclick="platePreview(2,1,2);">预览</a>
				<a class="a2" onclick="closeColumnDiv();">取消</a> 
			</div>
		</div>

		<div name="预览" class="pop_div2 previ_channel">
			<span class="pop_closeBt_s"></span>
			<div class="sb_middle">
				<div class="clearfix listBody" id="paging">
					<ul class="list_tab_tit clearfix">
						<li class="current" onclick="platePreview(2,1);">期刊文档</li>
						<li style="margin-left:1px;" onclick="platePreview(2,2);">非期刊文档</li>
					</ul>					
					<p id="resultNum" class="resultNum"></p>
					<div class="list_box clearfix">
						<ul id="qikan" class="list_qikan clearfix" ></ul>
						<ul id="feiqikan" class="list_feiqikan clearfix" style="display:none"></ul>
					</div>
				</div>
			</div>
			<div class="sb_button" id="myPlate" style="display:none">
				<p class="initial" onclick="init();"><img src="word/img/lan_chushi.png"></p>
				<a class="a1" onclick="confirmInit();">确认</a>
				<a class="a2" onclick="cancel();">取消</a>
			</div>
			<div class="sb_button" id="recPlate" style="display:none">
				<a class="sub1" id="sub" onclick="plateAddHot();"></a>
				<a class="" id="sub2" style="display:none" onclick="plateDelHot();">取消订阅</a>
				<a class="a2" >关闭</a>
			</div>
		</div>
	</div>
	<input type="hidden" id="oldTopList" value="" />
	<input type="hidden" id="oldDelList" value="" />
	<input type="hidden" id="topList" value="" />
	<input type="hidden" id="delList" value="" />
	<input type="hidden" id="initFlag" value="0" />
	<input type="hidden" id="editFlag" value="0" />
	<input type="hidden" id="source" value="" />
	<input type="hidden" id="addFlag" value="0" />		
	<input type="hidden" id="contentType" value="" />	
	<input type="hidden" id="startPage" value="1" />	
	<input type="hidden" id="addOrEdit" value="" />
	<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
