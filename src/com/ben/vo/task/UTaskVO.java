package com.ben.vo.task;

import com.ls.ben.cache.staticcache.task.TaskCache;

/**
 * 功能:
 * 
 * @author 刘帅 2:56:49 PM
 */
public class UTaskVO {
	/** 任务id */
	private int tPk;
	/** 角色id */
	private int pPk;
	/** 角色名称 */
	private String pName;
	/** 任务组 */
	private String tZu;
	/** 任务排序 */
	private String tPx;
	/** 任务的ID */
	private int tId;
	/** 任务的标题 */
	private String tTitle;
	/** 任务类型 */
	private int tType;
	/** 下一步任务开始npc的id */
	private int tXrwnpcId;
	/** 下一个任务id */
	private int tNext;
	
	
	/** 中间点 如有多个中间点应用，隔开 */
	private String tPoint;
	/** 是否完成中间点的ID用，隔开 */
	private String tPointNo;
	
	
	
	/** 通过中间点需要的物品 */
	private String tZjdwp;
	/** 通过中间点需要物品的数量 */
	private int tZjdwpNumber;
	/** 完成物品数量 */
	private int tZjdwpOk;
	
	
	/** 通过中间点需要的装备 */
	private String tZjdzb;
	/** 通过中间点需要装备的数量 */
	private int tZjdzbNumber;
	/** 完成物品数量 */
	private int tZjdzbOk;
	
	
	
	/** 通过中间点是否删除物品 */
	private int tDjscwp;
	/** 通过中间点是否删除装备 */
	private int tDjsczb;
	/**通过中间点给的物品*/
	private String tMidstGs;
	/**通过中间点给的装备*/
	private String tMidstZb;
	
	/** 完成任务需要物品 */
	private String tGoods;
	/** 完成任务需要物品数量 */
	private String tGoodsNo;
	/** 完成物品数量 */
	private String tGoodsOk;
	/** 完成任务需要装备 */
	private String tGoodszb;
	/** 完成任务需要装备数量 */
	private String tGoodszbNumber;
	/** 完成装备数量 */
	private String tGoodszbOk;
	/** 完成任务需要的杀戮 */
	private String tKilling;
	/** 完成任务需要的杀戮数量 */
	private int tKillingNo;
	/** 完成杀戮数量 */
	private int tKillingOk;
	/** 完成任务需要宠物 */
	private int tPet;
	/** 完成任务需要宠物数量 */
	private int tPetNumber;
	/** 完成宠物数量 */
	private int tPetOk;
	/** 任务领取时间 */
	private String createTime;
	/** 任务时间（分钟） */
	private int tTime;
	/**是否为放弃任务 0没有放弃 1放弃*/
	private int tGiveUp;
	/**上一条任务的ID*/
	private int upTaskId;
	   
	
	public TaskVO getTaskInfo()
	{
		return TaskCache.getById(this.tId+"");
	}
	
	public int getTGiveUp()
	{
		return tGiveUp;
	}

	public void setTGiveUp(int giveUp)
	{
		tGiveUp = giveUp;
	}

	public int getUpTaskId()
	{
		return upTaskId;
	}

	public void setUpTaskId(int upTaskId)
	{
		this.upTaskId = upTaskId;
	}

	public String getTMidstGs()
	{
		if(tMidstGs == null || tMidstGs.equals("")){
			tMidstGs = "0";
		}
		return tMidstGs;
	}

	public void setTMidstGs(String midstGs)
	{
		tMidstGs = midstGs;
	}

	public String getTMidstZb()
	{
		if(tMidstZb == null || tMidstZb.equals("")){
			tMidstZb = "0";
		}
		return tMidstZb;
	}

	public void setTMidstZb(String midstZb)
	{
		tMidstZb = midstZb;
	}

	public String getTPoint() {
		if(tPoint == null || tPoint.equals("")){
			tPoint = "0";
		}
		return tPoint;
	}

	public void setTPoint(String point) {
		tPoint = point;
	}

	public String getTPointNo() {
		return tPointNo;
	}

	public void setTPointNo(String pointNo) {
		tPointNo = pointNo;
	}

	public String getTZjdwp() {
		if(tZjdwp == null || tZjdwp.equals("")){
			tZjdwp = "0";
		}
		return tZjdwp;
	}

	public void setTZjdwp(String zjdwp) {
		tZjdwp = zjdwp;
	}

	public int getTZjdwpNumber() {
		if(tZjdwpNumber == 0){
			tZjdwpNumber = 0;
		}
		return tZjdwpNumber;
	}

	public void setTZjdwpNumber(int zjdwpNumber) {
		tZjdwpNumber = zjdwpNumber;
	}

