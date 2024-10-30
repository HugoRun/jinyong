<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
<c:if test="${empty item_list}">
暂无氏族财富<br/>
</c:if>
<c:forEach items="${item_list}" var="item">
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/prop.do")%>"> 
<postfield name="cmd" value="des" />
<postfield name="propId" value="${item.id }" />
<postfield name="pre" value="fMList" />
</go>
${item.des }
</anchor><br/>
</c:forEach>
<br/>
<anchor>贡献资源
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/fStorage.do?cmd=wMList")%>" method="post">
<postfield name="page_no" value="1"/>
</go>
</anchor><br/>
<%@ include file="../../inc/return_build_manage.jsp"%>
