<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.pub.util.*,java.util.*,com.ben.vo.info.partinfo.PartInfoVO" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
	   List list = (List)request.getAttribute("paiminglist");
	   String untangleType = (String)request.getAttribute("unType");
	   String number = (String)request.getAttribute("number");
	%>
	<%=untangleType %>
	<br/>
	<%
	if(number.equals("n2")){
	for(int i=0;i<list.size();i++){ 
	PartInfoVO vo = (PartInfoVO)list.get(i);
	%>
	第<%=i+1 %>名:<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do?cmd=n3")%>"><postfield name="pPk" value="<%=vo.getPPk()%>" /><postfield name="number" value="<%=number%>" /></go><%=StringUtil.isoToGBK(vo.getPName()) %></anchor><%if(vo.getPTongName().equals("")){%>(无<%}else{%>(<%=vo.getPTongName() %><%} %>
	开黄金宝箱<%=vo.getPBorn() %>次)<br/>
	<%
	}
	}else
	if(number.equals("n1")){
	for(int i=0;i<list.size();i++){ 
	PartInfoVO vo = (PartInfoVO)list.get(i);
	%> 
		第<%=i+1 %>名:<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do?cmd=n3")%>"><postfield name="pPk" value="<%=vo.getPPk()%>" /><postfield name="number" value="<%=number%>" /></go><%=StringUtil.isoToGBK(vo.getPName()) %></anchor><%if (vo.getPCamp() == 1) {%>(正)<%} else if (vo.getPCamp() == 2){%>(邪)<%} else{%>(无)<%} %>
		<br/>
	<%} 
	}else
	if(number.equals("n7")){
	for(int i=0;i<list.size();i++){ 
	PartInfoVO vo = (PartInfoVO)list.get(i);
	%> 
		第<%=i+1 %>名:<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do?cmd=n3")%>"><postfield name="pPk" value="<%=vo.getPPk()%>" /><postfield name="number" value="<%=number%>" /></go><%=StringUtil.isoToGBK(vo.getPName()) %></anchor>
		(<%if (vo.getPCamp() == 1) {%>正<%} else if (vo.getPCamp() == 2){%>邪<%} else{%>无<%} %>
		<%=vo.getKillNum() %>)
		<br/>
	<%} 
	}%>
	<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/function/untangle/zongUnTangle.jsp")%>" ></go>返回</anchor>  
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
