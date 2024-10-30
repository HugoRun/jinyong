package com.ls.web.action.cooperate.air.bill;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.kong.sns.rest.client.Client;
import net.kong.sns.rest.client.ClientFactory;
import net.kong.sns.rest.client.model.PayRegOrderResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.dao.cooparate.bill.UAccountRecordDao;
import com.ls.ben.vo.cooperate.bill.UAccountRecordVO;
import com.ls.pub.constant.Channel;
import com.ls.web.service.validate.ValidateService;

public class BillAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.pay");

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String uPk = (String) request.getSession().getAttribute("uPk");
		String pPk = (String) request.getSession().getAttribute("pPk");
		String sessionKey = request.getParameter("SessionKey");

		String kbamt_str = request.getParameter("kbamt");// �û��ύ�۷ѽ��

		ValidateService validateService = new ValidateService();
		String hint = validateService
				.validateNonZeroNegativeIntegers(kbamt_str);

		if (hint != null)
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("index");
		}

		int kbamt = Integer.parseInt(kbamt_str);

		UAccountRecordDao dao = new UAccountRecordDao();
		// ���ɶ���
		UAccountRecordVO order = new UAccountRecordVO();
		order.setUPk(Integer.parseInt(uPk));
		order.setPPk(Integer.parseInt(pPk));
		order.setChannel(Channel.AIR + "");
		order.setMoney(kbamt);
		order.setAccountState(1 + "");
		order.setId(dao.insert(order));

		long orderId = order.getId();
		long amount = kbamt;
		String desc = "";
		String remark1 = "";
		String remark2 = "";

		Client client;
		try
		{
			client = ClientFactory.createInstance(sessionKey);

			// ��ʽ��
			PayRegOrderResponse result = client.pay_regOrder(orderId, amount,
					PayRegOrderResponse.PayType.K_GOLD, desc, remark1, remark2);
			// ������
			// Pay4TestRegOrderResponse result =
			// client.pay4Test_regOrder(orderId,amount,
			// Pay4TestRegOrderResponse.PayType.K_GOLD, desc,, remark2);
			if (result != null)
			{
				String account_state = "";
				String token = result.getToken();
				String code = result.getCode();
				logger.info("uPk=" + uPk + ";pPk=" + pPk);
				logger.info("֧�������" + result.getCode());
				logger.info("֧��token��" + result.getToken());
				logger.info("������Ϊ��" + orderId + "");

				if (token != null && !token.equals(""))
				{
					account_state = token;
					request.setAttribute("hint", hint);
					request.setAttribute("orderId", orderId);
					request.setAttribute("token", token);
					return mapping.findForward("submit");
				}
				else
				{
					account_state = code;

					if (code.equals("100101"))
					{
						hint = "����";
					}
					else
						if (code.equals("100100"))
						{
							hint = "K���ʻ�������";
						}
						else
							if (code.equals("100102"))
							{
								hint = "K���ʻ�������";
							}
							else
								if (code.equals("100010"))
								{
									hint = "֧��������";
								}
								else
									if (code.equals("100003"))
									{
										hint = "�����Ѵ���";
									}
									else
										if (code.equals("100004"))
										{
											hint = "token�ظ�";
										}
										else
											if (code.equals("100005"))
											{
												hint = "session_key��ʱ";
											}
											else
												if (code.equals("100020"))
												{
													hint = "�һ��ѳ��������������,ÿ��ÿ����߿ɶһ�15K��";
												}
												else
												{
													hint = "�����쳣������";
												}
				}

				dao.update(order.getId(), account_state);// ����״̬
			}
			else
			{
				hint = "�����쳣������";
			}
		}
		catch (IOException e)
		{
			hint = "�����쳣������";
			e.printStackTrace();
		}
		request.setAttribute("hint", hint);
		return mapping.findForward("result");
	}
}