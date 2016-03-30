/*
name:名字,order:顺序。
*/
var treeJson = [];
//树的结构
function zhuTree(name){
	var me = this;
	this.name = name;//生成目录树的div
	this.trClickId = 0;//点击的行
	this.rightMenuDiv = null;//右键菜单
	//console.info("this.name="+this.name);
	this.setJson=function (json){
		this.json=json;//设置json数据
	};
	this.getJson=function (){
		return this.json;
	};
	//初始化目录树
	this.init = function(){
		var json =[];
	};
	//
	/*删除节点
	 * delId = {"startId":"","endId":""},endId=0,表是没有下一个节点
	 */
	this.delNodeById = function (delId){
		var startId= delId.startId;
		var endId = delId.endId;
		
		var startNode = document.getElementById(startId);
		var endNode = document.getElementById(endId);
		if(endId==0){//没有下一个节点
			endNode = document.createElement("div");
			document.body.appendChild(endNode);
		}
		if(document.createRange){
			var range = document.createRange();
			range.setStart(startNode, 0);
			range.setEnd(endNode, 0);
			var oFragment = range.extractContents();//删除选中元素
		}else{//IE8浏览器
			var nextNode = startNode.nextSibling;
			while(nextNode){
				if(nextNode==endNode){//到达下一个节点
					break;
				}else if(nextNode.nodeType==9){//是顶级节点
					break;
				}else{
					ZU.removeChild(nextNode);
				}
				nextNode=startNode.nextSibling;
				if(nextNode==null){//没有下一个节点
					//nextNode=startNode.parentNode;
				}
			}
		}
		if(endId==0){//删除添加的元素
			ZU.removeChild(endNode);
		}
		ZU.removeChild(startNode);
	};//
	 /*
	  * 插入大纲子节点
	  * @parameter node 插入节点的json结构
	  * 插入节点的下一个节点的Id
	  * 在nextId 之前掺入节点,nextId为空的时候，插入到最后。
	  */
	this.insertChild = function(node,nextId){
		var nodename = "h"+node.order;
		var nodeH = document.createElement(nodename);
			nodeH.innerHTML = node.text;
			nodeH.id = node.idStr;
		if(nextId!=0){//存在下一个节点
			var nextHNode = document.getElementById(nextId);
			nextHNode.parentNode.insertBefore(nodeH,nextHNode);
		}else{
			document.body.appendChild(nodeH);
		};
	};
	//插入大纲节点nextId之前
	this.insertBedore = function(node,nextId){
		var nodename = "h"+node.order;
		var nodeH = document.createElement(nodename);
			nodeH.innerHTML = node.text;
			nodeH.id = node.idStr;
		if(hID!=0){//存在下一个节点
			var nextHNode = document.getElementById(hID);
			nextHNode.parentNode.insertBefore(nodeH,nextHNode);
		}else{
			document.body.appendChild(nodeH);
		};
	};
}
//重名称节点
function tree_rename(hId,text){
	var hNode = document.getElementById(hId);
	hNode.innerHTML = text;
}
//拖动函数
var dragobj={};//拖动的元素
dragobj.selectNode = null;//位置选中的元素
dragobj.location = null;//位于元素的位置  top:上部 end:下部
dragobj.topH = null;//添加的头标题
dragobj.endH = null;//添加尾标题
var scrollX=0;//页面滚动 left
var scrollY=0;//页面滚动 top

//设置一定的接收函数
function setMove(flag){
	if(flag){//true设置移动
		//document.onmousemove=mousemove;
		//document.addEventListener("mousemove",mousemove,false);
		dragobj.type=parent.dragobj.type;
		ZU.on(document, "mousemove", mousemove);
		scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
		scrollY = document.documentElement.scrollTop || document.body.scrollTop;
		//document.body.contentEditable='false'; 
		//document.designMode='Off'
	}else{
		//document.removeEventListener("mousemove",mousemove,false);
		ZU.un(document, "mousemove", mousemove);
		//document.onmousemove=function(){};
		//document.body.contentEditable='true'; 
		//document.designMode='on'
	}
	
}
/*
 * 如果鼠标焦点在iframe上面
 * 1.执行滚动,2.设置拖动的层。
 */
