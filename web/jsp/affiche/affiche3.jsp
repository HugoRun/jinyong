<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="login" title="<bean:message key="gamename"/>">
<p>
    <%
        String uPk = null;
        if (request.getParameter("uPk") != null) {
            uPk = request.getParameter("uPk");
        } else {
            uPk = (String) request.getAttribute("uPk");
        }
    %>
    24日更新，
    <bean:message key="gamename" />
    趣味博彩系统上线。20级以上玩家每天都在前往临安仙客来酒楼二楼的黑衣大汉出参与趣味博彩活动，近期中奖者可获得【五色神牛宠物蛋】×1！
    <br />
    <bean:message key="gamename" />
    竞猜十分简单，只需要根据系统引导从以下四组麻将字牌每组选择一个即可:第一组，东风、南风、北风、西风；第二组，梅、兰、菊、竹；第三组，春、夏、秋、冬；第四组，红中、白板、发财。如果四个字与系统公布的字牌相同，即中奖！
    <br />
    竞猜时间安排:每天8:00～19:59为竞猜时间，玩家可进行竞猜；20:00开奖，21:00～24:00领取奖励，过期作废！
    <br />
    竞猜奖励设置:
    <br />
    •原则上玩家投注的银两既为获奖者的奖励，但系统在有需要的时候可以追加奖励银两
    <br />
    •所有中奖者按购买注数银两平分所有奖金池的银两
    <br />
    •如本轮竞猜无人中奖，所有奖励将累计进入下一轮
    <br />
    •投注分为:1两，5两，10两，20两，50两，100两
    <br />
    •获奖玩家抽取15%的税
    <br />
    •每日税收的20%作为特殊奖励，一直累计到下月一日，作为上月投注超过15次但没中奖一次的玩家的特殊奖励。上月投注超过15次但没中奖一次的玩家可额外获得一次投注慈善竞猜的机会。
    <br />
    •玩家连续中奖可获得奖金翻倍的奖励，连续第二次中奖获得奖金×2，连续第三次中奖获得奖金×3，如此类推！
    <br />
    <anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/jsp/part/part_type.jsp")%>">
    <postfield name="uPk" value="<%=uPk%>" />
    </go>
    返回
    </anchor>
</p>
</card>
</wml>
