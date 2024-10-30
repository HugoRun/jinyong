<%@page pageEncoding="UTF-8" isErrorPage="false"%>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<%@page import="com.ls.pub.config.GameConfig"%>
<c:if test="${!empty discount_commoditys}">
本期打折商品:<br/>
<c:forEach items="${discount_commoditys}" var="commodity">
[
<anchor>购
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/mall.do?cmd=n6")%>">
<postfield name="c_id" value="${commodity.id }"/>
</go>
</anchor>
]★${commodity.discountDisplay}折★
<anchor>${commodity.propName }
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/mall.do?cmd=n6")%>">
<postfield name="c_id" value="${commodity.id }"/>
</go>
</anchor>
<c:if test="${commodity.commodityTotal!=-1}">
(剩余${commodity.storeNum})
</c:if>
<br/>
</c:forEach>
--------------------<br/>
</c:if>