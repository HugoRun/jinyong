/**
 * 
 */
package com.pm.vo.tongsiege;

/**
 * ���ɹ���pk��¼��
 * @author zhangjj
 *
 */
public class TongSiegePkLogVO
{
	
	/** ���ɹ���սɱ�˼�¼��ID  */
	public int pklogId;
	/*** ����սID,������ĳ�����еĹ���  **/
	public int siegeId	;
	/**   siege_id������Ĺ���ս�ڼ���ս��   ***/
	public int 	siegeNumber ;
		/**   ����ID  */
	public int 	tongId	;
		/**   ��ɫid */
	public int 	pPk;
		/**   �ڴ˴ι���ս����ɱ����  **/
	public int 	pkNumber	;
	
	/** �ڴ˴ι���ս�л�õ�����  */
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
