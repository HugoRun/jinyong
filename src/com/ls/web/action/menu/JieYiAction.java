package com.ls.web.action.menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.vo.friend.FriendVO;
import com.ben.vo.honour.TitleVO;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.rank.RankService;
import com.pm.service.systemInfo.SystemInfoService;
import com.web.jieyi.util.Constant;
import com.web.jieyi.util.JieyiVo;
import com.web.service.friend.FriendService;

public class JieYiAction extends BaseAction
{
	String operate;

	public String getOperate()
	{
		return operate;
	}

	public void setOperate(String operate)
	{
		this.operate = operate;
	}

	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try{
		BasicInfo bi = getBasicInfo(request);
		String ids = Constant.JIEYI_IDS;
		if (null == ids || "".equals(ids.trim()))
		{
			request.setAttribute("jieyihint", HINT1);
			return mapping.findForward(ERROR);

		}
        if(bi.getGrade()<Constant.JIEYI_LEVEL_LIMIT){
        	request.setAttribute("jieyihint", "等级需要在"+Constant.JIEYI_LEVEL_LIMIT+"级以上");
			return mapping.findForward(ERROR);
        }
		List<PlayerPropGroupVO> list = ppgd.findByProp_IDs(bi.getPPk(), ids);
		// 如果没有取到用户物品
		if (list == null || list.size() <= 0)
		{
			request.setAttribute("jieyihint", HINT2);
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
						if(ppgv.getPropNum()>1){
						ppgv.setPropNum(ppgv.getPropNum()-1);
						copyList.add(ppgv);
						}
					haveCount++;
					break;
				}
			}
		}
		if (haveCount != eachId.length)
		{
			request.setAttribute("jieyihint", HINT2);
			return mapping.findForward(ERROR);
		}
			Constant.USER_GOOD.put(bi.getPPk(), ppgd.findByProp_IDs(bi.getPPk(), ids));
			Constant.JIEYI_MAP.remove(bi.getPPk());

		return mapping.findForward(INDEX);
		}catch (Exception e)
			{
				setMessage(request, "出错了");
				return mapping.findForward("mess");
			}
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
		try{
		String ids = Constant.JIEYI_IDS;
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
		}catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward("mess");
		}
	}

	// 添加、删除物品
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try{
		int p_pk = getP_Pk(request);
		String ids = Constant.JIEYI_IDS;
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
		}catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward("mess");
		}
	}

	
	//刷新接口
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
        try{
		int p_pk = getP_Pk(request);
		JieyiVo jieyiVo = new JieyiVo();
		synchronized (Constant.JIEYI_MAP)
		{
			if (Constant.JIEYI_MAP.containsKey(p_pk))
			{
				jieyiVo = Constant.JIEYI_MAP.get(p_pk);
			}
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
    	}catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward("mess");
		}
	}

	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try{
		int p_pk = getP_Pk(request);
		JieyiVo jieyiVo = new JieyiVo();
			if (Constant.JIEYI_MAP.containsKey(p_pk))
			{
				jieyiVo = Constant.JIEYI_MAP.get(p_pk);
			}
//		如果缺少结义物品
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
			request.setAttribute("message", HINT3);
//			return n5(mapping, form, request, response);
			return mapping.findForward(ERROR);
		}
//      如果排列顺序不正确
		StringBuffer sb = new StringBuffer();
		sb.append(jieyiVo.getGood_id1().trim()).append(",").append(
				jieyiVo.getGood_id2().trim()).append(",").append(
				jieyiVo.getGood_id3().trim()).append(",").append(
				jieyiVo.getGood_id4().trim()).append(",").append(
				jieyiVo.getGood_id5().trim()).append(",").append(
				jieyiVo.getGood_id6().trim());
		if (!sb.toString().trim().equals(Constant.JIEYI_IDS)){
			request.setAttribute("message", HINT4);
//			return n5(mapping, form, request, response);
			return mapping.findForward(ERROR);
		}
		
