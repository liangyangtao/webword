<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<title>知识创享网-修改密码</title>
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>" />

</head>
<body>
<!-- header -->
<jsp:include page="../myCenter.jsp"></jsp:include>
<!-- header_end -->

<!-- body -->
<div class="userCont">
    <div class="userTopBg">
        <div class="userTop">
            <h3 class="user_name"><em></em>${user.userName}</h3>
            <img src="word/img/tou.gif" class="user_img" /></div>
    </div>
    <div class="clearfix userBody">
        <jsp:include page="user-left.jsp"></jsp:include>
        <div class="userRight">
           <h1>修改密码</h1>
            <div class="userR_ edit_">
                <form class="userR_form" name="" method="post" action="">
                    <ul>
                        <li><span>原始密码</span>
                            <input type="password" name="old_pass" id="old_pass" maxlength="30">
                        </li>
                        <li class="form_tips"><p class="redTip">密码输入错误!</p></li>
                        <li><span>新密码</span>
                            <input type="password" name="new_pass" id="new_pass" maxlength="30">
                        </li>
                        <li class="form_tips"><p class="redTip">密码长度为6~30个字符!</p></li>
                        <li><span>确认密码</span>
                            <input type="password" name="sure_pass" id="sure_pass" maxlength="30">
                        </li>
                        <li class="form_tips"><p class="redTip">两次输入的密码不一致!</p></li>
                        <li class="last">
                            <input type="button"  value="确认" class="btn-login curr" onclick="change_pass()" tabindex="4"/>
                            <input type="button"  value="取消" class="btn-login"  onclick="reset_pass()" tabindex="4"/>
                        </li>
                    </ul>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- body_end --> 
<!-- footer -->
<jsp:include page="../footer.jsp"></jsp:include>
<!-- footer_end -->
<jsp:include page="user-window.jsp"></jsp:include>
<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/alert.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
