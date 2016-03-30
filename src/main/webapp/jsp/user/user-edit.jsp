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
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>知识创享网-编辑个人资料</title>
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
				<h3 class="user_name">
					<em></em>${user.userName}
				</h3>
				<img src="word/img/tou.gif" class="user_img" />
			</div>
		</div>
		<div class="clearfix userBody">
			<jsp:include page="user-left.jsp"></jsp:include>
			<div class="userRight">
				<h1>编辑资料</h1>
				<div class="userR_ edit_">
					<form class="userR_form" id="userR_form">
						<input type="hidden" id="userId" name="userId" value="${user.userId}"> 
						<ul>
							<li><span>邮箱</span>
								<p class="blueColor">${user.userAccount}</p></li>
							<li><span>手机号</span> 
									<p>${user.userPhone}</p>
									<input type="text" style="display: none;" name="userPhone" id="edi_phone" maxlength="11" value="${user.userPhone}">
									<p class="modify"  style="cursor: pointer;" onclick="editPhone(this,'<c:if test="${user.userName!=user.userAccount}">${user.userName}</c:if>');">修改</p>
							</li>
							<li style="display: none;"><span>&nbsp;&nbsp;</span>
								<input type="text" name="phoneCheck" id="phone_check" maxlength="6" style="width:110px;margin:0 8px 0 4px;"> 
								<label for="phone_check" class="placeholder">手机验证码</label> <input id="getCode" type="button" style="display:inline-block;background:#e3e3e3;border:1px solid #ccc;cursor:pointer;height:34px;outline:medium none; text-align:center; width:110px;border-radius:4px;margin-left:7px;font-size:12px;" onclick="sendMsg(this)" value="获取验证码" >
							</li>
							<li><span>昵称</span> 
									<p class="nn"><c:if test="${user.userName!=user.userAccount}">${user.userName}</c:if></p>
									<input type="text" style="display: none;" name="userName" id="edi_name" maxlength="50" <c:if test="${user.userName!=user.userAccount}">value='${user.userName}'</c:if>>
									<p class="modify" style="cursor: pointer;" onclick="editName(this,'${user.userPhone}');">修改</p>
							</li>
							<li class="form_tips" style="display: none;">4-12个字符，仅支持汉字、数字、英文、“-”、“_”</li>
							<li class="form_tips"><p class="redTip">手机号格式错误!</p>
							</li>
							<li class="last" style="display: none;">
								<input type="button" value="保存" onClick="edit_user_data();" class="btn-login curr" tabindex="4" />
								<input type="button" value="取消" onClick="cancle_user_data('${user.userPhone}','<c:if test="${user.userName!=user.userAccount}">${user.userName}</c:if>');" class="btn-login" tabindex="4" />
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
	<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
<script type="text/javascript">
<!--
//修改手机号
function editPhone(obj,name){
	$(".modify").show();
	$(obj).hide().prev().show().prev().hide().parent().next().show();
	$(".last").show();
	tipsHide($('.userR_form'));
	$("#edi_name").val(name).hide().prev().show().parent().next().hide();
}
//修改昵称
function editName(obj,phone){
	$(".modify").show();
	$("#phone_check").val("");
	$(obj).hide().prev().show().prev().hide().parent().next().show();
	$(".last").show();
	tipsHide($('.userR_form'));
	$("#edi_phone").val(phone).hide().prev().show().parent().next().hide();
}
//发送验证码
function sendMsg(){
	var reg_phone = /^(((13[0-9]{1})|(15[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //验证手机格式
	var phone = $.trim($("#edi_phone").val());
	if(phone==""||(phone!="" && !reg_phone.test(phone))){
		tipsShow($('.userR_form'), '手机格式错误!');return false;
	}
	if(validEmailorPhone(phone,function(data){
		tipsShow($('.userR_form'), data.msg);
	})){
		$.ajax({
	        type: "post",
	        url: "view/sendCheckMsg.do",
	        data:{userPhone:phone,userType:"0"},
	        async:false,
	        dataType:"json",
	        success: function(data) {
	        	if(data.status!="success"){
	        		tipsShow($('.userR_form'), data.info);
	        	}else{
	        		var i=60;
	        		$("#getCode").attr("value",i+'秒后重新获取');
	        		$("#getCode").attr('disabled','true');//让按钮不可点击，否则，会一直从60秒不停的倒计时
	        		 timer=setInterval(function(){
	        			i--;
	        			$("#getCode").attr("value",i+'秒后重新获取');
	        			if(i<0){
	        				$("#getCode").attr("value",'重新获取');
	        				$("#getCode").removeAttr("disabled");//让按钮恢复点击
	        				clearInterval(timer);
	        			}
	        		 },1000);
	        		tipsShow($('.userR_form'), "验证码已发送");
	        	}
	        }
	    });
	};
}

//保存资料
function edit_user_data(){
	tipsHide($('.userR_form'));
	var phone=$.trim($('#edi_phone').val());
	var name=$.trim($('#edi_name').val());
	if(phone=='' && $('#edi_phone').is(":visible")){		
		tipsShow($('.userR_form'),'请填写手机号!');
		return;
	}else if(phone!='' && !reg_phone.test(phone) && $('#edi_phone').is(":visible")){		
		tipsShow($('.userR_form'),'手机格式错误!');
		return;
	}else if(name==''&& $('#edi_name').is(":visible")){		
		tipsShow($('.userR_form'),'请填写昵称!');
		$('#edi_name').next('label.placeholder').show();
		return;
	}else if(!reg_name.test(name) && $('#edi_name').is(":visible")){
		tipsShow($('.userR_form'),'昵称必须4-12个字符（汉字=2字符），仅支持汉字、数字、英文、“-”、“_”');
		$('.edit_ .form_tips .redTip').show().html();
		return;
	}
	var reg = /^[\u4e00-\u9fa5]$/;
	var len = 0;
	for(var i=0;i<name.length;i++){
		if(reg.test(name[i])){
			len +=2;
		}else{
			len +=1;
		}
	}
	if(!(len>=4 && len<=12)){
		tipsShow($('.userR_form'),'昵称必须4-12个字符（汉字=2字符），仅支持汉字、数字、英文、“-”、“_”');
		return;
	}
	var data = {userId:$("#userId").val(),checkMsg:$.trim($("#phone_check").val())};
	if($('#edi_phone').is(":visible")){
		data.userPhone = phone;
		if(data.checkMsg.length<6){
			tipsShow($('.userR_form'), '手机验证码格式不对');return false;
		}
	}
	if($('#edi_name').is(":visible")){
		data.userName = name;
	}
	$.ajax({
        type: "post",
        url: "user/editSubmit.do",
        data:data,
        dataType:"json",
        async:false,
        contentType: "application/x-www-form-urlencoded; charset=utf-8", 
        success: function(data) {
        	if(data && data.result==1){        		
    			//showDiv(2);//保存成功
    			show_save_success(function(){
        		$('.edit_ p.modify,.edit_  p.nn').show();window.location.reload();});//20160122
    			$('.save_success p').text('修改成功！');
        	}else{
        		$('.edit_ .form_tips .redTip').hide();
    			$('.save_failure h3').text(data.msg);
    			//showDiv(1);//保存失败
    			show_save_failure();//20160122
        	}
        }
    });
}
//取消
function cancle_user_data(phone,name){
	$("#phone_check").val("");
	$("#edi_phone").val(phone).hide().prev().show().parent().next().hide();
	$("#edi_name").val(name).hide().prev().show().parent().next().hide();
	$(".modify").show();
	$(".last").hide();
	tipsHide($('.userR_form'));
}
//-->
</script>
</html>
