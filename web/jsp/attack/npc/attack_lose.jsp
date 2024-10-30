<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.npc.*"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.StringUtil,java.util.*"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="act" title="<bean:message key="gamename"/>">
<p>
    <%
    List npcs = null;
    NpcFighter npc = null;
    Fighter player = null;

    npcs = (List)request.getAttribute("npcs");
    player = (Fighter)request.getAttribute("player");
    StringBuffer npc_skill_display=new StringBuffer();

%>
    <%=player.getKillDisplay() %><br />
    <%
                 StringBuffer temp = new StringBuffer();
                 if( npcs!=null)
                 {
                     for(int i=0;i<npcs.size();i++)
                     {
                         npc = (NpcFighter)npcs.get(i);
                         temp.append(StringUtil.isoToGB(npc.getNpcName())+ "体力:"+npc.getCurrentHP());
                         if( npc.getPlayerInjure()!=0 || npc.getPetInjure()!=0 )
                         {
                             temp.append("(-"+npc.getPlayerInjure());
                             if( npc.getPetInjure()!=0 )
                             {
                                 temp.append("/-"+npc.getPetInjure());
                             }
                             temp.append(")");
                         }
                         temp.append("<br/>");
                         npc_skill_display.append(StringUtil.isoToGB(npc.getNpcName()+npc.getSkillName())+"<br/>");
                     }
                     out.print(temp.toString());
                 }
              %>

    您的气血:<%= player.getPHp() %>(<%= player.getInjureDisplay() %>)
    <br />
    您的内力:<%= player.getPMp()%>
    <%
                if( player.getExpendMP()!=0 )
                    out.print("(-"+player.getExpendMP() +")");

             %>
    <br />
    <%if(player.isNotDropExp() == false) { %>

    您丢失:经验<%=player.getDropExp() %>点
    <br />
    <%} else { %>
    免死符让您挽回经验<%=player.getDropExp() %>点！
    <br />
    <%} %>
    <%if(player.getDropDisplay() != null) { %>
    <%=player.getDropDisplay() %><br />
    <%} %>
    <anchor>
    <go method="get"
        href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubaction.do")%>"></go>
    继续
    </anchor>
    <br />
    <%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
