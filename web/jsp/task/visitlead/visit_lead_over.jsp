<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.web.service.player.*"%>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
<%
	String pPk = (String)session.getAttribute("pPk");
		String type = request.getParameter("type");
		int propID = 0;
		if (type.equals("1"))
		{// 丐帮
	propID = 65;
		}
		if (type.equals("2"))
		{// 少林
	propID = 53;
		}
		if (type.equals("3"))
		{// 明教
	propID = 59;
		} 
		PropUseService propUseService = new PropUseService();
		String hint = propUseService.full_timeprop(Integer.parseInt(pPk),propID);
%>
	<%=hint %> 
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
