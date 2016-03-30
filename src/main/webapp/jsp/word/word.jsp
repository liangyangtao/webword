<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<meta http-equiv="X-UA-Compatible" content="IE=8">
<!-- 放在前面 默认为IE8 -->
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<title>创享堂</title>
<link rel="stylesheet" type="text/css"
	href="word/css/word.css?time=${strTime}" />
<link rel="stylesheet" type="text/css"
	href="common/css/style.css?time=${strTime}" />
<link rel="stylesheet" type="text/css"
	href="apps/zhutree/css/tree.css?time=${strTime}" />
<link rel="stylesheet" type="text/css"
	href="apps/tree/tree.css?time=${strTime}" />
<script type="text/javascript" src="word/js/jquery-1.8.3.min.js?time=${strTime}"></script>
<script type="text/javascript" src="word/js/jquery.easing.1.3.js?time=${strTime}"></script>
<script type="text/javascript" src="word/js/word.js?time=${strTime}"></script>
<script type="text/javascript" src="word/js/laydate/laydate.js?time=${strTime}"></script>
<script type="text/javascript" charset="utf-8"
	src="ueditor/ueditor.config.js?time=${strTime}"></script>
<script type="text/javascript"
	src="ueditor/ueditor.all.js?time=${strTime}"></script>
<script type="text/javascript" src="common/js/global.js?time=${strTime}"></script>
<script type="text/javascript"
	src="apps/document/document.js?time=${strTime}"></script>
<script type="text/javascript" src="apps/document/drag.js?time=${strTime}"></script>
<script type="text/javascript"
	src="apps/zhutree/js/tree.js?time=${strTime}"></script>
<script type="text/javascript" src="apps/tree/tree.js?time=${strTime}"></script>
<script type="text/javascript" src="apps/document/document.tool.js?time=${strTime}"></script>
<script type="text/javascript" src="word/js/ajaxfileupload.js?time=${strTime}"></script>
<script type="text/javascript" src="word/js/onSearch.js?time=${strTime}"></script>
<script type="text/javascript" src="word/js/alert.js?time=${strTime}"></script>
</head>

<body>
	<div name="头部" class="cB_header">
		<div class="cbh_left">
			<a href="view/home.do"><img src="word/img/logo.png" /></a><img src="word/img/smart-word.png" style="margin-left:8px" />
		</div>
		<div class="cbh_right">
			<ul class="tool_ul">
				<li onclick="window.open('wordNew.do')"><img src="word/img/tool_1.png" /><br>新建</li>
				<li onclick="doSaveArticle()" id="liButtonSave"><img src="word/img/tool_2.png" /><br>保存</li>
				<li><img src="word/img/tool_3.png" /><br>另存为</li>
				<li id="liButtonDown" onclick="downfile(articleId,'article')"><img src="word/img/tool_4.png" /><br>下载</li>
				<li id="liButtonSubmit" ><img src="word/img/tool_5.png" /><br>提交审核</li>
				<li style="display:none"><img src="word/img/tool_6.png" /><br>导入</li>
				<li name="用户名" class="cbhr_user" onmouseover="showHeadUl();"><a target="_blank" href="user/journal/index.do">${user.userName}</a><img src="word/img/tool_b.png" /></li>
			</ul>
			<ul class="hideUl" onMouseLeave="hideHeadUl();">
            	<li class="line"><a target="_blank" href="user/journal/index.do" onClick="hideHeadUl();">我的文库</a>
				</li>
				<li class="line"><a target="_blank"  href="user/recharge.do" onClick="hideHeadUl();">我的账户</a>
				</li>
				<li><a href="javascript:;" onClick="show_exit();">退出</a>
				</li>
        </ul>		
		</div>
	</div>
	<div name="菜单" class="cB_banner">
		<span class="cbb_left">
			<ul>
				<li class="li1">编辑</li>
				<li>插入</li>
				<li>表格</li>
				<li>视图</li>
				<li>设置</li>
			</ul>
		</span>
		<span class="cbb_right">
			<img id="newArticleImg" src="word/img/cbbr_tip1.png" /><span id="newarticlename">${article.articleName}</span><span id="articleTypeSpan"></span>							 
		</span>
	</div>
	<div name="工具栏" class="cB_tools">
		<!--编辑----------------------------------------------------------------------------------------------------  -->
		<img class="t_up_down" src="word/img/t_up.png" />
		<div name="编辑" class="edit_display tdisplay tdisplay1">
		<div name="历史记录" class="cbt_div cbt_div1">
			<ul>
				<li class="li1"><div class="edui-box edui-button edui-for-undo edui-default" id="edui6"><div onmouseout="$EDITORUI[&quot;edui6&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui6&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui6&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui6&quot;].Stateful_onMouseDown(event, this);" id="edui6_state" class="edui-default edui-state-disabled"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui6&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui6&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="撤销" unselectable="on" id="edui6_body"><div class="edui-box edui-icon edui-default"></div>撤销</div></div></div></div></li><br>
				<li class="li2"><div class="edui-box edui-button edui-for-redo edui-default" id="edui7"><div onmouseout="$EDITORUI[&quot;edui7&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui7&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui7&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui7&quot;].Stateful_onMouseDown(event, this);" id="edui7_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui7&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui7&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="恢复" unselectable="on" id="edui7_body"><div class="edui-box edui-icon edui-default"></div>恢复</div></div></div></div></li>
			</ul>
			<h4>历史记录</h4>
		</div>
		<div name="剪贴板" class="cbt_div cbt_div2">
			<ul class="ul1">
				<li onclick="uePaste()" title="粘贴"><i></i><br>粘贴</li>
			</ul>
			<ul class="ul2">
				<li class="li1" onclick="ueCopy()" id="copyButton" title="复制"><i></i>&nbsp;复制</li><br>
				<li class="li2"><div class="edui-box edui-button edui-for-pasteplain edui-default" id="edui22"><div onmouseout="$EDITORUI[&quot;edui22&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui22&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui22&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui22&quot;].Stateful_onMouseDown(event, this);" id="edui22_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui22&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui22&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="纯文本粘贴模式" unselectable="on" id="edui22_body"><div class="edui-box edui-icon edui-default"></div>&nbsp;纯文本</div></div></div></div></li><br>		
				<li class="li3"><div class="edui-box edui-button edui-for-formatmatch edui-default" id="edui17"><div onmouseout="$EDITORUI[&quot;edui17&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui17&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui17&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui17&quot;].Stateful_onMouseDown(event, this);" id="edui17_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui17&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui17&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="格式刷" unselectable="on" id="edui17_body"><div class="edui-box edui-icon edui-default"></div>&nbsp;格式刷</div></div></div></div></li>
			</ul>
			<h4>剪贴板</h4> 
		</div>
		<div name="字体" class="cbt_div cbt_div3">
			<ul class="ul1">
				<li class="li1"><div class="edui-box edui-combox edui-for-fontfamily edui-default" id="edui92"><div onmouseout="$EDITORUI[&quot;edui92&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui92&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui92&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui92&quot;].Stateful_onMouseDown(event, this);" id="edui92_state" title="字体" class="edui-default"><div class="edui-combox-body edui-default"><div onclick="$EDITORUI[&quot;edui92&quot;]._onButtonClick(event, this);" class="edui-box edui-button-body edui-default" id="edui92_button_body">宋体</div><div class="edui-box edui-splitborder edui-default"></div><div onclick="$EDITORUI[&quot;edui92&quot;]._onArrowClick();" class="edui-box edui-arrow edui-default"></div></div></div></div></li>
				<!-- 字体下拉菜单 -->
				<!-- <dl name='字体下拉菜单'><dd class="dd1">宋体</dd><dd class="dd2">微软雅黑</dd><dd class="dd3">楷体</dd><dd class="dd4">黑体</dd><dd class="dd5">隶书</dd></dl> -->
				<li class="li2"><div class="edui-box edui-button edui-for-removeformat edui-default" id="edui16"><div onmouseout="$EDITORUI[&quot;edui16&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui16&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui16&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui16&quot;].Stateful_onMouseDown(event, this);" id="edui16_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui16&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui16&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="清除格式" unselectable="on" id="edui16_body"><div class="edui-box edui-icon edui-default"></div>&nbsp;&nbsp;清除格式</div></div></div></div></li>
				<li class="li3"><div class="edui-box edui-splitbutton edui-for-autotypeset edui-default" id="edui18"><div onmouseout="$EDITORUI[&quot;edui18&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui18&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui18&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui18&quot;].Stateful_onMouseDown(event, this);" id="edui18_state" title="自动排版" class="edui-default"><div class="edui-splitbutton-body edui-default"><div onclick="$EDITORUI[&quot;edui18&quot;]._onArrowClick();" class="edui-box edui-button-body edui-default" id="edui18_button_body"><div class="edui-box edui-icon edui-default"></div></div><div class="edui-box edui-splitborder edui-default" onclick="$EDITORUI[&quot;edui18&quot;]._onArrowClick();"></div><span onclick="$EDITORUI[&quot;edui18&quot;]._onArrowClick();">&nbsp;&nbsp;格式化&nbsp;</span><div onclick="$EDITORUI[&quot;edui18&quot;]._onArrowClick();" class="edui-box edui-arrow edui-default"></div></div></div></div></li>
			</ul>
			<ul class="ul2">
				<li class="li1"><div class="edui-box edui-combox edui-for-fontsize edui-default" id="edui105"><div onmouseout="$EDITORUI[&quot;edui105&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui105&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui105&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui105&quot;].Stateful_onMouseDown(event, this);" id="edui105_state" title="字号" class="edui-default"><div class="edui-combox-body edui-default"><div onclick="$EDITORUI[&quot;edui105&quot;]._onButtonClick(event, this);" class="edui-box edui-button-body edui-default" id="edui105_button_body">16px</div><div class="edui-box edui-splitborder edui-default"></div><div onclick="$EDITORUI[&quot;edui105&quot;]._onArrowClick();" class="edui-box edui-arrow edui-default"></div></div></div></div></li>
				<li class="li2" onclick="ueFontsize('add')" title="增大字体"></li>
				<li class="li3" onclick="ueFontsize('del')" title="缩小字体"></li>
				<li class="li4"><div class="edui-box edui-button edui-for-superscript edui-default" id="edui14"><div onmouseout="$EDITORUI[&quot;edui14&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui14&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui14&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui14&quot;].Stateful_onMouseDown(event, this);" id="edui14_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui14&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui14&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="上标" unselectable="on" id="edui14_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
				<li class="li5"><div class="edui-box edui-button edui-for-subscript edui-default" id="edui15"><div onmouseout="$EDITORUI[&quot;edui15&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui15&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui15&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui15&quot;].Stateful_onMouseDown(event, this);" id="edui15_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui15&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui15&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="下标" unselectable="on" id="edui15_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
				<li class="li6"><div class="edui-box edui-button edui-for-bold edui-default" id="edui9"><div onmouseout="$EDITORUI[&quot;edui9&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui9&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui9&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui9&quot;].Stateful_onMouseDown(event, this);" id="edui9_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui9&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui9&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="加粗" unselectable="on" id="edui9_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
				<li class="li7"><div class="edui-box edui-button edui-for-italic edui-default" id="edui10"><div onmouseout="$EDITORUI[&quot;edui10&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui10&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui10&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui10&quot;].Stateful_onMouseDown(event, this);" id="edui10_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui10&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui10&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="斜体" unselectable="on" id="edui10_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
				<li class="li8"><div class="edui-box edui-button edui-for-underline edui-default" id="edui11"><div onmouseout="$EDITORUI[&quot;edui11&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui11&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui11&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui11&quot;].Stateful_onMouseDown(event, this);" id="edui11_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui11&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui11&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="下划线" unselectable="on" id="edui11_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
				<li class="li9"><div class="edui-box edui-button edui-for-strikethrough edui-default" id="edui13"><div onmouseout="$EDITORUI[&quot;edui13&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui13&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui13&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui13&quot;].Stateful_onMouseDown(event, this);" id="edui13_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui13&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui13&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="删除线" unselectable="on" id="edui13_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
				<li class="li10"><div class="edui-box edui-splitbutton edui-for-forecolor edui-default edui-colorbutton" id="edui24"><div onmouseout="$EDITORUI[&quot;edui24&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui24&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui24&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui24&quot;].Stateful_onMouseDown(event, this);" id="edui24_state" title="字体颜色" class="edui-default"><div class="edui-splitbutton-body edui-default"><div onclick="$EDITORUI[&quot;edui24&quot;]._onButtonClick(event, this);" class="edui-box edui-button-body edui-default" id="edui24_button_body"><div class="edui-box edui-icon edui-default"></div><div class="edui-colorlump" id="edui24_colorlump"></div></div><div class="edui-box edui-splitborder edui-default"></div><div onclick="$EDITORUI[&quot;edui24&quot;]._onArrowClick();" class="edui-box edui-arrow edui-default"></div></div></div></div></li>
				<!-- 字体颜色下拉菜单 -->
