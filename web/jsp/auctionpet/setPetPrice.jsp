<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
<%
	String petPk = request.getParameter("petPk");
%> 
请输入您要拍卖的价格:<br/>
<input name="pet_silver"  type="text" size="5"  maxlength="5" format="5N" />两
<input name="pet_copper"  type="text" size="5"  maxlength="5" format="5N" />文
|<anchor>
	 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetSell.do") %>">
	<postfield name="petPk" value="<%=petPk%>" />
	<postfield name="cmd" value="n3" />
	 <postfield name="pet_silver" value="$pet_silver" />
	 <postfield name="pet_copper" value="$pet_copper" />
	 </go>
		确定
	 </anchor><br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetSell.do?cmd=n1") %>"></go>返回</anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
