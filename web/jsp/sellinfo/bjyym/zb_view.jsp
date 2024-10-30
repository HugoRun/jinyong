<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.dao.wrapinfo.*,com.ls.pub.constant.system.SystemConfig" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.*"%>
<%@page import="com.ls.ben.vo.goods.AccouteVO"%> 
<%@page import="com.ls.ben.vo.goods.ArmVO"%>
<%@page import="com.ls.ben.vo.goods.JewelryVO"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		String jumpterm = null;
		if (request.getParameter("jumpterm") != null) {
			jumpterm = request.getParameter("jumpterm");
		} else {
			jumpterm = (String) request.getAttribute("jumpterm");
		}
		String pPk = null;
		if (request.getParameter("pPk") != null) {
			pPk = request.getParameter("pPk");
		} else {
			pPk = (String) request.getAttribute("pPk");
		}
		String mapid = null;
		if (request.getParameter("mapid") != null) {
			mapid = request.getParameter("mapid");
		} else {
			mapid = (String) request.getAttribute("mapid");
		}
		String uPk = null;
		if (request.getParameter("uPk") != null) {
			uPk = request.getParameter("uPk");
		} else {
			uPk = (String) request.getAttribute("id");
		}
	%>
	<%
		String fl = request.getParameter("fl");
		String zbid = request.getParameter("zbid");
		EntityDAO entityDAO = new EntityDAO();
		ZhuangBeiDAO zhuangBeiDAO = new ZhuangBeiDAO(); 
	%> 
	[图片] 
	<br/>
	<%  AccouteVO accouteVO=null;
	    ArmVO armVO=null; 
	    JewelryVO jewelryVO=null;
	    if (Integer.parseInt(fl) == 1) { 
	    accouteVO = (AccouteVO) zhuangBeiDAO.getAccouteView(zbid);
	    }
		if (Integer.parseInt(fl) == 2) { 
		armVO = (ArmVO) zhuangBeiDAO.getArmView(zbid);
	    }
		if (Integer.parseInt(fl) == 3) { 
		jewelryVO = (JewelryVO) zhuangBeiDAO.getJewelry(zbid);
		}
		if (Integer.parseInt(fl) == 1) { 
	%>
	<%=entityDAO.getAccoutName(Integer.parseInt(zbid))%> 
	<%
		}
		if (Integer.parseInt(fl) == 2) { 
	%>
	<%=entityDAO.getArmName(Integer.parseInt(zbid))%>
	<%
		}
		if (Integer.parseInt(fl) == 3) { 
	%>
	<%=entityDAO.getJewelryName(Integer.parseInt(zbid))%>
	<%
		}
	%>
	<br/>  
	<%if (Integer.parseInt(fl) == 1) {  %>
	<%=accouteVO.getAccDisplay() %>
	<%} if (Integer.parseInt(fl) == 2) { %>
	<%=armVO.getArmDisplay() %>
	<%} if (Integer.parseInt(fl) == 3) { %>
	<%=jewelryVO.getJewDisplay() %>
	<%} %>
	<br/>
	------------------------
	<br/>
	<%if (Integer.parseInt(fl) == 1) {  %>
	耐久:<%=accouteVO.getAccDurability() %>
	<%} if (Integer.parseInt(fl) == 2) { %>
	耐久:<%=armVO.getArmDurability() %>
	<%} if (Integer.parseInt(fl) == 3) { %>
	耐久:<%=jewelryVO.getJewDurability() %>
	<%} %> 
	<br/>
	<%if (Integer.parseInt(fl) == 1) {  %>
	防御:<%=accouteVO.getAccDefXiao() %>-<%=accouteVO.getAccDefDa() %>
	<%} if (Integer.parseInt(fl) == 2) { %>
	防御:无
	<%} if (Integer.parseInt(fl) == 3) { %>
	防御:<%=jewelryVO.getJewDefXiao() %>-<%=jewelryVO.getJewDefDa() %>
	<%} %>  
	<br/>
	<%if (Integer.parseInt(fl) == 1) {  %>
	攻击:无
	<%} if (Integer.parseInt(fl) == 2) { %>
	攻击:<%=armVO.getArmAttackXiao() %>-<%=armVO.getArmAttackDa() %>
	<%} if (Integer.parseInt(fl) == 3) { %>
	攻击:<%=jewelryVO.getJewAttackXiao() %>-<%=jewelryVO.getJewAttackDa() %>
	<%} %>  
	<br/>
	特殊技能: 
	<%if (Integer.parseInt(fl) == 3&&jewelryVO.getJewSkill()!=null) { %>
	<%=entityDAO.getSkillName(jewelryVO.getJewSkill())%>
	<%} else{%>
	无
	<%} %> 
	<br/>
	------------------------
	<br/> 
	卖出价格:
     <%if (Integer.parseInt(fl) == 1) {  %>
	 <%=accouteVO.getAccSell() %>文
	 <%} if (Integer.parseInt(fl) == 2) { %>
	 <%=armVO.getArmSell() %>文
	 <%} if (Integer.parseInt(fl) == 3) { %>
	 <%=jewelryVO.getJewSell() %>文
	 <%} %>  
	<br/>
	使用等级:
     <%if (Integer.parseInt(fl) == 1) {  %>
	 <%=accouteVO.getAccReLevel() %> 级
	 <%} if (Integer.parseInt(fl) == 2) { %>
	 <%=armVO.getArmLevel() %> 级
	 <%} if (Integer.parseInt(fl) == 3) { %>
	 <%=jewelryVO.getJewReLevel() %> 级
	 <%} %>  
	<br/>
	性别要求:
    <%if (Integer.parseInt(fl) == 1) {  %>
	<%if(accouteVO.getAccSex()==1){ %>男<%}else if(accouteVO.getAccSex()==2){ %>女<%}else if(accouteVO.getAccSex()==3){ %>人妖<%}else{ %>无<%} %>
	<%} if (Integer.parseInt(fl) == 2) { %>
	无
	<%} if (Integer.parseInt(fl) == 3) { %>
	<%if(jewelryVO.getJewSex()==1){ %>男<%}else if(jewelryVO.getJewSex()==2){ %>女<%}else if(jewelryVO.getJewSex()==3){ %>人妖<%}else{ %>无<%} %>
	<%} %>  
	<br/>
	<%if (Integer.parseInt(fl) == 1) {  %>
	 <%if(accouteVO.getAccJob().equals("shaolin")){ %>
	 少林
	 <%}else if(accouteVO.getAccJob().equals("gaibang")){ %>
	 丐帮
	 <%}else if(accouteVO.getAccJob().equals("mingjiao")){ %>
	 明教
	 <%}else {%>
	   无
	 <%} %>
	 <%} if (Integer.parseInt(fl) == 2) { %>
	  <%if(armVO.getArmJob().equals("shaolin")){ %>
	 少林
	 <%}else if(armVO.getArmJob().equals("gaibang")){ %>
	 丐帮
	 <%}else if(armVO.getArmJob().equals("mingjiao")){ %>
	 明教
	 <%} %>  
	 <%} if (Integer.parseInt(fl) == 3) { %>
	 <%if(jewelryVO.getJewJob().equals("shaolin")){ %>
	 少林
	 <%}else if(jewelryVO.getJewJob().equals("gaibang")){ %>
	 丐帮
	 <%}else if(jewelryVO.getJewJob().equals("mingjiao")){ %>
	 明教
	 <%} %> 
	 <%} %>
	<br/>
	是否绑定:
     <%if (Integer.parseInt(fl) == 1) {  %>
	 <%if(accouteVO.getAccBonding()==1){ %>不绑定<%} else if(accouteVO.getAccBonding()==2){%>拾取绑定<%} else if(accouteVO.getAccBonding()==3){%>装备绑定<%} else{ %>无<%} %>
	 <%} if (Integer.parseInt(fl) == 2) { %>
	 <%if(armVO.getArmBanding()==1){ %>不绑定<%}else if(armVO.getArmBanding()==2){ %>拾取绑定<%} else{ %>无<%} %>
	 <%} if (Integer.parseInt(fl) == 3) { %>
	 <%if(jewelryVO.getJewBonding()==1){ %>不绑定<%}else if(jewelryVO.getJewBonding()==2){ %>拾取绑定<%} else{ %>无<%} %>
	 <%} %> 
	<br/>
	是否保护:
     <%if (Integer.parseInt(fl) == 1) {  %>
	 否
	 <%} if (Integer.parseInt(fl) == 2) { %>
	 <%if(armVO.getArmProtect()==1){ %>不保护<%}else if(armVO.getArmProtect()==2){ %>保护<%} %>
	 <%} if (Integer.parseInt(fl) == 3) { %>
	 <%if(jewelryVO.getJewProtect()==1){ %>不保护<%}else if(jewelryVO.getJewProtect()==2){ %>保护<%} %> 
	 <%} %>  
	 <br/>
	 
	  <anchor><prev />返回</anchor>
</p>
</card>
</wml>
