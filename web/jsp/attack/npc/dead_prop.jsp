<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.attack.*"%>
<%@page import="com.ls.ben.vo.info.attack.DropGoodsVO"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.StringUtil,java.util.*"%>
<%@page import="com.ls.web.service.player.RoleService"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="act" title="<bean:message key="gamename"/>">
<p>
    <%

    RoleService roleService = new RoleService();
    RoleEntity roleEntity = roleService.getRoleInfoBySession(request.getSession());

    Fighter player = null;
    String siegehint = (String)request.getAttribute("siegehint");
    player = (Fighter) request.getAttribute("player");
    String pk = (String)request.getAttribute("pk");
    String ss = (String)request.getAttribute("reqchair");
    if(player.getKillDisplay() != null && !player.getKillDisplay().equals("null")){
%>
    <%=player.getKillDisplay() %>
    <% }
    if ( roleEntity.getBasicInfo().getGrade() <= 10 ) {
        out.println("您的等级不满10级，死亡不损失任何经验!<br/>");
    }

     if ( siegehint != null && !siegehint.trim().equals("") && !siegehint.trim().equals("null") ) {
        out.println(siegehint);
     }
     if(player.getDropDisplay() != null){%><%=player.getDropDisplay().replaceAll("null,","").replace("null","") %><br />
    <% } %>
    【鸿蒙造化果】可原地复活是否使用?
    <br />

    <%if(ss != null && ss.equals("pk")) { %>
    <anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do")%>">
    <postfield name="cmd" value="n15" />
    <postfield name="choice" value="1" />
    </go>
    是
    </anchor>|<anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do")%>">
    <postfield name="cmd" value="n15" />
    <postfield name="choice" value="2" />
    </go>
    否
    </anchor>
    <br />
    <%} else { %>
    <anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do")%>">
    <postfield name="cmd" value="n15" />
    <postfield name="choice" value="1" />
    </go>
    是
    </anchor>|<anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do")%>">
    <postfield name="cmd" value="n15" />
    <postfield name="choice" value="2" />
    </go>
    否
    </anchor>
    <br />

    <%} %>
    <%@ include file="/init/init_dead_prop.jsp"%>

</p>
</card>
</wml>
