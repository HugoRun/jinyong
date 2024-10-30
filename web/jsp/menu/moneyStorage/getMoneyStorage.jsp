<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.dao.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*,com.ls.pub.constant.*"%>
<%@page import="com.ls.ben.vo.storage.*"%> 
<%@page import="com.ls.ben.dao.storage.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	<%
		String pPk =  (String)session.getAttribute("pPk");
	 	
	 	WareHouseDao storageDao = new WareHouseDao();
	 	WareHouseVO wareHouseVO = storageDao.getWareHouseIdBypPk(pPk+"",Wrap.COPPER);
	 	long warehouseMoney = Long.valueOf(wareHouseVO.getUwMoneyNumber());
	 			%>
您现在仓库中存放的金钱是:<%=MoneyUtil.changeCopperToStr(warehouseMoney+"") %><br/>
请输入您要取出的数量:<br/>
<input name="copper_num"  type="text"   maxlength="5" format="5N" /><%=GameConfig.getMoneyUnitName() %>
----
<anchor>
<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getMoneyStorage.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="copper_num" value="$copper_num" />
</go>
确定
</anchor><br/>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>