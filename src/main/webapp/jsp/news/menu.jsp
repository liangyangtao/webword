<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String basePath = request.getContextPath() + "/";
%>
<!--导航：begin-->
<div name="菜单" class="menu">
	<div class="menu_center">
		<div class="menu_bg vh">
			<ul>
				<script type="text/javascript">
					if ("${nowPlate.plateName}" == '') {
						if ("${showR}" == "N") {
							document
									.write('<li class="menu_item"><a class="a1" href="news/view/index.do">推荐 </a></li>');
						} else {
							document
									.write('<li class="menu_item"><a class="a1 cur_menu" href="news/view/index.do">推荐<i></i> </a></li>');
						}

					} else {
						document
								.write('<li class="menu_item"><a class="a1"  href="news/view/index.do">推荐 </a></li>');
					}
				</script>


				<c:forEach items="${newsMyPlates}" var="newsMyPlate"
					varStatus="status">
					<script type="text/javascript">
						if ("${nowPlate.plateName}" == "${newsMyPlate.plateName}") {
							document
									.write('<li class="menu_item"><a class="cur_menu" href="news/view/plate.do?plateId=${newsMyPlate.plateId}&columnId=${newsMyPlate.columnId}">${newsMyPlate.plateName}</a> </li>');
						} else {
							document
									.write('<li class="menu_item"><a  href="news/view/plate.do?plateId=${newsMyPlate.plateId}&columnId=${newsMyPlate.columnId}">${newsMyPlate.plateName}</a> </li>');
						}
					</script>

				</c:forEach>
			</ul>
			<p class="menu_manage clearfix">
				<a href="javascript:void(0)"><i></i>管理栏目</a>
			</p>
		</div>
		<a class="a9 menu_unfold"><i></i> </a>
	</div>
</div>
<!--导航:end-->
