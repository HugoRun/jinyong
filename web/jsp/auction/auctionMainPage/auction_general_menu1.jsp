<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.wrapinfo.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/menu/auctionBuy.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="auctionType" value="6" />
<postfield name="sortType" value="time" />
</go>
武器
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/menu/auctionBuy.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="auctionType" value="5" />
<postfield name="sortType" value="time" />
</go>
防具
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/menu/auctionBuy.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="auctionType" value="7" />
<postfield name="sortType" value="time" />
</go>
法宝
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/menu/auctionBuy.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="auctionType" value="4" />
<postfield name="sortType" value="time" />
</go>
道具
</anchor>