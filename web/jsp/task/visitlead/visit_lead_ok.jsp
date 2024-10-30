<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	<%
		String type = request.getParameter("type");
		String title = null;
		String hint = null;
		if(type.equals("1")){
		    title = "丐帮";
		    hint = "丐帮:人多势众，以棍为器，有打狗棍法绝学，武功尤其刚猛！";
		}
		if(type.equals("2")){
		    title = "少林";
		    hint = "少林:以刀为器，有易筋经等绝学，以气脉悠长而著称,可惜只收男弟子！";
		}
		if(type.equals("3")){
		    title = "明教";
		    hint = "明教:源自波斯，以剑为器，有乾坤大挪移等绝学，以诡谲而著称！";
		}
	%>
	您是否要选择<%=title %>:
	<br/>
	<%=hint %>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/task/visitlead/visit_lead_over.jsp")%>"> 
	<postfield name="type" value="<%=type %>" />
	</go>
	确定
	</anchor>
	<br/>
	<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/task/visitlead/visit_lead.jsp")%>" ></go>返回</anchor>
	<br/>
	<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
