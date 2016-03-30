//ie8 placeholder  兼容代码
if( !('placeholder' in document.createElement('input')) ){   
    $('input[placeholder],textarea[placeholder]').each(function(){    
      var that = $(this),    
      text= that.attr('placeholder');    
      if(that.val()===""){    
        that.val(text).addClass('placeholder');    
      }    
      that.focus(function(){    
        if(that.val()===text){    
          that.val("").removeClass('placeholder');    
        }    
      })    
      .blur(function(){    
        if(that.val()===""){    
          that.val(text).addClass('placeholder');    
        }    
      })    
      .closest('form').submit(function(){    
        if(that.val() === text){    
          that.val('');    
        }    
      });    
    });    
  } 
//ie8 placeholder  兼容代码
var path = $("#path").val() || "/webword/";
//统一监听ajax请求 
$.ajaxSetup({   
	error: function (XMLHttpRequest, textStatus, errorThrown){  
		if(XMLHttpRequest.status==403){  
			return false;  
		}  
	},    
	complete:function(XMLHttpRequest,textStatus){
		var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头,sessionstatus，   
	    if(sessionstatus=='reload'){//如果超时就处理 ，指定要跳转的页面    
            window.location.href=path+"jsp/repeatLogin.jsp"; //跳转到他人登录页面   
	    }else if(sessionstatus=='timeout'){
	    	window.location.href=path+"view/index.do"; //跳转到他人登录页面   
	    }
	}
}); 

var searchType;
function liclick(self){
	searchType=self.id;
}
function closeDiv(){	
	$('.ui_back2,.ui_back,.box_content,.pop_div,.pop_div2').hide();
	$('.pop_div input[type!="button"][type!="radio"],textarea').val("");
	$('.pop_div label.placeholder').show();
	$(".pop_div .form_tips p").hide();
}
function showDiv(n){
	$('.pop_div,.form_tips p').hide();
	$('.pop_div').eq(n).find('input[type!="button"][type!="radio"]').val("");
	$('.pop_div').eq(n).find('label.placeholder').show();
	$('.ui_back2,.ui_back,.box_content').show();
	$('.pop_div').eq(n).show();	
}
//登录
function show_login(){closeDiv();$('.ui_back2,.ui_back,.box_content,.login_pop').show();}
//注册
function show_register(){closeDiv();$('.ui_back2,.ui_back,.box_content,.regist_pop').show();}
//忘记密码
function forget_password(){closeDiv();$('.ui_back2,.ui_back,.box_content,.pass_back_1').show();}
//拨打客服
function service_tel(){closeDiv();$('.ui_back2,.ui_back,.box_content,.pass_back_2').show();}
//退出
function show_exit(){closeDiv();$('.ui_back2,.ui_back,.box_content,.exit_pop').show();}
//删除   user-window  index=4
function show_user_dele(){$('.ui_back2,.ui_back,.box_content,.dele_pop').show();}
//审核不通过原因   user-window  index=3
function show_no_reason(){$('.ui_back2,.ui_back,.box_content,.no_reason').show();}
//保存成功   user-window index=2
function show_save_success(fn){
	$('.pop_div2').hide();
	$('.ui_back2,.ui_back,.box_content,.save_success').show();
	window.setTimeout(function(){hide_save_success(fn);}, 2000);
}
function hide_save_success(fn){$('.ui_back2,.ui_back,.box_content,.save_success').hide();fn()}
//保存失败     user-windowindex=1
function show_save_failure(){$('.ui_back2,.ui_back,.box_content,.save_failure').show();}
//我的插件  user-window index=5
function show_my_plug(){$('.ui_back2,.ui_back,.box_content,.my_plug').show();}
/*首页 top 个人中心和退出*/
function showHeadUl(){$('.header .bg .hideUl').show();}
function hideHeadUl(){$('.header .bg .hideUl').hide();}
$(function(){
	$(".pop_div .pop_closeBt").click(function(){		
		$('.ui_back2,.ui_back,.box_content,.pop_div').hide();
	});
	$(".pop_div2 .pop_closeBt").click(function(){		
		$('.ui_back2,.ui_back,.box_content').hide();	
		$(this).parent().hide();
	});
	$('.pop_div input[type!="button"]').focus(function(){		
		$(this).parent().find('label.placeholder').hide();
	});
	$('.pop_div input[type!="button"]').blur(function(){	
		if($(this).val()==''){$(this).parent().find('label.placeholder').show();}
	});
	$('.edit_ input[type!="button"]').focus(function(){	
		$(this).parent().find('label.placeholder').hide();
	});
	$('.edit_ input[type!="button"]').blur(function(){	
		if($(this).val()==''){$(this).parent().find('label.placeholder').show();}
	});	
	$('.pop_div input[type!="button"]').each(function(){
		if($(this).val()==''){$(this).parent().find('label.placeholder').show();
		}else{
			$(this).parent().find('label.placeholder').hide();
		}	
	});
	$('.edit_ input[type!="button"]').each(function(){
		if($(this).val()==''){$(this).parent().find('label.placeholder').show();
		}else{
			$(this).parent().find('label.placeholder').hide();
		}	
	});
});
$('.header .bg').mouseleave(function(){hideHeadUl();});
function tipsShow(input_name,str){input_name.find(".form_tips p").show().html(str);}
function tipsHide(input_name){ input_name.find(".form_tips p").hide();}
function butOnClick(e,fun){
	if(e.keyCode==13){
		eval(fun);
	}
}
var reg_phone = /^(((13[0-9]{1})|(15[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //验证手机格式
var reg_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;//验证邮箱
var reg_name = /^[a-zA-Z0-9_\-\u4e00-\u9fa5]*$/;//昵称必须4-12个字符（一个汉字=2个字符），仅支持汉字、数字、英文、“-”、“_”!
//登录
function check_login(){
	var account=$.trim($('#username_1').val());
	var pass=$.trim($('#password_1').val());
	if(account==''){
		tipsShow($('.login_pop'), '请填写手机或邮箱!');
		$('#username_1').next('label.placeholder').show();
		return false;
	}else if(pass==''){
		tipsShow($('.login_pop'), '请填写密码!');
		$('#password_1').next('label.placeholder').show();
		return false;
	}
	$.ajax({
        type: "post",
        url: "view/login.do",
        data:{account:account,pass:pass},
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data && data.result==0){
        		tipsShow($('.login_pop'), data.msg);
        	}else{
    			window.location.reload();
        	}
        }
    });
}
//退出
function logout(){
	$.ajax({
        type: "post",
        url: "view/logout.do",
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data==1){
        		window.location.reload();
        	}
        }
    });
}

