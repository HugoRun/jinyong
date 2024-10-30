<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.ben.vo.info.effect.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p> 
<%
	String w_type = (String)request.getAttribute("w_type");
	PropUseEffect propUseEffect = (PropUseEffect)request.getAttribute("propUseEffect");
	String page_no = (String)request.getSession().getAttribute("page_no");
	if( propUseEffect!=null )
	{
 		if( propUseEffect.isEffected() )
 		{
 		%> 
		<%=propUseEffect.getEffectDisplay() %>
 
		<%
 		}
 		else
 		{
 		%> 
		<%=propUseEffect.getNoUseDisplay() %>

		<%
 		}
	}
	else
	{
		out.print("空指针");
	}
 %> 
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n1") %>">
<postfield name="w_type" value="<%=w_type %>" />
<postfield name="page_no" value="<%=page_no %>" />
</go>
返回
</anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
