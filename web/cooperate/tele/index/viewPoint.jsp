<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.web.service.mall.MallService"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%> 
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="fail" title="<bean:message key="gamename"/>">
<p>
<%
    MallService ms =new MallService();
        String url="http://202.102.39.11:9088/gameinterface/QueryAaccountBalance";
        String netElementId="888999";
        String custIdType="3";
        String custLabel=(String)request.getSession().getAttribute("teleid");
        String channelId=(String)request.getSession().getAttribute("channel_id");
        String cpId=(String)request.getSession().getAttribute("cpId");
        String transID=cpId+MallService.getDateStr()+"827315";
        String versionId="1_1_2";
        Map<String, String> params=new HashMap<String, String>();
        params.put("msgType", "QueryAaccountBalanceReq");
        params.put("netElementId",netElementId);
        params.put("custIdType",custIdType);
        params.put("custLabel", custLabel);
        params.put("channelId", channelId);
        params.put("transID", transID);
        params.put("versionId", versionId);
        String str= ms.serchPoint(url, params);
        String hint="";
        if("1".equals(str))
        {
    		hint="查询剩余点数失败，请重新操作!";
		}
		else
		{
			hint="亲爱的"+custLabel+",您的剩余点数为："+str;
		}
%>
<%=hint %><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/cooperate/tele/index/pointPage.jsp")%>" method="get"></go>返回上一级</anchor><br/>
</p>
</card>
</wml>
