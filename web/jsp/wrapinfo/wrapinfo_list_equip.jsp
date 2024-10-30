<%@page pageEncoding="UTF-8" isErrorPage="false"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<c:forEach items="${item_page.result}" var="item">
<anchor>${item.fullName }
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>"> 
<postfield name="cmd" value="n4" />
<postfield name="pwPk" value="${item.pwPk }" />
</go>
</anchor> 
<anchor>使用
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>"> 
<postfield name="cmd" value="n5" />
<postfield name="pwPk" value="${item.pwPk }" />
</go>
</anchor>|
<anchor>丢弃
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>"> 
<postfield name="cmd" value="n6" />
<postfield name="pwPk" value="${item.pwPk }" />
</go>
</anchor>
<br/>
</c:forEach>
${item_page.pageFoot }