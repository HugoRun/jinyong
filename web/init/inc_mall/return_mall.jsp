<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
温馨提示：每成功充值1元可兑换【<%=GameConfig.getYuanbaoName() %>】×1!<br/>
<anchor>返回商城<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mall.do?cmd=n0")%>" ></go></anchor><br/>
<%@ include file="/init/init_timeq.jsp"%>