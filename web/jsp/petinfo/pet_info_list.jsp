<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.petinfo.*,com.ben.vo.petinfo.PetInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	<%  
	     String pPk =  (String)request.getSession().getAttribute("pPk");
		 String hint = (String)request.getAttribute("hint");
		  PetInfoDAO dao = new PetInfoDAO();
		List list = dao.getPetInfoList(pPk);
	%>
	【宠物栏】<% if(list == null || list.size() == 0 ){%>0<%}else{%><%=list.size() %>
	<%} %>/6
	<%
	if(hint!=null){
	%>
	<br /><%=hint %>
	<%
	}
	 %>
	<%
		if(list != null && list.size()!=0){
		
		
		for(int i = 0 ; i < list.size(); i++){
		PetInfoVO vo = (PetInfoVO)list.get(i);	 %>
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/petinfoaction.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="petPk" value="<%=vo.getPetPk() %>" />
	<postfield name="petId" value="<%=vo.getPetId()  %>" />
	<postfield name="petGrade" value="<%=vo.getPetGrade() %>" />
	<postfield name="petFatigue" value="<%=vo.getPetFatigue() %>" />
	<postfield name="petIsBring" value="<%=vo.getPetIsBring() %>" />
	</go>
	<%=vo.getPetNickname() %>
	</anchor>
	<%if(vo.getPetIsBring()==1){%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/petbattleaction.do")%>">
	<postfield name="petPk" value="<%=vo.getPetPk() %>" />
	<postfield name="petIsBring" value="0" />
	</go>
	取消参战
	</anchor>
	<%} else {%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/petbattleaction.do")%>">
	<postfield name="petPk" value="<%=vo.getPetPk() %>" />
	<postfield name="petIsBring" value="1" />
	<postfield name="petGrade" value="<%=vo.getPetGrade() %>" />
	<postfield name="petFatigue" value="<%=vo.getPetFatigue() %>" />
	</go>
	参加战斗
	</anchor>
	<%} 
	%>
	|<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/petinfoaction.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="petPk" value="<%=vo.getPetPk()%>" />
	<postfield name="petGrade" value="<%=vo.getPetGrade() %>" />
	<postfield name="petFatigue" value="<%=vo.getPetFatigue() %>" />
	<postfield name="petIsBring" value="<%=vo.getPetIsBring() %>" />
	</go>
	遗弃
	</anchor>
	<%
	}}else{%>
	<br />
	暂无宠物
	<%} %>
	<br />
	<anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n1")%>"
		method="get"></go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
