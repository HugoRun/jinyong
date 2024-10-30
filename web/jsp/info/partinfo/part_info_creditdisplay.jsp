<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.dp.vo.credit.*,com.ls.pub.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<% PlayerCreditVO pcv=(PlayerCreditVO)request.getAttribute("pcvo");
		if(pcv!=null){
			%>
			声望:<%=pcv.getCreditname()%><br/>
			描述:<br/>
			<%=pcv.getCreditdisplay()%>
			<%
		}
	 %><br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/credit.do?cmd=n1")%>"></go>返回</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
