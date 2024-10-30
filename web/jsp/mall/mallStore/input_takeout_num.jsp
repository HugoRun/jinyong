<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.ben.vo.mall.UMallStoreVO" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<%
	UMallStoreVO mall_store_info = (UMallStoreVO)request.getAttribute("mall_store_info");
	String page_no = (String)request.getAttribute("page_no");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="mall" title="<s:message key = "gamename"/>">
<p>
<%@ include file="/init/system/error_hint.jsp"%>
请输入您要取出<%=mall_store_info.getPropName() %>的数量:<br/>
<input type="text" name="t_num" size="5" maxlength="2"/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n10")%>">
<postfield name="p_id" value="<%=mall_store_info.getPropId()%>"/>
<postfield name="t_num" value="$t_num"/>
<postfield name="page_no" value="<%=page_no%>"/>
</go>确定
</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n4")%>" method="get"></go>返回</anchor><br/>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>