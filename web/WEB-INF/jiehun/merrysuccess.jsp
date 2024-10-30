<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ls.ben.vo.system.UMessageInfoVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%String name = (String)request.getAttribute("name");
String honor = (String)request.getAttribute("honor");
 %>
红娘：恭喜恭喜～你现在和<%=name %>小姐已经正式结为夫妻了，希望你们两个白头偕老、早生贵子！
你获得特殊装备【婚戒(金)】，获得“<%=honor %>”称号
<br/>
 <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
