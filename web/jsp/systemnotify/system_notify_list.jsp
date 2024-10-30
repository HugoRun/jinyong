<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig,java.util.List,com.ls.ben.vo.info.partinfo.PartInfoVO,com.ls.pub.util.DateUtil,com.lw.vo.systemnotify.SystemNotifyVO" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
<%@ include file="/init/intimatehint.jsp"%><br/>
<%
		List<SystemNotifyVO> notifylist_huodong = (List<SystemNotifyVO>)request.getAttribute("notifylist_huodong");
if(notifylist_huodong !=null && notifylist_huodong.size()>0 ){
%>-----------<br/><%
for(int i = 0;i<notifylist_huodong.size();i++){
SystemNotifyVO vo = notifylist_huodong.get(i);
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sysnotify.do") %>">
<postfield name="cmd" value="n3" />
<postfield name="id" value="<%=vo.getId() %>" />
</go>
<%=vo.getNotifytitle() %>
</anchor>
<br/>
<%
} }%>
-----------<br/>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
