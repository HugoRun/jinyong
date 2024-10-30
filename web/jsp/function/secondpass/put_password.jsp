<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ls.pub.util.*,com.pub.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		
		String authpara = (String)request.getParameter("auth");
		String getTodayStr = DateUtil.getTodayStr();
		
		String auth = MD5.getMD5Str(getTodayStr).substring(1,6);
		
	if(	auth.equals(authpara)) { 
	 %>
	
	您现在可以修改密码:
	<input name="pass_word"  type="text" size="6"  maxlength="11" format="11M" />
	
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/secondPass.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="pass_word" value="$pass_word" />
	</go>
	传送
	</anchor>
<%} else { %>
	空指针！
<%} %>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
