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
<title>用户中心</title>
	<link rel="stylesheet" type="text/css" href="common/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="common/css/index.css"/>	
	<link rel="stylesheet" type="text/css" href="common/css/user.css"/>
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
<div class="content">
	<div class="left_list">		
		<div class="left_box2">
			<h4 class="t_h t_h2">账号设置</h4>			
			<ul>
				<li class="li1 active"><i></i><a>完善资料</a></li>
				<li class="li2"><i></i><a>修改密码</a></li>
				<li class="li3"><i></i><a>我的财富</a></li>
			</ul>			
		</div>
	</div>
	<!-- 完善资料 perfectdata -->
	<div class="right ractive">
		<form id="perfectdata_form" name="perfectdata_form" method="post">
		<div class="frm frm1">
			<!-- 注册修改之前界面-->
			<c:if test="${registCompanyType== 1}">
				<div class="cn">单位名称：<input type="text" class="companyNameUser" value="" name="companyInfo"/>
				<input type="hidden" value="" id="companyId" name="companyId"/>
					<span id="city_3">
	            		<select id="province" name="province" class="prov"></select><lect>
	            		<select id="city" name="city" class="city" disabled="disabled"></select><lect>
	            	</span>
				</div>
				
				<div style="padding-left: 115px;display:none;" id="subbranchDiv">
	        	 	<input class="" id="subbranch" name="subbranch" type="text" placeholder="请输入支行名称" onfocus="this.placeholder=''" onblur="this.placeholder='请输入支行名称'"/>
	        	</div>
	        	
	        	<div>
		             <!-- <div><span class="btn">找不到单位名称？</span></div> -->
		           	<div style="padding-right:190px;"><span class="btn">找不到单位名称？</span></div>
		            <div class="cn2">
		                <select name="companyType" id="companyType">
		                	<option value="2">银行</option>
		                    <option value="1">企业</option>
		                </select>
		            	<input type="text" class="companyName" value="" name="userEdit"/>
		            </div>
				</div>
			</c:if>
			<!-- 用户注册企业选择框-->
			<c:if test="${registCompanyType == 2}">
				<div class="cn">单位名称：<input type="text" class="companyNameUser" value="" name="companyInfo"/>
				<input type="hidden" value="" id="companyId" name="companyId"/>
					<!-- <select name="" id=""><option>北京</option></select>
					<select name="" id=""><option>北京</option></select> -->
					<span id="city_3">
	            		<select id="province" name="province" class="prov"></select><lect>
	            		<select id="city" name="city" class="city" disabled="disabled"></select><lect>
	            	</span>
				</div>
				<div style="padding-left: 115px;display:none;" id="subbranchDiv">
	        	 	<input class="" id="subbranch" name="subbranch" type="text" placeholder="请输入支行名称" onfocus="this.placeholder=''" onblur="this.placeholder='请输入支行名称'"/>
	        	</div>
			</c:if>
			<!-- 用户注册企业输入框-->
			<c:if test="${registCompanyType==3}">
	        	<div>
		            <div class="cn2">
		            	<span style="color:red;">* </span>单位名称：
		                <select name="companyType" id="companyType">
		                	<option value="2">银行</option>
		                    <option value="1">企业</option>
		                </select>
		            	<input type="text" class="companyName" value="" name="userEdit"/>
		            </div>
				</div>
			</c:if>
			<script type="text/javascript">
				if("${registCompanyType}"==1){
					$("#city_3").citySelect({prov:"${prov}", city:"${city}"});
					$(".companyNameUser").val("${userOtherInfo.companyInfo}");
					$("#companyId").val("${userOtherInfo.companyId}");
					if("${userOtherInfo.companyType}"==2){
						$("#subbranchDiv").show();//刚开始设置的是disply:none
						$("#subbranch").val("${userOtherInfo.userEdit}");
					}
				}
				if("${registCompanyType}"==2){
					$("#city_3").citySelect({prov:"${prov}", city:"${city}"});
					$(".companyNameUser").val("${userOtherInfo.companyInfo}");
					$("#companyId").val("${userOtherInfo.companyId}");
					if("${userOtherInfo.companyType}"==2){
						
						$("#subbranchDiv").show();//刚开始设置的是disply:none
						$("#subbranch").val("${userOtherInfo.userEdit}");
					}
				}
				if("${registCompanyType}"==3){
					if("${userOtherInfo.companyType}"==1){//如果是企业设置企业选中
						$("#companyType").find("option[value='1']").attr("selected",true);
					}
					$(".cn2").show();//刚开始设置的是disply:none
					$(".companyName").val("${userOtherInfo.userEdit}");
				}
				
				//$(".companyName").val("${userOtherInfo.userEdit}");
			</script>
			
			<div>邮箱：<input id="user_email" name="user_email" value="${user.userEmail}" readonly="readonly" style="color:#c1c1c1" class="old_ps" type="text"/><input  type="hidden" value=""/></div>
			<div>手机：<input id="userPhone" name="userPhone" value="${user.userPhone}" readonly="readonly" style="color:#c1c1c1" class="new_ps" type="text"/><input  type="hidden" value=""/></div>
			<div>姓名：<input id="user_name" name="user_name" value="${user.userName}" class="new_ps" type="text"/><input  type="hidden" value=""/></div>
			<div>岗位：<input id="station" name="station" value="${station}" class="new_ps" type="text"/><input  type="hidden" value=""/></div>
			<div>岗位职责：<input id="responsibility" name="responsibility" value="${responsibility}" class="new_ps" type="text"/><input  type="hidden" value=""/></div>
			<div>关注的行业：<input id="interest" name="interest" class="new_ps" value="${interest}" type="text"/><input  type="hidden" value=""/></div>
			<div id="text_tip1"></div>
			<a class="yes">保存</a>
		</div>
		</form>
	</div>
	
	<!-- 修改密码 editpassword -->
	<div class="right">
		<form id="editpassword_form" name="editpassword_form" method="post">
		<div class="frm frm2">
			<div>原密码：<input id="user_password_old" name="user_password_old" class="old_ps" type="password"/><input  type="hidden" value=""/></div>
			<div>新密码：<input id="user_password" name="user_password" class="new_ps" type="password"/><input  type="hidden" value=""/></div>
			<div>确认新密码：<input id="user_password2" name="user_password2" class="cf_ps" type="password"/><input  type="hidden" value=""/></div>
			<div id="text_tip"></div>
			<a class="yes">确认</a><a class="no">取消</a>
		</div>
		</form>
	</div>
	
	<!-- 我的财富 -->
	<div class="right">
		我的财富
	</div>	
