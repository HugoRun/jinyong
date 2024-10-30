<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ls.pub.util.*,com.pub.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		String hint = (String)request.getParameter("hint");
		if(hint == null || hint.equals("")) {
			hint = (String)request.getAttribute("hint");
		}
		String auth1 = (String)request.getAttribute("auth");
		String getTodayStr = DateUtil.getTodayStr();
		String auth = MD5.getMD5Str(getTodayStr).substring(1,6);
	if(	hint != null && !hint.equals("")) {
	 %>
	<%=hint %>
<%} else { %>
	空指针！
<%} %>
<br/>
<%if(auth != null && auth1.equals(auth)){  %>
<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/zhiModifyPassword.do?cmd=n1")%>">
	</go>
	返回
	</anchor>
	<%} %>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/index.jsp")%>">
	</go>
	返回登录
	</anchor> 
</p>
</card>
</wml>
