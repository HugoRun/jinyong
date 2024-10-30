<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%   
		String petPk = (String) request.getAttribute("petPk");
		String hint = (String) request.getAttribute("hint");
		String petGrade = (String) request.getAttribute("petGrade");
		String petFatigue = (String) request.getAttribute("petFatigue");
		String petIsBring = (String) request.getAttribute("petIsBring");
		String resultWml = (String) request.getAttribute("resultWml");
	%>
	<%
		if( hint!=null )
		{
			%><%=hint%><br />
	<%
		}
	 %>
	<input name="nickname" type="text" maxlength="4" size="6" />
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/petinfoaction.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="petPk" value="<%=petPk%>" />
	<postfield name="pet_nickname" value="$(nickname)" />
	<postfield name="petGrade" value="<%=petGrade %>" />
	<postfield name="petFatigue" value="<%=petFatigue %>" />
	<postfield name="petIsBring" value="<%=petIsBring %>" />
	</go>
	确定
	</anchor>
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/petinfoaction.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="petPk" value="<%=petPk%>" />
	<postfield name="petGrade" value="<%=petGrade %>" />
	<postfield name="petFatigue" value="<%=petFatigue %>" />
	<postfield name="petIsBring" value="<%=petIsBring %>" />
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
