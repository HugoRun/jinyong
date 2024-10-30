
package com.ls.ben.vo.goods.prop;

import com.ls.pub.config.GameConfig;


public class PropVO {
	/** 道具表 */
	private int propID;
	/** 名称 */
	private String propName;

	/** 类型不同物品类型使用有不同的功能，详情请参照《金庸群侠传物品》 */
	private int propClass;

	/** 使用等级 使用该物品要求角色等级 */
	private String propReLevel;

	
	
	/** 绑定 0是不绑定，1是拾取绑定，2是装备绑定 */
	private int propBonding;
	/** 可否堆叠 1表示可以重叠，0表示不可重叠，重叠数量最多20 */
	private int propAccumulate;
	/** 说明 物品描述 */
	private String propDisplay;
	/** 卖出价 物品卖给系统的价格 */
	private int propSell;
	/** 职业（可以是通用的） 职业id，只有该职业的角色才可使用该物品，可多个职业 */
	private String propJob;
	/** 使用后是否删除 */
	private String propDrop;
	/** 每天使用次数 角色每天使用该物品的次数限制 */
	private int propUsedegree;
	/** 性别 使用该物品的性别限制，男为1，女为2，无要求为0 */
	private int propSex;
	/** 保护 0是不保护，1是保护（保护属性待定） */
	private int propProtect;
	/** 图片 填写图片code */
	private String propPic;
	/*结婚要求,1为是，2为不要求*/
	private int marriage;
	
	/**特殊字节1*/
	private String propOperate1;

	/**特殊字节2*/
	private String propOperate2;
	
	/**特殊字节3*/
	private String propOperate3;
	
	/**存储位置,*/
	private int propPosition;
	
	/**存储位置,在拍卖场的存储位置*/
	private int propAuctionPosition;
	
	/**二次确认标志，0表示不用二次确认，1表示需要二次确认*/
	private int propReconfirm;
	
	 /**控制道具是否可用，0表示不受限制，1表示战斗时不可用*/
	private int propUseControl;

	/**
	 * 得到num个道具需要的包裹空间
	 * @return
	 */
	public int getNeedSpace(int num)
	{
		if( num<=0 )
		{
			return 0;
		}
		int need_space = (num+propAccumulate-1)/propAccumulate;
		return need_space;
	}
	
	/**
	 * 状态描述
	 * @return
	 */
	public String getStatusDisplay()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("物品状态:");
		if( this.getPropBonding()==1)
		{
			sb.append("不可交易");
		}
		else
		{
			sb.append("可以交易");
		}
		
		sb.append(",");
		
		if( this.getPropProtect()==1 )
		{
			sb.append("不可丢弃");
		}
		else
		{
			sb.append("可以丢弃");
		}
		sb.append("<br/>");
		return sb.toString();
	}
	
	/**
	 * 得到图片显示完整路径
	 * @return
	 */
	public String getPicDisplay()
	{
		if( propPic!=null && !propPic.equals(""))
		{
			return "<img src=\""+GameConfig.getContextPath()+"/image/item/prop/"+propPic+".png\" alt=\"\"/><br/>";
		}
		return "";
	}
	
	
	public int getPropID() {
		return propID;
	}

	public void setPropID(int propID) {
		this.propID = propID;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}
	
	
	
	public int getPropClass() {
		return propClass;
	}

	public void setPropClass(int propClass) {
		this.propClass = propClass;
	}
	
	
	
	public String getPropReLevel() {
		return propReLevel;
	}

	public void setPropReLevel(String propReLevel) {
		this.propReLevel = propReLevel;
	}


	public int getMarriage() {
		return marriage;
	}

	public void setMarriage(int marriage) {
		this.marriage = marriage;
	}

	public int getPropBonding() {
		return propBonding;
	}

	public void setPropBonding(int propBonding) {
		this.propBonding = propBonding;
	}

	public int getPropAccumulate() {
		return propAccumulate;
	}

	public void setPropAccumulate(int propAccumulate) {
		this.propAccumulate = propAccumulate;
	}

	public String getPropDisplay() {
		return propDisplay;
	}

	public void setPropDisplay(String propDisplay) {
		this.propDisplay = propDisplay;
	}

	public int getPropSell() {
		return propSell;
	}

	public void setPropSell(int propSell) {
		this.propSell = propSell;
	}

	public String getPropJob() {
		return propJob;
	}

	public void setPropJob(String propJob) {
		this.propJob = propJob;
	}

	public String getPropDrop() {
		return propDrop;
	}

	public void setPropDrop(String propDrop) {
		this.propDrop = propDrop;
	}

	public int getPropUsedegree() {
		return propUsedegree;
	}

	public void setPropUsedegree(int propUsedegree) {
		this.propUsedegree = propUsedegree;
	}

	public int getPropSex() {
		return propSex;
	}

	public void setPropSex(int propSex) {
		this.propSex = propSex;
	}

	public int getPropProtect() {
		return propProtect;
	}

	public void setPropProtect(int propProtect) {
		this.propProtect = propProtect;
	}

	public String getPropPic() {
		return propPic;
	}

	public void setPropPic(String propPic) {
		this.propPic = propPic;
	}

	public String getPropOperate1()
	{
		return propOperate1;
	}

	public void setPropOperate1(String propOperate1)
	{
		this.propOperate1 = propOperate1;
	}

	public String getPropOperate2()
	{
		return propOperate2;
	}

	public void setPropOperate2(String propOperate2)
	{
		this.propOperate2 = propOperate2;
	}

	public String getPropOperate3()
	{
		return propOperate3;
	}

	public void setPropOperate3(String propOperate3)
	{
		this.propOperate3 = propOperate3;
	}

	public int getPropPosition()
	{
		return propPosition;
	}

	public void setPropPosition(int propPosition)
	{
		this.propPosition = propPosition;
	}

	public int getPropReconfirm()
	{
		return propReconfirm;
	}

	public void setPropReconfirm(int propReconfirm)
	{
		this.propReconfirm = propReconfirm;
	}

	public int getPropAuctionPosition()
	{
		return propAuctionPosition;
	}

	public void setPropAuctionPosition(int propAuctionPosition)
	{
		this.propAuctionPosition = propAuctionPosition;
	}

	public int getPropUseControl()
	{
		return propUseControl;
	}

	public void setPropUseControl(int propUseControl)
	{
		this.propUseControl = propUseControl;
	}

}
