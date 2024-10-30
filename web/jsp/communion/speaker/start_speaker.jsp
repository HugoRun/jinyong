<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.pm.vo.sysInfo.SystemInfoVO,com.ls.pub.util.*" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">	
<p>
	<%String pg_pk = (String)request.getAttribute("pg_pk"); 
	String hint = (String)request.getAttribute("hint");
	if(hint != null && !hint.equals("")) {
		out.println(hint+"<br/>");
	}%>
请输入您的喊话内容:<br/> 
<input name="content" type="text"  format="*M" maxlength="19" /><br/>
<anchor>
<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/speaker.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="content" value="$content"/>
<postfield name="pg_pk" value="<%=pg_pk %>"/>
</go>
确定
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
