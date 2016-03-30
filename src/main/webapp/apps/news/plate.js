/**
 * 栏目管理
 */
MENU={};
PID=0;
window.onload=function(){
	pageload();//右边菜单
};
//页面加载
function pageload(){
	getUserWordPlate();//生成二级的目录树
	MENU = new menuTree("treeUl");//全局属性
}

/*
 * 生成目录树
 */
function getUserWordPlate(){
	var url="news/column/getUserNewsMyPlate.do";
	$.ajax({
		url:url,
		type:"get",
		success:function(data){
			var json=data.list;
			MENU.setJson(json);
			MENU.show();
			treeDisplay(0);//显示栏目
		},
		failure:function(data){
			msgShow("目录树失败");
		}
	});
}

/**
 * 点击添加弹出框
 */
function showPlatAdd(){
	showCre_Channel();
}

/**
 * 添加栏目
 */
function plateAdd(plateName,pid,conditions){
	var url="news/column/plateAdd.do";
	$.ajax({
		url:url,
		type:'POST',
		data:{
			"plateName":plateName,
			"pid":pid,
			"conditions":conditions
		},
		success:function(data){
			if(data.status=="success"){
				var wordPlate = data.wordPlate;
				//提价元素
				var plateName=wordPlate.plateName;
				if(plateName.length>8){
					plateName=plateName.substring(0,8)+"...";
				}
				var plateId = wordPlate.plateId;
				var liNode = document.createElement("li");
				liNode.id = "display_"+wordPlate.plateId;
				liNode.className = "li_sty";
				liNode.setAttribute("onmousedown","pmousedown(event,this)");
				//栏目图片
				var picPath = wordPlate.picPath == null ? "word/img/banner5.png" : "upload/"+wordPlate.picPath;
				var str="";
	        	    str+='<img src="'+picPath+'" />';
		        	str+='<div><p title="'+wordPlate.plateName+'">'+plateName.substring(0,6)+'<span class="span1" onclick="plateShow('+plateId+')"><em></em></span></p></div>';
		        	str+='<span onclick="plateDel(event,this,'+plateId+')" class="span2"></span>';
		        
				liNode.innerHTML=str;
				ZU.$id("displayUl").appendChild(liNode);
				//添加到目录树
				if(wordPlate.pid==0){//添加到一级
					var liNode = document.createElement("li");
					liNode.id="tree_li_"+wordPlate.plateId;
					liNode.className = "m1_li";
					str='<h2><em></em>';
					//2016-03-03只支持一级栏目
	                //str+='<a href="javascript:treeDisplay('+wordPlate.plateId+');" title="编辑">'+plateName+'</a></h2>';
					str+='<a title="'+wordPlate.plateName+'">'+plateName+'</a></h2>';
					liNode.innerHTML=str;
					ZU.$id('treeUl').appendChild(liNode);
					if(liNode.nextSibling==null){
						var ulNode = document.createElement("ul");
						ulNode.className = "m1_ul";
						ulNode.id="tree_ul_"+wordPlate.plateId;
						ulNode.style.cssText = "display:";
						ZU.$id('treeUl').appendChild(ulNode);
					}
				}else{//添加到二级
					var liNode = document.createElement("li");
					liNode.className = "m2_li";
					liNode.id="tree_li_"+wordPlate.plateId;
					liNode.innerHTML=plateName;
					ZU.$id('tree_ul_'+wordPlate.pid).appendChild(liNode);
				}
				closeDiv();
				//刷新推荐栏目的状态
				getHotColumnStatus();
			}
		}
	});
}

/**
 * 添加推荐的栏目
 */
