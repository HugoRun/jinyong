<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.web.service.partwrap.*,com.ls.model.user.*"%> 
<%@page import="com.pm.service.pic.PicService"%>
<%@page import="com.ben.dao.info.partinfo.*,com.ls.web.service.player.*"%>
<%@page import="com.ben.vo.info.partinfo.PartInfoVO"%>
<%@page import="com.ben.dao.tong.TongMemberDAO"%>
<%@page import="com.ls.web.service.honour.HonourService"%>
<%@page import="com.ben.vo.honour.RoleHonourVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	<%
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
	
		String number = (String) request.getAttribute("number");
		String u = (String) request.getAttribute("uPks");
		String p = (String) request.getAttribute("pPks");
		String aKen = (String) request.getAttribute("aKen"); 
		
		PartInfoDAO dao = new PartInfoDAO();
		PartInfoVO vo = (PartInfoVO) dao.getPartView(p);
		PartWrapService partWrapService = new PartWrapService();

		int wType = 1;
		List list = partWrapService.partwraplist(Integer.parseInt(p), wType);

		PicService picService = new PicService(); 
		String playerPic = picService.getPlayerPicStr(roleInfo, roleInfo.getBasicInfo().getPPk());
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
	<%=vo.getPName()%>
	<%
		if (vo.getPPkValue() > 10 && vo.getPPkValue() < 200) {
	%>(黄)<%
		} else if (vo.getPPkValue() > 200) {
	%>(红)<%
		}
	%>
	<br/>
	<%=playerPic%>
	称号:<%if(roleHonourVO == null){%>无<%}else{%><%=(roleHonourVO.getDetail()==null?"":roleHonourVO.getDetail())+roleHonourVO.getRoleHonourName() %>(<%=roleHonourVO.getRoleHonourTypeName() %>)<%} %>
	<br/>
	性别:<%
		if (vo.getPSex() == 1) {
	%>
	男
	<%
		} else if (vo.getPSex() == 2) {
	%>
	女
	<%
		} else if (vo.getPSex() == 3) {
	%>
	人妖
	<%
		}
	%>
	<br/>
	等级:<%=vo.getPGrade()%>级
	<br/>
	师门:<%if (vo.getPSchool().equals("shaolin")) {%>少林<%} else if (vo.getPSchool().equals("gaibang")) {%>丐帮<%} else if (vo.getPSchool().equals("mingjiao")) {%>明教<%} else {%>无<%}%> 
	<br/>
	帮派:
	<%
	if(vo.getPTongName() != null && !vo.getPTongName().equals("")){
	 %>
	<%=vo.getPTongName() %>
	<%}else{ %>无<%} %>
	<br/>
	是否可PK:<%
		if (vo.getPPks() == 1) {
	%>不可PK<%
		} else if (vo.getPPks() == 2) {
	%>可PK<%
		}
	%>
	<br/>
	PK点数:<%=vo.getPPkValue()%>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n16" />
	<postfield name="other_p_pk" value="<%=p%>" />
	<postfield name="uPks" value="<%=u%>" />
	<postfield name="aKen" value="<%=aKen%>" />
	</go>
	查看装备
	</anchor>
	<br/> 
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">
    <postfield name="cmd" value="n7" />
	<postfield name="pByuPk" value="<%=vo.getUPk()%>" />
	<postfield name="pByPk" value="<%=vo.getPPk()%>" /> 
	<postfield name="pByName" value="<%=vo.getPName()%>" /> 
	</go>
	密语
	</anchor> 
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
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/tongmemberaction.do") %>">
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
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do")%>">
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
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/blacklistaction.do")%>">
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
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do")%>">
	<postfield name="cmd" value="<%=number %>" />
	</go>
	返回
	</anchor>  
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
