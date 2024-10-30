<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" import="java.util.*,com.ls.pub.config.GameConfig,com.ls.pub.util.*,com.dp.vo.newbook.*,com.dp.vo.infovo.*,com.dp.vo.bzjvo.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
	<card id="map" title="<s:message key = "gamename"/>">
	  	<p> 
	  	   <%=request.getAttribute("mess")==null?"":request.getAttribute("mess").toString()%><br/>
	  	   <%BookInfoVO bookinfo=(BookInfoVO)request.getAttribute("bookinfo");%>
	  	   <%=StringUtil.isoToGB(bookinfo.getBookname())%><br/>
	  	   作者:<%=StringUtil.isoToGB(bookinfo.getAuthor())%><br/>
	  	   类别:<%=StringUtil.isoToGB(bookinfo.getTypename()) %><br/>
	  	    <anchor><go method="post" href="<%=GameConfig.getContextPath()%>/bookMenu.do">
			<postfield name="cmd" value="n9"/><postfield name="tipsign" value="7"/></go>打开书架</anchor>
	  	    <anchor>
				<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
				<postfield name="cmd" value="n10"/>
				<postfield name="bookid" value="<%=bookinfo.getBookid()%>"/>
				<postfield name="typeid" value="<%=bookinfo.getTypeid()%>"/>
				<postfield name="tipsign" value="7"/>
				</go>加入书架
			 </anchor><br/>
		   小说内容简介:<br/>
		  <%=StringUtil.isoToGB(bookinfo.getBookline())%><br/>
		  <% NewInfo newinfo=(NewInfo)request.getAttribute("newzjinfo");%>
		     最近更新章节:<anchor>
				<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
				<postfield name="cmd" value="n7"/>
				<postfield name="bookid" value="<%=newinfo.getBookid()%>"/>
				<postfield name="zjid" value="<%=newinfo.getZjcount()%>"/>
				<postfield name="tipsign" value="2"/>
				</go><%=newinfo.getZjname()%>
			 </anchor>(<%=newinfo.getGxdate()%>)<br/>
		  章节:<br/>
	  	  <%
     		List infolist=(List)request.getAttribute("zjinfo");
     		if(infolist!=null&&infolist.size()!=0){
     			Iterator iter=infolist.iterator();
     			while(iter.hasNext()){
     				  BookZJVO info=(BookZJVO)iter.next();
	     			%>
	     			 <anchor>
						<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
						<postfield name="cmd" value="n7"/>
						<postfield name="zjid" value="<%=info.getBookzj()%>"/>
						<postfield name="bookid" value="<%=bookinfo.getBookid()%>"/>
						<postfield name="tipsign" value="2"/>
						</go>第<%=info.getBookzj()%>章:<%=StringUtil.isoToGB(info.getZjname())%>
					 </anchor>
	     			<br/>
	     			<%
     			}
     		}
     	 	%>
     	 	<% 
     	 		Integer zjlistpage=Integer.parseInt(request.getAttribute("zjlistpage")+""); 
     	 		Integer zjlistcount=Integer.parseInt(request.getAttribute("zjlistcount")+"");
     	 		if(zjlistpage==1&&zjlistcount>1){%>
     	 		 上一页<anchor>
					<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
					<postfield name="cmd" value="n5"/>
					<postfield name="bookid" value="<%=bookinfo.getBookid()%>"/>
					<postfield name="page" value="<%=zjlistpage+1%>"/>
					</go>下一页
				 </anchor><br/>
				   <%}else if(zjlistpage==zjlistcount&&zjlistcount>1){%>
				   <anchor>
					<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
					<postfield name="cmd" value="n5"/>
					<postfield name="bookid" value="<%=bookinfo.getBookid()%>"/>
					<postfield name="page" value="<%=zjlistpage-1%>"/>
					</go>上一页
				  </anchor>下一页<br/>
				   <%}else if(zjlistcount==1){%>
				   	  上一页 下一页<br/>
				   <%}else{%>
					<anchor>
					<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
					<postfield name="cmd" value="n5"/>
					<postfield name="bookid" value="<%=bookinfo.getBookid()%>"/>
					<postfield name="page" value="<%=zjlistpage-1%>"/>
					</go>上一页
					 </anchor>
					 <anchor>
						<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
						<postfield name="cmd" value="n5"/>
						<postfield name="bookid" value="<%=bookinfo.getBookid()%>"/>
						<postfield name="page" value="<%=zjlistpage+1%>"/>
						</go>下一页
					 </anchor><br/>
				 <%}%>
			  第<%=zjlistpage%>/<%=zjlistcount%>页跳转到
			  <input type="text" name="pagesign" size="5"/>
			  页<anchor>
				<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
				<postfield name="cmd" value="n5"/>
				<postfield name="bookid" value="<%=bookinfo.getBookid()%>"/>
				<postfield name="page" value="$pagesign"/>
				</go>确定
			 </anchor><br/> 
    	   <anchor>
  	    	<go method="post"   href="<%=GameConfig.getContextPath()%>/bookMenu.do">
  	    		<postfield name="cmd" value="n1"/>
  	    	</go>返回书城首页
  	       </anchor><br/>
  	      ------------------------<br/>
  	       报时:<%=new Date().toLocaleString()%>
	   	</p>
    </card>
</wml>

