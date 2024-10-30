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
			display = "���ߴ�������ϵGM!";
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
			// �õ����BUFF���ܺ�
			String propOperate1 = vo.getPropOperate1();
			// �õ��ȼ�������
			String gradeFenBu = vo.getPropOperate2();
			// ȡ��BUFF�����ȼ���BUFF
			String[] buffIdStrings = propOperate1.split(";");
			List<Probability> list = new ArrayList<Probability>();
			// �õ�����ȼ�������BUFF
			int gradeZhi = gs.getGradeNum(gradeFenBu, roleInfo.getBasicInfo()
					.getGrade());
			String npcString = buffIdStrings[gradeZhi];
			NpcGaiLv buffGaiLv = null;
			String[] npcGaolv = npcString.split(",");
			// �õ��ȼ�����
			/** ********ȡ��ʹ�õ�BUFFID********** */
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
			/** ******ȡ������*********** */
			/** **��ȡ�µ�BUFF���ϵ�BUFF�ж��Ƿ� ����**** */
			BuffVO buffvo = buffDao.getBuff(buff_id);
			BuffEffectVO buffEffectVO_old = buffEffectDao
					.getBuffEffectByBuffType(roleInfo.getBasicInfo().getPPk(),
							BuffSystem.PLAYER, buffvo.getBuffType());
			if (buffEffectVO_old != null
					&& buffEffectVO_old.getBuffId() != buff_id)
			{

				buffMenuService.setBuffStatus(roleInfo.getBasicInfo().getPPk(),
						buff_id);
				display = "һ���������,�������" + buffvo.getBuffTime() + "����"
						+ buffvo.getBuffName() + "��Ч��!����������"
						+ buffEffectVO_old.getBuffName() + "��Ч����ʧ�ˡ���";
			}
			else
			{
				buffMenuService.setBuffStatus(roleInfo.getBasicInfo().getPPk(),
						buff_id);
				display = "һ���������,�������" + buffvo.getBuffTime() + "����"
						+ buffvo.getBuffName() + "��Ч��!";
			}
			GoodsService goodsService = new GoodsService();
			goodsService.removeProps(propGroup, 1);
		}
		request.setAttribute("display", display);
		return mapping.findForward("display");
	}
}