//验证手机号是否注册
function validEmailorPhone(account,callback){
	var flag = false;
	$.ajax({
        type: "post",
        url: "view/validEmailorPhone.do",
        data:{account:account},
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data && data.result==0){
        		if(callback){
        			callback(data);
        		}else{
        			tipsShow($('.regist_pop'), data.msg);
        		}
        	}else{
        		flag = true;
        	}
        }
    });
	return flag;
}
$("#username_1").blur(function(){
	var username=$.trim($('#username_1').val());
	if(username!="" && !reg_email.test(username) && !reg_phone.test(username)){
		tipsShow($('.login_pop'), '邮箱或手机号格式错误!');return false;
	}
	if(username!="" && (reg_email.test(username) || reg_phone.test(username))){
		var f = validEmailorPhone(username,function(){});
		if(f)
			tipsShow($('.login_pop'), '账号不存在!');return false;
	}
}).focus(function(){
	tipsHide($('.login_pop'));
});
$("#email_2").blur(function(){
	var email=$.trim($('#email_2').val());
	if(email!="" && !reg_email.test(email)){
		tipsShow($('.regist_pop'), '邮箱格式错误!');return false;
	}
	if(email!="" && reg_email.test(email)){
		var f = validEmailorPhone(email);
		if(f)
			tipsHide($('.regist_pop'));
	}
});
$("#phone_2").blur(function(){
	var phone=$.trim($('#phone_2').val());
	if(phone!="" && !reg_phone.test(phone)){
		tipsShow($('.regist_pop'), '手机号格式错误!');return false;
	}
});
$("#password_2").blur(function(){
	var pass=$.trim($('#password_2').val());
	if(pass!="" && pass.length < 6 || pass.length > 30){
		tipsShow($('.regist_pop'), '密码长度为6~30个字符!');return false;
	}
});
//注册
function check_regist(){
	var email=$.trim($('#email_2').val());
	var pass=$.trim($('#password_2').val());
	var phone = $.trim($("#phone_2").val());
	var checkMsg = $.trim($("#phone_check").val());
	if(email==''){
		tipsShow($('.regist_pop'), '请填写邮箱地址!');return false;
		$('#email_2').next('label.placeholder').show();
	}else if(!reg_email.test(email)){
		tipsShow($('.regist_pop'), '邮箱格式错误!');return false;
	}else if(phone==""){
		tipsShow($('.regist_pop'), '请填写手机号!');return false;
	}else if(phone!="" && !reg_phone.test(phone)){
		tipsShow($('.regist_pop'), '手机号格式错误!');return false;
	}else if(pass==''){
		tipsShow($('.regist_pop'), '请填写密码!');return false;
		$('#password_2').next('label.placeholder').show();
	}else if(pass.length < 6 || pass.length > 30){
		tipsShow($('.regist_pop'), '密码长度为6~30个字符!');return false;
	}else if(checkMsg.length<6){
		tipsShow($('.regist_pop'), '手机验证码格式不对!');return false;
	}
	$.ajax({
        type: "post",
        url: "view/regist.do",
        data:{userAccount:email,userEmail:email,userPassword:pass,userPhone:phone,checkMsg:checkMsg},
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data && data.result==0){
        		tipsShow($('.regist_pop'), data.msg);
        	}else{
    			window.location.reload();
        	}
        }
    });
}
function get_pass_1(){
	var email=$.trim($('#email_3').val());
	if(email==''){
		tipsShow($('.pass_back_1'), '请填写邮箱地址!');
		$('#email_3').next('label.placeholder').show();
	}else if(!reg_email.test(email)){
		tipsShow($('.pass_back_1'), '邮箱格式错误!');
	}else{
		tipsHide($('.pass_back_1'));
		showDiv(3);//20160122
	}	
}
function get_pass_3(){
	var pass_1=$.trim($('#pass_new_4').val());
	var pass_2=$.trim($('#pass_sure_4').val());
	var yanzheng=$.trim($('#yanzheng_4').val());
	var str='1234';
	if(pass_1==''){
		tipsShow($('.pass_back_3'), '请填写新密码!');
	}else if(pass_1.length < 6 || pass_1.length > 30){
		tipsShow($('.pass_back_3'), '密码长度为6~30个字符!');
	}else if(pass_2==''){
		tipsShow($('.pass_back_3'), '请重复输入新密码!');
	}else if(pass_2!=pass_1){
		tipsShow($('.pass_back_3'), '两次密码不一致!');
	}else if(yanzheng==''){
		tipsShow($('.pass_back_3'), '请输入验证码!');
	}else if(yanzheng!=str){
		tipsShow($('.pass_back_3'), '验证码错误!');
	}else{
		tipsHide($('.pass_back_3'));
		showDiv(5);//20160122
	}	
}
$("#new_pass").focus(function(){
	$('.edit_ .form_tips .redTip').eq(0).hide();
});
$("#new_pass").blur(function(){
	var pass_1=$.trim($('#old_pass').val());
	var pass_2=$.trim($('#new_pass').val());
	if(pass_2!=""){
		if(pass_2.length < 6 || pass_2.length > 30){
			$('.edit_ .form_tips .redTip').eq(1).show().html('密码长度为6~30个字符!');
			return;
		}else if(pass_2==pass_1){
			$('.edit_ .form_tips .redTip').eq(1).show().html('新密码不能与原密码相同!');
			return;
		}
	}
}).focus(function(){
	$('.edit_ .form_tips .redTip').eq(1).hide();
});
$("#sure_pass").blur(function(){
	var pass_2=$.trim($('#new_pass').val());
	var pass_3=$.trim($('#sure_pass').val());
	if(pass_2!="" && pass_3!="" && pass_3!=pass_2){
		$('.edit_ .form_tips .redTip').eq(2).show().html('两次输入的密码不一致!');
		return;
	}
}).focus(function(){
	$('.edit_ .form_tips .redTip').eq(2).hide();
});
//个人中心，修改密码
function change_pass(old_pass){
	var pass_1=$.trim($('#old_pass').val());
	var pass_2=$.trim($('#new_pass').val());
	var pass_3=$.trim($('#sure_pass').val());
	if(pass_1==''){
		$('.edit_ .form_tips .redTip').eq(0).show().html('请填写原密码!');
		return;
	}else{
		$('.edit_ .form_tips .redTip').eq(0).hide();
	}
	
	if(pass_2==''){
		$('.edit_ .form_tips .redTip').eq(1).show().html('请填写新密码!');
		return;
	}else if(pass_2.length < 6 || pass_2.length > 30){
		$('.edit_ .form_tips .redTip').eq(1).show().html('密码长度为6~30个字符!');
		return;
	}else if(pass_2==pass_1){
		$('.edit_ .form_tips .redTip').eq(1).show().html('新密码不能与原密码相同!');
		return;
	}else{
		$('.edit_ .form_tips .redTip').eq(1).hide();
	}
	
	if(pass_3==''){
		$('.edit_ .form_tips .redTip').eq(2).show().html('请重复输入新密码!');
		return;
	}else if(pass_3!=pass_2){
		$('.edit_ .form_tips .redTip').eq(2).show().html('两次输入的密码不一致!');
		return;
	}else{
		$('.edit_ .form_tips .redTip').eq(2).hide();
	}
	$.ajax({
        type: "post",
        url: "user/passSubmit.do",
        data:{oldPass:pass_1,pass:pass_2},
        dataType:"json",
        async:false,
        success: function(data) {
        	if(data && data.result==1){
        		$('.edit_ .form_tips .redTip').hide();
        		show_save_success();//20160122
        		$('.save_success p').text('修改成功！');
        	    reset_pass();
        	}else{
        		$('.edit_ .form_tips .redTip').hide();
        		$('.edit_ .form_tips .redTip').eq(0).show().html(data.msg);
        	}
        }
    });
}
//取消
function reset_pass(){
	$("#old_pass,#new_pass,#sure_pass").val("");
	tipsHide($('.userR_form'));
}

