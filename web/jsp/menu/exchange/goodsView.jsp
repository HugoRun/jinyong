<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	<%
		String resultWml = (String) request.getAttribute("resultWml");
		String equipPic = (String) request.getAttribute("equipPic");
		String menu_id = (String) request.getAttribute("menu_id");
		String w_type = (String) request.getAttribute("w_type");
		if (resultWml != null) {
	%>
	<%=resultWml%>

	<%
		} else {
			out.print("空指针");
		}
	%> 
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu.do?cmd=n1")%>" >
<postfield name="menu_id" value="<%=menu_id%>" />
</go>
返回 
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
