<%@ page pageEncoding="UTF-8"%>
<%@ include file="../inc/faction_head.jsp"%>
${faction.display }
<anchor>成员列表<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=memList")%>" method="get" /></anchor><br/>
敌对氏族<br/>
<anchor>其他氏族信息
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=otherFList&amp;page_no=1")%>" method="get"/></anchor><br/>
<%@ include file="../inc/return_faction.jsp"%>