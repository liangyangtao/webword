

var curmid;
var curkeyword;
var curtitle;
var curtype;
function pageLoad(){
	if(G_listSzie==0){//显示目录树
		fengXianClick();
	}
}
function fengXianClick(){
	$.ajax({
		  url: "risk/view/rightmodules.do",
		  type: "post",
		  dataType: "json",
		  success: function(d){
			 str="";
			 for(a=0;a<d.length;a++){//一级						
					 str+="<dl>";
					 if(d[a].right==1){
						 str+="<h5><span><i></i><img src='common/img/choose2.png' />"+d[a].mname+"</span></h5>";						 						 
						 for(b=0;b<d[a].child.length;b++){//二级	
							 if(d[a].child[b].right==1){
								 if(d[a].child[b].has==1){
									 str+="<dt><span><i></i><input name='module' checked='checked'  value="+d[a].child[b].mid+" type='checkbox' /><img src='common/img/choose2.png' />"+d[a].child[b].mname+"</span></dd>"; 
									 for(c=0;c<d[a].child[b].child.length;c++){//三级
										 if(d[a].child[b].child[c].right==1){
											 if(d[a].child[b].child[c].has==1){
												 str+="<dd><span><input name='module' checked='checked' value="+d[a].child[b].child[c].mid+" type='checkbox' /><img src='common/img/choose2.png'/ >"+d[a].child[b].child[c].mname+"</span></dd>";
											 }else{
												 str+="<dd><span><input name='module'  value="+d[a].child[b].child[c].mid+" type='checkbox' /><img src='common/img/choose2.png'/ >"+d[a].child[b].child[c].mname+"</span></dd>";
											 }
											 
										 }
											
										 }
									 
								 }else{
									 str+="<dt><span><i></i><input name='module' value="+d[a].child[b].mid+" type='checkbox' /><img src='common/img/choose2.png' />"+d[a].child[b].mname+"</span></dd>";
									 for(c=0;c<d[a].child[b].child.length;c++){//三级
										str+="<dd><span><input name='module' value="+d[a].child[b].child[c].mid+" type='checkbox' /><img src='common/img/choose2.png'/ >"+d[a].child[b].child[c].mname+"</span></dd>";
									 }	 
								 }
								
							 }
												 
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
$(document).ready(function(){
	/* 我的风险预警左侧菜单伸缩 */
	$(".left_list h4.t_h").click(function(){
		$(this).siblings(".content_box").slideToggle(300);
	})
	$(".left_list h4.t_h a").click(function(e){
		e.stopPropagation();
	})
	
	
	/* 弹窗生成树 */
	/* 选择行业 */
$(".fengxianmy").click(function(){
	fengXianClick();
});
	


	
	
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

/* 注册页面 数据校验 */		
$(".content  .form  .done").click(function(){
var reg_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;//验证邮箱
var reg_phone = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //验证手机格式
var email=$.trim($('.test_email').val());
var pass=$.trim($('.test_password').val());
var phone = $.trim($(".test_phone2").val());
var password = $.trim($("#password").val());//短信动态码
var verifyCode = $.trim($("#verifyCode").val());//图形验证码
var tip=['请输入邮箱地址!','邮箱格式错误!','请输入密码!','手机格式错误!','密码长度为6~20个字符!'];	
var s=	$("#text_tip");
	if(email==''){
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
	}else if(pass==''){
			s.html(tip[2]);
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(pass.length < 6 || pass.length > 20){
		s.html(tip[4]);
		setTimeout(function(){s.html(" ");},1500);return false;
	}else if(password==''){
		s.html("请输入短信动态码");
	setTimeout(function(){s.html(" ");},1500);return false;
	}else if(verifyCode==''){
		s.html("请输入图形验证码");
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
			   location.href="risk/view/home.do";
		   }else{
			   alert(data.msg);
		   }
	   }
	 });
}    


/* 手机验证码提交前  数据校验 */	
$(".content  .get_nub").click(function(){
	
	var reg_phone = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //验证手机格式
	var tip=['请输入邮箱地址!','邮箱格式错误!','请输入密码!','手机格式错误!','密码长度为6~20个字符!'];
	var phone = $.trim($(".test_phone2").val());
	var s=	$("#text_tip");
		if(phone==''){
			s.html("请输入手机号");
			setTimeout(function(){s.html(" ");},1500);return false;
		}else if(phone!="" && !reg_phone.test(phone)){
			s.html(tip[3]);
			setTimeout(function(){s.html(" ");},1500);return false;
		}else{nub();}
});

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
var str_data=$("#perfectdata_form input").map(function(){
	return ($(this).attr("name")+'='+$(this).val());
}).get().join("&");

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
var tip=['请输入用户名','用户名不存在!','请输入密码!','密码长度为6~20个字符!'];	
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
			   alert(data.info);
			   location.href="risk/view/home.do";
		   }else{
			   alert(data.info);
		   }
	   }
	 });
} 


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
			$(this).addClass("active").siblings("li").removeClass("active");
			$(".content .right:nth-child("+a+")").addClass("ractive").siblings(".right").removeClass("ractive");		
		});		
	});
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
});
		


