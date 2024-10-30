package com.ls.ben.vo.mall;

import java.util.Date;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;

/**
 * ���ܣ���Ʒ
 * @author ls
 * May 12, 2009
 * 3:53:09 PM
 */

public class CommodityVO
{
	/*1��	��Ʒid(��Ӧ��Ʒ��id)
	 * ����id
	2��	��Ʒ����
	3��	���׷�ʽ��1Ϊ���֣�2ΪԪ����
	4��	��Ʒ��Ǯ����ʾ������Ʒ����Ҫ��Ԫ������ֵ�������
	5��	��������
	6��	�Ƿ����Ƽ���Ʒ��0��Ĭ��ֵ��Ϊ�Ƽ���Ʒ��1Ϊ�Ƽ���Ʒ��
	7��	��Ʒ״̬��1��Ĭ��ֵ��Ϊ���ߣ�0Ϊ���ߣ�
	8��	����ʱ��
	discount  ,/**��Ʒ��ǰ���ۿۣ�������ʽΪ���磺80����8�ۣ�Ĭ��Ϊ100��ʾ������
    isUsedAfterBuy    int      default 1        ,/**������Ƿ��ֱ��ʹ��
 	commodity_total   int      default -1       ,/**��Ʒ����-1��ʾ������*/

	private PropVO prop;
	private int id;
	private int propId;
	private String propName;
	private int type;
	private int buyMode;
	private int originalPrice;
	private int sellNum;
	private int isHot;
	private int state;
	private Date datetime;
	private int discount;
	private int isUsedAfterBuy;
	private int commodityTotal;
	private int isVip=0;//�Ƿ��ǻ�Ա��Ʒ��1��0��
	
	public String getDisplay()
	{
		return this.getProp().getPropDisplay();
	}
	
	/**
	 * ���ݹ��������ж��Ƿ������
	 */
	public boolean isEnough(int sell_num)
	{
		int store_num = getStoreNum();//���
		if(  commodityTotal!=-1 && store_num-sell_num<0 )//�����ҿ�治��
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * �õ����
	 * @return
	 */
	public int getStoreNum()
	{
		return commodityTotal - sellNum;
	}
	
	public int getDiscount()
	{
		return discount;
	}
	/**
	 * �ۿ���Ϣ��ʾ����
	 * @return
	 */
	public String getDiscountDisplay()
	{
		int temp = discount%10;
		if( temp==0 )
		{
			return ""+discount/10;
		}
		else
		{
			return ""+(double)discount/10;
		}
	}
	
	public void setDiscount(int discount)
	{
		this.discount = discount;
	}
	public int getIsUsedAfterBuy()
	{
		return isUsedAfterBuy;
	}
	public void setIsUsedAfterBuy(int isUsedAfterBuy)
	{
		this.isUsedAfterBuy = isUsedAfterBuy;
	}
	public int getCommodityTotal()
	{
		return commodityTotal;
	}
	public void setCommodityTotal(int commodityTotal)
	{
		this.commodityTotal = commodityTotal;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public int getBuyMode()
	{
		return buyMode;
	}
	public void setBuyMode(int buyMode)
	{
		this.buyMode = buyMode;
	}
	
	/**
	 * ��������ۿ۵ĵ�ǰ�۸�
	 * @return
	 */
	public int getCurPrice( int user_discount )
	{
		double cur_price = originalPrice;
		
		if( discount==-1 )//���Ǵ�����Ʒ
		{
			cur_price = originalPrice;
		}
		else
		{
			cur_price = originalPrice*discount/100;
		}
		
		cur_price = cur_price*user_discount/100;
		
		return (int) Math.round(cur_price);
	}
	
	public int getSellNum()
	{
		return sellNum;
	}
	public void setSellNum(int sellNum)
	{
		this.sellNum = sellNum;
	}
	public int getIsHot()
	{
		return isHot;
	}
	public void setIsHot(int isHot)
	{
		this.isHot = isHot;
	}
	public int getState()
	{
		return state;
	}
	public void setState(int state)
	{
		this.state = state;
	}
	public Date getDatetime()
	{
		return datetime;
	}
	public void setDatetime(Date datetime)
	{
		this.datetime = datetime;
	}
	public int getPropId()
	{
		return propId;
	}
	public void setPropId(int propId)
	{
		this.propId = propId;
	}
	public String getPropName()
	{
		return propName;
	}
	public void setPropName(String propName)
	{
		this.propName = propName;
	}

	public int getOriginalPrice()
	{
		return originalPrice;
	}

	public void setOriginalPrice(int originalPrice)
	{
		this.originalPrice = originalPrice;
	}

	public PropVO getProp()
	{
		return PropCache.getPropById(this.propId);
	}


	public int getIsVip()
	{
		return isVip;
	}

	public void setIsVip(int isVip)
	{
		this.isVip = isVip;
	}

}
