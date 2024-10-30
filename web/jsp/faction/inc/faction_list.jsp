<%@ page pageEncoding="UTF-8" isELIgnored ="false"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
帮会列表:<br/>
<c:forEach items="${item_page.result}" var="item">
<anchor>${ item.name}
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do")%>"> 
<postfield name="cmd" value="des" />
<postfield name="fId" value="${ item.id}" />
<postfield name="pre" value="${pre }" />
</go>
</anchor>
(${item.grade }级氏族) 成员:${item.memberNum }
<%@ include file="faction_list_action.jsp"%>
<br/>
</c:forEach>
${item_page.pageFoot }