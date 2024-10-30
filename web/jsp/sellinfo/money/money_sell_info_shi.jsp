<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.pub.ben.info.*,com.ben.dao.info.partinfo.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
	PartInfoDAO dao = new PartInfoDAO(); 
	String pByPk = (String) request.getAttribute("pByPk");
	String pSilver = (String) request.getAttribute("pSilver");
	%>
	您赠送给了<%=dao.getPartName(pByPk)%> <%=pSilver %><%=GameConfig.getMoneyUnitName() %>！
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
