<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="java.util.*"%>
<%@page import="com.ls.pub.constant.Shortcut"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>  
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="ID" title="<bean:message key="gamename"/>">
<p>
<%
	String sc_pk = (String)request.getAttribute("sc_pk");
	List cures = (List)request.getAttribute("cures");
	PlayerPropGroupVO  cure = null;
 %>
药品列表<br/>
<%
	if( cures!=null && cures.size()!=0 )
	{
		for(int i=0;i<cures.size();i++)
		{
			cure = (PlayerPropGroupVO)cures.get(i);
			%>
			<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/shortcut.do?cmd=n2")%>">
			<postfield name="sc_pk" value="<%=sc_pk %>" />
			<postfield name="operate_id" value="<%=cure.getPropId() %>" />
			<postfield name="type" value="<%=Shortcut.CURE %>" />
			</go>
			<%= cure.getPropName()%>
			</anchor>
			<%
			if( (i+1)!=cures.size() )
			{
				out.print("<br/>");
			}
		}
	}
	else
	{
		out.print("暂无药品");
	}
 %>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/shortcut.do?cmd=n3")%>">
<postfield name="sc_pk" value="<%=sc_pk %>" />
</go>
返回
</anchor>
<%@ include file="/init/init_time.jsp"%> 
</p>
</card>
</wml>
