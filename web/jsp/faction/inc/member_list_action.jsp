<%@ page pageEncoding="UTF-8" isELIgnored ="false" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<c:if test="${roleEntity.PPk!=item.PPk && roleEntity.basicInfo.FJob>item.FJob}">
<c:choose>
<c:when test="${del!=null }">
<anchor>逐出氏族
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do")%>"> 
<postfield name="cmd" value="delHint" />
<postfield name="mId" value="${ item.PPk}" />
</go>
</anchor>
</c:when>
<c:when test="${job!=null }">
<anchor>更改职位
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do")%>"> 
<postfield name="cmd" value="selectJobHint" />
<postfield name="mId" value="${ item.PPk}" />
</go>
</anchor>
</c:when>
<c:when test="${title!=null }">
<anchor>更改称号
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do")%>"> 
<postfield name="cmd" value="inputTitle" />
<postfield name="mId" value="${ item.PPk}" />
</go>
</anchor>
</c:when>
<c:when test="${changeZZH!=null }">
<anchor>转让
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do")%>"> 
<postfield name="cmd" value="changeZZHHint" />
<postfield name="mId" value="${ item.PPk}" />
</go>
</anchor>
</c:when>
</c:choose>
</c:if>
