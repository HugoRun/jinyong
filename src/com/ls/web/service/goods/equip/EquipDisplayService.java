package com.ls.web.service.goods.equip;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.vo.info.npc.NpcShopVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.system.SystemConfig;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MoneyUtil;

/**
 * @author ls
 * 装备显示逻辑
 */
public class EquipDisplayService
{
	public static final int DISPLAY = 1;
	public static final int NOTDISPLAY = 0;

	/**
	 * 获得买装备的显示
	 * @param roleInfo
	 * @param equipId
	 * @param originalityPrice     原始价钱
	 * @return
	 */
	public String getBuyEquipDisplay(RoleEntity roleInfo,NpcShopVO npcShop)
	{
		int equip_id =  npcShop.getGoodsId();
		GameEquip gameEquip = EquipCache.getById(equip_id);
		StringBuffer sb = new StringBuffer();
		int total_price = npcShop.getPrice(roleInfo);
		sb.append(this.getEquipDisplay(roleInfo, gameEquip, false));
		sb.append("------------------------").append("<br/>");
		sb.append("买入价格:").append(MoneyUtil.changeCopperToStr(total_price)).append("<br/>");			
		return sb.toString();
	}
	
	/**
	 * 获得系统装备的显示，有对比功能（饰品不做对比）
	 * @param roleInfo
	 * @param equipId
	 * @param isCompare     true表示装备跟玩家身上的同一位置的装备对比，false不对比
	 * @return
	 */
	public String getEquipDisplay(RoleEntity roleInfo,int equipId, boolean isCompare)
	{
		GameEquip gameEquip = EquipCache.getById(equipId);
		return this.getEquipDisplay(roleInfo, gameEquip, isCompare);
	}
	
