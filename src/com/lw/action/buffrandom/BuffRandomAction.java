package com.lw.action.buffrandom;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.buff.BuffDao;
import com.ls.ben.dao.info.buff.BuffEffectDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.buff.BuffEffectVO;
import com.ls.ben.vo.info.buff.BuffVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.iface.function.Probability;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.buff.BuffSystem;
import com.ls.pub.util.MathUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.prop.GoldBoxService;
import com.ls.web.service.menu.buff.BuffMenuService;
import com.ls.web.service.player.RoleService;
import com.pm.constant.NpcGaiLv;

public class BuffRandomAction extends DispatchAction
{
	public ActionForward use(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String pg_pk = (String) request.getAttribute("pg_pk");
		String display = "";
		if (pg_pk == null || pg_pk.equals("null") || pg_pk.equals(""))
		{
			display = "道具错误请联系GM!";
		}
		else
		{
			PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
			BuffDao buffDao = new BuffDao();
			BuffEffectDao buffEffectDao = new BuffEffectDao();
			PropCache pc = new PropCache();
			GoldBoxService gs = new GoldBoxService();
			BuffMenuService buffMenuService = new BuffMenuService();
			PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(Integer
					.parseInt(pg_pk));
			PropVO vo = pc.getPropById(propGroup.getPropId());
			// 得到玩家BUFF的总和
			String propOperate1 = vo.getPropOperate1();
			// 得到等级的限制
			String gradeFenBu = vo.getPropOperate2();
			// 取出BUFF各个等级的BUFF
			String[] buffIdStrings = propOperate1.split(";");
			List<Probability> list = new ArrayList<Probability>();
			// 得到具体等级的所有BUFF
			int gradeZhi = gs.getGradeNum(gradeFenBu, roleInfo.getBasicInfo()
					.getGrade());
			String npcString = buffIdStrings[gradeZhi];
			NpcGaiLv buffGaiLv = null;
			String[] npcGaolv = npcString.split(",");
			// 得到等级限制
			/** ********取出使用的BUFFID********** */
			for (int i = 0; i < npcGaolv.length; i++)
			{
				buffGaiLv = new NpcGaiLv();
				String[] npcBenStrings = npcGaolv[i].split("-");
				buffGaiLv.setId(Integer.parseInt(npcBenStrings[0]));
				buffGaiLv.setProbability(Integer.parseInt(npcBenStrings[1]));
				list.add(buffGaiLv);
			}

			Probability probability = MathUtil.getRandomEntityFromList(list,
					MathUtil.DENOMINATOR);
			int buff_id = probability.getId();
			/** ******取出结束*********** */
			/** **获取新的BUFF和老的BUFF判断是否 覆盖**** */
			BuffVO buffvo = buffDao.getBuff(buff_id);
			BuffEffectVO buffEffectVO_old = buffEffectDao
					.getBuffEffectByBuffType(roleInfo.getBasicInfo().getPPk(),
							BuffSystem.PLAYER, buffvo.getBuffType());
			if (buffEffectVO_old != null
					&& buffEffectVO_old.getBuffId() != buff_id)
			{

				buffMenuService.setBuffStatus(roleInfo.getBasicInfo().getPPk(),
						buff_id);
				display = "一道金光闪过,您获得了" + buffvo.getBuffTime() + "分钟"
						+ buffvo.getBuffName() + "的效果!但是你您的"
						+ buffEffectVO_old.getBuffName() + "的效果消失了……";
			}
			else
			{
				buffMenuService.setBuffStatus(roleInfo.getBasicInfo().getPPk(),
						buff_id);
				display = "一道金光闪过,您获得了" + buffvo.getBuffTime() + "分钟"
						+ buffvo.getBuffName() + "的效果!";
			}
			GoodsService goodsService = new GoodsService();
			goodsService.removeProps(propGroup, 1);
		}
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}
}
