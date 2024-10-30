package com.ls.model.user;

import java.util.Calendar;
import java.util.Date;

import com.ben.jms.JmsUtil;
import com.ben.lost.CompassService;
import com.ben.shitu.model.ShituConstant;
import com.ben.shitu.service.ShituService;
import com.ls.ben.cache.dynamic.manual.attack.AttacckCache;
import com.ls.ben.dao.info.buff.BuffEffectDao;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.dao.info.partinfo.UGrowDao;
import com.ls.ben.vo.info.buff.BuffEffectVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.UGrowVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.organize.faction.Faction;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.buff.BuffSystem;
import com.ls.pub.constant.buff.BuffType;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.buff.BuffEffectService;
import com.ls.web.service.faction.FactionService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.MyServiceImpl;
import com.ls.web.service.rank.RankService;
import com.ls.web.service.room.RoomService;
import com.lw.service.UnchartedRoom.UnchartedRoomService;
import com.lw.service.menpaicontest.MenpaiContestService;
import com.pub.GameArgs;
import com.web.service.popupmsg.PopUpMsgService;

/**
 * ���ܣ���һ�����Ϣ��Ӧu_part_info��
 * @author ls Apr 3, 2009 1:51:51 PM
 */
public class BasicInfo  extends UserBaseWithSave
{
	/** ***********************��ɫ�����󲻱������************************* */

	/** ��ɫid */
	private int pPk;
	/** ������Ա��Ϣid */
	private int uPk;
	/** ��ɫ�� */
	private String name;
	/** ��ʾ��ɫ��,���ܻ���״̬�Ĳ�ͬ�����ϲ�ͬ�ı�� */
	private String displayName;
	/** �Ա� */
	private int sex;
	/**���壬1��2��**/
	private int pRace;
	/** ����ʱ�� */
	private String createTime;

	/** ***********************��ɫ������仰������************************* */

	/** �ȼ� */
	private int grade;
	/** ��ǰ������id */
	private String sceneId;
	private SceneVO sceneInfo;

	/** �Ƿ��ѻ� 1û��� 2 ��� */
	private int married;
	/** ����ID */
	private int partner;

	/** ��� */
	private int fId;//����id
	private int fJob;//����ְλ
	private int fContribute;//���ɹ���
	private Date fJoinTime;//������ɵ�ʱ��
	private String fTitle;//���ɳƺ�
	
	/** ͭǮ */
	private long copper;
	/** �������� */
	private int wrapContent;
	/** ����ʣ������ */
	private int wrapSpare=-1;//����Ҫ�洢�����ݿ�

	/** ����ֵ */
	private int hp;
	/** ����ֵ */
	private int mp;
	/** Ѫ���������� */
	private int upHp;
	/** mp�������� */
	private int upMp;

	/** ��ɫ��ǰ���� */
	private String curExp;

	/** pk���ֵ */
	private int evilValue;
	/** ����1��2�� */
	private int pkSwitch;
	/**
	 * pk����ʱ��
	 */
	private Date pkChangeTime;

	/** ��ɫ������ */
	private int basicGj;
	/** ��ɫ������� */
	private int basicFy;

	/** ������ */
	private double multipleDamage;
	/** pk��������������ϴ��о�ת��ʱ,��Ӧ��ȥ�ĵط� */
	private int shouldScene;

	/** ��Ϊʦ���ĵȼ� */
	private int te_level;

	/** ��һ�δ���ʱ�� */
	private String chuangong;

	// ���һ����ͽ���߰�ʦ��ʱ��
	private Date last_shoutu_time;

	/** ��ʱ���� */
	private String temp_Name;

	/** ���̨����� */
	private int zu = -1;

	// ��ҵ�����״̬
	private int player_state_by_new;

	private int attack_npc = 0;

	private int scratchticketnum = 0;

	private int addscratchticketnum = 0;
	/**ѣ�λغ�**/
	private int xuanyunhuihe = 0;
	/**�����غ�**/
	private int acthuihe = 0;
	/**Ч��**/
	private int actcontent = 0;
	/**�����غ�**/
	private int defhuihe = 0;
	/**Ч��**/
	private int defcontent = 0;
	/**����**/
	private String menpaiskilldisplay = "";
	/**pk����ʱ��*/
	private long pk_safe_time = 0;
	/**PK����״̬**/
	private int pk_safe_state = 0;
	//��ҽ������ʱ��
	private long tianguan_time = 0;
	//��ҽ��뵽��صĵڼ���
	private String tianguan_npc = "";
	//����ڸ���ص�ɱ����
	private int tianguan_kill_num = 0;
	//����Ƿ���ɱNPC
	private int menpainpcstate = 0;
	//���ɱ��NPC MENU ���
	private int menpainpcid = 0;
	/**
	 * 
	 * �Ƿ��ж�
	 */
	private boolean isPoisoning=false; 
	/**
	 * �ж������ܵĶ����˺�
	 */
	/**
	 * �ж��غ���
	 */
	private int poisonCount=0;
	private int addDamage;
	

