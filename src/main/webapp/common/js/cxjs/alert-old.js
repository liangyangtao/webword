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
			"backgroundColor" :"#ddede1"
			,"border":"1px solid #249b72"				
			,"border-radius" : "6px"
			,"z-index" : "20150721"
			,"width" : "280px"
			,"height" : "140px"
			,"position":"fixed"
			,"top":"50%"
			,"left":"50%"
			,"margin-left" : "-140px"
			,"margin-top" : "-70px"	
			,"font-family":"'宋体'"			
		});
		//设置标题
		var tt = $("<h3></h3>").css({			
			"color" : "#249b72"			
			,"font-weight":"bold"	
			,"height" : "30px"
			,"line-height" : "30px"
			,"padding" : "0px 20px"	
			,"font-size":"12px"					
		});
		tt.text(option.title);	altDlg.append(tt);

		var content = $("<div></div>").css({
			"color":"#666"
			,"font-size" : "12px"
			,"margin-top":"20px"
			,"line-height" : "20px"
			,"height" : "20px"
			,"text-align":"center"			
		});
		content.text(option.text);	altDlg.append(content);	
		var closeBt = $("<img src='word/img/alt-close.png'/>").css({			
			"position" : "absolute"
			,"right" : "20px"
			,"top" : "15px"
			,"cursor":"pointer"			
		});		
		altDlg.append(closeBt);	closeBt.click(function(){ close();});   
		var sureBt = $("<img src='word/img/s_yes.png'/>").css({			
			"position" : "absolute"
			,"right" : "104px"
			,"bottom" : "15px"
			,"cursor":"pointer"			
		});		
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
