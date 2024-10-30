<%@page contentType="text/vnd.wap.wml"  pageEncoding="UTF-8"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%
	PlayerEquipVO partEquipVO1 = (PlayerEquipVO) request.getAttribute("partWrapVO");
	if( partEquipVO1.getWHp() > 0 )
	{
%>增加气血:<%= partEquipVO1.getWHp()%><br/><%
	}
	if( partEquipVO1.getWMp() > 0 )
	{
%>增加内力:<%= partEquipVO1.getWMp()%><br/><%
	}
%>