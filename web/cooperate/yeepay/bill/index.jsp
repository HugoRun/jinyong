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
<s:message key = "gamename"/>充值通道<br/>
<%=roleInfo.getBasicInfo().getName()%>您好!欢迎您使用<s:message key = "gamename"/>【<%=GameConfig.getYuanbaoName() %>】充值服务,祝您游戏愉快!<br/> 
<anchor>
<go method="post" href="<%=GameConfig.getContextPath()%>/yeepay/bill.do?cmd=subIndex">
<postfield name="b_type" value="7" />
</go>
神州行移动充值卡(推荐)
</anchor><br/>
<anchor>
<go method="post" href="<%=GameConfig.getContextPath()%>/yeepay/bill.do?cmd=subIndex">
<postfield name="b_type" value="3" />
</go>
骏网一卡通充值
</anchor><br/>
<anchor>
<go method="post" href="<%=GameConfig.getContextPath()%>/yeepay/bill.do?cmd=subIndex">
<postfield name="b_type" value="2" />
</go>
盛大游戏卡充值
</anchor><br/>
<anchor>
<go method="post" href="<%=GameConfig.getContextPath()%>/yeepay/bill.do?cmd=subIndex">
<postfield name="b_type" value="6" />
</go>
联通一卡充充值
</anchor><br/>
<anchor>
<go method="post" href="<%=GameConfig.getContextPath()%>/yeepay/bill.do?cmd=subIndex">
<postfield name="b_type" value="5" />
</go>
电信充值卡充值
</anchor><br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>
