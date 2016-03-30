//显示提示框
function msgShow(msg){
	new altInfo({
		text:msg
	});
}
var zhtmledit;
var iframeObj=null;
var iframeXY={};
var ue;
var iframeTimer=0;

window.onload=function(){
	rightNewsMenu();//右边菜单
};
//页面加载
function pageload(){
	if(G_passType==""){//弹出重名提示
		$(".sbox_save_new,.box_content,.ui_back").fadeIn(100);
		ZU.$id("replaceNewName").value = G_articleName;
	}else{//关闭提示
		$(".sbox_save_new,.box_content,.ui_back").fadeOut(100);
	}
	//ue.setContent('欢迎使用ueditor', null);
	iframeObj = ZU.$id("ueditor_0");
	//console.info("iframeObj="+iframeObj);
	iframeXY=ZU.getNodeOffset(iframeObj);//获取iframe
	zhtmledit = iframeObj.contentWindow;
	//console.info("iframeObj="+iframeObj.src);
	ue.addListener('updateSections', updateSectionsF);
	//ue.zeroclipboard.clip(ZU.$id("copyButton"));	
	//zhtmledit.document.body.innerHTML="123";
	//加载目录树
	var fengMianDiv = ZU.$id("treeFengMian");
	zhuTree1 = new zhuTree("tree_list");
	zhuTree1.fengMianDiv=fengMianDiv;
	zhuTree1.setJson(nodeList);
	zhuTree1.show();
	//findPlugin(1);//使用的插件
	var articleLabelText=ZU.$id("articleLabelText");
	//设置状态
	buttonDel();
}

/*
 * 保存文档
 * 
 */
function doSaveArticle(){
	if(userPerm()){
		return false;
	}
	var nodeContent = ue.getContent();
	var textContengt = ZU.$id("contentdiv").value;
	if(nodeContent==textContengt){
		//console.info("相等");
		return false;
	}else{
		//console.info("不相等");
		ZU.$id("contentdiv").value=nodeContent;
	}
	var url="setArticleContent.do";
	var nodeContent = ue.getContent();
	ZU.Ajax.request(url,{
		async:false,//true 是异步的,false:同步
		method:"post",
		data:{'articleId' : articleId, 'nodeContent':nodeContent},
		success:function(data){	
			if(data.status!="success"){
				msgShow(data.info);
				//保存失败
				if(data.article!=null||data.article!=undefined){
					G_articleType=data.article.articleType;
					G_passType = data.article.passType;
				}
				return false;
			}else{
				//保存成功
				G_articleType=data.article.articleType;
				G_passType = data.article.passType;
				return true;
			}

		},
		failure:function(data,sessionstatus){
			if(sessionstatus=="reload"){
				msgShow("你已经下线");
			}
			else if(sessionstatus=="timeout"){
				msgShow("请更新登录");
			}else{
				msgShow("保存失败");
			}
			return false;
		}
	});
}

/*保存至我的文档/我的模板，弹出层*/
var getSaveId,getSaveName,savaFlag=2;
function sava_to_my(Name,Id,n){
	getSaveId=Id;
	getSaveName=Name;
	if(n=='1'){
		$(".sbox_save_to_wd,.box_content,.ui_back,.ui_back2").fadeIn(100);
		$(".sbox_save_to_wd input#saveToWd").val(getSaveName);
	}else{
		$(".sbox_save_to_mb,.box_content,.ui_back,.ui_back2").fadeIn(100);
		$(".sbox_save_to_mb input#saveToMb").val(getSaveName);
	}	
}
/*确定按钮 保存到我的文档*/
function sure_save_to(n){
	var n2=n=='1'?2:1;
	if(n=='1'){
		$(".sbox_save_to_wd,.box_content,.ui_back,.ui_back2").fadeOut(100);
		getSaveName=ZU.$id("saveToWd").value;
	}else{
		$(".sbox_save_to_mb,.box_content,.ui_back,.ui_back2").fadeOut(100);	
		getSaveName=ZU.$id("saveToMb").value;
	}
	if(getSaveName==""||getSaveName.replace(/\s+/g, '').length == 0){
		msgShow("名字不能为空或者全空格");
		return false;
	}else if(getSaveName.length>40){
		msgShow('名字小于40个字符');
		return;
	}
	var url='word/checkArticleName.do?articleId='+getSaveId+'&articleName='+getSaveName+'&flag='+savaFlag+'&documentType='+n2;
	url=encodeURI(url);
	var newArticleId=0;
	//console.log(url);
	ZU.Ajax.request(url,{
		async:false,//是同步的
		method:"GET",
		success:function(date){
			if(date.status=="success"){
				newArticleId=date.articleId;
				//window.location = "/webword/word.do?articleId="+date.articleId;
			}else{
				msgShow(date.info);
			}
			
		},
		failure:function(date){
			msgShow('保存失败');
		}
	});
	if(newArticleId !=0){
		//window.open("/webword/word.do?articleId="+newArticleId);
	}
}

/*删除文档的弹出层*/
var getDeleMyId,deleMyNum;
function delect_pop(Id,n){	
	$(".sbox_delect,.box_content,.ui_back").fadeIn(100);	
	getDeleMyId=Id;
	deleMyNum=n;
}
/*确认删除文档功能*/
function artDelect(){		
	if(deleMyNum=='1'){//删除我的新闻
		url='document/newsDelById.do?id='+getDeleMyId;
		ZU.Ajax.request(url,{
			async:true,//是异步的
			method:"GET",
			success:function(date){
				//msgShow(date.state+'本篇新闻删除成功');
				if(date.state!="success"){
					msgShow(date.info);
				}else{
					$('#newsMyDiv ul[tid='+getDeleMyId+']').remove();
					msgShow('删除成功');
				}
			},
			failure:function(date){
				msgShow('本篇新闻删除失败');
			}
		});
	}else if(deleMyNum=='2'){//删除我的文档
		if(G_articleId==getDeleMyId){
			msgShow('当前编辑文档不能删除');
			return false;
		}
		url='word/articleDelById.do?id='+getDeleMyId;
		ZU.Ajax.request(url,{
			async:true,//是异步的
			method:"GET",
			success:function(date){
				if(date.state!="success"){
					msgShow(date.info);
				}else{
					$('#articleMyDiv ul[tid='+getDeleMyId+']').remove();
					msgShow('删除成功');
				}
			},
			failure:function(date){
				msgShow('删除失败');
			}
		});
	}else if(deleMyNum=='3'){//删除我的插件
		url='plugin/pluginDelById.do?id='+getDeleMyId;		
		ZU.Ajax.request(url,{
			async:true,//是异步的
			method:"GET",
			success:function(date){
				if(date.state!="success"){
					msgShow(date.info);
				}else{
					$('#pluginMyDiv ul[tid='+getDeleMyId+']').remove();
					msgShow('删除成功');
				}
				//
			},
			failure:function(date){
				msgShow('删除失败');
			}
		});
	}else if(deleMyNum=='4'){//删除我的模板
		if(G_articleId==getDeleMyId){
			msgShow('当前编辑模板不能删除');
			return false;
		}
		url='word/articleDelById.do?id='+getDeleMyId;		
		ZU.Ajax.request(url,{
			async:true,//是异步的
			method:"GET",
			success:function(date){
				if(date.state!="success"){
					msgShow(date.info);
				}else{
					$('#TemplateMyDiv ul[tid='+getDeleMyId+']').remove();
					msgShow('模板删除成功');
				}
				//
			},
			failure:function(date){
				msgShow('本条模板删除失败');
			}
		});
	}
	$(".sbox_delect,.box_content,.ui_back").fadeOut(100);
}

/**
 * 我的模板保存方式
 * newArticleid:模板的iD
 * */
function my_plug_sava(newArticleid){
	ZU.$id("templateArticleId").value = newArticleid;
	$(".sbox_my_plug,.box_content,.ui_back").fadeIn(100);
}
//我的模板保存
function saveAsTemplate(){
	var newArticleid=ZU.$id("templateArticleId").value;
	if(ZU.$id("templateType1").checked){//新建文档
		var url='word/checkArticleName.do?articleId='+newArticleid+'&articleName=&flag=2&documentType=2';
		url=encodeURI(url);
		newArticleid=0;
		ZU.Ajax.request(url,{
			async:false,//是同步的
			method:"GET",
			success:function(date){
				if(date.status=="success"){
					//return false;
					newArticleid=date.articleId;
				}else{
					msgShow(date.info);
				}
				$(".sbox_my_plug,.box_content,.ui_back").fadeOut(100);
			},
			failure:function(date){
				msgShow('另存为失败');
			}
		});
		
		if(newArticleid!=0){
			window.open("/webword/word.do?articleId="+newArticleid);
		}
		
	}else{
		$(".sbox_my_plug,.box_content,.ui_back").fadeOut(100);
		window.open("/webword/word.do?articleId="+newArticleid);
	}
}
//加载右边菜单
function rightNewsMenu(){
	getNewsAll(1);//全部新闻
	getNewsLike(1);//喜欢新闻
	getNewsMy(1);//我的新闻
	getPluginAll(1);//全部插件
	getPluginMy(1);//我的插件
	getPluginLike(1);//喜欢插件
	getArticleAll(1);//全部文档
	getArticleMy(1);//我的文档
	getArticleLike(1);//喜欢文档	
	getTemplateAll(1);//全部模板
	getTemplateMy(1);//我的模板
	getTemplateLike(1);//喜欢模板	
}
/*
 * 改变搜索类型
 * 
 */
