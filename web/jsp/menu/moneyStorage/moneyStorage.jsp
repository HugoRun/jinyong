<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.dao.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*"%>  
<%@page import="com.ls.web.service.player.*,com.ls.model.user.*" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	<%
		
	%>
	 <%
	 	RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			
	 	long partCopper = roleInfo.getBasicInfo().getCopper();
	 			%>

您目前身上携带银两:<%=MoneyUtil.changeCopperToStr(partCopper) %><br/>
请输入您要存放的数量:<br/>
<input name="copper_num"  type="text"  size="5" maxlength="5"  /><%=GameConfig.getMoneyUnitName() %>
----
<anchor> 
<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/addMoneyStorage.do") %>">
<postfield name="cmd" value="n2" />
<postfield name="partCopper" value="<%=partCopper%>" />
<postfield name="copper_num" value="$copper_num" />
<postfield name="warehouseID" value="<%=request.getAttribute("warehouseID")%>" />
</go>
确定
</anchor><br/>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>