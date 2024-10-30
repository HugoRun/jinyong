<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.model.user.BasicInfo"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="group" title="<bean:message key="gamename"/>">
<p> 
<%
		String b_pk = (String) request.getAttribute("b_pk");
		String a_pk = (String) request.getAttribute("a_pk");
		String notify_type = (String) request.getAttribute("notify_type");
		BasicInfo b_basic_info = (BasicInfo)request.getAttribute("b_basic_info");
		List equip_list = (List)request.getAttribute("equip_list");
		if( b_basic_info!=null )
		{
%>
<%=b_basic_info.getName() %><br/>
性别:<%if (b_basic_info.getSex() == 1) {%>男<%} else if (b_basic_info.getSex() == 2) {%>女<%} else if (b_basic_info.getSex() == 3) {%>人妖<%}%><br/>
等级:<%=b_basic_info.getGrade()%>级<br/>
种族:<%=b_basic_info.getRaceName()%><br/>
帮派:<%=b_basic_info.getFactionName()%><br/>
是否可PK:<%if (b_basic_info.getPkSwitch() == 1) {%>不可PK<%} else if (b_basic_info.getPkSwitch() == 2) {%>可PK<%}%><br/>
PK点数:<%=b_basic_info.getEvilValue()%><br/>
装备:
<%if(equip_list != null && equip_list.size() != 0)
{	
	for (int i = 0; i < equip_list.size(); i++) 
	{
	PlayerEquipVO partEquipVO = (PlayerEquipVO) equip_list.get(i);
%><%=partEquipVO.getFullName()%>
<%	}
}%>
<br/>  
<anchor> 
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do?cmd=n4")%>">
<postfield name="a_pk" value="<%=a_pk %>"/>
<postfield name="b_pk" value="<%=b_pk %>"/>
<postfield name="notify_type" value="<%=notify_type %>"/>
</go>
同意组队
</anchor>
<%
	}
	else
	{
%>对方已不离线<%
	}
%>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
