// JavaScript Document
var altInfo = function(para_option){
	var option = $.extend({		
		title : "提示!"
		,text : ""           
		}, para_option || {});	
	var maskBg = $('<div></div>');
	var altDlg = $('<div></div>');
	
	//遮罩层设置
	var optionMaskBg = function(){
		maskBg.css({
			"backgroundColor" : "#000"
			,"width" : "100%"
			,"height" : "100%"
			,"opacity" : "0.2"
			,"filter" : "alpha(opacity=50)"
			,"z-index" : "20150720"
			,"position": "fixed"
			,"left":0
			,"top":0
		});
	};
	//提示框设置
	var optionAltdlg = function(){
		altDlg.css({
			"backgroundColor" :"#fff"
			,"border":"1px solid #19a97b"	
			,"z-index" : "20150721"
			,"width" : "280px"
			,"height" : "140px"
			,"position":"fixed"
			,"top":"50%"
			,"left":"50%"
			,"margin-left" : "-140px"
			,"margin-top" : "-70px"	
			,"font-family":"'Microsoft YaHei'"			
		});
		//设置标题
		var tt = $("<h3></h3>").css({
			"backgroundColor" :"#f6f6f6"
			,"color" : "#646464"		
			,"height" : "36px"
			,"line-height" : "36px"
			,"padding-left" : "20px"	
			,"font-size":"14px"	
			,"font-weight":"bold"
			,"position":"relative"
		});
		var spanI=$("<em></em>").css({
			"position":"absolute" 
			,"left":"0px" 
			,"top":"11px"
			,"display":"block"
			,"width":"10px" 
			,"height":"15px"
			,"background":"#3eb18c"
		});			
		tt.text(option.title);	
		tt.prepend(spanI);
		altDlg.append(tt);

		var content = $("<div></div>").css({
			"color":"#666"
			,"font-size" : "12px"
			,"margin-top":"20px"
			,"line-height" : "20px"	
			,"padding-left":"20px"
		});
		content.text(option.text);	altDlg.append(content);	
		var closeBt = $("<span class='closeBt'></span>").css({			
			"position" : "absolute"
			,"right" : "16px"
			,"top" : "15px"
			,"cursor":"pointer"	
			,"background": "url('../../img/cximg/close1.png')"
			,"background-position":"0px 0px"	
	        ,"height": "12px"
	        ,"margin":"0"
	        ,"overflow": "hidden"	   
	        ,"text-indent": "-9999em"
	        ,"width": "12px"
	        ,"z-index":"1"
		});	
		closeBt.hover(				
				function(){	$(this).css({'background-position':'0px -18px'})},
				function(){	$(this).css({'background-position':'0px 0px'})}
		);
		altDlg.append(closeBt);	closeBt.click(function(){ close();});   
		var sureBt = $("<a class='sure'>确定</a>").css({			
			"position" : "absolute"
			,"right" : "104px"
			,"bottom" : "15px"
			,"cursor":"pointer"	
			,"background": "#fff"
		    ,"border": "1px solid #d2d2d2"
		    ,"color": "#646464"	    
		    ,"display": "inline-block"
		    ,"font-size": "12px"
		    ,"height": "26px"
		    ,"line-height": "26px"
		    ,"margin":" 0 7px"
		    ,"padding":" 0 26px"
		    ,"text-align": "center" 			
		});	
		sureBt.hover(				
				function(){	$(this).css({'background':'#f6f3f3'	,"border": "1px solid #19a97b"})},
				function(){	$(this).css({'background':'#fff'	,"border": "1px solid #d2d2d2"})}
		);
		altDlg.append(sureBt);	sureBt.click(function(){ close();});
	}
	var show = function(){		
		optionMaskBg();
		optionAltdlg();
		$("body").append(maskBg);
		$("body").append(altDlg);
	};show();
	var close = function(){		
		maskBg.remove();
		altDlg.remove();
	};

}
