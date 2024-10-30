<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.attack.NPCBout"%>
<%@page import="com.ls.ben.vo.info.attack.DropGoodsVO"%>
<%@page import="com.ls.ben.vo.info.npc.*"%>
<%@page import="com.ls.pub.util.StringUtil,java.util.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="act" title="<s:message key = "gamename"/>">
<p>
	<%
	NpcAttackVO npc = null;
	NPCBout bout = null;
	bout = (NPCBout)request.getAttribute("bout");
	List npcdrops = bout.getPDropGoods();
	DropGoodsVO dropGoods = null;
%>
	战斗胜利！
	<br />
	您的体力:<%= bout.getPCurrentHP() %><br />
	您的内力:<%= bout.getPCurrentMP()%><br />
	您获得了:经验+<%=bout.getPGetExp() %>,钱<%=bout.getPGetMoney() %><br />
	<%
				if( !bout.getPCurrentLevel().equals("") )
				{
					out.print(bout.getPCurrentLevel());
				}
			 %>
	<%
				if( npcdrops!=null &&  npcdrops.size()!=0 )
				{
					for(int i=0;i<npcdrops.size();i++)
					{
						dropGoods = (DropGoodsVO)npcdrops.get(i);
						%>

	<a href="<%=response.encodeURL(GameConfig.getContextPath()+"/")%>"><%=StringUtil.isoToGB(dropGoods.getGoodsName())%>*<%=dropGoods.getDropNum()%></a>
	<% 
						if( (i+1)!=npcdrops.size() )
						{
							out.print(",");
						}
					}
					out.print("<br/>");
				}			
			%>


<a href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do?cmd=n2")%>">继续</a><br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