function plateAddHot(columnId){

	if (columnId == null){
		columnId = ZU.$id("columnId2").value;
	}
	var url="news/column/plateAddHot.do";
	$.ajax({
		url:url,
		type:"post",
		async:false,
		data:{
			"columnId":columnId,
			"pid":PID
		},
		success:function(data){
			if(data.status=="success"){
				var wordPlate = data.wordPlate;
				//提价元素
				var plateName=wordPlate.plateName;
				if(plateName.length>8){
					plateName=plateName.substring(0,8)+"...";
				}
				var plateId = wordPlate.plateId;
				var columnId = wordPlate.columnId;
				var liNode = document.createElement("li");
				liNode.id = "display_"+wordPlate.plateId;
				liNode.className = "li_sty";
				liNode.setAttribute("onmousedown","pmousedown(event,this)");
				//栏目图片
				var picPath = wordPlate.picPath;
				var str="";
		        	str+='<img src="upload/'+picPath+'" />';
		        	str+='<div><p title="'+wordPlate.plateName+'">'+plateName.substring(0,6)+'<span class="span1" onclick="plateShow('+plateId+')"><em></em></span></p></div>';
		        	str+='<span onclick="plateDel(event,this,'+plateId+')" class="span2"></span>';
		        
				liNode.innerHTML=str;
				ZU.$id("displayUl").appendChild(liNode);
				//添加到目录树
				if(wordPlate.pid==0){//添加到一级
					var liNode = document.createElement("li");
					liNode.id="tree_li_"+wordPlate.plateId;
					liNode.className = "m1_li";
					str='<h2><em></em>';
					//2016-03-03只支持一级栏目
					//str+='<a href="javascript:treeDisplay('+wordPlate.plateId+');" title="编辑">'+plateName+'</a></h2>';
					str+='<a title="'+wordPlate.plateName+'">'+plateName+'</a></h2>';
					liNode.innerHTML=str;
					ZU.$id('treeUl').appendChild(liNode);
					if(liNode.nextSibling==null){
						var ulNode = document.createElement("ul");
						ulNode.className = "m1_ul";
						ulNode.id="tree_ul_"+wordPlate.plateId;
						ulNode.style.cssText = "display:";
						ZU.$id('treeUl').appendChild(ulNode);
					}
				}else{//添加到二级
					var liNode = document.createElement("li");
					liNode.className = "m2_li";
					liNode.id="tree_li_"+wordPlate.plateId;
					liNode.innerHTML=plateName;
					ZU.$id('tree_ul_'+wordPlate.pid).appendChild(liNode);
				}
				closeDiv();
				$("#addFlag").val(1);
				//刷新推荐栏目的状态
				getHotColumnStatus();
			}
		}
	});
}

/*
 * 删除栏目
 */
function plateDelURL(e,obj,plateId){
	//plateDelUrl(plateId);
	//console.info("删除栏目"+plateId);
	plateDelUrl(plateId);
}
/**
 * 删除栏目url
 */
function plateDel(e,obj,plateId){
	var url="news/column/plateDel.do?plateId="+plateId;
	$.ajax({
		url:url,
		type:"get",
		success:function(data){	
			if(data.status=="success"){
				var liNode = ZU.$id("tree_li_"+plateId);
				var uiNode = ZU.$id("tree_ul_"+plateId);
				var displayNode = ZU.$id("display_"+plateId);
				
				ZU.removeChild(liNode);
				ZU.removeChild(uiNode);
				ZU.removeChild(displayNode);
				//刷新推荐栏目的状态
				getHotColumnStatus();
			}
		}
	});
}

/**
 * 取消订阅
 */
function plateDelHot(){
	var columnId = ZU.$id("columnId2").value;
	var url="news/column/plateDelHot.do?columnId="+columnId;
	$.ajax({
		url:url,
		type:"get",
		success:function(data){	
			if(data.status=="success"){
				var plateId = data.plateId;
				var liNode = ZU.$id("tree_li_"+plateId);
				var uiNode = ZU.$id("tree_ul_"+plateId);
				var displayNode = ZU.$id("display_"+plateId);
				
				ZU.removeChild(liNode);
				ZU.removeChild(uiNode);
				ZU.removeChild(displayNode);
			}
			//刷新推荐栏目的状态
			getHotColumnStatus();
		}
	});
	$('.ui_back2,.ui_back,.box_content,.modifi_channel,.previ_channel').hide();
}
/*
 * 栏目排序
 */
function plateOrder(){
	
}

