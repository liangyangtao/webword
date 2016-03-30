<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta name="keywords" content="${risk.keyWords}">
	<meta name="description" content="${risk.title}">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>	
	<title>${risk.title}|<c:forEach items = "${modulPathNewList}" var="module" varStatus="status">
	${module.mname}|
	</c:forEach>
	银联信</title>
	<link rel="stylesheet" type="text/css" href="common/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="common/css/news_content.css"/>	
	<script type="text/javascript" src="common/js/jquery-1.8.3.min.js"></script>	
	<script type="text/javascript" src="common/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="jsp/riskview/js/moreLike.js"></script>
	<script type="text/javascript" src="common/js/common.js"></script>
	
	<script charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=5386391856;pophcol=2&amp;lang=zh"></script>
  </head>
  <body onload="getMoreLikeRisks(<c:out value="${risk.crawl_id}"/>,<c:out value="${categoryId}"/>)">
  
  <!-- header -->
<jsp:include page="header.jsp"></jsp:include>
  
<div class="direct">当前位置：
<c:forEach items = "${modulePathList}" var="module" varStatus="status">
<a class="d">${module.mname}</a>
<c:if test="${!status.last}">&gt;</c:if>
</c:forEach>
</div>
<div class="content">
	<div class="left">
		<h4 class="title"><c:out value="${risk.title}"/></h4>
		<div class="message"><fmt:formatDate value="${risk.newDate}" pattern="yyyy-MM-dd HH:mm"/>
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;来源：${risk.webName}</div>
		<div class="t_content">
			<p class="share_text">
				<c:out value="${risk.content}" escapeXml="false"/>
			</p>
		</div>
		<div class="share">
			分享到：
			<a class="share_btn" title="新浪微博" sid="sinaminiblog" ><img src="common/img/sh_img1.png"/></a>
			<a class="share_btn" title="QQ空间" sid="qzone"><img src="common/img/sh_img2.png"/></a>
			<a class="share_btn" title="腾讯微博" sid="qqmb" ><img src="common/img/sh_img3.png"/></a>
			<a class="share_btn" title="微信" sid="weixin" ><img src="common/img/sh_img4.png"/></a>
			<a class="share_btn" title="电子邮件" sid="email" ><img src="common/img/sh_img5.png"/></a>
			<a class="share_btn1" title="更多" onclick="javascript:bShare.more(event);return false;"><img src="common/img/sh_img6.jpg"/></a>
			<c:if test="${canShare=='Y'}">
				<c:if test="${isCollection=='Y'}">
					<a class="collection"><span class="hidden">已</span>收藏<i></i></a>
				</c:if>
				<c:if test="${isCollection=='N'}">
					<input id="esId_hidden"  style="display:none" value="${risk.esId}"/>
					
					<p id="content_hidden" style="display:none">
						<c:out value="${risk.content}"/>
					</p>
					<a class="collection active"><span class="hidden">已</span>收藏<i></i></a>
				</c:if>
			</c:if>
		</div>
	</div>
	<div class="right">
		<h4 class="r_title"><a>相关文章推荐</a></h4>
		<ul class="list">
			
		</ul>
	</div>
</div>


<div name="遮挡背景" class="ui_back"></div>
<div class="box_content">
<!--  试用到期 温馨提示1  -->
	<div  class="sbox_ day_tips day_tips1">
			<h3>温馨提示<img src="common/img/s_exit.png" /></h3>
			<div class="sb_middle">
				<p class="p1">尊敬的:${user.userName}</p>
				<p class="p2"> &nbsp;&nbsp;&nbsp;&nbsp;您的免费试用已到期，为不影响您的正常使用，请您及时联系客服进行充值缴费。<br>&nbsp;&nbsp;&nbsp;&nbsp;联系电话: 010-63368810</p>
			</div>
			<div class="sb_button" ><a class="a1">知道了</a></div>
		</div>
</div>

<!-- footer -->
<jsp:include page="footer.jsp"></jsp:include>

<script>
$(".share_btn").click(function(){
		var url=['http://t.sina.com.cn','http://qzone.qq.com','http://t.qq.com','http://weixin.qq.com/',''];
        var index=$(this).index();
        var title=$(".content h4.title").text();
        var summary=$(".share_text").html();
        var e=arguments[0] || window.event;//兼容火狐
		var sid = $(this).attr("sid")||"sinaminiblog";
			bShare.entries[0].title = title;
			bShare.entries[0].url =url[index];
			bShare.entries[0].summary = summary;
			bShare.share(e,sid,0);
    });
    bShare.addEntry({
        title: " ",                         //分享标题
        url: "",                          //分享url
        summary:"" ,                        //分享内容
        pic:''                              //分享图片
    });
    $(".hidden").click(function(){
		alert(1);
    });

</script>
  
  
  
  </body>
</html>
