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
		String petGrade = (String) request.getAttribute("petGrade");
		String petFatigue = (String) request.getAttribute("petFatigue");
		String petIsBring = (String) request.getAttribute("petIsBring");
		String resultWml = (String) request.getAttribute("resultWml");
		if(petIsBring == null || petIsBring.equals("") || petIsBring.equals("null")){
		 petIsBring = "0";
		}
	%>
	<%
		if( resultWml!=null )
		{
			%><%=resultWml%>
	<%
		}
		else
		{
			out.print("宠物信息错误<br/>");
		}
	 %>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/petinfoaction.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="petPk" value="<%=petPk%>" />
	<postfield name="petGrade" value="<%=petGrade %>" />
	<postfield name="petFatigue" value="<%=petFatigue %>" />
	<postfield name="petIsBring" value="<%=petIsBring %>" />
	</go>
	遗弃
	</anchor>
	|
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/petinfoaction.do")%>">
	<postfield name="cmd" value="n4" />
	<postfield name="petPk" value="<%=petPk%>" />
	<postfield name="petGrade" value="<%=petGrade %>" />
	<postfield name="petFatigue" value="<%=petFatigue %>" />
	<postfield name="petIsBring" value="<%=petIsBring %>" />
	</go>
	改名
	</anchor>
	|
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
	<postfield name="cmd" value="n10" />
	<postfield name="w_type" value="1" />
	<postfield name="pet_pk" value="<%=petPk %>" />
	<postfield name="petGrade" value="<%=petGrade %>" />
	<postfield name="petFatigue" value="<%=petFatigue %>" />
	<postfield name="petIsBring" value="<%=petIsBring %>" />
	</go>
	喂养
	</anchor>
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/petinfoaction.do")%>">
	<postfield name="cmd" value="n6" />
	<postfield name="petPk" value="<%=petPk %>" />
	<postfield name="petGrade" value="<%=petGrade %>" />
	<postfield name="petFatigue" value="<%=petFatigue %>" />
	<postfield name="petIsBring" value="<%=petIsBring %>" />
	</go>
	查看技能
	</anchor>
	<br />
	<%if(Integer.parseInt(petIsBring)==1){%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/petbattleaction.do")%>">
	<postfield name="petPk" value="<%=petPk %>" />
	<postfield name="petGrade" value="<%=petGrade %>" />
	<postfield name="petFatigue" value="<%=petFatigue %>" />
	<postfield name="petIsBring" value="0" />
	</go>
	取消参战
	</anchor>
	<%} else {%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/petbattleaction.do")%>">
	<postfield name="petPk" value="<%=petPk %>" />
	<postfield name="petIsBring" value="1" />
	<postfield name="petGrade" value="<%=petGrade %>" />
	<postfield name="petFatigue" value="<%=petFatigue %>" />
	<postfield name="petGrade" value="<%=petGrade %>" />
	<postfield name="petFatigue" value="<%=petFatigue %>" />
	</go>
	参加战斗
	</anchor>
	<%}%>
	<br />
	<anchor>
	<go method="get"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/petinfoaction.do?cmd=n1")%>" ></go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
