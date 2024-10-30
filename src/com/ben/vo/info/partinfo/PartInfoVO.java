/**
 * 
 */
package com.ben.vo.info.partinfo;

import java.util.Date;

/**
 * @author 侯浩军
 * 
 * 7:51:14 PM
 */
public class PartInfoVO {

	public static final int P_GRADE = 1;// 等级
	public static final int P_HP = 230;// 生命值
	public static final int P_MP = 70;// 法力值
	public static final int P_FORCE = 12;// 力
	public static final int P_AGILE = 10;// 敏
	public static final int P_PHYSIQUE = 15;// 体魄
	public static final int P_SAVVY = 10;// 悟性
	public static final int P_GJ = 37;// 攻击
	public static final int P_FY = 28;// 防御

	/** 角色id */
	private int pPk;
	/** 创建人员信息id */
	private int uPk;
	/** 角色名 */
	private String pName;
	/** 性别 */
	private int pSex;
	/** 等级 */
	private int pGrade;
	/** 生命值 */
	private int pUpHp;
	/** 当前HP值 */
	private int pHp;
	/** 生命值 */
	private int pUpMp;
	/** 法力值 */
	private int pMp;
	/** 攻击 */
	private int pGj;
	/** 防御 */
	private int pFy;
	/** 最小攻击 */
	private int pZbgjXiao;
	/** 最大攻击 */
	private int pZbgjDa;
	/** 最小防御 */
	private int pZbfyXiao;
	/** 最大防御 */
	private int pZbfyDa;
	/** 师徒1师傅2徒弟 */
	private int pTeacherType;
	/** 师傅的名称id */
	private int pTeacher;
	/** 是否已婚 1没结婚 2 结婚 */
	private int pHarness;
	/** 伴侣ID */
	private int pFere;
	/**种族1妖2巫**/
	private int pRace;
	/** 经验 */
	private String pExperience;
	/** 下经验 */
	private String pXiaExperience;
	/**本级经验*/
	private int pBenJiExp;
	/** 铜钱 */
	private String pCopper;
	/** pk值 */
	private int pPkValue;
	/** 开关1关2开 */
	private int pPks;
	/** 所在地图坐标 */
	private String pMap;
	/** 是否组队0无1组 */
	private int pProcession;
	/** 队伍编号 */
	private String pProcessionNumner;
	/** 创建时间 */
	private String createTime;
	/** 包裹容量 */
	private int pWrapContent;
	/** 删除标志 */
	private int deleteFlag;
	/**  删除时间 */
	private String deleteTime;

	/** *****人物暴击率********** */
	private double pDropMultiple;
	
	
	/**作为师父的等级*/
	private int te_level;
	
	/**上一次传功时间*/
	private String chuangong;
	
	/**杀人数量*/
	private int killNum;
	
	//最后一次招徒或者拜师的时间
	private Date last_shoutu_time;
	public Date getLast_shoutu_time()
	{
		return last_shoutu_time;
	}

	public void setLast_shoutu_time(Date last_shoutu_time)
	{
		this.last_shoutu_time = last_shoutu_time;
	}

	public String getChuangong()
	{
		return chuangong;
	}

	public void setChuangong(String chuangong)
	{
		this.chuangong = chuangong;
	}

	public int getTe_level()
	{
		return te_level;
	}

	public void setTe_level(int te_level)
	{
		this.te_level = te_level;
	}
	
	
	public double getPDropMultiple()
	{
		return pDropMultiple;
	}

	public void setPDropMultiple(double dropMultiple)
	{
		pDropMultiple = dropMultiple;
	}

