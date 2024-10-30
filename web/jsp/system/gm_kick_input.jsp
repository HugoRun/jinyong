<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>   
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="message" title="<bean:message key="gamename"/>">
<p>
输入玩家名称:<br/>
<input name="p_name" type="text" /><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/gmkick.do")%>" >
<postfield name="cmd" value="n2" />
<postfield name="p_name" value="$p_name" />
</go>
确定
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
