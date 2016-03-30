//显示提示框
function msgShow(msg){
	new altInfo({
		text:msg
	});
}
//下载文档
function downfile(articleid,type){
	
	if(G_articleType=="template"){
		msgShow("模板不支持下载");
	}
	if(userPerm(true)){//不弹框
		//return false;没有保存权限
	}else{
		doSaveArticle(true);//保存文档
	}
	dowmAfter(articleid,type);
	/*
	var url="setArticleContent.do";
	
	ZU.Ajax.request(url,{
		method:"post",
		data:{'articleId' : articleId, 'nodeContent':nodeContent},
		success:function(data){
			
		},
		failure:function(data){
			show_save_success();
			$(".save_success").find("p").html("文件保存失败");
		}
	});
	*/
}

function dowmAfter(articleid,type) {
	var param = {
		articleId:articleId,
		type:type
	};
	url="homePage/downloadArticle.do?"+ZU.jsonToUrl(param);
	if(type=="content"){
		url = "homePage/downloadContent.do?"+ZU.jsonToUrl(param);
	}
	G_lb = new loading_box({title:'word文档生成中...',text:'请等待'});
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		timeout: 900000,
		success:function(data){
			G_lb.close();
			if(data.flag == "false"){
				show_save_success();
				setTimeout(function(){$(".save_success,.box_content,.ui_back").fadeOut(100)},2000);
				$(".save_success").find("p").html(data.info);
				return;
			}
			location.href="homePage/downloadfile.do?fileName="+encodeURIComponent(data.fileName)
							+"&articleId="+articleid+"&type="+type;
		},
		failure:function(data){
			G_lb.close();
			show_save_success();
			setTimeout(function(){$(".save_success,.box_content,.ui_back").fadeOut(100)},2000);
			$(".save_success").find("p").html("下载异常");
		}
	});
}
//另存为
function saveAsClick(articleId) {
	if(userPerm(true)){//不弹框
		//return false;没有保存权限
	}else{
		doSaveArticle(true);//另存为 保存内容
	}
	var articleName = $("#saveAsText").val();
	if(articleName==""||articleName.replace(/\s+/g, '').length == 0){
		msgShow("名字不能为空或者全空格");
		return;
	}else if(articleName.length>40){
		msgShow('名字小于40个字符');
		return;
	}
	var documentType = $("input[name='documentType']:checked").val();
	var param = {
		articleId:articleId,
		articleName:articleName,
		documentType:documentType,
		flag:2
	};
	var url="word/checkArticleName.do?"+ZU.jsonToUrl(param);
	var  newArticleId = 0;
	ZU.Ajax.request(url,{
		async:false,//同步
		method:"GET",
		success:function(data){
			$(".sbox_save_as,.box_content,.ui_back").fadeOut(100);
			if(data.articleId != ""){
				newArticleId=data.articleId;
				$(".sbox_save_as,.box_content,.ui_back").fadeOut(100);
			}else{
				$(".save_success,.box_content,.ui_back").fadeIn(100);
				set_sbox(".save_success");
				$(".save_success").find("p").html(data.info);
			}
		},
		failure:function(data){
			$(".sbox_save_as,.box_content,.ui_back").fadeOut(100);
			$(".save_success,.box_content,.ui_back").fadeIn(100);
			set_sbox(".save_success");
			$(".save_success").find("p").html("另存失败");
		}
	});
	if(newArticleId!=0){
		window.open("/webword/word.do?articleId="+newArticleId);
	}
}