	public int getDeleteFlag()
	{
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag)
	{
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteTime()
	{
		return deleteTime;
	}

	public void setDeleteTime(String deleteTime)
	{
		this.deleteTime = deleteTime;
	}

	public int getPWrapContent() {
		return pWrapContent;
	}

	public void setPWrapContent(int wrapContent) {
		pWrapContent = wrapContent;
	}

	public int getPUpHp() {
		return pUpHp;
	}

	public void setPUpHp(int upHp) {
		pUpHp = upHp;
	}

	public int getPUpMp() {
		return pUpMp;
	}

	public void setPUpMp(int upMp) {
		pUpMp = upMp;
	}


	public int getPProcession() {
		return pProcession;
	}

	public void setPProcession(int procession) {
		pProcession = procession;
	}

	public String getPProcessionNumner() {
		return pProcessionNumner;
	}

	public void setPProcessionNumner(String processionNumner) {
		pProcessionNumner = processionNumner;
	}

	public int getPZbgjXiao() {
		return pZbgjXiao;
	}

	public void setPZbgjXiao(int zbgjXiao) {
		pZbgjXiao = zbgjXiao;
	}

	public int getPZbgjDa() {
		return pZbgjDa;
	}

	public void setPZbgjDa(int zbgjDa) {
		pZbgjDa = zbgjDa;
	}

	public int getPZbfyXiao() {
		return pZbfyXiao;
	}

	public void setPZbfyXiao(int zbfyXiao) {
		pZbfyXiao = zbfyXiao;
	}

	public int getPZbfyDa() {
		return pZbfyDa;
	}

	public void setPZbfyDa(int zbfyDa) {
		pZbfyDa = zbfyDa;
	}

	public int getPHarness() {
		return pHarness;
	}

	public void setPHarness(int harness) {
		pHarness = harness;
	}

	public int getPFere() {
		return pFere;
	}

	public void setPFere(int fere) {
		pFere = fere;
	}

	public String getPCopper() {
		return pCopper;
	}

	public void setPCopper(String copper) {
		pCopper = copper;
	}

	public int getPPk() {
		return pPk;
	}

	public void setPPk(int pk) {
		pPk = pk;
	}

	public int getUPk() {
		return uPk;
	}

	public void setUPk(int pk) {
		uPk = pk;
	}

	public String getPName() {
		return pName;
	}

	public void setPName(String name) {
		pName = name;
	}

	public int getPSex() {
		return pSex;
	}

	public void setPSex(int sex) {
		pSex = sex;
	}

	public int getPGrade() {
		return pGrade;
	}

	public void setPGrade(int grade) {
		pGrade = grade;
	}

	public int getPHp() {
		return pHp;
	}

	public void setPHp(int hp) {
		pHp = hp;
	}

	public int getPMp() {
		return pMp;
	}

	public void setPMp(int mp) {
		pMp = mp;
	}


	public int getPGj() {
		return pGj;
	}

	public void setPGj(int gj) {
		pGj = gj;
	}

	public int getPFy() {
		return pFy;
	}

	public void setPFy(int fy) {
		pFy = fy;
	}

	public int getPTeacherType() {
		return pTeacherType;
	}

	public void setPTeacherType(int teacherType) {
		pTeacherType = teacherType;
	}

	public int getPTeacher() {
		return pTeacher;
	}

	public void setPTeacher(int teacher) {
		pTeacher = teacher;
	}

	public String getPExperience() {
		return pExperience;
	}

	public void setPExperience(String experience) {
		pExperience = experience;
	}

	public String getPXiaExperience() {
		return pXiaExperience;
	}

	public void setPXiaExperience(String xiaExperience) {
		pXiaExperience = xiaExperience;
	}


	public int getPPkValue() {
		return pPkValue;
	}

	public void setPPkValue(int pkValue) {
		pPkValue = pkValue;
	}

	public int getPPks() {
		return pPks;
	}

	public void setPPks(int pks) {
		pPks = pks;
	}

	public String getPMap() {
		return pMap;
	}

	public void setPMap(String map) {
		pMap = map;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public static int getP_GRADE() {
		return P_GRADE;
	}

	public static int getP_HP() {
		return P_HP;
	}

	public static int getP_MP() {
		return P_MP;
	}

	public static int getP_FORCE() {
		return P_FORCE;
	}

	public static int getP_AGILE() {
		return P_AGILE;
	}

	public static int getP_PHYSIQUE() {
		return P_PHYSIQUE;
	}

	public static int getP_SAVVY() {
		return P_SAVVY;
	}

	public static int getP_GJ() {
		return P_GJ;
	}

	public static int getP_FY() {
		return P_FY;
	}

	public int getPBenJiExp()
	{
		return pBenJiExp;
	}

	public void setPBenJiExp(int benJiExp)
	{
		pBenJiExp = benJiExp;
	}

	public int getKillNum()
	{
		return killNum;
	}

	public void setKillNum(int killNum)
	{
		this.killNum = killNum;
	}

	public int getPRace()
	{
		return pRace;
	}

	public void setPRace(int race)
	{
		pRace = race;
	}




}
