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
	  		友情提示:<br/>
	  		----------------------<br/>
	  		<%
	  		   Integer sign=Integer.parseInt(request.getAttribute("sign")+"");
	  		   String message=request.getAttribute("message").toString();
	  		   String copmess=request.getAttribute("copmess").toString();
	  		   if(sign==1){
	  		   	   %>
	  		   	   <%=copmess%><br/>
	  		   	   <%=message%><br/>
	  		   	   <anchor><go method="post" href="<%=GameConfig.getContextPath()%>/bookMenu.do">
		 		   <postfield name="cmd" value="n18"/>
		 		   <postfield name="sign" value="1"/>
		 		   </go>返回</anchor><br/>
	  		   	 <%}else{%>
	  		       <%=copmess%><br/>
	  		   	   <%=message%><br/>
	  		   	   <anchor><go method="post" href="<%=GameConfig.getContextPath()%>/bookMenu.do">
		 		   <postfield name="cmd" value="n18"/><postfield name="sign" value="2"/></go>确定</anchor>
		 		   <anchor><go method="post" href="<%=GameConfig.getContextPath()%>/bookMenu.do">
		 		   <postfield name="cmd" value="n18"/><postfield name="sign" value="1"/></go>取消</anchor>
		 		   <br/>
	  		  <%}%>
	  		   ----------------------<br/>
  	       报时:<%=new Date().toLocaleString()%>
	   	</p>
    </card>
</wml>
