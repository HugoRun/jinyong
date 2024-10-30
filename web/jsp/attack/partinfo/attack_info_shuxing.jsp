<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
    import="java.util.*,com.ls.ben.vo.info.partinfo.PartInfoVO"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>

<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n7" />
    </go>
    状态
    </anchor>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n8" />
    </go>
    属性
    </anchor>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n9" />
    </go>
    装备
    </anchor>
    <br/>
    <%
        PartInfoVO vo = (PartInfoVO) request.getAttribute("player");

    %>
    攻击:<%=vo.getPGj()+vo.getPZbgjXiao() %>-<%=vo.getPGj()+vo.getPZbgjDa() %>
    <br/>
    防御:<%=vo.getPFy()+vo.getPZbfyXiao() %>-<%=vo.getPFy()+vo.getPZbfyDa() %>
    <br/>
    气血值:<%=vo.getPHp() %>/<%=vo.getPMaxHp() %>
    <br/>
    内力值:<%=vo.getPMp() %>/<%=vo.getPMaxMp() %>
    <br/>
    力量:<%=vo.getPForce() %>
    <br/>
    敏捷:<%=vo.getPAgile() %>
    <br/>
    体魄:<%=vo.getPPhysique() %> <br/>
    悟性:<%=vo.getPSavvy() %><br/>
    金系内功伤害/抗性:<%=vo.getWx().getJinGj()%>/<%=vo.getWx().getJinFy()%><br/>
    木系内功伤害/抗性:<%=vo.getWx().getMuGj()%>/<%=vo.getWx().getMuFy()%><br/>
    水系内功伤害/抗性:<%=vo.getWx().getShuiGj()%>/<%=vo.getWx().getShuiFy()%><br/>
    火系内功伤害/抗性:<%=vo.getWx().getHuoGj()%>/<%=vo.getWx().getHuoFy()%><br/>
    土系内功伤害/抗性:<%=vo.getWx().getTuGj()%>/<%=vo.getWx().getTuFy()%><br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do?cmd=n4")%>"></go>返回</anchor> <br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