</div>
<!-- footer -->
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
oSearchSuggest1($(".companyNameUser"),$(".cn"),
        "200","61",$(".ractive"),8,function(li){
	$("#companyId").val($(li).attr("mid"));
	
});
//账户设置点击找不到单位名称:
$("span.btn").click(function(){
    if($(".cn2").is(":hidden")){
    	$(".cn2").show();
    }else{
    	$(".cn2").hide();
    	$(".companyName").val(null);//设置选择框中的值为空,不再发送该数据
    }
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
        	inputVal=input.val();
        	flag=true;
        	sendKeyWordToBack1();
            event.stopPropagation();
        });
        input.bind('blur',
        function() {
        	 if(flag){
        		 input.val('');
        		 $("#companyId").val(null);//隐藏框中值设置为空\
        		 if("${registCompanyType}"!=3){
        			 $("#subbranchDiv").hide();
                	 $("#subbranch").val(null);//设置选择框中的值为空,不再发送该数据
        		 }
        		
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
        var newDiv =  "<div id='form_cities1'><ul id='panel_cities1'>";
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
            
            //if($(this).attr("bctype")==2){//选中的是银行
            	//$("#companyType").find("option[value='2']").attr("selected",true);
            //}
            if($(this).attr("bctype")==2){//选中的是银行
            	$("#subbranchDiv").show();
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