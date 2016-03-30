var screenFlag = false; //当前是否全屏：false-->否；true-->是
var screenWidth = screen.availWidth - 17; //获取屏幕尺寸
var z = 100;
var baseZoom = (888 / 728) * z; //放大缩小按钮原始倍率
var harfZoom = baseZoom / 2;
var baseWidth = 728; //报告原始尺寸
var iframeHeight = ""; //加载完成后赋值
var onePageHeight = 1029; //每页的高度
var buyStatus = 0;//购买状态
var len = 1;//默认加载页面
var pageCount = 1;//文档总页数
var adHeight = 62;//iframe高度用
var suffix;
$(function() {
    // 回到顶部按钮
    $("#toTop").click(function(event) {
        $('body,html').animate({scrollTop: 0}, 0);
        return false;
    });
    len = $("#endPage").val();
    buyStatus = $("#buyStatus").val();
    pageCount = $("#pageCount").val();
	if (len == pageCount || buyStatus == 0){
		adHeight = -10;
	}
    //未购买的页面做限制
    if (buyStatus == 0){
    	if (len >= 5){
           len = 3;  		
    	}else{
           len = 1;    		
    	}
        $('.divOuter').css('display','block');
        showBuyNow();
    }
    var path ="doc/article" + $("#articleId").val(); + "/";
    suffix = $("#articleFormat").val();
    if(suffix && (suffix=="ppt" || suffix=="pptx")){
    	onePageHeight = 547;
    	iframeHeight = 560.44* len + adHeight;
    }else{
    	iframeHeight = 1042.24* len + adHeight;
    }
    $(window).scroll(function(event) {
    	setPageNo();
    	roll();   	
    });
    //$("#preContentBox").append("<iframe style='top:-5px;' id='content1' src='" + path + "/html5/index.html' frameBorder='0'  scrolling='no' width='100%' height='" + iframeHeight + "' onload='iframeOnload(this)'  ></iframe>");   
    var articleId = $("#articleId").val();
    $("#preContentBox").append("<iframe style='top:-5px;' id='content1' src='view/getHtml.do?articleId="+articleId+"' frameBorder='0'  scrolling='no' width='100%' height='" + iframeHeight + "' onload='iframeOnload(this)'  ></iframe>");   
    var T = setInterval(function(){
	    var iframe = document.getElementsByTagName("iframe");
	    var aaa = document.getElementsByTagName("iframe")[0].contentWindow.document.getElementById("page-container");
	    //var bbb = document.getElementsByTagName("iframe")[0].contentWindow.document.getElementById("sidebar"); 
	    if(aaa!=null /*&& bbb!=null*/){
	    	setNewZoom(baseZoom);
	        $("#preContentBox").show();
		    clearInterval(T);
	    }
   }, 500);
});

//全屏
function screenFull() {
    fullScreen();
}
//放大
function zoomBig() {
    changeZoom("bigger");
}

//缩小
function zoomSmall() {
    changeZoom("narrow");
}

//iframe回调
function iframeOnload(iframe) {
    var iframehrefs = $("#content1").contents().find("a[href^='#']");
    $.each(iframehrefs, function(index, value) {
        $(value).click(function() {
            var href = $(value).attr("href");
        	var hrefPage = href.substring(3, href.length);
        	var goPage = parseInt(hrefPage,16);
            var endPageNew = $("#endPage").val();
            if (goPage > endPageNew){
            	loadArticleContent(parseInt(endPageNew)+1,goPage,1)
            }else{
                var iframehref = $("#content1").contents().find(href);
                var pos = $(iframehref).offset().top;
                if(suffix && (suffix=="ppt" || suffix=="pptx")){
                	$('html,body').animate({scrollTop: (pos+150)+ "px"}, 0);	
                }else{
                    $('html,body').animate({scrollTop: (pos+320)+ "px"}, 0);	
                }
            }
            return false; //返回false,取消iframe中的锚链作用
        });
    });
    if(!$("#userId").val()){
    	//禁用frame中的右键  
    	iframe.contentWindow.document.body.oncontextmenu=function(){return false;};  
    	//取消选取
    	iframe.contentWindow.document.body.onselectstart=function(){return false;};  
    	//防止复制
    	iframe.contentWindow.document.body.oncopy=function(){return false;};  
    	//防止剪切
    	iframe.contentWindow.document.body.oncut=function(){return false;};  
    }
   // $('html,body').animate({scrollTop: 0 }, 0);
}