//      满足条件，跳转到好友列表
		return n7(mapping, form, request, response);
		}catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward("mess");
		}
	}
	
	
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try{
		int p_pk = getP_Pk(request);
		QueryPage queryPage = new QueryPage();
		String page_Str = request.getParameter("page");
		int page = 0;
		if (page_Str != null)
		{
			page = Integer.parseInt(page_Str);
		}
		int size = friendService.getFriendNum1(p_pk,0);
		List<FriendVO> friendlist = null;
		int pageall = 0;
		if(size!=0){
		pageall = size / queryPage.getPageSize() + (size % queryPage.getPageSize() == 0 ? 0 : 1);
		// 查询在线玩家
		friendlist = friendService.listfriend1(p_pk, page * queryPage.getPageSize(), queryPage.getPageSize(),0);
		}
		request.setAttribute("pageall", pageall);
		request.setAttribute("page", page);
		request.setAttribute("friendlist", friendlist);
		return mapping.findForward(FRIENDLIST);
		}catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward("mess");
		}
	}
	
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try{
		BasicInfo bi = getBasicInfo(request);
		int p_pk = bi.getPPk();
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		RoleEntity friend = roleService.getRoleInfoById(pByPk);
		if(friend == null || friend.isOnline()==false){
//			好友不在线
			request.setAttribute("message", HINT5);
//			return n5(mapping, form, request, response);
			return mapping.findForward(ERROR);
		}
		if(friend.getBasicInfo().getGrade()<Constant.JIEYI_LEVEL_LIMIT||bi.getGrade()<Constant.JIEYI_LEVEL_LIMIT){
			request.setAttribute("message", HINT22);
//			return n5(mapping, form, request, response);
			return mapping.findForward(ERROR);
		}
		FriendVO fv = friendService.viewfriend(friend.getBasicInfo().getPPk(), bi.getPPk()+"");
		if(fv==null){
			request.setAttribute("message", HINT22);
//			return n5(mapping, form, request, response);
			return mapping.findForward(ERROR);
		}
		
		if(fv.getDear()<Constant.JIEYI_DEAR_NEED){
			request.setAttribute("message", HINT22);
//			return n5(mapping, form, request, response);
			return mapping.findForward(ERROR);
		}
		else{
			request.setAttribute("pByPk", pByPk);
			request.setAttribute("pByName", pByName);
			return mapping.findForward(CONMIT);
		}
		}catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward("mess");
		}
	}
	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try{
//	暂时删除结义所需物品
		BasicInfo bi = getBasicInfo(request);
		int p_pk = bi.getPPk();
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
	JieyiVo jieyiVo = new JieyiVo();
		if (Constant.JIEYI_MAP.containsKey(p_pk))
		{
			jieyiVo = Constant.JIEYI_MAP.get(p_pk);
		}
//	List<String> list = new ArrayList<String>();
//	list.add(jieyiVo.getGood_id1().trim());
//	list.add(jieyiVo.getGood_id2().trim());
//	list.add(jieyiVo.getGood_id3().trim());
//	list.add(jieyiVo.getGood_id4().trim());
//	list.add(jieyiVo.getGood_id5().trim());
//	list.add(jieyiVo.getGood_id6().trim());
//	Map<String, Integer> map = new HashMap<String, Integer>();
//	for(String s : list){
//		if(map.get(s.trim())!=null){
//			map.put(s.trim(), map.get(s.trim())+1);
//		}else{
//			map.put(s.trim(), 1);
//		}
//	}
//	for(String s : map.keySet()){
//		System.out.println(s.trim()+"=============="+map.get(s.trim()));
//		goodsService.removeProps(p_pk, Integer.parseInt(s.trim()), map.get(s.trim()));
//	}
	StringBuffer sb = new StringBuffer();
	sb.append(jieyiVo.getGood_id1().trim()).append(",").append(
			jieyiVo.getGood_id2().trim()).append(",").append(
			jieyiVo.getGood_id3().trim()).append(",").append(
			jieyiVo.getGood_id4().trim()).append(",").append(
			jieyiVo.getGood_id5().trim()).append(",").append(
			jieyiVo.getGood_id6().trim());
//	ppgd.jieYiDelProp(sb.toString().trim(),"-1");
	UMessageInfoVO uif = new UMessageInfoVO();
	uif.setCreateTime(new Date());
	uif.setMsgOperate1(sb.toString().trim());
	uif.setMsgPriority(PopUpMsgType.JIEYI_FIRST);
	uif.setMsgType(PopUpMsgType.JIEYI);
	uif.setPPk(Integer.parseInt(pByPk.trim()));
	uif.setResult(bi.getName()+"申请与您结义");
	uif.setMsgOperate2(p_pk+"");
	uMsgService.sendPopUpMsg(uif);
	request.setAttribute("jieyihint", jieYISuccessMes(pByName,"义"));
	return mapping.findForward(ERROR);
		}catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward("mess");
		}
	}
	
