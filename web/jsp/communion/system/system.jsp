<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.pm.vo.sysInfo.SystemInfoVO,com.ls.ben.dao.info.partinfo.*" %>
<%@page import="com.ls.pub.util.*" %>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>

系统频道<br />
	<anchor>
	<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath())+"/systemcommaction.do?cmd=n1"%>"></go>
	刷新
	</anchor> <br/> 
    <%
    List<SystemInfoVO> sysInfolist = (List)request.getAttribute("list"); 
	if(sysInfolist == null || sysInfolist.size() == 0){}else {
	for(SystemInfoVO infovo:sysInfolist){
	if(infovo.getInfoType() != 4) {
		%>
系统消息:<%=StringUtil.isoToGBK(infovo.getSystemInfo()) %><br/> 
	<%} else {
	PartInfoDao infodao = new PartInfoDao();
	%>
					<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>"> 
	<postfield name="cmd" value="n13" />   
	<postfield name="pPks" value="<%=infovo.getPPk() %>" /> 
	</go>
	<%=infodao.getNameByPpk(infovo.getPPk())%>
	</anchor>大声呐喊:<%=infovo.getSystemInfo()%><br/>
					<%
	}
	}} %>
	<%@ include file="/init/caidan.jsp"%>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
