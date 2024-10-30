<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" import="java.util.*,com.ls.pub.config.GameConfig,com.ls.pub.util.*,com.dp.vo.newbook.*,com.dp.vo.infovo.*,com.dp.vo.bzjvo.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
	<card id="map" title="<s:message key = "gamename"/>">
	  	<p> 
	  		友情提示:<br/>
	  		<%
	  		   String message=request.getAttribute("mess").toString();
	  		   if(message!=null){
	  		   		%>
	  		   			<%=message%>
	  		   		<%
	  		   }
	  		 %><br/>
	  	     <%@ include file="/init/init_time.jsp"%>
	   	</p>
    </card>
</wml>
