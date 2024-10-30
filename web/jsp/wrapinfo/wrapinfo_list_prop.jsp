<%@page pageEncoding="UTF-8" isErrorPage="false"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<c:forEach items="${item_page.result}" var="item">
<c:if test="${item.propNum>0}">

<anchor>${item.propName}x${item.propNum}
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="pg_pk" value="${item.pgPk}" />
<postfield name="goods_id" value="${item.propId}" />
<postfield name="goods_type" value="${item.propType}" />
<postfield name="isReconfirm" value="${item.propIsReconfirm}" />
</go>
</anchor>
<c:if test="${w_type!=5}">
<c:choose>

<c:when test="${item.propType==41}">
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
<postfield name="cmd" value="n22" />
<postfield name="pg_pk" value="${item.pgPk}" />
<postfield name="wupinlan" value="0" />
</go>
使用
</anchor>
</c:when>

<c:when test="${item.propType==67}">
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
<postfield name="cmd" value="n18" />
<postfield name="pg_pk" value="${item.pgPk}" />
<postfield name="wupinlan" value="0" />
</go>
使用
</anchor>
</c:when>

<c:otherwise>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
<postfield name="cmd" value="n3" />
<postfield name="pg_pk" value="${item.pgPk}" />
<postfield name="goods_id" value="${item.propId}" />
<postfield name="goods_type" value="${item.propType}" />
</go>
使用
</anchor>
</c:otherwise>

</c:choose>
|<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
<postfield name="cmd" value="n7" />
<postfield name="pg_pk" value="${item.pgPk}" />
<postfield name="goods_id" value="${item.propId}" />
<postfield name="goods_type" value="${item.propType}" />
<postfield name="isReconfirm" value="${item.propIsReconfirm}" />
</go>
丢弃
</anchor>
</c:if>
<br/>
</c:if>
</c:forEach>
${item_page.pageFoot }
