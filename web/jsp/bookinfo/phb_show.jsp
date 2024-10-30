<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" import="java.util.*,com.ls.pub.config.GameConfig,com.dp.vo.phb.*,com.ls.pub.util.*"%>  
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
	<card id="map" title="<bean:message key="gamename"/>">
	  <p> 
	  	   ***<%=StringUtil.isoToGB(request.getAttribute("typename")+"").trim()+"小说收藏榜"%>***<br/>
	  	   ----------------------<br/>
	  	   <%
	  	   	 Integer typeid=Integer.parseInt(request.getAttribute("typeid")+"");
	  	   	 Integer squpage=Integer.parseInt(request.getAttribute("squpage")+"");
	  	   	 Integer squpagecount=Integer.parseInt(request.getAttribute("squpagecount")+"");
	  	     List novelist=(List)request.getAttribute("phlist");
	  	     if(novelist!=null&&novelist.size()!=0){
	  	     	 Iterator iter=novelist.iterator();
	  	     	 while(iter.hasNext()){
	  	     	 		PhVO ph=(PhVO)iter.next();
	  	     	 		%>
	  	     	 		  <anchor>
				  	    	<go method="post" href="<%=GameConfig.getContextPath()%>/bookMenu.do">
				  	    		 <postfield name="cmd" value="n3"/>
				  	    		<postfield name="typeid" value="<%=ph.getTypeid()%>"/>
				  	    		<postfield name="tipsign" value="10"/>
				  	    	</go>[<%=StringUtil.isoToGB(ph.getTypename())+"  "%>]
				  	      </anchor>
				  	       <anchor>
				  	    	<go method="post" href="<%=GameConfig.getContextPath()%>/bookMenu.do">
				  	    		<postfield name="cmd" value="n5"/>
	 							<postfield name="bookid" value="<%=ph.getBookid()%>"/>
	 							<postfield name="tipsign" value="10"/>
				  	    	</go><%=StringUtil.isoToGB(ph.getBookname())+"  "%>
				  	      </anchor>
	  						收藏次数为<%=ph.getScount()%>次!<br/>
	  	     	 		<%
	  	     	 }
	  	      %>
	  	      <% if(squpage==1&&squpagecount>1){%>
		  	   <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n14"/>
	  	    		<postfield name="phpage" value="<%=squpage+1%>"/>
	  	    	</go>下一页
	  	       </anchor>
	  	       <%}else if(squpage==squpagecount&&squpagecount>1){ %>
	  	       <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n14"/>
	  	    		<postfield name="phpage" value="<%=squpage-1%>"/>
	  	    	</go>上一页
	  	       </anchor>
	  	       <%}else if(squpage>1&&squpage<squpagecount){%>
	  	        <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n14"/>
	  	    		<postfield name="phpage" value="<%=squpage-1%>"/>
	  	    	</go>上一页
	  	       </anchor>
	  	       <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n14"/>
	  	    		<postfield name="phpage" value="<%=squpage+1%>"/>
	  	    	</go>下一页
	  	       </anchor>
	  	       第<%=squpage%>/<%=squpagecount%>页
  	      	  <%}
  	      	   }else{%>此类小说暂无人收藏!<%}%><br/>
 	    	<anchor>
  	    	<go method="post"  href="<%=GameConfig.getContextPath()%>/bookMenu.do">
  	    		<postfield name="cmd" value="n19"/>
  	    	</go>返回
  	   	   </anchor><br/>
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