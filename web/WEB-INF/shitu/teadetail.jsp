<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.shitu.model.ShituConstant"%>
<%@page import="com.ben.vo.info.partinfo.PartInfoVO"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ben.vo.honour.RoleHonourVO"%>
<%@page import="com.ben.shitu.model.Shitu"%>
<%@page import="com.ben.dao.tong.TongMemberDAO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
    <%
		RoleEntity roleInfo = (RoleEntity)request.getAttribute("roleInfo");
		int wType = 1;
		String playerPic = (String)request.getAttribute("playerPic");
		String backtype = (String)request.getSession().getAttribute("backtype");
		if (backtype == null || backtype.equals("") || backtype.equals("null"))  {
			backtype = "1";
		}
		RoleHonourVO roleHonourVO = (RoleHonourVO)request.getAttribute("roleHonourVO");
		PartInfoVO vo = (PartInfoVO) request.getAttribute("vo");
		Object id = request.getAttribute("id");
		Object te_id = request.getAttribute("te_id");
	%>

	<%
		String apply_hint = (String) request.getAttribute("apply_hint");
		if (apply_hint != null) {
	%>
	<%=apply_hint%><br/>
	<%
		}
	%> 
	<%=vo.getPName() %> <% String aa = ShituConstant.getTeLevel(vo.getTe_level()); %>
	<%=aa!=null&&!"".equals(aa.trim())?"("+aa.trim()+")":"" %>
	<%
		if (vo.getPPkValue() > 10 && vo.getPPkValue() < 200) {
	%>(黄)<%
		} else if (vo.getPPkValue() > 200) {
	%>(红)<%
		}
	%>
	<br/>
	称号:<%if(roleHonourVO == null){%>无<%}else{%><%=roleHonourVO.getRoleHonourName() %>(<%=roleHonourVO.getRoleHonourTypeName() %>)<%} %>
	<br/>
	<%=playerPic%>
	性别:<%if (vo.getPSex() == 1) {%>男<%} else if (vo.getPSex() == 2) {%>女<%} else if (vo.getPSex() == 3) {%>人妖<%}%>
	<br/>
	等级:<%=vo.getPGrade()%>级
	<br/>
	师门:<%if (vo.getPSchool().equals("shaolin")) {%>少林<%} else if (vo.getPSchool().equals("gaibang")) {%>丐帮<%} else if (vo.getPSchool().equals("mingjiao")) {%>明教<%} else {%>无<%}%> 
	<br/>
	帮派:<%if(vo.getPTongName() != null && !vo.getPTongName().equals("")){%><%=vo.getPTongName() %><%}else{ %>无<%} %>
	<br/>
	阵营:<%if(vo.getPCamp() != 0){%><%if(vo.getPCamp()==1){ %>正<%}else{%>邪<%}}else{ %>无<%} %>
	<br/>
	是否可PK:<%if (vo.getPPks() == 1) {%>不可PK<%} else if (vo.getPPks() == 2) {%>可PK<%}%>
	<br/>
	PK点数:<%=vo.getPPkValue()%>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/baishi.do")%>">
    <postfield name="cmd" value="n4" />
	<postfield name="id" value="<%=id%>" />
	<postfield name="te_id" value="<%=te_id%>" /> 
	</go>
	拜师
	</anchor>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/baishi.do") %>"> 
	<postfield name="cmd" value="n2" />
	</go>
	返回
	</anchor> <br/>
	<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
