package com.ls.ben.vo.info.partinfo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.vo.map.SceneVO;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MathUtil;

public class PartInfoVO
{
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
	private int pHp;
	/** ����ֵ */
	private int pMp;

	/** ******************��������ֵ*********************** */
	private int gjSubjoin = 0;
	/** ******************��������ֵ*********************** */
	private int fySubjoin = 0;

	/** ��ɫ������ */
	private int pGj;
	/** ��ɫ������� */
	private int pFy;

	/** װ����󹥻� */
	private int pZbgjDa = 0;
	/** װ�������� */
	private int pZbfyDa = 0;
	/** װ����С���� */
	private int pZbgjXiao = 0;
	/** װ����С���� */
	private int pZbfyXiao = 0;

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
	/** �������� */
	private String pBjExperience;
	/** �¾��� */
	private String pXiaExperience;
	/** ͭǮ */
	private String pCopper;
	/** pkֵ */
	private int pPkValue;
	/** ����1��2�� */
	private int pPks;
	/**
	 * pk����ʱ��
	 */
	private Date pkChangeTime;

	/** ��ʶ�Ƿ�����������״̬��0��1�� */
	private int pIsInitiative;
	/** ��ʶ�Ƿ��ڱ�������״̬��0��1�� */
	private int pIsPassivity;

	/** ���ڵ�ͼ���� */
	private String pMap;
	/** ����ʱ�� */
	private String createTime;

	/** Ѫ���������� */
	private int pUpHp;
	/** mp�������� */
	private int pUpMp;

	/** �������� */
	private int pWrapContent;

	/** ������� */
	private PlayerWXVO wx = null;
	
	/** ��� */
	private int fId;//����id
	private int fJob;//����ְλ
	private int fContribute;//���ɹ���
	private Date fJoinTime;//������ɵ�ʱ��
	private String fTitle;//���ɳƺ�
	
	/** ���ӵ������ */
	private int appendDropProbability = 0;
	/** ���Ӳ������ */
	private int appendCatchProbability = 0;
	/** ����ӳ� */
	private int appendExp = 0;
	/** ��Ʒװ�������� */
	private int appendNonsuchProbability = 0;

	/** �Ƿ������ж� */
	private boolean isImmunityPoison;
	/** �Ƿ����߻��� */
	private boolean isImmunityDizzy;

	/** ���ӳ��ﾭ��ӳ� */
	private int appendPetExp;
	/** *****���ﱩ����********** */
	private double dropMultiple;

	/** ΪsceneId�����������ˣ�����������������,�����ʹ�����������ߺ���Ӧ�ûص��ĵص� */
	// private int shouldScene;
	/** ɾ����־ */
	private int deleteFlag;
	/** ɾ��ʱ�� */
	private Date deleteTime;

	/** ��Ϊʦ���ĵȼ� */
	private int te_level;

	/** ���һ�δ���ʱ�� */
	private String chuangong;

	// ���һ����ͽ���߰�ʦ��ʱ��
	private Date last_shoutu_time;

	// ����Ƿ�Ϊ����
	private int player_state_by_new;

	/***״̬����**/
	private String contentdisplay ="";
	
	private int loginState;//��½״̬��0�����ߣ�1����
	
	/**
	 * �õ��Ա���
	 */
	public String getSexName()
	{
		return ExchangeUtil.exchangeToSex(this.pSex);
	}
	
	/**
	 * ������صļ�����:(����,80��, 100����,�׳�) 
	 * @return
	 */
	public String getFDes()
	{
		if( fId<=0 )
			return "";
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(ExchangeUtil.getFJobName(this.fJob)).append(",");
		if( StringUtils.isEmpty(this.fTitle)==false)
		{
			sb.append(this.fTitle).append(",");
		}
		sb.append(this.pGrade).append("��").append(",");
		sb.append(this.fContribute).append("����").append(",");
		if( this.loginState==1 )//����
		{
			sb.append(SceneCache.getById(this.pMap).getSceneName());
		}
		else
		{
			sb.append("������");
		}
		sb.append(")");
		return sb.toString();
	}
	/**
	 * �����Ա�������������ﴫ�͵�ʱ���õ�
	 * @return String
	 */
	public String getDisplay()
	{
		if( fId<=0 )
			return "";
		StringBuffer sb = new StringBuffer();
		sb.append("��");
		sb.append(ExchangeUtil.getFJobName(this.fJob)).append("��");
		sb.append(this.getPName());
		sb.append("(");
		SceneVO sv= SceneCache.getById(this.getPMap());
		sb.append(sv.getSceneName());
		sb.append(")");
		return sb.toString();
	}
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

	public int getAppendPetExp()
	{
		return appendPetExp;
	}

