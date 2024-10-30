package com.web.action.vip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.vo.honour.TitleVO;
import com.ls.ben.cache.staticcache.honour.TitleCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.mall.CommodityVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.mall.MallService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.web.service.vip.VipService;

/**
 * ��Ա�߼�
 */
public class VipAction extends ActionBase
{
	public static Logger logger = Logger.getLogger("log.action");

	/**
	 * ʹ�û�Ա��
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		String prop_id = request.getParameter("prop_id").trim();
		
		// �����������
		GoodsService goodsService = new GoodsService();
		int del_num = goodsService.removeProps(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(prop_id), 1,GameLogManager.R_USE);
		if( del_num>0 )
		{
			PropVO prop = PropCache.getPropById(Integer.parseInt(prop_id));
			String vip_title_id = prop.getPropOperate2().trim();// VIP�ƺ�id
			TitleVO vip_title = TitleCache.getById(vip_title_id);
			roleInfo.getTitleSet().gainTitle(vip_title);//��û�Ա�ƺ�
		}

		request.setAttribute("hint", roleInfo.getTitleSet().getVIP().getHint());
		return mapping.findForward("vippage");
	}

	/**
	 * ����VIP����
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		String prop_id = request.getParameter("prop_id").trim();
		// ����ͨ������ID �õ��̳ǵ���Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(prop_id);
		if (commodityVO != null)
		{
			commodityVO.setDiscount(80);
			String hint = mallService.buy(roleInfo, commodityVO, 1 + "");// ����
			if (hint != null)
			{
				request.setAttribute("hint", hint);
				return mapping.findForward("mallvipprop");
			}
			else
			{
				hint = "�㹺��" + commodityVO.getPropName() + "��1��ԭ��"
						+ commodityVO.getOriginalPrice() + ""+GameConfig.getYuanbaoName()+"��8���Żݺ�"
						+ (commodityVO.getOriginalPrice() * 80 / 100) + ""+GameConfig.getYuanbaoName()+"��";
				request.setAttribute("hint", hint);
				return mapping.findForward("mallvipprop");
			}
		}
		else
		{
			String hint = "���ݴ�������ϵ����Ա";
			request.setAttribute("hint", hint);
			return mapping.findForward("mallvipprop");
		}

	}

	/***************************************************************************
	 * ��ȡVIP������
	 **************************************************************************/
	/** ��ת����ȡVIP���ʵ�ҳ�� */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		VipService vs = new VipService();
		String outprint = vs.getPlayerGetVipMoneyPrint();
		request.setAttribute("outprint", outprint);
		return mapping.findForward("getlaorage");
	}

	/** *���û����VIP��Ա�������ת* */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String prop_id = request.getParameter("prop_id").trim();
		// ����ͨ������ID �õ��̳ǵ���Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(prop_id);
		if (commodityVO != null)
		{
			commodityVO.setDiscount(80);
			String hint = mallService.buy(roleInfo, commodityVO, 1 + "");// ����
			if (hint != null)
			{
				EconomyService economyService = new EconomyService();
				long yuanbao = economyService.getYuanbao(roleInfo
						.getBasicInfo().getUPk());
				if (commodityVO.getOriginalPrice() > yuanbao)
				{
					return mapping.findForward("chongzhi");
				}
				else
				{
					request.setAttribute("hint", hint);
					return mapping.findForward("mallvipprop");
				}
			}
			else
			{
				if(GameConfig.getChannelId() == Channel.AIR){
					request.setAttribute("hint", "������"+GameConfig.getYuanbaoName()+"��"
							+ commodityVO.getOriginalPrice() + "������"
							+ commodityVO.getPropName() + "��1!���ڽ�ɫ�����̳�����ʹ��"
							+ commodityVO.getPropName() + "���ɳ�ΪǬ����Ա!");
				}else{
					request.setAttribute("hint", "������"+GameConfig.getYuanbaoName()+"��"
							+ commodityVO.getOriginalPrice() + "������"
							+ commodityVO.getPropName() + "��1!���ڽ�ɫ�����̳�����ʹ��"
							+ commodityVO.getPropName() + "���ɳ�Ϊ��Ѫ��Ա!");
				}
				return mapping.findForward("mallvipprop");
			}
		}
		else
		{
			String hint = "���ݴ�������ϵ����Ա";
			request.setAttribute("hint", hint);
			return mapping.findForward("mallvipprop");
		}
	}

	/** ***�����ȡ��������***** */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		VipService vs = new VipService();
		String result = vs.playerGetVipMoney(roleInfo);
		if (result == null)// ��ȡ��һ��
		{
			request.setAttribute("hint", "���Ѿ���ȡ������Ĺ�����,����������!");
			return mapping.findForward("mallvipprop");
		}
		else
		{
			if (result.equals("nomoney"))// ���ǻ�Աû�й�����ȡ
			{
				String outprint = vs.getPlayerGetVipMoneyByBuyVip(response,
						request);
				if (outprint == null)
				{
					request.setAttribute("hint", "û�л�Ա��Ʒ,����GM��ϵ!");
					return mapping.findForward("mallvipprop");
				}
				else
				{
					request.setAttribute("outprint", outprint);
					return mapping.findForward("buyvip");
				}

			}
			else
			{
				request.setAttribute("hint", result);
				return mapping.findForward("mallvipprop");
			}
		}
	}
}