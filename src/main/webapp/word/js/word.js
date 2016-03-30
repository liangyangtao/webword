(function($,h,c){var a=$([]),e=$.resize=$.extend($.resize,{}),i,k="setTimeout",j="resize",d=j+"-special-event",b="delay",f="throttleWindow";e[b]=250;e[f]=true;$.event.special[j]={setup:function(){if(!e[f]&&this[k]){return false}var l=$(this);a=a.add(l);$.data(this,d,{w:l.width(),h:l.height()});if(a.length===1){g()}},teardown:function(){if(!e[f]&&this[k]){return false}var l=$(this);a=a.not(l);l.removeData(d);if(!a.length){clearTimeout(i)}},add:function(l){if(!e[f]&&this[k]){return false}var n;function m(s,o,p){var q=$(this),r=$.data(this,d);r.w=o!==c?o:q.width();r.h=p!==c?p:q.height();n.apply(this,arguments)}if($.isFunction(l)){n=l;return m}else{n=l.handler;l.handler=m}}};function g(){i=h[k](function(){a.each(function(){var n=$(this),m=n.width(),l=n.height(),o=$.data(this,d);if(m!==o.w||l!==o.h){n.trigger(j,[o.w=m,o.h=l])}});g()},e[b])}})(jQuery,this); 