	public BasicInfo(int p_pk) throws Exception
	{
		super(p_pk,"u_part_info", "p_pk",p_pk+"");
		PartInfoDao partInfoDao = new PartInfoDao();
		PartInfoVO vo = partInfoDao.getPartInfoByID(p_pk);

		if( vo==null )
		{
			throw new Exception();
		}
		
		pPk = vo.getPPk();
		uPk = vo.getUPk();
		temp_Name = name = vo.getPName();
		sex = vo.getPSex();
		createTime = vo.getCreateTime();

		grade = vo.getPGrade();

		RoomService roomService = new RoomService();
		sceneId = vo.getPMap();
		setSceneInfo(roomService.getById(sceneId));

		married = vo.getPHarness();
		partner = vo.getPFere();
		pRace=vo.getPRace();
		/** ��Ǯ */
		copper = Long.parseLong(vo.getPCopper());
		/** ����������� */
		wrapContent = vo.getPWrapContent();
		
		/** ����ֵ */
		hp = vo.getHP();
		/** ����ֵ */
		mp = vo.getPMp();
		/** Ѫ���������� */
		upHp = vo.getPUpHp();
		/** mp�������� */
		upMp = vo.getPUpMp();
	
		/** ��ɫ��ǰ���� */
		curExp = vo.getPExperience();
		
		/** pk���ֵ */
		evilValue = vo.getPPkValue();
		/** ����1��2�� */
		pkSwitch = vo.getPPks();
		/** �ϴθ���pk���ص�ʱ�� */
		pkChangeTime = vo.getPkChangeTime();

		/** ��ɫ������ */
		basicGj = vo.getPGj();
		/** ��ɫ������� */
		basicFy = vo.getPFy();
		

		multipleDamage = vo.getDropMultiple();
		te_level = vo.getTe_level();
		chuangong = vo.getChuangong();
		last_shoutu_time = vo.getLast_shoutu_time();
		player_state_by_new = vo.getPlayer_state_by_new();
		
		//********���������Ϣ
		this.fId = vo.getFId(); 
		this.fJob = vo.getFJob(); 
		this.fContribute = vo.getFContribute(); 
		this.fJoinTime = vo.getFJoinTime(); 
		this.fTitle = vo.getFTitle();
	}

	/**
	 * ս����������ʼ��ս�����״̬����
	 */
	public void initFightState()
	{
		setPoisoning(false);
		this.setPoisonCount(0);
		setAddDamage(0);
		setXuanyunhuihe(0);
		setActhuihe(0);
		setActcontent(0);
		setDefhuihe(0);
		setDefcontent(0);
		setMenpaiskilldisplay("");
		setPk_safe_time(new Date().getTime());
		setPk_safe_state(1);
	}
	
	/**
	 * ͨ������ְλ�ж��Ƿ���Ȩ�޲�����ֻ��jobְ�񣨰���jobְ�񣩵�Ȩ�޲ſ�ִ��
	 * @param job      ����ִ�е�ְ��
	 * @return
	 */
	public boolean isOperateByFJob( int job )
	{
		if( this.fJob>=job )
		{
			return true;
		}
		return false;
	}
	
	/**
	 * �Ƿ����ת���峤
	 */
	public boolean getIsChangeZuzhang()
	{
		return this.isOperateByFJob(Faction.ZUZHANG);
	}
	/**
	 * �Ƿ���Է�����ļ��Ϣ
	 */
	public boolean getIsRecruit()
	{
		return this.isOperateByFJob(Faction.HUFA);
	}
	/**
	 * �Ƿ���Ը��ĳ�Ա�ƺ�
	 */
	public boolean getIsChangeTitle()
	{
		return this.isOperateByFJob(Faction.ZHANGLAO);
	}
	/**
	 * �Ƿ���Ը���ְλ
	 */
	public boolean getIsChangeJob()
	{
		return this.isOperateByFJob(Faction.ZHANGLAO);
	}
	/**
	 * �Ƿ���Թ����������
	 */
	public boolean getIsManageApply()
	{
		return this.isOperateByFJob(Faction.ZHANGLAO);
	}
	/**
	 * �Ƿ����ɾ����Ա
	 * @return
	 */
	public boolean getIsDelMember()
	{
		return this.isOperateByFJob(Faction.ZHANGLAO);
	}
	/**
	 * �Ƿ���Է�������
	 * @return
	 */
	public boolean getIsPublishNotice()
	{
		return this.isOperateByFJob(Faction.HUFA);
	}
	/**
	 * �Ƿ����ɾ������
	 * @return
	 */
	public boolean getIsDelNotice()
	{
		return this.isOperateByFJob(Faction.ZHANGLAO);
	}
	
