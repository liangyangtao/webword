/*
text:名字
order:顺序。
className: add:折叠+,del:展开, space:无没有子元素
dispaly: add|none:隐藏,其他:展开
*/
var treeTimer=0;//树的定时器
/*
 * id:0:默认 内容插件的ID
 * text:名字
 * className:  add 是折叠的,del是展开的,''是空的
 * order: h1中的1
 * icon:图标 '':是没有图标,'normal':普通图标，'plugin':内容图标.
 */
var treeJson = [{
            'id':'1',
            'text':'标题1',
			'className':'',
			'icon':'normal',
            'order':'1'
        },{
			'id':'2',
			'text':'断节点1',
			'className':'add',
			'icon':'normal',
			'order':'3'
		},{
			'id':'3',
			'text':'断节点1-1',
			'className':'add',
			'icon':'normal',
			'order':'4'
		},{
			'id':'4',
			'text':'断节点2',
			'className':'',
			'icon':'normal',
			'order':'3'
		},{
			'id':'5',
			'text':'标题1-1',
			'className':'add',
			'icon':'normal',
			'order':'2'
		},{
			'id':'6',
			'text':'标题1-1-1',
			'className':'',
			'icon':'normal',
			'order':'3'
		},{
			'id':'7',
			'text':'标题1-1-1-1',
			'className':'',
			'icon':'normal',
			'order':'4'
		},{
			'id':'8',
			'text':'标题1-1-1-1-1',
			'className':'',
			'icon':'normal',
			'order':'5'
		},{
			'id':'9',
			'text':'标题1-1-1-2',
			'className':'',
			'icon':'normal',
			'order':'4'
		},{
			'id':'10',
			'text':'标题1-1-2',
			'className':'',
			'icon':'normal',
			'order':'3'
		},{
			'id':'11',
			'text':'标题1-2',
			'className':'',
			'icon':'normal',
			'order':'2'
		},{
			'id':'12',
			'text':'标题1-2-1',
			'className':'',
			'icon':'normal',
			'order':'3'
		},{
			'id':'13',
			'text':'标题1-3',
			'className':'',
			'icon':'normal',
			'order':'2'
		},{
			'id':'14',
			'text':'标题2',
			'className':'',
			'icon':'normal',
			'order':'1'
		}
	];
