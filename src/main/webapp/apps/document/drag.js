/*
 * 拖动处理
 */
var dragobj={};//全局变量 dragobj.o 拖动层
//鼠标弹起
//document.onmouseup=mouseup;
function mouseup(){
	//console.info("mouseup= 鼠标弹起");
	g_unmousescroll();
	if(!(dragobj.o!=null)){
		return false;//移动元素为空
	}
	//console.info("type="+dragobj.type);
	if(dragobj.type=="move"){//移动
	}else if(dragobj.type=="plugin"){//插件的拖动
		if(iframeObj.contentWindow.dragobj.omflag){//存在插入框
			var pluginId = dragobj.o.id.split("_")[1];
			var param = dragobj.o.id.split("_")[2];
			var pluginName = dragobj.o.id.split("_")[3];
			installPlugin(pluginId,param,pluginName);//plugmouseup("str");
		}
		ZU.removeChild(dragobj.o);
		iframeObj.contentWindow.setMove(false);
	}else if(dragobj.type=="article"||dragobj.type=="articleNode"){//插入文档或者节点
		document.onmouseup=function(){};
		if(iframeObj.contentWindow.dragobj.omflag){//存在插入框
			var selectNode = iframeObj.contentWindow.dragobj.selectNode;
			dragobj.location = iframeObj.contentWindow.dragobj.location;
			var nextTrNode=null;
			var trNode = ZU.$id("span_"+selectNode.id);
			var articleId = dragobj.o.id.split("_")[1];
			var hId= 0;
			if(dragobj.type=="articleNode"){
				hId=dragobj.o.id.split("_")[1];
				articleId= dragobj.o.id.split("_")[4];
			}
			var node = getArticleContent(dragobj.type,articleId,hId);//请求具体的内容
			if(node.content ==undefined){
				//console.info(node.content+"空值");
				msgShow("文档内容为空");
				iframeObj.contentWindow.delOm();
				iframeObj.contentWindow.setIframe();
			}else{
				if(trNode!=null){
					if(dragobj.location=="top"){//节点上面
						nextTrNode=trNode.parentNode.parentNode;
					}else if(dragobj.location=="end"){
						nextTrNode = trNode.parentNode.parentNode.nextSibling;//zhuTree1.getNextTrNode(trNode);
					}
					iframeObj.contentWindow.delOm();
					ue.fireEvent('saveScene');
					iframeObj.contentWindow.dragAddContent(node,nextTrNode);
					iframeObj.contentWindow.setIframe();
					updateSectionsF();//整体更新大纲
					ue.fireEvent('saveScene');
				}else{//空
					nextTrNode = iframeObj.contentWindow.getotempNext();
					iframeObj.contentWindow.delOm();
					ue.fireEvent('saveScene');
					iframeObj.contentWindow.dragAddContentEnd(node,nextTrNode);
					iframeObj.contentWindow.setIframe();
					updateSectionsF();//整体更新大纲
					ue.fireEvent('saveScene');
				}
			}
		}//存在插入框
		iframeObj.contentWindow.setMove(false);
		ZU.removeChild(dragobj.o);
		
	}else if(dragobj.type=="newsAll"||dragobj.type=="newsMy"){//新闻，我的新闻
		document.onmouseup=function(){};
		if(iframeObj.contentWindow.dragobj.omflag){//存在插入框
			var idList=dragobj.o.id.split("_");
			var id=idList[1];//内容的id
			var categoryId=idList[2];
			var esId=idList[3];
			if(idList.length>5){
				for(var i=4;i<idList.length-1;i++){
					esId+="_"+idList[i];
				}
			}
			var urltype=1;//
			if(dragobj.type=="newsMy"){
				var urltype=0;//我的新闻
			}
			var node = geturlcontent(id,urltype,categoryId,esId);//请求具体的内容
			if(node.text==undefined){
				iframeObj.contentWindow.delOm();
			}else{
				var selectNode = iframeObj.contentWindow.dragobj.selectNode;//位置选中的元素
				dragobj.location = iframeObj.contentWindow.dragobj.location;//元素的位置
				var hId = selectNode.id;
				node.idStr= ZU.getRandom();//新加元素的ID;
				var nextTrNode = null;
				var trNode = ZU.$id("span_"+selectNode.id);
				if(trNode!=null){
					if(dragobj.location=="top"){//节点上面
						nextTrNode=zhuTree1.insertBefore(node,hId);//目录树插入子元素
					}else if(dragobj.location=="end"){//节点下面
						var order = ZU.getSplitOrder(trNode.parentNode.parentNode);
						if(order<6){
							nextTrNode=zhuTree1.insertChildFirst(node,hId);//目录树插入子元素
						}else if(order>=6){
							nextTrNode=zhuTree1.insertNext(node,hId);//目录树插入子元素
						}
					}
					iframeObj.contentWindow.delOm();
					ue.fireEvent('saveScene');
					iframeObj.contentWindow.dragAddNode(node,nextTrNode);
					iframeObj.contentWindow.setIframe();
					ue.fireEvent('saveScene');
				}else{//没有标题
					//console.info("没有标题");
					nextTrNode = iframeObj.contentWindow.getotempNext();
					iframeObj.contentWindow.delOm();
					ue.fireEvent('saveScene');
					iframeObj.contentWindow.dragAddNewsEnd(node,nextTrNode);
					iframeObj.contentWindow.setIframe();
					updateSectionsF();//整体更新大纲
					ue.fireEvent('saveScene');
				}
				locationId(node.idStr);
			}
			ZU.removeChild(dragobj.o);
		}else{
			
			ZU.removeChild(dragobj.o);
		}
		//iframe 暂停鼠标拖动处理
		iframeObj.contentWindow.setMove(false);
	}
		document.onmousemove=function(){};
		g_unmousescroll();//删除滚动动作
		dragobj={};
}
/*
 * 鼠标移动
 */
