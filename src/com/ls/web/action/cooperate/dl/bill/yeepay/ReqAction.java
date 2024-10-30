package com.ls.web.action.cooperate.dl.bill.yeepay;

import com.ls.ben.vo.cooperate.bill.UAccountRecordVO;
import com.ls.pub.config.GameConfig;
import com.ls.pub.yeepay.nonbankcard.NonBankcardPaymentResult;
import com.ls.pub.yeepay.nonbankcard.NonBankcardService;
import com.ls.web.service.cooperate.bill.BillService;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;


/**
 * @author ls
 * 功能:神州行充值
 * Jan 12, 2009
 */
public class ReqAction extends DispatchAction {

    Logger logger = Logger.getLogger("log.pay");

    /**
     * 充值
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String p_pk = (String) session.getAttribute("pPk");
        String u_pk = (String) session.getAttribute("uPk");

        if (p_pk == null || u_pk == null) {
            logger.info("session中已无有效的pPk和uPk");
            return null;
        }

        BillService billService = new BillService();

        String resultWml = null;

        try {
            request.setCharacterEncoding("GBK");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        // 商户订单号
        String addtime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        String order_id = "017001" + addtime;
        //String p2_Order = formatString(request.getParameter("p2_Order"));
        String p2_Order = order_id;
        // 订单金额
        String p3_Amt = formatString(request.getParameter("pay"));

        // 卡号
        String pa7_cardNo = formatString(request.getParameter("code"));

        // 卡密码
        String pa8_cardPwd = formatString(request.getParameter("psw"));


        // 具体通道
        String pd_FrpId = formatString(request.getParameter("pd_FrpId"));

        // 交易成功通知地址
        String p8_Url = formatString("http://189hi.cn/orderrcv/yeepayszx_feed.do");//正式的神州行地址
        if (pd_FrpId.equals("SNDACARD")) {
            p8_Url = "http://189hi.cn/orderrcv/yeepaysnda_feed.do";//正式的盛大地址
        }
        //"http://219.239.94.130:8080/orderrcv/yeepaysnda_feed.do"                             //正式的盛大地址
        //String p8_Url = formatString("http://203.86.68.248:8888/orderrcv/yeepayszx_feed.do");//测试地址

        //记录玩家充值信息
        UAccountRecordVO account_record = new UAccountRecordVO();
        account_record.setUPk(Integer.parseInt(u_pk));
        account_record.setPPk(Integer.parseInt(p_pk));
        account_record.setCode(pa7_cardNo);
        account_record.setPwd(pa8_cardPwd);
        account_record.setMoney(Integer.parseInt(p3_Amt));
        account_record.setChannel(pd_FrpId);
        account_record.setAccountState("发送充值请求");

        logger.info("易宝(" + pd_FrpId + ")通道");
        int record_id = billService.account(account_record);

        String area_id = GameConfig.getAreaId();

        // 商户扩展信息
        //String pa_MP = formatString(request.getParameter("pa_MP"));
        String pa_MP = area_id + "and" + record_id;

        try {
            NonBankcardPaymentResult rs = NonBankcardService.pay(p2_Order, p3_Amt, p8_Url, pa_MP, pa7_cardNo, pa8_cardPwd, pd_FrpId);
            //充值的成功
            if ("1".equals(rs.getR1_Code())) {
                resultWml = billService.getSuccessHint();
                //resultWml = resultWml+"你将得到"+p3_Amt+"个元宝<br/>";
            } else {
                logger.info("易宝(" + pd_FrpId + ")通道提交充值请求：提交充值请求失败,错误代码" + rs.getR1_Code());
                resultWml = billService.getFailHint(rs.getR1_Code());

                //out.println("请重试<a href='"+response.encodeURL("index.jsp")+"'>返回充值</a><br/>");
            }
            billService.updateState(record_id, rs.getR1_Code());

            /* 	该方法是根据《易宝支付非银行卡支付专业版接口文档 v3.0》生成一个模拟的交易结果通知串.
             * 	商户使用模拟的交易结果通知串可以直接测试自己的交易结果接收程序(callback)的正确性.
             * 	实际的交易结果通知机制以《易宝支付非银行卡支付专业版接口文档 v3.0》为准，该方法只是
             * 	模拟了交易结果通知串.正式上线时请不要调用此方法.
             */

            //NonBankcardService.generationTestCallback(p2_Order,p3_Amt,p8_Url,pa7_cardNo,pa8_cardPwd,pa_MP);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("易宝(" + pd_FrpId + ")通道提交充值请求：充值失败,错误代码" + e);
            resultWml = billService.getFailHint(e.toString());
            billService.updateState(record_id, e.toString());
        }
        //logger.info(resultWml);
        request.setAttribute("resultWml", resultWml);
        return mapping.findForward("success");
    }

    private String formatString(String text) {
        if (text == null) {
            return "";
        }
        return text;

    }
}