function isLogin(){
	var f = false;
	$.ajax({
        type: "post",
        url: "view/islogin.do",
        async:false,
        contentType: "application/x-www-form-urlencoded; charset=utf-8", 
        success: function(data) {
        	if(!data || data!=1){
        		//showDiv(1);
        		show_login();//20120122
        	}else{
        		f = true;
        	}
        }
    });
	return f;
}
function limitWord(Wclass,Wlength){
	$('.userRight .news_ul li div'+Wclass).each(function(){
		var titLength = $(this).find("a.a1").text().length;		
		if(titLength>Wlength){
			var str=$(this).find("a.a1").text().substring(0,Wlength);
			$(this).find("a.a1").text(str+'...');
		}
	});
}
limitWord('.w450',22);limitWord('.w680',28);limitWord('.w380',22);
/*
$('.userLeft h2').click(function(){			
	if($(this).next('ul').is(':visible')) {
		$(this).find('em').removeClass('icon2').addClass('icon1');
		$(this).next('ul').stop(true,false).hide();			
	}else{
		$(this).find('em').removeClass('icon1').addClass('icon2');
		$(this).next('ul').stop(true,false).show();				
	}
});*/
$(function(){
	$(".userLeft ul li[class!='current']").hover(
		function(){	$(this).addClass('hover');},
		function(){	$(this).removeClass('hover');}
	);
	
	$("input.allCheck").click(function(){	
		if($(this).attr('checked')=='checked'){
			$(this).attr('checked','checked');
			$(this).parent().parent().find('.news_ul li:visible').find("input[type='checkbox']:not(:disabled)").attr('checked','checked');
		}else{
			//alert($(this).attr('checked'));
			$(this).removeAttr('checked');
			$(this).parent().parent().find('.news_ul li:visible').find("input[type='checkbox']").removeAttr('checked');		
		}
	});
	
	$('.userRight .news_ul li div').hover(
		function(){	$(this).find("a.a2,a.a3").show();},
		function(){	$(this).find("a.a2,a.a3").hide();
	});
	/*搜索框 文档和模板，选中样式*/
	$(".header2 .li1 a").click(function(){
		var $index=$('.header2 .li1 a').index(this);	
		$(this).addClass("current").siblings().removeClass("current");
		$("#type").val($(this).attr("name"));
		$("#articleFormat").val($("input[name='articleFormat']:checked").val());
		//如果搜索过了
		if(window.location.href.indexOf("search")>-1){
			var keyword = encodeURI($.trim($(".search_input").val()));
			var searchType=
			window.location = path+"view/search.do?keyword="+keyword+"&searchType="+searchType+"&articleFormat="+$("input[name='articleFormat']:checked").val();
		}
	});
	//搜索输入框
	$(".header2 .search_input").keydown(function(e){
		if(e.keyCode==13){
			var keyword = encodeURI($.trim($(".search_input").val()));
			var el = document.getElementsByClassName('search_input')[0];
			var placeholder = encodeURI((el.getAttribute("placeholder")));
			var searchNews =$("#searchNews").val();
			
			if(searchNews=="news"){
				if (keyword && keyword != placeholder) {
					window.location = path
							+ "news/view/search.do?keyword="
							+ keyword;
							
				} else {
					window.location = path + "news/view/index.do";
				}
				
			}else{
			
			if(keyword&&keyword!=placeholder){
				window.location = path+"view/search.do?keyword="+keyword+"&searchType="+searchType+"&articleFormat="+$("input[name='articleFormat']:checked").val();
			}else{
				window.location = path+"view/home.do";
			}
			}
		}
	});
	//搜索按钮
	$(".header2 .search").click(function(){
		var keyword = encodeURI($.trim($(".search_input").val()));
		var el = document.getElementsByClassName('search_input')[0];
		var placeholder = encodeURI((el.getAttribute("placeholder")));
		var searchNews =$("#searchNews").val();
		if(searchNews=="news"){
			if (keyword && keyword != placeholder) {
				window.location = path
						+ "news/view/search.do?keyword="
						+ keyword
						;
			} else {
				window.location = path + "news/view/index.do";
			}
			
		}else{
		if(keyword&&keyword!=placeholder){
			//searchType为全文或标题检索。articleFormat是文档类型
			$(this).attr("href","view/search.do?keyword="+keyword+"&searchType="+searchType+"&articleFormat="+$("input[name='articleFormat']:checked").val());
		}else{
			window.location = path+"view/home.do";
		}}
	});
	
	/*一级菜单 滑过出现二级菜单*/
	(function(){
		$(".menu .second_menu li:gt(7)").remove();
		if($(".menu .menu_bg a.a1").length>8){			
			$(".menu .menu_bg a.a1:gt(7)").remove();
		};
	})();
	$(".menu .menu_bg a:not(:first-child)").mouseover(function(){		
		var $index=$('.menu .menu_bg a').index(this)-1;	
		var str = $(".menu .second_menu li p").eq($index).find('a').length;		
		$(".menu .second_menu li p").hide();
		if($index < $(".menu .menu_bg a").length-1 && str > 0){				
			$(".menu .second_menu li p").eq($index).show(); 					
		}
	});
	$(".menu").mouseleave(function(){
		$(".menu .second_menu li p").hide();
	});
	$(".menu .menu_bg a:first-child").mouseover(function(){
		$(".menu .second_menu li p").hide();
	}); 
	/*栏目页》新建栏目》  标签和关键词切换*/
	$(".add_channel .tabul li").click(function(){
		var $index=$('.add_channel .tabul li').index(this);	
		$(this).addClass("current").siblings().removeClass("current");
		$(".add_channel .tabBox .tabDiv").eq($index).show().siblings().hide(); 
	});
	/*栏目页》查看栏目设置》  标签和关键词切换*/
	$(".modifi_channel .tabul li").click(function(){
		var $index=$('.modifi_channel .tabul li').index(this);	
		$(this).addClass("current").siblings().removeClass("current");
		$(".modifi_channel .tabBox .tabDiv").eq($index).show().siblings().hide(); 
	});
});
/*重置密码*/
function get_pass_4(){	
	var pass_1=$.trim($('#pass_new_4').val());
	var pass_2=$.trim($('#pass_sure_4').val());
	var yanzheng=$.trim($('#yanzheng_4').val());
	var str='1234';	
	if(pass_1==''){
		$('#resetTip p').html("请填写新密码!").show();		
	}else if(pass_1.length < 6 || pass_1.length > 30){
		$('#resetTip p').html("密码长度为6~30个字符!").show();		
	}else if(pass_2==''){
		$('#resetTip p').html("请重复输入新密码!").show();		
	}else if(pass_2!=pass_1){
		$('#resetTip p').html("两次密码不一致!").show();		
	}else if(yanzheng==''){		
		$('#resetTip p').html("请输入验证码!").show();
	}else if(yanzheng!=str){
		$('#resetTip p').html("验证码错误!").show();		
	}else{
		$('#resetTip p').hide();	
	}	
}
/*我的文库》插件》插件详情弹出*/
function closePlugDiv(){$('.ui_back2,.ui_back,.box_content,.pre_view_plugin').hide();	}
function showPlugDiv(id,name){	
	$.ajax({
        type: "post",
        url: "plugin/getPlugin.do?pluginId="+id,
        async:false,
        contentType: "application/x-www-form-urlencoded; charset=utf-8", 
        success: function(data) {
        	if(data && data.list){
        		if(name.length>24){
        			name=name.substring(0,24)+'...';
        		}        		
        		$(".pre_view_plugin h3").text(name);
            	$(".pre_view_plugin .sb_middle div").html(data.list.pluginIntro);
            	$('.ui_back2,.ui_back,.box_content,.pre_view_plugin').show();	
        	}else{
        		new altInfo({
					text:"查看失败！"
				});
        	}
        }
    });
}
/*
 * 发送检验的短信
 * 
 * */
