<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.petinfo.*,com.ben.vo.petinfo.PetInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
	<%@page import="com.pub.ben.info.*,com.ls.ben.cache.staticcache.pet.*"%>
	<%@page import="com.ls.ben.dao.info.pet.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p> 
	<%   
	    String petId =(String) request.getAttribute("petId");
		String petPk = (String) request.getAttribute("petPk");
		PetInfoDAO dao = new PetInfoDAO();
		PetSkillDao petSkillDao = new PetSkillDao();
		PetInfoVO vo = (PetInfoVO) dao.getPetInfoView(petPk);
		PetSkillCache petCache = new PetSkillCache();
	%>
	宠物名称:<%=vo.getPetName()%>
	<br/>
	宠物昵称:<%=vo.getPetNickname()%>
	<br/>
	等级:<%=vo.getPetGrade() %>级
	<br/>
	经验:<%=vo.getPetBenExp() %>/<%=vo.getPetXiaExp() %>
	<br/> 
	攻击:<%=vo.getPetGjXiao() %>-<%=vo.getPetGjDa() %>
	<br/>
	卖出价格:<%=vo.getPetSale() %>
	<br/>
	宠物成长率:<%=vo.getPetGrow() %>
	<br/>
	<%%>
	技能:<%
		if(vo.getPetSkillOne() != 0 || vo.getPetSkillTwo() != 0 || vo.getPetSkillThree() != 0 || 
				vo.getPetSkillFour() != 0 ||  vo.getPetSkillFive() != 0){
				out.println(petCache.getName(vo.getPetSkillOne())
					+ petCache.getName(vo.getPetSkillTwo())
					+ petCache.getName(vo.getPetSkillThree())
					+ petCache.getName(vo.getPetSkillFour())
					+ petCache.getName(vo.getPetSkillFive())); 
			} else {
				out.println("无");
			
			} %>
	
	<br/>
	寿命:<%=vo.getPetLonge() %>/<%=vo.getPetLife() %>
	<br/>
	体力: <%=vo.getPetFatigue() %>
	<br/>
	状态:<%if(vo.getPetIsBring()==1){ %>参加战斗<%}else{ %>未参加战斗<%} %>
	<br/>
	<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/auctionpet/setPetPrice.jsp")%>">
			<postfield name="petPk" value="<%=vo.getPetPk()%>" />
			</go>
			拍卖
			</anchor><br/>
<anchor> <go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetSell.do?cmd=n1")%>"></go>返回</anchor>  
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