function changeSearchType(type){
	if(type==0||type==1) searchParam.sort=type;
}

var urlJson={
		"newsAll":{
			"url":"document/searchNews.do"
		},
		"newsLike":{
			"url":"document/searchNews.do"
		}
};
/*
 * 搜索参数
 */
var searchParam = {
		searchType:"",
		must:"",
		arbitrarily:"",
		not:"",
		searchSource:"2",/*1:全文 2:标题*/
		sort:"1",
		startTime:"",
		endTime:"",
		fromSource:"",
		pageId:"1",
		pageSize:"10",
		strType:"",//默认新闻库,risk:审核通过
		desc:"全部新闻"
};

/*
 * 加载新闻 url
 * @param pageId:第几页 
 * @param must newsAll,newLike
 * 
 */
function getNewsAll(pageId,must){
	//console.info("新闻搜索");
	var param=ZU.extend({}, searchParam, true);
	param.startTime="";
	param.searchType="normal";
	param.pageId=pageId;
	if(must&&must!="") param.must = must;
	var today=new Date();
	var t=today.getTime()-1000*60*60*24;
	var yesterday=new Date(t);
	param.endTime=ZU.timeToStr(yesterday);
	//param.endTime="2015-07-15 08:00:00";
	//console.info("param.endTime="+param.endTime);
	param.startTime="1970-01-01 00:00:00";
	var url="document/searchNews.do?"+ZU.jsonToUrl(param);	
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			var str="";
			for(var i=0;i<data.list.list.length;i++){
				var node = data.list.list[i];
				var title = ZU.clearHtml(node.crawlTitle).replace(/\"/g,"\\\"");
				str+="<ul class='ul'>";
				str+="<li><img src='word/img/box_i.png' /><a id='p_"+node.crawlId+"_"+node.categoryId+"_"+node.esId+"' onmousedown='pmousedown(event,this,\"newsAll\",\"newsAllDiv\")' title='"+node.text+"' >"+node.crawlTitle+"</a></li>";
				str+="<li class='li2'><img title='另存为' src='word/img/list_o.png' onclick='save_mynews(\""+title+"\","+node.crawlId+","+node.categoryId+",\""+node.esId+"\")'/><img title='预览' onclick='pre_views(\""+title+"\","+node.crawlId+","+node.categoryId+",\""+node.esId+"\")' src='word/img/poi.png'> "+ZU.stampToTimeStr1(node.newsTime)+"</li>";
				str+="</ul>";
				//
			}
			var divObj = ZU.$id("newsAllDiv");
			divObj.innerHTML=str;
			var pageDiv=ZU.$id("newsAllPage");
			pageDiv.innerHTML = pageHtml(data.pageId,data.pageCount,"getNewsAll");
			var msgSpan = ZU.$id("msgSpan");
			msgSpan.innerHTML="找到相关新闻"+data.count+"篇";
			setCookie("news",param.must);//设置cookie
		},
		failure:function(data){
		}
	});
}
/*
 * 喜爱参数
 */
var newsLikeParam = {
		searchType:"",
		must:"",
		arbitrarily:"",
		not:"",
		searchSource:"2",/*1:全文 2:标题*/
		sort:"1",
		startTime:"",
		endTime:"",
		fromSource:"",
		pageId:"1",
		pageSize:"10",
		strType:"",//默认新闻库,risk:审核通过
		desc:"喜欢新闻"
};
/*
 * 获取喜爱新闻
 * 
 */
function getNewsLike(pageId){
	var param=ZU.extend({}, newsLikeParam, true);
	param.pageId=pageId;
	param.arbitrarily=getCookie("news");
	if(param.arbitrarily==null||param.arbitrarily=="") param.arbitrarily="银行"; 
	var url="document/searchNews.do?"+ZU.jsonToUrl(param);
	//console.info(url);
		ZU.Ajax.request(url,{
			async:true,//是异步的
			method:"GET",
			success:function(data){
				var str="";
				for(var i=0;i<data.list.list.length;i++){
					var node = data.list.list[i];
					var title = ZU.clearHtml(node.crawlTitle).replace(/\"/g,"\\\"");
					str+="<ul class='ul'>";
					str+="<li><img src='word/img/box_i.png' /><a id='p_"+node.crawlId+"_"+node.categoryId+"_"+node.esId+"'  onmousedown='pmousedown(event,this,\"newsAll\",\"newsLikeDiv\")' title='"+node.text+"'>"+node.crawlTitle+"</a></li>";
					str+="<li class='li2'><img title='另存为' onclick='save_mynews(\""+title+"\","+node.crawlId+","+node.categoryId+",\""+node.esId+"\")' src='word/img/list_o.png'/><img title='预览' onclick='pre_views(\""+title+"\","+node.crawlId+","+node.categoryId+",\""+node.esId+"\")' src='word/img/poi.png'> "+ZU.stampToTimeStr1(node.newsTime)+"</li>";
					str+="</ul>";
					//
				}
				var divObj = ZU.$id("newsLikeDiv");
				divObj.innerHTML=str;
				var pageDiv=ZU.$id("newsLikePage");
				pageDiv.innerHTML = pageHtml(data.pageId,data.pageCount,"getNewsLike");
				
			},
			failure:function(data){
			}
		});
}

/*
 * 
 */
/*
 * 获取我的新闻
 */
var myParam = {
		contentName:"",
		pageId:"1",
		pageSize:"10"
};
function getNewsMy(pageId){
	//searchMyNews.do?contentName=&pageId=1&pageSize=5
	var param=ZU.extend({}, myParam, true);
	param.pageId=pageId;
	var url="document/searchMyNews.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
			async:true,//是异步的
			method:"GET",
			success:function(data){
				var str="";
				for(var i=0;i<data.list.length;i++){
					var node = data.list[i];
					var contentName=node.contentName.replace(/\"/g,"\\\"");
					str+="<ul class='ul' tid='"+node.contentId+"'>";
					str+="<li><img src='word/img/box_i.png' /><a id='p_"+node.contentId+"' onmousedown='pmousedown(event,this,\"newsMy\",\"newsMyDiv\")' title='"+node.contentName+"'>"+node.contentName+"</a></li>";
					str+="<li class='li2'><img title='预览' onclick='pre_myviews(\""+contentName+"\","+node.contentId+")' src='word/img/poi.png'><img title='删除' src='word/img/dele.png' onclick='delect_pop("+node.contentId+',1'+")'/><em>"+ZU.stampToTimeStr1(node.insertTime)+"</em></li>";
					str+="</ul>";
					//
				}
				var divObj = ZU.$id("newsMyDiv");
				divObj.innerHTML=str;
				var pageDiv=ZU.$id("newsMyPage");
				pageDiv.innerHTML = pageHtml(data.pageId,data.pageCount,"getNewsMy");
				//preview_mynews(data);
				setCookie("news",myParam.contentName);//设置cookie
				var msgSpan = ZU.$id("msgSpan");
				msgSpan.innerHTML="找到相关新闻"+data.count+"篇";
			},
			failure:function(data){
			}
		});
}
/*
 * 生成页面的url
 * @param pageId 页号 1
 * @param pageSize 页大小 10
 * @param pageCount 页面大小
 * pageUl
 */
function pageHtml(pageId,pageCount,fstr){
	//console.info("pageId="+pageId+",pageCount="+pageCount+",fstr="+fstr);
	var pageSize=5;//显示5个
	var	str="";
	var newPageId=(pageId+1)-pageSize;
	if(newPageId<0) newPageId=0;
	
	if(pageId==1&&newPageId==0){
		//str="<li class='l1'><上一页</li>";
	}else{
		str="<li class='l1' onclick='"+fstr+"("+(pageId-1)+")'><上一页</li>";
	}

	//console.info("newPageId="+newPageId);
	for(var i=1;i<=pageSize;i++){
		str+="<li onclick='"+fstr+"("+(newPageId+i)+")'";
		if(pageId==(newPageId+i)){
			str+="style='color:#fff;background-color:#28af85;border:1px solid #0e8054;'";
		}
			
		str+=">"+(newPageId+i)+"</li>";
		if((newPageId+i)>=pageCount){
			//pageId=pageCount;
			break;
		}
	}
	if(pageId==pageCount||pageCount==0){
		//console.info("pageId"+pageId+",pageCount="+pageCount);
		//str+="<li class='l_last'>下一页></li>";
	}else{
		str+="<li class='l_last' onclick='"+fstr+"("+(pageId+1)+")'>下一页></li>";
	}
	
	//if()
	//<li class="l1"><上一页</li><li>1</li><li>2</li><li>3</li><li>4</li><li>5</li><li class="l_last">下一页></li>
	return str;
}
/*
 * 插件处理
 */
var pluginParam = {
		userId:"",
		name:"",
		pageId:"1",
		pageSize:"10"
};
/*
 * 全部插件
 */
