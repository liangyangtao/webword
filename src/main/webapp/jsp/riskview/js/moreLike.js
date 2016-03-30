
var getMoreLikeRisks=function(riskId,categoryId){
	
	var aj = $.ajax( {    
	    url:'risk/view/getMoreLikeRisks.do',// 跳转到 action    
	    data:{    
	    	riskId : riskId,
	    	categoryId:categoryId
	    },    
	    type:'post',    
	    cache:false,
	    dataType:'json',    
	    success:function(data) {    
	    	var htmlStr="";
	    	//<li class="t"><i>1</i><a  target="_blank" href="news_content.html" title="[安徽]前10个月安徽省民营工工工">[安徽]前10个月安徽省民营工工工</a></li>
	    	var riskList=data.riskList;
	    	
	    	for(var j=1;j<riskList.length;j++){
	    		var _categoryId=riskList[j].categoryId.split(" ")[0];
	    		
	    		if(j<=3){
	    			htmlStr=htmlStr+"<li class=\"t\"><i>"+j+"</i><a  target=\"_blank\" " +
    				"href=\"risk/view/getNewsById.do?esId="+riskList[j].esId+"&riskId="+riskList[j].crawl_id+"&categoryId="+_categoryId+"\" title=\""+riskList[j].title+"\">"+riskList[j].title+"</a></li>";
	    		}else{
	    			htmlStr=htmlStr+"<li ><i>"+j+"</i><a  target=\"_blank\" " +
    				"href=\"risk/view/getNewsById.do?esId="+riskList[j].esId+"&riskId="+riskList[j].crawl_id+"&categoryId="+_categoryId+"\" title=\""+riskList[j].title+"\">"+riskList[j].title+"</a></li>";
	    		}
	    	}
	    	$("ul.list").html(htmlStr);
	     },
	     error : function() {    
	          // view("异常！");    
	          //alert("异常！");
	     }
	});  
};
