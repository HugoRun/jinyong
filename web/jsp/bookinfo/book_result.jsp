<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" import="java.util.*,com.ls.pub.config.GameConfig,com.dp.vo.typevo.*,com.ls.pub.util.*,com.dp.vo.newbook.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
	<card id="map" title="<bean:message key="gamename"/>">
	  	<p> 
	 	小说搜索结果:<br/>
	 	----------------------<br/>
     	<%
     		List infolist=(List)request.getAttribute("searchlist");
     		if(infolist!=null&&infolist.size()!=0){
     			Iterator iter=infolist.iterator();
     			while(iter.hasNext()){
     				  NewInfo info=(NewInfo)iter.next();
     			%> 	 [<anchor><go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	 				 <postfield name="cmd" value="n3"/>
	 				 <postfield name="typeid" value="<%=info.getTypeid()%>"/>
	 				 <postfield name="stipsign" value="9"/>
	 				 </go><%=StringUtil.isoToGB(info.getTypename())%></anchor>] 
	 				 <anchor><go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	 				 <postfield name="cmd" value="n5"/>
	 				 <postfield name="bookid" value="<%=info.getBookid()%>"/>
	 				 <postfield name="stipsign" value="9"/>
	 				 <%=StringUtil.isoToGB(info.getBookname())%></go></anchor>
	 				  <anchor><go method="post" href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	 				 <postfield name="cmd" value="n13"/>
	 				 <postfield name="aut" value="<%=StringUtil.isoToGB(info.getAuthor())%>"/>
	 				 <postfield name="stipsign" value="9"/>
	 				 <%=StringUtil.isoToGB(info.getAuthor())%></go></anchor><br/>
	     			<%
     			}
     		}else{%>
     		抱歉,没有此类小说!
     		<%}
     	 %>
     	 <br/>
     	  <anchor><go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	 		  <postfield name="cmd" value="n19"/>返回上一层</go></anchor><br/>
     	  <anchor><go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	 		  <postfield name="cmd" value="n9"/>返回我的书架</go></anchor><br/>
     	 <anchor>
	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	    		<postfield name="cmd" value="n1"/>
	    	</go>返回书城首页
	     </anchor><br/>
	      ----------------------<br/>
  	       报时:<%=new Date().toLocaleString()%>
  	       </p>
  	    </card>
</wml>
