<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.pm.vo.auction.*"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*"%>
<%@page import="com.ls.pub.bean.*,com.pm.vo.auction.*"%>
<%@page import="com.pm.vo.constant.*"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.ben.cache.dynamic.manual.user.RoleCache"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
	String hint = (String)request.getAttribute("hint");
	if (hint != null){
	%>
	<%=hint %>
	<%} %>
	<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mail.do?cmd=n1") %>" method="get"></go>返回</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
