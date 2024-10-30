package com.ls.model.organize.faction;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ls.ben.dao.faction.FNoticeDao;
import com.ls.ben.dao.faction.FactionDao;
import com.ls.ben.dao.faction.game.FUpgradeMaterialDao;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.organize.Organize;
import com.ls.model.organize.faction.game.FUpgradeMaterial;
import com.ls.model.user.RoleEntity;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.ExchangeUtil;
import com.ls.web.service.player.RoleService;
import com.pm.service.mail.MailInfoService;
import com.pm.vo.mail.MailInfoVO;

/**
 * @author ls
 * 帮派
 */
public class Faction extends Organize
{
	/*********职位权限(族长、长老、护法和族众)******/
	public final static int ZUZHANG = 3;
	public final static int ZHANGLAO = 2;
	public final static int HUFA = 1;
	public final static int ZUZHONG = 0;

	private FactionDao factionDao = new FactionDao();
	
	/***********帮派基本信息*************/
	private int id;
	private String name;
	private int memberNum=1;//当前成员数量
	private int race;
	private int grade=1;
	private int prestige;//声望
	private int citangGrade=1;//祠堂等级:0表示还没创建，大于0表示祠堂等级
	private boolean isDisband=false;//是否处在解散缓冲期，到次日凌晨3时才真正解散
	private Date createTime=null;//创建时间
	private int mGradeTotal;//成员等级总和
	private Date changeZZHTime=null;//下次转让族长的时间
	
	private FStorage fStorage;//帮派仓库
	
	//*******非持久化信息
	private FNotice lastedNotice;//最新的一个公告
	
	/**
	 * 成员升级，增加帮派成员等级总数
	 */
	public void memUpgrade()
	{
		factionDao.updateMGradeTotal(this.id, 1);
	}
	
