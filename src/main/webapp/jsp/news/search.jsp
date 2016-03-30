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
<title>知识创享网-新闻搜索</title>

<link href="<%=basePath%>word/css/news.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>word/css/index.css?v=<%=System.currentTimeMillis()%>" />
<style type="text/css">
em {
	color: red;
}
</style>

<script type="text/javascript">
var searchType = "${searchType}";
var articleFormat = "${articleFormat}";
var changeOldPicUrl ="${oldPicUrl}";
var changePicUrl ="${picUrl}";
var conLength =0;
function goPage(pageNo, startPage) {
		var url = "<%=basePath%>news/view/search.do?keyword=${keyword}";
		if (pageNo > 1) {
			url += (url.indexOf("?") > 0 ? "&" : "?") + "pageNo=" + pageNo;
		}
		if (startPage > 1) {
			url += (url.indexOf("?") > 0 ? "&" : "?") + "startPage="
					+ startPage;
		}
		url += (url.indexOf("?") > 0 ? "&" : "?") + "searchType=${searchType}&articleFormat=${articleFormat}";
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

String.prototype.replaceAll = function (reallyDo, replaceWith, ignoreCase) {
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);
    } else {
        return this.replace(reallyDo, replaceWith);
    }
}
</script>
</head>
<body>
	<!-- header -->
	<jsp:include page="searchheader.jsp"></jsp:include>
	<!-- header_end -->
	<!--页面主体：begin-->
	<div name="页面主体" class="main  clearfix">
		<div name="列表" class="main_list main_br">
			<p class="nums">找到相关新闻约${newsList.countResult}篇</p>

			<ul name="搜索结果列表" class="news_list search_list">
				<c:forEach items="${newsList.data}" var="news">
					<script type="text/javascript">
						var liDiv="";
						if (searchType=="ALL") {
							var picUrls = '${news.picUrl}';
							if (picUrls.length > 0) {
							conLength = 130;
								liDiv = '<li class="news_item have_img">';
							}else{
							conLength =180;
							    liDiv = '<li class="news_item">';
							}
						} else {
							liDiv = '<li class="news_item">';
						}
						document.write(liDiv);
					</script>
					<h3>
						<script type="text/javascript">
					 var title1='${news.title}';
					 var title = title1.split('<em>').join('');
					 title = title.split('</em>').join('');
					 var crawl_id ='${news.crawl_id}';
                     document.write("<a target=\"_blank\" title=\""+title+"\" href=\"news/view/showNewsDetails.do?newsId="+crawl_id+"\">"+title1+"</a>");
					 
					 </script>

					</h3>
					<script type="text/javascript">
				            if(searchType=="ALL"){
				            var picDiv="";
							var picUrls = '${news.picUrl}';
							var picurl="";
							if (picUrls.length > 0) {
								picurl = picUrls.split(",")[0];
				 			    picurl=picurl.replace(changeOldPicUrl,changePicUrl);
								picDiv = '<a href="news/view/showNewsDetails.do?newsId=${news.crawl_id}"><img class="news_img" src="'+picurl+'" /></a>';
							}
						    document.write(picDiv);
				            
								var summaryDiv ='${news.content}';
												var analyList = ${analyList};
        						
								var length = fucCheckLength(summaryDiv);
        						if (length > conLength) {
            							summaryDiv =subCharStr(summaryDiv, conLength);
        						}
        						
//         						alert(summaryDiv);
        						
        						for ( var i = 0; i < analyList.length; i++) {
            						var str = analyList[i];
            						var tempStr = "<em>" + str + "</em>";
            						summaryDiv = summaryDiv.replaceAll(str, tempStr);
           						}
								
// 								alert(summaryDiv);
        						
                                summaryDiv='<p class=\"summary\">'+summaryDiv+'......</p>';
								document.write(summaryDiv);
				         }
				        </script>
					<p class="feed_time">
						<span>${news.webName}</span> <span> <script
								type="text/javascript">
var long = parseInt("${news.newsDate}");
var date = new Date(long);
var today = new Date();
var sjc = (today.getTime() - date.getTime()) / 1000;
var re = "";
if (sjc < 0) {
	re = "当前";
} else {
	if (sjc >= 60 * 60 * 24) {
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
		re = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":"
				+ seconds;
	} else {
		if (sjc >= 60 * 60) {
			re = parseInt(sjc / (60 * 60)) + "小时";
		} else {
			if (sjc >= 60) {
				re = parseInt(sjc / 60) + "分钟";
			} else {
				re = sjc + "秒";
			}
		}
		re += "前";
	}
}
document.write(re);
						</script> </span>
					</p>
					<script type="text/javascript">
						document.write('</li>');
					</script>
				</c:forEach>
			</ul>

		</div>
		<div name="侧边栏" class="search_sidebar">
			<div class="search_sidebox">
				<div class="search_sideboxtt">
					<h3>大家都在搜</h3>
				</div>
				<div class="search_sideboxcont">
					<div class="hot">
						<ul class="hot_list">
							<c:forEach items="${newsWords}" var="hotword" varStatus="status">
								<Li class="hot_item"><span>${status.index+1}</span> <%-- <a
									href="<%=basePath%>news/view/search.do?keyword=${hotword}">${hotword}</a> --%>
									<script type="text/javascript">
						var keword = '${hotword}';
						var ecKeyword = encodeURI(keword);
						var result = '<a target="_blank" href="news/view/search.do?keyword='
								+ ecKeyword + '" title="' + keword + '">'
								+ keword + '</a>';
						document.write(result);
					</script>
								</Li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="search_sidebox">
				<script type="text/javascript">
				 if('${fn:length(likeDocuments)}'==0){
				 }else{
				   document.write('<div class="search_sideboxtt"><h3>相关研究</h3></div>');
				 }
				</script>

				<div class="search_sideboxcont">
					<div class="hot">
						<ul class="hot_list">
							<c:forEach items="${likeDocuments}" var="likeDocument"
								varStatus="status">
								<li class="hot_item"><i><img
										src="word/img/doc_03.jpg" /> </i><a target="_blank"  title="${likeDocument.articleName}"
									href="<%=basePath%>view/preview.do?articleId=${likeDocument.articleId}">${likeDocument.articleName}</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="pager.jsp"></jsp:include>
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
	<script type="text/javascript" src="<%=basePath%>word/js/newssh.js?v=<%=System.currentTimeMillis()%>"></script>
</body>

</html>
