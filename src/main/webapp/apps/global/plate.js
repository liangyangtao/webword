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
	var url="global/getUserWordPlate.do";
	ZU.Ajax.request(url,{
		method:"get",
		success:function(data){
			if(data.status=="success"){
				
			}else{
				
			}
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
 * 添加之前验证栏目数量
 */
function showPlatAdd(){
	var url = "global/getPlateCount.do";
	ZU.Ajax.request(url,{
		method:"post",
		data:{
			"pid":PID
		},
		success:function(data){
			if(data.status!="success"){
				msgShow(data.info);
				return false;
			}
			showCre_Channel();
		},
		failure:function(data){
			msgShow("添加失败");
		}
	});

}

/**
 * 添加栏目
 */
function plateAdd(plateName,pid,conditions,topList,delList){
	var url="global/plateAdd.do";
	ZU.Ajax.request(url,{
		method:"post",
		data:{
			"plateName":plateName,
			"pid":pid,
			"conditions":conditions,
			"topList":topList,
			"delList":delList
		},
		success:function(data){
			if(data.status!="success"){
				msgShow(data.info);
				return false;
			}
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
			var str="";
	        	str+='<img src="word/img/banner5.png" />';
	        	str+='<div><p title="'+wordPlate.plateName+'">'+plateName.substring(0,6)+'<span class="span1" onclick="plateShow('+plateId+')"><em></em></span></p></div>';
	        	str+='<span onclick="plateDel(event,this,'+plateId+')" class="span2"></span>';
	        
			liNode.innerHTML=str;
			ZU.$id("displayUl").appendChild(liNode);
			//添加到目录树
			if(wordPlate.pid==0){//添加到一级
				var liNode = document.createElement("li");
				liNode.id="tree_li_"+wordPlate.plateId;
				liNode.className = "m1_li";
				str='<h2><em class="icon1" onclick="emclick(this)"></em>';
				str+='<a href="javascript:treeDisplay('+wordPlate.plateId+');" title="编辑">'+plateName+'</a></h2>';
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
		},
		failure:function(data){
			msgShow("添加失败");
		}
	});
}

/**
 * 添加推荐的栏目
 */
function plateAddHot(columnId){
	var topList = ZU.$id("topList").value;
	var delList = ZU.$id("delList").value;
	if (columnId == null){
		columnId = ZU.$id("columnId2").value;
	}
	var url="global/plateAddHot.do";
	ZU.Ajax.request(url,{
		method:"post",
		data:{
			"columnId":columnId,
			"topList":topList,
			"delList":delList,
			"pid":PID
		},
		success:function(data){
			if(data.status!="success"){
				msgShow(data.info);
				return false;
			}
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
			var str="";
	        	str+='<img src="word/img/banner5.png" />';
	        	str+='<div><p title="'+wordPlate.plateName+'">'+plateName.substring(0,6)+'<span class="span1" onclick="plateShow('+plateId+')"><em></em></span></p></div>';
	        	str+='<span onclick="plateDel(event,this,'+plateId+')" class="span2"></span>';
	        
			liNode.innerHTML=str;
			ZU.$id("displayUl").appendChild(liNode);
			//添加到目录树
			if(wordPlate.pid==0){//添加到一级
				var liNode = document.createElement("li");
				liNode.id="tree_li_"+wordPlate.plateId;
				liNode.className = "m1_li";
				str='<h2><em class="icon1" onclick="emclick(this)"></em>';
				str+='<a href="javascript:treeDisplay('+wordPlate.plateId+');" title="编辑">'+plateName+'</a></h2>';
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
		},
		failure:function(data){
			msgShow("添加失败");
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
	var url="global/plateDel.do?plateId="+plateId;
	ZU.Ajax.request(url,{
		method:"get",
		success:function(data){	
			if(data.status!="success"){
				
			}
			var liNode = ZU.$id("tree_li_"+plateId);
			var uiNode = ZU.$id("tree_ul_"+plateId);
			var displayNode = ZU.$id("display_"+plateId);
			
			ZU.removeChild(liNode);
			ZU.removeChild(uiNode);
			ZU.removeChild(displayNode);
			//刷新推荐栏目的状态
			getHotColumnStatus();
		},
		failure:function(data){
			msgShow("删除失败");
		}
	});
}

/**
 * 取消订阅
 */
function plateDelHot(){
	var columnId = ZU.$id("columnId2").value;
	var url="global/plateDelHot.do?columnId="+columnId;
	ZU.Ajax.request(url,{
		method:"get",
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
		},
		failure:function(data){
			msgShow("删除失败");
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
	var url="global/updateOrder.do";
	var param = {
			firstId:firstId,
			location:location,
			loactionId:loactionId
	};
	ZU.Ajax.request(url,{
		method:"get",
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
		},
		failure:function(data){
			msgShow("排序失败");
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
			//str+='<li class="m1_li"><h2><em class="icon2"></em><a href="javascript:;">国有银行</a></h2></li>';
			str+='<li class="m1_li" id="tree_li_'+node.plateId+'"><h2><em class="icon1" onclick="emclick(this)"></em><a href="javascript:treeDisplay('+node.plateId+');" title="'+node.plateName+'">'+plateName+'</a></h2></li>';
			str+='<ul class="m1_ul" id="tree_ul_'+node.plateId+'" style="display:">';    
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
		var plateName=node.innerHTML;
		if(aList.length>0){
			plateName = aList[0].innerHTML;
		}
        str+='<li id="display_'+plateId+'" onmousedown="pmousedown(event,this)" class="li_sty">';
        str+='<img src="word/img/banner5.png"/>';
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
		//showDiv(1);
		showCre_Channel();//20160122
	}else{//修改栏目
		plateGet(plateId);
	}	
}
/**
 * 获取栏目
 */
function plateGet(plateId){
	var url="global/plateGet.do?plateId="+plateId;
	ZU.Ajax.request(url,{
		method:"get",
		success:function(data){
			if(data.status!="success"){
				
			}
			buttonClear();
			//showDiv(1);
			showCre_Channel();//20160122
			buttonSet(data.wordPlate);
			
		},
		failure:function(data){
			msgShow("获取失败");
		}
	});
}
/**
 * 修改栏目
 */
function plateEdit(plateId,plateName,conditions,topList,delList){
	var url="global/plateEdit.do";
	ZU.Ajax.request(url,{
		method:"post",
		data:{
			"plateName":plateName,
			"plateId":plateId,
			"conditions":conditions,
			"topList":topList,
			"delList":delList
		},
		success:function(data){
			if(data.status!="success"){
				
			}
			closeDiv();//关闭窗口
			//修改栏目成功
			var plateId = data.wordPlate.plateId;
			var plateName = data.wordPlate.plateName;
			var liNode = ZU.$id("display_"+plateId);
			var pNode = liNode.getElementsByTagName("p")[0];
			pNode.innerHTML=plateName+'<span class="span1" onclick="plateShow('+plateId+')"><em></em></span>';
			//修改目录树
			liNode=ZU.$id("tree_li_"+plateId);
			var aList = liNode.getElementsByTagName("a");
			if(plateName.length>8){
				plateName=plateName.substring(0,8)+"...";
			}
			if(aList.length>0){
				aList[0].innerHTML=plateName;
			}else{
				liNode.innerHTML=plateName;
			}
			
		},
		failure:function(data){
			msgShow("修改失败");
		}
	});
}
/*
 * 弹出框提交
 */
function plateButton(){
	var plateName=spaceTo_(ZU.$id("plateName").value);
	var plateId = ZU.$id("plateId").value;
	var editFlag = ZU.$id("editFlag").value;//操作确认标识  为1时提交当前操作
	var topList = "";
	var delList = "";
	if (editFlag == 1){
		topList = ZU.$id("topList").value;
		delList = ZU.$id("delList").value;
	}else{
		topList = ZU.$id("oldTopList").value;
		delList = ZU.$id("oldDelList").value;
	}

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
	var flag = 0;
	if(ZU.$id("word_tit").checked){
		flag=1;
	}
	if(plateName.length>8){
		msgShow("栏目名称不大于8个字");
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
		"flag":flag
	};
	conditions= JSON.stringify(conditions).replace(/null/g,"");
	if(plateId==0){//增加
		plateAdd(plateName,PID,conditions,topList,delList);
	}else{//修改
		plateEdit(plateId,plateName,conditions,topList,delList);
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
	$("#oldTopList").val(plate.topList);
	$("#oldDelList").val(plate.delList);
	$("#topList").val(plate.topList);
	$("#delList").val(plate.delList);
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
	var url="global/getColumn.do?columnId="+columnId;
	ZU.Ajax.request(url,{
		method:"post",
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
			
			if (conditions.flag != null && conditions.flag == 1){
				$("#wordFlag").html("文档标题");					
			}else{
				$("#wordFlag").html("文档全文");	
			}
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
		url : "global/getHotColumnStatus.do",
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
						columnStatus += "<li class='li_sty' id='display_"+columnsList1[j].id+"'><img src='word/img/banner5.png'>";
						columnStatus += "<div><p title='"+columnsList2[h].columnName+"'>"+columnsList2[h].columnName+"";
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
		"flag":flag
	};
	conditions = JSON.stringify(conditions).replace(/null/g,"");
	return conditions;
}

//栏目预览
//contentType(1:期刊   2：非期刊)
//source：来源（1：我的栏目的预览  2：推荐栏目的预览）
//是否点击分页调取 1:是  2：不是
function platePreview(pageFlag,contentType,source,pageNo,startPage){
	$("#resultNum").html("");
	if (contentType == null){
		contentType = $("#contentType").val();
	}
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
    	startPage = $("#startPage").val();
    }
    if (startPage < 0){
    	startPage = 1;
    }
    pageNo = pageNo || 1;
	startPage = startPage || 1;
	var addFlag = $("#addFlag").val();
	var topList = $("#topList").val();
	var delList = $("#delList").val();
	var contentTypePage;
	var plateId = ZU.$id("plateId").value;
	var columnId = ZU.$id("columnId2").value;

	if (source == 1){
		$("#myPlate").css('display','block'); 
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
		$("#myPlate").css('display','none'); 
	}
		 
	$("#qikan li").remove();	 
	$("#feiqikan li").remove();
	$(".page_p").remove();

	var ajaxUrl;
	var ajaxData;
	if (source == 1){//我的栏目
		var conditions = getTagAndWord();
		if (conditions == false){
			return;
		}
		ajaxUrl = "global/refreshArtAndJour.do";
		ajaxData = {
			plateId : plateId,
			topList : topList,
			delList : delList,
			conditions :conditions,
			contentType : contentType,
			pageNo : pageNo,
			startPage : startPage		
		}
		$("#source").val(1);
	}else if (source == 2){//推荐栏目
		ajaxUrl = "global/refreshArtAndJourForColumn.do";
		ajaxData = {
			id : columnId,
			topList : topList,
			delList : delList,
			contentType : contentType,
			pageNo : pageNo,
			startPage : startPage
		}
		$("#source").val(2);
	}
	$(".previ_channel").show().css({
		"position" : "absolute",
		"top" : "10px",
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
			    if (data.contentType == 1){		       
			        for (var i = 0; i < data.list.length; i++){
			           var topValue = "置顶";
			           var topEvent = "setTop";
			           var delClass = "dele_1";
			           var delEvent = "dele";
//			           var topDis = "block";
			           var delDis = "block";
			           var buyDis = "none";
			           if (data.list[i].buyFlag == 1){//购买状态
			        	   buyDis = "block";
			           }
			           if (data.list[i].status == 2){//删除状态
				           delClass = "add_1";
				           delEvent = "cancelDele";   
			           }
			    	   var qikan = "<li><div><img src='"+data.list[i].cover+"'>" +
			    	               "<span class='icon icon1'><em>"+data.list[i].type+"</em> </span>" +
				    	           "<span class='brought_icon' style='display:"+buyDis+"'></span>" + 
//			    	               "<p id='jour_"+data.list[i].journalId+"' style='display:"+topDis+"' class='setTop' onclick='"+topEvent+"("+data.list[i].journalId+");'>"+topValue+"</p>" +
			    	               "<p id='dele_"+data.list[i].journalId+"' style='display:"+delDis+"' class='dele "+delClass+"' onclick='"+delEvent+"("+data.list[i].journalId+");'></p></div>"+
			    	               "<h5>"+data.list[i].name+"</h5></li>";
//			    	               "<h5><a target='_blank' href='view/preview.do?journalId="+data.list[i].journalId+"' title='"+data.list[i].name+"'>"+data.list[i].name+"</a></h5></li>";
			       	   $("#qikan").append(qikan);
			        }	
			        contentTypePage = 1;
			        $("#contentType").val(1);
			    }else{
			        for (var i = 0; i < data.list.length; i++){
						var keyword = data.list[i].articleKeyWord;
						var keywordA = "";
						if(keyword != " "){
							var keywords = keyword.split(" ");
							for(var j=0;j<6;j++){
								if (keywords[j] != "" && typeof(keywords[j]) != "undefined"){
									keywordA+="<a target='_blank' href='view/search.do?keyword="+keywords[j]+"'>"+keywords[j]+"</a>";	
								}							
							}
						}
						
						var articleSkip = typeof(data.list[i].articleSkip) == "undefined" ? "" : data.list[i].articleSkip;
						var htmlPage = typeof(data.list[i].htmlPage) == "undefined" ? "" : data.list[i].htmlPage;
						var downCount = typeof(data.list[i].downCount) == "undefined" ? "" : data.list[i].downCount;
						var viewCount = typeof(data.list[i].viewCount) == "undefined" ? "" : data.list[i].viewCount;
                    	var long = parseInt(data.list[i].updateTime);
                    	var date = new Date(long);
                    	var updateTime = date.getFullYear()+"-"+(date.getMonth()<9?("0"+(date.getMonth()+1)):(date.getMonth()+1))+"-"+(date.getDate()<10?("0"+date.getDate()):date.getDate());
			    	    var feiqikan = "<li>" +
			    	   		       "<div class='name'>" +
			    		           "<span class='ic-h-"+data.list[i].articleFormat+"'></span>" +
			    		           "<p title='"+data.list[i].articleName+"'>"+data.list[i].articleName+"</p> "+
			    		           "</div>" +
			    		           "<div class='fullText'>"+articleSkip+"</div>" +
//			    		           "<div class='fullText'><a target='_blank' href='view/preview.do?articleId="+data.list[i].articleId+"'>"+articleSkip+"</a></div>" +
			    		           "<div class='keyword'>"+keywordA+"</div> " +
			    		           "<div class='supple'> "+
			    		           "<span>"+updateTime+"</span>|" +
			    		           "<span>共"+htmlPage+"页</span>|" +
			    		           "<span>"+downCount+"次下载</span>|" +
			    		           "<span>"+viewCount+"阅读</span></div> </li>";
			       	    $("#feiqikan").append(feiqikan);
			        }
			        contentTypePage = 2;
			        $("#contentType").val(2);
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
		    			page+='<a href="javascript:platePreview(1,'+contentTypePage+','+data.source+',1,1);">首页</a><a href="javascript:platePreview(1,'+contentTypePage+','+data.source+','+(data.pageNo-1)+','+(data.startPage-1)+');">&lt;上一页</a>';
		    		}
		    		for(var c =data.startPage;c<= data.endPage;c++){
		    			if(c==data.pageNo){
		    				page+='<a href="javascript:platePreview(1,'+contentTypePage+','+data.source+','+c+','+data.startPage+');" class="onPage">'+c+'</a>';
		    			}else{
		    				page+='<a href="javascript:platePreview(1,'+contentTypePage+','+data.source+','+c+','+data.startPage+');">'+c+'</a>';
		    			}
		    		}
		    		if(data.pageNo<data.pageCount){
		    			if(data.pageNo==data.endPage){
		    				page+='<a  href="javascript:platePreview(1,'+contentTypePage+','+data.source+','+(data.pageNo+1)+','+(data.startPage+1)+');">下一页&gt;</a>';
		    			}else{
		    				page+='<a  href="javascript:platePreview(1,'+contentTypePage+','+data.source+','+(data.pageNo+1)+');">下一页&gt;</a>';
		    			}
			    		page+='<a  href="javascript:platePreview(1,'+contentTypePage+','+data.source+','+data.pageCount+','+(data.pageCount - data.movepage)+');">尾页</a>';		

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

//置顶
function setTop(journalId){

	$("#jour_"+journalId).text('取消置顶');
	$("#jour_"+journalId).removeAttr("onclick"); 
	$("#jour_"+journalId).attr("onclick","cancelTop("+journalId+")"); 
	$("#jour_"+journalId).parents('li').remove().prependTo('.previ_channel .list_box .list_qikan');
	$("#dele_"+journalId).css('display','none'); //隐藏删除

	var topList = $("#topList").val();

	if (topList != ""){
		$("#topList").val(journalId+","+topList);
	}else{
		$("#topList").val(journalId);
	}
	
	$("#jour_"+journalId).parents('li').find('.dele').bind('click', function(){
		if($("#jour_"+journalId).hasClass('dele_1')){ 
			$(this).removeClass("dele_1").addClass("add_1");
		}else{	
			$("#jour_"+journalId).removeClass("add_1").addClass("dele_1");
		}	 
	});
	//刷新
	platePreview(2);
}

//取消置顶
function cancelTop(journalId){

	$("#jour_"+journalId).text('置顶');
	$("#jour_"+journalId).removeAttr("onclick"); 
	$("#jour_"+journalId).attr("onclick","setTop("+journalId+")"); 
	$("#dele_"+journalId).css('display','block'); //显示删除
	var topList = $("#topList").val();
	if (topList != ""){	
		var topArr = topList.split(",");
		var topListNew = null;
		for (var i=0;i<topArr.length;i++){
			if (topArr[i] != journalId){
				if (topListNew == null){
					topListNew = topArr[i];
				}else{
					topListNew = topListNew + "," +topArr[i];				
				}
			}
		}
		$("#topList").val(topListNew);
	}
	//刷新
	platePreview(2);
}

//删除
function dele(journalId){

	$("#dele_"+journalId).removeAttr("onclick"); 
	$("#dele_"+journalId).attr("onclick","cancelDele("+journalId+")"); 

	if($("#dele_"+journalId).hasClass('dele_1')){ 
		$("#dele_"+journalId).removeClass("dele_1").addClass("add_1");
	}else{	
		$("#dele_"+journalId).removeClass("add_1").addClass("dele_1");
	}	 
//	$("#jour_"+journalId).css('display','none');
	var delList = $("#delList").val();
	if (delList != ""){
		$("#delList").val(delList+","+journalId);
	}else{
		$("#delList").val(journalId);
	}

	//刷新
	platePreview(2);
}

//取消删除
function cancelDele(journalId){

	$("#dele_"+journalId).removeAttr("onclick"); 
	$("#dele_"+journalId).attr("onclick","dele("+journalId+")"); 
//	$("#jour_"+journalId).css('display','block'); //显示置顶
	if($("#dele_"+journalId).hasClass('add_1')){ 
		$("#dele_"+journalId).removeClass("add_1").addClass("dele_1");
	}else{	
		$("#dele_"+journalId).removeClass("dele_1").addClass("add_1");
	}	
	
	var delList = $("#delList").val();
	if (delList != ""){		
		var delArr = delList.split(",");
		var delListNew = null;
		for (var i=0;i<delArr.length;i++){
			if (delArr[i] != journalId){
				if (delListNew == null){
					delListNew = delArr[i];
				}else{
					delListNew = delListNew + "," +delArr[i];				
				}
			}
		}
		$("#delList").val(delListNew);
	}
	//刷新
	platePreview(2);
}

//初始化：只是更改初始化的一个标记 将值改为1
function init(){
	$("#initFlag").val(1);
	//刷新
	$("#topList").val("");
	$("#delList").val("");
	platePreview(2);
	$("#topList").val($("#oldTopList").val());
	$("#delList").val($("#oldDelList").val());
}
//确认
function confirmInit(){
	
	//初始化的确认
	var initValue = $("#initFlag").val();
	if (initValue == 1){
		$("#topList").val("");
		$("#delList").val("");
	}
	//操作的确认
	$("#editFlag").val(1);
}

//取消
function cancel(){	
	$("#editFlag").val(0);
}
//关闭添加栏目及查看栏目窗口
function closeColumnDiv(){	
	$('.ui_back2,.ui_back,.box_content,.pop_div2').hide();
	$("#resultNum").html("");
	$("#oldTopList").val("");
	$("#oldDelList").val("");
	$("#topList").val("");
	$("#delList").val("");
	$("#initFlag").val("0");
	$("#editFlag").val("0");
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
    		url = "global/searchTags.do";
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
