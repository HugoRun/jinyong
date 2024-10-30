<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.wrapinfo.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.pm.vo.constant.AuctionType"%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath() + "/menu/auctionGet.do")%>">
	<postfield name="cmd" value="n9" />
	<postfield name="getType" value="1" />
	</go>
财产
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath() + "/menu/auctionGet.do")%>">
	<postfield name="cmd" value="n9" />
	<postfield name="getType" value="<%=AuctionType.ARM %>" />
	</go>
武器
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath() + "/menu/auctionGet.do")%>">
	<postfield name="cmd" value="n9" />
	<postfield name="getType" value="<%=AuctionType.ACCOUTE %>" />
	</go>
防具
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath() + "/menu/auctionGet.do")%>">
	<postfield name="cmd" value="n9" />
	<postfield name="getType" value="<%=AuctionType.JEWELRY %>" />
	</go>
法宝
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath() + "/menu/auctionGet.do")%>">
	<postfield name="cmd" value="n9" />
	<postfield name="getType" value="4" />
	</go>
道具
</anchor>