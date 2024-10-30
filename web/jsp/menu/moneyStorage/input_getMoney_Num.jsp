<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.dao.info.partinfo.*"%> 
<%@page import="com.ls.ben.vo.storage.*"%> 
<%@page import="com.ls.ben.dao.storage.*,com.ls.pub.constant.*"%> 
<%@page import="com.ls.web.service.player.*,com.ls.model.user.*" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p> 
	<%
		String uPk = (String)session.getAttribute("uPk");
		String pPk =  (String)session.getAttribute("pPk");
	 	RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			
	 	long partCopper = roleInfo.getBasicInfo().getCopper();
	 	WareHouseDao storageDao = new WareHouseDao();
	 	WareHouseVO wareHouseVO = storageDao.getWareHouseIdBypPk(pPk+"",Wrap.COPPER);
	 	long warehouseMoney = Long.valueOf(wareHouseVO.getUwMoneyNumber());

	 			%>
<%String resultWml = (String)request.getAttribute("resultWml"); %>
<%=resultWml %><br/>
	 			<%-- <br/><br/>
	 			 请输入您要取出的数量:
				<br/>
					 <input name="silver_num"  type="text" emptyok="false" size="10"  maxlength="15" format="5N" />
	 				两
	 				<input name="copper_num"  type="text" emptyok="false" size="10"  maxlength="15" format="5N" />
	 				文
	 				<anchor> 
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getMoneyStorage.do")%>">
					<postfield name="cmd" value="n2" />
					<postfield name="partCopper" value="<%=partCopper%>" />
					 <postfield name="silver_num" value="$silver_num" />
					  <postfield name="copper_num" value="$copper_num" />
					<postfield name="warehouseID" value="<%=request.getAttribute("warehouseID")%>" />
					</go>
					确定
					</anchor> 
	 				<br/> --%>
	 				<anchor> 
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getMoneyStorage.do")%>">
					<postfield name="cmd" value="n1" />
					<postfield name="warehouseID" value="<%=request.getAttribute("warehouseID")%>" />
					</go>
					返回
					</anchor>
	 				<br/>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
	 			