//提交审核
function checkSubmitClick(articleId){
	if(userPerm()){
		return false;
	}
	doSaveArticle();//提交审核 保存内容
	//
	var articleJournalId=0;//期刊的Id
	var articleJournalName="";//刊次名字
	var articleJournalTime="";//期刊刊次
	
	var articleName = "";//文档的名字
	var radioValue = "";//文档的类型 1:模板 2:文档
	var plateId = "";
	var keyword = "";//文档的关键词
	var brief = "";//文档简介
	var articleLabel = "";//文档的标签
	
	articleName = $("#articleName_1").val();
	radioValue = $("input[name='radioValue']:checked").val();
	plateId = $("#plateId").val();
	keyword = $("#keyword_1").val();
	brief = $("#brief_1").val();
	articleLabel = $("#articleLabelText_1").val();
	var priceNode = ZU.$id("article_price");
	var article_price =parseInt(priceNode.value,10);
	
	if(isNaN(article_price)){//不是数字
		$(".save_success,.box_content,.ui_back").fadeIn(100);
		set_sbox(".save_success");
		$(".save_success").find("p").html("填写正确的数字");
		return;
	}else{
		if(article_price==priceNode.value){//是整数
			if(article_price>=0&&article_price<=9999999){	
			}else{
				show_save_success();
				$(".save_success").find("p").html("文档价格:请输入1-7位的整数");
				return;
			}
		}else{
			show_save_success();
			$(".save_success").find("p").html("文档价格:请输入1-7位的整数");
			return;
		}

	}
	if(ZU.$id("TypeRadio2").checked){//非期刊
	}else{//期刊文档
		articleJournalId=ZU.$id("articleJournalId_1").value;
		articleJournalName=ZU.$id("articleJournalName_1").value;
		articleJournalTime=ZU.$id("articleJournalTime_1").value;
		var journalPrice = parseInt(priceNode.getAttribute("journalPrice"),10);
		if(journalPrice==0&&article_price>0){
			priceNode.value=0;
			show_save_success();
			$(".save_success").find("p").html("期刊价格为0,文档价格也应为0");
			return;
		}
		if(article_price>journalPrice){//
			var priceNodeValue = priceNode.getAttribute("price");
			priceNode.value=priceNodeValue;
			show_save_success();
			$(".save_success").find("p").html("文档价格不能大于期刊价格");
			return;
		}

		if(articleJournalId<=0){//请选择期刊
			show_save_success();
			$(".save_success").find("p").html("请选择期刊");
			return;
		}
		if(articleJournalTime==""||articleJournalName==""){
			show_save_success();
			$(".save_success").find("p").html("请选择期刊时间");
			return;
		}
	}

	if(articleName==""){
		show_save_success();
		$(".save_success").find("p").html("请输入文件名");
		return;
	}
	if(plateId==""){
		show_save_success();
		$(".save_success").find("p").html("请选择分类");
		return;
	}
	if(keyword==""){
		show_save_success();
		$(".save_success").find("p").html("请输入关键字");
		return;
	}else{
		var info=checklable(keyword,8,15,"关键词");
		if(info.flag=="false"){
			show_save_success();
			$(".save_success").find("p").html(info.text);
			return;
		}
	}
	if(brief==""){
		show_save_success();
		$(".save_success").find("p").html("请输入简介");
		return;
	}
	if(articleLabel==""){
		show_save_success();
		$(".save_success").find("p").html("请输标签");
		return;
	}else{
		var info = checklable(articleLabel,40,15,"标签");
		if(info.flag=="false"){
			show_save_success();
			$(".save_success").find("p").html(info.text);
			return;
		}
	}


	G_lb = new loading_box({title:'提交审核中...',text:'请等待'});
	var param = {
		articleId:articleId,
		articleName:$.trim(articleName),
		radioValue:radioValue,
		plateId:plateId,
		keyword:$.trim(keyword),
		brief:$.trim(brief),
		articleLabel:$.trim(articleLabel),
		articleJournalId:articleJournalId,
		articleJournalName:articleJournalName,
		articleJournalTime:articleJournalTime,
		articlePrice:article_price
	};
	var url="word/editSubmitApprove.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		timeout: 900000,
		success:function(data){
			G_lb.close();
			show_save_success();	
			if(data.state == "success"){
				$(".submit_audit,.box_content,.ui_back").fadeOut(100);
				$(".save_success").find("p").html("提交审核成功");
				setTimeout(function(){
				//console.info("关闭窗口");
				//open(location, '_self').close();
				//window.close();
				//window.opener=null; 
				//window.close();
				window.location.href=window.location.href;
				},2000);
			}else{
				$(".save_success").find("p").html(data.info);
			}
		},
		failure:function(data){
			G_lb.close();
			show_save_success();
			$(".save_success").find("p").html("提交审核失败");
		}
	});
}

