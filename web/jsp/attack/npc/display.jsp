<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
    import="java.util.*,com.ls.pub.util.*,com.pub.ben.info.*"
    pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="mall" title="<bean:message key="gamename"/>">
<p>
<%
String display = (String)request.getAttribute("display");
if(display!=null&&!display.equals("")&&!display.equals("null")){
%><%=display %><%
}
%>
<br/>
<anchor>返回游戏<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/scene.do?isRefurbish=1")%>" method="get"></go></anchor><br/>
--------------------
<br />
报时:<%=DateUtil.getMainPageCurTimeStr()%>
</p>
</card>
</wml>