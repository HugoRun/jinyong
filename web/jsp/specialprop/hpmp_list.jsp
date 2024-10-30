<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.lw.vo.specialprop.SpecialPropVO,
com.lw.dao.specialprop.SpecialPropDAO,com.ls.ben.dao.info.partinfo.PlayerPropGroupDao,
com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		int p_pk = Integer.parseInt(request.getAttribute("p_pk").toString());
		int pg_pk1 = Integer.parseInt(request.getAttribute("pg_pk1").toString());
		int prop_id = Integer.parseInt(request.getAttribute("prop_id").toString());
		int pagenum = Integer.parseInt(request.getAttribute("pagenum").toString());
		int thispage = Integer.parseInt(request.getAttribute("thispage").toString());
		List list = (List)request.getAttribute("proplist");
		String wupinlan = (String)request.getAttribute("wupinlan");
		String page_no = (String)request.getSession().getAttribute("page_no");
	%>
	<% 
		
		
		if(list.size() != 0){
		for(int i = 0; i<list.size();i++){
		int h = Integer.parseInt(list.get(i).toString());
		PlayerPropGroupDao pdao = new PlayerPropGroupDao();
		PlayerPropGroupVO pgvo =pdao.getByPgPk(h);
		%> 
		<anchor>
		<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>"> 
		<postfield name="cmd" value="n17" /> 
		<postfield name="p_pk" value="<%=p_pk %>" /> 
		<postfield name="pg_pk" value="<%=h %>" /> 
		<postfield name="pg_pk1" value="<%=pg_pk1 %>" /> 
		<postfield name="prop_id1" value="<%=prop_id %>" /> 
		<postfield name="prop_id" value="<%=pgvo.getPropId() %>" /> 
		<postfield name="type" value="<%=pgvo.getPropType() %>" />
		<postfield name="wupinlan" value="<%=wupinlan %>" /> 
		</go>
		<%=pgvo.getPropName() %>×<%=pgvo.getPropNum() %>
		</anchor>
		<br/>
		<%
		}
		}
		%>
	 <%
		if( thispage != pagenum )
		{
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>"> 
	<postfield name="cmd" value="n16" />
	<postfield name="thispage" value="<%=thispage+1%>" />
	<postfield name="p_pk" value="<%=p_pk %>" /> 
	<postfield name="prop_id" value="<%=prop_id %>" /> 
	<postfield name="wupinlan" value="<%=wupinlan %>" /> 
	</go>
	下一页
	</anchor>
	<%
		}
		if(thispage !=1 )
		{
	%> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>"> 
	<postfield name="cmd" value="n16" />
	<postfield name="thispage" value="<%=thispage-1%>" />
	<postfield name="p_pk" value="<%=p_pk %>" /> 
	<postfield name="prop_id" value="<%=prop_id %>" />
	<postfield name="wupinlan" value="<%=wupinlan %>" /> 
	</go>
	上一页
	</anchor>
	<%
		}
	 if(Integer.parseInt(wupinlan) == 0){%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>"> 
	<postfield name="cmd" value="n1" /> 
	<postfield name="w_type" value="6" /> 
	<postfield name="page_no" value="<%=page_no %>" /> 
	</go>
	返回
	</anchor>
	<%}else{%>
	<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n3")%>" ></go>返回</anchor>
	<%} %>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
