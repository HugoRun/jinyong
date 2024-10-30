<%@ include file="/init/templete/game_head_with_hint.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/intimatehint.jsp"%><br/>
<c:if test="${!empty notifylist_gengxin}">
<c:forEach items="${notifylist_gengxin}" var="item">
<anchor>${item.notifytitle }
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sysnotify.do") %>">
<postfield name="cmd" value="n1" />
<postfield name="id" value="${item.id }" />
</go>
</anchor><br/>
</c:forEach>
</c:if>

<c:choose>

<c:when test="${!empty role_list}">
选择角色:<br/>
<c:forEach items="${role_list}" var="item">
<anchor>${item.PName }进入游戏
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do") %>">
<postfield name="cmd" value="n4" />
<postfield name="pPk" value="${item.PPk }"/>
</go>
</anchor>
(${item.PGrade }级  ${item.sexName } )
<anchor>删除
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/role.do") %>">
<postfield name="cmd" value="n6" />
<postfield name="pPk" value="${item.PPk }" />
<postfield name="pName" value="${item.PName }" />  
<postfield name="pGrade" value="${item.PGrade }" />
</go>
</anchor>
<anchor>新手引导登陆
<go method="poset" href="<%=response.encodeURL(GameConfig.getContextPath()+"/guide.do") %>">
<postfield name="pPk" value="${item.PPk }" />
</go>
</anchor>
<br/>
</c:forEach>
</c:when>

<c:otherwise>
<anchor>创建角色<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/role.do?cmd=n1") %>" method="get"/></anchor><br/>
</c:otherwise>

</c:choose>

<c:if test="${!empty notifylist_huodong}">
-----------<br/>
<c:forEach items="${notifylist_gengxin}" var="item">
<anchor>${item.notifytitle }
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sysnotify.do") %>">
<postfield name="cmd" value="n1" />
<postfield name="id" value="${item.id }" />
</go>
</anchor><br/>
</c:forEach>
</c:if>
<%
	if(GameConfig.getChannelId()==Channel.TELE)
	{
		%>
		<%@ include file="/cooperate/tele/index/mainpage.jsp"%>
		<%
	}
 %>
-----------<br/>
<%@ include file="/init/return_url/return_zhuanqu.jsp"%>
</p>
</card>
</wml>
