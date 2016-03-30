<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>
<%@ page import="com.database.bean.WordColumnGroup"%>
<%@ page import="com.database.bean.WordColumn"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>

<%--<%
	List<WordColumnGroup> leftGroups=(List<WordColumnGroup>)request.getAttribute("leftGroup");
	//leftGroups取值需要进入第二层List
	WordColumnGroup l1=(WordColumnGroup)leftGroups.get(0);
	//第一层菜单栏
	List<WordColumn> leftList1=l1.getColumns();
	 List list = new ArrayList();   
     list.add("gouchao");
     list.add("gouchao");
     list.add("gouchao");
     list.add("gouchao");
    
	

%>--%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>知识创享网-文档首页</title>
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>"/>
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>" />
</head>
<body>
	<!-- header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- header_end -->

	<!-- body -->
	<div name="区域1" class="body_div1">
		<div name="sideMenu" class="sideMenu">
			<c:forEach items="${leftGroup.columns}" var="left1" end="4">
				<dl>
					<dt>
						<a title="${left1.columnName}"
							href="view/column.do?columnId=${left1.id}">${left1.columnName}</a>
					</dt>
					<dd>
						<c:forEach items="${left1.columns}" var="left2" varStatus="status">
							<c:if test="${status.count%2!=0}">
								<a title="${left2.columnName}"
									href="view/column.do?columnId=${left2.id}">${left2.columnName}</a>
							</c:if>
							<c:if test="${status.count%2==0}">
								<a title="${left2.columnName}"
									href="view/column.do?columnId=${left2.id}">${left2.columnName}</a>
								<br>
							</c:if>
						</c:forEach>
					</dd>
				</dl>
			</c:forEach>


			<p class='MyChannel' style="display:none">
				<span>+</span><em>自定义频道</em>
			</p>
		</div>
		<div name="banner" class="banBody">
			<div class="banner-box">
				<div class="bd">
					<ul>
						<li><img src="word/img/03.jpg" width='710' height='320' />
						</li>
						<li><img src="word/img/04.jpg" width='710' height='320' />
						</li>
					</ul>
				</div>
				<div class="banner-btn">
					<a class="prev" href="javascript:void(0);"></a> <a class="next"
						href="javascript:void(0);"></a>
					<div class="hd">
						<ul></ul>
					</div>
				</div>
			</div>
			<div class="banBot">
				<a target="_blank"
					href="http://www.unbank.info/page/sid/1/pid/1714.shtml"><img
					src="word/img/01.jpg" width='355' height='116' /> </a> <a
					target="_blank"
					href="http://www.unbank.info/page/sid/1/pid/1710.shtml"><img
					src="word/img/02.jpg" width='355' height='116' /> </a>
			</div>
		</div>
		<ul name="banner" class="ul2" style="display:none">
			<li class="li1"><img src="word/img/banner5.png" />
			</li>
			<li><img src="word/img/banner6.png" />
			</li>
		</ul>
		<ul class="ul3">
			<li class="li1"><c:if test="${empty user}">
					<p class="nologin">
						加入知识创享网<br> <a href="javascript:show_login();">立即加入</a>
					</p>
				</c:if> <c:if test="${not empty user}">
					<dl>
						<dt>
							<a href="user/edit.do"><img src="word/img/tou.gif"> </a>
						</dt>
						<dd>
							<strong><a href="user/edit.do">${user.userName}</a> </strong><br>
							<p>
								<span class="green">${userMoney.money == null ? 0 : userMoney.money}</span>&nbsp;创享币
								<a href="user/recharge.do" class="recharge">充 值</a>
							</p>
						</dd>
					</dl>
					<p style="padding:5px 16px 10px">
								<a href="user/article.do">我的文档</a>：<a href="user/article.do"
									class="green">${articleCount}</a>&nbsp;&nbsp;<a
									href="user/upload.do">我的上传</a>：<a href="user/upload.do"
									class="green">${uploadCount}</a>
							</p>
					<a href="user/upload.do?flag=1" class="btn"><em></em>上传我的文档</a>
				</c:if></li>

			<li class="li2">
				<dl>
					<dt>
						<img src="word/img/news_title2.png" />
					</dt>
					<c:forEach items="${newsList.data}" var="news">
						<dd>
							<a target="_blank" href="news/view/showNewsDetails.do?newsId=${news.crawl_id}"
								title="${news.title}">${news.title}</a>
						</dd>
					</c:forEach>
					<i style="display:none;"></i>
				</dl></li>
			<li class="li3" style="display:none;">
				<dl>
					<dd>【资讯】创业公司怎样开人</dd>
					<dd>【论坛】小米4年600亿背后</dd>
					<dd>【论坛】富翁都是闯出来的</dd>
					<dd>【资讯】柳传志柳青父女对决</dd>
					<dd>【资讯】乔布斯15大励志箴言</dd>
					<dd>【资讯】日本马桶盖O2O</dd>
				</dl></li>
		</ul>
	</div>
	<div name="区域1底部" class="body_div1_b">
		<div class="bd_bg">
			<c:forEach items="${midGroup.columns}" var="mid1" end="3">
				<div>
					<dl>
						<dt>${mid1.columnName}</dt>
						<c:forEach items="${mid1.columns}" var="mid2" varStatus="status">
							<c:if test="${status.count%3!=0}">
								<dd>
									<a href="view/column.do?columnId=${mid2.id}"
										title="${mid2.columnName}">${mid2.columnName}</a>
								</dd>
							</c:if>
							<c:if test="${status.count%3==0}">
								<dd>
									<a href="view/column.do?columnId=${mid2.id}"
										title="${mid2.columnName}">${mid2.columnName}</a>
								</dd>
								<br>
							</c:if>
						</c:forEach>
					</dl>
					<i></i>
				</div>
			</c:forEach>
		</div>
	</div>

	<div name="区域1底部" class="body_div2_b">
		<h2>&nbsp;最新专区</h2>
		<ul class="lastArea">
			<c:forEach items="${latestJournal.list}" var="lastArea">
				<li><div>
						<a target="_blank"
							href="view/preview.do?journalId=${lastArea.journalId}"
							title="${lastArea.name}"><img src="${lastArea.cover}">
						</a><span class="icon icon1"><em>${lastArea.type}</em> </span>
						<a target="_blank" href="view/preview.do?journalId=${lastArea.journalId}"><p class="time">
							<span>更新<script type="text/javascript">
								if ("${lastArea.updateTime}") {
									var long = parseInt("${lastArea.updateTime}");
									var date = new Date(long);
									document
											.write(date.getFullYear()
													+ "-"
													+ (date.getMonth() < 9 ? ("0" + (date
															.getMonth() + 1))
															: (date.getMonth() + 1))
													+ "-"
													+ (date.getDate() < 10 ? ("0" + date
															.getDate())
															: date.getDate()));
									//console.info(date);
								}
							</script> </span>
						</p></a>
					</div>
					<h5>
						<a title="${lastArea.name}" target="_blank"
							href="view/preview.do?journalId=${lastArea.journalId}">${lastArea.name}</a>
					</h5>
				</li>
			</c:forEach>
		</ul>
		<div class="lastArea2">
			<ul>
				<c:forEach items="${latestDoc}" var="latestDoc" varStatus="status">
					<c:if test="${status.count<=5 }">
						<li><p>
								<em class="middot"></em>
								<c:if test="${latestDoc.articleFormat == ''}">
									<span class="ic-h ic-h-doc"></span>
								</c:if>
								<c:if test="${latestDoc.articleFormat != ''}">
									<span class="ic-h ic-h-${latestDoc.articleFormat}"></span>
								</c:if>
								<a title="${latestDoc.articleName}" target="_blank"
									href="view/preview.do?articleId=${latestDoc.articleId}"> <c:if
										test="${fn:length(latestDoc.articleName)>23}">${fn:substring(latestDoc.articleName,0,23)}...</c:if>
									<c:if test="${fn:length(latestDoc.articleName)<=23}">${latestDoc.articleName}</c:if>
								</a>
							</p>
							<a title="${latestDoc.articleName}" target="_blank"
									href="view/preview.do?articleId=${latestDoc.articleId}"><h5>
								<script type="text/javascript">
									if ("${latestDoc.updateTime}") {
										var long = parseInt("${latestDoc.updateTime}");
										var date = new Date(long);
										document
												.write(date.getFullYear()
														+ "-"
														+ (date.getMonth() < 9 ? ("0" + (date
																.getMonth() + 1))
																: (date
																		.getMonth() + 1))
														+ "-"
														+ (date.getDate() < 10 ? ("0" + date
																.getDate())
																: date
																		.getDate()));
									}
								</script>
							</h5></a>
						</li>
					</c:if>
				</c:forEach>
			</ul>
			<ul>
				<c:forEach items="${latestDoc}" var="latestDoc" varStatus="status">
					<c:if test="${status.count>5 }">
						<li><p>
								<em class="middot"></em>
								<c:if test="${latestDoc.articleFormat == ''}">
									<span class="ic-h ic-h-doc"></span>
								</c:if>
								<c:if test="${latestDoc.articleFormat != ''}">
									<span class="ic-h ic-h-${latestDoc.articleFormat}"></span>
								</c:if>
								<a title="${latestDoc.articleName}" target="_blank"
									href="view/preview.do?articleId=${latestDoc.articleId}"><c:if
										test="${fn:length(latestDoc.articleName)>23}">${fn:substring(latestDoc.articleName,0,23)}...</c:if>
									<c:if test="${fn:length(latestDoc.articleName)<=23}">${latestDoc.articleName}</c:if>
								</a>
							</p>
							<h5>
								<script type="text/javascript">
									if ("${latestDoc.insertTime}") {
										var long = parseInt("${latestDoc.insertTime}");
										var date = new Date(long);
										document
												.write(date.getFullYear()
														+ "-"
														+ (date.getMonth() < 9 ? ("0" + (date
																.getMonth() + 1))
																: (date
																		.getMonth() + 1))
														+ "-"
														+ (date.getDate() < 10 ? ("0" + date
																.getDate())
																: date
																		.getDate()));
									}
								</script>
							</h5>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
	<!-- 回到顶部按钮  -->
	<div class="backToTop">
		<a class="reader-feedback" style="display:none" href="javascript:void(0);" title="新版反馈"
			hidefocus="true"><span></span>反馈</a> <a id="toTop" href="javascript:void(0);"
			title="回到顶部" hidefocus="true"><span class="bg-index s-ic-h top"></span>
		</a>
	</div>
	<!-- 回到顶部按钮-end  -->
	<!-- body_end -->
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
	<script type="text/javascript" src="word/js/jquery.SuperSlide.2.1.1.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript">
		/*首页banner*/
	$(document).ready(function(){	
		if($(".banner").length>0){
			$(".banBody").hover(function(){
				$(this).find('.prev,.next').stop(true,false).fadeTo("show",1);
			},function(){
				$(this).find('.prev,.next').stop(true,false).fadeTo("show",0);
			});	
			$(".banner-box").slide({
				titCell:".hd ul",
				mainCell:".bd ul",
				effect:"fold",
				interTime:3500,
				delayTime:500,
				autoPlay:true,
				autoPage:true, 
				trigger:"click" 
			});
		}	
	});
	</script>
</body>
</html>
