<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.task.*,com.ben.vo.task.*,com.ls.web.service.player.*,com.pub.ben.info.*,com.ben.*"
	pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig,,com.ls.ben.cache.staticcache.equip.*" %> 
<%@page import="com.ls.model.user.*,com.ls.model.property.task.CurTaskInfo,com.ls.ben.cache.staticcache.task.TaskCache,com.ls.pub.util.ExchangeUtil" %>
	
<%
	response.setContentType("text/vnd.wap.wml");
    RoleService roleService = new RoleService();
	RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
	
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
【任务】<br/>
	<%
String hint = (String)request.getAttribute("hint");
 %>
 <%if(hint != null){
	%>
	<%=hint %><br/>
	<% 
	} %>
	
	<%
			TaskCache taskCache = new TaskCache();
				String tId =(String) request.getAttribute("tId");
				TaskDAO dao = new TaskDAO();
				UTaskDAO uTaskDAO = new UTaskDAO();
				GoodsService goodsService = new GoodsService();
				EntityDAO entityDAO = new EntityDAO();
				TaskVO vo =(TaskVO) taskCache.getById(tId);;
				TaskDAO taskDAO = new TaskDAO();
		%>
	<%=vo.getTName()%>
	<br/> 
	 <%
	if(vo.getTTishi()!=null && !vo.getTTishi().equals("")){
	if(vo.getTKey()!=null && !vo.getTKey().equals("") && vo.getTKeyValue()!=null && !vo.getTKeyValue().equals("")){
	String tishi = vo.getTTishi();
	String TKey = vo.getTKey();
	if(TKey == null && TKey.equals("")){
	%>
	  <%=tishi %>
	  <br/>  
	  <%
	}else{
	
	String[] ss = TKey.split("-");
    String[] ee = vo.getTKeyValue().split(","); 
    String qq = null;
		for(int i = 0 ; i < ss.length ;i++){
			if(i == 0){ 
				qq = tishi.replace(ss[i], "<anchor><go method=\"post\"  href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do")+"\"><postfield name=\"cmd\" value=\"n7\" /><postfield name=\"taskid\" value=\""+ tId + "\" /><postfield name=\"mapid\" value=\""+ ee[i] + "\" /></go>"+ss[i]+"</anchor>" ); 
			} 
			if(i > 0){
				qq= qq.replace(ss[i], "<anchor><go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do")+"\"><postfield name=\"cmd\" value=\"n7\" /><postfield name=\"taskid\" value=\""+ tId + "\" /><postfield name=\"mapid\" value=\""+ ee[i] + "\" /></go>"+ss[i]+"</anchor>" );
			}
		} 
	  %>
	  <%=qq%>
	  <br/>  
	  <%}} } %> 
	  <%
	  String ss =vo.getTDisplay();  
	  String rr = ss.replaceAll("\\(OWN\\)",""+roleInfo.getBasicInfo().getName()+"");
	 //替换任务说明
			String task_content = ExchangeUtil.getTitleBySex(rr, roleInfo.getBasicInfo().getSex());
	 %> 
	 <%=task_content %>
	 <br/>
	 <%
	if(vo.getTMoney()!=null && !vo.getTMoney().equals("") && !vo.getTMoney().equals("0")){
	%>
	  金钱:<%=vo.getTMoney() %>文<br/> 
	<%} %>
	<%
	if(vo.getTExp()!=null && !vo.getTExp().equals("") && !vo.getTExp().equals("0")){
	%>
	  经验:<%=vo.getTExp() %><br/> 
	<%} %>
	<%
	if(vo.getTEncouragement()!=null && !vo.getTEncouragement().equals("") && !vo.getTEncouragement().equals("0")){
	String[] tGeidjs = vo.getTEncouragement().split(",");
	String[] tGeidjNumbers = vo.getTWncouragementNo().split(",");
	for(int i = 0 ; i < tGeidjs.length ;i++){
	%>
	物品:<%=goodsService.getGoodsName(Integer.parseInt(tGeidjs[i]),4) %> x <%=tGeidjNumbers[i] %><br/> 				
	<%
	}
	%>
	<%} %>
	<%
	if(vo.getTEncouragementZb()!=null && !vo.getTEncouragementZb().equals("") && !vo.getTEncouragementZb().equals("0")){
	%>
	装备:
     <%
     String cc="0";
	 String pp="0";
     String ad[] = vo.getTEncouragementZb().split(",");  
     for(int ow = 0 ; ow < ad.length ; ow++){
     if(ow==0){
	 pp = ad[ow]; //装备ID 
	 }
	 if(Integer.parseInt(cc)==1&&Integer.parseInt(pp)!=0){
	 %>
	 <%=EquipCache.getById(Integer.parseInt(pp)) %> x <%=vo.getTEncouragementNoZb() %><br/> 
	 <% 
	 }
     }
     %> 
	<%} %>
	
	<anchor>
	<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do?cmd=n1")%>"></go>
	返回 
	</anchor> 
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
	