function getPluginAll(pageId,name){
	var param=ZU.extend({}, pluginParam, true);
	param.pageId = pageId;
	if(name&&name!="") param.name = name;
	var url="plugin/searchPlugins.do?"+ZU.jsonToUrl(param);
	
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			var str="";
			for(var i=0;i<data.list.length;i++){
				var node = data.list[i];
				str+="<ul class='ul'>";
				str+="<li id='pluginAll_"+node.pluginId+"_"+node.pluginParams+"_"+node.pluginName+"' onmousedown='pmousedown(event,this,\"plugin\",\"pluginAllDiv\")' class='li1' title='"+node.pluginIntro+"'><img src='word/img/box_i.png' />"+node.pluginName.substring(0,22)+"</li>";
				str+="<li class='li2'><img title='另存为' onclick='save_myslugins(\""+node.pluginName+"\","+node.pluginId+")' src='word/img/list_o.png'/><img title='简介' onclick='pre_views_plug(\""+node.pluginName+"\","+node.pluginId+")' src='word/img/poi.png'/></li>";
				str+="</ul>";
				//
			}
			var divObj = ZU.$id("pluginAllDiv");
			divObj.innerHTML=str;
			var pageDiv=ZU.$id("pluginAllPage");
			pageDiv.innerHTML = pageHtml(data.pageId,data.pageCount,"getPluginAll");
			var msgSpan = ZU.$id("msgSpan");
			msgSpan.innerHTML="找到相关插件"+data.count+"个";
			setCookie("plugin",param.name);//设置cookie
		},
		failure:function(data){
		}
	});
}
/*
 * 我的插件
 */
var pluginMyParam ={
		userId:"",
		name:"",
		pageId:"1",
		pageSize:"10"
};
 
/*
 * 我的插件
 */
function getPluginMy(pageId,name){
	var param=ZU.extend({}, pluginMyParam, true);
	param.pageId = pageId;
	param.userId=userId;//用户的Id
	if(name&&name!="") param.name = name;
	var url="plugin/searchMyPlugins.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			var str="";
			for(var i=0;i<data.list.length;i++){
				var node = data.list[i];
				str+="<ul class='ul' tid='"+node.pluginUserId+"'>";
				str+="<li id='pluginMy_"+node.pluginId+"_"+node.pluginParams+"_"+node.myPluginName+"' onmousedown='pmousedown(event,this,\"plugin\",\"pluginMyDiv\")' class='li1' title='"+node.myPluginName+"'><img src='word/img/box_i.png' />"+node.myPluginName.substring(0,22)+"</li>";
				str+="<li class='li2'><img title='简介' onclick='pre_views_plug(\""+node.myPluginName+"\","+node.pluginId+")' src='word/img/poi.png'/><img title='删除' src='word/img/dele.png' onclick='delect_pop("+node.pluginUserId+",3)'/></li>";
				str+="</ul>";
				//
			}
			var divObj = ZU.$id("pluginMyDiv");
			divObj.innerHTML=str;
			var pageDiv=ZU.$id("pluginMyPage");
			pageDiv.innerHTML = pageHtml(data.pageId,data.pageCount,"getPluginMy");
			var msgSpan = ZU.$id("msgSpan");
			msgSpan.innerHTML="找到相关插件"+data.count+"个";
			setCookie("plugin",param.name);//设置cookie
		
		},
		failure:function(data){
		}
	});
}
/*
 * 喜欢的插件
 */
var pluginLikeParam ={
		userId:"",
		name:"",
		pageId:"1",
		pageSize:"10"
};
/*
 * 喜欢的插件
 */
function getPluginLike(pageId){
	var param=ZU.extend({}, pluginLikeParam, true);
	param.pageId = pageId;
	param.name=getCookie("plugin");
	var url="plugin/searchPlugins.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			var str="";
			for(var i=0;i<data.list.length;i++){
				var node = data.list[i];
				str+="<ul class='ul'>";
				str+="<li id='pluginLike_"+node.pluginId+"_"+node.pluginParams+"_"+node.pluginName+"' onmousedown='pmousedown(event,this,\"plugin\",\"pluginLikeDiv\")' class='li1' title='"+node.pluginIntro+"'><img src='word/img/box_i.png' />"+node.pluginName.substring(0,22)+"</li>";
				str+="<li class='li2'><img title='另存为' onclick='save_myslugins(\""+node.pluginName+"\","+node.pluginId+")' src='word/img/list_o.png'/><img title='简介' onclick='pre_views_plug(\""+node.pluginName+"\","+node.pluginId+")' src='word/img/poi.png'/></li>";
				str+="</ul>";
				//
			}
			var divObj = ZU.$id("pluginLikeDiv");
			divObj.innerHTML=str;
			var pageDiv=ZU.$id("pluginLikePage");
			pageDiv.innerHTML = pageHtml(data.pageId,data.pageCount,"getPluginLike");
			//var msgSpan = ZU.$id("msgSpan");
			//msgSpan.innerHTML="找到相关插件"+data.count+"个";
			setCookie("plugin",param.name);//设置cookie
		},
		failure:function(data){
		}
	});
}

var articleParam ={
		"searchType":"normal",//全部文档
		"name":"",
		"articleType":"document",
		"passType":"PASSED",
		"userId":"",//用户的id
		"pageId":"1",
		"pageSize":"10"
};
/*
 * 获取全部文档,平台文档
 */
function getArticleAll(pageId){
	var param=ZU.extend({}, articleParam, true);
	param.pageId = pageId;
	var url="word/searchArticle.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			var str="";
			for(var i=0;i<data.list.length;i++){
				var node = data.list[i];
				str+='<ul class="ul">';
				str+='<h3 id="articleAll_a_'+node.articleId+'" onclick="viewNode(event,'+node.articleId+',this,\'articleAll\')" class="down">';
				str+='<a id="article_'+node.articleId+'" onmousedown="pmousedown(event,this,\'article\',\'articleAllDiv\')" title="'+node.articleName+'">'+node.articleName.substring(0,15)+'</a></h3>';
				str+='<li id="articleAll_b_'+node.articleId+'" style="display:none"></li>';
				str+='<span class="li2"><a title="保存我的文档" href="javascript:;" onclick="sava_to_my(\''+node.articleName+'\','+node.articleId+',1)"><img src="word/img/list_o.png"/></a><img title="预览" onclick="pre_mydocumet(\''+node.articleName+'\','+node.articleId+')" src="word/img/poi.png"/><em>'+ZU.stampToTimeStr1(node.updateTime)+'</em></span>';//img src="word/img/list_o.png"/>
				str+='</ul>';
				//
			}
			var divObj = ZU.$id("articleAllDiv");
			divObj.innerHTML=str;
			var pageDiv=ZU.$id("articleAllPage");
			pageDiv.innerHTML = pageHtml(data.pageId,data.pageCount,"getArticleAll");
			setCookie("article",param.name);//设置cookie
			var msgSpan = ZU.$id("msgSpan");
			msgSpan.innerHTML="找到相关文档"+data.count+"篇";
		},
		failure:function(data){
			//console.info("获取全部文档异常");
		}
	});
}
//我的文档
var articleMyParam ={
		"searchType":"",//我的文档
		"passType":"",
		"name":"",
		"articleType":"document",
		"userId":"",//用户的id
		"pageId":"1",
		"pageSize":"10"
};
/*
 * 获取我的文档
 */
function getArticleMy(pageId){
	var param=ZU.extend({}, articleMyParam, true);
	param.pageId = pageId;
	var url="word/searchArticle.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			var str="";
			for(var i=0;i<data.list.length;i++){
				var node = data.list[i];
				str+='<ul class="ul" tid="'+node.articleId+'">';
				str+='<h3 id="articleMy_a_'+node.articleId+'" onclick="viewNode(event,'+node.articleId+',this,\'articleMy\')" class="down">';
				str+='<a id="articleMy_'+node.articleId+'" onmousedown="pmousedown(event,this,\'article\',\'articleMyDiv\')" title="'+node.articleName+'">'+node.articleName.substring(0,15)+'</a></h3>';
				str+='<li id="articleMy_b_'+node.articleId+'" style="display:none"></li>';
				str+='<span class="li2"><img title="预览" onclick="pre_mydocumet(\''+node.articleName+'\','+node.articleId+')" src="word/img/poi.png"/> <a href="word.do?articleId='+node.articleId+'" title="修改" target="_blank"><img src="word/img/xg.png"/></a><img title="删除" src="word/img/dele.png" onclick="delect_pop('+node.articleId+",2"+')" /><em>'+ZU.stampToTimeStr1(node.updateTime)+'</em></span>';//
				str+="</ul>";
				//
			}
			var divObj = ZU.$id("articleMyDiv");
			divObj.innerHTML=str;
			var pageDiv=ZU.$id("articleMyPage");
			pageDiv.innerHTML = pageHtml(data.pageId,data.pageCount,"getArticleMy");
			setCookie("article",param.name);//设置cookie
			var msgSpan = ZU.$id("msgSpan");
			msgSpan.innerHTML="找到相关文档"+data.count+"篇";
		},
		failure:function(data){
			//console.info("获取我的文档异常");
		}
	});
}
//喜欢
var articleLikeParam ={
		"searchType":"normal",//全部文档
		"name":"",
		"passType":"PASSED",
		"articleType":"document",
		"userId":"",//用户的id
		"pageId":"1",
		"pageSize":"10",
		"desc":"like"
};
/*
 * 获取喜爱文档
 */