function mousemove(e){
	if ( e && e.preventDefault ){ 
		e.preventDefault();
	} else { 
		window.event.returnValue = false;
	}
	e=ZU.getMouseXY(e);
	//console.info("子mousemove");
	//console.info("子mousemove1  e.x="+e.x+",e.y="+e.y);
	e.x=e.x-scrollX-scrollX;
	e.y=e.y-scrollY-scrollY;
	objMove(e);
	parent.setDragXY(e);
};
/*
 * 鼠标移动
 * e 位置
 */
function objMove(e){
	//console.info("--子---e.x="+e.x+",e.y="+e.y+",scrollY="+scrollY);
	e.x=e.x+scrollX;
	e.y=e.y+scrollY;
	//console.info("document.documentElement.scrollTop="+document.documentElement.scrollTop);
	//console.info("document.body.scrollTop="+document.body.scrollTop);
	//var nodelist = document.getElementsByName("hTitle");//获取所有的标题 hTitle
	if(dragobj.nodeList==null){
		
	}
	var b=ZU.inner(dragobj.otemp,e);
	if(b!=0){
		//console.info("在插入层上面");
		return false;
	}
	//var nodelist=document.querySelectorAll("h1,h2,h3,h4,h5,h6");//获取所有的标题
	var nodelist=[];
	if(dragobj.type=="newsAll"||dragobj.type=="newsMy"){//新闻的拖动
		nodelist =ZU.getElementsByTagName("h1,h2,h3,h4,h5,h6");
	}else if(dragobj.type=="plugin"){//插件
		//nodelist = document.body.childNodes;
		nodelist =ZU.getElementsByTagName("h1,h2,h3,h4,h5,h6,p");
	}else if(dragobj.type=="article"||dragobj.type=="articleNode"){//文档,和文档节点
		nodelist =ZU.getElementsByTagName("h1,h2,h3,h4,h5,h6");
	}else{
		
	}
	if(dragobj.type=="newsAll"||dragobj.type=="newsMy"||dragobj.type=="article"||dragobj.type=="articleNode"){

	}
	var childList = document.body.childNodes;
	if(childList.length==1){
		var firstNode = childList[0];
		nodelist.push(firstNode);
	}else if(childList.length>1){
		var firstNode = childList[0];
		nodelist.push(firstNode);
		var endNode = childList[childList.length-1];
		nodelist.push(endNode);
		//console.info("添加第一个和第二个元素");
	}else{
		//console.info(childList.length);
	}
	//console.info(nodelist.length);
	for(var i=0;i<nodelist.length;i++){
        var pnode=nodelist[i];
       //console.info("pnode="+pnode.getAttribute("name"));
		if(pnode == null)    //已经移出的层不再遍历
            continue;
		var b=ZU.inner(pnode,e);
		if(b==0) continue;
		//dragobj.otemp.style.width=pnode.offsetWidth;
		//elm.style.width = pnode.offsetWidth;
		if(b==1){//元素上部
		 	pnode.parentNode.insertBefore(dragobj.otemp,pnode);
		 	dragobj.selectNode = pnode;
		 	dragobj.location="top";
		}else{//元素下部
			if(pnode.nextSibling==null){
				pnode.parentNode.appendChild(dragobj.otemp);
			}else{
				pnode.parentNode.insertBefore(dragobj.otemp,pnode.nextSibling);
			}
		 	dragobj.selectNode = pnode;
		 	dragobj.location="end";
		}
		//console.info("dragobj.location="+dragobj.location+",pnode.innerHTML="+pnode.innerHTML);
		dragobj.omflag=true;//有插入框
        return false;
    }
}

/*
 * 删插插入框
 */
function delOm(){
	dragobj.omflag=false;//不存在掺入框
	ZU.removeChild(dragobj.otemp);
}
/*
 * 增加id
 */