//只有树结构
function zhuTreeStr(name,scrollId){
	var me = this;
	this.name = name;//生成目录树的div
	this.scrollId=scrollId;
	this.treeDiv;//目录树
	this.setJson=function (json){
		this.json=json;//设置json数据
	};
	/*根据order新建tr的node节点
	 * @parameter ndoe 需要生成的节点json
	 * @return 表格的tr节点
	 */
	this.createTrNode = function(node){
		var tr = document.createElement("tr");
		var title="0_"+node.order+"_"+node.idStr+"_"+this.name;
		tr.id="tr_"+title;
		if(node.display =="add"||node.display =="none"){
			tr.style.display = "none";
		}
		var td = document.createElement("td");
		for(var k=0;k<node.order;k++){
			var img = document.createElement("img");
			img.className="tree_space";
			img.src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
			td.appendChild(img);
		}
		//生成节点图标
		var imgIcon = document.createElement("img");
			if(node.icon==undefined){
				imgIcon.className="normal";
			}else{
				imgIcon.className="normal";//node.icon;
			}
			imgIcon.src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
			td.appendChild(imgIcon);
		
		var span = document.createElement("span");
			span.className="treeSpan";
			span.id ="span_"+node.idStr+"_"+this.name;
			span.innerHTML=node.text;
			span.title=node.text;
			//span.innerHTML=node.text.substring(0,12);/*20150818 treespan 标题显示不全*/
			span.setAttribute("onmousedown","pmousedown(event,this,\'articleNode\',\'"+this.scrollId+"\')");
			/*
			span.onmousedown=function(){
				onmousedown="" 
			}*/
			/*
			span.ondblclick= function(){
				me.spanOndblclick(this);
			};*/
			td.appendChild(span);
		//预览的图片
		var imgView = document.createElement("img");
			imgView.className = "imgView";
			imgView.src = "word/img/poi.png";
			imgView.title = '预览';
			td.appendChild(imgView);
			
		if(node.dispaly=="none"){
			tr.style.display ="none";
		}
		var endImg = ZU.getEnd2Img(td);//最后一个图片
		if(node.className=="add"){
			endImg.className="img_tree_add";
		}else if(node.className=="del"){
			endImg.className="img_tree_del";
		}
		tr.appendChild(td);
		return tr;
	};
	this.divClick=function(obj){
		//console.info("文档树divClick:obj.id = "+obj.id);
		//console.info("obj.id="+obj.id);//会触发两次
		var e=e||window.event;
		var obj1=e.target||e.srcElement;
		//console.info("点击的元素="+obj1);
		if(obj1.className=="img_tree_add"){//展开子元素
			this.treeDisplay(obj1,"add");
		}else if(obj1.className=="img_tree_del"){//折叠此元素
			this.treeDisplay(obj1,"del");
		}else if(obj1.className=="imgView"){//预览的图片
			var trNode = obj1.parentNode.parentNode;
			var articleId=ZU.getSplitArticleId(trNode);//tr节点
			var hId=ZU.getSplitId(trNode);
			var hName=ZU.getSplitOrder(trNode);
			pre_mydocumet_list("节点预览",articleId,hId,hName);
		}else{
			//console.info("divClick:不处理");
		}
		
	};
	//折叠树节点
	this.treeDisplay=function(obj,type){
		var trOrder=0;
		var trNodeNext;
		if(obj!=null){
			var trNode = obj.parentNode.parentNode;
			trOrder = parseInt(trNode.id.split("_")[2],10);//点击元素的tr
			trNodeNext=trNode.nextSibling;
		}else{//obj是空元素，则作用整个
			var trList = this.table.getElementsByTagName("tr");
			if(trList.length>0){
				trNodeNext=trList[0];
			}else{//没有tr则退出
				return null;
			}
			
		}
		if(type=="add"){//展开元素 这个比较难
			if(trNodeNext!=null){
				if(obj!=null){
					this.addMap[ZU.getSplitId(trNode)]=undefined;
					//delete this.addMap[ZU.getSplitId(trNode)];
					obj.className = "img_tree_del";
				}
			}
			var flag=true;
			var flagOrder=0;
			var preorder = trOrder;//等于点击的tr
			var depth=1;
			while(trNodeNext){
				var nextOrder = parseInt(trNodeNext.id.split("_")[2]);//获得order
				if(trOrder<nextOrder){//属于展开节点的子节点
					var endImg = ZU.getEnd2Img(trNodeNext);//获取最后一个的图片的标识
					//console.info("flagOrder="+flagOrder+",nextOrder="+nextOrder+",flagOrder="+flagOrder+",flag="+flag);
					if(nextOrder<=flagOrder){//下一个节点 是最近add节点的父或者同级
						//console.info("+++");
						flag=true;
					}
					if(flag){//展开，显示此元素
						trNodeNext.style.display="";
					}else{
						trNodeNext.style.display="none";
					}
					if(endImg.className=="img_tree_add"){//不展开
						//pid.push(false);
						flag=false;
						if(flagOrder==0){
							flagOrder=nextOrder;
						}else if(flagOrder>nextOrder){
							flagOrder = nextOrder;
						}
					}else if(endImg.className=="img_tree_del"){//img_tree_del
						if(nextOrder<=flagOrder){//下一个节点 是最近add节点的父或者同级
							flag =true;
							flagOrder=0;
						}
					}else{
						//pid.push(false);
					}
					trNodeNext=trNodeNext.nextSibling;
				}else{
					//console.info("退出循环 ,depth="+depth+"pid="+pid);
					break;
				}
			}
		}else if(type=="del"){//折叠元素
			if(trNodeNext!=null){
				if(obj!=null){
					this.addMap[ZU.getSplitId(trNode)]="add";
					obj.className = "img_tree_add";
				}
			}
			while(trNodeNext){
				var order = parseInt(trNodeNext.id.split("_")[2],10);
				if(order>trOrder){//大于现在的节点
					trNodeNext.style.display="none";
					trNodeNext=trNodeNext.nextSibling;
				}else{
					console.info("退出循环");
					break;
				}
			}
		}
	};
	//动态生成目录树
	this.show=function(){
		this.addMap ={};
		var treeDiv = document.getElementById(this.name);
		this.treeDiv = treeDiv;
		treeDiv.innerHTML="";
		treeDiv.onclick = function(){ me.divClick(treeDiv);};
		var oFragment=document.createDocumentFragment();
		var dptable =document.createElement("table");
			dptable.id = this.name+"_table";
	    	dptable.setAttribute("border", "0");
        	dptable.setAttribute("cellSpacing", "0");
        	dptable.setAttribute("cellPadding", "0");
        	dptable.setAttribute("width", "100%");
		this.table = dptable;
		for(var i=0;i<this.json.length;i++){
			var node = this.json[i];
			var nexti=i+1;
			if(nexti<this.json.length){//存在下一个节点
				if(this.json[nexti].order>node.order){//下一个节点是上一个节点的子元素
					if(node.className=="add"){
						
					}else{
						node.className="del";
					}
				}else{
					if(node.className=="add"){
						node.className="";
					}
					//node.className="tree_space";
				}
			}else{
				//node.className="tree_space";
			}
			node.dispaly="";
			if(node.className=="add"){
				this.addMap[node.idStr]="add";//保存所有的折叠节点
			}
			var tr = this.createTrNode(node);
			dptable.appendChild(tr);
		}
		var divNode = document.createElement("div");
		divNode.className="treeDiv";
		divNode.appendChild(dptable);
		//console.info("dptable.innerHTML="+dptable.innerHTML);
		oFragment.appendChild(divNode);
		treeDiv.appendChild(oFragment);
		this.treeDisplay(null,"add");//展开元素
	};
}
//树的结构
function zhuTree(name){
	var me = this;
	this.name = name;//生成目录树的div
	this.trClickId = 0;//点击的行
	this.rightMenuDiv = null;//右键菜单
	this.addMap ={};// id:className add 所有的折叠元素
	this.editFlag= false;//true:单个标题处于编辑状态
	this.treeDiv;//目录树
	this.fengMianDiv=null;
	//console.info("this.name="+this.name);
	this.setJson=function (json){
		this.json=json;//设置json数据
	};
	this.getJson=function (){
		return this.json;
	};
	//动态生成目录树
	this.show=function(){
		this.addMap ={};
		var treeDiv = document.getElementById(this.name);
		this.treeDiv = treeDiv;
		treeDiv.innerHTML="";
		treeDiv.onclick = function(){ me.divClick(treeDiv);};
		//鼠标在上面
		document.onclick = function(){
			//隐藏右键菜单
			me.rightMenuHidden();
		};
		//鼠标到达
		treeDiv.onmouseover = function(){
			document.oncontextmenu = function(e){
				//console.info("document右键菜单 return false");
				return false;
			};
			//鼠标点击,隐藏右键菜单
			document.onmousedown = function(){
				//console.info("右键鼠标到达");
				//me.rightMenuHidden();
			};
		};
		//鼠标离开
		treeDiv.onmouseout = function(){
			document.oncontextmenu = function(){
				//console.info("document右键菜单");
			};
			//鼠标点击,隐藏右键菜单
			document.onmousedown = function(){
				//console.info("右键鼠标离开");
				me.rightMenuHidden();
			};
		};
		//div的右键菜单
		treeDiv.oncontextmenu = function(event){
			me.rightMenuDispaly(event);
			//stopBubble(e);
		};
		//初始化菜单
		this.rightMenuInt();
		var oFragment=document.createDocumentFragment();
		var dptable =document.createElement("table");
			dptable.id = this.name+"_table";
	    	dptable.setAttribute("border", "0");
        	dptable.setAttribute("cellSpacing", "0");
        	dptable.setAttribute("cellPadding", "0");
        	dptable.setAttribute("width", "100%");
		this.table = dptable;
		for(var i=0;i<this.json.length;i++){
			var node = this.json[i];
			var nexti=i+1;
			if(nexti<this.json.length){//存在下一个节点
				if(this.json[nexti].order>node.order){//下一个节点是上一个节点的子元素
					if(node.className=="add"){
						
					}else{
						node.className="del";
					}
				}else{
					if(node.className=="add"){
						node.className="";
					}
					//node.className="tree_space";
				}
			}else{
				//node.className="tree_space";
			}
			node.dispaly="";
			if(node.className=="add"){
				this.addMap[node.idStr]="add";//保存所有的折叠节点
			}
			var tr = this.createTrNode(node);
			dptable.appendChild(tr);
		}
		var divNode = document.createElement("div");
		divNode.className="treeDiv";
		divNode.appendChild(dptable);
		oFragment.appendChild(divNode); 
		treeDiv.appendChild(oFragment);
		this.treeDisplay(null,"add");//展开元素
	};
	//右键菜单初始化
	this.rightMenuInt = function(){
		var divObj = document.createElement("div");
		//divObj.id = "menu";
		divObj.className = "menu";
		var uiObj = document.createElement("ul");
		var liObj = document.createElement("li");
			liObj.innerHTML = "<a href=\"javascript:void(0)\">添加子节点</a>";
		liObj.onclick = function(){
			tree_insertChild();
		};
		uiObj.appendChild(liObj);
		
		var liObj1 = document.createElement("li");
		liObj1.innerHTML = "<a href=\"javascript:void(0)\">添加节点之前</a>";
		liObj1.onclick = function(){
			tree_insertBefore();
		};
		uiObj.appendChild(liObj1);
		
		var liObj2 = document.createElement("li");
		liObj2.innerHTML = "<a href=\"javascript:void(0)\">添加节点之后</a>";
		liObj2.onclick = function(){
			tree_insertNext();
		};
		uiObj.appendChild(liObj2);

		var liObj3 = document.createElement("li");
		liObj3.innerHTML = "<a href=\"javascript:void(0)\">删除</a>";
		liObj3.onclick = function(){
			tree_del();//删除节点
		};
		uiObj.appendChild(liObj3);

		var liObj4 = document.createElement("li");
		liObj4.innerHTML = "<a href=\"javascript:void(0)\">重命名</a>";
		liObj4.onclick = function(){
			tree_dblclick();
		};
		uiObj.appendChild(liObj4);
		
		var liObj5 = document.createElement("li");
		liObj5.innerHTML = "<a href=\"javascript:void(0)\">内容插件</a>";
		liObj5.onclick = function(){
			tree_contentPlugin();
		};
		uiObj.appendChild(liObj5);
		
		
		divObj.appendChild(uiObj);
		this.treeDiv.appendChild(divObj);
		//document.body.appendChild();
		this.rightMenuDiv = divObj;
		
	};
	//右键菜单显示
	this.rightMenuDispaly = function(event){
			var e = event || window.event||arguments.callee.caller.arguments[0];
			var mymenu = this.rightMenuDiv;

			mymenu.style.display = "block";
			//如果右键菜单超出屏幕范围，左下角出现在鼠标点击位置
			var t = e.clientY + 5 ;
			if(t + mymenu.offsetHeight > document.documentElement.clientHeight){
				t -= mymenu.offsetHeight;
			}
			mymenu.style.left = e.clientX + 5 +"px";
			mymenu.style.top = t + "px";
			//console.info("e.clientY="+e.clientY);
	};
	//右键隐藏
	this.rightMenuHidden = function(){
		//console.info("菜单隐藏");
		var mymenu = this.rightMenuDiv;
		if(mymenu.style.display!="none"){
			mymenu.style.display = "none";
		}
	};
	/*
	 * trNewNode的Display是否隐藏
	 * trNod存在,标识trNewNode还没有加入到document中
	 * 找到trNewNode 上一个父节点是否是隐藏和显示
	 */
	
	this.updateDisplayNode = function(trNewNode,trNode){
		var trPreNode=trNewNode.previousSibling;
		if(trNode!=null) trPreNode=trNode;
		var minOrder=10;
		var flag = false;
		if(trPreNode!=null){
			minOrder= ZU.getSplitOrder(trNewNode);
			var order = ZU.getSplitOrder(trPreNode);
			if(order == minOrder){//新节点和上一个节点 order相同,且上一个节点是隐藏的，新节点也隐藏
				if(trPreNode.style.display=="none"){
					trPreNode=null;
					flag = true;
				}
			}
		}
		while(trPreNode){
			var order = ZU.getSplitOrder(trPreNode);
			var trPreImg = ZU.getEnd1Img(trPreNode);
			if(minOrder>order){
				minOrder=order;
				if(trPreNode.style.display=="none"||trPreImg.className=="img_tree_add"){
					flag = true;
					break;
				}
			}
			//if(minOrder==1) break;
			trPreNode = trNewNode.previousSibling;
		}
		if(flag){
			trNewNode.style.display = "none";
		}
	};
	/*
	 * 根据node的order更新节点
	 * @param node 更新节点的结构
	 */
	this.updateNode = function(node){
		var spanNode = document.getElementById("span_"+node.idStr);
		var trOldNode = spanNode.parentNode.parentNode;//获取大纲节点
		var trOldOrder = ZU.getSplitOrder(trOldNode);
		
		if(trOldOrder==node.order){//只修改标题名字
			if(spanNode.innerHTML!=node.text){
				spanNode.innerHTML = node.text;
			}
		}else{
			//console.info("节点的order变化");
			var trNode = trOldNode.previousSibling;//上一个节点
			var parentId = null;
			if(trNode!=null){
				parentId = ZU.getSplitId(trNode);
			}
			this.delNodeOne(trOldNode);
			//ZU.removeChild(trOldNode);//删除原来的节点
			this.insertNode(node,parentId);
		}
	};
	/*根据order新建tr的node节点
	 * @parameter ndoe 需要生成的节点json
	 * @return 表格的tr节点
	 */
	this.createTrNode = function(node){
		var tr = document.createElement("tr");
		var title="0_"+node.order+"_"+node.idStr;
		tr.id="tr_"+title;
		tr.onmousedown = function (){
			me.trClick(this);
		};
		tr.onmouseover = function(){
			me.trMouserOver(this);
		};
		tr.onmouseout = function(){
			me.trMouserOut(this);
		};
		if(node.display =="add"||node.display =="none"){
			tr.style.display = "none";
		}
		var td = document.createElement("td");
		td.ondblclick= function(){
			var evt = evt || window.event;
			//evt.preventDefault();
			var obj1=evt.target||evt.srcElement;
			//console.info("obj1="+obj1);
			if(obj1.nodeName=="TD"||obj1.nodeName=="SPAN"){
					var obj=this.getElementsByTagName("span")[0];
					me.spanOndblclick(obj);
			 }
			return false;
		};
		for(var k=0;k<node.order;k++){
			var img = document.createElement("img");
			img.className="tree_space";
			img.src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
			td.appendChild(img);
		}
		//生成节点图标
		if(node.idStr.toString().split("-").length>1){
			node.icon="tree_plugin";
		}
		var imgIcon = document.createElement("img");
			if(node.icon==undefined){
				imgIcon.className="normal";
			}else{
				imgIcon.className=node.icon;
			}
			imgIcon.src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
			td.appendChild(imgIcon);
		
		var span = document.createElement("span");
			span.className="treeSpan";
			span.id ="span_"+node.idStr;
			span.innerHTML=node.text;
			span.title=node.text;
			/*
			span.ondblclick= function(){
				me.spanOndblclick(this);
			};*/
			td.appendChild(span);
		if(node.dispaly=="none"){
			tr.style.display ="none";
		}
		var endImg = ZU.getEnd1Img(td);//最后一个图片
		if(node.className=="add"){
			endImg.className="img_tree_add";
		}else if(node.className=="del"){
			endImg.className="img_tree_del";
		}
		tr.appendChild(td);
		return tr;
	};
	//插入节点之后,返回 插入的节点
	this.insertNext = function (node,id){
		if(!(id!=null)) id = ZU.getSplitId(this.trClickId);// return false;//如果id为null,默认是点击的节点。
		var trNode = document.getElementById("span_"+id).parentNode.parentNode;//点击tr元素
		if(!(trNode!=null)) return false;
		var trOrder = ZU.getSplitOrder(trNode);
		node.order = trOrder;
		var nextTrNode = this.getNextTrNode(trNode);//获取他的下一个同级和上级节点
		if(nextTrNode!=null){
			this.htmlInsert(node,nextTrNode);
		}else{//没有元素
			this.htmlInsert(node);
		}
		return nextTrNode;
	};
	/*
	 * 插入节点之前
	 * @param node:插入节点的json结构
	 * @param id:节点的id
	 */
	this.insertBefore =  function(node,id){
		if(!(id!=null)) id = ZU.getSplitId(this.trClickId);// return false;//如果id为null,默认是点击的节点。
		var trNode = document.getElementById("span_"+id).parentNode.parentNode;//点击的元素
		if(!(trNode!=null)) return false;
		var trOrder = ZU.getSplitOrder(trNode);
		node.order = trOrder;
		this.htmlInsert(node,trNode);
		return trNode;
	};
	//添加到节点的第一个节点 id : span的id
	this.insertChildFirst = function(node,id){
		var trNode = document.getElementById("span_"+id).parentNode.parentNode;//点击tr元素
		if(!(trNode!=null)) return false;
		var trOrder = ZU.getSplitOrder(trNode);
		node.order = trOrder+1;
		if(node.order>6||node.order<1) {
			msgShow("仅支持6级节点");
			return false;
		}
		var endImg = ZU.getEnd1Img(trNode);
		if(endImg.className=="tree_space"){
			endImg.className ="img_tree_del";
			node.display="del";
		}else if(endImg.className=="img_tree_add"){
			node.display="add";
		}else if(endImg.className =="img_tree_del"){
			node.display="del";
		}
		if(trNode.style.display=="none") node.dispaly="none";
		var nextTrNode = trNode.nextSibling;
		this.htmlInsert(node,nextTrNode);//插入到next节点之前
		return nextTrNode;//返回下一个几点，null 就是没有下以一个节点
		
	};
	/*
	 *  根据id,插入子节点
	 *  @param id:节点的Id
	 *  @param node 插入节节点 id:先节点的ID,text:节点的名称,order:大纲等级
	 *  @return nextTrNode:下一个同级或者父级节点。
	 */
	this.insertChild = function(node,id){//
		if(!(id!=null)) id = ZU.getSplitId(this.trClickId);// return false;//如果id为null,默认是点击的节点。
		var trNode = document.getElementById("span_"+id).parentNode.parentNode;//点击的元素
		if(!(trNode!=null)) return false;
		var trOrder = ZU.getSplitOrder(trNode);
		node.order = trOrder+1;
		if(node.order>6||node.order<1) {
			msgShow("仅支持6级节点");
			return false;
		}
		var endImg = ZU.getEnd1Img(trNode);
		if(endImg.className=="tree_space"){
			endImg.className ="img_tree_del";
			node.display="del";
		}else if(endImg.className=="img_tree_add"){
			node.display="add";
		}else if(endImg.className =="img_tree_del"){
			node.display="del";
		}
		if(trNode.style.display=="none") node.dispaly="none";
		var nextTrNode = this.getNextTrNode(trNode);
		this.htmlInsert(node,nextTrNode);//插入到next节点之前
		return nextTrNode;//返回下一个几点，null 就是没有下以一个节点
	};
	
	/*
	 * 插入到前面一个
	 * @param node:插入节点
	 * @param nextTrNode:之前的节点
	 */
	this.htmlInsert = function(node,nextTrNode){
		var tr = this.createTrNode(node);
		if(nextTrNode!=null){
			nextTrNode.parentNode.insertBefore(tr,nextTrNode);
		}else{//不存在下一个节点
			this.table.appendChild(tr);
		}
	};
	/*
	 * 只删除一个节点，相邻节点间变化className
	 * @param obj object:代表节点间，string:代表Id
	 */
	this.delNodeOne = function(obj){
		if(typeof(obj)=="object"){
			
		}else {
			var spanNode = document.getElementById("span_"+obj);
			if(spanNode!=null){
				obj= spanNode.parentNode.parentNode;
			}else{
				//console.info("没有此元素");
				return 
			}
		}
		var trPreNode = obj.previousSibling;//上一个元素
		var trNextNode = obj.nextSibling;//下一个元素
		
		var parentPreTr = this.getParentPreTr(obj);//得到他的父节点
		var parentNextTr = this.getNextTrNode(obj);//obj的下一个同级或者父节节点
		if(!(parentPreTr!==null)) {//没有父节点
			this.treeDisplay(ZU.getEnd1Img(obj), "add");//展开此节点
			ZU.removeChild(obj);//删除节点
			return false;
		}
		var objDisplay = obj.style.display;//元素的状态
		var objImgClass = ZU.getEnd1Img(obj).className;//节点的className
		
		ZU.removeChild(obj);//删除节点
		if(trPreNode!=null){//存在上一个元素
			var trPreImg= ZU.getEnd1Img(trPreNode);
			var trPreOrder = ZU.getSplitOrder(trPreNode);
			if(trNextNode!=null){//存在下一个元素
				var trNextImg = ZU.getEnd1Img(trNextNode);
				var trNextOrder = ZU.getSplitOrder(trNextNode);
				
				if(trPreOrder<trNextOrder){//上一个元素是下一个元素的父
					if(trPreImg.className =="tree_space"){
						if(trNextNode.style.display ==="none"){//子元素是隐藏,节点变成收缩
							trPreImg.className = "img_tree_add";
						}else{
							trPreImg.className = "img_tree_del";
						}
						
					}
				}
				
			}else{//不存在下一个节点
				trPreImg.className = "tree_space";
			}
		}else{//不存在上一个节点
			
		}
		if(parentPreTr!=null){//存在父节点
			var parentPreTrDisplay = parentPreTr.style.display;
			var parentPreTrClass = ZU.getEnd1Img(parentPreTr).className;
			if(parentPreTrDisplay=="none"||parentPreTrClass=="img_tree_add"){
				//父节点是隐藏，删除元素所有子节点都要隐藏
				if(objDisplay=="none"||objImgClass=="img_tree_add"){
					//删除的节点是隐藏的不处理
					return ;
				}
				var parentId=0;
				if(parentNextTr!=null){//存在下一个同级和父级节点
					parentId = ZU.getSplitId(parentNextTr);
				}
				while(trNextNode){//下一个节点和
					var trNextId= ZU.getSplitId(trNextNode);
					if(parentId==0||(parentId!=trNextId)){
						trNextNode.style.display ="none";
					}else{
						//console.info("退出循环");
						break;
					}
					trNextNode = trNextNode.nextSibling;
				}
			}else{//父节点不是隐藏的
				//this.treeDisplay(ZU.getEnd1Img(parentPreTr), "del");//折叠此元素
				this.treeDisplay(ZU.getEnd1Img(parentPreTr), "add");//展开此元素
			}
		}
	};
	//删除目录树节点,返回开始节点 和结束节点的ID
	this.delNode = function(){
		var delId = [];//
		delId.startId = this.trClickId.split("_")[3],10;;
		if(this.trClickId!=0){
			var trClickNode = document.getElementById(this.trClickId);//点击的元素
			var preTrNode = trClickNode.previousSibling;//上一个元素
			var delNodeArray = [];
			var trOrder = parseInt(trClickNode.id.split("_")[2],10);//点击元素的order
			while(trClickNode){
				delNodeArray.push(trClickNode);
				trClickNode= trClickNode.nextSibling;
				if(trClickNode!=null){//存在下一个元素
					var nextOrder = parseInt(trClickNode.id.split("_")[2],10);
						if(nextOrder<=trOrder){
							delId.endId = trClickNode.id.split("_")[3];
							//console.info("同级或者上级,退出");
							break;//遍历到同级或者上级,就退出
						}
				}else{
					delId.endId=0;
					//console.info("没有下一个节点退出");
				}
			}
			//删除的数组的元素
			for(var i=0;i<delNodeArray.length;i++){
				ZU.removeChild(delNodeArray[i]);
			}
			if(preTrNode!=null){// 如果上级元素是展开的，删除后，上级元素没有子节点，修改del为space状态
				var preTrOrder = parseInt(preTrNode.id.split("_")[2],10);
				if(preTrOrder<trOrder){//删除的元素属于他的子集
					var preNextTrNode = preTrNode.nextSibling;//上一个的下个元素
					if(preNextTrNode!=null){
						var preNextTrOrder = parseInt(preNextTrNode.id.split("_")[2],10);
						if(preNextTrOrder<=preTrOrder){
							var imgList = preTrNode.getElementsByTagName("img");
							var endImg = imgList[imgList.length-1];//获取最后一个的图片的
							if(endImg.className=="img_tree_del"){
								endImg.className = "tree_space";
							}
						}
					}
				}
			}
			this.trChildId = 0;
		}//if
		return delId;
	};
	//鼠标在上面
	this.trMouserOver=function(obj){
		if(obj.className!="tree_tr_click"){
			obj.className = "tree_tr_mouserover";
		}
	};
	//鼠标离开
	this.trMouserOut = function(obj){
		if(obj.className!="tree_tr_click"){
			obj.className = "";
		}
	};
	//鼠标点击行,颜色变化
	this.trClick = function(obj){
		//console.info("trClick="+obj.id);
		if(this.trClickId!=0){
			var trClickNode = document.getElementById(this.trClickId);
			if(trClickNode!=null){
				trClickNode.className = ""; 
			}
		}
		obj.className = "tree_tr_click";
		this.trClickId = obj.id;
		tree_location(obj);
		this.fengMianDiv.className ="";
	};
	//点击动作,发生折叠动作
	this.divClick=function(obj){
		//console.info("divClick:obj.id = "+obj.id);
		//console.info("obj.id="+obj.id);//会触发两次
		//关闭右键菜单
		this.rightMenuHidden();
		var e=e||window.event;
		var obj1=e.target||e.srcElement;
		//console.info("点击的元素="+obj1);
		if(obj1.className=="img_tree_add"){//展开子元素
			this.treeDisplay(obj1,"add");
		}else if(obj1.className=="img_tree_del"){//折叠此元素
			this.treeDisplay(obj1,"del");
		}else{
			//console.info("divClick:不处理");
		}
		
	};
	//折叠树节点
	this.treeDisplay=function(obj,type){
		var trOrder=0;
		var trNodeNext;
		if(obj!=null){
			var trNode = obj.parentNode.parentNode;
			trOrder = parseInt(trNode.id.split("_")[2],10);//点击元素的tr
			trNodeNext=trNode.nextSibling;
		}else{//obj是空元素，则作用整个
			var trList = this.table.getElementsByTagName("tr");
			if(trList.length>0){
				trNodeNext=trList[0];
			}else{//没有tr则退出
				return null;
			}
			
		}
		if(type=="add"){//展开元素 这个比较难
			if(trNodeNext!=null){
				if(obj!=null){
					this.addMap[ZU.getSplitId(trNode)]=undefined;
					//delete this.addMap[ZU.getSplitId(trNode)];
					obj.className = "img_tree_del";
				}
			}
			var flag=true;
			var flagOrder=0;
			var preorder = trOrder;//等于点击的tr
			var depth=1;
			while(trNodeNext){
				var nextOrder = parseInt(trNodeNext.id.split("_")[2]);//获得order
				if(trOrder<nextOrder){//属于展开节点的子节点
					var endImg = ZU.getEnd1Img(trNodeNext);//获取最后一个的图片的标识
					//console.info("flagOrder="+flagOrder+",nextOrder="+nextOrder+",flagOrder="+flagOrder+",flag="+flag);
					if(nextOrder<=flagOrder){//下一个节点 是最近add节点的父或者同级
						//console.info("+++");
						flag=true;
					}
					if(flag){//展开，显示此元素
						trNodeNext.style.display="";
					}else{
						trNodeNext.style.display="none";
					}
					if(endImg.className=="img_tree_add"){//不展开
						//pid.push(false);
						flag=false;
						if(flagOrder==0){
							flagOrder=nextOrder;
						}else if(flagOrder>nextOrder){
							flagOrder = nextOrder;
						}
					}else if(endImg.className=="img_tree_del"){//img_tree_del
						if(nextOrder<=flagOrder){//下一个节点 是最近add节点的父或者同级
							flag =true;
							flagOrder=0;
						}
					}else{
						//pid.push(false);
					}
					trNodeNext=trNodeNext.nextSibling;
				}else{
					//console.info("退出循环 ,depth="+depth+"pid="+pid);
					break;
				}
			}
		}else if(type=="del"){//折叠元素
			if(trNodeNext!=null){
				if(obj!=null){
					this.addMap[ZU.getSplitId(trNode)]="add";
					obj.className = "img_tree_add";
				}
			}
			while(trNodeNext){
				var order = parseInt(trNodeNext.id.split("_")[2],10);
				if(order>trOrder){//大于现在的节点
					trNodeNext.style.display="none";
					trNodeNext=trNodeNext.nextSibling;
				}else{
					//console.info("退出循环");
					break;
				}
			}
		}
	};
	//获取节点的 下一个同级或者上级的元素
	this.getNextTrNode = function (trNode){
		if(!(trNode!=null)) return null;
		var nextTrNode = trNode.nextSibling;
		var trOrder = 	ZU.getSplitOrder(trNode);
		do{
			if(nextTrNode!=null){
				nextTrOrder = ZU.getSplitOrder(nextTrNode);
				if(nextTrOrder<=trOrder){
					//console.info("找到退出");
					break;
				}
				nextTrNode = nextTrNode.nextSibling;//小一个元素
			}
		} while(nextTrNode)
		return  nextTrNode;
	};
	/*
	 * 获取节点的上一个上级节点父节点
	 * @param trNode 树的节点
	 * 
	 */
	this.getParentPreTr = function(trNode){
		var parentPreTr = null;
		if(!(trNode!=null)) return null;//trNode是空
		var trOrder = ZU.getSplitOrder(trNode);//节点的order
		var	preTrNode = trNode.previousSibling;//上一个元素
		while(preTrNode){
			var preOrder = ZU.getSplitOrder(preTrNode);
			if(preOrder<trOrder){
				//console.info("找到父节点返回");
				parentPreTr= preTrNode;
				break;
			}
			preTrNode = preTrNode.previousSibling;//上一个元素
		}
		return parentPreTr;
	};
	//内容双击编辑 动作
	this.spanOndblclick = function(obj){
		var trNode = obj.parentNode;//
		var inputNode = document.createElement("input");
		//inputNode.style.cssText="width:200px;";
		inputNode.value = ZU.textContent(obj);//.innerHTML;
		inputNode.onblur = function(){
			var  nodeValue = inputNode.value;
			if(nodeValue!=ZU.textContent(obj)){
				obj.innerHTML=nodeValue.replace(/\s/g,"&nbsp;");
				tree_rename(trNode.parentNode);
			}
			ZU.removeChild(this);//删除input输入框
			obj.style.display= "";
		};
		trNode.appendChild(inputNode);
		inputNode.focus();
		inputNode.select();
		obj.style.display="none";
	};
	/* 根据node的order插入节点
	 * @param node 插入的节点json结构
	 * @param parentId 上一个节点的Id null代表不存在上一个节点
	 * 
	 */
	this.insertNode = function (node,parentId){
		var trNode = null;
		if(parentId!=null){
			trNode = document.getElementById("span_"+parentId).parentNode.parentNode;
		}
		var trNewNode = this.createTrNode(node);
		var trNewOrder = ZU.getSplitOrder(trNewNode);
		var trNewendImg = ZU.getEnd1Img(trNewNode);//最后一个图片
		
		if(trNode!=null){//存在此节点的情况
			var trOrder = ZU.getSplitOrder(trNode);
			var trNodeImg = ZU.getEnd1Img(trNode);
			var trNextNode = trNode.nextSibling;
			if(trNextNode!=null){//存在下一个元素
				var trNextOrder = ZU.getSplitOrder(trNextNode);
				
				if(trNewOrder<trNextOrder){//新节点属于下一个节点的父节点
					trNewendImg.className="img_tree_del";
					if(trNextNode.style.display=="none"){
						trNewendImg.className="img_tree_add";
					}
					//新加节点clsaa处理完成
				}
				//出上一个节点的class
				if(trNewOrder<= trOrder){//新节点是上一个节点同级 和上级节点
					trNodeImg.className = "tree_space";
				}else{//新节点是上一个节点的子节点
					if(trNodeImg.className =="tree_space"){
						trNodeImg.className ="img_tree_del";
					}
				}
				//新节点是否隐藏display:none 循环遍历
				this.updateDisplayNode(trNewNode,trNode);
				//
				trNextNode.parentNode.insertBefore(trNewNode,trNextNode);
			}else{//不存在下一个元素
				//console.info("不存在下一个元素");
				if(trNewOrder>trOrder){//新节点是上一个节点子元素
					trNodeImg.className = "img_tree_del";
				}
				this.updateDisplayNode(trNewNode,trNode);
				this.table.appendChild(trNewNode);
				
			}
		}else{
			//console.info("不存在trNode上节点,就是插入到第一个节点");
			//插入到第一个元素
			var trList = this.table.getElementsByTagName("tr");
			if(trList.length>0){
				var firstTr = trList[0];
				if(trNewOrder<ZU.getSplitOrder(firstTr)){//新节点是下一个节点的父节点
					trNewendImg.className="img_tree_del";
					//if(trNextNode.style.display=="none"){//不会出现此情况
						//trNewendImg.className="img_tree_add";
					//}
				}
				this.table.insertBefore(trNewNode,firstTr);
			}else{
				this.table.appendChild(trNewNode);
			}
		}
	};
}
//鼠标移动上
function tree_tr_mouserover(obj){
	if(obj.className !="tree_tr_click"){
		obj.className = "tree_tr_mouserover";
	}
}
//鼠标离开
function tree_tr_mouserout(obj){
	if(obj.className !="tree_tr_click"){
		obj.className = "";
	}
}
//鼠标点击
function tree_tr_click(obj){
	obj.className = "tree_tr_click";
}
function pidFlag(pid){
	var flag=true;
	for(var i=0;i<pid.length;i++){
		if(!pid[i]) return false;
	}
	return flag;
	/*
	if(pid.length==1) return pid[0];
	var end = pid.pop();
	if(!end) return false;
	return end&&pidFlag(pid)
	*/
}
//阻止事件冒泡函数
function stopBubble(e){
    if (e && e.stopPropagation)
        e.stopPropagation();
    else
        window.event.cancelBubble=true;
}
//双击事件
function dblclick(obj){
	//console.info("双击事件");
}
//
//添加子元素
function tree_insertChild(){
	var node={};
		node.idStr=ZU.getRandom();
		node.text="子标题";
	var nextTrNode = zhuTree1.insertChild(node);
	zhuTree1.rightMenuHidden();
	var nextId= 0;
	if(nextTrNode!=null){
		nextId = ZU.getSplitId(nextTrNode);
	}
	ue.fireEvent('saveScene');
	iframeObj.contentWindow.Tree.insertChild(node,nextId);
	ue.fireEvent('saveScene');
}
//添加到下一个
function tree_insertNext(node){
	var node={};
	node.idStr=ZU.getRandom();
	node.text="后标题";
	var nextTrNode = zhuTree1.insertNext(node);
	zhuTree1.rightMenuHidden();
	var nextId= 0;
	if(nextTrNode!=null){
		nextId = ZU.getSplitId(nextTrNode);
	}
	ue.fireEvent('saveScene');
	iframeObj.contentWindow.Tree.insertChild(node,nextId);
	ue.fireEvent('saveScene');
}
//插入到标题之前
function tree_insertBefore(){
	var node={};
	node.idStr=ZU.getRandom();
	node.text="前标题";
	var trNode = zhuTree1.insertBefore(node);
	zhuTree1.rightMenuHidden();
	var nextId= 0;
	if(trNode!=null){
		nextId = ZU.getSplitId(trNode);
	}
	ue.fireEvent('saveScene');
	iframeObj.contentWindow.Tree.insertChild(node,nextId);
	ue.fireEvent('saveScene');
}
//删除节点
function tree_del(){
	//delId 是个json结构 删除开始 ID 和结束的ID
	var delId = zhuTree1.delNode();
	zhuTree1.rightMenuHidden();
	ue.fireEvent('saveScene');
	iframeObj.contentWindow.Tree.delNodeById(delId);
	ue.fireEvent('saveScene');
	
}
//双击编辑,出现滚动框
function tree_dblclick(){
	var  trId = zhuTree1.trClickId;
	var obj= document.getElementById(trId);
	if(obj!=null){
		obj=obj.getElementsByTagName("span")[0];
		zhuTree1.spanOndblclick(obj);
	}
}
//重命名
function tree_rename(trNode){
	//console.info("重命名标题");
	var order = ZU.getSplitOrder(trNode);
	var hId = trNode.id.split("_")[3];
	var spanText = trNode.getElementsByTagName("span")[0].innerHTML;
	ue.fireEvent('saveScene');
	iframeObj.contentWindow.tree_rename(hId,spanText);
	var ueRange = ue.selection.getRange();
	var obj=iframeObj.contentWindow.document.getElementById(hId);
	ueRange.selectNode(obj);
	//ueRange.select();
	zhuTree1.editFlag = true;
	ue.execCommand( 'removeformat', '','','' );//清除格式
	//ueParagraph('h'+order);
	zhuTree1.editFlag = false;
	ue.fireEvent('saveScene');
	//console.info(trNode.innerHTML);
}
/*
 * 更具node节点的order 插入节点 
 * @parameter node 节点节点的node
 * @param parentId 插入到节点Id之后。
 * 
 */
