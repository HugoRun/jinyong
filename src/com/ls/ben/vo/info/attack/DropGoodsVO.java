/**
 * 
 */
package com.ls.ben.vo.info.attack;

/**
 * ����:
 * @author ��˧
 *
 * 9:23:53 AM
 */
public class DropGoodsVO {
	
	/**ID */
	private int dPk;
	/**��ɫid*/
	private int pPk;
	/**��������*/
	private int dropNum;
	/**��Ʒid*/
	private int goodsId;
	/**��Ʒ����*/
	private String goodsName;
	/**��Ʒ����*/
	private int goodsType;
	/**������Ʒ��Ʒ�� 0��ʾ��ͨ��1��ʾ���㣬2��ʾ����3��ʾ��Ʒ*/
	private int goodsQuality;
	/** ������Ƿ�Ϊ�ǳ���Ҫ����Ʒ */
	private int goodsImportance;
	/** ������Ϣ */
	private String dropGoodsInfo;
	
	public String getDropGoodsInfo()
	{
		return dropGoodsInfo;
	}
	public void setDropGoodsInfo(String dropGoodsInfo)
	{
		this.dropGoodsInfo = dropGoodsInfo;
	}
	public int getGoodsImportance()
	{
		return goodsImportance;
	}
	public void setGoodsImportance(int goodsImportance)
	{
		this.goodsImportance = goodsImportance;
	}
	public int getDPk() {
		return dPk;
	}
	public void setDPk(int pk) {
		dPk = pk;
	}
	public int getPPk() {
		return pPk;
	}
	public void setPPk(int pk) {
		pPk = pk;
	}

	public int getDropNum() {
		return dropNum;
	}
	public void setDropNum(int dropNum) {
		this.dropNum = dropNum;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}
	public int getGoodsQuality()
	{
		return goodsQuality;
	}
	public void setGoodsQuality(int goodsQuality)
	{
		this.goodsQuality = goodsQuality;
	}
	
	

}
