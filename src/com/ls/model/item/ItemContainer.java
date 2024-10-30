package com.ls.model.item;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ls.model.user.RoleEntity;

/**
 * @author ls
 * 游戏里可以流通的的物品（道具，装备，金钱，声望，经验，帮派贡献度）容器
 */
public class ItemContainer
{
	private List<Item> item_list = new ArrayList<Item>();
	
	public ItemContainer()
	{
		
	}
	
	public ItemContainer( String itemStr )
	{
		if( StringUtils.isEmpty(itemStr)==false )
		{
			String[] item_str_list = itemStr.split(Item.FIRST_SPLIT);
			for (String item_str : item_str_list)
			{
				Item item = ItemBuilder.build(item_str);
				if( item!=null )
				{
					item_list.add(item);
				}
			}
		}
	}
	
	/**
	 * 得到需要包裹的空间
	 * @return
	 */
	public int getNeedWrapSpace()
	{
		int need_wrap_space = 0;
		for (Item item : item_list)
		{
			need_wrap_space+=item.getNeedWrapSpace();
		}
		return need_wrap_space;
	}
	
	/**
	 * 玩家获得该物品集合
	 * @return
	 */
	public String gainItems(RoleEntity roleInfo,int gain_type )
	{
		//需要包裹的空间数
		int need_wrap_space = getNeedWrapSpace();
		
		String hint = roleInfo.getBasicInfo().isEnoughWrapSpace(need_wrap_space);
		if( hint!=null)
		{
			return hint;
		}
		for (Item item : item_list)
		{
			item.gain(roleInfo,gain_type);
		}
		
		return null;
	}
	
	/**
	 * 得到物品组数
	 * @return
	 */
	public int getItemGroupNum()
	{
		return this.item_list.size();
	}
	
	/**
	 * 材料是否足够
	 * @return
	 */
	public boolean isEnough()
	{
		return false;
	}
	
	/**
	 * 添加
	 */
	public void add(Item item)
	{
		if( item!=null )
		{
			item_list.add(item);
		}
	}
	
	/**
	 * 得到描述字符串
	 */
	public String getDes()
	{
		StringBuffer sb = new StringBuffer();
		if( item_list.size()>0 )
		{
			for (int i = 0; i < item_list.size(); i++)
			{
				sb.append(item_list.get(i).getDes());
				if( i<(item_list.size()-1) )
				{
					sb.append(",");
				}
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 转换成字符串
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		if( item_list.size()>0 )
		{
			for (int i = 0; i < item_list.size(); i++)
			{
				sb.append(item_list.get(i).toString());
				if( i<(item_list.size()-1) )
				{
					sb.append(",");
				}
			}
		}
		
		return sb.toString();
	}
	
}