package com.ls.ben.vo.info.partinfo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.vo.map.SceneVO;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MathUtil;

public class PartInfoVO
{
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
	private int pHp;
	/** 法力值 */
	private int pMp;

	/** ******************攻击附加值*********************** */
	private int gjSubjoin = 0;
	/** ******************防御附加值*********************** */
	private int fySubjoin = 0;

	/** 角色本身攻击 */
	private int pGj;
	/** 角色本身防御 */
	private int pFy;

	/** 装备最大攻击 */
	private int pZbgjDa = 0;
	/** 装备最大防御 */
	private int pZbfyDa = 0;
	/** 装备最小攻击 */
	private int pZbgjXiao = 0;
	/** 装备最小防御 */
	private int pZbfyXiao = 0;

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
	/** 本级经验 */
	private String pBjExperience;
	/** 下经验 */
	private String pXiaExperience;
	/** 铜钱 */
	private String pCopper;
	/** pk值 */
	private int pPkValue;
	/** 开关1关2开 */
	private int pPks;
	/**
	 * pk更改时间
	 */
	private Date pkChangeTime;

	/** 标识是否处在主动攻击状态，0否；1是 */
	private int pIsInitiative;
	/** 标识是否处在被动攻击状态，0否；1是 */
	private int pIsPassivity;

	/** 所在地图坐标 */
	private String pMap;
	/** 创建时间 */
	private String createTime;

	/** 血量本级上限 */
	private int pUpHp;
	/** mp本级上限 */
	private int pUpMp;

	/** 包裹容量 */
	private int pWrapContent;

	/** 玩家五行 */
	private PlayerWXVO wx = null;
	
	/** 帮会 */
	private int fId;//帮派id
	private int fJob;//帮派职位
	private int fContribute;//帮派贡献
	private Date fJoinTime;//加入帮派的时间
	private String fTitle;//帮派称号
	
	/** 附加掉落概率 */
	private int appendDropProbability = 0;
	/** 附加捕获概率 */
	private int appendCatchProbability = 0;
	/** 经验加成 */
	private int appendExp = 0;
	/** 极品装备掉落率 */
	private int appendNonsuchProbability = 0;

	/** 是否免疫中毒 */
	private boolean isImmunityPoison;
	/** 是否免疫击晕 */
	private boolean isImmunityDizzy;

	/** 附加宠物经验加成 */
	private int appendPetExp;
	/** *****人物暴击率********** */
	private double dropMultiple;

	/** 为sceneId，是人物死了，而身上有免死道具,其如果使用了免死道具后所应该回到的地点 */
	// private int shouldScene;
	/** 删除标志 */
	private int deleteFlag;
	/** 删除时间 */
	private Date deleteTime;

	/** 作为师父的等级 */
	private int te_level;

	/** 最后一次传功时间 */
	private String chuangong;

	// 最后一次招徒或者拜师的时间
	private Date last_shoutu_time;

	// 玩家是否为新手
	private int player_state_by_new;

	/***状态描述**/
	private String contentdisplay ="";
	
	private int loginState;//登陆状态：0不在线，1在线
	
	/**
	 * 得到性别名
	 */
	public String getSexName()
	{
		return ExchangeUtil.exchangeToSex(this.pSex);
	}
	
	/**
	 * 帮派相关的简单描述:(长老,80级, 100荣誉,巫城) 
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
		sb.append(this.pGrade).append("级").append(",");
		sb.append(this.fContribute).append("荣誉").append(",");
		if( this.loginState==1 )//在线
		{
			sb.append(SceneCache.getById(this.pMap).getSceneName());
		}
		else
		{
			sb.append("不在线");
		}
		sb.append(")");
		return sb.toString();
	}
	/**
	 * 氏族成员的描述，在坐骑传送的时候用到
	 * @return String
	 */
	public String getDisplay()
	{
		if( fId<=0 )
			return "";
		StringBuffer sb = new StringBuffer();
		sb.append("【");
		sb.append(ExchangeUtil.getFJobName(this.fJob)).append("】");
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
	 * 在最大最小攻击之间随机产生攻击值包括装备攻击
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
	 * 在最大防御最小防御之间随机产生攻击值,包括装备防御
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
	 * 总的基础防御
	 * @return
	 */
	public int getBasicFy()
	{
		int fy = getPFy();
		return MathUtil.getIntegetValueByAddRate(fy, fySubjoin);
	}
	
	/**
	 * 在最大装备防御最小装备防御之间随机产生攻击值(总的装备防御)
	 * @return
	 */
	public int getZbFy()
	{
		int zb_fy = MathUtil.getRandomBetweenXY(getPZbfyXiao(), getPZbfyDa());
		return MathUtil.getIntegetValueByAddRate(zb_fy, fySubjoin);
	}
	/**
	 * 在最大装备攻击最小装备攻击之间随机产生攻击值(总的装备攻击)
	 * @return
	 */
	public int getZbGj()
	{
		int zb_gj = MathUtil.getRandomBetweenXY(getPZbgjXiao(), getPZbgjDa());
		return MathUtil.getIntegetValueByAddRate(zb_gj, fySubjoin);
	}

	/**
	 * 总的防御小
	 * @return
	 */
	public int getFyXiao()
	{
		int fy_xiao = getPFy() + getPZbfyXiao();
		return MathUtil.getIntegetValueByAddRate(fy_xiao, fySubjoin);
	}
	
	/**
	 * 总的防御大
	 * @return
	 */
	public int getFyDa()
	{
		int fy_xiao = getPFy() + getPZbfyDa();
		return MathUtil.getIntegetValueByAddRate(fy_xiao, fySubjoin);
	}
	/**
	 * 总的防攻击小
	 * @return
	 */
	public int getGjXiao()
	{
		int gj_xiao = getPGj() + this.getPZbgjXiao();
		return MathUtil.getIntegetValueByAddRate(gj_xiao, fySubjoin);
	}
	
	/**
	 * 总的攻击大
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
	 * 得到数据库里取的真实hp,初始化basicInfo用
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
	 * **攻击力加成 public int gjJoin() { Passskill sk = new Passskill();
	 * EquipService se = new EquipService(); int gjJoin = (int) (getPGj() *
	 * sk.getGjMultiple(pPk) + sk.getGjAdd(pPk) + se
	 * .getPlayerEquipAttributeGj(pPk)); return gjJoin; }
	 * 
	 * /** **防御力加成**** public int fyJoin() { Passskill sk = new Passskill();
	 * EquipService se = new EquipService(); int fyJoin = (int) (getPFy() *
	 * sk.getFyMultiple(pPk) + sk.getFyAdd(pPk) + se
	 * .getPlayerEquipAttributeFy(pPk)); return fyJoin; }
	 * 
	 * /** **HP加成**** public int hpJoin() { Passskill sk = new Passskill();
	 * EquipService se = new EquipService(); int hpJoin = (int) (getPUpHp() *
	 * sk.getHpMultiple(pPk) + sk.getHpAdd(pPk) +
	 * se.getPlayerEquipAttributeHp(pPk)); return hpJoin; }
	 * 
	 * /** **MP加成**** public int mpJoin() { Passskill sk = new Passskill();
	 * EquipService se = new EquipService(); int mpJoin = (int) (getPUpMp() *
	 * sk.getMpMultiple(pPk) + sk.getMpAdd(pPk) +
	 * se.getPlayerEquipAttributeMp(pPk)); return mpJoin; }
	 * 
	 * /** **暴击率加成**** public double bjJoin() { Passskill sk = new Passskill();
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
