<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.dao.info.partinfo.*,com.ls.pub.util.*"%>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="group" title="<s:message key = "gamename"/>">
<p>
<%
	String notify_type = request.getParameter("notify_type");
	String a_pk = request.getParameter("a_pk");
	String b_pk = request.getParameter("b_pk"); 
	String chair = request.getParameter("chair"); 
	PartInfoDao infodao = new PartInfoDao();
	String name = infodao.getNameByPpk(Integer.valueOf(b_pk));
	if (a_pk != null && b_pk != null) {
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do?cmd=n10")%>"> 
<postfield name="a_pk" value="<%=a_pk %>" />
<postfield name="b_pk" value="<%=b_pk %>" />
<postfield name="notify_type" value="<%=notify_type %>" />
<postfield name="chair" value="<%=chair %>" />
</go>
<%=StringUtil.isoToGBK(name) %>
</anchor>
玩家向您提出组队申请<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/group/beggroup/beg_group_page_ok.jsp")%>"> 
<postfield name="a_pk" value="<%=a_pk %>" />
<postfield name="b_pk" value="<%=b_pk %>" />
<postfield name="notify_type" value="<%=notify_type %>" />
<postfield name="chair" value="<%=chair %>" />
</go>
同意组队
</anchor>
<%	}
	else
	{
		out.print("空指针");
	}
%>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
