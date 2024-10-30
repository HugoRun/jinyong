<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*" %>

<%
	response.setContentType("text/vnd.wap.wml");
			RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
	
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p> 
    请选择您的师门:
    <br/>
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/task/visitlead/visit_lead_ok.jsp")%>">
	<postfield name="type" value="1" />
	</go> 
	丐帮(天下第一大帮,生命力极强,故门派特性偏于生存)
	</anchor>  
	<br/>
	<%if(roleInfo.getBasicInfo().getSex()==1){
	 %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/task/visitlead/visit_lead_ok.jsp")%>">
	<postfield name="type" value="2" />
	</go> 
	少林(武林泰山,重以德服人,故门派特性偏于防守)
	</anchor> 
	<br/>
	<%} %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/task/visitlead/visit_lead_ok.jsp")%>">
	<postfield name="type" value="3" />
	</go> 
	明教(源于波斯,侵略性极重,故门派特性偏于进攻)
	</anchor> 
	<br/>
	<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
