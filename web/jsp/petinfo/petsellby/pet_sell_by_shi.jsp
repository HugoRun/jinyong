<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.info.partinfo.*,com.ben.vo.info.partinfo.PartInfoVO,com.ben.dao.petinfo.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>  
	<%  
	    String uPk = (String)session.getAttribute("uPk");
		String pPk = request.getParameter("pPk");
		String pByPk = request.getParameter("pByPk");
		String petId = request.getParameter("petId");
		String psSilverMoney = request.getParameter("psSilverMoney");
		String psCopperMoney = request.getParameter("psCopperMoney");
		String psPk = request.getParameter("psPk");
		PartInfoDAO daos = new PartInfoDAO();
		PartInfoVO vo = (PartInfoVO) daos.getPartView(pPk);
		
		int yz = Integer.parseInt(vo.getPSilver()) - Integer.parseInt(psSilverMoney);
		int tq = Integer.parseInt(vo.getPCopper()) - Integer.parseInt(psCopperMoney);
		PartInfoVO voss = (PartInfoVO) daos.getPartView(pByPk);
		int byyz = Integer.parseInt(voss.getPSilver()) + Integer.parseInt(psSilverMoney);
		int bytq = Integer.parseInt(voss.getPCopper()) + Integer.parseInt(psCopperMoney);
		//一下是执行金钱返回的
		//daos.getUpdateMoney(yz + "", tq + "", pPk);
		//daos.getUpdateMoney(byyz + "", bytq + "", pByPk);
		
		//这个是修改宠物主人 
		PetInfoDAO dd = new PetInfoDAO(); 
		dd.getPetzhuren(pPk,pByPk,petId); 
		dd.getPetSellDelete(psPk);  
		PartInfoVO partInfoVO =(PartInfoVO) daos.getPartTypeListPpk(pPk); 
	  //  response.sendRedirect("../../../partannal.do?mapid="+vo.getPMap()+"&uPk="+uPk+"&pPk="+pPk+"&pName="+partInfoVO.getPName()+"&chair="+request.getParameter("chair"));
		request.getRequestDispatcher("../../../partannal.do?mapid="+vo.getPMap()+"&uPk="+uPk+"&pPk="+pPk+"&pName="+partInfoVO.getPName()+"&chair="+session.getAttribute("checkStr")).forward(request, response); 
				
	%> 
</p>
</card>
</wml>
