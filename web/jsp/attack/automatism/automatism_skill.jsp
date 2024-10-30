<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.skill.*"%>
<%@page import="com.ls.pub.constant.Shortcut,com.pub.ben.info.*"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="act" title="<bean:message key="gamename"/>">
<p>
    请选择您在自动战斗中使用的技能:
    <br />
    <%
        List skills = (List) request.getAttribute("skills");
        PlayerSkillVO skill = null;
    %>
    <anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/attackNPC.do")%>">
    <postfield name="cmd" value="n11" />
    <postfield name="type" value="<%=Shortcut.ATTACK%>" />
    <postfield name="operate_id" value="0" />
    <postfield name="killName" value="攻击" />
    </go>
    攻击
    </anchor>
    <br />
    <%
        if (skills != null && skills.size() != 0) {
            for (int i = 0; i < skills.size(); i++) {
                skill = (PlayerSkillVO) skills.get(i);
    %>
    <anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                                    + "/attackNPC.do")%>">
    <postfield name="cmd" value="n11" />
    <postfield name="p_pk" value="<%=skill.getPPk()%>" />
    <postfield name="type" value="<%=Shortcut.SKILL%>" />
    <postfield name="operate_id" value="<%=skill.getSPk()%>" />
    <postfield name="killName" value="<%=skill.getSkName()%>" />
    </go>
    <%=skill.getSkName()%>
    </anchor>
    <br />
    <%
        }
        }
    %>
    <anchor>
    <go method="get"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/attackNPC.do?cmd=n4")%>" ></go>
    返回
    </anchor>
    <br />
    <%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