<!-- 				<dl class="color_menu" name="字体颜色下拉菜单">
					<dd class="dd1"><i></i><a>清除颜色</a></dd>
					<dt>通用颜色</dt>
					<dd class="dd2 _dd2"><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i></dd>
					<dd class="dd2"><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i></dd>
					<dd class="dd2"><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i></dd>
					<dd class="dd2"><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i></dd>
					<dd class="dd2"><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i></dd>
					<dd class="dd2"><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i></dd>
					<dt>标准颜色</dt>
					<dd class="dd2 ddn"><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i></dd>
				</dl> -->
				<li class="li11"><div class="edui-box edui-splitbutton edui-for-backcolor edui-default edui-colorbutton" id="edui27"><div onmouseout="$EDITORUI[&quot;edui27&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui27&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui27&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui27&quot;].Stateful_onMouseDown(event, this);" id="edui27_state" title="背景色" class="edui-default"><div class="edui-splitbutton-body edui-default"><div onclick="$EDITORUI[&quot;edui27&quot;]._onButtonClick(event, this);" class="edui-box edui-button-body edui-default" id="edui27_button_body"><div class="edui-box edui-icon edui-default"></div><div class="edui-colorlump" id="edui27_colorlump"></div></div><div class="edui-box edui-splitborder edui-default"></div><div onclick="$EDITORUI[&quot;edui27&quot;]._onArrowClick();" class="edui-box edui-arrow edui-default"></div></div></div></div></li>			
			</ul>
			<h4>字体</h4>
		</div>
		<div name="段落" class="cbt_div cbt_div4">
			<ul class="ul1">
				<li class="li1"><div class="edui-box edui-menubutton edui-for-insertunorderedlist edui-default" id="edui43"><div onmouseout="$EDITORUI[&quot;edui43&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui43&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui43&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui43&quot;].Stateful_onMouseDown(event, this);" id="edui43_state" title="无序列表" class="edui-default"><div class="edui-menubutton-body edui-default"><div onclick="$EDITORUI[&quot;edui43&quot;]._onButtonClick(event, this);" class="edui-box edui-button-body edui-default" id="edui43_button_body"><div class="edui-box edui-icon edui-default"></div></div><div class="edui-box edui-splitborder edui-default"></div><div onclick="$EDITORUI[&quot;edui43&quot;]._onArrowClick();" class="edui-box edui-arrow edui-default"></div></div></div></div></li>
				<!-- 编号下拉菜单 -->
				<!-- <dl class="nub_menu" name='编号下拉菜单'><dd>○ 空心项目符号</dd><dd>● 实心项目符号</dd><dd>■ 方形项目符号</dd></dl> -->
				<li class="li2"><div class="edui-box edui-menubutton edui-for-insertorderedlist edui-default" id="edui30"><div onmouseout="$EDITORUI[&quot;edui30&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui30&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui30&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui30&quot;].Stateful_onMouseDown(event, this);" id="edui30_state" title="有序列表" class="edui-default"><div class="edui-menubutton-body edui-default"><div onclick="$EDITORUI[&quot;edui30&quot;]._onButtonClick(event, this);" class="edui-box edui-button-body edui-default" id="edui30_button_body"><div class="edui-box edui-icon edui-default"></div></div><div class="edui-box edui-splitborder edui-default"></div><div onclick="$EDITORUI[&quot;edui30&quot;]._onArrowClick();" class="edui-box edui-arrow edui-default"></div></div></div></div></li>
				<li class="li3"><div class="edui-box edui-menubutton edui-for-rowspacingtop edui-default" id="edui53"><div onmouseout="$EDITORUI[&quot;edui53&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui53&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui53&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui53&quot;].Stateful_onMouseDown(event, this);" id="edui53_state" title="段前距" class="edui-default"><div class="edui-menubutton-body edui-default"><div onclick="$EDITORUI[&quot;edui53&quot;]._onArrowClick();" class="edui-box edui-button-body edui-default" id="edui53_button_body"><div class="edui-box edui-icon edui-default"></div></div><div class="edui-box edui-splitborder edui-default"></div><div onclick="$EDITORUI[&quot;edui53&quot;]._onArrowClick();" class="edui-box edui-arrow edui-default"></div></div></div></div></li>
				<li class="li4"><div class="edui-box edui-menubutton edui-for-rowspacingbottom edui-default" id="edui60"><div onmouseout="$EDITORUI[&quot;edui60&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui60&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui60&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui60&quot;].Stateful_onMouseDown(event, this);" id="edui60_state" title="段后距" class="edui-default"><div class="edui-menubutton-body edui-default"><div onclick="$EDITORUI[&quot;edui60&quot;]._onArrowClick();" class="edui-box edui-button-body edui-default" id="edui60_button_body"><div class="edui-box edui-icon edui-default"></div></div><div class="edui-box edui-splitborder edui-default"></div><div onclick="$EDITORUI[&quot;edui60&quot;]._onArrowClick();" class="edui-box edui-arrow edui-default"></div></div></div></div></li>
			</ul>
			<ul class="ul2">
				<li class="li1"><div class="edui-box edui-button edui-for-justifyleft edui-default" id="edui121"><div onmouseout="$EDITORUI[&quot;edui121&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui121&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui121&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui121&quot;].Stateful_onMouseDown(event, this);" id="edui121_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui121&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui121&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="居左对齐" unselectable="on" id="edui121_body"><div class="edui-box edui-icon edui-default"></div><div class="edui-box edui-label edui-default"></div></div></div></div></div></li>
				<li class="li2"><div class="edui-box edui-button edui-for-justifycenter edui-default" id="edui122"><div onmouseout="$EDITORUI[&quot;edui122&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui122&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui122&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui122&quot;].Stateful_onMouseDown(event, this);" id="edui122_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui122&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui122&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="居中对齐" unselectable="on" id="edui122_body"><div class="edui-box edui-icon edui-default"></div><div class="edui-box edui-label edui-default"></div></div></div></div></div></li>
				<li class="li3"><div class="edui-box edui-button edui-for-justifyright edui-default" id="edui123"><div onmouseout="$EDITORUI[&quot;edui123&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui123&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui123&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui123&quot;].Stateful_onMouseDown(event, this);" id="edui123_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui123&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui123&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="居右对齐" unselectable="on" id="edui123_body"><div class="edui-box edui-icon edui-default"></div><div class="edui-box edui-label edui-default"></div></div></div></div></div></li>
				<li class="li4"><div class="edui-box edui-button edui-for-justifyjustify edui-default" id="edui124"><div onmouseout="$EDITORUI[&quot;edui124&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui124&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui124&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui124&quot;].Stateful_onMouseDown(event, this);" id="edui124_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui124&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui124&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="两端对齐" unselectable="on" id="edui124_body"><div class="edui-box edui-icon edui-default"></div><div class="edui-box edui-label edui-default"></div></div></div></div></div></li>
				<li class="li5" title='增加缩进' onclick="ue.execCommand( 'indent1','add' );"></li>
				<li class="li6" title="减少缩进" onclick="ue.execCommand( 'indent1','del' );"></li>				
				<img src="word/img/u_line.png" />
				<li class="li7"><div class="edui-box edui-menubutton edui-for-lineheight edui-default" id="edui67"><div onmouseout="$EDITORUI[&quot;edui67&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui67&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui67&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui67&quot;].Stateful_onMouseDown(event, this);" id="edui67_state" title="行间距" class="edui-default"><div class="edui-menubutton-body edui-default"><div onclick="$EDITORUI[&quot;edui67&quot;]._onArrowClick();" class="edui-box edui-button-body edui-default" id="edui67_button_body"><div class="edui-box edui-icon edui-default"></div></div><div class="edui-box edui-splitborder edui-default"></div><div onclick="$EDITORUI[&quot;edui67&quot;]._onArrowClick();" class="edui-box edui-arrow edui-default"></div></div></div></div></li>
			</ul>
			<h4>段落</h4>
		</div>
		<div name="样式 " class="cbt_div cbt_div5">
			<ul class="ul1">
				<li id="toolTitle0" onclick="ueParagraph('p')" unselectable="on"><img src="word/img/font-style/style_01.png" unselectable="on"/></li>
				<!--  <li><img src="word/img/font-style/style_02.png" /></li>-->
				<li id="toolTitle1" onclick="ueParagraph('h1')"><img src="word/img/font-style/style_03.png" /></li>
				<li id="toolTitle2" onclick="ueParagraph('h2')"><img src="word/img/font-style/style_04.png"  /></li>
				<li id="toolTitle3" onclick="ueParagraph('h3')"><img src="word/img/font-style/style_05.png" /></li>
				<li id="toolTitle4" onclick="ueParagraph('h4')"><img src="word/img/font-style/style_06.png" /></li>
				<li id="toolTitle5" onclick="ueParagraph('h5')"><img src="word/img/font-style/style_07.png" /></li>
				<li id="toolTitle6" onclick="ueParagraph('h6')"><img src="word/img/font-style/style_08.png" /></li>
				<!--<li><img src="word/img/font-style/style_09.png" /></li>
				<li><img src="word/img/font-style/style_10.png" /></li>
				<li><img src="word/img/font-style/style_11.png" /></li>
				 <li><img src="word/img/font-style/style_12.png" /></li>
				<li><img src="word/img/font-style/style_13.png" /></li>
				<li><img src="word/img/font-style/style_14.png" /></li>
				<li><img src="word/img/font-style/style_15.png" /></li>
				<li><img src="word/img/font-style/style_16.png" /></li>
				<li><img src="word/img/font-style/style_17.png" /></li>
				<li><img src="word/img/font-style/style_18.png" /></li>
				<li><img src="word/img/font-style/style_19.png" /></li>
				<li><img src="word/img/font-style/style_20.png" /></li>
				<li><img src="word/img/font-style/style_21.png" /></li>
				<li><img src="word/img/font-style/style_22.png" /></li>
				<li><img src="word/img/font-style/style_23.png" /></li>
				<li><img src="word/img/font-style/style_24.png" /></li>
				<li><img src="word/img/font-style/style_25.png" /></li>
				<li><img src="word/img/font-style/style_26.png" /></li>
				<li><img src="word/img/font-style/style_27.png" /></li>
				<li><img src="word/img/font-style/style_28.png" /></li> -->
			</ul>
			<ul class="ul2">
				<li class="li1"></li><br>
				<li class="li2"></li><br>
				<li class="li3"></li>
			</ul>
			<h4>样式</h4>
		</div>
		<div name="编辑" class="cbt_div cbt_div6">
			<ul>
				<li class="li1"><div class="edui-box edui-button edui-for-searchreplace1 edui-default" id="edui276"><div onmouseout="$EDITORUI[&quot;edui276&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui276&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui276&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui276&quot;].Stateful_onMouseDown(event, this);" id="edui276_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui276&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui276&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="查询替换" unselectable="on" id="edui276_body"><div class="edui-box edui-icon edui-default"></div><div class="edui-box edui-label edui-default"></div> 查找</div></div></div></div></li>
				<li class="li2"><div class="edui-box edui-button edui-for-wordimage edui-default" id="edui247"><div onmouseout="$EDITORUI[&quot;edui247&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui247&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui247&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui247&quot;].Stateful_onMouseDown(event, this);" id="edui247_state" class="edui-default edui-state-disabled"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui247&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui247&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="图片转存" unselectable="on" id="edui247_body"><div class="edui-box edui-icon edui-default"></div><div class="edui-box edui-label edui-default"></div>&nbsp;转存</div></div></div></div></li>
			</ul>
			<h4>编辑</h4>
		</div>
		</div>
		<!--插入----------------------------------------------------------------------------------------------------  -->
		<div name="插入" class="insert_display tdisplay tdisplay2">
		<div name="页" class="cbt_div cbt_div7">
			<ul>
				<li  onclick="return $EDITORUI[&quot;edui220&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui220&quot;]._onMouseDown(event, this);" ><div class="edui-box edui-button edui-for-pagebreak edui-default" id="edui220"><div onmouseout="$EDITORUI[&quot;edui220&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui220&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui220&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui220&quot;].Stateful_onMouseDown(event, this);" id="edui220_state" class="edui-default"><div class="edui-button-wrap edui-default"><div class="edui-button-body edui-default" title="分割线" unselectable="on" id="edui220_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div><br>分割线</li>
			</ul>
			<h4>线</h4>
		</div>
		<div name="字符" class="cbt_div cbt_div8">
			<ul>
				<li onclick="return $EDITORUI[&quot;edui237&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui237&quot;]._onMouseDown(event, this);"><div class="edui-box edui-button edui-for-spechars edui-default" id="edui237"><div onmouseout="$EDITORUI[&quot;edui237&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui237&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui237&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui237&quot;].Stateful_onMouseDown(event, this);" id="edui237_state" class="edui-default"><div class="edui-button-wrap edui-default"><div  class="edui-button-body edui-default" title="字符" unselectable="on" id="edui237_body"><div class="edui-box edui-icon edui-default"></div><div class="edui-box edui-label edui-default"></div></div></div></div></div><br>字符</li>
			</ul>
			<h4>页</h4>
		</div>
		<div name="链接" class="cbt_div cbt_div9">
			<ul>
				<li class="li1"><div class="edui-box edui-button edui-for-link edui-default" id="edui135"><div onmouseout="$EDITORUI[&quot;edui135&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui135&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui135&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui135&quot;].Stateful_onMouseDown(event, this);" id="edui135_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui135&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui135&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="添加链接" unselectable="on" id="edui135_body"><div class="edui-box edui-icon edui-default"></div><div class="edui-box edui-label edui-default"></div>&nbsp;&nbsp;添加链接</div></div></div></div></li>
				<li class="li2" title="取消链接"><div class="edui-box edui-button edui-for-unlink edui-default" id="edui136"><div onmouseout="$EDITORUI[&quot;edui136&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui136&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui136&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui136&quot;].Stateful_onMouseDown(event, this);" id="edui136_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui136&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui136&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="取消链接" unselectable="on" id="edui136_body"><div class="edui-box edui-icon edui-default"></div>&nbsp;&nbsp;取消链接</div></div></div></div></li>
			</ul>
			<h4>链接</h4>
		</div>
		<div name="图片" class="cbt_div cbt_div10">
			<ul class="ul3"><li onclick="return $EDITORUI[&quot;edui153&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui153&quot;]._onMouseDown(event, this);"><div class="edui-box edui-button edui-for-insertimage edui-default" id="edui153"><div onmouseout="$EDITORUI[&quot;edui153&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui153&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui153&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui153&quot;].Stateful_onMouseDown(event, this);" id="edui153_state" class="edui-default"><div class="edui-button-wrap edui-default"><div  class="edui-button-body edui-default" title="多图上传" unselectable="on" id="edui153_body"><div class="edui-box edui-icon edui-default"></div><div class="edui-box edui-label edui-default"></div></div></div></div></div><br>图片</li></ul>
			<ul class="ul1">
				<li class="li1" title="单图上传"><div class="edui-box edui-button edui-for-simpleupload edui-default" id="edui148"><div onmouseout="$EDITORUI[&quot;edui148&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui148&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui148&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui148&quot;].Stateful_onMouseDown(event, this);" id="edui148_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui148&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui148&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="单图上传" unselectable="on" id="edui148_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
				<li class="li2"><div class="edui-box edui-splitbutton edui-for-emotion edui-default" id="edui154"><div onmouseout="$EDITORUI[&quot;edui154&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui154&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui154&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui154&quot;].Stateful_onMouseDown(event, this);" id="edui154_state" title="表情" class="edui-default"><div class="edui-splitbutton-body edui-default" onclick="$EDITORUI[&quot;edui154&quot;]._onArrowClick();"><div onclick="$EDITORUI[&quot;edui154&quot;]._onButtonClick(event, this);" class="edui-box edui-button-body edui-default" id="edui154_button_body"><div class="edui-box edui-icon edui-default"></div></div><div class="edui-box edui-splitborder edui-default"></div><div onclick="$EDITORUI[&quot;edui154&quot;]._onArrowClick();" class="edui-box edui-arrow edui-default"></div>&nbsp;&nbsp;表情</div></div></div></li>				
			</ul>
			<ul class="ul2">
				<li class="li1"><div class="edui-box edui-button edui-for-snapscreen edui-default" id="edui238"><div onmouseout="$EDITORUI[&quot;edui238&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui238&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui238&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui238&quot;].Stateful_onMouseDown(event, this);" id="edui238_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui238&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui238&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="截图" unselectable="on" id="edui238_body"><div class="edui-box edui-icon edui-default"></div><div class="edui-box edui-label edui-default"></div>&nbsp;&nbsp;截图</div></div></div></div></li>
				<li class="li2"><div class="edui-box edui-button edui-for-scrawl edui-default" id="edui160"><div onmouseout="$EDITORUI[&quot;edui160&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui160&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui160&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui160&quot;].Stateful_onMouseDown(event, this);" id="edui160_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui160&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui160&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="涂鸦" unselectable="on" id="edui160_body"><div class="edui-box edui-icon edui-default"></div><div class="edui-box edui-label edui-default"></div>&nbsp;&nbsp;涂鸦</div></div></div></div></li>				
			</ul>
			<h4>图片</h4>
		</div>
		<div name="表格" class="cbt_div cbt_div11">
			<ul>
				<!-- <li><div class="edui-box edui-splitbutton edui-for-inserttable edui-default" id="edui249"><div onmouseout="$EDITORUI[&quot;edui249&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui249&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui249&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui249&quot;].Stateful_onMouseDown(event, this);" id="edui249_state" title="插入表格" class="edui-default"><div class="edui-splitbutton-body edui-default"><div onclick="$EDITORUI[&quot;edui249&quot;]._onButtonClick(event, this);" class="edui-box edui-button-body edui-default" id="edui249_button_body"><div class="edui-box edui-icon edui-default"></div></div><div class="edui-box edui-splitborder edui-default"></div><div onclick="$EDITORUI[&quot;edui249&quot;]._onArrowClick();" class="edui-box edui-arrow edui-default"></div></div></div></div><br>表格</li> -->
			</ul>
			<h4>表格</h4>
		</div>
		<div name="封面" class="cbt_div cbt_div12">
			<ul>
				<li title="封面"><img src="word/img/fengmian.png"/><br>封面</li>
			</ul>
			<h4>封面</h4>
		</div>
		</div>
		<!--表格----------------------------------------------------------------------------------------------------  -->
		<div name="表格" class="table_display tdisplay tdisplay3">
		<div name="表格" class="cbt_div cbt_div13">
			<ul class="ul1"><li onclick="$EDITORUI[&quot;edui249&quot;]._onButtonClick(event, this);"><div class="edui-box edui-splitbutton edui-for-inserttable edui-default" id="edui249"><div onmouseout="$EDITORUI[&quot;edui249&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui249&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui249&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui249&quot;].Stateful_onMouseDown(event, this);" id="edui249_state" title="插入表格" class="edui-default"><div class="edui-splitbutton-body edui-default"><div  class="edui-box edui-button-body edui-default" id="edui249_button_body"><div class="edui-box edui-icon edui-default"></div></div><div class="edui-box edui-splitborder edui-default"></div><div onclick="$EDITORUI[&quot;edui249&quot;]._onArrowClick();" class="edui-box edui-arrow edui-default"></div></div></div></div><br>插入表格</li></ul>
			<ul class="ul2"><li onclick="return $EDITORUI[&quot;edui252&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui252&quot;]._onMouseDown(event, this);"><div class="edui-box edui-button edui-for-deletetable edui-default" id="edui252"><div onmouseout="$EDITORUI[&quot;edui252&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui252&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui252&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui252&quot;].Stateful_onMouseDown(event, this);" id="edui252_state" class="edui-default"><div class="edui-button-wrap edui-default"><div  class="edui-button-body edui-default" title="删除表格" unselectable="on" id="edui252_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div><br>删除表格</li></ul>
			<ul class="ul3">
				<li class="li1"><div class="edui-box edui-button edui-for-insertrow edui-default" id="edui254"><div onmouseout="$EDITORUI[&quot;edui254&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui254&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui254&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui254&quot;].Stateful_onMouseDown(event, this);" id="edui254_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui254&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui254&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="前插入行" unselectable="on" id="edui254_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
				<li class="li2"><div class="edui-box edui-button edui-for-insertcol edui-default" id="edui256"><div onmouseout="$EDITORUI[&quot;edui256&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui256&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui256&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui256&quot;].Stateful_onMouseDown(event, this);" id="edui256_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui256&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui256&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="前插入列" unselectable="on" id="edui256_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
			</ul><br/>
			<ul class="ul4">
				<li class="li1"><div class="edui-box edui-button edui-for-deleterow edui-default" id="edui255"><div onmouseout="$EDITORUI[&quot;edui255&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui255&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui255&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui255&quot;].Stateful_onMouseDown(event, this);" id="edui255_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui255&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui255&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="删除行" unselectable="on" id="edui255_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
				<li class="li2"><div class="edui-box edui-button edui-for-deletecol edui-default" id="edui257"><div onmouseout="$EDITORUI[&quot;edui257&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui257&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui257&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui257&quot;].Stateful_onMouseDown(event, this);" id="edui257_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui257&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui257&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="删除列" unselectable="on" id="edui257_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
			</ul>
			<h4>表格</h4>
		</div>
		<div name="合并单元格" class="cbt_div cbt_div14">
			<ul class="ul1">
				<li class="li1"><div class="edui-box edui-button edui-for-mergecells edui-default" id="edui258"><div onmouseout="$EDITORUI[&quot;edui258&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui258&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui258&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui258&quot;].Stateful_onMouseDown(event, this);" id="edui258_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui258&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui258&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="合并多个单元格" unselectable="on" id="edui258_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
				<li class="li2"><div class="edui-box edui-button edui-for-mergedown edui-default" id="edui260"><div onmouseout="$EDITORUI[&quot;edui260&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui260&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui260&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui260&quot;].Stateful_onMouseDown(event, this);" id="edui260_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui260&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui260&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="下合并单元格" unselectable="on" id="edui260_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
				<li class="li3"><div class="edui-box edui-button edui-for-mergeright edui-default" id="edui259"><div onmouseout="$EDITORUI[&quot;edui259&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui259&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui259&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui259&quot;].Stateful_onMouseDown(event, this);" id="edui259_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui259&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui259&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="右合并单元格" unselectable="on" id="edui259_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
			</ul><br>
			<ul class="ul2">
				<li class="li1"><div class="edui-box edui-button edui-for-splittocells edui-default" id="edui261"><div onmouseout="$EDITORUI[&quot;edui261&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui261&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui261&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui261&quot;].Stateful_onMouseDown(event, this);" id="edui261_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui261&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui261&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="完全拆分单元格" unselectable="on" id="edui261_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
				<li class="li2"><div class="edui-box edui-button edui-for-splittocols edui-default" id="edui263"><div onmouseout="$EDITORUI[&quot;edui263&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui263&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui263&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui263&quot;].Stateful_onMouseDown(event, this);" id="edui263_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui263&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui263&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="拆分成列" unselectable="on" id="edui263_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
				<li class="li3"><div class="edui-box edui-button edui-for-splittorows edui-default" id="edui262"><div onmouseout="$EDITORUI[&quot;edui262&quot;].Stateful_onMouseOut(event, this);" onmouseover="$EDITORUI[&quot;edui262&quot;].Stateful_onMouseOver(event, this);" onmouseup="$EDITORUI[&quot;edui262&quot;].Stateful_onMouseUp(event, this);" onmousedown="$EDITORUI[&quot;edui262&quot;].Stateful_onMouseDown(event, this);" id="edui262_state" class="edui-default"><div class="edui-button-wrap edui-default"><div onclick="return $EDITORUI[&quot;edui262&quot;]._onClick(event, this);" onmousedown="return $EDITORUI[&quot;edui262&quot;]._onMouseDown(event, this);" class="edui-button-body edui-default" title="拆分成行" unselectable="on" id="edui262_body"><div class="edui-box edui-icon edui-default"></div></div></div></div></div></li>
			</ul>
			<h4>合并单元格</h4>
		</div>		
		</div>
		<!--视图----------------------------------------------------------------------------------------------------  -->
		<div name="视图" class="view_display tdisplay tdisplay4">
		<div name="大纲区" class="cbt_div cbt_div15">
			<ul>
				<li title="显示或隐藏大纲区"><img src="word/img/view1.png"><br>显示\隐藏</li>
			</ul>
			<h4>大纲区</h4>
		</div>
		<div name="知识资料区" class="cbt_div cbt_div16">
			<ul>
				<li title="显示或隐藏知识资料区"><img src="word/img/view2.png"><br>显示\隐藏</li>
			</ul>
			<h4>知识资料区</h4>
		</div>		
		</div>
		<!--设置----------------------------------------------------------------------------------------------------  -->
		<div name="设置" class="set_display tdisplay tdisplay5">
		<div name="属性设置" class="cbt_div cbt_div17">
			<ul>
				<li class="li1" title="全局属性"><img src="word/img/set1.png"><br>全局属性</li>
				<li class="li2" title="大纲属性"><img src="word/img/set2.png"><br>大纲属性</li>
			</ul>
			<h4>属性设置</h4>
		</div>
		<div name="字数统计" class="cbt_div cbt_div18" style="display:none">
			<ul>
				<li title="字数统计"><img src="word/img/wordcount.png"><br>字数统计</li>
			</ul>
			<h4>字数统计</h4>
		</div>
		<div name="打印" class="cbt_div cbt_div19">
			<ul>
				<li onclick="ue.execCommand( 'print' )" title="打印"><img src="word/img/print.png"><br>打印</li>
			</ul>
			<h4>打印</h4>
		</div>		
		</div>
	</div>
	
	


	<!-- 编辑区域 -->
	<div name="编辑区" class="edit_body" id="editDiv">
		<div class="iframe_body" id="ifame_bo">
			<div id="fengMianDiv"></div>
			<!-- logo.png -->
			<script id="editor" type="text/plain" style="width:900px;height:99%" ></script>
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
				<li class="li_last" title="关闭"><img src="word/img/llist_5.png" />
				</li>
				<!--  <li><img src="word/img/llist_4.png" /></li>
				<li><img src="word/img/llist_3.png" /></li>-->
				<li title="收缩"><img class="direct" src="word/img/llist_1.png" />
				</li>
				<li title="添加一个标题" style="display:none;"><img
					src="word/img/llist_2.png" /></li>
			</ul>
		</div>
		<div name="树形标题" class="tree_list">
			<div id="treeFengMian" onMouseOver="fengMianOver(this)"
				onMouseOut="fengMianOut(this)" onclick="fengMianClick(this)">
				<img class="tree_space"
					src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw=="><img
					class="normal"
					src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==">封面
			</div>
			<div id="tree_list"></div>
		</div>
		<!--  
		<div name="树形标题" class="tree_list" style="display:none">
			<ul>
				<li class="tree1" name="一级"><img src="word/img/tree2.png" /><img src="word/img/tree1.png" />封面</li>
				<li class="tree1" name="一级带下拉菜单"><img src="word/img/tree3.png" /><img src="word/img/tree1.png" />键入标题</li>
				<li class="tree4" name="四级"><img src="word/img/tree2.png" /><img src="word/img/tree1.png" />键入标题</li>
				<li class="tree4" name="四级带下拉菜单"><img src="word/img/tree3.png" /><img src="word/img/tree1.png" />键入标题</li>
				<li class="tree2" name="二级"><img src="word/img/tree2.png" /><img src="word/img/tree1.png" />键入标题</li>
				<li class="tree2" name="二级带下拉菜单"><img src="word/img/tree3.png" /><img src="word/img/tree1.png" />键入标题</li>
				<li class="tree4" name="四级"><img src="word/img/tree2.png" /><img src="word/img/tree1.png" />键入标题</li>
				<li class="tree4" name="四级带下拉菜单"><img src="word/img/tree3.png" /><img src="word/img/tree1.png" />键入标题</li>
				<li class="tree3" name="三级"><img src="word/img/tree2.png" /><img src="word/img/tree1.png" />键入标题</li>
				<li class="tree3" name="三级带下拉菜单"><img src="word/img/tree3.png" /><img src="word/img/tree1.png" />键入标题</li>
				<li class="tree4" name="四级"><img src="word/img/tree2.png" /><img src="word/img/tree1.png" />键入标题</li>
				<li class="tree4" name="四级带下拉菜单"><img src="word/img/tree3.png" /><img src="word/img/tree1.png" />键入标题</li>
				<li class="tree2" name="二级"><img src="word/img/tree2.png" /><img src="word/img/tree1.png" />键入标题</li>
				<li class="tree2" name="二级带下拉菜单"><img src="word/img/tree3.png" /><img src="word/img/tree1.png" />键入标题</li>
			</ul>
		</div>
		-->
		<div name="右边框" class="llist_div2">
			<img class="direct" src="word/img/llist_d.png" />
		</div>
	</div>
	<div name="显示左边栏" class="llist_show list_show">
		<img src="word/img/rlist_1.png" />
	</div>
	<div name="右边栏" class="r_listbox list_bar">
		<div class="rlist_div1">
			<ul class="ul">
				<h3>知识资料区</h3>
				<li class="li_last"><img src="word/img/llist_5.png" /></li>
				<li><img class="direct" src="word/img/rlist_1.png" /></li>
			</ul>
		</div>
		<div class="list_tp" name="列表头部">
			<ul class="ul">
				<li class="active">新闻</li>
				<li>文档</li>
				<li>插件</li>
				<li>模板</li>
			</ul>
			<div class="div div1">
				<input type="text" id="newsInput" /> <a class="a1" id="newsButton">搜索</a><a
					class="a2">高级搜索</a><img src="word/img/xx.png" />
			</div>
			<div class="div div2">
				<input type="text" id="articleInput" /> <a class="a1"
					id="articleButton">搜索</a><img src="word/img/xx.png" />
			</div>
			<div class="div div3">
				<input type="text" id="pluginInput" /> <a class="a1"
					id="pluginButton">搜索</a><img src="word/img/xx.png" />
			</div>
			<div class="div div4">
				<input type="text" id="TemplateInput" /> <a class="a1"
					id="TemplaButton">搜索</a><img src="word/img/xx.png" />
			</div>
			<div name="搜索提示" class="ser_tip">
				<span class="s1" id="msgSpan">找到相关新闻591888篇</span><span class="s2">按焦点排序<img
					src="word/img/t_down.png" /> </span>
				<ul name="排序选择框" class="s_tip">
					<li onclick="changeSearchType(2)">按焦点排序</li>
					<li onclick="changeSearchType(1)">按时间排序</li>
				</ul>
			</div>
		</div>
		<!-- 新闻 ---------------------------------------------------------------------------------------------------------->
		<div name="最新新闻和猜你喜欢" class="news_like document1">
			<img class="bk" src="word/img/bk.png" /> <a class="a1 an active">最新新闻</a><a
				class="a2 an">我的新闻</a><a class="a3 an">猜你喜欢</a>
			<div class="news_box">
				<div name="最新新闻" class="news_div lan an1" id="newsAllDiv">
					<!-- 循环项 
				<ul class="ul">
					<li><img src="word/img/box_i.png" /><a title="1美国2架F18战机因机械故障迫降台南机场（图）"> 1美国2架F18战机因机械故障迫降台南机场（图）</a></li><li class="li2"><img src="word/img/list_o.png"/><img src="word/img/poi.png"> 1小时前  2015-04-01</li>
				</ul>-->
					<!-- 循环 -->
					<!--  <p class="no_answer">抱歉！没有您要搜索的内容。</p> -->
				</div>
				<div name="我的新闻" class="news_div lan an2" id="newsMyDiv">
					<!-- 循环项 -->
					<ul class="ul">
						<li><img src="word/img/box_i.png" /><a
							title="1美国2架F18战机因机械故障迫降台南机场（图）"> 1美国2架F18战机因机械故障迫降台南机场（图）</a></li>
						<li class="li2"><img src="word/img/poi.png"> 1小时前
							2015-04-01</li>
					</ul>
					<!-- 循环 -->

				</div>
				<div name="猜你喜欢" class="like_div lan an3" id="newsLikeDiv">
					<!-- 循环项 -->
					<ul class="ul">
						<li>2<img src="word/img/box_i.png" /><a
							title="1美国2架F18战机因机械故障迫降台南机场（图）"> 美国2架F18战机因机械故障迫降台南机场（图）</a></li>
						<li class="li2"><img src="word/img/list_o.png" /><img
							src="word/img/poi.png"> 1小时前 2015-04-01</li>
					</ul>
					<!-- 循环 -->
					<ul class="ul">
						<li>2<img src="word/img/box_i.png" /><a
							title="1美国2架F18战机因机械故障迫降台南机场（图）"> 美国2架F18战机因机械故障迫降台南机场（图）</a></li>
						<li class="li2"><img src="word/img/list_o.png" /><img
							src="word/img/poi.png"> 1小时前 2015-04-01</li>
					</ul>
					<ul class="ul">
						<li>2<img src="word/img/box_i.png" /><a
							title="1美国2架F18战机因机械故障迫降台南机场（图）"> 美国2架F18战机因机械故障迫降台南机场（图）</a></li>
						<li class="li2"><img src="word/img/list_o.png" /><img
							src="word/img/poi.png"> 1小时前 2015-04-01</li>
					</ul>
					<ul class="ul">
						<li>2<img src="word/img/box_i.png" /><a
							title="1美国2架F18战机因机械故障迫降台南机场（图）"> 美国2架F18战机因机械故障迫降台南机场（图）</a></li>
						<li class="li2"><img src="word/img/list_o.png" /><img
							src="word/img/poi.png"> 1小时前 2015-04-01</li>
					</ul>
				</div>
			</div>
			<div class="n_list_page anPage">
				<ul name="列表" class="n_list n_list1" id="newsAllPage">
					<li class="l1">&lt;上一页</li>
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li class="l_last">下一页&gt;</li>
				</ul>
				<ul name="列表" class="n_list n_list2" id="newsMyPage">
					<li class="l1">&lt;上一页</li>
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li class="l_last">下一页&gt;</li>
				</ul>
				<ul name="列表" class="n_list n_list3" id="newsLikePage">
					<li class="l1">&lt;上一页</li>
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li class="l_last">下一页&gt;</li>
				</ul>
				</li>
			</div>
		</div>
		<!-- 文档 ---------------------------------------------------------------------------------------------------------->
		<div name="平台文档、我的文档、共创文档、猜你喜欢" class="news_like document2">
			<img class="bk" src="word/img/bk.png" /> <a class="b1 bn active">平台文档</a><a
				class="b2 bn">我的文档</a><a class="b3 bn">共创文档</a><a class="b4 bn">猜你喜欢</a>
			<div class="news_box">
				<div name="平台文档" class="news_div lbn bn1" id="articleAllDiv">
					<!-- 循环项 -->
					<ul class="ul">
						<h3>
							<a title="行业研究每日要参4月2日">行业研究每日要参4月2日</a><img
								src="word/img/poi.png" />
						</h3>
						<li style="display:none">
							<div>节点内容</div>
						</li>
						<!--  
					<li class="treec">第一章 本期关注<img src="word/img/poi.png"/></li>
					<li class="tree_h">第二章 信贷环境<img src="word/img/poi.png"/></li>	
					<li class="treec">第一章 信贷政策<img src="word/img/poi.png"/></li>
					<li class="treec">第一章 信贷政策<img src="word/img/poi.png"/></li>
					<li class="treec">第一章 信贷政策<img src="word/img/poi.png"/></li>
					-->
						<span class="li2"><img src="word/img/list_o.png" /> 1小时前
							2015-04-01</span>
					</ul>
					<!-- 循环 -->
				</div>
				<div name="我的文档" class="news_div lbn bn2" id="articleMyDiv">
					<!-- 循环项 -->
					<ul class="ul">
						<h3>
							<a title="行业研究每日要参4月2日">行业研究每日要参4月2日</a><img
								src="word/img/poi.png" />
						</h3>
						<li class="treec">第一章 本期关注<img src="word/img/poi.png" /></li>
						<li class="tree_h">第二章 信贷环境<img src="word/img/poi.png" /></li>
						<li class="treec">第一章 信贷政策<img src="word/img/poi.png" /></li>
						<li class="treec">第一章 信贷政策<img src="word/img/poi.png" /></li>
						<li class="treec">第一章 信贷政策<img src="word/img/poi.png" /></li>
						<span class="li2"> 1小时前 2015-04-01</span>
					</ul>
					<!-- 循环 -->

				</div>
				<div name="共创文档" class="news_div lbn bn3" id="articleCreateDiv">
					<!-- 循环项 -->
					<ul class="ul">
					<span>  &nbsp;建设中,敬请期待....</span>
					<!--  
						<h3>
							<a title="行业研究每日要参4月2日">行业研究每日要参4月2日</a><img
								onclick="pre_mydocumet($(this).siblings('.li1').text(),462)"
								src="word/img/poi.png" />
						</h3>
						<li class="treec">第一章 本期关注<img class="imgView"
							onclick="pre_mydocumet($(this).siblings('.li1').text(),462)"
							src="word/img/poi.png" /></li>
						<span class="li2"> 1小时前 2015-04-01</span>
					-->
					</ul>
					<!-- 循环 -->

				</div>
				<div name="猜你喜欢" class="like_div lbn bn4" id="articleLikeDiv">
					<!-- 循环项 -->
					<ul class="ul">
						<h3>
							<a title="行业研究每日要参4月2日">行业研究每日要参4月2日</a><img
								src="word/img/poi.png" />
						</h3>
						<li class="treec">第一章 本期关注<img src="word/img/poi.png" /></li>
						<li class="tree_h">第二章 信贷环境<img src="word/img/poi.png" /></li>
						<li class="treec">第一章 信贷政策<img src="word/img/poi.png" /></li>
						<li class="treec">第一章 信贷政策<img src="word/img/poi.png" /></li>
						<li class="treec">第一章 信贷政策<img src="word/img/poi.png" /></li>
						<span class="li2"><img src="word/img/list_o.png" /> 1小时前
							2015-04-01</span>
					</ul>
					<!-- 循环 -->

				</div>
			</div>
			<div class="n_list_page bnPage">
				<ul name="列表" class="n_list n_list1" id="articleAllPage">
					<li class="l1">&lt;上一页</li>
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li class="l_last">下一页&gt;</li>
				</ul>
				<ul name="列表" class="n_list n_list2" id="articleMyPage">
					<li class="l1">&lt;上一页</li>
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li class="l_last">下一页&gt;</li>
				</ul>
				<ul name="列表" class="n_list n_list3" id="articleCreatePage">
					<li class="l1" style="color:#fff;background-color:#099266">1</li>
					</li>
				</ul>
				<ul name="列表" class="n_list n_list4" id="articleLikePage">
					<li class="l1">&lt;上一页</li>
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li class="l_last">下一页&gt;</li>
				</ul>
			</div>
		</div>
		<!-- 插件 ---------------------------------------------------------------------------------------------------------->
		<div name="全部插件 当前使用插件 我的插件和猜你喜欢" class="news_like document3">
			<img class="bk" src="word/img/bk.png" /> <a class="c1 cn active">全部插件</a><a
				class="c2 cn">我的插件</a><a class="c3 cn">当前插件</a><a class="c4 cn">猜你喜欢</a>
			<div class="news_box">
				<div name="全部插件" class="news_div lcn cn1" id="pluginAllDiv">
					<!-- 循环项 -->
					<ul class="ul">
						<li class="li1" title="消费情况分析"><img src="word/img/box_i.png" />消费情况分析</li>
						<li class="li2"><img src="word/img/list_o.png" /><img
							src="word/img/poi.png" /></li>
					</ul>
					<!-- 循环 -->
					<ul class="ul">
						<li class="li1" title="进出口情况分析"><img src="word/img/box_i.png" />进出口情况分析</li>
						<li class="li2"><img src="word/img/list_o.png" /><img
							src="word/img/poi.png" /></li>
					</ul>
					<ul class="ul">
						<li class="li1" title="经济增长情况分析"><img
							src="word/img/box_i.png" />经济增长情况分析</li>
						<li class="li2"><img src="word/img/list_o.png" /><img
							src="word/img/poi.png" /></li>
					</ul>
					<ul class="ul">
						<li class="li1" title="消费情况分析"><img src="word/img/box_i.png" />消费情况分析</li>
						<li class="li2"><img src="word/img/list_o.png" /><img
							src="word/img/poi.png" /></li>
					</ul>
					<ul class="ul">
						<li class="li1" title="进出口情况分析"><img src="word/img/box_i.png" />进出口情况分析</li>
						<li class="li2"><img src="word/img/list_o.png" /><img
							src="word/img/poi.png" /></li>
					</ul>
					<ul class="ul">
						<li class="li1" title="经济增长情况分析"><img
							src="word/img/box_i.png" />经济增长情况分析</li>
						<li class="li2"><img src="word/img/list_o.png" /><img
							src="word/img/poi.png" /></li>
					</ul>
					<ul class="ul">
						<li class="li1" title="消费情况分析"><img src="word/img/box_i.png" />消费情况分析</li>
						<li class="li2"><img src="word/img/list_o.png" /><img
							src="word/img/poi.png" /></li>
					</ul>
				</div>
				<div name="我的插件" class="news_div lcn cn2" id="pluginMyDiv">
					<!-- 循环项 -->
					<ul class="ul">
						<li class="li1" title="消费情况分析"><img src="word/img/box_i.png" />消费情况分析</li>
						<li class="li2"><img src="word/img/poi.png" /></li>
					</ul>
					<!-- 循环 -->

				</div>
				<div name="当前使用插件" class="news_div lcn cn3" id="pluginUserDiv">
					<!-- 循环项 -->
					<ul class="ul">
						<li class="li1" title="消费情况分析"><img src="word/img/box_i.png" />消费情况分析</li>
						<li class="li2"><img
							onclick="save_myslugins($(this).siblings('.li1').text(),5)"
							src="word/img/list_o.png" /><img
							onclick="pre_views_plug($(this).siblings('.li1').text(),5)"
							src="word/img/poi.png" /></li>
					</ul>
					<!-- 循环 -->

				</div>
				<div name="猜你喜欢" class="like_div lcn cn4" id="pluginLikeDiv">
					<!-- 循环项 -->
					<ul class="ul">
						<li class="li1" title="消费情况分析"><img src="word/img/box_i.png" />消费情况分析</li>
						<li class="li2"><img src="word/img/list_o.png" /><img
							src="word/img/poi.png" /></li>
					</ul>
					<!-- 循环 -->
				</div>
			</div>
			<div class="n_list_page cnPage">
				<ul name="列表" class="n_list n_list1" id="pluginAllPage">
					<li class="l1">&lt;上一页</li>
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li class="l_last">下一页&gt;</li>
				</ul>
				<ul name="列表" class="n_list n_list2" id="pluginMyPage">
					<li class="l1">&lt;上一页</li>
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li class="l_last">下一页&gt;</li>
				</ul>
				<ul name="列表" class="n_list n_list3" id="pluginUserPage">
					<li class="l1">&lt;上一页</li>
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li class="l_last">下一页&gt;</li>
				</ul>
				<ul name="列表" class="n_list n_list4" id="pluginLikePage">
					<li class="l1">&lt;上一页</li>
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li class="l_last">下一页&gt;</li>
				</ul>
			</div>
		</div>
		<!-- 模板 ---------------------------------------------------------------------------------------------------------->
		<div name="平台模板、我的模板、共创模板、猜你喜欢" class="news_like document4">
			<img class="bk" src="word/img/bk.png" /> <a class="d1 dn active">平台模板</a><a
				class="d2 dn">我的模板</a><a class="d3 dn">猜你喜欢</a>
			<div class="news_box">
				<div name="平台模板" class="news_div ldn dn1" id="TemplateAllDiv">
					<!-- 循环项 -->
					<ul class="ul">
						<h3>
							<a title="行业研究每日要参4月2日">行业研究每日要参4月2日</a><img
								src="word/img/poi.png" />
						</h3>
						<li style="display:none">
							<div>节点内容</div>
						</li>
						<!--  
					<li class="treec">第一章 本期关注<img src="word/img/poi.png"/></li>
					<li class="tree_h">第二章 信贷环境<img src="word/img/poi.png"/></li>	
					<li class="treec">第一章 信贷政策<img src="word/img/poi.png"/></li>
					<li class="treec">第一章 信贷政策<img src="word/img/poi.png"/></li>
					<li class="treec">第一章 信贷政策<img src="word/img/poi.png"/></li>
					-->
						<span class="li2"><img src="word/img/list_o.png" /> 1小时前
							2015-04-01</span>
					</ul>
					<!-- 循环 -->
				</div>
				<div name="我的模板" class="news_div ldn dn2" id="TemplateMyDiv">
					<!-- 循环项 -->
					<ul class="ul">
						<h3>
							<a title="行业研究每日要参4月2日">行业研究每日要参4月2日</a><img
								src="word/img/poi.png" />
						</h3>
						<li class="treec">第一章 本期关注<img src="word/img/poi.png" /></li>
						<li class="tree_h">第二章 信贷环境<img src="word/img/poi.png" /></li>
						<li class="treec">第一章 信贷政策<img src="word/img/poi.png" /></li>
						<li class="treec">第一章 信贷政策<img src="word/img/poi.png" /></li>
						<li class="treec">第一章 信贷政策<img src="word/img/poi.png" /></li>
						<span class="li2"> 1小时前 2015-04-01</span>
					</ul>
					<!-- 循环 -->

				</div>
				<div name="猜你喜欢" class="like_div ldn dn3" id="TemplateLikeDiv">
					<!-- 循环项 -->
					<ul class="ul">
						<h3>
							<a title="行业研究每日要参4月2日">行业研究每日要参4月2日</a><img
								src="word/img/poi.png" />
						</h3>
						<li class="treec">第一章 本期关注<img src="word/img/poi.png" /></li>
						<li class="tree_h">第二章 信贷环境<img src="word/img/poi.png" /></li>
						<li class="treec">第一章 信贷政策<img src="word/img/poi.png" /></li>
						<li class="treec">第一章 信贷政策<img src="word/img/poi.png" /></li>
						<li class="treec">第一章 信贷政策<img src="word/img/poi.png" /></li>
						<span class="li2"><img src="word/img/list_o.png" /> 1小时前
							2015-04-01</span>
					</ul>
					<!-- 循环 -->

				</div>
			</div>
			<div class="n_list_page dnPage">
				<ul name="列表" class="n_list n_list1" id="TemplateAllPage">
					<li class="l1">&lt;上一页</li>
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li class="l_last">下一页&gt;</li>
				</ul>
				<ul name="列表" class="n_list n_list2" id="TemplateMyPage">
					<li class="l1">&lt;上一页</li>
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li class="l_last">下一页&gt;</li>
				</ul>
				<ul name="列表" class="n_list n_list3" id="TemplateLikePage">
					<li class="l1">&lt;上一页</li>
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li class="l_last">下一页&gt;</li>
				</ul>
			</div>
		</div>
		<div name="左边框" class="rlist_div2">
			<img class="direct" src="word/img/llist_r.png" />
		</div>
	</div>
	<div name="显示右边栏" class="rlist_show list_show">
		<img src="word/img/llist_1.png" />
	</div>
	<!-----------弹出框--------------------------------------------------------------------------------------------------------------------->
	<div name="遮挡背景" class="ui_back2"></div>
	<div name="遮挡背景" class="ui_back"></div>
	<div class="box_content">
		<!-- 重命名 -->
		<div name="重命名" class="pop_div2 sbox_save">
			<span class="pop_closeBt" ></span>
			<h3>重命名</h3>
			<div class="sb_middle">
				<div>
					请输入文档的名称： <input id="replaceName" type="text" value="新建文档001" /> <br>
					<span class="tip">文档重名，请重新输入名字保存！</span>
				</div>
			</div>
			<div class="sb_button">
				<a class="a1" onclick="replaceNameClick()">确认</a><a class="a2">关闭</a>
			</div>
		</div>
		<!-- 重命名-新建文档 -->
		<div name="新建文档" class="pop_div2 sbox_save_new">
			<span class="pop_closeBt" ></span>
			<h3>重命名-新建文档</h3>
			<div class="sb_middle">
				<div>
					请输入文档的名称： <input id="replaceNewName" type="text" value="新建文档001" />
					<br> <span class="tip">文档重名，请重新输入名字保存！</span>
				</div>
			</div>
			<div class="sb_button">
				<a class="a1" onclick="replaceNewNameClick()">确认</a>
				<a class="a2" onclick="closeSeft()">关闭</a>
			</div>
		</div>
		<!-- 删除 -->
		<div name="删除" class="pop_div2 sbox_delect">
			<span class="pop_closeBt" ></span>
			<h3>提示</h3>
			<div class="sb_text">确认删除?</div>
			<div class="sb_button">
				<a class="a1" onclick="artDelect()">确认</a><a class="a2">关闭</a>
			</div>
		</div>
		<!-- 保存至文档 -->
		<div name="保存至" class="pop_div2 sbox_save_to_wd">
			<span class="pop_closeBt" ></span>
			<h3>将此文档保存至我的文档</h3>
			<div class="sb_middle">
				<div>
					文件名： <input id="saveToWd" class="input" type="text" value="" /> <br>
				</div>
			</div>
			<div class="sb_button">
				<a class="a1" onclick="sure_save_to(1)">确认</a><a class="a2">关闭</a>
			</div>
		</div>
		<!-- 保存至模板 -->
		<div name="保存至" class="pop_div2 sbox_save_to_mb">
			<span class="pop_closeBt" ></span>
			<h3>将此模板保存至我的模板</h3>
			<div class="sb_middle">
				<div>
					文件名： <input id="saveToMb" class="input" type="text" value="" /> <br>
				</div>
			</div>
			<div class="sb_button">
				<a class="a1" onclick="sure_save_to(2)">确认</a><a class="a2">关闭</a>
			</div>
		</div>
		<!-- 另存为 -->
		<div name="另存为" class="pop_div2 sbox_save_as">
			<span class="pop_closeBt" ></span>
			<h3>另存为</h3>
			<div class="sb_middle">
				<div>
					请选择保存位置： <input type="radio" class="radio" name="documentType"
						value="1" /> 我的模板 <input type="radio" class="radio"
						name="documentType" value="2" checked="checked" /> 我的文档<br>
					输入模板/文档的名称： <input id="saveAsText" class="input" type="text"
						value="新建文档001" /> <br>
				</div>
			</div>
			<div class="sb_button">
				<a class="a1" onclick="saveAsClick(articleId)">确认</a><a class="a2">关闭</a>
			</div>
		</div>

		<!-- 我的模板保存方式 -->
		<div name="我的模板" class="pop_div2 sbox_my_plug">
			<span class="pop_closeBt" ></span>
			<h3>提示</h3>
			<div class="sb_middle">
				<div>
					您对此模板的修改将按以下的方式保存，请选择：<br> <input id="templateType1"
						type="radio" class="radio" name="templateType" value="1"
						checked="checked" /> 以此模板新建文档<br> <input id="templateType2"
						type="radio" class="radio" name="templateType" value="2" /> 编辑原模板<br>
					<input id="templateArticleId" type="hidden" value="" />
				</div>
			</div>
			<div class="sb_button">
				<a class="a1" onclick="saveAsTemplate()">确认</a><a class="a2">关闭</a>
			</div>
		</div>
		<!-- 提交审核 -->
		<div name="提交审核" class="pop_div2 submit_audit">
			<span class="pop_closeBt" ></span>
			<h3>提交审核</h3>
			<div class="sb_middle">
				<input type="radio" class="radio" id="TypeRadio1" name="Tradio"
					checked="checked" /> <label for="TypeRadio1">期刊文档</label> <input
					type="radio" class="radio" id="TypeRadio2" name="Tradio" /> <label
					for="TypeRadio2">非期刊文档</label> <br>
				 <div class="tabDivBg">					
						<input id="articleJournalId_1" type="hidden" value="0" />
						<input id="articleJournalTypeId_1" type="hidden" value="0" />
						<h2 class="qikan_h2" style="margin-top:0px;"><span class="span1">期刊名称</span>： <input id="qikanName" type="text"
							style="width:244px;" /><a class="creatNew" style="display:none">新建</a></h2>
							<span class="span1" style="margin:8px 0;">文档标题</span>： <input style="margin:8px 0;" id="articleName_1" type="text"
							value="" />
						<h2 class="qikan_h2" style="margin-bottom:0px;"><span class="span1">文档期次</span>： <input
							id="articleJournalTime_1" type="text" class="time" placeholder=""
						onblur="this.placeholder=''" /> 
						<p id="articleJournalNameP"></p></h2>
						<input id="articleJournalName_1" type="hidden" value="2015-11-03日刊"  />
						<span class="span1">文档简介</span>：
						<textarea id="brief_1"></textarea>
						<br> <span class="signD"><span class="span1">文档标签：</span>
							<h4 id="articleLabelH4"></h4> </span>
						<div style="padding-left:78px;margin:0;">
							<textarea id="articleLabelText_1"></textarea>
						</div>
						<h5 style="padding-left:78px;margin-bottom:8px;">
							（多个标签用空格分隔，最多15个标签，每个标签最多为40个字）</h5>
						<span class="span1">文档关键词</span>： <input maxlength="45"
							id="keyword_1" type="text" />
						<h5 style="padding-left:78px;">（多个关键词用空格分隔，最多15个关键词，每个关键词最多为8个字）</h5>
						<span class="span1">文档价格</span>：<input id="article_price" type="text"
							style="width:140px;margin:0 0 0 5px;" value="0" price="0" journalPrice="0"/><p>&nbsp;创享币</p>				
				</div>
			</div>
			<div class="sb_button">
				<a class="a4" onclick="checkSubmitClick(articleId)">提交</a>
				<a class="a3">取消</a>
			</div>
		</div>
		<!-- 新建期刊 -->
		<div name="新建期刊" class="pop_div2 creat_pop">
			<span class="pop_closeBt" ></span>
			<h3>新建期刊</h3>
			<div class="sb_middle">
				<div>
				<form id="form">
				<input type="hidden" name="id" id="update_journal_id" />
				<input type="hidden" name="cover" id="cover" value="">
				<input type="hidden" name="type" id="typeName" value="日刊">
					<span class="span1">期刊名称</span>： <input id="qikanName1" name="name" type="text"
						value="" /><br> <span class="span1">期刊简介</span>：
					<textarea id="qikanBrief" name="skip" ></textarea>
					<br> <span class="span1">出刊频率</span>： <select id="typeId" name="typeId" onchange="$('#typeName').val($(this).find('option:selected').text())">
						<option value="1">日刊</option>
						<option value="2">周刊</option>
						<option value="3">半月刊</option>
						<option value="4">月刊</option>
						<option value="5">双月刊</option>
						<option value="6">季刊</option>
						<option value="7">半年刊</option>
						<option value="8">年刊</option>
					</select> <br> <span class="span1">期刊标签</span>：
					<h4>
						<em>投资银行业务</em><em>中小企业</em><em>投资银行业务</em><em>中小企业</em>
					</h4>
					<div style="padding-left:85px;margin:0;">
						<textarea id="brief_3" name="label"></textarea>
					</div>
					<h5 style="padding-left:78px;">（多个标签用空格分隔，最多15个标签，每个标签最多为40个字）</h5>
					<span class="span1">期刊关键词</span>： <input id="keyword_3" name="keyword" type="text" />
					<h5 style="padding-left:78px;">（多个关键词用空格分隔，最多15个关键词，每个关键词最多为8个字）</h5>
					</form>
					<form id="uploadForm1" enctype="multipart/form-data" method="post" target="uploadIframe">
						<span style="margin-left:-11px" class="span1">封</span>面： <input
							type="file" name="upimg" id="upimg"> <input
							type="button" value="上传" onclick="uploadImg()"
							class="upform">
					<h5>封面图片的格式为:".jpg"、".jpeg"、".bmp"、".gif"、".png"；</h5>
					<h5>大小限制在1M以内！图片推荐尺寸827X1170，分辨率72dpi!</h5>
					<span class="span1">期刊价格</span>：<input id="journal_price" type="text"
							style="width:140px;margin-left:6px" /><p style="display:inline-block;">&nbsp;创享币/年</p>
					</form>
				</div>
			</div>
			<div class="sb_button">
				<a class="a4" onclick="journalAdd()">提交</a><a class="a3">取消</a>
			</div>
		</div>
		<!-- 设置插件属性 -->
		<div name="插件属性" class="pop_div2 plugin_properties">
			<span class="pop_closeBt"  id="pluginClose"></span>
			<h3>插件属性</h3>
			<div class="sb_middle">
				<div>
				    <span style="display:none">开始时间： <input id="pluginStartDate" class="time" onclick="laydate({elem:'#pluginStartDate'});this.placeholder=''"
					onblur="this.placeholder='开始时间'" placeholder="开始时间" type="text" value="" /><br></span>
				    <span style="display:none">结束时间： <input id="pluginEndDate" class="time" onclick="laydate({elem:'#pluginEndDate'});this.placeholder=''"
					onblur="this.placeholder='结束时间'" placeholder="结束时间" type="text" value="" /><br></span> 
					<span style="display:none"><span class="span1">区</span>域： <input type="text" id="pluginProvince" class="are_a select" /><br></span> 
					<span style="display:none"><span class="span1">行</span>业： <input type="text" id="pluginIndustry" class="hang_ye select" /><br></span>
					<span style="display:none">发行对象：
						<select id="pluginPublish" style="border: 1px solid #b5b8c8;cursor: pointer; height: 24px; line-height: 24px;  width: 290px;background: url('word/img/time_down.png') no-repeat right">
							<option value="1">个人</option>
							<option value="2">机构</option>
							<option value="3">个人和机构</option>
							<option value="4">全部</option>
						</select><br>					
					</span> 
					<span style="display:none">银行类型： 
						<select id="pluginBankType" style="border: 1px solid #b5b8c8;cursor: pointer; height: 24px; line-height: 24px;  width: 290px;background: url('word/img/time_down.png') no-repeat right">
							<option value="0">商业银行</option>
							<option value="1">国有银行</option>
							<option value="2">股份制商业银行</option>
							<option value="3">城商行</option>
							<option value="4">农商行</option>
							<option value="5">外资行</option>
						</select><br>
					</span>
					<span style="display:none">物业类型： 
						<select id="pluginProperty" style="border: 1px solid #b5b8c8;cursor: pointer; height: 24px; line-height: 24px;  width: 290px;background: url('word/img/time_down.png') no-repeat right">
							<option value="1">住宅</option>
							<option value="2">办公楼</option>
							<option value="3">商业营业用房</option>
						</select>
					</span>		
					<span style="display:none">走势图　： 
						<select id="datafield" style="border: 1px solid #b5b8c8;cursor: pointer; height: 24px; line-height: 24px;  width: 290px;background: url('word/img/time_down.png') no-repeat right">
							<option value="1">国际原油期货价格走势图</option>
							<option value="2">国际部分地区原油现货价格走势图</option>
							<option value="3">国际成品油期货价格走势</option>
							<option value="4">新加坡成品油现货价格走势</option>
							<option value="5">国内成品油出厂价格</option>
							<option value="6">上海燃料油1311期货合约结算价格走势</option>
							<option value="7">澳大利亚BJ动力煤价格走势</option>
							<option value="8">国内焦煤市场价格走势</option>
							<option value="9">中转地秦皇岛港动力煤价格走势</option>
							<option value="10">渤海商品交易所焦炭现货结算价格走势</option>
							<option value="11">国际钢铁综合价格指数走势</option>
							<option value="12">国际主要钢材品种价格指数走势</option>
							<option value="13">国内铁精粉价格走势</option>
							<option value="14">国内钢铁期货价格走势</option>
							<option value="15">国内螺纹钢—HRB400 20mm价格走势</option>
							<option value="16">国内热轧—3.0热轧板价格走势</option>
							<option value="17">国内50*5角钢价格走势</option>
							<option value="18">国际有色金属期货价格走势</option>
							<option value="19">上海有色金属期货价格走势</option>
							<option value="20">本期国内塑料指数变化图</option>
							<option value="21">波罗的海运费指数走势图</option>
							<option value="22">中国沿海散货运价综合指数(CCBFI：综合指数)</option>
							<option value="23">中国沿海散货运价指数(CCBFI：金属矿石)</option>
							<option value="24">中国沿海散货运价指数(CCBFI：煤炭)</option>
							<option value="25">铁矿石海运费走势图</option>
							<option value="26">大豆产品现货价格走势图</option>
							<option value="27">棕榈油现货价格走势图</option>
							<option value="28">大豆产品期货价格走势图</option>
							<option value="29">棕榈油期货价格走势图</option>
							<option value="30">CME木材期货收盘价格走势图</option>		
						</select>
					</span>				
					<input type="hidden" id="pluginProvinceId" class="are_aid" value="2"> 
					<input type="hidden" id="pluginIndustryId" class="hang_yeid" value="96">
				</div>
			</div>
			<div class="sb_button">
				<a class="a6"  onclick="getPluginContent(this,'do')">执行</a>
				<a class="a7" onclick="getPluginContent(this,'insert')" id="pluginInsertDiv">插入</a>
				<a class="a2" id="plugin_properties">关闭</a>
			</div>
		</div>
		<!-- 设置全局属性 -->
		<div name="设置全局属性" class="pop_div2 global_properties">
			<span class="pop_closeBt" ></span>
			<h3>设置全局属性</h3>
			<div class="sb_middle">
				<div>
					结束时间： <input type="text" class="time" id="endDate"
						onclick="laydate({elem:'#endDate'})" /> <br> <span>区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;域：</span>
					<input type="text" id="globalProvince" class="are_a select" /> <input
						type="hidden" value="" class="are_aid" id="globalProvinceId" /> <br>
					<span class="span1">行&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</span> <input
						type="text" id="globalIndustry" class="hang_ye select" /> <input
						type="hidden" value="" class="hang_yeid" id="globalIndustryId" />
				</div>
			</div>
			<div class="sb_button">
				<a class="a1"onclick="globalSettingClick(articleId)">确认</a><a class="a2">关闭</a>
			</div>
		</div>
		<!-- 设置大纲属性 -->
		<div name="设置大纲属性" class="pop_div2 set_outline_attribute ">
			<span class="pop_closeBt" ></span>
			<h3>设置大纲属性</h3>
			<div class="sb_middle">
				<div>
					<span>时&nbsp;&nbsp;&nbsp;&nbsp;间：</span> <input id="time1"
						type="text" placeholder="开始时间"
						onclick="laydate({elem:'#time1'});this.placeholder='';"
						onblur="this.placeholder='开始时间'" class="time" /> <input
						id="time2" onclick="laydate({elem:'#time2'});this.placeholder='';"
						onblur="this.placeholder='结束时间'" placeholder="结束时间" type="text" />
					<br> <span>省&nbsp;&nbsp;&nbsp;&nbsp;市：</span> <input
						id="outProvince" type="text" class="are_a select" /> <input
						type="hidden" value="" class="are_aid" id="outProvinceId" /> <br>
					<span class="span1">行&nbsp;&nbsp;&nbsp;&nbsp;业：</span> <input
						id="outIndustry" type="text" class="hang_ye select" /> <input
						type="hidden" value="" class="hang_yeid" id="outIndustryId" />
				</div>
			</div>
			<div class="sb_button">
				<a class="a1"onclick="outlineSettingClick(articleId)">确认</a><a class="a2">关闭</a>
			</div>
		</div>
		<!-- 设置封面属性 -->
		<div name="设置封面属性" class="pop_div2 cover_properties">
			<span class="pop_closeBt" ></span>
			<h3>设置封面属性</h3>
			<div class="sb_middle">
				<div>
					<span class="span1">区</span>域： <input type="text" id="areaId"
						class="are_a select" /> <a class="are_i select_i"></a> <input
						type="hidden" id="area_id" value="" class="are_aid" /> <br>
					<span class="span1">行</span>业： <input type="text" id="industryId"
						class="hang_ye select" /> <a class="hang_ye_i select_i"></a> <input
						type="hidden" id="industry_id" value="" class="hang_yeid" /> <br>
					<span class="span1">标</span>题： <input type="text" id="title"
						maxlength="30" /> <br> <span class="span2"
						style="letter-space:2px;">子标题</span>： <input type="text"
						id="subtitle" /> <br>
					<form id="uploadForm" action="homePage/upLoadPic.do"
						enctype="multipart/form-data" method="post" target="uploadIframe">
						<span class="span1" style="padding-right:1px">封</span>面： <input
							type="file" id="filename" name="fileform"> <input
							class="upform" type="button" onclick="uploadPic(articleId)"
							value="上传">
					</form>
					<p>封面图片的格式为:".jpg"、".jpeg"、".bmp"、".gif"、".png"；</p>
					<p>大小限制在1M以内！图片推荐尺寸827X1170，分辨率72dpi!</p>
				</div>
			</div>
			<div class="sb_button">
				<a class="a5" onclick="delHomepageClick()">删除</a>
				<a class="a1" onclick="saveHomepageClick(articleId)">确认</a>
				<a class="a2">关闭</a>
			</div>
		</div>
		<!-- 预览 -->
		<div name="预览" class="pop_div2 pre_view">
			<span class="pop_closeBt" ></span>
			<h3><span>预览</span></h3>
			<div class="sb_middle">
				<div>
					江苏某第三方收单机构曾用同一名称注册“扬州金逸影城”商户1300余户，每户交易均不超过10笔。其注册时用一套图片，通过PS修改在不同违规商户中重复使用。根据人民银行收单检查结果，个别收单机构抽检商户虚假比例高达70%。某些第三方收单机构为了迅速扩大市场份额，通过网络出售POS，其中商户准入等一系列收单风险把控非常薄弱，导致风险事件频繁发生。
				</div>
			</div>
		</div>
		<!-- 保存为我的新闻 -->
		<div name="保存为我的新闻" class="pop_div2 save_mynews">
			<span class="pop_closeBt" ></span>
			<h3>保存为我的新闻</h3>
			<div class="sb_middle">
				<div>
					新闻名称： <input type="text" /> <br> <span class="tip">新闻重名，请重新输入名字保存！</span>
				</div>
			</div>
			<div class="sb_button">
				<a class="a1">确认</a><a class="a2">关闭</a>
			</div>
		</div>
		<!-- 保存为我的文档 -->
		<div name="保存为我的文档" class="pop_div2 save_mydocument">
			<span class="pop_closeBt" ></span>
			<h3>保存为我的文档</h3>
			<div class="sb_middle">
				<div>
					文档名称： <input type="text" /> <br> <span class="tip">文档重名，请重新输入名字保存！</span>
				</div>
			</div>
			<div class="sb_button">
				<a class="a1">确认</a><a class="a2">关闭</a>
			</div>
		</div>
		<!-- 保存为我的插件 -->
		<div name="保存为我的插件" class="pop_div2 save_myplug">
			<span class="pop_closeBt" ></span>
			<h3>保存为我的插件</h3>
			<div class="sb_middle">
				<div>
					插件名称： <input type="text" /> <br> <span class="tip">插件重名，请重新输入名字保存！</span>
				</div>
			</div>
			<div class="sb_button">
				<a class="a1">确认</a><a class="a2">关闭</a>
			</div>
		</div>
		<!-- 保存成功 -->
		<div name="保存成功" class="pop_div2 save_success">	
			<span class="pop_closeBt" ></span>		
			<h3>提示</h3>
			<p>保存成功！</p>			
		</div>
		<!-- 日期不能为空 -->
		<div name="日期不能为空" class="pop_div2 time_null">
			<span class="pop_closeBt" ></span>
			<h3>提示</h3>
			<span>日期不能为空！</span>			
		</div>
		<!-- 上传文件 -->
		<div name="上传文件" class="pop_div2 upload_fire">
			<span class="pop_closeBt" ></span>
			<h3>上传文件</h3>
			<form id="importForm" action="homePage/importFile.do"
				enctype="multipart/form-data" method="post" target="uploadIframe">				
				<div class="sb_middle">
					<div>
						<span class="span1">上</span>传： <input class="input"
							name="uploadfile" id="uploadfile" type="file" /> <a
							onclick="importFile(articleId)">上传</a><br> <span class="tip">上传word文档的格式为：“doc”；</span><br>
						批量上传： <input class="input" name="batchuploadfile"
							id="batchuploadfile" type="file" /> <a
							onclick="batchimportFile(articleId)">上传</a><br> <span
							class="tip">上传word文档的格式为：“zip”；</span><br>
						<!-- 请选择类型：<input type="radio" name="radio3" checked="checked"/>文档
							 <input class="rinput" type="radio" name="radio3"/>模板 -->
					</div>
				</div>
				<div class="sb_button">
					<a class="a1" style="display:none">确认</a><a class="a2">关闭</a>
				</div>
			</form>
		</div>
		<!-- 选择城市 -->
		<div name="选择城市" class="pop_div2 city_options">
			<span class="pop_closeBt" ></span>
			<h3>选择城市</h3>
			<div class="sb_middle">
				<div>
					<!-- <span><input type="radio" name="radio4" checked="checked"/>北京市</span> -->

				</div>
			</div>
			<div class="sb_button">
				<a class="a1">确认</a><a class="a2">关闭</a>
			</div>
		</div>
		<!-- 选择行业 -->
		<div name="选择行业" class="pop_div2 industry_options">
			<span class="pop_closeBt" ></span>
			<h3>选择行业</h3>
			<div class="sb_middle">
				<div>
					<!-- <dl> -->
					<!-- 一级 -->
					<!-- <h5><span><i></i><input type="checkbox" checked="checked"/><img src="word/img/choose2.png" />一级农业</span></h5> -->
					<!-- 二级 -->
					<!-- <dt><span><i></i><input type="checkbox"/><img src="word/img/choose2.png" />小麦、玉米、大豆</span></dt> -->
					<!-- 三级 -->
					<!-- <dd><span><input type="checkbox"/><img src="word/img/choose2.png" />农小麦</span></dd>
						<dd><span><input type="checkbox"/><img src="word/img/choose2.png" />农小麦</span></dd>
						<dt><span><i></i><input type="checkbox"/><img src="word/img/choose2.png" />小麦、玉米、大豆</span></dt>
						<dd><span><input type="checkbox"/><img src="word/img/choose2.png" />农小麦</span></dd> -->
					<!-- </dl> -->
				</div>
			</div>
			<div class="sb_button">
				<a class="a1">确认</a><a class="a2">关闭</a>
			</div>
		</div>

		<!-- 高级搜索 -->
		<div name="高级搜索" class="pop_div2 advanced_search">
			<span class="pop_closeBt" id="advanced_search_close"></span>
			<h3>高级搜索</h3>
			<div class="sb_middle">
				<div>
					包含以下的<span class="black">全部</span>关键词：&nbsp;&nbsp; <input
						class="allkeywords" type="text" id="ascondition_all" /> <br>
					包含以下<span class="black">任意</span>一个关键词： <input type="text"
						class="onlykeywords" id="ascondition_any" /> <br> <span
						class="black">不包含</span>以下关键词：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="notkeywords" type="text" id="ascondition_nocontains" />
					<br> <span class="tip">关键词全部、任意、不包含中不能全部为空</span> 查询关键词位于： <input
						class="radio radio1" type="radio" name="radio6"
						id="ascondition_part_2" value="2" /> 新闻标题&nbsp;&nbsp; <input
						class="radio rinput" type="radio" checked="checked" name="radio6"
						id="ascondition_part_1" value="1" /> 新闻全文<br> 起止时间： <input
						type="text" id="time_start" class="time time1" placeholder=""
						onclick="laydate({elem:'#time_start',max:laydate.now()});this.placeholder=''"
						onblur="this.placeholder=''" class="time" /> <input type="text"
						id="time_end" class="time" placeholder=""
						onclick="laydate({elem:'#time_end',max:laydate.now()});this.placeholder=''"
						onblur="this.placeholder=''" /> <br> 搜索结果排序方式： <select
						id="ascondition_sort">
						<option value="2">按焦点排序</option>
						<option value="1">按时间排序</option>
					</select> <br> 信息库：<input type="checkbox" class="fengxian_check"
						id="strTypeCheckbox" name="strTypeCheckbox">风险信息库 <br>
					新闻源： <input id="ascondition_fromSource" class="news_sourse1"
						type="text" /> <br> <input type="hidden"
						class="news_sour_id" id="ascondition_fromId"> 历史记录：<br>
					<ul id="historyUl">
						<!-- <li>（互联网 金融 经济 P2P）- （it）-（信贷）</li>
						<li>（江苏 南京 无锡 下周）-（煤炭）-（煤气）</li>
						<li>（第三方支付 手机支付 支付宝）-（P2P）-（宜信）</li>
						<li>（第三方支付 手机支付 支付宝）-（P2P）-（宜信）</li>
						<li>（第三方支付 手机支付 支付宝）-（P2P）-（宜信）</li> -->
					</ul>
				</div>
			</div>
			<div class="sb_button">
				<a class="a8" title="重置" onclick="advanceReset()">重置</a>
				<a class="a9" title="搜索" onclick="advanceClick()">搜索</a>
				<a class="a3" title="取消">取消</a>
			</div>
		</div>
		<!-- 内容插件 -->
		<div name="内容插件" class="pop_div2 contents_plugin">
			<span class="pop_closeBt" id="contents_plugin_close"></span>
			<h3>内容插件</h3>
			<div class="sb_middle">
				<div>
					<h4>
						<img src="word/img/point.png" />设置内容插件（关键词以空格分隔）
					</h4>
					插件名称 <input type="text" style="display:none" /> <input
						id="contentpluginName" class="pluginName"
						onfocus="this.placeholder=''" onblur="this.placeholder='请输入插件名称'"
						placeholder="请输入插件名称" type="text" value="" /> <br> 包含以下的<span
						class="black">全部</span>关键词：&nbsp;&nbsp;<input type="text"
						style="display:none" /> <input id="contentpluginMust"
						class="allkeywords must" onfocus="this.placeholder=''"
						onblur="this.placeholder='例如：煤炭 经济'" placeholder="例如：煤炭 经济"
						type="text" value="" /> <br> 包含以下<span class="black">任意</span>一个关键词：<input
						type="text" style="display:none" /> <input
						id="contentpluginArbitrarily" type="text"
						onfocus="this.placeholder=''"
						onblur="this.placeholder='例如：煤气 天然气'" placeholder="例如：煤气 天然气"
						class="onlykeywords arbitrarily" value="" /> <br> <span
						class="black">不包含</span>以下关键词： <input id="contentpluginNot"
						class="notkeywords not" onfocus="this.placeholder=''"
						onblur="this.placeholder='例如：煤气 天然气'" placeholder="例如：煤气 天然气"
						type="text" id="ascondition_nocontains" value="" /> <br> <span
						class="tip">关键词全部、任意、不包含中不能全部为空</span> 查询关键词位于： <input
						id="contentpluginSearchsource_1" class="radio radio1 searchSource"
						type="radio" name="radio5" checked="checked" value="1" />
					在全文中&nbsp;&nbsp; <input id="contentpluginSearchsource_2"
						class="radio rinput" type="radio" name="radio5" value="2" />
					仅在标题中<br> 起止时间：
					<!-- 
                <input type="text" id="time_start1" value="" class="time time1 startTime" placeholder="" onclick="laydate({elem:'#time_start1'});this.placeholder=''"  onblur="this.placeholder=''" class="time" />
                <input type="text" id="time_end1"  value=""  class="time endTime" placeholder=""onclick="laydate({elem:'#time_end1'});this.placeholder=''" onblur="this.placeholder=''" />
                 -->
					<input type="text" id="time_start1" class="time startTime"
						placeholder=""
						onclick="laydate({elem:'#time_start1'});this.placeholder=''"
						onblur="this.placeholder=''" /> <input type="text" id="time_end1"
						class="time endTime" placeholder=""
						onclick="laydate({elem:'#time_end1'});this.placeholder=''"
						onblur="this.placeholder=''" /> <br> 重复周期： <input
						id="contentpluginRepeattime" type="text" disabled="true" value="1"
						class="round_time repeatTime" /> （单位：天）<br>
					自动执行：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
						id="contentpluginRepeatflag_1" class="radio radio2"
						checked="checked repeatFlag" type="radio" name="radio11" value="1" />
					是&nbsp;&nbsp; <input id="contentpluginRepeatflag_1"
						class="radio rinput repeatFlag" type="radio" name="radio11"
						value="0" /> 否<br> 搜索结果排序方式： <select id="contentpluginSort"
						class="sort">
						<option value="1">按时间排序</option>
						<option value="2" selected="selected">按焦点排序</option>
					</select> <br> 信息库：<input type="checkbox" class="fengxian_check"
						id="strTypeCheckbox1" name="strTypeCheckbox1">风险信息库 <br>
					搜索结果显示条数： <select id="contentpluginPagesize"
						class="ser_result_nub pageSize">
						<option value="1">1</option>
						<option value="5">5</option>
						<option selected="selected" value="10">10</option>
						<option value="15">15</option>
						<option value="20">20</option>
						<option value="25">25</option>
						<option value="30">30</option>
					</select> <br> 新闻源： <input id="contentpluginFromSource"
						class="news_sourse1 fromSource" type="text" /> <input
						type="hidden" class="news_sour_id" id="contentpluginFromId"
						value=""> <input type="hidden" id="contentpluginId"
						value="0"> <input type="hidden" id="contentpluginTrId"
						value="0">
				</div>
			</div>
			<div class="sb_button">					
				<a class="a5" title="删除" onclick="contentPluginDel()">删除</a>
				<a class="a8" title="重置" onclick="contentPluginRest()">重置</a>
				<a class="a1" title="确认" onclick="contentPluginSetUrl()">确认</a>
				<a class="a3" title="取消">取消</a>
			</div>
		</div>
		<!-- 全部 我的新闻源 -->
		<div name="全部 我的新闻源" class="pop_div2 news_sourse">
			<span class="pop_closeBt" ></span>
			<h3>全部 </h3><h3 style="width:45%;">我的新闻源</h3>
			<div class="sb_middle">
				<div class="div1" id="LeSeaBox">
					网站名称： <input class="input i1 w70" type="text" /> <a id="LeSearch"
						onselectstart="return false;">搜索</a><br> <span class="span2">
						<input type="checkbox" /> 全</span>部：<br>
					<!--  -->
					<span class="over_scroll"> </span>
				</div>
				<div class="divm">
					<img class="add" src="word/img/remove-2.png" /><br> <img
						class="remove" src="word/img/remove.png" />
				</div>
				<div class="div2" id="RiSeaBox">
					网站名称： <input class="input i1 w70" type="text" /><a id="RiSearch"
						onselectstart="return false;">搜索</a><br> <span class="span2">
						<input type="checkbox" /> 全</span>部：<br>
					<!--  -->
					<span class="over_scroll"> </span>
				</div>
			</div>
			<div class="sb_button">
				<a name="确认" class="a1">确认</a><a class="a2">关闭</a>
				<!--<input type="hidden" id="getNewsName"/>
            <input type="hidden" id="getNewsID"/>-->
			</div>
		</div>
		<!-- 退出 -->
		<div class="pop_div2 exit_pop">
			<span class="pop_closeBt" onclick="closeExitDiv();"></span>
			<h3>提示</h3>
			<p class='tips'>确认退出?</p>
			<div class="sb_button">
				<a class="a1" onclick="loginout();">确认</a>
				<a class="a2" onclick="closeExitDiv();">关闭</a>
			</div>
		</div>
		<!-- box_content end -->
	</div>
	<!-----------end--------------------------------------------------------------------------------------------------------------------->
	<div id="contentdiv1" style="display:none"></div>
	<input id="contentdiv2" name="" type="text" value="" />
	<!-- 有问题 -->
	<textarea id="contentdiv" name="textarea">${content.nodeContent}</textarea>
	<iframe src="" name="uploadIframe" id="uploadIframe"
		style="display:none"></iframe>
	<input type="hidden" id="articleNameInput"
		value="${article.articleName}" />
	<input type="hidden" id="articleSkipInput"
		value="${article.articleSkip}" />
	<input type="hidden" id="articleKeyWordInput"
		value="${article.articleKeyWord}" />
	<input type="hidden" id="articleLabelInput"
		value="${article.articleLabel}" />
	<script type="text/javascript">
