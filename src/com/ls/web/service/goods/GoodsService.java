package com.ls.web.service.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.goods.equip.GameEquipDao;
import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.dao.info.npc.NpcShopDao;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.GoodsControlVO;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.attack.DropGoodsVO;
import com.ls.ben.vo.info.npc.NpcShopVO;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.item.ItemContainer;
import com.ls.model.item.impl.Prop;
import com.ls.model.item.impl.UEquip;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.ActionType;
import com.ls.pub.constant.BondingType;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MathUtil;
import com.ls.pub.util.MoneyUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.log.DataErrorLog;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.title.TitleService;
import com.lw.dao.specialprop.SpecialPropDAO;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.lw.service.specialprop.SpecialPropService;
import com.lw.vo.specialprop.SpecialPropVO;
import com.pm.constant.SpecialNumber;
import com.pm.service.mail.MailInfoService;
import com.pm.service.pic.PicService;
import com.pm.vo.mail.MailInfoVO;
import com.web.service.friend.FriendService;
import com.web.service.popupmsg.PopUpMsgService;

/**
 * ����:��Ʒ����������Ӱ����ﶪ����������Ʒ����ʾ��Ʒ����
 * 
 * @author ��˧ 9:49:09 AM
 */
public class GoodsService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * �õ���Ұ������prop_type���͵Ĳ��ظ��ĵ��ߣ���ս������ʹ�õĵ���
	 * 
	 * @param p_pk
	 * @return
	 */
	public List<PlayerPropGroupVO> getDisdinctAndBattleUsableProps(int p_pk, int pg_type)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getDisdinctAndBattleUsableProps(p_pk, pg_type);
	}

	public List<PlayerPropGroupVO> getDisdinctAttackPetProps(int p_pk, int prop_type)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getListByPropType(p_pk, prop_type);
	}

	/**
	 * �õ���Ұ������pw_type���͵����е���
	 * 
	 * @param p_pk
	 * @return
	 */
	public List<PlayerPropGroupVO> getPlayerPropList(int p_pk, int pg_type)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getPropsByPpk(p_pk, pg_type);
	}

	
	/**
	 * ��ҳ:�õ���Ұ������pw_type���͵����е���
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPagePropList(int p_pk, int pg_type, int page_no)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getPagePropsByPpk(p_pk, pg_type, page_no);
	}
	
	/**
	 * ��ҳ:�õ���Ұ�����Ŀ��Խ��׵ģ�������ģ�װ��
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPageSaleEquipOnWrap(int p_pk,int page_no)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		return equipDao.getPageSaleEquipOnWrap(p_pk, page_no,Equip.ON_WRAP);
	}
	
	/**
	 * ��ҳ:�õ���Ұ������װ��
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPageEquipOnWrap(int p_pk,int page_no)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		return equipDao.getPageByPosition(p_pk, page_no,Equip.ON_WRAP);
	}
	
	/**
	 * ��ҳ:�õ���Ұ������ָ�����͵�װ��
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPageByTypeOnWrap(int p_pk,int equipType,int page_no)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		return equipDao.getPageByTypeOnWrap(p_pk, equipType,page_no);
	}
	/**
	 * ��ҳ�õ���ҵ�ĳ��װ�������� ���ߣ�����
	 */
	public QueryPage getPageEquipOnWrap(int p_pk,int equip_type,int page_no)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		return equipDao.getPageEquipOnWrap(p_pk, equip_type, page_no);
	}

	/**
	 * ��ҳ:�õ���Ұ������װ��������Ƕ�ı�ʯ�б�
	 * @param equip
	 * @param page_no
	 * @return
	 */
	public QueryPage getPageInlayPropByEquip( PlayerEquipVO equip, int page_no)
	{
		if( equip==null )
		{
			return null;
		}
		
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getPagePropByTypes(equip.getPPk(), equip.getInlayPropTypeStr(), page_no);
	}


	/**
	 * ��ҳ:�õ���Ұ������pw_type���͵����е���
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPageListByPropType(int p_pk, int prop_type, int page_no)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getPageListByPropType(p_pk, prop_type, page_no);
	}
	
	/**
	 * ��ҳ:�õ���Ұ������pw_type���͵����е���
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPagePropListpet(int p_pk, int pg_type, int page_no)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getPagePropsByPpkpet(p_pk, pg_type, page_no);
	}

	/**
	 * ������Ʒ����,��һ��Ϊ��λ������һ����ĵ���
	 * 
	 * @return
	 */
	public int removeProps(PlayerPropGroupVO propGroup, int remove_num)
	{
		if (propGroup == null)
		{
			logger.debug("propGroupΪ��");
			return -1;
		}

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		if (propGroup.getPropNum() == remove_num) // �Ƴ����������ڵ���������
		{
			propGroupDao.deletePropGroup(propGroup.getPgPk());
			EquipService equipService = new EquipService();
			equipService.addWrapSpare(propGroup.getPPk(), 1); // ������Ұ���ʣ��ռ�����
		}
		else
			if (propGroup.getPropNum() > remove_num)// //�Ƴ����������ڵ���������
			{
				propGroupDao.updatePropGroupNum(propGroup.getPgPk(), propGroup
						.getPropNum()
						- remove_num);
				// ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(propGroup.getPropId(), StatisticsType.PROP,
						remove_num, StatisticsType.USED,
						StatisticsType.SHIYONG, 0);

			}
		if (propGroup.getPropType() == PropType.BOX_CURE
				|| propGroup.getPropType() == PropType.FIX_ARM_PROP)
		{ // ����õ�������װҩƷ,����뽫��Ҳ��������߱���Ҳɾ��
			propGroupDao.deletePropBoxCure(propGroup.getPgPk(), propGroup
					.getPPk());
		}
		return remove_num;
	}

	/** װ������ߵĶ��� */
	public void removeSpecialProp(int p_pk, int pg_pk, int prop_id)
	{
		PropVO prop = PropCache.getPropById(prop_id);
		
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		dao.deletePropGroup(pg_pk);
		// ������ͷŰ����ռ�
		EquipService equipService = new EquipService();
		equipService.addWrapSpare(p_pk, 1);

		GameLogManager.getInstance().propLog(p_pk, prop, 1, GameLogManager.R_DROP);
		
		/*// ִ��ͳ��
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(prop_id, 4, 1, "used", "diuqi", 0);*/
	}

	/**
	 * ������Ʒ����,���Ե��ߵ����е�������������ߵĶ���
	 * 
	 * @return   �����Ƴ�����������������򷵻�-1
	 */
	public int removeProps(int p_pk, int prop_id, int remove_num,int remove_type)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PropVO prop = PropCache.getPropById(prop_id);

		int accumulate_num = prop.getPropAccumulate();// �����ص�������

		int current_num = propGroupDao.getPropNumByByPropID(p_pk, prop_id);// ���е�����
		int total_num = current_num - remove_num;// �Ƴ�remove_num�������

		if( total_num<0 )
		{
			return -1;
		}
		
		// int current_groups
		// =(current_num+accumulate_num-1)/accumulate_num;;//���е�����
		// ��Ϊ��ҵİ�������Ʒ���Ӳ�һ����������״̬, ���Կ�������ɢ��,����Ҫ�����ݿ���ȡ�������� .
		int current_groups = propGroupDao
				.getPropGroupNumByPropID(p_pk, prop_id);// ���е�����

		int new_groups = (total_num + accumulate_num - 1) / accumulate_num;// ����goods_num�����ߺ������

		int release_groups = current_groups - new_groups;// �����ͷŵĵ�������

		int goodsgourp_goodsnum = total_num % accumulate_num;// ������������ĵ�������

		logger.info("current_groups=" + current_groups + " ,new_groups="
				+ new_groups + " ,total_num=" + total_num);
		if (release_groups > 0)
		{
			// �ͷ���Ұ���need_groups�ռ�
			propGroupDao.deletePropGroup(p_pk, prop_id, release_groups);
		}

		// ����������иõ��ߵĵ�����
		propGroupDao.updatePropGroupUpNum(p_pk, prop_id, accumulate_num);

		if (total_num > 0 && goodsgourp_goodsnum != 0)// �������ʣ�����,��ʣ����߲�Ϊ�ɵ��ӵı���ʱ��
		{
			propGroupDao.updatePropGroupNumByPropID(p_pk, prop_id,
					goodsgourp_goodsnum);
		}

		// �ͷ���Ұ���ʣ��ռ�����
		EquipService equipService = new EquipService();
		equipService.addWrapSpare(p_pk, release_groups);
		
		GameLogManager.getInstance().propLog(p_pk, prop, remove_num, remove_type);
		
		return remove_num;
	}

	/*// ɾ������ָ��id�ĵ���
	public void removeProps(int p_pk)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");
		if (roleInfo != null)
		{
			EquipService equipService = new EquipService();
			equipService.addWrapSpare(p_pk, new PlayerPropGroupDao()
					.removeByProp(p_pk, LangjunConstants.LANGJUN_GOOD_DETAIL));
		}
	}*/

	/** ͳ�Ƶ��ߵ����� *//*
	public void removePropsStatistics(int p_pk, int prop_id, int remove_num,
			String type)
	{
		removeProps(p_pk, prop_id, remove_num);
		// ִ��ͳ��
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(prop_id, StatisticsType.PROP, remove_num,StatisticsType.USED, type, 0);
	}*/

	
	/**
	 * �õ���Ʒ����
	 * @param goods_id
	 * @param goods_type
	 * @return
	 */
	public String getGoodsName(int goods_id, int goods_type)
	{

		String goods_name = ""; // װ������

		if (goods_type == GoodsType.EQUIP)
		{
			GameEquip gameEquip = EquipCache.getById(goods_id);
			goods_name = gameEquip.getName();
		}
		else if (goods_type == GoodsType.PROP)
		{
			PropVO prop = PropCache.getPropById(goods_id);
			goods_name = prop.getPropName();
		}
		return goods_name;
	}

	/**
	 * �õ���Ʒ������Ϣ
	 */
	public GoodsControlVO getGoodsControl(int goods_id, int goods_type)
	{

		if (goods_type == GoodsType.PROP)// ����
		{
			PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
			return propGroupDao.getPropControl(goods_id);
		}
		else
		// װ��
		{
			PlayerEquipDao equipDao = new PlayerEquipDao();
			PlayerEquipVO equip = equipDao.getByID(goods_id);
			GoodsControlVO goodsControl = new GoodsControlVO();
			goodsControl.setId(goods_id);
			goodsControl.setProtect(equip.getWProtect());
			goodsControl.setBonding(equip.getWBonding());
			goodsControl.setIsReconfirmed(equip.getWIsreconfirm());
			return goodsControl;
		}
	}
	
	/**
	 * ͨ�������õ�������Ϣ
	 */
	public PlayerPropGroupVO getPropByPgPk(int pg_pk )
	{
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		return playerPropGroupDao.getByPgPk(pg_pk);
	}
	
	/**
	 * �õ����ָ�����͵ĵ����б�
	 */
	public  List<PlayerPropGroupVO> getListByPropType( int p_pk,int prop_type)
	{
		PlayerPropGroupDao PlayerPropGroupDao = new PlayerPropGroupDao();
		return PlayerPropGroupDao.getListByPropType(p_pk, prop_type);
	}
	
	/**
	 * �õ���������Ƕ�õı�ʯ�б�
	 */
	public  QueryPage getPageInlayStoneList( int p_pk,int page_no)
	{
		StringBuffer prop_type_str = new StringBuffer();
		prop_type_str.append("0");
		prop_type_str.append(",").append(PropType.EQUIP_INLAY_STONE_JIN);
		prop_type_str.append(",").append(PropType.EQUIP_INLAY_STONE_MU);
		prop_type_str.append(",").append(PropType.EQUIP_INLAY_STONE_SHUI);
		prop_type_str.append(",").append(PropType.EQUIP_INLAY_STONE_HUO);
		prop_type_str.append(",").append(PropType.EQUIP_INLAY_STONE_TU);
		
		PlayerPropGroupDao PlayerPropGroupDao = new PlayerPropGroupDao();
		return PlayerPropGroupDao.getPagePropByTypes(p_pk, prop_type_str.toString(),page_no);
	}
	
	
	/*
	 * 
	 * װ��id,װ�����ͣ�����ͷ����Ь�ӣ���������ָ֮��ģ�,�����ĸ�װ����arm,accouter,Ojewely�� 1,2,1
	 * 
	 * ҩƷ-----1 ���-----2 װ��-----3 ����-----4 ����-----5
	 */

	/**
	 * ��Ҽ���Ʒ,result=-1ʱ��ʾ������������
	 * 
	 * @param p_pk
	 * @param goods_id
	 * @param goods_type
	 * @param sendInfo
	 *            ���Ϊһ���͸��ݵ�����Ʒ�Ƿ�����Ҫ��Ʒ����ϵͳ��Ϣ�����Ϊ�㣬�򲻷���
	 */
	public int pickupGoods(int p_pk, int drop_pk, int sendInfo,
			HttpServletResponse response, HttpServletRequest request)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		
		int result = -1;
		
		DropGoodsVO dropGoods = role_info.getDropSet().getItemById(drop_pk);

		result = putGoodsToWrap(p_pk, dropGoods.getGoodsId(), dropGoods
				.getGoodsType(), dropGoods.getGoodsQuality(), dropGoods
				.getDropNum(),GameLogManager.G_NPD_DROP);

		// �õ�װ����PWPK�滻��ҵ�GOODINFO�е���ʾ��Ϣ

		if (result != -1)
		{
			// �ɹ�������Ʒ��ɾ����Ʒ������ʱ��Ķ�Ӧ������
			role_info.getDropSet().removeDropItem(drop_pk);
			// ִ��ͳ��
			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(dropGoods.getGoodsId(), dropGoods.getGoodsType(),
					dropGoods.getDropNum(), StatisticsType.DEDAO,
					StatisticsType.DAGUAI, p_pk);
		}
		if (dropGoods.getDropGoodsInfo() != null
				&& dropGoods.getGoodsType() != GoodsType.PROP)
		{
			if (sendInfo != 0)
			{
				if (dropGoods.getGoodsType() == GoodsType.EQUIP)
				{
					/*GameEquip gameEquip = EquipCache.getById(dropGoods.getGoodsId());
					PlayerEquipVO myequipvo = new PlayerEquipVO();
					PartWrapDAO partEquipdDAO = new PartWrapDAO();
					PlayerEquipVO equipVO = partEquipdDAO.partWrapView(result+ "");
					EquipDisplayService equipDisplayService = new EquipDisplayService();
					EquipRelelaDao equipRelelaDao = new EquipRelelaDao();
					String displayString = equipDisplayService.getEquipDisplay(equipVO, myequipvo, "",EquipDisplayService.NOTDISPLAY);
					equipRelelaDao.insertEquipRelela(equipVO.getPwPk(),
							displayString);
					StringBuffer sBuffer = new StringBuffer();
					sBuffer.append("<anchor>");
					sBuffer.append("<go method=\"post\" href=\""
							+ response.encodeURL(GameConfig.getContextPath()
									+ "/equiprelela.do") + "\">");
					sBuffer.append("<postfield name=\"cmd\" value=\"n2\" /> ");
					sBuffer.append("<postfield name=\"pwpk\" value=\""
							+ equipVO.getPwPk() + "\" /> ");
					sBuffer.append("</go>");
					sBuffer.append(gameEquip.getName());
					sBuffer.append("</anchor> ");
					String display = dropGoods.getDropGoodsInfo();
					String str = sBuffer.toString();
					String display_str = display.replace(gameEquip.getName()+ "", str + "");
					dropGoods.setDropGoodsInfo(display_str);
					sendInfo(p_pk, dropGoods);*/
				}
			}
		}
		return result;
	}


	/**
	 * ����Ʒ�������
	 * 
	 * @param p_pk
	 * @param goods_id
	 *            ������Ʒ��id
	 * @param goods_type
	 *            ������Ʒ�����ͣ�EQUIP��PROP��
	 * @param goods_num
	 *            ������Ʒ������
	 * @return ����-1��ʾ�����ռ䲻�������ܷ���
	 */
	public int putGoodsToWrap(int p_pk, int goods_id, int goods_type,int goods_num)
	{
		return putGoodsToWrap(p_pk, goods_id, goods_type, 0, goods_num,GameLogManager.NO_LOG);
	}

	/**
	 * ����Ʒ�������
	 * 
	 * @param p_pk
	 * @param goods_id ������Ʒ��id
	 * @param goods_type ������Ʒ�����ͣ�ACCOUTE��ARM��JEWELRY��PROP��
	 * @param goods_num   ������Ʒ������
	 * @param goods_quality  ������Ʒ��Ʒ��
	 * @return ����-1��ʾ�����ռ䲻�������ܷ���
	 */
	public int putGoodsToWrap(int p_pk, int goods_id, int goods_type,int goods_quality, int goods_num,int gain_type)
	{
		int result = -1;
		// ʰȡ����
		if (goods_type == GoodsType.PROP)
		{
			result = putPropToWrap(p_pk, goods_id, goods_num,gain_type);
		}
		else// ʰȡװ��
		{
			result = putEquipToWrap(p_pk, goods_id, goods_quality,goods_num,gain_type);
		}
		return result;
	}

	/**
	 * �ѵ��߷ŵ���������鵱ǰ���߰���������Ч��
	 * @param p_pk
	 * @param goods_id			����id
	 * @param goods_num			��������
	 * @return
	 */
	public String putPropToWrap(String roleName, String propName, String propNum)
	{
		if( StringUtil.isNumber(propNum)==false )
		{
			return "��������";
		}
		
		int p_pk = new PartInfoDao().getByName(roleName);
		if( p_pk==-1 )
		{
			return "�޸ý�ɫ";
		}
		int prop_id = new PropDao().getPropIdByName(propName);
		if( prop_id==-1 )
		{
			return "�޸õ���";
		}
		
		int hint = this.putPropToWrap(p_pk, prop_id, Integer.parseInt(propNum),GameLogManager.G_SYSTEM);
		if( hint==-1 )
		{
			return "�����ռ䲻��";
		}
		return "���óɹ�";
	}
	
	/**
	 * �ѵ��߷ŵ���������鵱ǰ���߰���������Ч��
	 * @param p_pk
	 * @param goods_id			����id
	 * @param goods_num			��������
	 * @param gain_type			��õ��ߵĲ�������
	 * @return
	 */
	public int putPropToWrap(int p_pk, int goods_id, int goods_num,int gain_type)
	{
		if (goods_num == 0)
		{
			return 0;
		}
		EquipService equipService = new EquipService();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();

		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");


		PropVO prop = PropCache.getPropById(goods_id);

		int prop_id = prop.getPropID();

		int accumulate_num = prop.getPropAccumulate();// �����ص�������

		int current_num = propGroupDao.getPropNumByByPropID(p_pk, goods_id);// ���е�����

		int total_num = current_num + goods_num;// ����goods_num�������

		int current_groups = 0;// ���е�����
		if (current_num != 0)
		{
			// ��Ϊ��ҵİ�������Ʒ���Ӳ�һ����������״̬, ���Կ�������ɢ��,����Ҫ�����ݿ���ȡ�������� .
			current_groups = propGroupDao.getPropGroupNumByPropID(p_pk,
					goods_id);
		}

		int new_groups = (total_num - 1) / accumulate_num + 1;// ����goods_num�����ߺ������

		int need_groups = new_groups - current_groups;// ��Ҫ���ӵĵ�������,��Ҫ�����ĸ���Ϊ������ʾ���Ӱ�������

		int goodsgourp_goodsnum = total_num % accumulate_num;// ������������ĵ�������

		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();// ʣ��İ����ռ���

		if (need_groups > 0 && wrap_spare < need_groups)
		{
			//�����ռ䲻��
			return -1;
		}

		if (need_groups > 0)// ��Ӳ���ĵ�����ĸ���
		{
			// �ҵ�û���ص����ĵ�����
			PlayerPropGroupVO propGroup = new PlayerPropGroupVO();
			propGroup.setPgType(prop.getPropPosition());
			propGroup.setPropBonding(prop.getPropBonding());
			propGroup.setPropProtect(prop.getPropProtect());
			propGroup.setPropIsReconfirm(prop.getPropReconfirm());
			propGroup.setPropUseControl(prop.getPropUseControl());

			propGroup.setPPk(p_pk);

			propGroup.setPropId(prop.getPropID());
			propGroup.setPropType(prop.getPropClass());

			// ����µĵ����飬��������accumulate_num
			propGroup.setPropNum(accumulate_num);
			for (int i = 0; i < need_groups; i++)
			{
				propGroupDao.addPropGroup(propGroup);
			}
		}

		if (need_groups < 0)// ��Ҫ�ĵ�������Ϊ������ʾ����������Ҫ�ͷŵĵ�������
		{
			int delete_group_num = -need_groups;
			propGroupDao.deletePropGroup(p_pk, prop_id, delete_group_num);
		}

		// ������������е���idΪprop_id�ĵ��ߵ���������
		propGroupDao.updatePropGroupUpNum(p_pk, prop_id, accumulate_num);

		if (goodsgourp_goodsnum != 0)// ������������ĵ���������Ϊ0ʱ���������е�һ�����Ϊ��������������ĵ���������
		{
			propGroupDao.updateOneGroupPropNum(p_pk, prop_id,
					goodsgourp_goodsnum);
		}

		// ������ҵ�ʣ���������
		equipService.addWrapSpare(p_pk, -need_groups);

		// �ж��Ƿ����������
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		String key_prop_id = GameConfig
				.getPropertiesObject("attainprop_mallprop");
		int pg_pk = playerPropGroupDao.getPgpkByProp(p_pk, Integer
				.parseInt(key_prop_id));
		if (pg_pk == -1)
		{
			int digit = GameConfig.getAttainProp(prop_id, "attain_prop_id");// ������ҵȼ��Ƿ���ϵͳ�趨֮��
			if (digit != -1)
			{
				new PopUpMsgService().addSysSpecialMsg(p_pk, prop_id, digit,
						PopUpMsgType.ATTAIN_PROP_TYPE);
			}
		}

		GameLogManager.getInstance().propLog(p_pk, prop, goods_num, gain_type);
		return 0;
	}

	/**
	 * �ѵ��߷ŵ�������
	 * 
	 * @param p_pk
	 * @param goods_id
	 * @param w_type
	 * @return
	 */
	public int isPropToWrap(int p_pk, int goods_id, int goods_num)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk );

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();

		PropVO prop = PropCache.getPropById(goods_id);

		int accumulate_num = prop.getPropAccumulate();// �����ص�������

		int current_num = propGroupDao.getPropNumByByPropID(p_pk, goods_id);// ���е�����
		int current_groups_sql = propGroupDao.getPropGroupNumByPropID(p_pk,
				goods_id); // ��ǰ���ݿ��е�����

		int total_num = current_num + goods_num;// ����goods_num�������

		int current_groups = 0;// ���е�����
		if (current_num != 0)
		{
			// ��Ϊ��ҵİ�������Ʒ���Ӳ�һ����������״̬, ���Կ�������ɢ��,����Ҫ�����ݿ���ȡ�������� .
			// current_groups =(current_num-1)/accumulate_num+1;
			current_groups = propGroupDao.getPropGroupNumByPropID(p_pk,
					goods_id);
		}

		int new_groups = (total_num - 1) / accumulate_num + 1;// ����goods_num�����ߺ������

		int need_groups = new_groups - current_groups;// ��Ҫ���ӵĵ�������

		int goodsgourp_goodsnum = total_num % accumulate_num;// ������������ĵ�������

		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();// ʣ��İ����ռ���

		if (wrap_spare == 0)
		{
			if (current_num == 0) // ���û�пո�,�Ұ�����Ҳ�޴���,��϶��Ų���
			{
				logger.debug("�����ռ䲻��");
				return -1;
				// ���Ž�������������ԭ�������������д���Ʒ��ռ�������������ֵ,Ҳ�Ų���
			}
			else
				if (current_num + goods_num > current_groups_sql
						* accumulate_num)
				{

					logger.debug("�����ռ䲻��");
					return -1;
				}
		}
		else
		{

			if (current_num == 0)
			{
				// ���������û�д���,�Ҵ���Ʒ���������ڿ����������������ֵ,Ҳ�Ų���
				if (goods_num > wrap_spare * accumulate_num)
				{
					return -1;
				}
			}
			else
			{
				// ����������Ʒ�������ͼ�����������ԭռ�����Ϳ������֮�͵���������ֵ,Ҳ�Ų���.
				if ((current_num + goods_num) > (current_groups_sql + wrap_spare)
						* accumulate_num)
				{
					return -1;
				}
			}
		}
		return 0;
	}



	/**
	 * ��װ���������,����1����ɹ�
	 * @param roleName		��ɫ��
	 * @param equipName		װ����
	 * @param equipNum		װ������
	 * @param qulity		װ��Ʒ��
	 * @return
	 */
	public String putEquipToWrap(String roleName, String equipName,String equipNum,String qulity)
	{
		if( StringUtil.isNumber(equipNum)==false || Integer.parseInt(equipNum)<0 )
		{
			return "��������";
		}
		if( StringUtil.isNumber(qulity)==false )
		{
			return "Ʒ�ʴ���";
		}
		
		int p_pk = new PartInfoDao().getByName(roleName);
		if( p_pk==-1 )
		{
			return "�޸ý�ɫ";
		}
		int equip_id = new GameEquipDao().getIdByName(equipName);
		if( equip_id==-1 )
		{
			return "�޸�װ��";
		}
		
		int result = this.putEquipToWrap(p_pk, equip_id, Integer.parseInt(qulity), Integer.parseInt(equipNum),GameLogManager.G_SYSTEM);
		
		switch( result )
		{
			case -1:return "�����ռ䲻��";
			case -2:return "Ʒ�ʴ���";
		}
		return "���óɹ�";
	}
	
	/**
	 * ��װ��ֱ�Ӵ����������
	 * @param roleInfo
	 * @param equip_id
	 * @param equip_quality
	 * @return       ����null��ʾ�ɹ��������ʾʧ��ԭ��
	 */
	public String giveEquipOnBody(RoleEntity roleInfo, int equip_id,int equip_quality)
	{
		EquipService equipService = new EquipService();
		// ����װ���������
		PlayerEquipVO equip = equipService.createEquipByQuality(roleInfo.getPPk(), equip_id, equip_quality,GameLogManager.G_SYSTEM);
		if( equip==null )
		{
			DataErrorLog.debugData("GoodsService.giveEquipOnBody:equip_id="+equip_id+";equip_quality="+equip_quality);
			return "װ������ʧ��";
		}
		return equipService.puton(roleInfo, equip);
	}
	
	
	/**
	 * ��װ���������,����1����ɹ�
	 * @param p_pk
	 * @param equip_id				װ��id
	 * @param goods_num
	 * @param gain_type				���װ���Ĳ�������
	 * @return              ��ʾ�������������������-1����ʧ��
	 */
	public int putEquipToWrap(int p_pk, int equip_id, int goods_num)
	{
		return this.putEquipToWrap(p_pk, equip_id, 0, goods_num,GameLogManager.NO_LOG);
	}
	/**
	 * ��װ���������,����1����ɹ�
	 * @param p_pk
	 * @param equip_id				װ��id
	 * @param equip_quality			װ��Ʒ��
	 * @param goods_num
	 * @param gain_type				���װ���Ĳ�������
	 * @return              ��ʾ�������������������-1����ʧ��,-2��ʾ�Ƿ�װ��Ʒ��
	 */
	public int putEquipToWrap(int p_pk, int equip_id,int equip_quality, int goods_num,int gain_type)
	{
		int result = 0;

		if( equip_quality>Equip.Q_ORANGE || equip_quality < Equip.Q_PUTONG )
		{
			//�Ƿ�Ʒ��
			return -2;
		}
		
		EquipService equipService = new EquipService();
		
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk);
	
		// �õ�����ʣ��Ŀռ�
		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();
		PlayerEquipVO equip = null;

		if (wrap_spare < goods_num)// �жϰ�����û��ʣ��ռ�
		{
			//�����ռ䲻��,��Ʒ����Ϊ
			return -1;
		}

		for (int i = 0; i < goods_num; i++)
		{
			// ����װ���������
			equip = equipService.createEquipByQuality(p_pk, equip_id, equip_quality,gain_type);
			if( equip!=null )
			{
				result++;
			}
		}
		// װ�������������ʣ�����
		equipService.addWrapSpare(p_pk, -goods_num);

		return result;

	}

	/**
	 * ����װ��
	 * @param p_pk     
	 * @param equip_id      װ��id
	 * @param equip_type
	 */
	public void removeEquipByEquipID(int p_pk, int equip_id)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		equipDao.deleteByEquip(p_pk, equip_id);

		// ������Ұ���ʣ��ռ�����
		EquipService equipService = new EquipService();
		equipService.addWrapSpare(p_pk, 1);
	}

	/**
	 * �Ƴ�װ��
	 * @param p_pk
	 * @param pw_pk			װ��id	
	 * @param remove_type	�Ƴ���������
	 */
	public void removeEquipById(int p_pk, int pw_pk,int remove_type)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		equipDao.deleteByID(p_pk, pw_pk);

		// ������Ұ���ʣ��ռ�����
		EquipService equipService = new EquipService();
		equipService.addWrapSpare(p_pk, 1);
	}

	/** ͳ�����װ�� *//*
	public void removeEquipStatistics(int p_pk, int pw_pk, String type)
	{
		PlayerEquipDao dao = new PlayerEquipDao();
		PlayerEquipVO vo = dao.getByID(pw_pk);
		// ִ��ͳ��
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(vo.getEquipId(),GoodsType.EQUIP, 1, StatisticsType.USED,type, 0);

		removeEquipById(p_pk, pw_pk);
	}
*/
	/**
	 * ��װ��
	 * @param p_pk
	 * @param pw_pk װ��id
	 * @param price  ��Ǯ
	 */
	public void saleEquip(RoleEntity roleInfo, int pw_pk, int price)
	{
		// ɾ���������װ��
		removeEquipById(roleInfo.getPPk(), pw_pk, GameLogManager.R_SHOP);

		// ���û�����Ǯ��
		roleInfo.getBasicInfo().addCopper(price);
		
		// ���
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getPPk(), roleInfo.getName(), roleInfo.getBasicInfo().getCopper()+ "", price + "", "��װ��");

		/*// ִ��ͳ��
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(6, StatisticsType.MONEY, price, StatisticsType.DEDAO,StatisticsType.SELL, p_pk);*/
	}

	/**
	 * �жϵ����Ƿ����ܱ����ģ���������ȷ�ϣ��Ƿ񱣻����Ƿ�ʰȡ�󶨣��Ƿ�װ����
	 * 
	 * @param goods_id
	 *            ��Ʒid
	 * @param protect
	 *            �Ƿ񱣻�
	 * @param bonding
	 *            �Ƿ�װ����
	 * @return
	 */
	public String isProtected(int goods_id, int goods_type)
	{
		String hint = null;

		GoodsControlVO goodsControl = getGoodsControl(goods_id, goods_type);
		if (goodsControl == null)
		{
			hint = "��Ʒ����";
			return hint;
		}

		if (goodsControl.getProtect() == 1)// �Ƿ����ܱ�����
		{
			hint = "�ܱ�����Ʒ���ɽ���,���ɶ���";
			return hint;
		}

		return hint;
	}

	/**
	 * �ж���Ʒ�Ƿ��
	 * 
	 * @param goods_id
	 *            ��ɫװ������ߵı�id
	 * @param goods_type
	 *            ��ʾ��װ�����ǵ���
	 * @param action
	 *            ��ʾ��ҵ���Ϊ���綪�������������ף�װ��
	 * @return
	 */
	public String isBinded(int goods_id, int goods_type, int action)
	{
		String hint = null;

		GoodsControlVO goodsControl = getGoodsControl(goods_id, goods_type);
		if (goodsControl == null)
		{
			hint = "��Ʒ����";
			return hint;

		}
		return isBinded(goodsControl, action);

	}

	/**
	 * ��ͨ:�ɽ��ף����������ɶ�������ʹ�� ����:���ɽ��ף��������������ɶ�����������������ʹ��
	 * ʰȡ��:��Ʒ������Ʒ���󼴲��ɽ���,���������������������ɶ�������ʹ�� װ����:��Ʒװ���󼴲��ɽ��ף������������ɶ�������ʹ��
	 * ���װ�:��Ʒ�ɽ��ף����������ɶ������Լ�����ʹ�ã����׺���Է�ʰȡ��
	 * 
	 * @param goods_id
	 * @param goods_type
	 * @param action
	 *            ��ʾ��ҵ���Ϊ���綪�������������ף�װ����ʹ��
	 * @return
	 */
	public String isBinded(GoodsControlVO goodsControl, int action)
	{
		String hint = null;
		if (goodsControl == null)
		{
			hint = "��Ʒ����";
			return hint;
		}
		if (goodsControl.getProtect() == 1)// �Ƿ����ܱ�����
		{
			if (action == ActionType.SALE || action == ActionType.EXCHANGE
					|| action == ActionType.THROW
					|| action == ActionType.AUCTION)
			{
				hint = "�ܱ�����Ʒ���ɽ���,��������,��������,���ɶ���";
				return hint;
			}
		}
		if (goodsControl.getBonding() == BondingType.PICKBOUND)// ʰȡ��
		{
			if (action == ActionType.EXCHANGE || action == ActionType.AUCTION)
			{
				hint = "ʰȡ����Ʒ���ɽ���,��������";
				return hint;
			}
			if (goodsControl.getBonding() == BondingType.ARMBOND)
			{
				if (action == ActionType.EXCHANGE
						|| action == ActionType.AUCTION)
				{
					hint = "װ������Ʒ���ɽ���,��������";
					return hint;
				}
			}
		}
		else
			if (goodsControl.getBonding() == BondingType.EXCHANGEBOND)// ���װ�
			{
				if (action == ActionType.USE)
				{
					hint = "���װ���Ʒ����ʹ��";
					return hint;
				}
			}

		return hint;
	}


	/**
	 * �õ�װ���۸�
	 * 
	 * @param accouter_id
	 * @param accouter_type
	 * @return
	 */
	public int getEquipPrice(int equipId)
	{
		GameEquip gameEqup = EquipCache.getById(equipId);
		return gameEqup.getPrice();
	}

	/**
	 * ������
	 * 
	 * @param p_pk
	 * @param prop_id
	 * @param prop_num
	 * @return
	 */
	public String saleProps(int pg_pk, int prop_num)
	{
		StringBuffer resultWml = new StringBuffer();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(pg_pk);

		RoleEntity roleInfo = RoleService.getRoleInfoById(propGroup.getPPk()+ "");

		// ж������
		removeProps(propGroup, prop_num);

		int price = propGroup.getPropPrice();

		// ���
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo
				.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()
				+ "", price * prop_num + "", "������ID" + propGroup.getPropId()
				+ "����" + prop_num);

		// ���û�����Ǯ��
		roleInfo.getBasicInfo().addCopper(price * prop_num);

		if (propGroup.getPgType() == 41)
		{
			SpecialPropDAO specialdao = new SpecialPropDAO();
			SpecialPropVO vo = specialdao.getEquipPropByPgpk(String
					.valueOf(pg_pk));
			if (vo != null)
			{
				specialdao.delEquipItem(String.valueOf(pg_pk));
			}
		}
		resultWml.append("������" + StringUtil.isoToGBK(propGroup.getPropName())
				+ ",���" + MoneyUtil.changeCopperToStr(price * prop_num));

		// ִ��ͳ��
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(6, StatisticsType.MONEY, price, StatisticsType.DEDAO,
				StatisticsType.SELL, propGroup.getPPk());
		return resultWml.toString();
	}

	/**
	 * �õ�������
	 * 
	 * @param pg_pk
	 * @return
	 */
	public PlayerPropGroupVO getGoodsGroupByPgPk(int pg_pk)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getByPgPk(pg_pk);
	}

	/**
	 * �õ�װ������
	 * @param pg_pk
	 * @return
	 */
	public PlayerEquipVO getEquipByID(int pw_pk)
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getByID(pw_pk);
	}

	/**
	 * �õ�������Ϣ
	 * @param goods_id
	 * @return
	 */
	public PropVO getPropInfo(int prop_id)
	{
		return PropCache.getPropById(prop_id);
	}

	/**
	 * �õ��������Ϣ�ű�
	 * @param goods_id
	 * @return
	 */
	public String getPropInfoWmlMai(int pPk, int prop_id)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");

		TitleService titleService = new TitleService();
		PicService picService = new PicService();
		String propPic = picService.getPropPicStr(roleInfo, prop_id);

		StringBuffer resultWml = new StringBuffer();
		PropVO prop = getPropInfo(prop_id);
		/*
		 * �����µĶ��ŵ�ҩ������ս���л��ս���лָ�1000��HP�� ------------------------ �����۸�:100��
		 * ʹ�ü���:99�� �Ա�Ҫ��:�� ְҵҪ��:�� �Ƿ��:�� �Ƿ񱣻�:�� ʹ�� ����
		 */
		resultWml.append(prop.getPropName()).append("<br/>");
		resultWml.append(propPic);
		resultWml.append(prop.getPropDisplay()).append("<br/>");
		resultWml.append("�����۸�:").append( MoneyUtil.changeCopperToStr(prop.getPropSell())).append("<br/>");
		resultWml.append("ʹ�ü���:" + getPropDisplay(prop.getPropReLevel()) + "��").append("<br/>");

		if (prop.getPropSex() != 0 && !(prop.getPropSex() + "").equals(""))
		{
			resultWml.append("�Ա�Ҫ��:").append( ExchangeUtil.exchangeToSex(prop.getPropSex())).append("<br/>");
		}
		if (prop.getPropJob() != null && !prop.getPropJob().equals("")
				&& !prop.getPropJob().equals("0"))
		{
			resultWml.append("ְҵҪ��:").append(titleService.getTitleNamesByTitleIDs(prop.getPropJob())).append("<br/>");
		}
		return resultWml.toString();
	}

	/**
	 * �õ��������Ϣ�ű�,�̵�ר��,��ʾ����۸�
	 * @param goods_id
	 * @return
	 */
	public String getPropInfoWmlMai(RoleEntity roleInfo, NpcShopVO npcShop)
	{
		int prop_id = npcShop.getGoodsId();
		int prop_buy_price =npcShop.getPrice(roleInfo);
		TitleService titleService = new TitleService();
		PicService picService = new PicService();
		String propPic = picService.getPropPicStr(roleInfo, prop_id);

		StringBuffer resultWml = new StringBuffer();
		PropVO prop = getPropInfo(prop_id);
		/*
		 * �����µĶ��ŵ�ҩ������ս���л��ս���лָ�1000��HP�� ------------------------ ����۸�:100��
		 * ʹ�ü���:99�� �Ա�Ҫ��:�� ְҵҪ��:�� �Ƿ��:�� �Ƿ񱣻�:�� ʹ�� ����
		 */
		resultWml.append(prop.getPropName()).append("<br/>");
		resultWml.append(propPic);
		resultWml.append(prop.getPropDisplay()).append("<br/>");
		resultWml.append("ʹ�ü���:").append(getPropDisplay(prop.getPropReLevel())).append("��").append("<br/>");
		resultWml.append("�Ա�Ҫ��:").append(ExchangeUtil.exchangeToSex(prop.getPropSex())).append("<br/>");
		resultWml.append("ְҵҪ��:").append(titleService.getTitleNamesByTitleIDs(prop.getPropJob())).append("<br/>");
		resultWml.append("------------------------").append("<br/>");
		resultWml.append("����۸�:").append(MoneyUtil.changeCopperToStr(prop_buy_price)).append("<br/>");
		return resultWml.toString();
	}

	/**
	 * �õ�������������Ʒ��ʾ
	 * @param goods_id
	 * @return
	 */
	public String getPropInfoWml(int pPk, int prop_id)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");
		PropVO prop = getPropInfo(prop_id);
		
		if( prop==null )
		{
			return "ϵͳ���޸õ���,����ϵGM<br/>";
		}

		TitleService titleService = new TitleService();
		PicService picService = new PicService();
		String propPic = picService.getPropPicStr(roleInfo, prop_id);

		StringBuffer resultWml = new StringBuffer();
		/*
		 * �����µĶ��ŵ�ҩ������ս���л��ս���лָ�1000��HP�� ------------------------ ����۸�:100��
		 * ʹ�ü���:99�� �Ա�Ҫ��:�� ְҵҪ��:�� �Ƿ��:�� �Ƿ񱣻�:�� ʹ�� ����
		 */
		resultWml.append(prop.getPropName());
		resultWml.append("<br/>");
		resultWml.append(propPic);
		resultWml.append(prop.getPropDisplay());
		resultWml.append("<br/>");
		resultWml.append("--------------------");
		resultWml.append("<br/>");
		resultWml.append("�����۸�:").append(prop.getPropSell());
		resultWml.append("��ʯ");
		resultWml.append("<br/>");
		resultWml.append("ʹ�ü���:").append(getPropDisplay(prop.getPropReLevel())).append("��");
		resultWml.append("<br/>");
		resultWml.append("�Ա�Ҫ��:").append(ExchangeUtil.exchangeToSex(prop.getPropSex()));
		resultWml.append("<br/>");
		resultWml.append("ְҵҪ��:").append(titleService.getTitleNamesByTitleIDs(prop.getPropJob()));
		resultWml.append("<br/>");
		return resultWml.toString();
	}

	/**
	 * �õ�ר�ŵ��й���װҩ�Ľ���
	 * @param goods_id
	 * @return
	 */
	public String getPropInfoWml(int pPk, int prop_id, String pg_pk)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");

		TitleService titleService = new TitleService();
		PicService picService = new PicService();
		String propPic = picService.getPropPicStr(roleInfo, prop_id);

		StringBuffer resultWml = new StringBuffer();
		PropVO prop = getPropInfo(prop_id);
		/*
		 * �����µĶ��ŵ�ҩ������ս���л��ս���лָ�1000��HP�� ------------------------ �����۸�:100��
		 * ʹ�ü���:99�� �Ա�Ҫ��:�� ְҵҪ��:�� �Ƿ��:�� �Ƿ񱣻�:�� ʹ�� ����
		 */
		resultWml.append(prop.getPropName());
		resultWml.append("<br/>");
		resultWml.append(propPic);
		resultWml.append(prop.getPropDisplay());
		resultWml.append("<br/>");
		if (prop.getPropClass() != PropType.BOX_CURE)
		{ // �����������װ��ҩƷ, ��������ʾ��ʣ��Ѫ����������
			resultWml.append("------------------------");
			resultWml.append("<br/>");
		}
		else
		{
			resultWml.append("ʣ���������").append(
					getSpecialPropNum(pPk, pg_pk, prop.getPropOperate2(),
							SpecialNumber.KUNZHUANG)).append("��").append(
					"<br/>");
		}
		resultWml.append("�����۸�:").append( MoneyUtil.changeCopperToStr(prop.getPropSell()));
		resultWml.append("<br/>");
		resultWml.append("ʹ�ü���:").append(getPropDisplay(prop.getPropReLevel())).append( "��");
		resultWml.append("<br/>");
		resultWml.append("�Ա�Ҫ��:").append( ExchangeUtil.exchangeToSex(prop.getPropSex()));
		resultWml.append("<br/>");
		resultWml.append("ְҵҪ��:").append( titleService.getTitleNamesByTitleIDs(prop.getPropJob()));
		resultWml.append("<br/>");
		return resultWml.toString();
	}

	/**
	 * �õ�������Ϣ�ű�
	 * 
	 * @param goods_id
	 * @return
	 */
	public String getPropInfoWml(int pPk, int prop_id, String w_type,
			String pg_pk, String goods_type, String isReconfirm,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");

		TitleService titleService = new TitleService();
		StringBuffer resultWml = new StringBuffer();
		PropVO prop = getPropInfo(prop_id);

		/*
		 * �����µĶ��ŵ�ҩ������ս���л��ս���лָ�1000��HP�� ------------------------ �����۸�:100��
		 * ʹ�ü���:99�� �Ա�Ҫ��:�� ְҵҪ��:�� �Ƿ��:�� �Ƿ񱣻�:�� ʹ�� ����
		 */
		resultWml.append(prop.getPropName());
		resultWml.append("<br/>");
		if(roleInfo.getSettingInfo().getGoodsPic()==1 ){
			resultWml.append(prop.getPicDisplay());
		}
		resultWml.append(prop.getPropDisplay());
		resultWml.append("<br/>");
		if (prop.getPropClass() != PropType.BOX_CURE)
		{
			resultWml.append("------------------------");
			resultWml.append("<br/>");
		}
		else
		{
			resultWml.append("ʣ���������").append(
					getSpecialPropNum(pPk, pg_pk, prop.getPropOperate2(),
							SpecialNumber.KUNZHUANG)).append("��").append(
					"<br/>");
		}
		resultWml.append("�����۸�:").append( MoneyUtil.changeCopperToStr(prop.getPropSell()));
		resultWml.append("<br/>");
		resultWml.append("ʹ�ü���:" + getPropDisplay(prop.getPropReLevel()) + "��");
		resultWml.append("<br/>");
		resultWml.append("�Ա�Ҫ��:").append( ExchangeUtil.exchangeToSex(prop.getPropSex()));
		resultWml.append("<br/>");
		resultWml.append("ְҵҪ��:").append(titleService.getTitleNamesByTitleIDs(prop.getPropJob()));
		resultWml.append("<br/>");
		resultWml.append(prop.getStatusDisplay());
		/*String datestr = dgdao.getUsePropTime(prop_id);
		if (!datestr.equals("0"))
		{
			String newdatestr = datestr.replace(",", "~");
			resultWml.append("ʹ��ʱ��:" + newdatestr);
			resultWml.append("<br/>");
		}*/
		if (Integer.parseInt(w_type) != 4)
		{
			resultWml.append("<anchor> ");

			resultWml.append("<go method=\"post\" href=\"").append(response.encodeURL(GameConfig.getContextPath())).append( "/wrap.do").append( "\">");

			resultWml.append("<postfield name=\"cmd\" value=\"n3\" /> ");
			resultWml.append("<postfield name=\"w_type\" value=\"").append( w_type).append("\" /> ");
			resultWml.append("<postfield name=\"pg_pk\" value=\"").append( pg_pk).append( "\" /> ");
			resultWml.append("<postfield name=\"goods_id\" value=\"").append(prop_id).append("\" /> ");
			resultWml.append("<postfield name=\"goods_type\" value=\"").append(goods_type).append("\" /> ");
			resultWml.append("</go>");
			resultWml.append("ʹ�� ");
			resultWml.append("</anchor> ");

			resultWml.append("<anchor> ");
			resultWml.append("<go method=\"post\" href=\"").append(response.encodeURL(GameConfig.getContextPath())).append("/wrap.do").append( "\">");
			resultWml.append("<postfield name=\"cmd\" value=\"n7\" /> ");
			resultWml.append("<postfield name=\"w_type\" value=\"").append(w_type).append( "\" /> ");
			resultWml.append("<postfield name=\"pg_pk\" value=\"").append(pg_pk).append("\" /> ");
			resultWml.append("<postfield name=\"goods_id\" value=\"").append(prop_id).append("\" /> ");
			resultWml.append("<postfield name=\"goods_type\" value=\"").append(goods_type).append("\" /> ");
			resultWml.append("<postfield name=\"isReconfirm\" value=\"").append(isReconfirm ).append("\" /> ");
			resultWml.append("</go>");
			resultWml.append("����");
			resultWml.append("</anchor> ");
			resultWml.append("<br/>");
		}
		return resultWml.toString();
	}

	/**
	 * �õ�������Ϣ�ű�
	 * 
	 * @param goods_id
	 * @return
	 */
	public String getPropInfoWmlTong(int pPk, int prop_id, String pg_pk)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");

		TitleService titleService = new TitleService();
		PicService picService = new PicService();
		String propPic = picService.getPropPicStr(roleInfo, prop_id);
		StringBuffer resultWml = new StringBuffer();
		PropVO prop = getPropInfo(prop_id);

		/*
		 * �����µĶ��ŵ�ҩ������ս���л��ս���лָ�1000��HP�� ------------------------ �����۸�:100��
		 * ʹ�ü���:99�� �Ա�Ҫ��:�� ְҵҪ��:�� �Ƿ��:�� �Ƿ񱣻�:�� ʹ�� ����
		 */
		resultWml.append(prop.getPropName());
		resultWml.append("<br/>");
		resultWml.append(propPic);
		resultWml.append(prop.getPropDisplay());
		resultWml.append("<br/>");
		if (prop.getPropClass() != PropType.BOX_CURE)
		{
			resultWml.append("------------------------");
			resultWml.append("<br/>");
		}
		else
		{
			resultWml.append("ʣ���������").append(
					getSpecialPropNum(pPk, pg_pk, prop.getPropOperate2(),
							SpecialNumber.KUNZHUANG)).append("��").append(
					"<br/>");
		}
		resultWml.append("�����۸�:").append(MoneyUtil.changeCopperToStr(prop.getPropSell()));
		resultWml.append("<br/>");
		resultWml.append("ʹ�ü���:").append(getPropDisplay(prop.getPropReLevel()) + "��");
		resultWml.append("<br/>");
		resultWml.append("�Ա�Ҫ��:").append(ExchangeUtil.exchangeToSex(prop.getPropSex()));
		resultWml.append("<br/>");
		resultWml.append("ְҵҪ��:").append(titleService.getTitleNamesByTitleIDs(prop.getPropJob()));
		resultWml.append("<br/>");
		resultWml.append(prop.getStatusDisplay());

		return resultWml.toString();
	}

	/**
	 * �����װҩƷ��ʣ��ʹ����
	 * 
	 * @param pk
	 * @param pg_pk
	 * @return
	 */
	private int getSpecialPropNum(int pk, String pg_pk, String propOperate2,
			int sp_type)
	{
		PlayerPropGroupDao ppgdao = new PlayerPropGroupDao();
		int surplus = ppgdao.getSurplus(pk, Integer.valueOf(pg_pk), sp_type);
		if (surplus == 0)
		{
			surplus = Integer.valueOf(propOperate2);
		}
		return surplus;
	}

	/** �Ե���ʹ�õȼ������������ʹ�����޵ȼ�Ϊ1000��, �Ͳ�����ʾ */
	private String getPropDisplay(String propReLevel)
	{
		String[] maxLevel = propReLevel.split(",");
		if (Integer.valueOf(maxLevel[1]) == 1000)
		{
			return maxLevel[0];
		}
		else
		{
			return propReLevel;
		}
	}

	/**
	 * ����Ʒ
	 * 
	 * @param p_pk
	 * @param npcshop_id
	 * @param goods_num
	 * @param sceneId
	 * @return
	 */
	public String buyGoods(RoleEntity roleInfo, int npcshop_id, int goods_num)
	{
		StringBuffer resultWml = new StringBuffer();

		if (goods_num == 0)
		{
			logger.info("������������Ϊ0");
			resultWml.append("����������!<br/>");
			return resultWml.toString();
		}

		NpcShopDao npcShopDao = new NpcShopDao();

		NpcShopVO npcshop = npcShopDao.getNpcShopById(npcshop_id);
		if (npcshop == null)
		{
			return null;
		}

		int total_price = npcshop.getPrice(roleInfo) * goods_num;

		long money = roleInfo.getBasicInfo().getCopper();

		// ����
		if (money < total_price)
		{
			resultWml.append("����" + goods_num + "��" + npcshop.getGoodsName()
					+ ",��Ҫ" + MoneyUtil.changeCopperToStr(total_price)
					+ ",����Ǯ������!<br/>");
		}
		else
		{
			// ����
			if (putGoodsToWrap(roleInfo.getPPk(), npcshop.getGoodsId(), npcshop.getGoodsType(), goods_num) == -1)
			{
				resultWml.append("����ʣ��ռ䲻��!<br/>");
			}
			else
			{

				// ���
				LogService logService = new LogService();
				logService.recordMoneyLog(roleInfo.getPPk(),roleInfo.getName(), roleInfo.getBasicInfo().getCopper()+ ""
						,-total_price + "", "�����ID"+ npcshop.getGoodsName() + "����" + goods_num);

				roleInfo.getBasicInfo().addCopper(-total_price);
				resultWml.append("������").append(MoneyUtil.changeCopperToStr(total_price)).append("������");
				resultWml.append(npcshop.getGoodsName()).append("��").append(goods_num).append("<br/>");
				// ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY, total_price,StatisticsType.USED, StatisticsType.BUY, roleInfo.getPPk());


			}
		}

		return resultWml.toString();
	}

	/**
	 * �õ���������
	 * 
	 * @param p_pk
	 * @param prop_id
	 * @return
	 */
	public int getPropNum(int p_pk, int prop_id)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getPropNumByByPropID(p_pk, prop_id);
	}

	/**
	 * �жϵ����Ƿ���Ҫ����ȷ��
	 */
	public boolean isReconfirmByPropId(int prop_id)
	{
		boolean isReconfirm = false;
		PropVO propVO = PropCache.getPropById(prop_id);

		if (propVO != null && propVO.getPropReconfirm() == 0)
		{
			isReconfirm = true;
		}

		return isReconfirm;

	}


	/**
	 * ������Ʒ,
	 * @param loser ʧ����
	 * @param winer  ʤ����
	 */
	public void dropGoods(Fighter loser, Fighter winer)
	{
		int drop_num = getDropNum(loser.getPPkValue());

		logger.info(" ������������Ʒ����=" + drop_num);
		StringBuffer sb = new StringBuffer();
		if( drop_num>0 )
		{
			PlayerPropGroupDao pgDao = new PlayerPropGroupDao();
			PlayerEquipDao equipDao = new PlayerEquipDao();
			//�õ�δ�ܱ����ĵ���
			List<Integer> prop_key_list = pgDao.getNoProtectPropId(loser.getPPk());
			//�õ�δ�ܱ�����װ��
			List<Integer> equip_key_list = equipDao.getNoProtectEquipId(loser.getPPk());
			
			if( prop_key_list.size()==0 && equip_key_list.size()==0 )
			{
				//����Ʒ�ɵ�
				return;
			}
			ItemContainer drop_list = new ItemContainer();

			sb.append("�������Ʒ��:");
			for (int i = 0; i < drop_num; i++)
			{
				if( prop_key_list.size()==0 && equip_key_list.size()==0 )
				{
					//���û�е�����Ʒ���˳�
					break;
				}
				
				if( prop_key_list.size()==0 )
				{
					//���û�е��������װ��
					drop_list.add(dropEquip(loser, equip_key_list));
				}
				else if( equip_key_list.size()==0 )
				{
					//���û��װ����������
					drop_list.add(dropProp(loser, prop_key_list));
				}
				else//���װ�������߶��У���50%�ĸ���ѡ�����װ�������
				{
					if( MathUtil.isAppearByPercentage(50))
					{
						drop_list.add(dropEquip(loser, equip_key_list));
					}
					else
					{
						drop_list.add(dropProp(loser, prop_key_list));
					}
				}
			}
			
			if( drop_list.getItemGroupNum()>0 )
			{
				//����е�����Ʒ
				//��ʧ������ʾ��Ϣ
				loser.appendKillDisplay("�������Ʒ��:").append(drop_list.getDes()).append("<br/>");
				//��ʤ���߷����ʼ�
				winer.appendKillDisplay(loser.getPName()).append("�����������Ʒ�뵽�ʼ�����ȡ").append("<br/>");
				
				MailInfoService mailInfoService = new MailInfoService();
				MailInfoVO mail_info = new MailInfoVO();
				StringBuffer title = new StringBuffer();//�ʼ�����
				title.append(loser.getPName()).append("����ɱ���������Ʒ");
				mail_info.createPKDropItemMail(winer.getPPk(), title.toString(),drop_list.getDes(), drop_list);
				mailInfoService.sendMail(mail_info);
			}
		}
		loser.setDropDisplay(sb.toString());
	}



	/**
	 * �������һ��װ��
	 * @param loser
	 * @param equip_key_list			���Ե����װ��id�б�
	 * @return
	 */
	private UEquip dropEquip(Fighter loser, List<Integer> equip_key_list)
	{
		UEquip u_equip = null;
		if ( equip_key_list != null && equip_key_list.size() != 0)
		{
			int random_index = MathUtil.getRandomBetweenXY(0, equip_key_list.size()-1);
			int pw_pk = equip_key_list.remove(random_index);
			EquipService equipService = new EquipService();
			PlayerEquipDao playerEquipDao = new PlayerEquipDao();
			PlayerEquipVO equipvo = playerEquipDao.getByID(pw_pk);
			if ( equipvo.isProtectOnPK()==false)//���ܱ�����װ��
			{
				if( equipvo.isOnBody() )
				{
					RoleEntity role_info = RoleService.getRoleInfoById(loser.getPPk());
					role_info.getEquipOnBody().takeoff(equipvo);
				}
				else
				{
					// ������Ұ���ʣ��ռ�����
					equipService.addWrapSpare(loser.getPPk(), 1);
				}
				equipvo.drop();//װ������
				u_equip = new UEquip(equipvo);
			}
		}
		return u_equip;
	}

	/**
	 *  �������һ�����
	 * @param loser
	 * @param prop_key_list			���Ե���ĵ���id�б�
	 * @return
	 */
	private Prop dropProp(Fighter loser, List<Integer> prop_key_list)
	{
		Prop drop_prop = null;//����ĵ���
		
		if (prop_key_list != prop_key_list && prop_key_list.size() != 0)
		{
			//�õ����pg_pk
			int random_index = MathUtil.getRandomBetweenXY(0, prop_key_list.size()-1);
			int pg_pk = prop_key_list.remove(random_index);
			PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
			PlayerPropGroupVO vo = playerPropGroupDao.getByPgPk(pg_pk);
			if( vo!=null )
			{
				drop_prop = new Prop(vo.getPropId(),vo.getPropNum());
				playerPropGroupDao.deletePropGroup(pg_pk);
				// ������Ұ���ʣ��ռ�����
				EquipService equipService = new EquipService();
				equipService.addWrapSpare(loser.getPPk(), 1);
				
				GameLogManager.getInstance().propLog(loser.getPPk(), vo.getPropInfo(), vo.getPropNum(),GameLogManager.R_DEAD);
			}

		}
		return drop_prop;
	}

	/**
	 * ��õ�����Ʒ������
	 * @param pkValue
	 * @return
	 */
	private int getDropNum(int pkValue)
	{
		int dropNum = 1;
		if (pkValue > 200)
		{
			if (pkValue % 100 == 0)
			{
				dropNum = (pkValue - 200) / 100 + 1;
			}
			else
			{
				dropNum = (pkValue - 200) / 200 + 2;
			}

		}
		else
			if (pkValue == 0)
			{
				dropNum = 0;
			}

		return dropNum;
	}

	/**
	 * ������װҩƷ���ܿ�������
	 * 
	 * @param propGroup
	 * @param add_mp
	 */
	public void reduceBoxCure(PlayerPropGroupVO propGroup, int add_mp,
			int sp_type)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		propGroupDao.reduceBoxCure(propGroup, add_mp, sp_type);
	}

	/**
	 * ��Ҽ�ȫ����Ʒ,result=-1ʱ��ʾ������������
	 * @param p_pk
	 * @param goods_id
	 * @param goods_type
	 */
	public int pickupAllGoods(int p_pk)
	{
		int result = -1;

		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");

		List<DropGoodsVO> dropGoods = roleInfo.getDropSet().getList();
		DropGoodsVO dropGoodsVO = null;

		logger.info("ȡ������dropGoods.size=" + dropGoods.size());

		// ����ʣ��Ŀո���
		int wrapSpare = roleInfo.getBasicInfo().getWrapSpare();

		if (wrapSpare < 0)
		{
			return result;
		}

		List<DropGoodsVO> equipGoods = new ArrayList<DropGoodsVO>();

		for (int i = dropGoods.size() - 1; i >= 0; i--)
		{
			dropGoodsVO = dropGoods.get(i);
			if (dropGoodsVO.getGoodsType() != 4)
			{
				dropGoods.remove(i);
				equipGoods.add(dropGoodsVO);
			}
		}

		logger.info("equipGoods.size=" + equipGoods.size()
				+ " ,dropGoods.size=" + dropGoods.size());
		int handleWrapSpare = wrapSpare - equipGoods.size();
		// ���˼���װ����Ŀո���
		if (handleWrapSpare < 0)
		{
			return result;
		}

		// ���Դ���ÿ�����ߵ���Ҫռ�ݵİ����ռ䣬ǰһ������Ϊ����id,��һ������Ϊ�����ռ�
		Map<Integer, Integer[]> map = new HashMap<Integer, Integer[]>();

		Integer[] eachPropSpare = null;
		for (int i = dropGoods.size() - 1; i >= 0; i--)
		{
			dropGoodsVO = dropGoods.get(i);
			eachPropSpare = propNeedSpare(dropGoodsVO);
			logger.info("name=" + dropGoodsVO.getGoodsName()
					+ " ,eachPropSpare[0]=" + eachPropSpare[0]
					+ " ,eachPropSpare[1]=" + eachPropSpare[1]);
			if (eachPropSpare[0] == -1)
			{
				return -1;
			}
			else
			{
				handleWrapSpare = handleWrapSpare - eachPropSpare[0];
				if (handleWrapSpare < 0)
				{
					return -1;
				}
				map.put(dropGoodsVO.getGoodsId(), eachPropSpare);
			}
		}

		logger.info("����ʣ��ռ�=" + handleWrapSpare);

		if (handleWrapSpare >= 0)
		{
			for (int i = dropGoods.size() - 1; i >= 0; i--)
			{
				dropGoodsVO = dropGoods.get(i);
				putInWareSpare(dropGoodsVO, map.get(dropGoodsVO.getGoodsId()));
			}
			for (int i = equipGoods.size() - 1; i >= 0; i--)
			{
				dropGoodsVO = equipGoods.get(i);
				putGoodsToWrap(p_pk, dropGoodsVO.getGoodsId(), dropGoodsVO
						.getGoodsType(), dropGoodsVO.getGoodsQuality(),
						dropGoodsVO.getDropNum(),GameLogManager.G_NPD_DROP);
				// ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(dropGoodsVO.getGoodsId(), dropGoodsVO
						.getGoodsType(), dropGoodsVO.getDropNum(),
						StatisticsType.DEDAO, StatisticsType.DAGUAI, p_pk);
			}

		}
		else
		{
			return -1;
		}

		// ɾ���������ڵ�����е���Ϣ
		roleInfo.getDropSet().clearDropItem();
		return 1;

	}

	/**
	 * �����߲嵽������
	 * @param dropGoodsVO
	 * @param need_group
	 *            ��һ��Ϊ���ߵ�ռ�ݰ����ռ��С, �ڶ���Ϊ����ʱʣ�������ĵ�������
	 */
	public void putInWareSpare(DropGoodsVO dropGoodsVO, Integer[] need_group)
	{
		logger.info("need_group=" + need_group.toString());

		int need_groups = need_group[0];
		int goodsgourp_goodsnum = need_group[1];

		int p_pk = dropGoodsVO.getPPk();
		int goods_num = dropGoodsVO.getDropNum();

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PropVO prop = PropCache.getPropById(dropGoodsVO.getGoodsId());

		int accumulate_num = prop.getPropAccumulate();

		// �ҵ�û���ص����ĵ�����
		PlayerPropGroupVO propGroup = new PlayerPropGroupVO();
		propGroup.setPgType(prop.getPropPosition());
		propGroup.setPropBonding(prop.getPropBonding());
		propGroup.setPropProtect(prop.getPropProtect());
		propGroup.setPropIsReconfirm(prop.getPropReconfirm());
		propGroup.setPropUseControl(prop.getPropUseControl());

		propGroup.setPPk(p_pk);

		propGroup.setPropId(prop.getPropID());
		propGroup.setPropType(prop.getPropClass());

		// ����µĵ����飬��������accumulate_num
		propGroup.setPropNum(accumulate_num);
		for (int i = 0; i < need_groups; i++)
		{
			propGroupDao.addPropGroup(propGroup);
		}

		propGroup = propGroupDao.getPropGroupByPropID(p_pk, dropGoodsVO
				.getGoodsId());
		if (goodsgourp_goodsnum != 0)
		{
			propGroupDao.updatePropGroupNum(propGroup.getPgPk(),
					goodsgourp_goodsnum);
		}
		if (need_groups == 0 && goodsgourp_goodsnum == 0 && goods_num != 0) // ����Ҫ�ӵ����飬����װ��������
		{
			propGroupDao
					.updatePropGroupNum(propGroup.getPgPk(), accumulate_num);
		}

		// �����ռ����need_groups
		EquipService equipService =new EquipService();
		equipService.addWrapSpare(p_pk, -need_groups);

	}

	/**
	 * �鿴����ĵ�����Ҫ���ٸ�İ����ռ�
	 * 
	 * @param dropGoodsVO
	 * @return
	 */
	public Integer[] propNeedSpare(DropGoodsVO dropGoodsVO)
	{
		Integer[] need_arg = new Integer[2];
		int p_pk = dropGoodsVO.getPPk();
		int goods_id = dropGoodsVO.getGoodsId();
		int goods_num = dropGoodsVO.getDropNum();

		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk);

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();

		PropVO prop = PropCache.getPropById(goods_id);

		int accumulate_num = prop.getPropAccumulate();// �����ص�������

		int current_num = propGroupDao.getPropNumByByPropID(p_pk, goods_id);// ���е�����
		int current_groups_sql = propGroupDao.getPropGroupNumByPropID(p_pk,
				goods_id); // ��ǰ���ݿ��е�����

		int total_num = current_num + goods_num;// ����goods_num�������

		int current_groups = 0;// ���е�����
		if (current_num != 0)
		{
			// ��Ϊ��ҵİ�������Ʒ���Ӳ�һ����������״̬, ���Կ�������ɢ��,����Ҫ�����ݿ���ȡ�������� .
			// current_groups =(current_num-1)/accumulate_num+1;
			current_groups = propGroupDao.getPropGroupNumByPropID(p_pk,
					goods_id);
		}

		int new_groups = (total_num - 1) / accumulate_num + 1;// ����goods_num�����ߺ������

		int need_groups = new_groups - current_groups;// ��Ҫ���ӵĵ�������

		int goodsgourp_goodsnum = total_num % accumulate_num;// ������������ĵ�������

		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();// ʣ��İ����ռ���

		if (wrap_spare == 0)
		{
			if (current_num == 0) // ���û�пո�,�Ұ�����Ҳ�޴���,��϶��Ų���
			{
				logger.debug("�����ռ䲻��");
				need_arg[0] = -1;
				return need_arg;
				// ���Ž�������������ԭ�������������д���Ʒ��ռ�������������ֵ,Ҳ�Ų���
			}
			else
				if (current_num + goods_num > current_groups_sql
						* accumulate_num)
				{

					logger.debug("�����ռ䲻��");
					need_arg[0] = -1;
					return need_arg;
				}
		}
		else
		{

			if (current_num == 0)
			{
				// ���������û�д���,�Ҵ���Ʒ���������ڿ����������������ֵ,Ҳ�Ų���
				if (goods_num > wrap_spare * accumulate_num)
				{
					need_arg[0] = -1;
					return need_arg;
				}
			}
			else
			{
				// ����������Ʒ�������ͼ�����������ԭռ�����Ϳ������֮�͵���������ֵ,Ҳ�Ų���.
				if ((current_num + goods_num) > (current_groups_sql + wrap_spare)
						* accumulate_num)
				{
					need_arg[0] = -1;
					return need_arg;
				}
			}
		}

		need_arg[0] = need_groups;
		need_arg[1] = goodsgourp_goodsnum;

		return need_arg;

	}

	/**
	 * �ѵ��߷ŵ���������鵱ǰ���߰���������Ч��
	 * 
	 * @param p_pk
	 * @param goods_id
	 * @param w_type
	 * @return
	 */
	public int returnpropspare(RoleEntity roleInfo, int p_pk, int goods_id,
			int goods_num)
	{

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();

		PropVO prop = PropCache.getPropById(goods_id);

		int accumulate_num = prop.getPropAccumulate();// �����ص�������

		int current_num = propGroupDao.getPropNumByByPropID(p_pk, goods_id);// ���е�����

		int total_num = current_num + goods_num;// ����goods_num�������

		int current_groups = 0;// ���е�����
		if (current_num != 0)
		{
			// ��Ϊ��ҵİ�������Ʒ���Ӳ�һ����������״̬, ���Կ�������ɢ��,����Ҫ�����ݿ���ȡ�������� .
			// current_groups =(current_num-1)/accumulate_num+1;
			current_groups = propGroupDao.getPropGroupNumByPropID(p_pk,
					goods_id);
		}
		int new_groups = (total_num - 1) / accumulate_num + 1;// ����goods_num�����ߺ������
		int need_groups = new_groups - current_groups;// ��Ҫ���ӵĵ�������,��Ҫ�����ĸ���Ϊ������ʾ���Ӱ�������
		int goodsgourp_goodsnum = total_num % accumulate_num;// ������������ĵ�������
		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();// ʣ��İ����ռ���
		if (need_groups > 0 && wrap_spare < need_groups)
		{
			logger.debug("�����ռ䲻��");
			return -1;
		}

		if (need_groups > 0)// ��Ӳ���ĵ�����ĸ���
		{
			for (int i = 0; i < need_groups; i++)
			{
			}
		}

		if (need_groups < 0)// ��Ҫ�ĵ�������Ϊ������ʾ����������Ҫ�ͷŵĵ�������
		{
			int delete_group_num = -need_groups;
		}
		// ������������е���idΪprop_id�ĵ��ߵ���������

		if (goodsgourp_goodsnum != 0)// ������������ĵ���������Ϊ0ʱ���������е�һ�����Ϊ��������������ĵ���������
		{
		}
		// ������ҵ�ʣ���������
		return need_groups;
	}

	/**
	 * ���ذ��������Ƿ񹻲���
	 */
	public boolean isEnoughWrapSpace(int pPk, int number)
	{

		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");

		// �õ�����ʣ��Ŀռ�
		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();

		if ((wrap_spare - number) < 0)
		{
			return false;
		}
		return true;
	}

	/** װ������ߵ����� */
	public String getEquipPropInfoWml(int pPk, int prop_id, String w_type,
			String pg_pk, String goods_type, String isReconfirm,
			String wupinlan, HttpServletRequest request,
			HttpServletResponse response)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");

		TitleService titleService = new TitleService();
		PicService picService = new PicService();
		String propPic = picService.getPropPicStr(roleInfo, prop_id);
		StringBuffer resultWml = new StringBuffer();
		PropVO prop = getPropInfo(prop_id);
		int pgpk = Integer.parseInt(pg_pk);
		SpecialPropService sps = new SpecialPropService();
		SpecialPropDAO dao = new SpecialPropDAO();
		SpecialPropVO vo = dao.getSpecialProp(pPk, pg_pk);
		// String a = null;
		// int x1 = 0;
		// int x2 = 0;
		// int x3 = 0;
		// int x4 = 0;
		// int x5 = 0;
		// int x6 = 0;
		// int time = 0;
		// if(vo == null){
		// a = prop.getPropOperate1();
		// String[] x = a.split(",");
		// x1 = Integer.valueOf(x[0]);
		// x2 = Integer.valueOf(x[1]);
		// x3 = Integer.valueOf(x[2]);
		// x4 = Integer.valueOf(x[3]);
		// x5 = Integer.valueOf(x[4]);
		// x6 = Integer.valueOf(x[5]);
		// time = Integer.parseInt(prop.getPropOperate2());
		// }else{
		// a = vo.getPropoperate1();
		// String[] x = a.split(",");
		// x1 = Integer.valueOf(x[0]);
		// x2 = Integer.valueOf(x[1]);
		// x3 = Integer.valueOf(x[2]);
		// x4 = Integer.valueOf(x[3]);
		// x5 = Integer.valueOf(x[4]);
		// x6 = Integer.valueOf(x[5]);
		// time = sps.getUserTimeByPgpk(pPk,pg_pk);
		// }
		/*
		 * ʣ��HP:1000/10000 ʣ��ʱ��:240����/3600���� ʹ�� ���� ����
		 */
		if (prop.getPropClass() == PropType.EQUIPPROP
				|| prop.getPropClass() == PropType.JIEHUN_JIEZHI)
		{
			resultWml.append(prop.getPropName());
			resultWml.append("<br/>");
			resultWml.append(propPic);
			resultWml.append(prop.getPropDisplay());
			resultWml.append("<br/>");
			resultWml.append("------------------------");
			resultWml.append("<br/>");
			resultWml.append("�����۸�:"
					+ MoneyUtil.changeCopperToStr(prop.getPropSell()));
			resultWml.append("<br/>");
			resultWml.append("ʹ�ü���:" + getPropDisplay(prop.getPropReLevel())
					+ "��");
			resultWml.append("<br/>");
			resultWml.append("�Ա�Ҫ��:"
					+ ExchangeUtil.exchangeToSex(prop.getPropSex()));
			resultWml.append("<br/>");
			resultWml.append("ְҵҪ��:"
					+ titleService.getTitleNamesByTitleIDs(prop.getPropJob()));
			resultWml.append("<br/>");
			if (prop.getPropClass() == PropType.JIEHUN_JIEZHI)
			{
				resultWml.append("��������:"
						+ new FriendService().findLove_dear(pPk));
				resultWml.append("<br/>");
			}

			/**
			 * if(x1 != 0) { resultWml.append("ʣ��HP:"+ x2 +"/"+ x3);
			 * resultWml.append("<br/>"); } if(x4 != 0) {
			 * resultWml.append("ʣ��MP:"+ x5 +"/"+ x6); resultWml.append("<br/>"); }
			 */
			if (prop.getPropClass() == PropType.EQUIPPROP)
			{
				int time = 0;
				if (vo == null)
				{
					time = Integer.parseInt(prop.getPropOperate2());
				}
				else
				{
					time = sps.getUserTimeByPgpk(pPk, pg_pk);
				}
				resultWml.append("ʣ��ʱ��:" + time + "����/"
						+ prop.getPropOperate2() + "����");
				resultWml.append("<br/>");
			}
		}
		if (prop.getPropClass() == PropType.EQUIPPROP)
		{
			if (vo == null)
			{
				resultWml.append("<anchor> ");
				// resultWml.append("<go method=\"post\" sendreferer=\"true\"
				// href=\"/stateaction.do\"> ");
				resultWml.append("<go method=\"post\" href=\""
						+ response.encodeURL(GameConfig.getContextPath()
								+ "/stateaction.do") + "\">");
				resultWml.append("<postfield name=\"cmd\" value=\"n22\" /> ");
				resultWml.append("<postfield name=\"pg_pk\" value=\"" + pgpk
						+ "\" /> ");
				resultWml.append("<postfield name=\"w_type\" value=\"" + w_type
						+ "\" /> ");
				resultWml.append("<postfield name=\"wupinlan\" value=\""
						+ wupinlan + "\" /> ");
				resultWml
						.append("<postfield name=\"goods_type\" value=\"2\" /> ");
				resultWml.append("</go>");
				resultWml.append("ʹ�� ");
				resultWml.append("</anchor> |");

				resultWml.append("<anchor> ");
				// resultWml.append("<go method=\"post\" sendreferer=\"true\"
				// href=\"/wrap.do\"> ");
				resultWml.append("<go method=\"post\" href=\""
						+ response.encodeURL(GameConfig.getContextPath()
								+ "/wrap.do") + "\">");
				resultWml.append("<postfield name=\"cmd\" value=\"n7\" /> ");
				resultWml.append("<postfield name=\"w_type\" value=\"" + w_type
						+ "\" /> ");
				resultWml.append("<postfield name=\"pg_pk\" value=\"" + pg_pk
						+ "\" /> ");
				resultWml.append("<postfield name=\"goods_id\" value=\""
						+ prop_id + "\" /> ");
				resultWml.append("<postfield name=\"goods_type\" value=\""
						+ goods_type + "\" /> ");
				resultWml.append("<postfield name=\"isReconfirm\" value=\""
						+ isReconfirm + "\" /> ");
				resultWml.append("</go>");
				resultWml.append("����");
				resultWml.append("</anchor> ");
				resultWml.append("<br/>");
			}
			else
			{
				if (vo.getProptime() == 0)
				{
					resultWml.append("<anchor> ");
					// resultWml.append("<go method=\"post\"
					// sendreferer=\"true\" href=\"/stateaction.do\"> ");
					resultWml.append("<go method=\"post\" href=\""
							+ response.encodeURL(GameConfig.getContextPath()
									+ "/stateaction.do") + "\">");
					resultWml
							.append("<postfield name=\"cmd\" value=\"n22\" /> ");
					resultWml.append("<postfield name=\"pg_pk\" value=\""
							+ pgpk + "\" /> ");
					resultWml.append("<postfield name=\"w_type\" value=\""
							+ w_type + "\" /> ");
					resultWml.append("<postfield name=\"wupinlan\" value=\""
							+ wupinlan + "\" /> ");
					resultWml
							.append("<postfield name=\"goods_type\" value=\"2\" /> ");
					resultWml.append("</go>");
					resultWml.append("ʹ�� ");
					resultWml.append("</anchor> |");

					resultWml.append("<anchor> ");
					// resultWml.append("<go method=\"post\"
					// sendreferer=\"true\" href=\"/wrap.do\"> ");
					resultWml.append("<go method=\"post\" href=\""
							+ response.encodeURL(GameConfig.getContextPath()
									+ "/wrap.do") + "\">");
					resultWml
							.append("<postfield name=\"cmd\" value=\"n7\" /> ");
					resultWml.append("<postfield name=\"w_type\" value=\""
							+ w_type + "\" /> ");
					resultWml.append("<postfield name=\"pg_pk\" value=\""
							+ pg_pk + "\" /> ");
					resultWml.append("<postfield name=\"goods_id\" value=\""
							+ prop_id + "\" /> ");
					resultWml.append("<postfield name=\"goods_type\" value=\""
							+ goods_type + "\" /> ");
					resultWml.append("<postfield name=\"isReconfirm\" value=\""
							+ isReconfirm + "\" /> ");
					resultWml.append("</go>");
					resultWml.append("����");
					resultWml.append("</anchor> ");
					resultWml.append("<br/>");
				}
				else
				{
					resultWml.append("<anchor> ");
					// resultWml.append("<go method=\"post\"
					// sendreferer=\"true\" href=\"/stateaction.do\"> ");
					resultWml.append("<go method=\"post\" href=\""
							+ response.encodeURL(GameConfig.getContextPath()
									+ "/stateaction.do") + "\">");
					resultWml
							.append("<postfield name=\"cmd\" value=\"n21\" /> ");
					resultWml.append("<postfield name=\"pg_pk\" value=\""
							+ pgpk + "\" /> ");
					resultWml.append("<postfield name=\"w_type\" value=\""
							+ w_type + "\" /> ");
					resultWml
							.append("<postfield name=\"goods_type\" value=\"2\" /> ");
					resultWml
							.append("<postfield name=\"wupinlan\" value=\"1\" /> ");
					resultWml.append("</go>");
					resultWml.append("���� ");
					resultWml.append("</anchor> ");
				}
			}
		}
		if (prop.getPropClass() == PropType.JIEHUN_JIEZHI)
		{
			if (vo == null)
			{
				resultWml.append("<anchor> ");
				// resultWml.append("<go method=\"post\" sendreferer=\"true\"//
				// href=\"/stateaction.do\"> ");
				resultWml.append("<go method=\"post\" href=\""
						+ response.encodeURL(GameConfig.getContextPath()
								+ "/stateaction.do") + "\">");
				resultWml.append("<postfield name=\"cmd\" value=\"n18\" /> ");
				resultWml.append("<postfield name=\"pg_pk\" value=\"" + pgpk
						+ "\" /> ");
				resultWml.append("<postfield name=\"w_type\" value=\"" + w_type
						+ "\" /> ");
				resultWml.append("<postfield name=\"wupinlan\" value=\""
						+ wupinlan + "\" /> ");
				resultWml
						.append("<postfield name=\"goods_type\" value=\"2\" /> ");
				resultWml.append("</go>");
				resultWml.append("ʹ�� ");
				resultWml.append("</anchor> |");

				resultWml.append("<anchor> ");
				// resultWml.append("<go method=\"post\" sendreferer=\"true\"
				// href=\"/wrap.do\"> ");
				resultWml.append("<go method=\"post\" href=\""
						+ response.encodeURL(GameConfig.getContextPath()
								+ "/wrap.do") + "\">");
				resultWml.append("<postfield name=\"cmd\" value=\"n7\" /> ");
				resultWml.append("<postfield name=\"w_type\" value=\"" + w_type
						+ "\" /> ");
				resultWml.append("<postfield name=\"pg_pk\" value=\"" + pg_pk
						+ "\" /> ");
				resultWml.append("<postfield name=\"goods_id\" value=\""
						+ prop_id + "\" /> ");
				resultWml.append("<postfield name=\"goods_type\" value=\""
						+ goods_type + "\" /> ");
				resultWml.append("<postfield name=\"isReconfirm\" value=\""
						+ isReconfirm + "\" /> ");
				resultWml.append("</go>");
				resultWml.append("����");
				resultWml.append("</anchor> ");
				resultWml.append("<br/>");
			}
			else
			{
				if (vo.getProptime() == 0)
				{
					resultWml.append("<anchor> ");
					// resultWml.append("<go method=\"post\"
					// sendreferer=\"true\"href=\"/stateaction.do\"> ");
					resultWml.append("<go method=\"post\" href=\""
							+ response.encodeURL(GameConfig.getContextPath()
									+ "/stateaction.do") + "\">");
					resultWml
							.append("<postfield name=\"cmd\" value=\"n18\" /> ");
					resultWml.append("<postfield name=\"pg_pk\" value=\""
							+ pgpk + "\" /> ");
					resultWml.append("<postfield name=\"w_type\" value=\""
							+ w_type + "\" /> ");
					resultWml.append("<postfield name=\"wupinlan\" value=\""
							+ wupinlan + "\" /> ");
					resultWml
							.append("<postfield name=\"goods_type\" value=\"2\" /> ");
					resultWml.append("</go>");
					resultWml.append("ʹ��");
					resultWml.append("</anchor> ");

					resultWml.append("<anchor> ");
					// resultWml.append("<go method=\"post\"
					// sendreferer=\"true\"href=\"/wrap.do\"> ");
					resultWml.append("<go method=\"post\" href=\""
							+ response.encodeURL(GameConfig.getContextPath()
									+ "/wrap.do") + "\">");
					resultWml
							.append("<postfield name=\"cmd\" value=\"n7\" /> ");
					resultWml.append("<postfield name=\"w_type\" value=\""
							+ w_type + "\" /> ");
					resultWml.append("<postfield name=\"pg_pk\" value=\""
							+ pg_pk + "\" /> ");
					resultWml.append("<postfield name=\"goods_id\" value=\""
							+ prop_id + "\" /> ");
					resultWml.append("<postfield name=\"goods_type\" value=\""
							+ goods_type + "\" /> ");
					resultWml.append("<postfield name=\"isReconfirm\" value=\""
							+ isReconfirm + "\" /> ");
					resultWml.append("</go>");
					resultWml.append("����");
					resultWml.append("</anchor> ");
					resultWml.append("<br/>");
				}
				else
				{
					resultWml.append("<anchor> ");
					// resultWml.append("<go method=\"post\"
					// sendreferer=\"true\"href=\"/stateaction.do\"> ");
					resultWml.append("<go method=\"post\" href=\""
							+ response.encodeURL(GameConfig.getContextPath()
									+ "/stateaction.do") + "\">");
					resultWml
							.append("<postfield name=\"cmd\" value=\"n21\" /> ");
					resultWml.append("<postfield name=\"pg_pk\" value=\""
							+ pgpk + "\" /> ");
					resultWml.append("<postfield name=\"w_type\" value=\""
							+ w_type + "\" /> ");
					resultWml
							.append("<postfield name=\"goods_type\" value=\"2\" /> ");
					resultWml
							.append("<postfield name=\"wupinlan\" value=\"1\" /> ");
					resultWml.append("</go>");
					resultWml.append("����");
					resultWml.append("</anchor> ");
				}
			}
		}
		/**
		 * resultWml.append("<anchor> "); resultWml.append("<go
		 * method=\"post\" sendreferer=\"true\" href=\"/wrap.do\"> ");
		 * resultWml.append("<postfield name=\"cmd\" value=\"n16\" /> ");
		 * resultWml.append("<postfield name=\"pg_pk1\" value=\"" + pg_pk + "\" />
		 * "); resultWml.append("<postfield name=\"prop_id\" value=\"" +
		 * prop_id + "\" /> "); resultWml.append("<postfield name=\"p_pk\"
		 * value=\"" + pPk + "\" /> "); resultWml.append("<postfield
		 * name=\"wupinlan\" value=\"" + wupinlan + "\" /> ");
		 * resultWml.append("</go>"); resultWml.append("����");
		 * resultWml.append("</anchor> ");
		 */
		resultWml.append("<br/>");
		return resultWml.toString();
	}

	/** �õ����ҩƷ���б� *//*
	public List getPropByHP(int p_pk)
	{
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		List list = dao.getHpMp(p_pk, 1);
		return list;
	}

	public List getPropByMP(int p_pk)
	{
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		List list = dao.getHpMp(p_pk, 2);
		return list;
	}
*/
	/**
	 * �õ���ͬ���͵ĵ���
	 * @param p_pk
	 * @param prop_type
	 * @return
	 *//*
	public List<PlayerPropGroupVO> getAllPropType(int p_pk, int prop_type)
	{
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		return dao.getLuckyProp(p_pk, prop_type);
	}*/


	/**
	 * �ҵ��������ٵĵ�����
	 * @return
	 */
	public PlayerPropGroupVO getPropGroupByPropID(int p_pk, int prop_id)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getPropGroupByPropID(p_pk, prop_id);
	}

	/**
	 * �����ؾ���ͼ��ӵ����
	 */
	public int updateMiJingOwner(int last_owner, int new_owner)
	{

		int result = new PlayerPropGroupDao().updateMiJingOwner(last_owner,
				new_owner);
		if (result > 0)
		{
			EquipService equipService = new EquipService();
			equipService.addWrapSpare(last_owner, result);
			equipService.addWrapSpare(new_owner, -result);
		}
		return result;
	}

	/**
	 * �鿴�����Ƿ�ӵ���ؾ���ͼ
	 */
	public boolean haveMiJing(int ppk)
	{
		return new PlayerPropGroupDao().haveMiJing(ppk);
	}

	/**
	 * ɾ�����ϵ��ؾ���ͼ
	 */
	public void removeMiJing(int ppk)
	{

		int result = new PlayerPropGroupDao().removeMiJing(ppk);
		if (result > 0)
		{
			EquipService equipService = new EquipService();
			equipService.addWrapSpare(ppk, result);
		}
	}
}
