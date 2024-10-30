<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.model.user.SettingInfo,com.ls.pub.constant.SettingType,com.ls.pub.util.ExchangeUtil" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>  
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="setting" title="<s:message key = "gamename"/>">
<p>
<%
	SettingInfo settingInfo = (SettingInfo)request.getAttribute("settingInfo");
%> 
物品装备图片
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="type" value="<%=SettingType.GOODSPIC %>" />
</go>
<%=ExchangeUtil.exchangeSwitch(settingInfo.getGoodsPic()) %>
</anchor><br/>
人物图片
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="type" value="<%=SettingType.PERSONPIC %>" />
</go>
<%=ExchangeUtil.exchangeSwitch(settingInfo.getPersonPic()) %>
</anchor><br/>
NPC图片
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="type" value="<%=SettingType.NPCPIC %>" />
</go>
<%=ExchangeUtil.exchangeSwitch(settingInfo.getNpcPic()) %>
</anchor><br/>
坐骑图片
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="type" value="<%=SettingType.PETPIC %>" />
</go>
<%=ExchangeUtil.exchangeSwitch(settingInfo.getPetPic()) %>
</anchor><br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=index")%>"></go>返回上级</anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
