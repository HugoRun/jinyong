<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*, com.ls.ben.vo.info.skill.PlayerSkillVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>   
	<%  
	    
		PlayerSkillVO skill = (PlayerSkillVO)request.getAttribute("skill");
		String next_sleight = (String)request.getAttribute("next_sleight");
	%>
	
	技能名称:<%=skill.getSkName() %><br/>
	技能描述:<%=skill.getSkDisplay() %><br/>
	<%if( skill.getSkType() == 1)
	  {%>
	攻击范围:<%if(skill.getSkArea()==1){%>群体攻击<%}else{%>单体攻击<%} %><br/>
	<%}%>
	<%if( skill.getSkType() == 1)
	  {%>
	伤害:<%= skill.getSkDamageDi()%>-<%=skill.getSkDamageGao() %><br/>
	<%}%>
	<%if( skill.getSkType() == 1)
	{%>
	熟练度:<%=skill.getSkSleight() %><%if( next_sleight!=null && !next_sleight.equals("0") ){%>/<%= next_sleight%><%}
	%>
	<br/>
	<%}%>
	<%if( skill.getSkType() == 3)
	{%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/juexue.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="sk_id" value="<%=skill.getSkId() %>" />
	</go>
	运转
	</anchor>
	<br/>
	<%}%>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n5")%>"></go>返回</anchor>	
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
