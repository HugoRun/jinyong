package com.ls.model.item.impl;

import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.model.item.Item;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipService;

/**
 * @author ls
 * 玩家装备
 */
public class UEquip extends Item
{
	private PlayerEquipVO uEquip;
	
	public UEquip()
	{
	}
	
    public UEquip(PlayerEquipVO uEquip)
    {
    	this.uEquip = uEquip;
    	super.init(uEquip.getFullName(), 1);
    }
	
	@Override
	public void init(String pwPk) throws Exception
	{
		GoodsService goodsService = new GoodsService();
		
		uEquip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		if( uEquip==null)
		{
			throw new Exception("无该装备：pwPK="+pwPk);
		}
		
		if( uEquip.getPPk()!=-1 )
		{
			throw new Exception("该装备有拥有者：p_pk="+uEquip.getPPk());
		}
		
		super.init(uEquip.getWName(), 1);
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		return sb.append("uzb-").append(uEquip.getPwPk()).toString();
	}

	@Override
	public int getNeedWrapSpace()
	{
		return 1;
	}

	@Override
	public void gain(RoleEntity roleInfo,int gain_type)
	{
		EquipService equipService = new EquipService();
		equipService.updateOwner(roleInfo, uEquip.getPwPk(),gain_type);
	}

}
