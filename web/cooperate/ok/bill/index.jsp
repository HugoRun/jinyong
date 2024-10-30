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
    String osid = (String)session.getAttribute("osid");
    String cpurl = GameConfig.getUrlOfGame()+"/returnMall.do";
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="bill" title="充值">
<p>
<%@ include file="/init/system/error_hint.jsp"%>
【<%=GameConfig.getYuanbaoName() %>充值服务】
<anchor><go href="<%=response.encodeURL("http://wapok.cn/gameconsume.php?osid="+osid+"&amp;backurl="+cpurl)%>" method="get"></go>ok币充值</anchor><br/>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/ok/bill.do?cmd=n1")%>">
<postfield name="kbamt" value="5" />
</go>
兑换5ok币
</anchor><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/ok/bill.do?cmd=n1")%>">
<postfield name="kbamt" value="10" />
</go>
兑换10ok币
</anchor><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/ok/bill.do?cmd=n1")%>">
<postfield name="kbamt" value="50" />
</go>
兑换50ok币
</anchor><br/>
-------------------<br/>
手动兑换<br/>
请输入ok币兑换数量:<input name="kbamt" type="text" format="*N" size="4"/><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/ok/bill.do?cmd=n1")%>">
<postfield name="kbamt" value="$kbamt" />
</go>
兑换确认
</anchor><br/>
<anchor><go href="<%=response.encodeURL("http://wapok.cn/gameconsume.php?osid="+osid+"&amp;backurl="+cpurl)%>" method="get"></go>ok币充值</anchor><br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>
