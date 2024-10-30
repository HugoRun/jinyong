<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.map.SceneVO"%>
<%String time = (String)request.getAttribute("time"); %>
你已经开始离线挂机，最长挂机时间为<%=time==null?"":time %><br/>

<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/loginout.do?cmd=n1")%>"></go>返回首页</anchor><br/>
<anchor><go href="http://localhost:8080/gp/sky.do?ssid=null" method="get"></go>返回专区</anchor><br/>


<%@ include file="/WEB-INF/inc/footer.jsp"%>
