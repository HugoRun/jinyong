<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" import="java.util.*,com.ls.pub.config.GameConfig,com.ls.pub.util.*,com.dp.vo.newbook.*,com.dp.vo.infovo.*,com.dp.vo.bzjvo.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
	<card id="map" title="<s:message key = "gamename"/>">
	  	<p> 
	  	   <%
	  	   	  String bookname=(String)request.getAttribute("bookname");
	  	      String zjname=(String)request.getAttribute("zjname");
	  	      Integer zjcount=Integer.parseInt(request.getAttribute("zjcount")+"");
	  	      String line=(String)request.getAttribute("neirong");
	  	      Integer bookid=Integer.parseInt(request.getAttribute("bookid")+"");
	  	      Integer zjpage=Integer.parseInt(request.getAttribute("page")+"");
	  	      Integer pagecount=Integer.parseInt(request.getAttribute("pagecount")+"");
	  	      Integer zjgross=Integer.parseInt(request.getAttribute("zjgross")+"");
	  	   %>
	  	   <%=StringUtil.isoToGB(bookname)%><br/>
	  	   第<%=zjcount%>章  <%=StringUtil.isoToGB(zjname)%><br/>
	  	   <%=StringUtil.isoToGB(line)%><br/>
	  		<% if(zjpage==1&&pagecount>1){%>
    	 		 上一页 <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n17"/>
	  	    		<postfield name="bookid" value="<%=bookid%>"/>
	  	    		<postfield name="zjid" value="<%=zjcount%>"/>
	  	    		<postfield name="page" value="<%=zjpage+1 %>"/>
	  	    	</go>下一页
	  	       </anchor>
			   <%}else if(zjpage==pagecount&&pagecount>1){%>
			  <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n17"/>
	  	    		<postfield name="bookid" value="<%=bookid%>"/>
	  	    		<postfield name="zjid" value="<%=zjcount%>"/>
	  	    		<postfield name="page" value="<%=zjpage-1%>"/>
	  	       </go>上一页
	  	       </anchor>下一页
			   <%}else if(pagecount==1){%>
			   	  上一页 下一页
			   <%}else{%>
				<anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n17"/>
	  	    		<postfield name="bookid" value="<%=bookid%>"/>
	  	    		<postfield name="zjid" value="<%=zjcount%>"/>
	  	    		<postfield name="page" value="<%=zjpage-1%>"/>
	  	       </go>上一页
	  	       </anchor>
		  	   <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n17"/>
	  	    		<postfield name="bookid" value="<%=bookid%>"/>
	  	    		<postfield name="zjid" value="<%=zjcount%>"/>
	  	    		<postfield name="page" value="<%=zjpage+1 %>"/>
	  	    	</go>下一页 </anchor>
			 <%}%>
			第<%=zjpage%>/<%=pagecount%>页<br/>
			
			<% if(zjcount==1&&zjgross>1){%>
    	 		 上一章 <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n7"/>
	  	    		<postfield name="bookid" value="<%=bookid%>"/>
	  	    		<postfield name="zjid" value="<%=zjcount+1 %>"/>
	  	    		<postfield name="tipsign" value="1"/>
	  	    	</go>下一章
	  	       </anchor>
			   <%}else if(zjcount==zjgross&&zjgross>1){%>
			   <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n7"/>
	  	    		<postfield name="bookid" value="<%=bookid%>"/>
	  	    		<postfield name="zjid" value="<%=zjcount-1 %>"/>
	  	    		<postfield name="tipsign" value="-1"/>
	  	       </go>上一章
	  	       </anchor>下一章
			   <%}else if(zjgross==1){%>
			   	  上一章 下一章
			   <%}else{%>
				<anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n7"/>
	  	    		<postfield name="bookid" value="<%=bookid%>"/>
	  	    		<postfield name="zjid" value="<%=zjcount-1 %>"/>
	  	    		<postfield name="tipsign" value="-1"/>
	  	       </go>上一章
	  	       </anchor>
		  	   <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n7"/>
	  	    		<postfield name="bookid" value="<%=bookid%>"/>
	  	    		<postfield name="zjid" value="<%=zjcount+1 %>"/>
	  	    		<postfield name="tipsign" value="1"/>
	  	    	</go>下一章
	  	       </anchor>
			 <%}%> 第<%=zjcount%>/<%=zjgross%>章<br/>
  	       <anchor>
  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
  	    		<postfield name="cmd" value="n15"/>
  	    		<postfield name="bookid" value="<%=bookid%>"/>
  	    		<postfield name="zjid" value="<%=zjcount%>"/>
  	    	</go>设为书签
  	       </anchor><br/>
  	        <anchor>
	  	    	<go method="post"  href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n19"/>
	  	    	</go>返回上一层
	  	     </anchor><br/>
    	  	<anchor><go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	 		 <postfield name="cmd" value="n9"/>返回我的书架</go></anchor><br/>
  	       <anchor>
  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
  	    		<postfield name="cmd" value="n1"/>
  	    	</go>返回书城首页
  	       </anchor><br/>
  	       ---------------------------------<br/>
  	       报时:<%=new Date().toLocaleString()%>
	   	</p>
    </card>
</wml>
