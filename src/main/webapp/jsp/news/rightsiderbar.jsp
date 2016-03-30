<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String basePath = request.getContextPath() + "/";
%>
<div name="侧边栏" class="main_sidebar">
	<div class="main_sidebox">
		<div class="main_sideboxtt">
			<h3>今日热搜</h3>
		</div>
		<div class="main_sideboxcont">
			<div class="tags">
				<c:forEach items="${newsWords}" var="hotword" varStatus="status">
					<script type="text/javascript">
						var keword = '${hotword}';
						var ecKeyword = encodeURI(keword);
						var tags_c = Math.round(Math.random() * 5) +1;
						var result = '<a class="tags_c'
								+ tags_c
								+ '" target="_blank" href="news/view/search.do?keyword='
								+ ecKeyword + '" title="' + keword + '">'
								+ keword + '</a>';
						document.write(result);
					</script>
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="main_sidebox">
		<div class="main_sideboxtt">
			<h3>热门栏目</h3>
			<a href="javascript:void(0);" id="showPlateEdit">全部</a>
		</div>
		<div class="main_sideboxcont">
			<div class="cols">
				<ul class="cols_list">
					<!-- newsColumns -->
					<c:forEach items="${newsColumns}" var="newsColumn"
						varStatus="status">

						<li class="cols_item clearfix"><a
							href="news/view/plate.do?columnId=${newsColumn.data.id}"><img
								src="upload/${newsColumn.data.picPath}" class="cols_icon" /> </a>
							<h4>
								<a href="news/view/plate.do?columnId=${newsColumn.data.id}">${newsColumn.data.columnName}</a>
							</h4> <%--  <span>${newsColumn.data.count}万人订阅</span> --%> <script
								type="text/javascript">
								var plateId = "${newsColumn.data.id}";
								if ("${newsColumn.isAdd}" == "Y") {
									document
											.write('<a class="sub sub_cal"  myvalue="'+plateId+'" myvalue2="N"><span>取消订阅 </span></a>');
								} else {
									document
											.write('<a class="sub sub_able"  myvalue="'+plateId+'" myvalue2="N"><i></i><span>订阅</span></a>');
								}
							</script></li>

					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>