	/**
	 * PK�������幱�׺���������
	 * @return		�Ƿ��������幱��
	 */
	public boolean addFContributeAndFPrestige()
	{
		Faction faction = getFaction();
		if( faction!=null )
		{
			//***********����������
			addFContribute(1);//���Ӹ��˰��ɹ���
			faction.updatePrestige(1);//���Ӱ�������
			return true;
		}
		return false;
	}
	
	/**
	 * �õ���������
	 */
	public String getFactionName()
	{
		Faction faction = this.getFaction();
		if( faction!=null )
		{
			return faction.getName();
		}
		else
		{
			return "��";
		}
	}
	/**
	 * �õ�������Ϣ
	 */
	public Faction getFaction()
	{
		if( this.fId==0 )
		{
			return null;
		}
		Faction faction =  FactionService.getById(fId);
		if( faction==null )
		{
			this.leaveFaction();
		}
		return faction;
	}
	/**
	 * �õ�����id
	 */
	public int getFId()
	{
		Faction faction = getFaction();
		if( faction==null )
		{
			return 0;
		}
		else
		{
			return faction.getId();
		}
	}
	
	/**
	 * �뿪����
	 */
	public void leaveFaction()
	{
		if( this.fId<=0 )
		{
			return;
		}
		this.setFId(0);
		this.setFJob(0);
		this.setFContribute(0);
		this.setFTitle(null);
		save();
	}
	/**
	 * ���İ��ɳƺ�
	 * @param new_job
	 */
	public void changeFTitle(String new_title)
	{
		if( this.fId<=0 )
		{
			return;
		}
		this.setFTitle(new_title);
		save();
	}
	/**
	 * ���İ���ְλ
	 * @param new_job
	 */
	public void changeFJob(int new_job)
	{
		if( this.fId<=0 || new_job == this.fJob )
		{
			return;
		}
		this.setFJob(new_job);
		save();
	}
	
	/**
	 * �ж��Ƿ����㹻�İ�������
	 * @return
	 */
	public boolean isEnoughFContribute( int need_attribute )
	{
		if( this.fId<=0 || need_attribute>this.fContribute )
		{
			return false;
		}
		return true;
	}
	
	/**
	 * ���İ�������
	 * @param update_attribute    ������ʾ�ӣ�������ʾ��
	 */
	public void addFContribute(int update_attribute)
	{
		if( update_attribute==0 )
		{
			return;
		}
		if( update_attribute<0 && this.isEnoughFContribute(-update_attribute)==false )
		{
			return;
		}
		this.setFContribute(this.fContribute+update_attribute);
		save();
	}
	
	/**
	 * �������
	 */
	public void jionFaction( int fId ,int job )
	{
		if( this.fId>0 )
		{
			return;
		}
		this.setFId(fId);
		this.setFJob(job);
		this.setFContribute(0);
		this.setFJoinTime(new Date());
	}
	
	/**
	 * �жϰ��������Ƿ��㹻
	 */
	public String isEnoughWrapSpace( int need_space)
	{
		if( need_space>this.getWrapSpare())
		{
			return "�����ռ���Ҫ"+need_space+"����,����ֻ��"+this.wrapSpare+"����";
		}
		return null;
	}
	
	/**
	 * �õ���Ǯ����
	 * @return
	 */
	public String getMoneyDes()
	{
		return MoneyUtil.changeCopperToStr(copper);
	}
	
	
	/**
	 * �ж�Ǯ�Ƿ��㹻
	 */
	public boolean isEnoughMoney( int need_money )
	{
		if( this.copper<need_money )
		{
			return false;
		}
		return true;
	}
	
