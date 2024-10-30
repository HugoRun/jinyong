<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"%>
<%@page import="java.util.*,com.ben.vo.honour.HonourVO,com.ben.vo.vip.RoleVipVO,com.ls.model.property.RoleVip,com.ls.model.user.*,com.ls.web.service.player.RoleService"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
	    RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
		HonourVO vo = (HonourVO) request.getAttribute("honourVO");
		//RoleVip roleVip = new RoleVip(roleInfo.getBasicInfo().getPPk());//得到玩家的VIP数据
		RoleVipVO roleVipVO = roleInfo.getRoleVip().getRoleVipView();
	%>
	<%=vo.getHoTitle()%>(<%=vo.getHoTypeName() %>)<br />
	<%=vo.getHoDisplay()%><br />
	<%if(vo.getHoType() == HonourVO.VIP||vo.getHoType() == HonourVO.ZBHONOUR){%>
	附加属性:攻击+<%=vo.getHoAttack()%>%,防御+<%=vo.getHoDef()%>%,气血+<%=vo.getHoHp()%>%,暴击+<%=vo.getHoCrit()%>%,<%if(roleVipVO != null){%><%if(roleVipVO.getIsDieDropExp() == 0){ %>死亡损失经验 <%}else{%>死亡不损失经验<%}} %><br />
	<%}else{ %>
	附加属性:攻击+<%=vo.getHoAttack()%>,防御+<%=vo.getHoDef()%>,气血+<%=vo.getHoHp()%>,暴击+<%=vo.getHoCrit()%><br />
	<%} %>
	<anchor>
	<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath() + "/honour.do?cmd=n1")%>" ></go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>