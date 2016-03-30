<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<title>${news.title}</title>
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" type="text/css" href="word/css/bshare.css?v=<%=System.currentTimeMillis()%>" />
</head>
<body>

	<div name="header" class="header">
		<div class="bg">
			<span class="h_left"><a
				href="http://www.zhishicx.com/webword/view/index.do">首页</a> <span>|</span>
				<a>客户端</a> <span>|</span> 客服热线：010-63368810</span> <span class="h_right">您好，欢迎来到知识创享网！

				<a style="display:"
				href="http://www.zhishicx.com/webword/user/journal/index.do"
				class="username" onmouseover="showHeadUl();"> 创享平台用户</a> <a
				class="shop_car" id="topShopCar" style="display:none"
				href="http://www.zhishicx.com/webword/cart/index.do">购物车<span
					id="cartCount">（0）</span> </a> </span>

			<ul class="hideUl" onmouseleave="hideHeadUl();" style="right:10px;"
				onclick="hideHeadUl();">
				<li class="line"><a
					href="http://www.zhishicx.com/webword/user/journal/index.do">我的文库</a>
				</li>
				<li class="line"><a
					href="http://www.zhishicx.com/webword/user/recharge.do">我的账户</a>
				</li>
				<li><a href="../../common/css/javascript:;"
					onclick="showDiv(0);">退出</a>
				</li>
			</ul>

		</div>
	</div>
	<div name="header2" class="header2" style="border-bottom:2px solid #d0d0d0;">
		<a href="http://www.zhishicx.com/webword/view/home.do" style="float:left"><img
			class="logo img" src="word/img/header1.png" style="margin-right:0px;"> </a>
			<p class="news_blank_naviga"><a href="###" class="a_h1">新闻</a><a href="###" class="a_h2">&gt;投资银行</a>&gt;正文	</p>	
			<ul class="ul" style="float:right;height:40px;">			
			<li class="li2"><input class="search_input"
				placeholder="这里搜索您关注的资讯" onfocus="this.placeholder=''"
				onblur="if(this.value==''){this.placeholder='这里搜索您关注的资讯'}"
				type="text"> <span><a class="search">搜&nbsp;&nbsp;索</a>
			</span></li></ul>
	</div>	
	<!-- body -->
	<div class="userCont">
		
		<div class="newsContent" >
			<div class="left">
				<h4 class="title">${news.title}</h4>
				<!-- <div class="message"><fmt:formatDate value="${date}" type="both"/></div> -->
				<div class="message">2016-01-20 08:38:20　来源: <span>新京报即时新闻(北京)</span><br>文章关键词： <span>刘维佳 中组部</span>
				<div class="share-sns">
				    <div class="bshare-custom"><span>分享：</span>
				        <div class="bsPromo bsPromo2"></div>
				        <a title="分享到新浪微博" class="bshare-sinaminiblog"></a> 
				        <a title="分享到微信" class="bshare-weixin"></a> 
				        <a title="分享到QQ空间" class="bshare-qzone"></a> 
				        <a title="更多平台" class="bshare-more more-style-addthis"></a>
				        <span style="margin-left:10px">收藏：</span><span class="bs-collect"></span> 				        
				    </div>
				    <div class="bs-collect-tip">收藏成功，<a href="###">查看我的收藏</a></div>
				</div>
				</div>
				
				
				<script type="text/javascript" charset="utf-8"
						src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
				<script type="text/javascript" charset="utf-8"
					src="word/js/bshareC0.js?v=<%=System.currentTimeMillis()%>"></script>
				<div class="clearfloat"></div>	
				<div class="t_content">					
					<div>
						${news.content}
					</div>					
			</div>
			</div>
			<div class="right">
				<h4 class="r_title">
					<a>新闻推荐</a>
				</h4>
				<ul class="list">
					<c:forEach items="${moreLike}" var="news"  varStatus="status">
						<li <c:if test="${status.index<3}">class="t"</c:if>><i>${status.index+1}</i><a target="_blank"
							href="view/news.do?newsId=${news.crawl_id}"
							title="${news.title}">${news.title}</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<!-- body_end -->
	<!-- 回到顶部按钮  -->
	<div class="backToTop">
		<a class="reader-feedback" style="display:none" href="javascript:void(0);" title="新版反馈"
			hidefocus="true"><span></span>反馈</a> <a id="toTop" href="javascript:void(0);"
			title="回到顶部" hidefocus="true"><span class="bg-index s-ic-h top"></span>
		</a>
	</div>
	<!-- 回到顶部按钮-end  -->
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
	
	<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript">
		function hideCollectTip(){$('.bs-collect-tip').hide()}
		$(function(){
			$('.bs-collect').live('click',function(){					
				if($(this).hasClass('bs-collect_cur')){
					$(this).removeClass('bs-collect_cur');				
				}else{ 
					$(this).addClass('bs-collect_cur');
					$('.bs-collect-tip').show();
					window.setTimeout('hideCollectTip()', 2000);	
				}
			})
					
		});	
		$(".bshare-custom a.bshare-sinaminiblog").click(function() {          
            var tt=	$('h4.title').text();
            var imgUrl=$('.t_content img').eq(0).attr('src'); 
            bShare.addEntry({  
                title: "",  
                url: "",  
                summary:""+"(分享自 知识创享网)",                 
                pic:  imgUrl
            });  
        }) 
	</script>
</body>
</html>
