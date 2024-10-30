<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
退出氏族,您的氏族荣誉将归0,您的氏族祝福也将被清空,您确定要退出氏族吗?<br/>
<anchor>确定<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=exit")%>" method="get"/></anchor><br/>
<%@ include file="../../inc/return_faction.jsp"%>