function tree_insert(node,parentId){
	//zhuTree1.insert(node,parentId);
}
//
var node={};
node.name="zhu";
//console.info("--"+node.name);
function setNode(node){
	node.name="123";
}
setNode(node);
//console.info("--"+node.name);
//锚点跳转
function tree_location(trObj){
	var id = ZU.getSplitId(trObj);
	locationId(id);
}
/*
 * 锚点跳转
 */
function locationId(id){
	var obj = iframeObj.contentWindow.document.getElementById(id);
	var objOffsetTop = obj.offsetTop+ZU.getNodeOffset(document.getElementById("fengMianDiv")).height;
	var center = document.getElementById("ifame_bo");//.parentNode;
	//console.info("objOffsetTop="+objOffsetTop);
	center.scrollTop = objOffsetTop;
	//iframeObj.contentWindow.ZU.location(id);
}
//正文的节点操作
/*
 * 获取节点最近的上一个的大纲节点,没有在返回null
 * @param node 节点
 * @return 返回node所在最近的上级大纲节点 没有在返回null
 */
function tree_content_getPreHNode(node){
	//return iframeObj.contentWindow.parentHNode(node);
	//console.info("获取---父的标题元素");
	if(!(node!=null)) return null;
	if(/H\d{1}/.test(node.nodeName)){
		return node;//在标题元素里面
	}
	var preNode = node.previousSibling;//上一个节点
	var preNodeWhile = node.previousSibling;//上一个节点
	while(preNodeWhile!=null){
		if(preNodeWhile!=null&&/H\d{1}/.test(preNodeWhile.nodeName)){
			return  preNodeWhile;
		}
		preNodeWhile=preNodeWhile.previousSibling;
	}
	if(true){
		var parentNode = node.parentNode;
		if(parentNode!=null&&parentNode.nodeType==1){//是document节点
			return null;
		}else{
			parentHNode(parentNode);
		}
	}
}
/*
 * 工具栏 设置为大纲的时候触发。
 * 之前是按照点触发，现在是整体触发，因为按键特别复杂,根本额发判断
 * 还有就是批量设置和取消 多个元素为标题和段落
 * @param node 内容区插入的大纲节点
 * 
 */
