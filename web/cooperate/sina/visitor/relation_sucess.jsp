<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="fail" title="<s:message key = "gamename"/>">
<p>
<%
    String login_platform_url = GameConfig.getUrlOfLoginPlatform();
    String passport_visitor = (String)request.getAttribute("passport_visitor");
    String display = (String)request.getAttribute("display");
%>
欢迎您来到金庸的武侠世界!<br/>
<%
    if(display != null && !display.equals("")){
    %><%=display%><br/><%
    }
%>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/sina/vis.do?cmd=n5")%>" method="post">
<postfield name="passport_visitor" value="<%=passport_visitor %>" />
</go>
进入游戏
</anchor><br/>
<anchor><go href="<%=login_platform_url %>" method="get"></go>返回专区</anchor>
</p>
</card>
</wml>