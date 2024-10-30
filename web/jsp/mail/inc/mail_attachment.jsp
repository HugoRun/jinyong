<%@ page pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<c:choose>
<c:when test="${mailInfo.mailType==3}">
<anchor>领取
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mail.do?cmd=n5")%>">
<postfield name="mail_id" value="${ mailInfo.mailId}" />
</go>
</anchor><br/>
</c:when>
<c:when test="${mailInfo.mailType==4}">
您
<anchor>是
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do")%>">
<postfield name="cmd" value="inviteResult" />
<postfield name="fId" value="${mailInfo.sendPk }" />
<postfield name="mailId" value="${ mailInfo.mailId}" />
<postfield name="result" value="1" /></go></anchor>  
<anchor>否
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do")%>">
<postfield name="cmd" value="inviteResult" />
<postfield name="fId" value="${mailInfo.sendPk }" />
<postfield name="mailId" value="${ mailInfo.mailId}" />
<postfield name="result" value="0" /></go></anchor>同意加入?<br/>
</c:when>
<c:when test="${mailInfo.mailType==5}">
<anchor>接管氏族
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do")%>">
<postfield name="cmd" value="assume" />
<postfield name="mailId" value="${ mailInfo.mailId}"/>
</go><br/>
</anchor>
</c:when>
<c:when test="${mailInfo.mailType==10}">
<anchor>领取
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mail.do")%>">
<postfield name="cmd" value="receiveAttachment" />
<postfield name="mailId" value="${ mailInfo.mailId}"/>
</go><br/>
</anchor>
</c:when>
</c:choose>