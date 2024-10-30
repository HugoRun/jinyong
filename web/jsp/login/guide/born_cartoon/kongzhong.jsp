<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
<img src="<%=GameConfig.getGameUrl()%>/image/prelude/duanxugao/yisheng1.png" alt="" /><br/>
我没有名字,没有父母,没有朋友,更没有家.只有一张刺着生辰的丝绢帕.我掏出手帕,思绪回到一年前……<br/>
我在为一个盗贼组织做事,大家为生活聚在一起,没有义气可言.一次任务的失败.我替一个兄弟抗了下来,被逐出了组织.流浪到了牛家村,被老爹发现了奄奄一息的我.村里的神医将我拉回了人世间,于是我便在村里住下了.<br/>
小雪,老爹的女儿,她就象她的名字一样纯洁无暇.我只能远远得看着她,就象仙女一样.那天打柴回来,雨好大,我那张写满生辰的纸又湿透了,我放在老爹房里的桌上晾干.第二天纸傍边放了一张刺着我生辰的丝绢帕.<br/>
一阵风把我从过去拉回现在,手帕被风卷起,向远处飘去,我心中一惊,赶紧追去.追上前,只见一个背着半仙幌子的老人正在仔细看着那手帕.<br/>
我站在他面前一句话没说,老人看来我一眼,将手帕递给我,留了一句话,日后必有大成!<br/>
我痴痴得望着他的身影,将手帕紧紧攥在手里,心中浮现出"英雄"二字.<br/>
<anchor> 
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n5") %>">
<postfield name="step" value="2" />
</go>
跳过
</anchor>
</p>
</card>
</wml>
