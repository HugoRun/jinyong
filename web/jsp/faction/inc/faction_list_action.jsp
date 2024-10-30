<%@ page pageEncoding="UTF-8" isELIgnored ="false"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<c:choose>
<c:when test="${pre=='applyFList'}">
<anchor>申请加入
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do")%>"> 
<postfield name="cmd" value="apply" />
<postfield name="fId" value="${ item.id}" />
</go>
</anchor>
</c:when>
</c:choose>