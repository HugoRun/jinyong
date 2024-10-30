<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" language="java"%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="index" title="充值确认">
<p>
<%
        String amount = (String)request.getAttribute("pay");
        String pay_cardno = (String)request.getAttribute("code");
        String pay_cardpwd = (String)request.getAttribute("psw");
        String pd_FrpId = (String)request.getAttribute("pd_FrpId");
 %>
您输入了<%= amount%>元面值的神州行移动充值卡<br/>
卡号:<%=pay_cardno%><br/>
密码:<%=pay_cardpwd%><br/>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/req.do")%>" method="post">
<postfield name="pay" value="<%=amount %>"/>
<postfield name="code" value="<%=pay_cardno %>"/>
<postfield name="psw" value="<%=pay_cardpwd %>"/>
<postfield name="pd_FrpId" value="<%=pd_FrpId %>"/>
</go>确定
</anchor><br/>
<anchor>
<go method="post"  href="<%=GameConfig.getContextPath()%>/cooperate/dl/bill/szx/input.jsp">
<postfield name="money" value="<%=amount %>"/>
</go>重新输入
</anchor><br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>