function addIdH(obj){
	var hList =ZU.getElementsByTagName("h1,h2,h3,h4,h5,h6",obj);
	var minOrder = 7;//获取最小的id
	for(var i=0;i<hList.length;i++){
		var hNode = hList[i];
		var order = ZU.getHLevel(hNode);
		if(minOrder>order) minOrder=order;
		if(hNode.id==""){
			hNode.id = ZU.getRandom();
		}
	}
}
//去除多余的h4标签
function clearHtml(obj){
	var hList =ZU.getElementsByTagName("h1,h2,h3,h4,h5,h6",obj);
	for(var i=0;i<hList.length;i++){
		var hNode = hList[i];
		var pList = ZU.getElementsByTagName("p,div,table",hNode);
		if(pList.length>0){//
			for(var j=0;j<hNode.childNodes.length;){//标题里面包含
				var pNode =hNode.childNodes[j];
				hNode.parentNode.insertBefore(pNode,hNode);
			}
		//处理完成 删除
			ZU.removeChild(hNode);
		}
	}
	addIdH(obj);
}
// 添加新闻,内容插件
function dragAddNode(node,nextTrNode){
	var text = node.text;
	var content = node.content;//toLowerCase()
	content=contentReplace(content);
	var hNode = document.createElement("h"+node.order);
	hNode.id=node.idStr;
	hNode.innerHTML = text;
	var divnode=document.createElement("div");
	divnode.innerHTML=content;
	clearHtml(divnode);
	if(nextTrNode!=null){//存在下一个节点
		var id = ZU.getSplitId(nextTrNode);
		var nexthNode = ZU.$id(id);//下一个h节点间
		nexthNode.parentNode.insertBefore(hNode,nexthNode);
		nexthNode.parentNode.insertBefore(divnode,nexthNode);
	}else{
		document.body.appendChild(hNode);
		document.body.appendChild(divnode);
	}
}
/*
 * 添加新闻,定位点，不是h
 */
function dragAddNewsEnd(node,nextNode){
	var text = node.text;
	var content = node.content;//toLowerCase()
	content=contentReplace(content);
	var hNode = document.createElement("h1");
	hNode.id=node.idStr;
	hNode.innerHTML = text;
	var divnode=document.createElement("div");
	divnode.innerHTML=content;
	clearHtml(divnode);
	if(nextNode!=null){//存在下一个节点
		nextNode.parentNode.insertBefore(hNode,nextNode);
		nextNode.parentNode.insertBefore(divnode,nextNode);
	}else{
		document.body.appendChild(hNode);
		document.body.appendChild(divnode);
	}
}
/*
 * 文档添加元素
 * content:添加的字符串
 * nextTrNode插入节点
 * 
 */

function dragAddContent(node,nextTrNode){
	//var oFragment=document.createDocumentFragment();
	//oFragment.innerHTML=node.content;
	var divnode=document.createElement("div");
	//替换空的段落
	node.content=contentReplace(node.content);
	divnode.innerHTML=node.content;
	var nodelist =ZU.getElementsByTagName("h1,h2,h3,h4,h5,h6",divnode);
	for(var i=0;i<nodelist.length;i++){
		var nodei = nodelist[i];
		nodei.id=ZU.getRandom();
	}
	if(nextTrNode!=null){//存在下一个节点
		var id = ZU.getSplitId(nextTrNode);
		var nexthNode = ZU.$id(id);//下一个h节点间
		nexthNode.parentNode.insertBefore(divnode,nexthNode);
	}else{
		document.body.appendChild(divnode);
	}
	
}
/*
 * 文档添加元素
 * content:添加的字符串
 * nextTrNode插入节点
 * 
 */

function dragAddContentEnd(node,nextTrNode){
	var divnode=document.createElement("div");
	node.content=contentReplace(node.content);//替换空的段落
	divnode.innerHTML=node.content;
	var nodelist =ZU.getElementsByTagName("h1,h2,h3,h4,h5,h6",divnode);
	for(var i=0;i<nodelist.length;i++){
		var nodei = nodelist[i];
		nodei.id=ZU.getRandom();
	}
	if(nextTrNode!=null){//存在下一个节点
		nextTrNode.parentNode.insertBefore(divnode,nextTrNode);
	}else{
		document.body.appendChild(divnode);
	}
	
}
/*
 * 添加插件内容
 * node 节点 node.content内容
 */
