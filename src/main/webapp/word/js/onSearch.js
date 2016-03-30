//input 绑定inputid,suggestWrap append到谁, cLeft left值 ,cTop top值, closeB点击关闭的div ,pageSize每页记录 ,callback选择后回调
function oSearchSuggest(input, suggestWrap, cLeft, cTop, closeB, pageSize,callback) {
	var lock = false;
    var init = function() {
        input.bind('click',
        function(event) {
        	lock = false;
            sendKeyWordToBack();
            event.stopPropagation();
        });
        input.bind('keyup', function(){
        	lock = false;
        	sendKeyWordToBack();
            event.stopPropagation();
        });
        input.bind('blur',
        function() {
        	!lock && input.val('');
        });
        closeB.bind('click',
        function() {
        	!lock && $('#form_cities').remove();
        	!lock && input.val('');
        });
    };
    var sendKeyWordToBack = function(pageId) {
        dataDisplay(pageId || 1);
    };
    var dataDisplay = function(pageId) {
    	if(!pageId) pageId=1;
    	var valText = $.trim(input.val());
    	var data ;
        $.ajax({
            type: "POST",
            url: "global/searchJournal.do",
            async: false,
            data: {
                name: valText,
                pageId: pageId,
                pageSize: pageSize
            },
            dataType: "json",
            success: function(d) {
                if (d && d.status == "success") {
                	data = d;
                }
            }
        });
        $('#form_cities').remove();
        if (! (data && data.count > 0 && data.list && data.list.length)) {
            return;
        }
        var newDiv = "<div id='form_cities'><ul id='panel_cities'>";
        for (var i = 0; i < data.list.length; i++) {
            newDiv += "<li journalid=" + data.list[i].id + " typeid="+data.list[i].typeId+" price="+data.list[i].price+">" + data.list[i].name + "</li>";
        }
        var pageCount = data.count % pageSize == 0 ? data.count / pageSize: Math.ceil(data.count / pageSize) ;
        var pageId = data.pageId;
        if (data && data.count > 0 && data.list && data.list.length && pageCount>1) {
        	newDiv += "</ul><div id='flip_cities'><a href='javascript:void(0);'>«&nbsp;向前</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);'>向后&nbsp;»</a></div></div>";
        }
        suggestWrap.append(newDiv);
        $('#form_cities').show().css({
            'left': cLeft + 'px',
            'top': cTop + 'px'
        });
        $('#form_cities').find('li').mouseover(function() {
            $(this).addClass('hover').siblings().removeClass('hover');
        });
        $('#form_cities').find('li').bind('click',
        function() {
        	lock = true;
            input.val($(this).html());
            callback(this);
            $('#form_cities').remove();
            event.stopPropagation();
        });
        $('#flip_cities').find('a').hover(function(){
        	lock = true;
        },function(){
        	lock = false;
        });
        $('#flip_cities').find('a').eq(0).bind('click',function(event) {
        	if(pageId>1){
        		sendKeyWordToBack(pageId-1);
        	}
        });
        $('#flip_cities').find('a').eq(1).bind('click',function(event) {
        	if(pageId<pageCount){
        		sendKeyWordToBack(pageId+1);
        	}
        });
    };
    init();
};

/*
1.日刊 day 日刊：2015-08-16
2 周刊 week 周刊：2015-08-1周
3.半月刊 halfMonths 半月刊：2015-08上
4.月刊 months 月刊：2015-08
5.双月刊 months 双月刊：2015-08
6 季刊 season 季刊：2015-1季度
7 半年刊  haifYear 半年刊：2015上半年
8 年刊 year 年刊：2015年
dateStr=2015-08-16,type=1 
返回 "日刊：2015-08-16"
*/
function getJournalStr(dateStr,type){
	if(dateStr=="") return "";
	var strList = dateStr.split("-");
	var newDate = new Date(strList[0],strList[1]-1,strList[2]);
	var year = newDate.getFullYear();//年
	var month = newDate.getMonth()+1;//月
	var date = newDate.getDate();
	var weekDay = newDate.getDay();//0-6 0:周日
	var str="";
	if(type==1){//日刊
		str="日刊:"+dateStr;
	}else if(type==2){//周刊
		
//		var flag=Math.ceil((date + 6 - weekDay) / 7);
		var flag = weekofmonth(year,month,date);
		var today = new Date();
		today.setFullYear(year,month-1,1);
		if(today.getDay() != 0){//1号是否周日（每周的第一天），是周一时周数不变，否则周数减一
			flag -= 1;
		}
		if(flag == 0){
			flag +=1;
		}
		str="周刊:"+year+"-"+month+"-"+flag+"周";
	}else if(type==3){//半月刊
		if(date>15){
			str="半月刊:"+year+"-"+month+"下";
		}else{
			str="半月刊:"+year+"-"+month+"上";;
		}
	}else if(type==4){//月刊
		str="月刊:"+year+"-"+month+"";
	}else if(type==5){
		str="双月刊:"+year+"-"+month+"";;
	}else if(type==6){//季刊
		if((1<=month)&&(month<=3)){
			str="季刊:"+year+"-1季度";
		}else if((4<=month)&&(month<=6)){
			str="季刊:"+year+"-2季度";
		}else if((7<=month)&&(month<=9)){
			str="季刊:"+year+"-3季度";
		}else if((10<=month)&&(month<=12)){
			str="季刊:"+year+"-4季度";
		}
	}else if(type==7){//半年刊：2015上半年
		if(month>6){
			str="半年刊:"+year+"下半年";
		}else{
			str="半年刊:"+year+"上半年";
		}
	}else if(type==8){
		str="年刊:"+year+"年";
	}
	return str;
}
/*
 * 根据期刊类型,算出每个期刊下文档的价格
 * price 期刊的价格
 * type 1:日刊，2
 * 
 */
function getJournalPrice(price,type){
	var typeArray=[365,52,24,12,6,4,2,1];
	var articelPrice=0;
	if(type>0){//期刊的类型要对
		try{
			articelPrice= Math.floor(price/typeArray[(type-1)]);
		}
		catch(e){
			
		}
	}
	return articelPrice;
}
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

/**
 * 获取当前日期是本月第几周
 * @param a 年
 * @param b 月
 * @param c 日
 * @returns
 */
function weekofmonth(a,b,c){
	/* 
	a = d = 当前日期 
	b = 6 - w = 当前周的还有几天过完（不算今天） 
	a + b 的和在除以7 就是当天是当前月份的第几周 
	*/ 
	var date = new Date(a, parseInt(b) - 1, c), w = date.getDay(), d = date.getDate(); 
	return Math.ceil((d + 6 - w) / 7); 

}