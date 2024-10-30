<%@page contentType="text/vnd.wap.wml" import="java.util.*"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%
    //穿装备
    String zbsex = (String) request.getAttribute("zbsex");
    String zbdengji = (String) request.getAttribute("zbdengji");
    String zbliliang = (String) request.getAttribute("zbliliang");
    String zbmingjie = (String) request.getAttribute("zbmingjie");
    String zbqixue = (String) request.getAttribute("zbqixue");
    String zbwuxing = (String) request.getAttribute("zbwuxing");
    String zbjiehun = (String) request.getAttribute("zbjiehun");
    String zbmenpai = (String) request.getAttribute("zbmenpai");
    //传武器
    String wqdengji = (String) request.getAttribute("wqdengji");
    String wqliliang = (String) request.getAttribute("wqliliang");
    String wqminjie = (String) request.getAttribute("wqminjie");
    String wqqixue = (String) request.getAttribute("wqqixue");
    String wqwuxing = (String) request.getAttribute("wqwuxing");
    String wqjiehun = (String) request.getAttribute("wqjiehun");
    String wqmenpai = (String) request.getAttribute("wqmenpai");
    //传首饰
    String ssxingbie = (String) request.getAttribute("ssxingbie");
    String ssdengji = (String) request.getAttribute("ssdengji");
    String ssli = (String) request.getAttribute("ssli");
    String ssmin = (String) request.getAttribute("ssmin");
    String ssti = (String) request.getAttribute("ssti");
    String sswu = (String) request.getAttribute("sswu");
    String ssjh = (String) request.getAttribute("ssjh");
    String ssmenpai = (String) request.getAttribute("ssmenpai");
%>
<%
    if (ssxingbie != null) {
%>
您的性别和该首饰不符。
<br />
<%
    }
    if (ssdengji != null) {
%>
您的等级没有达到首饰的要求。
<br />
<%
    }
    if (ssli != null) {
%>
您的力量没有达到首饰的要求。
<br />
<%
    }
    if (ssmin != null) {
%>
您的敏捷没有达到首饰的要求。
<br />
<%
    }
    if (ssti != null) {
%>
您的体魄没有达到首饰的要求。
<br />
<%
    }
    if (sswu != null) {
%>
您的悟性没有达到首饰的要求。
<br />
<%
    }
    if (ssjh != null) {
%>
您的还没有结婚。
<br />
<%
    }
    if (ssmenpai != null) {
%>
您的还没有加入门派。
<br />
<%
    }
    if (zbsex != null) {
%>
您的性别和该装备不符。
<br />
<%
    }
    if (zbdengji != null) {
%>
您的等级没有达到装备的要求。
<br />
<%
    }
    if (zbliliang != null) {
%>
您的力量没有达到装备的要求。
<br />
<%
    }
    if (zbmingjie != null) {
%>
您的敏捷没有达到装备的要求。
<br />
<%
	}
	if (zbqixue != null) {
%>
您的体魄没有达到装备的要求。
<br />
<%
	}
	if (zbwuxing != null) {
%>
您的悟性没有达到装备的要求。
<br />
<%
	}
	if (zbjiehun != null) {
%>
您的还没有结婚。
<br />
<%
	}
	if (zbmenpai != null) {
%>
您的还没有加入门派。
<br />
<%
	}
	if (wqdengji != null) {
%>
您的等级没有达到武器的要求。
<br />
<%
	}
	if (wqliliang != null) {
%>
您的力量没有达到武器的要求。
<br />
<%
	}
	if (wqminjie != null) {
%>
您的敏捷没有达到武器的要求。
<br />
<%
	}
	if (wqqixue != null) {
%>
您的体魄没有达到武器的要求。
<br />
<%
	}
	if (wqwuxing != null) {
%>
您的悟性没有达到武器的要求。
<br />
<%
	}
	if (wqjiehun != null) {
%>
您的还没有结婚。
<br />
<%
	}
	if (wqmenpai != null) {
%>
您的还没有加入门派。
<br />
<%
	}
%>
