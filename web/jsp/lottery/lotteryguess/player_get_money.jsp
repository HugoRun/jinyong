<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import ="java.util.*,com.ls.pub.util.*" %>
<%@page import ="com.lw.dao.lottery.LotteryInfoDao,com.lw.vo.lottery.LotteryInfoVO" %>
<%@page import ="com.ls.ben.vo.goods.prop.PropVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="ID" title="<s:message key = "gamename"/>">
<p><%   
		String lotterydisplay =request.getAttribute("lotterydisplay").toString();
		String prop =request.getAttribute("prop").toString();
		%>
		<%=lotterydisplay %><br/>
		<% if(!prop.equals("") && prop !=null) {%>
		<%= prop %>
		<%} %>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