function updateOrder(firstId,location,loactionId){
	var url="news/column/updateOrder.do";
	var param = {
			firstId:firstId,
			location:location,
			loactionId:loactionId
	};
	$.ajax({
		url:url,
		type:"get",
		data:param,
		success:function(data){
			if(data.status=="success"){
				var firstLiNode=ZU.$id("tree_li_"+firstId);
				var firstUlNode=ZU.$id("tree_ul_"+firstId);
				var loactionLi = ZU.$id("tree_li_"+loactionId);
				var loactionUl = ZU.$id("tree_ul_"+loactionId);
				if(loactionUl!=null){//一级栏目增加
					if(location=="before"){
						loactionLi.parentNode.insertBefore(firstLiNode,loactionLi);
						loactionLi.parentNode.insertBefore(firstUlNode,loactionLi);
					}else{
						if(loactionUl.nextSibling!=null){
							var nextNode = loactionUl.nextSibling;
							loactionLi.parentNode.insertBefore(firstLiNode,nextNode);
							loactionLi.parentNode.insertBefore(firstUlNode,nextNode);
						}else{
							loactionLi.parentNode.appendChild(firstLiNode);
							loactionLi.parentNode.appendChild(firstUlNode);
						}
					}
				}else{//二级栏目增加
					if(location=="before"){
						loactionLi.parentNode.insertBefore(firstLiNode,loactionLi);
					}else{//after
						if(loactionLi.nextSibling!=null){
							loactionLi.parentNode.insertBefore(firstLiNode,loactionLi.nextSibling);
						}else{
							loactionLi.parentNode.appendChild(firstLiNode);
						}
					}
				}
			}
		}
	});
}
/**
 * 生成目录树
 * @param name 生成div的名称
 */
function menuTree(name){
	var me = this;
	this.name = name;//生成目录树的div
	this.treeDiv;//目录树
	this.addMap ={};//保存打开关闭
	this.setJson=function (json){
		this.json=json;//设置json数据
	};
	this.show = function() {
		var json = this.json;
		var treeDiv = document.getElementById(this.name);//目录树的div
		//var oFragment=document.createDocumentFragment();
		var str='';//生成html 字符串
		for(var i=0;i<this.json.length;i++){
			var node = json[i];
			var plateName=node.plateName;
			if(plateName.length>8){
				plateName=plateName.substring(0,8)+"...";
			}
			//2016-03-03只支持一级栏目
			//str+='<li class="m1_li" id="tree_li_'+node.plateId+'"><h2><em class="icon1" onclick="emclick(this)"></em><a href="javascript:treeDisplay('+node.plateId+');" title="'+node.plateName+'">'+plateName+'</a></h2></li>';
			str+='<li class="m1_li" id="tree_li_'+node.plateId+'" pic="'+node.picPath+'" plname="'+node.plateName+'"><h2><em></em><a title="'+node.plateName+'">'+plateName+'</a></h2></li>';
			str+='<ul class="m1_ul" id="tree_ul_'+node.plateId+'" pic="'+node.picPath+'" plname="'+node.plateName+'" style="display:">';    
			if(node.subs.length>0){//存在子元素 current
				
				for(var j=0;j<node.subs.length;j++){
					var subNode = node.subs[j];
					var subPlateName=subNode.plateName;
					if(subPlateName.length>8){
						subPlateName=subPlateName.substring(0,8)+"...";
					}
					str+='<li class="m2_li"  id="tree_li_'+subNode.plateId+'" title="'+subNode.plateName+'">'+subPlateName+'</li>';
				}
			}
			str+='</ul>';
		}
		str+="";
		treeDiv.innerHTML=str;
		//treeDiv.appendChild(oFragment);
	};
	/**
	 * 新建节点
	 * node.id
	 * node.name
	 */
	this.createTrNode = function(node){
		var liNode = document.createElement("li");
		liNode.id=node.plateId;
		liNode.innerHTML=node.plateName;
		return liNode;
	};
}
/**
 * 点击折叠
 * @param obj
 */
function emclick(obj){
	if(obj.className=="icon1"){//加号
		var nextNode =obj.parentNode.parentNode.nextSibling;
		obj.className="icon2";
		if(nextNode!= null&&nextNode.nodeName=="UL"){
			nextNode.style.display="block";	
		}
	}else{
		var nextNode =obj.parentNode.parentNode.nextSibling;
		obj.className="icon1";
		if(nextNode!= null&&nextNode.nodeName=="UL"){
			nextNode.style.display="";
			
		}
	}
}
/**
 * 显示栏目
 */