/*
 * 设置尺寸 放大倍率
 */
function setNewZoom(newZoom) {
    //原始尺寸以下不处理
    if (newZoom >= z) {
        //页面缩放
        iframeHtmlReSize(newZoom / z);
        var ewidth = baseWidth * (newZoom / z);
        if (screenFlag) {
            $(".previBody").css("width", screenWidth + "px");
            $(".buyNowDivOut").css("width", screenWidth + "px"); 

            $("a.reader-fullScreen").addClass('isFullScreen');
        } else {
        	$("a.reader-fullScreen").removeClass('isFullScreen');
            //如果是放大
            if (newZoom >= (harfZoom * 2)) {
                //主题区域宽度
                $(".previBody").css("width", ewidth + 332); //左侧框
                $(".buyNowDivOut").css("width", ewidth + 332); //左侧框
            } else {
                $(".previBody").css("width", 1200); //左侧框
                $(".buyNowDivOut").css("width", 1200); //左侧框
            }
        }
        //设置iframe宽高
        document.all("content1").width = ewidth;
        if (screenFlag) {
        	document.all("content1").height = iframeHeight * (newZoom / z) + 30;	
        }else{
            document.all("content1").height = iframeHeight * (newZoom / z);	
        }
        //内容区域宽度
        $(".preContentBox,.yulan").css("width", ewidth);
        $(".buyNowDiv").css("width", ewidth);
        setPageNo();
        roll();
    }
    $(".previewFooter a.zoom-add").removeClass('zoom-add-disable');
    $(".previewFooter a.zoom-decrease").removeClass('zoom-decrease-disable');
    //缩放完毕后看是否还可继续 控制按钮样式
    if((baseZoom-10)<z || screenFlag){
    	$(".previewFooter a.zoom-decrease").addClass('zoom-decrease-disable');	
    }
    if(((baseZoom+10)*baseWidth)/z>(screenWidth-332) || screenFlag){
    	$(".previewFooter a.zoom-add").addClass('zoom-add-disable');
    }
}
/*
 * 全屏切换
 */
function fullScreen() { 	
    if (screenFlag) {
        screenFlag = false;
        $("body").removeClass('max-screen');
        $(".previewFooter a.reader-fullScreen").removeClass('isFullScreen');
        setNewZoom(baseZoom);
        roll();
    } else {
        var ratio = screenWidth / baseWidth * z;
        screenFlag = true;
        $("body").addClass('max-screen');        
        $(".previewFooter a.reader-fullScreen").addClass('isFullScreen');
        setNewZoom(ratio);
        //hideBuyNow();
    }
}

//放大缩小
function changeZoom(zoom) {
    if (screenFlag) { //全屏时不操作放大缩小
        return;
    }
    if (zoom == "narrow") { //缩小
        if ((baseZoom - 10) >= z) {
            baseZoom -= 10;
            setNewZoom(baseZoom);
        }/*else{
        	baseZoom = z;
        	setNewZoom(baseZoom);
        }*/
    } else if (zoom == "bigger") { //放大
        if (((baseZoom+10)*baseWidth)/z<=(screenWidth-332)) { //200
            baseZoom += 10;
            setNewZoom(baseZoom);
        }/*else{
        	baseZoom = (screenWidth-332-8)*z/baseWidth;
            setNewZoom(baseZoom);
        }*/
    }
}

//页面跳转功能
function pageNumber(pageNumberId) {
    //未购买的页面做限制
    if (buyStatus == 0){
    	pageNumberId = len;
    }
    //获取iframe元素
    var endPageNew = $("#endPage").val();
    if (pageNumberId > endPageNew){
    	loadArticleContent(parseInt(endPageNew)+1,pageNumberId,1)
    }else{
        var iframePfId = $("#content1").contents().find("#pf" + pageNumberId.toString(16));
        if(suffix && (suffix=="ppt" || suffix=="pptx")){
        	$('html,body').animate({scrollTop: (iframePfId.offset().top+150)+ "px"}, 0);	
        }else{
            $('html,body').animate({scrollTop: (iframePfId.offset().top+320)+ "px"}, 0);	
        }
    }
}

