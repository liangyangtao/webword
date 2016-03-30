<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${(not empty page.list or page.count>0) and page.pageCount>1}">
 <div class="pagination notb">
 	<p>
     	<c:if test="${page.pageNo<=1}">
     		<a>首页</a>
     		<a>&lt;上一页</a>
     	</c:if>
     	<c:if test="${page.pageNo>1}">
     		<a href="javascript:goPage(1);">首页</a>
     		<a href="javascript:goPage(${page.pageNo-1},${page.startPage-1});">&lt;上一页</a>
     	</c:if>
     	<c:forEach var="c" begin="${page.startPage}" end="${page.endPage}">
     		<a href="javascript:goPage(${c},${page.startPage});"<c:if test="${c==page.pageNo}">class="onPage cur"</c:if>>${c}</a>
     	</c:forEach>
     	<c:if test="${page.pageNo<page.pageCount}">
     		<a  href="javascript:goPage(${page.pageNo+1}<c:if test="${page.pageNo==page.endPage}">,${page.startPage+1}</c:if>);">下一页&gt;</a>
     		<a href="javascript:goPage(${page.pageCount},${page.pageCount-page.movepage});">尾页</a>
     	</c:if>
     	<c:if test="${page.pageNo>=page.pageCount}">
     		<a >下一页&gt;</a>
     		<a>尾页</a>
     	</c:if>
     </p>
</div>
 </c:if>