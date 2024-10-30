<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.util.StringUtil,java.util.*,com.ls.pub.util.*,com.pm.vo.horta.HortaVO"%>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>

<%
	String hint = (String)request.getAttribute("hint");
	String main_type = (String)request.getAttribute("main_type");
	List<HortaVO> ableList = (List<HortaVO>)request.getAttribute("ableList");
	List<HortaVO> unAbleList = (List<HortaVO>)request.getAttribute("unAbleList");
	
	HortaVO hortaVO = null;
	if ( hint != null && !hint.equals("") && !hint.equals("null")) {
		out.println(hint+"<br/>");
	}
%>

<card id="login" title="<bean:message key="gamename"/>">
<p> 
	<%	
	if("1".equals(main_type))
	{
		%>
		 【会员奖励】<br/>
		<%
	}
	else if ("2".equals(main_type))
	{
		%>
		 【等级奖励】<br/>
		<%
	}
	else 
	{
		%>
		 【在线奖励】<br/>
		<%
	}
	 %>	
	
	 可领取:<br/>
	<%
		if (ableList != null && ableList.size() != 0) {
			for (int i = 0; i < ableList.size(); i++) {
				hortaVO = (HortaVO) ableList.get(i);
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/horta.do")%>">
	<postfield name="cmd" value="n4" />
	<postfield name="hor_id" value="<%=hortaVO.getHortaId()%>" />
	<postfield name="main_type" value="<%=main_type%>" />
	<postfield name="lingqu" value="1" />
	</go>
	<%=StringUtil.isoToGB(hortaVO.getHorta_sonName())%>
	</anchor>
	<br/>
	<%
		}} else {out.println("无<br/>");}%> 
		
		
		不可领取:<br/>
		<%
		if (unAbleList != null && unAbleList.size() != 0) {
			for (int i = 0; i < unAbleList.size(); i++) {
				hortaVO = (HortaVO) unAbleList.get(i);
				String diaplay = "";
				if ( hortaVO.getDisplay() != null && !hortaVO.getDisplay().equals("") && !hortaVO.getDisplay().equals("null")) {
					diaplay = "("+hortaVO.getDisplay()+")";
				}
				%> 
				<anchor>
				<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/horta.do")%>">
				<postfield name="cmd" value="n4" />
				<postfield name="hor_id" value="<%=hortaVO.getHortaId()%>" />
				<postfield name="main_type" value="<%=main_type %>" />
				<postfield name="lingqu" value="0" />
				</go>
					<%=StringUtil.isoToGB(hortaVO.getHorta_sonName())%><%=diaplay %>
				</anchor>
				<br/>
		<%} }
		else
		{
			out.println( "无<br/>" );
		}
	%>
	<br/>
	<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/horta.do?cmd=n1")%>" method="get"></go>返回</anchor>
	
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