$(document).ready(function(){

/* 内容插件 */
//$(".contents_plugin,.box_content,.ui_back").fadeIn(100);
/* 内容插件弹窗时间框初始值 */
$("#time_start,#time_end,#time_start1,#time_end1").val(laydate.now());
/*$("#time_start_2,#time_end_2").val("");
$("#time_start_2").click(function(){laydate({elem:'#time_start_2'});$(this).attr('placeholder','');})
$("#time_end_2").click(function(){laydate({elem:'#time_end_2'});$(this).attr('placeholder','');})
$("#time_start_2,#time_end_2").blur(function(){	$(this).attr('placeholder','');})*/

/* 内容插件 */
var cont_plugin={
	pluginName:$(".contents_plugin .pluginName").val(),//插件名称
		  must:$(".contents_plugin .must").val(),//全部关键字
   arbitrarily:$(".contents_plugin .arbitrarily").val(),//任意关键字
		   not:$(".contents_plugin .not").val(),//不包含关键字
  searchSource:$(".contents_plugin .searchSource").attr("checked")=="checked",//如果searchSource值为true则 选中的为新闻全文 值为false 选中的为新闻标题		   
		  sort:$(".contents_plugin .sort option:selected").attr("value"),//1:按时间排序,2：按焦点排序。默认：按焦点排序
	 startTime:$(".contents_plugin .startTime").val(),//开始时间
	   endTime:$(".contents_plugin .endTime").val(),//结束时间
	fromSource:$(".contents_plugin .fromSource").val(),//新闻源
	  pageSize:$(".contents_plugin .pageSize option:selected").attr("value"),//搜索显示条数（下拉框）
	repeatFlag:$(".contents_plugin .repeatFlag").attr("checked")=="checked",//如果repeatFlag值为true则 选中的为自动执行 值为false 选中的否	
    repeatTime:$(".contents_plugin .repeatTime").val(),//执行周期
};
var interval1;
$("#time_start1,#time_end1").blur(function(){//给周期赋值	
	if($(".contents_plugin .startTime").val()!=''&&$(".contents_plugin .endTime").val()!=''){
		$(".contents_plugin .repeatTime").val(1+GetDateDiff($(".contents_plugin .startTime").val(),$(".contents_plugin .endTime").val()));
		setTimeout(function(){clearInterval(interval1);},10000);	
	}	
})
$("#time_start1,#time_end1").focus(function(){//给周期赋值	
		interval1=setInterval(function(){
			if($(".contents_plugin .startTime").val()!=''&&$(".contents_plugin .endTime").val()!=''){
				$(".contents_plugin .repeatTime").val(1+GetDateDiff($(".contents_plugin .startTime").val(),$(".contents_plugin .endTime").val()));	
			}else{		
				$(".contents_plugin .repeatTime").val('');
			}
		},1);	
			
})



var llistbar_show = 0,
	rlistbar_show = 0;//0：显示；1：隐藏；2：关闭；
/* 菜单点击 */
var each1 = -1;
$(".cbb_left ul li").each(function(n){
	$(this).click(function(){
		/* 显示菜单 */
		if($(".cB_tools").css("display")=="none"){
		var top =$(".cB_tools").outerHeight(true)+Number($(".edit_body").css("top").split("px")[0]);
		$(".edit_body,.r_listbox,.l_listbox").css("top",top);
		$(".cB_tools").slideDown();
		}
		var a=n+1;each1=a;
		$(".cbb_left ul li").css("background-color","#19a97b");
		$(this).css("background-color","#05825a");
		$(".tdisplay").css("display","none");
		$(".tdisplay"+a+"").css("display","block");
	});
	$(this).hover(function(){		
		if(each1!=n+1){$(this).css("background-color","#1c9971")}
	},function(){
		if(each1!=n+1){$(this).css("background-color","#19a97b")}
	})
})

/* 工具栏 */
	var tools = {
		font      : $(".cB_tools .cbt_div3 .ul1 .li1"),		
		font_list : $(".cB_tools .cbt_div3 .ul1 dd"),
		nub_menu  : $(".cB_tools .cbt_div4 .ul1 .li1"),
		nub_menu_list:$(".cB_tools .cbt_div4 .ul1  .nub_menu dd"),
		d_view    : $(".cB_tools .cbt_div15 ul li"),
		n_view    : $(".cB_tools .cbt_div16 ul li"),
		all_tmenu : $(document).find(".cB_tools dl"),	
		tmenu_show_hidden:function(obj){
			obj.fadeToggle(0);			
		},
		
		
	};
	function stopPropagation(e) {
		if (e.stopPropagation) 
			e.stopPropagation();
		else 
			e.cancelBubble = true;
        }
/* 工具栏收缩 */
$(".cB_tools .t_up_down").click(function(){
	var top= $(".cB_tools").outerHeight(true)-6;	//20150820 大纲区和知识资料区覆盖了导航	
	$(this).parents(".cB_tools").slideUp();
	//$(".edit_body,.r_listbox,.l_listbox").css({"transition":"all .3s ease-in-out","top":Number($(".edit_body").css("top").split("px")[0])-top});
	$(".edit_body,.r_listbox,.l_listbox").css({"transition":"all .3s ease-in-out","top":"103px"});
	})
/* 视图菜单下 大纲区及知识资料区的点击 */
	function panduan(){
		if(llistbar_show==0&&rlistbar_show==0){$("#ifame_bo").css({"margin-left":$(".l_listbox").width()+10,"transition":"all .3s ease-in-out"})}
		if(llistbar_show==0&&rlistbar_show!=0){$("#ifame_bo").css({"margin-left":$(".l_listbox").width()+10,"transition":"all .3s ease-in-out"})}
		if(llistbar_show!=0&&rlistbar_show==0){$("#ifame_bo").css({"margin-left":"1%","transition":"all .3s ease-in-out"})}
		if(llistbar_show!=0&&rlistbar_show!=0){$("#ifame_bo").css({"margin-left":"auto","transition":"all .3s ease-in-out"})}						
	}
	$(".l_listbox").resize(function(){		
		$("#ifame_bo").css({"margin-left":$(".l_listbox").width()+10,"transition":"all .3s ease-in-out"});		
		re_width();
	})
	$(".r_listbox").resize(function(){
		panduan();re_width();
	})
	tools.d_view.click(function(){		
		tools.tmenu_show_hidden(bar.left_bar);
		bar.left_bar.css("left","0");
		$(".llist_show").css("display")=="block" ? tools.tmenu_show_hidden($(".llist_show")) : 0;
		bar.left_bar.css("display")=="none" ? llistbar_show=2 : llistbar_show=0;
		panduan();
	})
	tools.n_view.click(function(){		
		tools.tmenu_show_hidden(bar.right_bar);
		bar.right_bar.css("right","0");
		$(".rlist_show").css("display")=="block" ? tools.tmenu_show_hidden($(".rlist_show")) : 0;
		bar.right_bar.css("display")=="none"? rlistbar_show=2 : rlistbar_show=0;
		panduan();	
	})
/* 自动设定编辑区域的宽度 增加滚动条 */	
	function re_width(){		
			var w1= llistbar_show==0 ? $(".l_listbox").width() : 0;
			var w2= rlistbar_show==0 ? $(".r_listbox").width() : 0;
			$("#ifame_bo").width($(window).width()-w1-w2-20);
	}	
	$(window).resize(function(){
		re_width();
	})		
/* 点击文档清理所有窗口 */
	$(document).click(function(){
		tools.all_tmenu.fadeOut();
		/* 自动设定编辑区域的宽度 增加滚动条 */
		$(".cB_tools .cbt_div5 .ul1").removeClass("open");
		re_width();
	}); 
/* 字体下拉菜单 */
	tools.font.click(function(e){
		tools.tmenu_show_hidden($(this).siblings("dl").eq(0));
		stopPropagation(e);
	});
	tools.font_list.each(function(){
		$(this).click(function(n){
			tools.font.html($(this).text()+"<i></i>");
			tools.tmenu_show_hidden($(this).parents("dl"));
		})
	});
	/* 打开字体颜色下拉菜单 */	
/* 	$(".cB_tools .cbt_div3 .ul2 .li10").click(function(e){
		tools.tmenu_show_hidden($(this).siblings("dl").eq(0));
		stopPropagation(e);
	}); */
	/* 加载时给字体颜色菜单添加颜色 */
/* 	var color = [['#ffffff','#000000','#eeece1','#1f497d','#4f81bd','#c0504d','#9bbb59','#8064a2','#4bacc6','#f79646'],
	['#f2f2f2','#7f7f7f','#ddd9c3','#c6d9f0','#dbe5f1','#f2dcdb','#ebf1dd','#e5e0ec','#dbeef3','#fdeada'],
	['#d8d8d8','#595959','#c4bd97','#8db3e2','#b8cce4','#e5b9b7','#d7e3bc','#ccc1d9','#b7dde8','#fbd5b5'],
	['#bfbfbf','#3f3f3f','#938953','#548dd4','#95b3d7','#d99694','#c3d69b','#b2a2c7','#92cddc','#fac08f'],
	['#a5a5a5','#262626','#494429','#17365d','#366092','#953734','#76923c','#5f497a','#31859b','#e36c09'],
	['#7f7f7f','#0c0c0c','#1d1b10','#0f243e','#244061','#632423','#4f6128','#3f3151','#205867','#974806'],
	['#c00000','#ff0000','#ffc000','#ffff00','#92d050','#00b050','#00b0f0','#0070c0','#002060','#7030a0']];
	for (var a=0; a< 7;a++){
			for(var b=0;b<10;b++){
				var a1=a+3,b1=b+1;
				$(".color_menu dd:nth-child("+a1+") i:nth-child("+b1+")").css("background-color",color[a][b]);
				$(".color_menu .ddn i:nth-child("+b1+")").css("background-color",color[6][b]);
			}
		} */
	/* 点击清除颜色 */
/* 	$(".cB_tools .cbt_div3 .ul2 .color_menu  .dd1 a").click(function(){
		$(".cB_tools .cbt_div3 .ul2 .li10 i").css("background-color","#fff");
		$(this).parents("dl").fadeOut();
	}) */
	/* 给字体颜色菜单添加鼠标经过及点击事件 */
/* 	$(".color_menu .dd2").each(function(n){				
		$(".color_menu .dd2 i").each(function(c){
			var n1=n+3,c1=c+1;
			$(".color_menu dd:nth-child("+n1+") i:nth-child("+c1+")").hover(function(){
				$(".color_menu .dd1 i").css("background-color",color[n][c]);
			},function(){$(".color_menu .dd1 i").css("background-color","#fff");});
			$(".color_menu .ddn i:nth-child("+c1+")").hover(function(){
				$(".color_menu .dd1 i").css("background-color",color[6][c]);
			},function(){$(".color_menu .dd1 i").css("background-color","#fff");});
			if(n1!=6){
				$(".color_menu dd:nth-child("+n1+") i:nth-child("+c1+")").click(function(){
					$(".cB_tools .cbt_div3 .ul2 .li10 i").css("background-color",color[n][c]);
					tools.tmenu_show_hidden($(this).parents("dl"));	
				});
			}else{
				$(".color_menu .ddn i:nth-child("+c1+")").click(function(){
					$(".cB_tools .cbt_div3 .ul2 .li10 i").css("background-color",color[6][c]);
					tools.tmenu_show_hidden($(this).parents("dl"));
				});
			}
		})			
	}) */
	/* 编号点击下拉菜单 */
	tools.nub_menu.click(function(e){
		tools.tmenu_show_hidden($(this).siblings("dl").eq(0));
		stopPropagation(e)
	});
	tools.nub_menu_list.each(function(){
		$(this).click(function(n){			
			tools.tmenu_show_hidden($(this).parents("dl"));
		})
	});

	
/* 左右边栏 */
	var mousedown1 = false ;
	var mousedown2 = false ;
	var bar = {
		left_bar : $(".l_listbox"),
		right_bar : $(".r_listbox"),
		left_border : $(".llist_div2"),//左边栏的右边框
		right_border : $(".rlist_div2"),//右边栏的左边框
		bar_close:function(obj){
			obj.fadeOut();
		},
		bar_hidden:function(obj,direct){
			var wid = -obj.width()+"px";
			direct < 2 ? obj.animate({left:wid},{easing: 'easeOutQuad', duration:500}) :
			obj.animate({right:wid},{easing: 'easeOutQuad', duration:500}) ;			
		},
		bar_show:function(obj,direct){
			direct < 1 ? obj.animate({left:"0px"},{easing: 'easeOutQuad', duration:500}) :
			obj.animate({right:"0"},{easing: 'easeOutQuad', duration:500}) ;
		},
		bar_resize_x:function(obj){
			$(document).mousemove(function(e){					
						var e= e ? e:event;	
					if(mousedown1){							
						var wid1 = e.clientX ;																		
						obj.css({"width" : wid1});					
					}					
			})			
		},
		bar_resize_x_right:function(obj){
			$(document).mousemove(function(e){					
						var e= e ? e:event;	
					 if(mousedown2&&!mousedown1){
						var wid2 = $("body").width()-e.clientX-obj.css("right").split("px")[0]+"px";
						obj.css({"width" : wid2});
					}					
			})			
		}
	}
	/* 点击左右边栏的收回按钮 */
	$(document).find(".direct").each(function(index){
		$(this).mousedown(function(){
			var a = index < 2 ? 0 : 1 ;
			 a==0 ? llistbar_show=1 : rlistbar_show=1;
			bar.bar_hidden($(this).parents(".list_bar"),index);re_width();
			$(this).parents(".list_bar").siblings(".list_show").eq(a).fadeIn();			
			if(a==0&&rlistbar_show!=0){$("#ifame_bo").css({"margin-left":"auto","transition":"all .3s ease-in-out"})}
			if(a==0&&rlistbar_show==0){$("#ifame_bo").css({"margin-left":"1%","transition":"all .3s ease-in-out"})}
			if(a==1&&llistbar_show!=0){$("#ifame_bo").css({"margin-left":"auto","transition":"all .3s ease-in-out"})}
			if(a==1&&llistbar_show==0){$("#ifame_bo").css({"margin-left":$(".l_listbox").width()+10,"transition":"all .3s ease-in-out"})}
		})
	})
	/* 点击左右边栏的关闭按钮 */
	$(".li_last img").each(function(n){
		$(this).click(function(){
			n==0 ? llistbar_show=2 : rlistbar_show=2;
			bar.bar_close($(this).parents(".list_bar"));re_width();
			if(n==0&&rlistbar_show!=0){$("#ifame_bo").css({"margin-left":"auto","transition":"all .3s ease-in-out"})}
			if(n==0&&rlistbar_show==0){$("#ifame_bo").css({"margin-left":"1%","transition":"all .3s ease-in-out"})}
			if(n==1&&llistbar_show!=0){$("#ifame_bo").css({"margin-left":"auto","transition":"all .3s ease-in-out"})}			
		})
	})
	/* 点击左右边栏的显示按钮 */
	$(".list_show img").each(function(index){
		$(this).click(function(){
			index==0 ? llistbar_show=0 : rlistbar_show=0;
			bar.bar_show($(this).parents(".list_show").siblings(".list_bar").eq(index),index);
			bar.bar_close($(this).parents(".list_show"));re_width();
			if(index==0){$("#ifame_bo").css({"margin-left":$(".l_listbox").width()+10,"transition":"all .3s ease-in-out"})}
			if(index!=0&&llistbar_show!=0){$("#ifame_bo").css({"margin-left":"1%","transition":"all .3s ease-in-out"})}			
		})
	})
	/* 拖动改变边栏宽度 */
	bar.left_border.mousedown(function(){
		mousedown1 = true;
		$(".ui_back2").css("display","block");
		bar.bar_resize_x(bar.left_bar);
	});
	$(document).mouseup(function(){mousedown1 = false;mousedown2 = false;$(".ui_back2").css("display","none");})
	
 	bar.right_border.mousedown(function(){
		mousedown2 = true;
		$(".ui_back2").css("display","block");
		bar.bar_resize_x_right(bar.right_bar);		
	})
	/* 右侧边栏 新闻 文档 插件的切换 */
	$(".r_listbox  .list_tp  .ul li").each(function(n){
		$(this).click(function(){
			var a=n+1;
			$(this).addClass("active").siblings('li').removeClass("active");
			$(".news_like,.r_listbox  .list_tp  .div").css("display","none");
			$(".document"+a+" ,.r_listbox  .list_tp  .div"+a).css("display","block");
			$(".ser_tip").css("display","none");
		})
	})
	/* 搜索 */
	/*$(".r_listbox .list_tp .div input").keyup(function(event){//监听回车查询
		var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
        if (keyCode == 13 ) {
			$(".r_listbox .list_tp .div .a1").click();
            event.returnValue=false;
        } 
	})    //发送了两个搜索请求，所以隐藏该段代码*/     
	var text=["找到相关新闻 篇","找到相关文档 篇","找到相关插件 个","找到相关模板 个"];
	$(".r_listbox .list_tp .div .a1").click(function(){
		$(".news_like").each(function(n){//判断是新闻、文档、插件
			var a = $(this).find("a").eq(0).html();
			if($(this).css("display")=="block"){			
				$(this).children(".an,.bn,.cn,.dn").each(function(s){
					if(n==0&&s==2&&$(this).hasClass("active")){//新闻 下猜你喜欢
						var a=1;
						$(".news_like .a1").addClass("active").html("搜索结果").siblings(".an").removeClass("active");
						$(".r_listbox .news_like .an").css({"background-color":"#fff","border":"1px solid #999","color":"#999","z-index":"0"});			
						$(".r_listbox .news_like .a"+3+"").css({"color":"#19a97b","z-index":"0","border":"1px solid #999"});
						$(".r_listbox .news_like .a"+a+"").css({"color":"#19a97b","z-index":"2","background-color":"#f4f9f7","border":"1px solid #19a97b"}).siblings("a").css({"z-index":"0","border-bottom":"1px solid #d2d2d2"});
						if(a!=3){$(".r_listbox .news_like .a"+3+"").css({"border":"1px solid #fff"});}
						$(".r_listbox .news_like .lan,.r_listbox .news_like .n_list").css("display","none");
						$(".r_listbox .news_like .an"+a+" ,.r_listbox .news_like .n_list"+a).fadeIn();
						}
					if(n==1&&s==3&&$(this).hasClass("active")){//文档 下猜你喜欢
						var a=1;
						$(".news_like .b1").addClass("active").html("搜索结果").siblings(".bn").removeClass("active");
						$(".r_listbox .news_like .bn").css({"background-color":"#fff","border":"1px solid #999","color":"#999","z-index":"0"});
						$(".r_listbox .news_like .b"+4+"").css({"color":"#19a97b","border":"1px solid #fff"});
						$(".r_listbox .news_like .b"+a+"").css({"background-color":"#f4f9f7","border":"1px solid #19a97b"});
						$(".r_listbox .news_like .b"+a+"").css({"color":"#19a97b","z-index":"2"}).siblings("a").css("z-index","0");
						if(a!=4){$(".r_listbox .news_like .b"+4+"").css({"border":"1px solid #fff"});}
						$(".r_listbox .news_like .lbn,.r_listbox .news_like .anPage .n_list").css("display","none");
						$(".r_listbox .news_like .bn"+a+" ,.r_listbox .news_like .anPage .n_list"+a).fadeIn();
						
						}
					if(n==2&&s==3&&$(this).hasClass("active")){//插件 下猜你喜欢
						var a=1;
						$(".news_like .c1").addClass("active").html("搜索结果").siblings(".cn").removeClass("active");
						$(".r_listbox .news_like .cn").css({"background-color":"#fff","border":"1px solid #999","color":"#999","z-index":"0"});
						$(".r_listbox .news_like .c"+4+"").css({"color":"#19a97b","border":"1px solid #fff"});
						$(".r_listbox .news_like .c"+a+"").css({"background-color":"#f4f9f7","border":"1px solid #19a97b"});
						$(".r_listbox .news_like .c"+a+"").css({"color":"#19a97b","z-index":"2"}).siblings("a").css("z-index","0");
						if(a!=4){$(".r_listbox .news_like .c"+4+"").css({"border":"1px solid #fff"});}
						$(".r_listbox .news_like .lcn ,.r_listbox .news_like .cnPage .n_list").css("display","none");
						$(".r_listbox .news_like .cn"+a+" ,.r_listbox .news_like .cnPage .n_list"+a).fadeIn();
						}
					if(n==3&&s==2&&$(this).hasClass("active")){//模板 下猜你喜欢
						var a=1;
						$(".news_like .d1").addClass("active").html("搜索结果").siblings(".dn").removeClass("active");
						$(".r_listbox .news_like .dn").css({"background-color":"#fff","border":"1px solid #999","color":"#999","z-index":"0"});
						$(".r_listbox .news_like .d"+3+"").css({"color":"#19a97b","z-index":"0","border":"1px solid #999"});
						$(".r_listbox .news_like .d"+a+"").css({"color":"#19a97b","z-index":"2","background-color":"#f4f9f7","border":"1px solid #19a97b"}).siblings("a").css({"z-index":"0","border-bottom":"1px solid #d2d2d2"});
						if(a!=3){$(".r_listbox .news_like .d"+3+"").css({"border":"1px solid #fff"});}
						$(".r_listbox .news_like .ldn,.r_listbox .news_like .dnPage .n_list").css("display","none");
						$(".r_listbox .news_like .dn"+a+" ,.r_listbox .news_like .dnPage .n_list"+a).fadeIn();
						
						}	
					if($(this).hasClass("active")||((n==0&&s==2)||(n==1&&s==3)||(n==2&&s==3)||(n==3&&s==2))){//判断点击的是哪一个菜单	
						var s_value=document.getElementById("newsInput").value,//新闻搜索框
							w_value=document.getElementById("articleInput").value,
							c_value=document.getElementById("pluginInput").value,//插件搜索框
							t_value=document.getElementById("TemplateInput").value;//模板搜索框
							if((n==0&&s==0||n==0&&s==2)&&$(this).hasClass("active")){//搜索最新新闻
								searchParam.must=s_value;
								getNewsAll(1);
							
							}
							if(n==0&&s==1&&$(this).hasClass("active")){//搜索我的新闻
								myParam.contentName=s_value;
								getNewsMy(1);
							}
							if((n==1&&s==0||n==1&&s==3)&&$(this).hasClass("active")){//搜索全部文档
								articleParam.name = w_value;
								getArticleAll(1);
							}
							if(n==1&&s==1&&$(this).hasClass("active")){//搜索我的文档
								articleMyParam.name = w_value;
								getArticleMy(1);
							}
							if((n==2&&s==0||n==2&&s==3)&&$(this).hasClass("active")){//搜索全部插件
								pluginParam.name = c_value;
								getPluginAll(1);
							}
							if(n==2&&s==1&&$(this).hasClass("active")){//搜索我的插件								
								pluginMyParam.name =c_value;
								getPluginMy(1);
							}
							if((n==3&&s==0||n==3&&s==2)&&$(this).hasClass("active")){//搜索全部模板								
								TemplateParam.name = t_value;
								getTemplateAll(1);								
							}
							if(n==3&&s==1&&$(this).hasClass("active")){//搜索我的模板
								TemplateMyParam.name = t_value;
								getTemplateMy(1);							
							}													
							if(((n==0&&s==2)||(n==1&&s==3)||(n==2&&s==3)||(n==3&&s==2))){
								$(this).html("猜你喜欢").siblings(".bk").fadeIn();}
							else{$(this).html("搜索结果").siblings(".bk").fadeIn();}
							 
						$(".ser_tip").slideDown().children(".s1").html(text[n]);

				if(n!=0){$(".s2").css("display","none")}else{$(".s2").css("display","inline-block")}
					}
				})							
			}
		})
	});
	/* 搜索后返回 */
	var text2=[["最新新闻","我的新闻","猜你喜欢"],["平台文档","我的文档","共创文档","猜你喜欢"],["全部插件","我的插件","当前插件","猜你喜欢"],["平台模板","我的模板","猜你喜欢"]];
	$(".news_like").each(function(a1){
		$(this).find(".an,.bn,.cn,.dn").each(function(a2){
			$(this).click(function(e2){
				if($(this).html()=="搜索结果"){
					$(this).siblings(".bk").fadeIn();
					$(".ser_tip").slideDown();}				
				else{
					$(this).siblings(".bk").fadeOut();
					$(".ser_tip").slideUp();}	
				columnClick(this);
			});
		});								
		$(this).find(".bk").click(function(e){
			$(this).parents(".news_like").find(".an,.bn,.cn,.dn").each(function(a2){
				if($(this).hasClass("active")){
					
					$(this).html(text2[a1][a2]);
					$(this).siblings(".bk").fadeOut();
					$(".ser_tip").slideUp();
					document.getElementById("newsInput").value="";
					document.getElementById("pluginInput").value="";
					if(a1==0&&a2==0){//全部新闻
						searchParam.must="";
						getNewsAll(1,"");
					};
					if(a1==0&&a2==1){//我的新闻
						myParam.contentName="";
						getNewsMy(1);
					}
					if(a1==1&&a2==0){//全部文档
						articleParam.name="";
						getArticleAll(1);
					}
					if(a1==1&&a2==1){//我的文档
						articleMyParam.name="";
						getArticleMy(1);
					}
					if(a1==2&&a2==0){//全部插件
						pluginParam.name = "";
						getPluginAll(1,"");
					}
					if(a1==2&&a2==1){//我的插件						
						pluginMyParam.name = "";
						getPluginMy(1,'');
					}
					if(a1==3&&a2==0){//全部模板						
						TemplateParam.name = "";
						getTemplateAll(1);						
					}
					if(a1==3&&a2==1){//我的模板
						TemplateMyParam.name = "";
						getTemplateMy(1);											
					}
				}
			});
		});	
				
	/* 搜索框清空 */
	$(".r_listbox .list_tp .div").each(function(m){
		$(this).children("img").click(function(){
			$(this).siblings("input").focus().attr('value','');	
		})
		$(this).children("input").focus(function(){
			$(this).css('background','none');			
		})
		$(this).children("input").keyup(function(){
			 if($(this).attr('value')!='' ){
				$(this).css('background','none');$(this).siblings("img").css("display","block");
				} 
			else { $(this).css("background","url('word/img/ser_bg.png') no-repeat left center");
			$(this).siblings("img").css("display","none");
			} 		
		})
		$(this).children("input").blur(function(){
			if($(this).attr('value')!='' ){
				$(this).css('background','none');$(this).siblings("img").css("display","block");
				} 
			else { $(this).css("background","url('word/img/ser_bg.png') no-repeat left center");
			$(this).siblings("img").css("display","none");
			} 				
		})
	})
 	/* 翻页点击事件 */
/* 		$(".r_listbox .news_like .n_list li").each(function(n){
			$(this).mousedown(function(){
				if(n!=0&&!$(this).hasClass("l_last")){
					$(this).css({"color":"#fff","background-color":"#099266"}).siblings("li").css({"color":"#19a97b","background-color":"#fff"});
					}
			})
		})  */
	})		
	$(".r_listbox .list_tp .ser_tip .s2").click(function(){
		$(".r_listbox  .list_tp .ser_tip .s_tip").fadeToggle();
	})
	$(".r_listbox  .list_tp .ser_tip .s_tip li").each(function(index){
		$(this).click(function(){//选择按时间排序 还是按焦点排序
			$(".r_listbox .list_tp .ser_tip .s2").html($(this).text()+"<img src='word/img/t_down.png' />");
			$(this).parents(".s_tip").fadeOut();
			if(index==1){
				searchParam.sort="1";
			}else{
				searchParam.sort="2";
			}
			$(".r_listbox .list_tp .div .a1").click();
			
		})
	})

	
	/* 新闻 全部新闻 我的新闻和猜你喜欢的切换 */
	var each = -1;
	$(".r_listbox  .news_like .an").each(function(n){
		$(this).click(function(){
			var a=n+1;each=a;
			$(this).addClass("active").siblings(".an").removeClass("active");
			$(".r_listbox .news_like .an").css({"background-color":"#fff","border":"1px solid #999","color":"#999","z-index":"0"});			
			$(".r_listbox .news_like .a"+3+"").css({"color":"#19a97b","z-index":"0","border":"1px solid #999"});
			$(".r_listbox .news_like .a"+a+"").css({"color":"#19a97b","z-index":"2","background-color":"#f4f9f7","border":"1px solid #19a97b"}).siblings("a").css({"z-index":"0","border-bottom":"1px solid #d2d2d2"});
			if(a!=3){$(".r_listbox .news_like .a"+3+"").css({"border":"1px solid #fff"});}
			$(".r_listbox .news_like .lan,.r_listbox .news_like .anPage .n_list").css("display","none");
			$(".r_listbox .news_like .an"+a).fadeIn();
			$(".r_listbox .news_like .anPage .n_list"+a).fadeIn();
			//a1 a2
		});
		$(this).hover(function(){
			$(this).css({"background-color":"#f4f9f7","border":"1px solid #19a97b"});
			$(this).css("color",$(this).css("border-left-color")+"");
		},function(){if((each!=n+1&&each!=-1)||(each==-1&&n!=0)){$(this).css({"background-color":"#fff","border":"1px solid #999"})}
				if(n==2&&!$(this).hasClass("active")){$(this).css({"background-color":"#fff","border":"1px solid #fff"});}
				if(n!=2){$(this).css("color",$(this).css("border-left-color")+"");}
		});
	})
		/* 文档 平台文档 我的文档 共创文档和猜你喜欢的切换 */
	var each3 = -1;
	$(".r_listbox  .news_like .bn").each(function(n){
		$(this).click(function(){
			var a=n+1;each3=a;
			$(this).addClass("active").siblings(".bn").removeClass("active");
			$(".r_listbox .news_like .bn").css({"background-color":"#fff","border":"1px solid #999","color":"#999","z-index":"0"});
			$(".r_listbox .news_like .b"+4+"").css({"color":"#19a97b","border":"1px solid #fff"});
			$(".r_listbox .news_like .b"+a+"").css({"background-color":"#f4f9f7","border":"1px solid #19a97b"});
			$(".r_listbox .news_like .b"+a+"").css({"color":"#19a97b","z-index":"2"}).siblings("a").css("z-index","0");
			if(a!=4){$(".r_listbox .news_like .b"+4+"").css({"border":"1px solid #fff"});}
			$(".r_listbox .news_like .lbn,.r_listbox .news_like .bnPage .n_list").css("display","none");
			//$(".r_listbox .news_like .bn"+a+" ,.r_listbox .news_like .n_list"+a).fadeIn();
			$(".r_listbox .news_like .bn"+a).fadeIn();
			$(".r_listbox .news_like .bnPage .n_list"+a).fadeIn();
		});
		$(this).hover(function(){
			$(this).css({"background-color":"#f4f9f7","border":"1px solid #19a97b"});
			$(this).css("color",$(this).css("border-left-color")+"");
		},function(){
			if((each3!=n+1&&each3!=-1)||(each3==-1&&n!=0&&n!=3)){$(this).css({"background-color":"#fff","border":"1px solid #999"});}
			if(n==3&&!$(this).hasClass("active")){$(this).css({"background-color":"#fff","border":"1px solid #fff"});}
			if(n!=3){$(this).css("color",$(this).css("border-left-color")+"");}
		});		
	})	
	/* 文档 树形结构的伸开 与收缩 */
	var sn=0;
	$(".document2 .lbn  .ul h3").click(function(){		
		$(this).toggleClass("down");
		$(this).siblings("li").slideToggle();
		if(sn%2==0){}else{$(".tree_h").nextAll(".treec").css("display","none")}
	})
	$(".document2 .lbn  .ul .tree_h").click(function(){
		sn++;
		$(this).toggleClass("treedown");
		$(this).nextAll(".treec").slideToggle();		
	})
	/* 插件 全部插件 当前插件 我的插件和猜你喜欢的切换 */
	var each4 = -1;
	$(".r_listbox  .document3 .cn").each(function(n){
		$(this).click(function(){
			var a=n+1;each4=a;
			$(this).addClass("active").siblings(".cn").removeClass("active");
			$(".r_listbox .news_like .cn").css({"background-color":"#fff","border":"1px solid #999","color":"#999","z-index":"0"});
			$(".r_listbox .news_like .c"+4+"").css({"color":"#19a97b","border":"1px solid #fff"});
			$(".r_listbox .news_like .c"+a+"").css({"background-color":"#f4f9f7","border":"1px solid #19a97b"});
			$(".r_listbox .news_like .c"+a+"").css({"color":"#19a97b","z-index":"2"}).siblings("a").css("z-index","0");
			if(a!=4){$(".r_listbox .news_like .c"+4+"").css({"border":"1px solid #fff"});}
			$(".r_listbox .news_like .lcn ,.r_listbox .news_like .cnPage .n_list").css("display","none");
			//$(".r_listbox .news_like .cn"+a+" ,.r_listbox .news_like .n_list"+a).fadeIn();
			$(".r_listbox .news_like .cn"+a).fadeIn();
			$(".r_listbox .news_like .cnPage .n_list"+a).fadeIn();
		});
		$(this).hover(function(){
			$(this).css({"background-color":"#f4f9f7","border":"1px solid #19a97b"});
			$(this).css("color",$(this).css("border-left-color")+"");
		},function(){
			if((each4!=n+1&&each4!=-1)||(each4==-1&&n!=0&&n!=3)){$(this).css({"background-color":"#fff","border":"1px solid #999"});}
			if(n==3&&!$(this).hasClass("active")){$(this).css({"background-color":"#fff","border":"1px solid #fff"});}
			if(n!=3){$(this).css("color",$(this).css("border-left-color")+"");}
		});		
	})	
	
	/* 模板 平台模板 我的模板 共创模板和猜你喜欢的切换 */
	var each3 = -1;
	$(".r_listbox  .news_like .dn").each(function(n){
		$(this).click(function(){
			var a=n+1;each=a;
			$(this).addClass("active").siblings(".dn").removeClass("active");
			$(".r_listbox .news_like .dn").css({"background-color":"#fff","border":"1px solid #999","color":"#999","z-index":"0"});			
			$(".r_listbox .news_like .d"+3+"").css({"color":"#19a97b","z-index":"0","border":"1px solid #999"});
			$(".r_listbox .news_like .d"+a+"").css({"color":"#19a97b","z-index":"2","background-color":"#f4f9f7","border":"1px solid #19a97b"}).siblings("a").css({"z-index":"0","border-bottom":"1px solid #d2d2d2"});
			if(a!=3){$(".r_listbox .news_like .d"+3+"").css({"border":"1px solid #fff"});}
			$(".r_listbox .news_like .ldn,.r_listbox .news_like .dnPage .n_list").css("display","none");
			//$(".r_listbox .news_like .dn"+a+" ,.r_listbox .news_like .n_list"+a).fadeIn();
			$(".r_listbox .news_like .dn"+a).fadeIn();
			$(".r_listbox .news_like .dnPage .n_list"+a).fadeIn();
			//a1 a2
		});
		$(this).hover(function(){
			$(this).css({"background-color":"#f4f9f7","border":"1px solid #19a97b"});
			$(this).css("color",$(this).css("border-left-color")+"");
		},function(){if((each!=n+1&&each!=-1)||(each==-1&&n!=0)){$(this).css({"background-color":"#fff","border":"1px solid #999"})}
				if(n==2&&!$(this).hasClass("active")){$(this).css({"background-color":"#fff","border":"1px solid #fff"});}
				if(n!=2){$(this).css("color",$(this).css("border-left-color")+"");}
		});	
	})	
	/* 文档 树形结构的伸开 与收缩 */
	var sn=0;
	$(".document4 .ldn  .ul h3").click(function(){		
		$(this).toggleClass("down");
		$(this).siblings("li").slideToggle();
		if(sn%2==0){}else{$(".tree_h").nextAll(".treec").css("display","none")}
	})
	$(".document4 .ldn  .ul .tree_h").click(function(){
		sn++;
		$(this).toggleClass("treedown");
		$(this).nextAll(".treec").slideToggle();		
	})
	
$(".cB_banner .cbb_left ul .li1").click();
	/* 样式展开 */
	var clickb= 0;
	$(".cB_tools .cbt_div5 .ul2 .li3").click(function(e){
		clickb==1 ? $(this).siblings(".li1").click() : 0;
		$(".cB_tools .cbt_div5 .ul1").toggleClass("open");
		stopPropagation(e);
	})
	$(".cB_tools .cbt_div5 .ul2 .li2").click(function(){
		clickb=1;
		$(".cB_tools .cbt_div5 .ul1 li:nth-child(-n+5) img,.cB_tools .cbt_div5 .ul1 li:nth-child(-n+5)").animate({"height":0,"border-width":"0px"},{easing: 'easeOutQuad', duration:500});
		
	})
	$(".cB_tools .cbt_div5 .ul2 .li1").click(function(){
		clickb=0;
		$(".cB_tools .cbt_div5 .ul1 li:nth-child(-n+5)").animate({"height":61,"border-width":"2px"},{easing: 'easeOutQuad', duration:500});
		$(".cB_tools .cbt_div5 .ul1 li:nth-child(-n+5) img").animate({"height":57},{easing: 'easeOutQuad', duration:500});
		
	})
/* 弹窗 */

/* 预览窗移动 */
var dragging = false;
var iX, iY;
var _this;//判断托动的窗体
var this_;
$(".pre_view h3 img").click(function(){
	$(".pre_view,.box_content,.ui_back").fadeOut(100);
})
$(".pre_view h3").mousedown(function(e){
	_this=$(this)[0];this_ = $(this).parents(".pre_view")[0];
	dragging = true;
	iX = e.clientX;
	iY = e.clientY;
	_this.setCapture && _this.setCapture();	
	return false;
})
$(document).mousemove(function(e) {
	if (dragging) {
	var e = e || window.event;
	var oX = e.clientX - iX;
	var oY = e.clientY - iY;
	//console.log(iX,iY);
	$(".pre_view").css({"left":oX + "px", "top":oY + "px"});
	return false;
	}
})
$(document).mouseup(function(e) {
	try{
		dragging = false;
		$(".pre_view h3")[0].releaseCapture();
		e.cancelBubble = true;
	}catch(e){
		
	}

})
/* 所有弹出框关闭按钮 */
$(".pop_div2 .pop_closeBt,.sb_button .a2,.sb_button .a3").click(function(){		
	var a=0;
	$(this).parents(".pop_div2").fadeOut(100);
	$(this).parents(".box_content").find(".pop_div2").each(function(){
		if($(this).css("display")=="block" ){a++;}
	});
	a>1?0: $(".box_content,.ui_back").fadeOut(100);
	if(this.id=="plugin_properties"||this.id=="pluginClose"){
		dragclean();
	}
	//清楚插件预览窗口大小
	$(".pre_view").removeClass('pre_view_plugin');	
	$(".news_sourse .sb_middle .over_scroll").html('');
})
$('#advanced_search_close,#contents_plugin_close').click(function(){	
	$(this).parents('.pop_div2').find('input#ascondition_fromSource,input#contentpluginFromSource').val('');	
	
})
/* 高级搜索 */
$(".r_listbox .list_tp .div .a2").click(function(){	
	$(".advanced_search,.box_content,.ui_back").fadeIn(100);
	$(".advanced_search select option").click(function(){//高级搜索选择1.时间排序 2.焦点排序
		searchParam.sort=$(this).attr("value");
	})
})
/* 全局属性 */
$(".cB_tools .cbt_div17 ul .li1").click(function(){
	showGlobal();
	$(".global_properties,.box_content,.ui_back").fadeIn(100);
})
/* 大纲属性 */
$(".cB_tools .cbt_div17 ul .li2").click(function(){
	showOutLine();
	$(".set_outline_attribute,.box_content,.ui_back").fadeIn(100);
})

/* 选择行业 */
$(".hang_ye").click(function(){
	$.ajax({
			  url: "/webword/global/getIndustryByPid.do?pid=0",
			  type: "post",
			  dataType: "json",
			  success: function(d){
				 str="";
				 for(a=0;a<d.length;a++){//一级
					 if(d[a].pid==0){
						 str+="<dl>";
						 str+="<h5 class='tree"+d[a].id+"' onmousedown='tree_get("+d[a].id+")'><span><i></i><input type='radio' id='"+d[a].id+"' name='radio6'/><img src='word/img/choose2.png' />"+d[a].name+"</span></h5>";						 
						 str+="</dl>";
					 }
				 }
				 $(".industry_options .sb_middle div").html(str);
				
			  }
		  });
	$(this).blur();	  
	$(".industry_options,.box_content,.ui_back").fadeIn(100);
	$(".industry_options .sb_button .a1").click(function(){
		var choise_value="",aid=0;
		$(this).parents(".industry_options").find("span").each(function(){
			if($(this).find("input").attr("checked")=="checked"){choise_value=$(this).text();aid=$(this).find("input").attr("id");}		
		})
		$(".pop_div2").each(function(){
			if($(this).css("display")=="block"){$(this).find(".hang_ye").val(choise_value);$(this).find(".hang_yeid").attr("value",aid);}				
		})		
		$(".industry_options").fadeOut(100);
	})
})

/* 行业样式自动变化 */
/* $(".industry_options .sb_middle dl").each(function(y){
	$(this).find("h5").each(function(){//一级菜单
		if($(this).nextAll("dd,dt").length==0){$(this).find("i").addClass("i_has")}else{$(this).find("i").removeClass("i_has")}
		$(this).click(function(){
			if(!$(this).hasClass("i_has")&&$(this).find("i").hasClass("i_down")){$(this).nextAll("dt,dd").slideUp(300).find("i").removeClass("i_down");$(this).find("i").removeClass("i_down");}
			else if(!$(this).hasClass("i_has")&&!$(this).find("i").hasClass("i_down")){
				$(this).nextAll("dt").slideDown(300).find("i");
				$(this).find("i").addClass("i_down");
			}	
		})
	})
	$(this).find("dt").each(function(){
		if($(this).nextUntil("dt").length==0){$(this).find("i").addClass("i_has")}else{$(this).find("i").removeClass("i_has")}
		$(this).click(function(){
			if(!$(this).hasClass("i_has")){$(this).nextUntil("dt").slideToggle(300);$(this).find("i").toggleClass("i_down");}
		})
	})
}) */

/* 新闻源展开树 */
$(".news_sourse .sb_middle .tree1").each(function(){
	//$(this).find("i").click(function(){//收展树
		//$(this).toggleClass("i_down").parents("ul").find(".tree2").slideToggle(300);
		//$(this).toggleClass("i_down");
	//});
	$(this).find("input").click(function(){//每一组全选
			$(this).attr("checked")=="checked"?$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",$(this).attr("checked")) 
			:$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",false) ;
	});		
	$(".news_sourse .sb_middle .span2 input").click(function(){//全部选中
		$(this).attr("checked")=="checked"?$(this).parents(".span2").siblings(".over_scroll").find("input").attr("checked",$(this).attr("checked"))
		:$(this).parents(".span2").siblings(".over_scroll").find("input").attr("checked",false);		
	})
})
/* 新闻源移动 */
//$(".news_sourse,.box_content,.ui_back").fadeIn(100);
var bc,ac;
/* 向右移动 */
$(".news_sourse .sb_middle div.divm img.add").live("click",function(){
	$(".news_sourse .sb_middle .div1 ul:visible .tree1").each(function(index1){
			bc=$(".news_sourse .sb_middle .div2").find(".ol1");
			
			if($(this).find("input").attr("checked")=="checked"){
				var sco=0;//判断有没有相同的
				var _this=$(this);
				for(i=0;i<bc.length;i++){								
					if(_this.parents("ul").find(".ol1").text()==bc.eq(i).text()){						
						sco=1;
					}//发现相同网站不移动
				 }				
				 if(sco==0){//判断右侧没有相同的 新建个
					//var str1="<ul style='display:block'><span class='tree1'>"+_this.html()+"</span></ul>";
					var str1="<ul style='display:block' tid='"+_this.parent().attr("tid")+"'><span class='tree1'><span><input type='checkbox' checked='checked' /><ol class='ol1'>"+_this.parent().find(".ol1").text()+"</ol></span></span></ul>";
					$(".news_sourse .sb_middle .div2 .over_scroll").append(str1);
					//$(".news_sourse .sb_middle .div2 .over_scroll").find("input").attr("checked","checked")
					_this.parent("ul").fadeOut(0);
					
				 }			
				
				$(".news_sourse .sb_middle .tree1").each(function(){
					$(this).find("input").click(function(){//每一组全选
						$(this).attr("checked")=="checked"?$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",$(this).attr("checked")) 
						:$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",false) ;
					});	
				});
			}
		bc=$(".news_sourse .sb_middle .div2").find(".ol1");
	});
});
/* 向左移动 */
$(".news_sourse .sb_middle div.divm img.remove").live("click",function(){		
	$(".news_sourse .sb_middle .div2 ul:visible .tree1").each(function(index1){
		ac=$(".news_sourse .sb_middle .div1").find(".ol1");	
		if($(this).find("input").attr("checked")=="checked"){
				var _this=$(this);
				var sco=0;//判断有没有相同的
				var str=str1='';
				for(i=0;i<ac.length;i++){				
					if(_this.parents("ul").find(".ol1").text()==ac.eq(i).text()){
						ac.eq(i).parent().parent().css("display","inline");
						ac.eq(i).parent().parent().find('input[type="checkbox"]').attr('checked','checked');
						ac.eq(i).parent().parent().parent().css("display","block");
				}else{					
					}
				_this.parent("ul").remove();
				//_this.parent("ul").fadeOut(0)
				}
				
				$(".news_sourse .sb_middle .tree1").each(function(){
					$(this).find("input").click(function(){//每一组全选
						$(this).attr("checked")=="checked"?$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",$(this).attr("checked")) 
						:$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",false) ;
					});	
				});
			}
		ac=$(".news_sourse .sb_middle .div1").find(".ol1");		
	});
});
//高级搜索的搜索功能
function highSearch(selfThat,isLeSear){
	//selfThat 弹出 “选择新闻源” 的弹层的input的value
	//isLeSear 判断是否是  选择新闻源弹出层本身左侧的搜索
	var newsNameVa,moduleNameVa;
	if(isLeSear=='yes'){
		newsNameVa=$('#LeSeaBox input').eq(0).val();
		moduleNameVa=$('#LeSeaBox input').eq(1).val();
	}else if(isLeSear=='no'){		
		newsNameVa='';
		moduleNameVa='';
	}	
	
	var hiddenId=new Array;
	var noIdsVa='1,2,3';
	$(".news_sourse .sb_middle .div1 .over_scroll").html('');	
	$.ajax({
 	   type: "GET",
 	   url: "/webword/homePage/searchNewsSource.do?newsName="+newsNameVa+"&moduleName="+moduleNameVa+"&noIds="+noIdsVa+"",
 	   dataType:"json",
 	  success: function(result){		   
		   var html='';		     
		   if(selfThat!=''){			   
			    var self_text;			    
				self_text=selfThat.split('_');
				if(self_text.length>0){
						for(var j=0;j<self_text.length;j++){
							var str1="<ul style='display:block'><span class='tree1'><span><input type='checkbox' checked='checked' /><ol class='ol1'>"+self_text[j]+"</ol></span></span></ul>";
							
							$(".news_sourse .sb_middle .div2 .over_scroll").append(str1);
							for(var i = 0; i < result.length; i++){
								if(result[i]==self_text[j]){									
									//result.splice(i,1);
									hiddenId.push(self_text[j]);
									
								}
							}
						}
				}		
		   }
		   
		   for(var i = 0; i < result.length; i++){			   
				if(hiddenId.length>0 ){					
					for(var j = 0; j < hiddenId.length; j++){
						if( result[i] == hiddenId[j]){
							//console.log(result[i].id);
							html+='<ul style="display:none"><span class="tree1"><span><input type="checkbox" /><ol class="ol1">'+result[i].sourcename+'</ol></span></span></ul>';	
							result.splice(i,1);
						}
					}					
				}
				
				html+='<ul tid="'+result[i].id+'"><span class="tree1"><span><input type="checkbox" /><ol class="ol1">'+result[i].sourcename+'</ol></span></span></ul>';						
		 
		   }
		   
		  $(".news_sourse #LeSeaBox .over_scroll").html(html);
		  $(".news_sourse .sb_middle .tree1").each(function(){
			$(this).find("input").click(function(){//每一组全选
					$(this).attr("checked")=="checked"?$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",$(this).attr("checked")) 
					:$(this).parents(".tree1").nextUntil(".tree1").find("input").attr("checked",false) ;
			});	
			$(".news_sourse .sb_middle .span2 input").click(function(){//全部选中
				$(this).attr("checked")=="checked"?$(this).parents(".span2").siblings(".over_scroll").find("input").attr("checked",$(this).attr("checked"))
				:$(this).parents(".span2").siblings(".over_scroll").find("input").attr("checked",false);		
			});
		});
	   },
 	   error:function(){
 		  $("#LeSeaBox .over_scroll").html("获取信息失败,请稍后重试");
 	   }
 	});
}
$('#LeSeaBox a#LeSearch').click(function(){highSearch('','yes');});
$('#RiSeaBox a#RiSearch').click(function(){
	bc=$(".news_sourse .sb_middle .div2").find(".ol1");
	var textArr=bc;	
	var str=str1='';
	var idr=idr1='';		
	for(var i=0;i<textArr.length;i++){
	  str = textArr.eq(i).text()+'_';
	  str1+=str;
	}
	str1=str1.substr(0,(str1.length-1));
	
	var ss=$('#RiSeaBox input').eq(0).val();		
	if(ss==''){
		$(".news_sourse .sb_middle .div2 .over_scroll").html('');		
		//highSearch(str1,'no');		 	   			    
	    str1=str1.split('_');
		if(str1.length>0){
			for(var j=0;j<str1.length;j++){
				var html="<ul style='display:block'><span class='tree1'><span><input type='checkbox' checked='checked' /><ol class='ol1'>"+str1[j]+"</ol></span></span></ul>";
				$(".news_sourse .sb_middle .div2 .over_scroll").append(html);				
			}
		}		
		  
	}else{		
		for(i=0;i<bc.length;i++){
			var flag = bc.eq(i).text().indexOf(ss);
			if(flag=='-1'){
				bc.eq(i).parent().parent().parent().hide();
			}
		}
	}
});
	
/* 弹出新闻源 */
var newsFlag;
$(".news_sourse1").click(function(){
	$('#form_cities').hide();
	$(this).blur();
	$(".news_sourse,.box_content,.ui_back,.ui_back2").fadeIn(100);	
	highSearch($(this).val(),'no');
	$('.news_sourse #LeSeaBox input').val("");
	$('.news_sourse #RiSeaBox input').val("");
	var cla=$(this).parent().parent().parent();
	if(cla.hasClass('advanced_search')){
		newsFlag='newsFlag1';
	}else if(cla.hasClass('contents_plugin')){
		newsFlag='newsFlag2';
	}
})
/* 新闻源确认 */
$(".news_sourse .sb_button .a1").click(function(){	
	var textArr=$('.news_sourse .sb_middle .div2 .over_scroll ul .ol1');	
	var str=str1='';
	var idr=idr1='';		
	for(var i=0;i<textArr.length;i++){
	str = textArr.eq(i).text()+'_';
	idr = textArr.eq(i).parent().parent().parent().attr('tid')+',';
	str1+=str;
	idr1+=idr;	}
	str1=str1.substr(0,(str1.length-1));
	idr1=idr1.substr(0,(idr1.length-1));
	//$("input#getNewsName").val(str1);
	//$('input#getNewsId').val(idr1);
	$(this).parents(".pop_div2").fadeOut(100);
	if(newsFlag=='newsFlag1'){
		$("input#ascondition_fromSource").val(str1);
		$('input#ascondition_fromId').val(idr1);
	}else if(newsFlag=='newsFlag2'){
		$("input#contentpluginFromSource").val(str1);
		$('input#contentpluginFromId').val(idr1);
		}
	$(".news_sourse .sb_middle .over_scroll").html('');	
})

//栏目》新闻源》回车效果
$(document).keydown(function(event){  
if (event.keyCode == "13") {//keyCode=13是回车键      
	if($('.news_sourse').is(":visible")){		
		var isFocus=$(".news_sourse #LeSeaBox input.input").is(":focus"); 
		if(true==isFocus){ 		
			$(".news_sourse #LeSeaBox a#LeSearch").click();
		}else{ 		
			$(".news_sourse #RiSeaBox a#RiSearch").click();
		} 
	}
	
}
});
/* 选择区域城市 */
$(".are_a").click(function(){
	$.ajax({
			  url: "/webword/global/getAllArea.do?mid=0",
			  type: "post",
			  dataType: "json",
			  success: function(d){
				 str="";
				 for(a=0;a<d.length;a++){//一级
					 if(d[a].mid==0){
						 str+="<dl>";
						 str+="<h5 class='treem"+d[a].id+"' onmousedown='tree_get2("+d[a].id+")'><span><i></i><input type='radio' id='"+d[a].id+"' name='radiom'/><img src='word/img/choose2.png' />"+d[a].name+"</span></h5>";						 
						 str+="</dl>";
					 }
				 }
				 $(".city_options .sb_middle div").html(str);
				
			  }
		  });
	$(this).blur();	  
	$(".city_options,.box_content,.ui_back").fadeIn(100);
	$(".city_options .sb_button .a1").click(function(){
		var choise_value="",aid=0;
		$(this).parents(".city_options").find("span").each(function(){
			if($(this).find("input").attr("checked")=="checked"){choise_value=$(this).text();aid=$(this).find("input").attr("id");}
		})
		$(".pop_div2").each(function(){
			if($(this).css("display")=="block"){$(this).find(".are_a").val(choise_value);$(this).find(".are_aid").attr("value",aid);}
		})
		$(".city_options").fadeOut(100);
	})

})

/* 新建文档 */
$(".cB_banner .cbb_right").click(function(){	
	$(".sbox_save,.box_content,.ui_back").fadeIn(100);
	document.getElementById("replaceName").value = G_articleName;
})
/* 提交审核 */
$(".cB_header .cbh_right ul li:nth-child(5)").click(function(){
	$(".save_success").hide();
	if(userPerm()){
		return false;
	}
	$(".submit_audit,.box_content,.ui_back,.ui_back2").fadeIn(100);
	$("#articleName").val($("#newarticlename").text());
	$("#articleName_1").val($("#newarticlename").text());
	if(G_articleLabel==""){
		$("#articleLabelText").val($("#newarticlename").text());
	}else{
		$("#articleLabelText").val(G_articleLabel);
	}
	$("#brief").val(G_articleSkip);//简介
	$("#keyword").val(G_articleKeyWord);//关键词
	showLabel();//显示关键字
	$("#TypeRadio1").attr('checked','checked');
	//console.log($("#TypeRadio1").attr('checked'));
})
//提交审核弹层中的，radio切换
$(".submit_audit .sb_middle #TypeRadio2").click(function(){		
	$('.submit_audit .tabDivBg .qikan_h2').hide();  	})
$(".submit_audit .sb_middle #TypeRadio1").click(function(){		
	$('.submit_audit .tabDivBg .qikan_h2').show();  	})
if($('.submit_audit').length>0){
	//我的上传，我的审核，联想框
	var ss =  new oSearchSuggest($('.submit_audit .tabDivBg #qikanName'),$('.submit_audit .sb_middle'),'86','73',$('.submit_audit'),10,function(li){
		$("#articleJournalId").val();
		var journalid = $(li).attr("journalid");
		var journalTypeId = $(li).attr("typeid");
		var journalPrice = $(li).attr("price")
		ZU.$id("articleJournalId_1").value=journalid;
		ZU.$id("articleJournalTypeId_1").value=journalTypeId;
		journalTimeChange();//显示期刊的名字
		var priceNode = ZU.$id("article_price");
		var priceNodeValue=getJournalPrice(journalPrice,journalTypeId)
		priceNode.setAttribute("price",priceNodeValue);
		priceNode.setAttribute("journalPrice",journalPrice);
		priceNode.value = priceNodeValue//计算前的价格
	});	
}
/* 提交审核中 新建期刊 */
$(".submit_audit a.creatNew").click(function(){	
	$(".creat_pop").show().css({
		"position":"absolute",
		"top":"10px",
		"left":"50%",
		"margin":"0 0 0 -250px"
	});
	//显示期刊类型
	var url="global/getJournalType.do"
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"get",
		success:function(data){
			var typeList=data.typeList;
			var str="";
			var selectNode = ZU.$id("typeId");
			selectNode.innerHTML="";
			 for(var i=0;i<typeList.length;i++){
				 var option = new Option(typeList[i].name,typeList[i].id);
				 option.setAttribute("type",typeList[i].type);
				 selectNode.options.add(option);
				 //str+="<option value="+typeList[i].id+" type="+typeList[i].type+">"+typeList[i].name+"</option>"
			 }
			 if(typeList.length>0){
				 //ZU.$id("typeId").innerHTML=str;
			 }
		},
		failure:function(data){
		}
	});
})
/*提交审核中 新建期刊 》关闭*/
function closeCreatPer(){$('.creat_pop').hide();}
/* 另存为 */
$(".cB_header .cbh_right .tool_ul li:nth-child(3)").click(function(){
	$(".sbox_save_as,.box_content,.ui_back").fadeIn(100);
	$("#saveAsText").val($("#newarticlename").text());
})