function sendCheckMsg(obj){
	var phone=$.trim($('#phone_2').val());
	if(phone=="" || !reg_phone.test(phone)){
		return false;
	}
	if(!validEmailorPhone(phone)) return;
	$.ajax({
        type: "post",
        url: "view/sendCheckMsg.do",
        data:{userPhone:$.trim($("#phone_2").val()),userType:"0"},
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data.status!="success"){
        		tipsShow($('.regist_pop'), data.info);
        	}else{
        		tipsShow($('.regist_pop'), "验证码已发送");
        		var i=60;
        		$(".regist_pop .getTelYan").attr("value",i+'秒后重新获取');
        		$('.regist_pop .getTelYan').attr('disabled','true');//让按钮不可点击，否则，会一直从60秒不停的倒计时
        		 timer=setInterval(function(){
        			i--;
        			$(".regist_pop .getTelYan").attr("value",i+'秒后重新获取');
        			if(i<0){
        				$(".regist_pop .getTelYan").attr("value",'重新获取');
        				$('.regist_pop .getTelYan').removeAttr("disabled");//让按钮恢复点击
        				clearInterval(timer);
        			}
        		 },1000);
        		tipsShow($('.regist_pop'), '');
        	}
        }
    });
}

/**/
$(function(){
	/*首页index  按全文/标题搜索*/
	$('.header2 .ul .li3 span,.header2 .ul .li3 em').click(function(event){		
		$('.header2 .hideUl').show();		
		event.stopPropagation();
	});
	$('.header2 .hideUl li').click(function(){
		var str=$(this).text();
		$('.header2 .ul .li3 span').text(str);
		$(this).parent().hide();
	});
	$('.header2 .hideUl').mouseleave(function(){
		$('.header2 .hideUl').hide();
	});
	$(document).click(function(){
		$('.header2 .hideUl').hide();
	}); 
	/*个人中心-编辑资料*/
	$('.edit_ .userR_form .li_1 .modify').click(function(){
		$('.edit_ .userR_form .li_1').hide();
		$('.edit_ .userR_form .li_2,.edit_ .userR_form .li_3,.edit_ .userR_form .last').show();	
	});
	$('.edit_ .userR_form .li_4 .modify').click(function(){
		$('.edit_ .userR_form .li_4').hide();
		$('.edit_ .userR_form .li_5,.edit_ .userR_form .li_6,.edit_ .userR_form .last').show();	
	});
	$('.edit_ .userR_form .last .cancel').click(function(){	
		$('.edit_ .userR_form .last,.edit_ .form_tips .redTip').hide();
		if($('.edit_ .userR_form .li_2').is(":visible") && $('.edit_ .userR_form .li_5').is(":hidden")){		
			$('.edit_ .userR_form .li_1').show();
			$('.edit_ .userR_form .li_2,.edit_ .userR_form .li_3').hide();
		}else if($('.edit_ .userR_form .li_2').is(":hidden") && $('.edit_ .userR_form .li_5').is(":visible")){	
			$('.edit_ .userR_form .li_4').show();
			$('.edit_ .userR_form .li_5,.edit_ .userR_form .li_6').hide();
		}else if($('.edit_ .userR_form .li_2').is(":visible") && $('.edit_ .userR_form .li_5').is(":visible")){		
			$('.edit_ .userR_form .li_1,.edit_ .userR_form .li_4').show();
			$('.edit_ .userR_form .li_2,.edit_ .userR_form .li_3,.edit_ .userR_form .li_5,.edit_ .userR_form .li_6').hide();
		}else{	return;}		
	});
	/*栏目二级页-*/
	/*推荐，对钩和加号，切换*/
//	$('.lanmuTuijian .list_ul2 li .span2').click(function(){		
//		if($(this).hasClass('finish_1')){ $(this).removeClass("finish_1").addClass("add_1");
//		}else{	$(this).removeClass("add_1").addClass("finish_1");}	
//	});
	/*栏目二级页-推荐，查看更多*/
	(function(){
		$('.lanmuTuijian').children('div').each(function(){
			if($(this).find('.list_ul2 li').length>15){
				$(this).find('.more').show();
				$(this).find('.list_ul2 li:gt(15)').hide();
			}			
		});	
		$('.lanmuTuijian .list_ul2 li .span2').each(function(){
			if($(this).hasClass('finish_1')){	$(this).show();	}			
		});
	})();	
	$('.lanmuTuijian .more').click(function(){	
		if($(this).prev().find('li').length>16){
			var $hideD=$(this).prev().find('li:gt(15)');		
			if($hideD.is(":visible")){	$(this).html('<em></em>展开更多');$hideD.hide();
			}else{					
				$(this).html('<em></em>收起');$hideD.show();
			}
		}		
	});
	/*栏目二级页-期刊/非期刊切换*/
//	$('.userCont .listBody .list_tab_tit li').click(function(){		
//		$(this).addClass("current").siblings().removeClass("current");
//		var $index=$('.userCont .listBody  .list_tab_tit li').index(this);   
//	        $('.userCont .list_box ul').eq($index).show().siblings().hide();     
//	});
	
//	/*添加栏目，联想框*/
//	function oSearchSuggest(input,suggestWrap,cLeft,cTop,closeB, pageSize){		
//		var keyword = "";		
//		var init = function(){		
//			input.bind('click',function(event){	sendKeyWordToBack();event.stopPropagation();});		
//			input.bind('keyup',sendKeyWordToBack);
//			if($('.submit_audit').length>0){
//				input.bind('blur',function(){ input.val(''); });
//			}						
//			closeB.bind('click', function(){ $('#form_cities').remove();});			
//		};	
//		var sendKeyWordToBack = function(){											
//			var dataDisplay = function(data){	
//				console.log(data);
//				if(data.length<=0){	return;	}	
//				$('#form_cities').remove();	
//				var newDiv="<div id='form_cities'><ul id='panel_cities'>";					
//				for(var i=0; i<data.length; i++){					
//					newDiv+="<li>"+data[i]+"</li>";					
//				}				
//				newDiv+="</ul><div id='flip_cities'><a href='javascript:;'>«&nbsp;前一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a	href='javascript:;'>后一页&nbsp;»</a></div></div>";
//				suggestWrap.append(newDiv);	
//				$('#form_cities').show().css({
//					'left':cLeft+'px',
//					'top':cTop+'px'
//				});
//				$('#form_cities').find('li').mouseover(function(){ $(this).addClass('hover').siblings().removeClass('hover'); });
//				$('#form_cities').find('li').bind('click', function(){ input.val($(this).html());	$('#form_cities').remove();	});	
//				//$('#form_cities').mouseleave(function(){	$('#form_cities').remove();	});
//			};
//			var valText = $.trim(input.val());
//			if(valText ==''){	keyword = '北京地铁线路';	}else{	keyword = valText;	}
//			var aData = [];
//			aData.push(keyword+'1');
//			aData.push(keyword+'2');
//			aData.push(keyword+'3');
//			aData.push(keyword+'4');
//			aData.push(keyword+'5');			
//			dataDisplay(aData);	
//			/*
//			 $.ajax({
//				   type: "POST",
//				   url: "${ctx}/front/suqiu2/search/prompt-keyword.action",
//				   async:false,
//				   data: obj,
//				   dataType: "json",
//				   success: function(data){
//					 //var json = eval("("+data+")");
//					 var key=data.split(",");
//					 var aData = [];
//					 for(var i=0;i<key.length;i++){
//							//以下为根据输入返回搜索结果的模拟效果代码,实际数据由后台返回
//						if(key[i]!=""){
//							  aData.push(key[i]);
//						}
//					 }
//					//将返回的数据传递给实现搜索输入框的输入提示js类
//					 ss.dataDisplay(aData);
//				   }
//	 		});*/
//		};			
//		init();
//	};
//	if($('.submit_audit').length>0){
//		//我的上传，我的审核，联想框
//		var ss =  new oSearchSuggest($('.submit_audit .tabDivBg #qikanName'),$('.submit_audit .sb_middle'),'95','108',$('.submit_audit'));	
//	}	
	if($('.add_channel').length>0){
		//我的栏目，新建栏目，联想框>全部标签
		var ss2 =  new oSearchSuggest($('.add_channel .sb_middle input#mustTagNames'),$('.add_channel .sb_middle'),'171','122',$('.add_channel'),5,1);
		//我的栏目，新建栏目，联想框>任意标签
		var ss3 =  new oSearchSuggest($('.add_channel .sb_middle input#shouldTagNames'),$('.add_channel .sb_middle'),'171','160',$('.add_channel'),5,1);
		//我的栏目，新建栏目，联想框>不包含标签
		var ss3 =  new oSearchSuggest($('.add_channel .sb_middle input#mustNotTagNames'),$('.add_channel .sb_middle'),'171','197',$('.add_channel'),5,1);
		//我的栏目，新建栏目，联想框>全部关键词
		var ss4 =  new oSearchSuggest($('.add_channel .sb_middle input#mustWordNames'),$('.add_channel .sb_middle'),'171','122',$('.add_channel'),5,2);
		//我的栏目，新建栏目，联想框>任意关键词
		var ss5 =  new oSearchSuggest($('.add_channel .sb_middle input#shouldWordNames'),$('.add_channel .sb_middle'),'171','160',$('.add_channel'),5,2);
		//我的栏目，新建栏目，联想框>不包含关键词
		var ss6 =  new oSearchSuggest($('.add_channel .sb_middle input#mustNotWordNames'),$('.add_channel .sb_middle'),'171','197',$('.add_channel'),5,2);
	}
//	if($('.modifi_channel').length>0){
//		//我的栏目，查看栏目，联想框>全部标签
//		var ss7 =  new oSearchSuggest($('.modifi_channel .sb_middle input#mustTagNames'),$('.modifi_channel .sb_middle'),'164','112',$('.modifi_channel'));
//		//我的栏目，查看栏目，联想框>任意标签
//		var ss8 =  new oSearchSuggest($('.modifi_channel .sb_middle input#shouldTagNames'),$('.modifi_channel .sb_middle'),'164','150',$('.modifi_channel'));
//		//我的栏目，查看栏目，联想框>不包含标签
//		var ss9 =  new oSearchSuggest($('.modifi_channel .sb_middle input#mustNotTagNames'),$('.modifi_channel .sb_middle'),'164','187',$('.modifi_channel'));
//		//我的栏目，查看栏目，联想框>全部关键词
//		var ss10 =  new oSearchSuggest($('.modifi_channel .sb_middle input#mustWordNames'),$('.modifi_channel .sb_middle'),'164','112',$('.modifi_channel'));
//		//我的栏目，查看栏目，联想框>任意关键词
//		var ss11 =  new oSearchSuggest($('.modifi_channel .sb_middle input#shouldWordNames'),$('.modifi_channel .sb_middle'),'164','150',$('.modifi_channel'));
//		//我的栏目，查看栏目，联想框>不包含关键词
//		var ss12 =  new oSearchSuggest($('.modifi_channel .sb_middle input#mustNotWordNames'),$('.modifi_channel .sb_middle'),'164','187',$('.modifi_channel'));
//	}
//		
	/*标题-截字*/
	if($('.qikanDetail #old_Detail').length>0){qikanDetail_('true');}
	if($('.previBody .reader-side .reader-side-1').length>0){Previ_Brief_word();}
});
/*二级栏目 》新建栏目弹出层*/
function showCre_Channel(){$('.ui_back2,.ui_back,.box_content,.add_channel').show();buttonClear();}
function closeCre_Channel(){$('.ui_back2,.ui_back,.box_content,.add_channel').hide();}
/*二级栏目 》查看栏目设置弹出*/
function closeModi_Channel(){$('.ui_back2,.ui_back,.box_content,.modifi_channel').hide();}
function showModi_Channel(){ $('.ui_back2,.ui_back,.box_content,.modifi_channel').show();}


