package com.ls.web.action.menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.vo.friend.FriendVO;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.service.log.LogService;
import com.ls.web.service.rank.RankService;
import com.web.jieyi.util.Constant;
import com.web.jieyi.util.JieyiVo;

public class JieHunAction extends BaseAction
{
	public ActionForward nn1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			BasicInfo bi = getBasicInfo(request);
			if (bi.getSex() == 2)
			{
				return mapping.findForward(ERRORGENDER);
			}
			List<FriendVO> list1 = friendService.isMerry(bi.getPPk() + "");
			if (list1 != null && list1.size() > 0)
			{
				request.setAttribute("jieyihint", HINT10);
				return mapping.findForward(ERROR);
			}
			String ids = Constant.JIEHUN_IDS;
			if (null == ids || "".equals(ids.trim()))
			{
				request.setAttribute("jieyihint", HINT11);
				return mapping.findForward(ERROR);

			}

			QueryPage queryPage = new QueryPage();
			String page_Str = request.getParameter("page");
			int page = 0;
			if (page_Str != null)
			{
				page = Integer.parseInt(page_Str);
			}
			int size = friendService.getCanMerryCount(bi.getPPk(), bi.getSex());
			List<FriendVO> friendlist = null;
			int pageall = 0;
			if (size != 0)
			{
				pageall = size / queryPage.getPageSize()
						+ (size % queryPage.getPageSize() == 0 ? 0 : 1);
				// ��ѯ�������
				friendlist = friendService.getCanMerry(bi.getPPk(),
						bi.getSex(), page * queryPage.getPageSize(), queryPage
								.getPageSize());
			}
			request.setAttribute("pageall", pageall);
			request.setAttribute("page", page);
			request.setAttribute("friendlist", friendlist);
			return mapping.findForward(MERRY);
		}
		catch (Exception e)
		{
			setMessage(request, "������");
			return mapping.findForward("mess");
		}
	}

	public ActionForward nn2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		try
		{
			BasicInfo bi = getBasicInfo(request);
			int p_pk = bi.getPPk();
			String pByPk = request.getParameter("pByPk");
			String pByName = request.getParameter("pByName");
			RoleEntity friend = roleService.getRoleInfoById(pByPk);
			if (friend == null || friend.isOnline()==false )
			{
				// ���Ѳ�����
				request.setAttribute("message", "�Է��������޷���飡");
				// return nn1(mapping, form, request, response);
				return mapping.findForward(ERROR);
			}
			if (friend.getBasicInfo().getGrade() < Constant.JIEHUN_LEVEL_LIMIT
					|| bi.getGrade() < Constant.JIEHUN_LEVEL_LIMIT)
			{
				if (bi.getGrade() < Constant.JIEHUN_LEVEL_LIMIT)
				{
					request.setAttribute("message", "���ĵȼ�����");
				}
				else
				{
					request.setAttribute("message", "�Է��ȼ�����");
				}
				// return nn1(mapping, form, request, response);
				return mapping.findForward(ERROR);
			}
			FriendVO fv = friendService.viewfriend(friend.getBasicInfo()
					.getPPk(), bi.getPPk() + "");
			if (fv == null)
			{
				request.setAttribute("message", getMerryHint(pByName));
				// return nn1(mapping, form, request, response);
				return mapping.findForward(ERROR);
			}
			List<FriendVO> list1 = friendService.isMerry(friend.getBasicInfo()
					.getPPk()
					+ "");
			if (list1 != null && list1.size() > 0)
			{
				request.setAttribute("message", getMerryHint(pByName));
				// return nn1(mapping, form, request, response);
				return mapping.findForward(ERROR);
			}
			if (bi.getCopper() < Constant.JIEHUN_MONEY_NEED)
			{
				request.setAttribute("message", getMerryHint(pByName));
				return nn1(mapping, form, request, response);
			}

			if (fv.getDear() < Constant.JIEHUN_DEAR_NEED)
			{
				request.setAttribute("message", getMerryHint(pByName));
				// return nn1(mapping, form, request, response);
				return mapping.findForward(ERROR);
			}
			UMessageInfoVO uif = new UMessageInfoVO();
			uif.setCreateTime(new Date());
			uif.setMsgPriority(PopUpMsgType.JIEHUN_FIRST);
			uif.setMsgType(PopUpMsgType.JIEHUN);
			uif.setPPk(Integer.parseInt(pByPk.trim()));
			uif.setResult(bi.getName() + "�����������");
			uif.setMsgOperate2(p_pk + "");
			uMsgService.sendPopUpMsg(uif);
			request.setAttribute("name", pByName);
			return mapping.findForward(JIEHUNSHENQING);
		}
		catch (Exception e)
		{
			setMessage(request, "������");
			return mapping.findForward("mess");
		}
	}

	public ActionForward nn3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			BasicInfo bi = getBasicInfo(request);
			int caozuo = Integer
					.parseInt(request.getParameter("caozuo").trim());
			String send_id = request.getParameter("send_id");

			UMessageInfoVO uif = new UMessageInfoVO();
			uif.setCreateTime(new Date());
			uif.setMsgPriority(PopUpMsgType.MERRY_AGREE_FIRST);
			uif.setMsgType(PopUpMsgType.MERRY_AGREE);
			uif.setPPk(Integer.parseInt(send_id.trim()));
			uif.setResult(bi.getName() + "ͬ���������");
			uif.setMsgOperate1(caozuo + "");
			uif.setMsgOperate2(bi.getPPk() + "");
			uMsgService.sendPopUpMsg(uif);
			if (caozuo == 0)
			{
				// ͬ��
				return mapping.findForward(TONGYIJIEHUN);
			}
			else
			{
				// �ܾ�
				return mapping.findForward("no_refurbish_scene");
			}
		}
		catch (Exception e)
		{
			setMessage(request, "������");
			return mapping.findForward("mess");
		}
	}

	public ActionForward nn4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			BasicInfo bi = getBasicInfo(request);
			String send_id = request.getParameter("send_id");
			RoleEntity re = getRoleEntity(request);
			RoleEntity friend = roleService.getRoleInfoById(send_id);
			if (friend == null || friend.isOnline()==false)
			{
				request.setAttribute("jiehunle", "����Է������ߣ����ܽ���ˣ�");
				return mapping.findForward(GOOD_NOT_ENOUGH);
			}
			if (bi.getWrapSpare() < 1
					|| friend.getBasicInfo().getWrapSpare() < 1)
			{
				request.setAttribute("jiehunle", "��������һ���İ������������ˣ�");
				return mapping.findForward(GOOD_NOT_ENOUGH);
			}
			int caozuo = Integer
					.parseInt(request.getParameter("caozuo").trim());
			if (caozuo == 1)
			{
				UMessageInfoVO uif = new UMessageInfoVO();
				uif.setCreateTime(new Date());
				uif.setMsgPriority(PopUpMsgType.CAN_NOT_MERRY_FIRST);
				uif.setMsgType(PopUpMsgType.CAN_NOT_MERRY);
				uif.setPPk(Integer.parseInt(send_id.trim()));
				uif.setResult(bi.getName() + "ûǮ�������");
				uif.setMsgOperate1(1 + "");
				uif.setMsgOperate2(bi.getPPk() + "");
				uMsgService.sendPopUpMsg(uif);
				return mapping.findForward(GOOD_NOT_ENOUGH);
			}

			String ids = Constant.JIEHUN_IDS;
			List<PlayerPropGroupVO> list = ppgd
					.findByProp_IDs(bi.getPPk(), ids);
			if (bi.getCopper() < Constant.JIEHUN_MONEY_NEED)
			{
				UMessageInfoVO uif = new UMessageInfoVO();
				uif.setCreateTime(new Date());
				uif.setMsgPriority(PopUpMsgType.CAN_NOT_MERRY_FIRST);
				uif.setMsgType(PopUpMsgType.CAN_NOT_MERRY);
				uif.setPPk(Integer.parseInt(send_id.trim()));
				uif.setResult(bi.getName() + "ûǮ�������");
				uif.setMsgOperate1(1 + "");
				uif.setMsgOperate2(bi.getPPk() + "");
				uMsgService.sendPopUpMsg(uif);
				return mapping.findForward(GOOD_NOT_ENOUGH);
			}
			// ���û��ȡ���û���Ʒ
			if (list == null || list.size() <= 0)
			{
				UMessageInfoVO uif = new UMessageInfoVO();
				uif.setCreateTime(new Date());
				uif.setMsgPriority(PopUpMsgType.CAN_NOT_MERRY_FIRST);
				uif.setMsgType(PopUpMsgType.CAN_NOT_MERRY);
				uif.setPPk(Integer.parseInt(send_id.trim()));
				uif.setResult(bi.getName() + "ûǮ�������");
				uif.setMsgOperate1(1 + "");
				uif.setMsgOperate2(bi.getPPk() + "");
				uMsgService.sendPopUpMsg(uif);
				return mapping.findForward(GOOD_NOT_ENOUGH);
			}
			List<PlayerPropGroupVO> copyList = copyList(list);
			String[] eachId = ids.split(",");
			int haveCount = 0;
			for (String s : eachId)
			{
				List<PlayerPropGroupVO> copyList1 = copyList(copyList);
				for (PlayerPropGroupVO ppgv : copyList1)
				{
					if ((ppgv.getPropId() + "").trim().equals(s.trim()))
					{
						copyList.remove(ppgv);
						if (ppgv.getPropNum() > 1)
						{
							ppgv.setPropNum(ppgv.getPropNum() - 1);
							copyList.add(ppgv);
						}
						haveCount++;
						break;
					}
				}
			}
			if (haveCount != eachId.length)
			{
				UMessageInfoVO uif = new UMessageInfoVO();
				uif.setCreateTime(new Date());
				uif.setMsgPriority(PopUpMsgType.CAN_NOT_MERRY_FIRST);
				uif.setMsgType(PopUpMsgType.CAN_NOT_MERRY);
				uif.setPPk(Integer.parseInt(send_id.trim()));
				uif.setResult(bi.getName() + "ûǮ�������");
				uif.setMsgOperate1(1 + "");
				uif.setMsgOperate2(bi.getPPk() + "");
				uMsgService.sendPopUpMsg(uif);
				return mapping.findForward(GOOD_NOT_ENOUGH);
			}
			List<FriendVO> merryList = friendService.isMerry(bi.getPPk() + "");
			List<FriendVO> merryList1 = friendService.isMerry(send_id.trim());
			if ((merryList != null && merryList.size() > 0)
					|| (merryList1 != null && merryList1.size() > 0))
			{
				UMessageInfoVO uif = new UMessageInfoVO();
				uif.setCreateTime(new Date());
				uif.setMsgPriority(PopUpMsgType.CAN_NOT_MERRY_FIRST);
				uif.setMsgType(PopUpMsgType.CAN_NOT_MERRY);
				uif.setPPk(Integer.parseInt(send_id.trim()));
				uif.setResult(bi.getName() + "ûǮ�������");
				uif.setMsgOperate1(1 + "");
				uif.setMsgOperate2(bi.getPPk() + "");
				uMsgService.sendPopUpMsg(uif);
				request.setAttribute("jiehunle", "�Σ������е�һ���Ѿ�����ˣ�");
				return mapping.findForward(GOOD_NOT_ENOUGH);
			}
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (String s : eachId)
			{
				if (map.get(s.trim()) != null)
				{
					map.put(s.trim(), map.get(s.trim()) + 1);
				}
				else
				{
					map.put(s.trim(), 1);
				}
			}
			// �۳���Ʒ
			for (String s : map.keySet())
			{
				goodsService.removeProps(bi.getPPk(), Integer
						.parseInt(s.trim()), map.get(s.trim()),GameLogManager.R_MATERIAL_CONSUME);
			}

			// ���
			LogService logService = new LogService();
			logService.recordMoneyLog(bi.getPPk(), bi.getName(), bi.getCopper()
					+ "", -Constant.JIEHUN_MONEY_NEED + "", "���");

			// �۳�����
			bi.addCopper(-Constant.JIEHUN_MONEY_NEED);
			// //��ȡ�ƺ�
			// HonourVO hv = honourService.findByName("����");
			// HonourVO hv2 = honourService.findByName("�Ϲ�");
			// if(hv == null){
			// id1 = honourService.addHonor("����");
			// }else{
			// id1 = hv.getHoId();
			// }
			// if(hv2==null){
			// id2 = honourService.addHonor("�Ϲ�");
			// }else{
			// id2 = hv2.getHoId();
			// }
			// 1Ϊ�У�2ΪŮ
			String name = roleService.getName(send_id)[0];
			/*if (bi.getSex() == 1)
			{
				roleHonourDAO.addRoleHonout1(bi.getPPk(), Constant.MAN_HONOR,
						16, name + "��");
				roleHonourDAO.addRoleHonout1(Integer.parseInt(send_id.trim()),
						Constant.WOMAN_HONOR, 16, bi.getName() + "��");
			}
			else
			{
				roleHonourDAO.addRoleHonout1(bi.getPPk(), Constant.WOMAN_HONOR,
						16, name + "��");
				roleHonourDAO.addRoleHonout1(Integer.parseInt(send_id.trim()),
						Constant.MAN_HONOR, 16, bi.getName() + "��");
			}
			re.reloadRoleHonour(bi.getPPk());
			if(friend != null){
				friend.reloadRoleHonour(Integer.parseInt(send_id.trim()));
			}*/
			request.setAttribute("honor", name + "���Ϲ�");
			request.setAttribute("name", name);
			// Integer i = goodsService.findByName(Constant.MERRY_GIFT);
			// if(i!=null){
			// goodsService.putPropToWrap(bi.getPPk(), i, 1);
			// goodsService.putPropToWrap(Integer.parseInt(send_id.trim()), i,
			// 1);
			// }
			goodsService.putPropToWrap(bi.getPPk(), Constant.MERRY_GIFT, 1,GameLogManager.G_SYSTEM);
			goodsService.putPropToWrap(Integer.parseInt(send_id.trim()),
					Constant.MERRY_GIFT, 1,GameLogManager.G_SYSTEM);
			friendService.jieyi(bi.getPPk() + "", send_id, 2);
			friendService.jieyi(send_id, bi.getPPk() + "", 2);
			RankService rs = new RankService();
			rs.updateDear(bi.getPPk(), Constant.INIT_LOVE_DEAR, friend.getBasicInfo().getName());
			rs.updateDear(send_id, Constant.INIT_LOVE_DEAR, bi.getName());
