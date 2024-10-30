<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.pm.vo.auctionpet.AuctionPetVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
	<%@page import="com.ls.ben.dao.info.pet.*,com.ls.ben.cache.staticcache.pet.*"%>
	<%@page import="com.pm.dao.auctionpet.*,java.text.*" %>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%   
	    String sortType = (String)request.getAttribute("sortType");
		String petId =(String) request.getAttribute("petId");
		String petPk = (String) request.getAttribute("petPk");
		AuctionPetDao dao = new AuctionPetDao();
		PetSkillDao petSkillDao = new PetSkillDao();
		PetSkillCache petCache = new PetSkillCache();
		AuctionPetVO vo = (AuctionPetVO) dao.getPetInfoView(petPk);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	%> 
	拍卖价格:<%=vo.getPetPrice() %>
	<br/>
	宠物名称:<%=vo.getPetName()%>
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
	
	技能:<%if(vo.getPetSkillOne() != 0 || vo.getPetSkillTwo() != 0 || vo.getPetSkillThree() != 0 || 
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
	拍卖时间:<%=df.format(df.parse(vo.getPetAuctionTime())) %>
	<br/>
			<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>">
			<postfield name="cmd" value="n3" /> 
			<postfield name="petPk" value="<%=request.getAttribute("petPk")%>" />
			</go>
			拍买
			</anchor>
	<br/>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>">
	<postfield name="cmd" value="n4" />  
	<postfield name="page_no" value="<%=request.getAttribute("page_no") %>" />
	<postfield name="sortType" value="<%=request.getAttribute("sortType") %>" />
	<postfield name="pet_name" value="<%=request.getAttribute("pet_name") %>" />
	</go>
	返回
	</anchor>  
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