//设置全局属性
function globalSettingClick(articleId) {
	if(userPerm()){
		return false;
	}
	var endDate = $("#endDate").val();
	var province = $("#globalProvinceId").val();
	var industry = $("#globalIndustryId").val();
	if(endDate==""||province==""||industry==""){
		msgShow("参数不能为空");
		return false;
	}
	var param = {
		articleId:articleId,
		endDate:endDate,
		province:province,
		industry:industry
	};
	var url="word/saveGlobalSetting.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		timeout: 9000,
		success:function(data){
			$(".global_properties,.box_content,.ui_back").fadeOut(100);
			
			show_save_success();
			$(".save_success").find("p").html(data.info);
		},
		failure:function(data){
			$(".global_properties,.box_content,.ui_back").fadeOut(100);
			show_save_success();
			$(".save_success").find("p").html("设置失败");
		}
	});
}

//设置大纲属性
function outlineSettingClick(articleId) {
	if(userPerm()){
		return false;
	}
	var startDate = $("#time1").val();
	var endDate = $("#time2").val();
	var province = $("#outProvinceId").val();
	var industry = $("#outIndustryId").val();
	
	if(startDate==""||endDate==""||province==""||industry==""){
		msgShow("参数不能为空");
		return false;
	}
	var param = {
		articleId:articleId,
		startDate:startDate,
		endDate:endDate,
		province:province,
		industry:industry
	};
	var url="word/saveOutlineSetting.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		timeout: 9000,
		success:function(data){
			$(".set_outline_attribute,.box_content,.ui_back").fadeOut(100);
			show_save_success();
			$(".save_success").find("p").html(data.info);
		},
		failure:function(data){
			$(".global_properties,.box_content,.ui_back").fadeOut(100);
			show_save_success();
			$(".save_success").find("p").html("设置失败");
		}
	});
}