/*个人中心-我的上传-提交审核弹层中的，radio切换*/
$(".submit_audit .sb_middle #TypeRadio2").click(function(){		
	$('.submit_audit .tabDivBg .qikan_h2').hide();  $("#upload_type_id,#upload_journal_date,#upload_qikan_name,#articleJournalId,#articleJournalName,#articlePrice").val("")	});
$(".submit_audit .sb_middle #TypeRadio1").click(function(){		
	$('.submit_audit .tabDivBg .qikan_h2').show();  	});
/*个人中心-我的期刊-期刊详情*/
//期刊详情，文字默认显示130
function getDetail(){
	if($('.qikanDetail #old_Detail').length>0){
		var detail=$('.qikanDetail #old_Detail .brief p').html();return detail;
	};
}	
var detail_all=getDetail();
function qikanDetail_(flag){
	var str=detail_all.substring(0,125);	
	if(flag=='true'&& detail_all.length>125){		
		$('.qikanDetail #old_Detail .brief p').html(str+'...');
		$('#old_Detail .brief .btn').show();				
	}else{
		$('.qikanDetail #old_Detail .brief p').html(detail_all);
	}		
};
if($('.qikanDetail #old_Detail').length>0){	qikanDetail_('true');}
$('#old_Detail .brief .btn').click(function(){	
	var str=$(this).text();	
	if(str=='详情'){	qikanDetail_('false');$(this).text('收起');
	}else{	qikanDetail_('true');$(this).text('详情');}	
});
//期刊详情，显示返回按钮
function qikan_BackBtn(){$('.qikanSearch .qikanSea_h2 span').show();return false;};
//期刊详情，退出修改
function qikanDetail_edit(){	
	$('.qikanDetail h2 .modify,.qikanDetail #old_Detail').hide();
	$('.qikanDetail h2 .submit,.qikanDetail h2 .cancel,.qikanDetail #modi_Detail').show();}
