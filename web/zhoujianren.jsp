<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.langjun.util.LangjunConstants"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.ben.cache.dynamic.manual.user.RoleCache"%>
<%@page import="com.ls.web.service.player.PlayerService"%>
<%@page import="com.ls.web.service.player.RoleService"%>
<%@page import="com.ben.shitu.model.DateUtil"%>
<%@page import="com.ben.lost.LostConstant"%>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="timeout" title="<s:message key = "gamename"/>">
<p>
 <%String message = (String)request.getAttribute("message"); %>
<%=message==null?"":message+"<br/>" %>
<%=DateUtil.tranStringTime() %><br/>
<%if(LangjunConstants.LAST_LANGJUN_PPK!=0){ 
RoleEntity re1 = new RoleCache().getByPpk(LangjunConstants.LAST_LANGJUN_PPK);
if(re1!=null){
%>
上一轮千面郎君为 ： <%=re1.getBasicInfo().getRealName() %><br/>
所在场景：<%=re1.getBasicInfo().getSceneInfo().getSceneName() %><br/>

<%}else{%>
上一轮千面郎君<%=new RoleService().getName(LangjunConstants.LAST_LANGJUN_PPK+"")[0] %>已下线<br/>
<%} %>
<%
}else{ %>
上一轮没有千面郎君<br/>
<%} %>


<%if(LangjunConstants.LANGJUN_PPK!=0){ 
RoleEntity re = new RoleCache().getByPpk(LangjunConstants.LANGJUN_PPK);
if(re!=null){
%>
当前的千面郎君为 ： <%=re.getBasicInfo().getRealName() %><br/>
所在场景：<%=re.getBasicInfo().getSceneInfo().getSceneName() %><br/>

<%}else{%>
当前的千面郎君<%=new RoleService().getName(LangjunConstants.LANGJUN_PPK+"")[0] %>已下线<br/>
<%} %>
<%
}else{ %>
当前没有千面郎君<br/>
<%} %>
<input type="text" name="name" /><br/>
千面郎君活动结束时间(格式：2009-09-24 12:12:12):<br/>
<input type="text" name="langjuntime"  /><br/>
<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/langjun.do?cmd=n2")%>">
<postfield name="name" value="$(name)" />
<postfield name="langjuntime" value="$(langjuntime)"/>
</go>
开始千面郎君活动</anchor><br/>	
<br/>	
迷宫倒计时<br/>
<%if(LostConstant.LOST_END_TIME!=null){ %>
迷宫结束时间：<%=DateUtil.getDate(LostConstant.LOST_END_TIME) %><br/>
<%} %>
迷宫将于
<input type="text" name="losthour" fotmat="*N" maxlength="2"  size="4"/>时
<input type="text" name="lostmin" fotmat="*N" maxlength="2" size="4"/>分
<input type="text" name="lostsec" fotmat="*N" maxlength="2" size="4"/>秒
之后结束
<br/>
<input type="text" name="myname" /><br/>
<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/langjun.do?cmd=n22")%>">
<postfield name="myname" value="$(myname)" />
<postfield name="losthour" value="$(losthour)"/>
<postfield name="lostmin" value="$(lostmin)"/>
<postfield name="lostsec" value="$(lostsec)"/>
</go>
确定</anchor>
</p>
</card>
</wml>