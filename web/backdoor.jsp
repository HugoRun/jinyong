<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<wml>
<card id="login" title="">
<p>
账号:<input name="uName" type="text"  maxlength="11" /><br/>
密码:<input name="uPaw" type="text" maxlength="11" />(5-11位,0-9,a-z组合)<br/>
<anchor>
<go method="post" href="login.do">
<postfield name="name" value="$uName" />
<postfield name="paw" value="$uPaw" />
<postfield name="cmd" value="n10" />
</go>
确定
</anchor>
<anchor><go method="get" href="login.do?cmd=n7"></go>注册</anchor><br/>
</p>
</card>
</wml>