//上传图片
function uploadPic(articleId){
	var uploadBody = document.getElementById("uploadIframe").contentWindow.document.body;
	uploadBody.innerHTML="";
	var form = document.getElementById("uploadForm");
	var filename = $("#filename").val();
	if(filename == ""){
		$(".save_success,.box_content,.ui_back").fadeIn(100);
		$(".save_success").find("p").html("请选择图片");
		return;
	}else{
		filename=filename.toLowerCase();
		if((filename.indexOf(".jpg") < 0)&&(filename.indexOf(".jpeg")<0)&&(filename.indexOf(".bmp")<0)&&(filename.indexOf(".gif")<0)&&(filename.indexOf(".png")<0)){$(".save_success,.box_content,.ui_back").fadeIn(100);
			$(".save_success,.box_content,.ui_back").fadeIn(100);
			$(".save_success").find("p").html("请选择图片文件");
			return;
		}
	}
	form.action="homePage/upLoadPic.do?articleId="+articleId;
	form.submit();
	var lb = new loading_box({title:'上传图片中',text:'请等待'});
	var uploadId = setInterval(function(){
		var uploadBody1 = document.getElementById("uploadIframe").contentWindow.document.body;
		//console.info("--"+uploadBody1.innerHTML);
		  if(uploadBody1.innerHTML!=""){
			  lb.close();
			  clearInterval(uploadId);
			  msgShow("上传图片成功");
			  return false;
		  }
		  //console.info("上传等待"+uploadBody1.innerHTML+"-");
	},1000);
}
//导入
function importFile(articleId){
	var uploadBody = document.getElementById("uploadIframe").contentWindow.document.body;
	uploadBody.innerHTML="";
	var form = document.getElementById("importForm");
	form.action="homePage/importFile.do?articleId="+articleId;
	
	var filename = $("#uploadfile").val();
	if(filename == ""){
		$(".save_success,.box_content,.ui_back").fadeIn(100);
		$(".save_success").find("p").html("请选择文件");
		return;
	}else{
		if(filename.toLowerCase().indexOf(".doc") < 0){
			$(".save_success,.box_content,.ui_back").fadeIn(100);
			$(".save_success").find("p").html("请选择word文档");
			return;
		}
	}
	form.submit();
	var lb = new loading_box({title:'文件导入中',text:'请等待'});
	var uploadId = setInterval(function(){
		var uploadBody1 = document.getElementById("uploadIframe").contentWindow.document.body;
		console.info("文件导入中");
		  if(uploadBody1.innerHTML!=""){
			  lb.close();
			  clearInterval(uploadId);
			  msgShow("导入成功");
			  //$(".upload_fire,.box_content,.ui_back").fadeOut(100);
			  return false;
		  }
	},3000);
}
function batchimportFile(articleId){
	var uploadBody = document.getElementById("uploadIframe").contentWindow.document.body;
	uploadBody.innerHTML="";
	var form = document.getElementById("importForm");
	form.action="homePage/batchImportFile.do?articleId="+articleId;
	
	var filename = $("#batchuploadfile").val();
	if(filename == ""){
		$(".save_success,.box_content,.ui_back").fadeIn(100);
		$(".save_success").find("p").html("请选择zip文件");
		return;
	}else{
		if(filename.toLowerCase().indexOf("zip") < 0){
			$(".save_success,.box_content,.ui_back").fadeIn(100);
			$(".save_success").find("p").html("请选择后缀zip文件");
			return;
		}
	}
	form.submit();
	var lb = new loading_box({title:'文件导入中',text:'请等待'});
	var uploadId = setInterval(function(){
		var uploadBody1 = document.getElementById("uploadIframe").contentWindow.document.body;
		  if(uploadBody1.innerHTML!=""){
			  lb.close();
			  clearInterval(uploadId);
			  msgShow("导入成功");
			  //$(".upload_fire,.box_content,.ui_back").fadeOut(100);
			  return false;
		  }
	},3000);
}
//设置封面属性
function saveHomepageClick(articleId){
	if(userPerm()){
		return false;
	}
	var industryId = $("#industry_id").val();
	var industryname = $("#industryId").val();
	//var industryname =  $("#industryId").find("option:selected").text();
	var areaId = $("#area_id").val();
	var areaname = $("#areaId").val();
	//var areaname = $("#areaId").find("option:selected").text();
	var title = $("#title").val();
	var subtitle = $("#subtitle").val();
	/*var param = {
		articleId:articleId,
		industryId:industryId,
		industryname:industryname,
		areaId:areaId,
		areaname:areaname,
		title:title,
		subtitle:subtitle
	};*/
	var hpBean = new Object();
	hpBean['articleId'] = articleId;
	if(areaId != ""){
		hpBean['areaId'] = areaId;
		hpBean['areaname'] = areaname;
	}
	//hpBean['endTime'] = ChangeDateToString(new Date());
	if(industryId != ""){
		hpBean['industryId'] = industryId;
		hpBean['industryname'] = industryname;
	}
	//console.info("title.length="+title.length);
	//console.info("subtitle.length="+subtitle.length);
	if(title.length>30){
		msgShow("标题小于30个字符");
		return false;
	}
	if(subtitle.length>18){
		msgShow("子标题小于18个字符");
		return false;
	}
	if(title != ""){
		hpBean['title'] = title;
	}
	if(subtitle != ""){
		hpBean['subtitle'] = subtitle;
	}
	//var strhpBean=
	//JSON.stringify(hpBean)
	var url="homePage/saveHomePageSetting.do";
	var strhpBean = JSON.stringify(hpBean).replace(/null/g,"");
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"POST",
		//timeout: 900000,
		data:{
			'strhpBean':strhpBean
		},
		success:function(data){
			if(data.state=="success"){
				$(".cover_properties,.box_content,.ui_back").fadeOut(100);
				if(data.picpath != ""){
					$("#fengMianDiv").html('<img width="900px" src="'+data.picpath+'">');
				}
				show_save_success();
				$(".save_success").find("p").html(data.info);
			}else{
				$(".cover_properties,.box_content,.ui_back").fadeOut(100);
				show_save_success();
				$(".save_success").find("p").html(data.info);
			}
		},
		failure:function(data){
			$(".cover_properties,.box_content,.ui_back").fadeOut(100);
			show_save_success();
			$(".save_success").find("p").html("设置失败");
		}
	});
}

function ChangeDateToString(DateIn)
{
    var Year = 0 ;
    var Month = 0 ;
    var Day = 0 ;
    var CurrentDate = "" ;
    // 初始化时间
    Year = DateIn.getFullYear ();
    Month = DateIn.getMonth ()+ 1 ;
    Day = DateIn.getDate ();
    CurrentDate = Year + "-" ;
    if ( Month >= 10 )
    {
        CurrentDate = CurrentDate + Month + "-" ;
    }
    else
    {
         CurrentDate = CurrentDate + "0" + Month + "-" ;
    }
    if ( Day >= 10 )
    {
        CurrentDate = CurrentDate + Day ;
    }
    else
    {
        CurrentDate = CurrentDate + "0" + Day ;
    }
    return CurrentDate ;
} 

