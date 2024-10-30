<%@ page pageEncoding="UTF-8" isELIgnored ="false"%>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@page import="com.ls.pub.config.GameConfig"%>
<c:if test="${fRecruit.isRecruit==true}">
${fRecruit.content }<br/>
<c:if test="${fRecruit.FId!=roleInfo.basicInfo.faction.id }">
<anchor>点击申请加入
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do")%>"> 
<postfield name="cmd" value="apply" />
<postfield name="fId" value="${fRecruit.FId }" />
</go>
</anchor><br/>
</c:if>
</c:if>