function qikanDetail_exitEdit(){
	$('.qikanDetail h2 .submit,.qikanDetail h2 .cancel,.qikanDetail #modi_Detail').hide();
	$('.qikanDetail h2 .modify,.qikanDetail #old_Detail').show();
}
/*预览页，简介弹出层*/
function Previ_Brief_word(){	
	var str=$('.previBody .reader-side .reader-side-1 p.p_brief').text();
	str=str.replace(/\s/g,"");
	if(str.length>70){	
		$('.previBody .reader-side .reader-side-1 p.p_brief').html(str.substring(0,70)+'&nbsp;&nbsp;...');	
		$('.previBody .reader-side .reader-side-1 a').show();
	}	
};

function closePreviBrief(){ $('.ui_back2,.ui_back,.box_content,.previ_brief').hide();}
function showPreviBrief(){ $('.ui_back2,.ui_back,.box_content,.previ_brief').show();}

/*新闻预览页*/
/*一级栏目页*/
//fixMenu 两种状态切换
$(window).scroll(function(event) {	
	var scrollHeight = $(document).height()-680; //浏览器当前窗口文档的高度 27388   减去底部和头部的高度600	
	if( $(window).scrollTop() > 200 && $(window).scrollTop() < scrollHeight) {		
			$(".fixMenuBox").show();
	}else{ $(".fixMenuBox").hide();}	
});
$('.fixMenuColumn  .closeBt').click(function(){	 $('.fixMenuColumn').hide();  $('.fixMenu_hide').show();});
$('.fixMenu_hide').click(function(){	 $('.fixMenuColumn').show();  $('.fixMenu_hide').hide();});

//添加栏目，预览弹出层
//$(".add_channel .sb_button a.a5,.modifi_channel .sb_button a.a5").click(function(){
//	$(".previ_channel").show().css({
//		"position":"absolute",
//		"top":"10px",
//		"left":"50%",
//		"margin":"0 0 0 -250px"
//	});
//});
/*添加栏目，预览弹出层 > 期刊/非期刊切换*/
$('.previ_channel .listBody .list_tab_tit li').click(function(){		
	$(this).addClass("current").siblings().removeClass("current");
	var $index=$('.previ_channel .listBody  .list_tab_tit li').index(this);   
        $('.previ_channel .list_box ul').eq($index).show().siblings().hide();     
});
/*添加栏目，预览弹出层 > 订阅，取消订阅切换*/
//$('.previ_channel .sb_button .subscribe').click(function(){		
//	if($(this).hasClass('sub1')){ $(this).removeClass("sub1").addClass("sub2");
//	}else{	$(this).removeClass("sub2").addClass("sub1");}	   
//});
/*添加栏目，预览弹出层 > 删除按钮切换*/
$('.previ_channel .list_box .list_qikan li .dele').click(function(){	
	if($(this).hasClass('dele_1')){ $(this).removeClass("dele_1").addClass("add_1");
	}else{	$(this).removeClass("add_1").addClass("dele_1");}	   
});
///*添加栏目，预览弹出层 > 置顶效果*/
//$('.previ_channel .list_box .list_qikan li .setTop').click(function(){	
//	//$('.previ_channel .list_box .list_qikan li .setTop').text('置顶');
//	alert(11);
//	$(this).text('取消置顶');
//	$(this).parents('li').remove().prependTo('.previ_channel .list_box .list_qikan');
//	$(this).parents('li').find('.dele').bind('click', function(){
//		if($(this).hasClass('dele_1')){ $(this).removeClass("dele_1").addClass("add_1");
//		}else{	$(this).removeClass("add_1").addClass("dele_1");}	 
//	});
//});
/*添加栏目，预览弹出层 > 关闭弹出*/
$('.previ_channel .pop_closeBt_s,.previ_channel .sb_button a.a1,.previ_channel .sb_button a.a2').click(function(){	
	$('.previ_channel .list_tab_tit li').eq(0).addClass("current").siblings().removeClass("current");//20151110 tab初始化
	$('.previ_channel .list_box ul').eq(0).show().siblings().hide();//20151110 tab初始化
	$('.previ_channel').hide(); 
});
$('.previ_channel .sb_button .sub1,.previ_channel .sb_button a.12').click(function(){	
	$('.ui_back2,.ui_back,.box_content,.modifi_channel,.previ_channel').hide();
	$('.previ_channel .list_tab_tit li').eq(0).addClass("current").siblings().removeClass("current");//20151110 tab初始化
	$('.previ_channel .list_box ul').eq(0).show().siblings().hide();//20151110 tab初始化
});

//首页，最新专区，time 》效果
function slipeUp(obj,H_NEW,T_NEW,H_OLD,T_OLD){
	if(obj.length>0){
		obj.children('li').hover(
				function(){
					$(this).find('p.time').animate({ 	   
				    lineHeight: H_NEW ,
				    top:T_NEW
					  }, "fast" );
				},function(){
					$(this).find('p.time').animate({ 	   
					    lineHeight: H_OLD,
					    top:T_OLD
						  }, "fast");
				}
			);
	}
	
}
slipeUp($('.body_div2_b .lastArea'),"44px","150px","24px" ,"170px");
slipeUp($('.listBody .list_box .list_qikan'),"40px","142px","20px" ,"162px");
slipeUp($('.user_resource .lastArea'),"40px","115px","20px" ,"132px");
//首页，ie8不支持css3选择器，错位
function ind_ie8(){
	$('.body_div1_b .bd_bg div:last-child').css('width','270px');
	$('.body_div1_b .bd_bg div:last-child i').css('display','none');
	$('.body_div2_b .lastArea li:nth-child(6n)').css('marginRight','0');
	$('.reader-side-2 ul li:nth-child(3n+0)').css({'margin':'6px 0px 0 0px'});		
};ind_ie8();

//购物车结算删除
function closeShopcarDele(){ $('.ui_back2,.ui_back,.box_content,.shopcar_dele').hide();}
function showsSopcarDele(){ $('.ui_back2,.ui_back,.box_content,.shopcar_dele').show();}
//preview pop
function showBuyNow(){$('.buyNowDivOut').show();}
function hideBuyNow(){$('.buyNowDivOut').hide();}
function showRechSucc(){$('.ui_back2,.ui_back,.box_content,.pay_tips').show();}
function RechRechSucc(){$('.ui_back2,.ui_back,.box_content,.pay_tips').hide();}

function showBuyTipSucc(){$('.ui_back2,.ui_back,.box_content,.pay_tip_succ').show();}
function hideBuyTipSucc(){$('.ui_back2,.ui_back,.box_content,.pay_tip_succ').hide();}
function showBuyTipFail(){$('.ui_back2,.ui_back,.box_content,.pay_tip_fail').show();}
function hideBuyTipFail(){$('.ui_back2,.ui_back,.box_content,.pay_tip_fail').hide();}