//删除封面信息
function delHomepageClick(){
	if(userPerm()){
		return false;
	}
	var param = {
		articleId:articleId
	};
	var url="homePage/deleteHomePageSetting.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			if(data.status == "success"){
				document.getElementById("fengMianDiv").innerHTML="";
				$(".cover_properties,.box_content,.ui_back").fadeOut(100);
				show_save_success();
				$("#fengMianDiv").html("");
				$(".save_success").find("p").html("删除成功");
			} else{
				$(".cover_properties,.box_content,.ui_back").fadeOut(100);
				show_save_success();
				$(".save_success").find("p").html("删除失败");
			}
		},
		failure:function(data){
			$(".cover_properties,.box_content,.ui_back").fadeOut(100);
			show_save_success();
			$(".save_success").find("p").html("删除失败");
		}
	});
}
//重命名
function replaceNameClick(){
	if(userPerm()){
		return false;
	}
	var articleName = $("#replaceName").val();
	if(articleName==""||articleName.replace(/\s+/g, '').length == 0){
		msgShow("名字不能为空或者全空格");
		return false;
	}else if(articleName.length>40){
		msgShow('名字小于40个字符');
		return false;
	}
	var param = {
		articleId:articleId,
		articleName:articleName
	};
	
	var url="word/replaceName.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			//alert(data.info);
			//$(".sbox_save,.box_content,.ui_back").fadeOut(100);
			$("#newarticlename").text(articleName);
			G_articleName=articleName;
			if(data.status=="success"){
				$(".sbox_save,.box_content,.ui_back").fadeOut(100);
				show_save_success();
				$(".save_success").find("p").html(data.info);
				setTimeout(function(){$(".save_success,.box_content,.ui_back").fadeOut(100);},2000);
				}
			else{
				$(".sbox_save div .tip").css({"display":'block'});
				$(".sbox_save div .tip").html(data.info);
				setTimeout(function(){$(".sbox_save div .tip").fadeOut(100);},2000);}
			//$(".save_success,.box_content,.ui_back").fadeIn(100);
			//$(".save_success").find("p").html(data.info);
		},
		failure:function(data){
			//$(".sbox_save,.box_content,.ui_back").fadeOut(100);
			$(".sbox_save div .tip").css({"display":'block'});
			$(".sbox_save div .tip").html("保存失败");
			setTimeout(function(){$(".sbox_save div .tip").fadeOut(100);},2000);
		}
	});
}

function saveConditions(ascondition) {
	//var ascondition = new Object();
	//ascondition['articleId'] = articleId;
	//ascondition['ascondition_all'] = ascondition_all;
	//console.info("--"+ascondition.ascondition_nocontains);
	var asconditionStr= JSON.stringify(ascondition).replace(/null/g,"");
	//console.info("--"+asconditionStr);
	var url="document/insertAdvCondition.do";
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"POST",
		data:{
			"ascondition":asconditionStr
		},
		success:function(data){
			historyList();
		},
		failure:function(data){
			show_save_success();
			$(".save_success").find("p").html("保存失败");
		}
	});
}
//退出
function loginout(){
	$.ajax({
        type: "post",
        url: "view/logout.do",
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data==1){
        		window.location.reload();
        	}
        }
    });
	closeExitDiv();
}
//替换空-
function spaceTo_(str){
	str = str.replace(/\s+/g,'_');
	if(str.substring(0,1)=="_"){
		str=str.substring(1);
	}
	if(str.substring(str.length-1)=="_"){
		str=str.substring(0,str.length-1);
	}
	return str;
}

