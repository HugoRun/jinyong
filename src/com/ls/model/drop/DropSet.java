package com.ls.model.drop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ls.ben.vo.info.attack.DropExpMoneyVO;
import com.ls.ben.vo.info.attack.DropGoodsVO;

/**
 * @author ls
 * ���伯�ϣ���ʱ�����ҵ�����Ʒ,��Ǯ�;���ĵط���
 */
public class DropSet
{
	private DropExpMoneyVO exp_and_money = null;
	private Map<Integer,DropGoodsVO> item_list = new HashMap<Integer,DropGoodsVO>(20);
	private int key =0;
	/**
	 * ��ӵ���Ľ�Ǯ�;���
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
	 * �õ�����Ľ�Ǯ�;���
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
	 * �������Ľ�Ǯ�;���
	 */
	public void clearExpAndMoney()
	{
		exp_and_money = null;
	}
	
	/**
	 * �õ�������Ʒid
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
	 * �õ�������Ʒ����
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
	 * ����id�õ�������Ʒ
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
			//indexԽ��
			return null;
		}
	}
	
	
	/**
	 * �õ���Ʒ�б�
	 * @return
	 */
	public List<DropGoodsVO> getList()
	{
		return new ArrayList<DropGoodsVO>(item_list.values());
	}
	
	/**
	 * �����Ʒ����
	 * @param itemList
	 * @param maxDropNum   ����������
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
	 * �����Ʒ����
	 */
	public void addDropItem(List<DropGoodsVO> itemList)
	{
		if( itemList!=null )
		{
			this.addDroItem(itemList, itemList.size());
		}
	}
	/**
	 * �����Ʒ
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
	 * �Ƴ�һ��������Ʒ
	 */
	public void removeDropItem(int index)
	{
		try
		{
			this.item_list.remove(index);
		}
		catch (IndexOutOfBoundsException e)
		{
			//indexԽ��
		}
	}
	
	/**
	 * ������е�������
	 */
	public void clearDropItem()
	{
		key = 0;
		item_list.clear();
	}
}