//页面zoom方法
function iframeHtmlReSize(zoom) {
    var iframe = document.getElementsByTagName("iframe");
    var aaa = document.getElementsByTagName("iframe")[0].contentWindow.document.getElementById("page-container");
    var bbb = document.getElementsByTagName("iframe")[0].contentWindow.document.getElementById("sidebar"); /* for Firefox */
    $(aaa).css("-moz-transform", 'scale(' + zoom + ')');
    $(bbb).css("-moz-transform", 'scale(' + zoom + ')'); /* for Chrome || Safari */
    $(aaa).css("-webkit-transform", 'scale(' + zoom + ')');
    $(bbb).css("-webkit-transform", 'scale(' + zoom + ')'); /* for IE9 */
    $(aaa).css("-ms-transform", 'scale(' + zoom + ')');
    $(bbb).css("-ms-transform", 'scale(' + zoom + ')'); /* for Opera */
    $(aaa).css("-o-transform", 'scale(' + zoom + ')');
    $(bbb).css("-o-transform", 'scale(' + zoom + ')');
    if (zoom < 1) {
        $(aaa).css("-moz-transform-origin", 'left top');
        $(bbb).css("-moz-transform-origin", 'left top');
        $(aaa).css("-webkit-transform-origin", 'left top');
        $(bbb).css("-webkit-transform-origin", 'left top');
        $(aaa).css("-ms-transform-origin", 'left top');
        $(bbb).css("-ms-transform-origin", 'left top');
        $(aaa).css("-o-transform-origin", 'left top');
        $(bbb).css("-o-transform-origin", 'left top');
    } else {
        $(aaa).css("-moz-transform-origin", 'center top');
        $(bbb).css("-moz-transform-origin", 'center top');
        $(aaa).css("-webkit-transform-origin", 'center top');
        $(bbb).css("-webkit-transform-origin", 'center top');
        $(aaa).css("-ms-transform-origin", 'center top');
        $(bbb).css("-ms-transform-origin", 'center top');
        $(aaa).css("-o-transform-origin", 'center top');
        $(bbb).css("-o-transform-origin", 'center top');
    }
}

//设置页码
function setPageNo(){
	 scrollTop = $(document).scrollTop();
     //滚动设置页码(有问题)
     var pageCount = $("#pageCount").val();
     //未购买的页面做限制
     if (buyStatus == 0){
    	 if (pageCount >= 5){
    		 pageCount = 3; 
    	 }else{
    		 pageCount = 1;  
    	 }   	 
     }
     var preContentBoxWidth = $(".preContentBox").width();
     var iframeRatio = preContentBoxWidth / baseWidth; //倍率
     //11为每一页间隔
     var pageHeight = (onePageHeight+13.14) * iframeRatio;
     var pageNumber = parseInt((scrollTop + 300) / pageHeight);
     if (pageNumber <= (pageCount - 1)) {
         $("#pageNumber").val(pageNumber + 1);
     };
}

function roll(){		
	var scrollTop = $(this).scrollTop();
	var sc_Width = document.body.offsetWidth;
	var scrollHeight = $(document).height()-800; //浏览器当前窗口文档的高度 27388   减去底部和头部的高度800
	var scrollWidth = (sc_Width-$(".previBody").width())/2;
	var windowHeight = $(this).height();
	if(scrollTop + windowHeight == scrollHeight){
		return;
	}
	var top = $(".reader-side").offset().top;
	if(scrollTop>top&&scrollTop<scrollHeight){		
		if(sc_Width>1200){			
			$(".reader-side-3").addClass("PreFixed").css({"right":scrollWidth});
		}		
		//$(".backToTop").show();
	}else{
		if(sc_Width>1200){
			$(".reader-side-3").removeClass("PreFixed").css({"right":"auto"});
		}
		//$(".backToTop").hide();
	}
}

