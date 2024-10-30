<%@ page pageEncoding="UTF-8" isErrorPage="false"%>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@ page import="com.ben.rank.model.RankConstants"%>
<c:choose>
<c:when test="${roleInfo.isRookie==false}">
<anchor>物<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/wrap.do?cmd=n1&amp;w_type=3")%>" method="get"></go></anchor>
|<anchor>状<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/stateaction.do?cmd=n1")%>" method="get"></go></anchor>
|<anchor>任<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/taskinfoaction.do?cmd=n1")%>" method="get"></go></anchor>
|<anchor>氏<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=index")%>" method="get"></go></anchor><br/>

<anchor>商<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mall.do?cmd=n0")%>" method="get"></go></anchor>
|<anchor>拍<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/jsp/auction/auctionMainPage/auction_main_shi.jsp")%>" method="get"></go></anchor>
|<anchor>骑<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mounts.do?cmd=n15")%>" method="get"></go></anchor>
|<anchor>奖${hortHint}<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/horta.do?cmd=n1")%>" method="get"></go></anchor><br/>


<anchor>聊<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/communioninfoaction.do?cmd=n1")%>" method="get"></go></anchor>
|<anchor>友<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/friendaction.do?cmd=n2")%>" method="get"></go></anchor>
|<anchor>仇<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/hite.do?cmd=n1")%>" method="get"></go></anchor>
|杀<br/>

<anchor>练<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mounts.do?cmd=n19")%>" method="get"></go></anchor>
|<anchor>队<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/group.do?cmd=n2")%>" method="get"></go></anchor>
|<anchor>活<go href="<%=response.encodeURL(GameConfig.getContextPath() + "/sysnotify.do?cmd=n2")%>" method="get"></go></anchor>
|<anchor>排<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.SHENBING %>" /></go></anchor><br/>

<anchor>系<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/syssetting.do?cmd=index")%>" method="get"></go></anchor>
|<anchor>图<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/viewpic.do?cmd=n1")%>" method="get"></go></anchor>
|<anchor>助<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/help.do?cmd=n1")%>" method="get"></go></anchor>
|<anchor>邮${mail_hint }<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mail.do?cmd=n1")%>" method="get"></go></anchor><br/>
</c:when>





<c:otherwise>
<anchor>物<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/wrap.do?cmd=n1&amp;w_type=3")%>" method="get"></go></anchor>
|<anchor>状<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/stateaction.do?cmd=n1")%>" method="get"></go></anchor>
|<anchor>任<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/taskinfoaction.do?cmd=n1")%>" method="get"></go></anchor>
氏<br/>

商
|拍
|骑
|奖<br/>


<anchor>聊<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/communioninfoaction.do?cmd=n1")%>" method="get"></go></anchor>
|<anchor>友<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/friendaction.do?cmd=n2")%>" method="get"></go></anchor>
|<anchor>仇<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/hite.do?cmd=n1")%>" method="get"></go></anchor>
|杀<br/>
练
|队
|活
|排<br/>

<anchor>系<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/syssetting.do?cmd=index")%>" method="get"></go></anchor>
|<anchor>图<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/viewpic.do?cmd=n1")%>" method="get"></go></anchor>
|<anchor>助<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/help.do?cmd=n1")%>" method="get"></go></anchor>
|邮<br/>

</c:otherwise>
</c:choose>