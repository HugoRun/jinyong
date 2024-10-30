package com.ls.web.action.cooperate.yeepay.bill;


import com.ls.ben.vo.cooperate.bill.UAccountRecordVO;
import com.ls.web.service.cooperate.bill.BillService;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CallBackAction extends DispatchAction {


    Logger logger = Logger.getLogger("log.pay");

    /**
     * 应答处理
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        logger.info("##########易宝直接回调############");

        String resultWml = "";

        // 业务类型
        String r0_Cmd = formatString(request.getParameter("r0_Cmd"));
        // 支付结果
        String r1_Code = formatString(request.getParameter("r1_Code"));
        // 商户编号
        String p1_MerId = formatString(request.getParameter("p1_MerId"));
        // 商户订单号
        String rb_Order = formatString(request.getParameter("rb_Order"));
        // 易宝支付交易流水号
        String r2_TrxId = formatString(request.getParameter("r2_TrxId"));
        // 商户扩展信息
        String pa_MP = formatString(request.getParameter("pa_MP"));
        // 支付金额
        String rc_Amt = formatString(request.getParameter("rc_Amt"));
        // 签名数据
        String hmac = formatString(request.getParameter("hmac"));
        logger.info("r0_Cmd:" + r0_Cmd);
        logger.info("r1_Code:" + r1_Code);
        logger.info("p1_MerId:" + p1_MerId);
        logger.info("rb_Order:" + rb_Order);
        logger.info("r2_TrxId:" + r2_TrxId);
        logger.info("pa_MP:" + pa_MP);
        logger.info("rc_Amt:" + rc_Amt);
        logger.info("hmac:" + hmac);

        int record_id = -1;
        try {
            record_id = Integer.parseInt(pa_MP.trim());
        } catch (Exception e) {
            logger.info("PM解析错误");
        }

        BillService billService = new BillService();
        UAccountRecordVO account_record = billService.getAccountRecord(record_id);

        if (account_record == null) {
            logger.info("#####无效ID为：" + pa_MP + "的值记录#####");
        }


        //用户在当乐注册的用户名
        //用户支付的实际金额，单位为元
        //用户支付使用的通道ID，支付通道对照表由当乐提供给商户,详见:表格 3 pc-id（付款渠道）对照表
        //唯一编号,订单号或系统生成的唯一序列号，由游戏厂商生成(防止重复提交)系统中会根据merchant-idgame-idserver-idseq-str唯一匹配数据
        //userName = passport.getUserName();

        //NonBankcardService.verifyCallback(r0_Cmd,r1_Code,p1_MerId,rb_Order,r2_TrxId,pa_MP,rc_Amt,hmac);

        if (r1_Code.equals("1")) {
            account_record.setMoney((int) Double.parseDouble(rc_Amt));//充值金额以回调金额为主
            //logger.info(userName);
            if (billService.accountSuccessNotify(account_record)) {
                logger.info("易宝回调,充值成功;订单号：" + rb_Order);
            }
        } else {
            logger.info("易宝回调,充值失败,错误信息：" + r1_Code + "；订单号：" + rb_Order);
            billService.accountFailNotify(account_record, r1_Code);
        }
        // 应答机制收到支付结果通知时必须回写以"success"开头的字符串
        resultWml = "success";
        request.setAttribute("resultWml", resultWml);
        return mapping.findForward("success");
    }


    String formatString(String text) {
        if (text == null) {
            return "";
        }
        return text;
    }
}