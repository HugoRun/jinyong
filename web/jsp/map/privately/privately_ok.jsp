<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.web.service.communion.CommunionService,com.ben.vo.communion.CommunionVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page
	import="com.ls.model.user.*,com.ls.web.service.player.*,com.web.service.communion.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
	    String pByPk = (String)request.getAttribute("pByPk");
		RoleService roleService = new RoleService();
		String backtype = (String)request.getSession().getAttribute("backtype");
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String pPk = roleInfo.getBasicInfo().getPPk() + "";
		String hint = (String) request.getAttribute("hint");
	%>
<%=hint%>
	<%
	    CommunionService communionService = new CommunionService();
		List list = communionService.getOtherWhisper(Integer.parseInt(pPk), 5);
		if (list != null && list.size() != 0) {
		String user_list = "";
			for (int i = 0; i < list.size(); i++) {
				CommunionVO vo = (CommunionVO) list.get(i);
				if (vo.getPPkBy() != Integer.parseInt(pPk)) {
					if (user_list.indexOf(vo.getPPkBy() + "") == -1) {
				%>
				<anchor>
	            <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do") %>">
                <postfield name="cmd" value="n7" />
	            <postfield name="pByPk" value="<%=vo.getPPkBy() %>" /> 
                <postfield name="pByName" value="<%=vo.getPNameBy() %>" /> 
                </go>
                <%=vo.getPNameBy() %>
                </anchor>
				<br/>
				<%
		}
					user_list += vo.getPPkBy() + ",";
				}
			} 
		}
	%>
	<%if (backtype != null&&backtype.contains("n")){%>
	 <anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do?cmd=n3")%>">
	 <postfield name="pPk" value="<%=pByPk%>" />
	 <postfield name="number" value="<%=backtype%>" />
	 </go>
	 返回
	 </anchor>
	 <%}else{%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>"> 
	<postfield name="cmd" value="n13" />   
	<postfield name="pPks" value="<%=pByPk %>" /> 
	</go>
	返回
	</anchor>
	 <%} %>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
