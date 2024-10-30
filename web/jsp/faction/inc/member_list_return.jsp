<%@ page pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<%@page import="com.ls.pub.config.GameConfig"%>
<c:if test="${pre!=null }">
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd="+request.getAttribute("pre"))%>" method="get" /></anchor>
</c:if>