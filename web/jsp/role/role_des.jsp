<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<%@page import="com.pm.service.pic.PicService"%>
<%@page import="com.ben.dao.info.partinfo.*"%>
<%@page import="com.ben.vo.info.partinfo.PartInfoVO"%>
<%@page import="com.ls.model.user.*"%> 
<%@page import="com.ls.web.service.player.*"%>
<%@page import="com.ben.shitu.model.ShituConstant"%>
<%
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String pPk = roleInfo.getBasicInfo().getPPk() + ""; 

		String u = (String) request.getAttribute("uPks");
		String p = (String) request.getAttribute("pPks");
		String aKen = (String) request.getAttribute("aKen");
		String pa = (String) request.getAttribute("pa");
		
		PartInfoDAO dao = new PartInfoDAO();
		PartInfoVO vo = (PartInfoVO) dao.getPartView(p); 

		int wType = 1;

		PicService picService = new PicService(); 
		String playerPic = picService.getPlayerPicStr(roleInfo, p);
		String backtype = (String)request.getSession().getAttribute("backtype");
		if (backtype == null || backtype.equals("") || backtype.equals("null"))  {
			backtype = "1";
		}
		HonourService honourService = new HonourService();
		RoleHonourVO roleHonourVO = honourService.getRoleHonourIsReveal(Integer.parseInt(p), 1);
	%>

	<%
		String apply_hint = (String) request.getAttribute("apply_hint");
		if (apply_hint != null) {
	%>
	<%=apply_hint%><br/>
	<%
		}
	%> 
	<%=vo.getPName() %> 
	<%if(vo.getPPk()!=LangjunConstants.LANGJUN_PPK&&vo.getPPk()!=LangjunConstants.XIANHAI_LANGJUN){ %>
	<%=ShituConstant.getTeLevel1(vo.getTe_level()) %>
	<%
		if (vo.getPPkValue() > 10 && vo.getPPkValue() < 200) {
	%>(黄)<%
		} else if (vo.getPPkValue() > 200) {
	%>(红)<%
		}
	%>
	<br/>
	称号:<%if(roleHonourVO == null){%>无<%}else{%><%=(roleHonourVO.getDetail()==null?"":roleHonourVO.getDetail())+roleHonourVO.getRoleHonourName() %>(<%=roleHonourVO.getRoleHonourTypeName() %>)<%} %>
	<br/>
	<%} %>
	<%=playerPic%>
	性别:<%if (vo.getPSex() == 1) {%>男<%} else if (vo.getPSex() == 2) {%>女<%} else if (vo.getPSex() == 3) {%>人妖<%}%>
	<br/>
	等级:<%=vo.getPGrade()%>级
	<br/>
	帮派:<%if(vo.getPTongName() != null && !vo.getPTongName().equals("")){%><%=vo.getPTongName() %><%}else{ %>无<%} %>
	<br/>
	是否可PK:<%if (vo.getPPks() == 1) {%>不可PK<%} else if (vo.getPPks() == 2) {%>可PK<%}%>
	<br/>
	PK点数:<%=vo.getPPkValue()%>
	<br/>
	<%if(vo.getPPk()!=LangjunConstants.LANGJUN_PPK&&vo.getPPk()!=LangjunConstants.XIANHAI_LANGJUN){ %>
	<%if (pa != null) {%><%=pa%><br/><%}%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">
    <postfield name="cmd" value="n7" />
	<postfield name="pByuPk" value="<%=vo.getUPk()%>" />
	<postfield name="pByPk" value="<%=vo.getPPk()%>" /> 
	<postfield name="pByName" value="<%=vo.getPName()%>" /> 
	</go>
	密语
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>"> 
	<postfield name="cmd" value="n1" />
	<postfield name="pByuPk" value="<%=vo.getUPk()%>" />
	<postfield name="pByPk" value="<%=vo.getPPk()%>" /> 
	</go>
	交易
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n16" />
	<postfield name="other_p_pk" value="<%=p%>" />
	<postfield name="uPks" value="<%=u%>" />
	<postfield name="aKen" value="<%=aKen%>" />
	</go>
	查看装备宠物
	</anchor>
	<!-- <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n23" />
	<postfield name="pPks" value="<%=p%>" />
	<postfield name="uPks" value="<%=u%>" />
	<postfield name="aKen" value="<%=aKen%>" />
	</go>
	查看宠物信息
	</anchor> <br/>-->
	<%
	//判断是否帮主 是帮主就出现该连接 
	TongMemberDAO tongMemberDAO = new TongMemberDAO();
	if(tongMemberDAO.tongtmrights(roleInfo.getBasicInfo().getPPk(),roleInfo.getBasicInfo().getTongId())==1 
	|| tongMemberDAO.tongtmrights(roleInfo.getBasicInfo().getPPk(),roleInfo.getBasicInfo().getTongId())==2 
	|| tongMemberDAO.tongtmrights(roleInfo.getBasicInfo().getPPk(),roleInfo.getBasicInfo().getTongId())==3 
	|| tongMemberDAO.tongtmrights(roleInfo.getBasicInfo().getPPk(),roleInfo.getBasicInfo().getTongId())==4){
	 %>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/tongmemberaction.do")%>">
	<postfield name="cmd" value="n14" />
	<postfield name="tPk" value="<%=roleInfo.getBasicInfo().getTongId() %>" />
	<postfield name="pNmae" value="<%=vo.getPName()%>" />
	<postfield name="uPks" value="<%=u%>" />
	<postfield name="pPks" value="<%=p%>" />
	<postfield name="aKen" value="<%=aKen%>" /> 
	</go>
	邀请入帮
	</anchor>
	<%} %>
	 
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do")%>">
	<postfield name="pByPk" value="<%=vo.getPPk()%>" />
	<postfield name="pByName" value="<%=vo.getPName()%>" />
	<postfield name="uPks" value="<%=u%>" />
	<postfield name="pPks" value="<%=p%>" />
	<postfield name="aKen" value="<%=aKen%>" />
	<postfield name="cmd" value="n1" />
	</go>
	加为好友
	</anchor>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/blacklistaction.do")%>">
	<postfield name="pByPk" value="<%=vo.getPPk()%>" />
	<postfield name="pByName" value="<%=vo.getPName()%>" />
	<postfield name="uPks" value="<%=u%>" />
	<postfield name="pPks" value="<%=p%>" />
	<postfield name="aKen" value="<%=aKen%>" />
	<postfield name="cmd" value="n2" />
	</go>
	加入黑名单
	</anchor>
	<br/>

	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="b_ppk" value="<%=p%>" />
	<postfield name="b_upk" value="<%=u%>" />
	<postfield name="b_aKen" value="<%=aKen%>" />
	</go>
	申请组队
	</anchor>
	<br/>
	<%} %>
	<% if(backtype.equals("1")){%>
	<anchor>
	<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>
	返回
	</anchor>
	<%} %>
	<% if(backtype.equals("3")){%>
	<anchor>
	<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/communioninfoaction.do?cmd=n1")%>" method="get"></go>
	返回
	</anchor>
	<%} %>
	<% if(backtype.equals("2")){%>
	<anchor>
	<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pk.do?cmd=n1")%>" method="get"></go>
	返回
	</anchor>
	<%} %>
	
<%@ include file="/init/templete/game_foot.jsp"%>
