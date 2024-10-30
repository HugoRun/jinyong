<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"%>
<%@page import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		String pPk = (String) request.getAttribute("pPk");
		String uPk = (String) request.getAttribute("uPk");
		String pName = (String) request.getAttribute("pName");
		String mapid = (String) request.getAttribute("mapid");
		String partBorn = (String) request.getAttribute("partBorn"); 
	%>
	<%
		//第一种出生
		if (Integer.parseInt(partBorn) == 1) {
	%>
	<img src="<%=GameConfig.getContextPath()%>/image/prelude/jubaopeng/fuweng3.gif" alt="" /><br/>
	“少爷真坏～老爷一早很不高兴，您今天就别去杨二叔那了。”<br/>“杨二叔是我师傅！老头子整天要我读书，太没劲，我要做天下第一！”我穿着好衣服，还是乖乖去见老头子。<br/>怎想到这个决定却影响了整个江湖！彻底改变了我的人生！<br/>
	<%
		}
		//第二种出生
		if (Integer.parseInt(partBorn) == 2) {
	%>
	<img src="<%=GameConfig.getContextPath()%>/image/prelude/tulongdao/xiake3.gif" alt="" /><br/>
	“小子，给我出来！”我正要答话，老头子在外面叫我了。<br/>“我天下第一了，一定回来娶您做我娘子！”我穿好上衣，对着小雪正色道。小雪红着脸收拾着药酒。<br/>怎想到这个决定却影响了整个江湖！彻底改变了我的人生！<br/>
	<%
		}
		//第三种出生
		if (Integer.parseInt(partBorn) == 3) {
	%>
	<img src="<%=GameConfig.getContextPath()%>/image/prelude/weibu/hunhun3.gif" alt="" /><br/>
	一天一个算命的看到我的生辰八字，留了一句话:日后必有大成！<br/>我痴痴得望着算命的身影，将手帕紧紧攥在手里，心中浮现出“英雄”二字。<br/>
	<%
		}
		//第四种出生
		if (Integer.parseInt(partBorn) == 4) {
	%>
	<img src="<%=GameConfig.getContextPath()%>/image/prelude/duanxugao/yisheng3.gif" alt="" /><br/>
	这短短的一句话，炸响在了我的脑海中，使得我一阵失神。<br/>“民心无医，只治其身还有何意义，呵呵~”我自嘲的笑了笑，朝杨二叔离开方向深深一鞠后，便大步向家中走去。<br/>这短短的一句话彻底改变了我的人生！影响了整个江湖！<br/>
	<%
		}
		//第五种出生
		if (Integer.parseInt(partBorn) == 5) {
	%>
	<img src="<%=GameConfig.getContextPath()%>/image/prelude/ruanweijia/jiangjun3.gif" alt="" /><br/>
	“小丫头，今天怎么不去陪您的傻小子，到跑来看哥练武啊？是否是义父他老人家来了？”<br/>“不理您了”小雪横了我一眼便一蹦一跳的跑开了，“伯父让您练完功，去大厅找他”<br/>“知道了~雪儿”<br/>我万万没有想到，这次去见爹他老人家影响了整个江湖！彻底改变了我的人生！<br/>
	<%
		}
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/partannal.do")%>">
	<postfield name="mapid" value="<%=mapid%>" />
	<postfield name="uPk" value="<%=uPk%>" />
	<postfield name="pPk" value="<%=pPk%>" />
	<postfield name="pName" value="<%=pName%>" />
	</go>
	进入游戏
	</anchor>
	<br/>
	<anchor> 
	 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/partannal.do")%>">
	 <postfield name="mapid" value="<%=mapid %>" />
	 <postfield name="uPk" value="<%=uPk %>" />
	 <postfield name="pPk" value="<%=pPk %>" />
	 <postfield name="pName" value="<%=pName %>" /> 
	 </go>
	 跳过
	 </anchor>
</p>
</card>
</wml>