/*保存至我的文档/我的模板，弹出层*/
var getSaveId,getSaveName,savaFlag=2;
function sava_to_my(Name,Id,type){
	if(!isLogin()){
		return false;
	}
	getSaveId=Id;
	getSaveName=Name;
	if(type=='document'){
		//文档
		$(".sbox_save_to_wd,.box_content,.ui_back2,.ui_back").fadeIn(100);
		$(".sbox_save_to_wd input#saveToWd").val(getSaveName);
	}else{
		//模板
		$(".sbox_save_to_mb,.box_content,.ui_back2,.ui_back").fadeIn(100);
	}	
}
/*确定按钮 保存到我的文档*/
function sure_save_to(type){
	if(type=='document'){
		getSaveName=ZU.$id("saveToWd").value;
		if(getSaveName==""||getSaveName.replace(/\s+/g, '').length == 0){
			msgShow('名字不能为空或者全空格');
			return;
		}else if(getSaveName>40){
			msgShow('名字小于40个字符');
			return;
		}
		$(".sbox_save_to_wd,.box_content,.ui_back2,.ui_back").fadeOut(100);	
		type = 2;
	}else{
		$(".sbox_save_to_mb,.box_content,.ui_back2,.ui_back").fadeOut(100);
		type = $(".pop_div input[name=documentType]:checked").val();
	}
	var url='word/checkArticleName.do?articleId='+getSaveId+'&articleName='+getSaveName+'&flag=2&documentType='+type;
	ZU.Ajax.request(encodeURI(url),{
		async:true,//是异步的
		method:"GET",
		success:function(date){
			if(date.status=="success"){
				window.location = $("#path").val()+"word.do?articleId="+date.articleId;
			}else{
				msgShow(date.info);
			}
		},
		failure:function(date){
			msgShow('另存为失败');
		}
	});
}
//显示提示框
function msgShow(msg){
	new altInfo({
		text:msg
	});
}


//根据是否登录和是否购买判断下载、编辑等权限type 2:编辑 3：下载
function power(type){
    var buyStatus = $("#buyStatus").val();
    if (isLogin()){
       if (buyStatus != 0){
          return true;
       }else{ 
          prevBuy(type);
          return false;
       }  
    }else{
       return false;
    }
}
//加入购物车
function moveCart(e,type,No){		
    if (isLogin()){
	    var articleId = $("#articleId").val();
		var journalId = $("#journalId").val();
        var journalType = $("#journalType").val();
		var resoureType;
		var buyType = $("input[name='buy']:checked").val();
		if (type == 1){
		   resoureType = "journal";
		   articleId = 0;
		}else{
		   resoureType = $("#resoureType").val();
	       if (buyType == 2){
	       	   resoureType = "journal";
		       articleId = 0;
	       }
		}
		$.ajax({
			type : "post",
			url : "cart/add.do",
			async : false,
			data : {
				resoureType : resoureType,
				articleId : articleId,
				journalId : journalId
			},
			dataType : "json",
			success : function(data) {	
			  if (data.result == 0){
		  		  new altInfo({
					 text : data.msg
				  });		  
			  }else if (data.result == 1){
			      //加入购物车效果
			      if(No=='top'){
			    	  MoveBox(e,'1');
			      }else if(No=='mid'){
			    	  MoveBox(e,'2');
			      }
				 
			      
				  $("#cartCount").text("("+data.count+")");
				  //更改页面加入购物车状态
				  if (resoureType == "journal"){	
					  if (buyType == 2){
						  //页面中间的购物车状态
					      $("#cart1").css('display','none'); 
					      $("#cart2").css('display','none'); 
						  $("#cart3").css('display','none'); 
				          $("#cart4").css('display','block');  
					  }
					  //页面右上侧的购物车状态
					  $("#jourCartStatus").val(1);
				      $("#shopCart1").css('display','none'); 
				      $("#shopCart2").css('display','none'); 
			          $("#shopCart3").css('display','block'); 
				  	  if (journalType == 8){
						  //底部购物车状态
						  $("#botCart1").css('display','none'); 
						  $("#botCart2").css('display','block'); 
					  }
				  }else{
					  //页面中间的购物车状态
					  $("#artCartStatus").val(1);
				      $("#cart1").css('display','none'); 
				      $("#cart2").css('display','none'); 
					  $("#cart3").css('display','none'); 
					  $("#cart4").css('display','block');  
					  //底部购物车状态
					  $("#botCart1").css('display','none'); 
					  $("#botCart2").css('display','block'); 
				  }
			  }
			}
		});
    }
}
//购买前验证type 1:直接购买   2：点编辑  3：点下载
function prevBuy(type){
	
    if (isLogin()){
	    RechRechSucc();//关闭等待充值信息的窗口
		var articleId = $("#articleId").val();
		var journalId = $("#journalId").val();
		var resoureType = $("#resoureType").val();
	    var buyType = $("input[name='buy']:checked").val();
	    var price = $("#articlePrice").val();
        if (buyType == 2){
       	   resoureType = "journal";
	       articleId = 0;
	       price = $("#journalPrice").val();
        }

		$.ajax({
			type : "post",
			url : "buy/prevBuy.do",
			async : false,
			data : {
				resoureType : resoureType,
				articleId : articleId,
				journalId : journalId
			},
			dataType : "json",
			success : function(data) {	
			  if (data.result == -1){
		  		  new altInfo({
					  text : data.msg
			      });	
			  }else if (data.result == 0){
			      //余额不足，需要充值
				  $("#payMoney").html("所选商品应付："+price+"创享币。");
			      $("#rechargeMoney").val(data.needRecharge);
			      showBuyTipSucc();
			  }else{
			      //可以直接购买
				  if (type == 1){
					  $("#needMoney").html("所选商品应付："+price+"创享币。");
				  }else if (type == 2){
					  $("#needMoney").html("购买后才能编辑此文档。</br>所选商品应付："+price+"创享币。");
				  }else if (type == 3){
					  $("#needMoney").html("购买后才能下载此文档。</br>所选商品应付："+price+"创享币。");
				  }
			      showBuyTipFail();
			  }
			}
		});
	}
} 

