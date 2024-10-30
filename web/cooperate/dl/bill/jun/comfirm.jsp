<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" language="java" errorPage="" %>
<%@ page import="com.ls.pub.yeepay.*"%>
<%@page import="com.ls.ben.vo.cooperate.bill.UAccountRecordVO"%>
<%@page import="com.ls.web.service.cooperate.bill.BillService"%>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*" %>
<%@page import="com.ls.pub.config.GameConfig"%>

<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="index" title="充值确认">
<p>
<%
        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(session);

        String checkStr = (String)session.getAttribute("checkStr");
        int u_pk = -1;
        int p_pk = -1;
        if (roleInfo != null) {
            u_pk = roleInfo.getBasicInfo().getUPk();
            p_pk = roleInfo.getBasicInfo().getPPk();
        }

        String merchant_key = "2WE46VM162146qso9tCcg5T3oiEf3659UW71w9V63qQ959R93LR2aw2IT4op";
        String cmd = "BankDirectConnect";
        String merchant_id = "10000469871";
        String addtime = new java.text.SimpleDateFormat("yyyyMMddHHmmss")
                .format(new java.util.Date());
        String order_id = "017001" + addtime;//订单号
        String amount = request.getParameter("pay");
        String currency = "CNY";
        String order_pid = "";
        String order_pcat = "";
        String order_pdesc = "";
        String returl = response.encodeURL(GameConfig.getUrlOfGame()+"/jun/recvOrder.do");//返回游戏的地址
        String pay_cardno = request.getParameter("code");
        String pay_cardpwd = request.getParameter("psw");
        String pm_id = "JUNNET-NET";
        String hmac = "";
		
		//记录玩家充值信息
		BillService billService = new BillService();
		UAccountRecordVO account_record = new UAccountRecordVO();
		account_record.setUPk(u_pk);
		account_record.setPPk(roleInfo.getBasicInfo().getPPk());
		account_record.setCode(pay_cardno);
		account_record.setPwd(pay_cardpwd);
		account_record.setMoney(Integer.parseInt(amount));
		account_record.setChannel("jun");
		account_record.setAccountState("发送充值请求");
		
		int record_id = billService.account(account_record);
		
		String area_id = GameConfig.getAreaId();
		
		String ext = area_id+"and"+record_id;
		
		String[] hmacArgs = {
				cmd,
				merchant_id,
				order_id,
				amount,
				currency,
				order_pid,
				order_pcat,
				order_pdesc,
				Des3Encryption.encode(merchant_key.substring(0, 24),
						pay_cardno),
				Des3Encryption.encode(merchant_key.substring(0, 24),
						pay_cardpwd), ext, pm_id };
		hmac = DigestUtil.getHmac(hmacArgs, merchant_key);
		
		
		//测试地址
		//String url="http://203.86.68.248:8888/orderrcv/yeepayjunnet_feed.do?"+"cmd="+cmd+"&merchant_id="+merchant_id+"&order_id="+order_id+"&amount="+amount+"&currency="+currency+"&order_pid="+order_pid+"&order_pcat="+order_pcat+"&order_pdesc="+order_pdesc;
		//正式地址
		String url = "http://189hi.cn/orderrcv/yeepayjunnet_feed.do?"
				+ "cmd="
				+ cmd
				+ "&merchant_id="
				+ merchant_id
				+ "&order_id="
				+ order_id
				+ "&amount="
				+ amount
				+ "&currency="
				+ currency
				+ "&order_pid="
				+ order_pid
				+ "&order_pcat="
				+ order_pcat
				+ "&order_pdesc="
				+ order_pdesc;
		url = url + "&returl=" + returl + "&pay_cardno=" + pay_cardno
				+ "&pay_cardpwd=" + pay_cardpwd + "&ext=" + ext + "&pm_id="
				+ pm_id + "&hmac=" + hmac;
		////System.out.println("url=" + url);

		if (hmac != null) {
	%>
您输入了<%=amount%>元面值的骏网一卡通充值卡:<br/>
卡号:<%=pay_cardno%><br/>
密码:<%=pay_cardpwd%><br/>
	
	<anchor>
	<go href="http://189hi.cn/orderrcv/yeepayjunnet_feed.do"
		method="post">
	<postfield name="cmd" value="<%=cmd%>" />
	<postfield name="merchant_id" value="<%=merchant_id%>" />
	<postfield name="order_id" value="<%=order_id%>" />
	<postfield name="amount" value="<%=amount%>" />
	<postfield name="currency" value="<%=currency%>" />
	<postfield name="order_pid" value="<%=order_pid%>" />
	<postfield name="order_pcat" value="<%=order_pcat%>" />
	<postfield name="order_pdesc" value="<%=order_pdesc%>" />
	<postfield name="returl" value="<%=returl%>" />
	<postfield name="pay_cardno"
		value="<%=Des3Encryption.encode(merchant_key.substring(0, 24),
								pay_cardno)%>" />
	<postfield name="pay_cardpwd"
		value="<%=Des3Encryption.encode(merchant_key.substring(0, 24),
								pay_cardpwd)%>" />
	<postfield name="ext" value="<%=ext%>" />
	<postfield name="pm_id" value="<%=pm_id%>" />
	<postfield name="hmac" value="<%=hmac%>" />
	</go>
	确定
	</anchor><br/>
<anchor>
<go method="post"  href="<%=GameConfig.getContextPath()%>/cooperate/dl/bill/jun/input.jsp">
<postfield name="money" value="<%=amount %>"/>
</go>重新输入
</anchor><br/>
	<%
		}
	%>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>