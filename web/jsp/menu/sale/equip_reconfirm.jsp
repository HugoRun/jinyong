<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p> 
<% 
	String page_no = (String)request.getAttribute("page_no");
	String w_type = (String)request.getAttribute("w_type");
	int pw_pk = (Integer)request.getAttribute("pw_pk");
	
	String resultWml = (String)request.getAttribute("resultWml");
	if( resultWml!=null )
	{
 
%> 
	<%=resultWml%><br/>
 
<%
	}
	else
	{
		out.print("空指针");
	}
 %> 
					<anchor> 
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/sale.do")%>">
					<postfield name="cmd" value="n2" />
					<postfield name="page_no" value="<%=page_no %>" />
					<postfield name="w_type" value="<%=w_type+"" %>" />
					<postfield name="goods_id" value="<%=pw_pk+"" %>" />
					<postfield name="reconfirm" value="1" />
					</go>
					确定 
					</anchor>|<anchor>
 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/sale.do?cmd=n1") %>">
 <postfield name="w_type" value="<%=w_type+"" %>" />
 </go>
 取消
 </anchor>
<br/>
 
 <anchor>
 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/sale.do?cmd=n1") %>">
 <postfield name="w_type" value="<%=w_type+"" %>" />
 </go>
 返回
 </anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
