<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.List,com.ls.ben.vo.mall.MallLogVO,com.ls.pub.bean.QueryPage"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="mall" title="<s:message key = "gamename"/>">
<p>
<%
	QueryPage queryPage= (QueryPage)request.getAttribute("queryPage");	
	List<MallLogVO> mall_logs = (List<MallLogVO>)queryPage.getResult();	

	if( mall_logs!=null && mall_logs.size()!=0 )
	{
		for( MallLogVO mall_log:mall_logs )
		{
%>
<%= mall_log.getLog()%><br/>
<%
		}
		if( queryPage.hasNextPage() )
		{
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n5")%>">
<postfield name="page_no" value="<%=queryPage.getCurrentPageNo()+1%>"/>
</go>下一页  
</anchor>
<%
		}
		if( queryPage.hasPreviousPage() )
		{
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n5")%>">
<postfield name="page_no" value="<%=queryPage.getCurrentPageNo()-1%>"/>
</go>上一页  
</anchor>
<%
		}
		if ( queryPage.getCurrentPageNo() == 1 && queryPage.getTotalPageCount() > 2) {	
	 %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n5")%>">
	<postfield name="page_no" value="<%=queryPage.getTotalPageCount() %>"/>
	</go>
	到末页
	</anchor>
	 <%} 
	 if ( queryPage.getCurrentPageNo() == queryPage.getTotalPageCount() && queryPage.getTotalPageCount() > 2 ) 
	 {	 %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n5")%>">
	<postfield name="page_no" value="<%= 1%>"/>
	</go>
	到首页
	</anchor>	 
	<%}  %>
第<%= queryPage.getCurrentPageNo()%>/<%=queryPage.getTotalPageCount() %>页
<%
	}
	else
	{
%>
暂无记录<br/>
<%
	}
%>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n4")%>" method="get"></go>返回</anchor><br/>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>
