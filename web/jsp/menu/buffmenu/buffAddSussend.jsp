<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.pub.util.*,com.ls.ben.vo.info.buff.BuffVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p> 
<%
	BuffVO buffvo = (BuffVO)request.getAttribute("buffvo");
 %> <br/>
 
 您增加了有益状态,效果为<%=StringUtil.isoToGBK(buffvo.getBuffDisplay()) %>,持续时间为
 
 <%if(buffvo.getBuffTime() != 0) {%>
 	<%=buffvo.getBuffTime() %>分钟.
 <%} else { %>
 	<%=buffvo.getBuffBout() %>回合.
 <%} %>	
 <br/>
<%@ include file="/init/init_time.jsp"%>  
</p> 
</card>
</wml>
 