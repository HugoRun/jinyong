<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.dp.vo.credit.*,com.ls.pub.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%@ include file="part_info_menu_head.jsp"%>
	(<%=StringUtil.isoToGB(request.getAttribute("ppname")+"")%>)所得声望:<br/>
	<% List pcvlist=(List)request.getAttribute("pcvlist");
		if(pcvlist!=null&&pcvlist.size()!=0){
			Iterator iter=pcvlist.iterator();
			while(iter.hasNext()){
				PlayerCreditVO pcv=(PlayerCreditVO)iter.next();
				%>
				  <anchor> 
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/credit.do")%>">	
					<postfield name="cmd" value="n2"/>
					<postfield name="cid" value="<%=pcv.getPcid()%>"/>
					</go>
					<%=StringUtil.isoToGB(pcv.getCreditname())%>
				   </anchor>:<%=pcv.getPcount()%><br/>
				<%
			}
		}else{
			%>
			暂无声望!<br/>
			<%
		}
	%>
<%@ include file="part_info_menu_foot.jsp"%>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>