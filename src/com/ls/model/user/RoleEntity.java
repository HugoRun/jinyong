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
 * @author ls 功能:玩家角色的信息的容器
 */
public class RoleEntity implements Serializable
{
	private StateInfo stateInfo;//状态
	private BasicInfo basicInfo;
	private RoleSkillInfo roleSkillInfo;//技能
	private SettingInfo settingInfo;//设置信息
	private RoleShortCutInfo roleShortCutInfo;//快捷键
	private RolePetInfo rolePetInfo;
	private RoleTaskInfo taskInfo; //任务
	private RoleSystemInfo roleSystemInfo;
	private RoleTitleSet roleTitle;//称号
	private EquipOnBody equipOnBody;//身上的装备
	private MountSet mountSet;//坐骑
	private EquipProduct equipProduct;
	private StoneProduct stoneProduct;
	
	private PKState pKState;//PK状态
	private DropSet dropSet;//掉落集合
	
	private EventManager eventManager;//非瞬时时间管理器
	
	private RoleCounter counter;//计数器
	
	/**
	 * 初始化构造用户信息
	 * @param p_pk
	 * @throws Exception 
	 */
	public RoleEntity(int p_pk) throws Exception
	{
		basicInfo = new BasicInfo(p_pk);
		stateInfo = new StateInfo(p_pk);
	}
	