//购买
function buy(type){
    if (isLogin()){
		var articleId = $("#articleId").val();
		var journalId = $("#journalId").val();
		var resoureType = $("#resoureType").val();
		var buyType = $("input[name='buy']:checked").val();
	    if (buyType == 2){
	       	resoureType = "journal";
		    articleId = 0;
	    }
		$.ajax({
			type : "post",
			url : "buy/directBuy.do",
			async : false,
			data : {
				resoureType : resoureType,
				articleId : articleId,
				journalId : journalId
			},
			dataType : "json",
			success : function(data) {	
			  if (data.result == 1){
			      hideBuyTipFail();  
			      $('.ui_back2,.ui_back,.box_content,.pay_tip_small').show();
			      var int = setInterval(function(){
			    	 $('.ui_back2,.ui_back,.box_content,.pay_tip_small').show();
			  		 clearInterval(int);
					 //刷新当前页面
					 location.reload();
			      }, 1000);
			  }else{
				  new altInfo({
					text : data.msg
				  });		      
			  }
			}
		});
	}
} 

//充值
function recharge(a){
    if (isLogin()){
        var rechargeMoney = $("#rechargeMoney").val();
		if(rechargeMoney != null && rechargeMoney > 0){
		    hideBuyTipSucc();//关闭余额不足提示窗口
		    showRechSucc();//打开等待充值信息的窗口
			$(a).attr("href","alipay/userorder.do?rechargecount="+$("#rechargeMoney").val());
		}else{
			new altInfo({
			   text : "充值数量不可为空！"
			});
		}
	}
}

//编辑
function saveMy(name,id,type){
	if(!power(2)){
		return false;
	}
	sava_to_my(name,id,type);
}

//最底端的购买（购买当前文档）
function buyArt(){
    if (isLogin()){
	    RechRechSucc();//关闭等待充值信息的窗口
		var articleId = $("#articleId").val();
		var journalId = $("#journalId").val();
		var resoureType = $("#resoureType").val();
        var journalType = $("#journalType").val();
		var price = $("#articlePrice").val();
		if (journalType == 8){
			resoureType = "journal";
			price = $("#journalPrice").val();
		}
		$.ajax({
			type : "post",
			url : "buy/prevBuy.do",
			async : false,
			data : {
				resoureType : resoureType,
				articleId : articleId,
				journalId : journalId
			},
			dataType : "json",
			success : function(data) {	
			  if (data.result == -1){
		  		  new altInfo({
					  text : data.msg
			      });	
			  }else if (data.result == 0){
			      //余额不足，需要充值
			      $("#rechargeMoney").val(data.needRecharge);
			      showBuyTipSucc();
			  }else{
			      //可以直接购买
				  $("#needMoney").html("所选商品应付："+price+"创享币。");
			      showBuyTipFail();
			  }
			}
		});
	}
} 