/* 复选框全选 */
 $(".manage_en ul.list .all_select_input").click(function(e){
		 if($(this).attr("checked")=="checked"){
			$(this).parents("ul").find(".check_input").attr("checked","checked");						
		 }else{$(this).parents("ul").find(".check_input").attr("checked",false); }
		e.stopPropagation();
	 });
/* -----------------ready_end------------------------ */
});





























//点击一级栏目
function show(mid,title){
	curmid = mid;
	curtitle = title;
	var keyword =$("#keyword").val();
	 get_doclist(mid,title,1,keyword,curtype);
	
}


//格式化日期用
Date.prototype.Format = function(fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,
        //月份
        "d+": this.getDate(),
        //日
        "h+": this.getHours(),
        //小时
        "m+": this.getMinutes(),
        //分
        "s+": this.getSeconds(),
        //秒
        "q+": Math.floor((this.getMonth() + 3) / 3),
        //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
function get_doclist(mid,title,curNumber,keyword,sorttype) {
	curtitle=title;
	curmid=mid;
	curtype = sorttype;
	var pageAllNum, currentCount;
	var param = {mid:mid,keyword:keyword,sorttype:sorttype};
	if(curNumber){
		param.curNumber = curNumber;
	}
    $.ajax({
        url: "/webword/risk/view/queryArticleList.do",
        async: true,
        type: "post",
        data:param,
        dataType: "json",
        cache: false,
        success: function(d) {
        var str ='<h4 class="title">';
        str +=title;
        str +='<select name = "sorttype" id="sorttype" onchange = "change(this.value)">';
         
        str +='<option value="0" '+(sorttype==0?"selected='selected'":"")+' >相关度</option>';
        str +='<option value="1" '+(sorttype==1?"selected='selected'":"")+' >时间</option>';
        str +='</select>';
        str +='</h4>';
        str += '<div class="list">	';
        
        for (i = 0; i < d.data.length; i++) {
        	var dm = d.data[i];
        	if(dm){
        		str +='<div class="list1">';
        		var title1 = dm.title;
        		var newTiltle = title1.replace(/<[^>]+>/g,"");//去掉所有的html标记
        		str += '<h5 title="'+newTiltle+'"><img class="point" src="common/img/point.png"/><a class="title"  target="_blank" href="risk/view/getNewsById.do?riskId='+dm.crawl_id+'&categoryId='+curmid+'&esId='+dm.esId+'&id=0">'+dm.title+'</a><span class="time">' + (new Date(dm.newsDate)).Format('yyyy-MM-dd hh:mm:ss') + '</h5>';
        		str +='<p class="contents">'+dm.content+'</p>';
        		if(dm.keyWords!=null){
        			str += '<div class="choise"><ul>';
        			var strs= new Array(); 
        			strs = dm.keyWords.split(" ");
        			if(strs.length>8){
        				for(j=0 ; j<8 ;j++){
        					if(strs[j]!=""&&strs[j]!='<p>'){
        						str += '<li class="link'+(j+1)+'"><a href="javascript:;" onclick="searchnews(\''+strs[j]+'\')">'+strs[j]+'</a></li>';
        					}
        					
        				}
        				
        			}else{
        				for(k=0 ; k<strs.length ; k++){
        					if(strs[k]!=""&&strs[k]!='<p>'){
        						//str += "<li class='link"+(k+1)+"'><a href='javascript:;' onclick='searchnews(\""+strs[k]+"\")'>"+strs[k]+"</a></li>";
        						str += '<li class="link'+(k+1)+'"><a href="javascript:;" onclick="searchnews(\''+strs[k]+'\')">'+strs[k]+'</a></li>';
        					}
        					
        				}
        			}
        			str +='</ul></div>';
        		}
        		str +='</div>';
        	}
        	
        }
        str +='</div>';
        str +='<div class="page_list"><dl>';
        var pagetcount = d.count / 20;
        str += pageHtml(curNumber,pagetcount,title,mid,keyword);
        str +='</dl></div>';
        document.getElementById('contentDiv').innerHTML=str;
        }
    });
};

function pageHtml(pageId,pageCount,title,mid,keyword){
	//console.info("pageId="+pageId+",pageCount="+pageCount+",fstr="+fstr);
	var pageSize=5;//显示5个
	var	str="";
	var newPageId=(pageId+1)-pageSize;
	if(newPageId<0) newPageId=0;
	
	if(pageId==1&&newPageId==0){
	}else{
		str="<dt onclick='get_doclist("+mid+",\""+title+"\","+(pageId-1)+",\""+keyword+"\","+curtype+")'><上一页</dt>";
	}

	//console.info("newPageId="+newPageId);
	for(var i=1;i<=pageSize;i++){
		str+="<dd onclick='get_doclist("+mid+",\""+title+"\","+(newPageId+i)+",\""+keyword+"\","+curtype+")'";
		if(pageId==(newPageId+i)){
			str+="class='active'";
		}
			
		str+=">"+(newPageId+i)+"</dd>";
		if((newPageId+i)>=pageCount){
			//pageId=pageCount;
			break;
		}
	}
	if(pageId==pageCount||pageCount==0){
		//console.info("pageId"+pageId+",pageCount="+pageCount);
		//str+="<li class='l_last'>下一页></li>";
	}else{
		str+="<dt  onclick='get_doclist("+mid+",\""+title+"\","+(pageId+1)+",\""+keyword+"\","+curtype+")'>下一页></dt>";
	}
	
	//if()
	//<li class="l1"><上一页</li><li>1</li><li>2</li><li>3</li><li>4</li><li>5</li><li class="l_last">下一页></li>
	return str;
}
function CompanyPageHtml(pageId,pageCount,title,mid,keyword){
	var pageSize=5;//显示5个
	var	str="";
	var newPageId=(pageId+1)-pageSize;
	if(newPageId<0) newPageId=0;
	if(pageId==1&&newPageId==0){
	}else{
		str="<dt onclick='getMoreCompanylist("+mid+",\""+title+"\","+(pageId-1)+",\""+keyword+"\","+curtype+")'><上一页</dt>";
	}
	for(var i=1;i<=pageSize;i++){
		str+="<dd onclick='getMoreCompanylist("+mid+",\""+title+"\","+(newPageId+i)+",\""+keyword+"\","+curtype+")'";
		if(pageId==(newPageId+i)){
			str+="class='active'";
		}
		str+=">"+(newPageId+i)+"</dd>";
		if((newPageId+i)>=pageCount){
			break;
		}
	}
	if(pageId==pageCount||pageCount==0){
	}else{
		str+="<dt  onclick='getMoreCompanylist("+mid+",\""+title+"\","+(pageId+1)+",\""+keyword+"\","+curtype+")'>下一页></dt>";
	}
	return str;
}





function a(){
	
	
	$.ajax({
		  url: "/webword/risk/view/saveMymodule.do",
		  type: "post",
		  data: {mids:'83', keyword:'CPI'},
		  dataType: "json",
		  success: function(d){
			
			  alert(d);
	
				
		  }
});	
}



function test1(){
	if($("#keyword").val()){
		var ckeyword =$("#keyword").val();
		//this.style.background='#f9f9f9';
		 get_doclist(curmid,curtitle,1,ckeyword,curtype);
		
	}
	
	
}

function searchnews(searchword){
//	alert(searchword);
	searchword = encodeURI(searchword);
	window.open('/webword/risk/view/searchallbyword.do?keyword='+searchword);
}

function submitinfo(){
	var station = $("#station").val();
	var responsibility =$("#responsibility").val();
	var interest =$("#interest").val();
	var mykeyword =$("#mykeyword").val();
	if(mykeyword==""){
		mykeyword=" ";
	}
	var items = $(":checkbox[name=module]");
	var ids="";
	for(var i = 0 ; i<items.length ; i++){
		if(items[i].checked){
			ids+=items[i].value+",";
		}
	}
	var param = {station:station,responsibility:responsibility,keyword:mykeyword,interest:interest,mids:ids};
    $.ajax({
        url: "/webword/risk/view/saveMymodule.do",
        async: true,
        type: "post",
        data:param,
        dataType: "json",
        cache: false,
        success: function(d) {
        	$(".industry_options,.box_content,.ui_back").fadeOut(300);
        	  window.location.reload();
        }
    });
	
}
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


function change(value){
	//value=0 相关度 
	//value=1时间
	curtype = value;
	var ckeyword =$("#keyword").val();
	 get_doclist(curmid,curtitle,1,ckeyword,curtype);
}
//点击我的企业
function showCompany(mid,title){
	//curmid = mid;
	curtitle = title;
	var keyword =$("#keyword").val();
	 get_companylist(mid,title,1,keyword,curtype);
}
function get_companylist(mid,title,curNumber,keyword,sorttype) {
	$('body,html').animate({scrollTop: 0}, 0);
	var hiddenid = $("#hiddeninput").val();
	var hiddenMname = $("#sc").val();
	var hiddenId = parseInt(hiddenid);
	curtitle=title;	
	//curmid=mid;
	curtype = sorttype;
    if(hiddenId!=NaN&&hiddenMname!=undefined&&hiddenMname!=""){
    	//如果模糊查询，重新设置mid，title
		mid = hiddenId;
		title=hiddenMname;
	}
	var param = {mid:mid,keyword:keyword,sorttype:sorttype};
	if(curNumber){
		param.curNumber = curNumber;
	}
    $.ajax({
        url: "/webword/risk/view/queryCompanyList.do",
        async: true,
        type: "post",
        data:param,
        dataType: "json",
        cache: false,
        success: function(d) {
        var str ='<h4 class="titleNew">';
        str += title;
        str +="<span id='searchs' class='searchBox'><input id ='hiddeninput' type ='hidden' /><input type='text' class='search' id='sc' placeholder='请选择企业' /><a class='searchBtn' href='javascript:;' onclick='showCompany(\""+hiddenId+"\",\""+hiddenMname+"\")'>查 找</a></span></h4>";
        str += '<div class="list">';
        for (i = 0; i < d.length; i++) {
        	var dm = d[i];
        	var mname = dm.mname;
        	var id = dm.mid;
        	if(dm){
        		str +='<div class="listModle"><h5><img class="point" src="common/img/point.png"/>'+dm.mname;
        		str +="<span class='more'><a href='javascript:;' onclick='showMoreCompany(\""+id+"\",\""+mname+"\")' >更多》</a></span></h5>";
        		console.info(str);
         		for(var j = 0 ; j < dm.list.length ; j++){
         			for(var k = 0 ; k < dm.list[j].data.length; k++){
             			str += '<p title="'+dm.list[j].data[k].title+'"><a class="title"  target="_blank" href="risk/view/getNewsByMid.do?riskId='+ dm.list[j].data[k].crawl_id+'&categoryId='+mid+'&esId='+dm.list[j].data[k].esId+'&id=0">'+dm.list[j].data[k].title+'</a><span class="time">' + (new Date(dm.list[j].data[k].newsDate)).Format("yyyy-MM-dd") + '</span></p>';
         			}
         		}
         		str +='</div>';
        	}
        }
        str +='</div>';
        document.getElementById('contentDiv').innerHTML=str;
        new oSearchSuggest($("#sc"),$("#searchs"),
                "0","30",$(".bodyContent"),8,function(li){
        	$("#hiddeninput").val($(li).attr("mid"));
        });
        }
    });
};
//点击更多企业
function showMoreCompany(mid,title){
	
	curmid = mid;
	curtitle = title;
	var keyword =$("#keyword").val();
	getMoreCompanylist(mid,title,1,keyword,curtype);
}
function getMoreCompanylist(mid,title,curNumber,keyword,sorttype) {
	$('body,html').animate({scrollTop: 0}, 0);
	curtitle=title;
	curmid=mid;
	curtype = sorttype;
	var param = {mid:mid,keyword:keyword,sorttype:sorttype};
	if(curNumber){
		param.curNumber = curNumber;
	}
    $.ajax({
        url: "/webword/risk/view/queryCompanyList.do",
        async: true,
        type: "post",
        data: param,
        dataType: "json",
        cache: false,
        success: function(d) {
        var str ='<h4 class="titleNew">';
        str +=title;
        str +='</h4>';
        str += '<div class="list">	';
        
        for (i = 0; i < d.data.length; i++) {
        	var dm = d.data[i];
        	if(dm){
        		str +='<div class="listNew">';
        		str += "<h5 title='"+dm.title+"'><img class='point' src='common/img/point.png' /><a  class='title'  target='_blank' href='risk/view/getNewsByMid.do?riskId="+dm.crawl_id+"&categoryId="+curmid+"&esId="+dm.esId+"&id=0'>"+dm.title+"</a><span class='time'>" + (new Date(dm.newsDate)).Format("yyyy-MM-dd") + "</h5>";
        		str +='</div>';
        	}
        }
        str +="</div>";
        str +='<div class="page_list"><dl>';
        var pagetcount = d.count / 20;
        str += CompanyPageHtml(curNumber,pagetcount,title,mid,keyword);
        str +='</dl></div>';
        document.getElementById('contentDiv').innerHTML=str;
        }
    });
};

//点击全部企业
function showAllCompany(mid,title){
	curmid = mid;
	curtitle = title;
	var keyword =$("#keyword").val();
	getAllcompanylist(mid,title,1,keyword,curtype);
}

function getAllcompanylist(mid,title,curNumber,keyword,sorttype) {
	$('body,html').animate({scrollTop: 0}, 0);
	var hiddenid = $("#hiddeninput").val();
	var hiddenMname = $("#sc").val();
	var hiddenId = parseInt(hiddenid);
	curtitle=title;
	curmid=mid;
	curtype = sorttype;
	if(hiddenId!=NaN&&hiddenMname!=undefined&&hiddenMname!=""){
	    	//如果模糊查询，重新设置mid，title
			mid = hiddenId;
			title=hiddenMname;
		}
	var param = {mid:mid,keyword:keyword,sorttype:sorttype};
	if(curNumber){
		param.curNumber = curNumber;
	}
    $.ajax({
        url: "/webword/risk/view/queryCompanyList.do",
        async: true,
        type: "post",
        data:param,
        dataType: "json",
        cache: false,
        success: function(d) {
        	
        var str ='<h4 class="titleNew">';
        str +=title;
        str +="<span id='searchs' class='searchBox'><input id ='hiddeninput' type ='hidden' /><input type='text' class='search' id='sc' placeholder='请选择企业'/><a class='searchBtn' href='javascript:;' onclick='showCompany(\""+hiddenId+"\",\""+hiddenMname+"\")'>查 找</a></span></h4>";
        str += '<div class="list">';
        
        for (i = 0; i < d.data.length; i++) {
        	var dm = d.data[i];
        	var temparr = new Array();
        	temparr = dm.categoryId.split(" ");
        	var cId = temparr[0];
        	if(dm){
        		str +='<div class="listNew">';
        		str += "<h5 title='"+dm.title+"'><img class='point' src='common/img/point.png'/><a class='title'  target='_blank' href='risk/view/getNewsByMid.do?riskId="+dm.crawl_id+"&categoryId="+cId+"&esId="+dm.esId+"&id=0'>"+dm.title+"</a><span class='time'>" + (new Date(dm.newsDate)).Format("yyyy-MM-dd") + "</h5>";
        		str +='</div>';
        	}
        }
        str +="</div>";
        str +='<div class="page_list"><dl>';
        var pagetcount = d.count / 20;
        str += CompanyPageHtml(curNumber,pagetcount,title,mid,keyword);
        str +='</dl></div>';
        document.getElementById('contentDiv').innerHTML=str;
        new oSearchSuggest($("#sc"),$("#searchs"),
                "0","30",$(".bodyContent"),8,function(li){
        	$("#hiddeninput").val($(li).attr("mid"));
        });
        }
    });
};

//联想框
function oSearchSuggest(input, suggestWrap, cLeft, cTop, closeB, pageSize,callback) {
	var flag=false;//标示,如果值是输入的联想内容,翻页的时候触发blur事件,不清空文本框值
    var inputVal=input.val();//联想框中的值,当点击翻页的时候还原该值
    var init = function() {
        input.bind('click',
        function(event) {
        	
            sendKeyWordToBack();
            event.stopPropagation();
        });
        input.bind('keyup', function(){
        	inputVal=input.val();
        	flag=true;
        	sendKeyWordToBack();
            event.stopPropagation();
        });
        input.bind('blur',
        function() {
            input.val('');
            $("#hiddeninput").val(null);//隐藏框中值设置为空
        });
        closeB.bind('click',
        function() {
            $('#form_cities').remove();
        });
    };
    var sendKeyWordToBack = function(pageId) {
        dataDisplay(pageId || 1);
    };
    var dataDisplay = function(pageId) {
        //alert("aaa");
    	if(!pageId) pageId=1;
    	var valText = $.trim(input.val());
    	var data ;
        $.ajax({
            type: "POST",
            url: "/webword/risk/view/searchCompanyList.do",
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
        $('#form_cities').remove();
        if (! (data && data.count > 0 && data.list && data.list.length)) {
            return;
        }
        var newDiv = "<div id='form_cities'><ul id='panel_cities'>";
        for (var i = 0; i < data.list.length; i++) {
        	newDiv += "<li mid=" + data.list[i].mid +">" + data.list[i].mname + "</li>";
        }
        var pageCount = data.count % pageSize == 0 ? data.count / pageSize: Math.ceil(data.count / pageSize) ;
        var pageId = data.pageId;
        if (data && data.count > 0 && data.list && data.list.length && pageCount>1) {
        	newDiv += "</ul><div id='flip_cities'><a href='javascript:void(0);'>«&nbsp;向前</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);'>向后&nbsp;»</a></div></div>";
        }
        suggestWrap.append(newDiv);
        $('#form_cities').show().css({
            'left': cLeft + 'px',
            'top': cTop + 'px'
        });
        $('#form_cities').find('li').mouseover(function() {
            $(this).addClass('hover').siblings().removeClass('hover');
        });
        $('#form_cities').find('li').bind('click',
        function() {
            input.val($(this).html());
            callback(this);
            $('#form_cities').remove();
        });
        $('#flip_cities').find('a').eq(0).bind('click',function(event) {
        	input.val(inputVal);
        	input.focus();
        	if(pageId>1){
        		sendKeyWordToBack(pageId-1);
        	}
        	event.stopPropagation();
        });
        $('#flip_cities').find('a').eq(1).bind('click',function(event) {
        	input.val(inputVal);
        	input.focus();
        	if(pageId<pageCount){
        		sendKeyWordToBack(pageId+1);
        	}
        	event.stopPropagation();
        });
    };
    init();
};

