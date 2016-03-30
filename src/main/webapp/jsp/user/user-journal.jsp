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
<meta charset="utf-8"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>知识创享网-我的期刊</title>
<link rel="stylesheet" type="text/css" href="word/css/base.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" type="text/css" href="word/css/index.css?v=<%=System.currentTimeMillis()%>" />

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
            <h1>我的期刊<span class="dele" onClick="delpre();">删除</span><span class="creat" onClick="showCreatPer()">新建</span></h1>
            <div class="userR_ news_">
                <h2>
                    <input type="checkbox" class="allCheck">
                    <span style="width:320px; padding-left:116px">期刊名称</span><span style="width:190px;padding-left:60px"><span>状态</span>
                    <select id="status" onchange="goPage(1);">
                        <option <c:if test="${page.status==null or page.status==''}">selected</c:if> value="">全部</option>
                        <option <c:if test="${page.status!=null and page.status=='SUBMITTED'}">selected</c:if> value="SUBMITTED">审核中</option>
                        <option <c:if test="${page.status!=null and page.status=='PASSED'}">selected</c:if> value="PASSED">公开</option>
                        <option <c:if test="${page.status!=null and page.status=='FAILED'}">selected</c:if> value="FAILED">审核不通过</option>
                        <option <c:if test="${page.status!=null and page.status=='SAVED'}">selected</c:if> value="SAVED">私有</option>
                    </select>
                    </span><span>创建时间</span></h2>
                <ul class="news_ul">
	                <c:forEach items="${page.list}" var="journal">
	                	<li>
		                	<input type="checkbox" name="journalIds"  value="${journal.id}">
	                        
	                        <a href="user/journal/detail.do?journalId=${journal.id}"  title="${journal.name}" class="w450" style="padding-left:15px;">
	                        	<c:if test="${fn:length(journal.name)>30}">${fn:substring(journal.name,0,30)}...</c:if>
		                        <c:if test="${fn:length(journal.name)<=30}">${journal.name}</c:if>
	                        </a>
	                        <span class="state">
	                        	<c:if test="${journal.passType=='SUBMITTED'}">审核中</c:if>
	                        	<c:if test="${journal.passType=='PASSED'}">公开</c:if>
	                        	<c:if test="${journal.passType=='FAILED'}">审核不通过<a href="javascript:info('${journal.id}');">&nbsp;查看原因 </a>&nbsp;<a href="javascript:showCreatPer('${journal.id}')">提交审核</a></c:if>
	                        	<c:if test="${journal.passType=='SAVED'}"><a class="operate" style="padding-left:0px;" onClick="showCreatPer('${journal.id}');">提交审核</a></c:if>
	                        </span> 
	                        <span class="time"><fmt:formatDate value="${journal.createTime}" pattern="yyyy-MM-dd HH:mm"/></span> 
                        </li>
	                </c:forEach>
	                <c:if test="${empty page.list}"><li>暂无记录</li></c:if>
                </ul>
            </div>
            <jsp:include page="../pager.jsp"></jsp:include>
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
<!--
//分页跳转
function goPage(pageNo,startPage){
	var url = $("#path").val()+"user/journal/index.do";
	if($("#status").val()){
		url+="?status="+$("#status").val();
	}
	if(pageNo>1){
		url+= (url.indexOf("?")>0?"&":"?")+"pageNo="+pageNo;
	}
	if(startPage>1){
		url+= (url.indexOf("?")>0?"&":"?")+"startPage="+startPage;
	}
	window.location = url;
}
function delpre(){
	if($("input[type=checkbox][name=journalIds]:checked").size()==0){
		new altInfo({
			text:"请选中要删除的文件！"	
		});
	}else{
		//showDiv(4);
		show_user_dele();//20160122
	}
}
//删除期刊
function del(){
	var journalIds = [];
 	$.each($("input[type=checkbox][name=journalIds]:checked"),function(){
 		journalIds.push($(this).val());
 	});
 	$.ajax({
        type: "post",
        url: "user/delJournal.do",
        data:{journalIds:journalIds.join("_")},
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data && data.result==0){
        		new altInfo({
					text:data.msg
				});
        	}else{
        		window.location = $("#path").val()+"user/journal/index.do"+($("#status").val()?("?status="+$("#status").val()):"");
        	}
        }
    });
}
//拒绝原因
function info(journaiId){
	$.ajax({
        type: "post",
        url: "view/reason.do",
        data:{articleId:journaiId,type:"journal"},
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data){
        		$(".no_reason .sb_middle").html(data.reason);
				//showDiv(3);
				show_no_reason();//20160122
        	}else{
        		new altInfo({
					text:"系统错误！"	
				});
        	}
        }
    });
}
//上传封面
function uploadImg() {
	var uploadBody = document.getElementById("uploadIframe").contentWindow.document.body;
	uploadBody.innerHTML="";
	var form = document.getElementById("uploadForm");
	if ($("#upimg").val().length <= 0 || $("#upimg").val() == null) {
        new altInfo({text:"请选择图片"});
        return;
    }
    var len = $("#upimg").val().lastIndexOf(".");
    var type = $("#upimg").val().substring(len + 1).toLowerCase();
    if (type != "jpg" && type != "jpeg" && type != "bmp" && type != "gif" && type != "png") {
        new altInfo({text:"图片格式错误，只支持jpg、jpeg、bmp、gif、png"});
        return;
    }
	form.action="user/journal/uploadCover.do";
	form.submit();
	var lb = new loading_box({title:'上传图片中',text:'请等待'});
	var uploadId = setInterval(function(){
		  uploadBody = document.getElementById("uploadIframe").contentWindow.document.body;
		  var res = uploadBody.innerHTML;
		  if(res!=""){
		      var json =  eval("("+res+")");
		      if(json){
		      	  lb.close();
				  clearInterval(uploadId);
				   if(json.success==true){
					  new altInfo({text:"上传成功"});
					  return false;
				  }else{
					  new altInfo({text:json.msg});
					  return false;
				  }
		      }
		  }
	},1000);
    /**$.ajaxFileUpload({
        type: "post",
        //请求方式
        url: "user/journal/uploadCover.do",
        secureuri: false,
        //是否需要安全协议，一般设置为false
        fileElementId: "upimg",
        //文件上传域的ID
        dataType: "json",
        //返回值类型 一般设置为json
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        success: function(data) {
        	if(data && data.success==true){
        		$("#cover").val(data.path);
        		new altInfo({text:"上传成功"});
        	}else{
        		new altInfo({text:data.msg});
        	}
        }
    });*/
}
//提交
function journalAdd(){
	var id = $.trim($("#update_journal_id").val());
	var uploadBody = document.getElementById("uploadIframe").contentWindow.document.body;
	if(uploadBody.innerHTML!=""){
		$("#cover").val(eval("("+uploadBody.innerHTML+")").path);
	}
	if(!id){
		if ($.trim($("#cover").val()).length <= 0 || $.trim($("#cover").val()) == null) {
	        new altInfo({text:"请上传期刊封面"});
	        return;
	    }
	}
	if ($.trim($("#qikanName").val()).length <= 0 || $.trim($("#qikanName").val()) == null) {
        new altInfo({text:"请填写期刊名称"});
        return;
    }
    if ($.trim($("#qikanName").val()).length >40) {
        new altInfo({text:"期刊名称不能超过40字符"});
        return;
    }
    if ($.trim($("#qikanBrief").val()).length <= 0 || $.trim($("#qikanBrief").val()) == null) {
        new altInfo({text:"请填写期刊简介"});
        return;
    }
    if ($.trim($("#qikanBrief").val()).length >1000) {
        new altInfo({text:"期刊简介不能大于1000个字符"});
        return;
    }
    var bq = $.trim($("#brief_3").val());
    if (bq.length <= 0 || bq == null) {
        new altInfo({text:"请填写期刊标签"});
        return;
    }
    var tems = bq.split(" ");
    var bqs = [];
    for(var i = 0;i<tems.length;i++){
    	if($.trim(tems[i]).length>=0 && $.trim(tems[i])!=""){
	    	bqs.push($.trim(tems[i]));
	    }
    }
    for(var i = 0;i<bqs.length;i++){
    	if($.trim(bqs[i]).length>40){
    		new altInfo({text:"每个标签最多为40个字"});
    		return;
    		break;
    	}
    	for(var j = 0;j<bqs.length;j++){
    		if($.trim(bqs[i])==$.trim(bqs[j]) && i!=j){
    			new altInfo({text:"标签重复"});
    			return;
    			break;
    		}
    	}
    }
    var kw = $.trim($("#keyword_3").val());
    if (kw.length <= 0 || kw == null) {
        new altInfo({text:"请填写期刊关键词"});
        return;
    }
    var tems = kw.split(" ");
    var kws = [];
    for(var i = 0;i<tems.length;i++){
    	if($.trim(tems[i]).length>=0 && $.trim(tems[i])!=""){
	    	kws.push($.trim(tems[i]));
	    }
    }
    for(var i = 0;i<kws.length;i++){
    	if($.trim(kws[i]).length>8){
    		new altInfo({text:"每个关键词最多为8个字"});
    		return;
    		break;
    	}
    	for(var j = 0;j<kws.length;j++){
    		if($.trim(kws[i])==$.trim(kws[j]) && i!=j){
    			new altInfo({text:"关键词重复"});
    			return;
    			break;
    		}
    	}
    }
    if (kws.length>15) {
        new altInfo({text:"最多15个关键词"});
        return;
    }
    if($.trim($("#journalPrice").val()).length <=0 || $.trim($("#journalPrice").val())==null) {
        new altInfo({text:"请填写期刊价格"});
        return;
    }
    if(isNaN($.trim($("#journalPrice").val()))) {
        new altInfo({text:"价格格式错误"});
        return;
    }
    if(parseFloat($.trim($("#journalPrice").val()))>9999999){
    	new altInfo({text:"价格区间为0-9999999"});
        return;
    }
	$.ajax({
        type: "post",
        url: "user/journal/add.do",
        data:$("#form").serialize(),
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data && data.result==1){
        		//showDiv(2);
        		show_save_success(function(){window.location.reload()});//20160122
        		$('.save_success p').text('提交成功！');
        	}else{
        		if(data && data.msg){
        			new altInfo({
						text:data.msg
					});
        		}else{
	        		new altInfo({
						text:"保存失败！"	
					});
        		}
        	}
        }
    });
}
//点击添加标签
function setLabel(em){
 	if($(em).hasClass("disabled")){
 		return;
 	}else{
 		$(em).addClass("disabled");
 		var v = $("#brief_3").val();
	   	if($.trim(v)==""){
	   		$("#brief_3").val($(em).text());
	   	}else{
	   		$("#brief_3").val(v+" "+$(em).text());
	   	}
	   	validBrief();
 	}
}
//失去焦点验证标签
function validBrief(){
	var bq = $.trim($("#brief_3").val());
	var sty = {"color":"#d71921","margin-left":"10px"};
    if (bq.length <= 0 || bq == null) {
        $("#brief_3").parent().next().css(sty).html("*请填写期刊标签");
        return false;
    }
    var tems = bq.split(" ");
    var bqs = [];
    for(var i = 0;i<tems.length;i++){
    	if($.trim(tems[i]).length>=0 && $.trim(tems[i])!=""){
	    	bqs.push($.trim(tems[i]));
	    }
    }
    for(var i = 0;i<bqs.length;i++){
    	if($.trim(bqs[i]).length>40){
    		$("#brief_3").parent().next().css(sty).html("*每个标签最多为40个字");
    		return  false;
    		break;
    	}
    	for(var j = 0;j<bqs.length;j++){
    		if($.trim(bqs[i])==$.trim(bqs[j]) && i!=j){
    			$("#brief_3").parent().next().css(sty).html("*标签重复");
    			return false;
    			break;
    		}
    	}
    }
    $("#brief_3").parent().next().css({"color":"#888","margin-left":"0px"}).text("（多个标签用空格分隔，每个标签最多为40个字）");
    return true;
}
//失去焦点验证关键词
function validKeyword(){
	var kw = $.trim($("#keyword_3").val());
	var sty = {"color":"#d71921","margin-left":"10px"};
    if (kw.length <= 0 || kw == null) {
        $("#keyword_3").next().css(sty).html("*请填写期刊关键词");
        return false;
    }
    var tems = kw.split(" ");
    var kws = [];
    for(var i = 0;i<tems.length;i++){
    	if($.trim(tems[i]).length>=0 && $.trim(tems[i])!=""){
	    	kws.push($.trim(tems[i]));
	    }
    }
    for(var i = 0;i<kws.length;i++){
    	if($.trim(kws[i]).length>8){
    		$("#keyword_3").next().css(sty).html("*每个关键词最多为8个字");
    		return false;
    		break;
    	}
    	for(var j = 0;j<kws.length;j++){
    		if($.trim(kws[i])==$.trim(kws[j]) && i!=j){
    			$("#keyword_3").next().css(sty).html("*关键词重复");
    			return false;
    			break;
    		}
    	}
    }
    if (kws.length>15) {
        $("#keyword_3").next().css(sty).html("*最多15个关键词");
        return false;
    }
    $("#keyword_3").next().css({"color":"#888","margin-left":"0px"}).text("（多个关键词用空格分隔，最多15个关键词，每个关键词最多为8个字）");
    return true;
}
//打开新建窗口
function showCreatPer(journalId){ 
	$(".creat_pop").find("em").removeClass("disabled");
	if(journalId){
		$("#update_journal_id").val(journalId); 
		$.ajax({
	        type: "post",
	        url: "user/getJournal.do",
	        data:{journalId:journalId},
	        async:false,
	        dataType:"json",
	        success: function(data) {
	        	if(data!=null){
					$("#qikanName").val(data.name); 
					$("#qikanBrief").val(data.skip); 
					if(data.label){
						var ls = data.label.split(" ");
						for(var i=0;i<ls.length;i++){
							if($.trim(ls[i])!=""){
								$.each($(".creat_pop").find("em"),function(){
									if($(this).text()==$.trim(ls[i])){
										$(this).addClass("disabled");
									}
								});
							}
						}
						$("#brief_3").val(data.label); 
					}
					$("#keyword_3").val(data.keyword); 
					$("#typeId").val(data.typeId); 
					$("#typeName").val($("#typeId").find("option:selected").text());
					$("#journalPrice").val(data.price); 
	        	}
	        	$('.creat_pop h3').text("编辑期刊");
				$(".ui_back2,.ui_back,.box_content,.creat_pop").show();
	        }
	    });
	}else{
		$("#typeId").val(1);
		$("#typeName").val($("#typeId").find("option:selected").text());
		$('.creat_pop h3').text("新建期刊");
		$(".ui_back2,.ui_back,.box_content,.creat_pop").show();
	}
}
$("#brief_3").keyup(function(e){
    if(e.keyCode==32){
        validBrief();
    }
 });
$("#keyword_3").keyup(function(e){
    if(e.keyCode==32){
        validKeyword();
    }
});
//-->
</script>
</html>