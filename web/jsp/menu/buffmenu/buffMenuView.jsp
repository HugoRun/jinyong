<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.ben.vo.menu.OperateMenuVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
<%
	OperateMenuVO buffmenuvo = (OperateMenuVO)request.getAttribute("buffmenuvo");
	if( buffmenuvo!=null )
	{
%> 
	<%=buffmenuvo.getMenuDialog() %><br/>
 			<anchor>
				<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/buffMenu.do")%>">
				<postfield name="cmd" value="n2" />
				<postfield name="pPk" value="<%=request.getAttribute("pPk") %>" />
				<postfield name="menu_id" value="<%=buffmenuvo.getId() %>" />
				<postfield name="mapid" value="<%=request.getAttribute("mapid") %>" />
				</go>
				增加状态
			</anchor>
				<br/>
<%
	}
	else
	{
		out.print("空指针");
	}
 %> 


<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
