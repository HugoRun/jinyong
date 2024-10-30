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
	<img src="<%=GameConfig.getContextPath()%>/image/prelude/jubaopeng/fuweng2.gif" alt="" /><br/>
	“少爷，起床拉”<br/>“小桃啊，啊～啊～”我打着哈欠，掀开被子，准备下床。<br/>“啊，少爷您又……”小桃秋波透水的美目惊羞的瞪着我。<br/>我低头一看，只见裤裆透湿……<br/>
	<%
		}
		//第二种出生
		if (Integer.parseInt(partBorn) == 2) {
	%>
	<img src="<%=GameConfig.getContextPath()%>/image/prelude/tulongdao/xiake2.gif" alt=""/><br/>
	我回过头看到小雪通红的娇容，如葱嫩白的柔指正擦拭着我健壮赤裸的后背。<br/>“看什么，转过去”小雪娇嗔着。<br/>“呵呵，我要去求杨二叔以后把您嫁给我！”我喜不自禁“啊！疼！”<br/>“让您不正经！”小雪狠狠的捻了我一下，“想要独步武林，又要抱得佳人？美事都让您全占了！”<br/>
	<%
		}
		//第三种出生
		if (Integer.parseInt(partBorn) == 3) {
	%>
	<img src="<%=GameConfig.getContextPath()%>/image/prelude/weibu/hunhun2.gif" alt=""/><br/>
	小雪，二叔的女儿，我只能远远得看着她，就象仙女一样。<br/>那天打柴回来，雨好大，我那张写满生辰的纸又湿透了，我放在二叔房里的桌上晾干，第二天傍边放了一张刺着我生辰的丝绢帕。<br/>
	<%
		}
		//第四种出生
		if (Integer.parseInt(partBorn) == 4) {
	%>
	 <img src="<%=GameConfig.getContextPath()%>/image/prelude/duanxugao/yisheng2.gif" alt=""/><br/>
	 杨二叔随意地问道:“您今后可有何志向？”<br/>“弟子愿学好医术，将来好多多救治我大宋百姓”<br/>杨二叔淡然一笑，在小雪的搀扶下，缓步离开，边走边自语着:“民心无医，只治其身还有何意义”<br/>
	<%
		}
		//第五种出生
		if (Integer.parseInt(partBorn) == 5) {
	%>
	 <img src="<%=GameConfig.getContextPath()%>/image/prelude/ruanweijia/jiangjun2.gif" alt=""/><br/>
	“呼~”我舒展下略显劳累的双腿，向树荫下的小雪走去。<br/>小雪忙将一条一早准备好的毛巾递给我，略显好奇地问道:“哥，您每日都这么练功，不辛苦吗？”<br/>“辛苦只是暂时的，待我练好一身武艺，将来定将胡虏驱逐出境，还我大宋山河~”<br/>“哥，您好棒啊”小雪崇拜万分的看着。<br/>
	<%
		}
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/prelude.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="mapid" value="<%=mapid %>" />
	<postfield name="uPk" value="<%=uPk %>" />
	<postfield name="pPk" value="<%=pPk %>" />
	<postfield name="pName" value="<%=pName %>" />
	<postfield name="partBorn" value="<%=partBorn %>" />
	</go>
	继续
	</anchor>
	<br/>
	 <anchor> 
	 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/partannal.do") %>">
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
