<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"%>
<%@page import="java.util.*,com.ben.vo.honour.RoleHonourVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>
<%@ include file="/jsp/info/partinfo/part_info_menu_head.jsp"%>
	<%
		List list = (List) request.getAttribute("list");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				RoleHonourVO vo = (RoleHonourVO) list.get(i);
	%>
	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/honour.do")%>">
	<postfield name="cmd" value="n2" />  
	<postfield name="roId" value="<%=vo.getRoId() %>" />  
	</go>
	<%=((vo.getDetail()==null||"null".equals(vo.getDetail().trim()))?"":vo.getDetail())+vo.getRoleHonourName() %>(<%=vo.getRoleHonourTypeName() %>)
	</anchor>|
	<%if(vo.getIsReveal() == 0){ %>
	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/honour.do")%>">
	<postfield name="cmd" value="n3" />  
	<postfield name="roId" value="<%=vo.getRoId() %>" />  
	<postfield name="is_reveal" value="1" />  
	</go>显示</anchor>
	<%}else{ %>
	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/honour.do")%>">
	<postfield name="cmd" value="n3" />  
	<postfield name="roId" value="<%=vo.getRoId() %>" />  
	<postfield name="is_reveal" value="0" />  
	</go>不显示</anchor>
	<%} %>
	<%if(vo.getTimeDisplay() != null){
	%><%=vo.getTimeDisplay()%><%
	}
		%><br/><%}
		} else {
	%>
	暂无称号
	<%
		}
	%>
	<%@ include file="/jsp/info/partinfo/part_info_menu_foot.jsp"%>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
