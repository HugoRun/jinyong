package com.ls.model.item;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ls.model.user.RoleEntity;

/**
 * @author ls
 * ��Ϸ�������ͨ�ĵ���Ʒ�����ߣ�װ������Ǯ�����������飬���ɹ��׶ȣ�����
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
	 * �õ���Ҫ�����Ŀռ�
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
	 * ��һ�ø���Ʒ����
	 * @return
	 */
	public String gainItems(RoleEntity roleInfo,int gain_type )
	{
		//��Ҫ�����Ŀռ���
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
	 * �õ���Ʒ����
	 * @return
	 */
	public int getItemGroupNum()
	{
		return this.item_list.size();
	}
	
	/**
	 * �����Ƿ��㹻
	 * @return
	 */
	public boolean isEnough()
	{
		return false;
	}
	
	/**
	 * ���
	 */
	public void add(Item item)
	{
		if( item!=null )
		{
			item_list.add(item);
		}
	}
	
	/**
	 * �õ������ַ���
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
	 * ת�����ַ���
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