function treeDisplay(id){
	var json=MENU.json;
	var nodeList = [];
	PID=id;
	if(id==0){//我的栏目,显示一级栏目
		ZU.$id("treePath").innerHTML="";
		nodeList = ZU.$id("treeUl").childNodes;
		ZU.$id("myMenuH").className="current";
		currentDel();
	}else{
		var node = ZU.$id("tree_li_"+id);
		if(node!=null){
			var aNodeList = node.getElementsByTagName("a");
			if(aNodeList.length>0){
				ZU.$id("treePath").innerHTML=aNodeList[0].innerHTML;
			}
			nodeList= node.nextSibling.childNodes;
			emclick(node.getElementsByTagName("em")[0]);
		}
		currentDel();
		node.className="current";
		ZU.$id("myMenuH").className="";
	}
	//遍历栏目
	var str="";
	for(var i=0;i<nodeList.length;i++){
		var node = nodeList[i];
		if(node.nodeName!='LI') continue;
		var plateId= node.id.split("_")[2];
		var aList = node.getElementsByTagName("a");
		var pic =$("#"+node.id+"").attr('pic');
		var picPath = (pic == "undefined" ? "word/img/banner5.png" : "upload/"+pic);
//		var plateName=node.innerHTML;
		var plateName=$("#"+node.id+"").attr('plname');
//		if(aList.length>0){
//			plateName = aList[0].innerHTML;
//		}
        str+='<li id="display_'+plateId+'" onmousedown="pmousedown(event,this)" class="li_sty">';
        str+='<img src="'+picPath+'"/>';
        str+='<div><p title="'+plateName+'">'+plateName.substring(0,6)+'<span class="span1" onclick="plateShow('+plateId+')"><em></em></span></p></div>';
        str+='<span onclick="plateDel(event,this,'+plateId+')" class="span2"></span>';
        str+='</li>';
	}
	ZU.$id("displayUl").innerHTML=str;
}

function plateShow(plateId){
	buttonClear();
	if(plateId==0){//添加
		G_plateId=0;
		buttonClear();
		showDiv(1);
	}else{//修改栏目
		plateGet(plateId);
	}	
}

/**
 * 获取栏目
 */
function plateGet(plateId){
	var url="news/column/plateGet.do?plateId="+plateId;
	$.ajax({
		url:url,
		type:"get",
		success:function(data){
			if(data.status=="success"){
				buttonClear();
				showCre_Channel();
				buttonSet(data.wordPlate);	
			}
		}
	});
}
/**
 * 修改栏目
 */
function plateEdit(plateId,plateName,conditions){
	var url="news/column/plateEdit.do";
	$.ajax({
		url:url,
		type:"post",
		data:{
			"plateName":plateName,
			"plateId":plateId,
			"conditions":conditions
		},
		success:function(data){
			if(data.status=="success"){
				closeDiv();//关闭窗口
				//修改栏目成功
				var plateId = data.wordPlate.plateId;
				var plateName = data.wordPlate.plateName;
				var liNode = ZU.$id("display_"+plateId);
				var pNode = liNode.getElementsByTagName("p")[0];
				pNode.title = plateName;
				pNode.innerHTML=plateName.substring(0,6)+'<span class="span1" onclick="plateShow('+plateId+')"><em></em></span>';
				//修改目录树
				liNode=ZU.$id("tree_li_"+plateId);
				var aList = liNode.getElementsByTagName("a");
				if(aList.length>0){
					aList[0].title = plateName;
					if (plateName.length > 8){
						plateName = plateName.substring(0,8)+"...";
					}
					aList[0].innerHTML= plateName;
				}else{
					liNode.title = plateName;
					if (plateName.length > 8){
						plateName = plateName.substring(0,8)+"...";
					}
					liNode.innerHTML=plateName;
				}	
			}
		}
	});
}
/*
 * 弹出框提交
 */
