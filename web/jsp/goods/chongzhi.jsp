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
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="bill" title="充值">
<p>
<%=roleInfo.getBasicInfo().getName()%>您的<%=GameConfig.getYuanbaoName() %>余额不足,请充值元宝.
<%@ include file="/init/system/error_hint.jsp"%>
<s:message key = "gamename"/>充值通道<br/>
欢迎您使用<s:message key = "gamename"/>【<%=GameConfig.getYuanbaoName() %>】充值服务,祝您游戏愉快!<br/> 
输入要兑换K币的数量:<input name="kbamt" type="text" format="*N" size="3"/><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/goods.do?cmd=n8")%>">
<postfield name="kbamt" value="$kbamt" />
</go>
兑换
</anchor><br/>
<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/goods.do?cmd=n2")%>"></go>返回</anchor><br/>
温馨提示:每成功兑换1K币可获得【<%=GameConfig.getYuanbaoName() %>】×1,并可获得商城积分×1!<br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
