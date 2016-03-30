<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>知识创享网-我的上传</title>
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
				<h3 class="user_name">
					<em></em>${user.userName}
				</h3>
				<img src="word/img/tou.gif" class="user_img" />
			</div>
		</div>
		<div class="clearfix userBody">
			<jsp:include page="user-left.jsp"></jsp:include>
			<div class="userRight">
				<h1>我的上传
					<span class="dele" onClick="delpre();">删除</span><span class="upload" onClick="showUploadFile()">上传文档</span><span class="upload" onClick="showBatchUploadFile()">批量上传</span>
				</h1>
				<div class="userR_ news_">
					  <h2>
                    <input type="checkbox" class="allCheck">
                    <span>格式</span><span style="width:225px; padding-left:116px">标题</span><span style="width:185px;">
                    <span>状态</span>
                    <select id="status" onchange="goPage(1);">
                        <option <c:if test="${page.status==null or page.status==''}">selected</c:if> value="">全部</option>
                        <option <c:if test="${page.status!=null and page.status=='SAVED'}">selected</c:if> value="SAVED">私有</option>
                        <option <c:if test="${page.status!=null and page.status=='SUBMITTED'}">selected</c:if> value="SUBMITTED">审核中</option>
                        <option <c:if test="${page.status!=null and page.status=='PASSED'}">selected</c:if> value="PASSED">公开</option>
                        <option <c:if test="${page.status!=null and page.status=='FAILED'}">selected</c:if> value="FAILED">审核不通过</option>
                    </select>
                    </span>
                    <span style="width:102px;padding-left:40px">操作</span>
                    <span>创建时间</span></h2>
					<ul class="news_ul">
						<c:forEach items="${page.list}" var="article">
							<li><input type="checkbox" name="articleIds"
								value="${article.articleId}"> <span class="ic-h-${article.articleFormat}"></span>
								<a <c:if test="${article.passType=='PASSED' }">target="_blank" href="view/preview.do?articleId=${article.articleId}"</c:if>
								title="${article.articleName}" class="w380 w340"> <c:if
										test="${fn:length(article.articleName)>23}">${fn:substring(article.articleName,0,23)}...</c:if>
									<c:if test="${fn:length(article.articleName)<=23}">${article.articleName}</c:if>
							</a> <span class="state"> <c:if
										test="${article.passType=='SAVED'}">私有</c:if> <c:if
										test="${article.passType=='SUBMITTED'}">审核中</c:if> <c:if
										test="${article.passType=='PASSED'}">公开</c:if> <c:if
										test="${article.passType=='FAILED'}">审核不通过<a
											href="javascript:info('${article.articleId}');">&nbsp;查看原因
										</a>
									</c:if> 
									</span> 
									<c:if test="${article.passType=='SUBMITTED' || article.passType=='PASSED'}"><a class="operate" ></a></c:if>
									<c:if test="${article.passType!='SUBMITTED' && article.passType!='PASSED'}"><a class="operate" href="javascript:showSubmit(${article.articleId})">提交审核</a></c:if> <span class="time"><fmt:formatDate
										value="${article.updateTime}" pattern="yyyy-MM-dd HH:mm" /> </span>
							</li>
						</c:forEach>
						<c:if test="${empty page.list}">
							<li>暂无记录</li>
						</c:if>
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
	<script type="text/javascript" src="word/js/laydate/laydate.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="word/js/onSearch.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