function MoveBox(e,N){	
	if($("#topShopCar").length>0){		
		$('#star').stop(true).show().css({		
			"width":"47px",
			"height":"37px",
			"position": "absolute",
			"z-index": "20151220",
			"left": e.pageX+ "px",
			"top": e.pageY + "px"
		});
		var endLeft,endTop;
		if(N=='1'){
			endLeft= $("#topShopCar").offset().left;
			endTop= $("#topShopCar").offset().top
		}else if(N=='2'){
			endLeft= e.pageX +600;
			endTop=  e.pageY -450;
		}else if(N=='3'){
			endLeft= e.pageX -100;
			endTop=  e.pageY -450;
		}
		
		$('#star').stop(true).animate({
			"left": endLeft + "px",
			"top": endTop + "px",
			"width": "0px",
			"height": "0px"
		},700).fadeOut();
			
	}else{return false;}	
	
}

$(function() {
    $(window).scroll(function() {
        var scrollt = document.documentElement.scrollTop + document.body.scrollTop;
        if (scrollt > 200) {
            $("#toTop").fadeIn(400)
        } else {
            $("#toTop").stop().fadeOut(400)
        }
    });
    $("#toTop").click(function() {
        $("html,body").animate({
            scrollTop: "0px"
        },
        200)
    })
});




//myPlateEdit  select news_sourse
/* 新闻源展开树 */
$(".news_sourse .sb_middle .tree1").each(function(){
	//$(this).find("i").click(function(){//收展树
		//$(this).toggleClass("i_down").parents("ul").find(".tree2").slideToggle(300);
		//$(this).toggleClass("i_down");
	//});
	$(this).find("input").click(function(){//每一组全选
			$(this).attr("checked")=="checked"?$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",$(this).attr("checked")) 
			:$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",false) ;
	});		
	$(".news_sourse .sb_middle .span2 input").click(function(){//全部选中
		$(this).attr("checked")=="checked"?$(this).parents(".span2").siblings(".over_scroll").find("input").attr("checked",$(this).attr("checked"))
		:$(this).parents(".span2").siblings(".over_scroll").find("input").attr("checked",false);		
	})
})

/* 新闻源移动 */
var bc,ac;
/* 向右移动 */
$(".news_sourse .sb_middle div.divm img.add").live("click",function(){
	$(".news_sourse .sb_middle .div1 ul:visible .tree1").each(function(index1){
			bc=$(".news_sourse .sb_middle .div2").find(".ol1");
			
			if($(this).find("input").attr("checked")=="checked"){
				var sco=0;//判断有没有相同的
				var _this=$(this);
				for(i=0;i<bc.length;i++){								
					if(_this.parents("ul").find(".ol1").text()==bc.eq(i).text()){						
						sco=1;
					}//发现相同网站不移动
				 }				
				 if(sco==0){//判断右侧没有相同的 新建个
					//var str1="<ul style='display:block'><span class='tree1'>"+_this.html()+"</span></ul>";
					var str1="<ul style='display:block'><span class='tree1'><span><input type='checkbox' checked='checked' /><ol class='ol1'>"+_this.parent().find(".ol1").text()+"</ol></span></span></ul>";
					$(".news_sourse .sb_middle .div2 .over_scroll").append(str1);
					//$(".news_sourse .sb_middle .div2 .over_scroll").find("input").attr("checked","checked")
					_this.parent("ul").fadeOut(0);
				 }			
				
				$(".news_sourse .sb_middle .tree1").each(function(){
					$(this).find("input").click(function(){//每一组全选
						$(this).attr("checked")=="checked"?$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",$(this).attr("checked")) 
						:$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",false) ;
					});	
				});
			}
		bc=$(".news_sourse .sb_middle .div2").find(".ol1");
	});
});
/* 向左移动 */
$(".news_sourse .sb_middle div.divm img.remove").live("click",function(){		
	$(".news_sourse .sb_middle .div2 ul:visible .tree1").each(function(index1){
		ac=$(".news_sourse .sb_middle .div1").find(".ol1");	
		if($(this).find("input").attr("checked")=="checked"){
				var _this=$(this);
				var sco=0;//判断有没有相同的
				for(i=0;i<ac.length;i++){				
					if(_this.parents("ul").find(".ol1").text()==ac.eq(i).text()){
							ac.eq(i).parent().parent().css("display","inline");
							ac.eq(i).parent().parent().find('input[type="checkbox"]').attr('checked','checked');
							ac.eq(i).parent().parent().parent().css("display","block");
					}else{
						
						}
					_this.parent("ul").remove();
					//_this.parent("ul").fadeOut(0)
				}
				$(".news_sourse .sb_middle .tree1").each(function(){
					$(this).find("input").click(function(){//每一组全选
						$(this).attr("checked")=="checked"?$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",$(this).attr("checked")) 
						:$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",false) ;
					});	
				});
			}
		ac=$(".news_sourse .sb_middle .div1").find(".ol1");		
	});
});

