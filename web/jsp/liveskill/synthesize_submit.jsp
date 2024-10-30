<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
	<% 
		int s_type = Integer.parseInt(request.getAttribute("s_type").toString());
		String name = request.getAttribute("name").toString();
		String propinfo = request.getAttribute("propinfo").toString();
		String s_id = request.getAttribute("s_id").toString();
		String num = request.getAttribute("num").toString();
		List proplist = (List)request.getAttribute("proplist");
		List playerproplist = (List)request.getAttribute("playerproplist");
		String minsleight = request.getAttribute("minsleight").toString();
		String maxsleight = request.getAttribute("maxsleight").toString();
	%>
	<%=name %>:<%=propinfo %>.
	<br/>
	生产需要:
	<% for( int i=0;i<proplist.size();i++ ){
		if( proplist!=null && proplist.size()!=0 ){
			String article = (String) proplist.get(i);
			String[] articles = article.split("-");
			for(int a=1;a < articles.length;a++){ %>
			<%=articles[a] %>
			<%if(a+1 != articles.length){ %>
			,<%}}} }%><br/>
	熟练度要求<%=minsleight %>以上,最高可提升技能熟练度到<%=maxsleight %>.<br/>
	您现在有
	<%for( int i=0;i<playerproplist.size();i++ ){
		if( proplist!=null && playerproplist.size()!=0 ){
			String article = (String) playerproplist.get(i);
			String[] articles = article.split("-");
			for(int a=1;a < articles.length;a++){ %>
			<%=articles[a] %>
			<%if(a+1 != articles.length){ %>
			,<%}}} }%>,
	最多可以制作<%=name %>×<%=num %>!<br/>
	生产
	<input name="searchsign"  type="text" size="5"  maxlength="5" format="5N" />
	个
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n4" />
	<postfield name="num" value="$searchsign" />
	<postfield name="s_id" value="<%=s_id %>" />
	<postfield name="num" value="<%=num %>" />
	<postfield name="s_type" value="<%=s_type %>" />
	<postfield name="maxsleight" value="<%=maxsleight %>" />
	<postfield name="address" value="0" />
	</go>
	确定
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
