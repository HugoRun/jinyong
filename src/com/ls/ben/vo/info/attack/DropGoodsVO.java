/**
 * 
 */
package com.ls.ben.vo.info.attack;

/**
 * 功能:
 * @author 刘帅
 *
 * 9:23:53 AM
 */
public class DropGoodsVO {
	
	/**ID */
	private int dPk;
	/**角色id*/
	private int pPk;
	/**掉落数量*/
	private int dropNum;
	/**物品id*/
	private int goodsId;
	/**物品名字*/
	private String goodsName;
	/**物品类型*/
	private int goodsType;
	/**掉落物品的品质 0表示普通，1表示优秀，2表示良，3表示极品*/
	private int goodsQuality;
	/** 掉落的是否为非常重要的物品 */
	private int goodsImportance;
	/** 掉落信息 */
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
