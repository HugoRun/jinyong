<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<%@ include file="part_info_menu_head.jsp"%>
<%@page import="com.ls.ben.vo.info.partinfo.PartInfoVO,com.ls.model.user.RoleEntity" %>
<%@page import="java.text.DecimalFormat,com.ls.pub.constant.system.SystemConfig"%> 
<%   
		PartInfoVO player = (PartInfoVO) request.getAttribute("player");
		RoleEntity roleInfo = (RoleEntity) request.getAttribute("roleInfo");
		String playerPic = (String) request.getAttribute("playerPic");
		String glory_value = (String) request.getAttribute("glory_value");
		int pk_value = (Integer) request.getAttribute("pk_value");
		DecimalFormat df = new DecimalFormat("0.00");
%>
<%=roleInfo.getFullName()%><br/>
<%=playerPic%>
等级:<%=player.getPGrade()%><br/>
气血值:<%=player.getPHp()%>/<%=player.getPMaxHp()%><br/>
内力值:<%=player.getPMp()%>/<%=player.getPMaxMp()%><br/>
攻击:<%=player.getGjXiao()* SystemConfig.attackParameter%>-<%=player.getGjDa()* SystemConfig.attackParameter%><br/>
防御:<%=player.getFyXiao()* SystemConfig.attackParameter%>-<%=player.getFyDa()* SystemConfig.attackParameter%><br/>
暴击率:<%=df.format(player.getDropMultiple())%><br/>
罪恶值:<%=pk_value%><br/>
氏族声望:<%=glory_value%><br/>
五行攻击:<%if (player.getWx().getJinGj() == 0 && player.getWx().getMuGj() == 0 && player.getWx().getShuiGj() == 0 && player.getWx().getHuoGj() == 0 && player.getWx().getTuGj() == 0) {%>无<%}%>
	<%if (player.getWx().getJinGj() != 0) {%>金+<%=player.getWx().getJinGj()%><%}%>
	<%if (player.getWx().getMuGj() != 0) {%>木+<%=player.getWx().getMuGj()%><%}%>
	<%if (player.getWx().getShuiGj() != 0) {%>水+<%=player.getWx().getShuiGj()%><%}%>
	<%if (player.getWx().getHuoGj() != 0) {%>火+<%=player.getWx().getHuoGj()%><%}%>
	<%if (player.getWx().getTuGj() != 0) {%>土+<%=player.getWx().getTuGj()%><%}%>
	<br/>
五行防御:<%if (player.getWx().getJinFy() == 0 && player.getWx().getMuFy() == 0 && player.getWx().getShuiFy() == 0 && player.getWx().getHuoFy() == 0 && player.getWx().getTuFy() == 0) {%>无<%}%>
	<%if (player.getWx().getJinFy() != 0) {%>金+<%=player.getWx().getJinFy()%><%}%>
	<%if (player.getWx().getMuFy() != 0) {%>木+<%=player.getWx().getMuFy()%><%}%>
	<%if (player.getWx().getShuiFy() != 0) {%>水+<%=player.getWx().getShuiFy()%><%}%>
	<%if (player.getWx().getHuoFy() != 0) {%>火+<%=player.getWx().getHuoFy()%><%}%>
	<%if (player.getWx().getTuFy() != 0) {%>土+<%=player.getWx().getTuFy()%><%}%>
	<br/>
<%@ include file="part_info_menu_foot.jsp"%> 
<%@ include file="/init/templete/game_foot.jsp"%>
