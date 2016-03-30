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
<title>注册页</title>
	<link rel="stylesheet" type="text/css" href="common/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="common/css/index.css"/>	
	<link rel="stylesheet" type="text/css" href="common/css/regist.css"/>
	<script type="text/javascript" src="common/js/jquery-1.8.3.min.js"></script>	
	<script type="text/javascript" src="common/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="common/js/common.js"></script>
	<script type="text/javascript" src="common/js/jquery.cityselect.js"></script>
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
<form id="regist_form" name="regist_form" method="post">
<div class="content">
	<div class="a_menu">
		<a class="m1 active"><img src="common/img/rg_img1.png"/>会员注册</a><!-- <a class="m2"><img src="common/img/rg_img2.png"/>手机注册</a> -->
		<br>&nbsp;&nbsp;&nbsp;&nbsp;您好！欢迎您注册北京银联信风险预警系统！注册成功后您将获得30天免费使用期，祝您使用愉快！
	</div>
	<div class="form  forma1">
		
		
		<div id="tempid" class="company">
            <span class="color">* </span>单位名称：<input type="text" class="companyName" id="companyName" name="companyName"placeholder="单位名称" onfocus="this.placeholder=''" onblur="this.placeholder='单位名称'"/>
            <input id="companyId" type="hidden" name="companyId" /><span id="city_3">
            <select id="province" name="province" class="prov"></select>
            <select id="city" name="city" class="city" disabled="disabled"></select></span>
        </div>
        <div style="padding-left: 115px;display:none;" id="subbranchDiv">
        	 <input class="" id="subbranch" name="subbranch" type="text" placeholder="请输入支行名称" onfocus="this.placeholder=''" onblur="this.placeholder='请输入支行名称'"/>
        </div>
        
        <div>
            <div><span class="btn">找不到单位名称？</span></div>
            <div class="companyS">
                <select name="companyType" id="companyType">
                	<option value="2">银行</option>
                    <option value="1">企业</option>
                </select>
                <input class="" id="companyName_spare" name="companyName2" type="text" placeholder="请输入单位名称" onfocus="this.placeholder=''" onblur="this.placeholder='请输入单位名称'"/>
            </div>
        </div>
		
		<div class="d1"><span class="color">* </span>姓名：<input class="width239 name" type="text" name="userName" /><input type="hidden"/></div>
		<div class="d1"><span class="color">* </span>邮箱：<input id="user_email" name="user_email" class="nub test_email" type="text"/><input type="hidden"/>
			<!-- span class="tip mtip">该邮箱已注册可<a class="color">直接登录</a>，如忘记密码<a class="color">点击此处找回</a></span --></div>
		<div class="da1"><span class="color">* </span>手机号：<input id="userPhone" name="userPhone" class="nub test_phone2" type="text"/><input type="hidden"/><!-- input type="submit"/ --><a class="get_nub">免费获取短信动态码</a></div>
		<div class="da2"><span class="color">* </span> 手机验证：<input id="password" name="password" class="" /><input type="hidden"/></div>
		<div class="d2"><span class="color">* </span> 创建密码：<input id="user_password" name="user_password" class="test_password" type="password"/><input type="hidden"/><span class="tip mtip">长度为6-20位，区分大小写</span></div>
		<div class="d3"><span class="color">* </span> 确认密码：<input id="user_password2" name="user_password2" class="test_password2" type="password"/><input type="hidden"/></div>
		
		
		

		
		
		
		
		
		
		
		<div style="clear:both"></div>
		<script type="text/javascript">
		$("#city_3").citySelect({prov:"北京", city:"东城区"});
		</script>
		
		<!-- 
		<div class="d4">所在城市：
			<select id="province" name="province"><option value="河南">河南</option><option value="河北">河北</option></select>
			<select id="city" name="city"><option value="郑州">郑州</option><option value="石家庄">石家庄</option></select>
		</div>
		-->

		<div class="d5">您的岗位：<input id="station" name="station" class="" type="text"/><input type="hidden"/></div>
		<div class="d6">岗位职责：<input id="responsibility" name="responsibility" class="" placeholder="请输入关键词描述您的职位职责" onfocus="this.placeholder=''" onblur="this.placeholder='请输入关键词描述您的职位职责'" type="text"/><input type="hidden"/></div>
		<div class="d7">关注的行业：<input id="interest" name="interest" class="" placeholder="请输入关键词描述您关注的行业" onfocus="this.placeholder=''" onblur="this.placeholder='请输入关键词描述您关注的行业'" type="text"/><input type="hidden"/></div>
		
		<div class="d8"><span class="color">* </span> 验证码：
		<input id="verifyCode" name="verifyCode" class="" type="text" placeholder="请输入计算结果 " onfocus="this.placeholder=''" onblur="this.placeholder='请输入计算结果'"/><input type="hidden"/>
	<!--  	<img id="kaptchaImage" name="kaptchaImage" alt="验证码" src="Kaptcha.jpg"><span class="tip yztip">请填写验证码，不区分大小写 </span>-->
		<img id="verificationCodeImage" name="verificationCodeImage" title="点击更换验证码" src="risk/view/getVerificationCode.do"   onclick="this.src='risk/view/getVerificationCode.do';">
		<!-- <a class="ch_one">看不清，换一个</a> -->
		</div>
		<script type="text/javascript">  
			$(function(){  
			    $('#kaptchaImage').click(function () {
			        $(this).attr('src', 'Kaptcha.jpg?' + Math.floor(Math.random()*100) ); });
			    $('.content  .ch_one').click(function () {
			        $('#kaptchaImage').attr('src', 'Kaptcha.jpg?' + Math.floor(Math.random()*100) ); });
			});  
		</script> 
		
		<div id="text_tip"></div>
		<div class="button"><a class="done">同意以下协议并注册</a><br/><a class="deal">《银联信用户服务协议》</a></div>
	</div>
	<!-- <div class="form  forma2">
		<div class="d1"><span class="color">* </span>手机号：<input class="nub test_phone2" type="text"/><input type="hidden"/><a class="get_nub">免费获取短信动态码</a></div>
		<div class="d2"><span class="color">* </span> 短信动态码：<input class="" type="password"/><input type="hidden"/></div>
		<div class="d3"><span class="color">* </span> 创建密码：<input class="test_password2" type="password"/><input type="hidden"/></div>
		<div class="d4"><span class="color">* </span> 确认密码：<input class="" type="password"/><input type="hidden"/></div>
		<div class="d5">您的岗位：<input class="" type="text"/><input type="hidden"/></div>
		<div class="d6">岗位职责：<input class="" placeholder="请输入关键词描述您的职位职责" onfocus="this.placeholder=''" onblur="this.placeholder='请输入关键词描述您的职位职责'" type="text"/><input type="hidden"/></div>
		<div class="d7">关注的行业：<input class="" placeholder="请输入关键词描述您关注的行业" onfocus="this.placeholder=''" onblur="this.placeholder='请输入关键词描述您关注的行业'" type="text"/><input type="hidden"/></div>
		<div class="d8">验证码：<input class="" type="text"/><input type="hidden"/><img src="common/img/yzm.png"/><span class="tip yztip">请填写验证码，不区分大小写 </span><a class="ch_one">看不清，换一个</a></div>
		<div id="text_tip2"></div>
		<div class="button"><a class="done">同意以下协议并注册</a><br/><a class="deal">《银联信用户服务协议》</a></div>
	</div> -->
