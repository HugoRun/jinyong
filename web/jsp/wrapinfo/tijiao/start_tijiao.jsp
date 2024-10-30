<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"  "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*"%>
<%@page import="com.ls.pub.bean.*"%>
<%@page import="java.util.*,com.ben.vo.info.partinfo.PartInfoVO"%>
<%@page import="com.ls.ben.cache.staticcache.prop.PropCache"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	
	<%
	String hint = (String)request.getAttribute("hint");
		String menu_id = (String)request.getAttribute("menu_id");
		int di = (Integer)request.getAttribute("di");
		String[] props = (String[])request.getAttribute("props");
		PropCache propCache = new PropCache();
		if (hint != null && !hint.equals("")) 
				out.println(hint+"<br/>");
	%>
	您现有<%out.println(propCache.getPropNameById(Integer.parseInt(props[0]))+","+propCache.getPropNameById(Integer.parseInt(props[1]))
				+","+propCache.getPropNameById(Integer.parseInt(props[2]))); %>大字<%=di %>套，请输入您要上交的数量：
	<br/>
	<input name="number" maxlength="6" />
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/tijiao.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="number" value="$number" />
	<postfield name="menu_id" value="<%=menu_id %>" />
	</go>
	提交
	</anchor> 
	
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