	/**
	 * ��ɫ��������
	 */
	public void reset()
	{
		UGrowDao u_grow_dao = new UGrowDao();
		UGrowVO init_info = u_grow_dao.getByGradeAndRace(1, this.pRace);//�õ���ɫ��ʼ����Ϣ
		
		
		/** ����ֵ���� */
		int pUpHp = init_info.getGHP();
		/** ����ֵ���� */
		int pUpMp = init_info.getGMP();
		/** ���� */
		int pGj = init_info.getGGj();
		/** ���� */
		int pFy = init_info.getGFy();
		/** ������ */
		double pDropMultiple = init_info.getGDropMultiple();
		
		/** ��ʼ��Ǯ */
		int pCopper = 100;
		/** ����1��2�� */
		int pPks = 1;
		/** �������� */
		int pWrapContent = 50;

		/** ���������� */
		String pMap = "1";
		switch(this.pRace)
		{
			case 1:pMap="1";break;//��
			case 2:pMap="66";break;//��
		}
		
		this.setGrade(1);//��ʼ����1��
		this.setCurExp("0");
		this.setUpHp(pUpHp);
		this.setUpMp(pUpMp);
		this.setBasicGj(pGj);
		this.setBasicFy(pFy);
		this.setCopper(pCopper);
		this.setPkSwitch(pPks);
		this.setWrapContent(pWrapContent);
		this.setMultipleDamage(pDropMultiple);
		
		this.setPlayer_state_by_new(0);//������״̬
		
		//��ԭ��ǰѪ���ͷ���
		this.setHp(upHp);
		this.setMp(upMp);
		
		this.wrapSpare = pWrapContent;//��ʼ��ʣ���������
		
		this.updateSceneId(pMap);
		
		this.save();
	}

	
	/**
	 * ���ӵȼ���������
	 */
	public void addGrade()
	{
		this.setGrade(++grade);
		UGrowDao growDao = new UGrowDao();
		UGrowVO grow = growDao.getByGradeAndRace(grade, this.pRace);
		// ����partInfoVO(����)
		LogService logService = new LogService();
		logService.recordPlayerLog(this.getPPk(), this.getName(), upHp + "",grow.getGHP() + "", "HP��ҵȼ�" + this.getGrade());
		logService.recordPlayerLog(this.getPPk(), this.getName(), upMp + "",grow.getGMP() + "", "MP��ҵȼ�" + this.getGrade());
		logService.recordPlayerLog(this.getPPk(), this.getName(), basicGj + "",grow.getGGj() + "", "GJ��ҵȼ�" + this.getGrade());
		logService.recordPlayerLog(this.getPPk(), this.getName(), basicFy + "",grow.getGFy() + "", "FY��ҵȼ�" + this.getGrade());
		this.setUpHp(upHp + grow.getGHP());
		this.setUpMp(upMp + grow.getGMP());
		this.setHp(upHp + grow.getGHP());
		this.setMp(upMp+ grow.getGMP());
		this.setBasicGj(basicGj + grow.getGGj());
		this.setBasicFy(basicFy + grow.getGFy());

		this.setMultipleDamage(grow.getGDropMultiple());// ������û�б��־û�

		// ��ҵȼ��ﵽ30����pk����Ĭ���޸�Ϊ����
		if( this.grade == 30)
		{
			updatePkSwitchIn30Grade(2);
		}

		String content = "��ɫ��:" + name + ";������ȼ�Ϊ:" + grade + ";��ǰ����Ϊ:"+ curExp;
		logService.recordUpgradeLog(pPk, name, content);
		int digit = GameConfig.getGoUpGrade(grade, "go_up_grade");// ������ҵȼ��Ƿ���ϵͳ�趨֮�ڵ�
		if (digit != -1)
		{
			new PopUpMsgService().addSysSpecialMsg(pPk, grade, digit,
					PopUpMsgType.GO_UP_GRADE);
		}
		// �������������ȼ������
		if (grade == Integer.parseInt(GameConfig
				.getPropertiesObject("prop_grade_role_grade")))
		{
			new PopUpMsgService().addSysSpecialMsg(pPk, grade, 0,
					PopUpMsgType.PROP_GRADE);
		}
		if (grade == ShituConstant.BAISHI_LEVEL_LIMIT)
		{
			new ShituService().delShitu(pPk);
		}
		// ͳ����Ҫ
		new RankService().updateAdd(pPk, "p_level", 1);
		new MyServiceImpl().levelUp(pPk);
		RoleEntity role_info = this.getRoleEntity();
		JmsUtil.updateLevel(role_info.getStateInfo().getSuper_qudao(),
				role_info.getStateInfo().getQudao(), role_info.getStateInfo()
						.getUserid(), name, grade);
		Faction faction = this.getFaction();
		if( faction!=null )
		{
			faction.memUpgrade();
		}
	}

	public void updateSceneId(String new_scene_id)
	{
		if (sceneId.equals(new_scene_id))
		{
			return;// scene_idû�䲻������
		}

		RoomService roomService = new RoomService();
		SceneVO scene_info = roomService.getById(new_scene_id);
		if (scene_info == null)
		{
			// ���û�иõ�ͼid������
			return;
		}

		MenpaiContestService ms = new MenpaiContestService();
		UnchartedRoomService rs = new UnchartedRoomService();
		RoleEntity role_info = this.getRoleEntity();
		ms.updatePlayerMenpaiContestState(role_info,new_scene_id);
		rs.updatePlayerUnchartedRoomState(role_info,new_scene_id);// �ؾ�
		
		// �����ı�ʱ����
		this.setSceneId(new_scene_id);
		this.setSceneInfo(scene_info);

		AttacckCache attacckCache = new AttacckCache();

		attacckCache.destoryAllNpc(this.pPk);// ���������
		
		CompassService.pass(this);
	}

