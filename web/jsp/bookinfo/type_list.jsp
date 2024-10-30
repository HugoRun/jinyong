<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml"> 
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" import="java.util.*,com.ls.pub.config.GameConfig,com.dp.vo.typevo.*,com.ls.pub.util.*,
com.dp.vo.newbook.*,com.pub.ben.info.*,com.ls.web.service.player.*,com.ls.model.user.*"%>  
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
  <p> 
  	<% RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);%>
  	<%=roleInfo.getBasicInfo().getName()%>,欢迎来到书城!<br/>
  	 分类 
  	 <anchor>
	 <go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	 <postfield name="cmd" value="n14"/>排行
	 </go>
	 </anchor>
  	 <anchor>
  	 <go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
  	 <postfield name="cmd" value="n9"/>书架
  	 </go>
  	 </anchor><br/>
	 小说搜索(请输入小小说名称):<br/>
	 <input type="text" name="searchsign" maxlength="8" />
      <anchor>
		<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
		<postfield name="cmd" value="n2"/>
		<postfield name="novename" value="$searchsign" />
		<postfield name="tipsign" value="0"/>
		</go>确定
	  </anchor><br/>
	  今日更新:<br/>
     	<%
     		List infolist=(List)request.getAttribute("infolist");
     		if(infolist!=null&&infolist.size()!=0){
     			Iterator iter=infolist.iterator();
     			while(iter.hasNext()){
     				  NewInfo info=(NewInfo)iter.next();
	     			%> 
	     			 [<anchor><go method="post" href="<%=GameConfig.getContextPath()%>/bookMenu.do"><postfield name="cmd" value="n3"/><postfield name="typeid" value="<%=info.getTypeid()%>"/><postfield name="tipsign" value="0"/></go><%=StringUtil.isoToGB(info.getTypename())%></anchor>] 
	 				 <anchor><go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	 				 <postfield name="cmd" value="n5"/>
	 				 <postfield name="bookid" value="<%=info.getBookid()%>"/>
	 				 <postfield name="tipsign" value="0"/>
	 				 <%=StringUtil.isoToGB(info.getBookname())%></go></anchor>
	 				 <anchor><go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	 				  <postfield name="cmd" value="n7"/>
	 				  <postfield name="bookid" value="<%=info.getBookid()%>"/>
	 				  <postfield name="zjid" value="<%=info.getZjcount()%>"/>
	 				  <postfield name="tipsign" value="0"/>
	 				  第<%=info.getZjcount()%>章 <%=StringUtil.isoToGB(info.getZjname())%>
	 				  </go></anchor>
	     			 (<%=info.getAuthor() %>) 
	     			 <br/>
	     			<%
     			}
     			%>
     			更多
     			<%
     		}else{%>
     		  暂无！
     		<%
     		}
     	 %>
      <br/> 
	  <anchor> 
		<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
			<postfield name="cmd" value="n16"/>
		</go>退出书城
	  </anchor> <br/>
	  ------------------------<br/>
	   报时:<%=new Date().toLocaleString()%>
     </p>
     </card>
</wml>
