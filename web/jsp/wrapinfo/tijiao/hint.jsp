<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"  "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*"%>
<%@page import="com.ls.pub.bean.*"%>
<%@page import="java.util.*,com.ben.vo.info.partinfo.PartInfoVO"%>
<%@page import="com.ls.ben.cache.staticcache.prop.PropCache"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	
	<%
		// 此处也被装备显示调用.不用轻易删除
		String hint = (String)request.getAttribute("hint");
		
		if (hint != null && !hint.equals("")) 
				out.println(hint+"<br/>");
	%>
	
	
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
