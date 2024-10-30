<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.model.user.BasicInfo,com.ls.model.user.RoleEntity"%>
<%@page import="com.web.service.avoidpkprop.AvoidPkPropService"%>
<%@page import="com.ben.shitu.model.ShituConstant"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="login" title="<bean:message key="gamename"/>">
<p>
<%
		RoleEntity roleInfo = (RoleEntity) request.getAttribute("roleInfo");
		BasicInfo player = roleInfo.getBasicInfo();
		int pk_value = player.getEvilValue();
		
		String playerPic = (String) request.getAttribute("playerPic");
		String glory_value = (String) request.getAttribute("glory_value");
		String buff_list_describe = (String) request.getAttribute("buff_list_describe");
		String jieyiname = (String) request.getAttribute("jieyiname");
		String jiehunname = (String) request.getAttribute("jiehunname");
		String raceStr= player.getPRace()==1?"妖族":"巫族";
		
%>
<%@ include file="part_info_menu_head.jsp"%>
<%
		if (player != null) {
%>
<%=playerPic %>
<%=roleInfo.getFullName()%><br/>
种族:<%=raceStr %><br/>
称号:<%=roleInfo.getTitleSet().getShowTitleName()%><br/>
结义:<%=jieyiname %><br/>
结婚:<%=jiehunname %><br/>
等级:<%=player.getGrade()%><br/>
性别:<%if (player.getSex() == 1) {%>男<%} else if (player.getSex() == 2) {%>女<%} else if (player.getSex() == 3) {%>人妖<%}%><br/>
经验值:<%=player.getCurExp()%> /<%=player.getNextGradeExp()%><br/>
<%}%>
祝福:<%if (buff_list_describe.length() > 8) {%><anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/stateaction.do")%>">
	<postfield name="cmd" value="n14" />
	</go><%=buff_list_describe.substring(0,8)%>...
	</anchor>
	<br/>
	<%} else {%><anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/stateaction.do")%>">
	<postfield name="cmd" value="n14" />
	</go><%=buff_list_describe%>
	</anchor>
	<br/>
	<%}%>
PK开关:<%if (player.getPkSwitch() == 1) {%>关<%} else {%>开<%}%><br/>
<%AvoidPkPropService avoidPkPropService = new AvoidPkPropService();int time = avoidPkPropService.getAvoidPkProp(player.getPPk());if (time > 0) {%>PK保护还剩:<%=time%>分钟	<br/><%}%>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
