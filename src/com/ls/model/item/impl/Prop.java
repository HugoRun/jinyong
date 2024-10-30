package com.ls.model.item.impl;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.model.item.Item;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.goods.GoodsService;

/**
 * @author ls
 * µÀ¾ß
 */
public class Prop extends Item
{
	private PropVO prop;
	
	public Prop()
	{
		
	}
	
	public Prop(int propId,int propNum )
	{
		this(PropCache.getPropById(propId),propNum);
	}
	
	public Prop(PropVO prop,int propNum )
	{
		this.prop = prop;
		super.init(prop.getPropName(), propNum);
	}
	
	public Prop(String propStr)  throws Exception
	{
		this.init(propStr);
	}
	
	public void init(String propStr) throws Exception
	{
		String[] temp = propStr.split("-");
		this.prop = PropCache.getPropById(Integer.parseInt(temp[0]));
		super.init(prop.getPropName(), Integer.parseInt(temp[1]));
	}

	public int getId()
	{
		return prop.getPropID();
	}
	
	public boolean equals(Object item)
	{
		if(item instanceof Prop)
		{
			Prop prop = (Prop) item;
			if( this.getId()==prop.getId() )
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		return sb.append(Item.PROP_PREFIX_STR).append(Item.SECOND_SPLIT).append(prop.getPropID()).append(Item.SECOND_SPLIT).append(this.num).toString();
	}

	@Override
	public int getNeedWrapSpace()
	{
		int need_wrap_space = this.num/this.prop.getPropAccumulate();
		if( this.num%this.prop.getPropAccumulate()>0 )
		{
			need_wrap_space++;
		}
		return need_wrap_space;
	}

	@Override
	public void gain(RoleEntity roleInfo,int gain_type)
	{
		GoodsService goodsService = new GoodsService();
		goodsService.putPropToWrap(roleInfo.getPPk(), this.prop.getPropID(), this.num,gain_type);
	}

}
