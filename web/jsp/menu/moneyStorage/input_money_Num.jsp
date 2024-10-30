<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.dao.info.partinfo.*"%> 
<%@page import="com.ls.ben.vo.storage.*"%> 
<%@page import="com.ls.ben.dao.storage.*,com.ls.pub.constant.*"%> 
<%@page import="com.ls.web.service.player.*,com.ls.model.user.*" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p> 
	<%
		String uPk = (String)request.getSession().getAttribute("uPk");
		String pPk =  (String)request.getSession().getAttribute("pPk");

	 	RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			
	 	long partCopper = roleInfo.getBasicInfo().getCopper();
	 	WareHouseDao storageDao = new WareHouseDao();
	 	WareHouseVO wareHouseVO = storageDao.getWareHouseIdBypPk(pPk+"",Wrap.COPPER);
	 	long warehouseMoney = Long.valueOf(wareHouseVO.getUwMoneyNumber());
	 			String resultWml = (String)request.getAttribute("resultWml"); %>
<%=resultWml %><br/>
	 				<anchor> 
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/addMoneyStorage.do")%>">
					<postfield name="cmd" value="n1" />
					<postfield name="partCopper" value="<%=partCopper%>" />
					<postfield name="warehouseID" value="<%=request.getAttribute("warehouseID")%>" />
					</go>
					返回
					</anchor><br/>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
