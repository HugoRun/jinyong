<%@page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
【会员服务】<br/>
${role_title}您好!我是会员服务生露露,很高兴为您服务!VIP商城只有会员才可购买!<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=vipIntro&amp;type=vip")%>" method="get"></go>★会员特权★</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=vipmall")%>" method="get"></go>进入VIP商城</anchor><br/>
您当前会员等级:${vip_name }<br/>
当前积分:${jifen}<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/sky/bill.do?cmd=n0")%>" method="get"></go>我想获得积分</anchor><br/>
<anchor>【洪荒会员周卡】
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n6")%>" method="post">
<postfield name="c_id" value="47"/>
<postfield name="type" value="8"/>
</go>
</anchor><br/>
<anchor>【洪荒会员月卡】
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n6")%>" method="post">
<postfield name="c_id" value="48"/>
<postfield name="type" value="8"/>
</go>
</anchor><br/>
<anchor>【鸿蒙会员周卡】
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n6")%>" method="post">
<postfield name="c_id" value="49"/>
<postfield name="type" value="8"/>
</go>
</anchor><br/>
<anchor>【鸿蒙会员月卡】
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n6")%>" method="post">
<postfield name="c_id" value="50"/>
<postfield name="type" value="8"/>
</go>
</anchor><br/>
--------------------<br/>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>


		