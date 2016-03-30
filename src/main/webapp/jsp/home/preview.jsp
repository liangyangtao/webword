<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<title>${article.articleName}</title>
<link rel="stylesheet" type="text/css"
	href="common/css/style.css?time=${strTime}" />
<script type="text/javascript" src="common/js/global.js?time=${strTime}"></script>
<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/alert.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/jquery-fly.js?v=<%=System.currentTimeMillis()%>"></script>
<link rel="stylesheet" type="text/css"
	href="apps/preview/css/common.css?v=<%=System.currentTimeMillis()%>">
<link rel="stylesheet" type="text/css"
	href="apps/preview/css/report.css?v=<%=System.currentTimeMillis()%>">
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>">
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>">
<style type="text/css" media="screen">
html,body {	height:100%;}
body {margin:0;padding:0;overflow:auto;}
#flashContent {	display:none;}
.highlight {background:green;font-weight:bold;color:white;}
/*html5*/
/* article,aside,dialog,footer,header,section,footer,nav,figure,menu{display:block} */
</style>

<script type="text/javascript" src="apps/preview/js/report.js?v=<%=System.currentTimeMillis()%>"></script>
</head>
<body style="background:#f9f9f9">
	<!-- header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<input type="hidden" id="articleFormat" value="${article.articleFormat}"/>
	<!-- header_end -->
	<div class="reportTop padl30 clearfix" style="display:none">
		<div class="l">
			<a href="view/toHomePage.action" class="back_index" target="_parent"></a>
		</div>
	</div>
	<!-- 头部 end  -->
	<!-- 顶部的下载与翻页 s -->
	<div class="download downloadfix" id="download" style="display:none">
		<div class="pos1200 w1200">
			<!-- <div class="preview_search">//分页搜索先屏蔽
				<input type="text" class="search_page" id="searchTextInput" onKeyDown="searchKeyDown(event);" />
				<img src="images/icons/presearch.jpg" class="search_btn" alt="搜索分页" onclick="searchText()" style="cursor:pointer"/>
			</div> -->
			<div class="reade_c_r">
				<!--<a id="j_hand" href="javascript:void(0);" class="move_btn drag_hand" data-label="手型"></a>
				<a id="j_select" href="javascript:void(0);" class="move_btn select_hand" data-label="选择文本"></a>-->
				<a id="j_zoomout" href="javascript:zoomSmall();"
					class="ico_rd zoom_out_no" data-label="缩小"></a> <a id="j_zoomin"
					href="javascript:zoomBig();" class="ico_rd zoom_in" data-label="放大"></a>
				<div class="tooltip_container" style="display: none;"></div>
			</div>
		</div>
	</div>
	<!-- <div id="downloadCopy" style="height:62px; width:100%; background:red; display:none;"></div> -->

	<!-- 顶部的下载与翻页 e -->
	<!--  主体内容 s -->
	<div name="区域1" class="previBody" style="min-height: 800px;">
		<div class="doc-header">
			<div class="reader-side">
				<!-- <h3 class="bgTitle" id="columnName">商业银行现金管理季度分析报告</h3> -->
				<div class="ifameshowbox" id="ifameshowbox"></div>
					<div class="reader-side-1 clearfix">
						<c:if test="${articleYear != null}"><h2>${wordJournal.name}</h2></c:if>
						<div class="brief">
							<p><c:if test="${articleYear != null}">出刊频率： ${wordJournal.type}</c:if></p>
						<c:if test="${articleYear != null}">
							<p class="p_price">
								期刊价格：
							<c:if test="${jourBuyStatus == 1}">免费</p></c:if>
							<c:if test="${jourBuyStatus == 2}">
								<span class="grey">${wordJournal.price}创享币/年</span>
								<font color="#f97908" style="float:right;margin-right:10px;">已购买</font>
								</p>
							</c:if>
							<c:if test="${jourBuyStatus == 0}">${wordJournal.price}创享币/年
							   <c:if test="${jourCartStatus == 0}"><a onclick="moveCart(event,1,'top');" id="shopCart1"><em></em>加入购物车</a></c:if>
							   <c:if test="${jourCartStatus == 1}"><font id="shopCart2" color="#f97908" style="float:right;margin-right:10px;">已加入购物车</font></c:if> 
	                           <font id="shopCart3" color="#f97908" style="display:none;float:right;margin-right:10px;">已加入购物车</font>
							   </p>
							</c:if>
						</c:if>
						<p class="p_brief">
								<c:if test="${articleYear != null}">期刊简介：${wordJournal.skip}</c:if>
								<c:if test="${articleYear == null}">简介：${article.articleSkip}</c:if>
							</p>
							<a href="javascript:;" onclick="showPreviBrief();">详细</a>
						</div>
					</div>
				<c:if test="${articleYear != null}">
					<div class="reader-side-2 clearfix">
					   <!--  
						<h2>
							<span>选择分期</span> 
							<p>
							<select id="articleYear" onchange="selectChange(1);">
								<c:forEach items="${yearList}" var="year">
									<option <c:if test="${year == articleYear}">selected="selected"</c:if> value="${year}">${year}年</option>
								</c:forEach>
							</select> 
							<c:if test="${wordJournal.typeId == 1 || wordJournal.typeId == 2 || wordJournal.typeId == 3}">
								<select id="articleMonth" onchange="selectChange(2);">
									<c:forEach items="${monthList}" var="month">
										<option <c:if test="${month == articleMonth}">selected="selected"</c:if> value="${month}">${month}月</option>
									</c:forEach>
								</select>
							</c:if></p>
	                    </h2>
	                    -->
						<h2>
							<span>选择分期</span> 
							<p>
							<select id="articleYear" onchange="selectChangeNew();">
								<c:forEach items="${yearList}" var="year">
									<option <c:if test="${year == articleYear}">selected="selected"</c:if> value="${year}">${year}年</option>
								</c:forEach>
							</select> 
							<c:if test="${wordJournal.typeId != 6 && wordJournal.typeId != 7 && wordJournal.typeId != 8}">
								<select id="articleMonth" onchange="selectChangeNew();">
									<c:forEach items="${monthList}" var="month">
										<option <c:if test="${month == articleMonth}">selected="selected"</c:if> value="${month}">${month}月</option>
									</c:forEach>
								</select>
								<c:if test="${wordJournal.typeId == 1}">
									<select id="articleDay" onchange="selectChangeNew();">
										<c:forEach items="${dayList}" var="day">
											<option <c:if test="${day.articleDay == articleDay}">selected="selected"</c:if> value="${day.articleDay}">${day.articleDay}日</option>
										</c:forEach>
									</select>
								</c:if>
							</c:if>
							</p>
	                    </h2>	                    
						<ul id="showList">
							<c:forEach items="${showList}" var="show">								
								
								 <li <c:if test="${show.articleId == article.articleId}"> class="grey"</c:if> onclick="previewJournalNew(${show.articleId},${selArticleId})"/>
								 ${show.articleShow}
