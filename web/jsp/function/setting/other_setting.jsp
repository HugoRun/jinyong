<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.model.user.SettingInfo,com.ls.pub.constant.SettingType,com.ls.pub.util.ExchangeUtil" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>  
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="setting" title="<s:message key = "gamename"/>">
<p>
<%
	SettingInfo settingInfo = (SettingInfo)request.getAttribute("settingInfo");
%> 
交易 
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do")%>">
<postfield name="cmd" value="n7" />
<postfield name="type" value="<%=SettingType.DEALCONTROL %>" />
</go>
<%=ExchangeUtil.exchangeSwitch(settingInfo.getDealControl()) %>
</anchor>
<br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=index")%>"></go>返回上级</anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
