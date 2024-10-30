<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" language="java" errorPage="" %>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="index" title="骏网一卡通充值方式">
<p>
骏网一卡通充值卡充值【<%=GameConfig.getYuanbaoName() %>】快速通道<br/>
您可以使用面值为5、6、10、15、30、50、60、100元骏网一卡通充值卡为本游戏帐号充值。<br/>
请选择您要充值的骏网一卡通充值卡面值:<br/>
<anchor>
<go href="<%=response.encodeURL("cooperate/dl/bill/jun/input.jsp") %>" method="post">
<postfield name="money" value="5"/>
</go>5元
</anchor>
<anchor>
<go href="<%=response.encodeURL("cooperate/dl/bill/jun/input.jsp") %>" method="post">
<postfield name="money" value="6"/>
</go>6元
</anchor>
<anchor>
<go href="<%=response.encodeURL("cooperate/dl/bill/jun/input.jsp") %>" method="post">
<postfield name="money" value="10"/>
</go>10元
</anchor>
<anchor>
<go href="<%=response.encodeURL("cooperate/dl/bill/jun/input.jsp") %>" method="post">
<postfield name="money" value="15"/>
</go>15元
</anchor>
<anchor>
<go href="<%=response.encodeURL("cooperate/dl/bill/jun/input.jsp") %>" method="post">
<postfield name="money" value="30"/>
</go>30元
</anchor>
<anchor>
<go href="<%=response.encodeURL("cooperate/dl/bill/jun/input.jsp") %>" method="post">
<postfield name="money" value="50"/>
</go>50元
</anchor>
<anchor>
<go href="<%=response.encodeURL("cooperate/dl/bill/jun/input.jsp") %>" method="post">
<postfield name="money" value="60"/>
</go>60元
</anchor>
<anchor>
<go href="<%=response.encodeURL("cooperate/dl/bill/jun/input.jsp") %>" method="post">
<postfield name="money" value="100"/>
</go>100元
</anchor><br/>
 提示:请选择与充值卡面额一致的充值类型,否则将充值失败,系统不予补偿!<br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>