	/**
	 * 是否是活跃帮派
	 */
	public boolean isActived()
	{
		//判断创建是否超过10天
		if( DateUtil.getDifferDays(createTime, new Date())>10 && this.isFull()==false)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * 得到祠堂当前等级信息
	 * @return
	 */
	public FUpgradeMaterial getCitangInfo()
	{
		FUpgradeMaterialDao  fUpgradeMaterialDao = new FUpgradeMaterialDao();
		return fUpgradeMaterialDao.getOneByType(FUpgradeMaterial.C_UPGRADE, this.citangGrade);
	}
	
	/**
	 * 得到升级材料
	 * @param upgradeType  升级类型
	 * @return
	 */
	public FUpgradeMaterial getUpgradeMaterial(int upgradeType)
	{
		int upgrade_grade = 0;//升级等级
		if( upgradeType==FUpgradeMaterial.F_UPGRADE )
		{
			upgrade_grade = this.grade+1;
		}
		else if( upgradeType==FUpgradeMaterial.C_UPGRADE )
		{
			upgrade_grade = this.citangGrade+1;
		}
		FUpgradeMaterialDao  fUpgradeMaterialDao = new FUpgradeMaterialDao();
		return fUpgradeMaterialDao.getOneByType(upgradeType, upgrade_grade);
	}
	
	/**
	 * 显示
	 */
	public String getDisplay()
	{
		StringBuffer sb = new StringBuffer();
		RoleEntity role=this.getLeader();
		String leaderName="";
		if(role!=null&&this.leaderId!=-1)
		{
			leaderName=role.getName();
		}
		else
		{
			leaderName="暂无(长老可接管该氏族)";
		}
		sb.append(this.getFullName()).append("<br/>");
		sb.append("氏族等级:").append(this.grade).append("级<br/>");
		sb.append("族长:").append(leaderName).append("<br/>");
		sb.append("现有族员:").append(this.memberNum).append("人<br/>");
		sb.append("氏族声望:").append(this.prestige).append("点<br/>");
		sb.append("祠堂等级:").append(this.citangGrade).append("级<br/>");
		return sb.toString();
	}
	
	/**
	 * 最新公告
	 */
	public FNotice getLastedNotice()
	{
		if( lastedNotice==null )
		{
			FNoticeDao fNoticeDao = new FNoticeDao();
			lastedNotice = fNoticeDao.getLastedNotice(this.id);
			if( lastedNotice==null )
			{
				lastedNotice = new FNotice();
			}
		}
		return lastedNotice;
	}
	/**
	 * 删除公告
	 */
	public void delNotice(int id)
	{
		if( lastedNotice!=null && lastedNotice.getId()==id )
		{
			lastedNotice=null;
		}
		FNoticeDao fNoticDao = new FNoticeDao();
		fNoticDao.delById(id);
	}
	
	/**
	 * 发布公告
	 */
	public void publishNotice(FNotice notice)
	{
		FNoticeDao fNoticDao = new FNoticeDao();
		fNoticDao.add(notice);
		this.lastedNotice = notice;
	}
	
	/**
	 * 得到种族名字
	 */
	public String getFullName()
	{
		return this.name+"("+ExchangeUtil.getRaceName(race)+")";
	}
	
	/**
	 * 更新声望
	 * @param update_prestige
	 * @return
	 */
	public boolean updatePrestige(int update_prestige)
	{
		if( update_prestige<0 && this.prestige<-update_prestige )//声望不足
		{
			return false;
		}
		this.prestige+=update_prestige;
		this.save();
		return true;
	}
	
	/**
	 * 升级
	 */
	public String upgrade()
	{
		this.grade++;
		this.save();
		return null;
	}
	/**
	 * 升级
	 */
	public String upgradeCitang()
	{
		this.citangGrade++;
		this.save();
		return null;
	}
	
	/**
	 * 判断是否可以加入帮派成员
	 * @param operater                执行者
	 * @param member			      加入的成员
	 * @return
	 */
	public String isAddMember( RoleEntity member)
	{
		if( member==null )
		{
			return "无该角色";
		}
		if(member.getIsRookie())
		{
			return member.getName()+"处于新手引导阶段不可邀请加入帮派！";
		}
		if( member!=null && member.getBasicInfo().getFaction()!=null )
		{
			return member.getName()+"已加入其他帮派";
		}
		
		if( member.getBasicInfo().getPRace()!=this.race)
		{
			return "种族不符";
		}
		
		if( this.isFull() )
		{
			return "帮派人数已满";
		}
		return null;
	}
	
	/**
	 * 添加帮派成员
	 * @param member		 加入的成员
	 * @return
	 */
	public String addMember(RoleEntity member)
	{
		String hint = this.isAddMember( member);
		if( hint!=null )
		{
			return hint;
		}
		
		member.getBasicInfo().jionFaction(this.id, ZUZHONG);
		memberNum++;
		this.mGradeTotal = member.getBasicInfo().getGrade();
		this.save();
		return null;
	}

	
	@Override
	public String changeLeader(RoleEntity newLeader)
	{
		if( newLeader==null )
		{
			return "该玩家不存在,不能转让族长";
		}
		
		//判断是否到转让的冷却时间，时间为24小时
		String hint = DateUtil.returnTimeStr(this.changeZZHTime);
		if( hint!=null )
		{
			return "现在不能转让族长,距下次转让族长还有"+hint;
		}
		
		//原族长降为长老
		this.getLeader().getBasicInfo().changeFJob(Faction.ZUZHONG);
		//更新新的族长
		newLeader.getBasicInfo().changeFJob(Faction.ZUZHANG);
		this.leaderId = newLeader.getPPk();
		FactionDao factionDao = new FactionDao();
		
		Calendar cur_time = Calendar.getInstance();
		cur_time.add(Calendar.HOUR, 24);//得到下次可以转让氏族的时间
		
		this.changeZZHTime = cur_time.getTime();
		factionDao.updateChangeZZHTime(this.id);
		return null;
	}

	@Override
	public String create(RoleEntity leader,String faction_name)
	{
		if( leader==null )
		{
			return "创建错误";
		}
		
		if( leader.getBasicInfo().getFaction()!=null )
		{
			return "你已加入其他帮派";
		}
		
		int leader_id = leader.getBasicInfo().getPPk();
		
		this.leaderId = leader_id;
		this.name = faction_name;
		this.race = leader.getBasicInfo().getPRace();
		this.mGradeTotal = leader.getBasicInfo().getGrade();
		
		factionDao.add(this);
		if( this.id==0 )
		{
			return "创建失败";
		}

		leader.getBasicInfo().jionFaction(id,ZUZHANG);
		
		member_list.put(leader_id, leader);
		return null;
	}

	@Override
	public String delMember(RoleEntity member)
	{
		if( member!=null )
		{
			if( member.getBasicInfo().getFJob()==Faction.ZUZHANG)
			{
				//如果是族长离开
				PartInfoDao partInfoDao = new PartInfoDao();
				List<Integer> list = partInfoDao.getUpZhanglaoListByFId(getId());
				MailInfoService mailInfoService = new MailInfoService();
				MailInfoVO mail = new MailInfoVO();
				mail.setMailType(MailInfoService.F_DISBAND_MAIL);
				mail.setSendPk(getId());
				mail.setTitle(getName()+"即将解散!");
				mail.setContent("您的氏族正处理解散过程,若24小时内,您仍未改变心意,则您的氏族将彻底解散!");
				for(Integer p_pk:list )
				{
					/**给玩家发送一个加入帮派的邮件  */
					mail.setReceivePk(p_pk);
					mailInfoService.sendMail(mail);
				}
				this.leaderId=-1;
			}
			member.getBasicInfo().leaveFaction();
			memberNum--;
			this.mGradeTotal-=member.getBasicInfo().getGrade();
			this.isDisband=true;
			this.save();
		}
		return null;
	}
	/**
	 * 接管帮派
	 */
	public void assume(RoleEntity operater)
	{
		//如果是长老接管
		if( operater.getBasicInfo().getFaction().getId()==this.id && operater.getBasicInfo().getFJob()==Faction.ZHANGLAO )
		{
			RoleEntity role= this.getLeader();
			if(role!=null)
			{
				role.getBasicInfo().leaveFaction();
				this.memberNum--;
				this.mGradeTotal-=role.getBasicInfo().getGrade();
			}
		}
		operater.getBasicInfo().changeFJob(Faction.ZUZHANG);
		this.leaderId=operater.getPPk();
		this.isDisband = false;
		this.save();
	}
	
	/**
	 * 解散
	 */
	public void disband()
	{
		this.isDisband = true;
		this.save();
	}

	@Override
	public RoleEntity getLeader()
	{
		if( leaderId==-1 )
		{
			PartInfoDao partInfoDao = new PartInfoDao();
			leaderId = partInfoDao.getFactinLeader(this.id);
		}
		if(leaderId==-1)
		{
			return null;
		}
		return RoleService.getRoleInfoById(leaderId+"");
	}

	@Override
	public List<RoleEntity> getMemberList()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 是否可以增加最大长老的数量
	 */
	public String isChangeJob(int job)
	{
		PartInfoDao partInfoDao = new PartInfoDao();
		int cur_job_num = partInfoDao.getCurFJobNum(this.id, job);//当前职业的数量
		int max_job_num = this.getMaxJobNum(job);
		if( cur_job_num>=max_job_num )
		{
			return ExchangeUtil.getFJobName(job)+"的数量已满";
		}
		return null;
	}

	/**
	 * 得到职务的最大数量
	 * @return
	 */
	public int getMaxJobNum(int job)
	{
		switch(job)
		{
			case ZUZHANG:return 1;
			case ZHANGLAO:
				if( this.grade==1)
				{
					return 2;
				}
				else if( this.grade==2)
				{
					return 3;
				}
				break;
			case HUFA:
				if( this.grade==1)
				{
					return 5;
				}
				else if( this.grade==2)
				{
					return 10;
				}
				break;
			case ZUZHONG:return this.getMaxMemberNum();
		}
		return 0;
	}
	
	/**
	 * 得到当前帮派的最大人数
	 */
	public int getMaxMemberNum()
	{
		switch(this.grade)
		{
			case 1:return 20;
			case 2:return 30;
		}
		return 0;
	}
	
	/**
	 * 得到当前成员数量
	 */
	@Override
	public int getMemberNum()
	{
		return memberNum;
	}

	/**
	 * 判断帮派是否已满
	 */
	public boolean isFull()
	{
		if( getMemberNum()>= getMaxMemberNum() )
		{
			return true;
		}
		return false;
	}

	
	
	@Override
	public boolean isJoin(RoleEntity member)
	{
		return false;
	}

	
	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public int getGrade()
	{
		return grade;
	}

	public int getPrestige()
	{
		return prestige;
	}

	public int getCitangGrade()
	{
		return citangGrade;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setGrade(int grade)
	{
		this.grade = grade;
	}

	public void setPrestige(int prestige)
	{
		this.prestige = prestige;
	}

	public void setCitangGrade(int citangGrade)
	{
		this.citangGrade = citangGrade;
	}

	public int getRace()
	{
		return race;
	}

	public void setRace(int race)
	{
		this.race = race;
	}

	public void save()
	{
		factionDao.save(this);
	}

	public void setMemberNum(int memberNum)
	{
		this.memberNum = memberNum;
	}
	public boolean getIsDisband()
	{
		return isDisband;
	}
	public void setIsDisband(boolean isDisband)
	{
		this.isDisband = isDisband;
	}
	public FStorage getFStorage()
	{
		if( fStorage==null )
		{
			FactionDao factionDao = new FactionDao();
			String material_str = factionDao.getStorageStr(id);
			fStorage = new FStorage(id,material_str);
		}
		return fStorage;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}


	public int getMGradeTotal()
	{
		return mGradeTotal;
	}

	public void setMGradeTotal(int gradeTotal)
	{
		if( gradeTotal<0 )
		{
			gradeTotal = 0;
		}
		mGradeTotal = gradeTotal;
	}


	public void setChangeZZHTime(Date changeZZHTime)
	{
		this.changeZZHTime = changeZZHTime;
	}
}
