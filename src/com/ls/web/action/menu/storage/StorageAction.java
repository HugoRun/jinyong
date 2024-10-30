package com.ls.web.action.menu.storage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.dao.storage.WareHouseDao;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.storage.WareHouseVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.constant.Wrap;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.mall.MallService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.storage.StorageService;

/**
 * ��Ʒ�ֿ����
 * @author ls
 */
public class StorageAction extends ActionBase{

	// ������Ʒ�б�
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		StorageService storageService = new StorageService();
		GoodsService goodsSerivce = new GoodsService();
		
		String uw_type_str = request.getParameter("w_type");
		String page_no_str = request.getParameter("page_no");
		
		int w_type=Wrap.BOOK;
		int page_no = 1;
		
		if (uw_type_str!=null)
		{
			w_type = Integer.parseInt(uw_type_str);
		}
		
		if (page_no_str!=null && !page_no_str.equals("") && !page_no_str.equals("null"))
		{
			page_no = Integer.parseInt(page_no_str);
		}
		RoleEntity  roleInfo = super.getRoleEntity(request);
		
		int p_pk = roleInfo.getBasicInfo().getPPk();

		/* ��ѯ���ݿ��иý�ɫ��û�и����Ͳֿ� */
		WareHouseVO warevo = storageService.getWareHouseByPPk(p_pk);; 
		
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
		item_page.setURL(response, "/menu/storage.do?cmd=n1&amp;w_type="+w_type);
		