	public void updateMarried(int married)
	{
		this.setMarried(married);
	}

	public void updatePartner(int partner)
	{
		this.setPartner(partner);
	}


	/**
	 * ���ӽ�Ǯ��ֵ
	 * @param addCopper 	���ӵ�ֵ(����)
	 */
	public void addCopper(long addCopper)
	{
		if( addCopper==0 )
		{
			return;
		}
		this.setCopper(copper + addCopper);
		// ͳ����Ҫ
		new RankService().updateAdd(pPk, "money", addCopper);
	}

	/**
	 * ���Ӱ�������
	 * @param addWrapContent	���ӵ�ֵ(����)
	 */
	public void addWrapContent(int addWrapContent)
	{
		this.setWrapContent(this.wrapContent + addWrapContent);
	}

	/**
	 * �ı�����Ŀռ�
	 * @param updateWrapSpare	���ӻ���ٵİ�����ʣ��ռ������(����)
	 */
	public void addWrapSpare(int addWrapSpare)
	{
		int new_wrap_spare = this.getWrapSpare() + addWrapSpare;
		if( new_wrap_spare<0 )
		{
			new_wrap_spare = 0;
		}
		else if( new_wrap_spare>this.wrapContent )
		{
			new_wrap_spare = this.wrapContent;
		}
		this.wrapSpare = new_wrap_spare;
	}

	public void updateHp(int hp)
	{
		this.setHp(hp);
	}

	public void updateMp(int mp)
	{
		this.setMp(mp);
	}


	/**
	 * ���Ӿ���
	 * @param curExp	Ϊ���Ӻ�ľ��飬�������ӵķ���
	 */
	public void updateCurExp(String curExp)
	{
		this.setCurExp(curExp);
		// ͳ����Ҫ
		new RankService().updatea(pPk, "p_exp", curExp);
	}

	/**
	 * ���Ӿ���
	 * @param addExp	Ϊ�������ӷ���
	 */
	public void updateAddCurExp(long addExp)
	{
		long currentExp = Integer.parseInt(this.curExp.trim()) + addExp;
		if (currentExp <= 0)
		{
			return;
		}
		// ͳ����Ҫ
		new RankService().updateAdd(pPk, "p_exp", addExp);
		this.setCurExp(currentExp + "");
	}

	/**
	 * �޸�ʦ���ȼ�
	 * @param evilValue
	 */
	public void updateTeLevel(int te_level)
	{
		if (te_level > 1)
		{
			this.setTe_level(te_level);
		}
	}

	/**
	 * �޸�ʦ������ʱ��
	 * @param evilValue
	 */
	public void updateChuangong()
	{
		this.setChuangong(com.ben.shitu.model.DateUtil.getDateSecondFormat());
	}

	// �������ֵ,evilValueΪ���º��ֵ
	private void updateEvilValue(int evilValue)
	{
		if( evilValue<0 )
		{
			evilValue = 0;
		}
		if( this.evilValue==0 && evilValue==0 )
		{
			return;
		}
		// ͳ����Ҫ
		new RankService().updatea(pPk, "evil", evilValue);
		this.setEvilValue(evilValue);
	}

	/**
	 * ��ʱ���������ֵ
	 */
	public void consumeEvilValueByTime()
	{
		if( this.evilValue <= 0) {
			return ;
		}
		long cur_time = Calendar.getInstance().getTimeInMillis();
		long pre_reduce_time = this.getRoleEntity().getStateInfo().getEvilValueConsumeTime();//ǰһ���������ֵ��ʱ��
	
		int sub_evil_value = (int) ((cur_time-pre_reduce_time)/GameArgs.CONSUME_TIME_UNIT);//�������������ֵ
		if( sub_evil_value>0 )
		{
			addEvilValue(-sub_evil_value);	
			this.getRoleEntity().getStateInfo().updateEvilValueConsumeTime(pre_reduce_time+sub_evil_value*GameArgs.CONSUME_TIME_UNIT);		
		}
	}
	
	// �������ֵ,addEvilValueΪҪ���º��ֵ,addEvilValueΪ���������
	public void addEvilValue(int addEvilValue)
	{
		if( addEvilValue==0 )
		{
			return;
		}
		if( addEvilValue<0 )//�����ֵ
		{
			BuffEffectService buffEffectService = new BuffEffectService();
			BuffEffectDao buffEffectDao = new BuffEffectDao();
			BuffEffectVO buffvo = buffEffectDao.getBuffEffectByBuffType(this.p_pk, BuffSystem.PLAYER, BuffType.REDUCEPKVALUE);
			if( buffEffectService.isEffected(buffvo) )
			{
				addEvilValue *= (buffvo.getBuffEffectValue()/100);
			}
		}
		this.updateEvilValue(evilValue+addEvilValue);
	}

