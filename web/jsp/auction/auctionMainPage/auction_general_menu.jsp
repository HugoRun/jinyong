<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.wrapinfo.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/menu/auction.do?cmd=n5")%>">
<postfield name="table_type" value="1" />
</go>
武器
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/menu/auction.do?cmd=n5")%>">
<postfield name="table_type" value="2" />
</go>
防具
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/menu/auction.do?cmd=n5")%>">
<postfield name="table_type" value="3" />
</go>
法宝
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/menu/auction.do?cmd=n1")%>">
</go>
道具
</anchor>