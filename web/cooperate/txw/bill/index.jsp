<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.constant.Channel"%>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="bill" title="充值">
<p>
<%@ include file="/init/system/error_hint.jsp"%>
<%
RoleService roleService = new RoleService();
RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
%>
【<%=GameConfig.getYuanbaoName() %>充值服务】<br/>
<deal value="500" pid="<%=roleInfo.getBasicInfo().getUPk() %>" pnum="500" pname="元宝">兑换5蛙元</deal><br/>
<deal value="1000" pid="<%=roleInfo.getBasicInfo().getUPk() %>" pnum="1000" pname="元宝">兑换10蛙元</deal><br/>
<deal value="1500" pid="<%=roleInfo.getBasicInfo().getUPk() %>" pnum="1500" pname="元宝">兑换15蛙元</deal><br/>
-------------------<br/>
手动兑换<br/>
请输入蛙元兑换数量:<input name="kbamt" type="text" format="*N" maxlength="3"/>(目前限额最高200蛙元)<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/cooperate/txw/bill/submit.jsp")%>">
<postfield name="kbamt" value="$kbamt" />
</go>
兑换确认
</anchor><br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>