	/**
	 * ����PK����
	 * @param pkSwitch
	 */
	public void updatePkSwitch(int pkSwitch)
	{
		this.setPkSwitch(pkSwitch);
		this.setPkChangeTime();
	}

	/**
	 * ����PK����,����30���г����Զ�����,������ȫ��������, �˷���ֻ�޸�pk����,���޸�pk�����޸�ʱ��
	 * @param pkSwitch
	 */
	public void updatePkSwitchIn30Grade(int pkSwitch)
	{
		this.setPkSwitch(pkSwitch);
		// �������������ȼ������
		if (pkSwitch == Integer.parseInt(GameConfig
				.getPropertiesObject("pk_switch_type")))
		{
			new PopUpMsgService().addSysSpecialMsg(pPk, pkSwitch, 0,
					PopUpMsgType.PK_SWITCH);
		}
	}


	/**
	 * ����״̬
	 * @param player_state_by_new
	 */
	public void updatePlayer_state_by_new(int player_state_by_new)
	{
		this.setPlayer_state_by_new(player_state_by_new);
		this.save();
	}

	public int getPPk()
	{
		return pPk;
	}

	public int getUPk()
	{
		return uPk;
	}

	public String getName()
	{
		return temp_Name;
	}

	public String getRealName()
	{
		return name;
	}

	public String getTemp_Name()
	{
		return temp_Name;
	}

	public void setTemp_Name(String temp_Name)
	{
		this.temp_Name = temp_Name;
	}

	public String getSexName()
	{
		return ExchangeUtil.exchangeToSex(sex);
	}
	public int getSex()
	{
		return sex;
	}


	public String getCreateTime()
	{
		return createTime;
	}

	public int getGrade()
	{
		return grade;
	}

	public String getSceneId()
	{
		return sceneId;
	}

	public int getMarried()
	{
		return married;
	}

	public int getPartner()
	{
		return partner;
	}

	public long getCopper()
	{
		return copper;
	}

	public int getWrapContent()
	{
		return wrapContent;
	}

	public int getWrapSpare()
	{
		if( wrapSpare==-1 )
		{
			PlayerEquipDao playerEquipDao = new PlayerEquipDao();
			PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
			int cur_content_on_wrap = 0;
			cur_content_on_wrap = playerEquipDao.getNumOnWrap(pPk);
			cur_content_on_wrap += playerPropGroupDao.getNumOnWrap(pPk);
			wrapSpare = this.getWrapContent()-cur_content_on_wrap;
		}
		return wrapSpare;
	}

	public int getHp()
	{
		return hp;
	}

	public int getMp()
	{
		return mp;
	}

	public int getUpHp()
	{
		return upHp;
	}

	public int getUpMp()
	{
		return upMp;
	}

	public String getCurExp()
	{
		return curExp;
	}

	public String getPreGradeExp()
	{
		UGrowDao u_grow_dao = new UGrowDao();
		UGrowVO grow_info = u_grow_dao.getByGradeAndRace(this.grade, this.pRace);//�õ���ɫ��ʼ����Ϣ
		return grow_info.getGExp();
	}

	public String getNextGradeExp()
	{
		UGrowDao u_grow_dao = new UGrowDao();
		UGrowVO grow_info = u_grow_dao.getByGradeAndRace(this.grade, this.pRace);//�õ���ɫ��ʼ����Ϣ
		return grow_info.getGNextExp();
	}

	public int getEvilValue()
	{
		consumeEvilValueByTime();
		return evilValue;
	}

	/**
	 * �õ�pk��������
	 * @return
	 */
	public String getPkSwitchDes()
	{
		if( pkSwitch==2 )
		{
			return "��PK";
		}
		else
		{
			return "����PK";
		}
	}
	
	public int getPkSwitch()
	{
		return pkSwitch;
	}

	public int getBasicGj()
	{
		return basicGj;
	}

	public int getBasicFy()
	{
		return basicFy;
	}

	public double getMultipleDamage()
	{
		return multipleDamage;
	}

	private void setMultipleDamage(double multipleDamage)
	{
		this.multipleDamage = multipleDamage;
		uPartInfoTab.addPersistenceColumn("p_drop_multiple", multipleDamage
				+ "");
	}


	private void setGrade(int grade)
	{
		this.grade = grade;
		uPartInfoTab.addPersistenceColumn("p_grade", grade + "");
	}

	private void setSceneId(String sceneId)
	{
		this.sceneId = sceneId;
		uPartInfoTab.addPersistenceColumn("p_map", sceneId + "");
	}

	private void setMarried(int married)
	{
		this.married = married;
		uPartInfoTab.addPersistenceColumn("p_harness", married + "");
	}

