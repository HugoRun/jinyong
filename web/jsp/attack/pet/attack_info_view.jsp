<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
    import="java.util.*,com.ben.dao.petinfo.*,com.ben.vo.petinfo.PetInfoVO,com.ben.dao.info.partinfo.*,com.ben.vo.info.partinfo.PartInfoVO"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
    <%@page import="com.pub.ben.info.*,com.ls.ben.cache.staticcache.pet.*"%>
    <%@page import="com.ls.ben.dao.info.pet.*,com.pm.service.pic.*"%>
    <%@page import="com.ls.web.service.player.RoleService"%>
    <%@page import="com.ls.model.user.RoleEntity"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="act" title="<bean:message key="gamename"/>">
<p>
    <%
        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(session);

        String pPk =  roleInfo.getBasicInfo().getPPk()+"";
        String uPk =  roleInfo.getBasicInfo().getUPk()+"";
        String jumpterm = null;
        if (request.getParameter("jumpterm") != null) {
            jumpterm = request.getParameter("jumpterm");
        } else {
            jumpterm = (String) request.getAttribute("jumpterm");
        }
        String mapid = null;
        if (request.getParameter("mapid") != null) {
            mapid = request.getParameter("mapid");
        } else {
            mapid = (String) request.getAttribute("mapid");
        }
        String petId = request.getParameter("petId");
        String petPk = request.getParameter("petPk");
        PetInfoDAO dao = new PetInfoDAO();
        PetSkillDao petSkillDao = new PetSkillDao();
        PetInfoVO vo = (PetInfoVO) dao.getPetInfoView(petPk);
        PicService picService = new PicService();
        PetSkillCache petCache = new PetSkillCache();

        String petImg1 = picService.getPetPicStr(roleInfo,petPk);
    %>

宠物名称:<%=vo.getPetName()%><br/>
<%=petImg1 %>
宠物昵称:<%=vo.getPetNickname()%><br/>
等级:<%=vo.getPetGrade() %>级<br/>
经验:<%=vo.getPetBenExp() %>/<%=vo.getPetXiaExp() %><br/> 
最小攻击:<%=vo.getPetGjXiao() %><br/>
最大攻击:<%=vo.getPetGjDa() %><br/>
卖出价格:<%=vo.getPetSale() %><br/>
宠物成长率:<%=vo.getPetGrow() %><br/>
五行属性:<%if(vo.getPetWx()==1){ %>金<%}else if(vo.getPetWx()==2){ %>木<%}else if(vo.getPetWx()==3){ %>水<%}else if(vo.getPetWx()==4){ %>火<%}else if(vo.getPetWx()==5){ %>土<%} %><br/>
五行属性值:<%=vo.getPetWxValue() %><br/>
技能1:<%=petCache.getName(vo.getPetSkillOne()) %><br/>
技能2:<%=petCache.getName(vo.getPetSkillTwo()) %><br/>
技能3:<%=petCache.getName(vo.getPetSkillThree()) %><br/>
技能4:<%=petCache.getName(vo.getPetSkillFour()) %><br/>
技能5:<%=petCache.getName(vo.getPetSkillFive()) %><br/>
寿命:<%=vo.getPetLonge() %><br/>
体力:<%=vo.getPetFatigue() %><br/>
状态:<%if(vo.getPetIsBring()==1){ %>参加战斗<%}else{ %>未参加战斗<%} %><br/>
<anchor> 
<go method="post"  href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/attack/pet/attack_info_list.jsp")%>">
<postfield name="mapid" value="<%=mapid%>" />
<postfield name="uPk" value="<%=uPk%>" />
<postfield name="pPk" value="<%=pPk %>" />
<postfield name="jumpterm" value="<%=jumpterm %>" /> 
</go>
返回
</anchor>  
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