		request.setAttribute("warevo", warevo);
		request.setAttribute("item_page", item_page);
		request.setAttribute("w_type", "" + w_type);
		request.setAttribute("pageNo", page_no+"");
		return mapping.findForward("storage_list");
	}	
	
	// �������
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{	
		RoleEntity  roleInfo = this.getRoleEntity(request);
		
		//װ�����ͣ�1��ҩƷ��2���飬3��װ����4������5������
		String w_type = request.getParameter("w_type");
		request.setAttribute("w_type",w_type);

		//�洢ҳ��
		request.setAttribute("page_no", request.getParameter("pageNo")+"");
		
		String resultWml = null;

		GoodsService goodsSerivce = new GoodsService();
		StorageService storageSerivce = new StorageService();
		
		int warespare = storageSerivce.getWareSpareById( roleInfo.getBasicInfo().getPPk());
		
		// ����װ��
		if (Integer.parseInt(w_type) == Wrap.EQUIP)
		{
			String equip_id = request.getParameter("equip_id");
			if(warespare >=1 ){
				resultWml = storageSerivce.storeEquip( roleInfo.getBasicInfo().getPPk(),Integer.parseInt(equip_id));
			}else {
				resultWml = "�Բ��𣬲ֿ�������";
			}
			
		} else	
		{	
			/* ��ѯ�ý�ɫ�Ĳֿ��ж��ٿ��� */
			String pg_pk = request.getParameter("pg_pk");
			PlayerPropGroupVO goodsGroup = goodsSerivce.getGoodsGroupByPgPk(Integer.parseInt(pg_pk));
			String prop_num_str = request.getParameter("prop_num");
			int prop_num;
			if (prop_num_str == null)	
			{
				
				// ����
				if (goodsGroup.getPropNum() == 1)// ֻ��һ��
				{
						
					if(warespare >=1 ){
						
						resultWml = storageSerivce.storeProps(Integer.parseInt(pg_pk),1,roleInfo);
					}else {	
						resultWml = "�Բ��𣬲ֿ�������";
					}
					
				} else
				// �ж�������û��������
				{	
					request.setAttribute("w_type", w_type);
					request.setAttribute("pg_pk", pg_pk);
					return mapping.findForward("input_num");
				}
			} else
			{
				if(prop_num_str == null || prop_num_str.equals("")||prop_num_str.equals("null")||prop_num_str.equals("all")){
					prop_num_str = goodsGroup.getPropNum()+"";
				}
				try
				{
					prop_num = Integer.parseInt(prop_num_str);
					if(prop_num < 0){
						resultWml = "�½𾯸���, ˢװ����һ��Υ����Ϊ!";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						return mapping.findForward("input_num");
					}	
					if(prop_num == 0){
						resultWml = "���ܴ洢�����Ʒ!";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						return mapping.findForward("input_num");
					}	
					if (goodsGroup.getPropNum() >= prop_num)
					{
						int need_space = goodsGroup.getPropInfo().getNeedSpace(prop_num);
						if(warespare >= need_space ){
							resultWml = storageSerivce.storeProps(Integer.parseInt(pg_pk),prop_num,roleInfo);
						}else{	
							resultWml = "�Բ��𣬲ֿ�������";
						}
						
					} else
					{
						// ��������
						resultWml = "�Բ��𣬸���Ʒ����������";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						return mapping.findForward("input_num");
					}
				} catch (NumberFormatException e)
				{
					// �����ĸ�ʽ����ȷ
					resultWml = "��ȷ������Ʒ����";
					request.setAttribute("w_type", w_type);
					request.setAttribute("pg_pk", pg_pk);
					request.setAttribute("resultWml", resultWml);
					return mapping.findForward("input_num");
				}
			}
		}
		request.setAttribute("resultWml", resultWml);
		
		try
		{
			request.getRequestDispatcher("/menu/storage.do?cmd=n1&w_type="+w_type+"&page_no="+request.getAttribute("page_no")+"").forward(request, response);
		}
		catch (Exception e)
		{
			System.out.println("��ת����.....");
		}
		return null;
	}
	
	
	/**
	 * ��ɫ����װ���鿴��ϸ��Ϣ
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		String pwPk = request.getParameter("pwPk");
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip,true);
		
		request.setAttribute("equip", equip);
		request.setAttribute("equip_display", equip_display);
		request.setAttribute("page_no", request.getParameter("page_no"));
		request.setAttribute("w_type", request.getParameter("w_type"));
		return mapping.findForward("equip_view");
	}
	
	// ��ɫ�������߲鿴��ϸ��Ϣ
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GoodsService goodsService = new GoodsService();
		
		RoleEntity  roleInfo = this.getRoleEntity(request);
				
		String goods_id = request.getParameter("goods_id");
		
		String goods_display = goodsService.getPropInfoWml(roleInfo.getBasicInfo().getPPk(),Integer.parseInt(goods_id));
		
		request.setAttribute("page_no", request.getParameter("page_no"));
		request.setAttribute("w_type", request.getParameter("w_type"));
		request.setAttribute("pg_pk", request.getParameter("pg_pk"));
		request.setAttribute("goods_display", goods_display);
		return mapping.findForward("goods_view");
	}
	
	/**���Ӳֿ�����Ķ���ȷ��ҳ��*/
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleEntity  roleInfo = super.getRoleEntity(request);
				
		WareHouseDao dao = new WareHouseDao();
		int num = dao.getPlayerWarehouseNum(roleInfo.getBasicInfo().getPPk());
		if(num == 80){
			request.setAttribute("num", ""+1);
			request.setAttribute("display", "��һ�ι�������20����λ��Ҫ���ѡ�"+GameConfig.getYuanbaoName()+"����200!");
			return mapping.findForward("submit");
		}else if(num == 100){
			request.setAttribute("num", ""+2);
			request.setAttribute("display", "�ڶ��ι�������20����λ��Ҫ���ѡ�"+GameConfig.getYuanbaoName()+"����500!");
			return mapping.findForward("submit");
		}else if(num == 120){
			request.setAttribute("num", ""+3);
			request.setAttribute("display", "�����ι�������20����λ��Ҫ���ѡ�"+GameConfig.getYuanbaoName()+"����1000!");
			return mapping.findForward("submit");
		}if(num == 140){
			request.setAttribute("num", ""+4);
			request.setAttribute("display", "���Ĵι�������20����λ��Ҫ���ѡ�"+GameConfig.getYuanbaoName()+"����1500!");
			return mapping.findForward("submit");
		}else if(num == 160){
			request.setAttribute("num", ""+5);
			request.setAttribute("display", "����ι�������20����λ��Ҫ���ѡ�"+GameConfig.getYuanbaoName()+"����2000!");
			return mapping.findForward("submit");
		}else{
			request.setAttribute("display", "���Ĳֿ���λ�Ѿ�Ϊ�����!");
			return mapping.findForward("display");
		}
		
	}
	
	/**���Ӱ�������ȥ��ҵ�Ԫ������*/
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		/**����ǵ���ƽ̨��ר������**/
		if(GameConfig.getChannelId()==Channel.TELE)
		{
			return n8(mapping,form,request,response);
		}
		RoleEntity  roleInfo = super.getRoleEntity(request);
		int num = Integer.parseInt(request.getParameter("num").toString());
		EconomyService economyService = new EconomyService();
		long yuanbao = economyService.getYuanbao(roleInfo.getBasicInfo().getUPk());
		int useyuanbao = 0;
		if (num == 1)
		{
			useyuanbao = 200;
		}
		else
			if (num == 2)
			{
				useyuanbao = 500;
			}
			else
				if (num == 3)
				{
					useyuanbao = 1000;
				}
				else
					if (num == 4)
					{
						useyuanbao = 1500;
					}
					else
						if (num == 5)
						{
							useyuanbao = 2000;
						}
						else
						{
							useyuanbao = -1;
						}

		if (useyuanbao == -1)
		{
			request.setAttribute("display", "�������������¹���<br/>");
			return mapping.findForward("display");
		}
		else
		
		if(useyuanbao > yuanbao){
			request.setAttribute("display", "����"+GameConfig.getYuanbaoName()+"��������!");
			return mapping.findForward("display");
		}else{
		WareHouseDao dao = new WareHouseDao();
		dao.updatePlayerWarehouseMaxNum(roleInfo.getBasicInfo().getPPk(), 20);
		dao.updatePlayerWarehouseNum(roleInfo.getBasicInfo().getPPk(), 20);
		economyService.spendYuanbao(roleInfo.getBasicInfo().getUPk(), useyuanbao);
		
		LogService logService = new LogService();
		logService.recordYBLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), useyuanbao+"", useyuanbao+"", "����ֿ�");
		
		request.setAttribute("display", "����ɹ�,���Ĳֿ����������20������!");
		return mapping.findForward("display");
		}
	} 
	/**
	 * ����ר��
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		RoleEntity  roleInfo = super.getRoleEntity(request);
		int num = Integer.parseInt(request.getParameter("num").toString());
		String consumCode="";
		if(num==1)
		{
			consumCode="store1";
		}
		else if(num==2)
		{
			consumCode="store2";
		}
		else if(num==3)
		{
			consumCode="store3";
		}
		else if(num==4)
		{
			consumCode="store4";
		}
		else if(num==5)
		{
			consumCode="store5";
		}
		String display = null;
		MallService ms=new MallService();
		String hint=ms.consumeForTele(request, roleInfo, consumCode, "1");
		if(hint!=null)
		{
			display=hint;
		}
		else
		{
			display="����ɹ������Ĳֿ�������10�����ӣ�";
		}
		return mapping.findForward("display");
	}
}