$(".cB_header .cbh_right .tool_ul li:nth-child(6)").click(function(){
	$(".upload_fire,.box_content,.ui_back").fadeIn(100);
})

/* 高级搜索 */
$(".r_listbox .list_tp .div a.a2").click(function(){
	historyList();
	$(".advanced_search,.box_content,.ui_back").fadeIn(100);
})
/* 高级搜索确认 */
$(".advanced_search .sb_button .a1").click(function(){
	var allkeywords=$(".allkeywords").val(),//全部关键字
		onlykeywords=$(".onlykeywords").val(),//任意一个关键字
		notkeywords=$(".notkeywords").val();//不包含关键字
		if(allkeywords==""&&onlykeywords==""&&notkeywords==""){
			$(".advanced_search .sb_middle .tip").css("display","block");
			setTimeout(function(){$(".advanced_search .sb_middle .tip").css("display","none");},2000); 
		}
})

/* 设置封面属性 */
$(".cover_properties .select").unbind("click");
$(".cover_properties .are_i").click(function(){//选择区域
	$(".are_a").click();
})
$(".cover_properties .hang_ye_i").click(function(){//选择行业
	$(".hang_ye").click();
})
$(".cB_tools .cbt_div12 ul li").click(function(){
	if(userPerm()){
		return false;
	}
	$(".cover_properties,.box_content,.ui_back").fadeIn(100);
	
	var param = {
		articleId:articleId
	};
	var url="homePage/getHomePageSetting.do?"+ZU.jsonToUrl(param);
	ZU.Ajax.request(url,{
		async:true,//是异步的
		method:"GET",
		success:function(data){
			if(data.status == "success"){
				$("#industryId").val(data.data.industryname);
				$("#industry_id").val(data.data.industryId);
				$("#industryId").find("option:selected").text(data.data.industryname);
				$("#areaId").val(data.data.areaname);
				$("#area_id").val(data.data.areaId);
				$("#areaId").find("option:selected").text(data.data.areaname);
				$("#title").val(data.data.title);
				$("#subtitle").val(data.data.subtitle);
			}
		},
		failure:function(data){
		}
	});
})
/* 复制的hover事件 */
setTimeout(function(){$(".global-zeroclipboard-container").hover(function(){
			$(".cB_tools .cbt_div2 .ul2 .li1").css("background-color","#e2f4e9");
		},function(){
			$(".cB_tools .cbt_div2 .ul2 .li1").css("background-color","#fff");
		})
		
},1000);
/* 保存成功 */
$(".save_success .sb_button .a1").click(function(){
	$(".save_success,.box_content,.ui_back,.ui_back2").fadeOut(100);
})

