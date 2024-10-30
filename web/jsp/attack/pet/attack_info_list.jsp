<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
    import="java.util.*,com.ben.dao.petinfo.*,com.ben.vo.petinfo.PetInfoVO"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="act" title="<bean:message key="gamename"/>">
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
        PetInfoDAO dao = new PetInfoDAO();
        List list = dao.getPetInfoList(pPk);
        for(int i = 0 ; i < list.size(); i++){
        PetInfoVO vo = (PetInfoVO)list.get(i);     %>
         <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/attack/pet/attack_info_view.jsp")%>">
    <postfield name="mapid" value="<%=mapid%>" />
    <postfield name="jumpterm" value="<%=jumpterm %>" />
    <postfield name="petPk" value="<%=vo.getPetPk() %>" />
    <postfield name="petId" value="<%=vo.getPetId()  %>" />
    </go>
    <%=vo.getPetNickname() %><br/>
    </anchor>
    <%} %>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do?cmd=n4")%>"></go>返回</anchor><br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
