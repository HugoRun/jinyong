<%@ page pageEncoding="UTF-8"%>
<%@ include file="../inc/faction_head.jsp"%>
创建氏族条件<br/>
1.等级达到30级<br/>
2.灵石×1000<br/>
3.氏族令×1<br/>
<anchor>我要创建氏族<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=inputFName")%>" method="get"/></anchor><br/>
<%@ include file="../inc/return_faction.jsp"%>