<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" import="java.util.*,com.ls.pub.config.GameConfig,com.ls.pub.util.*,com.dp.vo.newbook.*,com.dp.vo.infovo.*,com.dp.vo.bzjvo.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
	<card id="map" title="<bean:message key="gamename"/>">
	  	<p> 
	  		<%
	  		   String message=request.getAttribute("mess").toString();
	  		   if(message!=null){
	  		   		%>
	  		   			<%=message%>
	  		   		<%
	  		   }
	  		 %><br/>
  	      <anchor><go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
 		   <postfield name="cmd" value="n9"/>返回我的书架</go></anchor><br/>
	  	   <anchor>
  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
  	    		<postfield name="cmd" value="n1"/>
  	    	</go>返回书城首页
  	       </anchor><br/>
  	       ----------------------<br/>
  	       报时:<%=new Date().toLocaleString()%>
	   	</p>
    </card>
</wml>