function getArticleLike(pageId){
	var param=ZU.extend({}, articleLikeParam, true);
	param.pageId = pageId;
	param.name=getCookie("article");
	if(param.name==""||param.name==null){
		param.name="银行";
	}
	var url="word/searchArticle.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			var str="";
			for(var i=0;i<data.list.length;i++){
				var node = data.list[i];
				str+='<ul class="ul">';
				str+='<h3 id="articleLike_a_'+node.articleId+'" onclick="viewNode(event,'+node.articleId+',this,\'articleLike\')" class="down">';
				str+='<a id="articleLike_'+node.articleId+'" onmousedown="pmousedown(event,this,\'article\',\'articleLikeDiv\')" title="'+node.articleName+'">'+node.articleName.substring(0,15)+'</a></h3>';
				str+='<li id="articleLike_b_'+node.articleId+'" style="display:none"></li>';
				str+='<span class="li2"><a onclick="sava_to_my(\''+node.articleName+'\','+node.articleId+',1)" title="修改" target="_blank"><img src="word/img/list_o.png"/></a><img title="预览" onclick="pre_mydocumet(\''+node.articleName+'\','+node.articleId+')" src="word/img/poi.png"/><em>'+ZU.stampToTimeStr1(node.updateTime)+'</em></span>';//img src="word/img/list_o.png"/>
				str+="</ul>";
				//
			}
			var divObj = ZU.$id("articleLikeDiv");
			divObj.innerHTML=str;
			var pageDiv=ZU.$id("articleLikePage");
			pageDiv.innerHTML = pageHtml(data.pageId,data.pageCount,"getArticleLike");
			var msgSpan = ZU.$id("msgSpan");
			msgSpan.innerHTML="找到相关文档"+data.count+"篇";
		},
		failure:function(data){
			//console.info("获取喜爱文档异常");
		}
	});
}

/*
 * 显示文档节点
 * event:鼠标
 * id:文章的ID
 * type : articleAll,
 */
function viewNode(evnet,id,hObj,type){
	var e=evnet||window.event;
	var obj1=e.target||e.srcElement;
	//console.info(obj1.nodeName);
	if(obj1.nodeName!="H3") return false; 
	var obj = document.getElementById(type+"_b_"+id);
	if(obj.style.display==""){
		obj.style.display="none";
		hObj.className = "down";
		return false;
	}
	hObj.className = "";
	var url="getNodeList.do?articleId="+id;
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			obj.style.display = "";
			var nodeTree = new zhuTreeStr(type+"_b_"+id,type+"Div");
			nodeTree.setJson(data);
			nodeTree.show();
			
		},
		failure:function(data){
			//console.info("获取节点失败");
		}
	});
	
}
/*模板开始*/
var TemplateParam ={
		"searchType":"normal",//全部模板
		"passType":"PASSED",
		"name":"",
		"articleType":"template",
		"userId":"",//用户的id
		"pageId":"1",
		"pageSize":"10"
};
/*
 * 获取全部模板
 */
function getTemplateAll(pageId){
	var param=ZU.extend({}, TemplateParam, true);
	param.pageId = pageId;
	var url="word/searchArticle.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){			
			var str="";
			for(var i=0;i<data.list.length;i++){
				var node = data.list[i];
				str+='<ul class="ul">';
				str+='<h3 id="TemplateAll_a_'+node.articleId+'" onclick="viewNode(event,'+node.articleId+',this,\'TemplateAll\')" class="down">';
				str+='<a id="Template_'+node.articleId+'" onmousedown="pmousedown(event,this,\'article\',\'TemplateAllDiv\')" title="'+node.articleName+'">'+node.articleName.substring(0,15)+'</a></h3>';
				str+='<li id="TemplateAll_b_'+node.articleId+'" style="display:none"></li>';
				str+='<span class="li2"><a onclick="sava_to_my(\''+node.articleName+'\','+node.articleId+',2)" title="保存我的模板"><img src="word/img/list_o.png"/></a><img title="预览" onclick="pre_mydocumet(\''+node.articleName+'\','+node.articleId+')" src="word/img/poi.png"/><em>'+ZU.stampToTimeStr1(node.updateTime)+'</em></span>';//img src="word/img/list_o.png"/>
				str+='</ul>';
				//
			}
			var divObj = ZU.$id("TemplateAllDiv");
			divObj.innerHTML=str;
			var pageDiv=ZU.$id("TemplateAllPage");
			pageDiv.innerHTML = pageHtml(data.pageId,data.pageCount,"getTemplateAll");
			setCookie("Template",param.name);//设置cookie
			var msgSpan = ZU.$id("msgSpan");
			msgSpan.innerHTML="找到相关模板"+data.count+"篇";
		},
		failure:function(data){
			//console.info("获取全部模板异常");
		}
	});
}
//我的模板
var TemplateMyParam ={
		"searchType":"",//全部模板
		"name":"",
		"passType":"",
		"articleType":"template",
		"userId":"",//用户的id
		"pageId":"1",
		"pageSize":"10"
};
/*
 * 获取我的模板
 */
function getTemplateMy(pageId){
	var param=ZU.extend({}, TemplateMyParam, true);
	param.pageId = pageId;
	var url="word/searchArticle.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			var str="";
			for(var i=0;i<data.list.length;i++){
				var node = data.list[i];
				str+='<ul class="ul" tid="'+node.articleId+'">';
				str+='<h3 id="TemplateMy_a_'+node.articleId+'" onclick="viewNode(event,'+node.articleId+',this,\'TemplateMy\')" class="down">';
				str+='<a id="TemplateMy_'+node.articleId+'" onmousedown="pmousedown(event,this,\'article\',\'TemplateMyDiv\')" title="'+node.articleName+'">'+node.articleName.substring(0,15)+'</a></h3>';
				str+='<li id="TemplateMy_b_'+node.articleId+'" style="display:none"></li>';
				str+='<span class="li2"><img title="预览" onclick="pre_mydocumet(\''+node.articleName+'\','+node.articleId+')" src="word/img/poi.png"/><a href="javascript:;" onclick="my_plug_sava('+node.articleId+')" title="编辑" ><img src="word/img/xg.png"/></a><img title="删除" src="word/img/dele.png" onclick="delect_pop('+node.articleId+",4"+')"/><em>'+ZU.stampToTimeStr1(node.updateTime)+'</em></span>';//img src="word/img/list_o.png"/>
				str+="</ul>";
				//
			}
			var divObj = ZU.$id("TemplateMyDiv");
			divObj.innerHTML=str;
			var pageDiv=ZU.$id("TemplateMyPage");
			pageDiv.innerHTML = pageHtml(data.pageId,data.pageCount,"getTemplateMy");
			setCookie("Template",param.name);//设置cookie
			var msgSpan = ZU.$id("msgSpan");
			msgSpan.innerHTML="找到相关模板"+data.count+"篇";
		},
		failure:function(data){
			//console.info("获取我的模板异常");
		}
	});
}
//喜欢
var TemplateLikeParam ={
		"searchType":"normal",//normal:全部模板
		"name":"",
		"passType":"PASSED",
		"articleType":"template",
		"userId":"",//用户的id
		"pageId":"1",
		"pageSize":"10"
};
/*
 * 获取喜爱模板
 */
function getTemplateLike(pageId){
	var param=ZU.extend({}, TemplateLikeParam, true);
	param.pageId = pageId;
	param.name=getCookie("Template");
	if(param.name==""||param.name==null){
		param.name="银行";
	}
	var url="word/searchArticle.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			var str="";
			for(var i=0;i<data.list.length;i++){
				var node = data.list[i];
				str+='<ul class="ul">';
				str+='<h3 id="TemplateLike_a_'+node.articleId+'" onclick="viewNode(event,'+node.articleId+',this,\'TemplateLike\')" class="down">';
				str+='<a id="TemplateLike_'+node.articleId+'" onmousedown="pmousedown(event,this,\'article\',\'TemplateLikeDiv\')" title="'+node.articleName+'">'+node.articleName.substring(0,15)+'</a></h3>';
				str+='<li id="TemplateLike_b_'+node.articleId+'" style="display:none"></li>';
				str+='<span class="li2"><a onclick="sava_to_my(\''+node.articleName+'\','+node.articleId+',2)" title="修改" target="_blank"><img src="word/img/list_o.png"/></a><img title="预览" onclick="pre_mydocumet(\''+node.articleName+'\','+node.articleId+')" src="word/img/poi.png"/><em>'+ZU.stampToTimeStr1(node.updateTime)+'</em></span>';//img src="word/img/list_o.png"/>
				str+="</ul>";
				//
			}
			var divObj = ZU.$id("TemplateLikeDiv");
			divObj.innerHTML=str;
			var pageDiv=ZU.$id("TemplateLikePage");
			pageDiv.innerHTML = pageHtml(data.pageId,data.pageCount,"getTemplateLike");
			var msgSpan = ZU.$id("msgSpan");
			msgSpan.innerHTML="找到相关模板"+data.count+"篇";
		},
		failure:function(data){
			//console.info("获取喜爱模板异常");
		}
	});
}
/*
 * 显示模板节点
 * event:鼠标
 * id:文章的ID
 * type : articleAll,
 */
