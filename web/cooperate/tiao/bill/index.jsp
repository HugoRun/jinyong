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
	String user_name = (String)session.getAttribute("user_name");
	String cpurl = GameConfig.getUrlOfGame()+"/returnMall.do";
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="bill" title="充值">
<p>
<%@ include file="/init/system/error_hint.jsp"%>
【<%=GameConfig.getYuanbaoName() %>充值服务】
<anchor><go href="<%=response.encodeURL("http://wap.wanba.cn/Pay.aspx?Name="+user_name+"&amp;Return="+cpurl)%>" method="get"></go>金豆充值</anchor><br/>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/tiao/bill.do?cmd=n1")%>">
<postfield name="kbamt" value="500" />
</go>
兑换500<%=GameConfig.getYuanbaoName() %>
</anchor><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/tiao/bill.do?cmd=n1")%>">
<postfield name="kbamt" value="1000" />
</go>
兑换1000<%=GameConfig.getYuanbaoName() %>
</anchor><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/tiao/bill.do?cmd=n1")%>">
<postfield name="kbamt" value="2000" />
</go>
兑换2000<%=GameConfig.getYuanbaoName() %>
</anchor><br/>
-------------------<br/>
手动兑换<br/>
请输入兑换<%=GameConfig.getYuanbaoName() %>数量:<input name="kbamt" type="text" format="*N" size="4"/><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/tiao/bill.do?cmd=n1")%>">
<postfield name="kbamt" value="$kbamt" />
</go>
兑换确认
</anchor><br/>
<anchor><go href="<%=response.encodeURL("http://wap.wanba.cn/Pay.aspx?Name="+user_name+"&amp;Return="+cpurl)%>" method="get"></go>金豆充值</anchor><br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>
