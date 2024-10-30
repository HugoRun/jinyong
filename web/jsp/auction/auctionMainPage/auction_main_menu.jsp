<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.wrapinfo.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<anchor>灵石拍卖<go method="get"
	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/jsp/auction/auctionMainPage/auction_main_shi.jsp")%>"></go></anchor>|仙晶拍卖|<anchor>拍卖仓库
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath() + "/menu/auctionGet.do")%>">
	<postfield name="cmd" value="n9" />
	</go></anchor><br/>
	
	
	
	<!--<anchor>
<go method="get"
	href="<%=response.encodeURL(GameConfig.getContextPath() + "/jsp/auction/auctionMainPage/auction_main_jing.jsp")%>"></go>
仙晶拍卖
</anchor>|
-->