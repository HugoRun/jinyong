<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.constant.Channel"%>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*"%>
<%@page import="com.ls.pub.config.GameConfig"%>

<%
    response.setContentType("text/vnd.wap.wml");
%>
<%
    RoleService roleService = new RoleService();
    RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
    String skyid = (String)session.getAttribute("skyid");
    String cpurl = GameConfig.getUrlOfGame()+"/returnMall.do";
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="bill" title="充值">
<p>
<%@ include file="/init/system/error_hint.jsp"%>
【<%=GameConfig.getYuanbaoName() %>充值服务】<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sina/bill.do?cmd=n1")%>">
<postfield name="sinaamt_str" value="5" />
</go>
兑换5U币
</anchor><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sina/bill.do?cmd=n1")%>">
<postfield name="sinaamt_str" value="10" />
</go>
兑换10U币
</anchor><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sina/bill.do?cmd=n1")%>">
<postfield name="sinaamt_str" value="20" />
</go>
兑换20U币
</anchor><br/>
-------------------<br/>
手动兑换<br/>
请输入U币兑换数量:<input name="sinaamt_str" type="text" format="*N" size="4"/><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sina/bill.do?cmd=n1")%>">
<postfield name="sinaamt_str" value="$sinaamt_str" />
</go>
兑换确认
</anchor><br/>
<anchor><go method="post" href="http://3g.sina.com.cn/dpool/paycenter/index.php?pid=jygame"/>新浪U币支付中心</anchor><br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>