	/**
	 * 角色重置
	 */
	public void reset()
	{
		//如果是100级的新手登陆
		if( this.getBasicInfo().getPlayer_state_by_new()==1 && this.getGrade()==GameConfig.getGradeUpperHighLimit() )
		{
			basicInfo.reset();//属性重置
			this.getRoleShortCutInfo().clearShortcut();//清空快捷键
			this.getRoleSkillInfo().clear();//清空技能
			this.getTitleSet().init();//初始化称号
			this.getEquipOnBody().clear();//清空身上所穿装备
			this.getTaskInfo().clear();//清除所接的所有任务
			
			//清空道具和装备
			PlayerEquipDao playerEquipDao = new PlayerEquipDao();
			PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
			playerEquipDao.clear(this.getPPk());
			playerPropGroupDao.clear(this.getPPk());
			
			RankService rankService = new RankService();
			//判断是否有角色相关排行信息
			if(rankService.isExist(this.getPPk())<1)
			{
				rankService.insert(this.getPPk(), this.getName(),getGrade());
			}
			//系统赠送给角色免费的一级坐骑
			RoleService roleService = new RoleService();
			roleService.addMountsForPart(this);
		}
	}
	
	
	/**
	 * 得到基本描述信息
	 */
	public String getBasicDes()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(this.getBasicInfo().getRaceName());
		sb.append(",").append(this.getBasicInfo().getGrade()).append("级");
		if( this.getBasicInfo().getFaction()!=null )
		{
			sb.append(",").append(this.getBasicInfo().getFactionName());
			sb.append(",").append(ExchangeUtil.getFJobName(this.getBasicInfo().getFJob()));
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * 得到详细描述信息
	 */
	public String getDisplay()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.getFullName()).append("<br/>");
		sb.append("称号:").append(getTitleSet().getShowTitleName()).append("<br/>");
		sb.append("性别:").append(this.getBasicInfo().getSexName()).append("<br/>");
		sb.append("等级:").append(this.getBasicInfo().getGrade()).append("级<br/>");
		sb.append("帮派:").append(this.getBasicInfo().getFactionName()).append("<br/>");
		sb.append("是否可PK:").append(this.getBasicInfo().getPkSwitchDes()).append("<br/>");
		sb.append("PK点数:").append(this.getBasicInfo().getEvilValue()).append("<br/>");
		return sb.toString();
	}
	
	/**
	 * 得到描述信息
	 */
	public String getDes()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.getFullName()).append("<br/>");
		sb.append("称号:").append(getTitleSet().getShowTitleName()).append("<br/>");
		sb.append("性别:").append(this.getBasicInfo().getSexName()).append("<br/>");
		sb.append("等级:").append(this.getBasicInfo().getGrade()).append("级<br/>");
		sb.append("帮派:").append(this.getBasicInfo().getFactionName()).append("<br/>");
		sb.append("是否可PK:").append(this.getBasicInfo().getPkSwitchDes()).append("<br/>");
		sb.append("PK点数:").append(this.getBasicInfo().getEvilValue()).append("<br/>");
		return sb.toString();
	}
	
	/**
	 * 得到角色全名
	 * @return
	 */
	public String getFullName()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.getName());
		sb.append(ShituConstant.getTeLevel1(this.getBasicInfo().getTe_level()));
		if (this.getBasicInfo().getEvilValue() > GameArgs.YELLOW_NAME_VALUE && this.getBasicInfo().getEvilValue() < GameArgs.RED_NAME_VALUE) 
		{
			sb.append("(黄)");
		}
		else if (this.getBasicInfo().getEvilValue() >= GameArgs.RED_NAME_VALUE) 
		{
			sb.append("(红)");
		}
		
		return sb.toString();
	}
	
	/**
	 * 是否在线
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
	 * 角色正常登陆
	 */
	public void login(HttpServletRequest request )
	{
		loginDoSomethings(request);
		this.reset();// 角色信息重置
	}
	
	/**
	 * 新手登陆
	 */
	public void rookieLogin(HttpServletRequest request )
	{
		loginDoSomethings(request);
	}
	
	/**
	 * 是否是新手
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
	 * 登陆时需做的一些处理
	 */
	private void loginDoSomethings(HttpServletRequest request)
	{
		int p_pk = this.getPPk();
		LoginService.online_role.put(this.getUPk(), p_pk);
		
		this.getStateInfo().login(request.getSession());
	
		//标记账号登陆
		LoginDao loginDao = new LoginDao();
		loginDao.updateState(this.getUPk()+"", request.getRemoteHost());
		
		//初始化视野
		initView(this);
		//初始化角色弹出式消息
		new UMessageInfoDao().initPopMsg(p_pk);
		// 给玩家好友表登陆做标记
		FriendDAO friendDAO = new FriendDAO();
		friendDAO.updateFriendOnline(p_pk, 1);
		//玩家登陆给玩家发送称号消息
		getTitleSet().sendTitleSysInfo(this);
		// 初始化npc
		AttacckCache attacckCache = new AttacckCache();
		attacckCache.initNpc(p_pk);
		
		// 将登陆信息加入到登陆信息统计表中
		StatisticsService statisticsService = new StatisticsService();
		statisticsService.recordPersonLoginInfo(p_pk+ "", getBasicInfo().getGrade());

		// 更新登陆状态
		PartInfoDao partInfoDao = new PartInfoDao();
		partInfoDao.updateLoginState(p_pk + "", 1);

		//删除玩家的秘境信息
		UnchartedRoomService us = new UnchartedRoomService();
		us.updateOfflinePlayerUnchartedRoomState(this);
		//处理玩家上线后的秘境地图信息
		us.updateOfflinePlayerTianguanState(this);
		
		//删除玩家门派地图信息
		MenpaiContestService ms = new MenpaiContestService();
		ms.updateOfflinePlayerMenpaiContestState(this);
		
		// 登录处理
		PlayerStatisticsService playerStatisticsService = new PlayerStatisticsService();
		playerStatisticsService.playerStatisticsFlow(this);
		
		//给玩家发放奖励
		//PlayerGetGamePrizeService ps = new PlayerGetGamePrizeService();
		//ps.getNewYearPrize(this);
		
		SceneVO cur_scene = this.getBasicInfo().getSceneInfo();
		int map_type = cur_scene.getMap().getMapType();
		if( map_type==MapType.INSTANCE )
		{
			//如果在副本区域则传送的中心点
			this.getBasicInfo().updateSceneId(cur_scene.getMap().getBarea().getBareaPoint()+"");
		}
	}
	
	/**
	 * 初始化玩家视野
	 */
	private void initView(RoleEntity roleInfo)
	{
		PlayerService playerService = new PlayerService();
		String cur_view = null;
		String scene_id = roleInfo.getBasicInfo().getSceneId();
		cur_view = playerService.getCurrentView(roleInfo.getPPk(), Integer.parseInt(scene_id));

		roleInfo.getStateInfo().setView(cur_view);// 更新玩家视野

		ViewCache viewCache = new ViewCache();
		viewCache.remove(cur_view, roleInfo);// 移除玩家之前的视野
	}
	
	/**
	 * 角色退出
	 */
	public void logout()
	{
		LoginService.online_role.remove(getUPk());
		this.getStateInfo().setCurState(PlayerState.OUTLINE);
		if( this.pKState!=null )//初始化PK状态
		{
			this.pKState.outlineNotify();
		}
		if( this.dropSet!=null )//初始化掉落表
		{
			this.dropSet.clearExpAndMoney();
		}
		GroupModel group_info = getStateInfo().getGroupInfo();
		if( group_info!=null )//如果在组队状态则离开队伍
		{
			group_info.leaveGroup(this);
		}
		
		getBasicInfo().consumeEvilValueByTime();//消除罪恶值
	}
	
	/**
	 * 得到角色等级
	 */
	public int getGrade()
	{
		return this.basicInfo.getGrade();
	}
	/**
	 * 得到角色名字
	 */
	public String getName()
	{
		return this.basicInfo.getName();
	}
	/**
	 * 得到角色id
	 * @param p_pk
	 */
	public int getPPk()
	{
		return this.basicInfo.getPPk();
	}
	/**
	 * 得到账号id
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
	 * 持久化数据
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
	 * 是否可以跟otherPK
	 * @return
	 */
	public boolean isPKToOther(RoleEntity other)
	{
		boolean result = false;
		
		AvoidPkPropService avoidPkPropService = new AvoidPkPropService();
		
		int me_grade = this.getGrade();
		int other_grade = other.getGrade();

		/*通用：
		1.	等级相差10级以上不能PK；相差10级可以PK
		2.	30级以下角色不能PK；大于等于30级角色才可以PK*/
		if( me_grade>=30 && Math.abs(me_grade-other_grade)<=10 )
		{
			boolean me_is_redname = this.isRedname();//me是否是红名
			boolean other_is_redname = other.isRedname();//other是否是红名
			//自己或对方有一个是红名都可攻击
			if( me_is_redname || other_is_redname )
			{
				return true;
			}
			else
			{
				SceneVO cur_scene = this.getBasicInfo().getSceneInfo();
				
				boolean me_is_pk_protected = avoidPkPropService.isAvoidPkPropTime(this.getPPk());//自己是否有PK道具保护
				if( me_is_pk_protected==false )//自己不受PK道具保护
				{
					boolean other_is_pk_protected = avoidPkPropService.isAvoidPkPropTime(other.getPPk());//other是否有PK道具保护
					boolean is_same_race = this.getBasicInfo().getPRace()==other.getBasicInfo().getPRace()?true:false;//是否是相同种族
					//同种族PK
					if(  is_same_race && cur_scene.isAllowPK() && other_is_pk_protected==false && this.getBasicInfo().getPkSwitch()==2 && other.getBasicInfo().getPkSwitch()==2)
					{
						return true;
					}
					//不同种族PK
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
	 * 是否是红名
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