function viewNode(evnet,id,hObj,type){
	var e=evnet||window.event;
	var obj1=e.target||e.srcElement;
	//console.info(obj1.nodeName);
	if(obj1.nodeName!="H3") return false; 
	var obj = document.getElementById(type+"_b_"+id);
	if(obj.style.display==""){
		obj.style.display="none";
		hObj.className = "down";
		return false;
	}
	hObj.className = "";
	var url="getNodeList.do?articleId="+id;
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			obj.style.display = "";
			var nodeTree = new zhuTreeStr(type+"_b_"+id,type+"Div");
			nodeTree.setJson(data);
			nodeTree.show();
			
		},
		failure:function(data){
			//console.info("获取节点失败");
		}
	});
	
}
/*模板结束*/
/*
 * value 要设置cookie的值
 * name :cookie名字 news,plugin,article
 */
function setCookie(name,value){
	if(value==null||value=="") {
		//console.info("cookie 设置是空值");
		return ;
	}
	var value = value.replace("  "," ");
	var str=ZU.getCookie(name);
	//console.info("str="+str);
	if(str==null||str==""){
		str=value;
	}else{
		if(testLable(str,value)){//存在,不添加
			
		}else{//不存在加入
			str= value+" "+ZU.getCookie(name);
		}
	}
	var strArray = str.split(" ");//按照空格拆分出数组
	//console.info(strArray.length);
	strArray.length=6;
	value = strArray.join(" ");
	ZU.setCookie(name,value);
}
/*
 * name :cookie名字
 */
function getCookie(name){
	return ZU.getCookie(name);
}
/*
 * 标准的
 */
function getPlugin(pluginId){
	var url="plugin/getPlugin.do?pluginId="+pluginId;
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			
		},
		failure:function(data){
		}
	});
}

/*
 * 遍历插件
 * 
 */
var pluginFind={
		"pageId":1,
		"name":"",
		"pageSize":10
};
//plugId=6&industryId=96&industryName=it&areaId=2&areaName=北京市&startTime=&endTime=2015-05-12&params=2,3,4&pluginName=行业偿债能力&id=plugin_6_1431481301939
function findPlugin(pageId){
	var plist=iframeObj.contentWindow.findPlugin(pluginFind.name);
	//console.info("--"+plist.length);
	var str="";
	var strat = (pageId-1)*pluginFind.pageSize;
	var end =strat+pluginFind.pageSize-1;
	for(var i=strat;i<plist.length;i++){
		var node = plist[i];
		var urlStr= node.getAttribute("text");
		var plugId = ZU.getParam("plugId", urlStr);
		var pluginName = ZU.getParam("pluginName", urlStr);
		var params = ZU.getParam("params", urlStr);
		//是插件
		if(i>end) break;
		str+="<ul class='ul'>";
		str+="<li id='pluginUser_"+plugId+"_"+params+"_"+pluginName+"' onmousedown='pmousedown(event,this,\"plugin\",\"pluginUserDiv\")' class='li1' title='"+pluginName+"'><img src='word/img/box_i.png' />"+pluginName+"</li>";
		str+="<li class='li2'>";
		str+="<img onclick='pluginDel(\""+node.id+"\",this)' title='删除' src='word/img/llist_5.png'/>";
		str+="<img onclick='pluginDo(\""+node.id+"\",\""+plugId+"\",\""+pluginName+"\",\""+urlStr+"\")' title='执行' src='word/img/llist_3.png'/>";
		str+="<img onclick='save_myslugins(\""+pluginName+"\","+plugId+")' title='另存为' src='word/img/list_o.png'/>";
		str+="<img onclick='pre_views_plug(\""+pluginName+"\","+plugId+")' title='详情' src='word/img/poi.png'/></li>";
		str+="</ul>";
	}
	var divObj = ZU.$id("pluginUserDiv");
	divObj.innerHTML=str;
	var pageCount=Math.floor((plist.length/10)+1);
	var pageDiv=ZU.$id("pluginUserPage");
	pageDiv.innerHTML = pageHtml(pageId,pageCount,"findPlugin");
	//var msgSpan = ZU.$id("msgSpan");
	//msgSpan.innerHTML="找到相关插件"+data.count+"个";
	//setCookie("plugin",param.name);//设置cookie
	
}
/*
 * 执行插件
 * plugId:
 * pluginName:
 * urlStr:
 */
//plugId=6&industryId=96&industryName=it&areaId=2&areaName=北京市&startTime=&endTime=2015-05-12&params=2,3,4&pluginName=行业偿债能力&id=plugin_6_1431481301939
function pluginDo(id,plugId,pluginName){
	//G_pluginParams = '3,4';
	//ue.fireEvent('saveScene');
	G_pluginDivId = id;
	G_pluginId = plugId;//pluginId;
	
	G_pluginName = pluginName;
	var urlStr = iframeObj.contentWindow.getPluginText(id);//获取插件的配置
	G_pluginParams = ZU.getParam("params", urlStr);
	var arry = G_pluginParams.split(",");
	
	var endDate = document.getElementById("pluginEndDate");
	var industry = document.getElementById("pluginIndustryId");
	var provice = document.getElementById("pluginProvinceId");
	var industryName = document.getElementById("pluginIndustry");
	var proviceName = document.getElementById("pluginProvince");
	
	endDate.value="";
	industry.value = "";
	provice.value="";
	industryName.value="";
	proviceName.value ="";
	
	endDate.parentNode.style.display="none";
	industryName.parentNode.style.display = "none";
	proviceName.parentNode.style.display ="none";
	
	for(var i=0;i<arry.length;i++){
		if(arry[i]==1){//开始时间
			var startTime = ZU.getParam("startTime", urlStr);
		}else if(arry[i]==2){//结束时间
			var endTime	= ZU.getParam("endTime", urlStr);
			if(endTime==""){
				endDate.value="";
			}else{
				endDate.value = endTime.split(" ")[0];
			}
			endDate.parentNode.style.display="";
		}else if(arry[i]==3){//区域
			proviceName.value=ZU.getParam("areaName", urlStr);
			proviceName.parentNode.style.display="";
			
			var areaId	= ZU.getParam("areaId", urlStr);
			provice.value=areaId;
			
		}else if(arry[i]==4){//行业
			industryName.value=ZU.getParam("industryName", urlStr);
			industryName.parentNode.style.display="";
			
			industry.value=ZU.getParam("industryId", urlStr);
			
		}
	}
	document.getElementById("pluginInsertDiv").style.display="none";
	
	$(".plugin_properties,.box_content,.ui_back").fadeIn(100);
}
/*
 * 删除Id
 * id:插件的id
 */
function pluginDel(id,obj){
	ue.fireEvent('saveScene');
	iframeObj.contentWindow.pluginDel(id);
	ZU.removeChild(obj.parentNode.parentNode);
	ue.fireEvent('saveScene');
}

//获取全局属性
function showGlobal(){
	var url="word/getGlobalByArticleId.do?articleId="+articleId;
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			var endDate = document.getElementById("endDate");
			var industry = document.getElementById("globalIndustryId");
			var provice = document.getElementById("globalProvinceId");
			var industryName = document.getElementById("globalIndustry");
			var proviceName = document.getElementById("globalProvince");
			
			if(data.status=="error"){//空值
				endDate.value="";
				industry.value = "";
				provice.value="";
				industryName.value="";
				proviceName.vauel ="";
				
			}else if(data.status=="success"){//
				if(data.bean.endTime!=undefined||data.bean.endTime!=null){
					endDate.value = ZU.stampToTimeStr(data.bean.endTime).split(" ")[0];
				}
				if(data.bean.areaName!=undefined||data.bean.areaName!=null){
					proviceName.value = data.bean.areaName;
				}
				if(data.bean.areaId!=undefined||data.bean.areaId!=null){
					provice.value =data.bean.areaId;
				}
				if(data.bean.industryName!=undefined||data.bean.industryName!=null){
					industryName.value = data.bean.industryName;
				}
				if(data.bean.industryId!=undefined||data.bean.industryId!=null){
					industry.value =data.bean.industryId;
				}
				
			}
		},
		failure:function(data){
		}
	});
}
//获取大纲属性
function showOutLine(){
	var url="word/getOutlineByArticleId.do?articleId="+articleId;
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			var endDate = document.getElementById("time2");
			var startDate = document.getElementById("time1");
			var industry = document.getElementById("outIndustryId");
			var provice = document.getElementById("outProvinceId");
			var industryName = document.getElementById("outIndustry");
			var proviceName = document.getElementById("outProvince");
			
			if(data.status=="error"){//空值
				endDate.value="";
				industry.value = "";
				provice.value="";
				industryName.value="";
				proviceName.vauel ="";
				
			}else if(data.status=="success"){//
				if(data.bean.endTime!=undefined||data.bean.endTime!=null){
					endDate.value = ZU.stampToTimeStr(data.bean.endTime).split(" ")[0];
				}
				if(data.bean.startTime!=undefined||data.bean.startTime!=null){
					startDate.value = ZU.stampToTimeStr(data.bean.startTime).split(" ")[0];
				}
				if(data.bean.areaName!=undefined||data.bean.areaName!=null){
					proviceName.value = data.bean.areaName;
				}
				if(data.bean.areaId!=undefined||data.bean.areaId!=null){
					provice.value =data.bean.areaId;
				}
				if(data.bean.industryName!=undefined||data.bean.industryName!=null){
					industryName.value = data.bean.industryName;
				}
				if(data.bean.industryId!=undefined||data.bean.industryId!=null){
					industry.value =data.bean.industryId;
				}
			}
		},
		failure:function(data){
		}
	});
}
/*
 * 高级搜搜重置
 */
