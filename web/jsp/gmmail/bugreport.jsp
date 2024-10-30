<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="ID" title="<s:message key = "gamename"/>">
<p>
尊贵的洪荒玩家您好！<br/>
非常感谢您能提出在游戏中遇到的BUG,提交的时候尽可能详细的描述BUG的情况<br/>方便洪荒团队可有效及时的修正您提出的BUG,给广大玩家提供一个流畅刺激的洪荒世界！<br/>
为了感谢您提供有效的BUG信息会有奖励机制,详情请查看活动信息！<br/>
请输入您反馈的BUG信息(500字以内):<br/>
内容:<input name="content" type="text" /><br/>
<anchor>提交
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/gmmail.do?cmd=n3")%>">
<postfield name="content" value="$(content)" />
</go>
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>