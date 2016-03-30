<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*" %>
<%@ page import="com.database.bean.WordColumnGroup" %>
<%@ page import="com.database.bean.WordColumn" %>
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
<title>购物车</title>
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>">
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>">
</head>
<body>
<!-- header -->
	<jsp:include page="../header.jsp"></jsp:include>
<!-- header_end --> 
<style>
.menu{display:none;}
</style>
<!-- body -->
<div class="shopcarCheckout">
	<c:if test="${empty carts}">
	<div class="shopcarEmpty">
    	<img src="word/img/shopcar_empty.png" />
    	<p>您的购物车还是空的<br>赶紧行动吧？</p>
    </div>
    </c:if>
    <c:if test="${not empty carts}">
    <h1>
        <p>购物车&nbsp;<c:if test="${not empty map and map.result==0}">${map.msg}</c:if></p>
    </h1>
    <h2> <span>全部商品</span> </h2>
    <div class="cart-table-th clearfix">
        <div class="th th-chk">
            <div class="cart-checkbox">
                <input type="checkbox" value="true" name="select-all" class="J_CheckBoxShop" onchange="checkAll(this);">
                <label for="J_SelectAllCbx1">全选</label>
            </div>
        </div>
        <div class="th th-item">
            <div class="td-inner">商品信息</div>
        </div>
        <div class="th th-amount">
            <div class="td-inner">类&nbsp;&nbsp;&nbsp;&nbsp;型</div>
        </div>
        <div class="th th-price">
            <div class="td-inner">价&nbsp;&nbsp;&nbsp;&nbsp;格</div>
        </div>
        <div class="th th-op">
            <div class="td-inner">操&nbsp;&nbsp;&nbsp;&nbsp;作</div>
        </div>
    </div>
    <div class="item-body clearfix">
    <form id="cartForm" action="cart/pay.do" method="post">
	    <c:forEach items="${carts}" var="cart">
	    	<ul class="item-content clearfix <c:if test="${cart.flag!=0}">alreadyBg</c:if>">
	            <li class="td td-chk">
	                <div class="td-inner">
	                    <input <c:if test="${cart.flag!=0}">disabled</c:if> type="checkbox" name="cartIds" class="J_CheckBox" onchange="checkedOne(this);" value="${cart.cartId}"  resoureType="${cart.resoureType}" journalId="${cart.journalId}"  articleId="${cart.articleId}" price="<c:if test="${cart.resoureType=='journal'}">${cart.journalPrice}</c:if><c:if test="${cart.resoureType!='journal'}">${cart.articlePrice}</c:if>">
	                </div>
	            </li>
	            <li class="td td-item">
	                <div class="td-inner">
	                	<c:if test="${cart.resoureType=='journal'}">
		                    <div class="item-pic"><a target="_blank" href="view/preview.do?journalId=${cart.journalId}" title="${cart.journalName}"><img src="${cart.cover}" width="74" height="85"></a><span class="icon icon1"><em>${cart.type}</em> </span></div>
		                    <div class="item-info"> <a target="_blank" href="view/preview.do?journalId=${cart.journalId}" title="${cart.journalName}">
		                        <h3>
									<c:if test="${fn:length(cart.journalName)>16}">${fn:substring(cart.journalName,0,16)}...</c:if>
									<c:if test="${fn:length(cart.journalName)<=16}">${cart.journalName}</c:if>
								</h3>
		                        <p>${cart.type}</p>
		                        </a> 
		                    </div>
	                    </c:if>
	                    <c:if test="${cart.resoureType=='journalarticle'}">
		                    <div class="item-pic"><a target="_blank" href="view/preview.do?journalId=${cart.journalId}" title="${cart.journalName}"><img src="${cart.cover}" width="74" height="85"></a><span class="icon icon1"><em>${cart.type}</em> </span></div>
		                    <div class="item-info"> <a target="_blank" href="view/preview.do?journalId=${cart.journalId}" title="${cart.articleName}">
		                        <h3>
									<c:if test="${fn:length(cart.journalName)>16}">${fn:substring(cart.journalName,0,16)}...</c:if>
									<c:if test="${fn:length(cart.journalName)<=16}">${cart.journalName}</c:if>
								</h3>
								</a> 
								<a target="_blank" href="view/preview.do?articleId=${cart.articleId}" title="${cart.articleName}">
		                        	<p>
		                        	<c:if test="${fn:length(cart.articleName)>16}">${fn:substring(cart.articleName,0,16)}...</c:if>
									<c:if test="${fn:length(cart.articleName)<=16}">${cart.articleName}</c:if>
									</p> 
		                        </a> 
		                    </div>
	                    </c:if>
	                    <c:if test="${cart.resoureType=='article'}">
		                    <div class="item-info"> <a target="_blank" href="view/preview.do?articleId=${cart.articleId}" title="${cart.articleName}">
		                        <h3>
									<c:if test="${fn:length(cart.articleName)>16}">${fn:substring(cart.articleName,0,16)}...</c:if>
									<c:if test="${fn:length(cart.articleName)<=16}">${cart.articleName}</c:if>
								</h3>
		                        <!-- <p>全年12期</p> -->
		                        </a> 
		                    </div>
	                    </c:if>
	                </div>
	            </li>
	            <li class="td td-amount">
	                <div class="td-inner"><em class="price-original"><c:if test="${cart.resoureType=='journalarticle'}">期刊文档</c:if><c:if test="${cart.resoureType=='article'}">非期刊文档</c:if><c:if test="${cart.resoureType=='journal'}">期刊</c:if></em></div>
	            </li>
	            <li class="td td-price">
	                <div class="td-inner"><em class="price-original"><c:if test="${cart.resoureType=='journal'}">${cart.journalPrice}</c:if><c:if test="${cart.resoureType!='journal'}">${cart.articlePrice}</c:if>创享币</em></div>
	            </li>
	            <li class="td td-op">
	                <div class="td-inner"><a href="javascript:void(0);" onclick="delpre('${cart.cartId}',this)">删&nbsp;&nbsp;&nbsp;&nbsp;除</a>
	                <span style="display:block;color:#f59762;position:relative;top:-25px;line-height:18px;"><c:if test="${cart.flag!=0}">
	                	已购买
	                </c:if></span>
	                </div>
	            </li>
	        </ul>
	    </c:forEach>
	    </form>
    </div>
    <div class="float-bar-wrapper">
        <div class="cart-checkbox">
            <input type="checkbox" value="true" name="select-all" id="J_SelectAllCbx2" class="J_CheckBoxShop" onchange="checkAll(this);">
            <label for="J_SelectAllCbx2">全选</label>
        </div>
        <div class="operations"><a class="J_Delete" hidefocus="true" href="javascript:void(0);" onclick="delpre();">删除</a></div>
        <div class="float-bar-right">
            <div class="amount-sum"><span class="txt">已选商品<em id="J_SelectedItemsCount">0</em>件商品</span> </div>
            <div class="price-sum"><span class="txt">总价:<em id="J_Total">0创享币</em>
                </span>
            </div>
            <div class="btn-area"><a class="submit-btn submit-btn-disabled" id="J_Go" href="javascript:void(0)" onclick="prevPay(this);"><span>结&nbsp;算</span><b></b></a></div>
        </div>
    </div>
    </c:if>