function advanceReset(){
	document.getElementById("ascondition_all").value="";
	document.getElementById("ascondition_any").value="";
	document.getElementById("ascondition_nocontains").value="";
	
	document.getElementById("time_end").value ="";
	document.getElementById("time_start").value ="";
	document.getElementById("ascondition_sort").options[0].selected=true;//排序方式 1:时间,2权重
	document.getElementById("ascondition_part_1").checked=true;//范围 1全文 2标题
}
/*
 * 高级搜索参数
 */
var advancedParam = {
		searchType:"",
		must:"",
		arbitrarily:"",
		not:"",
		searchSource:"1",/*1全文 2标题*/
		sort:"1",/*1:时间,2权重*/
		startTime:"",
		endTime:"",
		fromSource:"",
		pageId:"1",
		strType:"",//默认新闻库,risk:审核通过
		pageSize:"10"
};
/*
 * 高级搜索点击
 */
function advanceClick(){
	advancedParam.must = document.getElementById("ascondition_all").value;
	advancedParam.arbitrarily = document.getElementById("ascondition_any").value;
	advancedParam.not = document.getElementById("ascondition_nocontains").value;
	
	advancedParam.startTime = document.getElementById("time_end").value;
	advancedParam.endTime = document.getElementById("time_start").value;
	if(document.getElementById("ascondition_part_2").checked){
		advancedParam.searchSource=2;//按标题
	}else {
		advancedParam.searchSource=1;//全文
	}
	advancedParam.sort = document.getElementById("ascondition_sort").value;
	advancedParam.fromSource = document.getElementById("ascondition_fromSource").value;
	if(advancedParam.must==""&&advancedParam.arbitrarily==""&&advancedParam.not==""&&advancedParam.fromSource==""){
		msgShow("请输入搜索关键字！");
		return false;//没有输入值,直接返回
	}
	if(ZU.$id("strTypeCheckbox").checked){//搜索风险信息库
		advancedParam.strType = "risk";
	}else{
		advancedParam.strType = "";
	}
	advanceUrl(1);
	setSearchHistory();
}
//高级搜索 url
function advanceUrl(pageId){
	var param=ZU.extend({}, advancedParam, true);
	param.pageId=pageId;
	var url="document/searchNews.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			var str="";
			for(var i=0;i<data.list.list.length;i++){
				var node = data.list.list[i];
				var title = ZU.clearHtml(node.crawlTitle).replace(/\"/g,"\\\"");
				str+="<ul class='ul'>";
				str+="<li><img src='word/img/box_i.png' /><a id='p_"+node.crawlId+"_"+node.categoryId+"_"+node.esId+"' onmousedown='pmousedown(event,this,\"newsAll\",\"newsAllDiv\")' title='"+node.text+"' >"+node.crawlTitle+"</a></li>";
				str+="<li class='li2'><img title='另存为' src='word/img/list_o.png' onclick='save_mynews(\""+title+"\","+node.crawlId+","+node.categoryId+",\""+node.esId+"\")'/><img title='预览' onclick='pre_views(\""+title+"\","+node.crawlId+","+node.categoryId+",\""+node.esId+"\")' src='word/img/poi.png'> "+ZU.stampToTimeStr1(node.newsTime)+"</li>";
				str+="</ul>";
				//
			}
			var divObj = ZU.$id("newsAllDiv");
			if(data.count==0){
				str='<p class="no_answer">抱歉！没有您要搜索的内容。</p>';
			}
			divObj.innerHTML=str;
			var pageDiv=ZU.$id("newsAllPage");
			pageDiv.innerHTML = pageHtml(data.pageId,data.pageCount,"advanceUrl");
			var msgSpan = ZU.$id("msgSpan");
			msgSpan.innerHTML="找到相关新闻"+data.count+"篇";
			setCookie("news",param.must);//设置cookie
			$(".news_like .a1").addClass("active").html("搜索结果").siblings(".an").removeClass("active");
			$(".r_listbox .news_like .an").css({"background-color":"#fff","border":"1px solid #999","color":"#999","z-index":"0"});			
			$(".r_listbox .news_like .a1").css({"color":"#19a97b","z-index":"0","border":"1px solid #999"});
			$(".r_listbox .news_like .a1").css({"border":"1px solid #fff"});
			$(".r_listbox .news_like .lan,.r_listbox .news_like .n_list").css("display","none");
			$(".r_listbox .news_like .an1 ,.r_listbox .news_like .n_list1").fadeIn();
			$(".r_listbox .news_like .a1").css({"color":"#19a97b","z-index":"2","background-color":"#f4f9f7","border":"1px solid #19a97b"}).siblings("a").css({"z-index":"0","border-bottom":"1px solid #d2d2d2"});
			$(".news_like .a1").siblings(".bk").fadeIn(); 
		},
		failure:function(data){
		}
	});
}
/*
 * 点击赋值
 */
function historyClick(obj){
	var text = obj.getAttribute("text");
	
	var ascondition_all=ZU.getParam("ascondition_all",text);
	var ascondition_any=ZU.getParam("ascondition_any",text);
	var ascondition_nocontains =ZU.getParam("ascondition_nocontains",text);
	
	var ascondition_stime=ZU.getParam("ascondition_stime",text);
	var ascondition_etime=ZU.getParam("ascondition_etime",text);
	var ascondition_part=ZU.getParam("ascondition_part",text);
	var ascondition_sort=ZU.getParam("ascondition_sort",text);
	
	document.getElementById("ascondition_all").value=ascondition_all;
	document.getElementById("ascondition_any").value=ascondition_any;
	document.getElementById("ascondition_nocontains").value=ascondition_nocontains;
	
	document.getElementById("time_end").value =ascondition_stime;
	document.getElementById("time_start").value =ascondition_etime;
	document.getElementById("ascondition_sort").options[ascondition_sort-1].selected=true;//排序方式 1:时间,2权重
	
	document.getElementById("ascondition_part_"+ascondition_part).checked=true;//范围 1全文 2标题
}
//设置搜索历史记录
function setSearchHistory(){
	var ascondition = new Object();
	ascondition['articleId'] = articleId;
	ascondition['ascondition_all'] = document.getElementById("ascondition_all").value;//所有
	ascondition['ascondition_any'] = document.getElementById("ascondition_any").value;//任意
	ascondition['ascondition_nocontains'] = document.getElementById("ascondition_nocontains").value;//不包含
	
	ascondition['ascondition_stime'] = document.getElementById("time_end").value;//开始时间
	ascondition['ascondition_etime'] = document.getElementById("time_start").value;//结束时间
	//范围 1全文 2标题
	if(document.getElementById("ascondition_part_2").checked){
		ascondition['ascondition_part']=2;//按标题
	}else {
		ascondition['ascondition_part']=1;//全文
	}
	ascondition['ascondition_sort'] = document.getElementById("ascondition_sort").value;//排序方式 1:时间,2权重
	
	ascondition['hitnumber'] = "1";
	ascondition['type'] = "advanced";
	
	saveConditions(ascondition);
}
function historyList(){
	$.ajax({
		  url: " /webword/document/searchAdvCondition.do?start=0&limit=5&type=advanced",
		  type: "post",
		  dataType: "json",
		  success: function(d){
			var str="";
			for(i=0;i<d.advConditionList.length;i++){
				var node = d.advConditionList[i];
				var text ="ascondition_all="+node.ascondition_all+"&ascondition_any="+node.ascondition_any;
				text+="&ascondition_nocontains="+node.ascondition_nocontains;
				if(node.ascondition_stime!=""&&node.ascondition_stime!=undefined){
					text+="&ascondition_stime="+node.ascondition_stime.split(" ")[0];
				}else{
					text+="&ascondition_stime=";
				}
				if(node.ascondition_etime!=""&&node.ascondition_etime!=undefined){
					text+="&ascondition_etime="+node.ascondition_etime.split(" ")[0];
				}else{
					text+="&ascondition_etime=";
				}
				text+="&ascondition_sort="+node.ascondition_sort+"&ascondition_part="+node.ascondition_part;
				//console.info("text = "+node.ascondition_stime.split(" ")[0]);
				str+="<li text='"+text+"' onclick='historyClick(this)'>";
				str+="("+node.ascondition_all+")-";
				str+="("+node.ascondition_any+")-";
				str+="("+node.ascondition_nocontains+")";
				str+="</li>";
			}
			$(".advanced_search ul").html(str);
			 /* for()
			 $(".advanced_search ul"). */
			  
		  }
});			
}
/*
 * 栏目点击
 * 新闻a1开始,文档b1,插件c1
 */
function columnClick(obj){
	//console.info("obj.innerHTML="+obj.innerHTML);
	//console.info(obj.className);
	var className = obj.className;
	if(className.indexOf("a2")>-1){//我的新闻
		getNewsMy(1);
	}else if(className.indexOf("a3")>-1){//猜你喜欢
		
	}else if(className.indexOf("b2")>-1){//我的文档
		getArticleMy(1);
	}else if(className.indexOf("c2")>-1){//我的插件
		//findPlugin(1);//更新插件
		getPluginMy(1);
	}else if(className.indexOf("c3")>-1){//当前插件
		findPlugin(1);
	}else if(className.indexOf("d2")>-1){//我的模板
		getTemplateMy(1);
	}
}
/*
 * 清除插件属性
 */
