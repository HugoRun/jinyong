<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<c:if test="${!empty prop_wml}">
${prop_wml}
<anchor>购买
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n6")%>">
<postfield name="c_id" value="${c_id }"/>
<postfield name="page_no" value="${page_no }"/>
<postfield name="type" value="${type }"/>
</go>
</anchor><br/>
</c:if>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>