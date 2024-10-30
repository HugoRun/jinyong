<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.pub.util.StringUtil"%>
<%@page import="com.ls.ben.vo.info.partinfo.ShortcutVO"%>
<%@page
    import="com.ls.ben.vo.info.npc.*,com.ls.ben.vo.info.partinfo.Fighter"%>
<%@page import="java.util.*"%>
<%@page import="com.ls.ben.vo.info.effect.*,com.pub.ben.info.*"%>
<%@page import="com.ls.pub.constant.*"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.web.service.player.RoleService"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="act" title="<s:message key = "gamename"/>">
<p>
    <%
    RoleService roleService = new RoleService();
    RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
 %>
    <%
  String pPk = (String)request.getSession().getAttribute("pPk");

String notpet = (String)request.getAttribute("notpet");
String petInfolist = (String)request.getAttribute("petInfolist");
if(notpet!=null){
 %>
    捕获失败！
    <br />
    <%} if(petInfolist!=null){
 %>
    <%=petInfolist %><br />
    <%} %>
    <%
        Fighter player = null;
        player = (Fighter) request.getAttribute("player");
        List npcs = (List) request.getAttribute("npcs");

        List shortcuts = (List) request.getAttribute("shortcuts");
        ShortcutVO shortcut = null;

        NpcAttackVO npc = null;
        if (player == null || npcs == null) {
            //System.out.println("空指针。。。");
        }
        PropUseEffect propUseEffect = (PropUseEffect)request.getAttribute("propUseEffect");
    %>
    <%
        int j = 0;
        for (; j < 6; j++) {
            shortcut = (ShortcutVO) shortcuts.get(j);

    %>

    <anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do?cmd=n6")%>">
    <postfield name="sc_pk" value="<%=shortcut.getScPk()%>" />
    </go>
    <%if(StringUtil.isoToGB(shortcut.getScDisplay()).length() >3 ){ %>
    <%=StringUtil.isoToGB(shortcut.getScDisplay()).substring(0,3) %>
    <%}else{ %>
    <%=StringUtil.isoToGB(shortcut.getScDisplay()) %>
    <%} %>
    </anchor>
    <%
        if(j!=2&&j!=5)
        {
    %>|<%
        }
        else
        {
        %><br/><%
        }
    }
    %>


    您的气血:<%=player.getPHp()%>/<%=player.getPMaxHp()%>
    <%
        if( propUseEffect!=null && propUseEffect.getIsEffected() && propUseEffect.getPropType()==PropType.ADDHP )
        {
            %>(+<%= propUseEffect.getEffectValue()%>)<%
        }
     %>
    <br />
    您的内力:<%=player.getPMp()%>/<%=player.getPMaxMp() %>
    <%
        if( propUseEffect!=null && propUseEffect.getIsEffected() && propUseEffect.getPropType()==PropType.ADDMP )
        {
            %>(+<%= propUseEffect.getEffectValue()%>)<%
        }
     %>
    <br />
    ----------------------
    <br />
    <%
             if(player.getKillDisplay()!=null&&!player.getKillDisplay().equals("null")){
             %>
          <%=player.getKillDisplay() %>
    <%
    }
        if (npcs != null && npcs.size() != 0) {
            for (int i = 0; i < npcs.size(); i++) {
                npc = (NpcAttackVO) npcs.get(i);
                if (npc != null ) {
                    out.print(StringUtil.isoToGB(npc.getNpcName()) + ":" + npc.getCurrentHP()+"/"+npc.getNpcHP());
                    out.print("<br/>");
                }
            }

        }
    %>

    <%
        if( propUseEffect!=null )
        {
            if( propUseEffect.getIsEffected() )
            {
                %><%= propUseEffect.getEffectDisplay()%><br />
    <%
            }
            else
            {
            %><%= propUseEffect.getNoUseDisplay()%><br />
    <%
            }
        }
     %>
    <anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do?cmd=n1")%>">
    <postfield name="daguai" value="1" />
    </go>
    快捷键设置
    </anchor>
    <br />
    <%
    if(roleInfo.getSettingInfo().getAutoAttackSetting().getAttackType() != -1){
    %>l
    <anchor>
    <go method="get"
        href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do?cmd=n14") %>"></go>
    停止自动战斗
    </anchor>
    <jsp:forward page="/attackNPC.do?cmd=n13" />
    <%
    }else{
    %>
    <anchor>
    <go method="get"
        href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do?cmd=n10") %>"></go>
    自动战斗
    </anchor>
    <%} %>
    <br />
    <%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