	public void updatePUpHp(int add)
	{
		this.pUpHp = pUpHp + pUpHp * add / 100;
	}

	public void setAppendPetExp(int appendPetExp)
	{
		this.appendPetExp = appendPetExp;
	}



	/**
	 * �������С����֮�������������ֵ����װ������
	 * @return
	 */
	public int getGj()
	{
		int gj = MathUtil.getRandomBetweenXY(
				(int) (getPGj() + getPZbgjXiao()),
				(int) (getPGj() + getPZbgjDa()));
		int c = gj * gjSubjoin / 100;
		int s = c + gj;
		return s;
	}

	/**
	 * ����������С����֮�������������ֵ,����װ������
	 * @return
	 */
	public int getFy()
	{
		int fy = MathUtil.getRandomBetweenXY((getPFy() + getPZbfyXiao()),(getPFy() + getPZbfyDa()));
		int s = fy * fySubjoin / 100;
		int q = s + fy;
		return q;
	}

	
	/**
	 * �ܵĻ�������
	 * @return
	 */
	public int getBasicFy()
	{
		int fy = getPFy();
		return MathUtil.getIntegetValueByAddRate(fy, fySubjoin);
	}
	
	/**
	 * �����װ��������Сװ������֮�������������ֵ(�ܵ�װ������)
	 * @return
	 */
	public int getZbFy()
	{
		int zb_fy = MathUtil.getRandomBetweenXY(getPZbfyXiao(), getPZbfyDa());
		return MathUtil.getIntegetValueByAddRate(zb_fy, fySubjoin);
	}
	/**
	 * �����װ��������Сװ������֮�������������ֵ(�ܵ�װ������)
	 * @return
	 */
	public int getZbGj()
	{
		int zb_gj = MathUtil.getRandomBetweenXY(getPZbgjXiao(), getPZbgjDa());
		return MathUtil.getIntegetValueByAddRate(zb_gj, fySubjoin);
	}

	/**
	 * �ܵķ���С
	 * @return
	 */
	public int getFyXiao()
	{
		int fy_xiao = getPFy() + getPZbfyXiao();
		return MathUtil.getIntegetValueByAddRate(fy_xiao, fySubjoin);
	}
	
	/**
	 * �ܵķ�����
	 * @return
	 */
	public int getFyDa()
	{
		int fy_xiao = getPFy() + getPZbfyDa();
		return MathUtil.getIntegetValueByAddRate(fy_xiao, fySubjoin);
	}
	/**
	 * �ܵķ�����С
	 * @return
	 */
	public int getGjXiao()
	{
		int gj_xiao = getPGj() + this.getPZbgjXiao();
		return MathUtil.getIntegetValueByAddRate(gj_xiao, fySubjoin);
	}
	
	/**
	 * �ܵĹ�����
	 * @return
	 */
	public int getGjDa()
	{
		int gj_xiao = getPGj() + getPZbgjDa();
		return MathUtil.getIntegetValueByAddRate(gj_xiao, fySubjoin);
	}


	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getUPk()
	{
		return uPk;
	}

	public void setUPk(int pk)
	{
		uPk = pk;
	}

	public String getPName()
	{
		return pName;
	}

	public void setPName(String name)
	{
		pName = name;
	}

	public int getPSex()
	{
		return pSex;
	}

	public void setPSex(int sex)
	{

		pSex = sex;
	}

	public int getPGrade()
	{
		return pGrade;
	}

	public void setPGrade(int grade)
	{
		pGrade = grade;
	}

	public int getPHp()
	{
		if (pHp > getPMaxHp())
		{
			pHp = getPMaxHp();
		}
		return pHp;
	}

	/**
	 * �õ����ݿ���ȡ����ʵhp,��ʼ��basicInfo��
	 * 
	 * @return
	 */
	public int getHP()
	{
		return pHp;
	}

	public void setPHp(int hp)
	{
		pHp = hp;
	}

	public int getPMp()
	{
		if (pMp > getPMaxMp())
		{
			pMp = getPMaxMp();
		}
		return pMp;
	}

	public void setPMp(int mp)
	{
		pMp = mp;
	}


	public int getPGj()
	{
		return pGj;
	}

	public void setPGj(int gj)
	{
		pGj = gj;
	}

	public int getPFy()
	{
		return pFy;
	}

	public void setPFy(int fy)
	{
		pFy = fy;
	}

	public int getPZbgjDa()
	{
		return pZbgjDa;
	}

	public void setPZbgjDa(int zbgjDa)
	{
		pZbgjDa = zbgjDa;
	}

	public int getPZbfyDa()
	{
		return pZbfyDa;
	}

	public void setPZbfyDa(int zbfyDa)
	{
		pZbfyDa = zbfyDa;
	}