function plateButton(){
	var plateName=spaceTo_(ZU.$id("plateName").value);
	var plateId = ZU.$id("plateId").value;
	if(plateName=="") {
		msgShow("输入栏目名字");
		return false;
	}
	var mustTagNames=spaceTo_(ZU.$id("mustTagNames").value);
	var mustNotTagNames=spaceTo_(ZU.$id("mustNotTagNames").value);
	var shouldTagNames=spaceTo_(ZU.$id("shouldTagNames").value);
	var mustWordNames=spaceTo_(ZU.$id("mustWordNames").value);
	var mustNotWordNames=spaceTo_(ZU.$id("mustNotWordNames").value);
	var shouldWordNames=spaceTo_(ZU.$id("shouldWordNames").value);
	var selectSources=ZU.$id("select_sources").value;
	var flag = 0;
	if(ZU.$id("word_tit").checked){
		flag=1;
	}
	if(plateName.length>18){
		msgShow("栏目名称不大于18个字");
		return false;
	}
	if(mustTagNames==""&&mustNotTagNames==""&&shouldTagNames==""&&mustWordNames==""&&mustNotWordNames==""&&shouldWordNames==""){
		msgShow("填写条件");
		return false;
	}
	var conditions={
		"mustTagNames":mustTagNames,
		"mustNotTagNames":mustNotTagNames,
		"shouldTagNames":shouldTagNames,
		"mustWordNames":mustWordNames,
		"mustNotWordNames":mustNotWordNames,
		"shouldWordNames":shouldWordNames,
		"flag":flag,
		"source":selectSources
	};
	conditions= JSON.stringify(conditions).replace(/null/g,"");
	if(plateId==0){//增加
		plateAdd(plateName,PID,conditions);
	}else{//修改
		plateEdit(plateId,plateName,conditions);
	}
}
//清空输入框
function buttonClear(){
	ZU.$id("plateId").value=0;
	ZU.$id("plateName").value="";
	ZU.$id("mustTagNames").value="";
	ZU.$id("mustNotTagNames").value="";
	ZU.$id("shouldTagNames").value="";
	ZU.$id("mustWordNames").value="";
	ZU.$id("mustNotWordNames").value="";
	ZU.$id("shouldWordNames").value="";
	ZU.$id("select_sources").value="";
	ZU.$id("word_tit").checked="checked";
}
//设置值
function buttonSet(plate){
	if(plate==null) return false;
	ZU.$id("plateId").value=plate.plateId;
	ZU.$id("plateName").value=plate.plateName;
	var conditions=eval('(' + plate.conditions + ')');
	ZU.$id("mustTagNames").value=_Tospace(conditions.mustTagNames);
	ZU.$id("mustNotTagNames").value=_Tospace(conditions.mustNotTagNames);
	ZU.$id("shouldTagNames").value=_Tospace(conditions.shouldTagNames);
	ZU.$id("mustWordNames").value=_Tospace(conditions.mustWordNames);
	ZU.$id("mustNotWordNames").value=_Tospace(conditions.mustNotWordNames);
	ZU.$id("shouldWordNames").value=_Tospace(conditions.shouldWordNames);
	if(conditions.flag==1){
		ZU.$id("word_tit").checked="checked";
	}else{
		ZU.$id("word_con").checked="checked";
	}
	ZU.$id("select_sources").value=conditions.source;
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
//替换空-
function _Tospace(str){
	return str.replace(/_/g," ");	
}
//删除选中的状态
function currentDel(){
	var nodeList = ZU.$id("treeUl").childNodes;
	for(var i=0;i<nodeList.length;i++){
		var node = nodeList[i];
		if(node.nodeName!="LI") continue;
		if(node.className.indexOf("current")>-1){
			node.className=node.className.replace('current',"m1_li");
		}
	}
	
}
	
//显示提示框
function msgShow(msg){
	new altInfo({
		text:msg
	});
}


//获取推荐的栏目详情
function getColumn(columnId,addFlag){

	if (addFlag == null){
		addFlag = $("#addFlag").val();
	}
	if (addFlag == 1){
		$("#addFlag").val(1);
	}else{
		$("#addFlag").val(0);
	}
	$('.ui_back2,.ui_back,.box_content,.modifi_channel').show();
	var url="news/column/getColumn.do?columnId="+columnId;
	$.ajax({
		url:url,
		type:"post",
		success:function(data){
			
			$("#columnId2").val(data.wordColumn.id);
			$("#columnName2").html(data.wordColumn.columnName);
			var conditions = eval('(' + data.wordColumn.conditions + ')');
			$("#mustTagNames2").html(conditions.mustTagNames);
			$("#shouldTagNames2").html(conditions.shouldTagNames);
			$("#mustNotTagNames2").html(conditions.mustNotTagNames);
			
			$("#mustWordNames2").html(conditions.mustWordNames);
			$("#shouldWordNames2").html(conditions.shouldWordNames);
			$("#mustNotWordNames2").html(conditions.mustNotWordNames);	
			
			$("#mustTagNames2").attr("title",conditions.mustTagNames);
			$("#shouldTagNames2").attr("title",conditions.shouldTagNames);
			$("#mustNotTagNames2").attr("title",conditions.mustNotTagNames);
			$("#mustWordNames2").attr("title",conditions.mustWordNames);
			$("#shouldWordNames2").attr("title",conditions.shouldWordNames);
			$("#mustNotWordNames2").attr("title",conditions.mustNotWordNames);
			
			
			if (conditions.flag != null && conditions.flag == 1){
				$("#wordFlag").html("新闻标题");					
			}else{
				$("#wordFlag").html("新闻全文");	
			}
			$("#select_sources2").html(conditions.source == "" ? "全部" : conditions.source);	
			
		},
		failure:function(data){
			msgShow("获取失败");
		}
	});
}

//动态获取推荐栏目的状态
function getHotColumnStatus(){
	
	$.ajax({
		type : "post",
		url : "news/column/getHotColumnStatus.do",
		async : false,
		dataType : "json",
		success : function(data) {
			$("#hotColumn").remove();
			var columnStatus = "<div class='clearfix lanmuTuijian' id='hotColumn'>";
			columnStatus += "<h3>栏目推荐<em></em></h3>";
			for (var i = 0;i < data.hotGroups.length;i ++){
				var columnsList1 = data.hotGroups[i].columns;
				for (var j = 0;j < columnsList1.length;j ++){
					columnStatus += "<div class='clearfix' id='s"+columnsList1[j].id+"'>";
					columnStatus += "<h4>"+columnsList1[j].columnName+"</h4><ul class='list_ul2 clearfix'>";
					var columnsList2 = columnsList1[j].columns;
					for (var h = 0;h < columnsList2.length;h ++){
						columnStatus += "<li class='li_sty' id='display_"+columnsList1[j].id+"'><a href='news/view/plate.do?columnId="+columnsList2[h].id+"'><img src='upload/"+columnsList2[h].picPath+"'></a>";
						columnStatus += "<div><p title='"+columnsList2[h].columnName+"'>"+columnsList2[h].columnName.substring(0,6)+"";
						columnStatus += "<span class='span1' onclick='getColumn("+columnsList2[h].id+","+columnsList2[h].addFlag+")'><em></em></span> </p></div> ";
						if (columnsList2[h].addFlag != 1){
							columnStatus += "<span class='span2 add_1' onclick='plateAddHot("+columnsList2[h].id+");'></span></li>";
						}else{
							columnStatus += "<span class='span2 finish_1'></span></li>";
						}
						
					}
					columnStatus += "</ul><p class='more'><em></em>展开更多</p></div>";
				}
			}
			columnStatus += "</div>";
			$("#columnList").append(columnStatus);
			$('.lanmuTuijian').children('div').each(function(){
				if($(this).find('.list_ul2 li').length>15){
					$(this).find('.more').show();
					$(this).find('.list_ul2 li:gt(15)').hide();
				}			
			});	
			$('.lanmuTuijian .list_ul2 li .span2').each(function(){
				if($(this).hasClass('finish_1')){	$(this).show();	}			
			});
			$('.lanmuTuijian .more').click(function(){	
				if($(this).prev().find('li').length>16){
					var $hideD=$(this).prev().find('li:gt(15)');		
					if($hideD.is(":visible")){	$(this).html('<em></em>展开更多');$hideD.hide();
					}else{					
						$(this).html('<em></em>收起');$hideD.show();
					}
				}		
			});
		}
	});
}

//获取栏目标签及关键字
function getTagAndWord(){
	var mustTagNames=spaceTo_(ZU.$id("mustTagNames").value);
	var mustNotTagNames=spaceTo_(ZU.$id("mustNotTagNames").value);
	var shouldTagNames=spaceTo_(ZU.$id("shouldTagNames").value);
	var mustWordNames=spaceTo_(ZU.$id("mustWordNames").value);
	var mustNotWordNames=spaceTo_(ZU.$id("mustNotWordNames").value);
	var shouldWordNames=spaceTo_(ZU.$id("shouldWordNames").value);
	var flag = 0;
	var selectSources=ZU.$id("select_sources").value;
	if(ZU.$id("word_tit").checked){
		flag=1;
	}
	if(mustTagNames==""&&mustNotTagNames==""&&shouldTagNames==""&&mustWordNames==""&&mustNotWordNames==""&&shouldWordNames==""){
		msgShow("填写条件");
		return false;
	}
	var conditions={
		"mustTagNames":mustTagNames,
		"mustNotTagNames":mustNotTagNames,
		"shouldTagNames":shouldTagNames,
		"mustWordNames":mustWordNames,
		"mustNotWordNames":mustNotWordNames,
		"shouldWordNames":shouldWordNames,
		"flag":flag,
		"source":selectSources
	};
	conditions = JSON.stringify(conditions).replace(/null/g,"");
	return conditions;
}

//栏目预览
//pageFlag：是否点击分页调取 1:是  2：不是
function platePreview(pageFlag,source,pageNo,startPage){
	$("#resultNum").html("");
	if (source == null){
		source = $("#source").val();
	}
	if (pageNo == null){
		if (pageFlag == 1){
			pageNo = $(".onPage").text();
		}else{
			pageNo = 1;
		}
	}
    if (startPage == null){
		if (pageFlag == 1){
			startPage = $("#startPage").val();
		}else{
			startPage = 1;
		} 	
    }
    if (startPage < 0){
    	startPage = 1;
    }
    pageNo = pageNo || 1;
	startPage = startPage || 1;
	var addFlag = $("#addFlag").val();
	var plateId = ZU.$id("plateId").value;
	var columnId = ZU.$id("columnId2").value;

	if (source == 1){
		$("#recPlate").css('display','none'); 
	}else if (source == 2){
		$("#recPlate").css('display','block');
		if (addFlag == 1){
			$("#sub").css('display','none');	
			$("#sub2").attr("style",{"display":"none"});
		}else{
			$("#sub2").css('display','none');	
			$("#sub").attr("style",{"display":"none"});
		}
	}
		 
	$("#feiqikan li").remove();
	$(".page_p").remove();

	var ajaxUrl;
	var ajaxData;
	if (source == 1){//我的栏目
		var conditions = getTagAndWord();
		if (conditions == false){
			return;
		}
		ajaxUrl = "news/column/previewNews.do";
		ajaxData = {
			plateId : plateId,
			conditions :conditions,
			pageNo : pageNo,
			startPage : startPage		
		}
		$("#source").val(1);
	}else if (source == 2){//推荐栏目
		ajaxUrl = "news/column/previewNewsForColumn.do";
		ajaxData = {
			id : columnId,
			pageNo : pageNo,
			startPage : startPage
		}
		$("#source").val(2);
	}
	$(".previ_channel").show().css({
		"position" : "absolute",
		"top" : "100px",
		"left" : "50%",
		"margin" : "0 0 0 -250px"
	});
	$.ajax({
		type : "post",
		url : ajaxUrl,
		async : false,
		data : ajaxData,
		dataType : "json",
		success : function(data) {
			if (data.list.length > 0){
		        for (var i = 0; i < data.list.length; i++){
		    	    var feiqikan = "<li>" +
		    	   		       "<div class='name'>" +
		    		           "<p title='"+data.list[i].title+"'>"+data.list[i].title+"</p> "+
		    		           "</div>" +
		    		           "<div class='fullText'>"+data.list[i].content+"</div>" +
		    		           "<div class='supple'> "+
		    		           "<span>"+data.list[i].webName+"</span>|" +
		    		           "<span>"+data.list[i].newsDateTime+"</span>" +
		    		           "</div> </li>";
		       	    $("#feiqikan").append(feiqikan);
		        }

			    $("#resultNum").html("共"+data.count+"条结果");
				$("#startPage").val(data.startPage);
			    //paging分页
				$(".page_p").remove();
				if(data.pageCount>1){
					var page = '<div class="page_p"><p>';
		    		if(data.pageNo<=1){
		    			page+='<a>首页</a><a>&lt;上一页</a>';
		    		}
		    		if(data.pageNo>1){
		    			page+='<a href="javascript:platePreview(1,'+data.source+',1,1);">首页</a><a href="javascript:platePreview(1,'+data.source+','+(data.pageNo-1)+','+(data.startPage-1)+');">&lt;上一页</a>';
		    		}
		    		for(var c =data.startPage;c<= data.endPage;c++){
		    			if(c==data.pageNo){
		    				page+='<a href="javascript:platePreview(1,'+data.source+','+c+','+data.startPage+');" class="onPage">'+c+'</a>';
		    			}else{
		    				page+='<a href="javascript:platePreview(1,'+data.source+','+c+','+data.startPage+');">'+c+'</a>';
		    			}
		    		}
		    		if(data.pageNo<data.pageCount){
		    			if(data.pageNo==data.endPage){
		    				page+='<a  href="javascript:platePreview(1,'+data.source+','+(data.pageNo+1)+','+(data.startPage+1)+');">下一页&gt;</a>';
		    			}else{
		    				page+='<a  href="javascript:platePreview(1,'+data.source+','+(data.pageNo+1)+');">下一页&gt;</a>';
		    			}
			    		page+='<a  href="javascript:platePreview(1,'+data.source+','+data.pageCount+','+(data.pageCount - data.movepage)+');">尾页</a>';		

		    		}
		    		if(data.pageNo>=data.pageCount){
		    			page+='<a >下一页&gt;</a><a>尾页</a>';
		    		}
		    		page+='</p></div>';
		    		$("#paging").append(page);
				}
			}else{
				var page = '<div class="page_p">暂无记录<p>';
				$("#paging").append(page);			
			}
		}
	});
}

//关闭添加栏目及查看栏目窗口
function closeColumnDiv(){	
	$('.ui_back2,.ui_back,.box_content,.pop_div2').hide();
	$("#startPage").val("1");
}

//标签和关键词联想
//input 绑定inputid,suggestWrap append到谁, cLeft left值 ,cTop top值, closeB点击关闭的div ,pageSize每页记录 ,searchType联想内容：1 标签  2 关键词
function oSearchSuggest(input, suggestWrap, cLeft, cTop, closeB, pageSize,searchType) {
    var init = function() {
        input.bind('click',
        function(event) {
            sendKeyWordToBack();
            event.stopPropagation();
        });
        input.bind('keyup', function(){
        	sendKeyWordToBack();
            event.stopPropagation();
        });
//        input.bind('blur',
//        function() {
//            input.val('');
//        });
        closeB.bind('click',
        function() {
            $('#form_cities').remove();
        });
    };
    var sendKeyWordToBack = function(pageId) {
        dataDisplay(pageId || 1);
    };
    var dataDisplay = function(pageId) {
    	if(!pageId) pageId=1;
    	var valText = $.trim(input.val());
    	var data ;
    	var url;
    	if (searchType == 1){
    		url = "news/column/searchNewsLabels.do";
    	}else{
    		url = "global/searchLabel.do";
    	}
        $.ajax({
            type: "POST",
            url: url,
            async: false,
            data: {
                name: valText,
                pageId: pageId,
                pageSize: pageSize
            },
            dataType: "json",
            success: function(d) {
                if (d.status == "success") {
                	data = d;
                }
            }
        });
        $('#form_cities').remove();
        if (! (data && data.count > 0)) {
            return;
        }
        var newDiv = "<div id='form_cities'><ul id='panel_cities'>";
        for (var i = 0; i < data.list.length; i++) {
            newDiv += "<li>" + data.list[i].name + "</li>";
        }
        var pageCount = data.count % pageSize == 0 ? data.count / pageSize: Math.ceil(data.count / pageSize) ;
        var pageId = data.pageId;
        newDiv += "</ul><div id='flip_cities'><a href='javascript:void(0);'>«&nbsp;向前</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);'>向后&nbsp;»</a></div></div>";
        suggestWrap.append(newDiv);
        $('#form_cities').show().css({
            'left': cLeft + 'px',
            'top': cTop + 'px'
        });
        $('#form_cities').find('li').mouseover(function() {
            $(this).addClass('hover').siblings().removeClass('hover');
        });
        $('#form_cities').find('li').bind('click',
        function() {
            input.val($(this).html());
            $('#form_cities').remove();
        });
        $('#flip_cities').find('a').eq(0).bind('click',function(event) {
        	if(pageId>1){
        		sendKeyWordToBack(pageId-1);
        	}
        	event.stopPropagation();
        });
        $('#flip_cities').find('a').eq(1).bind('click',function(event) {
        	if(pageId<pageCount){
        		sendKeyWordToBack(pageId+1);
        	}
        	event.stopPropagation();
        });
    };
    init();
};