<script type="text/javascript">
<!--
if(window.location.toString().indexOf("flag=1")>-1){
	showUploadFile();
}
//分页跳转
function goPage(pageNo, startPage) {
	var url = $("#path").val() + "user/upload.do";
	if ($("#status").val()) {
		url += "?status=" + $("#status").val();
	}
	if (pageNo > 1) {
		url += (url.indexOf("?") > 0 ? "&" : "?") + "pageNo=" + pageNo;
	}
	if (startPage > 1) {
		url += (url.indexOf("?") > 0 ? "&" : "?") + "startPage="
				+ startPage;
	}
	window.location = url;
}
//删除前确认
function delpre(){
	if($("input[type=checkbox][name=articleIds]:checked").size()==0){
		new altInfo({
			text:"请选中要删除的文件！"	
		});
	}else{
		//showDiv(4);
		show_user_dele();//20160122
	}
}
//删除上传
function del(){
	var articleIds = [];
 	$.each($("input[type=checkbox][name=articleIds]:checked"),function(){
 		articleIds.push($(this).val());
 	});
 	$.ajax({
        type: "post",
        url: "user/delArticle.do",
        data:{articleIds:articleIds.join("_")},
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data && data.result==0){
        		new altInfo({
					text:data.msg
				});
        	}else{
        		window.location = $("#path").val()+"user/upload.do"+($("#status").val()?("?status="+$("#status").val()):"");
        	}
        }
    });
}
//拒绝原因
function info(articleId){
	$.ajax({
        type: "post",
        url: "view/reason.do",
        data:{articleId:articleId,type:"document"},
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
var flag = true;
//上传文档
function importFile() {
	if(!flag){
		return;
	}
    var uploadBody = document.getElementById("uploadIframe").contentWindow.document.body;
	uploadBody.innerHTML="";
	var form = document.getElementById("uploadWordForm");
	 if ($("#uploadfile").val().length <= 0 || $("#uploadfile").val() == null) {
        new altInfo({text:"请选择文档"});
        return;
    }
    var len = $("#uploadfile").val().lastIndexOf(".");
    var type = $("#uploadfile").val().substring(len + 1).toLowerCase();
    if (type != "doc" && type != "docx" && type!="pdf" && type!="ppt" && type!="pptx") {
        new altInfo({text:"文档格式错误，只支持doc、docx，pdf，ppt，pptx"});
        return;
    }
    flag = false;
	form.action="user/importFile.do";
	form.submit();
	var lb = new loading_box({title:'文件上传中',text:'请等待'});
	var uploadId = setInterval(function(){
		  uploadBody = document.getElementById("uploadIframe").contentWindow.document.body;
		  var res = uploadBody.innerHTML;
		  if(res!=""){
		  	  flag = true;
		      var json =  eval("("+res+")");
		      if(json){
		      	  lb.close();
				  clearInterval(uploadId);
				   if(json.success==true){
					   //showDiv(2);
					   show_save_success(function(){window.location.reload()});//20160122
					   $('.save_success p').text('上传成功！');
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
        url: "user/importFile.do",
        secureuri: false,
        //是否需要安全协议，一般设置为false
        fileElementId: "uploadfile",
        //文件上传域的ID
        dataType: "json",
        //返回值类型 一般设置为json
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        success: function(data) {
        	if(data && data.success==true){
        		showDiv(2);
        	}else{
        		new altInfo({text:data.msg});
        	}
        }
    });*/
}
//批量上传文档
function importBatchFile() {
	if(!flag){
		return;
	}
    var uploadBody = document.getElementById("uploadIframe").contentWindow.document.body;
	uploadBody.innerHTML="";
	var form = document.getElementById("uploadBatchWordForm");
	 if ($("#batchuploadfile").val().length <= 0 || $("#batchuploadfile").val() == null) {
        new altInfo({text:"请选择批量文件"});
        return;
    }
    var len = $("#batchuploadfile").val().lastIndexOf(".");
    var type = $("#batchuploadfile").val().substring(len + 1).toLowerCase();
    if (type != "zip") {
        new altInfo({text:"文件格式错误，只支持zip"});
        return;
    }
    flag = false;
	form.action="user/importBatchFile.do";
	form.submit();
	var lb = new loading_box({title:'文件上传中',text:'请等待'});
	var uploadId = setInterval(function(){
		  uploadBody = document.getElementById("uploadIframe").contentWindow.document.body;
		  var res = uploadBody.innerHTML;
		  if(res!=""){
		  	  flag = true;
		      var json =  eval("("+res+")");
		      if(json){
		      	  lb.close();
				  clearInterval(uploadId);
				   if(json.success==true){
					   //showDiv(2);
					   show_save_success(function(){window.location.reload()});//20160122
					   $('.save_success p').text('上传成功！');
					  return false;
				  }else{
					  new altInfo({text:json.msg});
					  return false;
				  }
		      }
		  }
	},1000);
}
var price = 0;
//期刊联想
new oSearchSuggest($(".submit_audit .tabDivBg #upload_qikan_name"),$(".submit_audit .sb_middle"),"85","62",$(".submit_audit"),10,function(li){
	$("#articleJournalId").val($(li).attr("journalid"));
	$("#upload_type_id").val($(li).attr("typeId"));
	price = $(li).attr("price");
	$("#articlePrice").val(getJournalPrice($(li).attr("price"),$(li).attr("typeId")));
	if($.trim($("#upload_journal_date").val())){
		var articleJournalName = getJournalStr($.trim($("#upload_journal_date").val()),$(li).attr("typeId"));
		$("#upload_journal_date").next().html(articleJournalName);
		$("#articleJournalName").val(articleJournalName.split(":")[1]);
	}
});	
//提交审核
function submitArticle(){
	var ra = $("input[type=radio][name=Tradio]:checked").val();
	if(ra==1){
		if ($("#upload_qikan_name").val().length <= 0 || $("#upload_qikan_name").val() == null) {
	        new altInfo({text:"请选择期刊"});
	        return;
	    }
	    if ($("#upload_journal_date").val().length <= 0 || $("#upload_journal_date").val() == null) {
	        new altInfo({text:"请选择文档期次"});
	        return;
	    }
	}
	if ($("#upload_article_name").val().length <= 0 || $("#upload_article_name").val() == null) {
        new altInfo({text:"请填文档标题"});
        return;
    }
    if ($("#upload_article_skip").val().length <= 0 || $("#upload_article_skip").val() == null) {
        new altInfo({text:"请填写文档简介"});
        return;
    }
    var bq = $.trim($("#upload_article_label").val());
    if (bq.length <= 0 || bq == null) {
        new altInfo({text:"请填写文档标签"});
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
    var kw = $.trim($("#upload_article_keyword").val());
    if (kw.length <= 0 || kw == null) {
        new altInfo({text:"请填写文档关键字"});
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
    if ($("#articlePrice").val().length <= 0 || $("#articlePrice").val() == null) {
        new altInfo({text:"请填写文档价格"});
        return;
    }
    if (isNaN($("#articlePrice").val())) {
        new altInfo({text:"文档价格格式错误"});
        return;
    }
    if(ra==1){
	    if (parseFloat($("#articlePrice").val())>price) {
	        new altInfo({text:"文档价格不能大于期刊价格"});
	        return;
	    }
    }
    if(parseFloat($.trim($("#articlePrice").val()))>9999999){
    	new altInfo({text:"价格区间为0-9999999"});
        return;
    }
	$.ajax({
        type: "post",
        url: "word/editSubmitApprove.do",
        data:$("#upload_submit_form").serialize(),
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data && data.state=="success"){
        		//showDiv(2);
        		show_save_success(function(){window.location.reload()});//20160122
        		$('.save_success p').text('提交成功！');
        	}else{
        		new altInfo({
					text:data.info
				});
        	}
        }
    });
}
//点击添加标签
 function setLabel(em){
	if($(em).hasClass("disabled")){
		return
	}else{
		$(em).addClass("disabled");
		var v = $("#upload_article_label").val();
	   	if($.trim(v)==""){
	   		$("#upload_article_label").val($(em).text());
	   	}else{
	   		$("#upload_article_label").val(v+" "+$(em).text());
	   	}
	   	validBrief();
	}
}
//选择日期和期刊时显示文字
function setTips(){
	if($.trim($("#upload_article_name").val())){
		var articleJournalName =getJournalStr($("#upload_journal_date").val(),$("#upload_type_id").val());
		$("#upload_journal_date").next().html(articleJournalName);
		$("#articleJournalName").val(articleJournalName.split(":")[1]);
	}
}
//失去焦点验证标签
function validBrief(){
	var bq = $.trim($("#upload_article_label").val());
	var sty = {"color":"#d71921","margin-left":"10px"};
    if (bq.length <= 0 || bq == null) {
        $("#upload_article_label").parent().next().css(sty).html("*请填写期刊标签");
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
    		$("#upload_article_label").parent().next().css(sty).html("*每个标签最多为40个字");
    		return  false;
    		break;
    	}
    	for(var j = 0;j<bqs.length;j++){
    		if($.trim(bqs[i])==$.trim(bqs[j])  && i!=j){
    			$("#upload_article_label").parent().next().css(sty).html("*标签重复");
    			return false;
    			break;
    		}
    	}
    }
    $("#upload_article_label").parent().next().css({"color":"#888","margin-left":"0px"}).text("（多个标签用空格分隔，每个标签最多为40个字）");
    return true;
}
//失去焦点验证关键词
function validKeyword(){
	var kw = $.trim($("#upload_article_keyword").val());
	var sty = {"color":"#d71921","margin-left":"10px"};
    if (kw.length <= 0 || kw == null) {
        $("#upload_article_keyword").next().css(sty).html("*请填写文档关键词");
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
    		$("#upload_article_keyword").next().css(sty).html("*每个关键词最多为8个字");
    		return false;
    		break;
    	}
    	for(var j = 0;j<kws.length;j++){
    		if($.trim(kws[i])==$.trim(kws[j]) && i!=j){
    			$("#upload_article_keyword").next().css(sty).html("*关键词重复");
    			return false;
    			break;
    		}
    	}
    }
    if (kws.length>15) {
        $("#upload_article_keyword").next().css(sty).html("*最多15个关键词");
        return false;
    }
    $("#upload_article_keyword").next().css({"color":"#888","margin-left":"0px"}).text("（多个关键词用空格分隔，最多15个关键词，每个关键词最多为8个字）");
    return true;
}
//弹出提交审核
function showSubmit(articleId){
	$(".submit_audit").find("em").removeClass("disabled");
	if(articleId){
		$.ajax({
	        type: "post",
	        url: "user/getArticle.do",
	        data:{articleId:articleId},
	        async:false,
	        dataType:"json",
	        success: function(data) {
	        	if(data!=null){
	        		var label = data.articleLabel;
	        		if(label){
						var ls = label.split(" ");
						for(var i=0;i<ls.length;i++){
							if($.trim(ls[i])!=""){
								$.each($(".submit_audit").find("em"),function(){
									if($(this).text()==$.trim(ls[i])){
										$(this).addClass("disabled");
									}
								});
							}
						}
					}
					$("#upload_article_id").val(data.articleId); 
					$("#upload_article_name").val(data.articleName);
					$("#upload_article_skip").val(data.articleSkip);
					$("#upload_article_label").val(label);
					$("#upload_article_keyword").val(data.articleKeyWord);
					$("#articlePrice").val(data.articlePrice);
					$('.ui_back2,.ui_back,.box_content,.submit_audit').show();
	        	}
	        }
	    });
	}
}
$("#upload_article_label").keyup(function(e){
    if(!e) var e = window.event; 
    if(e.keyCode==32){
        validBrief();
    }
 });
$("#upload_article_keyword").keyup(function(e){
    if(!e) var e = window.event; 
    if(e.keyCode==32){
        validKeyword();
    }
});
function showUploadFile(){ 
 	flag = true;
 	$('.ui_back2,.ui_back,.box_content,#upload_file').show();
}
function showBatchUploadFile(){
$('.ui_back2,.ui_back,.box_content,#upload_file_batch').show();
}
function addDate(days){
	var d=new Date();
	d.setDate(d.getDate()+days);
	var m=d.getMonth()+1;
	return d.getFullYear()+'-'+m+'-'+d.getDate();
} 
laydate({
   elem: '#upload_journal_date',
   max: laydate.now(addDate(365)),
   choose:setTips
});
//-->
</script>
</html>