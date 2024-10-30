<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ls.ben.vo.system.UMessageInfoVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%String jiehunle = (String)request.getAttribute("jiehunle");
if(jiehunle!=null&&!"".equals(jiehunle.trim())){
 %>
 红娘：<%=jiehunle %>
 <%}else { %>
红娘：唉～原来你一穷二白，回头多多努力，去赚足老婆本再来吧！
<%} %>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
