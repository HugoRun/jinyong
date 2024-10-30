<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.model.user.SettingInfo,com.ls.pub.constant.SettingType,com.ls.pub.util.ExchangeUtil" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<%
	SettingInfo settingInfo = (SettingInfo)request.getAttribute("settingInfo");
%>
<card id="setting" title="<s:message key = "gamename"/>">
<p>
聊天设置<br/>
公共聊天频道
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do")%>">
<postfield name="cmd" value="n5"/>
<postfield name="type" value="<%=SettingType.PUBLICCHAT %>" />
</go>
<%=ExchangeUtil.exchangeSwitch(settingInfo.getPublicChat()) %>
</anchor><br/>
阵营聊天频道
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do")%>">
<postfield name="cmd" value="n5"/>
<postfield name="type" value="<%=SettingType.CAMPCHAT %>" />
</go>
<%=ExchangeUtil.exchangeSwitch(settingInfo.getCampChat()) %>
</anchor><br/>
帮派聊天频道
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do")%>">
<postfield name="cmd" value="n5"/>
<postfield name="type" value="<%=SettingType.TONGCHAT %>" />
</go>
<%=ExchangeUtil.exchangeSwitch(settingInfo.getTongChat()) %>
</anchor><br/>
组队聊天频道
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do")%>">
<postfield name="cmd" value="n5"/>
<postfield name="type" value="<%=SettingType.DUIWUCHAT %>" />
</go>
<%=ExchangeUtil.exchangeSwitch(settingInfo.getDuiwuChat()) %>
</anchor><br/>
密语聊天频道
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do")%>">
<postfield name="cmd" value="n5"/>
<postfield name="type" value="<%=SettingType.SECRETCHAT %>" />
</go>
<%=ExchangeUtil.exchangeSwitch(settingInfo.getSecretChat()) %>
</anchor><br/>
首页聊天快捷输入栏
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do")%>">
<postfield name="cmd" value="n5"/>
<postfield name="type" value="<%=SettingType.INDEXCHAT %>" />
</go>
<%=ExchangeUtil.exchangeSwitch(settingInfo.getIndexChat()) %>
</anchor><br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=index")%>"></go>返回上级</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