function dragAddPlugin(node,pluginDivId,nodeNext){
	var urlstr= node.urlstr;
	var divnode=null;
	node.content=contentReplace(node.content);//替换空的段落
	if(pluginDivId!=0){//重新执行 替换
		divnode = document.getElementById(pluginDivId);
		divnode.setAttribute("text",urlstr);//+"&id="+id
		divnode.innerHTML=node.content;
	}else{//新的执行替换
		divnode=document.createElement("div");
		var id="plugin_"+ZU.getParam("plugId",urlstr)+"_"+new Date().getTime();
		divnode.setAttribute("id",id);
		divnode.setAttribute("name","plugtext");//插件的名字
		divnode.className="plugincss";
		divnode.setAttribute("title",ZU.getParam("pluginName", urlstr));
		divnode.setAttribute("text",urlstr);//+"&id="+id
		divnode.innerHTML=node.content;
		if(nodeNext==null){
			document.body.appendChild(divnode);
		}else{
			nodeNext.parentNode.insertBefore(divnode,nodeNext);
		}
		
	}
	/*
	 * 增加id
	 */
	var hList =ZU.getElementsByTagName("h1,h2,h3,h4,h5,h6",divnode);
	var minOrder = 7;//获取最小的id
	for(var i=0;i<hList.length;i++){
		var hNode = hList[i];
		var order = ZU.getHLevel(hNode);
		if(minOrder>order) minOrder=order;
		if(hNode.id==""){
			hNode.id = ZU.getRandom();
		}
	}
	window.parent.setIframe(document.body.scrollHeight);
	//node.content='<img src="resources/images/53/66/0/43/17/38/89/1489381743006653.png">';	
	//alert(node.content);
	//console.info("node.content="+node.content);
	//console.info("--"+divnode.innerHTML);
	//alert(divnode.innerHTML);
	parent.findPlugin(1);
}

//document.onmouseout =mouseout;
function mouseout(){
	//console.info("子鼠标离开");
}
//document.onmouseover=mouseover;
function mouseover(){
	//console.info("子鼠标到达");
}
//document.onmouseup=mouseup;
function mouseup(){
	//console.info("子鼠标释放");
}
//页面加载
var Tree;
function pageload(){
	var bodyObj = document.getElementsByTagName("body")[0];
	for(var i=0;i<bodyObj.childNodes.length;i++){
		var obj=bodyObj.childNodes[i];//
		//odeName=#text chrome谷歌浏览器。
		if(obj.innerHTML!=null){
			//console.info("i="+i+",nodeName="+obj.nodeName+",innerHTML="+obj.innerHTML);
		}
	}
	var om=document.createElement("div");//om为位置层
    om.style.width="100%";
    om.style.height="40px";
    om.style.border = "1px dashed #99bbe8";    //ikaiser添加，实现虚线框
	dragobj.otemp=om;
	document.body.contentEditable='true'; 
	document.designMode='on';
	var ie=false;
	if (ie) {
		document.body.disabled = true;
		document.body.contentEditable = true;
		document.body.disabled = false;
    } else {
		document.body.contentEditable = true;
    }
	scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
	scrollY = document.documentElement.scrollTop || document.body.scrollTop;
	//UE.Editor
	/*
	setTimeout(function(){
	editor = window.parent.UE.instants['ueditorInstant0'];
	editor._setup(document);},
	0);
	*/
	setIframe();
	//parent.document.all("HtmlEdit").style.width=document.body.scrollWidth;
	Tree = new zhuTree();
	Tree.init();//初始化大纲
	//屏蔽掉树的菜单
	ZU.on(document, "mousedown", function(){window.parent.tree_rightMenu(false);});
	//document.addEventListener("mousedown",function(){window.parent.tree_rightMenu(false);},false); 
}
function setIframe(){
	//console.info("document.body.scrollHeight="+document.body.scrollHeight);
	//window.parent.setIframe(document.body.scrollHeight);
}
//删除标题节点
function deleteNodeH(preNodeId,nextNodeId){
	//console.info("preNodeId="+preNodeId+",nextNodeId="+nextNodeId);
	//return ;
	var preNode = document.getElementById(preNodeId);
	var nextNode = document.getElementById(nextNodeId);
	if(nextNodeId==0){
		nextNode = document.createElement("div");
		document.body.appendChild(nextNode);
	}
	var range = document.createRange();
	range.setStart(preNode, 0);
	range.setEnd(nextNode, 0);
	var oFragment = range.extractContents();//删除选中元素
	if(nextNodeId==0){//删除添加的元素
		oDel(nextNode);
	}
}