<!-- 									<a href="view/preview.do?articleId=${show.articleId}&selArticleId=${selArticleId}">${show.articleShow}</a> -->
								</li>							
							</c:forEach>
						</ul>
					</div>
				</c:if>
				<div class="reader-side-3 clearfix">
				<script type="text/javascript">
					if('${fn:length(articles)}'==0){
				 }else{
				   document.write('<h2><span>相关推荐</span></h2>');
				 }
				</script>
					<ul class="listUl">
						<c:forEach items="${articles}" var="doc">
							<li>
								<h1>
								    <c:if test="${doc.articleFormat == ''}">
								        <b class="ic-h ic-h-doc"></b>
								    </c:if>
									<c:if test="${doc.articleFormat != ''}">
								        <b class="ic-h ic-h-${doc.articleFormat}"></b>
								    </c:if>
									<span> <a target="_blank"
										href="view/preview.do?articleId=${doc.articleId}"
										title="${doc.articleName}"> <c:if
												test="${fn:length(doc.articleName)>18}">${fn:substring(doc.articleName,0,18)}...</c:if>
											<c:if test="${fn:length(doc.articleName)<=18}">${doc.articleName}</c:if>
									</a> </span>
								</h1>
								<p class="doc-value2">
									<span title="${doc.viewCount}">热度:<c:forEach begin="1" end="5" var="len">
										<c:if test="${doc.viewCount>=len*50}">
											<b class="ic-h2 ic-h-star-s-on"></b>
										</c:if>
										<c:if test="${empty doc.viewCount or doc.viewCount<=len*50}">
											<b class="ic-h2 ic-h-star-s-off"></b>
										</c:if>
									</c:forEach>
									</span>
								</p></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="reader-container">
				<!-- <a href="javascript:;" id="beforeArticle" class="l btnLeft"></a>
				<a href="javascript:;" id="afterArticle" class="r btnRight"></a>
				<h2>${article.articleName}</h2> -->
				<div class="reader-title">
					<h1 class="previ-h1 clearfix">
						<b class="ic-h ic-h-${article.articleFormat}"></b><span>${article.articleName}</span>
						<c:if test="${article.articleFormat=='doc' or article.articleFormat=='docx'}">
						<a
							class="editBtn" href="javascript:void(0);"
							onclick="saveMy('${article.articleName}','${article.articleId}','${article.articleType}');return false;"><em></em>
							<c:if test="${article.articleType=='template'}">编辑此模板</c:if> <c:if test="${article.articleType=='document'}">编辑此文档</c:if></a>
							</c:if>
					</h1>
					<p class="doc-value clearfix">
						&nbsp;&nbsp;${articleCount.viewCount}人阅读&nbsp;&nbsp;|&nbsp;&nbsp;${articleCount.downCount}次下载&nbsp;&nbsp; 
					</p>
				</div>
				<!-- 在线预览内容 s  -->
				<div class="yulan">
					<div class="preContentBox" id="preContentBox"
						style="display: none;position:relative;z-index:9;">
						<c:if test="${buyStatus != 0}">
							<a onclick="return isLogin();" class="ic reader-fullScreen top-right-fullScreen"
								style="right: 0px;;top: 5px;" href="javascript:screenFull()"
								title="全屏显示" data-toolsbar-log="fullscreen"
								alog-action="htmltoolbar.fullscreen"></a>
						</c:if>
						<!-- id="topRightFullScreen" -->
					</div>
					<div  class="divOuter" style="display:none;"> <div class="opacity"></div></div>
				</div>
				
			</div>
			
		</div>
	</div>
	
	<!--  主体内容 e -->
	<!-- 在线预览固定底部 start  -->
	<!-- <div style="display:none;"><a href="javascript:void(0)" id="prevpage" onclick="preclick()" class="lTurn" ></a></div> -->
	<div class="download previewFooter">
		<div class="pos1200 w1200">
		    <c:if test="${buyStatus != 0}">
			   <a href="view/download.do?articleId=${article.articleId}" onclick="return power(3);" class="xiazai bottom_xiazai">下载</a>
			   <c:if test="${buyStatus == 1}"><div class="currentGold">免费</div></c:if>
			   <c:if test="${buyStatus == 2 || buyStatus == 3}">
			       <c:if test="${buyStatus == 2}"><a href="javascript:void(0);" class="recharge" style="background: #ccc;color:#4F4F4F">已购买</a></c:if>
				   <div class="currentGold" style="text-decoration:line-through">${article.articlePrice}创享币</div>
			   </c:if> 
			</c:if>
			<c:if test="${buyStatus == 0}">
			   <a href="javascript:void(0);" onclick="buyArt();" class="recharge">购买</a>
			   <c:if test="${wordJournal.typeId != 8}">
				   <c:if test="${artCartStatus == 0}"><a href="javascript:void(0);" id="botCart1" class="bot_shopcar" onclick="moveArtCart(event,2);">加入购物车</a></c:if>
				   <c:if test="${artCartStatus == 1}"><a href="javascript:void(0);" class="bot_shopcar" style="background: #ccc;color:#4F4F4F">已加入购物车</a></c:if>
			   </c:if>
			   <c:if test="${wordJournal.typeId == 8}">
				   <c:if test="${jourCartStatus == 0}"><a href="javascript:void(0);" id="botCart1" class="bot_shopcar" onclick="moveArtCart(event,2);">加入购物车</a></c:if>
				   <c:if test="${jourCartStatus == 1}"><a href="javascript:void(0);" class="bot_shopcar" style="background: #ccc;color:#4F4F4F">已加入购物车</a></c:if>
			   </c:if>
			   <a href="javascript:void(0);" class="bot_shopcar" style="display:none;background: #ccc;color:#4F4F4F" id="botCart2">已加入购物车</a>
			   <div class="currentGold">${article.articlePrice}创享币</div>	
			</c:if>			
			<div class="reader-tools-page">
				<input id="pageNumber" type="text" class="page-input" value="1"
					data-toolsbar-log="jumppage" onKeyDown="pageKeyDown(event);">
				<span class="page-count" id="totalPage">/${article.htmlPage}</span>
				<input type="hidden" value="${article.htmlPage}" id="pageCount" />
			</div>
			<div class="reader-tools-zoom">
				<a onclick="return isLogin();" href="javascript:zoomSmall()" class="ic zoom-decrease" title="缩小"
					data-toolsbar-log="zoomout" alog-action="htmltoolbar.zoomout"></a>
				<a onclick="return isLogin();" href="javascript:zoomBig()" class="ic zoom-add" title="放大"
					data-toolsbar-log="zoomin" alog-action="htmltoolbar.zoomin"></a>
			</div>
			<div class="reader-tools-zoom2">
				<a onclick="return isLogin();" href="javascript:screenFull()" class="ic reader-fullScreen"
					title="全屏显示" data-toolsbar-log="fullscreen"
					alog-action="htmltoolbar.fullscreen"></a>
			</div>
		</div>
	</div>
	<!-- 在线预览固定底部 end  -->

	<!-- 时间选择显示信息区域 start -->
	<div class="pos" id="posShowDiv">简介：${article.articleSkip}</div>
	<!-- 时间选择显示信息区域 end -->

	<!-- 回到顶部按钮  -->
	<div class="backToTop">
		<a class="reader-feedback" style="display:none" href="javascript:void(0);" title="新版反馈"
			hidefocus="true"><span></span>反馈</a> <a id="toTop" href="javascript:void(0);"
			title="回到顶部" hidefocus="true"><span class="bg-index s-ic-h top"></span>
		</a>
	</div>
	<img id="star" src="word/img/shopcar_big.png"/>
	<div class="buyNowDivOut"><div class="buyNowDiv">
		<div class="div_inner"><h3>试读已经结束，如果需要继续阅读或下载，敬请购买</h3>
		<ul>
		    <c:if test="${wordJournal.typeId != 8}">
			   <li><input type="radio" name="buy" id="buythis" value="1" checked="checked" onclick="changeType(1);"><label for="buythis">购买此篇文档：<em>${article.articlePrice}创享币</em></label></li>
			</c:if>
			<c:if test="${resoureType == 'journalarticle'}">
			   <li><input type="radio" name="buy" id="buyall" value="2" <c:if test="${wordJournal.typeId == 8}">checked="checked"</c:if> onclick="changeType(2);"><label for="buyall">购买全年期刊：<em>${wordJournal.price}创享币</em></label></li>
			</c:if>
		</ul>
		<div class="div_Bt">
			<a class="buyBt" onclick="prevBuy(1);">立即购买</a>
			<c:if test="${wordJournal.typeId != 8}">
				<c:if test="${artCartStatus == 0}"><a id="cart1" onclick="moveCart(event,2,'mid');" class="addToCar">加入购物车</a></c:if>
	            <c:if test="${artCartStatus == 1}"><a id="cart2" class="addToCar" style="background: #ccc;color:#4F4F4F">已加入购物车</a></c:if>
            </c:if>
            <c:if test="${wordJournal.typeId == 8}">
                <c:if test="${jourCartStatus == 0}"><a id="cart1" onclick="moveCart(event,2,'mid');" class="addToCar">加入购物车</a></c:if>
                <c:if test="${jourCartStatus == 1}"><a id="cart2" class="addToCar" style="background: #ccc;color:#4F4F4F">已加入购物车</a></c:if>
            </c:if>
            <a id="cart3" style="display:none" onclick="moveCart(event,2,'mid');" class="addToCar">加入购物车</a>
            <a id="cart4" style="display:none;background: #ccc;color:#4F4F4F" class="addToCar">已加入购物车</a>
		</div>
		</div>
	</div></div>
	<!-- footer -->
	<jsp:include page="../footer.jsp"></jsp:include>
	<!-- footer_end -->
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
		<div class="pop_div login_pop">
			<span class="pop_closeBt" ></span>
			<div class="pop_logo">
				<img src="word/img/header1.png">
			</div>
			<form class="form-login" name="form-login" method="post" action=""
				autocomplete="off">
				<ul>
					<li><input type="text" name="username" style="display:none">
						<input type="text" name="username" id="username_1" maxlength="30"
						autocomplete="off"> <label for="username_1"
						class="placeholder">邮箱或手机号</label></li>
					<li><input type="text" name="password" style="display:none">
						<input type="text" onfocus="this.type='password'" name="password"
						id="password_1" maxlength="30" autocomplete="off"
						onkeydown="javascript:butOnClick(event,'check_login()');">
						<label for="password_1" class="placeholder">密码<em>&nbsp;长度为6~30个字符</em>
					</label></li>
					<li class="form_tips">
						<p>请输入您的账号</p> <a href="javascript:;" onClick="service_tel()">忘记密码？</a>
					</li>
					<li class="last"><input type="button" value="登录"
						class="btn-login curr" onClick="check_login()" tabindex="4" /> <input
						type="button" value="注册" class="btn-login" onClick="show_register()"
						tabindex="4" /></li>
				</ul>
			</form>
		</div>
		<div class="pop_div regist_pop">
			<span class="pop_closeBt" ></span>
			<div class="pop_logo" style="padding:20px 0">
				<img src="word/img/header1.png">
			</div>
			<form class="form-login" name="form-login" method="post" action=""
				autocomplete="off">
				<ul>
					<li><input type="text" name="email" style="display:none">
						<input type="text" name="email" id="email_2" maxlength="30"
						autocomplete="off"> <label for="email_2"
						class="placeholder">邮箱</label>
						<p class="redTip">
							<em>*</em>必填
						</p></li>
					<li><input type="text" name="phone" style="display:none">
						<input type="text" name="phone" id="phone_2" maxlength="30"
						autocomplete="off"> <label for="phone_2"
						class="placeholder">手机号</label></li>
					<li><input type="text" name="phoneCheck" style="display:none">
						<input type="text" style="width:125px;" name="phoneCheck"
						id="phone_check" maxlength="10" autocomplete="off"> <input
						type="button" class="getTelYan" onclick="sendCheckMsg(this)"
						value="获取验证码"> <label for="phone_check"
						class="placeholder">手机验证码</label></li>
					<li><input type="text" name="password" style="display:none">
						<input type="text" onfocus="this.type='password'" name="password"
						id="password_2" maxlength="30" autocomplete="off"
						onkeydown="javascript:butOnClick(event,'check_regist()');">
						<label for="password_2" class="placeholder">密码<em>&nbsp;长度为6~30个字符</em>
					</label>
						<p class="redTip">
							<em>*</em>必填
						</p></li>
					<li class="form_tips">
						<p>请输入您的账号</p> <a style="right:56px;" href="javascript:;"
						onClick="forget_password()">忘记密码？</a></li>
					<li class="last"><input type="button" value="登录"
						class="btn-login " onClick="show_login()" tabindex="4" /> <input
						type="button" value="注册" class="btn-login curr"
						onClick="check_regist()" tabindex="4" /></li>
				</ul>
			</form>
		</div>



		<div class="pop_div pass_back_1">
			<span class="pop_closeBt" ></span>
			<div class="pop_logo">
				<img src="word/img/header1.png">
			</div>
			<form class="form-login" name="form-login" method="post" action="">
				<ul>
					<li><span style="width:102px">请输入登录邮箱</span> <input
						type="text" name="email" id="email_3" maxlength="30"></li>
					<li class="form_tips">
						<p>请输入您的账号</p></li>
					<li class="last"><input type="button" value="下一步"
						onClick="get_pass_1()" class="btn-login curr" tabindex="4" /></li>
				</ul>
			</form>
		</div>
		<div class="pop_div pass_back_2">
			<span class="pop_closeBt" ></span>
			<div class="pop_logo">
				<img src="word/img/header1.png">
			</div>
			<form class="form-login" name="form-login" method="post" action="">
				<ul>
					<li class="text">请拨打客服电话：010-63368810</li>
					<li class="last"><input type="button" value="确认"
						onClick="closeDiv();" class="btn-login curr" tabindex="4" /></li>
				</ul>
			</form>
		</div>
		<div class="pop_div pass_back_3">
			<span class="pop_closeBt" ></span>
			<div class="pop_logo">
				<img src="word/img/header1.png">
			</div>
			<form class="form-login" name="form-login" method="post" action="">
				<ul>
					<li><span>原密码</span>
						<p class="blue">密码重置，请输入新密码确认</p></li>
					<li><span>新密码</span> <input type="text" name="password"
						id="pass_new_4" maxlength="30">
						<p class="redTip">密码长度为6~30个字符</p></li>
					<li><span>确认密码</span> <input type="text" name="password"
						id="pass_sure_4" maxlength="30">
						<p class="redTip">重复输入密码确认无误！</p></li>
					<li><span>验证码</span> <input type="text" name="yanzheng"
						id="yanzheng_4" maxlength="16" tabindex="2" autocomplete="off"
						onkeydown="javascript:butOnClick(event);" style="width:88px">
						<img class="yan_img" src="word/img/yanzheng.png">
						<p class="blue">换张图片</p>
						<p class="redTip">请输入图片中的数字！</p></li>
					<li class="form_tips">
						<p style="margin-left:76px;">请输入您的账号</p></li>
					<li><input style="margin-left:230px" type="button" value="确定"
						onClick="get_pass_3()" class="btn-login curr" tabindex="4" /></li>
				</ul>
			</form>
		</div>
		<div class="pop_div pass_back_4">
			<span class="pop_closeBt" ></span>
			<h3>密码修改成功！</h3>
			<a href="javascript:;" class="btn-login" onclick="closeDiv();">确认</a>
		</div>

		<!-- 保存至文档 -->
		<div name="保存至" class="pop_div2 sbox_save_to_wd">
			<span class="pop_closeBt"></span>
			<h3>
				<c:if test="${user.userId==article.userId}">将此文档新建副本保存至我的文档</c:if>
				<c:if test="${user.userId!=article.userId}">将此文档保存至我的文档</c:if>
			</h3>
			<div class="sb_middle">
				<div>
					文件名： <input id="saveToWd" class="input" type="text" value="" /> <br>
				</div>
			</div>
			<div class="sb_button">
			    <a class="a1" onclick="sure_save_to('${article.articleType}');">确认</a>
				<a class="a3" onclick="closeDiv();">关闭</a>
			</div>
		</div>
		<div name="该模版保存方式" class="pop_div2 sbox_save_to_mb my_plug">
			<span class="pop_closeBt"></span>
			<h3>提示</h3>
			<div class="sb_middle">
				<div>
					您对此模板的修改将按以下的方式保存，请选择：<br> <br> <input type="radio"
						class="radio" name="documentType" value="2" checked="checked" />
					以此模板新建文档<br> <br> <input type="radio" class="radio"
						name="documentType" value="1" /> 保存至我的模板进行编辑<br>
				</div>
			</div>
			<div class="sb_button">
				<a class="a1" onclick="sure_save_to('${article.articleType}');">确认</a><a
					class="a3" onclick="closeDiv();">关闭</a>
			</div>
		</div>
		<div name="简介" class="pop_div2 previ_brief">
			<span class="pop_closeBt"></span>
			<h3>简介</h3>
			<div class="sb_middle">
				<div>
					<c:if test="${articleYear != null}">简介：${wordJournal.skip}</c:if>
				   <c:if test="${articleYear == null}">简介：${article.articleSkip}</c:if> 
				</div>
			</div>

		</div>
		<div name="支付提示" class="pop_div2 pay_tip_succ">
			<span class="pop_closeBt"></span>
			<h3>支付提示</h3>
			<div class="sb_middle">
				<div>
				    <h5 id="payMoney"></h5>
					<h5>当前账户剩余创享币：<em>${userMoney == null ? 0 :userMoney.money}</em>。创享币不足，无法完成付款。</h5>
					<h5>输入充值数量：<input id="rechargeMoney" type="text" class="input_" onkeyup="this.value=this.value.replace(/\D|^0/g,'')" 
					onafterpaste="this.value=this.value.replace(/\D|^0/g,'')">1创享币=1元</h5>
					<span>支付方式：</span>
                    <p class="pay_ways"><span class="w110 current"><img src="word/img/zhifubao.png"></span></p>  
                    <h5>温馨提示：支持线下充值，请联系客服 010-63368810</h5>    
                    <h5>　　　　（工作日9:30-17:30）</h5>                                        
				</div>
			</div>
			<div class="sb_button">
				<a class="a7" onclick="recharge(this);" target="_blank">充值并购买</a>
				<a class="a2" onclick="closeDiv();">取消</a>
			</div>
		</div>
		<div name="支付提示" class="pop_div2 pay_tip_fail">
			<span class="pop_closeBt"></span>
			<h3>支付提示</h3>
			<div class="sb_middle" id="needMoney"></div>
			<div class="sb_button">
				<a class="a11" onclick="buy();">确认购买</a>				
			</div>
		</div>
		<div name="支付提示" class="pay_tip_small">			
			<p>购买成功!</p>			
		</div>
		<div name="支付提示" class="pop_div2 pay_tips"	>
			<span class="pop_closeBt"></span>
			<h3>支付提示</h3>
			<div class="sb_middle">
				<dl>
					<dt>
						<img src="word/img/paysuc_tips.png">
					</dt>
					<dd>
						<h5>请您在新打开的页面上完成付款</h5>
						<p>付款完成前请不要关闭此窗口</p>
						<p>完成付款后请根据您的情况点击下面按钮</p>
					</dd>
				</dl>
			</div>
			<div class="sb_button">
				<a class="a8" onclick="prevBuy(1);">继续购买</a>
				<a class="a9" onclick="closeDiv();">支付遇到问题</a>
			</div>
		</div>		
	</div>
	<input type="hidden" id="articleId" value="${article.articleId}" />
	<input type="hidden" id="selArticleId" value="${selArticleId}" />
	<input type="hidden" id="journalType" value="${wordJournal.typeId}" />
	<input type="hidden" id="journalId" value="${wordJournal.id}" />
    <input type="hidden" id="money" value="${userMoney.money}" />
	<input type="hidden" id="buyStatus" value="${buyStatus}" />
	<input type="hidden" id="resoureType" value="${resoureType}" />	
	<input type="hidden" id="artCartStatus" value="${artCartStatus}" />	
	<input type="hidden" id="jourCartStatus" value="${jourCartStatus}" />	
	<input type="hidden" id="articlePrice" value="${article.articlePrice}" />	
	<input type="hidden" id="journalPrice" value="${wordJournal.price}" />	
	<input type="hidden" id="endPage" value="${endPage}"/>	
	<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script>
