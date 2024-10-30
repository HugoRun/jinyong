<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page  import="com.ls.ben.vo.info.npc.*"%>
<%@page import="com.ls.pub.util.StringUtil"%>
<%@page  import="com.ls.ben.vo.info.partinfo.*"%>
<%@page  import="com.ls.ben.vo.info.pet.*"%>
<%@page  import="com.ls.ben.dao.info.pet.*"%>
<%@page import="java.util.*"%> 
<%@page import="com.pm.vo.sysInfo.*,com.pub.ben.info.*"%> 
<%@page import="com.ls.web.service.player.RoleService"%> 
<%@page import="com.ls.model.user.RoleEntity,com.ls.ben.cache.staticcache.pet.*"%>  

<%
	response.setContentType("text/vnd.wap.wml");
%>
<%
	RoleService roleService = new RoleService();
	RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
 %>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<%
	List npcs = null;
	NpcFighter npc = null;
	Fighter player = null;
	npcs = (List)request.getAttribute("npcs");
	player = (Fighter)request.getAttribute("player");
	String hp_str = (String)request.getAttribute("hp");
	String mp_str = (String)request.getAttribute("mp");
	String name = (String)request.getAttribute("name");
	List<SystemInfoVO> sysInfolist = (List<SystemInfoVO>)request.getAttribute("sysInfoVO");

	int hp = -1;
	int mp = -1;
	String propname = "";
	if(name!=null)
	{
		propname = name;
	}
	if( hp_str!=null  )
	{
		 hp = Integer.parseInt(hp_str);
	}
	if( mp_str!=null  )
	{
		 mp = Integer.parseInt(mp_str);
	}
	List shortcuts = (List)request.getAttribute("shortcuts");
	ShortcutVO shortcut = null; 
	StringBuffer npc_skill_display=new StringBuffer();
	String npc_dead_display="";
	String player_skill_display="";
    PetSkillDao petSkill = new PetSkillDao(); 
    PetSkillCache petSkillCache = new PetSkillCache();
%> 
<% 
  if(roleInfo.getSettingInfo().getAutoAttackSetting().getAttackType() != -1){ 
%>
<card id="act" title="<s:message key = "gamename"/>" ontimer="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do?cmd=n13") %>"><timer value="17"/>  
<p> 
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/unfilter.do?cmd=n1") %>"></go>停止自动战斗</anchor>  
	<%
		if(player.getJuexuedisplay()!=null&&!player.getJuexuedisplay().equals(""))
		{
		%><br/><%=player.getJuexuedisplay() %><%
		}
		if( npcs!=null )
		{
	        player_skill_display = player.getSkillDisplay();
	    }
		%> 
	<br/>您使用了<%=player_skill_display%>
	<%if(sysInfolist != null ) {
		SystemInfoVO  systemInfoVO = null;
		for(int i=0;i<sysInfolist.size();i++) {
			systemInfoVO = sysInfolist.get(i);
			out.println("<br/>"+systemInfoVO.getSystemInfo());
		}
	}
	
	 if(!player_skill_display.equals("捕捉宠物,没有成功")){%>
	 		 
	   		<%--您使用了request.getAttribute("bout_type")攻击--%>
	   		<%} %>
	   		<br/>
	   			<%if( player.getPet()!=null )
	 			{
	 				PetInfoVO pet = player.getPet();
	 				out.print(StringUtil.isoToGB(pet.getPetNickname())+"使用了"+player.getPetSkillDisplay()+"<br/>");
	 			}
	   		 %>
		   	<%if(hp==0){out.print(propname+"的气血使用量为0<br/>");}if(mp==0){out.print(propname+"的内力使用量为0<br/>");} %>
		   	<!--  player.getKillDisplay()   -->   
			 您的气血:<%= player.getPHp() %>/<%= player.getPMaxHp() %>(<%=player.getInjureDisplay() %><% if(player.getJuexueInjure()!=null&&!player.getJuexueInjure().equals("")){%><%=player.getJuexueInjure()%><% } %>)
			 <% 
			if(hp != -1){
			out.print("(+"+hp+")");
			}%>
			 <br/>
			 您的内力:<%= player.getPMp()%>/<%= player.getPMaxMp() %>
			<%
				if(mp != -1){
			    out.print("(-"+player.getExpendMP()+")");
			    out.print("(+"+mp+")");
			    }else{
				    if( player.getExpendMP()!=0 )
					out.print("(-"+player.getExpendMP()+")");}
			 %>
			 <br/>
