package com.ls.web.action.menu.sale;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.ActionType;
import com.ls.pub.constant.Wrap;
import com.ls.pub.util.MoneyUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.player.EconomyService;
import com.pub.ben.info.Expression;

/**
 * ������Ʒ
 * @author ls
 */
public class SaleAction extends ActionBase
{
	// ��Ʒ�б�
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String w_type_str = request.getParameter("w_type");
		if (w_type_str == null)
		{
			w_type_str = (String) request.getAttribute("w_type");
		}
		String page_no_str = request.getParameter("page_no");
		if (request.getParameter("page_no") == null){
			page_no_str = request.getParameter("pageNo");
		}	
		if (page_no_str == null)	
		{
			page_no_str = (String) request.getAttribute("page_no");
		}
		
		
		RoleEntity roleInfo = super.getRoleEntity(request);
		int p_pk = roleInfo.getBasicInfo().getPPk();
		

		int w_type;
		int page_no;

		if (w_type_str == null)
		{
			w_type = Wrap.BOOK;
		}
		else
		{
			w_type = Integer.parseInt(w_type_str);
		}

		if (page_no_str == null)
		{
			page_no = 1;
		}
		else
		{
			page_no = Integer.parseInt(page_no_str);
		}
		
		String path = request.getServletPath();
		request.setAttribute("path", path);
		
		GoodsService goodsSerivce = new GoodsService();
		EconomyService economyServcie = new EconomyService();
		
		long yuanbao = economyServcie.getYuanbao(roleInfo.getBasicInfo().getUPk());
		
