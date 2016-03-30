<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>登陆页</title>
	<link rel="stylesheet" type="text/css" href="common/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="common/css/index.css"/>	
	<link rel="stylesheet" type="text/css" href="common/css/login.css"/>
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


<div name="遮挡背景" class="ui_back"></div>
<div class="box_content">
<!--  试用到期 温馨提示1  -->
	<div  class="sbox_ day_tips day_tips1">
			<h3>温馨提示<img src="common/img/s_exit.png" /></h3>
			<div class="sb_middle">
				<p class="p1">尊敬的：</p>
				<p class="p2"> &nbsp;&nbsp;&nbsp;&nbsp;您的免费试用已到期，为不影响您的正常使用，请您及时联系客服进行充值缴费。<br>&nbsp;&nbsp;&nbsp;&nbsp;联系电话: 010-63368810</p>
			</div>
			<div class="sb_button"><a class="a1">知道了</a></div>
		</div>
<!--  试用到期 温馨提示2  -->
	<div  class="sbox_ day_tips day_tips2">
			<h3>温馨提示</h3>
			<div class="sb_middle">
				<p class="p1">尊敬的：</p>
				<p class="p2"> &nbsp;&nbsp;&nbsp;&nbsp;您的免费试用期在3天后即将到期，为不影响您的正常使用，请及时联系客服进行充值缴费。<br>&nbsp;&nbsp;&nbsp;&nbsp;联系电话: 010-63368810</p>
			</div>
			<div class="sb_button"><a class="a1">知道了</a></div>
	</div>
</div>
<form id="login_form" name="regist_form" method="post">
<div class="content">
	<h2>用户登录</h2>
	<div><input id="userName" name="userName" class="name test_user" value="${cookie['loginName'].value}" placeholder="邮箱 或 手机号" onfocus="this.placeholder=''" onblur="this.placeholder='邮箱 或 手机号'" type="text"/></div>
	<div><input id="passWord" name="passWord" class="password test_pass" value="${cookie['loginPwd'].value}" placeholder="密码" onfocus="this.placeholder=''" onblur="this.placeholder='密码'" type="password"/></div>
	<div class="chiose"><input id="type" name="type" class="check" type="checkbox" checked="checked" value="1" <c:if test="${cookie['loginName'].value!=''}">checked</c:if>/>自动登录 | <a href="risk/view/tofondpassword1.do">忘记密码</a></div>
	<div id="text_tip3"></div>
	<a class="login_btn">登 录</a>
	<div class="right">没有账号？<a href="risk/view/toregist.do" class="color">立即注册</a></div>
	<div class="another">其他登录方式:<a class="qq"></a><a class="weixin"></a></div>
</div>
<script type="text/javascript">
/* 登陆按钮侦听回车 */
$(document).ready(function(){
	document.onkeydown=keyDownSearch;
})
 function keyDownSearch(e) {
      // 兼容FF和IE和Opera     
    var theEvent = e || window.event;     
   var code = theEvent.keyCode || theEvent.which || theEvent.charCode;     
    if (code == 13) {
          $(".content .login_btn").click();//具体处理函数 
          return false;     
      }     
      return true;     
   }


</script>
</form>
<!-- footer -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>	