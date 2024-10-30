<%@page pageEncoding="UTF-8" isErrorPage="false"%>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<%@page import="com.ls.pub.config.GameConfig"%>
<c:if test="${!empty hotsell_commoditys}">
本周热销商品:<br/>
<c:forEach items="${hotsell_commoditys}" var="commodity">
[
<anchor>购
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/mall.do?cmd=n6")%>">
<postfield name="c_id" value="${commodity.id }"/>
</go>
</anchor>
]
<anchor>${commodity.propName}
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/mall.do?cmd=n6")%>">
<postfield name="c_id" value="${commodity.id }"/>
</go>
</anchor>
(已卖${commodity.sellNum}份)
<br/>
</c:forEach>
--------------------<br/>
</c:if>