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
	 String pByName =(String) request.getAttribute("pByName");
	 String pByPk = (String) request.getAttribute("pByPk");
	 String hint = (String) request.getAttribute("hint");
	 %>
	 您准备发信息给<%=pByName %>，请输入您的信息:
	 <br/> 
	 <%if(hint!=null){ %>
	 <%=hint %><br/>
	 <%} %>
	 <input name="title" type="text" />
	 <br/> 
	 <anchor>
	 <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do") %>"> 
	 <postfield name="cmd" value="n5" />
	 <postfield name="pByName" value="<%=pByName %>" />
	 <postfield name="pByPk" value="<%=pByPk %>" />
	 <postfield name="title" value="$title" />
	 </go>
	  确定
	</anchor> 
	 <br/> 
<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do") %>"> 
	<postfield name="cmd" value="n3" />
	<postfield name="pByPk" value="<%=pByPk %>" />
	<postfield name="pByName" value="<%=pByName %>" />
	</go>
	返回
	</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