	private void setPartner(int partner)
	{
		this.partner = partner;
		uPartInfoTab.addPersistenceColumn("p_fere", partner + "");
	}


	private void setCopper(long copper)
	{
		this.copper = copper;
		uPartInfoTab.addPersistenceColumn("p_copper", copper + "");
	}

	private void setWrapContent(int wrapContent)
	{
		this.wrapContent = wrapContent;
		uPartInfoTab.addPersistenceColumn("p_wrap_content", wrapContent + "");
	}


	private void setHp(int hp)
	{
		this.hp = hp;
		uPartInfoTab.addPersistenceColumn("p_hp", hp + "");
	}

	private void setMp(int mp)
	{
		this.mp = mp;
		uPartInfoTab.addPersistenceColumn("p_mp", mp + "");
	}

	private void setUpHp(int upHp)
	{
		this.upHp = upHp;
		uPartInfoTab.addPersistenceColumn("p_up_hp", upHp + "");
	}

	private void setUpMp(int upMp)
	{
		this.upMp = upMp;
		uPartInfoTab.addPersistenceColumn("p_up_mp", upMp + "");
	}

	private void setCurExp(String curExp)
	{
		this.curExp = curExp;
		uPartInfoTab.addPersistenceColumn("p_experience", curExp);
	}


	private void setEvilValue(int evilValue)
	{
		this.evilValue = evilValue;
		uPartInfoTab.addPersistenceColumn("p_pk_value", evilValue + "");
	}

	private void setPkSwitch(int pkSwitch)
	{
		this.pkSwitch = pkSwitch;
		uPartInfoTab.addPersistenceColumn("p_pks", pkSwitch + "");
	}

	private void setBasicGj(int basicGj)
	{
		this.basicGj = basicGj;
		uPartInfoTab.addPersistenceColumn("p_gj", basicGj + "");
	}

	private void setBasicFy(int basicFy)
	{
		this.basicFy = basicFy;
		uPartInfoTab.addPersistenceColumn("p_fy", basicFy + "");
	}


	public Date getPkChangeTime()
	{
		return pkChangeTime;
	}

	private void setPkChangeTime()
	{
		this.pkChangeTime = new Date();

		String date_str = DateUtil.formatDateToStr(this.pkChangeTime,
				"yyyy-MM-dd hh:mm:ss");

		uPartInfoTab.addPersistenceColumn("p_pk_changetime", date_str);
	}

	public int getShouldScene()
	{
		return shouldScene;
	}

