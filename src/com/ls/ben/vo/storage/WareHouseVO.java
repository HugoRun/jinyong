package com.ls.ben.vo.storage;

/**
 * 
 * ����:�ֿ�VO
 * @author �ſ���
 * 10:26:18 AM
 */
public class WareHouseVO {

	/** �ֿ�id */
	private int uwId;
	/** ������ԱId */
	private int uPk;
	/** ��ɫId */
	private int pPk;
	/** ���ߵ�id */
	private int uwPropId;
	/** �ֿ�ķ��࣬�Ͱ����е���Ʒ����һ��������6�ǽ�Ǯ��7�ǳ�� */
	private int uwType;
	/** ���ߵķ��࣬�������ڸ��Ա��еķ���  */
	private int uwPropType;
	/**�ֿ����*/
	private int uwNumber;	
	/** �ֿ���Ʒ*/
	private String uwArticle;
	/** ���ߵ����� */
	private int uwPropNumber;
	/** ��Ǯ�ֿ�����*/
	private String uwMoney;	
	/** ����ֿ����� */
	private String uwPet;
	/** ��Ǯ�ֿ�*/
	private String uwMoneyNumber;
	/** ����ֿ�*/
	private int uwPetNumber; 		
	/** �ֿ�ʣ������ */
	private int uwWareHouseSpare;
	/** ����ʱ�� */
	private String create_time;
	
	
	/** ���߰� */
	private int uw_bonding;
	/** ���߱��� */
	private int uw_protect;
	/** ����ȷ�� */
	private int uwIsReconfirm;
	/** �����Ƿ���� */
	private int propUseControl;
	
	/** �������⹦���ֶ�1 ,��Ϊ�ֿ�����, ��jygame���еĲ�ͬ*/
	private String propOperate1;
	
	
	public String getPropOperate1()
	{
		return propOperate1;
	}
	public void setPropOperate1(String propOperate1)
	{
		this.propOperate1 = propOperate1;
	}
	public int getUw_bonding()
	{
		return uw_bonding;
	}
	public void setUw_bonding(int uw_bonding)
	{
		this.uw_bonding = uw_bonding;
	}
	public int getUw_protect()
	{
		return uw_protect;
	}
	public void setUw_protect(int uw_protect)
	{
		this.uw_protect = uw_protect;
	}
	public int getUwIsReconfirm()
	{
		return uwIsReconfirm;
	}
	public void setUwIsReconfirm(int uwIsReconfirm)
	{
		this.uwIsReconfirm = uwIsReconfirm;
	}
	public int getPropUseControl()
	{
		return propUseControl;
	}
	public void setPropUseControl(int propUseControl)
	{
		this.propUseControl = propUseControl;
	}
	public int getUwWareHouseSpare() {
		return uwWareHouseSpare;
	}
	public void setUwWareHouseSpare(int uwWareHouseSpare) {
		this.uwWareHouseSpare = uwWareHouseSpare;
	}
	public int getUwId() {
		return uwId;
	}
	public void setUwId(int uwId) {
		this.uwId = uwId;
	}
	public int getUPk() {
		return uPk;
	}
	public void setUPk(int pk) {
		uPk = pk;
	}
	public int getPPk() {
		return pPk;
	}
	public void setPPk(int pk) {
		pPk = pk;
	}
	public int getUwType() {
		return uwType;
	}
	public void setUwType(int uwType) {
		this.uwType = uwType;
	}
	public int getUwNumber() {
		return uwNumber;
	}
	public void setUwNumber(int uwNumber) {
		this.uwNumber = uwNumber;
	}
	public String getUwArticle() {
		return uwArticle;
	}
	public void setUwArticle(String uwArticle) {
		this.uwArticle = uwArticle;
	}
	
	public String getUwMoney() {
		return uwMoney;
	}
	public void setUwMoney(String uwMoney) {
		this.uwMoney = uwMoney;
	}
	public String getUwPet() {
		return uwPet;
	}
	public void setUwPet(String uwPet) {
		this.uwPet = uwPet;
	}
	public String getUwMoneyNumber() {
		return uwMoneyNumber;
	}
	public void setUwMoneyNumber(String uwMoneyNumber) {
		this.uwMoneyNumber = uwMoneyNumber;
	}
	public int getUwPetNumber() {
		return uwPetNumber;
	}
	public void setUwPetNumber(int uwPetNumber) {
		this.uwPetNumber = uwPetNumber;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public int getUwPropId() {
		return uwPropId;
	}
	public void setUwPropId(int uwPropId) {
		this.uwPropId = uwPropId;
	}
	public int getUwPropType() {
		return uwPropType;
	}
	public void setUwPropType(int uwPropType) {
		this.uwPropType = uwPropType;
	}
	public int getUwPropNumber() {
		return uwPropNumber;
	}
	public void setUwPropNumber(int uwPropNumber) {
		this.uwPropNumber = uwPropNumber;
	}


	
}
