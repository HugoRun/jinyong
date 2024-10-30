<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ls.ben.vo.info.skill.PlayerSkillVO" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>   
	<%  
	    
		List<PlayerSkillVO> skills = (List)request.getAttribute("skills");
		
	%>
<%@ include file="part_info_menu_head.jsp"%>
	<%
		if( skills!=null && skills.size()>0 )
		{
			for(PlayerSkillVO skill:skills )
			{
				%>
				<anchor>
				<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n6")%>">
				<postfield name="sk_id" value="<%=skill.getSkId() %>" />
				</go>
				<%=skill.getSkName() %>
				</anchor><br/>
				<%
			}
		}
		else
		{
			out.print("您还没有学习技能<br/>");
		}
	%> 
<%@ include file="part_info_menu_foot.jsp"%>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
