
<%@page import="com.ls.web.service.player.RoleService"%><%@include file="/init/templete/game_head.jsp" %>
<%@page  pageEncoding="UTF-8"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
<%@page import="com.ben.pk.active.PKVs"%>
<%@page import="com.ls.ben.vo.pkhite.PKHiteVO"%>
<%@page import="com.ls.ben.cache.dynamic.manual.user.RoleCache"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.pub.util.StringUtil"%>
<%@page import="com.ls.ben.vo.mounts.UserMountsVO"%>
<%@page import="com.ls.ben.vo.mounts.MountsVO"%>
	<%
		UserMountsVO umv = (UserMountsVO) request.getAttribute("mountsVO");
		MountsVO mv=umv.getMountInfo();
		request.setAttribute("mountsVO",mv);
		String mountState = (String) request.getAttribute("mountState");
		String useNum = (String) request.getAttribute("useNum");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String carryNum=roleInfo.isRedname()?mv.getCarryNum1()/2+"":mv.getCarryNum1()+"";
		String mountPicStr=mv.getPicStr(roleInfo);
	%>
【坐骑栏】<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/mounts.do")%>">
	<postfield name="cmd" value="n16" />
	</go>
	换乘
	</anchor>
	|
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/mounts.do")%>">
	<postfield name="cmd" value="n16" />
	<postfield name="args" value="up" />
	</go>
	升级
	</anchor>
	|
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/mounts.do")%>">
	<postfield name="cmd" value="n14" />
	</go>
	购买
	</anchor>
	<br />
	
<%=mountPicStr%>【${mountsVO.name }】<br/>
${mountsVO.display }<br />
	群族：<%
		if (mv.getType() == 1) {
	%>走兽<%
		}
		if (mv.getType() == 2) {
	%>飞禽<%
		}
		if (mv.getType() == 3) {
	%>鳞甲<%
		}
	%><br />
	等级：<%=mv.getLevel()%>级
	<br />
<c:if test="${!empty mountsVO.functionDisplay}">
祝福：${mountsVO.functionDisplay }<br />
</c:if>
	状态：<%
		if (mountState.equals("1")) {
	%>乘骑<%
		} else {
	%>未乘骑<%
		}
	%><br />

	1级技能:任务传送
	<%
		if(mv.getLevel()==1)
		{
			%>
			<%=useNum%>/<%=carryNum%>
			<%
		}
	 %>
	<br />
	<%
		if (mv.getLevel() >= 2) {
	%>
	2级技能:
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mounts.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="mountID" value="<%=umv.getId()%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	<postfield name="carryGrade" value="2" />
	</go>
	场景传送
	</anchor>|<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mounts.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="mountID" value="<%=umv.getId()%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	<postfield name="carryGrade" value="2" />
	</go>
	练级传送
	</anchor>
	<%
		if (mv.getLevel() == 2) {
	%>
	<%=useNum%>/<%=carryNum%>
	<%
		}
		} else {
	%>2级技能:场景传送，练级传送<%
		}
	%>
	<br />
	<%
		if (mv.getLevel() >= 3) {
	%>3级技能:
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mounts.do")%>">
	<postfield name="cmd" value="n6" />
	<postfield name="mountID" value="<%=umv.getId()%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	<postfield name="carryGrade" value="3" />
	</go>
	好友传送
	</anchor>
	|
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mounts.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="mountID" value="<%=umv.getId()%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	<postfield name="carryGrade" value="3" />
	</go>
	队友传送
	</anchor>
	|
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mounts.do")%>">
	<postfield name="cmd" value="n18" />
	<postfield name="mountID" value="<%=umv.getId()%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	<postfield name="carryGrade" value="3" />
	</go>
	氏族传送
	</anchor>
	<%
		if (mv.getLevel() == 3) {
	%>
	<%=useNum%>/<%=carryNum%>
	<%
		}
		} else {
	%>3级技能:好友传送，队友传送，氏族传送<%
		}
	%>
	<br />

	<%
		if (mv.getLevel() >= 4) {
	%>4级技能:
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mounts.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="mountID" value="<%=umv.getId()%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	<postfield name="carryGrade" value="4" />
	</go>
	BOSS传送
	</anchor>
	|
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mounts.do")%>">
	<postfield name="cmd" value="n4" />
	<postfield name="mountID" value="<%=umv.getId()%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	<postfield name="carryGrade" value="4" />
	</go>
	副本传送
	</anchor>
	|
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mounts.do")%>">
	<postfield name="cmd" value="n7" />
	<postfield name="mountID" value="<%=umv.getId()%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	<postfield name="carryGrade" value="4" />
	</go>
	仇敌追杀
	</anchor>
	<%
		if (mv.getLevel() == 4) {
	%>
	<%=useNum%>/<%=carryNum%>
	<%
		}
		} else {
	%>4级技能:BOSS传送，副本传送，仇敌传送<%
		}
	%>
	<br />
	5级技能:悬赏追杀
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/mounts.do")%>">
	<postfield name="cmd" value="n16" />
	</go>
	换乘
	</anchor>
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/mounts.do")%>">
	<postfield name="cmd" value="n16" />
	</go>
	返回
	</anchor>
	<br />
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