	public int getPZbgjXiao()
	{
		return pZbgjXiao;
	}

	public void setPZbgjXiao(int zbgjXiao)
	{
		pZbgjXiao = zbgjXiao;
	}

	public int getPZbfyXiao()
	{
		return pZbfyXiao;
	}

	public void setPZbfyXiao(int zbfyXiao)
	{
		pZbfyXiao = zbfyXiao;
	}

	public int getPTeacherType()
	{
		return pTeacherType;
	}

	public void setPTeacherType(int teacherType)
	{
		pTeacherType = teacherType;
	}

	public int getPTeacher()
	{
		return pTeacher;
	}

	public void setPTeacher(int teacher)
	{
		pTeacher = teacher;
	}

	public int getPHarness()
	{
		return pHarness;
	}

	public void setPHarness(int harness)
	{
		pHarness = harness;
	}

	public int getPFere()
	{
		return pFere;
	}

	public void setPFere(int fere)
	{
		pFere = fere;
	}


	public String getPExperience()
	{
		return pExperience;
	}

	public void setPExperience(String experience)
	{
		pExperience = experience;
	}

	public String getPXiaExperience()
	{
		return pXiaExperience;
	}

	public void setPXiaExperience(String xiaExperience)
	{
		pXiaExperience = xiaExperience;
	}


	public String getPCopper()
	{
		return pCopper;
	}

	public void setPCopper(String copper)
	{
		pCopper = copper;
	}


	public int getPPkValue()
	{
		if( pPkValue<0 )
		{
			pPkValue = 0;
		}
		return pPkValue;
	}

	public void setPPkValue(int pkValue)
	{
		pPkValue = pkValue;
	}

	public int getPPks()
	{
		return pPks;
	}

	public void setPPks(int pks)
	{
		pPks = pks;
	}

	public String getPMap()
	{
		return pMap;
	}

