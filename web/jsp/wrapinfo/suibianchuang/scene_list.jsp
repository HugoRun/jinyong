<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"  "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*,com.pub.*"%>
<%@page import="com.ls.pub.bean.*,com.pm.vo.chuansong.*"%>
<%@page import="java.util.*,com.ben.vo.info.partinfo.PartInfoVO"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	
	<%
		List<SuiBianChuanVO> list = (List<SuiBianChuanVO>)request.getAttribute("list");
		String pg_pk = (String)request.getAttribute("pg_pk");
		// 传送区域类型
		String type = (String)request.getAttribute("type");
		SuiBianChuanVO suiBianChuanVO = null;
		
		
		//分页显示初始化 
		int no = 0;//当前页数
		int pageSize = 7;//每页最多显示条数
		int allSize = 1;//总记录数
		if (list != null && list.size() > 0) {
			allSize = list.size();
		}		
		String pageNo = request.getParameter("pageNo");
		if(pageNo == null || pageNo.equals("") || pageNo.equals("null")){
			pageNo = (String)request.getAttribute("pageNo");
		}
		PageBean pageb = new PageBean();
        pageb.init(pageNo,pageSize,allSize,response.encodeURL(GameConfig.getContextPath()+"/suibianchuan.do?cmd=n2&amp;type="+type+"&amp;pg_pk="+pg_pk));
		no = pageb.getIndex();
		
		if (list != null && list.size() != 0) {
		 for(int i = (no - 1)*pageSize; i < no * pageSize&&i < list.size() ; i++){
			//for (int i = 0; i < list.size(); i++) {
				suiBianChuanVO =  list.get(i);
		
		
		%>
		
			<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/suibianchuan.do?cmd=n3")%>" >
			<postfield name="carryId" value="<%=suiBianChuanVO.getCarryId() %>" />
			<postfield name="pg_pk" value="<%=pg_pk %>" />
			</go>
			<%=suiBianChuanVO.getSceneName() %>(<%=suiBianChuanVO.getCarryGrade()+"级"  %>)
			</anchor>
			<br/>
		<%}
		}
		else
		{}
		
		if ( pageb.getFooter().equals("") ) {
 			out.print(pageb.getIndex()+"/"+pageb.getPageNum()+"<br/>"); 
 		} else  {
 			out.print(pageb.getFooter()+"<br/>"+pageb.getIndex()+"/"+pageb.getPageNum()+"<br/>"); 
 		}
	 		
	%>
	<br/>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n1") %>"></go></anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
