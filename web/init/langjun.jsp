<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.menu.OperateMenuVO"%>
<%@page import="java.util.*"%>
<%@page import="com.ben.langjun.util.LangjunConstants"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.ben.cache.dynamic.manual.user.RoleCache"%>
<%@page import="com.ls.pub.util.StringUtil"%>
<%@page import="com.ben.langjun.util.LangjunUtil"%>
<%@page import="com.ls.web.service.player.RoleService"%>
<%@page import="com.ls.ben.vo.map.SceneVO"%>
<%
    String new_scene_id = request.getAttribute("new_scene_id")+"";
    RoleService roleService1122 = new RoleService();
    RoleEntity roleInfo1122 = roleService1122.getRoleInfoBySession(request.getSession());
    int my_p_pk1122 = roleInfo1122.getBasicInfo().getPPk();
%>
<%if(LangjunConstants.LANGJUN_PPK!=0&&(Integer.parseInt((request.getSession().getAttribute("pPk")+"").trim())!=LangjunConstants.LANGJUN_PPK)){

if(LangjunUtil.isQianEff(my_p_pk1122)){
RoleEntity roleInfo1123 = roleService1122.getRoleInfoById(LangjunConstants.LANGJUN_PPK+"");
if(roleInfo1123!=null){
SceneVO sv = roleInfo1123.getBasicInfo().getSceneInfo();
%>
千里眼：【千面郎君】目前正躲在<%=sv.getMap().getMapName() %>的<%=sv.getSceneName() %><br/>
<%}}
boolean yinshenview = com.ben.shitu.model.DateUtil.checkSecond(LangjunConstants.LANGJUN_YINSHEN, LangjunConstants.YINSHEN_CANNOTVIEW_SECOND);
boolean chushiView = com.ben.shitu.model.DateUtil.checkSecond(LangjunConstants.LANGJUN_TIME, LangjunConstants.LANGJUN_CANNOTVIEW_SECOND);

if(chushiView&&(yinshenview||LangjunUtil.isEff(my_p_pk1122))){
RoleEntity lang = new RoleCache().getByPpk(LangjunConstants.LANGJUN_PPK);
if(new_scene_id!=null&&lang!=null&&lang.getBasicInfo().getSceneId().trim().equals(new_scene_id.trim())){
 %>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">
<postfield name="cmd" value="n13" />
<postfield name="pPks" value="<%=lang.getBasicInfo().getPPk() %>" />
</go>
<%=LangjunConstants.LANGJUN_NAME%>
</anchor>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/pk.do")%>">
<postfield name="cmd" value="n3" />
<postfield name="aPpk" value="<%=request.getSession().getAttribute("pPk")%>" />
<postfield name="bPpk" value="<%=LangjunConstants.LANGJUN_PPK%>" />
<postfield name="tong" value="0" />
</go>
攻击
</anchor>
<br />
<%}}} %>

<%
%>