		QueryPage item_page = null;
		switch(w_type)
		{
			case Wrap.CURE:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.CURE,page_no);break;
			case Wrap.BOOK:item_page = goodsSerivce.getPagePropList(p_pk,Wrap.BOOK, page_no);break;
			case Wrap.EQUIP:item_page = goodsSerivce.getPageEquipOnWrap(p_pk, page_no);break;
			case Wrap.TASK:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.TASK,page_no);break;
			case Wrap.REST:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.REST,page_no);break;
			case Wrap.SHOP:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.SHOP,page_no);break;
		}
		
		item_page.setURL(response, "/menu/sale.do?cmd=n1&amp;w_type="+w_type);
		
		request.setAttribute("roleInfo", roleInfo);
		request.setAttribute("item_page", item_page);
		request.setAttribute("yuanbao", "" + yuanbao);
		request.setAttribute("w_type", "" + w_type);
		request.setAttribute("page_no", page_no+"");
		return mapping.findForward("wrap_list");
	}

	// ��������
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("page_no", request.getParameter("page_no"));
		
		RoleEntity roleInfo = super.getRoleEntity(request);

		String goods_id = request.getParameter("goods_id");
		String w_type = request.getParameter("w_type");
		String reconfirm = request.getParameter("reconfirm");

		String hint = null;

		GoodsService goodsSerivce = new GoodsService();
		int good_type = 0;
		if( Integer.parseInt(w_type)!=Wrap.EQUIP )
		{
			good_type = 4;//����
		}

		String bind_hint = goodsSerivce.isBinded(Integer.parseInt(goods_id), good_type,ActionType.SALE);
		if( bind_hint!=null )
		{
			request.setAttribute("w_type", w_type);
			request.setAttribute("resultWml", bind_hint);

			return mapping.findForward("sale_hint");
		}
		
		
		// ��װ��
		if (Integer.parseInt(w_type) == Wrap.EQUIP)
		{

			PlayerEquipVO playerEquip = goodsSerivce.getEquipByID(Integer
					.parseInt(goods_id));

			//if (goodsSerivce.isReconfirmByEquipID(playerEquip.getPwPk()))// ��Ҫ����ȷ��
			//{
				if (reconfirm.equals("0"))// û�ж���ȷ��
				{
					hint = "��ȷ��Ҫ����" + StringUtil.isoToGBK(playerEquip.getWName()) + "��";
					request.setAttribute("resultWml", hint);
					request.setAttribute("w_type", w_type);
					request.setAttribute("pw_pk", playerEquip.getPwPk());
					return mapping.findForward("equip_reconfirm");
				}
				else if (reconfirm.equals("1"))
				{
					goodsSerivce.saleEquip(roleInfo, Integer.parseInt(goods_id),playerEquip.getWPrice());
					hint = "������"
							+ StringUtil.isoToGBK(playerEquip.getWName())
							+ ",���"
							+ MoneyUtil.changeCopperToStr(playerEquip
									.getWPrice()) + "!";
				}

			//}
			else
			// ����Ҫ����ȷ��
			{
				goodsSerivce.saleEquip(roleInfo, Integer.parseInt(goods_id),playerEquip.getWPrice());
				hint = "������" + StringUtil.isoToGBK(playerEquip.getWName())
						+ ",���"
						+ MoneyUtil.changeCopperToStr(playerEquip.getWPrice())
						+ "!";
			}

			request.setAttribute("hint", hint);

		}
		// ������
		else
		{
			PlayerPropGroupVO goodsGroup = goodsSerivce
					.getGoodsGroupByPgPk(Integer.parseInt(goods_id));

			String prop_num_str = request.getParameter("prop_num");
			int prop_num;
			
			if(prop_num_str != null && !prop_num_str.equals("") && !prop_num_str.equals("null")){
			Pattern p = Pattern.compile(Expression.positive_integer_contain0_regexp);
			Matcher m = p.matcher(prop_num_str);
			boolean b = m.matches();
			if (b == false)
			{
				hint = "��������ȷ������";
				request.setAttribute("w_type", w_type);
				request.setAttribute("pg_pk", goods_id);
				request.setAttribute("resultWml", hint);
				return mapping.findForward("input_num");
			}
			if(prop_num_str.length() >6){
				hint = "��������ȷ������";
				request.setAttribute("w_type", w_type);
				request.setAttribute("pg_pk", goods_id);
				request.setAttribute("resultWml", hint);
				return mapping.findForward("input_num");
			}
			}
			
			if (prop_num_str == null)
			{
				 
				// ������
				if (goodsGroup.getPropNum() == 1)// ֻ��һ��
				{
					// ��Ҫ��Ʒ����ȷ��
					if (reconfirm == null)// û�ж���ȷ��
					{
						if (!goodsSerivce.isReconfirmByPropId(goodsGroup
								.getPropId())) // ����Ҫ����ȷ��
						{
							hint = goodsSerivce.saleProps(Integer
									.parseInt(goods_id), goodsGroup
									.getPropNum());
						}
						else
						{
							hint = "��ȷ��Ҫ����" + StringUtil.isoToGBK(goodsGroup.getPropName()) + "��";
							request.setAttribute("resultWml", hint);
							request.setAttribute("w_type", w_type);
							request.setAttribute("pg_pk", goods_id);
							request.setAttribute("prop_num", "1");

							return mapping.findForward("prop_reconfirm");
						}
					}
					else
					// �Ѿ�����ȷ��
					{
						hint = goodsSerivce.saleProps(Integer
								.parseInt(goods_id), goodsGroup.getPropNum());
					}
				}
				else
				// �ж�������û��������
				{
					request.setAttribute("w_type", w_type);
					request.setAttribute("pg_pk", goods_id);

					return mapping.findForward("input_num");
				}
			}
			else
			{
				try
				{
					String all_sale = request.getParameter("all_sale");
					if(all_sale == null || all_sale.equals("")) {
						prop_num = Integer.parseInt(prop_num_str);
						
					} else {
						prop_num = goodsGroup.getPropNum();
					}
					
					
					
					
					if (goodsGroup.getPropNum() >= prop_num)
					{
						// ��Ҫ��Ʒ����ȷ��
						if (reconfirm == null) // û�ж���ȷ��
						{
							if (!goodsSerivce.isReconfirmByPropId(goodsGroup
									.getPropId())) // ����Ҫ����ȷ��
							{
								hint = goodsSerivce.saleProps(Integer
										.parseInt(goods_id), prop_num);
							}
							else
							{
								hint = "��ȷ��Ҫ����" + StringUtil.isoToGBK(goodsGroup.getPropName())
										+ "��" + prop_num + "��";
								request.setAttribute("resultWml", hint);
								request.setAttribute("w_type", w_type);
								request.setAttribute("pg_pk", goods_id);
								request.setAttribute("prop_num", prop_num + "");

								return mapping.findForward("prop_reconfirm");
							}
						}
						else
						// �Ѿ�����ȷ��
						{
							hint = goodsSerivce.saleProps(Integer
									.parseInt(goods_id), prop_num);
						}
					}
					else
					{
						// ��������
						hint = "�Բ��𣬸���Ʒ����������";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", goods_id);
						request.setAttribute("resultWml", hint);

						return mapping.findForward("input_num");
					}
				}
				catch (NumberFormatException e)
				{
					// �����ĸ�ʽ����ȷ
					hint = "��ȷ������Ʒ����";
					request.setAttribute("w_type", w_type);
					request.setAttribute("pg_pk", goods_id);
					request.setAttribute("resultWml", hint);

					return mapping.findForward("input_num");
				}
			}
		}

		request.setAttribute("w_type", w_type);
		request.setAttribute("resultWml", hint);

		return n1(mapping, form, request, response);
	}

	// ��ʾ��Ʒ����
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo =  super.getRoleEntity(request);
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		String goods_id = request.getParameter("goods_id");

		GoodsService goodsService = new GoodsService();
		String goods_display = goodsService.getPropInfoWmlMai(p_pk,Integer.parseInt(goods_id));

		request.setAttribute("w_type", request.getParameter("w_type"));
		request.setAttribute("pg_pk", request.getParameter("pg_pk"));
		request.setAttribute("page_no", request.getParameter("page_no"));
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("goods_display", goods_display);
		return mapping.findForward("goods_display");
	}

	/**
	 * ��ɫ����װ���鿴��ϸ��Ϣ
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		
		RoleEntity  roleInfo = super.getRoleEntity(request);
		
		String pwPk = request.getParameter("pwPk");
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip,true);
		request.setAttribute("pg_pk", pwPk);
		request.setAttribute("equip_display", equip_display);
		request.setAttribute("page_no", request.getParameter("page_no"));
		return mapping.findForward("equip_display");
	}
}