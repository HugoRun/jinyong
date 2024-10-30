<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.ben.vo.menu.OperateMenuVO" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p> 
<%
	int previousVictor = (Integer)request.getAttribute("previousVictor");
	OperateMenuVO menuvo = (OperateMenuVO)request.getAttribute("menuvo");
	if( previousVictor == 1 )
	{
%> 
	目前演武场由正道控制，邪道玩家不可进入！
<%
	}else if( previousVictor == 2){%> 
		目前演武场由邪道控制，正道玩家不可进入！
<%	} else { %>
		由于上场战和，所有玩家均不可进入!
<%} %>
 <br/>
 演武会开启时间每天12:30～13:00，21:00～21:30！
  <br/>
 	<anchor>
	<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fieldmanager.do?cmd=n2&amp;menu_id="+menuvo.getId()) %>">
	</go>
 	进入演武场
	 </anchor>

<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
