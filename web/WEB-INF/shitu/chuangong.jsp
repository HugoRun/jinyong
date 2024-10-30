<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.shitu.model.ShituConstant"%>
<%@page import="com.ben.vo.info.partinfo.PartInfoVO"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ben.vo.honour.RoleHonourVO"%>
<%@page import="com.ben.shitu.model.Shitu"%>
<%@page import="com.ben.dao.tong.TongMemberDAO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%Object teaEXP = request.getAttribute("teaEXP");
Object stu_id = request.getAttribute("stu_id");
Object id = request.getAttribute("id");
Object stu_name = request.getAttribute("stu_name");
 %>
 你确定将自己的<%=teaEXP %>经验传功给徒弟<%=stu_name %>吗？<br/>
 <anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/shitu.do") %>"> 
	<postfield name="cmd" value="n12" />
	<postfield name="stu_id" value="<%=stu_id%>" />
	<postfield name="id" value="<%=id %>" />
	<postfield name="stu_name" value="<%=stu_name %>" />
	</go>
	确定
</anchor> 
	<br/>
 
 
 
 <anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/shitu.do") %>"> 
	<postfield name="cmd" value="n6" />
	<postfield name="stu_id" value="<%=stu_id%>" />
	<postfield name="id" value="<%=id %>" />
	</go>
	返回
	</anchor> 
	<br/>
	<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
