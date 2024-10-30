package com.ls.model.user;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.ben.dao.friend.FriendDAO;
import com.ben.shitu.model.ShituConstant;
import com.ls.ben.cache.dynamic.manual.attack.AttacckCache;
import com.ls.ben.cache.dynamic.manual.view.ViewCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.dao.login.LoginDao;
import com.ls.ben.dao.system.UMessageInfoDao;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.drop.DropSet;
import com.ls.model.equip.ChangeEquipWX;
import com.ls.model.equip.EquipOnBody;
import com.ls.model.equip.EquipProduct;
import com.ls.model.equip.StoneProduct;
import com.ls.model.equip.UpgradeEquip;
import com.ls.model.event.EventManager;
import com.ls.model.fight.PKState;
import com.ls.model.group.GroupModel;
import com.ls.model.property.RolePetInfo;
import com.ls.model.property.RoleShortCutInfo;
import com.ls.model.property.RoleSkillInfo;
import com.ls.model.property.RoleSystemInfo;
import com.ls.model.property.RoleTaskInfo;
import com.ls.model.property.RoleTitleSet;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.util.ExchangeUtil;
import com.ls.web.service.login.LoginService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.rank.RankService;
import com.lw.service.UnchartedRoom.UnchartedRoomService;
import com.lw.service.menpaicontest.MenpaiContestService;
import com.lw.service.player.PlayerStatisticsService;
import com.pm.service.statistics.StatisticsService;
import com.pub.GameArgs;
import com.web.service.avoidpkprop.AvoidPkPropService;

/**
 * @author ls ����:��ҽ�ɫ����Ϣ������
 */
public class RoleEntity implements Serializable
{
	private StateInfo stateInfo;//״̬
	private BasicInfo basicInfo;
	private RoleSkillInfo roleSkillInfo;//����
	private SettingInfo settingInfo;//������Ϣ
	private RoleShortCutInfo roleShortCutInfo;//��ݼ�
	private RolePetInfo rolePetInfo;
	private RoleTaskInfo taskInfo; //����
	private RoleSystemInfo roleSystemInfo;
	private RoleTitleSet roleTitle;//�ƺ�
	private EquipOnBody equipOnBody;//���ϵ�װ��
	private MountSet mountSet;//����
	private EquipProduct equipProduct;
	private StoneProduct stoneProduct;
	
	private PKState pKState;//PK״̬
	private DropSet dropSet;//���伯��
	
	private EventManager eventManager;//��˲ʱʱ�������
	
	private RoleCounter counter;//������
	
	/**
	 * ��ʼ�������û���Ϣ
	 * @param p_pk
	 * @throws Exception 
	 */
	public RoleEntity(int p_pk) throws Exception
	{
		basicInfo = new BasicInfo(p_pk);
		stateInfo = new StateInfo(p_pk);
	}
	
