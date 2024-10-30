package com.ls.web.action.wrap.goldbox;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.laba.labaVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.player.PlayerState;
import com.ls.web.service.laba.LaBaService;
import com.ls.web.service.player.RoleService;

public class LaBaAction extends DispatchAction
{

	/** һ�����ʵõ���Ʒ */
	public ActionForward n0(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		RoleService roleService = new RoleService();
		// �õ���ɫ��Ϣ
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		if (roleInfo.getBasicInfo().getWrapSpare() < 2)
		{
			request.setAttribute("display", "�������ռ䲻��,���������,Ԥ��2�����ϸ���!");
			return display(mapping, form, request, response);
		}
		roleInfo.getStateInfo().setCurState(PlayerState.BOX);//������̳�ʱ״̬�ܱ���
		LaBaService lbs = new LaBaService();
		// �õ���ɫID
		String s_p_pk = String.valueOf(roleInfo.getBasicInfo().getPPk());// request.getSession().getAttribute("pPk").toString();
		// ��ñ���ID
		String prop_id = request.getSession().getAttribute("prop_id")
				.toString();
		// ���ݱ���ID�õ�������Ϣ
		PropVO pv = lbs.getGoodByID(prop_id);
		// �������ֶ�һ��õ�npcid�Լ�����
		String s_NPC[] = pv.getPropOperate1().split(",");
		// �󽱵Ľ���ֵ
		int daNum = Integer.parseInt(s_NPC[0].split("-")[1]);
		// �н��Ľ���ֵ
		int zhongNum = Integer.parseInt(s_NPC[1].split("-")[1]);
		// ��ñ��ο��������õĽ�����Ʒ,���ݸ��ʣ������á�npc_vo1��Ϊ������Ʒ
		labaVO lv = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo, 0);
		// �õ�������Ʒ��ID
		String goodId = String.valueOf(lv.getNvo().getGoodsId());
		labaVO lv2 = null;// �ڶ�����Ʒ��ֻ������ʾ��ҳ���ϣ���Ҳ����ô���Ʒ
		labaVO lv3 = null;// ��������Ʒ��ֻ������ʾ��ҳ���ϣ���Ҳ����ô���Ʒ
		// rdNum�����жϽ�����Ʒ�����ڴ󽱡��н����ǹ���(С)����
		// getGoodsName���ֵ�����ı䣬Ϊ����+��ô�(�����н����߹�����)���������
		int rdNum = lv.getSNum();
		// npc_id�����жϣ�����õ����𣬺�����õ���
		int npc_id = lv.getNvo().getNpcID();
		// -------------���ʹ�ú���Ƴ���һ������-----------------------------------
		lbs.RemoveBox(roleInfo, Integer.parseInt(prop_id), Integer
				.parseInt(s_p_pk));// ������ֱ��ɾ��������
		HttpSession otherSession = request.getSession();