/*ready_end*/
})
/* 给所有弹出框定义样式 */
function set_sbox(c){	
	var he=-$(c)[0].offsetHeight/2;
	$(c).css({"top":"50%","margin-top":he});
}
/* 区域城市树获取二级 */
function tree_get2(id,treesub){	
	$.ajax({
			  url: "/webword/global/getAllArea.do?mid="+id,
			  type: "post",
			  dataType: "json",
			  success: function(d){
				var str="";
				for(b=0;b<d.length;b++){//二级
					 if(d[b].mid==id){
						 treesub!=2?str+="<dt id='tree2m"+d[b].id+"'><span><i onmousedown='tree_get3("+d[b].id+")'></i><input type='radio' id='"+d[b].id+"' name='radiom'/><img src='word/img/choose2.png' />"+d[b].name+"</span></dt>"
						:str+="<dd><span><input type='radio' id='"+d[b].id+"' name='radiom'/><img src='word/img/choose2.png' />"+d[b].name+"</span></dd>";
					 }
				}				
				if($(".treem"+id+"").parents("dl").find("dt").length==0&&treesub!=2){$(".treem"+id+"").after(str);}
				if($("#tree2m"+id+"").nextUntil("dt").length==0&&treesub==2){$("#tree2m"+id+"").after(str);$("#tree2m"+id+"").nextUntil("dt").slideToggle(300);}
			   
			   $(".city_options .sb_button .a1").click(function(){
					var choise_value="",aid=0;
					$(this).parents(".city_options").find("span").each(function(){
						if($(this).find("input").attr("checked")=="checked"){choise_value=$(this).text();aid=$(this).find("input").attr("id");}							
					})
					$(".pop_div2").each(function(){
						if($(this).css("display")=="block"){$(this).find(".are_a").val(choise_value);$(this).find(".are_aid").attr("value",aid);}				
					})
					$(".city_options").fadeOut(100);
				})
			   /* 行业样式自动变化 */
				if(treesub!=2){
					$(".treem"+id+"").each(function(){//一级菜单
						/*  */
						if($(this).parents("dl").find("dt").length==0){$(this).find("i").addClass("i_has").toggleClass("i_down");}
						else{
							$(this).find("i").removeClass("i_has");							
							if(!$(this).hasClass("i_has")&&$(this).find("i").hasClass("i_down")){
								$(this).nextAll("dt,dd").slideUp(300).find("i").removeClass("i_down");
								$(this).find("i").removeClass("i_down");
								}
							else if(!$(this).hasClass("i_has")&&!$(this).find("i").hasClass("i_down")){
								$(this).nextAll("dt").slideDown(300);
								$(this).find("i").addClass("i_down");
							}								
						}
						/*  */
					})
				}
					/*$(".city_options .sb_middle dl dt").each(function(){//二级菜单													
							$(this).find("i").click(function(e){																				
							})						  
					})*/
					
			  }
	});	
		
}
/* 区域城市获取三级 */
function tree_get3(id){
	if($("#tree2m"+id+"").nextUntil("dt").length==0){
		//console.log(2);
		tree_get2($("#tree2m"+id+"").find("input").attr("id"),2);
		$("#tree2m"+id+"").nextUntil("dt").slideToggle(300);
		$("#tree2m"+id+"").find("i").toggleClass("i_down");			
	}else{
		//console.log(3);
		tree_get2($("#tree2m"+id+"").find("input").attr("id"),2);
		$("#tree2m"+id+"").nextUntil("dt").slideToggle(300);
		$("#tree2m"+id+"").find("i").toggleClass("i_down");	
	}
}
/* 行业树获取二级 */
function tree_get(id,treesub){	
	$.ajax({
			  url: "/webword/global/getIndustryByPid.do?pid="+id,
			  type: "post",
			  dataType: "json",
			  success: function(d){
				var str="";
				for(b=0;b<d.length;b++){//二级
					 if(d[b].pid==id){
						 treesub!=2?str+="<dt id='tree2"+d[b].id+"'><span><i></i><input type='radio' id='"+d[b].id+"' name='radio6'/><img src='word/img/choose2.png' />"+d[b].name+"</span></dt>"
						:str+="<dd><span><input type='radio' id='"+d[b].id+"' name='radio6'/><img src='word/img/choose2.png' />"+d[b].name+"</span></dd>";
					 }
				}				
				if($(".tree"+id+"").parents("dl").find("dt").length==0&&treesub!=2){$(".tree"+id+"").after(str);}
				if($("#tree2"+id+"").nextUntil("dt").length==0&&treesub==2){$("#tree2"+id+"").after(str);}
			   
			   $(".industry_options .sb_button .a1").click(function(){
					var choise_value="",aid=0;
					$(this).parents(".industry_options").find("span").each(function(){
						if($(this).find("input").attr("checked")=="checked"){choise_value=$(this).text();aid=$(this).find("input").attr("id");}							
					})
					$(".pop_div2").each(function(){
						if($(this).css("display")=="block"){$(this).find(".hang_ye").val(choise_value);$(this).find(".hang_yeid").attr("value",aid);}				
					})
					$(".industry_options").fadeOut(100);
				})
			   /* 行业样式自动变化 */
				if(treesub!=2){
					$(".tree"+id+"").each(function(){//一级菜单
						/*  */
						if($(this).parents("dl").find("dt").length==0){$(this).find("i").addClass("i_has");}
						else{
							$(this).find("i").removeClass("i_has");							
							if(!$(this).hasClass("i_has")&&$(this).find("i").hasClass("i_down")){
								$(this).nextAll("dt,dd").slideUp(300).find("i").removeClass("i_down");
								$(this).find("i").removeClass("i_down");
								}
							else if(!$(this).hasClass("i_has")&&!$(this).find("i").hasClass("i_down")){
								$(this).nextAll("dt").slideDown(300);
								$(this).find("i").addClass("i_down");
							}								
						}
						/*  */
					})
				}
					$(".industry_options .sb_middle dl dt").each(function(){//二级菜单													
							$(this).find("i").click(function(){
									if($("#tree2"+id+"").nextUntil("dt").length==0){
										$(this).parents("dt").nextUntil("dt").slideToggle(300);
										$(this).toggleClass("i_down");
										tree_get($(this).siblings("input").attr("id"),2);
									}else{
										
									}
											
							})
						/*  */
					})
			  }
	});	
		
}



