<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
  	<meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=8"><!-- 放在前面 默认为IE8 -->
	<meta http-equiv="Expires" content="0">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-control" content="no-cache">
	<meta http-equiv="Cache" content="no-cache">
    <title>在线office</title>
	<link rel="stylesheet" type="text/css" href="css/word.css?time=${strTime}" />
	<link rel="stylesheet" type="text/css" href="css/style.css?time=${strTime}" />
	<link rel="stylesheet" type="text/css" href="css/tree.css?time=${strTime}" />
	<link rel="stylesheet" type="text/css" href="css/tree2.css?time=${strTime}" />
    <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>	
	<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="js/word.js?time=${strTime}"></script>

  </head>
  
<body>
	
	<!-- 编辑区域 -->
	<div name="编辑区" class="edit_body" id="editDiv">
		<div class="iframe_body" id="ifame_bo">
			<div id="fengMianDiv"></div><!-- logo.png -->
			<script id="editor" type="text/plain" style="width:900px;height:99%;"></script>
			<!-- <iframe id="eiframe"  width="100%" height="100%" frameborder="0" src="edit.html">
			</iframe> -->
			<div style="height:20px"></div>
		</div>	
		
	</div>
	<!-- 编辑区域 -->
	<div name="左边栏" class="l_listbox list_bar">
		<div class="llist_div1">
			<ul class="ul">
				<h3>大纲区</h3>
				<li class="li_last"><img src="img/llist_6.png" /></li>
				
			</ul>						
		</div>			
		<div name="树形标题" class="tree_list">
			<div id="treeFengMian"  onMouseOver="fengMianOver(this)" onMouseOut="fengMianOut(this)" onclick="fengMianClick(this)"  ><img class="tree_space" src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw=="><img class="normal" src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==">封面</div>
			<div id="tree_list"></div>
		</div>

</div>
<div name="显示左边栏" class="llist_show list_show"> <img src="img/rlist_1.png" /> </div>
<div name="显示右边栏" class="rlist_show list_show"> <img src="img/llist_1.png" /> </div>
<!-----------弹出框--------------------------------------------------------------------------------------------------------------------->
<div name="遮挡背景" class="ui_back2"></div>
<div name="遮挡背景" class="ui_back"></div>
<div class="box_content"> 
   
    <!-- 提交审核 -->
    <div name="提交审核" class="sbox_ submit_audit">
        <h3>提交审核<img src="img/s_exit.png" /></h3>
        <div class="sb_middle">
            <div> <span class="span3">名</span>称：
                <input id="articleName" type="text" value="" />
                <br>
                请选择类型：
                <input type="radio" class="radio" name="radioValue" value="1" checked="checked"/>
                模板
                <input type="radio" class="radio" value="2" name="radioValue"/>
                文档<br>
                <span class="signD"><span class="span1">设置标签：</span>
                <h4><em>投资银行业务</em><em>中小企业</em><em>投资银行业务</em><em>中小企业</em></h4></span>
                <h5 style="padding-left:78px;line-height:20px;"><strong>已选标签</strong>（多个标签用空格分隔，最多15个）</h5>
                <div style="padding-left:78px;margin:0;"><textarea maxlength="100" onfocus="this.placeholder=''"   id="brief" rows="3" cols="20" value="100字以内"></textarea>
                </div>
                <span class="span1">输入简介</span>：
                <textarea maxlength="100" onfocus="this.placeholder=''" onblur="this.placeholder='100字以内'" placeholder="100字以内" id="brief" rows="3" cols="20" value="100字以内"></textarea>
                <br>
                <span class="span2">关键字</span>：
                <input maxlength="45" onfocus="this.placeholder=''" onblur="this.placeholder='45字以内'" placeholder="45字以内" id="keyword" type="text"/>
            </div>
        </div>
        <div class="sb_button"><a class="a2"></a><a class="a1" onclick="checkSubmitClick(articleId)"></a></div>
    </div>
    
	<!-- box_content end -->
	</div>
	
	
	<!-----------end--------------------------------------------------------------------------------------------------------------------->
<div id="contentdiv" style="display:none">${content.nodeContent}</div>
<iframe src="" name="uploadIframe" id="uploadIframe" style="display:none"></iframe>	
<script type="text/javascript">
var iframeObj;
var strTime="${strTime}";
var articleId = ${article.articleId};//文章的id
var userId = ${userId};//用户的ID
var G_articleName = "${article.articleName}";//文章的名字
var ue = UE.getEditor('editor');
	ue.ready(function() {
	    ue.setContent(document.getElementById("contentdiv").innerHTML);
	    ue.focus();
	    pageload();
	});
var nodeList = eval(${list});

var picpath = "${picpath}";
if(picpath=="11"){
	$("#fengMianDiv").html("");
}else{
	$("#fengMianDiv").html('<img  width="900px" src="'+picpath+'">');
}
</script>

</body>	
</html>	
