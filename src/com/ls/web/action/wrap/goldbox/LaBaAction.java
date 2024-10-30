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

	/** 一定概率得到物品 */
	public ActionForward n0(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		RoleService roleService = new RoleService();
		// 得到角色信息
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		if (roleInfo.getBasicInfo().getWrapSpare() < 2)
		{
			request.setAttribute("display", "您包裹空间不够,请整理包裹,预留2个以上格子!");
			return display(mapping, form, request, response);
		}
		roleInfo.getStateInfo().setCurState(PlayerState.BOX);//玩家在商城时状态受保护
		LaBaService lbs = new LaBaService();
		// 得到角色ID
		String s_p_pk = String.valueOf(roleInfo.getBasicInfo().getPPk());// request.getSession().getAttribute("pPk").toString();
		// 获得宝箱ID
		String prop_id = request.getSession().getAttribute("prop_id")
				.toString();
		// 根据宝箱ID得到宝箱信息
		PropVO pv = lbs.getGoodByID(prop_id);
		// 从特殊字段一里得到npcid以及概率
		String s_NPC[] = pv.getPropOperate1().split(",");
		// 大奖的结束值
		int daNum = Integer.parseInt(s_NPC[0].split("-")[1]);
		// 中将的结束值
		int zhongNum = Integer.parseInt(s_NPC[1].split("-")[1]);
		// 获得本次开启宝箱获得的奖励物品,根据概率，随机获得。npc_vo1作为奖励物品
		labaVO lv = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo, 0);
		// 得到奖励物品的ID
		String goodId = String.valueOf(lv.getNvo().getGoodsId());
		labaVO lv2 = null;// 第二件物品，只用来显示在页面上，玩家不会获得此物品
		labaVO lv3 = null;// 第三件物品，只用来显示在页面上，玩家不会获得此物品
		// rdNum用来判断奖励物品是属于大奖、中奖还是鼓励(小)奖。
		// getGoodsName里的值有所改变，为名字+获得大奖(或者中奖或者鼓励奖)的随机数字
		int rdNum = lv.getSNum();
		// npc_id用来判断，这里得到存起，后面会用到。
		int npc_id = lv.getNvo().getNpcID();
		// -------------点击使用后就移除掉一个宝箱-----------------------------------
		lbs.RemoveBox(roleInfo, Integer.parseInt(prop_id), Integer
				.parseInt(s_p_pk));// 在这里直接删除掉宝箱
		HttpSession otherSession = request.getSession();

		if (rdNum >= 0 && rdNum <= daNum)// 如果获得的奖励物品为大奖
		{
			lv2 = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo,
					(daNum + 1));// 那么这里得到中奖物品，只用来显示
			lv3 = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo,
					(zhongNum + 1));// 那么这里得到鼓励奖物品，只用来显示
			request.setAttribute("xiao", lv3.getNvo().getGoodsName());
			request.setAttribute("da", lv.getNvo().getGoodsName());
			request.setAttribute("zhong", lv2.getNvo().getGoodsName());
			otherSession.setAttribute("xiao", lv3.getNvo().getGoodsName());// 得到物品名字
			otherSession.setAttribute("da", lv.getNvo().getGoodsName());// 同上
			otherSession.setAttribute("zhong", lv2.getNvo().getGoodsName());// 同上
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
			otherSession.setAttribute("jName", "大奖："
					+ lv.getNvo().getGoodsName());
		}
		else
			if (rdNum >= (daNum + 1) && rdNum <= zhongNum)// 如果奖励物品为中奖
			{
				lv2 = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo,
						daNum);// 那么这里得到大奖物品，只用来显示
				lv3 = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo,
						zhongNum + 1);// 那么这里得到鼓励奖物品，只用来显示
				request.setAttribute("xiao", lv3.getNvo().getGoodsName());
				request.setAttribute("da", lv2.getNvo().getGoodsName());
				request.setAttribute("zhong", lv.getNvo().getGoodsName());
				otherSession.setAttribute("xiao", lv3.getNvo().getGoodsName());// 得到物品名字
				otherSession.setAttribute("da", lv2.getNvo().getGoodsName());// 同上
				otherSession.setAttribute("zhong", lv.getNvo().getGoodsName());// 同上
				otherSession.setAttribute("xiao_id", lv3.getNvo().getGoodsId());
				otherSession.setAttribute("da_id", lv2.getNvo().getGoodsId());
				otherSession.setAttribute("zhong_id", lv.getNvo().getGoodsId());
				otherSession.setAttribute("jName", "小奖："
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
			{// 如果获得的奖励物品为鼓励(小)奖
				lv2 = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo,
						daNum);// 那么这里得到大奖物品，只用来显示
				lv3 = lbs.getGood(prop_id, Integer.parseInt(s_p_pk), roleInfo,
						zhongNum);// 那么这里得到中奖物品，只用来显示
				request.setAttribute("xiao", lv.getNvo().getGoodsName());
				request.setAttribute("da", lv2.getNvo().getGoodsName());
				request.setAttribute("zhong", lv3.getNvo().getGoodsName());
				otherSession.setAttribute("xiao", lv.getNvo().getGoodsName());// 得到物品名字
				otherSession.setAttribute("da", lv2.getNvo().getGoodsName());// 同上
				otherSession.setAttribute("zhong", lv3.getNvo().getGoodsName());// 同上
				otherSession.setAttribute("xiao_id", lv.getNvo().getGoodsId());
				otherSession.setAttribute("da_id", lv2.getNvo().getGoodsId());
				otherSession
						.setAttribute("zhong_id", lv3.getNvo().getGoodsId());
				otherSession.setAttribute("jName", "鼓励奖："
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
		// 三种物品，要根据获得的第一个物品来决定后两个物品是哪个级别的
		request.setAttribute("count", "0");// 记录点击"幸运提示字"的次数
		// -----------------------存入session，后面用到--------------------
		otherSession.setAttribute("good_id", goodId);
		otherSession.setAttribute("str_Word", "-1");// 用来显示点击后的字还是"幸运提示符"
		otherSession.setAttribute("str_Word_2", "-1");// 同上
		otherSession.setAttribute("str_Word_3", "-1");// 同上
		otherSession.setAttribute("prop_id", prop_id);
		otherSession.setAttribute("count", "0");
		otherSession.setAttribute("npc_id", npc_id);
		otherSession.setAttribute("daNum", daNum);
		otherSession.setAttribute("zhongNum", zhongNum);
		otherSession.setAttribute("goodType", lv.getNvo().getGoodsType());
		otherSession.setAttribute("first", "0");

		// ---------------------request数据，页面显示需要---------
		request.setAttribute("str_Word", "-1");
		request.setAttribute("str_Word_2", "-1");
		request.setAttribute("str_Word_3", "-1");
		// ----------------------------------------------------
		return mapping.findForward("toOpenLaba");
	}

	/** 点击"幸运"后的看到的字 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		LaBaService lbs = new LaBaService();
		String strs[] = new String[3];
		// =====================从session里得到数据========
		// 获得的奖励物品的ID:goodid
		String goodid = request.getSession().getAttribute("good_id").toString();
		// num用于判断点击的是第几个"幸运提示字"符
		String num = request.getParameter("num");
		// 获得宝箱ID:prop_id
		String prop_id = request.getSession().getAttribute("prop_id")
				.toString();
		// npc_id 用来判断获得是大奖、中奖还是小奖
		String npc_id = request.getSession().getAttribute("npc_id").toString();
		// 根据prop_id得到宝箱数据
		PropVO pv = lbs.getGoodByID(prop_id);
		// 把NPCID组拆分
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
		if (first.equals("0"))// 如果是第一次跳到这个页面
		{
			// count 为统计次数
			int count = Integer.parseInt(request.getSession().getAttribute(
					"count").toString());
			// da为大奖物品的名字
			String da = request.getSession().getAttribute("da").toString();
			// zhong为中奖物品的名字
			String zhong = request.getSession().getAttribute("zhong")
					.toString();
			// xiao为鼓励(小)奖物品的名字
			String xiao = request.getSession().getAttribute("xiao").toString();
			// str_Word用来显示点击后的字还是"幸运提示符"
			String str_Word = request.getSession().getAttribute("str_Word")
					.toString();
			// str_Word_2同上
			String str_Word_2 = request.getSession().getAttribute("str_Word_2")
					.toString();// 同上
			// str_Word_3同上
			String str_Word_3 = request.getSession().getAttribute("str_Word_3")
					.toString();// 同上
			HttpSession otherSession = request.getSession();
			sNPCZone = pv.getPropOperate1().split(",");
			// 大奖的NPC的ID
			sNPCDA = sNPCZone[0].split("-")[0];
			// 中奖的NPC的ID
			sNPCZHONG = sNPCZone[1].split("-")[0];
			// 小奖的NPC的ID
			sNPCXIAO = sNPCZone[2].split("-")[0];
			otherSession.setAttribute("sNPCDA", sNPCDA);
			otherSession.setAttribute("sNPCZHONG", sNPCZHONG);
			otherSession.setAttribute("sNPCXIAO", sNPCXIAO);
			ArrayList<String> al = null;
			// =======================
			strs[0] = str_Word;
			strs[1] = str_Word_2;
			strs[2] = str_Word_3;
			if (npc_id.equals(sNPCDA))// 如果是大奖
			{
				al = lbs.getListThree(strs, num, count);// 调用获得大奖提示字方法
				request.setAttribute("prop_id", prop_id);
			}
			else
				if (npc_id.equals(sNPCZHONG))// 如果获得的是中奖
				{
					al = lbs.getListTwo(strs, num, count);// 调用获得中奖提示字方法
					request.setAttribute("prop_id", prop_id);
				}
				else
					if (npc_id.equals(sNPCXIAO))// 如果获得的是鼓励奖
					{
						al = lbs.getList(strs, num, count);// 调用获得鼓励奖提示字方法
						request.setAttribute("prop_id", prop_id);
					}
			// ------------新数据放入session-------------
			otherSession.setAttribute("good_id", goodid);
			otherSession.setAttribute("str_Word", al.get(0));// 用来显示点击后的字还是"幸运提示符"
			otherSession.setAttribute("str_Word_2", al.get(1));// 同上
			otherSession.setAttribute("str_Word_3", al.get(2));// 同上
			otherSession.setAttribute("prop_id", prop_id);
			count++;
			otherSession.setAttribute("count", String.valueOf(count));
			otherSession.setAttribute("npc_id", npc_id);
			// ---------------------页面数据需要-------------------
			request.setAttribute("str_Word", al.get(0));
			request.setAttribute("str_Word_2", al.get(1));
			request.setAttribute("str_Word_3", al.get(2));
			request.setAttribute("da", da);
			request.setAttribute("zhong", zhong);
			request.setAttribute("xiao", xiao);
			// ---------------------------
			if (count == 3) // 如果点击三次"幸运提示字"了
			{
				String jName = request.getSession().getAttribute("jName")
						.toString();
				request.setAttribute("jName", jName);
				otherSession.setAttribute("jName", jName);// 奖励物品的名字
				request.setAttribute("cueWord", "");// 提示信息
				return mapping.findForward("toLingQu"); // 这里会跳到领取奖励页面，
			}
			return mapping.findForward("toOpenLaba");
		}
		else
		{
			// count 为统计次数
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
			// str_Word用来显示点击后的字还是"幸运提示符"
			String str_Word = request.getSession().getAttribute("str_Word")
					.toString();
			// str_Word_2同上
			String str_Word_2 = request.getSession().getAttribute("str_Word_2")
					.toString();// 同上
			// str_Word_3同上
			String str_Word_3 = request.getSession().getAttribute("str_Word_3")
					.toString();// 同上
			String da = request.getSession().getAttribute("da").toString();
			String zhong = request.getSession().getAttribute("zhong")
					.toString();
			String xiao = request.getSession().getAttribute("xiao").toString();
			String goodName = request.getSession().getAttribute("goodName")
					.toString();
			String goodID = request.getSession().getAttribute("good_id")
					.toString();
			System.out.println("为何第一次得到的是其他物品ID：----------->" + goodID);
			strs[0] = str_Word;
			strs[1] = str_Word_2;
			strs[2] = str_Word_3;
			if (npc_id.equals(sNPCDA))// 如果是大奖
			{
				al = lbs.getListThree(strs, num, count);// 调用获得大奖提示字方法
				request.setAttribute("prop_id", prop_id);
			}
			else
				if (npc_id.equals(sNPCZHONG))// 如果获得的是中奖
				{
					al = lbs.getListTwo(strs, num, count);// 调用获得中奖提示字方法
					request.setAttribute("prop_id", prop_id);
				}
				else
					if (npc_id.equals(sNPCXIAO))// 如果获得的是鼓励奖
					{
						al = lbs.getList(strs, num, count);// 调用获得鼓励奖提示字方法
						request.setAttribute("prop_id", prop_id);
					}
			// ---------------------------
			otherSession.setAttribute("str_Word", al.get(0));// 用来显示点击后的字还是"幸运提示符"
			otherSession.setAttribute("str_Word_2", al.get(1));// 同上
			otherSession.setAttribute("str_Word_3", al.get(2));// 同上
			otherSession.setAttribute("goodName", goodName);// 同上
			request.setAttribute("str_Word", al.get(0));
			request.setAttribute("str_Word_2", al.get(1));
			request.setAttribute("str_Word_3", al.get(2));
			request.setAttribute("da", da);
			request.setAttribute("zhong", zhong);
			request.setAttribute("xiao", xiao);
			count++;
			otherSession.setAttribute("count", String.valueOf(count));
			if (count == 3) // 如果点击三次"幸运提示字"了
			{

				request.setAttribute("jName", goodName);
				otherSession.setAttribute("jName", goodName);// 奖励物品的名字
				request.setAttribute("cueWord", "");// 提示信息
				return mapping.findForward("toLingQu"); // 这里会跳到领取奖励页面，
			}
			return mapping.findForward("toOpenLaba");
		}

	}

	/**
	 * 当玩家点击我不满意的时候，这里要重新跳到初始页面，并进行道具验证
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
		// 这里得到宝箱的ID
		String pIDOfBox = request.getSession().getAttribute("prop_id")
				.toString();
		// 根据宝箱id得到宝箱数据
		PropVO pv = lbs.getGoodByID(pIDOfBox);
		// 验证玩家身上是否有可以用来刷新的道具,第一个参数为刷新宝箱所要道具的pg_pk
		boolean boo = lbs.checkHaveThisProp(pv.getPropOperate2(), roleInfo);
		if (boo == true)
		{
			// 跳回初始页,这里可以是跳回初始页，也可以不是跳回首页
			// 并删除掉一个刷新用道具
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
				// 这里重新得到了获得的物品，但是大、小、鼓励的NPCid怎么变呢
				ArrayList<String> al = lbs.getGoodTwo(da_id, zhong_id, xiao_id,
						Integer.parseInt(daNum), Integer.parseInt(zhongNum),
						sNPCDA, sNPCZHONG, sNPCXIAO, da_Name, zhong_Name,
						xiao_Name, da_Type, zhong_Type, xiao_Type);
				// ----------------------------------------------------
				// ---------------------还需要把三个物品id重新存入session--------
				HttpSession otherSession = request.getSession();
				otherSession.setAttribute("good_id", al.get(1));
				otherSession.setAttribute("npc_id", al.get(2));// NPCid
				otherSession.setAttribute("str_Word", "-1");// 用来显示点击后的字还是"幸运提示符"
				otherSession.setAttribute("str_Word_2", "-1");// 同上
				otherSession.setAttribute("str_Word_3", "-1");// 同上
				otherSession.setAttribute("count", "0");// 同上
				otherSession.setAttribute("daNum", daNum);// 同上
				otherSession.setAttribute("zhongNum", zhongNum);// 同上
				otherSession.setAttribute("first", "1");// 同上
				otherSession.setAttribute("goodName", al.get(0));// 同上
				otherSession.setAttribute("goodType", al.get(3));// 同上
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
				request.setAttribute("result", "移除道具失败");
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
			// 否则跳到提示页，当玩家没有"如虎添翼"道具时，直接在本页显示不可以再刷新，
			request.setAttribute("cueWord", "您已没有【如虎添翼】道具，可以去商城购买。");
			return mapping.findForward("toLingQu");
		}
	}

	/**
	 * 跳到商城
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
		// 跳到商城页
		return mapping.findForward("to_shangcheng");
	}

	/**
	 * 领取奖励
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
		// 获得的物品的ID
		String goodid = request.getSession().getAttribute("good_id").toString();
		// 得到角色ID
		String p_pk = request.getSession().getAttribute("pPk").toString();
		// 宝箱ID
		String prop_id = request.getSession().getAttribute("prop_id")
				.toString();
		String good_type = request.getSession().getAttribute("goodType")
				.toString();
		// 玩家领取奖励，只需要删除掉宝箱，把物品放入玩家背包中。
		// 而刷新宝箱的道具在玩家选择刷新的时候就删除掉了。
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