function mousemove(e){
	if ( e && e.preventDefault ){ 
		e.preventDefault();
	} else { 
		window.event.returnValue = false;
	}
	e=ZU.getMouseXY(e);
	//console.info("mousemove1  e.x="+e.x+",e.y="+e.y)
	if(dragobj.o!=null){//拖动元素不等于空
        dragobj.o.style.left=(e.x-dragobj.xx[0])+"px";
        dragobj.o.style.top=(e.y-dragobj.xx[1])+"px";
        //console.info("e.x="+e.x+",dragobj.xx[0]="+dragobj.xx[0]+",dragobj.o.style.left="+dragobj.o.style.left);
        //console.info("e.y="+e.y+",contentObj.scrollTop="+contentObj.scrollTop+"height="+dragobj.fengMianDivXY.height);
        //console.info("e.y="+e.y+",dragobj.contentObjXY.top="+dragobj.contentObjXY.top+",dragobj.contentObjXY.height="+dragobj.contentObjXY.height+",iframeObj.scrollTop="+iframeObj.scrollTop);
        //console.info("e.x="+e.x+",iframeXY.left="+iframeXY.left+",iframeXY.width="+iframeXY.width);
        if(((e.y>dragobj.contentObjXY.top)&&(e.y<(dragobj.contentObjXY.top+iframeXY.height+dragobj.fengMianDivXY.height)))&&(e.x>dragobj.contentObjXY.left)&&(e.x<(dragobj.contentObjXY.left+dragobj.contentObjXY.width))){//在输入框内
			//换算成iframe鼠标中的位置
        	var contentObj=dragobj.contentObj; //内容区滚动的元素
			e.y=(e.y-iframeXY.top+contentObj.scrollTop);
			e.x=(e.x-iframeXY.left+contentObj.scrollLeft);
			//console.info("-e.y="+e.y+",iframeXY.top="+iframeXY.top+",contentObj.scrollTop="+contentObj.scrollTop);
			if(e.y>0){
				iframeObj.contentWindow.objMove(e);
			}
		}else{
			if(iframeObj.contentWindow.dragobj.omflag){
				//console.info("不在范围内,存在插入框,则删除删除插入框");
				iframeObj.contentWindow.delOm();
			}
			//console.info("不在范围内");
		}
        //console.info("width="+dragobj.o.style.width)
    }
};
/*
 新闻:拖动
 e:鼠标 obj:拖动的元素 
 urltype:请求的类型  0:我的新闻 1:索引新闻,scrollid:滚动层的id
 type:请求的类型  contentnode:新闻,plugin:插件,article:文档
 strhtml:这暂时 没用
*/
function pmousedown(e,obj,type,scrollid,strhtml){
	//console.info(arguments.callee.name+"鼠标点击元素");
	try{
		e.preventDefault();
	}catch(e1){
		
	};
	document.onmouseup=mouseup;
	if(dragobj.o!=null) return false;
	document.onmousemove=mousemove;
	g_onmousescroll();//增加鼠标滚动函数
	dragobj.omflag=false;//没有插入框
	dragobj.type=type;//内容的拖动content
	if(scrollid){
		dragobj.scrollnode=document.getElementById(scrollid).parentNode;
		//console.info("dragobj.scrollnode.scrollTop="+dragobj.scrollnode.scrollTop);
	}
	e=ZU.getMouseXY(e);//鼠标的位置
	//console.info("pmousedown"+"鼠标点击元素"+obj.id.split("_")[1]);
	dragobj.xy = ZU.getNodeOffset(obj,dragobj.scrollnode);//元素的位置
	dragobj.xx=new Array((e.x-dragobj.xy.left),(e.y-dragobj.xy.top));//0 为 元素定位
	//console.info("e.y="+e.y+",dragobj.xy.top="+dragobj.xy.top+",dragobj.xx[1]="+dragobj.xx[1]);
	var dragnode=document.createElement("div");
	dragnode.setAttribute("id", obj.id+"_"+new Date().getTime());
	dragnode.innerHTML=obj.innerHTML;
	dragnode.style.left=(dragobj.xy.left)+"px";
	dragnode.style.top=(dragobj.xy.top)+"px";
	//dragnode.style.width=(dragobj.xy.width)+"px";
	dragnode.style.height=(dragobj.xy.height+8)+"px";
	dragnode.style.position="absolute";
	//dragnode.style.border = "1px dashed red";
	dragnode.style.zIndex =99901;
	dragnode.style.background="#fff";//99bce8
	//dragnode.style.filter='alpha(opacity=0)';  
	//dragnode.style.line_height=(dragobj.xy.height+8)+"px";
	dragnode.className ="transparent_class";
	document.body.appendChild(dragnode);
	
	dragobj.o=dragnode;//拖动的元素
	
	iframeObj.contentWindow.setMove(true);
	dragobj.contentObj=document.getElementById("ifame_bo"); //内容区editor
	dragobj.contentObjXY = ZU.getNodeOffset(dragobj.contentObj);
	//dragobj.editorObj = document.getElementById("editor");
	dragobj.fengMianDiv = document.getElementById("fengMianDiv");
	dragobj.fengMianDivXY = ZU.getNodeOffset(dragobj.fengMianDiv);
	
	iframeXY=ZU.getNodeOffset(iframeObj);//获取iframe
	
	//iframeObj.contentWindow.addTopEnd();
    return false;
}

