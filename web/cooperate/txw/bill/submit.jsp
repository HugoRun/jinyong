<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.web.service.validate.*"%>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="bill" title="充值">
<p>
<%
RoleService roleService = new RoleService();
RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
String kbamt = request.getParameter("kbamt");
ValidateService validateService = new ValidateService();
String display = validateService.validateNonZeroNegativeIntegers(kbamt);
if(display != null){
%>输入错误,请重新输入!<br/>
请输入蛙元兑换数量:<input name="kbamt" type="text" format="*N" maxlength="3"/>(目前限额最高200蛙元)<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/cooperate/txw/bill/submit.jsp")%>">
<postfield name="kbamt" value="$kbamt" />
</go>
兑换确认
</anchor><br/>
<anchor>
<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/cooperate/txw/bill/index.jsp")%>" ></go>
返回上一级
</anchor><br/>
<%}else{
int wayuan = Integer.parseInt(kbamt)*100;
%><deal value="<%=wayuan %>" pid="<%=roleInfo.getBasicInfo().getUPk() %>" pnum="<%=wayuan %>" pname="<%=GameConfig.getYuanbaoName() %>">确定支付</deal><br/>
<%}%>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>