//获取父的标题元素
function parentHNode(node){
	//console.info("获取---父的标题元素");
	if(!(node!=null)) return null;
	if(/H\d{1}/.test(node.nodeName)){
		return node;//在标题元素里面
	}
	var preNode = node.previousSibling;//上一个节点
	if(/H\d{1}/.test(preNode.nodeName)){
		//是标题节点
	}else {
		var parentNode = node.parentNode;
		if(parentNode.nodeType==1){//是document节点
			return null;
		}else{
			parentHNode(parentNode);
		}
	}
}
/*添加子标题节点
*ndeo:插入的节点
*parentNode:父节点
*nextNode:下一个节点
*内容插入是，插入到下一个节点前面
*/
function insertNode(node,parentNode,nextNode){
	var hNode = document.createElement("h"+node.data.order);
	hNode.innerHTML="新建标题";
	hNode.id=node.data.id;
	if(nextNode==0){//不存在下一个节点，直接插入到最后
		document.body.appendChild(hNode);
	}else{//掺入到 一个节点 之前
		var hNextNode = document.getElementById(nextNode.data.id);
		if(hNextNode!=null){
			hNextNode.parentNode.insertBefore(hNode);
		}
	}
	//setIframe();
	parent.ue.fireEvent("contentchange");
}
//判断是否存在此晕死
//遍历所有的h标题
function addevent(){
	
}
/*
 * 遍历插件
 * name 插件名字
 */
function findPlugin(name){
	var divList = document.getElementsByTagName("div");
	var plist=[];
	for(var i=0;i<divList.length;i++){
		var node=divList[i];
		//console.info("name="+node.getAttribute("name"))
		if(node.getAttribute("name")!="plugtext") continue;
		if(name!=""){//存在搜索
			var title = node.getAttribute("title");
			if(title.split(name).length>1){//存在此名字
				plist.push(node);
			}
		}else{
			plist.push(node);
		}
		
	}
	return plist;
}
/*
 * 删除插件
 * id:插件的Id
 */
function pluginDel(id){
	var obj = document.getElementById(id);
		ZU.removeChild(obj);
}
/*
 *获取插件text
 */
function getPluginText(id){
	var urlStr=null;
	var obj = document.getElementById(id);
	if(obj!=null){//
		urlStr = obj.getAttribute("text");
	}
	return urlStr;
}
/*
 * 添加开头 和结尾
 */
function addTopEnd(){
	/*
	dragobj.endH = document.createElement("h1");
	dragobj.endH.setAttribute("name","endH1Name");
	dragobj.endH.innerHTML="<br/>";
	document.body.appendChild(dragobj.endH);
	
	dragobj.topH = document.createElement("h1");
	dragobj.topH.setAttribute("name","topH1Name");
	dragobj.topH.innerHTML="<br/>";
	var firstNode = document.body.childNodes[0];
	document.body.insertBefore(dragobj.topH,firstNode);
	*/
	
}
/*
 * 删除 开始和结尾
 */
function delTopEnd(){
	/*ZU.removeChild(dragobj.topH);
	ZU.removeChild(dragobj.endH);
	*/
}
/*
 * 获取 位置层 下一个元素
 */
function getotempNext(){
	var nextNode = null;
	if(dragobj.otemp!=null){
		nextNode = dragobj.otemp.nextSibling;
	}
	return nextNode;
	
}
/*
 * 替换内容
 */
function contentReplace(content){
	return content.replace(/(<p><\/p>\s*)+/gi,"<p><br/></p>");
}

