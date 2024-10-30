<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
	<%
		int p_pk = Integer.parseInt(request.getAttribute("p_pk").toString());
		int pg_pk = Integer.parseInt(request.getAttribute("pg_pk").toString());
		int prop_id = Integer.parseInt(request.getAttribute("prop_id").toString());
		int prop_id1 = Integer.parseInt(request.getAttribute("prop_id1").toString());
		int type = Integer.parseInt(request.getAttribute("type").toString());
		String pg_pk1 = request.getAttribute("pg_pk1").toString();
		String wupinlan = (String)request.getAttribute("wupinlan");
		String page_no = (String)request.getSession().getAttribute("page_no");
	%>
	请输入药品数量:
	<br/>
	<input name="searchsign"  type="text" size="5"  maxlength="5" format="5N" />

	<br/>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
	<postfield name="cmd" value="n18" />
	<postfield name="num" value="$searchsign" />
	<postfield name="p_pk" value="<%=p_pk%>" />
	<postfield name="pg_pk" value="<%=pg_pk%>" />
	<postfield name="pg_pk1" value="<%=pg_pk1%>" />
	<postfield name="prop_id" value="<%=prop_id%>" />
	<postfield name="prop_id1" value="<%=prop_id1%>" />
	<postfield name="type" value="<%=type%>" />
	<postfield name="wupinlan" value="<%=wupinlan%>" />
	</go>
	确定
	</anchor>
	<br/>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
	<postfield name="cmd" value="n16" />
	<postfield name="p_pk" value="<%=p_pk%>" />
	<postfield name="prop_id" value="<%=prop_id%>" />
	<postfield name="prop_id1" value="<%=prop_id1%>" />
	<postfield name="type" value="<%=type%>" />
	<postfield name="wupinlan" value="<%=wupinlan%>" />
	<postfield name="page_no" value="<%=page_no%>" />
	</go>
	返回
	</anchor>



	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