var iframeObj;
var strTime="${strTime}";
var articleId = ${article.articleId};//文章的id
var G_articleId = ${article.articleId};//文章的id
var userId = ${userId};//用户的ID
var G_articleUserId = ${article.userId};//文章的名字
var G_articleName = ZU.$id("articleNameInput").value;//文章的名字
var G_articleType = "${article.articleType}";//类型'template','news','document'
var G_passType = "${article.passType}"//'SAVED','SUBMITTED','FAILED','HANDLED','PASSED'
var G_articleSkip = ZU.$id("articleSkipInput").value;//简介
var G_articleKeyWord = ZU.$id("articleKeyWordInput").value;//关键词
var G_articleLabel = ZU.$id("articleLabelInput").value;//标签
var ue = UE.getEditor('editor');
	ue.ready(function() {
		var contentHtml =  document.getElementById("contentdiv").value;
		//console.info("contentHtml="+contentHtml);
		if(contentHtml==""){
			contentHtml="<h1 id=\""+articleId+"\">标题1</h1><p>输入内容</p>";
		}else{
			ZU.$id("contentdiv").value=ue.getContent();
		}
	    ue.setContent(contentHtml);
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
document.onkeydown=banBackSpace;
function banBackSpace(e){
                var ev = e || window.event;//获取event对象  
                var obj = ev.target || ev.srcElement;//获取事件源  
                var t = obj.type || obj.getAttribute('type');//获取事件源类型  
                //获取作为判断条件的事件类型  
                var vReadOnly = obj.readOnly;  
                var vDisabled = obj.disabled;  
                //处理undefined值情况  
                vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;  
                vDisabled = (vDisabled == undefined) ? true : vDisabled;  
                //当敲Backspace键时，事件源类型为密码或单行、多行文本的，  
                //并且readOnly属性为true或disabled属性为true的，则退格键失效  
                var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);  
                //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效  
                var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";  
                //判断  
                if (flag2 || flag1) return false 
}
function addDate(days){
       var d=new Date();
       d.setDate(d.getDate()+days);
       var m=d.getMonth()+1;
       return d.getFullYear()+'-'+m+'-'+d.getDate();
     } 
 laydate({
    elem: '#articleJournalTime_1',
   max: laydate.now(addDate(365)),
   choose:journalTimeChange
   });

</script>

</body>
</html>
