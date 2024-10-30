package com.ls.ben.vo.mall;

import java.util.Date;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;

/**
 * 功能：商品
 * @author ls
 * May 12, 2009
 * 3:53:09 PM
 */

public class CommodityVO
{
	/*1）	商品id(对应物品的id)
	 * 道具id
	2）	商品分类
	3）	交易方式（1为积分，2为元宝）
	4）	商品价钱（表示购买商品所需要的元宝或积分的数量）
	5）	卖出数量
	6）	是否是推荐商品（0（默认值）为推荐商品，1为推荐商品）
	7）	商品状态（1（默认值）为上线，0为下线）
	8）	上市时间
	discount  ,/**商品当前的折扣，表现形式为例如：80代表8折，默认为100表示不打折
    isUsedAfterBuy    int      default 1        ,/**购买后是否可直接使用
 	commodity_total   int      default -1       ,/**商品总数-1表示不限量*/

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
	private int isVip=0;//是否是会员商品，1是0否
	
	public String getDisplay()
	{
		return this.getProp().getPropDisplay();
	}
	
	/**
	 * 根据购买数量判断是否库存充足
	 */
	public boolean isEnough(int sell_num)
	{
		int store_num = getStoreNum();//库存
		if(  commodityTotal!=-1 && store_num-sell_num<0 )//限量且库存不足
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * 得到库存
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
	 * 折扣信息显示处理
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
	 * 玩家自身折扣的当前价格
	 * @return
	 */
	public int getCurPrice( int user_discount )
	{
		double cur_price = originalPrice;
		
		if( discount==-1 )//不是打折商品
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
