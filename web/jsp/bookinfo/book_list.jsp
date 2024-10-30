<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" import="java.util.*,com.ls.pub.util.*,com.dp.vo.newbook.*,com.ls.pub.config.GameConfig"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
	<card id="map" title="<s:message key = "gamename"/>">
	  	<p> 
	  		<%
	  		 Integer tpage=Integer.parseInt(request.getAttribute("typepage")+"");
	  		 Integer tpcount=Integer.parseInt(request.getAttribute("tpcount")+"");
	  		 String typename=(String)request.getAttribute("typename");
	  		 Integer typeid=Integer.parseInt(request.getAttribute("typeid")+"");
	  		%>
	  	    ***<%=StringUtil.isoToGB(typename).trim()+"小说专区"%>***<br/>
	  	     ----------------------<br/>
	  	     <anchor>
				<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
				<postfield name="cmd" value="n4"/>
				<postfield name="typeid" value="<%=typeid%>" />
				<postfield name="tipsign" value="5"/>
				</go><%=StringUtil.isoToGB(typename).trim()+"小说收藏榜"%>
			 </anchor><br/>
			 ----------------------<br/>
	  		<%
     		List infolist=(List)request.getAttribute("searchlist");
     		if(infolist!=null&&infolist.size()!=0){
     			Iterator iter=infolist.iterator();
     			while(iter.hasNext()){
     				  NewInfo info=(NewInfo)iter.next();
	     			%>
	     			<anchor><go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	 				 <postfield name="cmd" value="n5"/>
	 				 <postfield name="bookid" value="<%=info.getBookid()%>"/>
	 				 <postfield name="tipsign" value="5"/>
	 				 <%=StringUtil.isoToGB(info.getBookname())%></go></anchor>
					  最新章节:
	     			 <anchor>
						<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
						<postfield name="cmd" value="n7"/>
						<postfield name="bookid" value="<%=info.getBookid()%>"/>
	 				    <postfield name="zjid" value="<%=info.getZjcount()%>"/>
	 				    <postfield name="tipsign" value="4"/>
						</go>第<%=info.getZjcount()%>章 <%=" "+StringUtil.isoToGB(info.getZjname())%>
					  </anchor>
	     			<br/>
	     			<%
     			}
     	 	%><br/>
     	 	 <% if(tpage==1&&tpcount>1){%>
		  	   <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n3"/>
	  	    		<postfield name="typepage" value="<%=tpage+1%>"/>
	  	    	</go>下一页
	  	       </anchor>
	  	       <%}else if(tpage==tpcount&&tpcount>1){ %>
	  	       <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n3"/>
	  	    		<postfield name="typepage" value="<%=tpage-1%>"/>
	  	    	</go>上一页
	  	       </anchor>
	  	       <%}else if(tpage>1&&tpage<tpcount){%>
	  	        <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n3"/>
	  	    		<postfield name="typepage" value="<%=tpage-1%>"/>
	  	    	</go>上一页
	  	       </anchor>
	  	       <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n3"/>
	  	    		<postfield name="typepage" value="<%=tpage+1%>"/>
	  	    	</go>下一页
	  	       </anchor>第<%=tpage%>/<%=tpcount%>页<%}%>
  	      	  <%}else{%>暂无此类小说!<%}%><br/>
     	 	 小说名快搜:<br/><input type="text" name="searchsign" size="10"/>
		      <anchor>
				<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
				<postfield name="cmd" value="n8"/>
				<postfield name="novename" value="$searchsign" />
				<postfield name="typeid" value="<%=typeid%>"/>
				<postfield name="tipsign" value="5"/>
				</go>确定
			  </anchor>
			  <br/>
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
