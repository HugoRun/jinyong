<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>
    <%
        String uPk = null;
        if (request.getParameter("uPk") != null) {
            uPk = request.getParameter("uPk");
        } else {
            uPk = (String) request.getAttribute("uPk");
        }
    %>
    铁血屠龙6月23日更新，第一届帮会争霸大赛奖品发放！帮会“名剑山庄”一枝独秀，轻松将“第一帮”的至尊头衔收入囊中，血战邪盟、独占神话分列二、三位。请获奖帮会的帮众前往大散关岳飞处领取奖励。飞狐区开区冲级大赛将在6月26日结束，23日更新40级到50级补充了大量支线任务。
    <br />
    其他更新：
    <br />
    1.修改了60级玩家无法开启黄金宝箱的bug
    <br />
    2.修改了一套同五行属性的装备没有效果加成的bug
    <br />
    3.修改了卖出从仓库取出的物品需要二次确认的bug
    <br />
    4.修改使用小还丹没有增加经验的bug
    <br />
    <anchor>
    <go method="get"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/login.do?cmd=n3")%>" ></go>
    返回
    </anchor>
</p>
</card>
</wml>
