<%@ page pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@page import="com.ls.pub.config.GameConfig"%>
<c:if test="${pre!=null }">
<c:choose>
<c:when test="${pre=='ttIndex' }">
<anchor>领取祝福
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/fBuild.do")%>" method="post">
<postfield name="cmd" value="useHint" />
<postfield name="bId" value="${bId }" />
</go>
</anchor><br/>
</c:when>

<c:when test="${pre=='createIndex' }">
<anchor>建设
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/fBuild.do")%>" method="post">
<postfield name="cmd" value="create" />
<postfield name="bId" value="${bId }" />
</go>
</anchor><br/>
</c:when>

<c:when test="${pre=='upgradeIndex' }">
<c:if test="${fBuild.isUpgraded==true }">
<anchor>升级
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/fBuild.do")%>" method="post">
<postfield name="cmd" value="upgrade" />
<postfield name="bId" value="${bId }" />
</go>
</anchor><br/>
</c:if>
</c:when>
</c:choose>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/fBuild.do")%>" method="post" >
<postfield name="cmd" value="${pre }" />
</go>
</anchor>
</c:if>