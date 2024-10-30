<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
	<%@page import="com.ls.ben.vo.map.SceneVO"%>
	<%@page import="com.ls.pub.util.*" %> 
<%
	response.setContentType("text/vnd.wap.wml");
	SceneVO vo = (SceneVO)request.getAttribute("scenevo");
	String mapDisplay = (String)request.getAttribute("mapDisplay"); 
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
[<%=StringUtil.isoToGBK(vo.getSceneName()) %>]<br/>
	<img alt="map" src="<%=GameConfig.getGameUrl()%>/image/map/<%=vo.getScenePhoto() %>.png" />
	<br/>
	<%=StringUtil.isoToGBK(mapDisplay) %>
	<br/>
	<%if(vo.getSceneShang() != 0){ %>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/viewpic.do?cmd=n2")%>">
	<postfield name="mapid" value="<%=vo.getSceneShang() %>" />
	</go>
	上移 
	</anchor>
	<%}else { %>
	上移
	<%}if(vo.getSceneXia() != 0 ){ %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/viewpic.do?cmd=n2")%>">
	<postfield name="mapid" value="<%=vo.getSceneXia() %>" />
	</go>
	下移
	</anchor>
	<%}else { %>
	下移
	<%} %>
	<br/>
	<% if(vo.getSceneZuo() != 0 ){%>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/viewpic.do?cmd=n2")%>">
	<postfield name="mapid" value="<%=vo.getSceneZuo() %>" />
	</go>
	左移 
	</anchor>
	<%}else { %>
	左移
	<%}if(vo.getSceneYou() != 0){ %>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/viewpic.do?cmd=n2")%>">
	<postfield name="mapid" value="<%=vo.getSceneYou() %>" />
	</go>
	右移
	</anchor>
	<%}else { %>
	右移
	<%} %>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
