<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.lw.vo.instance.InstanceOutVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	<%
	List list = (List)request.getAttribute("outlist");
	for(int i=0;i<list.size();i++){
	InstanceOutVO vo = (InstanceOutVO)list.get(i);
	%>
	<%=vo.getInstanceName() %>
	<%=vo.getInstanceLv() %>级
	<%=vo.getInstanceTime() %>
	<%if(i==list.size()-1){
	}else{%><br/><%
	}
	}
	%>
	<br/>
	<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=index")%>" ></go>返回</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>




