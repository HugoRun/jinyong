<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"  import="com.ls.pub.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>   
	<%  
		String buff_list_describe = (String)request.getAttribute("buff_list_describe");
	%>
	祝福列表:<br/>
	<%if(buff_list_describe != null && !buff_list_describe.equals("")) {%>
		<%=StringUtil.isoToGBK(buff_list_describe) %>
	<%} %>
	<br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n1")%>"></go>返回</anchor>	
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
