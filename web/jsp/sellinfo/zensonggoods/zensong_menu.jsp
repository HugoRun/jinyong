<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.constant.Wrap"%>
请选择您要赠送的物品：<br/>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>"> 
	<postfield name="cmd" value="n3" />
	<postfield name="pByPk" value="${pByPk }" /> 
	<postfield name="w_type" value="<%=Wrap.BOOK %>" />  
	</go>
	书卷
	</anchor>  
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">  
	<postfield name="cmd" value="n3" />
	<postfield name="pByPk" value="${pByPk }" />  
	<postfield name="w_type" value="<%=Wrap.CURE %>" />  
	</go>
	药品
	</anchor> 
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="pByPk" value="${pByPk }" />  
	<postfield name="w_type" value="<%=Wrap.EQUIP %>" />  
	</go>
	装备
	</anchor> 
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">  
	<postfield name="cmd" value="n3" />
	<postfield name="pByPk" value="${pByPk }" /> 
	<postfield name="w_type" value="<%=Wrap.REST %>" />  
	</go>
	其他
	</anchor>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">  
	<postfield name="cmd" value="n3" />
	<postfield name="pByPk" value="${pByPk }" /> 
	<postfield name="w_type" value="<%=Wrap.SHOP %>" />  
	</go>
	商城
	</anchor>  
	<br/>