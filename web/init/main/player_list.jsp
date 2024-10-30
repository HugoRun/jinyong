<%@page pageEncoding="UTF-8" isErrorPage="false"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<c:if test="${!empty player_list_str}">
玩家:<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pk.do?cmd=n1&amp;page_no=1")%>" method="get" ></go>${player_list_str }</anchor><br/>
</c:if>