<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.web.service.player.RoleService"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ben.pk.active.PKActiveService"%>
<%@page import="com.ben.pk.active.PKActiveContent"%>
<%
      RoleService roleServices = new RoleService();
      PKActiveService pss=new PKActiveService();
	  RoleEntity roleInfo = roleServices.getRoleInfoBySession(request.getSession());
	  String sceneIds = roleInfo.getBasicInfo().getSceneId();
	  if(sceneIds.equals(PKActiveContent.SCENEID_PK))
	  {
	 	 String roleName=pss.getOherName(roleInfo.getBasicInfo().getPPk());
	  		%>
	您的对手是：<%=roleName%>	  		
	<br/>  		
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/pkactive.do?cmd=n4")%>">
	</go>
	进入比赛
	</anchor><br/>
	 <anchor>
	<go method="post"
		href="/pkactive.do?cmd=n5">
	<postfield name="cmd" value="n5" />
	</go>
	退出比赛
	</anchor>
	<br/>
	  		<%
	  }	
%>
