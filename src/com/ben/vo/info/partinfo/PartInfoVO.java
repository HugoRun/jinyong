/**
 * 
 */
package com.ben.vo.info.partinfo;

import java.util.Date;

/**
 * @author ��ƾ�
 * 
 * 7:51:14 PM
 */
public class PartInfoVO {

	public static final int P_GRADE = 1;// �ȼ�
	public static final int P_HP = 230;// ����ֵ
	public static final int P_MP = 70;// ����ֵ
	public static final int P_FORCE = 12;// ��
	public static final int P_AGILE = 10;// ��
	public static final int P_PHYSIQUE = 15;// ����
	public static final int P_SAVVY = 10;// ����
	public static final int P_GJ = 37;// ����
	public static final int P_FY = 28;// ����

	/** ��ɫid */
	private int pPk;
	/** ������Ա��Ϣid */
	private int uPk;
	/** ��ɫ�� */
	private String pName;
	/** �Ա� */
	private int pSex;
	/** �ȼ� */
	private int pGrade;
	/** ����ֵ */
	private int pUpHp;
	/** ��ǰHPֵ */
	private int pHp;
	/** ����ֵ */
	private int pUpMp;
	/** ����ֵ */
	private int pMp;
	/** ���� */
	private int pGj;
	/** ���� */
	private int pFy;
	/** ��С���� */
	private int pZbgjXiao;
	/** ��󹥻� */
	private int pZbgjDa;
	/** ��С���� */
	private int pZbfyXiao;
	/** ������ */
	private int pZbfyDa;
	/** ʦͽ1ʦ��2ͽ�� */
	private int pTeacherType;
	/** ʦ��������id */
	private int pTeacher;
	/** �Ƿ��ѻ� 1û��� 2 ��� */
	private int pHarness;
	/** ����ID */
	private int pFere;
	/**����1��2��**/
	private int pRace;
	/** ���� */
	private String pExperience;
	/** �¾��� */
	private String pXiaExperience;
	/**��������*/
	private int pBenJiExp;
	/** ͭǮ */
	private String pCopper;
	/** pkֵ */
	private int pPkValue;
	/** ����1��2�� */
	private int pPks;
	/** ���ڵ�ͼ���� */
	private String pMap;
	/** �Ƿ����0��1�� */
	private int pProcession;
	/** ������ */
	private String pProcessionNumner;
	/** ����ʱ�� */
	private String createTime;
	/** �������� */
	private int pWrapContent;
	/** ɾ����־ */
	private int deleteFlag;
	/**  ɾ��ʱ�� */
	private String deleteTime;

	/** *****���ﱩ����********** */
	private double pDropMultiple;
	
	
	/**��Ϊʦ���ĵȼ�*/
	private int te_level;
	
	/**��һ�δ���ʱ��*/
	private String chuangong;
	
	/**ɱ������*/
	private int killNum;
	
	//���һ����ͽ���߰�ʦ��ʱ��
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
