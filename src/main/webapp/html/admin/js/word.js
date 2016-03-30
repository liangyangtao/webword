$(document).ready(function(){
	/* 所有弹出框关闭按钮 */
	$(".sbox_ h3 img,.sb_button .a2").click(function(){		
		var a=0;
		$(this).parents(".sbox_").fadeOut(100);
		$(".box_content,.ui_back,.ui_back2").fadeOut(100);
	});
	
	$(".nav .p1").click(function(){
		$(".submit_audit,.box_content,.ui_back,.ui_back2").fadeIn(100);
	});
	/* 审核未通过 */
	$(".nav .p2").click(function(){
		$(".fail_audit,.box_content,.ui_back,.ui_back2").fadeIn(100);
	});

});
/* 审核成功 */
function succ_audit(){
	$(".submit_audit,.box_content,.ui_back,.ui_back2").fadeOut(100);
	$(".succ_audit,.box_content,.ui_back,.ui_back2").fadeIn(100);}
function close_audit(){$(".sbox_,.box_content,.ui_back,.ui_back2").fadeOut(100);}
/* 给所有弹出框定义样式 */
function set_sbox(c){	
	var he=-$(c)[0].offsetHeight/2;
	$(c).css({"top":"50%","margin-top":he});
}

function tree1click(i){
	$(".tree1"+i).find("i").toggleClass("i_down").parents("ul").find(".tree2").slideToggle(300);
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
					"margin-top" : 0-Number($(this).height()),
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
