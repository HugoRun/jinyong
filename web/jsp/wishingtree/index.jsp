<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.List,com.lw.vo.wishingtree.WishingTreeVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="mall" title="<s:message key = "gamename"/>">
<p>
<%
List<WishingTreeVO> wishing_list_top = (List<WishingTreeVO>)request.getAttribute("list");
if( wishing_list_top!=null && wishing_list_top.size()!=0 )
	{
		for( WishingTreeVO wishing:wishing_list_top )
		{
		%><%=wishing.getName() %>的祝福:<%=wishing.getWishing() %><br/><%
		}
	}	
%>
******************************<br/>
<%@ include file="/jsp/wishingtree/list.jsp"%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wishingtree.do?cmd=write")%>">
</go>
发送祝福
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>