</body>

<script type="text/javascript">
	//回车跳页功能
	function pageKeyDown(event) {
		if (event.keyCode == 13) {
			var number = parseInt($("#pageNumber").val());
			var totalPage = $("#totalPage").html().substring(1);
			if (!isNaN(number)) {
				if (number<=totalPage && number>0) {
					pageNumber(number);
				} else {
					new altInfo({
						text : "页码超出了文档范围！"
					});
				}
			} else {
				new altInfo({
					text : "请输入正确页码！"
				});
			}
		}
	}
	
    //选择分期（新）
	function selectChangeNew() {

		var articleYear = $("#articleYear").val();//选中的年
		var articleMonth = $("#articleMonth").val();//选中的月
		var articleDay = $("#articleDay").val();//选中的日
		var articleId = $("#articleId").val();//
		var selArticleId = $("#selArticleId").val();//
		var journalType = $("#journalType").val();//

		$.ajax({
			type : "post",
			url : "view/getJournalInfoNew.do",
			async : false,
			data : {
				selArticleId : selArticleId,
				articleYear : articleYear,
				articleMonth : articleMonth,
				articleDay : articleDay
			},
			dataType : "json",
			success : function(data) {	
			   if (journalType != 8){	
		            var selYearOpt = $("#articleYear option");  
	                selYearOpt.remove();  
	                var selYearObj = $("#articleYear"); 
	                for (var i = 0;i < data.articleYearList.length;i ++){
	                  if (data.articleShowList[0].articleYear == data.articleYearList[i]){
	                  	  selYearObj.append("<option selected='selected' value='"+data.articleYearList[i]+"'>"+data.articleYearList[i]+"年</option>")	;	                 
	                  }else{
	                      selYearObj.append("<option value='"+data.articleYearList[i]+"'>"+data.articleYearList[i]+"年</option>")	;	
	                  }
	                }
               }
               
               //先清除原有月份数据，再添加当前年对应的月份数据   
               var selMonthOpt = $("#articleMonth option");  
               selMonthOpt.remove();  
               var selMonthObj = $("#articleMonth"); 
               for (var i = 0;i < data.articleMonthList.length;i ++){
	                if (data.articleShowList[0].articleMonth == data.articleMonthList[i]){
	                	   selMonthObj.append("<option selected='selected' value='"+data.articleMonthList[i]+"'>"+data.articleMonthList[i]+"月</option>")	;	                 
	                }else{
	                    selMonthObj.append("<option value='"+data.articleMonthList[i]+"'>"+data.articleMonthList[i]+"月</option>")	;	
	                }
               }
               
               //日刊添加日
		       if (journalType == 1){
			       var selDayOpt = $("#articleDay option");  
                   selDayOpt.remove();  
                   var selDayObj = $("#articleDay"); 
                   for (var j = 0;j < data.articleDayList.length;j ++){
                      if (data.articleShowList[0].articleDay == data.articleDayList[j].articleDay){
                          selDayObj.append("<option selected='selected' value='"+data.articleDayList[j].articleDay+"'>"+data.articleDayList[j].articleDay+"日</option>");
                      }else{
                      	  selDayObj.append("<option value='"+data.articleDayList[j].articleDay+"'>"+data.articleDayList[j].articleDay+"日</option>");
                      }
                   }
		       }
			       
		       $("#showList li").remove();
		       $("#selArticleId").val(data.articleShowList[0].articleId);
		       for (var h = 0;h < data.articleShowList.length;h ++){
		          if (data.articleShowList[h].articleId == articleId){
		              $("#showList").append("<li class='grey' onclick='previewJournalNew("+data.articleShowList[h].articleId+","+data.articleShowList[0].articleId+");'>"+data.articleShowList[h].articleShow+"</li>");		       		          		          
		          }else{
		              $("#showList").append("<li onclick='previewJournalNew("+data.articleShowList[h].articleId+","+data.articleShowList[0].articleId+");'>"+data.articleShowList[h].articleShow+"</li>");		       		          
		          };
// 		       	  $("#showList").append("<li><a href='view/preview.do?articleId="+data.articleShowList[h].articleId+"&selArticleId="+data.articleShowList[0].articleId+"'>"+data.articleShowList[h].articleShow+"</a></li>");
		       };
			}
		});
	}	
	
	function previewJournalNew(articleId,selArticleId){
	     var url = $("#path").val() + "view/preview.do?articleId="+articleId+"&selArticleId="+selArticleId;
	     window.location = url;
	};
