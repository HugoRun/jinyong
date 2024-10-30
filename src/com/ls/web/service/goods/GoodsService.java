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
 * 功能:物品放入包裹，从包裹里丢弃，卖买物品，显示物品详情
 * 
 * @author 刘帅 9:49:09 AM
 */
public class GoodsService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * 得到玩家包裹里的prop_type类型的不重复的道具，且战斗可以使用的道具
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
	 * 得到玩家包裹里的pw_type类型的所有道具
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
	 * 分页:得到玩家包裹里的pw_type类型的所有道具
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPagePropList(int p_pk, int pg_type, int page_no)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getPagePropsByPpk(p_pk, pg_type, page_no);
	}
	
	/**
	 * 分页:得到玩家包裹里的可以交易的（非破损的）装备
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPageSaleEquipOnWrap(int p_pk,int page_no)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		return equipDao.getPageSaleEquipOnWrap(p_pk, page_no,Equip.ON_WRAP);
	}
	
	/**
	 * 分页:得到玩家包裹里的装备
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPageEquipOnWrap(int p_pk,int page_no)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		return equipDao.getPageByPosition(p_pk, page_no,Equip.ON_WRAP);
	}
	
	/**
	 * 分页:得到玩家包裹里的指定类型的装备
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPageByTypeOnWrap(int p_pk,int equipType,int page_no)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		return equipDao.getPageByTypeOnWrap(p_pk, equipType,page_no);
	}
	/**
	 * 分页得到玩家的某类装备，武器 防具，法宝
	 */
	public QueryPage getPageEquipOnWrap(int p_pk,int equip_type,int page_no)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		return equipDao.getPageEquipOnWrap(p_pk, equip_type, page_no);
	}

	/**
	 * 分页:得到玩家包裹里的装备可以镶嵌的宝石列表
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
	 * 分页:得到玩家包裹里的pw_type类型的所有道具
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPageListByPropType(int p_pk, int prop_type, int page_no)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getPageListByPropType(p_pk, prop_type, page_no);
	}
	
	/**
	 * 分页:得到玩家包裹里的pw_type类型的所有道具
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPagePropListpet(int p_pk, int pg_type, int page_no)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getPagePropsByPpkpet(p_pk, pg_type, page_no);
	}

	/**
	 * 丢弃物品道具,以一组为单位，丢弃一组里的道具
	 * 
	 * @return
	 */
	public int removeProps(PlayerPropGroupVO propGroup, int remove_num)
	{
		if (propGroup == null)
		{
			logger.debug("propGroup为空");
			return -1;
		}

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		if (propGroup.getPropNum() == remove_num) // 移除的数量等于道具组数量
		{
			propGroupDao.deletePropGroup(propGroup.getPgPk());
			EquipService equipService = new EquipService();
			equipService.addWrapSpare(propGroup.getPPk(), 1); // 增加玩家包裹剩余空间数量
		}
		else
			if (propGroup.getPropNum() > remove_num)// //移除的数量大于道具组数量
			{
				propGroupDao.updatePropGroupNum(propGroup.getPgPk(), propGroup
						.getPropNum()
						- remove_num);
				// 执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(propGroup.getPropId(), StatisticsType.PROP,
						remove_num, StatisticsType.USED,
						StatisticsType.SHIYONG, 0);

			}
		if (propGroup.getPropType() == PropType.BOX_CURE
				|| propGroup.getPropType() == PropType.FIX_ARM_PROP)
		{ // 如果该道具是捆装药品,则必须将其也从特殊道具表中也删除
			propGroupDao.deletePropBoxCure(propGroup.getPgPk(), propGroup
					.getPPk());
		}
		return remove_num;
	}

	/** 装备类道具的丢弃 */
	public void removeSpecialProp(int p_pk, int pg_pk, int prop_id)
	{
		PropVO prop = PropCache.getPropById(prop_id);
		
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		dao.deletePropGroup(pg_pk);
		// 给玩家释放包裹空间
		EquipService equipService = new EquipService();
		equipService.addWrapSpare(p_pk, 1);

		GameLogManager.getInstance().propLog(p_pk, prop, 1, GameLogManager.R_DROP);
		
		/*// 执行统计
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(prop_id, 4, 1, "used", "diuqi", 0);*/
	}

	/**
	 * 丢弃物品道具,是以道具的所有的数量来处理道具的丢弃
	 * 
	 * @return   返回移除的数量，如果不足则返回-1
	 */
	public int removeProps(int p_pk, int prop_id, int remove_num,int remove_type)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PropVO prop = PropCache.getPropById(prop_id);

		int accumulate_num = prop.getPropAccumulate();// 可以重叠的数量

		int current_num = propGroupDao.getPropNumByByPropID(p_pk, prop_id);// 现有的数量
		int total_num = current_num - remove_num;// 移除remove_num后的总数

		if( total_num<0 )
		{
			return -1;
		}
		
		// int current_groups
		// =(current_num+accumulate_num-1)/accumulate_num;;//现有的组数
		// 因为玩家的包裹该物品格子不一定都是理想状态, 所以可能有零散的,故需要从数据库中取现有组数 .
		int current_groups = propGroupDao
				.getPropGroupNumByPropID(p_pk, prop_id);// 现有的组数

		int new_groups = (total_num + accumulate_num - 1) / accumulate_num;// 减少goods_num个道具后的组数

		int release_groups = current_groups - new_groups;// 可以释放的道具组数

		int goodsgourp_goodsnum = total_num % accumulate_num;// 不完整道具组的道具数量

		logger.info("current_groups=" + current_groups + " ,new_groups="
				+ new_groups + " ,total_num=" + total_num);
		if (release_groups > 0)
		{
			// 释放玩家包裹need_groups空间
			propGroupDao.deletePropGroup(p_pk, prop_id, release_groups);
		}

		// 填满玩家所有该道具的道具组
		propGroupDao.updatePropGroupUpNum(p_pk, prop_id, accumulate_num);

		if (total_num > 0 && goodsgourp_goodsnum != 0)// 如果还有剩余道具,且剩余道具不为可叠加的倍数时。
		{
			propGroupDao.updatePropGroupNumByPropID(p_pk, prop_id,
					goodsgourp_goodsnum);
		}

		// 释放玩家包裹剩余空间数量
		EquipService equipService = new EquipService();
		equipService.addWrapSpare(p_pk, release_groups);
		
		GameLogManager.getInstance().propLog(p_pk, prop, remove_num, remove_type);
		
		return remove_num;
	}

	/*// 删除所有指定id的道具
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

	/** 统计道具的消耗 *//*
	public void removePropsStatistics(int p_pk, int prop_id, int remove_num,
			String type)
	{
		removeProps(p_pk, prop_id, remove_num);
		// 执行统计
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(prop_id, StatisticsType.PROP, remove_num,StatisticsType.USED, type, 0);
	}*/

	
	/**
	 * 得到物品名字
	 * @param goods_id
	 * @param goods_type
	 * @return
	 */
	public String getGoodsName(int goods_id, int goods_type)
	{

		String goods_name = ""; // 装备名称

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
	 * 得到物品控制信息
	 */
	public GoodsControlVO getGoodsControl(int goods_id, int goods_type)
	{

		if (goods_type == GoodsType.PROP)// 道具
		{
			PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
			return propGroupDao.getPropControl(goods_id);
		}
		else
		// 装备
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
	 * 通过主键得到道具信息
	 */
	public PlayerPropGroupVO getPropByPgPk(int pg_pk )
	{
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		return playerPropGroupDao.getByPgPk(pg_pk);
	}
	
	/**
	 * 得到玩家指定类型的道具列表
	 */
	public  List<PlayerPropGroupVO> getListByPropType( int p_pk,int prop_type)
	{
		PlayerPropGroupDao PlayerPropGroupDao = new PlayerPropGroupDao();
		return PlayerPropGroupDao.getListByPropType(p_pk, prop_type);
	}
	
	/**
	 * 得到包裹里镶嵌用的宝石列表
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
	 * 装备id,装备类型（属于头盔，鞋子，项链，戒指之类的）,属于哪个装备表（arm,accouter,Ojewely） 1,2,1
	 * 
	 * 药品-----1 书卷-----2 装备-----3 任务-----4 其他-----5
	 */

	/**
	 * 玩家捡到物品,result=-1时表示包裹格数不够
	 * 
	 * @param p_pk
	 * @param goods_id
	 * @param goods_type
	 * @param sendInfo
	 *            如果为一，就根据掉落物品是否是重要物品发送系统消息，如果为零，则不发。
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

		// 得到装备的PWPK替换玩家的GOODINFO中的显示信息

		if (result != -1)
		{
			// 成功捡起物品，删除物品掉落临时表的对应的数据
			role_info.getDropSet().removeDropItem(drop_pk);
			// 执行统计
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
	 * 把物品放入包裹
	 * 
	 * @param p_pk
	 * @param goods_id
	 *            放入物品的id
	 * @param goods_type
	 *            放入物品的类型（EQUIP，PROP）
	 * @param goods_num
	 *            放入物品的数量
	 * @return 返回-1表示包裹空间不够，不能放入
	 */
	public int putGoodsToWrap(int p_pk, int goods_id, int goods_type,int goods_num)
	{
		return putGoodsToWrap(p_pk, goods_id, goods_type, 0, goods_num,GameLogManager.NO_LOG);
	}

	/**
	 * 把物品放入包裹
	 * 
	 * @param p_pk
	 * @param goods_id 放入物品的id
	 * @param goods_type 放入物品的类型（ACCOUTE，ARM，JEWELRY，PROP）
	 * @param goods_num   放入物品的数量
	 * @param goods_quality  放入物品的品质
	 * @return 返回-1表示包裹空间不够，不能放入
	 */
	public int putGoodsToWrap(int p_pk, int goods_id, int goods_type,int goods_quality, int goods_num,int gain_type)
	{
		int result = -1;
		// 拾取道具
		if (goods_type == GoodsType.PROP)
		{
			result = putPropToWrap(p_pk, goods_id, goods_num,gain_type);
		}
		else// 拾取装备
		{
			result = putEquipToWrap(p_pk, goods_id, goods_quality,goods_num,gain_type);
		}
		return result;
	}

	/**
	 * 把道具放到包裹里，重组当前道具包裹个数的效果
	 * @param p_pk
	 * @param goods_id			道具id
	 * @param goods_num			道具数量
	 * @return
	 */
	public String putPropToWrap(String roleName, String propName, String propNum)
	{
		if( StringUtil.isNumber(propNum)==false )
		{
			return "数量错误";
		}
		
		int p_pk = new PartInfoDao().getByName(roleName);
		if( p_pk==-1 )
		{
			return "无该角色";
		}
		int prop_id = new PropDao().getPropIdByName(propName);
		if( prop_id==-1 )
		{
			return "无该道具";
		}
		
		int hint = this.putPropToWrap(p_pk, prop_id, Integer.parseInt(propNum),GameLogManager.G_SYSTEM);
		if( hint==-1 )
		{
			return "包裹空间不足";
		}
		return "放置成功";
	}
	
	/**
	 * 把道具放到包裹里，重组当前道具包裹个数的效果
	 * @param p_pk
	 * @param goods_id			道具id
	 * @param goods_num			道具数量
	 * @param gain_type			获得道具的操作类型
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

		int accumulate_num = prop.getPropAccumulate();// 可以重叠的数量

		int current_num = propGroupDao.getPropNumByByPropID(p_pk, goods_id);// 现有的数量

		int total_num = current_num + goods_num;// 增加goods_num后的总数

		int current_groups = 0;// 现有的组数
		if (current_num != 0)
		{
			// 因为玩家的包裹该物品格子不一定都是理想状态, 所以可能有零散的,故需要从数据库中取现有组数 .
			current_groups = propGroupDao.getPropGroupNumByPropID(p_pk,
					goods_id);
		}

		int new_groups = (total_num - 1) / accumulate_num + 1;// 增加goods_num个道具后的组数

		int need_groups = new_groups - current_groups;// 需要增加的道具组数,需要包裹的个数为负数表示增加包裹个数

		int goodsgourp_goodsnum = total_num % accumulate_num;// 不完整道具组的道具数量

		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();// 剩余的包裹空间数

		if (need_groups > 0 && wrap_spare < need_groups)
		{
			//包裹空间不够
			return -1;
		}

		if (need_groups > 0)// 添加不足的道具组的个数
		{
			// 找到没有重叠慢的道具组
			PlayerPropGroupVO propGroup = new PlayerPropGroupVO();
			propGroup.setPgType(prop.getPropPosition());
			propGroup.setPropBonding(prop.getPropBonding());
			propGroup.setPropProtect(prop.getPropProtect());
			propGroup.setPropIsReconfirm(prop.getPropReconfirm());
			propGroup.setPropUseControl(prop.getPropUseControl());

			propGroup.setPPk(p_pk);

			propGroup.setPropId(prop.getPropID());
			propGroup.setPropType(prop.getPropClass());

			// 添加新的道具组，数量都是accumulate_num
			propGroup.setPropNum(accumulate_num);
			for (int i = 0; i < need_groups; i++)
			{
				propGroupDao.addPropGroup(propGroup);
			}
		}

		if (need_groups < 0)// 需要的道具组数为负数表示，重组完需要释放的道具组数
		{
			int delete_group_num = -need_groups;
			propGroupDao.deletePropGroup(p_pk, prop_id, delete_group_num);
		}

		// 把玩家身上所有道具id为prop_id的道具的数量填满
		propGroupDao.updatePropGroupUpNum(p_pk, prop_id, accumulate_num);

		if (goodsgourp_goodsnum != 0)// 不完整道具组的道具数量不为0时，更新其中的一组道具为（不完整道具组的道具数量）
		{
			propGroupDao.updateOneGroupPropNum(p_pk, prop_id,
					goodsgourp_goodsnum);
		}

		// 更新玩家的剩余包裹个数
		equipService.addWrapSpare(p_pk, -need_groups);

		// 判断是否有这个道具
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		String key_prop_id = GameConfig
				.getPropertiesObject("attainprop_mallprop");
		int pg_pk = playerPropGroupDao.getPgpkByProp(p_pk, Integer
				.parseInt(key_prop_id));
		if (pg_pk == -1)
		{
			int digit = GameConfig.getAttainProp(prop_id, "attain_prop_id");// 返回玩家等级是否在系统设定之内
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
	 * 把道具放到包裹里
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

		int accumulate_num = prop.getPropAccumulate();// 可以重叠的数量

		int current_num = propGroupDao.getPropNumByByPropID(p_pk, goods_id);// 现有的数量
		int current_groups_sql = propGroupDao.getPropGroupNumByPropID(p_pk,
				goods_id); // 当前数据库中的组数

		int total_num = current_num + goods_num;// 增加goods_num后的总数

		int current_groups = 0;// 现有的组数
		if (current_num != 0)
		{
			// 因为玩家的包裹该物品格子不一定都是理想状态, 所以可能有零散的,故需要从数据库中取现有组数 .
			// current_groups =(current_num-1)/accumulate_num+1;
			current_groups = propGroupDao.getPropGroupNumByPropID(p_pk,
					goods_id);
		}

		int new_groups = (total_num - 1) / accumulate_num + 1;// 增加goods_num个道具后的组数

		int need_groups = new_groups - current_groups;// 需要增加的道具组数

		int goodsgourp_goodsnum = total_num % accumulate_num;// 不完整道具组的道具数量

		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();// 剩余的包裹空间数

		if (wrap_spare == 0)
		{
			if (current_num == 0) // 如果没有空格,且包裹中也无此物,则肯定放不下
			{
				logger.debug("包裹空间不够");
				return -1;
				// 若放进包裹中数量加原有数超过包裹中此物品所占格数的最大容纳值,也放不下
			}
			else
				if (current_num + goods_num > current_groups_sql
						* accumulate_num)
				{

					logger.debug("包裹空间不够");
					return -1;
				}
		}
		else
		{

			if (current_num == 0)
			{
				// 如果包裹中没有此物,且此物品的数量大于空余格数的最大可容纳值,也放不下
				if (goods_num > wrap_spare * accumulate_num)
				{
					return -1;
				}
			}
			else
			{
				// 若包裹中物品的数量和加入数量超过原占格数和空余格数之和的最大可容纳值,也放不下.
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
	 * 把装备放入包裹,返回1代表成功
	 * @param roleName		角色名
	 * @param equipName		装备名
	 * @param equipNum		装备数量
	 * @param qulity		装备品质
	 * @return
	 */
	public String putEquipToWrap(String roleName, String equipName,String equipNum,String qulity)
	{
		if( StringUtil.isNumber(equipNum)==false || Integer.parseInt(equipNum)<0 )
		{
			return "数量错误";
		}
		if( StringUtil.isNumber(qulity)==false )
		{
			return "品质错误";
		}
		
		int p_pk = new PartInfoDao().getByName(roleName);
		if( p_pk==-1 )
		{
			return "无该角色";
		}
		int equip_id = new GameEquipDao().getIdByName(equipName);
		if( equip_id==-1 )
		{
			return "无该装备";
		}
		
		int result = this.putEquipToWrap(p_pk, equip_id, Integer.parseInt(qulity), Integer.parseInt(equipNum),GameLogManager.G_SYSTEM);
		
		switch( result )
		{
			case -1:return "包裹空间不足";
			case -2:return "品质错误";
		}
		return "放置成功";
	}
	
	/**
	 * 把装备直接穿到玩家身上
	 * @param roleInfo
	 * @param equip_id
	 * @param equip_quality
	 * @return       返回null表示成功，否则表示失败原因
	 */
	public String giveEquipOnBody(RoleEntity roleInfo, int equip_id,int equip_quality)
	{
		EquipService equipService = new EquipService();
		// 生成装备放入包裹
		PlayerEquipVO equip = equipService.createEquipByQuality(roleInfo.getPPk(), equip_id, equip_quality,GameLogManager.G_SYSTEM);
		if( equip==null )
		{
			DataErrorLog.debugData("GoodsService.giveEquipOnBody:equip_id="+equip_id+";equip_quality="+equip_quality);
			return "装备生成失败";
		}
		return equipService.puton(roleInfo, equip);
	}
	
	
	/**
	 * 把装备放入包裹,返回1代表成功
	 * @param p_pk
	 * @param equip_id				装备id
	 * @param goods_num
	 * @param gain_type				获得装备的操作类型
	 * @return              表示放入包裹的数量，返回-1代表失败
	 */
	public int putEquipToWrap(int p_pk, int equip_id, int goods_num)
	{
		return this.putEquipToWrap(p_pk, equip_id, 0, goods_num,GameLogManager.NO_LOG);
	}
	/**
	 * 把装备放入包裹,返回1代表成功
	 * @param p_pk
	 * @param equip_id				装备id
	 * @param equip_quality			装备品质
	 * @param goods_num
	 * @param gain_type				获得装备的操作类型
	 * @return              表示放入包裹的数量，返回-1代表失败,-2表示非法装备品质
	 */
	public int putEquipToWrap(int p_pk, int equip_id,int equip_quality, int goods_num,int gain_type)
	{
		int result = 0;

		if( equip_quality>Equip.Q_ORANGE || equip_quality < Equip.Q_PUTONG )
		{
			//非法品质
			return -2;
		}
		
		EquipService equipService = new EquipService();
		
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk);
	
		// 得到包裹剩余的空间
		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();
		PlayerEquipVO equip = null;

		if (wrap_spare < goods_num)// 判断包裹有没有剩余空间
		{
			//包裹空间不足,物品类型为
			return -1;
		}

		for (int i = 0; i < goods_num; i++)
		{
			// 生成装备放入包裹
			equip = equipService.createEquipByQuality(p_pk, equip_id, equip_quality,gain_type);
			if( equip!=null )
			{
				result++;
			}
		}
		// 装备放入包裹更新剩余格数
		equipService.addWrapSpare(p_pk, -goods_num);

		return result;

	}

	/**
	 * 丢弃装备
	 * @param p_pk     
	 * @param equip_id      装备id
	 * @param equip_type
	 */
	public void removeEquipByEquipID(int p_pk, int equip_id)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		equipDao.deleteByEquip(p_pk, equip_id);

		// 增加玩家包裹剩余空间数量
		EquipService equipService = new EquipService();
		equipService.addWrapSpare(p_pk, 1);
	}

	/**
	 * 移除装备
	 * @param p_pk
	 * @param pw_pk			装备id	
	 * @param remove_type	移除操作类型
	 */
	public void removeEquipById(int p_pk, int pw_pk,int remove_type)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		equipDao.deleteByID(p_pk, pw_pk);

		// 增加玩家包裹剩余空间数量
		EquipService equipService = new EquipService();
		equipService.addWrapSpare(p_pk, 1);
	}

	/** 统计玩家装备 *//*
	public void removeEquipStatistics(int p_pk, int pw_pk, String type)
	{
		PlayerEquipDao dao = new PlayerEquipDao();
		PlayerEquipVO vo = dao.getByID(pw_pk);
		// 执行统计
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(vo.getEquipId(),GoodsType.EQUIP, 1, StatisticsType.USED,type, 0);

		removeEquipById(p_pk, pw_pk);
	}
*/
	/**
	 * 卖装备
	 * @param p_pk
	 * @param pw_pk 装备id
	 * @param price  价钱
	 */
	public void saleEquip(RoleEntity roleInfo, int pw_pk, int price)
	{
		// 删掉包裹里的装备
		removeEquipById(roleInfo.getPPk(), pw_pk, GameLogManager.R_SHOP);

		// 给用户增加钱数
		roleInfo.getBasicInfo().addCopper(price);
		
		// 监控
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getPPk(), roleInfo.getName(), roleInfo.getBasicInfo().getCopper()+ "", price + "", "卖装备");

		/*// 执行统计
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(6, StatisticsType.MONEY, price, StatisticsType.DEDAO,StatisticsType.SELL, p_pk);*/
	}

	/**
	 * 判断道具是否是受保护的，包括二次确认，是否保护，是否拾取绑定，是否装备绑定
	 * 
	 * @param goods_id
	 *            物品id
	 * @param protect
	 *            是否保护
	 * @param bonding
	 *            是否装备绑定
	 * @return
	 */
	public String isProtected(int goods_id, int goods_type)
	{
		String hint = null;

		GoodsControlVO goodsControl = getGoodsControl(goods_id, goods_type);
		if (goodsControl == null)
		{
			hint = "物品出错";
			return hint;
		}

		if (goodsControl.getProtect() == 1)// 是否是受保护的
		{
			hint = "受保护物品不可交易,不可丢弃";
			return hint;
		}

		return hint;
	}

	/**
	 * 判断物品是否绑定
	 * 
	 * @param goods_id
	 *            角色装备或道具的表id
	 * @param goods_type
	 *            表示是装备还是道具
	 * @param action
	 *            表示玩家的行为，如丢弃，买卖，交易，装备
	 * @return
	 */
	public String isBinded(int goods_id, int goods_type, int action)
	{
		String hint = null;

		GoodsControlVO goodsControl = getGoodsControl(goods_id, goods_type);
		if (goodsControl == null)
		{
			hint = "物品出错";
			return hint;

		}
		return isBinded(goodsControl, action);

	}

	/**
	 * 普通:可交易，可卖出，可丢弃，可使用 保护:不可交易，不可卖出，不可丢弃，不可拍卖，可使用
	 * 拾取绑定:物品进入物品栏后即不可交易,不可拍卖，但可卖出，可丢弃，可使用 装备绑定:物品装备后即不可交易，但可卖出，可丢弃，可使用
	 * 交易绑定:物品可交易，可卖出，可丢弃，自己不可使用；交易后与对方拾取绑定
	 * 
	 * @param goods_id
	 * @param goods_type
	 * @param action
	 *            表示玩家的行为，如丢弃，买卖，交易，装备，使用
	 * @return
	 */
	public String isBinded(GoodsControlVO goodsControl, int action)
	{
		String hint = null;
		if (goodsControl == null)
		{
			hint = "物品错误";
			return hint;
		}
		if (goodsControl.getProtect() == 1)// 是否是受保护的
		{
			if (action == ActionType.SALE || action == ActionType.EXCHANGE
					|| action == ActionType.THROW
					|| action == ActionType.AUCTION)
			{
				hint = "受保护物品不可交易,不可拍卖,不可买卖,不可丢弃";
				return hint;
			}
		}
		if (goodsControl.getBonding() == BondingType.PICKBOUND)// 拾取绑定
		{
			if (action == ActionType.EXCHANGE || action == ActionType.AUCTION)
			{
				hint = "拾取绑定物品不可交易,不可拍卖";
				return hint;
			}
			if (goodsControl.getBonding() == BondingType.ARMBOND)
			{
				if (action == ActionType.EXCHANGE
						|| action == ActionType.AUCTION)
				{
					hint = "装备绑定物品不可交易,不可拍卖";
					return hint;
				}
			}
		}
		else
			if (goodsControl.getBonding() == BondingType.EXCHANGEBOND)// 交易绑定
			{
				if (action == ActionType.USE)
				{
					hint = "交易绑定物品不可使用";
					return hint;
				}
			}

		return hint;
	}


	/**
	 * 得到装备价格
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
	 * 卖道具
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

		// 卸掉道具
		removeProps(propGroup, prop_num);

		int price = propGroup.getPropPrice();

		// 监控
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo
				.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()
				+ "", price * prop_num + "", "卖道具ID" + propGroup.getPropId()
				+ "数量" + prop_num);

		// 给用户增加钱数
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
		resultWml.append("您卖出" + StringUtil.isoToGBK(propGroup.getPropName())
				+ ",获得" + MoneyUtil.changeCopperToStr(price * prop_num));

		// 执行统计
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(6, StatisticsType.MONEY, price, StatisticsType.DEDAO,
				StatisticsType.SELL, propGroup.getPPk());
		return resultWml.toString();
	}

	/**
	 * 得到道具组
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
	 * 得到装备详情
	 * @param pg_pk
	 * @return
	 */
	public PlayerEquipVO getEquipByID(int pw_pk)
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getByID(pw_pk);
	}

	/**
	 * 得到道具信息
	 * @param goods_id
	 * @return
	 */
	public PropVO getPropInfo(int prop_id)
	{
		return PropCache.getPropById(prop_id);
	}

	/**
	 * 得到买道具信息脚本
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
		 * 少林寺的独门丹药，可在战斗中或非战斗中恢复1000点HP！ ------------------------ 卖出价格:100两
		 * 使用级别:99级 性别要求:无 职业要求:无 是否绑定:是 是否保护:是 使用 丢弃
		 */
		resultWml.append(prop.getPropName()).append("<br/>");
		resultWml.append(propPic);
		resultWml.append(prop.getPropDisplay()).append("<br/>");
		resultWml.append("卖出价格:").append( MoneyUtil.changeCopperToStr(prop.getPropSell())).append("<br/>");
		resultWml.append("使用级别:" + getPropDisplay(prop.getPropReLevel()) + "级").append("<br/>");

		if (prop.getPropSex() != 0 && !(prop.getPropSex() + "").equals(""))
		{
			resultWml.append("性别要求:").append( ExchangeUtil.exchangeToSex(prop.getPropSex())).append("<br/>");
		}
		if (prop.getPropJob() != null && !prop.getPropJob().equals("")
				&& !prop.getPropJob().equals("0"))
		{
			resultWml.append("职业要求:").append(titleService.getTitleNamesByTitleIDs(prop.getPropJob())).append("<br/>");
		}
		return resultWml.toString();
	}

	/**
	 * 得到买道具信息脚本,商店专用,显示买入价格
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
		 * 少林寺的独门丹药，可在战斗中或非战斗中恢复1000点HP！ ------------------------ 买入价格:100两
		 * 使用级别:99级 性别要求:无 职业要求:无 是否绑定:是 是否保护:是 使用 丢弃
		 */
		resultWml.append(prop.getPropName()).append("<br/>");
		resultWml.append(propPic);
		resultWml.append(prop.getPropDisplay()).append("<br/>");
		resultWml.append("使用级别:").append(getPropDisplay(prop.getPropReLevel())).append("级").append("<br/>");
		resultWml.append("性别要求:").append(ExchangeUtil.exchangeToSex(prop.getPropSex())).append("<br/>");
		resultWml.append("职业要求:").append(titleService.getTitleNamesByTitleIDs(prop.getPropJob())).append("<br/>");
		resultWml.append("------------------------").append("<br/>");
		resultWml.append("买入价格:").append(MoneyUtil.changeCopperToStr(prop_buy_price)).append("<br/>");
		return resultWml.toString();
	}

	/**
	 * 得到拍卖场道具物品显示
	 * @param goods_id
	 * @return
	 */
	public String getPropInfoWml(int pPk, int prop_id)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");
		PropVO prop = getPropInfo(prop_id);
		
		if( prop==null )
		{
			return "系统暂无该道具,请联系GM<br/>";
		}

		TitleService titleService = new TitleService();
		PicService picService = new PicService();
		String propPic = picService.getPropPicStr(roleInfo, prop_id);

		StringBuffer resultWml = new StringBuffer();
		/*
		 * 少林寺的独门丹药，可在战斗中或非战斗中恢复1000点HP！ ------------------------ 买入价格:100两
		 * 使用级别:99级 性别要求:无 职业要求:无 是否绑定:是 是否保护:是 使用 丢弃
		 */
		resultWml.append(prop.getPropName());
		resultWml.append("<br/>");
		resultWml.append(propPic);
		resultWml.append(prop.getPropDisplay());
		resultWml.append("<br/>");
		resultWml.append("--------------------");
		resultWml.append("<br/>");
		resultWml.append("卖出价格:").append(prop.getPropSell());
		resultWml.append("灵石");
		resultWml.append("<br/>");
		resultWml.append("使用级别:").append(getPropDisplay(prop.getPropReLevel())).append("级");
		resultWml.append("<br/>");
		resultWml.append("性别要求:").append(ExchangeUtil.exchangeToSex(prop.getPropSex()));
		resultWml.append("<br/>");
		resultWml.append("职业要求:").append(titleService.getTitleNamesByTitleIDs(prop.getPropJob()));
		resultWml.append("<br/>");
		return resultWml.toString();
	}

	/**
	 * 得到专门的有关捆装药的介绍
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
		 * 少林寺的独门丹药，可在战斗中或非战斗中恢复1000点HP！ ------------------------ 卖出价格:100两
		 * 使用级别:99级 性别要求:无 职业要求:无 是否绑定:是 是否保护:是 使用 丢弃
		 */
		resultWml.append(prop.getPropName());
		resultWml.append("<br/>");
		resultWml.append(propPic);
		resultWml.append(prop.getPropDisplay());
		resultWml.append("<br/>");
		if (prop.getPropClass() != PropType.BOX_CURE)
		{ // 如果道具是捆装类药品, 就让其显示出剩余血量或内力量
			resultWml.append("------------------------");
			resultWml.append("<br/>");
		}
		else
		{
			resultWml.append("剩余可治疗量").append(
					getSpecialPropNum(pPk, pg_pk, prop.getPropOperate2(),
							SpecialNumber.KUNZHUANG)).append("点").append(
					"<br/>");
		}
		resultWml.append("卖出价格:").append( MoneyUtil.changeCopperToStr(prop.getPropSell()));
		resultWml.append("<br/>");
		resultWml.append("使用级别:").append(getPropDisplay(prop.getPropReLevel())).append( "级");
		resultWml.append("<br/>");
		resultWml.append("性别要求:").append( ExchangeUtil.exchangeToSex(prop.getPropSex()));
		resultWml.append("<br/>");
		resultWml.append("职业要求:").append( titleService.getTitleNamesByTitleIDs(prop.getPropJob()));
		resultWml.append("<br/>");
		return resultWml.toString();
	}

	/**
	 * 得到道具信息脚本
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
		 * 少林寺的独门丹药，可在战斗中或非战斗中恢复1000点HP！ ------------------------ 卖出价格:100两
		 * 使用级别:99级 性别要求:无 职业要求:无 是否绑定:是 是否保护:是 使用 丢弃
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
			resultWml.append("剩余可治疗量").append(
					getSpecialPropNum(pPk, pg_pk, prop.getPropOperate2(),
							SpecialNumber.KUNZHUANG)).append("点").append(
					"<br/>");
		}
		resultWml.append("卖出价格:").append( MoneyUtil.changeCopperToStr(prop.getPropSell()));
		resultWml.append("<br/>");
		resultWml.append("使用级别:" + getPropDisplay(prop.getPropReLevel()) + "级");
		resultWml.append("<br/>");
		resultWml.append("性别要求:").append( ExchangeUtil.exchangeToSex(prop.getPropSex()));
		resultWml.append("<br/>");
		resultWml.append("职业要求:").append(titleService.getTitleNamesByTitleIDs(prop.getPropJob()));
		resultWml.append("<br/>");
		resultWml.append(prop.getStatusDisplay());
		/*String datestr = dgdao.getUsePropTime(prop_id);
		if (!datestr.equals("0"))
		{
			String newdatestr = datestr.replace(",", "~");
			resultWml.append("使用时限:" + newdatestr);
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
			resultWml.append("使用 ");
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
			resultWml.append("丢弃");
			resultWml.append("</anchor> ");
			resultWml.append("<br/>");
		}
		return resultWml.toString();
	}

	/**
	 * 得到道具信息脚本
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
		 * 少林寺的独门丹药，可在战斗中或非战斗中恢复1000点HP！ ------------------------ 卖出价格:100两
		 * 使用级别:99级 性别要求:无 职业要求:无 是否绑定:是 是否保护:是 使用 丢弃
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
			resultWml.append("剩余可治疗量").append(
					getSpecialPropNum(pPk, pg_pk, prop.getPropOperate2(),
							SpecialNumber.KUNZHUANG)).append("点").append(
					"<br/>");
		}
		resultWml.append("卖出价格:").append(MoneyUtil.changeCopperToStr(prop.getPropSell()));
		resultWml.append("<br/>");
		resultWml.append("使用级别:").append(getPropDisplay(prop.getPropReLevel()) + "级");
		resultWml.append("<br/>");
		resultWml.append("性别要求:").append(ExchangeUtil.exchangeToSex(prop.getPropSex()));
		resultWml.append("<br/>");
		resultWml.append("职业要求:").append(titleService.getTitleNamesByTitleIDs(prop.getPropJob()));
		resultWml.append("<br/>");
		resultWml.append(prop.getStatusDisplay());

		return resultWml.toString();
	}

	/**
	 * 获得捆装药品的剩余使用量
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

	/** 对道具使用等级的描述，如果使用上限等级为1000级, 就不予显示 */
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
	 * 买物品
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
			logger.info("购买数量不能为0");
			resultWml.append("请重新输入!<br/>");
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

		// 金额不足
		if (money < total_price)
		{
			resultWml.append("购买" + goods_num + "个" + npcshop.getGoodsName()
					+ ",需要" + MoneyUtil.changeCopperToStr(total_price)
					+ ",您的钱数不够!<br/>");
		}
		else
		{
			// 购买
			if (putGoodsToWrap(roleInfo.getPPk(), npcshop.getGoodsId(), npcshop.getGoodsType(), goods_num) == -1)
			{
				resultWml.append("包裹剩余空间不足!<br/>");
			}
			else
			{

				// 监控
				LogService logService = new LogService();
				logService.recordMoneyLog(roleInfo.getPPk(),roleInfo.getName(), roleInfo.getBasicInfo().getCopper()+ ""
						,-total_price + "", "买道具ID"+ npcshop.getGoodsName() + "数量" + goods_num);

				roleInfo.getBasicInfo().addCopper(-total_price);
				resultWml.append("您花费").append(MoneyUtil.changeCopperToStr(total_price)).append("购买了");
				resultWml.append(npcshop.getGoodsName()).append("×").append(goods_num).append("<br/>");
				// 执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY, total_price,StatisticsType.USED, StatisticsType.BUY, roleInfo.getPPk());


			}
		}

		return resultWml.toString();
	}

	/**
	 * 得到道具总数
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
	 * 判断道具是否需要二次确认
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
	 * 掉落物品,
	 * @param loser 失败者
	 * @param winer  胜利者
	 */
	public void dropGoods(Fighter loser, Fighter winer)
	{
		int drop_num = getDropNum(loser.getPPkValue());

		logger.info(" 死亡后掉落的物品数量=" + drop_num);
		StringBuffer sb = new StringBuffer();
		if( drop_num>0 )
		{
			PlayerPropGroupDao pgDao = new PlayerPropGroupDao();
			PlayerEquipDao equipDao = new PlayerEquipDao();
			//得到未受保护的道具
			List<Integer> prop_key_list = pgDao.getNoProtectPropId(loser.getPPk());
			//得到未受保护的装备
			List<Integer> equip_key_list = equipDao.getNoProtectEquipId(loser.getPPk());
			
			if( prop_key_list.size()==0 && equip_key_list.size()==0 )
			{
				//无物品可掉
				return;
			}
			ItemContainer drop_list = new ItemContainer();

			sb.append("掉落的物品有:");
			for (int i = 0; i < drop_num; i++)
			{
				if( prop_key_list.size()==0 && equip_key_list.size()==0 )
				{
					//如果没有掉落物品则退出
					break;
				}
				
				if( prop_key_list.size()==0 )
				{
					//如果没有道具则掉落装备
					drop_list.add(dropEquip(loser, equip_key_list));
				}
				else if( equip_key_list.size()==0 )
				{
					//如果没有装备则掉落道具
					drop_list.add(dropProp(loser, prop_key_list));
				}
				else//如果装备，道具都有，则50%的概率选择掉落装备或道具
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
				//如果有掉落物品
				//给失败者提示信息
				loser.appendKillDisplay("掉落的物品有:").append(drop_list.getDes()).append("<br/>");
				//给胜利者发送邮件
				winer.appendKillDisplay(loser.getPName()).append("死亡掉落的物品请到邮件中提取").append("<br/>");
				
				MailInfoService mailInfoService = new MailInfoService();
				MailInfoVO mail_info = new MailInfoVO();
				StringBuffer title = new StringBuffer();//邮件标题
				title.append(loser.getPName()).append("被你杀死掉落的物品");
				mail_info.createPKDropItemMail(winer.getPPk(), title.toString(),drop_list.getDes(), drop_list);
				mailInfoService.sendMail(mail_info);
			}
		}
		loser.setDropDisplay(sb.toString());
	}



	/**
	 * 随机掉落一件装备
	 * @param loser
	 * @param equip_key_list			可以掉落的装备id列表
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
			if ( equipvo.isProtectOnPK()==false)//不受保护的装备
			{
				if( equipvo.isOnBody() )
				{
					RoleEntity role_info = RoleService.getRoleInfoById(loser.getPPk());
					role_info.getEquipOnBody().takeoff(equipvo);
				}
				else
				{
					// 增加玩家包裹剩余空间数量
					equipService.addWrapSpare(loser.getPPk(), 1);
				}
				equipvo.drop();//装备掉落
				u_equip = new UEquip(equipvo);
			}
		}
		return u_equip;
	}

	/**
	 *  随机掉落一组道具
	 * @param loser
	 * @param prop_key_list			可以掉落的道具id列表
	 * @return
	 */
	private Prop dropProp(Fighter loser, List<Integer> prop_key_list)
	{
		Prop drop_prop = null;//掉落的道具
		
		if (prop_key_list != prop_key_list && prop_key_list.size() != 0)
		{
			//得到随机pg_pk
			int random_index = MathUtil.getRandomBetweenXY(0, prop_key_list.size()-1);
			int pg_pk = prop_key_list.remove(random_index);
			PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
			PlayerPropGroupVO vo = playerPropGroupDao.getByPgPk(pg_pk);
			if( vo!=null )
			{
				drop_prop = new Prop(vo.getPropId(),vo.getPropNum());
				playerPropGroupDao.deletePropGroup(pg_pk);
				// 增加玩家包裹剩余空间数量
				EquipService equipService = new EquipService();
				equipService.addWrapSpare(loser.getPPk(), 1);
				
				GameLogManager.getInstance().propLog(loser.getPPk(), vo.getPropInfo(), vo.getPropNum(),GameLogManager.R_DEAD);
			}

		}
		return drop_prop;
	}

	/**
	 * 获得掉落物品的数量
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
	 * 减少捆装药品的总可治疗量
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
	 * 玩家捡到全部物品,result=-1时表示包裹格数不够
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

		logger.info("取出来的dropGoods.size=" + dropGoods.size());

		// 此人剩余的空格数
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
		// 此人捡起装备后的空格数
		if (handleWrapSpare < 0)
		{
			return result;
		}

		// 用以储存每个道具的需要占据的包裹空间，前一个参数为道具id,后一个参数为包裹空间
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

		logger.info("最后的剩余空间=" + handleWrapSpare);

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
				// 执行统计
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

		// 删除掉此人在掉落表中的信息
		roleInfo.getDropSet().clearDropItem();
		return 1;

	}

	/**
	 * 将道具插到包裹里
	 * @param dropGoodsVO
	 * @param need_group
	 *            第一个为道具的占据包裹空间大小, 第二个为插入时剩余道具组的道具数量
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

		// 找到没有重叠慢的道具组
		PlayerPropGroupVO propGroup = new PlayerPropGroupVO();
		propGroup.setPgType(prop.getPropPosition());
		propGroup.setPropBonding(prop.getPropBonding());
		propGroup.setPropProtect(prop.getPropProtect());
		propGroup.setPropIsReconfirm(prop.getPropReconfirm());
		propGroup.setPropUseControl(prop.getPropUseControl());

		propGroup.setPPk(p_pk);

		propGroup.setPropId(prop.getPropID());
		propGroup.setPropType(prop.getPropClass());

		// 添加新的道具组，数量都是accumulate_num
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
		if (need_groups == 0 && goodsgourp_goodsnum == 0 && goods_num != 0) // 不需要加道具组，正好装满道具组
		{
			propGroupDao
					.updatePropGroupNum(propGroup.getPgPk(), accumulate_num);
		}

		// 包裹空间减少need_groups
		EquipService equipService =new EquipService();
		equipService.addWrapSpare(p_pk, -need_groups);

	}

	/**
	 * 查看掉落的道具需要多少格的包裹空间
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

		int accumulate_num = prop.getPropAccumulate();// 可以重叠的数量

		int current_num = propGroupDao.getPropNumByByPropID(p_pk, goods_id);// 现有的数量
		int current_groups_sql = propGroupDao.getPropGroupNumByPropID(p_pk,
				goods_id); // 当前数据库中的组数

		int total_num = current_num + goods_num;// 增加goods_num后的总数

		int current_groups = 0;// 现有的组数
		if (current_num != 0)
		{
			// 因为玩家的包裹该物品格子不一定都是理想状态, 所以可能有零散的,故需要从数据库中取现有组数 .
			// current_groups =(current_num-1)/accumulate_num+1;
			current_groups = propGroupDao.getPropGroupNumByPropID(p_pk,
					goods_id);
		}

		int new_groups = (total_num - 1) / accumulate_num + 1;// 增加goods_num个道具后的组数

		int need_groups = new_groups - current_groups;// 需要增加的道具组数

		int goodsgourp_goodsnum = total_num % accumulate_num;// 不完整道具组的道具数量

		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();// 剩余的包裹空间数

		if (wrap_spare == 0)
		{
			if (current_num == 0) // 如果没有空格,且包裹中也无此物,则肯定放不下
			{
				logger.debug("包裹空间不够");
				need_arg[0] = -1;
				return need_arg;
				// 若放进包裹中数量加原有数超过包裹中此物品所占格数的最大容纳值,也放不下
			}
			else
				if (current_num + goods_num > current_groups_sql
						* accumulate_num)
				{

					logger.debug("包裹空间不够");
					need_arg[0] = -1;
					return need_arg;
				}
		}
		else
		{

			if (current_num == 0)
			{
				// 如果包裹中没有此物,且此物品的数量大于空余格数的最大可容纳值,也放不下
				if (goods_num > wrap_spare * accumulate_num)
				{
					need_arg[0] = -1;
					return need_arg;
				}
			}
			else
			{
				// 若包裹中物品的数量和加入数量超过原占格数和空余格数之和的最大可容纳值,也放不下.
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
	 * 把道具放到包裹里，重组当前道具包裹个数的效果
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

		int accumulate_num = prop.getPropAccumulate();// 可以重叠的数量

		int current_num = propGroupDao.getPropNumByByPropID(p_pk, goods_id);// 现有的数量

		int total_num = current_num + goods_num;// 增加goods_num后的总数

		int current_groups = 0;// 现有的组数
		if (current_num != 0)
		{
			// 因为玩家的包裹该物品格子不一定都是理想状态, 所以可能有零散的,故需要从数据库中取现有组数 .
			// current_groups =(current_num-1)/accumulate_num+1;
			current_groups = propGroupDao.getPropGroupNumByPropID(p_pk,
					goods_id);
		}
		int new_groups = (total_num - 1) / accumulate_num + 1;// 增加goods_num个道具后的组数
		int need_groups = new_groups - current_groups;// 需要增加的道具组数,需要包裹的个数为负数表示增加包裹个数
		int goodsgourp_goodsnum = total_num % accumulate_num;// 不完整道具组的道具数量
		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();// 剩余的包裹空间数
		if (need_groups > 0 && wrap_spare < need_groups)
		{
			logger.debug("包裹空间不够");
			return -1;
		}

		if (need_groups > 0)// 添加不足的道具组的个数
		{
			for (int i = 0; i < need_groups; i++)
			{
			}
		}

		if (need_groups < 0)// 需要的道具组数为负数表示，重组完需要释放的道具组数
		{
			int delete_group_num = -need_groups;
		}
		// 把玩家身上所有道具id为prop_id的道具的数量填满

		if (goodsgourp_goodsnum != 0)// 不完整道具组的道具数量不为0时，更新其中的一组道具为（不完整道具组的道具数量）
		{
		}
		// 更新玩家的剩余包裹个数
		return need_groups;
	}

	/**
	 * 返回包裹各数是否够不够
	 */
	public boolean isEnoughWrapSpace(int pPk, int number)
	{

		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");

		// 得到包裹剩余的空间
		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();

		if ((wrap_spare - number) < 0)
		{
			return false;
		}
		return true;
	}

	/** 装备类道具的描述 */
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
		 * 剩余HP:1000/10000 剩余时间:240分钟/3600分钟 使用 丢弃 补充
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
			resultWml.append("卖出价格:"
					+ MoneyUtil.changeCopperToStr(prop.getPropSell()));
			resultWml.append("<br/>");
			resultWml.append("使用级别:" + getPropDisplay(prop.getPropReLevel())
					+ "级");
			resultWml.append("<br/>");
			resultWml.append("性别要求:"
					+ ExchangeUtil.exchangeToSex(prop.getPropSex()));
			resultWml.append("<br/>");
			resultWml.append("职业要求:"
					+ titleService.getTitleNamesByTitleIDs(prop.getPropJob()));
			resultWml.append("<br/>");
			if (prop.getPropClass() == PropType.JIEHUN_JIEZHI)
			{
				resultWml.append("爱情甜蜜:"
						+ new FriendService().findLove_dear(pPk));
				resultWml.append("<br/>");
			}

			/**
			 * if(x1 != 0) { resultWml.append("剩余HP:"+ x2 +"/"+ x3);
			 * resultWml.append("<br/>"); } if(x4 != 0) {
			 * resultWml.append("剩余MP:"+ x5 +"/"+ x6); resultWml.append("<br/>"); }
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
				resultWml.append("剩余时间:" + time + "分钟/"
						+ prop.getPropOperate2() + "分钟");
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
				resultWml.append("使用 ");
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
				resultWml.append("丢弃");
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
					resultWml.append("使用 ");
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
					resultWml.append("丢弃");
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
					resultWml.append("更换 ");
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
				resultWml.append("使用 ");
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
				resultWml.append("丢弃");
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
					resultWml.append("使用");
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
					resultWml.append("丢弃");
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
					resultWml.append("更换");
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
		 * resultWml.append("</go>"); resultWml.append("补充");
		 * resultWml.append("</anchor> ");
		 */
		resultWml.append("<br/>");
		return resultWml.toString();
	}

	/** 得到玩家药品的列表 *//*
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
	 * 得到相同类型的道具
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
	 * 找到数量最少的道具组
	 * @return
	 */
	public PlayerPropGroupVO getPropGroupByPropID(int p_pk, int prop_id)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		return propGroupDao.getPropGroupByPropID(p_pk, prop_id);
	}

	/**
	 * 更改秘境地图的拥有者
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
	 * 查看身上是否拥有秘境地图
	 */
	public boolean haveMiJing(int ppk)
	{
		return new PlayerPropGroupDao().haveMiJing(ppk);
	}

	/**
	 * 删除身上的秘境地图
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