	public int getTZjdwpOk() {
		return tZjdwpOk;
	}

	public void setTZjdwpOk(int zjdwpOk) {
		tZjdwpOk = zjdwpOk;
	}

	public String getTZjdzb() {
		return tZjdzb;
	}

	public void setTZjdzb(String zjdzb) {
		tZjdzb = zjdzb;
	}

	public int getTZjdzbNumber() {
		return tZjdzbNumber;
	}

	public void setTZjdzbNumber(int zjdzbNumber) {
		tZjdzbNumber = zjdzbNumber;
	}

	public int getTZjdzbOk() {
		return tZjdzbOk;
	}

	public void setTZjdzbOk(int zjdzbOk) {
		tZjdzbOk = zjdzbOk;
	}

	public int getTDjscwp() { 
		return tDjscwp;
	}

	public void setTDjscwp(int djscwp) {
		tDjscwp = djscwp;
	}

	public int getTDjsczb() {
		return tDjsczb;
	}

	public void setTDjsczb(int djsczb) {
		tDjsczb = djsczb;
	}

	public String getTGoodszb() {
		if(tGoodszb == null || tGoodszb.equals("")){
			tGoodszb = "0";
		}
		return tGoodszb;
	}

	public void setTGoodszb(String goodszb) {
		tGoodszb = goodszb;
	}

	public String getTGoodszbNumber() {
		if(tGoodszbNumber == null || tGoodszbNumber.equals("")){
			tGoodszbNumber = "0";
		}
		return tGoodszbNumber;
	}

	public void setTGoodszbNumber(String goodszbNumber) {
		tGoodszbNumber = goodszbNumber;
	}

	public String getTGoodszbOk() {
		return tGoodszbOk;
	}

	public void setTGoodszbOk(String goodszbOk) {
		tGoodszbOk = goodszbOk;
	}

	public String getTGoodsOk() {
		return tGoodsOk;
	}

	public void setTGoodsOk(String goodsOk) {
		tGoodsOk = goodsOk;
	}

	public int getTKillingOk() {
		return tKillingOk;
	}

	public void setTKillingOk(int killingOk) {
		tKillingOk = killingOk;
	}

	public int getTPetOk() {
		return tPetOk;
	}

	public void setTPetOk(int petOk) {
		tPetOk = petOk;
	}

	public int getTXrwnpcId() {
		return tXrwnpcId;
	}

	public void setTXrwnpcId(int xrwnpcId) {
		tXrwnpcId = xrwnpcId;
	}

	public int getTNext() {
		return tNext;
	}

	public void setTNext(int next) {
		tNext = next;
	}

	public int getTType() {
		return tType;
	}

	public void setTType(int type) {
		tType = type;
	}

	public int getTPk() {
		return tPk;
	}

	public void setTPk(int pk) {
		tPk = pk;
	}

	public int getPPk() {
		return pPk;
	}

	public void setPPk(int pk) {
		pPk = pk;
	}

	public String getPName() {
		return pName;
	}

	public void setPName(String name) {
		pName = name;
	}

	public String getTZu() {
		return tZu;
	}

	public void setTZu(String zu) {
		tZu = zu;
	}

	public String getTPx() {
		return tPx;
	}

	public void setTPx(String px) {
		tPx = px;
	}

	public int getTId() {
		return tId;
	}

	public void setTId(int id) {
		tId = id;
	}

	public String getTTitle() {
		return tTitle;
	}

	public void setTTitle(String title) {
		tTitle = title;
	}

	public String getTGoods() {
		if(tGoods == null || tGoods.equals("")){
			tGoods = "0";
		}
		return tGoods;
	}

	public void setTGoods(String goods) {
		tGoods = goods;
	}

	public String getTGoodsNo() {
		if(tGoodsNo == null || tGoodsNo.equals("")){
			tGoodsNo = "0";
		}
		return tGoodsNo;
	}

	public void setTGoodsNo(String goodsNo) {
		tGoodsNo = goodsNo;
	}

	public String getTKilling() {
		if(tKilling == null || tKilling.equals("")){
			tKilling = "0";
		}
		return tKilling;
	}

	public void setTKilling(String killing) {
		tKilling = killing;
	}

	public int getTKillingNo() {
		return tKillingNo;
	}

	public void setTKillingNo(int killingNo) {
		tKillingNo = killingNo;
	}

	public int getTPet() {
		return tPet;
	}

	public void setTPet(int pet) {
		tPet = pet;
	}

	public int getTPetNumber() {
		return tPetNumber;
	}

	public void setTPetNumber(int petNumber) {
		tPetNumber = petNumber;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getTTime() {
		return tTime;
	}

	public void setTTime(int time) {
		tTime = time;
	}

}
