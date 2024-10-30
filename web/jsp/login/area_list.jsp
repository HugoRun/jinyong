<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
登录后将此页面保存为书签，下次点击书签就可直接登录游戏了！<br/>
请选择分区:<br/> 
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3")%>" method="get"></go>屠龙区（火热）</anchor><br/>
<br/> 
<anchor>
<go href="http://189hi.cn/plaf/wml/gacs.jw?act=gogame&amp;gameid=10" method="get"></go>
返回专区
</anchor><br/>
<anchor>
<go href="http://189hi.cn/plaf/wml/gacs.jw?act=index" method="get"></go>
返回189HI.CN
</anchor> 
</p>
</card>
</wml>
