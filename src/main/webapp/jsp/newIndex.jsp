<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*" %>
<%@ page import="com.database.bean.WordColumnGroup" %>
<%@ page import="com.database.bean.WordColumn" %>
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
<title>知识创享网-首页</title>
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>" />

</head>
<body>
<!-- header -->
<input type="hidden" id="path" value="<%=basePath%>">
<input type="hidden" id="userId" value="${user.userId}">
<input type="hidden" id="type" value="document"/>
<div name="header" class="header">
	<div class="bg">
		<span class="h_left"><a href="view/index.do">首页</a> <span>|</span>
			<a>客户端</a> <span>|</span> 客服热线：010-63368810</span> <span class="h_right">您好，欢迎来到知识创享网！
			<c:if test="${user!=null}">
				<a style="display:" href="user/journal/index.do" class='username'
					onMouseOver="showHeadUl();">${user.userName}</a>
				<a class="shop_car" id="topShopCar" href="cart/index.do">购物车<span id="cartCount">（${cartCount}）</span></a>	
			</c:if> <c:if test="${user==null}">
				<a style="display:" href="javascript:;" onClick="show_login()">请登录</a>
			</c:if> </span>
		<c:if test="${user!=null}">
			<ul class="hideUl" onMouseLeave="hideHeadUl();"
				onClick="hideHeadUl();">
				<li class="line"><a href="user/journal/index.do">我的文库</a>
				</li>
				<li class="line"><a href="user/recharge.do">我的账户</a>
				</li>
				<li><a href="javascript:;" onClick="show_exit();">退出</a>
				</li>
			</ul>
		</c:if>
	</div>
</div>
<div name="header2" class="header2"><a href="view/index.do"><img class="logo img" src="word/img/header1.png" /></a>
    <ul style="float:right">
        <li class="li4">        
        <a name="news" href="news/view/index.do">新闻</a>
        <a name="document" href="view/home.do">文档</a>
        <a name="Data">数据</a>
        <a name="video">视频</a></li>        
    </ul>
</div>
<!-- header_end --> 
<style>
.header2 ul .li4{margin-top:50px;}
.header2 ul .li4 a{cursor: pointer; font-size:16px; margin-left:100px;}
.header2 ul .li4 a:hover{text-decoration:underline;color:#05825a;font-weight:bold;}
.header2 ul .li4 a:active,.header2 ul .li1 a.current{text-decoration:underline;}

.part_Banner{min-width:1200px;height:400px;background:url(word/img/part_index/banner2.jpg) no-repeat top center;}
.part_Ul{width:1200px;margin:46px auto 200px;overflow:hidden;text-align:center;}
.part_Ul li{display:inline-block;width:135px;margin:0 40px; height:135px;border:3px solid #05825a;background:#fff;-moz-border-radius:50%; border-radius:50%;font-family:"Microsoft YaHei"; color:#676767; font-size:17px;}
.part_Ul li .img{display:block;margin:18px auto 10px;width:75px; height:61px;}
.part_Ul li:hover{color:#fff;background:#19a97b;}
.part_Ul li .img1{background:url(word/img/part_index/ic-1.png);}
.part_Ul li:hover .img1{background:url(word/img/part_index/ic-2.png);}
.part_Ul li .img2{background:url(word/img/part_index/ic-3.png);}
.part_Ul li:hover .img2{background:url(word/img/part_index/ic-4.png);}
.part_Ul li .img3{background:url(word/img/part_index/ic-5.png);}
.part_Ul li:hover .img3{background:url(word/img/part_index/ic-6.png);}
.part_Ul li .img4{background:url(word/img/part_index/ic-7.png);}
.part_Ul li:hover .img4{background:url(word/img/part_index/ic-8.png);}
.part_Ul li .img5{background:url(word/img/part_index/ic-9.png);}
.part_Ul li:hover .img5{background:url(word/img/part_index/ic-10.png);}
</style>
	<!-- body -->
	<div class="part_Banner"></div>
	<ul class="part_Ul">
		<li><a href="view/home.do"><p class="img img1"></p>工作平台</a></li>
		<li><a href="view/home.do"><p class="img img2"></p>银行社区</a></li>
		<li><a href="view/home.do"><p class="img img3"></p>商务平台</a></li>
		<li><a href="view/home.do"><p class="img img4"></p>文案平台</a></li>
		<li><a href="view/home.do"><p class="img img5"></p>学习教室</a></li>
	</ul>
	<!-- body_end -->
<!-- footer -->
	<jsp:include page="/jsp/footer.jsp"></jsp:include>
<!-- footer_end -->
<div name="遮挡背景" class="ui_back2"><div name="遮挡背景" class="ui_back"></div></div>
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
	
<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
