<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
<%@page import="java.util.List"%>
<%
	String pByPkprop = request.getParameter("pByPk");//被请求人的ID
	String w_type = (String) request.getAttribute("w_type");
	QueryPage item_page = (QueryPage) request.getAttribute("item_page");
	List list = (List) item_page.getResult();
	PlayerPropGroupVO propGroup = null;
%>
<%
	if (list != null && list.size() != 0) {
		for (int i = 0; i < list.size(); i++) {
			propGroup = (PlayerPropGroupVO) list.get(i);
%>
<anchor>
<go method="post"  href="<%=response.encodeURL(GameConfig.getContextPath()+"/sellpropaction.do")%>">
<postfield name="cmd" value="n1" />
<postfield name="w_type" value="<%=w_type%>" />
<postfield name="pg_pk" value="<%=propGroup.getPgPk() %>" />
<postfield name="goods_id" value="<%=propGroup.getPropId()%>" />
<postfield name="pByPk" value="<%=pByPkprop %>" />
</go>
<%=propGroup.getPropName()%>*<%=propGroup.getPropNum()%>
</anchor>
<br/>
<%
		}
	} 
%>
<%=item_page.getPageFoot()%>