<%@ include file="inc/pk_head.jsp" %>
<%@page pageEncoding="UTF-8"%>
${playerA.killDisplay }
<c:if test="${!empty over_display}">
${over_display }<br/>
</c:if>
<c:forEach items="${drop_list}" var="dropgoods" varStatus="status">
<anchor>${ dropgoods.goodsName}x${ dropgoods.dropNum}
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/pk.do")%>">
<postfield name="cmd" value="n8" />
<postfield name="dp_pk" value="${ dropgoods.dpPk}" />
<postfield name="p_pk" value="${ dropgoods.APPk}" />
<postfield name="goods_id" value="${ dropgoods.goodsId}" />
<postfield name="goods_type" value="${ dropgoods.goodsType}" />
<postfield name="goods_num" value="${ dropgoods.dropNum}" />
<postfield name="goods_name" value="${ dropgoods.goodsName}" />
<postfield name="a_p_pk" value="${a_p_pk }" />
<postfield name="b_p_pk" value="${b_p_pk }" />
</go>
</anchor>
<c:if test="${status.last==false}">,</c:if>
</c:forEach>

<anchor>继续
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/pk.do")%>">
<postfield name="cmd" value="n9" />
<postfield name="type" value="${type }" />
<postfield name="a_p_pk" value="${a_p_pk }" />
<postfield name="b_p_pk" value="${b_p_pk }" />
</go>
</anchor>
<br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
