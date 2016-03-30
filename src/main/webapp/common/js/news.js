/** 新闻首页js */
$(function() {
	$(".news_more")
			.click(
					function() {
						var count = $("#count").val();
						var pageIndex = $("#pageIndex").val();
						var searchWords = $("#searchWords").val();
						var crawlIds = $("#crawlIds").val();
						var newsMaxSize = $("#newsMaxSize").val();
						if (newsMaxSize > count) {
							$
									.ajax({
										type : "post",
										url : "news/view/searchMore.do",
										data : "count=" + count + "&pageIndex="
												+ pageIndex + "&searchWords="
												+ searchWords + "&crawlIds="
												+ crawlIds,
										ifModified : true,
										dataType : "json",
										cache : false,
										error : function(source) {
										},
										success : function(source) {
											$("#crawlIds").val(
													crawlIds + "_"
															+ source.crawlIds);
											$("#pageIndex").val(
													source.pageIndex);
											var sum = Number(count)
													+ Number(source.count);
											$("#count").val(sum);
											// 判断是否隐藏more按钮
											if (source.hideFlag) {
												$(".news_more").hide();
											}
											// if(sum == newsMaxSize){
											// $(".news_more").hide();
											// }
											var newsList = source.newsList;
											// alert(newsList.data.length);
											for ( var i = 0; i < newsList.data.length; i++) {
												var result;
												var newsInfo = newsList.data[i];
												// 图片
												var picUrl = newsInfo.picUrl;
												// alert(picUrl);
												if (picUrl) {
													if (picUrl.length > 0) {
														picurl = picUrl
																.split(",")[0];
														picurl = picurl
																.replace(
																		changeOldPicUrl,
																		changePicUrl);
														result = '<li class="news_item have_img"><img class="news_img" src="'
																+ picurl
																+ '" />';
													}
												} else {
													result = '<li class="news_item ">';
												}

												result += "<h3><a target='_blank' title='"
														+ newsInfo.title
														+ "' href='news/view/showNewsDetails.do?newsId="
														+ newsInfo.crawl_id
														+ "'>"
														+ newsInfo.title
														+ "</a></h3>";

												var content = newsInfo.content;
												var length = fucCheckLength(content);
												if (length > 180) {
													content = subCharStr(
															content, 180);
												}
												content += "......";

												result += "<p class='summary'>"
														+ content + "</p>";

												var tempTime;
												if (newsInfo.newsDate) {
													var long = parseInt(newsInfo.newsDate);
													var date = new Date(long);
													var year = date
															.getFullYear();
													var month = date
															.getUTCMonth() + 1;
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
													var minutes = date
															.getMinutes();
													if (minutes < 10) {
														minutes = "0" + minutes;
													}
													var seconds = date
															.getSeconds();
													if (seconds < 10) {
														seconds = "0" + seconds;
													}
													tempTime = year + "-"
															+ month + "-" + day
															+ " " + hours + ":"
															+ minutes + ":"
															+ seconds;
												}

												result += "<p class='feed_time'><span>"
														+ newsInfo.webName
														+ "</span><span> "
														+ tempTime
														+ "</span></p>";

												result += "</li>";
												$(".news_list").append(result);
											}
										}
									});
						}
					});
	var menu_w = 0;
	var menu_size = $(".menu .menu_bg li a").length;
	for ( var i = 0; i < menu_size; i++) {
		menu_w += $(".menu .menu_bg li a").eq(i).width() + 50;
	}
	if (menu_w < 1160) {
		$(".menu .a9").removeClass("menu_unfold menu_fold");
		$(".menu .a9").addClass("menu_no");
		$(".menu_manage").remove();
	}
	/* banner展开收起 菜单栏 */
	$(".menu .a9").click(function() {
		if ($(this).hasClass("menu_unfold")) {
			$(this).removeClass("menu_unfold");
			$(this).addClass("menu_fold");
			$(".menu_bg").removeClass("vh");
		} else {
			$(this).addClass("menu_unfold");
			$(this).removeClass("menu_fold");
			$(".menu_bg").addClass("vh");
		}
	});
	$(".menu").click(function() {
		event.stopPropagation();
	});
	$(document).click(function() {
		$(".menu .a9").addClass("menu_unfold");
		$(".menu .a9").removeClass("menu_fold");
	});
	$("#showPlateEdit").click(function() {
		var f = isLogin();
		if (f) {
			window.location = path + "news/column/myPlateEdit.do";
		} else {
			show_login();
		}

	});
	$(".menu_no").click(function() {
		var f = isLogin();
		if (f) {
			window.location = path + "news/column/myPlateEdit.do";
		} else {
			show_login();
		}

	});
	$(".menu_manage a").click(function() {
		var f = isLogin();
		if (f) {
			window.location = path + "news/column/myPlateEdit.do";
		} else {
			show_login();
		}
	});

	/* 订阅，取消订阅 */
	$(".sub").click(function() {

		var f = isLogin();
		if (f) {
			var columnId = $(this).attr("myvalue");
			if ($(this).hasClass("sub_able")) {
				$(this).removeClass("sub_able");
				$(this).addClass("sub_cal");
				$(this).html("<span>取消订阅 </span>");

				$.ajax({
					type : "POST",
					url : "news/column/plateAddHot.do",
					data : "pid=0&columnId=" + columnId,
					success : function(msg) {
						/*
						 * window.location = path +
						 * "news/view/plate.do?plateId=" + columnId;
						 */
						window.location.reload();
					}
				});

			} else {
				$(this).addClass("sub_able");
				$(this).removeClass("sub_cal");
				$(this).html("<i></i><span>订阅</span>");

				$.ajax({
					type : "POST",
					url : "news/column/plateDelHot.do",
					data : "columnId=" + columnId,
					success : function(msg) {
						window.location.reload();
					}
				});

			}
		} else {
			show_login();
		}

	});
	/* 菜单栏结束 */
	/* 回到顶部 */
	$(window).scroll(
			function() {
				var scrollt = document.documentElement.scrollTop
						+ document.body.scrollTop;
				if (scrollt > 200) {
					$("#toTop").fadeIn(400);
				} else {
					$("#toTop").stop().fadeOut(400);
				}
			});
	$("#toTop").click(function() {
		$("html,body").animate({
			scrollTop : "0px"
		}, 200);
	});

	/* 回到顶部结束 */

	/* 搜索开始 */
	var searchType = "${searchType}";

	if (searchType == "TITLE") {
		$("input[name='searchType'][value='TITLE']").attr("checked", true);
	} else {
		$("input[name='searchType'][value='ALL']").attr("checked", true);
	}
	// 搜索输入框
	$(".banner .search_input")
			.keydown(
					function(e) {
						if (e.keyCode == 13) {
							var keyword = encodeURI($.trim($(".search_input")
									.val()));
							var el = document
									.getElementsByClassName('search_input')[0];
							var placeholder = encodeURI((el
									.getAttribute("placeholder")));
							if (keyword && keyword != placeholder) {
								window.location = path
										+ "news/view/search.do?keyword="
										+ keyword
										+ "&searchType="
										+ $("input[name='searchType']:checked")
												.val();
							} else {
								window.location = path + "news/view/index.do";
							}
						}
					});
	// 搜索按钮
	$(".banner .search").click(
			function() {
				var keyword = encodeURI($.trim($(".search_input").val()));
				var el = document.getElementsByClassName('search_input')[0];
				var placeholder = encodeURI((el.getAttribute("placeholder")));
				if (keyword && keyword != placeholder) {
					// searchType为全文或标题检索。articleFormat是文档类型
					$(this).attr(
							"href",
							"news/view/search.do?keyword="
									+ keyword
									+ "&searchType="
									+ $("input[name='searchType']:checked")
											.val());
				} else {
					window.location = path + "news/view/index.do";
				}
			});
	/* 搜索结束 */
});
function showHeadUl() {
	$('.topBar .bg .hideUl').show();
}
function hideHeadUl() {
	$('.topBar .bg .hideUl').hide();
}