</div>
</form>

<!-- footer -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
oSearchSuggest1($("#companyName"),$("#tempid"),
        "115","51",$(".content"),8,function(li){
	$("#companyId").val($(li).attr("mid"));
	
});

function oSearchSuggest1(input, suggestWrap, cLeft, cTop, closeB, pageSize,callback2) {
    var flag=false;//标示,如果值是输入的联想内容,翻页的时候触发blur事件,不清空文本框值
    var inputVal=input.val();//联想框中的值,当点击翻页的时候还原该值
	var init = function() {
        input.bind('click',
        function(event) {
            sendKeyWordToBack1();
            event.stopPropagation();
        });
        input.bind('keyup', function(){
        	flag=true;
        	inputVal=input.val();
        	sendKeyWordToBack1();
            event.stopPropagation();
           
        });
        input.bind('blur',
        function() {
        	 //$('#form_cities1').remove();
        	 if(flag){
        		 input.val('');
        		 $("#companyId").val(null);//隐藏框中值设置为空
        		 //$(".companyS").hide();
             	 //$("#companyName_spare").val(null);//设置找不到单位文本框,不再发送该数据
             	 $("#subbranchDiv").hide();
            	 $("#subbranch").val(null);//设置选择框中的值为空,不再发送该数据
        	 }
        });
        closeB.bind('click',
        function() {
            $('#form_cities1').remove();
        });
    };
    var sendKeyWordToBack1 = function(pageId) {
        dataDisplay1(pageId || 1);
    };
    var dataDisplay1 = function(pageId) {
        //alert("aaa");
    	if(!pageId) pageId=1;
    	var valText = $.trim(input.val());
    	var data ;
        $.ajax({
            type: "POST",
            url: "risk/view/searchCompanyAndBankList.do",
            async: false,
            data: {
                name: valText,
                pageId: pageId,
                pageSize: pageSize
            },
            dataType: "json",
            success: function(d) {
                if (d.status == "success") {
                	data = d;
                	
                }
            }
        });
        $('#form_cities1').remove();
        if (! (data && data.count > 0 && data.list && data.list.length)) {
            return;
        }
        var newDiv = "<div id='form_cities1'><ul id='panel_cities1'>";
        for (var i = 0; i < data.list.length; i++) {
        	newDiv += "<li mid=" + data.list[i].id +" bctype="+data.list[i].bctype+">" + data.list[i].bcname + "</li>";
        }
        var pageCount = data.count % pageSize == 0 ? data.count / pageSize: Math.ceil(data.count / pageSize) ;
        var pageId = data.pageId;
        if (data && data.count > 0 && data.list && data.list.length && pageCount>1) {
        	newDiv += "</ul><div id='flip_cities1'><a href='javascript:void(0);'>«&nbsp;向前</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);'>向后&nbsp;»</a></div></div>";
        }
        suggestWrap.append(newDiv);
        $('#form_cities1').show().css({
            'left': cLeft + 'px',
            'top': cTop + 'px'
        });
        $('#form_cities1').find('li').mouseover(function() {
            $(this).addClass('hover').siblings().removeClass('hover');
        });
        $('#form_cities1').find('li').bind('click',
        function() {
            input.val($(this).html());
            flag=false;
            //选中之后关闭找不到单位下面的选择框
            //$(".companyS").hide();
            //$("#companyName_spare").val(null);//设置选择框中的值为空,不再发送该数据
            
            if($(this).attr("bctype")==2){//选中的是银行
            	$("#subbranchDiv").show();
                //if($("#subbranchDiv").is(":hidden")){
                	//$("#subbranchDiv").show();
                //}else{
                	//$("#subbranchDiv").hide();
                	//$("#subbranch").val(null);//设置选择框中的值为空,不再发送该数据
                //}
                
            }else{
            	$("#subbranchDiv").hide();
            	$("#subbranch").val(null);//设置选择框中的值为空,不再发送该数据
            }
            callback2(this);
            $('#form_cities1').remove();
        });
        $('#flip_cities1').find('a').eq(0).bind('click',function(event) {
        	input.val(inputVal);
        	input.focus();
        	if(pageId>1){
        		sendKeyWordToBack1(pageId-1);
        	}
        	event.stopPropagation();
        });
        $('#flip_cities1').find('a').eq(1).bind('click',function(event) {
        	input.val(inputVal);
        	input.focus();
        	if(pageId<pageCount){
        		sendKeyWordToBack1(pageId+1);
        	}
        	event.stopPropagation();
        });
    };
    init();
};
</script>
</html>	