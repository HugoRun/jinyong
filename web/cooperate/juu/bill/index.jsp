<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.constant.Channel"%>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*"%>
<%@page import="com.ls.pub.config.sky.ConfigOfSky,com.ls.pub.config.GameConfig"%>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<%
	RoleService roleService = new RoleService();
	RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
	String user_name = (String)session.getAttribute("ssid");
	String cpurl = GameConfig.getUrlOfGame()+"/returnMall.do";
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="bill" title="充值">
<p>
<%@ include file="/init/system/error_hint.jsp"%>
【<%=GameConfig.getYuanbaoName() %>充值服务】<br/>
<anchor><go href="<%=response.encodeURL("http://wap.juu.cn/index.php?module=user&amp;view=gameRecharge&amp;game_id=51&amp;game_area=74&amp;account="+user_name+"&amp;return="+cpurl)%>" method="get"></go>快速充值</anchor><br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>
