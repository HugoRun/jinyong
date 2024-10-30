<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.dao.task.*,com.ben.vo.task.*,com.ls.web.service.player.*,com.pub.ben.info.*,com.ben.*" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig,com.ls.ben.cache.staticcache.equip.*,com.ls.web.service.goods.GoodsService" %> 
<%@page import="com.ls.model.user.*,com.ls.model.property.task.CurTaskInfo,com.ls.ben.cache.staticcache.task.TaskCache,com.ls.pub.util.ExchangeUtil" %>
	
<%
	response.setContentType("text/vnd.wap.wml");
			RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
	
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
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
			String tId =(String) request.getAttribute("tId");
				String tPk = (String) request.getAttribute("tPk");
				GoodsService goodsService = new GoodsService();
				TaskVO vo =(TaskVO) TaskCache.getById(tId);;
				CurTaskInfo UTaskVO = roleInfo.getTaskInfo().getCurTaskList().getById(tPk + "");
				String addressNames[]=null;
				String addressKeys[]=null;
		%>
<%=vo.getTName()%><br/> 
	 <%
	if(vo.getTTishi()!=null && !vo.getTTishi().equals("")){
	%>
	  <%=vo.getTTishi()%>
	  <br/>  
	  <%
	if(vo.getTKey()!=null && !vo.getTKey().equals("") && vo.getTKeyValue()!=null && !vo.getTKeyValue().equals("")){
	String tishi = vo.getTTishi();
	String TKey = vo.getTKey();
	if(TKey == null && TKey.equals("")){
	}else{
	String[] ss = TKey.split("-");
    String[] ee = vo.getTKeyValue().split(","); 
    addressNames=ss;
    addressKeys=ee;
    String qq = null;
		for(int i = 0 ; i < ss.length ;i++){
			if(i == 0){ 
				qq = tishi.replace(ss[i], ss[0] ); 
			} 
			if(i > 0){
				qq= qq.replace(ss[i], ss[1] );
			}
		} 
	  %>
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
	if (vo.getTSw() != null && !vo.getTSw().equals("0") && !vo.getTSw().equals("")) {
	%>
	  声望:<%=vo.getTSw() %><br/> 
	<%
	} 
	%>
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
     String equipId=vo.getTEncouragementZb().trim();
	 %>
装备:<%=EquipCache.getById(Integer.parseInt(equipId)).getName() %> x <%=vo.getTEncouragementNoZb() %><br/> 
	<%} %>
	<%
	if(UTaskVO.getTKillingNo()!=0){
	 %>
	任务完成:<%=UTaskVO.getTKillingOk() %>/<%=UTaskVO.getTKillingNo() %><br/>
	<%} 
	if(UTaskVO.getTKillingNo()!=0&& UTaskVO.getTKillingOk() !=0 && UTaskVO.getTKillingNo()==UTaskVO.getTKillingOk()){ 
	%> 
	任务完成<br/>
	<%} %> 
	<%
	if(UTaskVO.getTGoods() != null && !UTaskVO.getTGoods().equals("") && !UTaskVO.getTGoods().equals("0")){//取出任务中的物品
			String[] goods = UTaskVO.getTGoods().split(",");
			String[] goodsnumber = UTaskVO.getTGoodsNo().split(",");
			for(int gd = 0 ; gd < goods.length ; gd++){ 
			    int dd = goodsService.getPropNum(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(goods[gd]));//取出包裹里的物品数量
				 if(dd <= Integer.parseInt(goodsnumber[gd])){ 
				 %> 
				 任务完成:<%=vo.getTName() %> 
				 （<%=goodsService.getGoodsName(Integer.parseInt(goods[gd]),4) %>） <%=dd %>/<%=goodsnumber[gd] %> <br/>
					<%
		  }else if(dd > Integer.parseInt(goodsnumber[gd])){
		  %> 
				 任务完成 <br/>
		 <%
		  }
		  } 
		 
	   }
	   if(addressNames!=null&&addressKeys!=null)
	   {
	   		for(int i=0;i<addressNames.length;i++)
	   		{
	   		if(i==0){
	   	%>
	   	<anchor>
	<go method="post"  href="<%=response.encodeURL(GameConfig.getContextPath() +"/taskinfoaction.do")%>">
	<postfield name="cmd" value="n7" />
	<postfield name="taskid" value="<%=tId%>" />
	<postfield name="mapid" value="<%=addressKeys[0]%>" />
	</go>
	<%="前往"+addressNames[0]%>
	</anchor>
	<br/>
	<%}
			if(i==1)
			{
	 %>
	<anchor>
	<go method="post"  href="<%=response.encodeURL(GameConfig.getContextPath() +"/taskinfoaction.do")%>">
	<postfield name="cmd" value="n7" />
	<postfield name="taskid" value="<%=tId%>" />
	<postfield name="mapid" value="<%=addressKeys[1]%>" />
	</go>
	<%="前往"+addressNames[1]%>
	</anchor>
	   	<%
	   }}}
	 %> 
	<br/>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do?cmd=n1")%>"/></anchor> 
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
	