		if (rdNum >= 0 && rdNum <= daNum)// �����õĽ�����ƷΪ��
		{
			lv2 = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo,
					(daNum + 1));// ��ô����õ��н���Ʒ��ֻ������ʾ
			lv3 = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo,
					(zhongNum + 1));// ��ô����õ���������Ʒ��ֻ������ʾ
			request.setAttribute("xiao", lv3.getNvo().getGoodsName());
			request.setAttribute("da", lv.getNvo().getGoodsName());
			request.setAttribute("zhong", lv2.getNvo().getGoodsName());
			otherSession.setAttribute("xiao", lv3.getNvo().getGoodsName());// �õ���Ʒ����
			otherSession.setAttribute("da", lv.getNvo().getGoodsName());// ͬ��
			otherSession.setAttribute("zhong", lv2.getNvo().getGoodsName());// ͬ��
			otherSession.setAttribute("xiao_id", lv3.getNvo().getGoodsId());
			otherSession.setAttribute("da_id", lv.getNvo().getGoodsId());
			otherSession.setAttribute("zhong_id", lv2.getNvo().getGoodsId());
			otherSession.setAttribute("xiao_Name", lv3.getNvo().getGoodsName());
			otherSession.setAttribute("da_Name", lv.getNvo().getGoodsName());
			otherSession
					.setAttribute("zhong_Name", lv2.getNvo().getGoodsName());
			// --------------------------
			otherSession.setAttribute("xiao_Type", lv3.getNvo().getGoodsType());
			otherSession.setAttribute("da_Type", lv.getNvo().getGoodsType());
			otherSession
					.setAttribute("zhong_Type", lv2.getNvo().getGoodsType());
			// --------------------
			otherSession.setAttribute("jName", "�󽱣�"
					+ lv.getNvo().getGoodsName());
		}
		else
			if (rdNum >= (daNum + 1) && rdNum <= zhongNum)// ���������ƷΪ�н�
			{
				lv2 = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo,
						daNum);// ��ô����õ�����Ʒ��ֻ������ʾ
				lv3 = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo,
						zhongNum + 1);// ��ô����õ���������Ʒ��ֻ������ʾ
				request.setAttribute("xiao", lv3.getNvo().getGoodsName());
				request.setAttribute("da", lv2.getNvo().getGoodsName());
				request.setAttribute("zhong", lv.getNvo().getGoodsName());
				otherSession.setAttribute("xiao", lv3.getNvo().getGoodsName());// �õ���Ʒ����
				otherSession.setAttribute("da", lv2.getNvo().getGoodsName());// ͬ��
				otherSession.setAttribute("zhong", lv.getNvo().getGoodsName());// ͬ��
				otherSession.setAttribute("xiao_id", lv3.getNvo().getGoodsId());
				otherSession.setAttribute("da_id", lv2.getNvo().getGoodsId());
				otherSession.setAttribute("zhong_id", lv.getNvo().getGoodsId());
				otherSession.setAttribute("jName", "С����"
						+ lv.getNvo().getGoodsName());
				otherSession.setAttribute("xiao_Name", lv3.getNvo()
						.getGoodsName());
				otherSession.setAttribute("da_Name", lv2.getNvo()
						.getGoodsName());
				otherSession.setAttribute("zhong_Name", lv.getNvo()
						.getGoodsName());
				// ------------------
				otherSession.setAttribute("xiao_Type", lv3.getNvo()
						.getGoodsType());
				otherSession.setAttribute("da_Type", lv2.getNvo()
						.getGoodsType());
				otherSession.setAttribute("zhong_Type", lv.getNvo()
						.getGoodsType());
			}
			else
			{// �����õĽ�����ƷΪ����(С)��
				lv2 = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo,
						daNum);// ��ô����õ�����Ʒ��ֻ������ʾ
				lv3 = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo,
						zhongNum);// ��ô����õ��н���Ʒ��ֻ������ʾ
				request.setAttribute("xiao", lv.getNvo().getGoodsName());
				request.setAttribute("da", lv2.getNvo().getGoodsName());
				request.setAttribute("zhong", lv3.getNvo().getGoodsName());
				otherSession.setAttribute("xiao", lv.getNvo().getGoodsName());// �õ���Ʒ����
				otherSession.setAttribute("da", lv2.getNvo().getGoodsName());// ͬ��
				otherSession.setAttribute("zhong", lv3.getNvo().getGoodsName());// ͬ��
				otherSession.setAttribute("xiao_id", lv.getNvo().getGoodsId());
				otherSession.setAttribute("da_id", lv2.getNvo().getGoodsId());
				otherSession
						.setAttribute("zhong_id", lv3.getNvo().getGoodsId());
				otherSession.setAttribute("jName", "��������"
						+ lv.getNvo().getGoodsName());
				otherSession.setAttribute("xiao_Name", lv.getNvo()
						.getGoodsName());
				otherSession.setAttribute("da_Name", lv2.getNvo()
						.getGoodsName());
				otherSession.setAttribute("zhong_Name", lv3.getNvo()
						.getGoodsName());
				// ---------------------------------
				otherSession.setAttribute("xiao_Type", lv.getNvo()
						.getGoodsType());
				otherSession.setAttribute("da_Type", lv2.getNvo()
						.getGoodsType());
				otherSession.setAttribute("zhong_Type", lv3.getNvo()
						.getGoodsType());
			}
		// ������Ʒ��Ҫ���ݻ�õĵ�һ����Ʒ��������������Ʒ���ĸ������
		request.setAttribute("count", "0");// ��¼���"������ʾ��"�Ĵ���
		// -----------------------����session�������õ�--------------------
		otherSession.setAttribute("good_id", goodId);
		otherSession.setAttribute("str_Word", "-1");// ������ʾ�������ֻ���"������ʾ��"
		otherSession.setAttribute("str_Word_2", "-1");// ͬ��
		otherSession.setAttribute("str_Word_3", "-1");// ͬ��
		otherSession.setAttribute("prop_id", prop_id);
		otherSession.setAttribute("count", "0");
		otherSession.setAttribute("npc_id", npc_id);
		otherSession.setAttribute("daNum", daNum);
		otherSession.setAttribute("zhongNum", zhongNum);
		otherSession.setAttribute("goodType", lv.getNvo().getGoodsType());
		otherSession.setAttribute("first", "0");

		// ---------------------request���ݣ�ҳ����ʾ��Ҫ---------
		request.setAttribute("str_Word", "-1");
		request.setAttribute("str_Word_2", "-1");
		request.setAttribute("str_Word_3", "-1");
		// ----------------------------------------------------
		return mapping.findForward("toOpenLaba");
	}

	/** ���"����"��Ŀ������� */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		LaBaService lbs = new LaBaService();
		String strs[] = new String[3];
		// =====================��session��õ�����========
		// ��õĽ�����Ʒ��ID:goodid
		String goodid = request.getSession().getAttribute("good_id").toString();
		// num�����жϵ�����ǵڼ���"������ʾ��"��
		String num = request.getParameter("num");
		// ��ñ���ID:prop_id
		String prop_id = request.getSession().getAttribute("prop_id")
				.toString();
		// npc_id �����жϻ���Ǵ󽱡��н�����С��
		String npc_id = request.getSession().getAttribute("npc_id").toString();
		// ����prop_id�õ���������
		PropVO pv = lbs.getGoodByID(prop_id);
		// ��NPCID����
		String sNPCZone[] = new String[3];
		String sNPCDA = null;
		String sNPCZHONG = null;
		String sNPCXIAO = null;
		String da_id = null;
		String zhong_id = null;
		String xiao_id = null;
		String daNum = null;
		String zhongNum = null;
		String first = request.getSession().getAttribute("first").toString();
		if (first.equals("0"))// ����ǵ�һ���������ҳ��
		{
			// count Ϊͳ�ƴ���
			int count = Integer.parseInt(request.getSession().getAttribute(
					"count").toString());
			// daΪ����Ʒ������
			String da = request.getSession().getAttribute("da").toString();
			// zhongΪ�н���Ʒ������
			String zhong = request.getSession().getAttribute("zhong")
					.toString();
			// xiaoΪ����(С)����Ʒ������
			String xiao = request.getSession().getAttribute("xiao").toString();
			// str_Word������ʾ�������ֻ���"������ʾ��"
			String str_Word = request.getSession().getAttribute("str_Word")
					.toString();
			// str_Word_2ͬ��
			String str_Word_2 = request.getSession().getAttribute("str_Word_2")
					.toString();// ͬ��
			// str_Word_3ͬ��
			String str_Word_3 = request.getSession().getAttribute("str_Word_3")
					.toString();// ͬ��
			HttpSession otherSession = request.getSession();
			sNPCZone = pv.getPropOperate1().split(",");
			// �󽱵�NPC��ID
			sNPCDA = sNPCZone[0].split("-")[0];
			// �н���NPC��ID
			sNPCZHONG = sNPCZone[1].split("-")[0];
			// С����NPC��ID
			sNPCXIAO = sNPCZone[2].split("-")[0];
			otherSession.setAttribute("sNPCDA", sNPCDA);
			otherSession.setAttribute("sNPCZHONG", sNPCZHONG);
			otherSession.setAttribute("sNPCXIAO", sNPCXIAO);
			ArrayList<String> al = null;
			// =======================
			strs[0] = str_Word;
			strs[1] = str_Word_2;
			strs[2] = str_Word_3;
			if (npc_id.equals(sNPCDA))// ����Ǵ�
			{
				al = lbs.getListThree(strs, num, count);// ���û�ô���ʾ�ַ���
				request.setAttribute("prop_id", prop_id);
			}
			else
				if (npc_id.equals(sNPCZHONG))// �����õ����н�
				{
					al = lbs.getListTwo(strs, num, count);// ���û���н���ʾ�ַ���
					request.setAttribute("prop_id", prop_id);
				}
				else
					if (npc_id.equals(sNPCXIAO))// �����õ��ǹ�����
					{
						al = lbs.getList(strs, num, count);// ���û�ù�������ʾ�ַ���
						request.setAttribute("prop_id", prop_id);
					}
			// ------------�����ݷ���session-------------
			otherSession.setAttribute("good_id", goodid);
			otherSession.setAttribute("str_Word", al.get(0));// ������ʾ�������ֻ���"������ʾ��"
			otherSession.setAttribute("str_Word_2", al.get(1));// ͬ��
			otherSession.setAttribute("str_Word_3", al.get(2));// ͬ��
			otherSession.setAttribute("prop_id", prop_id);
			count++;
			otherSession.setAttribute("count", String.valueOf(count));
			otherSession.setAttribute("npc_id", npc_id);
			// ---------------------ҳ��������Ҫ-------------------
			request.setAttribute("str_Word", al.get(0));
			request.setAttribute("str_Word_2", al.get(1));
			request.setAttribute("str_Word_3", al.get(2));
			request.setAttribute("da", da);
			request.setAttribute("zhong", zhong);
			request.setAttribute("xiao", xiao);
			// ---------------------------
			if (count == 3) // ����������"������ʾ��"��
			{
				String jName = request.getSession().getAttribute("jName")
						.toString();
				request.setAttribute("jName", jName);
				otherSession.setAttribute("jName", jName);// ������Ʒ������
				request.setAttribute("cueWord", "");// ��ʾ��Ϣ
				return mapping.findForward("toLingQu"); // �����������ȡ����ҳ�棬
			}
			return mapping.findForward("toOpenLaba");
		}
		else
		{
			// count Ϊͳ�ƴ���
			int count = Integer.parseInt(request.getSession().getAttribute(
					"count").toString());
			HttpSession otherSession = request.getSession();
			sNPCDA = request.getSession().getAttribute("sNPCDA").toString();
			sNPCZHONG = request.getSession().getAttribute("sNPCZHONG")
					.toString();
			sNPCXIAO = request.getSession().getAttribute("sNPCXIAO").toString();
			ArrayList<String> al = null;
			da_id = request.getSession().getAttribute("da_id").toString();
			zhong_id = request.getSession().getAttribute("zhong_id").toString();
			xiao_id = request.getSession().getAttribute("xiao_id").toString();
			daNum = request.getSession().getAttribute("daNum").toString();
			zhongNum = request.getSession().getAttribute("zhongNum").toString();
			npc_id = request.getSession().getAttribute("npc_id").toString();
			// str_Word������ʾ�������ֻ���"������ʾ��"
			String str_Word = request.getSession().getAttribute("str_Word")
					.toString();
			// str_Word_2ͬ��
			String str_Word_2 = request.getSession().getAttribute("str_Word_2")
					.toString();// ͬ��
			// str_Word_3ͬ��
			String str_Word_3 = request.getSession().getAttribute("str_Word_3")
					.toString();// ͬ��
			String da = request.getSession().getAttribute("da").toString();
			String zhong = request.getSession().getAttribute("zhong")
					.toString();
			String xiao = request.getSession().getAttribute("xiao").toString();
			String goodName = request.getSession().getAttribute("goodName")
					.toString();
			String goodID = request.getSession().getAttribute("good_id")
					.toString();
			System.out.println("Ϊ�ε�һ�εõ�����������ƷID��----------->" + goodID);
			strs[0] = str_Word;
			strs[1] = str_Word_2;
			strs[2] = str_Word_3;
			if (npc_id.equals(sNPCDA))// ����Ǵ�
			{
				al = lbs.getListThree(strs, num, count);// ���û�ô���ʾ�ַ���
				request.setAttribute("prop_id", prop_id);
			}
			else
				if (npc_id.equals(sNPCZHONG))// �����õ����н�
				{
					al = lbs.getListTwo(strs, num, count);// ���û���н���ʾ�ַ���
					request.setAttribute("prop_id", prop_id);
				}
				else
					if (npc_id.equals(sNPCXIAO))// �����õ��ǹ�����
					{
						al = lbs.getList(strs, num, count);// ���û�ù�������ʾ�ַ���
						request.setAttribute("prop_id", prop_id);
					}
			// ---------------------------
			otherSession.setAttribute("str_Word", al.get(0));// ������ʾ�������ֻ���"������ʾ��"
			otherSession.setAttribute("str_Word_2", al.get(1));// ͬ��
			otherSession.setAttribute("str_Word_3", al.get(2));// ͬ��
			otherSession.setAttribute("goodName", goodName);// ͬ��
			request.setAttribute("str_Word", al.get(0));
			request.setAttribute("str_Word_2", al.get(1));
			request.setAttribute("str_Word_3", al.get(2));
			request.setAttribute("da", da);
			request.setAttribute("zhong", zhong);
			request.setAttribute("xiao", xiao);
			count++;
			otherSession.setAttribute("count", String.valueOf(count));
			if (count == 3) // ����������"������ʾ��"��
			{

				request.setAttribute("jName", goodName);
				otherSession.setAttribute("jName", goodName);// ������Ʒ������
				request.setAttribute("cueWord", "");// ��ʾ��Ϣ
				return mapping.findForward("toLingQu"); // �����������ȡ����ҳ�棬
			}
			return mapping.findForward("toOpenLaba");
		}

	}

	/**
	 * ����ҵ���Ҳ������ʱ������Ҫ����������ʼҳ�棬�����е�����֤
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		LaBaService lbs = new LaBaService();
		// ����õ������ID
		String pIDOfBox = request.getSession().getAttribute("prop_id")
				.toString();
		// ���ݱ���id�õ���������
		PropVO pv = lbs.getGoodByID(pIDOfBox);
		// ��֤��������Ƿ��п�������ˢ�µĵ���,��һ������Ϊˢ�±�����Ҫ���ߵ�pg_pk
		boolean boo = lbs.checkHaveThisProp(pv.getPropOperate2(), roleInfo);
		if (boo == true)
		{
			// ���س�ʼҳ,������������س�ʼҳ��Ҳ���Բ���������ҳ
			// ��ɾ����һ��ˢ���õ���
			boolean booTwo = lbs.RemovePropOfBox(roleInfo, Integer.parseInt(pv
					.getPropOperate2()));
			if (booTwo == true)
			{
				String sNPCDA = request.getSession().getAttribute("sNPCDA")
						.toString();
				String sNPCZHONG = request.getSession().getAttribute(
						"sNPCZHONG").toString();
				String sNPCXIAO = request.getSession().getAttribute("sNPCXIAO")
						.toString();
				String da = request.getSession().getAttribute("da").toString();
				String zhong = request.getSession().getAttribute("zhong")
						.toString();
				String xiao = request.getSession().getAttribute("xiao")
						.toString();
				String daNum = request.getSession().getAttribute("daNum")
						.toString();
				String zhongNum = request.getSession().getAttribute("zhongNum")
						.toString();
				String da_id = request.getSession().getAttribute("da_id")
						.toString();
				String xiao_id = request.getSession().getAttribute("xiao_id")
						.toString();
				String zhong_id = request.getSession().getAttribute("zhong_id")
						.toString();
				String da_Name = request.getSession().getAttribute("da_Name")
						.toString();
				String xiao_Name = request.getSession().getAttribute(
						"xiao_Name").toString();
				String zhong_Name = request.getSession().getAttribute(
						"zhong_Name").toString();
				// -----------------
				String zhong_Type = request.getSession().getAttribute(
						"zhong_Type").toString();
				String xiao_Type = request.getSession().getAttribute(
						"xiao_Type").toString();
				String da_Type = request.getSession().getAttribute("da_Type")
						.toString();
				// �������µõ��˻�õ���Ʒ�����Ǵ�С��������NPCid��ô����
				ArrayList<String> al = lbs.getGoodTwo(da_id, zhong_id, xiao_id,
						Integer.parseInt(daNum), Integer.parseInt(zhongNum),
						sNPCDA, sNPCZHONG, sNPCXIAO, da_Name, zhong_Name,
						xiao_Name, da_Type, zhong_Type, xiao_Type);
				// ----------------------------------------------------
				// ---------------------����Ҫ��������Ʒid���´���session--------
				HttpSession otherSession = request.getSession();
				otherSession.setAttribute("good_id", al.get(1));
				otherSession.setAttribute("npc_id", al.get(2));// NPCid
				otherSession.setAttribute("str_Word", "-1");// ������ʾ�������ֻ���"������ʾ��"
				otherSession.setAttribute("str_Word_2", "-1");// ͬ��
				otherSession.setAttribute("str_Word_3", "-1");// ͬ��
				otherSession.setAttribute("count", "0");// ͬ��
				otherSession.setAttribute("daNum", daNum);// ͬ��
				otherSession.setAttribute("zhongNum", zhongNum);// ͬ��
				otherSession.setAttribute("first", "1");// ͬ��
				otherSession.setAttribute("goodName", al.get(0));// ͬ��
				otherSession.setAttribute("goodType", al.get(3));// ͬ��
				// ---------------------------------------------------
				request.setAttribute("da", da);
				request.setAttribute("zhong", zhong);
				request.setAttribute("xiao", xiao);
				request.setAttribute("str_Word", "-1");
				request.setAttribute("str_Word_2", "-1");
				request.setAttribute("str_Word_3", "-1");
				return mapping.findForward("toOpenLaba_again");
			}
			else
			{
				request.setAttribute("result", "�Ƴ�����ʧ��");
				return mapping.findForward("the_laba_ok");
			}
		}
		else
		{
			String da = request.getSession().getAttribute("da").toString();
			String zhong = request.getSession().getAttribute("zhong")
					.toString();
			String xiao = request.getSession().getAttribute("xiao").toString();
			String jName = request.getSession().getAttribute("jName")
					.toString();
			String str_Word = request.getSession().getAttribute("str_Word")
					.toString();
			String str_Word_2 = request.getSession().getAttribute("str_Word_2")
					.toString();
			String str_Word_3 = request.getSession().getAttribute("str_Word_3")
					.toString();
			request.setAttribute("da", da);
			request.setAttribute("zhong", zhong);
			request.setAttribute("xiao", xiao);
			request.setAttribute("jName", jName);
			request.setAttribute("str_Word", str_Word);
			request.setAttribute("str_Word_2", str_Word_2);
			request.setAttribute("str_Word_3", str_Word_3);
			// ����������ʾҳ�������û��"�绢����"����ʱ��ֱ���ڱ�ҳ��ʾ��������ˢ�£�
			request.setAttribute("cueWord", "����û�С��绢�������ߣ�����ȥ�̳ǹ���");
			return mapping.findForward("toLingQu");
		}
	}

	/**
	 * �����̳�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		// �����̳�ҳ
		return mapping.findForward("to_shangcheng");
	}

	/**
	 * ��ȡ����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		LaBaService lbs = new LaBaService();
		// ��õ���Ʒ��ID
		String goodid = request.getSession().getAttribute("good_id").toString();
		// �õ���ɫID
		String p_pk = request.getSession().getAttribute("pPk").toString();
		// ����ID
		String prop_id = request.getSession().getAttribute("prop_id")
				.toString();
		String good_type = request.getSession().getAttribute("goodType")
				.toString();
		// �����ȡ������ֻ��Ҫɾ�������䣬����Ʒ������ұ����С�
		// ��ˢ�±���ĵ��������ѡ��ˢ�µ�ʱ���ɾ�����ˡ�
		String result = lbs.getGoodAndRemoveABox(goodid, Integer
				.parseInt(prop_id), Integer.parseInt(p_pk), Integer
				.parseInt(good_type));
		request.setAttribute("result", result);
		return mapping.findForward("the_laba_ok");
	}

	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String display = (String) request.getAttribute("display");
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}
}
