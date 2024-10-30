package com.ls.ben.vo.storage;

/**
 * 
 * 功能:仓库VO
 * @author 张俊俊
 * 10:26:18 AM
 */
public class WareHouseVO {

	/** 仓库id */
	private int uwId;
	/** 创建人员Id */
	private int uPk;
	/** 角色Id */
	private int pPk;
	/** 道具的id */
	private int uwPropId;
	/** 仓库的分类，和包裹中的物品分类一样，另外6是金钱，7是宠物。 */
	private int uwType;
	/** 道具的分类，是它们在各自表中的分类  */
	private int uwPropType;
	/**仓库格数*/
	private int uwNumber;	
	/** 仓库物品*/
	private String uwArticle;
	/** 道具的数量 */
	private int uwPropNumber;
	/** 金钱仓库上限*/
	private String uwMoney;	
	/** 宠物仓库数量 */
	private String uwPet;
	/** 金钱仓库*/
	private String uwMoneyNumber;
	/** 宠物仓库*/
	private int uwPetNumber; 		
	/** 仓库剩余容量 */
	private int uwWareHouseSpare;
	/** 创建时间 */
	private String create_time;
	
	
	/** 道具绑定 */
	private int uw_bonding;
	/** 道具保护 */
	private int uw_protect;
	/** 二次确定 */
	private int uwIsReconfirm;
	/** 道具是否可用 */
	private int propUseControl;
	
	/** 道具特殊功能字段1 ,仅为仓库所用, 与jygame库中的不同*/
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
