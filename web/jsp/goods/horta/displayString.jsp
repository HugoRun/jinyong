<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.util.StringUtil,java.util.*,com.ls.pub.util.*,com.pm.vo.horta.HortaVO"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.web.service.player.RoleService"%>
<%@page import="com.ls.model.vip.Vip"%>
<%@page import="com.ls.model.vip.VipManager"%>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>

<%
	String hor_id = (String)request.getAttribute("hor_id");
	String main_type = (String)request.getAttribute("main_type");
	String lingqu = (String)request.getAttribute("lingqu");
	HortaVO hortaVO = (HortaVO)request.getAttribute("hortaVO");
	RoleService roleService = new RoleService();
	RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
	Vip vip= roleInfo.getTitleSet().getVIP();
	boolean isOneVip=false;
	boolean isTwoVip=false;
	if(vip!=null)
	{
		int vipLevel=vip.getLevel();
		if(vipLevel==VipManager.LEVEL_1)
		{
			isOneVip=true;
		}
		else if(vipLevel==VipManager.LEVEL_2)
		{
			isTwoVip=true;
		}
	}
%>

<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%	
	if ( hortaVO != null ) {
		out.println(hortaVO.getHortaDisplay()+"<br/>");
	}
	
	if ( lingqu != null && lingqu.equals("1")) {
	 %>		 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/horta.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="main_type" value="<%=main_type %>" />
	<postfield name="hor_id" value="<%=hor_id %>" />
	</go>
		领取
	</anchor>
	<br/>
	<%} 
	
		if(hortaVO!=null&&main_type.equals("1")&&lingqu != null && lingqu.equals("0"))
		{
			if(Integer.parseInt(hortaVO.getVipGrade())==VipManager.LEVEL_1&&!isOneVip)
			{
				%>
					<anchor>
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do")%>">
					<postfield name="cmd" value="nomral" />
					<postfield name="type" value="3" />
					</go>
						成为洪荒会员
					</anchor><br/>
				<%
			}
			if(Integer.parseInt(hortaVO.getVipGrade())==VipManager.LEVEL_2&&!isTwoVip)
			{
				%>
					<anchor>
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do")%>">
					<postfield name="cmd" value="nomral" />
					<postfield name="type" value="3" />
					</go>
						成为鸿蒙会员
					</anchor><br/>
				<%
			}
		}
	 %>
	
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/horta.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="main_type" value="<%=main_type %>" />
	</go>
		返回上级
	</anchor>
	<br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
