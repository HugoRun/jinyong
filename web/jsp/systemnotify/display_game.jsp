<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>   
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="message" title="<s:message key = "gamename"/>">
<p>
<%
String display = request.getAttribute("display").toString();
if(display != null){
%><%=display %><%
}
%>
<br/>
<anchor>
<go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pubbuckaction.do")%>" method="get"></go>
返回游戏
</anchor>
</p>
</card>
</wml>
