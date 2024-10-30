<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="aboute" title="<s:message key = "gamename"/>">
<p>
游戏名：洪荒online<br/>
游戏类型：mmrpg<br/>
公司名称：北京瑞迅宏成信息科技有限公司<br/>
客服电话：021-28501353<br/>
免责声明：本游戏版权归北京瑞迅宏成信息科技有限公司所有，游戏中的文字、图片等内容<br/>为版权所有的个人态度，中国电信对此不承担任何法律责任。<br/>
版本号：hh1.00<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/login.do?cmd=n3")%>" method="get"></go>返回上一级</anchor>
</p>
</card>
</wml>
