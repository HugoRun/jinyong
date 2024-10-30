<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" import="java.util.*,com.ls.pub.config.GameConfig,com.ls.pub.util.*,com.dp.vo.mybookvo.*,com.dp.vo.infovo.*,com.dp.vo.bzjvo.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
	<card id="map" title="<s:message key = "gamename"/>">
	  	<p> 
	  		*****我的书架*****<br/>
	  		您目前已经收藏<%=Integer.parseInt(request.getAttribute("bookcount")+"")%>本小说，共可收藏5本小说！<br/>
	  		<%
	  			List bmlist=(List)request.getAttribute("mylist");
	  			Iterator iter=bmlist.iterator();
	  			while(iter.hasNext()){
	  				BookMeVO bm=(BookMeVO)iter.next();
	  				%>
	  				[<anchor>
				   <go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
					  <postfield name="cmd" value="n3"/>
					  <postfield name="typeid" value="<%=bm.getTypeid()%>"/>
					  <postfield name="tipsign" value="8"/>
				   </go><%=StringUtil.isoToGB(bm.getTypename())%>
				   </anchor> ]
				   <anchor><go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	 				 <postfield name="cmd" value="n5"/>
	 				 <postfield name="bookid" value="<%=bm.getBookid()%>"/>
	 				 <postfield name="tipsign" value="8"/>
	 				 <%=StringUtil.isoToGB(bm.getBookname())%></go></anchor>
				    <anchor>
						<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
							<postfield name="cmd" value="n11"/>
							<postfield name="myid" value="<%=bm.getMyid()%>"/>
						</go>删除
				    </anchor><br/>
				    更新章节<anchor>
						<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
						<postfield name="cmd" value="n7"/>
						<postfield name="zjid" value="<%=bm.getNewzjcount()%>"/>
						<postfield name="bookid" value="<%=bm.getBookid()%>"/>
						<postfield name="tipsign" value="3"/>
						</go>第<%=bm.getNewzjcount()%>章<%="  "+StringUtil.isoToGB(bm.getZjname())%>
					 </anchor><br/>
				    书签<%String[] marks=bm.getBookmark().split(","); 
				    	if(marks[0].equals("0")){%>
				    		未知
				    	<%			    	
				    	}else{
				    	%>
				    	<anchor>
						<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
						<postfield name="cmd" value="n17"/>
						<postfield name="bookid" value="<%=bm.getBookid()%>"/>
						<postfield name="zjid" value="<%=marks[0]%>"/>
						<postfield name="page" value="<%=marks[1]%>"/>
						<postfield name="tipsign" value="8"/>
						</go>第<%=marks[0]%>章的第<%=marks[1]%>页
					 	</anchor>
				    	<%}%><br/>
				    <%}%><br/>
	  		  小说名快搜:<br/><input type="text" name="searchsign" maxlength="8" size="8"/>
		      <anchor>
				<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
				<postfield name="cmd" value="n12"/>
				<postfield name="novename" value="$searchsign" />
				<postfield name="roleid" value="<%=Integer.parseInt(request.getAttribute("roleid")+"")%>"/>
				<postfield name="tipsign" value="8"/>
				</go>确定
			  </anchor><br/>
			   <anchor>
	  	    	<go method="post"  href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n19"/>
	  	    	</go>返回上一层
	  	     </anchor><br/>
		  	   <anchor>
	  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
	  	    		<postfield name="cmd" value="n1"/>
	  	    	</go>返回书城首页
	  	       </anchor><br/>
	  	  --------------------------------<br/>
  	       报时:<%=new Date().toLocaleString()%>
	  	</p>
    </card>
</wml>