/*保存为我的新闻*/
function save_mynews(crawlTitle,crawlId,categoryId,esId){
		crawlTitle=ZU.clearHtml(crawlTitle);
		$(".ui_back,.box_content,.save_mynews").fadeIn(100);
		$(".save_mynews").find("input").val(crawlTitle);
		$(".save_mynews .sb_button .a1").unbind('click');
		$(".save_mynews .sb_button .a1").click(function(){
			var da=encodeURI($(this).parents(".pop_div2").find("input").val());
			$.ajax({
				  url: "/webword/document/addContent.do",
				  type: "post",
				  dataType: "json",	
				  data:"contentName="+da+"&newsId="+crawlId+"&categoryId="+categoryId+"&esId="+esId,
				  success: function(d){
					if(d.state=="success"){
						$(".save_mynews").fadeOut(50);
						$(".save_success").find("p").html("保存成功");
						$(".save_success,.box_content,.ui_back").fadeIn(100);
						setTimeout(function(){$(".save_success,.box_content,.ui_back").fadeOut(100)},1500);}
					else {
						$(".save_mynews").find(".tip").fadeIn(100);
						setTimeout(function(){$(".save_mynews").find(".tip").fadeOut(100);},3000)
						}
				  }
			  });
			 //$(".save_mynews .sb_button .a1").unbind('click');
			 getNewsMy(1);
		})
		
}
function pre_views(crawlTitle,crawlId,categoryId,esId){
	//crawlTitle=ZU.clearHtml(crawlTitle).substring(0,22);/*20150819 标题少字*/
	crawlTitle=ZU.clearHtml(crawlTitle);
	/* 普通新闻预览 */														
		$.ajax({
			  url: "/webword/document/queryContentByCrawlId.do?crawlId="+crawlId+"&categoryId="+categoryId+"&esId="+esId,
			  type: "post",
			  dataType: "json",
			  success: function(d){
				$(".pre_view,.box_content,.ui_back").fadeIn(100);
				$(".pre_view h3 span").html(crawlTitle);//新闻源弹出层，也有h3 span，所以添加了.pre_view				
				$(".pre_view .sb_middle div").html(d.contentText);
			  }
		  });	
}
function pre_myviews(crawlTitle,crawlId){
	//crawlTitle=ZU.clearHtml(crawlTitle).substring(0,22);/*20150819 标题少字*/
	crawlTitle=ZU.clearHtml(crawlTitle);
	/* 我的新闻预览 */														
		$.ajax({
			  url: "/webword/document/queryContentById.do?newsId="+crawlId,
			  type: "post",
			  dataType: "json",
			  success: function(d){
				 $(".pre_view,.box_content,.ui_back").fadeIn(100);
				 $(".pre_view h3 span").html(crawlTitle);//新闻源弹出层，也有h3 span，所以添加了.pre_view					 
				$(".pre_view .sb_middle div").html(d.contentText);
			  }
		  });	
}
function pre_mydocumet(articleTitle,articleId){
	/* 我的文档预览 */														
		$.ajax({
			  url: "/webword/word/getContent.do?articleId="+articleId,
			  type: "post",
			  dataType: "json",
			  success: function(d){
				 $(".pre_view,.box_content,.ui_back").fadeIn(100);
				 $(".pre_view h3 span").html(articleTitle);//新闻源弹出层，也有h3 span，所以添加了.pre_view							 
				 if(d.nodeContent==undefined){
					 $(".pre_view .sb_middle div").html("");
				 }else{
					 if(d.savepicpath!=""){
						 var savepicpath = "<img src='"+d.savepicpath+"' /><br />"
						 $(".pre_view .sb_middle div").html(savepicpath+d.nodeContent);
					 }else{
						 $(".pre_view .sb_middle div").html(d.nodeContent);
					 }
					 
				 }
				
			  }
		  });	
}
function pre_mydocumet_list(articleTitle,articleId,hId,hName){
	/* 我的文档节点预览 */
		$.ajax({
			  url: "/webword/getNodeContent.do?articleId="+articleId+"&hId="+hId+"&hName="+hName,
			  type: "post",
			  dataType: "json",
			  success: function(d){
				 $(".pre_view,.box_content,.ui_back").fadeIn(100)
				 $(".pre_view h3 span").html(articleTitle);//新闻源弹出层，也有h3 span，所以添加了.pre_view		
				$(".pre_view .sb_middle div").html(d.nodeContent);
			  }
		  });	
}
/*保存为我的插件*/
function save_myslugins(pluginName,pluginId){
		$(".ui_back,.box_content,.save_myplug").fadeIn(100);
		$(".save_myplug").find("input").val(pluginName);
		$(".save_myplug .sb_button .a1").unbind('click');
		$(".save_myplug .sb_button .a1").click(function(){
			var da=$(this).parents(".pop_div2").find("input").val();
			$.ajax({
				  url: "/webword/plugin/saveUserPlugin.do",
				  type: "post",
				  dataType: "json",
				  data:"pluginId="+pluginId+"&pluginName="+da,
				  success: function(d){
					if(d.state=="success"){
						$(".save_myplug").fadeOut(50);
						$(".save_success").find("p").html("保存成功");
						$(".save_success,.box_content,.ui_back").fadeIn(100);
						set_sbox(".save_success");
						setTimeout(function(){$(".save_success,.box_content,.ui_back").fadeOut(100)},1500);}
					else if(d.state=="error"){
						$(".save_myplug").find(".tip").fadeIn(100);
						setTimeout(function(){$(".save_myplug").find(".tip").fadeOut(100);},2000)
						}
				  }
			  });
			//$(".save_myplug .sb_button .a1").unbind('click');
			getPluginMy(1,"");
		})
}
/* 预览 */	
function pre_views_plug(pluginName,pluginId){
		
		$(".pre_view").addClass('pre_view_plugin');	

		$.ajax({
			  url: "/webword/plugin/getPlugin.do?pluginId="+pluginId,
			  type: "post",
			  dataType: "json",
			  success: function(d){
				$(".pre_view,.box_content,.ui_back").fadeIn(100);
				$(".pre_view h3 span").html(pluginName);	//新闻源弹出层，也有h3 span，所以添加了.pre_view					
				$(".pre_view .sb_middle div").html(d.list.pluginIntro);
			  }
		  });
}

