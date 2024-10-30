package com.ls.web.action.goods;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.info.effect.PropUseEffect;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.equip.ChangeEquipWX;
import com.ls.model.equip.EquipProduct;
import com.ls.model.equip.UpgradeEquip;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.PropType;
import com.ls.pub.util.ExchangeUtil;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.goods.prop.PropUseService;

/**
 * @author ls
 * װ������
 */
public class EquipAction extends ActionBase
{
	//********************************����װ��pkʱ������
	/**
	 * ���Ա�����װ���б�
	 */
	public ActionForward protectEquipList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pPk = (String) request.getSession().getAttribute("pPk");
		String page_no = request.getParameter("page_no");
		if( page_no==null || page_no.equals("") )
		{
			page_no = "1";
		}
		
		EquipService equipService = new EquipService();
		QueryPage equip_page = equipService.getPageProtectList(Integer.parseInt(pPk), Integer.parseInt(page_no));
		equip_page.setURL( response,"/equip.do?cmd=protectEquipList");
		request.setAttribute("equip_page",equip_page);
		request.setAttribute("pg_pk",request.getParameter("pg_pk"));
		request.setAttribute("page_no",page_no);
		return mapping.findForward("protect_equip_list");
	}
	/**
	 * ����װ����ʾ
	 */
	public ActionForward protectHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("pg_pk",request.getParameter("pg_pk"));
		request.setAttribute("pwPk",request.getParameter("pwPk"));
		request.setAttribute("page_no",request.getParameter("page_no"));
		return mapping.findForward("protect_hint");
	}
	/**
	 * ����װ��
	 */
	public ActionForward protect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		EquipService equipService = new EquipService();
		
		String pwPk = request.getParameter("pwPk");
		String pg_pk = request.getParameter("pg_pk");
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String hint = equipService.protectEquip(this.getRoleEntity(request), equip, pg_pk);
		super.setHint(request, hint);
		
		return mapping.findForward("protect_end");
	}
	
	//********************************����װ��Ʒ��
	/**
	 * ��������װ��Ʒ�ʵ�װ���б�
	 */
	public ActionForward qualityEquipList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pg_pk = request.getParameter("pg_pk");
		String w_type = request.getParameter("w_type");
		
		String pPk = (String) request.getSession().getAttribute("pPk");
		String page_no = request.getParameter("page_no");
		if( page_no==null || page_no.equals("") )
		{
			page_no = "1";
		}
		
		EquipService equipService = new EquipService();
		QueryPage equip_page = equipService.getPageQualityList(Integer.parseInt(pPk), Integer.parseInt(page_no));
		equip_page.setURL( response,"/equip.do?cmd=qualityEquipList&amp;pg_pk="+pg_pk+"&amp;w_type="+w_type);
		request.setAttribute("equip_page",equip_page);
		request.setAttribute("page_no",page_no);
		request.setAttribute("pg_pk",request.getParameter("pg_pk"));
		return mapping.findForward("quality_equip_list");
	}
	/**
	 * ����װ��Ʒ����ʾ
	 */
	public ActionForward upgradeQualityHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("pwPk",request.getParameter("pwPk"));
		request.setAttribute("pg_pk",request.getParameter("pg_pk"));
		request.setAttribute("page_no",request.getParameter("page_no"));
		return mapping.findForward("upgrade_quality_hint");
	}
	/**
	 * ����װ��Ʒ��
	 */
	public ActionForward upgradeQuality(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		EquipService equipService = new EquipService();
		
		String pwPk = request.getParameter("pwPk");
		String pg_pk = request.getParameter("pg_pk");
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String hint = equipService.upgradeQuality(this.getRoleEntity(request), equip, pg_pk);
		super.setHint(request, hint);
		
		return mapping.findForward("return_wrap_hint");
	}
	
	
	
	//********************************�����
	/**
	 * �󶨵�װ���б�
	 */
	public ActionForward bindEquipList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pPk = (String) request.getSession().getAttribute("pPk");
		String page_no = request.getParameter("page_no");
		if( page_no==null || page_no.equals("") )
		{
			page_no = "1";
		}
		
		EquipService equipService = new EquipService();
		QueryPage equip_page = equipService.getPageBindList(Integer.parseInt(pPk), Integer.parseInt(page_no));
		equip_page.setURL( response,"/equip.do?cmd=bindEquipList");
		
		request.setAttribute("equip_page",equip_page);
		request.setAttribute("pg_pk",request.getParameter("pg_pk"));
		request.setAttribute("w_type",request.getParameter("w_type"));
		request.setAttribute("page_no",page_no);
		return mapping.findForward("bind_equip_list");
	}
	/**
	 * �������ʾ
	 */
	public ActionForward unbindHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("pg_pk",request.getParameter("pg_pk"));
		request.setAttribute("pwPk",request.getParameter("pwPk"));
		request.setAttribute("page_no",request.getParameter("page_no"));
		return mapping.findForward("unbind_hint");
	}
	/**
	 * �����
	 */
	public ActionForward unbind(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		EquipService equipService = new EquipService();
		
		String pwPk = request.getParameter("pwPk");
		String pg_pk = request.getParameter("pg_pk");
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String hint = equipService.unbind(this.getRoleEntity(request), equip, pg_pk);
		super.setHint(request, hint);
		
		return mapping.findForward("return_wrap_hint");
	}
	
	//********************************�޲�����װ��
	/**
	 * ����װ���б�
	 */
	public ActionForward badEquipList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pPk = (String) request.getSession().getAttribute("pPk");
		String page_no = request.getParameter("page_no");
		if( page_no==null || page_no.equals("") )
		{
			page_no = "1";
		}
		
		EquipService equipService = new EquipService();
		QueryPage equip_page = equipService.getPageBadList(Integer.parseInt(pPk), Integer.parseInt(page_no));
		equip_page.setURL( response,"/equip.do?cmd=badEquipList");
		request.setAttribute("equip_page",equip_page);
		request.setAttribute("pg_pk",request.getParameter("pg_pk"));
		request.setAttribute("page_no",page_no);
		return mapping.findForward("bad_equip_list");
	}
	/**
	 * �޸�����װ����ʾ
	 */
	public ActionForward maintainBadHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("pg_pk",request.getParameter("pg_pk"));
		request.setAttribute("pwPk",request.getParameter("pwPk"));
		request.setAttribute("page_no",request.getParameter("page_no"));
		return mapping.findForward("maintain_bad_hint");
	}
	/**
	 * �޸�����װ��
	 */
	public ActionForward maintainBad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		EquipService equipService = new EquipService();
		
		String pwPk = request.getParameter("pwPk");
		String pg_pk = request.getParameter("pg_pk");
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String hint = equipService.maintainBad(this.getRoleEntity(request), equip, pg_pk);
		super.setHint(request, hint);
		
		return mapping.findForward("return_wrap_hint");
	}
	
	//********************************(ͨ������)��������ȫ��װ��
	public ActionForward propMaintainIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		String pg_pk = (String)request.getAttribute("pg_pk");
		
		EquipService equipService = new EquipService();
		String hint = equipService.propMaintainHint(roleInfo, pg_pk);
		
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("hint", hint);
		return mapping.findForward("prop_maintain_index");
	}
	
	public ActionForward propMaintain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		PropUseService propUseService = new PropUseService();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		
		RoleEntity roleInfo = this.getRoleEntity(request);
		String pg_pk = request.getParameter("pg_pk");
		
		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk));
		PropUseEffect propUseEffect = propUseService.useFixArmProp(roleInfo, propGroup); 
		
		request.setAttribute("hint", propUseEffect.getNoUseDisplay());
		return mapping.findForward("return_wrap_hint");
	}
	
	//********************************(ͨ���˵�)��������ȫ��װ��
	public ActionForward maintainIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = this.getRoleEntity(request);
		
		int maintain_fee = roleEntity.getEquipOnBody().getMaintainAllFee();
		if( maintain_fee==0 )
		{
			super.setHint(request, "���װ���;�û������,����Ҫ����");
		}
		else 
		{
			if( roleEntity.getBasicInfo().getCopper()<maintain_fee )
				super.setHint(request, "��Ľ�Ǯ����,�޷��޸�");
		}
		request.setAttribute("feeDes", ExchangeUtil.getMoneyDes(maintain_fee));
		return mapping.findForward("maintain_index");
	}
	
	public ActionForward maintainAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = this.getRoleEntity(request);
		roleEntity.getEquipOnBody().maintainAll();
		request.setAttribute("hint", "�����ϵ�װ���Ѿ�������ϣ�");
		return mapping.findForward("return_hint");
	}
	
	//********************************װ�����
	/**
	 * ���Դ�׵�װ���б�
	 */
	public ActionForward punchEquipList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pPk = (String) request.getSession().getAttribute("pPk");
		String page_no = request.getParameter("page_no");
		if( page_no==null || page_no.equals("") )
		{
			page_no = "1";
		}
		
		EquipService equipService = new EquipService();
		QueryPage equip_page = equipService.getPagePunchList(Integer.parseInt(pPk), Integer.parseInt(page_no));
		equip_page.setURL( response,"/equip.do?cmd=punchEquipList");
		request.setAttribute("equip_page",equip_page);
		return mapping.findForward("punch_equip_list");
	}
	
	/**
	 * �����ʾ
	 */
	public ActionForward punchHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("pwPk",request.getParameter("pwPk"));
		return mapping.findForward("punch_hint");
	}
	
	/**
	 * ���
	 */
	public ActionForward punch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		
		String pw_pk = request.getParameter("pwPk");
		
		RoleEntity roleEntity = this.getRoleEntity(request);
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pw_pk));
		
		String hint= equip.punch(roleEntity);
		request.setAttribute("hint", hint);
		return mapping.findForward("punch_end");//���ش�׽���ҳ��
	}
	
	//********************************װ����Ƕ
	/**
	 * ������Ƕ��װ���б�
	 */
	public ActionForward inlayEquipList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pPk = (String) request.getSession().getAttribute("pPk");
		String page_no = request.getParameter("page_no");
		if( page_no==null || page_no.equals("") )
		{
			page_no = "1";
		}
		
		EquipService equipService = new EquipService();
		QueryPage equip_page = equipService.getPageInlayList(Integer.parseInt(pPk), Integer.parseInt(page_no));
		equip_page.setURL( response,"/equip.do?cmd=inlayEquipList");
		request.setAttribute("equip_page",equip_page);
		request.setAttribute("pgPk",request.getParameter("pgPk"));
		return mapping.findForward("inlay_equip_list");
	}
	/**
	 * ��Ƕ��ʯ�б�
	 */
	public ActionForward inlayStoneList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		
		String pw_pk = request.getParameter("pwPk");
		String page_no = request.getParameter("page_no");
		if( page_no==null || page_no.equals(""))
		{
			page_no = "1";
		}
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pw_pk));
		QueryPage stone_list = goodsService.getPageInlayPropByEquip(equip, Integer.parseInt(page_no));
		
		request.setAttribute("stone_list",stone_list);
		request.setAttribute("page_no",page_no);
		request.setAttribute("pwPk",pw_pk);
		return mapping.findForward("inlay_stone_list");
	}
	/**
	 * ��Ƕ��ʾ
	 */
	public ActionForward inlayHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("pwPk",request.getParameter("pwPk"));
		request.setAttribute("propId",request.getParameter("propId"));
		return mapping.findForward("inlay_hint");
	}
	
	/**
	 * ��Ƕ
	 */
	public ActionForward inlay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		
		String pw_pk = request.getParameter("pwPk");
		String propId = request.getParameter("propId");
		
		RoleEntity roleEntity = this.getRoleEntity(request);
		PlayerPropGroupVO stone = goodsService.getPropGroupByPropID(roleEntity.getBasicInfo().getPPk(), Integer.parseInt(propId));
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pw_pk));
		
		String hint= equip.inlay(stone);
		request.setAttribute("hint", hint);
		return mapping.findForward("inlay_end");//���ش�׽���ҳ��
	}
	
	//********************************װ������,����ת��
	/**
	 * ����,����ת����ҳ��
	 */
	public ActionForward productIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = this.getRoleEntity(request);
		EquipProduct equipProduct = roleEntity.getEquipProduct();
		request.setAttribute("equipProduct",equipProduct);
		return mapping.findForward("upgrade_index");
	}
	
	/**
	 * ��������,����ת����װ���б�
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pPk = (String) request.getSession().getAttribute("pPk");
		String page_no = request.getParameter("page_no");
		if( page_no==null || page_no.equals("") )
		{
			page_no = "1";
		}
		
		EquipService equipService = new EquipService();
		
		RoleEntity roleEntity = this.getRoleEntity(request);
		
		EquipProduct equipProduct = roleEntity.getEquipProduct();
		
		QueryPage equip_page = null;
		if( equipProduct instanceof UpgradeEquip)
		{
			equip_page = equipService.getPageUpgradeEquip(Integer.parseInt(pPk), Integer.parseInt(page_no));
			
		}
		else if( equipProduct instanceof ChangeEquipWX)
		{
			equip_page = equipService.getPageChangeWXEquip(Integer.parseInt(pPk), Integer.parseInt(page_no));
		}
		equip_page.setURL( response,"/equip.do?cmd=n1");
		request.setAttribute("equip_page",equip_page);
		request.setAttribute("equipProduct",equipProduct);
		return mapping.findForward("upgrade_equip_list");
	}
	
	/**
	 * ѡ��Ҫ����,����ת����װ��
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		
		String pw_pk = request.getParameter("pwPk");
		RoleEntity roleEntity = this.getRoleEntity(request);
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pw_pk));
		roleEntity.getEquipProduct().selectEquip(equip);
		return this.productIndex(mapping, form, request, response);
	}
	
	/**
	 * ����,����ת��
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity =this.getRoleEntity(request);
		EquipProduct equipProduct = roleEntity.getEquipProduct();
		String hint="";
		if(equipProduct.getEquip()==null)
		{
			hint="��û���������Ե�װ����";
			request.setAttribute("hint",hint);
			return mapping.findForward("upgrade_hint");
		}
		request.setAttribute("pwPk",equipProduct.getEquip().getPwPk());
		hint = equipProduct.process();
		request.setAttribute("hint", hint);
		request.setAttribute("equipProduct", equipProduct);
		return mapping.findForward("upgrade_hint");
	}
	
	/**
	 *  ���ӳɹ��ʵı�ʯ�б�
	 */
	public ActionForward stoneList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		
		RoleEntity roleEntity = this.getRoleEntity(request);
		List<PlayerPropGroupVO> stone_list =  goodsService.getListByPropType(roleEntity.getPPk(), PropType.UPGRADEHELPPROP);
		
		request.setAttribute("stone_list", stone_list);
		return mapping.findForward("stone_list");
	}
	
	/**
	 * ����ɹ��ʱ�ʯ����
	 */
	public ActionForward inputStoneNum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String propId = request.getParameter("propId");
		
		request.setAttribute("propId", propId);
		return mapping.findForward("input_stone_num");
	}
	
	/**
	 * ���뱣�ױ�ʯ����
	 */
	public ActionForward useProtStoneHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		
		RoleEntity roleEntity = this.getRoleEntity(request);
		
		int have_num = goodsService.getPropNum(roleEntity.getBasicInfo().getPPk(), UpgradeEquip.protect_stone_id);
		
		if( have_num>0)
		{
			request.setAttribute("propId",UpgradeEquip.protect_stone_id+"");
			request.setAttribute("propName",PropCache.getPropById(UpgradeEquip.protect_stone_id).getPropName());
		}
		return mapping.findForward("protect_stone");
	}
	/**
	 * ʹ�ñ��ױ�ʯ
	 */
	public ActionForward useProtStone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String propId = request.getParameter("propId");
		
		RoleEntity roleEntity = this.getRoleEntity(request);
		EquipProduct equipProduct = roleEntity.getEquipProduct();
		String hint = equipProduct.selectProtectStone(Integer.parseInt(propId));
		
		if( hint!=null )
		{
			request.setAttribute("hint", hint+"<br/>");
		}
		
		return this.productIndex(mapping, form, request, response);
	}
	
	/**
	 * ʹ�óɹ��ʱ�ʯ
	 */
	public ActionForward useRateStone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String propId = request.getParameter("propId");
		String propNum = request.getParameter("propNum");
		
		RoleEntity roleEntity = this.getRoleEntity(request);
		EquipProduct equipProduct = roleEntity.getEquipProduct();
		String hint = equipProduct.selectRateStone(Integer.parseInt(propId), Integer.parseInt(propNum));
		
		if( hint!=null )
		{
			request.setAttribute("hint", hint+"<br/>");
			request.setAttribute("propId", propId);
			return mapping.findForward("input_stone_num");
		}
		
		return this.productIndex(mapping, form, request, response);
	}
	
	
	//***********************************ͨ�÷���
	/**
	 * �鿴װ������
	 */
	public ActionForward des(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		
		String pwPk = request.getParameter("pwPk");
		
		String pre =  request.getParameter("pre");
		if( pre!=null )
		{
			if( pre.equals("bad") || pre.equals("quality") )
			{
				request.setAttribute("pg_pk", request.getParameter("pg_pk"));
			}
		}
		
		RoleEntity roleEntity =this.getRoleEntity(request);
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		

		String equip_display = equipDisplayService.getEquipDisplay(roleEntity,equip,false);
		request.setAttribute("equip_display", equip_display);
		request.setAttribute("pwPk", pwPk);
		request.setAttribute("page_no", request.getParameter("page_no"));
		request.setAttribute("pre", pre);
		return mapping.findForward("equip_des");
	}
}
