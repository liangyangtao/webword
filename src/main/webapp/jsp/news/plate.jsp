<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>知识创享网-新闻首页</title>
<link href="<%=basePath%>word/css/news.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet" />



<script type="text/javascript">
var isIndex =0;
var nowPlateId='${nowPlate.plateId}';
var nowColumnId ='${nowPlate.columnId}';
function goPage(pageNo, startPage) {
		var url = "<%=basePath%>news/view/plate.do?plateId="+nowPlateId;
		if (pageNo > 1) {
			url += (url.indexOf("?") > 0 ? "&" : "?") + "pageNo=" + pageNo;
		}
		if (startPage > 1) {
			url += (url.indexOf("?") > 0 ? "&" : "?") + "startPage="
					+ startPage;
		}
		url += (url.indexOf("?") > 0 ? "&" : "?") + "columnId="+nowColumnId; 
		window.location = url;
}
function subCharStr(str, n) {
    var _len = fucCheckLength(str, n);
    if (_len > n) {
        var _newLen = Math.floor(n / 2);
        var _strLen = str.length;
        var _newStr = "";
        for (var i = _newLen; i <= _strLen; i++) {
            var tmpStr = str.substr(0, i);
            if (fucCheckLength(tmpStr) > n) {
                return _newStr;
                break;
            } else {
                _newStr = tmpStr;
            }
        }
    } else {
        return str;
    }
}

function fucCheckLength(strTemp) {
    var i, sum;
    sum = 0;
    for (i = 0; i < strTemp.length; i++) {
        if ((strTemp.charCodeAt(i) >= 0) && (strTemp.charCodeAt(i) <= 255)) {
            sum = sum + 1;
        } else {
            sum = sum + 2;
        }
    }
    return sum;
}
	function del() {
		$.ajax({
			type : "POST",
			url : "news/column/plateDel.do",
			data : "plateId=" + nowPlateId,
			success : function(msg) {
				window.location = path + "news/view/index.do";
			}
		});

	}
</script>
</head>

<body>
	<!-- header -->
	<jsp:include page="header.jsp"></jsp:include>
	<!-- header_end -->
	<!-- menu  -->
	<jsp:include page="menu.jsp"></jsp:include>
	<!-- menu end -->
	<!--页面主体：begin-->
	<div name="页面主体" class="main  clearfix">
		<div name="列表" class="main_list">
			<div class="news_listtt clearfix">
				<h3>${nowPlate.plateName}</h3>
				<script type="text/javascript">
				var user ='${user}';
				if(nowPlateId==0 && user !=''){
				   if("${isOrder}"=="Y"){
				     document.write('<a class="sub sub_cal"  myvalue="'+nowPlateId+'" myvalue2="Y"><span>取消订阅 </span></a>');
				   }else{
				    document.write('<a class="sub sub_able"  myvalue="'+nowPlateId+'" myvalue2="Y"><i></i><span>订阅</span></a>');
				   }
				}else{
				if("${isOrder}"=="N"){
				 document.write('<a class="sub sub_able" myvalue="'+nowPlateId+'" myvalue2="Y"><i></i><span>订阅</span></a>');
				}else{
				 document.write('<a class="sub sub_cal"  myvalue="'+nowPlateId+'" myvalue2="Y"><span>取消订阅 </span></a>');
				}}
				</script>


			</div>
			<ul name="新闻列表" class="news_list">
				<script type="text/javascript">
				 if('${fn:length(newsList.data)}'==0){
				   document.write('<p style="height: 370px;" class="noRecord">暂无相关搜索结果，请重新设置栏目标签、关键词。</p>');
				 }
				</script>

				<c:forEach items="${newsList.data}" var="news">
					<script type="text/javascript">
						var result;
						if ("${news.picUrl}") {
							var picUrls = '${news.picUrl}';
							var picurl;
							if (picUrls.length > 0) {
								picurl = picUrls.split(",")[0];
				 			    picurl=picurl.replace("${oldPicUrl}","${picUrl}");
								result = '<li class="news_item have_img"><a target="_blank" href="news/view/showNewsDetails.do?newsId=${news.crawl_id}&sectionName=${nowPlate.plateName}&plateId=${nowPlate.plateId}&columnId=${nowPlate.columnId}"><img class="news_img" src="'+picurl+'" /></a>';
							}
						} else {
							result = '<li class="news_item ">';
						}
						document.write(result);
					</script>
					<h3>
						<a target="_blank" title="${news.title}"
							href="<%=basePath%>news/view/showNewsDetails.do?newsId=${news.crawl_id}&sectionName=${nowPlate.plateName}&plateId=${nowPlate.plateId}&columnId=${nowPlate.columnId}">${news.title}</a>
					</h3>
					<p class="summary">
						<script type="text/javascript">
							    var	result =  '${news.content}';
								var length = fucCheckLength(result);
        						if (length > 180) {
            						result =subCharStr(result, 180);
        						}
                                result+="......";
								document.write(result);
						</script>

					</p>
					<p class="feed_time">
						<span>${news.webName}</span><span> <script
								type="text/javascript">
								var long = parseInt("${news.newsDate}");
								var date = new Date(long);
								var year = date.getFullYear();
								var month = date.getUTCMonth() + 1;
								if (month < 10) {
									month = "0" + month;
								}
								var day = date.getDate();
								if (day < 10) {
									day = "0" + day;
								}
								var hours = date.getHours();
								if (hours < 10) {
									hours = "0" + hours;
								}
								var minutes = date.getMinutes();
								if (minutes < 10) {
									minutes = "0" + minutes;
								}
								var seconds = date.getSeconds();
								if (seconds < 10) {
									seconds = "0" + seconds;
								}
								var result = year + "-" + month + "-" + day
										+ " " + hours + ":" + minutes + ":"
										+ seconds;
								document.write(result);
						</script> </span>
					</p>
					<script type="text/javascript">
						document.write("</li>");
					</script>
				</c:forEach>
			</ul>
			<jsp:include page="pager.jsp"></jsp:include>
		</div>
		<jsp:include page="rightsiderbar.jsp"></jsp:include>
	</div>
	<!--页面主体：end-->
	<!--footer:begin-->
	<jsp:include page="footer.jsp"></jsp:include>
	<!--footer:end-->
	<script src="<%=basePath%>word/js/jquery-1.8.3.min.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript"
		src="<%=basePath%>word/js/jquery.easing.1.3.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="<%=basePath%>word/js/login.js?v=<%=System.currentTimeMillis()%>"></script>

	<script type="text/javascript"
		src="<%=basePath%>word/js/jquery.SuperSlide.2.1.1.js?v=<%=System.currentTimeMillis()%>"></script>
	<script type="text/javascript" src="<%=basePath%>word/js/news.js?v=<%=System.currentTimeMillis()%>"></script>

</body>

</html>