/* 进度条 */
var loading_box = function(para_option){
			var option = $.extend({
				title : "word文档生成中......"
				,text : "请等待"
			}, para_option || {});

			var obj = $("<div class='loading_box'></div>");
			var title = $("<p class='loading_box_title'>"+option.title+"</p>");
			var text = $("<p class='loading_box_text'>"+option.text+"</p>");
			var loading = $("<div class='loading_box_loading'></div>");
			var innerLoading = $("<div class='loading_box_inld'></div>");

			obj.append(title);
			obj.append(text);
			obj.append(loading);
			loading.append(innerLoading);

			var loadw = 0; 
			var inter;
			function show(){
				$('body').append(obj);
				inter=setInterval(function(){
					if(loadw==100)loadw = 0;
					loadw++;
					innerLoading.css("width",loadw+"%");
				},100);
			}
			show();
			$(".ui_back").fadeIn(100);
			return {
				close : function(){					
					obj.remove();$(".ui_back").fadeOut(100);
					clearInterval(inter);
				}
			}
		};
		/* //新建就new个
		var lb = new loading_box();
		setTimeout(function(){
			//关闭调用这个
			lb.close();
		},10000); */

function tree1click(i){
	$(".tree1"+i).find("i").toggleClass("i_down").parents("ul").find(".tree2").slideToggle(300);
}	
function getRandom(n){//随机数
        return Math.floor(Math.random()*n+1)
        }			