//	选择分期
// 	function selectChange(changeType) {
// 		var articleYear = $("#articleYear").val();
// 		var articleId = $("#articleId").val();
// 		var journalType = $("#journalType").val();
// 		var articleMonth = $("#articleMonth").val();

// 		$.ajax({
// 			type : "post",
// 			url : "view/getJournalInfo.do",
// 			async : false,
// 			data : {
// 				articleId : articleId,
// 				articleYear : articleYear,
// 				articleMonth : articleMonth
// 			},
// 			dataType : "json",
// 			success : function(data) {
//			    选择年份时，先清除原有月份数据，再添加当前年份数据
// 			    if (changeType == 1 && (journalType == 1 || journalType == 2 || journalType == 3)){
// 			       var selOpt = $("#articleMonth option");  
//                    selOpt.remove();  
//                    var selObj = $("#articleMonth"); 
//                    for (var i = 0;i < data.articleMonth.length;i ++){
//                       selObj.append("<option value='"+data.articleMonth[i]+"'>"+data.articleMonth[i]+"月</option>")
//                    }
//                    selectChange(2);
// 			    }else{
// 			       $("#showList li").remove();
// 			       for (var j = 0;j < data.articleShow.length;j ++){
// 			       	  $("#showList").append("<li><a href='view/preview.do?articleId="+data.articleShow[j].articleId+"'>"+data.articleShow[j].articleShow+"</a></li>");
// 			       }
// 			    }
// 			}
// 		});
// 	}

</script>

</html>
