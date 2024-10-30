<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.skill.SkillVO,com.ls.ben.vo.info.skill.PlayerSkillVO" %>
<%@page import="java.util.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
   <%
	   int type = Integer.parseInt( request.getAttribute("type").toString());
	   int s_type = Integer.parseInt( request.getAttribute("s_type").toString());
    %>
    <%if(type == 1){
   	    List<SkillVO> skill = (List<SkillVO>)request.getAttribute("skills");
    	for(int i = 0; i<skill.size();i++){
    	SkillVO vo = skill.get(i);
    %>
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="type" value="1" />
	<postfield name="sk_id" value="<%=vo.getSkId() %>" />
	<postfield name="sk_name" value="<%=vo.getSkName() %>" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	<%=vo.getSkName() %>
	</anchor>
	<br/>
    <% 
    	}
    %>
    <br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="type" value="2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	返回
	</anchor>
    <%
     }else{
     List<PlayerSkillVO> skill = (List<PlayerSkillVO>)request.getAttribute("skills");
     for(int i = 0; i<skill.size();i++){
     	 PlayerSkillVO vo = skill.get(i);
    %>
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="type" value="2" />
	<postfield name="s_pk" value="<%=vo.getSPk() %>" />
	<postfield name="sk_name" value="<%=vo.getSkName() %>" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	<%=vo.getSkName() %>
	</anchor>
	<br/>
    <%
     	}
    %>
    <br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="type" value="2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	返回
	</anchor>
    <%
     }
    %>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
