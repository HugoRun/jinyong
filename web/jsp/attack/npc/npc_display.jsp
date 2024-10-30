<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.pub.util.StringUtil"%>
<%@page import="com.ls.pub.constant.*"%>
<%@page import="com.ls.ben.vo.info.npc.*"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="act" title="<s:message key = "gamename"/>">
<p>
    <%
        NpcAttackVO npc = (NpcAttackVO) request.getAttribute("npc");
        String npcPic = (String) request.getAttribute("npcPic");
        String wx = (String) request.getAttribute("wx");
        if (npc == null) {
            //System.out.println("空指针。。。");
        }
    %>
    <%=npcPic%>
    <%=StringUtil.isoToGB(npc.getNpcName())%><br />
    <%
    if(npc.getNpcType() == NpcAttackVO.NIANSHOU){
    %>
    等级:??级
    <br />
    气血:???<br />
    防御:???<br />
    攻击:<%=wx%><br />
    <anchor>
    <go method="get"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/attackNPC.do?cmd=n4")%>"></go>
    返回
    </anchor>
    <br />
    <%
    }else{
    %>
    等级:<%=npc.getLevel()%>级
    <br />
    气血:<%=npc.getNpcHP()%><br />
    防御:<%=npc.getDefenceXiao()%>-<%=npc.getDefenceDa()%><br />
    攻击:<%=wx%><br />
    <anchor>
    <go method="get"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/attackNPC.do?cmd=n4")%>"></go>
    返回
    </anchor>
    <br />
    <%
    }
    %>
    <%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
