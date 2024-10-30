<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
     <%
     // 这里的upk和pPk ，请不要删除。
     String uPk = (String)request.getAttribute("uPk");
     String pPk = (String)request.getAttribute("pPk");
     String pName = (String)request.getAttribute("pName");
     %>
	 您确定要恢复角色:<%=pName %> ?
	 <br/>
	 <anchor>
	 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/role.do") %>">
	 <postfield name="cmd" value="n5" />
	 <postfield name="uPk" value="<%=uPk %>" />
	 <postfield name="pPk" value="<%=pPk %>" /> 
	 </go>	
      确定
	 </anchor> 
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3")%>"></go>取消</anchor><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3") %>">
<postfield name="uPk" value="<%=uPk %>" />
</go>返回首页</anchor>
</p>
</card>
</wml>
	