//
function geturlcontent(id,urltype,categoryId,esId){	
	var contentText = '';
	var url = "";
	if(urltype == 0){//我的内容
		//contentText = getContentInfo(id);
		url="document/queryContentById.do?newsId="+id;
	}else{//抓取索引内容
		//contentText = getGrabContentInfo(id);
		url="document/queryContentByCrawlId.do?crawlId="+id+"&categoryId="+categoryId+"&esId="+esId;
	}
	var urlcontentText=G_geturlcontent(url,urltype);
	if(urlcontentText!=null){
		//contentText = urlcontentText.replace(/<p><\/p>/g,'');//替换到空的层
	}
	return urlcontentText;
}
//获取内容
function G_geturlcontent(url,urltype){
	var contentText = {};
	ZU.Ajax.request(url,{
		async:false,//true 是异步的,false:同步
		method:"GET",
		success:function(data){
			contentText.id=ZU.getRandom();
			contentText.text=data.contentTitle;
			contentText.content = data.contentText;
		},
		failure:function(data){
			//msgShow("查询异常,请重试或刷新");
			if(data.responseText.indexOf("<title>知识创享网-首页</title>")>-1){
				msgShow("请重试或重新登录");
			}else{
				msgShow("查询异常");
			}
			//alert("查询我的内容详情异常!");
		}
	});
	return contentText;
}
/*
 * 获取文档内容
 * type 类型 article articleNode
 */

