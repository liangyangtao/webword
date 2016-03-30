<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<div class="userLeft">
    <h2><a href="javascript:void(0);">我的文库</a><em class="icon2"></em></h2>
    <ul style="display: block">
        <li <c:if test="${nav=='journal'}">class="current"</c:if>><a href="user/journal/index.do">我的期刊<span>(${map.journalCount})</span></a></li>
        <li <c:if test="${nav=='article'}">class="current"</c:if>><a href="user/article.do">我的文档<span>(${map.articleCount})</span></a></li>
        <li <c:if test="${nav=='template'}">class="current"</c:if>><a href="user/template.do">我的模板<span>(${map.templateCount})</span></a></li>
        <li <c:if test="${nav=='content'}">class="current"</c:if>><a href="user/content.do">我的新闻<span>(${map.contentCount})</span></a></li>
        <li <c:if test="${nav=='plugin'}">class="current"</c:if>><a href="user/plugin.do">我的插件<span>(${map.pluginCount})</span></a></li>
        <li <c:if test="${nav=='recommend'}">class="current"</c:if>><a href="user/recommend.do">推荐模板<span>(${map.recommendCount})</span></a></li>
        <li <c:if test="${nav=='upload'}">class="current"</c:if>><a href="user/upload.do">我的上传<span>(${map.uploadCount})</span></a></li>
        <li <c:if test="${nav=='resource'}">class="current"</c:if>><a href="user/resource.do">我的资源库<span>(${map.resourceCount})</span></a></li>
        <li <c:if test="${nav=='myfavorite'}">class="current"</c:if>><a href="user/myfavorite.do">我的收藏<span>(${map.myfavoritecount})</span></a></li>
    </ul>
     <h2><a href="javascript:void(0);">我的账户</a><em class="icon2"></em></h2>
     <ul style="display: block">
        <li <c:if test="${nav=='recharge'}">class="current"</c:if>><a href="user/recharge.do">充值<span></span></a></li>
        <li <c:if test="${nav=='purchase'}">class="current"</c:if>><a href="user/purchase.do">购买记录<span></span></a></li>
        <li <c:if test="${nav=='capital'}">class="current"</c:if>><a href="user/capital.do">资金明细<span></span></a></li>
        <li <c:if test="${nav=='edit'}">class="current"</c:if>><a href="user/edit.do">编辑资料</a></li>
        <li <c:if test="${nav=='pass'}">class="current"</c:if>><a href="user/pass.do">修改密码</a></li>
    </ul>
</div>