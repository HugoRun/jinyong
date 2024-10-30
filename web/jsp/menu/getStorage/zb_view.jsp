<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"  pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %> 
<card id="login" title="<s:message key = "gamename"/>">
<p>
<%
		String equip_display = (String)request.getAttribute("equip_display");
		
		if( equip_display != null && !equip_display.equals("") ) {
			out.print(equip_display);
		}else { %>
			无
<%} %>	
<br/>
	
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getStorage.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="WPk" value="<%=request.getAttribute("pwPk") %>" />
	<postfield name="w_type" value="3" />
	<postfield name="page_no" value="<%=request.getAttribute("page_no") %>" />
	</go>
	取出
	</anchor>
<br/>
<anchor>返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getStorage.do?cmd=n1") %>">
<postfield name="w_type" value="3" />
<postfield name="page_no" value="<%=request.getAttribute("page_no")  %>" />
</go>
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
