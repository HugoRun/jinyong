<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.attack.*"%>
<%@page import="com.ls.ben.vo.info.attack.DropGoodsVO"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.StringUtil,java.util.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="act" title="<s:message key = "gamename"/>">
<p>
	<%
		FightList fightList = null;
		Fighter player = null;
		player = (Fighter) request.getAttribute("player");
		fightList = (FightList) request.getAttribute("fightList");
		String deadnpcxiayibu = (String) request
				.getAttribute("deadnpcxiayibu");
		List dropgoods = fightList.getDropGoods();
		DropGoodsVO dropGoods = null;
	%>
战斗胜利！
<anchor>继续游戏<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/attackNPC.do?cmd=n5")%>" ></go></anchor><br/>
	<%
		if(player.getKillDisplay()!=null&&!player.getKillDisplay().equals("null"))
		{
			%>
				<%=player.getKillDisplay()%>
			<%
		}
	 %>
	您的体力:<%=player.getPHp()%><br/>
	您的内力:<%=player.getPMp()%><br/>
	<%
		if (fightList.getGrowDisplay() != null) {
	%>
	<%=fightList.getGrowDisplay()%><br/>
	<%
		}
	%>
	您获得了:<%=GameConfig.getMoneyUnitName() %>+<%=fightList.getMoney()%><br />
	<%
		if (fightList.getPet_display() != null) {
	%><%=fightList.getPet_display()%><br/>
	<%
		}
	%>
	<%
		if (player.getTask_display() != null) {
	%><%=player.getTask_display()%><br/>
	<%
		}
	%>
	<%
		if (deadnpcxiayibu != null) {
	%><%=deadnpcxiayibu%><br/>
	<%
		}
	%>

	<%
		if (dropgoods != null && dropgoods.size() != 0) {
	%>
<anchor>全部拾取<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/goods.do?cmd=n5")%>" ></go></anchor><br/>
	<%
		}
	%>
	<%
		if (dropgoods != null && dropgoods.size() != 0) {
			int c = dropgoods.size();
			for (int i = 0; i < c; i++) {
				dropGoods = (DropGoodsVO) dropgoods.get(i);
	%>
	<anchor><%=StringUtil.isoToGB(dropGoods.getGoodsName())%>×<%=dropGoods.getDropNum()%>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/goods.do?cmd=n1")%>">
	<postfield name="drop_pk" value="<%=dropGoods.getDPk()%>" />
	<postfield name="goods_id" value="<%=dropGoods.getGoodsId()%>" />
	<postfield name="goods_type" value="<%=dropGoods.getGoodsType()%>" />
	</go>
	</anchor>
	<%
		if ((i + 1) != dropgoods.size()) {
					out.print(",");
				}
			}
			out.print("<br/>");
		}
	%>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
