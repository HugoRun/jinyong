<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p> 
<%
	String pg_pk = (String)request.getAttribute("pg_pk");
	String w_type = (String)request.getAttribute("w_type");
	String resultWml = (String)request.getAttribute("resultWml");
	String pageNo = (String)request.getAttribute("page_no");
	if( resultWml!=null )
	{
%> 
<%= resultWml %><br/>
<%
	}
 %> 
 请输入您要储存的物品数量:<br/>
<input name="prop_num" type="text" format="*N" size="5"/>
	 <anchor>
	 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do") %>">
	 <postfield name="cmd" value="n2" />
	  <postfield name="w_type" value="<%=w_type %>" />
	 <postfield name="pg_pk" value="<%=pg_pk %>" />
	 <postfield name="prop_num" value="$prop_num" />
	 <postfield name="pageNo" value="<%=pageNo %>" />
	 </go>
	储存
	 </anchor><br/>
	 <anchor>
	 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do") %>">
	 <postfield name="cmd" value="n2" />
	  <postfield name="w_type" value="<%=w_type %>" />
	 <postfield name="pg_pk" value="<%=pg_pk %>" />
	 <postfield name="prop_num" value="all" />
	 <postfield name="pageNo" value="<%=pageNo %>" />
	 </go>
	全部储存
	 </anchor><br/>
 <anchor>
 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do?cmd=n1") %>">
 <postfield name="pageNo" value="<%=pageNo %>" />
 <postfield name="w_type" value="<%=w_type %>" />
 </go>
 返回
 </anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>