	/**
	 * 获得系统装备的显示，有对比功能（饰品不做对比）
	 * @param roleInfo
	 * @param gameEquip
	 * @param isCompare     true表示装备跟玩家身上的同一位置的装备对比，false不对比
	 * @return
	 */
	public String getEquipDisplay(RoleEntity roleInfo,GameEquip gameEquip, boolean isCompare)
	{
		StringBuffer des = new StringBuffer();
		
		PlayerEquipVO compareEquip = null;//要对比的装备
		
		if( isCompare==true && gameEquip.getType()!=Equip.JEWELRY )
		{
			compareEquip = roleInfo.getEquipOnBody().getByPositin(gameEquip.getType());
		}
		if (gameEquip != null) {
			des.append(gameEquip.getName()).append("<br/>");
			des.append(gameEquip.getPicDisplay());
			des.append(gameEquip.getDes()).append("<br/>");
			
			des.append("耐久:").append(gameEquip.getEndure()).append("<br/>");
			
			if( gameEquip.getMaxAtt()>0)//是否有攻击属性
			{
				des.append("攻击:").append(gameEquip.getMinAtt()*SystemConfig.attackParameter).append("-").append(gameEquip.getMaxAtt()*SystemConfig.attackParameter);
				if(compareEquip!=null)
					des.append("(").append(compareEquip.getWGjXiao()*SystemConfig.attackParameter).append("-").append(compareEquip.getWGjDa()*SystemConfig.attackParameter).append(")");
				des.append("<br/>");
			}
			
			if( gameEquip.getMaxDef()>0 )//是否有防御属性
			{
				des.append("防御:").append(gameEquip.getMinDef()*SystemConfig.attackParameter).append("-").append(gameEquip.getMaxDef()*SystemConfig.attackParameter);
				if(compareEquip!=null)
					des.append("(").append(compareEquip.getWFyXiao()*SystemConfig.attackParameter).append("-").append(compareEquip.getWFyDa()*SystemConfig.attackParameter).append(")");
				des.append("<br/>");
			}
			
			des.append("卖出价格:").append(gameEquip.getPriceDes()).append("<br/>");			
			des.append("使用等级:").append(gameEquip.getGrade()).append("级").append("<br/>");
			des.append("性别要求:").append(ExchangeUtil.exchangeToSex(gameEquip.getSex())).append("<br/>");
			des.append("种族要求:").append(ExchangeUtil.getRaceName(gameEquip.getJob())).append("<br/>");
			des.append("装备状态:");
			if( gameEquip.getIsProtected() == 1) {
				des.append("不可交易，不可卖出，不可丢弃，不可拍卖，可使用!"); 
			} else {
				des.append("可交易,可拍卖，可卖出，可丢弃，可使用!");
			}
			des.append("<br/>");
		} else {
			des.append("无该装备").append("<br/>");
		}
		return des.toString();
	}
	/**
	 * 获得玩家装备的显示，有对比功能（饰品不做对比）
	 * @param roleInfo
	 * @param equip
	 * @param isCompare     true表示装备跟玩家身上的同一位置的装备对比，false不对比
	 * @return
	 */
	public String getEquipDisplay(RoleEntity roleInfo,PlayerEquipVO equip, boolean isCompare)
	{
		StringBuffer des = new StringBuffer();
		
		PlayerEquipVO compareEquip = null;//要对比的装备
		
		if( isCompare==true && equip.getEquipType()!=Equip.JEWELRY )
		{
			compareEquip = roleInfo.getEquipOnBody().getByPositin(equip.getEquipType());
		}
		
		if (equip != null) {
			GameEquip gameEquip = equip.getGameEquip();
			
			des.append(equip.getFullName()).append("<br/>");
			des.append(equip.getQualityFullName());
			if(roleInfo.getSettingInfo().getGoodsPic()==1)
			{
				des.append(gameEquip.getPicDisplay());
			}
			des.append(gameEquip.getDes()).append("<br/>");
			des.append(equip.getHoleDes());
			des.append("耐久:").append(equip.getCurEndure()/10).append("/").append(equip.getMaxEndure()/10).append("<br/>");
			
			if( equip.getMinAtt()>0)//是否有攻击属性
			{
				des.append("攻击:").append(equip.getMinAtt()*SystemConfig.attackParameter).append("-").append(equip.getMaxAtt()*SystemConfig.attackParameter);
				if(compareEquip!=null)
				des.append("(").append(compareEquip.getMinAtt()*SystemConfig.attackParameter).append("-").append(compareEquip.getMaxAtt()*SystemConfig.attackParameter).append(")");
				des.append("<br/>");
			}
			if( equip.getMinDef()>0 )//是否有防御属性
			{
				des.append("防御:").append(equip.getMinDef()*SystemConfig.attackParameter).append("-").append(equip.getMaxDef()*SystemConfig.attackParameter);
				if(compareEquip!=null)
					des.append("(").append(compareEquip.getMinDef()*SystemConfig.attackParameter).append("-").append(compareEquip.getMaxDef()*SystemConfig.attackParameter).append(")");
				des.append("<br/>");
			}
			
			
			if ( equip.getWHp() > 0 ) 
			{
				des.append("气血:").append(equip.getWHp()).append("-").append(equip.getWHp());
				if(compareEquip!=null)
				des.append("(").append(compareEquip.getWHp()).append("-").append(compareEquip.getWHp()).append(")");
				des.append("<br/>");
			}
			
			
			if ( equip.getWMp() >  0 ) 
			{
				des.append("内力:").append(equip.getWMp()).append("-").append(equip.getWMp());
				if(compareEquip!=null)
				des.append("(").append(compareEquip.getWMp()).append("-").append(compareEquip.getWMp()).append(")");
				des.append("<br/>");
			}
			
			des.append("卖出价格:").append(gameEquip.getPriceDes()).append("<br/>");			
			des.append("使用等级:").append(gameEquip.getGrade()).append("级").append("<br/>");
			des.append("性别要求:").append(ExchangeUtil.exchangeToSex(gameEquip.getSex())).append("<br/>");
			des.append("种族要求:").append(ExchangeUtil.getRaceName(gameEquip.getJob())).append("<br/>");
			des.append(equip.getWuxing().getDes());
			des.append(equip.getInlayDes());
			
			
			if(equip.getAppendAttriDes()!=null && !equip.getAppendAttriDes().equals(""))
			{
				des.append("开光属性:").append(equip.getAppendAttriDes()).append("<br/>");
			}
			 
			des.append("装备状态:");
			if( equip.getWProtect() == 1 || equip.getWBonding()==1) 
			{
				if( equip.getWProtect() == 1 )//保护状态
				{
					des.append("不可交易,不可卖出,不可拍卖,可使用,不可丢弃"); 
				}
				else//绑定状态
				{
					des.append("不可交易,不可卖出,不可拍卖,可使用,可丢弃,"); 
				}
			} 
			else 
			{
				des.append("可交易,可拍卖,可卖出,可丢弃,可使用");
			}
			des.append(equip.getProtectDes());
			des.append("<br/>");
			 
		} else {
			des.append("无该装备").append("<br/>");
		}
		 return des.toString();
		}

}
