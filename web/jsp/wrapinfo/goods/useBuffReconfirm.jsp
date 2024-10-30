<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.dao.info.buff.BuffDao,com.ls.ben.vo.info.buff.*"%>  
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="ID" title="<bean:message key="gamename"/>">
<p> 
<%
	String pg_pk = (String)request.getAttribute("pg_pk");
	String goods_id = (String)request.getAttribute("goods_id");
	String goods_type = (String)request.getAttribute("goods_type");
	int buff_id = (Integer)request.getAttribute("buff_id");
	BuffDao buffdao = new BuffDao();
	BuffVO buffvo = buffdao.getBuff(buff_id);
 %>
您已经有了相同类型的状态:<%=buffvo.getBuffName() %>, 还需要再使用道具吗？<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do") %>">
<postfield name="cmd" value="n3" />
<postfield name="pg_pk" value="<%=pg_pk %>" />
<postfield name="goods_id" value="<%=goods_id %>" />
<postfield name="goods_type" value="<%=goods_type %>" />
<postfield name="reconfirm" value="1" />
</go>
使用
</anchor><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do") %>">
<postfield name="cmd" value="n1" />
<postfield name="pg_pk" value="<%=pg_pk %>" />
<postfield name="goods_id" value="<%=goods_id %>" />
<postfield name="goods_type" value="<%=goods_type %>" />
</go>
不使用
</anchor><br/>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
