<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ls.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n7" /> 
	</go>
	状态
	</anchor>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n8" /> 
	</go>
	属性
	</anchor>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n9" /> 
	</go>
	装备
	</anchor> 
	<br/> 
	图片
	<br/>
	<%   
		PartInfoVO player = (PartInfoVO)request.getAttribute("player"); 
	%>  
	<%if(player!=null){ %> 
	<%=player.getPName() %>
	<%
		if(player.getPPkValue()>10 && player.getPPkValue()<200 )
		{
			%>(黄)<%
		}
		else if(player.getPPkValue()>200 )
		{
			%>(红)<%
		}
	 %>
	<br/>
	称号:<%=player.getPTitleName()  %>
	<br/>
	等级:<%=player.getPGrade() %>
	<br/>
	性别:<%if(player.getPSex()==1){ %>男<%}else if(player.getPSex()==2){ %>女<%}else if(player.getPSex()==3){ %>人妖<%} %>
	<br/>
	门派:<%=player.getPSchoolName() %> 
	<br/>
	经验值:<%=player.getPExperience() %> /<%=player.getPXiaExperience() %>
	<br/>
	血气值:<%=player.getPHp() %>
	<br/>
	内力值:<%=player.getPMp() %>
	<br/>
	<% 
	if(player.getPHarness()==2){
	 %>
	老婆/老公:<%=player.getPFere() %>
	<br/>
	<%} }%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do")%>">
	<postfield name="cmd" value="n4" /> 
	<postfield name="pPk" value="<%=player.getPPk() %>" /> 
	</go>
	返回
	</anchor> 
	<br/>
	<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
