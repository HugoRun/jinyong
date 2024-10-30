package com.ben.vo.task;

import com.ls.ben.cache.staticcache.task.TaskCache;

/**
 * ����:
 * 
 * @author ��˧ 2:56:49 PM
 */
public class UTaskVO {
	/** ����id */
	private int tPk;
	/** ��ɫid */
	private int pPk;
	/** ��ɫ���� */
	private String pName;
	/** ������ */
	private String tZu;
	/** �������� */
	private String tPx;
	/** �����ID */
	private int tId;
	/** ����ı��� */
	private String tTitle;
	/** �������� */
	private int tType;
	/** ��һ������ʼnpc��id */
	private int tXrwnpcId;
	/** ��һ������id */
	private int tNext;
	
	
	/** �м�� ���ж���м��Ӧ�ã����� */
	private String tPoint;
	/** �Ƿ�����м���ID�ã����� */
	private String tPointNo;
	
	
	
	/** ͨ���м����Ҫ����Ʒ */
	private String tZjdwp;
	/** ͨ���м����Ҫ��Ʒ������ */
	private int tZjdwpNumber;
	/** �����Ʒ���� */
	private int tZjdwpOk;
	
	
	/** ͨ���м����Ҫ��װ�� */
	private String tZjdzb;
	/** ͨ���м����Ҫװ�������� */
	private int tZjdzbNumber;
	/** �����Ʒ���� */
	private int tZjdzbOk;
	
	
	
	/** ͨ���м���Ƿ�ɾ����Ʒ */
	private int tDjscwp;
	/** ͨ���м���Ƿ�ɾ��װ�� */
	private int tDjsczb;
	/**ͨ���м�������Ʒ*/
	private String tMidstGs;
	/**ͨ���м�����װ��*/
	private String tMidstZb;
	
	/** ���������Ҫ��Ʒ */
	private String tGoods;
	/** ���������Ҫ��Ʒ���� */
	private String tGoodsNo;
	/** �����Ʒ���� */
	private String tGoodsOk;
	/** ���������Ҫװ�� */
	private String tGoodszb;
	/** ���������Ҫװ������ */
	private String tGoodszbNumber;
	/** ���װ������ */
	private String tGoodszbOk;
	/** ���������Ҫ��ɱ¾ */
	private String tKilling;
	/** ���������Ҫ��ɱ¾���� */
	private int tKillingNo;
	/** ���ɱ¾���� */
	private int tKillingOk;
	/** ���������Ҫ���� */
	private int tPet;
	/** ���������Ҫ�������� */
	private int tPetNumber;
	/** ��ɳ������� */
	private int tPetOk;
	/** ������ȡʱ�� */
	private String createTime;
	/** ����ʱ�䣨���ӣ� */
	private int tTime;
	/**�Ƿ�Ϊ�������� 0û�з��� 1����*/
	private int tGiveUp;
	/**��һ�������ID*/
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