//	结义被邀请方同意或拒绝处理 
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try{
		BasicInfo bi = getBasicInfo(request);
		int caozuo = Integer.parseInt(request.getParameter("caozuo").trim());
		String operate = request.getParameter("operate");
		String send_id = request.getParameter("send_id");
		String name = request.getParameter("name");
		if(send_id==null||"".equals(send_id.trim())){
			request.setAttribute("jieyihint", HINT24);
			return mapping.findForward(ERROR);
		}
		if(caozuo == 0){
			if(operate!=null&&!"".equals(operate.trim())){
				List<PlayerPropGroupVO> list = ppgd.findByProp_IDs(Integer.parseInt(send_id.trim()), operate.trim());
				// 如果没有取到用户物品
				if (list == null || list.size() <= 0)
				{
					request.setAttribute("jieyihint", HINT24);
					return mapping.findForward(ERROR);
				}
				List<PlayerPropGroupVO> copyList = copyList(list);
				String[] eachId = operate.trim().split(",");
				int haveCount = 0;
				for (String s : eachId)
				{
					List<PlayerPropGroupVO> copyList1 = copyList(copyList);
					for (PlayerPropGroupVO ppgv : copyList1)
					{
						if ((ppgv.getPropId() + "").trim().equals(s.trim()))
						{
								copyList.remove(ppgv);
								if(ppgv.getPropNum()>1){
								ppgv.setPropNum(ppgv.getPropNum()-1);
								copyList.add(ppgv);
								}
							haveCount++;
							break;
						}
					}
				}
				if (haveCount != eachId.length)
				{
					request.setAttribute("jieyihint", HINT24);
					return mapping.findForward(ERROR);
				}
				String[] ss = operate.trim().split(",");
				Map<String, Integer> map = new HashMap<String, Integer>();
				for(String s : ss){
					if(map.get(s.trim())!=null){
						map.put(s.trim(), map.get(s.trim())+1);
					}else{
						map.put(s.trim(), 1);
					}
				}
				for(String s : map.keySet()){
					goodsService.removeProps(Integer.parseInt(send_id.trim()), Integer.parseInt(s.trim()), map.get(s.trim()),GameLogManager.R_MATERIAL_CONSUME);
				}
			}
			
//			同意处理
			friendService.jieyi(bi.getPPk()+"", send_id, 1);
			friendService.jieyi( send_id,bi.getPPk()+"", 1);
//			systemInfoService.insertSystemInfoBySystem(Integer.parseInt(send_id.trim()), jieYISuccMes(bi.getName(),"义"));
			int id1 = 0;
			int id2 = 0;
			/*//获取称号
			TitleVO hv = honourService.findByName("兄弟");
			TitleVO hv2 = honourService.findByName("姐妹");
			if(hv == null){
				id1 = honourService.addHonor("兄弟");
			}else{
				id1 = hv.getHoId();
			}
			if(hv2==null){
				id2 = honourService.addHonor("姐妹");
			}else{
				id2 = hv2.getHoId();
			}
			//1为男，2为女
			String[] ss = roleService.getName(send_id.trim());
			if(bi.getSex()==1){
				roleHonourDAO.addRoleHonout1(bi.getPPk(), id1, 16,ss[0]+"的");
			}else{
				roleHonourDAO.addRoleHonout1(bi.getPPk(), id2, 16,ss[0]+"的");
			}
			if(Integer.parseInt(ss[1].trim())==1){
				roleHonourDAO.addRoleHonout1(Integer.parseInt(send_id.trim()), id1, 16,bi.getName()+"的");
			}else{
				roleHonourDAO.addRoleHonout1(Integer.parseInt(send_id.trim()), id2, 16,bi.getName()+"的");
			}
			RoleEntity re = getRoleEntity(request);
			re.reloadRoleHonour(bi.getPPk());
			RoleEntity friend = roleService.getRoleInfoById(send_id.trim());
			if(friend != null){
				friend.reloadRoleHonour(friend.getBasicInfo().getPPk());
			}*/
			systemInfoService.insertSystemInfoBySystem(Integer.parseInt(send_id.trim()), jieYISuccMes(bi.getName()));
			systemInfoService.insertSystemInfoBySystem(getSysInfo(name, bi.getName()));
			request.setAttribute("jieyihint", jieYISuccMes(name));
			
			
			//测试用
//			Integer i = goodsService.findByName("xiongdi");
//			if(i!=null){
//		    goodsService.putPropToWrap(bi.getPPk(), i, 1);
//		    goodsService.putPropToWrap(Integer.parseInt(send_id.trim()), i, 1);
//			}
			return mapping.findForward(ERROR);
		}else{
//			拒绝处理
//			将扣除的结义物品返回
			
			
			
//			if(operate!=null&&!"".equals(operate.trim())){
//				String[] ss = operate.trim().split(",");
//				Map<String, Integer> map = new HashMap<String, Integer>();
//				for(String s : ss){
//					if(map.get(s.trim())!=null){
//						map.put(s.trim(), map.get(s.trim())+1);
//					}else{
//						map.put(s.trim(), 1);
//					}
//				}
//				for(String s : map.keySet()){
//					goodsService.putPropToWrap(Integer.parseInt(send_id.trim()), Integer.parseInt(s.trim()), map.get(s.trim()));
//				}
//			}
			systemInfoService.insertSystemInfoBySystem(Integer.parseInt(send_id.trim()), jieYIFailMes(bi.getName(),"义"));
			return mapping.findForward("no_refurbish_scene");
			
		}	}catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward("mess");
		}
		
	}
	
	//解除结义关系
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try{
		int p_pk = getP_Pk(request);
		QueryPage queryPage = new QueryPage();
		String page_Str = request.getParameter("page");
		int page = 0;
		if (page_Str != null)
		{
			page = Integer.parseInt(page_Str);
		}
		int size = friendService.getFriendNum1(p_pk,1);
		List<FriendVO> friendlist = null;
		int pageall = 0;
		if(size!=0){
		pageall = size / queryPage.getPageSize() + (size % queryPage.getPageSize() == 0 ? 0 : 1);
		// 查询在线玩家
		friendlist = friendService.listfriend1(p_pk, page * queryPage.getPageSize(), queryPage.getPageSize(),1);
		}
		request.setAttribute("pageall", pageall);
		request.setAttribute("page", page);
		request.setAttribute("friendlist", friendlist);
		return mapping.findForward(JIEYILIST);
		}catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward("mess");
		}
	}
	
	//解除结义关系处理
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try{
		String pByPk = request.getParameter("pByPk");
		RoleEntity friend = roleService.getRoleInfoById(pByPk);
		if(friend==null){
			request.setAttribute("jieyihint", HINT21);
			return mapping.findForward(ERROR);
		}
		BasicInfo bi = getBasicInfo(request);
		friendService.jieyi(bi.getPPk()+"", pByPk, 0);
		friendService.jieyi( pByPk,bi.getPPk()+"", 0);
		RankService rankService = new RankService();
		rankService.updateYiqiToZero(bi.getPPk());
		rankService.updateYiqiToZero(pByPk);
		/*roleHonourDAO.delRoleHonour(pByPk, bi.getName()+"的");
		roleHonourDAO.delRoleHonour(bi.getPPk()+"", friend.getBasicInfo().getName()+"的");
		RoleEntity re = getRoleEntity(request);
		re.reloadRoleHonour(bi.getPPk());
		if(friend != null){
			friend.reloadRoleHonour(friend.getBasicInfo().getPPk());
		}*/
		systemInfoService.insertSystemInfoBySystem(Integer.parseInt(pByPk.trim()), jiechuJieyiMes(bi.getName()));
		request.setAttribute("message", jiechuJieyi(friend.getBasicInfo().getName()));
		return mapping.findForward(ERROR);
		}catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward("mess");
		}
	}
	//确认解除结义
	public ActionForward n13(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try{
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		return mapping.findForward(JIECHUJIEYI);
		}catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward("mess");
		}
	}
	
	
}
