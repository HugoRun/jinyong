<%@ page pageEncoding="UTF-8" isELIgnored ="false"%>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<%@page import="com.ls.pub.config.GameConfig"%>
<c:if test="${pre!=null}">
<c:choose>
<c:when test="${pre=='f_prestige'||pre=='f_zhanli'||pre=='f_rich'}">
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4&amp;field="+request.getAttribute("pre"))%>" method="get" /></anchor>
</c:when>
<c:otherwise>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd="+request.getAttribute("pre"))%>" method="get" /></anchor>
</c:otherwise>
</c:choose>
</c:if>