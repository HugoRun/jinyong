<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" import="java.util.*,com.ls.pub.util.*,com.dp.vo.phb.*,com.ls.pub.config.GameConfig"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
	<card id="map" title="<s:message key = "gamename"/>">
	  	<p> 
	  		小说排行榜:<br/>
	  		 -----------------------<br/>
	  		<%
	  			Integer phpage=Integer.parseInt(request.getAttribute("phpage")+"");
	  			Integer phpagecount=Integer.parseInt(request.getAttribute("phpagecount")+"");
	  			List phlist=(List)request.getAttribute("phlist");
	  			if(phlist!=null&&phlist.size()!=0){
	  				Iterator iter=phlist.iterator();
	  				while(iter.hasNext()){
	  					PhVO ph=(PhVO)iter.next();
	  					%>
	  					  <anchor>
				  	    	<go method="post" href="<%=GameConfig.getContextPath()%>/bookMenu.do">
				  	    		 <postfield name="cmd" value="n3"/>
				  	    		 <postfield name="typeid" value="<%=ph.getTypeid()%>"/>
				  	    		 <postfield name="tipsign" value="6"/>
				  	    	</go>[<%=StringUtil.isoToGB(ph.getTypename())+"  "%>]
				  	      </anchor>
				  	       <anchor>
				  	    	<go method="post" href="<%=GameConfig.getContextPath()%>/bookMenu.do">
				  	    		<postfield name="cmd" value="n5"/>
	 							<postfield name="bookid" value="<%=ph.getBookid()%>"/>
	 							<postfield name="tipsign" value="6"/>
				  	    	</go><%=StringUtil.isoToGB(ph.getBookname())+"  "%>
				  	      </anchor>
	  						收藏次数为<%=ph.getScount()%>次!<br/>
	  					<%
	  				}
	  			}
	  		 %>
	  	   <% if(phpage==1&&phpagecount>1){%>
		  	   <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n14"/>
	  	    		<postfield name="phpage" value="<%=phpage+1%>"/>
	  	    	</go>下一页
	  	       </anchor>
	  	       <%}else if(phpage==phpagecount&&phpagecount>1){ %>
	  	       <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n14"/>
	  	    		<postfield name="phpage" value="<%=phpage-1%>"/>
	  	    	</go>上一页
	  	       </anchor>
	  	       <%}else if(phpage>1&&phpage<phpagecount){%>
	  	        <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n14"/>
	  	    		<postfield name="phpage" value="<%=phpage-1%>"/>
	  	    	</go>上一页
	  	       </anchor>
	  	       <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n14"/>
	  	    		<postfield name="phpage" value="<%=phpage+1%>"/>
	  	    	</go>下一页
	  	       </anchor>
  	      	  <%}if(phpagecount!=0){%>
  	      	 	 第<%=phpage%>/<%=phpagecount%>页
  	      	  <%}else{%>排行榜暂空!<%}%><br/>
  	        <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n1"/>
	  	    	</go>返回书城
	  	     </anchor><br/>
  	        -----------------------<br/>
  	       报时:<%=new Date().toLocaleString()%>
	   	</p>
    </card>
</wml>
