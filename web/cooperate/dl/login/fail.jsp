<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"     pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="fail" title="<s:message key = "gamename"/>">
<p>
用户验证失败!<br/> 
<anchor><go href="http://189hi.cn/plaf/wml/gacs.jw?act=gogame&amp;gameid=10" method="get"></go>返回专区</anchor><br/>
<anchor><go href="http://189hi.cn/plaf/wml/gacs.jw?act=index" method="get"></go>返回189HI.CN</anchor> 
</p>
</card>
</wml>