//最底端的加入购物车
function moveArtCart(e,type){		
    if (isLogin()){
	    var articleId = $("#articleId").val();
		var journalId = $("#journalId").val();
		var resoureType = $("#resoureType").val();
        var journalType = $("#journalType").val();
		if (journalType == 8){
			resoureType = "journal";
		}
		$.ajax({
			type : "post",
			url : "cart/add.do",
			async : false,
			data : {
				resoureType : resoureType,
				articleId : articleId,
				journalId : journalId
			},
			dataType : "json",
			success : function(data) {	
			  if (data.result == 0){
		  		  new altInfo({
					 text : data.msg
				  });		  
			  }else if (data.result == 1){
			      //加入购物车效果
				  MoveBox(e,'3');	  
				  $("#cartCount").text("("+data.count+")");
				  if (journalType == 8){
				      $("#cart1").css('display','none'); 
				      $("#cart2").css('display','none'); 
					  $("#cart3").css('display','none'); 
			          $("#cart4").css('display','block');  
					  $("#jourCartStatus").val(1); 
					  //页面右上侧的购物车状态
				      $("#shopCart1").css('display','none'); 
				      $("#shopCart2").css('display','none'); 
			          $("#shopCart3").css('display','block'); 					  
				  }else{
					  //更改页面中间购物车状态
					  var buyType = $("input[name='buy']:checked").val();
				      if (buyType == 1){
					      $("#cart1").css('display','none'); 
					      $("#cart2").css('display','none'); 
						  $("#cart3").css('display','none'); 
				          $("#cart4").css('display','block');  
						  $("#artCartStatus").val(1); 
				      }
				  }
				  //底部购物车状态
				  $("#botCart1").css('display','none'); 
				  $("#botCart2").css('display','block');   
			  }
			}
		});
    }
}
//选择买文档还是期刊
function changeType(type){
    var artCartStatus = $("#artCartStatus").val();
    var jourCartStatus = $("#jourCartStatus").val();
    $("#cart1").css('display','none'); 
    $("#cart2").css('display','none'); 
    if (type == 1){
        if (artCartStatus == 0){
           $("#cart3").css('display','block'); 
           $("#cart4").css('display','none'); 
        }else{
           $("#cart3").css('display','none'); 
           $("#cart4").css('display','block'); 
        }
    }else{
        if (jourCartStatus == 0){
           $("#cart3").css('display','block'); 
           $("#cart4").css('display','none'); 
        }else{
           $("#cart3").css('display','none'); 
           $("#cart4").css('display','block'); 
        }
    }
}

//动态加载文档内容type 1:跳页
function loadArticleContent(startPage,endPage,type){
    var articleId = $("#articleId").val();
	$.ajax({
		url:'view/getMoreArticleContents.do',
		data : {
			articleId : articleId,
			startPage: startPage,
			endPage:endPage
		},
		cache:false,
		dataType:'json',
		type:'POST',
		async: false,
		success: function(r){
			var contents = r.contents;
			var endPageNew = r.endPage;
			var pageNo = r.pageNo;
			$("#endPage").val(endPageNew);
			var ifameHeight = $("#content1").height();
		    var suffix = $("#articleFormat").val();

			if (endPageNew == pageCount){
				adHeight = -15;
			}else{
				adHeight = 32;
			}
		    if(suffix && (suffix=="ppt" || suffix=="pptx")){
		    	iframeHeight = 560.44* endPageNew + adHeight;
		    }else{
		    	iframeHeight = 1042.24* endPageNew + adHeight;
		    }
		    for (var i = pageNo - 1;i > 0;i --){
				$("#content1").contents().find("#more"+i).remove();
		    }
		    if (endPageNew == pageCount){
		    	$("#content1").contents().find("#more"+pageNo).remove();
		    }
		    setNewZoom(baseZoom);
		    if (screenFlag) {
		        var ratio = screenWidth / baseWidth * z;
		        screenFlag = true;
		        $("body").addClass('max-screen');        
		        $(".previewFooter a.reader-fullScreen").addClass('isFullScreen');
		        setNewZoom(ratio);
		    }

			$("#content1").contents().find("#page-container").append(contents);
		    $("#content1").contents().find("#preLoadImg").remove();
			//页面跳转
			if (type == 1){
				pageNumber(endPage);
			}
		}
  });
}

