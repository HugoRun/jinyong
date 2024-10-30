<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.dao.sellinfo.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%@ include file="/init/init_xito.jsp"%>
	<%
		//System.out.println(request.getParameter("mapid"));
		
		String sPk = request.getParameter("sPk"); 
		 
		SellInfoDAO dao = new SellInfoDAO();
		dao.getSelleInfoDeMon(sPk); 
		//response.sendRedirect("../../../pubaction.do?jumpterm=" + jumpterm+ "&pPk=" + pPk + "&mapid=" + mapid + "&uPk=" + uPk +"&chair="+request.getParameter("chair"));
		request.getRequestDispatcher("../../../pubaction.do?chair="+session.getAttribute("checkStr")).forward(request, response); 
				
	%> 
</p>
</card>
</wml>