function getArticleContent(type,articleId,hId){
	var contentText = {};
	var url;
	if(type=="articleNode"){//节点预览
		url="getNodeContent.do?articleId="+articleId+"&hId="+hId+"&hName=h1";
	}else{//文章预览
		url="word/getContent.do?articleId="+articleId;
	}
	ZU.Ajax.request(url,{
		async:false,//true 是异步的,false:同步
		method:"GET",
		success:function(data){
			contentText.content = data.nodeContent;
		},
		failure:function(data){
			msgShow("查询文档内容异常!");
			//alert("查询文档内容异常!");
		}
	});
	return contentText;
}

//iframe 设置父的拖动元素位置
function setDragXY(e){
	  dragobj.o.style.left=(e.x+iframeXY.left-dragobj.xx[0]-dragobj.contentObj.scrollLeft)+"px";
      dragobj.o.style.top=(e.y+iframeXY.top-dragobj.xx[1]-dragobj.contentObj.scrollTop)+"px";
}
//添加鼠标滚动时间
function g_onmousescroll(){
	/*
	//加入Mozilla的事件listerner
	if(window.addEventListener)
	document.addEventListener('DOMMouseScroll', moveObject, false);
	//document.removeEventListener('DOMMouseScroll', moveObject, false)
	//for IE/OPERA etc
	document.onmousewheel = moveObject;
	*/
}
//删除鼠标滚动事件
function g_unmousescroll(){
	/*
	//加入Mozilla的事件listerner
	if(window.addEventListener)
	//document.addEventListener('DOMMouseScroll', moveObject, false);
	document.removeEventListener('DOMMouseScroll', moveObject, false);
	//for IE/OPERA etc
	document.onmousewheel = function(){};
	*/
}
//插件处理
function installPlugin(pluginId,params,pluginName){
	G_pluginDivId=0;//插件内容divID
	G_pluginParams = params;
	//G_pluginParams = '3,4';
	G_pluginId = pluginId;//pluginId;
	
	G_pluginName = pluginName;
	
	document.getElementById("pluginInsertDiv").style.display="";
	$(".plugin_properties,.box_content,.ui_back").fadeIn(100);
	
	//设置属性窗口的值
	var url="word/getGlobalByArticleId.do?articleId="+articleId;
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			//获取属性成功
			var endDate = document.getElementById("pluginEndDate");
			var industry = document.getElementById("pluginIndustryId");
			var provice = document.getElementById("pluginProvinceId");
			var industryName = document.getElementById("pluginIndustry");
			var proviceName = document.getElementById("pluginProvince");
			var publisherName = document.getElementById("pluginPublish");
	        var bankTypeName = document.getElementById("pluginBankType");
	        var propertyName = document.getElementById("pluginProperty");
	        var startDate = document.getElementById("pluginStartDate");
	        var datafieldName = document.getElementById("datafield");
	        
	        startDate.value="";
			endDate.value="";
			industry.value = "";
			provice.value="";
			industryName.value="";
			proviceName.value ="";
			publisherName.value ="1";
			bankTypeName.value ="0";
			propertyName.value ="1";
			datafieldName.value ="1";
			
			endDate.parentNode.style.display="none";
			industryName.parentNode.style.display = "none";
			proviceName.parentNode.style.display ="none";
			
			publisherName.parentNode.style.display="none";
			bankTypeName.parentNode.style.display = "none";
			propertyName.parentNode.style.display ="none";
			startDate.parentNode.style.display ="none";
			datafieldName.parentNode.style.display ="none";
			
			var arry = G_pluginParams.split(",");
			for(var i=0;i<arry.length;i++){
				if(arry[i]==1){//开始时间
					startDate.parentNode.style.display="";	
				}else if(arry[i]==2){//结束时间
					endDate.parentNode.style.display="";
					 if(data.status=="success"){
						 endDate.value = ZU.stampToTimeStr(data.bean.endTime).split(" ")[0];
					 } 
				}else if(arry[i]==3){//区域
					proviceName.parentNode.style.display="";
					 if(data.status=="success"){
						 proviceName.value = data.bean.areaName;
						 provice.value =data.bean.areaId;
					 }
				}else if(arry[i]==4){//行业
					industryName.parentNode.style.display="";
					 if(data.status=="success"){
						 industryName.value = data.bean.industryName;
						 industry.value =data.bean.industryId;
					 }
				}else if (arry[i] == 5) {//发行对象
					publisherName.parentNode.style.display = "";
					
				}else if (arry[i] == 6) {//银行类型
					bankTypeName.parentNode.style.display = ""; 
					
				}else if (arry[i] == 7) {//物业类型
					propertyName.parentNode.style.display = "";
				}else if (arry[i] == 8){//走势图名称
					datafieldName.parentNode.style.display = "";
				}
			}
		},
		failure:function(data){
		}
	});
	
	
}
/*
 * 执行插件 点确定
 * pluginId:插件的ID
 * industry:行业id
 * industryName:行业名字
 * provice:区域的ID
 * proviceName:区域名字
 * startDate:开始时间
 * endDate:结束时间
 * type : do 执行，insert:插入
 */
