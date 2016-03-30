/*
 * 全局变量 dragobj.o 拖动层
 */
var dragobj={
	parentDivId:"",//拖动层父元素
	firstNode:null,//被拖动的元素
	dragNode:null,//拖动的元素
	scrollNode:null,//滚动框的元素
	omNode:null,//插入的
	omflag:false,//是否有插入框
	xy:null,//拖动元素的位置,拖动是初始化
	xx:null,//推动元素的相对位置 拖动时初始化
	location:"",//位置
	locationNode:""//定位的元素
};
//鼠标弹起
//document.onmouseup=mouseup;
function mouseup(){
	console.info("鼠标弹起");
	if(!(dragobj.dragNode!=null)){
		return false;//移动元素为空,退出
	}
	ZU.removeChild(dragobj.dragNode);//删除拖动的元素
	if(dragobj.omflag){//存在插入框
		dragobj.omNode.parentNode.insertBefore(dragobj.firstNode,dragobj.omNode);
		dragobj.firstNode.style.display="";
		ZU.removeChild(dragobj.omNode);
		console.info("location="+dragobj.location);
		//console.info("locationNode.id="+dragobj.locationNode.id+",location="+dragobj.location);
	}else{
		ZU.removeChild(dragobj.firstNode);//删除
		console.info("删除元素");
	}
	document.onmousemove=function(){};
	dragobj={};
	document.onmouseup=function(){};
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
	e=ZU.getMouseXY(e);//获取鼠标的位置
	if(dragobj.dragNode==null) return false;
	//在范围内,才显示定位
    dragobj.dragNode.style.left=(e.x-dragobj.xx[0])+"px";
    dragobj.dragNode.style.top=(e.y-dragobj.xx[1])+"px";
    
	console.info("e.x="+e.x+",e.y="+e.y);
	console.info("left="+dragobj.parentDivXY.left+",top="+dragobj.parentDivXY.top); 
	if((e.x>dragobj.parentDivXY.left)&&(e.x<(dragobj.parentDivXY.left+dragobj.parentDivXY.width))&&(e.y>dragobj.parentDivXY.top)&&(e.y<dragobj.parentDivXY.top+dragobj.parentDivXY.height)){
		console.info("范围内");
		moveList(e);
	}else{
		ZU.removeChild(dragobj.omNode);
		dragobj.omflag=false;
		//dragobj.location="out";
		console.info("范围外");
	}
}
/**
 * 元素的过滤
 * @param e 鼠标的位置
 */
function moveList(e){
	//console.info(dragobj.parentDiv.getElementsByTagName("li"));
	var nodelist = dragobj.parentDiv.getElementsByTagName("li");
	for(var i=0;i<nodelist.length;i++){
        var pnode=nodelist[i];
        //console.info("pnode="+pnode.getAttribute("name"));
 		if(pnode == null)    continue;//已经移出的层不再遍历
 		var bJson=ZU.innerJson(pnode,e);
 		if(bJson.out) continue;//元素外面 继续
 		if(bJson.left){//元素前
 		 	pnode.parentNode.insertBefore(dragobj.omNode,pnode);
 		 	dragobj.omflag=true;//有插入框
 		 	dragobj.location="before";
 		 	dragobj.posNode = pnode;
 		}else if(bJson.right){//元素后
 			if(pnode.nextSibling==null){
 				pnode.parentNode.appendChild(dragobj.omNode);
 			}else{
 				pnode.parentNode.insertBefore(dragobj.omNode,pnode.nextSibling);
 			}
 			dragobj.omflag=true;//有插入框
 		 	dragobj.location="after";
 		 	dragobj.locationNode = pnode;
 		}
 		//console.info("dragobj.location="+dragobj.location+",pnode.innerHTML="+pnode.innerHTML);
        return false;
	}
}
/**
 * 栏目拖动
 * @param e
 * @param obj
 */
function pmousedown(e,obj){
	try{
		e.preventDefault();
		}catch(e1){}
	document.onmouseup=mouseup;
	if(dragobj.dragElement!=null) return false;
	document.onmousemove=mousemove;
	dragobj.firstNode=obj;
	e=ZU.getMouseXY(e);//鼠标的位置
	//console.info("pmousedown"+"鼠标点击元素"+obj.id.split("_")[1]);
	dragobj.xy = ZU.getNodeOffset(obj,null);//元素的位置
	dragobj.xx=new Array((e.x-dragobj.xy.left),(e.y-dragobj.xy.top));//0 为 元素定位
	//console.info("e.y="+e.y+",dragobj.xy.top="+dragobj.xy.top+",dragobj.xx[1]="+dragobj.xx[1]);
	var dragnode=document.createElement("div");
	dragnode.setAttribute("id", obj.id+"_"+new Date().getTime());
	dragnode.innerHTML=obj.innerHTML;
	dragnode.style.left=(dragobj.xy.left)+"px";
	dragnode.style.top=(dragobj.xy.top)+"px";
	dragnode.style.height=(dragobj.xy.height)+"px";
	dragnode.style.position="absolute";
	dragnode.style.zIndex =99901;
	dragnode.style.background="#fff";//99bce8
	document.body.appendChild(dragnode);
	dragobj.dragNode = dragnode;
	var dragDiv = ZU.$id("dragDiv");
	dragobj.parentDiv=dragDiv;//获取拖动父元素
	dragobj.parentDivXY = ZU.getNodeOffset(dragobj.parentDiv);
	//插入框
	var omNode=obj.cloneNode(true);
	omNode.style.height="85px";
	omNode.innerHTML="插入层";
	dragobj.omNode=omNode;
	obj.parentNode.insertBefore(dragobj.omNode,obj);
	//原始元素隐藏
	dragobj.firstNode.style.display="none";
	dragobj.omflag=true;
	
}