function pluginParamDisplay(){
	var endDate = document.getElementById("pluginEndDate");
	var industry = document.getElementById("pluginIndustryId");
	var provice = document.getElementById("pluginProvinceId");
	var industryName = document.getElementById("pluginIndustry");
	var proviceName = document.getElementById("pluginProvince");
	
	endDate.value="";
	industry.value = "";
	provice.value="";
	industryName.value="";
	proviceName.value ="";
	
	endDate.parentNode.style.display="none";
	industryName.parentNode.style.display = "none";
	proviceName.parentNode.style.display ="none";
}
//内容插件-显示
function tree_contentPlugin(){
	var  trId = zhuTree1.trClickId;
	var  hId = trId.split("_")[3];
	var contentPluginId = 0;
	if(hId.split("-").length>1){
		contentPluginId = hId.split("-")[1];
	}
	document.getElementById("contentpluginId").value = contentPluginId;
	document.getElementById("contentpluginTrId").value = trId;
	var url="article/getContentPlugin.do?contentpluginId="+contentPluginId;
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			if(data.status=="success"){
				contentPluginSetValue(data.contentPlugin);
				$('.contents_plugin input[placeholder]').each(function(index,element){
					if($(this).val().length == 0){		
						$(this).next(".ie_ph_text").show();
					}else{		
						$(this).next(".ie_ph_text").hide();
					}
				});
			}else{
				contentPluginRest();//重置数据
			}
			
		},
		failure:function(data){
		}
	});
	$(".contents_plugin,.box_content,.ui_back").fadeIn(100);
}
/*
 * 内容插件参数
 */
var contentPluginParam={
		contentpluginId:"0",//内容插件Id
		contentpluginArticleid:0,
		contentpluginNodeid:"0",
		contentpluginName:"",//插件名称
		contentpluginMust:"",
		contentpluginArbitrarily:"",
		contentpluginNot:"",
		contentpluginPagesize:"10",//记录条数 10
		contentpluginSearchsource:"2",//1:全文 2:标题
		contentpluginSort:"2",//排序方式 1:时间 2:焦点
		contentpluginStarttime:"",//开始时间
		contentpluginEndtime:"",//结束时间
		contentpluginRepeatTime:"1",//执行周期
		contentpluginRepeatFlag:"0",//1:自动执行，0，不自行执行
		contentpluginFromsource:"",//新闻源
		contentpluginTrId:0,//目录树的id
		strType:""//risk:风险信息库
};
/*
 * 内容插件搜索
 */
function contentPluginSetUrl(){
	var PlugTime1=$('.contents_plugin #time_start1').val();
	var PlugTime2=$('.contents_plugin #time_end1').val();
	if(PlugTime1==''&&PlugTime2==''){
		$(".time_null,.box_content,.ui_back").fadeIn(100);
		return false;
	}
	if(userPerm()){
		return false;
	}
	contentPluginParam.contentpluginTrId = document.getElementById("contentpluginTrId").value;
	contentPluginParam.contentpluginId = document.getElementById("contentpluginId").value;
	contentPluginParam.contentpluginArticleid = articleId;
	contentPluginParam.contentpluginName = document.getElementById("contentpluginName").value;
	contentPluginParam.contentpluginMust = document.getElementById("contentpluginMust").value;
	contentPluginParam.contentpluginArbitrarily = document.getElementById("contentpluginArbitrarily").value;
	contentPluginParam.contentpluginNot = document.getElementById("contentpluginNot").value;
	
	if(contentPluginParam.contentpluginName==""){
		msgShow("填写插件名称");
		return false;
	}
	
	var contentpluginSearchsource= document.getElementById("contentpluginSearchsource_1");//全文：1,焦点：2
	if(contentpluginSearchsource.checked){
		contentPluginParam.contentpluginSearchsource=1;//全文
	}else{
		contentPluginParam.contentpluginSearchsource=2;//焦点
	}
	
	contentPluginParam.contentpluginStarttime = document.getElementById("time_end1").value;
	contentPluginParam.contentpluginEndtime = document.getElementById("time_start1").value;
	
	contentPluginParam.contentpluginRepeattime = document.getElementById("contentpluginRepeattime").value;
	var contentpluginRepeatflag = document.getElementById("contentpluginRepeatflag_1");//radio 单选 0:否，1是
	if(contentpluginRepeatflag.checked){
		contentPluginParam.contentpluginRepeatflag=1;
	}else{
		contentPluginParam.contentpluginRepeatflag=0;
	}
	
	contentPluginParam.contentpluginSort = document.getElementById("contentpluginSort").value;//select 1:时间 2:焦点
	contentPluginParam.contentpluginPagesize = document.getElementById("contentpluginPagesize").value;//select 1,5,10,
	contentPluginParam.contentpluginFromsource = document.getElementById("contentpluginFromSource").value;
	
	if(contentPluginParam.contentpluginMust==""&&contentPluginParam.contentpluginArbitrarily==""&&contentPluginParam.contentpluginNot==""&&contentPluginParam.contentpluginFromsource==""){
		msgShow("请输入搜索关键字！");
		return false;
	}
	if(ZU.$id("strTypeCheckbox1").checked){//搜索风险信息库
		contentPluginParam.strType = "risk";
	}else{
		contentPluginParam.strType = "";
	}
	G_lb = new loading_box({title:'内容插件执行中',text:'请等待'});
	var url="article/setContentPlugin.do";
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"POST",
		data:contentPluginParam,
		success:function(data){
			//contentPluginSetValue(data.contentPlugin);
			if(data.status=="success"){
				if(data.newId!=0){//&&contentPluginParam.contentpluginId!=data.newId
					var trId = contentPluginParam.contentpluginTrId;
					addTreeAndContent(trId,contentPluginParam.contentpluginId,data.newId,data.nodes.list);//添加目录
				}
				$(".contents_plugin,.box_content,.ui_back").fadeOut(100);
				doSaveArticle();//内容插件 保存内容
				G_lb.close();
				msgShow("成功加载"+data.listSize+"条信息");
			}else{
				G_lb.close();
				msgShow(data.info);
			}
		},
		failure:function(data){
			G_lb.close();
			msgShow("执行失败");
		}
	});
}
/*
 * 添加内容
 * trId
 * newId 新节点ID
 * list 数据列表
 * contentpluginId:来的内容id
 */
function addTreeAndContent(trId,contentpluginId,newId,list){
	var trNode = document.getElementById(trId);
	var hId = trId.split("_")[3];
	if(trNode==null) return false;//没有值
	var hOrder = ZU.getSplitOrder(trNode)+1;
	if(hOrder>6) hOrder=6;
	//tr_0_1_1148677597-289
	//span_1148677597-289
	if(contentpluginId!=newId){//新的id不等于
		//console.info("新的ID不等于旧的");
		if(contentpluginId!=0){//旧的ID存在
			trNode.id = trNode.id.replace("-"+contentpluginId,"-"+newId);
			
			var spanNode = trNode.getElementsByTagName("span")[0];
			spanNode.id = spanNode.id.replace("-"+contentpluginId,"-"+newId);
			
			var img =ZU.getEndImg(trNode);
			img.className ="tree_plugin";
			
			var hNode = iframeObj.contentWindow.document.getElementById(hId);
			if(hNode==null) return false;
			hNode.id=hNode.id.replace("-"+contentpluginId,"-"+newId);
		}else{//旧的不存在 就是等于0
			trNode.id = trNode.id.replace(hId,hId+"-"+newId);
			
			var spanNode = trNode.getElementsByTagName("span")[0];
			spanNode.id = spanNode.id.replace(hId,hId+"-"+newId);
			
			var img =ZU.getEndImg(trNode);
			img.className ="tree_plugin";
			
			var hNode = iframeObj.contentWindow.document.getElementById(hId);
			if(hNode==null) return false;
			hNode.id=hNode.id+"-"+newId;
		}
	}
	//添加插件 节点
	if(list.length==0) return false;
	var trNextNode = trNode.nextSibling;
	ue.fireEvent('saveScene');
	for(var i=0;i<list.length;i++){
		var node = {};
		node.idStr = ZU.getRandom();
		node.text = list[i].crawlTitle;
		node.content = list[i].text;
		node.order = hOrder;
		iframeObj.contentWindow.dragAddNode(node,trNextNode);
	}
	ue.fireEvent('saveScene');
	updateSectionsF();//整体更新大纲
}
/*
 * 删除数据
 */
function delTreeAndContent(trId,newId){
	var trNode = document.getElementById(trId);
	var hId = trId.split("_")[3];
	var newHId = hId.split("-")[0];
	if(trNode==null) return false;//没有值
	//tr_0_1_1148677597-289
	//span_1148677597-289
	trNode.id = trNode.id.replace(hId,newHId);
	
	var spanNode = trNode.getElementsByTagName("span")[0];
	spanNode.id = spanNode.id.replace(hId,newHId);
	
	var img =ZU.getEndImg(trNode);
	img.className ="normal";
	
	var hNode = iframeObj.contentWindow.document.getElementById(hId);
	if(hNode==null) return false;
	ue.fireEvent('saveScene');
	hNode.id=newHId;
	ue.fireEvent('saveScene');
}
/*
 * 内荣插件删除
 */
