<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<%@page import="com.ls.model.user.RoleEntity,com.ls.web.service.player.RoleService"%>
<%@page import="com.ls.pub.constant.Wrap"%> 
<% 
	   	RoleService roleService1 = new RoleService();
		String pByPk = request.getParameter("pByPk");//被请求人的ID
		String hint = (String) request.getAttribute("hint"); 
		RoleEntity roleInfo1pByuPk = roleService1.getRoleInfoById(pByPk);
%>
您正准备赠送<%=roleInfo1pByuPk.getBasicInfo().getName() %>物品!<br/>
<%@ include file="zensong_menu.jsp"%>
	<% 
		String w_type_a = (String)request.getAttribute("w_type");
		if( w_type_a == null )
		{
			w_type_a = "2";
		}
		if( Integer.parseInt(w_type_a) == Wrap.EQUIP )
		{
		%><%@ include file="zensong_equip_list.jsp"%>
		<%
		} 
		else
		{
		%><%@ include file="zensong_prop_list.jsp"%><%
		}
	%> <br/>
<anchor>返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">
<postfield name="cmd" value="n1" />
<postfield name="pByPk" value="<%=pByPk %>" /> 
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>