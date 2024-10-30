<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.web.service.partwrap.*"%>
<%@page import="com.ben.vo.partwrap.*,com.ls.pub.util.*"%>
<%@page import="com.pm.service.pic.PicService"%>
<%@page import="com.ben.dao.info.partinfo.*"%>
<%@page import="com.ben.vo.info.partinfo.PartInfoVO"%>
<%@page import="com.ls.web.service.honour.HonourService"%>
<%@page import="com.ben.vo.honour.RoleHonourVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		String classId = request.getParameter("classId");
		if(classId == null || classId.equals("")) {
			classId = (String)request.getAttribute("classId");
		}
		String classPageNo = request.getParameter("classPageNo");
		if(classPageNo == null || classPageNo.equals("")) {
			classPageNo = (String)request.getAttribute("classPageNo");
		}
		String p = (String) request.getAttribute("pPks");
		if(p == null || p.equals("")) {
			p = request.getParameter("pPks");
		}
		String className = (String) request.getAttribute("className");
		if(className == null || className.equals("")) {
			className = request.getParameter("className");
		}
		String type = (String) request.getAttribute("type");
		if(type == null || type.equals("")) {
			type = request.getParameter("type");
		}
		String pageId = (String) request.getAttribute("pageId");
		if(pageId == null || pageId.equals("")) {
			pageId = request.getParameter("pageId");
		}
		String pageNo = (String) request.getAttribute("pageNo");
		if(pageNo == null || pageNo.equals("")) {
			pageNo = request.getParameter("pageNo");
		}
		
		PartInfoDAO dao = new PartInfoDAO();
		PartInfoVO vo = (PartInfoVO) dao.getPartView(p);
		PartWrapService partWrapService = new PartWrapService();

		int wType = 1;
		List list = partWrapService.partwraplist(Integer.parseInt(p), wType);

		PicService picService = new PicService(); 
		//String playerPic = picService.getPlayerPicStr(pPk, p);
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
	<%//=playerPic%>
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
	装备:
	<%
		for (int i = 0; i < list.size(); i++) {
			PartEquipVO partEquipVO = (PartEquipVO) list.get(i);
			if (partEquipVO.getTableType() == 2
					&& partEquipVO.getWId() != 0) {
	%>
	        <%=partEquipVO.getWName()%>
	<%
		}
			if (partEquipVO.getTableType() == 1
					&& partEquipVO.getGoodsType() == 1
					&& partEquipVO.getWId() != 0) {
	%>
	<%=partEquipVO.getWName()%>
	<%
		}
			if (partEquipVO.getTableType() == 1
					&& partEquipVO.getGoodsType() == 2
					&& partEquipVO.getWId() != 0) {
	%>
	<%=partEquipVO.getWName()%>
	<%
		}
			if (partEquipVO.getTableType() == 1
					&& partEquipVO.getGoodsType() == 3
					&& partEquipVO.getWId() != 0) {
	%>
	<%=partEquipVO.getWName()%>
	<%
		}
			if (partEquipVO.getTableType() == 1
					&& partEquipVO.getGoodsType() == 4
					&& partEquipVO.getWId() != 0) {
	%>
	<%=partEquipVO.getWName()%>
	<%
		}
			if (partEquipVO.getTableType() == 3
					&& partEquipVO.getGoodsType() == 1
					&& partEquipVO.getWId() != 0) {
	%>
	<%=partEquipVO.getWName()%>
	<%
		}
			if (partEquipVO.getTableType() == 3
					&& partEquipVO.getGoodsType() == 2
					&& partEquipVO.getWId() != 0) {
	%>
	<%=partEquipVO.getWName()%>
	<%
		}
			if (partEquipVO.getTableType() == 3
					&& partEquipVO.getGoodsType() == 3
					&& partEquipVO.getWId() != 0) {
	%>
	<%=partEquipVO.getWName()%>
	<%
		}

		}
	%>
	<br/>
	
		
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do")%>">
	<postfield name="pByPk" value="<%=vo.getPPk()%>" />
	<postfield name="pByName" value="<%=vo.getPName()%>" />
	<postfield name="pPks" value="<%=p%>" />	
	<postfield name="classId" value="<%=classId %>" />
	<postfield name="classPageNo" value="<%=classPageNo %>" />
	<postfield name="className" value="<%=className %>" />
	<postfield name="cmd" value="n7" />
	</go>
	加为好友
	</anchor>
	<br/>
	<% 
	System.out.println("type="+type);
	if (type != null && type.equals("2")) {%>
		<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do")%>">
			<postfield name="cmd" value="n4" />
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="pageNo" value="<%=pageNo %>" />
			<postfield name="classPageNo" value="<%=classPageNo %>" />
			<postfield name="page_id" value="<%=pageId %>" />
			</go>
			返回文章
		</anchor>		
	<%} else if (type != null && type.equals("3")) { %>
		<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do")%>">
			<postfield name="cmd" value="n5" />
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="pageNo" value="<%=pageNo %>" />
			<postfield name="classPageNo" value="<%=classPageNo %>" />
			<postfield name="pageId" value="<%=pageId %>" />
			</go>
			返回文章回帖
		</anchor>
	<%} else { %>
	 	<anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="pageNo" value="<%=classPageNo %>" />
			</go>
			返回<%=className %>
		</anchor>
		<%} %>
		<br/>
	 
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