//新建期刊
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
	
	if ($("#cover").val().length <= 0 || $("#cover").val() == null) {
        msgShow("请上传期刊封面");
        return;
    }
	if ($("#qikanName1").val().length <= 0 || $("#qikanName1").val() == null) {
		msgShow("请填写期刊名称");
        return;
    }
    if ($("#qikanBrief").val().length <= 0 || $("#qikanBrief").val() == null) {
    	msgShow("请填写期刊简介");
        return;
    }
    if ($("#brief_3").val().length <= 0 || $("#brief_3").val() == null) {
    	msgShow("请填写期刊标签");
        return;
    }
    if ($("#brief_3").val().split(" ").length>15) {
    	msgShow("最多15个标签");
        return;
    }else{
		var info = checklable($("#brief_3").val(),40,15,"标签");
		if(info.flag=="false"){
			msgShow(info.text);
			return;
		}
    }
    if ($("#keyword_3").val().length <= 0 || $("#keyword_3").val() == null) {
        msgShow("请填写期刊关键字");
        return;
    }else{
		var info=checklable($("#keyword_3").val(),8,15,"关键词");
		if(info.flag=="false"){
			msgShow(info.text);
			return;
		}
    }
    
	$.ajax({
        type: "GET",
        url: "user/journal/add.do",
        data:$("#form").serialize(),
        async:false,
        dataType:"json",
        success: function(data) {
        	if(data && data.result==1){
        		closeCreatPer();
        		msgShow("新建成功");
        	}else{
        		new altInfo({
					text:"保存失败！"	
				});
        	}
        }
    });
}

//上传期刊封面
function uploadImg() {
	var uploadBody = document.getElementById("uploadIframe").contentWindow.document.body;
	uploadBody.innerHTML="";
	var form = document.getElementById("uploadForm");
	if ($("#upimg").val().length <= 0 || $("#upimg").val() == null) {
		msgShow("请选择图片");
        return;
    }
    var len = $("#upimg").val().lastIndexOf(".");
    var type = $("#upimg").val().substring(len + 1).toLowerCase();
    if (type != "jpg" && type != "jpeg" && type != "bmp" && type != "gif" && type != "png") {
    	msgShow("图片格式错误，只支持jpg、jpeg、bmp、gif、png");
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
					  msgShow("上传成功");
					  return false;
				  }else{
					  msgShow(json.msg);
					  return false;
				  }
		      }
		  }
	},1000);
   /** if ($("#upimg").val().length <= 0 || $("#upimg").val() == null) {
    	msgShow("请选择图片");
        return;
    }
    var len = $("#upimg").val().lastIndexOf(".");
    var type = $("#upimg").val().substring(len + 1).toLowerCase();
    if (type != "jpg" && type != "jpeg" && type != "bmp" && type != "gif" && type != "png") {
    	msgShow("图片格式错误，只支持jpg、jpeg、bmp、gif、png");
        return;
    }
    $.ajaxFileUpload({
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
        		msgShow("上传成功");
        	}else{
        		msgShow(data.msg);
        	}
        }
    });*/
}

function journalTimeChange(){
	var articleJournalTime_1=ZU.$id("articleJournalTime_1").value;
	var articleJournalTypeId_1=ZU.$id("articleJournalTypeId_1").value;
	var str = getJournalStr(articleJournalTime_1,articleJournalTypeId_1);
	ZU.$id("articleJournalNameP").innerHTML=str;//期刊名字
	if(str.split(":").length>1){
		ZU.$id("articleJournalName_1").value=str.split(":")[1];
	}
}
/*
 * 检验标签关键词
 * bq 字符串,
 * max:最大几个字。
 * count:数量
 * txt:名字
 */
function checklable(bq,max,count,txt){
	var tems = bq.split(" ");
	var bqs = [];
	var info={};
	info.flag="true";
	for(var i = 0;i<tems.length;i++){
		if($.trim(tems[i]).length>=0 && $.trim(tems[i])!=""){
	    	bqs.push($.trim(tems[i]));
	    }
	}
	if(bqs.length>count){
		info.flag="false";
		info.text="最多"+count+"个"+txt;
		return info;
	}
	for(var i = 0;i<bqs.length;i++){
		if($.trim(bqs[i]).length>max){
			info.flag="false";
			info.text="每个"+txt+"最多为"+max+"个字";
			return info;
			break;
		}
		for(var j = 0;j<bqs.length;j++){
			if($.trim(bqs[i])==$.trim(bqs[j]) && i!=j){
				info.flag="false";
				info.text=txt+"重复";
				return info;
				break;
			}
		}
	}
	return info;
}