	/**
	 * ��ɫ����
	 */
	public void reset()
	{
		//�����100�������ֵ�½
		if( this.getBasicInfo().getPlayer_state_by_new()==1 && this.getGrade()==GameConfig.getGradeUpperHighLimit() )
		{
			basicInfo.reset();//��������
			this.getRoleShortCutInfo().clearShortcut();//��տ�ݼ�
			this.getRoleSkillInfo().clear();//��ռ���
			this.getTitleSet().init();//��ʼ���ƺ�
			this.getEquipOnBody().clear();//�����������װ��
			this.getTaskInfo().clear();//������ӵ���������
			
			//��յ��ߺ�װ��
			PlayerEquipDao playerEquipDao = new PlayerEquipDao();
			PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
			playerEquipDao.clear(this.getPPk());
			playerPropGroupDao.clear(this.getPPk());
			
			RankService rankService = new RankService();
			//�ж��Ƿ��н�ɫ���������Ϣ
			if(rankService.isExist(this.getPPk())<1)
			{
				rankService.insert(this.getPPk(), this.getName(),getGrade());
			}
			//ϵͳ���͸���ɫ��ѵ�һ������
			RoleService roleService = new RoleService();
			roleService.addMountsForPart(this);
		}
	}
	
	
	/**
	 * �õ�����������Ϣ
	 */
	public String getBasicDes()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(this.getBasicInfo().getRaceName());
		sb.append(",").append(this.getBasicInfo().getGrade()).append("��");
		if( this.getBasicInfo().getFaction()!=null )
		{
			sb.append(",").append(this.getBasicInfo().getFactionName());
			sb.append(",").append(ExchangeUtil.getFJobName(this.getBasicInfo().getFJob()));
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * �õ���ϸ������Ϣ
	 */
	public String getDisplay()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.getFullName()).append("<br/>");
		sb.append("�ƺ�:").append(getTitleSet().getShowTitleName()).append("<br/>");
		sb.append("�Ա�:").append(this.getBasicInfo().getSexName()).append("<br/>");
		sb.append("�ȼ�:").append(this.getBasicInfo().getGrade()).append("��<br/>");
		sb.append("����:").append(this.getBasicInfo().getFactionName()).append("<br/>");
		sb.append("�Ƿ��PK:").append(this.getBasicInfo().getPkSwitchDes()).append("<br/>");
		sb.append("PK����:").append(this.getBasicInfo().getEvilValue()).append("<br/>");
		return sb.toString();
	}
	
	/**
	 * �õ�������Ϣ
	 */
	public String getDes()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.getFullName()).append("<br/>");
		sb.append("�ƺ�:").append(getTitleSet().getShowTitleName()).append("<br/>");
		sb.append("�Ա�:").append(this.getBasicInfo().getSexName()).append("<br/>");
		sb.append("�ȼ�:").append(this.getBasicInfo().getGrade()).append("��<br/>");
		sb.append("����:").append(this.getBasicInfo().getFactionName()).append("<br/>");
		sb.append("�Ƿ��PK:").append(this.getBasicInfo().getPkSwitchDes()).append("<br/>");
		sb.append("PK����:").append(this.getBasicInfo().getEvilValue()).append("<br/>");
		return sb.toString();
	}
	
	/**
	 * �õ���ɫȫ��
	 * @return
	 */
	public String getFullName()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.getName());
		sb.append(ShituConstant.getTeLevel1(this.getBasicInfo().getTe_level()));
		if (this.getBasicInfo().getEvilValue() > GameArgs.YELLOW_NAME_VALUE && this.getBasicInfo().getEvilValue() < GameArgs.RED_NAME_VALUE) 
		{
			sb.append("(��)");
		}
		else if (this.getBasicInfo().getEvilValue() >= GameArgs.RED_NAME_VALUE) 
		{
			sb.append("(��)");
		}
		
		return sb.toString();
	}
	
	/**
	 * �Ƿ�����
	 * @return
	 */
	public boolean isOnline()
	{
		if( PlayerState.OUTLINE == this.getStateInfo().getCurState())
		{
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * ��ɫ������½
	 */
	public void login(HttpServletRequest request )
	{
		loginDoSomethings(request);
		this.reset();// ��ɫ��Ϣ����
	}
	
	/**
	 * ���ֵ�½
	 */
	public void rookieLogin(HttpServletRequest request )
	{
		loginDoSomethings(request);
	}
	
	/**
	 * �Ƿ�������
	 * @return
	 */
	public boolean getIsRookie()
	{
		if( this.getBasicInfo().getPlayer_state_by_new()==1 )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * ��½ʱ������һЩ����
	 */
	private void loginDoSomethings(HttpServletRequest request)
	{
		int p_pk = this.getPPk();
		LoginService.online_role.put(this.getUPk(), p_pk);
		
		this.getStateInfo().login(request.getSession());
	
		//����˺ŵ�½
		LoginDao loginDao = new LoginDao();
		loginDao.updateState(this.getUPk()+"", request.getRemoteHost());
		
		//��ʼ����Ұ
		initView(this);
		//��ʼ����ɫ����ʽ��Ϣ
		new UMessageInfoDao().initPopMsg(p_pk);
		// ����Һ��ѱ��½�����
		FriendDAO friendDAO = new FriendDAO();
		friendDAO.updateFriendOnline(p_pk, 1);
		//��ҵ�½����ҷ��ͳƺ���Ϣ
		getTitleSet().sendTitleSysInfo(this);
		// ��ʼ��npc
		AttacckCache attacckCache = new AttacckCache();
		attacckCache.initNpc(p_pk);
		
		// ����½��Ϣ���뵽��½��Ϣͳ�Ʊ���
		StatisticsService statisticsService = new StatisticsService();
		statisticsService.recordPersonLoginInfo(p_pk+ "", getBasicInfo().getGrade());

		// ���µ�½״̬
		PartInfoDao partInfoDao = new PartInfoDao();
		partInfoDao.updateLoginState(p_pk + "", 1);

		//ɾ����ҵ��ؾ���Ϣ
		UnchartedRoomService us = new UnchartedRoomService();
		us.updateOfflinePlayerUnchartedRoomState(this);
		//����������ߺ���ؾ���ͼ��Ϣ
		us.updateOfflinePlayerTianguanState(this);
		
		//ɾ��������ɵ�ͼ��Ϣ
		MenpaiContestService ms = new MenpaiContestService();
		ms.updateOfflinePlayerMenpaiContestState(this);
		
		// ��¼����
		PlayerStatisticsService playerStatisticsService = new PlayerStatisticsService();
		playerStatisticsService.playerStatisticsFlow(this);
		
		//����ҷ��Ž���
		//PlayerGetGamePrizeService ps = new PlayerGetGamePrizeService();
		//ps.getNewYearPrize(this);
		
		SceneVO cur_scene = this.getBasicInfo().getSceneInfo();
		int map_type = cur_scene.getMap().getMapType();
		if( map_type==MapType.INSTANCE )
		{
			//����ڸ����������͵����ĵ�
			this.getBasicInfo().updateSceneId(cur_scene.getMap().getBarea().getBareaPoint()+"");
		}
	}
	
	/**
	 * ��ʼ�������Ұ
	 */
	private void initView(RoleEntity roleInfo)
	{
		PlayerService playerService = new PlayerService();
		String cur_view = null;
		String scene_id = roleInfo.getBasicInfo().getSceneId();
		cur_view = playerService.getCurrentView(roleInfo.getPPk(), Integer.parseInt(scene_id));

		roleInfo.getStateInfo().setView(cur_view);// ���������Ұ

		ViewCache viewCache = new ViewCache();
		viewCache.remove(cur_view, roleInfo);// �Ƴ����֮ǰ����Ұ
	}
	
	/**
	 * ��ɫ�˳�
	 */
	public void logout()
	{
		LoginService.online_role.remove(getUPk());
		this.getStateInfo().setCurState(PlayerState.OUTLINE);
		if( this.pKState!=null )//��ʼ��PK״̬
		{
			this.pKState.outlineNotify();
		}
		if( this.dropSet!=null )//��ʼ�������
		{
			this.dropSet.clearExpAndMoney();
		}
		GroupModel group_info = getStateInfo().getGroupInfo();
		if( group_info!=null )//��������״̬���뿪����
		{
			group_info.leaveGroup(this);
		}
		
		getBasicInfo().consumeEvilValueByTime();//�������ֵ
	}
	
	/**
	 * �õ���ɫ�ȼ�
	 */
	public int getGrade()
	{
		return this.basicInfo.getGrade();
	}
	/**
	 * �õ���ɫ����
	 */
	public String getName()
	{
		return this.basicInfo.getName();
	}
	/**
	 * �õ���ɫid
	 * @param p_pk
	 */
	public int getPPk()
	{
		return this.basicInfo.getPPk();
	}
	/**
	 * �õ��˺�id
	 * @param p_pk
	 */
	public int getUPk()
	{
		return this.basicInfo.getUPk();
	}
	
	public StateInfo getStateInfo()
	{
		if( stateInfo==null )
		{
			stateInfo = new StateInfo(this.getPPk());
		}
		return stateInfo;
	}

	public BasicInfo getBasicInfo()
	{
		return basicInfo;
	}

	public SettingInfo getSettingInfo()
	{
		if( this.settingInfo==null )
		{
			settingInfo = new SettingInfo(getPPk());
		}
		return settingInfo;
	}

	public RoleSkillInfo getRoleSkillInfo()
	{
		if( this.roleSkillInfo==null )
		{
			this.roleSkillInfo = new RoleSkillInfo(this.getPPk());
		}
		return roleSkillInfo;
	}

	public RoleShortCutInfo getRoleShortCutInfo()
	{
		if( this.roleShortCutInfo==null )
		{
			this.roleShortCutInfo = new RoleShortCutInfo(this.getPPk());
		}
		return roleShortCutInfo;
	}

	public RolePetInfo getRolePetInfo()
	{
		if( this.rolePetInfo==null )
		{
			this.rolePetInfo = new RolePetInfo(getPPk());
		}
		return rolePetInfo;
	}

	public RoleTaskInfo getTaskInfo()
	{
		if( this.taskInfo==null )
		{
			this.taskInfo = new RoleTaskInfo(this.getPPk());
		}
		return taskInfo;
	}
 
	public RoleSystemInfo getRoleSystemInfo()
	{
		if( this.roleSystemInfo==null )
		{
			this.roleSystemInfo = new RoleSystemInfo(getPPk());
		}
		return roleSystemInfo;
	}
	
	public RoleTitleSet getTitleSet()
	{ 
		if( this.roleTitle==null )
		{
			this.roleTitle = new RoleTitleSet(this.getPPk());
		}
		return roleTitle;
	}
	
	public EquipOnBody getEquipOnBody()
	{
		if( this.equipOnBody==null )
		{
			this.equipOnBody = new EquipOnBody(this.getPPk());
		}
		return equipOnBody;
	}
	
	public void createEquipProduct( int product_type )
	{
		switch( product_type)
		{
			case EquipProduct.upgrade:
				equipProduct = new UpgradeEquip(this.getPPk());break;
			case EquipProduct.change_wx:
				equipProduct = new ChangeEquipWX(this.getPPk());break;
		}
	}
	
	public StoneProduct getStoneProduct()
	{
		if( stoneProduct==null )
		{
			stoneProduct = new StoneProduct(this.getPPk());
		}
		return stoneProduct;
	}
	public EquipProduct getEquipProduct()
	{
		return equipProduct;
	}

	public EventManager getEventManager()
	{
		if( eventManager==null )
		{
			eventManager = new EventManager();
		}
		return eventManager;
	}

	public DropSet getDropSet()
	{
		if( dropSet==null )
		{
			dropSet = new DropSet();
		}
		return dropSet;
	}
	
	/**
	 * �־û�����
	 */
	public void save()
	{
		this.getBasicInfo().save();
		if( this.settingInfo!=null )
		{
			this.settingInfo.save();
		}
	}

	public MountSet getMountSet()
	{
		if( mountSet==null||mountSet.getCurMId()==-1 )
		{
			mountSet = new MountSet(this.getPPk());
		}
		return mountSet;
	}

	public PKState getPKState()
	{
		if( pKState==null )
		{
			pKState = new PKState(this.getPPk());
		}
		return pKState;
	}
	
	/**
	 * �Ƿ���Ը�otherPK
	 * @return
	 */
	public boolean isPKToOther(RoleEntity other)
	{
		boolean result = false;
		
		AvoidPkPropService avoidPkPropService = new AvoidPkPropService();
		
		int me_grade = this.getGrade();
		int other_grade = other.getGrade();

		/*ͨ�ã�
		1.	�ȼ����10�����ϲ���PK�����10������PK
		2.	30�����½�ɫ����PK�����ڵ���30����ɫ�ſ���PK*/
		if( me_grade>=30 && Math.abs(me_grade-other_grade)<=10 )
		{
			boolean me_is_redname = this.isRedname();//me�Ƿ��Ǻ���
			boolean other_is_redname = other.isRedname();//other�Ƿ��Ǻ���
			//�Լ���Է���һ���Ǻ������ɹ���
			if( me_is_redname || other_is_redname )
			{
				return true;
			}
			else
			{
				SceneVO cur_scene = this.getBasicInfo().getSceneInfo();
				
				boolean me_is_pk_protected = avoidPkPropService.isAvoidPkPropTime(this.getPPk());//�Լ��Ƿ���PK���߱���
				if( me_is_pk_protected==false )//�Լ�����PK���߱���
				{
					boolean other_is_pk_protected = avoidPkPropService.isAvoidPkPropTime(other.getPPk());//other�Ƿ���PK���߱���
					boolean is_same_race = this.getBasicInfo().getPRace()==other.getBasicInfo().getPRace()?true:false;//�Ƿ�����ͬ����
					//ͬ����PK
					if(  is_same_race && cur_scene.isAllowPK() && other_is_pk_protected==false && this.getBasicInfo().getPkSwitch()==2 && other.getBasicInfo().getPkSwitch()==2)
					{
						return true;
					}
					//��ͬ����PK
					else if( is_same_race==false && other_is_pk_protected==false )
					{
						return true;
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * �Ƿ��Ǻ���
	 * @return
	 */
	public boolean isRedname()
	{
		return this.getBasicInfo().getEvilValue()>=GameArgs.RED_NAME_VALUE?true:false;
	}

	public RoleCounter getCounter()
	{
		if( counter==null )
		{
			counter = new RoleCounter();
		}
		return counter;
	}
}
