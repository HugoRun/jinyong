<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.dao.sellinfo.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.dao.info.partinfo.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p> 
     <%//System.out.println(request.getParameter("mapid"));
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
	<br/>
	<%
	String pByPk = (String)session.getAttribute("pPks");
	SellInfoDAO dao = new SellInfoDAO();
	PartInfoDAO partInfoDAO = new PartInfoDAO();
	//int dd = dao.getSelleInfoIdBy(pByPk);
	
	%>
	要和您进行交易，您是否同意？
	<br/>
	<anchor>
	<go method="post"   href="<%=GameConfig.getContextPath()%>/jsp/sellinfo/bjyym/bjyym_sell_info.jsp">
	<postfield name="mapid" value="<%=mapid%>" />
	<postfield name="uPk" value="<%=uPk%>" />
	<postfield name="pPk" value="<%=pPk%>" />
	<postfield name="jumpterm" value="<%=jumpterm%>" />
	</go>
	是
	</anchor>
	<anchor>
	<go method="post"   href="<%=GameConfig.getContextPath()%>/jsp/sellinfo/sell_fou.jsp">
	<postfield name="mapid" value="<%=mapid%>" />
	<postfield name="uPk" value="<%=uPk%>" />
	<postfield name="pPk" value="<%=pPk%>" />
	<postfield name="jumpterm" value="<%=jumpterm%>" />
	<postfield name="pByPk" value="<%=pByPk%>" />
	</go>
	否
	</anchor>
	<br/>  
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
