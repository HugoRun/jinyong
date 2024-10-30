<%@ include file="inc/pk_head.jsp" %>
<%@page pageEncoding="UTF-8"%>
PK胜利:<br/>
您杀死了${playerB.PName }!<br/>
${playerA.killDisplay }

您体力${playerA.PHp }<br/>
您内力${playerA.PMp }<c:if test="${playerA.expendMP>0}">（-${playerA.expendMP}）</c:if><br/>
<c:if test="${!empty list}">
掉落的物品有:<br/>
<c:forEach items="${list}" var="dropgoods" varStatus="status">
<anchor>${ dropgoods.goodsName}x${ dropgoods.dropNum}
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pk.do")%>">
<postfield name="cmd" value="n8" />
<postfield name="dp_pk" value="${ dropgoods.dpPk}" />
<postfield name="p_pk" value="${ dropgoods.APPk}" />
<postfield name="goods_id" value="${ dropgoods.goodsId}" />
<postfield name="goods_type" value="${ dropgoods.goodsType}" />
<postfield name="goods_num" value="${ dropgoods.dropNum}" />
<postfield name="goods_name" value="${ dropgoods.goodsName}" />
<postfield name="a_p_pk" value="${playerA.PPk }" />
<postfield name="b_p_pk" value="${playerB.PPk }" />
</go>
</anchor>
<c:if test="${status.last==false}">,</c:if>
</c:forEach>
</c:if>

<anchor>继续游戏
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/pk.do")%>">
<postfield name="cmd" value="n9" />
<postfield name="type" value="2" />
<postfield name="a_p_pk" value="${playerA.PPk }" />
<postfield name="b_p_pk" value="${playerB.PPk }" />
</go>
</anchor>
<br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
