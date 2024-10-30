<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.constant.Channel"%>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*"%>
<%@page import="com.ls.pub.config.sky.ConfigOfSky,com.ls.pub.config.GameConfig"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="bill" title="充值">
<p>
<%@ include file="/init/system/error_hint.jsp"%>
【<%=GameConfig.getYuanbaoName()%>充值服务】<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/air/bill.do?cmd=n1")%>">
<postfield name="kbamt" value="5" />
</go>
兑换5元宝
</anchor><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/air/bill.do?cmd=n1")%>">
<postfield name="kbamt" value="10" />
</go>
兑换10元宝
</anchor><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/air/bill.do?cmd=n1")%>">
<postfield name="kbamt" value="15" />
</go>
兑换15元宝
</anchor><br/>
(1元人民币=10元宝)<br/>
-------------------<br/>
手动兑换<br/>
请输入元宝兑换数量:<input name="kbamt" type="text" format="*N" maxlength="4"/>(目前限额最高15元宝)<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/air/bill.do?cmd=n1")%>">
<postfield name="kbamt" value="$kbamt" />
</go>
兑换确认
</anchor><br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>