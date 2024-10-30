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
<go method="post" href="<%=GameConfig.getContextPath()%>/bill.do">
<postfield name="b_type" value="1" />
</go>
神州行移动充值卡
</anchor><br/>
可使用面值为30、50、100元移动神州行移动充值卡为本游戏帐号充值(本功能仅限全球卡,不支持本地卡)<br/>
<anchor>
<go method="post" href="/bill.do">
<postfield name="b_type" value="2" />
</go>
骏网一卡通充值
</anchor><br/>
可使用5、6、10、15、30、50、60、100元面值骏网一卡通充值卡卡为本游戏帐号戏充值<br/>
<anchor>
<go method="post" href="<%=GameConfig.getContextPath()%>/bill.do">
<postfield name="b_type" value="3" />
</go>
盛大游戏卡充值
</anchor><br/>
可使用面值为5、6、10、15、30、45、50、60、100元盛大游戏卡为本游戏帐号充值<br/> 
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>
