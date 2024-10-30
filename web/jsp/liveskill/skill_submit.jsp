<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.skill.SkillVO"%>
<%@page import="com.ls.ben.vo.info.skill.PlayerSkillVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	<% 
    int id = Integer.parseInt((String) request.getAttribute("id"));
    int type = Integer.parseInt((String) request.getAttribute("type"));
    int s_type = Integer.parseInt((String) request.getAttribute("s_type"));
    String sk_name = (String) request.getAttribute("sk_name");
    String display = (String) request.getAttribute("display");
     %>
	<%
		if (type == 1) {
	%>
	<%=display%>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="sk_id" value="<%=id%>" />
	<postfield name="money" value="500" />
	</go>
	确定
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="type" value="<%=type%>" />
	<postfield name="s_type" value="<%=s_type%>" />
	</go>
	返回
	</anchor>
	<%
		} else {
	%>
	<%=display%>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n4" />
	<postfield name="s_pk" value="<%=id%>" />
	<postfield name="sk_name" value="<%=sk_name%>" />
	</go>
	确定
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="type" value="<%=type%>" />
	<postfield name="s_type" value="<%=s_type%>" />
	</go>
	返回
	</anchor>
	<%
		}
	%>

	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
