<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.vo.task.UTaskVO" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	【任务栏】 
	<br/>
	<%
		List list = (List) request.getAttribute("list");
		if(list !=null && list.size()!=0){
		for (int i = 0; i < list.size(); i++) {
			UTaskVO vo = (UTaskVO) list.get(i);
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="tId" value="<%=vo.getTId()%>" />
	<postfield name="tPk" value="<%=vo.getTPk()%>" />
	</go>
	<%=vo.getTTitle()%>
	</anchor>
	<br/>
	<%
		}
		}else{
	%>
	暂无任务<br/>
	<%} %>
	<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do?cmd=n5")%>" ></go>寻找任务</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>