function getPluginContent(obj,type){
	//lb.show()
	var industry = document.getElementById("pluginIndustryId").value;
	var industryName = document.getElementById("pluginIndustry").value;
	var provice = document.getElementById("pluginProvinceId").value;
	var proviceName = document.getElementById("pluginProvince").value;
	var endDate = document.getElementById("pluginEndDate").value;
	//2015-11-25
	var startDate = document.getElementById("pluginStartDate").value;
	var publisherId = document.getElementById("pluginPublish").value;
	var publisherName = $("#pluginPublish").find("option:selected").text();
    var bankTypeId = document.getElementById("pluginBankType").value;
    var bankTypeName = $("#pluginBankType").find("option:selected").text();
    var propertyId = document.getElementById("pluginProperty").value;
    var propertyName = $("#pluginProperty").find("option:selected").text();
    //2015-12-30
    var datafieldId = document.getElementById("datafield").value;
	
	if(type=="do"){
		var industryNameInput = document.getElementById("pluginIndustry").parentNode.style.display;
		var proviceNameInput = document.getElementById("pluginProvince").parentNode.style.display;
		var endDateInput = document.getElementById("pluginEndDate").parentNode.style.display;
		var startDateInput = document.getElementById("pluginStartDate").parentNode.style.display;
		if(industry==""&&provice==""&&endDate==""){
			msgShow("请输入参数");
			return false;
		}else if(startDateInput != "none" && startDate == ""){
			msgShow("请选择开始时间");
			return false;
		}else if(endDateInput!="none"&&endDate==""){
			msgShow("请选择结束时间");
			return false;
		}else if(proviceNameInput!="none"&&proviceName==""){
			msgShow("请选择区域信息");
			return false;
		}else if(industryNameInput!="none"&&industryName==""){
			msgShow("选择行业信息");
			return false;
		}
		//判断开始时间不大于当前时间
		if (startDate != "" ){
			var start = new Date(startDate.replace("-", "/").replace("-", "/"));
			if (start > new Date()){
				msgShow("选取日期有误，请重新选择");
				return false;
			}
		}

		//判断开始时间小于结束时间
		if (startDate != "" && endDate !=""){
	        var start = new Date(startDate.replace("-", "/").replace("-", "/"));
	        var end = new Date(endDate.replace("-", "/").replace("-", "/"));
			if (start > end){				
				msgShow("选取日期有误，请重新选择");
				return false;
			}
		}
	}
	$(".plugin_properties,.box_content,.ui_back").fadeOut(100);//关闭输入框
	
	var urlstr='plugId='+G_pluginId;
	urlstr+='&industryId='+industry+'&industryName='+industryName+'&areaId='+provice+'&areaName='+proviceName;
	urlstr+='&startTime='+startDate+'&endTime='+endDate+'&publisherId='+publisherId+'&publisherName='+publisherName;
	urlstr+='&bankTypeId='+bankTypeId+'&bankTypeName='+bankTypeName+'&propertyId='+propertyId+'&propertyName='+propertyName+'&datafieldId='+datafieldId;
	urlstr+='&params='+G_pluginParams;//参数标识
	urlstr+="&pluginName="+G_pluginName;//插件名称
	//?plugId=5&industryId=96&industryName=石油和天然气开采业&areaId=2&areaName=北京市&startTime=&endTime=2015-04-10
	if(type=="do"){
		G_lb = new loading_box({title:'插件执行中',text:'请等待'});
		setTimeout(function(){pluginUrl(urlstr,industry,industryName,provice,proviceName,startDate,endDate,publisherId,publisherName,bankTypeId,bankTypeName,propertyId,propertyName,datafieldId);},10);
	}else{
		var contentText={};
		contentText.id=ZU.getRandom();
		contentText.content = "<div><img src='common/image/chajian.png' >"+G_pluginName+"</div>";
		contentText.urlstr=urlstr;
		insertPluginContent(contentText);
		msgShow("插件插入完毕");
	}

}
/*
 * 插件的url
 */
