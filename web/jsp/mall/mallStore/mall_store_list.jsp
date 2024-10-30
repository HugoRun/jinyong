<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.List,com.ls.ben.vo.mall.UMallStoreVO,com.ls.pub.bean.QueryPage"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="mall" title="<bean:message key="gamename"/>">
<p>
<%@ include file="/init/system/error_hint.jsp"%>
<%
	QueryPage queryPage= (QueryPage)request.getAttribute("queryPage");	
	List<UMallStoreVO> mall_stores = (List<UMallStoreVO>)queryPage.getResult();	

	if( mall_stores!=null && mall_stores.size()!=0 )
	{
		for( UMallStoreVO mall_store_info:mall_stores )
		{
%>
<%= mall_store_info.getPropName()%>×<%= mall_store_info.getCommodityNum()%>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n7")%>" method="post">
<postfield name="p_id" value="<%=mall_store_info.getPropId()%>"/>
<postfield name="page_no" value="<%= queryPage.getCurrentPageNo()%>"/>
</go>
取出
</anchor><br/>
<%
		}
		if( queryPage.hasNextPage() )
		{
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n4")%>">
<postfield name="page_no" value="<%=queryPage.getCurrentPageNo()+1%>"/>
</go>下一页  
</anchor>
<%
		}
		if( queryPage.hasPreviousPage() )
		{
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n4")%>">
<postfield name="page_no" value="<%=queryPage.getCurrentPageNo()-1%>"/>
</go>上一页  
</anchor>
<%
		}
		if ( queryPage.getCurrentPageNo() == 1 && queryPage.getTotalPageCount() > 2) {	
	 %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n4")%>">
	<postfield name="page_no" value="<%=queryPage.getTotalPageCount() %>"/>
	</go>
	到末页
	</anchor>
	 <%} 
	if ( queryPage.getCurrentPageNo() == queryPage.getTotalPageCount() && queryPage.getTotalPageCount() > 2 ) 
	 {	 %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n4")%>">
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
暂无商品<br/>
<%
	}
%>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n5")%>" method="get"></go>查看记录</anchor><br/>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>