function GetDateDiff(startDate,endDate)//计算时间差  
{  
    var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();     
    var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();     
    var dates = Math.abs((startTime - endTime))/(1000*60*60*24);     
    return  dates;
}

/**
 * 判断浏览器是否支持placeholder，添加input提示文字
 * 内容插件
 */
$(document).ready(function(){
	if(typeof(document.createElement('input').placeholder)=='undefined'){
		$('.contents_plugin input[placeholder]').each(function(index,element){
			var phStr = $(this).attr('placeholder');
			if(phStr.length > 0){
				var ph = $("<label class='ie_ph_text'></label>").text(phStr).css({
					"color" : "#999",
					"width" : $(this).width(),
					"height" : $(this).css("height"),
					"line-height" : $(this).css("height"),
					"margin-top" : 0-Number($(this).height())-8,
					"cursor" : "text"
				}).click(function(){
					$(this).prev("input[placeholder]").focus();
				});
				if($(this).css('float') == "right"){
					ph.css({
						"float":"right"
					});
				}
				$(this).blur(function(){
					if($(this).val().length == 0)
						$(this).next(".ie_ph_text").show();
				}).focus(function(){
					$(this).next(".ie_ph_text").hide();
				});
				$(this).after(ph);
			}
		});
	};
})

/*编辑页面-topnav 个人中心和退出*/
function showHeadUl(){$('.cB_header .cbh_right .hideUl').show();}	
function hideHeadUl(){$('.cB_header .cbh_right .hideUl').hide();}
$(document).click(function(){
	hideHeadUl();	
});
/**/
function closeDiv(){$('.ui_back2,.ui_back,.box_content,.pop_div2').hide();	}
function closeCreatPer(){$('.creat_pop').hide();}
function show_save_success(fn){
	$('.ui_back2,.ui_back,.box_content,.save_success').fadeIn(100);
	set_sbox(".save_success");		
}
function hide_save_success(fn){$('.ui_back2,.ui_back,.box_content,.save_success').hide();fn()}
//退出
function show_exit(){$('.ui_back2,.ui_back,.box_content,.exit_pop').show();}
function closeExitDiv(){$('.ui_back2,.ui_back,.box_content,.exit_pop').hide();}