function contentPluginDel(){
	if(userPerm()){
		return false;
	}
	var contentpluginId=document.getElementById("contentpluginId").value;
	if(contentpluginId==0){
		return false;
	}
	var url="article/delContentPlugin.do?contentpluginId="+contentpluginId;
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			//contentPluginSetValue(data.contentPlugin);
			if(data.status=="success"){
				var trId = document.getElementById("contentpluginTrId").value;
				delTreeAndContent(trId,contentpluginId);
				$(".contents_plugin,.box_content,.ui_back").fadeOut(100);
				doSaveArticle();//内容插件删除
				msgShow("删除成功");
			}
		},
		failure:function(data){
		}
	});
}

/*
 * 内动插件 设置值 显示的时候调用
 * contentPlugin
 */
function contentPluginSetValue(contentPlugin){
	var contentpluginName = document.getElementById("contentpluginName");
	var contentpluginMust = document.getElementById("contentpluginMust");
	var contentpluginArbitrarily = document.getElementById("contentpluginArbitrarily");
	var contentpluginNot = document.getElementById("contentpluginNot");
	
	var time_start1 = document.getElementById("time_end1");
	var time_end1 = document.getElementById("time_start1");
	
	var contentpluginRepeattime = document.getElementById("contentpluginRepeattime");
	
	var contentpluginSort = document.getElementById("contentpluginSort");//select 1:时间 2:焦点
	var contentpluginPagesize = document.getElementById("contentpluginPagesize");//select 1,5,10,
	var contentpluginFromSource = document.getElementById("contentpluginFromSource");
	
	var contentpluginId = document.getElementById("contentpluginId");
	
	contentpluginName.value = contentPlugin.contentpluginName;
	contentpluginMust.value = contentPlugin.contentpluginMust;
	contentpluginArbitrarily.value =contentPlugin.contentpluginArbitrarily;
	contentpluginNot.value =contentPlugin.contentpluginNot;
	
	var contentpluginSearchsource= document.getElementById("contentpluginSearchsource_"+contentPlugin.contentpluginSearchsource);//全文：1,焦点：2
	if(contentpluginSearchsource!=null){
		contentpluginSearchsource.checked=true;
	}
	time_start1.value = ZU.stampToTimeStr(contentPlugin.contentpluginStarttime).split(" ")[0];;
	time_end1.value = ZU.stampToTimeStr(contentPlugin.contentpluginEndtime).split(" ")[0];
	//是否自动执行
	var contentpluginRepeatflag = document.getElementById("contentpluginRepeatflag_"+contentPlugin.contentpluginRepeatflag);//radio 1:自动  0:不自行
	if(contentpluginRepeatflag!=null){
		contentpluginRepeatflag.checked =true;//默认自动执行
	}
	contentpluginRepeattime.value= contentPlugin.contentpluginRepeattime;
	
	contentpluginSort.options[contentPlugin.contentpluginSort-1].selected=true;//默认焦点排序 1:时间 2:焦点
	
	var pageSizeParam={
			"1":0,
			"5":1,
			"10":2,
			"15":3,
			"20":4,
			"25":5,
			"30":6
	};
	contentpluginPagesize.options[pageSizeParam[contentPlugin.contentpluginPagesize]].selected=true;//默认是10
	
	contentpluginFromSource.value = contentPlugin.contentpluginFromsource;
	
	if(contentPlugin.strType=="risk"){
		ZU.$id("strTypeCheckbox1").checked =true;
	}else{
		ZU.$id("strTypeCheckbox1").checked =false;
	}
	
}
//内容插件 重置
function contentPluginRest(){
	var contentpluginName = document.getElementById("contentpluginName");
	var contentpluginMust = document.getElementById("contentpluginMust");
	var contentpluginArbitrarily = document.getElementById("contentpluginArbitrarily");
	var contentpluginNot = document.getElementById("contentpluginNot");
	
	var contentpluginSearchsource= document.getElementById("contentpluginSearchsource_1");//全文：1,焦点：2
	
	var time_start1 = document.getElementById("time_end1");
	var time_end1 = document.getElementById("time_start1");
	
	var contentpluginRepeattime = document.getElementById("contentpluginRepeattime");
	var contentpluginRepeatflag = document.getElementById("contentpluginRepeatflag_1");//radio 单选
	
	var contentpluginSort = document.getElementById("contentpluginSort");//select 1:时间 2:焦点
	var contentpluginPagesize = document.getElementById("contentpluginPagesize");//select 1,5,10,
	var contentpluginFromSource = document.getElementById("contentpluginFromSource");
	
	var contentpluginId = document.getElementById("contentpluginId");
	
	contentpluginName.value = "";
	contentpluginMust.value = "";
	contentpluginArbitrarily.value ="";
	contentpluginNot.value ="";
	
	contentpluginSearchsource.checked=true;
	
	contentpluginRepeatflag.checked =true;//默认自动执行
	contentpluginSort.options[1].selected=true;//默认焦点排序
	contentpluginPagesize.options[2].selected=true;//默认是10
	contentpluginFromSource.value = "";	
	ZU.$id("strTypeCheckbox1").checked =false;
}
/*
 * flag 默认flase 弹框,true 不弹框
 * 返回 true 没有操作权限。
 */
function userPerm(flag){
	if(G_passType=="SUBMITTED"){
		if(!flag){
			msgShow("审核中,不允许操作");
		}
		return true;
	}else if(G_passType=="PASSED"){
		if(!flag){
			msgShow("审核完成,不允许操作");
		}
		return true;
	}else if(G_articleUserId!=userId){
		if(!flag){
			msgShow("没有权限操作此文档");
		}
		return true;
	}
	return false;
}
//测试标签是否 存在标签 true:存在标签
function testLable(str,text){
	//var regExp = new RegExp("\\s+人民\\s+", 'i');
	//console.info("str="+str+",text="+text);
	var regExp = new RegExp("(\\s+"+text+"\\s+)|(^"+text+"\\s+)|((\\s+"+text+"$))|(^"+text+"$)", 'i');
	return regExp.test(str);
}
//接收键盘
function articleLabelKeydown(e,obj){
	if(e!=null){
		var val = e.which | e.keyCode;
	}
	var str=obj.value;
	var hode = ZU.$id("articleLabelH4");
	var emList = hode.getElementsByTagName("em");
	for(var i=0;i<emList.length;i++){
		var node= emList[i];
		//console.info(node.innerHTML);
		if(testLable(str,node.innerHTML)){//存在值
			//console.info("设置");
			node.className="disabled";
		}else{
			node.className="";
		}
	}
}
//显示标签
function showLabel(){
	var hNode = ZU.$id("articleLabelH4");
	if(hNode.innerHTML==""){
		var str='<em onclick="labelClick(this)">银行</em><em onclick="labelClick(this)">中小企业</em><em onclick="labelClick(this)">投资银行</em><em onclick="labelClick(this)">金融</em><em onclick="labelClick(this)">财经</em>';
		hNode.innerHTML=str;
	}
}
//点击赋值
function labelClick(obj){
	if(obj.className=="disabled"){
		return false;
	}
	var articleLabelText=ZU.$id("articleLabelText_1");
	articleLabelText.value+=" "+obj.innerHTML;
	obj.className="disabled";
}
/**
 * 删除状态
 */
function buttonDel(){
	if(G_articleType=="template"){//是模板的话,删除下载
		var liButtonDown=ZU.$id("liButtonDown");
		liButtonDown.className = "grey";
		liButtonDown.removeAttribute("onclick");
		var newArticleImg = ZU.$id("newArticleImg");
		newArticleImg.src= "word/img/cbbr_tip2.png";
	}
	if(G_passType=="SUBMITTED"||G_passType=="PASSED"){//审核状态
		var liButtonSave=ZU.$id("liButtonSave");
		liButtonSave.className = "grey";
		liButtonSave.removeAttribute("onclick");
		var liButtonSubmit = ZU.$id("liButtonSubmit");
		liButtonSubmit.className = "grey";
		liButtonSubmit.removeAttribute("onclick");
	}
	//设置提交审核关键词
	ZU.$id("brief_1").value=G_articleSkip;
	ZU.$id("articleLabelText_1").value=G_articleKeyWord;
	ZU.$id("keyword_1").value=G_articleLabel;
	

}
//重命名
function replaceNewNameClick(){
	if(userPerm()){
		return false;
	}
	var articleName = $("#replaceNewName").val();
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
			if(data.status=="success"){
				$(".sbox_save_new,.box_content,.ui_back").fadeOut(100);
				$("#newarticlename").text(articleName);
				G_articleName=articleName;
				}
			else{
				$(".sbox_save_new div .tip").css({"display":'block'});
				$(".sbox_save_new div .tip").html(data.info);
				//setTimeout(function(){$(".sbox_save div .tip").fadeOut(100);},1500);
			}
		},
		failure:function(data){
			//$(".sbox_save,.box_content,.ui_back").fadeOut(100);
			$(".sbox_save_new div .tip").css({"display":'block'});
			$(".sbox_save_new div .tip").html("保存失败");
		}
	});
}
/*
 * 关闭自己窗口
 */
function closeSeft(){
	//改成跳转到个人中心
	window.location.href = "/webword/user/article.do";
	//open(location, '_self').close();
}