function pluginUrl(urlstr,industry,industryName,provice,proviceName,startDate,endDate,publisherId,publisherName,bankTypeId,bankTypeName,propertyId,propertyName,datafieldId){
	var url = "plugin/getPlugContent.do";
	ZU.Ajax.request(url,{
		async:true,//true 是异步的,false:同步
		timeout:90000,//超时时间
		method:"POST",
		data:{
    		plugId:G_pluginId,
    		industryId:industry,
    		industryName:industryName,
    		areaId:provice,
    		areaName:proviceName,
    		startTime:startDate,
    		endTime:endDate,
    		publisherId:publisherId,
    		publisherName:publisherName,
    		bankTypeId:bankTypeId,
    		bankTypeName:bankTypeName,
    		propertyId:propertyId,
    		propertyName:propertyName,
    		datafieldId:datafieldId
		},
		success:function(data){
			if(data.template!=null){
				var contentText={};
				contentText.id=ZU.getRandom();
				contentText.content = data.template;
				contentText.urlstr=urlstr;
				insertPluginContent(contentText);
				G_lb.close();
				msgShow("插件执行完毕");
			}else{
				G_lb.close();
				msgShow(""+data.error);
			}
			dragclean();
		},
		failure:function(data){
			dragclean();
			G_lb.close();
			msgShow("查询插件异常!");
		}
	});
}
/*
 * 插入插件内容
 * node 插件节点
 */

function insertPluginContent(node){
	var nodeNext = iframeObj.contentWindow.getotempNext();
	iframeObj.contentWindow.delOm();
	ue.fireEvent('saveScene');
	iframeObj.contentWindow.dragAddPlugin(node,G_pluginDivId,nodeNext);
	iframeObj.contentWindow.setIframe();
	updateSectionsF();//整体更新大纲
	ue.fireEvent('saveScene');
}
//拖动元素清理
function dragclean(){
	//console.info("清理垃圾");
	iframeObj.contentWindow.setMove(false);
	G_URL=null;//插件的全局变量
    dragobj={};
	document.onmousemove=function(){};
	iframeObj.contentWindow.delOm();
	iframeObj.contentWindow.delTopEnd();
	//oDel(dragobj.o);
    //oDel(dragobj.otemp);
}
//显示提示框
function msgShow(msg){
	$(".save_success,.box_content,.ui_back").fadeIn(100);
	$(".save_success").find("span").html(msg);
}

//设置iframe 高度
function setIframe(height){
	//console.info("height="+height);
	if(height<800) return false;
	document.getElementById("ueditor_0").style.height=height+"px";
}
/*
 * 鼠标滚动事件
 */
function moveObject(event){
	var delta = 0;
	if (!event) event = window.event;
	//console.info("event.wheelDelta="+event.wheelDelta+",event.detail="+event.detail);
	// normalize the delta
	if (event.wheelDelta) {//120
	// IE and Opera
	delta = event.wheelDelta / 60;
	} else if (event.detail) {//3
	// W3C
	delta = -event.detail / 2;
	}
	var currPos=dragobj.contentObj.scrollTop;
	//计算对象的下一个位置
	currPos=parseInt(currPos)-(delta*20);
	dragobj.contentObj.scrollTop = currPos;
}

//添加鼠标滚动时间
function g_onmousescroll(){
	//加入Mozilla的事件listerner
	if(window.addEventListener)
	document.addEventListener('DOMMouseScroll', moveObject, false);
	//document.removeEventListener('DOMMouseScroll', moveObject, false)
	//for IE/OPERA etc
	document.onmousewheel = moveObject;
}
//删除鼠标滚动事件
function g_unmousescroll(){
	//加入Mozilla的事件listerner
	if(window.addEventListener)
	//document.addEventListener('DOMMouseScroll', moveObject, false);
	document.removeEventListener('DOMMouseScroll', moveObject, false);
	//for IE/OPERA etc
	document.onmousewheel = function(){};
}
