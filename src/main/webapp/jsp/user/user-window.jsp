<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<div class="pop_div2 save_failure">
		<span class="pop_closeBt" ></span>
		<h3>提示</h3>
		<p class='tips'>保存失败！用户昵称已存在</p>
		<div class="sb_button">
			<a onclick="closeDiv();" class="a1">确认</a>
		</div>
	</div>
	<div class="save_success">	
		<p>保存成功！</p>	
	</div>
	<div class=" pop_div2 no_reason">
		<span class="pop_closeBt" ></span>
		<h3>审核不通过原因</h3>
		<div class="sb_middle">将第二个插件删除！</div>
		<div class="sb_button">
			<a class="a3" onclick="closeDiv();">关闭</a>
		</div>
	</div>
	<div class=" pop_div2 dele_pop">
		<span class="pop_closeBt" ></span>
		<h3>提示</h3>
		<p class='tips'>确认删除选中文件?</p>
		<div class="sb_button">
			<a onclick="del();" class="a1">确认</a>
			<a class="a2" onclick="closeDiv();">取消</a>
		</div>
	</div>
	<div class=" pop_div2 my_plug">
		<span class="pop_closeBt" ></span>
		<h3>提示</h3>
		<div class="sb_middle">
			<div>
				您对此模板的修改将按以下的方式保存，请选择：<br> <br> <input type="radio"
					class="radio" name="documentType" value="1" checked="checked" />
				以此模板新建文档<br> <br> <input type="radio" class="radio"
					name="documentType" value="2" /> 编辑原模板<br>
			</div>
		</div>
		<div class="sb_button">
			<a class="a1">确认</a><a class="a3" onclick="closeDiv();">关闭</a>
		</div>
	</div>
	<div name="预览" class=" pop_div2 pre_view_plugin">
		<span class="pop_closeBt" ></span>
		<h3>行业盈利能力</h3>
		<div class="sb_middle">
			<div>基于销售毛利率、销售利润率、资产报酬率三个指标，分析行业盈利能力。</div>
		</div>
	</div>
	<div name="新建期刊" class=" pop_div2 creat_pop">
		 <span class="pop_closeBt" ></span>
			<h3>新建期刊</h3>
			<div class="sb_middle">
				<div>
				<form id="form">
					<input type="hidden" name="cover" id="cover" />
					<input type="hidden" name="id" id="update_journal_id" />
					<input type="hidden" name="type" id="typeName"  value="日刊"/>
					<span class="span1">期刊名称</span>： <input id="qikanName" type="text"
						name="name"  maxlength="40"/><br> <span class="span1">期刊简介</span>：
					<textarea id="qikanBrief" name="skip" maxlength="1000"onfocus="this.placeholder=''" onblur="this.placeholder='1000字以内'" placeholder="1000字以内"></textarea>
					<br> <span class="span1">出刊频率</span>： <select id="typeId" name="typeId" onchange="$('#typeName').val($(this).find('option:selected').text())">
						<c:forEach items="${typeList}" var="type">
							<option value="${type.id}">${type.name}</option>
						</c:forEach>
					</select> <br> <span class="span1">期刊标签</span>：
					<h4 style="margin-left:1px;">
						<em onclick="setLabel(this)">银行</em><em onclick="setLabel(this)">中小企业</em><em onclick="setLabel(this)">投资银行</em><em onclick="setLabel(this)">金融</em><em onclick="setLabel(this)">财经</em>
					</h4>
					<div style="padding-left:81.5px;margin:0;">
						<textarea id="brief_3" name="label" maxlength="255" onblur="validBrief();" onfocus='$(this).parent().next().css({"color":"#888","margin-left":"0px"}).text("（多个标签用空格分隔，每个标签最多为40个字）")'></textarea>
					</div>
					<h5 style="padding-left:78px;">（多个标签用空格分隔，每个标签最多为40个字）</h5>
					<span class="span1">期刊关键词</span>： <input id="keyword_3" type="text"  name="keyword" maxlength="255"  onblur="validKeyword();" onfocus='$(this).next().css({"color":"#888","margin-left":"0px"}).text("（多个关键词用空格分隔，最多15个关键词，每个关键词最多为8个字）")'/>
					<h5 style="padding-left:68px;">（多个关键词用空格分隔，最多15个关键词，每个关键词最多为8个字）</h5>
					<span class="span1">期刊价格</span>：<input id="journalPrice" type="text" name="price"
							style="width:140px;margin-left:3.5px" /><p style="display:inline-block;color:#000;font-weight:normal;padding:0 0 0 6px;">创享币/年</p>
					</form>
					<form id="uploadForm" enctype="multipart/form-data" method="post" target="uploadIframe">
					<span style="margin-left:-13px" class="span1">封</span>面： <input
						type="file" name="upimg" id="upimg"> <input type="button"
						value="上传" onclick="uploadImg()" class="upform">
						<h5>封面图片的格式为:".jpg"、".jpeg"、".bmp"、".gif"、".png"；</h5>
					<h5>大小限制在1M以内！图片推荐尺寸827X1170，分辨率72dpi!</h5>
					</form>
					
				</div>
			</div>
			<div class="sb_button">
				<a class="a4" onclick="journalAdd();">提交审核</a><a class="a2"
					onclick="closeDiv();">取消</a>
			</div>
	</div>
	<div class=" pop_div2 upload_file" id="upload_file"  name="上传文件">
		<span class="pop_closeBt" ></span>
		<h3>上传文件</h3>
		<div class="sb_middle">
			<form id="uploadWordForm" enctype="multipart/form-data" method="post" target="uploadIframe">
			<div>
				<span class="span1">上&nbsp;&nbsp;&nbsp;&nbsp;传：</span> 
				<input type="file" id="uploadfile" name="uploadfile" class="input">
				<!--<a onclick="importFile();">上传</a><br>-->
				<span class="tip">上传文档的格式为：“doc”，“docx”，“pdf”，“ppt”，“pptx”；</span><br>
				<!--   批量上传：
                    <input type="file" id="batchuploadfile" name="batchuploadfile" class="input">
                    <a onclick="batchimportFile(articleId)">上传</a><br>
                    <span class="tip">上传word文档的格式为：“zip”；</span><br>
                    <!-- 请选择类型：<input type="radio" name="radio3" checked="checked"/>文档
							 <input class="rinput" type="radio" name="radio3"/>模板 -->
			</div>
			</form>
		</div>
		<div class="sb_button">
			<a class="a1" onclick="importFile();">确认</a><a class="a3" onclick="closeDiv();">关闭</a>
		</div>
	</div>
	<div class=" pop_div2  upload_file"  id="upload_file_batch" name="批量上传文件" style="height: 260px;">
		<span class="pop_closeBt" ></span>
		<h3>批量上传文件</h3>
		<div class="sb_middle">
			<form id="uploadBatchWordForm" enctype="multipart/form-data" method="post" target="uploadIframe">
			<div>
				<span class="span1">批&nbsp;量&nbsp;上&nbsp;传：</span> 
                    <input type="file" id="batchuploadfile" name="batchuploadfile" class="input">
                    <span class="tip">上传文件格式为：“zip”；</span><br>
			</div>
			</form>
		</div>
		<div class="sb_button">
			<a class="a1" onclick="importBatchFile();">确认</a><a class="a3" onclick="closeDiv();">关闭</a>
		</div>
	</div>
	<div name="提交审核" class=" pop_div2 submit_audit">
		    <span class="pop_closeBt" ></span>
			<h3>提交审核</h3>
			<form id="upload_submit_form">
			<div class="sb_middle">
				<input type="radio" class="radio" id="TypeRadio1" name="Tradio"
					checked="checked"  value="1"/> <label for="TypeRadio1">期刊文档</label> <input
					type="radio" class="radio" id="TypeRadio2" name="Tradio" value="0" /> <label
					for="TypeRadio2">非期刊文档</label> <br>
				<div class="tabDivBg">					
						<input id="upload_article_id"  name="articleId" type="hidden"/>
						<input name="radioValue"  type="hidden" value="2"/>
						<input id="articleJournalId" name="articleJournalId" type="hidden" />
						<input id="articleJournalName" name="articleJournalName" type="hidden" />
						<input id="upload_type_id" name="typeId" type="hidden" />
						<h2 class="qikan_h2"><span class="span1">期刊名称</span>： <input id="upload_qikan_name"  maxlength="40" type="text" 
							style="width:244px;" /></h2><span
							class="span1">文档标题</span>： <input id="upload_article_name" type="text"
							name="articleName" />  <h2 class="qikan_h2"><span class="span1">文档期次</span>： <input
							id="upload_journal_date" class="time" name="articleJournalTime" type="text" 
						 style="width:85px;" />
						<p></p></h2>
						<span class="span1">文档简介</span>：
						<textarea id="upload_article_skip" name="brief"></textarea>
						<br> <span class="signD"><span class="span1">文档标签：</span>
							<h4>
							<em onclick="setLabel(this)">银行</em>
							<em onclick="setLabel(this)">中小企业</em>
							<em onclick="setLabel(this)">投资银行</em>
							<em onclick="setLabel(this)">金融</em>
							<em onclick="setLabel(this)">财经</em>
							</h4> </span>
						<div style="padding-left:75.5px;margin:0;">
							<textarea id="upload_article_label" name="articleLabel" maxlength="255" onblur="validBrief();" onfocus='$(this).parent().next().css({"color":"#888","margin-left":"0px"}).text("（多个标签用空格分隔，每个标签最多为40个字）")'></textarea>
						</div>
						<h5 style="padding-left:78px;margin-bottom:8px;">
							（多个标签用空格分隔，每个标签最多为40个字）</h5>
						<span class="span1">文档关键词</span>： <input maxlength="255"
							id="upload_article_keyword" name="keyword"  type="text"  onblur="validKeyword();" onfocus='$(this).next().css({"color":"#888","margin-left":"0px"}).text("（多个关键词用空格分隔，最多15个关键词，每个关键词最多为8个字）")'/>
						<h5 style="padding-left:78px;">（多个关键词用空格分隔，最多15个关键词，每个关键词最多为8个字）</h5>
						<h2><span class="span1">文档价格</span>：<input id="articlePrice" name="articlePrice" type="text" 
							style="width:140px;margin-left:3px" /><p>&nbsp;创享币</p></h2>					
				</div>
			</div>
			</form>
			<div class="sb_button">
				<a class="a6" onclick="submitArticle();">提交</a><a class="a3" onclick="closeDiv();">关闭</a>
			</div>
		</div>
		<div name="支付提示" class=" pop_div2 pay_tips">
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
				<a class="a10" onclick="reload();">已完成付款</a>
				<a class="a9" onclick="closeDiv();">支付遇到问题</a>
			</div>
		</div>
</div>