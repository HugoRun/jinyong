package com.ls.model.drop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ls.ben.vo.info.attack.DropExpMoneyVO;
import com.ls.ben.vo.info.attack.DropGoodsVO;

/**
 * @author ls
 * 掉落集合（临时存放玩家掉落物品,金钱和经验的地方）
 */
public class DropSet
{
	private DropExpMoneyVO exp_and_money = null;
	private Map<Integer,DropGoodsVO> item_list = new HashMap<Integer,DropGoodsVO>(20);
	private int key =0;
	/**
	 * 添加掉落的金钱和经验
	 * @param expAndMoney
	 */
	public void addExpAndMoney(DropExpMoneyVO expAndMoney)
	{
		if( exp_and_money!=null )
		{
			exp_and_money.add(expAndMoney);
		}
		else
		{
			exp_and_money = expAndMoney;
		}
	}
	/**
	 * 得到掉落的金钱和经验
	 * @param expAndMoney
	 */
	public DropExpMoneyVO getExpAndMoney()
	{
		if( exp_and_money==null )
		{
			exp_and_money = new DropExpMoneyVO();
		}
		return exp_and_money;
	}
	/**
	 * 清除掉落的金钱和经验
	 */
	public void clearExpAndMoney()
	{
		exp_and_money = null;
	}
	
	/**
	 * 得到掉落物品id
	 */
	public int getItemIdById(int index)
	{
		DropGoodsVO drop_item = this.getItemById(index);
		if( drop_item!=null )
		{
			return drop_item.getGoodsId();
		}
		else
		{
			return -1;
		}
	}
	/**
	 * 得到掉落物品名字
	 */
	public String getItemNameById(int index)
	{
		DropGoodsVO drop_item = this.getItemById(index);
		if( drop_item!=null )
		{
			return drop_item.getGoodsName();
		}
		else
		{
			return "";
		}
	}
	
	
	/**
	 * 根据id得到掉率物品
	 * @param index
	 * @return
	 */
	public DropGoodsVO getItemById( int index)
	{
		try
		{
			return item_list.get(index);
		}
		catch (IndexOutOfBoundsException e)
		{
			//index越界
			return null;
		}
	}
	
	
	/**
	 * 得到物品列表
	 * @return
	 */
	public List<DropGoodsVO> getList()
	{
		return new ArrayList<DropGoodsVO>(item_list.values());
	}
	
	/**
	 * 添加物品集合
	 * @param itemList
	 * @param maxDropNum   最多掉落数量
	 */
	public void addDroItem(List<DropGoodsVO> itemList,int maxDropNum)
	{
		this.clearDropItem();
		if( itemList!=null )
		{
			if( maxDropNum>itemList.size())
			{
				maxDropNum = itemList.size();
			}
			for (int i = 0; i < maxDropNum; i++)
			{
				DropGoodsVO drop_item = itemList.get(i);
				this.addDropItem(drop_item);
			}
		}
	}
	/**
	 * 添加物品集合
	 */
	public void addDropItem(List<DropGoodsVO> itemList)
	{
		if( itemList!=null )
		{
			this.addDroItem(itemList, itemList.size());
		}
	}
	/**
	 * 添加物品
	 */
	public void addDropItem(DropGoodsVO dropItem)
	{
		if( dropItem!=null )
		{
			dropItem.setDPk(key++);
			item_list.put(dropItem.getDPk(),dropItem);
		}
	}
	
	/**
	 * 移除一个掉率物品
	 */
	public void removeDropItem(int index)
	{
		try
		{
			this.item_list.remove(index);
		}
		catch (IndexOutOfBoundsException e)
		{
			//index越界
		}
	}
	
	/**
	 * 情况所有掉落数据
	 */
	public void clearDropItem()
	{
		key = 0;
		item_list.clear();
	}
}
