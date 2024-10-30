<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
import="com.lw.vo.synthesize.SynthesizeVO"
pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="java.util.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
	<% 
	int s_type = Integer.parseInt((String)request.getAttribute("s_type"));
	List list = (List) request.getAttribute("list");
	List namelist = (List) request.getAttribute("namelist");
	int pagenum = Integer.parseInt(request.getAttribute("pagenum").toString());
	int thispage = Integer.parseInt(request.getAttribute("thispage").toString());
	%>
	<%
		for (int i = 0; i < list.size(); i++) {
			SynthesizeVO vo = (SynthesizeVO) list.get(i);
			String name = (String)namelist.get(i);
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="s_type" value="<%=s_type%>" />
	<postfield name="s_id" value="<%=vo.getSynthesizeID()%>" />
	<postfield name="name" value="<%=name%>" />
	<postfield name="minsleight" value="<%=vo.getSynthesizeMinSleight()%>" />
	<postfield name="maxsleight" value="<%=vo.getSynthesizeMaxSleight()%>" />
	</go>
	<%= name%>
	(<%= vo.getSynthesizeMaxSleight()%>)生产
	</anchor>
	<br/>
	<%
		}
	%>
	<%
		if( thispage != pagenum )
		{
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>"> 
	<postfield name="cmd" value="n2" />
	<postfield name="thispage" value="<%=thispage+1%>" />
	<postfield name="s_type" value="<%=s_type%>" />
	</go>
	下一页
	</anchor>
	<%
		}
		if(thispage !=1 )
		{
	%> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>"> 
	<postfield name="cmd" value="n2" />
	<postfield name="thispage" value="<%=thispage-1%>" />
	<postfield name="s_type" value="<%=s_type%>" />
	</go>
	上一页
	</anchor>
	<%
		}
	 %>
	 <br/>
	 <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	返回
	</anchor> 
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
