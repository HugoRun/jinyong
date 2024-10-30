/**
 * 
 */
package com.pm.vo.tongsiege;

/**
 * 帮派攻城pk记录表
 * @author zhangjj
 *
 */
public class TongSiegePkLogVO
{
	
	/** 帮派攻城战杀人记录表ID  */
	public int pklogId;
	/*** 攻城战ID,代表着某个城市的攻城  **/
	public int siegeId	;
	/**   siege_id所代表的攻城战第几次战斗   ***/
	public int 	siegeNumber ;
		/**   帮派ID  */
	public int 	tongId	;
		/**   角色id */
	public int 	pPk;
		/**   在此次攻城战伤所杀人数  **/
	public int 	pkNumber	;
	
	/** 在此次攻城战中获得的荣誉  */
	public int pkGlory;
	
		
	public int getPkGlory()
	{
		return pkGlory;
	}
	public void setPkGlory(int pkGlory)
	{
		this.pkGlory = pkGlory;
	}
		public int getPklogId()
		{
			return pklogId;
		}
		public void setPklogId(int pklogId)
		{
			this.pklogId = pklogId;
		}
		public int getSiegeId()
		{
			return siegeId;
		}
		public void setSiegeId(int siegeId)
		{
			this.siegeId = siegeId;
		}
		public int getSiegeNumber()
		{
			return siegeNumber;
		}
		public void setSiegeNumber(int siegeNumber)
		{
			this.siegeNumber = siegeNumber;
		}
		public int getTongId()
		{
			return tongId;
		}
		public void setTongId(int tongId)
		{
			this.tongId = tongId;
		}
		public int getPPk()
		{
			return pPk;
		}
		public void setPPk(int pk)
		{
			pPk = pk;
		}
		public int getPkNumber()
		{
			return pkNumber;
		}
		public void setPkNumber(int pkNumber)
		{
			this.pkNumber = pkNumber;
		}
	
	
	
	
	
}
