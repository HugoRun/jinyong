<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.web.service.group.GroupNotifyService"%>
<%@page import="com.ls.ben.vo.info.group.GroupNotifyVO"%>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
<%
	    String pPk =  (String)request.getSession().getAttribute("pPk");
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		GroupNotifyVO groupNotify = groupNotifyService.getGroupNotify( Integer.parseInt(pPk) ); 
		String car = request.getParameter("chair");
		if( groupNotify!=null )
		{
			groupNotifyService.deleteNotify(groupNotify.getNPk());
			if( groupNotify.getNotifyType()==GroupNotifyService.CREATE || groupNotify.getNotifyType()==GroupNotifyService.JOIN || groupNotify.getNotifyType()==GroupNotifyService.INVITE )//组队通知
			{
				//request.setAttribute("notify_type", groupNotify.getNotifyType()+"");
				//request.setAttribute("groupNotify", groupNotify);
				int a_pk = groupNotify.getNotifyedPk();
				int b_pk = groupNotify.getCreateNotifyPk();
				request.getRequestDispatcher("/jsp/group/notify/accept_hint.jsp?notify_type="+groupNotify.getNotifyType()+"&a_pk="+a_pk+"&b_pk="+b_pk+"&chair="+car).forward(request,response);
			}
			else if( groupNotify.getNotifyType()==GroupNotifyService.GROUPHINT ) 
			{
				request.getRequestDispatcher("/jsp/group/notify/group_hint.jsp?chair="+car).forward(request,response);
			}
		} 
	  %>
</p>
</card>
</wml>
