/*搜索页面js*/
$(function() {

	if (searchType == "TITLE") {
		$("input[name='searchType'][value='TITLE']").attr("checked", true);
	} else {
		$("input[name='searchType'][value='ALL']").attr("checked", true);
	}
	if (articleFormat == "SCORE") {
		$("#articleFormat").text("按焦点排序");
	} else {
		$("#articleFormat").text("按时间排序");
	}
	// 搜索输入框
	$(".banner .search_input").keydown(function(e) {
		if (e.keyCode == 13) {
			seachNews();
		}
	});
	// 搜索按钮
	$(".banner .search").click(function() {
		seachNews();
	});
	$(".search_menu").click(function() {
		$("#searchType").show();
	});

	$("#radioAll").click(function() {
		seachNews();
	});

	$("#radioTitle").click(function() {
		seachNews();
	});

	$("#title").click(function() {
		$("#articleFormat").text($("#title").text());
		/* $("#searchType").hide(); */
		seachNews();
	});
	$("#content").click(function() {
		$("#articleFormat").text($("#content").text());
		/* $("#searchType").hide(); */
		seachNews();
	});
	/*
	 * $(".banner .li2 .search").click(function (){ seachNews(); });
	 */

});
function showHeadUl() {
	$('.topBar .bg .hideUl').show();
}
function hideHeadUl() {
	$('.topBar .bg .hideUl').hide();
}
function seachNews() {
	var keyword = encodeURI($.trim($(".search_input").val()));
	var el = document.getElementsByClassName('search_input')[0];
	var placeholder = encodeURI((el.getAttribute("placeholder")));
	var articleFormatText = $("#articleFormat").text();
	var articleFormat = "TIME";
	if (articleFormatText == "按焦点排序") {
		articleFormat = "SCORE";
	}
	if (keyword && keyword != placeholder) {
		var chageHref = "news/view/search.do?keyword=" + keyword
				+ "&searchType=" + $("input[name='searchType']:checked").val()
				+ "&articleFormat=" + articleFormat;
		window.location = path + chageHref;

	} else {
		window.location = path + "news/view/index.do";
	}
}