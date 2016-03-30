// JavaScript Document
/*
 * 
 */
function g_TreeLoad(img_id,tr_id,obj,v_articleId){
	var url="WorkbenchData/articleOutline.action";
	Ext.Ajax.request({
		url:url,//请求地址
    	params:{
    		articleId:v_articleId
    	},
		method:'POST',//请求方式
		async:false,//同步请求
		success:function(response){//成功
			if(response.responseText != "null"){
				var rootnode = Ext.JSON.decode(response.responseText);
				var str="";
				str=g_TreeShow(rootnode,v_articleId);
				//alert(str);
				var tr_obj=document.getElementById(tr_id);
				var img_obj = document.getElementById(img_id);
				//str="<table><tr><td>111111111111</td></tr></table>";
				tr_obj.innerHTML=str;
				g_change(img_id,tr_id,img_obj);
			}else{
				Ext.MessageBox.alert('温馨提示！','文档节点失败！');
			}
		},
		failure:function(){//失败
			Ext.MessageBox.alert('温馨提示！','文档节点失败！');
		}
	});
}
/*
 * 目录树的显示
 */
function g_TreeShow(rootnode,v_articleId){
	var str='<table><tbody>';
	for(var i=0;i<rootnode.length;i++){
		var node = rootnode[i];
		if(i==0&&node.pid==0&&node.order==0&&node.nodeNameStatic=="封面"){
			continue;//封面不处理
		}
		var treeNodeId=v_articleId+'_'+node.nodeId;
		str+='<tr><td>';
		str+='<img id="tree_img_'+treeNodeId+'"';
		if(node.children.length>0){//存在子节点
			if(i==rootnode.length-1){//最后一个节点
				str+=' src="images/tree/folder_add_end.gif"';
			}else{
				str+=' src="images/tree/folder_add.gif"';
			}
			str+=' onmouseup="g_change(\'tree_img_'+treeNodeId+'\',\'tree_tr_'+treeNodeId+'\',this) "';
		}else{
			if(i==rootnode.length-1){//最后一个节点
				str+=' src="images/tree/file_end.gif" ';
			}else{
				str+=' src="images/tree/file.gif" ';
			}
		}
		str+=' /><span class="moveclass" articleId="'+v_articleId+'" nodeId="'+node.nodeId+'" ';
		str+=' onmousedown="articleMouseDown(event,this,0,\'articleGrid\')">';
		if(node.nodeNameStatic.length>12){//长度大于12个字符
			str+="  "+node.nodeNameStatic.substring(0,12)+'...</span></td>';
		}else{
			str+="  "+node.nodeNameStatic+'</span></td>';
		}
		
		str+='<td class="preview">';
		str+='<img src="images/tree/bo.gif" onclick="articlePreview(\''+v_articleId+'\',\''+node.nodeId+'\')" /></td>';
		str+='</tr>';
		if(node.children.length>0){//存在子节点
			str+='<tr>';
			if(i==rootnode.length-1){//最后一个节点
				str+='<td class="list1"';
			}else{
				str+='<td class="list"';
			}
			str+=' colspan="2" id="tree_tr_'+treeNodeId+'" style="display:none;">';
			str+=g_TreeShow(node.children,v_articleId);
			str+="</td></tr>";
		}
	}
	str+='</tbody></table>';
	//alert(str)
	return str;
}
/*
 * 
*/
function g_change(img_id,tr_id,obj){
	var tr_obj=document.getElementById(tr_id);
	if(tr_obj.style.display=="none"){//�����ص�
		tr_obj.style.display="";
		if(obj.src.indexOf("images/tree/top_add.gif")>-1){//�׽ڵ�
			obj.src="images/tree/top.gif";
		}else if(obj.src.indexOf("images/tree/folder_add.gif")>-1){//Ŀ¼�ڵ�
			obj.src="images/tree/folder.gif";
		}else if(obj.src.indexOf("images/tree/folder_add_end.gif")>-1){//����Ŀ¼�ڵ�
			obj.src="images/tree/folder_end.gif";
		}
	}else{
		tr_obj.style.display="none";
		if(obj.src.indexOf("images/tree/top.gif")>-1){//�׽ڵ�
			obj.src="images/tree/top_add.gif";
		}else if(obj.src.indexOf("images/tree/folder.gif")>-1){
			obj.src="images/tree/folder_add.gif";
		}else if(obj.src.indexOf("images/tree/folder_end.gif")>0){
			obj.src="images/tree/folder_add_end.gif";
		}
	}
}
/*
 * 文档节点预览
 */