function tree_content_insertChild(node){
	return false;
	if(/H\d{1}/.test(node.nodeName)){
		//是大纲元素
	}else{ //不是大纲的元素
		//console.info("不是大纲元素,删除大纲元素");
		zhuTree1.delNodeOne(node.id);
		node.removeAttribute("id");//删除属性值
		node.attributes.removeNamedItem("name");
		return false;
	}
	var hOrder = node.nodeName.substring(1,2);//获取order
	var parentHOrder = 0;
	var parentHId = 0;
	
	var treeNode ={};
		treeNode.idStr = node.id;
		treeNode.order = hOrder;
		treeNode.text = node.innerHTML;
		
	var newTrNode = document.getElementById("span_"+node.id);
	if(newTrNode!=null){//大纲节点存在.修改此节点
		zhuTree1.updateNode(treeNode,parentId);
	}else{//大纲节点不存在，需要添加子节点
		var parentH = tree_content_getPreHNode(node.previousSibling);//parentH 上一个大纲节点
		var parentId = null;
		if(parentH!=null){//存在此节点
			//parentHOrder=parentH.nodeName.substring(1,2);
			parentId = parentH.id;
		}
		if(parentH!=null){//找到她的上一个节点
			zhuTree1.insertNode(treeNode,parentId);
		}else{
			zhuTree1.insertNode(treeNode,parentId);
		}
	}
}
function treeUpdateAll(){
	var json = iframeObj.contentWindow.getAllHnode();
	for(var i=0;i<json.length;i++){
		var node = json[i];
		if(zhuTree1.addMap[node.idStr]!=undefined){//存在此元素
			node.className="add";
		}
	}
	zhuTree1.setJson(json);
	zhuTree1.show();
	
}
//单个修改大纲节点信息
function updateSectionsF(type,node){
	//console.info("触发大纲节点 type="+type);
	if(node!=null){
		var id= node.id;
		var text = node.innerHTML; 
		//修改大纲节点名字
		var spanNode = document.getElementById(id);
		if(spanNode!=null){
			spanNode.innerHTML;
		};
	}else{//整体更新
        try{
        	if(zhuTree1.editFlag) return ;//处于单个编辑状态，不整个更新
            if(treeTimer!=0) clearTimeout(treeTimer);
            treeTimer=setTimeout(function () {
        		//console.info("整体更新大纲");
        		treeUpdateAll();
            }, 300);
        }catch(e){
        	//console.info("更新大纲e="+e);
        }
	}
}
/*
 * 隐藏右键菜单
 * param flag true:显示 false:隐藏
 */
function tree_rightMenu (flag){
	if(flag){//显示右键菜单
		zhuTree1.rightMenuDispaly();
	}else{//隐藏右键菜单
		zhuTree1.rightMenuHidden();
	};
	//右键隐藏
}
/*
 * 封面动作
 */
//鼠标在上面
function fengMianOver(obj){
	if(obj.className!="tree_tr_click"){
		obj.className = "tree_tr_mouserover";
	}
};
//鼠标离开
function fengMianOut(obj){
	if(obj.className!="tree_tr_click"){
		obj.className = "";
	}
};
//鼠标点击行,颜色变化
function fengMianClick(obj){
	//console.info("trClick="+obj.id);
	if(zhuTree1.trClickId!=0){
		var trClickNode = document.getElementById(zhuTree1.trClickId);
		if(trClickNode!=null){
			trClickNode.className = ""; 
		}
		zhuTree1.trClickId = 0;
	}
	obj.className = "tree_tr_click";
	var center = document.getElementById("ifame_bo");//.parentNode;
	//console.info("objOffsetTop="+objOffsetTop);
	center.scrollTop = 1;;
};


