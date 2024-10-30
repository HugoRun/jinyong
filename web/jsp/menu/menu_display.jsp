<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ben.vo.task.UTaskVO,java.util.List"%>
<%@page import="com.web.service.*,com.pm.service.pic.PicService"%>
<%@page import="com.ls.pub.constant.GoodsType"%> 
<%@page import="com.web.service.taskpage.*"%> 
<%@page import="com.ls.web.service.player.RoleService"%> 
<%@page import="com.ls.model.user.RoleEntity"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
	<%
		RoleService roleService = new RoleService();
		RoleEntity roleInfoMenuDisplay = roleService.getRoleInfoBySession(request.getSession());
		TaskPageService taskPageService = new TaskPageService();
		
		int p_pk = roleInfoMenuDisplay.getBasicInfo().getPPk();
		String resultWml = (String) request.getAttribute("resultWml");
		String menu_id = (String) request.getAttribute("menu_id");
		String task_id = taskPageService.regroupTaskId((String) request.getAttribute("task_id"));
		String menu_type = (String) request.getAttribute("menu_type");
		String duihua = (String) request.getAttribute("duihua");
		String task_title = (String) request.getAttribute("task_title");
		String taskXiaYiBu = (String) request.getAttribute("taskXiaYiBu");
		List tMidstGs = (List) request.getAttribute("tMidstGs"); 
		String sg = (String) request.getAttribute("sg");
		String xd = (String) request.getAttribute("xd");
		String xw = (String) request.getAttribute("xw");
		String fh = (String) request.getAttribute("fh");
		TaskService taskService = new TaskService();
		PicService picService = new PicService(); 
		
		////System.out.println("pic="+pic);
		String number = "1";
		
	%> 
	<% 
		if (duihua != null) {
	%> 
	<%
		if (tMidstGs != null && tMidstGs.size() != 0) { 
				for (int p = 0; p < tMidstGs.size(); p++) {
					UTaskVO uTaskVO = (UTaskVO) tMidstGs.get(p);
					
					if (uTaskVO.getTMidstGs() != null && !uTaskVO.getTMidstGs().equals("0") && !uTaskVO.getTMidstGs().equals("")) {
						taskService.getGeiDJService(p_pk, uTaskVO.getTMidstGs(), GoodsType.PROP, 1+"");
					}
					if (uTaskVO.getTMidstZb() != null && !uTaskVO.getTMidstZb().equals("0") && !uTaskVO.getTMidstZb().equals("")) {
						taskService.getGeiZBService(p_pk, uTaskVO.getTMidstZb(), 1+ "");
					}
				}
			}
	%>
	<%=duihua%>
	<%
		if (task_title != null) {
	%>
	<br /><%=task_title%>
	<%
		}
	%>
	<br /><%if(taskXiaYiBu!=null){ %><%=taskXiaYiBu %><% } %>
	<%
		} else if (resultWml != null) {
	%>
	<%  
		if (task_id != null && !task_id.equals("") && Integer.parseInt(menu_type)==1) {
		        String[] cc = task_id.split(",");
		        ////System.out.println("cc.lengthcc.lengthcc.lengthcc.lengthcc.lengthcc.lengthcc.lengthcc.length  "+cc.length); 1,2
		        //for(int i = 0 ; i < cc.length ; i++){ 
		        if(cc.length == 1){
				String ss = taskPageService.taskPageViewService(roleInfoMenuDisplay, cc[0], menu_id,menu_type,sg, xd, xw, fh,request,response);
				%>
				<%=ss%> 
				<%
		        }else if(cc.length > 1){
		        String mapid = roleInfoMenuDisplay.getBasicInfo().getSceneId(); 
		        String ss = taskPageService.gettaskList(task_id, menu_id,menu_type, mapid, sg, xd, xw, fh,request,response);
		        %>
				<%=ss%> 
				<%
		        }
		       // } 
	%>
	<% 
		} else if(task_id != null && !task_id.equals("") && Integer.parseInt(menu_type) > 1){
		 String[] cc = task_id.split(",");
		String pic = picService.getMenuPicStr(roleInfoMenuDisplay,Integer.valueOf(menu_id));
		String ss = taskPageService.taskPageListService(task_id,menu_id,menu_type,sg, xd, xw, fh,request,response);
		if(cc.length == 1){
		%>
		<%=pic%> 
	    <%=resultWml%> 
	    <%=ss%> 
		<% 
		  }else if(cc.length > 1){
		 		String mapid = roleInfoMenuDisplay.getBasicInfo().getSceneId();
		        String sss = taskPageService.gettaskList(task_id, menu_id,menu_type, mapid, sg, xd, xw, fh,request,response);
		
		%>
		<%=pic%> 
	    <%=resultWml%> 
	    <%=sss%> 
		<% 
		}
		}else{
		String pic ="";
		if(roleInfoMenuDisplay.getSettingInfo().getNpcPic()==1)
		{
			pic = picService.getMenuPicStr(roleInfoMenuDisplay,Integer.valueOf(menu_id));
		}
		
	%>   
	<%=pic%>
	<%=resultWml%>
	<%
		}
		}
	%>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
 