function articlePreview(v_articleId,v_nodeId){
	var responseJson=articlePreviewUrl(v_articleId,v_nodeId);
	var winId="articlePreview";
	
	var obj = document.createElement("div");
	obj.innerHTML = "<div class=\"topic_css\">"+responseJson.content+"</div>";
	//移除name属性
	removeTagName(obj,'strong','name');
	var arrp = obj.getElementsByTagName('p');
	for(var i=0; i<arrp.length; i++){
		var text = arrp[i].innerHTML;
		arrp[i].setAttribute('style','cursor:move;');
		arrp[i].setAttribute('id','p'+i+'_'+v_articleId);
		arrp[i].setAttribute('onmousedown','pmousedownInfo(event,this,0,\''+winId+'\',\'text\')');
	}
	var divlist = obj.getElementsByTagName('div');
	for(var i=0;i<divlist.length;i++){
		var node = divlist[i];
		if(node.getAttribute('onmousedown')!=null){//带拖动标题
			node.setAttribute('onmousedown','pmousedownInfo(event,this,0,\''+winId+'\',\'text\')');
		}
	}
	var resultNew=obj.innerHTML;
	
	if(!Ext.getCmp(winId)){//弹出框不存在
		var contentInfo = Ext.create('Ext.window.Window',{
			title:'<font style="font-size:14px;line-height:22px;">文档预览</font>',
			width:870,
			height:550,
			id:winId,
			resizable:false,
			autoScroll:true,// 滚动条自动显示
	        bodyStyle:'overflow-y:auto;overflow-x:hidden;',// 隐藏横向滚动条，显示纵向滚动条
	//        closable:false,
			layout : 'fit',
			plain : true,
			bodyStyle : 'padding:5px;',
			html:resultNew
		});
		contentInfo.show();
	}else{//弹出框存在
		Ext.getCmp(winId).update(resultNew);
		Ext.getCmp(winId).setActive(true);
		//document.getElementById("articlePreview-body").innerHTML=resultNew;
		//contentInfo.html="123";
	}
}
//文档节点预览的url
function articlePreviewUrl(v_articleId,v_nodeId){
	var responseJson='';
	var url="WorkbenchData/articlePreview.action";
	Ext.Ajax.request({
		url:url,//请求地址
    	params:{
    		articleId:v_articleId,
    		nodeId:v_nodeId
    	},
		method:'POST',//请求方式
		async:false,//同步请求
		success:function(response){//成功
			if(response.responseText != "null"){
				responseJson = Ext.JSON.decode(response.responseText);
			}else{
				Ext.MessageBox.alert('温馨提示！','获取失败！');
			}
		},
		failure:function(){//失败
			Ext.MessageBox.alert('温馨提示！','获取失败！');
		}
	});
	return responseJson;
}
//文档插入节点
function articleInsert(v_articleId,v_nodeId,articleNewId,nodeNewId){
	if(v_nodeId==null){
		return false;
	}
	var responseJson=articleInsertUrl(v_articleId,v_nodeId,articleNewId,nodeNewId);
	//articleReload();//刷新文档区大纲区
	g_updateContentArea(responseJson);//文档区
	//大纲区
	g_updateOutLine(responseJson);
	g_Positioning(responseJson.nodes[0].nodeId);

}
//更新内容
function g_updateContentArea(responseJson){
	var v_noedId = responseJson.nodeId;
	var firstNodeId = responseJson.firstNodeId;
	var outNode = Ext.getCmp("outletId").store.getRootNode().findChild("id",v_noedId,true);
	var depth =2;
	if(outNode!=null){
		depth = outNode.data.depth+1;
		if(depth>6){
			depth=6;
		}
	}
	var divnode=document.createElement("div");
	divnode.innerHTML=g_updateContentHtml(responseJson.nodes,depth);
	
	if(firstNodeId==0){//没有子节点
		var textdiv = document.getElementById("textarea_"+v_noedId);
		textdiv.parentNode.appendChild(divnode);
	}else{//有子节点
		var anchordiv = document.getElementById("anchor_"+firstNodeId);
		anchordiv.parentNode.insertBefore(divnode,anchordiv);
	}

}
//更新大纲
function g_updateOutLine(responseJson){
	var nodes = responseJson.nodes;
	var v_nodeId =  responseJson.nodeId;
	g_updateOutLineHtml(nodes,v_nodeId);
}
//
function g_updateOutLineHtml(nodes,v_nodeId){
	var rootnode =  Ext.getCmp("outletId").store.getRootNode();
	var parentnode =rootnode.findChild("id",v_nodeId,true);
	for(var i=0;i<nodes.length;i++){
		var node = nodes[i];
		var newnode = Ext.create("UI.model.OutlineModel", {
			id : node.nodeId,
			text : node.nodeNameStatic,
			nodeNameStatic:node.nodeNameStatic,
			nodeNameDynamic:node.nodeNameDynamic,
			leaf : false,
			expanded : true,
			loaded : true,
			order :node.order
		});
		parentnode.insertChild(i,newnode);
		g_updateOutLineHtml(node.children,node.nodeId);
	}
}
//获取内容的html
function g_updateContentHtml(nodes,depth){
	var str='';
	if(depth>6){depth=6;}
	for(var i=0;i<nodes.length;i++){
		var node = nodes[i];
		var v_nodeId =  node.nodeId;
		str+='<div class="upload_main" id="anchor_'+v_nodeId+'" >';
		str+='<div class="post_title" id="post_'+v_nodeId+'">';
		str+='<div id="titleStyle_'+v_nodeId+'">';
			str+='<h'+depth+' id="title_'+v_nodeId+'" style="display:inline;" ondblclick="g_dbclick(this,'+v_nodeId+')">'+node.nodeNameStatic+'</h'+depth+'>';
			str+='<img id="editbutton_'+v_nodeId+'" src="images/Edit.png" style="margin-left:10px;" unselectable="on" onclick="clickEditButton(event,'+v_nodeId+')">';
		str+='</div>';
		str+='<div style="clear: both; margin: 0px; padding: 0px;"></div>';
		str+='<div id="textarea_'+v_nodeId+'" style="clear: both;" class="editable">';
			if(node.content==""){
				str+="<p>--请输入内容--</p>";
			}else{
				str+='<div name="plugtext" class="contentcss">'+node.content+'</div>';
			}
		str+="</div>";
		str+=g_updateContentHtml(node.children,depth+1);
		str+="</div>";
		str+="</div>";
	}
	return str;
}
//文档插入节点url
function articleInsertUrl(v_articleId,v_nodeId,articleNewId,nodeNewId){
	var responseJson='';
	var url="WorkbenchData/articleAddNode.action";
	Ext.Ajax.request({
		url:url,//请求地址
    	params:{
    		articleId:v_articleId,
    		nodeId:v_nodeId,
    		articleNewId:articleNewId,
    		nodeNewId:nodeNewId
    	},
		method:'POST',//请求方式
		async:false,//同步请求
		success:function(response){//成功
			if(response.responseText != "null"){
				responseJson = Ext.JSON.decode(response.responseText);
			}else{
				Ext.MessageBox.alert('温馨提示！','添加节点失败！');
			}
		},
		failure:function(){//失败
			Ext.MessageBox.alert('温馨提示！','添加节点成功！');
		}
	});
	return responseJson;
}
//文档刷新,包括大纲区,内容区
function articleReload(){
	document.getElementById("center").innerHTML="";
	Ext.getCmp("outletId").store.reload();
	//document.getElementById("center").innerHTML="";
	//createDocument(articleId);
}
//获取封面和第一个节点的ID
function G_getFengMianId(store){
	var rootnode = store.tree.root;
	G_fengMianId = 0;//封面节点的ID
	G_firstNodeId = 0;//第一个节点的Id
	for(var i=0;i<rootnode.childNodes.length;i++){
		var node = rootnode.childNodes[i].data;
		if(i==0){//封面
			if(node.depth==1&&node.order==0&&node.nodeNameStatic=="封面"){
				G_fengMianId=node.id;
			}else{
				G_firstNodeId=node.id;
			}
		}else if(i==1){
			if(G_firstNodeId==0){
				G_firstNodeId=node.id;
			}
		}else{
			//break;
		}
		//console.info("index="+node.index);
	}
	//console.info("G_fengMianId="+G_fengMianId+",G_firstNodeId="+G_firstNodeId);
}
//文档插入内容节点
function g_contentNode(v_articleId,v_nodeId,contentId,urltype){
	try{
	var contentText = '';
	var contentName  ='';
	var params={};
	var url = "";
	if(urltype == 0){//我的内容
		//contentText = getContentInfo(id);
		params['contentId'] = contentId;
		url="WorkbenchData/queryContentText.action";
	}else{//抓取内容
		//contentText = getGrabContentInfo(id);
		params['crawlId'] = contentId;
		url="WorkbenchData/queryGrabContentText.action";
	}
	Ext.Ajax.request({
		url:url,//请求地址
		params:params,//参数
		method:'POST',//请求方式
		async:false,//同步请求
		success:function(response){//成功
			if(response.responseText != "null"){
				var rootnode = Ext.JSON.decode(response.responseText);
				if(urltype==0){
					contentName=rootnode.content_name;
					contentText=rootnode.content;
				}else{
					contentName=rootnode.title;
					contentText=rootnode.text;
				}
				g_contentAddNode(v_articleId,v_nodeId,contentName,contentText);
			}
		},
		failure:function(){//失败
			Ext.MessageBox.alert('温馨提示！','查询我的内容详情异常！');
		}
	});
	}catch(e){
		Ext.MessageBox.alert('温馨提示！','发送请求失败');
	}
}
//添加子节点
function g_contentAddNode(v_articleId,v_nodeId,contentName,contentText){
	var url="WorkbenchData/addContentNode.action";
	Ext.Ajax.request({
		url:url,//请求地址
    	params:{
    		articleId:v_articleId,
    		nodeId:v_nodeId,
    		contentName:contentName,
    		contentText:contentText
    	},
		method:'POST',//请求方式
		async:false,//同步请求
		success:function(response){//成功
			if(response.responseText != "null"){
				var rootnode = Ext.JSON.decode(response.responseText);
				var nodeNewId = rootnode.newNodeId;
				g_contentAddHtml(v_articleId,v_nodeId,rootnode,contentName,contentText);
			}
		},
		failure:function(){//失败
			Ext.MessageBox.alert('温馨提示！','添加内容节点失败！');
		}
	});
}
//添加节点的内容节点的html
function g_contentAddHtml(v_articleId,v_nodeId,rootnode,contentName,contentText){
	var str='';
	var node = rootnode.newNode;
	var nodeNewId =  node.nodeId;
	var outnode =  Ext.getCmp("outletId").store.getRootNode();
	var parentnode =outnode.findChild("id",v_nodeId,true);
	var depth =2;
	if(parentnode!=null){
		depth = parentnode.data.depth+1;
		if(depth>6){
			depth=6;
		}
	}
	
		str+='<div class="upload_main" id="anchor_'+nodeNewId+'" >';
		str+='<div class="post_title" id="post_'+nodeNewId+'">';
		str+='<div id="titleStyle_'+nodeNewId+'">';
			str+='<h'+depth+' id="title_'+nodeNewId+'" style="display:inline;" ondblclick="g_dbclick(this,'+nodeNewId+')">'+contentName+'</h'+depth+'>';
			str+='<img id="editbutton_'+nodeNewId+'" src="images/Edit.png" style="margin-left:10px;" unselectable="on" onclick="clickEditButton(event,'+nodeNewId+')">';
		str+='</div>';
		str+='<div style="clear: both; margin: 0px; padding: 0px;"></div>';
		str+='<div id="textarea_'+nodeNewId+'" style="clear: both;" class="editable">';
			if(node.content==""){
				str+="<p>--请输入内容--</p>";
			}else{
				str+='<div name="plugtext" class="contentcss">'+contentText+"</div>";
			}
		str+="</div>";
		str+="</div>";
		str+="</div>";
		var divnode=document.createElement("div");
		divnode.innerHTML=str;
		var firstNodeId = rootnode.firstNodeId;
		if(firstNodeId==0){//没有子节点
			var textdiv = document.getElementById("textarea_"+v_nodeId);
			textdiv.parentNode.appendChild(divnode);
		}else{//有子节点
			var anchordiv = document.getElementById("anchor_"+firstNodeId);
			anchordiv.parentNode.insertBefore(divnode,anchordiv);
		}
		//添加节点
		var newnode = Ext.create("UI.model.OutlineModel", {
				id : node.nodeId,
				text : node.nodeNameStatic,
				nodeNameStatic:node.nodeNameStatic,
				nodeNameDynamic:node.nodeNameDynamic,
				leaf : false,
				expanded : true,
				loaded : true,
				order :node.order
			});
		parentnode.insertChild(0,newnode);
		g_Positioning(node.nodeId);
}
//体检编辑按钮单击事件
function clickEditButton(e,v_nodeId){
	//添加事件 
	if(G_setid!=null){
		clearTimeout(G_setid);
			//console.info(" 删除定时器");
	}
	G_setid=setTimeout(function(){
		ueditorClick(e, 'textarea_' + v_nodeId);
	},300);
}
//定位 内容区和大纲区定位
function g_Positioning(newnodeId){
	setTimeout(function(){
		var tree=Ext.getCmp("outletId");
		var node=tree.store.getNodeById(newnodeId);
		tree.selModel.select(node);
		location.hash = "anchor_" + newnodeId;
	},10);
}

