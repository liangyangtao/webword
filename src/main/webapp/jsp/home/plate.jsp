<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>知识创享网-栏目列表</title>
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>" />
<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
</head>
<body>
	<!-- header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- header_end -->
	<!-- body -->
	<div class="userCont">
		<p class="navigation">${navigater}&nbsp;&gt;&nbsp;<c:if test="${contentType!=2}">期刊文档</c:if><c:if test="${contentType==2}">非期刊文档</c:if></p>
		<div class="clearfix listBody">
			<ul class="list_tab_tit clearfix">
				<li id="ic1" onclick="javascript:document.getElementById('qi').click();" <c:if test="${contentType == 1}">class="current"</c:if>><em class="ic1"></em>
				<c:if test="${not empty plateId  }">
					<a id="qi" href="view/plate.do?plateId=${plateId}&contentType=1">期刊文档</a>
				</c:if>
				<c:if test="${not empty columnId}">
					<a id="qi" href="view/column.do?columnId=${columnId}&contentType=1">期刊文档</a>
				</c:if>
				</li>
				<li id="ic2" onclick="javascript:document.getElementById('fei').click();" <c:if test="${contentType == 2}">class="current"</c:if> style="margin-left:1px;"><em class="ic2"></em>
				<c:if test="${not empty plateId}">
					<a id="fei" href="view/plate.do?plateId=${plateId}&contentType=2">非期刊文档</a>
				</c:if>
				<c:if test="${not empty columnId}">
					<a id="fei" href="view/column.do?columnId=${columnId}&contentType=2">非期刊文档</a>
				</c:if>
				</li>
			</ul>
			<div class="list_box clearfix">
				<c:if test="${contentType == 1 or empty contentType}">
				 <ul id="qikan" class="list_qikan clearfix" >
						<c:forEach items="${page.list}" var="jour">
							<li>
								<div>
									<a target="_blank" href="view/preview.do?journalId=${jour.journalId}" ><img src="${jour.cover}" title="${jour.name}" alt="${jour.name}"></a>
									<span class="icon icon1"><em>${jour.type}</em> </span>
									<c:if test="${jour.buyFlag == 1}"><span class="brought_icon"></span></c:if>								
									<p class="time">
										<span>更新<fmt:formatDate value="${jour.updateDate}" pattern="yyyy-MM-dd" /></span>
									</p>
								</div>
								<h5>
									<a target="_blank"  href="view/preview.do?journalId=${jour.journalId}" title="${jour.name}">${jour.name}</a>
								</h5>
							</li>
						</c:forEach>
						<c:if test="${empty page.list}"><p class="noRecord">暂无相关搜索结果，请重新设置栏目标签、关键词。</p></c:if>
				</ul>
				</c:if>
				<c:if test="${contentType == 2}">
				<ul id="feiqikan" class="list_feiqikan clearfix" >
	                	<c:forEach items="${page.list}" var="doc">
		                	<li>
			                	<div class="name">
										<em class="middot"></em><span class="ic-h-${doc.articleFormat}"></span>
										<p><a target="_blank"  href="view/preview.do?articleId=${doc.articleId}" title="${doc.articleName}">${doc.articleName}</a></p>
									</div>
									<div class="fullText">
										<a target="_blank"  href="view/preview.do?articleId=${doc.articleId}">
										   ${doc.articleSkip}									
				                        </a>
									</div>
									<div class="keyword">
									<script type="text/javascript">
										var keyword = "${doc.articleKeyWord}";
										if(keyword){
											var keywords = keyword.split(" ");
											var a="";
											for(var i=0;i<keywords.length;i++){
												var keyword = $.trim(keywords[i]);
												if(keyword){
													a+='<a target="_blank" href="view/search.do?keyword='+encodeURI(keyword)+'">'+keyword+'</a>';
												}
											}
											document.write(a);
										}
									</script>
									</div>
									<div class="supple">
									    <span><fmt:formatDate value="${doc.updateDate}" pattern="yyyy-MM-dd"/></span>|
									    <span>共${doc.htmlPage}页</span>|<span>${doc.downCount}次下载</span>|<span>${doc.viewCount}阅读</span>
								</div>
							</li>
	                   </c:forEach>
                       <c:if test="${empty page.list}"><p class="noRecord">暂无相关搜索结果，请重新设置栏目标签、关键词。</p></c:if>
                </ul>
                </c:if>
			</div>
 		    <jsp:include page="../pager.jsp"></jsp:include> 
		</div>
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
	</div>
	
	
	<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
<script type="text/javascript">
//<!--

	//分页跳转
	function goPage(pageNo, startPage) {
		var url;
		if(window.location.toString().indexOf("plateId") > -1){
			url = $("#path").val() + "view/plate.do?plateId=${plateId}&contentType=${contentType}";
		}else{
			var url = $("#path").val() + "view/column.do?columnId=${columnId}&contentType=${contentType}";
		}
		if (pageNo > 1) {
			url += (url.indexOf("?") > 0 ? "&" : "?") + "pageNo=" + pageNo;
		}
		if (startPage > 1) {
			url += (url.indexOf("?") > 0 ? "&" : "?") + "startPage="
					+ startPage;
		}	
		window.location = url;
	}
//-->
$(function(){
var topHei=218;//header height
var botHei=$('.footer').outerHeight()+20;//footer height
var allHei=$(window).height();
var conPaddBot=100;
var goalHei=allHei-topHei-botHei-conPaddBot-70;
//alert(botHei);
$('.list_box ul p.noRecord').height(goalHei);
});

</script>
</html>
