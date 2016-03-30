
$(document).ready(function(){
/* 新闻阅读页 收藏按钮 */
$(".content .share .collection").click(function(){
	var me=$(this);
	var class1=$(this).attr("class");
	if(class1=='collection'){//已收藏
		return ;
	}else{
		var esId=$("#esId_hidden").val();
		var content=$("#content_hidden").text();
		var contentName=$(".content h4.title").text();
		$.ajax({
			   type: "POST",
			   url: "risk/view/add2MyCollection.do",
			   data: {'articleid':esId,'content':content,'contentName':contentName},
			   success: function(data){
				   if(data.result==1){
					   me.toggleClass("active");
				   }else{
					   alert(data.info);
				   }
			   }
			 });
		
	}
});

var reg_phone = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\d{8})$/;	
/* 上市公司风险 选择 */
	$("#sf_diva a").click(function(){
		$(this).addClass("active").siblings("a").removeClass("active");
	});
/* 上市公司风险 关闭菜单 */
$(".sf_div1 .div1 .hangye,.sf_div1 .div1 .area,.menu_hangye,.menu_area,.menu_hangye dl input").click(function(e){
	e.stopPropagation();
})
/* 上市公司风险 选择区域 */
$(".menu_area .body a input").click(function(){
	$(".sf_div1 .div1 input.area").attr("value",$(this).parent("a").text());
	$(".sf_div1 .div1 input.area_value").attr("value",$(this).attr("value"));
	$(".menu_area").fadeOut(200);
	$(".sf_div1 .div1 .area").removeClass("active");
})
/* 上市公司风险 选择行业  */
$(".menu_hangye dl span input").click(function(){
	$(".sf_div1 .div1 input.hangye").attr("value",$(this).parent("span").text());
	$(".sf_div1 .div1 input.hangye_value").attr("value",$(this).attr("value"));
	$(".menu_hangye").fadeOut(200);
	$(".sf_div1 .div1 .hangye").removeClass("active");
})

$(".menu_area").click(function(){
})
$(document).click(function(){
	$(".menu_hangye,.menu_area").fadeOut(100);
	$(".sf_div1 .div1 .hangye,.sf_div1 .div1 .area").removeClass("active");
})
 /*上市公司风险  树形点击收缩 */
	  var acore=0;
	$(".menu_hangye  dl h5").each(function(){
		$(this).click(function(){
			 $(this).parents("dl").find("dt").slideToggle(200);
			 $(this).find("i").toggleClass("i_down");
			 acore++;var s= acore%2==0 ? 0 : 1;
			 if(s==0){
				 $(this).parents("dl").find("dd").fadeOut(200).find("i").removeClass("i_down");
				 $(this).parents("dl").find("dt i").removeClass("i_down");
				 
			 }				 
		 });
	})
	
	  $(".menu_hangye  dl dt").click(function(){
		 $(this).nextUntil("dt").slideToggle(200);
		$(this).find("i").toggleClass("i_down");
	 });
	 /* 复选框全选 */
/* 	  $(".menu_hangye  dl dt input").click(function(e){
		 if($(this).attr("checked")=="checked"){
			$(this).parents("dt").nextUntil("dt").find("input").attr("checked","checked");						
		 }else{$(this).parents("dt").nextUntil("dt").find("input").attr("checked",false); }
		e.stopPropagation();
	 }); */	
	
	
	
	/* 弹窗生成树 */
	/* 选择行业 */
function allmodules(){
	$.ajax({
			  url: "risk/view/allmodules.do",
			  type: "post",
			  dataType: "json",
			  success: function(d){
				 str="";
				 for(a=0;a<d.length;a++){//一级				
				 
						 str+="<dl>";
						 str+="<h5><span><i></i><img src='common/img/choose2.png' />"+d[a].mname+"</span></h5>";						 						 
						 for(b=0;b<d[a].child.length;b++){//二级							
							str+="<dt><span><i></i><input name='module' value="+d[a].child[b].mid+" type='checkbox' /><img src='common/img/choose2.png' />"+d[a].child[b].mname+"</span></dd>";
							 for(c=0;c<d[a].child[b].child.length;c++){//三级
								str+="<dd><span><input name='module' value="+d[a].child[b].child[c].mid+" type='checkbox' /><img src='common/img/choose2.png'/ >"+d[a].child[b].child[c].mname+"</span></dd>";
							 }
						 }
						 
						 str+="</dl>";
				 }
				 $(".industry_options .sb_middle div.stype1").html(str);
				 /* 树形点击收缩 */
				  var acore=0;
				$(".industry_options  dl h5").each(function(){
					$(this).click(function(){
						 $(this).parents("dl").find("dt").slideToggle(200);
						 $(this).find("i").toggleClass("i_down");
						 acore++;var s= acore%2==0 ? 0 : 1;
						 if(s==0){
							 $(this).parents("dl").find("dd").fadeOut(200).find("i").removeClass("i_down");
							 $(this).parents("dl").find("dt i").removeClass("i_down");
						 }				 
					 });
				})
				
				  $(".industry_options  dl dt").click(function(){
					 $(this).nextUntil("dt").slideToggle(200);
					$(this).find("i").toggleClass("i_down");
				 });
				 /* 复选框全选 */
				  $(".industry_options  dl dt input").click(function(e){
					 if($(this).attr("checked")=="checked"){
						$(this).parents("dt").nextUntil("dt").find("input").attr("checked","checked");						
					 }else{$(this).parents("dt").nextUntil("dt").find("input").attr("checked",false); }
					e.stopPropagation();
				 });
			  }
		  });
		  $(".industry_options ,.ui_back,.box_content").fadeIn(200);
}
	


	
	
	/* 首页点击菜单切换图片 */
var s=['20','17','26','149'];

/* $(".img_change .middle  ul li a").each(function(a){
	$(this).click(function(){
		$(this).parents("li").addClass("active").siblings("li").removeClass("active");
		$(".i_box").each(function(b){
			var c = a==0 ?1: a-1;
			var bb=b+1;
			$(this).find(".box_banner  img").attr("src","../common/img/img"+s[c]+"_"+bb+".jpg");
		})
	})
})
	 */
/*新的注册页面校验:*/
    //
    $(".companyName").blur(function(){
        var val=$(this).val();
        if(val=="银行"){
            $(".zhihang").show();
        }else{
            $(".zhihang").hide();
        }
    });

    //注册界面点击找不到单位名称:
    $("span.btn").click(function(){
       // $(".companyS").slideToggle();
        //alert("是否隐藏"+$(".companyS").is(":hidden"));
        if($(".companyS").is(":hidden")){
        	$(".companyS").show();
        }else{
        	$(".companyS").hide();
        	$("#companyName_spare").val(null);//设置选择框中的值为空,不再发送该数据
        }
    });




/* 注册页面 数据校验 */		
$(".content  .form  .done").click(function(){
var companyName=$.trim($('.companyName').val());
var companyNameS=$.trim($('#companyName_spare').val());
var name=$.trim($('.name').val());
var regName = /^[\u4e00-\u9fff]+$/;
var reg_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;//验证邮箱
var email=$.trim($('.test_email').val());
var pass=$.trim($('.test_password').val());
var pass2=$.trim($('.test_password2').val());
var phone = $.trim($(".test_phone2").val());
var password = $.trim($("#password").val());//短信动态码
var verifyCode = $.trim($("#verifyCode").val());//图形验证码

var tip=['请输入邮箱地址!','邮箱格式错误!','请输入密码!','手机格式错误!','密码长度为6~20个字符!','请填写公司名称','请填写姓名','姓名格式不正确','与上面密码不一致'];
var s=	$("#text_tip");
    if((companyName=='')&&(companyNameS=="")){
        s.html(tip[5]);
        setTimeout(function(){s.html(" ");},1500);return false;
    }else if(name==''){
        s.html(tip[6]);
        setTimeout(function(){s.html(" ");},1500);return false;
    }
    else if(!regName.test(name)){
        s.html(tip[7]);
        setTimeout(function(){s.html(" ");},1500);return false;
    }else if(email==''){
		s.html(tip[0]);
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(!reg_email.test(email)){
		s.html(tip[1]);
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(phone==''){
		s.html("请输入手机号");
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(phone!="" && !reg_phone.test(phone)){
		s.html(tip[3]);
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(password==''){
		s.html("请输入短信动态码");
	setTimeout(function(){s.html(" ");},1500);return false;
	}else if(pass==''){
        setTimeout(function(){s.html(" ");},1500);return false;
    }else if(pass.length < 6 || pass.length > 20){
        s.html(tip[4]);
        setTimeout(function(){s.html(" ");},1500);return false;
    }else if(pass2==''){
        s.html("请确认密码");
        setTimeout(function(){s.html(" ");},1500);return false;
    }else if(pass2!=pass){
        s.html(tip[8]);
        setTimeout(function(){s.html(" ");},1500);return false;
    }else if(verifyCode==''){
		s.html("请输入验证码");
	setTimeout(function(){s.html(" ");},1500);return false;
	}else{regist();}
});

/*
 * 注册
 */
function regist(){
	var str_data1=$("#regist_form input").map(function(){
		return ($(this).attr("name")+'='+$(this).val());
	}).get().join("&");
	var str_data2=$("#regist_form select").map(function(){
		return ($(this).attr("name")+'='+$(this).val());
	}).get().join("&");
	var str_data = str_data1+"&"+str_data2;
	$.ajax({
	   type: "POST",
	   url: "risk/view/regist.do",
	   data: str_data,
	   success: function(data){
		   if(data.result==1){
			   alert("恭喜您 注册成功！");
			   location.href="/webword/risk/view/home.do";
		   }else{
			   alert(data.msg);
		   }
	   }
	 });
}    


/* 手机验证码提交前  数据校验 */	
$(".content  .get_nub").click(phonecode);
function phonecode(){
	
	var tip=['请输入邮箱地址!','邮箱格式错误!','请输入密码!','手机格式错误!','密码长度为6~20个字符!'];
	var phone = $.trim($(".test_phone2").val());
	var s=	$("#text_tip");
		if(phone==''){
			s.html("请输入手机号");
			setTimeout(function(){s.html(" ");},1500);return false;
		}else if(phone!="" && !reg_phone.test(phone)){
			s.html(tip[3]);
			setTimeout(function(){s.html(" ");},1500);return false;
		}else{mess_get();nub();}
}
var interv;
function mess_get(){
	var sco=60;
		 $(".content .form a.get_nub").unbind("click").css("background-color","#999999");			 
		 interv =setInterval(function(){
			 $(".content .form a.get_nub").html("剩余"+sco+"秒");	
			 if(sco<=0){
				$(".content  .get_nub").click(phonecode).html("免费获取短信动态码!").css("background-color","#73b6e6");
				clearInterval(interv);
			 }
			 sco--;
		 },1000);		 
}
/* 手机验证码提交 */
function nub(){
	var str_data=$("#regist_form input").map(function(){
		return ($(this).attr("name")+'='+$(this).val());
	}).get().join("&");		
	str_data+="&userType=0";
	$.ajax({
	   type: "POST",
	   url: "view/sendCheckMsg.do",
	   data: str_data,
	   success: function(data){
		   if(data.status=="success"){		   
			   alert("短信验证码已发送");			   
		   }else{
			 alert(data.info);
		   }
	   }
	 });
}



/* 修改密码 数据校验 */	
$(".content  .frm2  .no").click(function(){
	$("#editpassword_form")[0].reset();
});
$(".content  .frm2  .yes").click(function(){
	var tip=['请输入邮箱地址!','邮箱格式错误!','请输入密码!','手机格式错误!','密码长度为6~20个字符!'];
	var user_password_old = $.trim($("#user_password_old").val());
	var user_password = $.trim($("#user_password").val());
	var user_password2 = $.trim($("#user_password2").val());
	var s=	$("#text_tip");
	if(user_password!=user_password2){
		s.html("两次输入的密码不一致");
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(user_password_old==""){
		s.html("请输入原密码");
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(user_password_old.length < 6 || user_password_old.length > 20){
		s.html(tip[4]);
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(user_password.length < 6 || user_password.length > 20){
		s.html(tip[4]);
		setTimeout(function(){s.html(" ");},1500);return false;
	}else{editpassword();}
});

/*
 * 修改密码
 */
function editpassword(){
	var str_data=$("#editpassword_form input").map(function(){
		return ($(this).attr("name")+'='+$(this).val());
	}).get().join("&");
	
	$.ajax({
	   type: "POST",
	   url: "risk/view/editpassword.do",
	   data: str_data,
	   success: function(data){
		   if(data.result==1){
			   alert("恭喜您 修改密码成功！");
			   //location.href="risk/view/home.do";
		   }else{
			   alert(data.msg);
		   }
	   }
	 });
}    



/* 用户设置页面-完善资料 数据校验 */		
$(".content .frm1 a.yes").click(function(){
	var companyName=$.trim($('.companyNameUser').val());//公司名称选择框
	var companyName2=$.trim($('.companyName').val());//公司名称输入框
	if(companyName=='undefined'||companyName==null){
		companyName="";
	}
	if(companyName2=='undefined'||companyName2==null){
		companyName2="";
	}
	

var s=	$("#text_tip1");
if(companyName2=="" && companyName==""){
    s.html("<span style='color:red;'>请输入公司名称！</span>");
    setTimeout(function(){s.html(" ");},1500);return false;
}
var str_data=$("#perfectdata_form input").map(function(){
	return ($(this).attr("name")+'='+$(this).val());
}).get().join("&");

var str_data1=$("#perfectdata_form select").map(function(){
	return ($(this).attr("name")+'='+$(this).val());
}).get().join("&");
console.info(str_data);
var str_data = str_data+"&"+str_data1;
$.ajax({
   type: "POST",
   url: "risk/view/perfectdata.do",
   data: str_data,
   success: function(data){
	   if(data.result==1){
		   alert("恭喜您 完善资料成功！");
		   location.reload();
	   }else{
		   alert(data.msg);
	   }
   }
 });

});

/* 登陆页面校验 */
$(".content  .login_btn").click(function(){
var pass=$.trim($('.test_pass').val());
var user=$.trim($('.test_user').val());

var tip=['请输入用户名','用户名不存在!','请输入密码!','密码长度为6~20个字符!','请填写公司名称'];	
var s=	$("#text_tip3");
	if(user==''){
		s.html(tip[0]);
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(pass==""){
		s.html(tip[2]);
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(pass.length < 6 || pass.length > 20){
		s.html(tip[3]);
		setTimeout(function(){s.html(" ");},1500);return false;
	}else{login();}
});

/*
 * 登陆
 */
function login(){
	var str_data=$("#login_form input").map(function(){
		return ($(this).attr("name")+'='+$(this).val());
	}).get().join("&");
	
	$.ajax({
	   type: "POST",
	   url: "risk/view/login.do",
	   data: str_data,
	   success: function(data){
		   if(data.state=="OK"){
			   
			   if(data.status==22){
				   $(".day_tips2 .p1").html("尊敬的:"+data.user.userName);
				   $(".day_tips2 .p2").html("&nbsp;&nbsp;&nbsp;&nbsp;您的免费试用期在"+data.endTime+"日到期，为不影响您的正常使用，请及时联系客服进行充值缴费。<br>&nbsp;&nbsp;&nbsp;&nbsp;联系电话: 010-63368810");
				   $(".day_tips2,.box_content,.ui_back").fadeIn(300);//显示
				   $(".sbox_ h3 img,.sb_button .a1").click(function(){
						location.href="/webword/risk/view/home.do";
				   });	
			   }else if(data.status==23){
				   $(".day_tips1 .p1").html("尊敬的:"+data.user.userName);
				   $(".day_tips1,.box_content,.ui_back").fadeIn(300);//显示
				   /* 关闭弹窗 */
				   $(".sbox_ h3 img,.sb_button .a1").click(function(){
					location.href="/webword/risk/view/home.do";
				   });	
			   }else{
				   location.href="/webword/risk/view/home.do";
			   }
			 
		   }else{
			   alert(data.info);
		   }
	   }
	 });
} 
/* 选择自动登陆 */	
$("#login_form  .check").click(function(){
	if ($(".check").attr("checked")) {
		$(".check").val(1);
	}else{
		$(".check").val(0);
	}
});




/* 找回密码 第1步 */	
$(".content  #fond1_form  .abtn").click(function(){
	var tip=['请输入邮箱地址!','邮箱格式错误!','请输入密码!','手机格式错误!','密码长度为6~20个字符!'];
	var phone = $.trim($("#userPhone").val());
	var verifyCode = $.trim($("#verifyCode").val());//图形验证码
	var s=	$("#text_tip");
	if(phone==''){
		s.html("请输入手机号");
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(phone!="" && !reg_phone.test(phone)){
		s.html(tip[3]);
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(verifyCode==''){
		s.html("请输入图形验证码");
	setTimeout(function(){s.html(" ");},1500);return false;
	}else{fond1();}
});
function fond1(){
	var str_data=$("#fond1_form input").map(function(){
		return ($(this).attr("name")+'='+$(this).val());
	}).get().join("&");
	
	$.ajax({
	   type: "POST",
	   url: "risk/view/fondpassword1.do",
	   data: str_data,
	   success: function(data){
		   if(data.result==1){
			   //发送手机验证码
			   sendMsg(str_data);
		   }else{
			   alert(data.msg);
		   }
	   }
	 });
}
//忘记密码 发送短信
function sendMsg(str_data){
	$.ajax({
		   type: "POST",
		   url: "view/sendCheckMsg.do",
		   data: str_data,
		   success: function(data){
			   if(data.status=="error"){//发送失败
				   alert(data.info);
			   }else{
				   location.href="/webword/risk/view/tofondpassword2.do";
			   }
		   }
		 });
}
/* 找回密码 第2步 */	
$(".content  #fond2_form  .abtn").click(function(){
	var password = $.trim($("#password").val());//短信动态码
	var s=	$("#text_tip");
	if(password==''){
		s.html("请输入短信动态码");
	setTimeout(function(){s.html(" ");},1500);return false;
	}else{fond2();}
});
function fond2(){
	var str_data=$("#fond2_form input").map(function(){
		return ($(this).attr("name")+'='+$(this).val());
	}).get().join("&");
	
	$.ajax({
	   type: "POST",
	   url: "risk/view/fondpassword2.do",
	   data: str_data,
	   success: function(data){
		   if(data.result==1){
			   location.href="/webword/risk/view/tofondpassword3.do";
		   }else{
			   alert(data.msg);
		   }
	   }
	 });
}
/* 手机验证码提交前  数据校验 */	
$(".content  .get_a").click(function(){
		var str_data=$("#fond2_form input").map(function(){
			return ($(this).attr("name")+'='+$(this).val());
		}).get().join("&");
		str_data+="&userType=0";
		$.ajax({
		   type: "POST",
		   url: "view/sendCheckMsg.do",
		   data: str_data,
		   success: function(data){
			   if(data.status=="success"){
				   alert("短信验证码已发送");
			   }else{
				   alert(data.info);
			   }
		   }
		 });
}
);


/* 找回密码 第3步 */	
$(".content  #fond3_form  .abtn").click(function(){
	var tip=['请输入邮箱地址!','邮箱格式错误!','请输入密码!','手机格式错误!','密码长度为6~20个字符!'];
	var user_password = $.trim($("#user_password").val());
	var user_password2 = $.trim($("#user_password2").val());
	var s=	$("#text_tip");
	if(user_password!=user_password2){
		s.html("两次输入的密码不一致");
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(user_password.length < 6 || user_password.length > 20){
		s.html(tip[4]);
		setTimeout(function(){s.html(" ");},1500);return false;
	}else{fond3();}
});
function fond3(){
	var str_data=$("#fond3_form input").map(function(){
		return ($(this).attr("name")+'='+$(this).val());
	}).get().join("&");
	
	$.ajax({
	   type: "POST",
	   url: "risk/view/fondpassword3.do",
	   data: str_data,
	   success: function(data){
		   if(data.result==1){
			   location.href="/webword/risk/view/tofondpassword4.do";
		   }else{
			   alert(data.msg);
		   }
	   }
	 });
}


/* 找回密码 第4步 */	
$(".content  #fond4_form  .abtn").click(function(){
	location.href="/webword/risk/view/tologin.do";
});


/* else if(phone!="" && !reg_phone.test(phone)){
		tipsShow($('.regist_pop'), '手机格式错误!');return false;
	} */

/* menu1添加关键词 */
/* $(".ser_box .add_more").click(function(){
	$(this).before("<div class='inline'>"+$(this).prev().html()+"</div>");
})	 */
	
/* menu2菜单点击 */	
	$(".left_list ul li").each(function(n){
		$(this).click(function(){
			var a=n+2;
            $(this).addClass("highClass").siblings("li").removeClass("highClass");
			$(this).addClass("active").siblings("li").removeClass("active");
			$(".content .right:nth-child("+a+")").addClass("ractive").siblings(".right").removeClass("ractive");		
		});		
	});
    //点击更多切换页面:
    $(".more").click(function(){
        $(".content .right").removeClass("ractive");
        var index=$(this).index()-1;
        $(".content .more-right").eq(index).addClass("ractive").siblings(".more-right").removeClass("ractive");
    });

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
//
//			 $.ajax({
//				   type: "POST",
//				   url: "risk/view/searchCompanyAndBankList.do",
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
//	 		});
//		};
//		init();
//	};
    //我的定制:
    //var ss2 =  new oSearchSuggest($('.content input#search'),$('.searchBox'),'-1','29',$('.searchBtn'),5,1);
    //注册页面:
   // var ss3 =  new oSearchSuggest($('.form input#companyName'),$('.company'),'130','35',$('.content'),5,1);
  //  var ss3 =  new oSearchSuggest($('.form input.sheng'),$('.company'),'325','35',$('.content'),5,1);
   // var ss3 =  new oSearchSuggest($('.form input.shi'),$('.company'),'521','35',$('.content'),5,1);
    //账号设置:
   // var ss3 =  new oSearchSuggest($('.frm input.companyNameUser'),$('.cn'),'207','61',$('.content'),5,1);
   // var ss3 =  new oSearchSuggest($('.frm input.shengUser'),$('.cn'),'355','61',$('.content'),5,1);
  //  var ss3 =  new oSearchSuggest($('.frm input.shiUser'),$('.cn'),'435','61',$('.content'),5,1);
   /* 页面头部下拉菜单 */
$(".menu2_down2").click(function(event) {
        event.stopPropagation();
        $(".udown_menu").fadeToggle();
    });
$(document).click(function(event) {
        event.stopPropagation();
        $(".udown_menu").fadeOut();
    });

/* 我的风险预警 左侧菜单效果 */
$(".left_list .str1 a.title1").click(function(){
	$(this).parent(".str1").find(".str2").slideToggle(300);	
	$(this).find("i").toggleClass("down");
});
$(".left_list .str2 a.title2").click(function(){
	$(this).parent(".str2").find(".str3").slideToggle(300);	
	$(this).find("i").toggleClass("down");
});
$(".left_list .str3 a.title3").click(function(){
	$(".left_box1  .str3").removeClass("active");
	$(this).parent(".str3").addClass("active");	
});
		
		/* 注册页面 菜单点击 */
/* $(".a_menu a").each(function(n){
	$(this).click(function(){
		var a=n+1;
		$(this).addClass("active").siblings("a").removeClass("active");
		$(this).parents(".content").find(".form").fadeOut(0);
		$(".forma"+a+"").fadeIn(0);
	})
	
})		 */
/* 注册页面校验 */

/* 关闭弹窗 */
$(".sbox_ h3 img,.sb_button .a1").click(function(){
	$(this).parents(".sbox_").fadeOut(300);
	$(".box_content,.ui_back").fadeOut(300);
	
});	
/* 风险预警弹窗 */
$(".industry_options .sb1 .a3").click(function(){
	$(".industry_options  .sb_button").removeClass("sb1");
	$(".industry_options  .stype2").fadeIn(0).siblings(".stype1").fadeOut(0);
});
$(".industry_options .sb1 .a4").click(function(){
	$(".industry_options  .sb_button").addClass("sb1");
	$(".industry_options  .stype1").fadeIn(0).siblings(".stype2").fadeOut(0);
})
		
/* -----------------ready_end------------------------ */
})

/* 风险预警弹窗 */
function ser_box(){
	$(".industry_options,.box_content,.ui_back").fadeIn(200);
	
}

/* 注 */
/*  */
//退出登陆
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
//点击我的风险预警
function myRiskClick(){
	if(isLogin()){//已经登录
		window.location = "/webword/risk/view/myrisk.do";
	}else{
		window.location = "/webword/risk/view/tologin.do";
	}
}
function isLogin(){
	var f = false;
	$.ajax({
        type: "post",
        url: "view/islogin.do",
        async:false,
        contentType: "application/x-www-form-urlencoded; charset=utf-8", 
        success: function(data) {
        	if(!data || data!=1){//没有登录
        		//
        	}else{
        		f = true;//已经扥估
        	}
        }
    });
	return f;
}

 /*上市公司风险  下拉框*/	
function sf_menu1(){
	$(".menu_area .body a input").attr("checked",false);
	$(".sf_div1 .div1 .area").toggleClass("active");
	$(".menu_area").fadeToggle(100);
}
function sf_menu2(){
	$(".menu_hangye dl span input").attr("checked",false);
	$(".sf_div1 .div1 .hangye").toggleClass("active");
	$(".menu_hangye").fadeToggle(100);
}
/*h获得用户是否在试用期，试用期结束时间，和是否今天首次登录*/
function getStatus(){
	$.ajax({
        type: "post",
        url: "risk/view/getTipsMsg.do",
        async:false,
        contentType: "application/x-www-form-urlencoded; charset=utf-8", 
        success: function(data) {
        	var result=eval(data);
        	return result;
        }
    });
}
