<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.ShortcutVO,com.ls.pub.util.StringUtil"%>
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="ID" title="<s:message key = "gamename"/>">
<p>
<%
	List shortcuts = (List)request.getAttribute("shortcuts");
	ShortcutVO shortcut = null;
	if(shortcuts==null )
	{
		out.print("参数为空");
	}
 %>
将物品或者技能设置为快捷方式后可在战斗过程中直接使用。<br/>
当前快捷栏（点击快捷栏可更改当前快捷栏设置）<br/>
<%
	for(int i=0;i<shortcuts.size();i++)
	{
		shortcut = (ShortcutVO)shortcuts.get(i);
		%>
		<anchor>
		<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/shortcut.do?cmd=n3")%>">
		<postfield name="sc_pk" value="<%=shortcut.getScPk() %>" />
		</go>
		<%= StringUtil.isoToGB(shortcut.getScDisplay())%><br/>
		</anchor>
		<%
	}
 %>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/shortcut.do?cmd=n4")%>"></go>清空快捷栏</anchor><br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=index")%>"></go>返回</anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