	public void setShouldScene(int shouldScene)
	{
		this.shouldScene = shouldScene;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public SceneVO getSceneInfo()
	{
		return sceneInfo;
	}

	public void setSceneInfo(SceneVO sceneInfo)
	{
		this.sceneInfo = sceneInfo;
	}

	public int getPlayer_state_by_new()
	{
		return player_state_by_new;
	}

	private void setPlayer_state_by_new(int player_state_by_new)
	{
		this.player_state_by_new = player_state_by_new;
		uPartInfoTab.addPersistenceColumn("player_state_by_new",
				player_state_by_new + "");
	}

	// �ж�����ڲ���תְ��ʱ��
	public boolean isPlayerHaveTransfer()
	{
		if (this.grade == 39 || this.grade == 59 || this.grade == 69
				|| this.grade == 79)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	// �ж���ҵľ����Ƿ�����
	public boolean isPlayerExpFull()
	{
		if (Long.parseLong(this.curExp) >= Long.parseLong(this.getNextGradeExp()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getAttack_npc()
	{
		return attack_npc;
	}

	public void setAttack_npc(int attack_npc)
	{
		this.attack_npc = attack_npc;
	}

	public int getScratchticketnum()
	{
		return scratchticketnum;
	}

	public void setScratchticketnum(int scratchticketnum)
	{
		this.scratchticketnum = scratchticketnum;
	}

	public int getAddscratchticketnum()
	{
		return addscratchticketnum;
	}

	public void setAddscratchticketnum(int addscratchticketnum)
	{
		if(addscratchticketnum < 0){
			this.addscratchticketnum = 0;
		}else{
			this.addscratchticketnum = addscratchticketnum;	
		}
	}

	public int getXuanyunhuihe()
	{
		return xuanyunhuihe;
	}

	public void setXuanyunhuihe(int xuanyunhuihe)
	{
		if(xuanyunhuihe < 0){
			this.xuanyunhuihe = 0;
		}else{
			this.xuanyunhuihe = xuanyunhuihe;	
		}
	}

	public int getActhuihe()
	{
		return acthuihe;
	}

	public void setActhuihe(int acthuihe)
	{
		if(acthuihe < 0){
			this.acthuihe = 0;
		}else{
			this.acthuihe = acthuihe;	
		}
	}

	public int getActcontent()
	{
		return actcontent;
	}

	public void setActcontent(int actcontent)
	{
		if(actcontent < 0){
			this.actcontent = 0;
		}else{
			this.actcontent = actcontent;	
		}
	}

	public int getDefhuihe()
	{
		return defhuihe;
	}

	public void setDefhuihe(int defhuihe)
	{
		if(defhuihe < 0){
			this.defhuihe = 0;
		}else{
			this.defhuihe = defhuihe;	
		}
	}

	public int getDefcontent()
	{
		return defcontent;
	}

	public void setDefcontent(int defcontent)
	{
		if(defcontent < 0){
			this.defcontent = 0;
		}else{
			this.defcontent = defcontent;	
		}
	}

	public String getMenpaiskilldisplay()
	{
		return menpaiskilldisplay;
	}

	public void setMenpaiskilldisplay(String menpaiskilldisplay)
	{
		this.menpaiskilldisplay = menpaiskilldisplay;
	}

	public long getPk_safe_time()
	{
		return pk_safe_time;
	}

	public void setPk_safe_time(long pk_safe_time)
	{
		this.pk_safe_time = pk_safe_time;
	}

	public int getPk_safe_state()
	{
		return pk_safe_state;
	}

	public void setPk_safe_state(int pk_safe_state)
	{
		this.pk_safe_state = pk_safe_state;
	}

	public long getTianguan_time()
	{
		return tianguan_time;
	}

	public void setTianguan_time(long tianguan_time)
	{
		this.tianguan_time = tianguan_time;
	}

	public String getTianguan_npc()
	{
		return tianguan_npc;
	}

	public void setTianguan_npc(String tianguan_npc)
	{
		this.tianguan_npc = tianguan_npc;
	}

	public int getTianguan_kill_num()
	{
		return tianguan_kill_num;
	}

	public void setTianguan_kill_num(int tianguan_kill_num)
	{
		this.tianguan_kill_num = tianguan_kill_num;
	}

	public int getPoisonCount()
	{
		return poisonCount;
	}

	public void setPoisonCount(int poisonCount)
	{
		this.poisonCount = poisonCount;
	}

	public String getRaceName()
	{
		return ExchangeUtil.getRaceName(this.pRace);
	}
	
	public int getPRace()
	{
		return pRace;
	}

	public void setPRace(int race)
	{
		pRace = race;
	}

	private void setFId(int id)
	{
		fId = id;
		uPartInfoTab.addPersistenceColumn("f_id", fId + "");
	}

	private void setFJob(int job)
	{
		fJob = job;
		uPartInfoTab.addPersistenceColumn("f_job", fJob + "");
	}

	private void setFContribute(int contribute)
	{
		fContribute = contribute;
		uPartInfoTab.addPersistenceColumn("f_contribute", fContribute + "");
	}

	private void setFJoinTime(Date joinTime)
	{
		fJoinTime = joinTime;
		uPartInfoTab.addPersistenceColumn("f_jointime", DateUtil.formatDateToStr(fJoinTime, "yyyy-MM-dd HH:mm:ss"));
	}
	public boolean isPoisoning()
	{
		return isPoisoning;
	}

	public void setPoisoning(boolean isPoisoning)
	{
		this.isPoisoning = isPoisoning;
	}

	public int getAddDamage()
	{
		return addDamage;
	}

	public void setAddDamage(int addDamage)
	{
		this.addDamage = addDamage;
	}

	public int getMenpainpcstate()
	{
		return menpainpcstate;
	}

	public void setMenpainpcstate(int menpainpcstate)
	{
		this.menpainpcstate = menpainpcstate;
	}

	public int getMenpainpcid()
	{
		return menpainpcid;
	}

	public void setMenpainpcid(int menpainpcid)
	{
		this.menpainpcid = menpainpcid;
	}

	public int getZu()
	{
		return zu;
	}

	public void setZu(int zu)
	{
		this.zu = zu;
	}

	public String getChuangong()
	{
		return chuangong;
	}

	public void setChuangong(String chuangong)
	{
		this.chuangong = chuangong;
		uPartInfoTab.addPersistenceColumn("chuangong", chuangong);
	}

	public Date getLast_shoutu_time()
	{
		return last_shoutu_time;
	}

	public void setLast_shoutu_time()
	{
		this.last_shoutu_time = new Date();
	}

	public int getTe_level()
	{
		return te_level;
	}

	public void setTe_level(int te_level)
	{
		this.te_level = te_level;
		uPartInfoTab.addPersistenceColumn("te_level", te_level + "");
	}
	public int getFJob()
	{
		return fJob;
	}

	private void setFTitle(String title)
	{
		fTitle = title;
		uPartInfoTab.addPersistenceColumn("f_title", fTitle);
	}

	public int getFContribute()
	{
		return fContribute;
	}
}
