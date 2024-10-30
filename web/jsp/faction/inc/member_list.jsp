<%@ page pageEncoding="UTF-8" isELIgnored ="false"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
成员列表:<br/>
<c:forEach items="${item_page.result}" var="item">
<anchor>${ item.PName}
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>"> 
<postfield name="cmd" value="n13" />
<postfield name="pPks" value="${ item.PPk}" />
<postfield name="backtype" value="${backtype}" />
</go>
</anchor>
${ item.FDes}
<%@ include file="member_list_action.jsp"%>
<br/>
</c:forEach>
${item_page.pageFoot }