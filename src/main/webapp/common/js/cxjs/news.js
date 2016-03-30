// JavaScript Document
$(function(){
	
	var menu_w=0;
	var menu_size=$(".menu .menu_bg li a").length;
	for(var i=0; i<menu_size;i++){
		menu_w+=$(".menu .menu_bg li a").eq(i).width()+50;
	}
	if(menu_w<1160){
		$(".menu .a9").removeClass("menu_unfold menu_fold");
		$(".menu .a9").addClass("menu_no");
		$(".menu_manage").remove();
	}
	
	/*banner展开收起*/
	$(".menu .a9").click(function(){
		if($(this).hasClass("menu_unfold")){
			$(this).removeClass("menu_unfold");
			$(this).addClass("menu_fold");
			$(".menu_bg").removeClass("vh");
			}else{
				$(this).addClass("menu_unfold");
				$(this).removeClass("menu_fold");
				$(".menu_bg").addClass("vh");
				}
		})
	$(".menu").click(function(event){
		event.stopPropagation();
	})
	$(document).click(function(){
		$(".menu .a9").addClass("menu_unfold");
		$(".menu .a9").removeClass("menu_fold");
		$(".menu_bg").addClass("vh");
	})
	/*订阅，取消订阅*/
	$(".sub").click(function(){
		if($(this).hasClass("sub_able")){
			$(this).removeClass("sub_able");
			$(this).addClass("sub_cal");
			$(this).html("<span>取消订阅 </span>");
		}else{
			$(this).addClass("sub_able");
			$(this).removeClass("sub_cal");
			$(this).html("<i></i><span>订阅</span>");
		}
	})

	/*回到顶部*/
	 $(window).scroll(function() {
        var scrollt = document.documentElement.scrollTop + document.body.scrollTop;
        if (scrollt > 200) {
            $("#toTop").fadeIn(400)
        } else {
            $("#toTop").stop().fadeOut(400)
        }
    });
    $("#toTop").click(function() {
        $("html,body").animate({
            scrollTop: "0px"
        },
        200)
    })
	
	


/*首页index  焦点/时间搜索*/
	$('.banner .ul .li3 span,.banner .ul .li3 em').click(function(event){		
		$('.banner .hideUl').show();		
		event.stopPropagation();
	});
	$('.banner .hideUl li').click(function(){
		var str=$(this).text();
		$('.banner .ul .li3 span').text(str);
		$(this).parent().hide();
	});
	$('.banner .hideUl').mouseleave(function(){
		$('.banner .hideUl').hide();
	});
	$(document).click(function(){
		$('.banner .hideUl').hide();
	}); 
	
	})
	
/*首页 top 个人中心和退出*/
function showHeadUl(){$('.topBar .bg .hideUl').show();}
function hideHeadUl(){$('.topBar .bg .hideUl').hide();}
	
	
	
	