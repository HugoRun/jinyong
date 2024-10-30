<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml"> 
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>

<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	<%
		String resultWml = (String) request.getAttribute("resultWml");
		String pg_pk = (String) request.getAttribute("pg_pk");
		String prop_id = (String) request.getAttribute("prop_id");
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
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap/markup.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="pg_pk" value="<%=pg_pk %>" />
	<postfield name="goods_id" value="<%=prop_id %>" />
	</go>
	标记
	</anchor>
	<br/>
	
	<%
	if( request.getAttribute("isMarkup")==null )
	{
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap/markup.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="pg_pk" value="<%=pg_pk %>" />
	<postfield name="goods_id" value="<%=prop_id %>" />
	</go>
	确定
	</anchor>
	<br/>
	<%
	}
	%>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n1")%>"></go></anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
