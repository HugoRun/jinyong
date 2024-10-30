<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.ben.vo.info.effect.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p> 
<%
	PropUseEffect propUseEffect = (PropUseEffect)request.getAttribute("propUseEffect");
	if( propUseEffect!=null )
	{
 		if( propUseEffect.getIsEffected() )
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
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n1") %>"></go></anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
