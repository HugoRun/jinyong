<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig,com.ls.pub.util.DateUtil,java.util.*"%>
<br/>
<anchor>返回游戏<go href="<%=response.encodeURL(GameConfig.getContextPath() + "/pubbuckaction.do")%>" method="get" /></anchor><br/>
--------------------<br/>
报时:<%=DateUtil.getMainPageCurTimeStr()%>