</div>
<!-- body_end --> 
<!-- footer -->
<jsp:include page="../footer.jsp"></jsp:include>
<!-- footer_end -->
<div name="遮挡背景" class="ui_back2">
		<div name="遮挡背景" class="ui_back"></div>
	</div>
	<div class="box_content">
		<div class="pop_div2 exit_pop">
			<span class="pop_closeBt" ></span>
			<h3>提示</h3>
			<p class='tips'>确认退出?</p>
			<div class="sb_button">
				<a onclick="logout()" class="a1">确认</a><a class="a2"
					onclick="closeDiv()">取消</a>
			</div>
		</div>
		<div class="pop_div2 shopcar_dele"> <span class="pop_closeBt" ></span>
	        <h3>删除</h3>
	        <div class="sb_middle">确认删除该商品?</div>
	        <div class="sb_button"> <a class="a1">确认</a><a class="a2" onclick="closeDiv();">取消</a> </div>
	    </div>
	    <div name="支付提示" class="pop_div2 pay_tip_succ">
			<span class="pop_closeBt" ></span>
			<h3>支付提示</h3>
			<div class="sb_middle">
				<div>
					<h5></h5>
					<h5>当前账户剩余创享币：<em>10000</em>。创享币不足，无法完成付款。</h5>
					<h5>输入充值数量：<input type="text" class="input_" id="rechargecount">1创享币=1元</h5>
					<span>支付方式：</span>
                            <p class="pay_ways"><span class="w110 current"><img src="word/img/zhifubao.png"></span>
                           </p>  
                    <h5>温馨提示：支持线下充值，请联系客服 010-63368810</h5>    
                    <h5>　　　　（工作日9:30-17:30）</h5>                             
				</div>
			</div>
			<div class="sb_button">
				<a class="a7"  target="_blank" onclick="return openRecharge(this);">充值并购买</a>
				<a class="a2" onclick="closeDiv();">取消</a>
			</div>
		</div>
		<div name="支付提示" class="pop_div2 pay_tip_fail">
			<span class="pop_closeBt" ></span>
			<h3>支付提示</h3>
			<div class="sb_middle">
			</div>
			<div class="sb_button">
				<a class="a11" onclick="$('#cartForm').submit();">确认购买</a>
			</div>
		</div>
		<div name="支付提示" class="pop_div2 pay_tips"	>
			<span class="pop_closeBt" ></span>
			<h3>支付提示</h3>
			<div class="sb_middle">
				<dl>
					<dt>
						<img src="word/img/paysuc_tips.png">
					</dt>
					<dd>
						<h5>请您在新打开的页面上完成付款</h5>
						<p>付款完成前请不要关闭此窗口</p>
						<p>完成付款后请根据您的情况点击下面按钮</p>
					</dd>
				</dl>
			</div>
			<div class="sb_button">
				<a class="a8" onclick="prevPay();">继续购买</a><a class="a9" onclick="closeDiv();">支付遇到问题</a>
			</div>
		</div>	
	</div>
	<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/alert.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/login.js?v=<%=System.currentTimeMillis()%>"></script> 
