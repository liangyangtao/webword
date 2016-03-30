<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--  -->
<div class="header">
	<div class="middle">
		<span class="logo"><a href="risk/view/home.do"><img src="common/img/logo.png"/></a></span>
		<!-- 登录前 -->
		<c:if test="${user==null}">
		<span class="right"><img src="common/img/tel.png"/>010-63368810<a href="risk/view/tologin.do">登录</a><a href="risk/view/toregist.do">注册</a></span>
		</c:if>
		<!-- 登陆后 -->
		<c:if test="${user!=null}">
		<span class="right login"><img src="common/img/tel.png"/>010-63368810<a>${user.userName}</a><a class="down2 menu2_down2"></a></span>
		<ul class="udown_menu"><li><a href="view/home.do">知识创享平台</a></li><li><a href="risk/view/touser.do" target="_blank">账号设置</a></li><li class="ast"><a href="javascript:;" onclick="logout()">退出登录</a></li></ul>
		</c:if>
	</div>
</div>
<div class="menu img_change">
	<div class="middle">
		<ul>
			<!-- <li><a class="fengxian" onclick="remindEnd()">我的风险预警</a></li> -->
			<!-- 点击“我的风险预警”，如未登录，跳转登录页面；如果已登录，之前没有设置过栏目，则跳出栏目设置弹框，若之前设置过，则跳转我的风险预警页面 调用：ser_box() -->
			<li><a class="fengxian" onclick="transfer()">我的风险预警</a></li>
			<c:forEach items="${listModule}" var="module" varStatus="status">
				<li 
				<c:if test="${module.mid==id}">
				class="active"
				</c:if>
				<c:if test="${status.last}">
				class="last"
				</c:if>
				 ><a href="risk/view/home.do?id=${module.mid}&mname=${module.mname}">${module.mname}</a></li>
			</c:forEach>
			<!--<li class="active"><a>巴塞尔协议II风险</a></li>
			<li><a>区域风险</a></li>
			<li class="last"><a>行业风险</a></li> -->
		</ul>
	</div>
</div>



<script>
function transfer(){

	window.location.href = "/webword/risk/view/myrisk.do";
}


function remindEnd(){
	var islogin =isLogin();
	if(islogin){
		$.ajax({
	        type: "post",
	        url: "risk/view/getTipsMsg.do",
	        async:false,
	        contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	        success: function(data) {
	        	var result=eval(data);
	        	if(result.status==23){//试用期已经结束
	        		$(".day_tips1,.box_content,.ui_back").fadeIn(300);//显示
	        		$(".sbox_ h3 img,.sb_button .a1").click(function(){
						location.href="/webword/risk/view/myrisk.do";
				   });
				} else {
					window.location.href = "/webword/risk/view/myrisk.do";
				}
			}
		});
		} else {
			window.location.href = "/webword/risk/view/myrisk.do";
		}
	}
</script>