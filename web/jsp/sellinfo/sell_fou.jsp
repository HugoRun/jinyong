<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.dao.sellinfo.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	<%@ include file="/init/init_xito.jsp"%>
     <% //System.out.println(request.getParameter("mapid"));
		String jumpterm = "0";
		if (request.getParameter("jumpterm") != null) {
			jumpterm = request.getParameter("jumpterm");
		} else if ((String) request.getAttribute("jumpterm") != null) {
			jumpterm = (String) request.getAttribute("jumpterm");
		}
		String pPk = null;
		if (request.getParameter("pPk") != null) {
			pPk = request.getParameter("pPk");
		} else if ((String) request.getAttribute("pPk") != null) {
			pPk = (String) request.getAttribute("pPk");
		}
		String mapid = null;
		if (request.getParameter("mapid") != null) {
			mapid = request.getParameter("mapid");
		} else if ((String) request.getAttribute("mapid") != null) {
			mapid = (String) request.getAttribute("mapid");
		}
		String uPk = null;
		if (request.getParameter("uPk") != null) {
			uPk = request.getParameter("uPk");
		} else if ((String) request.getAttribute("id") != null) {
			uPk = (String) request.getAttribute("id");
		}  
	%>
	<%
	String pByPk = request.getParameter("pByPk");
	SellInfoDAO dao = new SellInfoDAO();
	dao.getSelleInfoDelete(pByPk);
	//response.sendRedirect("../../pubaction.do?jumpterm="+jumpterm+"&pPk="+pPk+"&mapid="+mapid+"&uPk="+uPk+"&chair="+request.getParameter("chair"));
	request.getRequestDispatcher("../../pubaction.do?jumpterm="+jumpterm+"&pPk="+pPk+"&mapid="+mapid+"&uPk="+uPk+"&chair="+session.getAttribute("checkStr")).forward(request,response);
				
	%>
	  
</p>
</card>
</wml>
