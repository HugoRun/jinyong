<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.skill.*"%>
<%@page import="java.util.*"%>
<%@page import="com.ls.pub.constant.Shortcut"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="ID" title="<bean:message key="gamename"/>">
<p>
    <%
        String sc_pk = (String) request.getAttribute("sc_pk");

        List skills = (List) request.getAttribute("skills");

        PlayerSkillVO skill = null;
        if (skills == null) {
            out.print("参数为空");
        }
    %>
    技能列表
    <br />
    <%
        if (skills != null && skills.size() != 0) {
            for (int i = 0; i < skills.size(); i++) {
                skill = (PlayerSkillVO) skills.get(i);
    %>
    <anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                                    + "/shortcut.do?cmd=n2")%>">
    <postfield name="sc_pk" value="<%=sc_pk%>" />
    <postfield name="type" value="<%=Shortcut.SKILL%>" />
    <postfield name="operate_id" value="<%=skill.getSPk()%>" />
    </go>
    <%=skill.getSkName()%><br />
    </anchor>
    <%
        }
        } else {
            out.print("暂无技能<br/>");
        }
    %>
    <anchor>
    <go method="get"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/attackNPC.do?cmd=n4")%>"></go>
    返回
    </anchor>
    <br />
    <%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
