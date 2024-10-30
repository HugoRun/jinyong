<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
对不起,你的<%=GameConfig.getYuanbaoName() %>不足以购买此道具!<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/sky/bill.do?cmd=n0")%>" method="get"></go>我要充值</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>