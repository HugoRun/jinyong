<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.constant.Channel"%>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*"%>

<%
    response.setContentType("text/vnd.wap.wml");
%>
<%
    RoleService roleService = new RoleService();
    RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="bill" title="充值">
<p>
    <s:message key = "gamename"/>充值通道
    <br/>
    <%=roleInfo.getBasicInfo().getName()%>您好!欢迎您使用<s:message key = "gamename"/>【<%=GameConfig.getYuanbaoName() %>】充值服务,祝您游戏愉快!
    <br/>
    <anchor>
    <go method="get" href="http://wap.tom.com/phone/nz_sub.jsp?cmd=n1&amp;c=327&amp;t=10601&amp;cons_amigo=<%=roleInfo.getBasicInfo().getPPk()%>">
    </go>
    TOM充值通道
    </anchor>
    <br/>
    <anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/emporiumaction.do?cmd=n1")%>"></go>返回商城</anchor><br/>
    <%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
