<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.pm.vo.question.QuestionVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p> 
<%
	List list = (ArrayList)request.getAttribute("ranklist");
	if( list!=null || list.size() == 0)
	{
		for(int i = 0; i<list.size(); i++){
			QuestionVO vo = (QuestionVO)list.get(i);
%> 
			第<%=i+1 %>名:<%=vo.getPName() %>
			<br/>
<%
	}
	}
	else
	{
		out.print("空指针");
	}
%> 
<br/>
<anchor>
<go  method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/question.do?cmd=n1") %>">
<postfield name="menu_id" value="<%=request.getAttribute("menu_id") %>"/>
</go>
返回
</anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