----------------------<br/> 
				<%
					if(player.getKillDisplay()!=null&&!player.getKillDisplay().equals("null"))
					{
						%>
						<%=player.getKillDisplay() %>
						<%
					}
				 %>

			 <%
	 			StringBuffer temp = new StringBuffer();
	 			if( npcs!=null)
	 			{
		 			for(int i=0;i<npcs.size();i++)
		 			{
		 				npc = (NpcFighter)npcs.get(i);
		 				temp.append(StringUtil.isoToGB(npc.getNpcName())+ ":"+npc.getCurrentHP()+"/"+npc.getNpcHP());
		 				if( npc.getPlayerInjure()!=0 || npc.getPetInjure()!=0 )
		 				{
			 				temp.append("(-"+npc.getPlayerInjure());
			 				if( npc.getPetInjure()!=0 )
			 				{
			 				temp.append("/");
			 				double ii = petSkillCache.getSeveral(player.getPetSkillDisplay());
			 				
			 				if(petSkillCache.getSeveral(player.getPetSkillDisplay())!=1)
			 				    {
			 				    temp.append(npc.getPetInjureOut());
			 				    }else{
			 					temp.append("-"+npc.getPetInjure());
			 				}		 				
			 				}
			 				temp.append(")");
		 				}
		 				temp.append("<br/>");
		 				
		 				npc_skill_display.append(StringUtil.isoToGB(npc.getNpcName())+npc.getSkillName()+"<br/>");
		 			}
		 			out.print(temp.toString());
	 			}
	 		 %>
	 	  
		   	<%
			if( npcs.size()==0 )
			{
			%>
		   		<a href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do?cmd=n2")%>">继续游戏</a><br/>
		   	<%
		   	}
		   	 %> 
	<%
}else{
 %>
<card id="act" title="<s:message key = "gamename"/>">
<p>
    
		<% 
		//是否受到其他人攻击
		String waring = (String)request.getAttribute("attack_warning");
		if( waring!= null )
		{
			out.print( waring+"<br/>" );
		}
		if(player.getJuexuedisplay()!=null&&!player.getJuexuedisplay().equals(""))
		{
		%><%=player.getJuexuedisplay() %><br/><%
		} 
		int j=0;
		for(;j<6;j++)
		{
			shortcut = (ShortcutVO)shortcuts.get(j);
			%>
				<anchor>
					<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do") %>">
					<postfield name="cmd" value="n6" />
					<postfield name="p_pk" value="<%=shortcut.getPPk() %>" />
					<postfield name="sc_pk" value="<%=shortcut.getScPk() %>" />
					</go>
					<%if(StringUtil.isoToGB(shortcut.getScDisplay()).length() >3 ){ %>
					<%=StringUtil.isoToGB(shortcut.getScDisplay()).substring(0,3) %> 
					<%}else{ %>
					<%=StringUtil.isoToGB(shortcut.getScDisplay()) %> 
					<%} %>
				</anchor>
			<%
		if(j!=2&&j!=5)
		{
	%>|<%
		}
		else
		{
		%><br/><%
		}
	}
	%>
			 您的气血:<%= player.getPHp() %>/<%= player.getPMaxHp() %>(<%=player.getInjureDisplay() %><% if(player.getJuexueInjure()!=null&&!player.getJuexueInjure().equals("")){%><%=player.getJuexueInjure()%><% } %>)
			 <% 
			if(hp != -1){
			out.print("(+"+hp+")");
			}%>
			 <br/>
			 您的内力:<%= player.getPMp()%>/<%= player.getPMaxMp() %>
			<%
				if(mp != -1){
			    out.print("(-"+player.getExpendMP()+")");
			    out.print("(+"+mp+")");
			    }else{
				    if( player.getExpendMP()!=0 )
					out.print("(-"+player.getExpendMP()+")");}
			 %>
			 <br/>
			  <%
		if( npcs!=null )
		{
	        player_skill_display = player.getSkillDisplay();
	    }
		%> 
您使用了<%=player_skill_display%><br/>
----------------------<br/>
			 <%
			 if(player.getKillDisplay() != null){
			 %>
			 <%=player.getKillDisplay() %>
			 <% } %>
			 <%
			    
	 			StringBuffer temp = new StringBuffer();
	 			if( npcs!=null)
	 			{
	 			   
		 			for(int i=0;i<npcs.size();i++)
		 			{
		 				npc = (NpcFighter)npcs.get(i);
		 				temp.append(StringUtil.isoToGB(npc.getNpcName())+ ":"+npc.getCurrentHP()+"/"+npc.getNpcHP());
		 				if( npc.getPlayerInjure()!=0 || npc.getPetInjure()!=0 )
		 				{
			 				temp.append("(-"+npc.getPlayerInjure());
			 				if( npc.getPetInjure()!=0 )
			 				{
			 				temp.append("/");
			 				double ii = petSkillCache.getSeveral(player.getPetSkillDisplay());
			 				
			 				if(petSkillCache.getSeveral(player.getPetSkillDisplay())!=1)
			 				    {
			 				    temp.append(npc.getPetInjureOut());
			 				    }else{
			 					temp.append("-"+npc.getPetInjure());
			 				}
			 				}
			 				temp.append(")");
		 				}
		 				temp.append("<br/>");
		 				
		 				npc_skill_display.append(StringUtil.isoToGB(npc.getNpcName())+npc.getSkillName()+"<br/>");
		 			}
		 			out.print(temp.toString());
	 			}
	 		 %>
	 	 
		   	<%
			if( npcs.size()==0 )
			{
			%>
		   		<a href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do?cmd=n2")%>">继续游戏</a><br/>
		   	<%
		   	} 
	 %><br/>
	 <%if(hp==0){out.print(propname+"的气血使用量为0<br/>");}if(mp==0){out.print(propname+"的内力使用量为0<br/>");} %>
	 <!-- player.getKillDisplay() -->
	 <%
	 if(sysInfolist != null ) {
		SystemInfoVO  systemInfoVO = null;
		for(int i=0;i<sysInfolist.size();i++) {
			systemInfoVO = sysInfolist.get(i);
			out.println(systemInfoVO.getSystemInfo());
		}
	}
	 
	 if(!player_skill_display.equals("捕捉宠物,没有成功")){%>
	 		 
	   		<%--您使用了=request.getAttribute("bout_type") 攻击--%>
	   		<%} %>
	   			<%if( player.getPet()!=null )
	 			{
	 				PetInfoVO pet = player.getPet();
	 				out.print(StringUtil.isoToGB(pet.getPetNickname())+"使用了"+player.getPetSkillDisplay()+"<br/>");
	 			}
	   		 %>
	 	<%=npc_skill_display.toString() %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do?cmd=n1")%>">
	<postfield name="daguai" value="1" />
	</go>
	快捷键设置
	</anchor> 
	<br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do?cmd=n10") %>"></go>自动战斗</anchor><br/>
	<%} %>
<%@ include file="/init/init_timeq.jsp"%>
</p>			
</card> 
</wml>