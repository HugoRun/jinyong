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
    String menu_id = (String)request.getAttribute("menu_id");
	String npcshop_id = (String)request.getAttribute("npcshop_id");
	String goods_display = (String)request.getAttribute("goods_display");
	String pageNo = request.getParameter("pageNo");
	if(pageNo == null || pageNo.equals("")){
		pageNo = (String)request.getAttribute("pageNo");
	}
	if( goods_display!=null )
	{ 
%> 
	<%=goods_display%>
	<input name="goods_num"   type="text" size="5"  maxlength="5" format="*N" />
	 <anchor>
	 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/buy.do") %>">
	 <postfield name="cmd" value="n3" />
	 <postfield name="npcshop_id" value="<%=npcshop_id %>" />
	 <postfield name="goods_num" value="$goods_num" />
	 <postfield name="menu_id" value="<%=menu_id %>" />
	 <postfield name="pageNo" value="<%=pageNo %>" />
	 </go>
		购买
	 </anchor>
<%
	}
	else
	{
		out.print("空指针");
	}
 %> 
<br/>
<anchor> 
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/buy.do?cmd=n1") %>">
<postfield name="menu_id" value="<%=menu_id %>" />
<postfield name="pageNo" value="<%=pageNo %>" />
</go>
返回
</anchor> 
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
