<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
<c:forEach items="${item_page.result}" var="item">
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/prop.do")%>"> 
<postfield name="cmd" value="des" />
<postfield name="propId" value="${item.propId }" />
<postfield name="pre" value="wMList" />
</go>
${item.propName }x${item.propNum }
</anchor>
<anchor>贡献
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fStorage.do")%>">
<postfield name="cmd" value="inputMNum" />
<postfield name="pg_pk" value="${item.pgPk }" />
</go>
</anchor>
<anchor>全部贡献
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/fStorage.do")%>">
<postfield name="cmd" value="contributeAll" />
<postfield name="pg_pk" value="${item.pgPk }" />
</go>
</anchor>
<br/>
</c:forEach>
${item_page.pageFoot }
<anchor>氏族财富
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/fStorage.do?cmd=fMList")%>" method="post">
<postfield name="page_no" value="1"/>
</go>
</anchor><br/>
<%@ include file="../../inc/return_build_manage.jsp"%>
