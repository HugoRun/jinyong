<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.effect.PropUseEffect"%>
<%@page import="com.ls.web.service.group.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
	PropUseEffect propUseEffect = (PropUseEffect)request.getAttribute("propUseEffect");
	String hint = (String)request.getAttribute("hint");
	if(propUseEffect.getEffectDisplay() != null ){
	%>
	<%=propUseEffect.getEffectDisplay() %><br/>
	<%
	}
	if(hint != null){
	%>
	<%=hint %>
	<%
	}
	%>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>