<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p> 
    <bean:message key="gamename"/>4月17日09:30--12:00更新维护，60级【真剑冢】副本上线！60级的玩家可在襄阳城城市中心郭靖处领取副本开门任务“真剑冢”，完成该任务后即可通过铁掌峰上剑冢内大雕传送进入副本开始探险！60级副本有5个boss，最后一个boss有几率掉落60级神兵武器！更新后每晚20点～24点全服双倍经验活动，双倍效果可遇2倍经验卡和5倍经验卡效果叠加，打怪最高可获得10倍经验的效果，让大家加速练级可更快加入副本探险！17日更新还取消了角色攻击怪物等级加成，打怪更加轻松！
    <br/>
    其他修改调整：
    <br/>
    1.降低了师门技能精通级的熟练度
    <br/>
    2.雷峰塔底(3,6)增加返回雷峰塔的跳转
    <br/>
    3.修改桃花岛的中心区域为渡口
    <br/>
    4.提高宠物的普通攻击力
    <br/>
    5.调整补血散(中)、回春散(中)使用等级为40级
    <br/>
    6.修改银两不够也可以任务传送的bug
    <br/>
    7.角色死亡损失的经验减半
    <br/>
    8.修改了副本外脱离副本队伍被强制传回区域中心点的bug
    <br/>
    9.修改了装备升级、装备分解时产生错误页面的bug
    <br/>
    10.修改了副本队伍一人杀死副本boss后其他人boss也消失的bug
    <br/>
    11.清明活动下线，朱仙镇战场不再掉落【阵亡将士的盔甲】【敌人的头颅】
    <br/>
    <anchor><go href="<%=GameConfig.getContextPath()%>/jsp/affiche/jiangli.jsp"method="get" ></go>金庸公测公告</anchor><br/>
    <anchor><go href="http://189hi.cn/plaf/wml/gacs.jw?act=gogame&amp;gameid=10" method="get" ></go>返回专区</anchor><br/>
    <anchor><go method="get" href="http://189hi.cn/plaf/wml/gacs.jw?act=index"></go>返回189HI.CN</anchor>
</p>
</card>
</wml>
