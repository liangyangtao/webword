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
<title>我的风险预警</title>
	<link rel="stylesheet" type="text/css" href="common/css/base.css"/>
	<link rel="stylesheet" type="text/css" href="common/css/menu1.css"/>	
	<script type="text/javascript" src="common/js/jquery-1.8.3.min.js"></script>	
	<script type="text/javascript" src="common/js/jquery.easing.1.3.js"></script>
	<!--<script type="text/javascript" src="common/js/common.js"></script>-->
	<script type="text/javascript" src="common/js/riskmy.js"></script>
	<script type="text/javascript" src="word/js/laydate/laydate.js"></script>
</head>
<style type="text/css">
	ul,li{list-style:none; padding-bottom: 1px;}
	a{text-decoration:none;color:#666;}
	/* #form_cities{display:none;background:#fff;border:1px solid #cdcdcd;padding:0;position:absolute; width:252px;z-index:20151030;}*/
	#panel_cities{padding:0;}
	#panel_cities li{padding:0px 6px;font-size:14px;cursor:pointer;height:25px;line-height:25px;color:#000;display:block;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;}
	#panel_cities li.hover{background:#eee;}
	#flip_cities{padding:0px 12px;text-align:center;line-height:25px;}
	#flip_cities a{color:#666; font-size:12px; text-decoration:none;}
	#flip_cities a:hover{color:#000;text-decoration: underline;}
	.submit_audit{width:80%;overflow:hidden;font-size:100%;font-weight:normal;height:auto;line-height:35px;background:#fff; border:1px solid black; margin-bottom:10px;}
	/*.add_channel #form_cities #flip_cities{display:none;} */
</style>  
<body onload="pageLoad();" class="bodyContent">
<script type="text/javascript">
var firstmid ="${firstmid}";
var title ="${fistname}";
var keyword ="${keyword}";
var temp =$("#keyword").val();
$(document).ready(function(){
	
		get_doclist(firstmid,title,1,keyword,0);
      
     if($("#status").val()==12){
 	  $(".day_tips1,.box_content,.ui_back").fadeIn(300);//显示
 	  $(".sbox_ h3 img,.sb_button .a1").click(function(){
 		  location.href="/webword/risk/view/myrisk.do";
 	    });	
     }	 
})
var G_listSzie=${listSize};

</script>
<!--  -->

<div name="遮挡背景" class="ui_back2"></div>
<div name="遮挡背景" class="ui_back"></div>
<div class="box_content">
<!-- 定制我的风险预警信息库 -->
		<div name="定制我的风险预警信息库" class="sbox_ industry_options">
			<h3>定制我的风险预警信息库<img src="common/img/s_exit.png" /></h3>
			<div class="sb_middle">
				<div class="stype stype1">					
					
				</div>
				<div class="stype stype2">	
						<p> 为了更高效的推送风险信息，请您完善以下信息：</p>
						<div class="s1">您的岗位：<input id="station" class="jobs " value="${station}" type="text"/><input value="" type="hidden"/></div>
						<div class="s2">岗位职责：<input id="responsibility" class="responsibility" value="${responsibility}" type="text"/><input value="" type="hidden"/></div>
						<div class="s3">关注的行业：<input id="interest" class="industry" value="${interest}" type="text"/><input value="" type="hidden"/></div>
						<div class="s4">我的关键词：<input id="mykeyword" class="keywords" value="${keyword}" type="text"/><input value="" type="hidden"/></div>
						<p class="tip2">关键词之间以一个空格分隔</p>
				</div>
			</div>
			<div class="sb_button  sb1"><a class="a1">关闭</a><a class="a2" href="javascript:;" onclick="submitinfo()">确认</a><a class="a3">下一步</a><a class="a4">上一步</a></div>
		</div>
		<div name="设置关键词-浦发银行（600000)" class="sbox_ set_keyword">
			<h3>设置关键词-浦发银行（600000)<img src="common/img/s_exit.png" /></h3>
			<div class="sb_middle">
				<div>包含以下<mark>全部</mark>关键词：<input type="text" class="all" value="" /><input type="hidden" class="all_value" value="" /></div>
				<div>包含以下<mark>任意</mark>一个关键词：<input type="text" class="any_one" value="" /><input type="hidden" class="any_one_value" value="" /></div>
				<div><mark>不包含</mark>以下关键词：<input type="text" class="not" value="" /><input type="hidden" class="not_value" value="" /></div>
				<div class="margin">查询关键词位于：<input type="radio" class="full_text" name="radioF" checked="checked" />在全文中<input type="radio" class="title" name="radioF" />仅在标题中</div>
				<div>起止时间：<input type="text" id="start_time1" placeholder="" onclick="laydate({elem:'#start_time1',max:laydate.now()});this.placeholder=''"  onblur="this.placeholder=''" class="start_time" value="" /><input type="text" id="end_time1" placeholder="" onclick="laydate({elem:'#end_time1',max:laydate.now()});this.placeholder=''" onblur="this.placeholder=''"  class="end_time" value="" /></div>
				<div>搜索结果排序方式：<select><option value="1">按时间排序</option></select></div>
				<div class="last">信息库：<input type="checkbox" class="check_input" />风险信息库</div>
			</div>
			<div class="sb_button  sb1"><a class="a1">取消</a><a class="a2" href="javascript:;" onclick="submitinfo()">确认</a><a class="a3">重置</a></div>
		</div>
		<div name="我的定制" class="sbox_ manage_en">
			<h3>我的定制<img src="common/img/s_exit.png" /></h3>
			<div class="sb_middle">	
				<ul class="list">
					<li><input type="checkbox" class="check_input all_select_input" />全部 </li>
					<li><input type="checkbox" class="check_input" /><span class="title">浦发银行（600000）</span><a><i class="i1"></i></a><a><i class="i2"></i></a></li>
					<li><input type="checkbox" class="check_input" /><span class="title">浦发银行（600000）</span><a><i class="i1"></i></a><a><i class="i2"></i></a></li>
					<li><input type="checkbox" class="check_input" /><span class="title">浦发银行（600000）</span><a><i class="i1"></i></a><a><i class="i2"></i></a></li>
					<li><input type="checkbox" class="check_input" /><span class="title">浦发银行（600000）</span><a><i class="i1"></i></a><a><i class="i2"></i></a></li>
					<li><input type="checkbox" class="check_input" /><span class="title">浦发银行（600000）</span><a><i class="i1"></i></a><a><i class="i2"></i></a></li>
					<li><input type="checkbox" class="check_input" /><span class="title">浦发银行（600000）</span><a><i class="i1"></i></a><a><i class="i2"></i></a></li>				
				</ul>			
			</div>
			<div class="sb_button  sb1"><input class="value_input" placeholder="请输入企业名称/股票代码，多个请以空格分隔" onfocus="this.placeholder=''" onblur="this.placeholder='请输入企业名称/股票代码，多个请以空格分隔'" value="" type="text" /><a class="a1">取消</a><a class="a2" href="javascript:;" onclick="submitinfo()">确认</a><a class="a3">新增</a></div>
		</div>
	
	<input type="hidden" id="status" value="${status}" >	
		<!--  购买到期 温馨提示1  -->
 <c:if test="${companyDate<1}"> 
    <div class="sbox_ day_tips day_tips1">
			<h3>温馨提示<img src="common/img/s_exit.png" /></h3>
			<div class="sb_middle">
				<p class="p1">尊敬的：${user.userName}</p>
			    <p class="p2">您订阅的栏目：${getshopDate.get(0).mname}在${getshopDate.get(0).right}天后即将到期，为不影响您的正常使用，请您及时联系客服进行充值缴费。<br>&nbsp;&nbsp;&nbsp;&nbsp;联系电话: 010-63368810</p>
			</div>
			<div class="sb_button"><a class="a1">知道了</a></div>
	</div>
 </c:if>
 <c:if test="${companyDate<4&&companyDate>0}">
    <c:if test="${getshopDate!=null}">
    <!--  购买到期 温馨提示2  -->
				<div class="sbox_ day_tips day_tips1">
					<h3>温馨提示</h3>
					<div class="sb_middle">
						<p class="p1">尊敬的：${user.userName}</p>
						<p class="p2">	您订阅的栏目：${getshopDate.get(0).mname}以及企业/行业,在${companyDate}天后即将到期，为不影响您的正常使用，请及时联系客服进行充值缴费。<br>&nbsp;&nbsp;&nbsp;&nbsp;联系电话:010-63368810</p>
						 <%--<c:if test="${companyDate<2}">
							<p class="p2">	您订阅的栏目：${getshopDate.get(0).mname}以及企业/行业,在${companyDate}天后即将到期，为不影响您的正常使用，请及时联系客服进行充值缴费。<br>&nbsp;&nbsp;&nbsp;&nbsp;联系电话:010-63368810</p>
					     </c:if>
					     <c:if test="${companyDate==2}">
							<p class="p2">	您订阅的栏目：${getshopDate.get(0).mname}以及企业/行业,在${companyDate}天后即将到期，为不影响您的正常使用，请及时联系客服进行充值缴费。<br>&nbsp;&nbsp;&nbsp;&nbsp;联系电话:010-63368810</p>
					     </c:if>
						<c:if test="${companyDate>2}">
						<c:forEach items="${getshopDate}" var="getshopDate">
							<p class="p2">	您订阅的栏目：${getshopDate.mname}以及企业/行业,在${getshopDate.right}天后即将到期，为不影响您的正常使用，请及时联系客服进行充值缴费。<br>&nbsp;&nbsp;&nbsp;&nbsp;联系电话:010-63368810</p>
					    </c:forEach>
					     </c:if> --%>
					</div>
					<div class="sb_button">
						<a class="a1">知道了</a>
					</div>
				</div>
	</c:if>
 </c:if>
 <c:if test="${companyDate<4&&companyDate>0}">
    <c:if test="${getshopDate==null}">
    	 <!--  购买到期 温馨提示3  -->
	<div class="sbox_ day_tips day_tips1">
			<h3>温馨提示</h3>
			<div class="sb_middle">
				<p class="p1">尊敬的：${user.userName}</p>
				<p class="p2">您定制的企业/行业,在${companyDate}天后即将到期，为不影响您的正常使用，请及时联系客服进行充值缴费。<br>&nbsp;&nbsp;&nbsp;&nbsp;联系电话: 010-63368810</p>
			</div>
			<div class="sb_button"><a class="a1">知道了</a></div>
	</div>
    
    </c:if>
 </c:if>

</div>
<!-- header -->
<jsp:include page="header.jsp"></jsp:include>
<!-- body -->
<div class="content">
	<div class="left_list">
		<div class="left_box1">
			<h4 class="t_h">自选栏目<a title="栏目管理" onclick=""  class="a1 fengxianmy"></a><a title="编辑" target='_blank' href="wordNew.do" class="a2"></a></h4>
			
		<c:forEach items="${list}" var="level1" varStatus="status1">
			<c:if test="${level1.has==1}">
			<div class="content_box">	
				<div class="str str1">
					<a class="title title1" href="javascript:;" <c:if test="${level1.child.size()==0}">onclick="show(${level1.mid},'${level1.mname}')"</c:if> ><i></i>${level1.mname}</a>
					<c:if test="${level1.child!=null}">
					<c:forEach items="${level1.child}" var="level2" varStatus="status2">
						<c:if test="${level2.has==1}">
							<div class="str str2">
								<a class="title title2" href="javascript:;" <c:if test="${level2.child.size()==0}">onclick="show(${level2.mid},'${level2.mname}')"</c:if> ><i></i>${level2.mname}</a>
								<c:if test="${level2.child!=null}">
								<c:forEach items="${level2.child}" var="level3" varStatus="status3">
								<c:if test="${level3.has==1}">
								<div class="str str3"><a  href="javascript:;" onclick="show(${level3.mid},'${level3.mname}')" class="title title3">${level3.mname}</a></div>
								</c:if>
								</c:forEach>
								</c:if>
							</div>
						</c:if>	
					</c:forEach>
					</c:if>
				</div>
			</div>
			</c:if>
		</c:forEach>
		</div>
		<div class="left_box1 left_box12">
			<h4 class="t_h">我的定制<a title="栏目管理" onclick="ser_box()"  class="a1"></a><a title="编辑" target='_blank' href="wordNew.do" class="a2"></a></h4>
			<div class="content_box">
				<ul class="ul1">
					<li title="已选企业（全部）"><a href="javascript:;" onclick="showAllCompany(0,'全部')">已选企业（全部）</a></li>
					<c:forEach items="${companyList}" var="cList" >
					   <li title="${cList.mname}"><a href="javascript:;" onclick="showCompany(${cList.mid},'${cList.mname}')">${cList.mname}</a></li>
					</c:forEach>
				</ul>
			
			</div>
		</div>
		<div class="left_box2">
			<h4 class="t_h t_h2">我关注的风险点</h4>
			<div class="ser_box">
				<div class="inline">
					<textarea id="keyword" class="input" onfocus="this.style.background='#fff'" onblur="test1()"  onmouseout="">${keyword}</textarea>
					<a class='ser_btn' href='javascript:;' onclick='searchnews($("#keyword").val())'></a>
				</div>
			<!--  	<a class="add_more">搜索本站</a>-->
			</div>
		</div>
	</div>
	<div class="right ractive" id="contentDiv">
	</div>
</div>
<!-- footer -->
<!-- footer -->
<jsp:include page="footer.jsp"></jsp:include>

</body>


</html>
