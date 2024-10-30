<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head_with_hint.jsp"%>

${other.des}

<c:if test="${pa!=null}">
${pa}<br/>
</c:if>
	<anchor>密语
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">
    <postfield name="cmd" value="n7" />
	<postfield name="pByPk" value="${other.PPk }" /> 
	<postfield name="pByName" value="${other.name }" /> 
	</go>
	</anchor><br/>
<c:if test="${me.isRookie==false&&other.isRookie==false}">
	<anchor>交易
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>"> 
	<postfield name="cmd" value="n1" />
	<postfield name="pByPk" value="${other.PPk }" /> 
	</go>
	</anchor><br/>
</c:if>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n16" />
	<postfield name="other_p_pk" value="${other.PPk }" />
	</go>
	查看装备
	</anchor><br/>
	<!-- <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n23" />
	<postfield name="pPks" value="${other.PPk }" />
	</go>
	查看宠物信息
	</anchor> <br/>-->
	<c:if test="${me.isRookie==false&&other.isRookie==false}">
	<anchor>申请组队
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="b_ppk" value="${other.PPk }" />
	</go>
	</anchor><br/>
	</c:if>
	<c:if test="${me.isRookie==false&&other.isRookie==false}">
	<anchor>加为好友
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do")%>">
	<postfield name="pByPk" value="${other.PPk }" />
	<postfield name="pByName" value="${other.name }" />
	<postfield name="cmd" value="n1" />
	</go>
	</anchor><br/>
	</c:if>
	<c:if test="${me.isRookie==false&&other.isRookie==false}">
	<anchor>加入黑名单
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/blacklistaction.do")%>">
	<postfield name="pByPk" value="${other.PPk }" />
	<postfield name="pByName" value="${other.name }" />
	<postfield name="cmd" value="n2" />
	</go>
	</anchor><br/>
	</c:if>

<c:choose>
<c:when test="${empty backtype || backtype=='1'}">
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pk.do?cmd=n1")%>" method="get"></go></anchor>
</c:when>
<c:when test="${backtype=='3'}">
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/communioninfoaction.do?cmd=n1")%>" method="get"></go></anchor>
</c:when>
<c:when test="${backtype=='applyList' || backtype=='memList' || backtype=='titleMList'|| backtype=='jobMList' || backtype=='zlList'}">
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd="+session.getAttribute("backtype"))%>" method="get"></go></anchor>
</c:when>
<c:when test="${backtype=='hitelist'}">
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/hite.do?cmd=n1")%>" method="get"></go></anchor>
</c:when>
<c:when test="${backtype=='mainmap'}">
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubaction.do")%>" method="get"></go></anchor>
</c:when>
</c:choose>
<%@ include file="/init/templete/game_foot.jsp"%>