//			rs.updateAdd(bi.getPPk(), "dear", Constant.INIT_LOVE_DEAR);
//			rs.updateAdd(send_id, "dear", Constant.INIT_LOVE_DEAR);
			bi.updateMarried(2);
			friend.getBasicInfo().updateMarried(2);
			UMessageInfoVO uif = new UMessageInfoVO();
			uif.setCreateTime(new Date());
			uif.setMsgPriority(PopUpMsgType.CAN_NOT_MERRY_FIRST);
			uif.setMsgType(PopUpMsgType.CAN_NOT_MERRY);
			uif.setPPk(Integer.parseInt(send_id.trim()));
			uif.setResult(bi.getName() + "�ɹ��������");
			uif.setMsgOperate1(bi.getName() + "������");
			uif.setMsgOperate2(bi.getPPk() + "");
			uMsgService.sendPopUpMsg(uif);

			// ��������
			// Integer i1 = goodsService.findByName("���������");
			// if(i1!=null){
			// goodsService.putPropToWrap(bi.getPPk(), i1, 1);
			// goodsService.putPropToWrap(Integer.parseInt(send_id.trim()), i1,
			// 1);
			// }
			// Integer i2 = goodsService.findByName("��ӡ��");
			// if(i2!=null){
			// goodsService.putPropToWrap(bi.getPPk(), i2, 1);
			// goodsService.putPropToWrap(Integer.parseInt(send_id.trim()), i2,
			// 1);
			// }

			return mapping.findForward(MERRY_SUCCESS);
		}
		catch (Exception e)
		{
			setMessage(request, "������");
			return mapping.findForward("mess");
		}
	}

	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		int p_pk = getP_Pk(request);
		List<FriendVO> list1 = friendService.isMerry(p_pk + "");
		if (list1 != null && list1.size() > 0)
		{
			request.setAttribute("jieyihint", HINT10);
			return mapping.findForward(ERROR);
		}
		String ids = Constant.JIEHUN_IDS;
		if (null == ids || "".equals(ids.trim()))
		{
			request.setAttribute("jieyihint", HINT11);
			return mapping.findForward(ERROR);

		}

		List<PlayerPropGroupVO> list = ppgd.findByProp_IDs(p_pk, ids);
		// ���û��ȡ���û���Ʒ
		if (list == null || list.size() <= 0)
		{
			request.setAttribute("jieyihint", HINT12);
			return mapping.findForward(ERROR);
		}
		List<PlayerPropGroupVO> copyList = copyList(list);
		String[] eachId = ids.split(",");
		int haveCount = 0;
		for (String s : eachId)
		{
			List<PlayerPropGroupVO> copyList1 = copyList(copyList);
			for (PlayerPropGroupVO ppgv : copyList1)
			{
				if ((ppgv.getPropId() + "").trim().equals(s.trim()))
				{
					copyList.remove(ppgv);
					if (ppgv.getPropNum() > 1)
					{
						ppgv.setPropNum(ppgv.getPropNum() - 1);
						copyList.add(ppgv);
					}
					haveCount++;
					break;
				}
			}
		}
		if (haveCount != eachId.length)
		{
			request.setAttribute("jieyihint", HINT12);
			return mapping.findForward(ERROR);
		}
		Constant.USER_GOOD.put(p_pk, list);
		Constant.JIEYI_MAP.remove(p_pk);

		return mapping.findForward(INDEX);
	}

	private List<PlayerPropGroupVO> copyList(List<PlayerPropGroupVO> list)
	{
		if (list == null)
			return new ArrayList<PlayerPropGroupVO>();
		return new ArrayList<PlayerPropGroupVO>(list);
	}

	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String ids = Constant.JIEHUN_IDS;
		String option = request.getParameter("option");
		int p_pk = getP_Pk(request);
		List<PlayerPropGroupVO> list = (Constant.USER_GOOD.get(p_pk) == null ? ppgd
				.findByProp_IDs(p_pk, ids)
				: Constant.USER_GOOD.get(p_pk));
		List<PlayerPropGroupVO> copyList = copyList(list);
		for (PlayerPropGroupVO ppv : list)
		{
			if (ppv.getPropNum() < 1)
			{
				copyList.remove(ppv);
			}
		}

		request.setAttribute("list", copyList);
		request.setAttribute("option", option);
		return mapping.findForward(GOODLIST);
	}

	// ��ӡ�ɾ����Ʒ
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		int p_pk = getP_Pk(request);
		String ids = Constant.JIEHUN_IDS;
		int option = Integer.parseInt(request.getParameter("option").trim());
		String propgroup_id = request.getParameter("propgroup");
		String good_id = request.getParameter("good_id");
		JieyiVo jieyiVo = new JieyiVo();
		int caozuo = Integer.parseInt(request.getParameter("caozuo").trim());
		if (Constant.JIEYI_MAP.containsKey(p_pk))
		{
			jieyiVo = Constant.JIEYI_MAP.get(p_pk);
		}

		switch (option)
		{
			case 1:
				if (caozuo == 1)
				{
					jieyiVo.setGood_id1(good_id);
					jieyiVo.setPg_pk1(propgroup_id);
				}
				else
				{
					jieyiVo.setGood_id1(null);
					jieyiVo.setPg_pk1(null);
				}
				break;
			case 2:
				if (caozuo == 1)
				{
					jieyiVo.setGood_id2(good_id);
					jieyiVo.setPg_pk2(propgroup_id);
				}
				else
				{
					jieyiVo.setGood_id2(null);
					jieyiVo.setPg_pk2(null);
				}
				break;
			case 3:
				if (caozuo == 1)
				{
					jieyiVo.setGood_id3(good_id);
					jieyiVo.setPg_pk3(propgroup_id);
				}
				else
				{
					jieyiVo.setGood_id3(null);
					jieyiVo.setPg_pk3(null);
				}
				break;
			case 4:
				if (caozuo == 1)
				{
					jieyiVo.setGood_id4(good_id);
					jieyiVo.setPg_pk4(propgroup_id);
				}
				else
				{
					jieyiVo.setGood_id4(null);
					jieyiVo.setPg_pk4(null);
				}
				break;
			case 5:
				if (caozuo == 1)
				{
					jieyiVo.setGood_id5(good_id);
					jieyiVo.setPg_pk5(propgroup_id);
				}
				else
				{
					jieyiVo.setGood_id5(null);
					jieyiVo.setPg_pk5(null);
				}
				break;
			case 6:
				if (caozuo == 1)
				{
					jieyiVo.setGood_id6(good_id);
					jieyiVo.setPg_pk6(propgroup_id);
				}
				else
				{
					jieyiVo.setGood_id6(null);
					jieyiVo.setPg_pk6(null);
				}
				break;
			default:
				if (caozuo == 1)
				{
					jieyiVo.setGood_id1(good_id);
					jieyiVo.setPg_pk1(propgroup_id);
				}
				else
				{
					jieyiVo.setGood_id1(null);
					jieyiVo.setPg_pk1(null);
				}
				break;
		}

		List<PlayerPropGroupVO> list = (Constant.USER_GOOD.get(p_pk) == null ? ppgd
				.findByProp_IDs(p_pk, ids)
				: Constant.USER_GOOD.get(p_pk));
		List<PlayerPropGroupVO> copyList = copyList(list);
		for (PlayerPropGroupVO ppv : copyList)
		{
			if ((ppv.getPgPk() + "").trim().equals(propgroup_id.trim()))
			{
				list.remove(ppv);
				if (caozuo == 1)
					ppv.setPropNum(ppv.getPropNum() - 1);
				else
					ppv.setPropNum(ppv.getPropNum() + 1);
				list.add(ppv);
				break;
			}
		}

		Constant.USER_GOOD.put(p_pk, list);
		Constant.JIEYI_MAP.put(p_pk, jieyiVo);
		return n5(mapping, form, request, response);
	}

	// ˢ�½ӿ�
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		int p_pk = getP_Pk(request);
		JieyiVo jieyiVo = new JieyiVo();
		if (Constant.JIEYI_MAP.containsKey(p_pk))
		{
			jieyiVo = Constant.JIEYI_MAP.get(p_pk);
		}
		if (jieyiVo != null)
		{
			if (jieyiVo.getPg_pk1() != null
					&& !"".equals(jieyiVo.getPg_pk1().trim()))
			{
				PlayerPropGroupVO ppgv = ppgd.findById(Integer.parseInt(jieyiVo
						.getPg_pk1().trim()));
				request.setAttribute("good1", ppgv);
			}
			if (jieyiVo.getPg_pk2() != null
					&& !"".equals(jieyiVo.getPg_pk2().trim()))
			{
				PlayerPropGroupVO ppgv = ppgd.findById(Integer.parseInt(jieyiVo
						.getPg_pk2().trim()));
				request.setAttribute("good2", ppgv);
			}
			if (jieyiVo.getPg_pk3() != null
					&& !"".equals(jieyiVo.getPg_pk3().trim()))
			{
				PlayerPropGroupVO ppgv = ppgd.findById(Integer.parseInt(jieyiVo
						.getPg_pk3().trim()));
				request.setAttribute("good3", ppgv);
			}
			if (jieyiVo.getPg_pk4() != null
					&& !"".equals(jieyiVo.getPg_pk4().trim()))
			{
				PlayerPropGroupVO ppgv = ppgd.findById(Integer.parseInt(jieyiVo
						.getPg_pk4().trim()));
				request.setAttribute("good4", ppgv);
			}
			if (jieyiVo.getPg_pk5() != null
					&& !"".equals(jieyiVo.getPg_pk5().trim()))
			{
				PlayerPropGroupVO ppgv = ppgd.findById(Integer.parseInt(jieyiVo
						.getPg_pk5().trim()));
				request.setAttribute("good5", ppgv);
			}
			if (jieyiVo.getPg_pk6() != null
					&& !"".equals(jieyiVo.getPg_pk6().trim()))
			{
				PlayerPropGroupVO ppgv = ppgd.findById(Integer.parseInt(jieyiVo
						.getPg_pk6().trim()));
				request.setAttribute("good6", ppgv);
			}
		}
		return mapping.findForward(INDEX);
	}

	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		int p_pk = getP_Pk(request);
		JieyiVo jieyiVo = new JieyiVo();
		if (Constant.JIEYI_MAP.containsKey(p_pk))
		{
			jieyiVo = Constant.JIEYI_MAP.get(p_pk);
		}
		// ���ȱ�ٽ�����Ʒ
		if (jieyiVo.getPg_pk1() == null
				|| "".equals(jieyiVo.getPg_pk1().trim())
				|| jieyiVo.getPg_pk2() == null
				|| "".equals(jieyiVo.getPg_pk2().trim())
				|| jieyiVo.getPg_pk3() == null
				|| "".equals(jieyiVo.getPg_pk3().trim())
				|| jieyiVo.getPg_pk4() == null
				|| "".equals(jieyiVo.getPg_pk4().trim())
				|| jieyiVo.getPg_pk5() == null
				|| "".equals(jieyiVo.getPg_pk5().trim())
				|| jieyiVo.getPg_pk6() == null
				|| "".equals(jieyiVo.getPg_pk6().trim()))
		{
			request.setAttribute("message", HINT13);
			return n5(mapping, form, request, response);
		}
		// �������˳����ȷ
		StringBuffer sb = new StringBuffer();
		sb.append(jieyiVo.getGood_id1().trim()).append(",").append(
				jieyiVo.getGood_id2().trim()).append(",").append(
				jieyiVo.getGood_id3().trim()).append(",").append(
				jieyiVo.getGood_id4().trim()).append(",").append(
				jieyiVo.getGood_id5().trim()).append(",").append(
				jieyiVo.getGood_id6().trim());
		if (!sb.toString().trim().equals(Constant.JIEHUN_IDS))
		{
			request.setAttribute("message", HINT14);
			return n5(mapping, form, request, response);
		}

		// ������������ת�������б�
		return n7(mapping, form, request, response);

	}

	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		QueryPage queryPage = new QueryPage();
		String page_Str = request.getParameter("page");
		int page = 0;
		if (page_Str != null)
		{
			page = Integer.parseInt(page_Str);
		}
		int size = friendService.getCanMerryCount(bi.getPPk(), bi.getSex());
		List<FriendVO> friendlist = null;
		int pageall = 0;
		if (size != 0)
		{
			pageall = size / queryPage.getPageSize()
					+ (size % queryPage.getPageSize() == 0 ? 0 : 1);
			// ��ѯ�������
			friendlist = friendService.getCanMerry(bi.getPPk(), bi.getSex(),
					page * queryPage.getPageSize(), queryPage.getPageSize());
		}
		request.setAttribute("pageall", pageall);
		request.setAttribute("page", page);
		request.setAttribute("friendlist", friendlist);
		return mapping.findForward(FRIENDLIST);
	}

	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		int p_pk = bi.getPPk();
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		RoleEntity friend = roleService.getRoleInfoById(pByPk);
		if (friend == null  || friend.isOnline()==false)
		{
			// ���Ѳ�����
			request.setAttribute("message", HINT15);
			return n5(mapping, form, request, response);
		}
		if (friend.getBasicInfo().getGrade() < Constant.JIEHUN_LEVEL_LIMIT
				|| bi.getGrade() < Constant.JIEHUN_LEVEL_LIMIT)
		{
			request.setAttribute("message", HINT16);
			return n5(mapping, form, request, response);
		}
		// if(friend.getBasicInfo().getCamp()!=bi.getCamp()){
		// request.setAttribute("message", HINT17);
		// return n5(mapping, form, request, response);
		// }
		FriendVO fv = friendService.viewfriend(friend.getBasicInfo().getPPk(),
				bi.getPPk() + "");
		if (fv == null)
		{
			request.setAttribute("message", HINT18);
			return n5(mapping, form, request, response);
		}
		List<FriendVO> list1 = friendService.isMerry(friend.getBasicInfo()
				.getPPk()
				+ "");
		if (list1 != null && list1.size() > 0)
		{
			request.setAttribute("message", HINT20);
			return n5(mapping, form, request, response);
		}
		if (bi.getCopper() < Constant.JIEHUN_MONEY_NEED)
		{
			request.setAttribute("message", HINT23);
			return n5(mapping, form, request, response);
		}

		if (fv.getDear() < Constant.JIEHUN_DEAR_NEED)
		{
			request.setAttribute("message", HINT19);
			return n5(mapping, form, request, response);
		}
		else
		{
			// ��ʱɾ������������Ʒ
			JieyiVo jieyiVo = new JieyiVo();
			if (Constant.JIEYI_MAP.containsKey(p_pk))
			{
				jieyiVo = Constant.JIEYI_MAP.get(p_pk);
			}
			List<String> list = new ArrayList<String>();
			list.add(jieyiVo.getGood_id1().trim());
			list.add(jieyiVo.getGood_id2().trim());
			list.add(jieyiVo.getGood_id3().trim());
			list.add(jieyiVo.getGood_id4().trim());
			list.add(jieyiVo.getGood_id5().trim());
			list.add(jieyiVo.getGood_id6().trim());
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (String s : list)
			{
				if (map.get(s.trim()) != null)
				{
					map.put(s.trim(), map.get(s.trim()) + 1);
				}
				else
				{
					map.put(s.trim(), 1);
				}
			}
			for (String s : map.keySet())
			{
				goodsService.removeProps(p_pk, Integer.parseInt(s.trim()), map
						.get(s.trim()),GameLogManager.R_MATERIAL_CONSUME);
			}
			StringBuffer sb = new StringBuffer();
			sb.append(jieyiVo.getGood_id1().trim()).append(",").append(
					jieyiVo.getGood_id2().trim()).append(",").append(
					jieyiVo.getGood_id3().trim()).append(",").append(
					jieyiVo.getGood_id4().trim()).append(",").append(
					jieyiVo.getGood_id5().trim()).append(",").append(
					jieyiVo.getGood_id6().trim());
			UMessageInfoVO uif = new UMessageInfoVO();
			uif.setCreateTime(new Date());
			uif.setMsgOperate1(sb.toString().trim());
			uif.setMsgPriority(PopUpMsgType.JIEHUN_FIRST);
			uif.setMsgType(PopUpMsgType.JIEHUN);
			uif.setPPk(Integer.parseInt(pByPk.trim()));
			uif.setResult(bi.getName() + "�����������");
			uif.setMsgOperate2(p_pk + "");
			uMsgService.sendPopUpMsg(uif);
			friendService.jieyi(bi.getPPk() + "", pByPk, 3);
			friendService.jieyi(pByPk, bi.getPPk() + "", 3);
			request.setAttribute("jieyihint", jieYISuccessMes(pByName, "��"));
			return mapping.findForward(ERROR);
		}
	}

	// ���屻���뷽ͬ���ܾ�����
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity re = getRoleEntity(request);
		BasicInfo bi = getBasicInfo(request);
		int caozuo = Integer.parseInt(request.getParameter("caozuo").trim());
		String operate = request.getParameter("operate");
		String send_id = request.getParameter("send_id");
		if (caozuo == 0)
		{
			// ͬ�⴦��
			friendService.jieyi(bi.getPPk() + "", send_id, 2);
			friendService.jieyi(send_id, bi.getPPk() + "", 2);
			systemInfoService.insertSystemInfoBySystem(Integer.parseInt(send_id
					.trim()), jieYISuccMes(bi.getName(), "��"));
			int id1 = 0;
			int id2 = 0;
			/*// ��ȡ�ƺ�
			TitleVO hv = honourService.findByName("����");
			TitleVO hv2 = honourService.findByName("�Ϲ�");
			if (hv == null)
			{
				id1 = honourService.addHonor("����");
			}
			else
			{
				id1 = hv.getId();
			}
			if (hv2 == null)
			{
				id2 = honourService.addHonor("�Ϲ�");
			}
			else
			{
				id2 = hv2.getId();
			}*/
			// 1Ϊ�У�2ΪŮ
			String name = roleService.getName(send_id)[0];
			/*if (bi.getSex() == 1)
			{
				roleHonourDAO.addRoleHonout1(bi.getPPk(), id2, 16, name + "��");
				roleHonourDAO.addRoleHonout1(Integer.parseInt(send_id.trim()),
						id1, 16, bi.getName() + "��");
			}
			else
			{
				roleHonourDAO.addRoleHonout1(bi.getPPk(), id1, 16, name + "��");
				roleHonourDAO.addRoleHonout1(Integer.parseInt(send_id.trim()),
						id2, 16, bi.getName() + "��");
			}
			re.reloadRoleHonour(bi.getPPk());
			RoleEntity friend = roleService.getRoleInfoById(send_id.trim());
			if(friend != null){
				friend.reloadRoleHonour(Integer.parseInt(send_id.trim()));
			}*/
			systemInfoService.insertSystemInfoBySystem(Integer.parseInt(send_id
					.trim()), jieYISuccMes(bi.getName(), "��"));

		}
		else
		{
			// �ܾ�����
			// ���۳��Ľ�����Ʒ����
			if (operate != null && !"".equals(operate.trim()))
			{
				String[] ss = operate.trim().split(",");
				Map<String, Integer> map = new HashMap<String, Integer>();
				for (String s : ss)
				{
					if (map.get(s.trim()) != null)
					{
						map.put(s.trim(), map.get(s.trim()) + 1);
					}
					else
					{
						map.put(s.trim(), 1);
					}
				}
				for (String s : map.keySet())
				{
					goodsService.putPropToWrap(
							Integer.parseInt(send_id.trim()), Integer
									.parseInt(s.trim()), map.get(s.trim()),GameLogManager.G_SYSTEM);
				}
			}
			friendService.jieyi(bi.getPPk() + "", send_id, 0);
			friendService.jieyi(send_id, bi.getPPk() + "", 0);

			systemInfoService.insertSystemInfoBySystem(Integer.parseInt(send_id
					.trim()), jieYIFailMes(bi.getName(), "��"));
		}
		return mapping.findForward(ERROR);
	}

	// ���
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			int p_pk = getP_Pk(request);
			List<FriendVO> list = friendService.isMerry(p_pk + "");
			if (list != null && list.size() > 0)
			{
				request.setAttribute("friendVo", list.get(0));
			}
			return mapping.findForward(LIHUN_CONMIT);
		}
		catch (Exception e)
		{
			setMessage(request, "������");
			return mapping.findForward(ERROR);
		}
	}

	// ��鴦��
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			RoleEntity re = getRoleEntity(request);
			String pByPk = request.getParameter("pByPk");
			RoleEntity friend = roleService.getRoleInfoById(pByPk);
			if (friend == null)
			{
				request.setAttribute("jieyihint", HINT21);
				return mapping.findForward(ERROR);
			}
			BasicInfo bi = getBasicInfo(request);
			friendService.jieyi(bi.getPPk() + "", pByPk, 0);
			friendService.jieyi(pByPk, bi.getPPk() + "", 0);
			// Integer i = goodsService.findByName(Constant.MERRY_GIFT);
			// if(i!=null){
			// goodsService.removeProps(bi.getPPk(), i, 1);
			// goodsService.removeProps(friend.getBasicInfo().getPPk(), i, 1);
			// specoalPropService.delItem(bi.getPPk(), i);
			// specoalPropService.delItem(friend.getBasicInfo().getPPk(), i);
			// }
			goodsService.removeProps(bi.getPPk(), Constant.MERRY_GIFT, 1,GameLogManager.R_MATERIAL_CONSUME);
			goodsService.removeProps(friend.getBasicInfo().getPPk(),
					Constant.MERRY_GIFT, 1,GameLogManager.R_MATERIAL_CONSUME);
			specoalPropService.delItem(bi.getPPk(), Constant.MERRY_GIFT);
			specoalPropService.delItem(friend.getBasicInfo().getPPk(),
					Constant.MERRY_GIFT);
			bi.updateMarried(1);
			friend.getBasicInfo().updateMarried(1);
			/*roleHonourDAO.delRoleHonour(pByPk, bi.getName() + "��");
			roleHonourDAO.delRoleHonour(bi.getPPk() + "", friend.getBasicInfo()
					.getName()
					+ "��");
			re.reloadRoleHonour(bi.getPPk());
			friend.reloadRoleHonour(Integer.parseInt(pByPk.trim()));*/
			systemInfoService.insertSystemInfoBySystem(Integer.parseInt(pByPk
					.trim()), lihunMes(bi.getName()));
			request.setAttribute("message", lihun(friend.getBasicInfo()
					.getName()));
			return mapping.findForward(ERROR);
		}
		catch (Exception e)
		{
			setMessage(request, "������");
			return mapping.findForward(ERROR);
		}
	}

	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			BasicInfo bi = getBasicInfo(request);
			String pByPk = request.getParameter("pByPk");
			String pByName = request.getParameter("pByName");
			if (pByPk == null || "".equals(pByPk.trim()) || pByName == null
					|| "".equals(pByName.trim()))
			{
				return mapping.findForward(ERROR);
			}
			FriendVO friendVO = null;
			List<FriendVO> list = friendService.isMerry(bi.getPPk() + "");
			if (list != null && list.size() > 0)
			{
				friendVO = list.get(0);
			}
			if (friendVO == null)
			{
				setMessage(request, "��û�н��");
				return mapping.findForward(ERROR);
			}
			String message = "���Ҫ��";
			int mailId = mailInfoService.insertMailReturnId(friendVO.getFdPk(),
					message, "aa");
			String help1 = "<anchor><go method=\"post\" href=\""+ response.encodeURL(GameConfig.getContextPath()+"/jiehun.do") + "\">"
					+ "<postfield name=\"cmd\" value=\"n16\" />"
					+ "<postfield name=\"mailId\" value=\""
					+ mailId
					+ "\" />"
					+ " </go>"
					+ "ͬ��</anchor><anchor><go method=\"post\" href=\""+ response.encodeURL(GameConfig.getContextPath()+"/jiehun.do") + "\">"
					+ "<postfield name=\"cmd\" value=\"n15\" />"
					+ "<postfield name=\"mailId\" value=\"" + mailId + "\" />"
					+ " </go>" + "�ܾ�</anchor><br/>";
			message = "���" + bi.getName() + "Ҫ��������������ϵ!<br/>";
			mailInfoService.updateMail(mailId, message + help1);

			// UMessageInfoVO uif = new UMessageInfoVO();
			// uif.setCreateTime(new Date());
			// uif.setMsgPriority(PopUpMsgType.LIHUN_FIRST);
			// uif.setMsgType(PopUpMsgType.LIHUN);
			// uif.setPPk(Integer.parseInt(pByPk.trim()));
			// uif.setResult(bi.getName()+"�����������");
			// uif.setMsgOperate2(bi.getPPk()+"");
			// uMsgService.sendPopUpMsg(uif);
			request.setAttribute("message", "�Ѿ���" + pByName
					+ "����������󣬶Է�ȷ���󼴿ɽ��˫��������ϵ��");
			return mapping.findForward(ERROR);
		}
		catch (Exception e)
		{
			setMessage(request, "������");
			return mapping.findForward(ERROR);
		}
	}

	public ActionForward n14(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			BasicInfo bi = getBasicInfo(request);
			String pByPk = request.getParameter("pByPk");
			systemInfoService.insertSystemInfoBySystem(Integer.parseInt(pByPk
					.trim()), notAgree(bi.getName()));
			return mapping.findForward("no_refurbish_scene");
		}
		catch (Exception e)
		{
			setMessage(request, "������");
			return mapping.findForward(ERROR);
		}
	}

	// �ܾ����
	public ActionForward n15(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			BasicInfo bi = getBasicInfo(request);
			String mailId = request.getParameter("mailId");
			if (mailId != null && !"".equals(mailId.trim()))
			{
				mailInfoService.deleteMailByid(mailId, bi.getPPk());
			}
			int p_pk = getP_Pk(request);
			FriendVO friendVO = null;
			List<FriendVO> list = friendService.isMerry(p_pk + "");
			if (list != null && list.size() > 0)
			{
				friendVO = list.get(0);
			}
			if (friendVO == null)
			{
				setMessage(request, "��û�н��");
			}
			else
			{
				setMessage(request, "��ܾ��������" + friendVO.getFdName()
						+ "���������ϵ��");
				mailInfoService.insertMailReturnId(friendVO.getFdPk(), "�ܾ����",
						"���" + bi.getName() + "�ܾ�������������ϵ!");
			}
			return mapping.findForward(ERROR);
		}
		catch (Exception e)
		{
			setMessage(request, "������");
			return mapping.findForward(ERROR);
		}
	}

	// ͬ�����
	public ActionForward n16(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			RoleEntity re = getRoleEntity(request);
			BasicInfo bi = getBasicInfo(request);
			String mailId = request.getParameter("mailId");
			if (mailId != null && !"".equals(mailId.trim()))
			{
				mailInfoService.deleteMailByid(mailId, bi.getPPk());
			}
			int p_pk = getP_Pk(request);
			FriendVO friendVO = null;
			List<FriendVO> list = friendService.isMerry(p_pk + "");
			if (list != null && list.size() > 0)
			{
				friendVO = list.get(0);
			}
			if (friendVO == null)
			{
				setMessage(request, "��û�н��");
			}
			else
			{
				friendService.jieyi(bi.getPPk() + "", friendVO.getFdPk() + "",
						0);
				friendService.jieyi(friendVO.getFdPk() + "", bi.getPPk() + "",
						0);
				goodsService.removeProps(bi.getPPk(), Constant.MERRY_GIFT, 1,GameLogManager.R_MATERIAL_CONSUME);
				goodsService.removeProps(friendVO.getFdPk(),
						Constant.MERRY_GIFT, 1,GameLogManager.R_MATERIAL_CONSUME);
				RankService rankService = new RankService();
				rankService.updateDearToZero(bi.getPPk());
				rankService.updateDearToZero(friendVO.getFdPk());
				specoalPropService.delItem(bi.getPPk(), Constant.MERRY_GIFT);
				specoalPropService.delItem(friendVO.getFdPk(),
						Constant.MERRY_GIFT);
				bi.updateMarried(1);
				new PartInfoDao().updateP_harness(friendVO.getFdPk(), 1);
				/*roleHonourDAO.delRoleHonour(friendVO.getFdPk() + "", bi
						.getName()
						+ "��");
				roleHonourDAO.delRoleHonour(bi.getPPk() + "", friendVO
						.getFdName()
						+ "��");
				re.reloadRoleHonour(bi.getPPk());
				RoleEntity re1 = roleService.getRoleInfoById(friendVO.getFdPk() + "");
				if(re1!=null){
					re1.reloadRoleHonour(friendVO.getFdPk());
				}*/
				setMessage(request, "��ͬ�������" + friendVO.getFdName() + "���������ϵ��");
				mailInfoService.insertMailReturnId(friendVO.getFdPk(), "ͬ�����",
						"���" + bi.getName() + "ͬ���������˻�����ϵ!");
			}
			return mapping.findForward(ERROR);
		}
		catch (Exception e)
		{
			setMessage(request, "������");
			return mapping.findForward(ERROR);
		}
	}

	// ǿ�ƽ��
	public ActionForward n17(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			setMessage(request, "ǿ���������"+GameConfig.getYuanbaoName()+"��" + Constant.LIHUN_YUANBAO_COST
					+ "����������Է����������ϵ����ȷ��Ҫ��Է�ǿ�������");
			return mapping.findForward("qiangzhi");
		}
		catch (Exception e)
		{
			setMessage(request, "������");
			return mapping.findForward(ERROR);
		}
	}

	public ActionForward n18(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			RoleEntity re = getRoleEntity(request);
			BasicInfo bi = getBasicInfo(request);
			long yuanbao = economyService.getYuanbao(bi.getUPk());
			if (yuanbao < Constant.LIHUN_YUANBAO_COST)
			{
				setMessage(
						request,
						"��û���㹻��"+GameConfig.getYuanbaoName()+"ǿ����Է���飡<br/><anchor><go href=\""+response.encodeURL(GameConfig.getContextPath()+"//sky/bill.do?cmd=n0")+"\" method=\"get\"></go>��Ҫ��ֵ</anchor>");
				return mapping.findForward(ERROR);
			}
			FriendVO friendVO = null;
			List<FriendVO> list = friendService.isMerry(bi.getPPk() + "");
			if (list != null && list.size() > 0)
			{
				friendVO = list.get(0);
			}
			if (friendVO == null)
			{
				setMessage(request, "��û�н��");
				return mapping.findForward(ERROR);
			}
			else
			{
				economyService.spendYuanbao(bi.getUPk(),
						Constant.LIHUN_YUANBAO_COST);
				friendService.jieyi(bi.getPPk() + "", friendVO.getFdPk() + "",
						0);
				friendService.jieyi(friendVO.getFdPk() + "", bi.getPPk() + "",
						0);
				RankService rankService = new RankService();
				rankService.updateDearToZero(bi.getPPk());
				rankService.updateDearToZero(friendVO.getFdPk());
				goodsService.removeProps(bi.getPPk(), Constant.MERRY_GIFT, 1,GameLogManager.R_MATERIAL_CONSUME);
				goodsService.removeProps(friendVO.getFdPk(),
						Constant.MERRY_GIFT, 1,GameLogManager.R_MATERIAL_CONSUME);
				specoalPropService.delItem(bi.getPPk(), Constant.MERRY_GIFT);
				specoalPropService.delItem(friendVO.getFdPk(),
						Constant.MERRY_GIFT);
				bi.updateMarried(1);
				new PartInfoDao().updateP_harness(friendVO.getFdPk(), 1);
				/*roleHonourDAO.delRoleHonour(friendVO.getFdPk() + "", bi
						.getName()
						+ "��");
				roleHonourDAO.delRoleHonour(bi.getPPk() + "", friendVO
						.getFdName()
						+ "��");
				re.reloadRoleHonour(bi.getPPk());
				RoleEntity re1 = roleService.getRoleInfoById(friendVO.getFdPk() + "");
				if(re1!=null){
					re1.reloadRoleHonour(friendVO.getFdPk());
				}*/
				setMessage(request, "���Ѿ������" + friendVO.getFdName()
						+ "ǿ�ƽ��������ϵ�ˣ�");
				mailInfoService.insertMailReturnId(friendVO.getFdPk(), "ǿ�����",
						"���" + bi.getName() + "ǿ�ƽ��������Ļ�����ϵ!");
			}
			return mapping.findForward(ERROR);
		}
		catch (Exception e)
		{
			setMessage(request, "������");
			return mapping.findForward(ERROR);
		}
	}
}
