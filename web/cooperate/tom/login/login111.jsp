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
    <bean:message key="gamename"/>4月3日更新，清明节活动上线！4月3日～4月10日，<br/>
    10级以上的玩家每天可在临安天街广场王重阳处领取5次清明节活动任务，
    <br/>
    每完成一次任务即可获得【青团】×1（【青团】使用后可获得30分钟5倍经验祝福效果），<br/>
    运气好的人还可以获得【<%=GameConfig.getYuanbaoName() %>】哦；
	<br/>
	朱仙镇战场掉落【阵亡将士的盔甲】和【敌人的头颅】，【阵亡将士的盔甲】×20可在岳飞处兑换【元宝】×1、<br/>
	【敌人的头颅】】×5可在岳飞处兑换【清明礼盒】×1，
	<br/>
	打开【清明礼盒】可获得45、55、60级套装部件一件！
	<br/>
	其它更新调整：
	<br/>
	1.每日任务无限做活动下线，武林大使出每日经验任务恢复每人每日天领取20次，
	<br/>
	仙客来酒楼和镖局每日银两任务恢复每人每日领取10次
	<br/>
	2.每日经验任务和银两任务可快速传送了
	<br/>
	3.降低了游戏任务中怪物的五行和攻击强度
	<br/>
	4.降低了55级以上冒险区域怪物的最高攻击
	<br/>
	5.增加散装药物（回春散、补血散）每次补给的数值
	<br/>
	6.增加临安到钱塘江码头、雷峰塔传送，终南山脚到终南山腰、蝴蝶谷传送、长白山脚到长白山腰、
	<br/>
	天池传送，昆仑山到崖底传送，大草原到大草原中部、大草原深处的传送
	<br/>
	7.增加了更多附带五行属性的任务奖励装备
	<br/>
	8.修改使用新手宝箱后包裹栏多一个格子的bug
	<br/>
	9.修改了角色成长附加的暴击率不起作用的bug
	<br/>
	10.宠物交易和拍卖时可以点击查看其具体属性了
	<br/>
	<anchor>
	<go href="<%=GameConfig.getContextPath()%>/jsp/affiche/jiangli.jsp"
		method="get">
	</go>
	金庸公测公告
	</anchor>
	<br/>

	<anchor>
	<go href="http://189hi.cn/plaf/wml/gacs.jw?act=gogame&amp;gameid=10"
		method="get">
	</go>
	返回专区
	</anchor>
	<br/>
	<anchor>
	<go method="get" href="http://189hi.cn/plaf/wml/gacs.jw?act=index">
	</go>
	返回189HI.CN
	</anchor>
</p>
</card>
</wml>
