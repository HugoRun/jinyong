<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.pub.util.*,java.util.*,com.ls.ben.vo.info.partinfo.PartInfoVO" %>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
<%
 	List equip_list = (List)request.getAttribute("equip_list");
	String number = (String)request.getAttribute("number");
	
	for(int i=0;i<equip_list.size();i++){ 
	PlayerEquipVO equip = (PlayerEquipVO)equip_list.get(i);
	%> 
		第<%=i+1 %>名:
		<anchor>
		<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do?cmd=n5")%>">
		<postfield name="pwPk" value="<%=equip.getPwPk()%>" />
		<postfield name="number" value="<%=number%>" />
		</go>
		<%=equip.getFullName() %>
		</anchor>
		(
		<anchor>
		<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do?cmd=n3")%>">
		<postfield name="pPk" value="<%=equip.getPPk()%>" />
		<postfield name="number" value="<%=number%>" />
		</go>
		<%=StringUtil.isoToGBK(equip.getRoleName()) %>
		</anchor>
		)
		<br/>
	<%} %>
<anchor>返回
<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/function/untangle/zongUnTangle.jsp")%>">
</go>
</anchor>  
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
