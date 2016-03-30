<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>我的账户-充值</title>
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>">
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>">
</head>
<body>
<!-- header -->
<jsp:include page="../myCenter.jsp"></jsp:include>
<!-- header_end --> 

<!-- body -->
<div class="userCont">
    <div class="userTopBg">
        <div class="userTop">
              <h3 class="user_name"><em></em>${user.userName}</h3>
            <img src="word/img/tou.gif" class="user_img" /></div>
    </div>
    <div class="clearfix userBody">
       <jsp:include page="user-left.jsp"></jsp:include>
       <div class="userRight">
            <h1>充值</h1>	
            <div class="userR_ edit_">
                <form class="userR_form" id="userR_form">
                    <input type="hidden" id="userId" name="userId" value="71">
                    <ul>
                        <li><span class="w75">当前账号：</span>
                            <p>${user.userName}</p>
                        </li>
                        <li><span class="w75">我的创享币：</span>
                            <p>${userMoney}</p>                            
                        </li>
                        
                        <li><span class="w75">充值数量：</span>                         
                            <input type="text"  name="rechargeMoney" id="rechargeMoney" maxlength="9" value='' style="width:155px;" 
                            onkeyup="this.value=this.value.replace(/\D|^0/g,'')" onafterpaste="this.value=this.value.replace(/\D|^0/g,'')">
                            <span>创享币</span><em style="color:#aaa">1创享币=1元</em>
                        </li>
                        <li style="margin-top:20px;"><span class="w75">支付方式：</span>
                            <p class="pay_ways"><span class="w110 current"><img src="word/img/zhifubao.png"></span>
                     </p>                            
                        </li>                        
                        <li class="form_tips">
                            <p class="redTip"></p>
                        </li>
                        <li class="last">
                            <input type="button" value="立即支付" class="btn-login btn-rechar" 
                            onclick="recharge();"/>                           
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
<iframe src="" name="uploadIframe" id="uploadIframe" style="display:none"></iframe>
<jsp:include page="user-window.jsp"></jsp:include>
<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/ajaxfileupload.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/alert.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="word/js/onSearch.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
<script type="text/javascript">
//充值
function recharge(){
    if (isLogin()){
    	var rechargeMoney = $("#rechargeMoney").val();
		if(rechargeMoney != null && rechargeMoney > 0){
			showRechSucc();//打开等待充值信息的窗口
		    window.open("alipay/userorder.do?rechargecount="+$("#rechargeMoney").val());
		}else{
			new altInfo({
				text : "充值数量不可为空！"
			});
		}
	}
}
//刷新当前页面
function reload(){
	location.reload();
}
</script>
</html>