//高级搜索的搜索功能
function highSearch(selfThat,isLeSear){
	
	//selfThat 弹出 “选择新闻源” 的弹层的input的value
	//isLeSear 判断是否是  选择新闻源弹出层本身左侧的搜索
	var newsNameVa,moduleNameVa;
	if(isLeSear=='yes'){
		newsNameVa=$('.news_sourse #LeSeaBox input').eq(0).val();
		moduleNameVa=$('.news_sourse #LeSeaBox input').eq(1).val();
	}else if(isLeSear=='no'){		
		newsNameVa='';
		moduleNameVa='';
	    $(".news_sourse .sb_middle .div2 .over_scroll").html('');
	}	
	var hiddenId=new Array;
	$(".news_sourse .sb_middle .div1 .over_scroll").html('');	
	$.ajax({
	   type: "GET",
	   url: "news/column/searchNewsSource.do?newsName="+newsNameVa,
	   dataType:"json",
	   success: function(data){
		   var resultJson = eval('(' + data + ')');
		   var result = resultJson.data;
		   var html='';		     
		   if(selfThat!=''){
			   
			    var self_text;			    
				self_text=selfThat.split('_');				
//				var selfVal=$(".add_channel #select_sources").val();
//				selfVal=selfVal.split('_');				
//				if(selfVal.length>0){
//						for(var j=0;j<selfVal.length;j++){
//							var str1="<ul style='display:block'><span class='tree1'><span><input type='checkbox' checked='checked' /><ol class='ol1'>"+selfVal[j]+"</ol></span></span></ul>";
//							alert(str1);
//							$(".news_sourse .sb_middle .div2 .over_scroll").append(str1);
//							for(var i = 0; i < result.length; i++){
//								if(result[i]==selfVal[j]){									
//									//result.splice(i,1);
//									hiddenId.push(selfVal[j]);
//									
//								}
//							}
//						}
//				}	
							
				if(self_text.length>0){
						for(var j=0;j<self_text.length;j++){
							var str1="<ul style='display:block'><span class='tree1'><span><input type='checkbox' checked='checked' /><ol class='ol1'>"+self_text[j]+"</ol></span></span></ul>";
							
							$(".news_sourse .sb_middle .div2 .over_scroll").append(str1);
							for(var i = 0; i < result.length; i++){
								if(result[i]==self_text[j]){									
									//result.splice(i,1);
									hiddenId.push(self_text[j]);
									
								}
							}
						}
				}		
		   }
		   
		   for(var i = 0; i < result.length; i++){			   
				if(hiddenId.length>0 ){					
					for(var j = 0; j < hiddenId.length; j++){
						if( result[i] == hiddenId[j]){
							//console.log(result[i].id);
							html+='<ul style="display:none"><span class="tree1"><span><input type="checkbox" /><ol class="ol1">'+result[i]+'</ol></span></span></ul>';	
							result.splice(i,1);
						}
					}					
				}
				
				html+='<ul tid="'+result[i].id+'"><span class="tree1"><span><input type="checkbox" /><ol class="ol1">'+result[i]+'</ol></span></span></ul>';
								
		  }
		 
		  $(".news_sourse #LeSeaBox .over_scroll").html(html);
		  $(".news_sourse .sb_middle .tree1").each(function(){
			$(this).find("input").click(function(){//每一组全选
					$(this).attr("checked")=="checked"?$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",$(this).attr("checked")) 
					:$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",false) ;
			});	
			$(".news_sourse .sb_middle .span2 input").click(function(){//全部选中
				$(this).attr("checked")=="checked"?$(this).parents(".span2").siblings(".over_scroll").find("input").attr("checked",$(this).attr("checked"))
				:$(this).parents(".span2").siblings(".over_scroll").find("input").attr("checked",false);		
			});
		});
	   },
	   error:function(){
		  $(".news_sourse #LeSeaBox .over_scroll").html("获取信息失败,请稍后重试");
	   }
	});
}
$('.news_sourse #LeSeaBox a#LeSearch').click(function(){highSearch('','yes');});  //新闻源  左侧筛选
$('.news_sourse #RiSeaBox a#RiSearch').click(function(){                          //新闻源  右侧筛选     
	bc=$(".news_sourse .sb_middle .div2").find(".ol1");
	
	var textArr=bc;	
	var str=str1='';
	var idr=idr1='';		
	for(var i=0;i<textArr.length;i++){
	  str = textArr.eq(i).text()+'_';
	  str1+=str;
	}
	str1=str1.substr(0,(str1.length-1));
	
	var ss=$('#RiSeaBox input').eq(0).val();		
	if(ss==''){
		$(".news_sourse .sb_middle .div2 .over_scroll").html('');		
		//highSearch(str1,'no');
		str1=str1.split('_');
		if(str1.length>0){
			for(var j=0;j<str1.length;j++){
				var html="<ul style='display:block'><span class='tree1'><span><input type='checkbox' checked='checked' /><ol class='ol1'>"+str1[j]+"</ol></span></span></ul>";
				$(".news_sourse .sb_middle .div2 .over_scroll").append(html);				
			}
		}	
	}else{		
		for(i=0;i<bc.length;i++){
			var flag = bc.eq(i).text().indexOf(ss);
			if(flag=='-1'){
				bc.eq(i).parent().parent().parent().hide();
			}
		}
	}
//	for(i=0;i<bc.length;i++){
//		var flag = bc.eq(i).text().indexOf(str);
//		if(flag=='-1'){
//			bc.eq(i).parent().parent().parent().hide();
//		}
//	}
});
	
/* 弹出  选择新闻源 */
$(".add_channel #select_sources").focus(function(){
	//alert($(this).val());
	$('#form_cities').hide();
	$(this).blur();
	$(".news_sourse,.box_content,.ui_back,.ui_back2").fadeIn(100);
	highSearch($(this).val(),'no');		
	$('.news_sourse #LeSeaBox input').val("");
	$('.news_sourse #RiSeaBox input').val("");
})
$(".modifi_channel #select_sources2").click(function(){	
	 var self_text = $(".modifi_channel #select_sources2").text().split('_');	
	 if (self_text != "全部"){
	     $(".selected_sourse .sb_middle .div2 .over_scroll").html('');
	     $(".selected_sourse,.box_content,.ui_back,.ui_back2").fadeIn(100);
		 var html='';
		 for(var i=0;i<self_text.length;i++){		 
			 html+="<ul style='display:block'><span class='tree1'><span><input type='checkbox' checked='checked' style='display:none' /><ol class='ol1'>"+self_text[i]+"</ol></span></span></ul>";
		 }	 
		 $(".selected_sourse .sb_middle .div2 .over_scroll").append(html); 
	 }
})
/* 新闻源确认 */
$(".news_sourse .sb_button .a1").click(function(){	
	var textArr=$('.news_sourse .sb_middle .div2 .over_scroll ul .ol1');	
	var str=str1='';
	var idr=idr1='';		
	for(var i=0;i<textArr.length;i++){
	  str = textArr.eq(i).text()+'_';
	  str1+=str;
	}
	str1=str1.substr(0,(str1.length-1));
	$(".add_channel input#select_sources").val(str1);
	$(this).parents(".news_sourse").fadeOut(100);	
	$(".news_sourse .sb_middle .over_scroll").html('');	
})

$(".news_sourse .sb_button .a2,.news_sourse .pop_closeBt_s").click(function(){	
	$(this).parents(".news_sourse").fadeOut(100);	
})
$(".selected_sourse .sb_button .a2,.selected_sourse .pop_closeBt_s").click(function(){	
	$(this).parents(".selected_sourse").fadeOut(100);	
})

$('.selected_sourse #RiSeaBox2 a#RiSearch2').click(function(){//右侧已选，内部筛选
	bc=$(".selected_sourse .sb_middle .over_scroll").find(".ol1");		
	var ss=$('#RiSeaBox2 input').eq(0).val();		
	if(ss==''){	
		var self_text = $(".modifi_channel #select_sources2").text().split('_');
	     $(".selected_sourse .sb_middle .div2 .over_scroll").html('');	    
		 var html='';
		 for(var i=0;i<self_text.length;i++){		 
			 html+="<ul style='display:block'><span class='tree1'><span><input type='checkbox' checked='checked' style='display:none' /><ol class='ol1'>"+self_text[i]+"</ol></span></span></ul>";
		 }	 
		 $(".selected_sourse .sb_middle .div2 .over_scroll").append(html); 			
	}else{		
		for(i=0;i<bc.length;i++){
			var flag = bc.eq(i).text().indexOf(ss);
			if(flag=='-1'){
				bc.eq(i).parent().parent().parent().hide();
			}
		}
	}
	
	
});
//栏目》新闻源》回车效果
$(document).keydown(function(event){  
if (event.keyCode == "13") {//keyCode=13是回车键      
	if($('.news_sourse').is(":visible")){		
		var isFocus=$(".news_sourse #LeSeaBox input.input").is(":focus"); 
		if(true==isFocus){ 		
			$(".news_sourse #LeSeaBox a#LeSearch").click();
		}else{ 		
			$(".news_sourse #RiSeaBox a#RiSearch").click();
		} 
	}else{	
		
		$(".selected_sourse #RiSeaBox2 a#RiSearch2").click();
	}
	
}
});
/**/
function showHeadUl_newC() {$('.topBar .bg .hideUl').show();}
function hideHeadUl_newC() {$('.topBar .bg .hideUl').hide();}