	public void setPMap(String map)
	{
		pMap = map;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public static int getP_GRADE()
	{
		return P_GRADE;
	}

	public static int getP_HP()
	{
		return P_HP;
	}

	public static int getP_MP()
	{
		return P_MP;
	}

	public static int getP_FORCE()
	{
		return P_FORCE;
	}

	public static int getP_AGILE()
	{
		return P_AGILE;
	}

	public static int getP_PHYSIQUE()
	{
		return P_PHYSIQUE;
	}

	public static int getP_SAVVY()
	{
		return P_SAVVY;
	}

	public static int getP_GJ()
	{
		return P_GJ;
	}

	public static int getP_FY()
	{
		return P_FY;
	}

	public String getPBjExperience()
	{
		return pBjExperience;
	}

	public void setPBjExperience(String bjExperience)
	{
		pBjExperience = bjExperience;
	}

	public int getPIsInitiative()
	{
		return pIsInitiative;
	}

	public void setPIsInitiative(int isInitiative)
	{
		pIsInitiative = isInitiative;
	}

	public int getPIsPassivity()
	{
		return pIsPassivity;
	}

	public void setPIsPassivity(int isPassivity)
	{
		pIsPassivity = isPassivity;
	}

	public int getPUpHp()
	{
		return pUpHp;
	}

	public void setPUpHp(int upHp)
	{
		pUpHp = upHp;
	}

	public int getPUpMp()
	{
		return pUpMp;
	}

	public void setPUpMp(int upMp)
	{
		pUpMp = upMp;
	}

	public int getPWrapContent()
	{
		return pWrapContent;
	}

	public void setPWrapContent(int wrapContent)
	{
		pWrapContent = wrapContent;
	}


	public int getGjSubjoin()
	{
		return gjSubjoin;
	}

	public void setGjSubjoin(int gjSubjoin)
	{
		this.gjSubjoin = gjSubjoin;
	}

	public int getFySubjoin()
	{
		return fySubjoin;
	}

	public void setFySubjoin(int fySubjoin)
	{
		this.fySubjoin = fySubjoin;
	}

	public PlayerWXVO getWx()
	{
		if (wx == null)
		{
			wx = new PlayerWXVO();
		}
		return wx;
	}

	public void setWx(PlayerWXVO wx)
	{
		this.wx = wx;
	}

	public int getAppendDropProbability()
	{
		return appendDropProbability;
	}

	public void setAppendDropProbability(int appendDropProbability)
	{
		if (appendDropProbability >= MathUtil.DROPDENOMINATOR)
		{

		}
		this.appendDropProbability = appendDropProbability;
	}

	public int getAppendCatchProbability()
	{
		return appendCatchProbability;
	}

	public void setAppendCatchProbability(int appendCatchProbability)
	{
		this.appendCatchProbability = appendCatchProbability;
	}

	public int getAppendExp()
	{
		return appendExp;
	}

	public void setAppendExp(int appendExp)
	{
		this.appendExp = appendExp;
	}

	public int getAppendNonsuchProbability()
	{
		return appendNonsuchProbability;
	}

	public void setAppendNonsuchProbability(int appendNonsuchProbability)
	{
		this.appendNonsuchProbability = appendNonsuchProbability;
	}

	public boolean isImmunityPoison()
	{
		return isImmunityPoison;
	}

	public void setImmunityPoison(boolean isImmunityPoison)
	{
		this.isImmunityPoison = isImmunityPoison;
	}

	public boolean isImmunityDizzy()
	{
		return isImmunityDizzy;
	}

	public void setImmunityDizzy(boolean isImmunityDizzy)
	{
		this.isImmunityDizzy = isImmunityDizzy;
	}

	/***************************************************************************
	 * **�������ӳ� public int gjJoin() { Passskill sk = new Passskill();
	 * EquipService se = new EquipService(); int gjJoin = (int) (getPGj() *
	 * sk.getGjMultiple(pPk) + sk.getGjAdd(pPk) + se
	 * .getPlayerEquipAttributeGj(pPk)); return gjJoin; }
	 * 
	 * /** **�������ӳ�**** public int fyJoin() { Passskill sk = new Passskill();
	 * EquipService se = new EquipService(); int fyJoin = (int) (getPFy() *
	 * sk.getFyMultiple(pPk) + sk.getFyAdd(pPk) + se
	 * .getPlayerEquipAttributeFy(pPk)); return fyJoin; }
	 * 
	 * /** **HP�ӳ�**** public int hpJoin() { Passskill sk = new Passskill();
	 * EquipService se = new EquipService(); int hpJoin = (int) (getPUpHp() *
	 * sk.getHpMultiple(pPk) + sk.getHpAdd(pPk) +
	 * se.getPlayerEquipAttributeHp(pPk)); return hpJoin; }
	 * 
	 * /** **MP�ӳ�**** public int mpJoin() { Passskill sk = new Passskill();
	 * EquipService se = new EquipService(); int mpJoin = (int) (getPUpMp() *
	 * sk.getMpMultiple(pPk) + sk.getMpAdd(pPk) +
	 * se.getPlayerEquipAttributeMp(pPk)); return mpJoin; }
	 * 
	 * /** **�����ʼӳ�**** public double bjJoin() { Passskill sk = new Passskill();
	 * EquipService se = new EquipService(); double bjJoin =
	 * sk.getBjMultiple(pPk) + se.getPlayerEquipAttributeBj(pPk); return bjJoin; }
	 */

	public int getPMaxHp()
	{
		int maxHp = getPUpHp();
		return maxHp;
	}

	public int getPMaxMp()
	{
		int maxMp = getPUpMp();
		return maxMp;
	}

	public double getDropMultiple()
	{
		return dropMultiple;
	}

	public void setDropMultiple(double dropMultiple)
	{
		this.dropMultiple = dropMultiple;
	}

	public int getDeleteFlag()
	{
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag)
	{
		this.deleteFlag = deleteFlag;
	}

	public Date getDeleteTime()
	{
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime)
	{
		this.deleteTime = deleteTime;
	}

	public Date getPkChangeTime()
	{
		return pkChangeTime;
	}

	public void setPkChangeTime(Date pkChangeTime)
	{
		this.pkChangeTime = pkChangeTime;
	}

	public int getPlayer_state_by_new()
	{
		return player_state_by_new;
	}

	public void setPlayer_state_by_new(int player_state_by_new)
	{
		this.player_state_by_new = player_state_by_new;
	}

	public String getContentdisplay()
	{
		return contentdisplay;
	}

	public void setContentdisplay(String contentdisplay)
	{
		this.contentdisplay = contentdisplay;
	}

	public int getPRace()
	{
		return pRace;
	}

	public void setPRace(int race)
	{
		pRace = race;
	}

	public int getFId()
	{
		return fId;
	}

	public int getFJob()
	{
		return fJob;
	}

	public int getFContribute()
	{
		return fContribute;
	}

	public Date getFJoinTime()
	{
		return fJoinTime;
	}

	public void setFId(int id)
	{
		fId = id;
	}

	public void setFJob(int job)
	{
		fJob = job;
	}

	public void setFContribute(int contribute)
	{
		fContribute = contribute;
	}

	public void setFJoinTime(Date joinTime)
	{
		fJoinTime = joinTime;
	}

	public int getLoginState()
	{
		return loginState;
	}

	public void setLoginState(int loginState)
	{
		this.loginState = loginState;
	}

	public String getFTitle()
	{
		return fTitle;
	}

	public void setFTitle(String title)
	{
		fTitle = title;
	}

	

}