</body>
<script type="text/javascript">
<!--
//全选
function checkAll(checkbox){
	if($(checkbox).is(":checked")){
		$("input[type=checkbox][name=cartIds]:not(:disabled)").attr("checked",true);
		$("input[type=checkbox][name=select-all]").attr("checked",true);
	}else{
		$("input[type=checkbox][name=cartIds]").attr("checked",false);
		$("input[type=checkbox][name=select-all]").attr("checked",false);
	}
	$.each($("input[type=checkbox][name=cartIds]"),function(i,p){
		changeStatus($(p));
	});
	changePrice();
};
//单选
function checkedOne(checkbox){
	changeStatus(checkbox);
	if($(checkbox).is(":checked") && $("input[type=checkbox][name=cartIds]:not(:disabled)").size()==$("input[type=checkbox][name=cartIds]:checked").size()){
		$("input[type=checkbox][name=select-all]").attr("checked",true);
	}else{
		$("input[type=checkbox][name=select-all]").attr("checked",false);
	}
	changePrice();
};
//期刊选择改变文档
function changeStatus(checkbox){
	//如果是期刊
	$.each($("input[type=checkbox][name=cartIds]"),function(i,p){
		if($(checkbox).attr("resoureType")=="journal" && $(p).attr("resoureType")=="journalarticle" && $(p).attr("journalId")==$(checkbox).attr("journalId")){
			if($(checkbox).is(":checked")){
				$(p).attr("disabled",true).attr("checked",false);
				$(p).parent().parent().parent().find("li:last").find("span").text("已包含");
				$(p).parent().parent().parent().addClass("alreadyBg");
			}else{
				$(p).attr("disabled",false).attr("checked",false);
				$(p).parent().parent().parent().find("li:last").find("span").empty();
				$(p).parent().parent().parent().removeClass("alreadyBg");
			}
		}
	});
}
//删除确认
function delpre(cartId,cb){
	var cbx = $("input[type=checkbox][name=cartIds]:checked");
	if(!cartId && cbx && cbx.size()==0){
		new altInfo({
			text:"请选中要删除的商品！"	
		});
		return;
	}else{
		showsSopcarDele();
		if(!cartId){
			$(".shopcar_dele .sb_middle").text("确认删除所选商品?");
		}else{
			$(".shopcar_dele .sb_middle").text("确认删除该商品?");
		}
		$(".shopcar_dele .a1").click(function(){
			del(cartId,cb);
		});
	}
}
//删除
function del(cartId,cb){
	//多删
	var cbx = $("input[type=checkbox][name=cartIds]:checked");
	var cartIds = [];
	if(!cartId){
		if(cbx && cbx.size()==0){
			new altInfo({
				text:"请选中要删除的商品！"	
			});
			return;
		}
	 	$.each(cbx,function(){
	 		cartIds.push($(this).val());
	 	});
	}else{
		cartIds.push(cartId);
	}
	$.ajax({
        type: "post",
        url: "cart/del.do",
        data:{cartIds:cartIds.join("_")},
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data.result==1){
        		/**closeShopcarDele();
        		var c = $("#cartCount").text().replace("（","").replace("）","");
	        	if(!cartId){
	        		cbx.parent().parent().parent().remove();
	        		$("#cartCount").text("（"+(c-cbx.size())+"）");
	        	}else{
	        		$(cb).parent().parent().parent().remove();
	        		 $("#cartCount").text("（"+(c-1)+"）");
	        	}
	        	changePrice();
	        	if($("input[type=checkbox][name=cartIds]").size()==0){
	        		$(".shopcarCheckout").html('<div class="shopcarEmpty"><img src="word/img/shopcar_empty.png" /><p>您的购物车还是空的<br>赶紧行动吧？</p></div>');
	        	}*/
	        	window.location=$("#path").val()+"cart/index.do";
        	}else{
        		new altInfo({
					text:"系统错误！"	
				});
        	}
        }
    });
}
//改变价格
function changePrice(){
	var cbs = $("input[type=checkbox][name=cartIds]:checked");
	$("#J_SelectedItemsCount").text(cbs.size());
	var price = 0.00;
	$.each(cbs,function(){
		price+=new Number($(this).attr("price") || 0);
	});
	$("#J_Total").text(price);
	if(cbs.size()==0){
		$('.shopcarCheckout .float-bar-wrapper .submit-btn').addClass('submit-btn-disabled');
	}else{
		$('.shopcarCheckout .float-bar-wrapper .submit-btn').removeClass('submit-btn-disabled');
	}
}
//结算
function prevPay(btn){
	if(btn && $(btn).hasClass("submit-btn-disabled")){
		return;
	}
	var cbx = $("input[type=checkbox][name=cartIds]:checked");
	var cartIds = [];
	if(cbx && cbx.size()==0){
		new altInfo({
			text:"请选中要结算的商品！"	
		});
		return;
	}
 	$.each(cbx,function(){
 		cartIds.push($(this).val());
 	});
	$.ajax({
        type: "post",
        url: "cart/prevPay.do",
        data:{cartIds:cartIds.join("_")},
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data.result==1){
        		$(".pay_tips").hide();
        		//钱够提交
        		if(data.money>=data.total){
        			$(".pay_tip_fail .sb_middle").text("所选商品应付："+data.total+"创享币。");
        			$(".ui_back2,.ui_back,.box_content,.pay_tip_fail").show();
        		}else{
        			//跳出充值框走充值通道
        			$(".pay_tip_succ em").text(data.money);
        			$(".pay_tip_succ h5:first").text("所选商品应付："+data.total+"创享币。");
        			$("#rechargecount").val(data.total-data.money);
        			$(".ui_back2,.ui_back,.box_content,.pay_tip_succ").show();
        		}
        	}else{
        		new altInfo({
					text:data.msg || "结算失败"
				});
				//1秒后刷新
				setTimeout(function(){
					window.location.reload();
				}, 2000);
        	}
        }
    });
}
//打开支付中弹窗
function openRecharge(a){
	if(isNaN($("#rechargecount").val())){
		new altInfo({
			text:"请输入正确的充值数"
		});
		return false;
	}
	$(a).attr("href","alipay/userorder.do?rechargecount="+$("#rechargecount").val());
	$(".ui_back2,.ui_back,.box_content,.pay_tip_succ").hide();
	$(".ui_back2,.ui_back,.box_content,.pay_tips").show();
}
//-->
$(function(){
var topHei=218;//header height
var botHei=$('.footer').outerHeight()+20;//footer height
var allHei=$(window).height();
var conPaddBot=100;
var goalHei=allHei-topHei-botHei-conPaddBot-70;
//alert(botHei);
$('.shopcarEmpty').css({
'padding-top':goalHei/2+'px',
'padding-bottom':goalHei/2+'px'
});
});
</script>
</html>