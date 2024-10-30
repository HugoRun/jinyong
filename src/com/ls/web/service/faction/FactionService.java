package com.ls.web.service.faction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.faction.FApplyInfoDao;
import com.ls.ben.dao.faction.FNoticeDao;
import com.ls.ben.dao.faction.FactionDao;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.log.GameLogManager;
import com.ls.model.organize.faction.FApplyInfo;
import com.ls.model.organize.faction.FNotice;
import com.ls.model.organize.faction.Faction;
import com.ls.model.organize.faction.FactionRecruit;
import com.ls.model.organize.faction.game.FUpgradeMaterial;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.util.ExchangeUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.prop.PropService;
import com.ls.web.service.log.DataErrorLog;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.validate.ValidateService;
import com.pm.service.mail.MailInfoService;
import com.pm.vo.mail.MailInfoVO;

/**
 * @author ls
 * 帮派相关逻辑
 */
public class FactionService
{
	//帮派信息缓存
	private static Map<Integer,Faction> faction_cache = new HashMap<Integer,Faction>(50);
	
	public static int MAX_GRADE = 2;//帮派的最大等级
	public static int MAX_CITANG_GRADE = 5;//帮派祠堂最大等级
	
	private static int CREATE_NEED_MONEY = 1000;//建帮需要的钱
	private static int CREATE_NEED_GRADE = 30;//需要的等级
	private static int CREATE_NEED_PROP = 233;//建帮令id
	
	private static int RECRUIT_NEED_PROP = 379;//招募令id
	private static int RECRUIT_NEED_MONEY = 100;//招募需要的钱

	/**
	 * 升级氏族祠堂
	 */
	public String upgradeCitang( RoleEntity operater )
	{
		Faction faction = operater.getBasicInfo().getFaction();
		
		if( faction.getGrade()<2 )
		{
			return "氏族等级为1级,不能升级祠堂,请先升级氏族";
		}
		
		if( faction.getCitangGrade()>=FactionService.MAX_CITANG_GRADE)
		{
			return "祠堂已达最高等级,不能升级";
		}
		
		String hint = this.upgrade(operater, FUpgradeMaterial.C_UPGRADE);
		if( hint==null )
		{
			//帮派祠堂升级
			faction.upgradeCitang();
			hint = "恭喜您,您的祠堂已升至"+faction.getCitangGrade()+"级,"+faction.getCitangInfo().getEffectDes()+"!";
		}
		
		return hint;
	}
	/**
	 * 升级氏族
	 */
	public String upgradeFaction( RoleEntity operater )
	{
		Faction faction = operater.getBasicInfo().getFaction();
		
		if( faction.getGrade()>=MAX_GRADE)
		{
			return "你的氏族已达最高等级";
		}
		
		String hint = this.upgrade(operater, FUpgradeMaterial.F_UPGRADE);
		if( hint==null )
		{
			//帮派升级
			faction.upgrade();
			hint = "恭喜您,您的氏族已经晋升为"+faction.getGrade()+"级氏族!";
		}
		
		return hint;
	}
	
	/**
	 * 升级
	 */
	private String upgrade(RoleEntity operater,int upgradeType)
	{
		String hint = this.isOperate(operater, Faction.ZUZHANG);
		if( hint!=null )
		{
			return hint;
		}
		
		Faction faction = operater.getBasicInfo().getFaction();
		
		FUpgradeMaterial fMaterial = faction.getUpgradeMaterial(upgradeType);//升级需要的材料
		if( fMaterial==null )
		{
			DataErrorLog.debugData("无帮派升级材料数据,帮派等级="+faction.getGrade());
			return "无升级材料数据";
		}
		
		//判断帮派声望是否足够
		if( faction.getPrestige()<fMaterial.getPrestige() )
		{
			return "氏族声望不足";
		}
		
		//判断帮派材料是否足够
		hint = faction.getFStorage().isEnoughMaterial(fMaterial.getFMStr());
		if( hint!=null )
		{
			return hint;
		}
		
		//判断个人材料是否足够
		if( operater.getBasicInfo().isEnoughMoney(fMaterial.getMoney())==false )
		{
			return "你的金钱不足";
		}
		
		GoodsService goodsService = new GoodsService();
		int cur_m_num = goodsService.getPropNum(operater.getPPk(), fMaterial.getMId());//得到当前材料数量
		if( cur_m_num<fMaterial.getMNum() )
		{
			return "材料不足";
		}
		
		
		//消耗个人材料和帮派材料
		operater.getBasicInfo().addCopper(-fMaterial.getMoney());
		faction.updatePrestige(-fMaterial.getPrestige());
		goodsService.removeProps(operater.getPPk(), fMaterial.getMId(), fMaterial.getMNum(),GameLogManager.R_MATERIAL_CONSUME);
		faction.getFStorage().consumeMaterial(fMaterial.getFMStr());
		
		return null;
	}
	
	/**
	 * 接管帮派
	 */
	public String assume(RoleEntity operater)
	{
		if( operater==null )
		{
			return "参数错误";
		}
		Faction faction = operater.getBasicInfo().getFaction();
		if( faction==null )
		{
			return "你的氏族已解散";
		}
		
		if( operater.getBasicInfo().isOperateByFJob(Faction.ZHANGLAO)==false )
		{
			return "你的权限不够";
		}
		
		if( faction.getIsDisband()==false )
		{
			return "氏族已被别人接管";
		}
		
		faction.assume(operater);
		return "恭喜您，您已成功接管了氏族，挽回了氏族！";
	}
	
	/**
	 * 解散帮派
	 * @param operater
	 * @return
	 */
	public String disband(RoleEntity operater,HttpServletResponse response)
	{
		String hint = this.isOperate(operater, Faction.ZUZHANG);
		if( hint!=null )
		{
			return hint;
		}
		Faction faction = operater.getBasicInfo().getFaction();
		faction.disband();
		
		PartInfoDao partInfoDao = new PartInfoDao();
		List<Integer> list = partInfoDao.getUpZhanglaoListByFId(faction.getId());
		
		MailInfoService mailInfoService = new MailInfoService();
		MailInfoVO mail = new MailInfoVO();
		mail.setMailType(MailInfoService.F_DISBAND_MAIL);
		mail.setSendPk(faction.getId());
		mail.setTitle(faction.getName()+"即将解散!");
		mail.setContent("您的氏族正处理解散过程,若24小时内,您仍为改变心意,则您的氏族将彻底解散!");
		for(Integer p_pk:list )
		{
			/**给玩家发送一个加入帮派的邮件  */
			mail.setReceivePk(p_pk);
			mailInfoService.sendMail(mail);
		}
		return mail.getContent();
	}
	
	/**
	 * 转让族长
	 */
	public String changeZuzhang(RoleEntity operater,RoleEntity new_zuzhang)
	{
		String hint = this.isOperate(operater, new_zuzhang, Faction.ZUZHANG);
		if( hint!=null )
		{
			return hint;
		}
		if( new_zuzhang.getBasicInfo().getFJob()!=Faction.ZHANGLAO)
		{
			return new_zuzhang.getName()+"的职位不是族长，不能转让族长职位";
		}
		
		Faction faction = new_zuzhang.getBasicInfo().getFaction();
		
		hint = faction.changeLeader(new_zuzhang);
		if( hint!=null )
		{
			return hint;
		}
		
		return "您已经成功将族长一职转让给来"+new_zuzhang.getName()+"，您现在的职位为族众！";
	}
	
	/**
	 * 发布招募信息
	 */
	public String recruit(RoleEntity operater,String rInfo)
	{
		String hint = this.isOperate(operater, Faction.HUFA);
		if( hint!=null )
		{
			return hint;
		}
		
		hint = ValidateService.validateBasicInput(rInfo, 25);
		if( hint!=null )
		{
			return hint;
		}
		//判断金钱是否足够
		if( operater.getBasicInfo().isEnoughMoney(RECRUIT_NEED_MONEY)==false)
		{
			return "金钱不足";
		}
		//判断是否有招募令
		PropService propService = new PropService();
		if( propService.isEnoughProp(operater, RECRUIT_NEED_PROP, 1)==false )
		{
			return "没有"+PropCache.getPropNameById(RECRUIT_NEED_PROP);
		}
		
		//扣除招募需要的材料
		GoodsService goodsService = new GoodsService();
		operater.getBasicInfo().addCopper(-RECRUIT_NEED_MONEY);
		goodsService.removeProps(operater.getPPk(), CREATE_NEED_PROP, 1,GameLogManager.R_USE);
		//todo:发布招募系统消息
		FactionRecruit.getInstance().recruit(operater.getBasicInfo().getFaction().getId(), rInfo);
		
		return null;
	}
	/**
	 * 更改成员的职位
	 */
	public String changeJob(RoleEntity operater,RoleEntity member,int new_job)
	{
		String hint = this.isOperate(operater, member, Faction.ZHANGLAO);
		if( hint!=null )
		{
			return hint;
		}
		
		if( new_job<Faction.ZUZHONG || new_job>=Faction.ZUZHANG)
		{
			return "非法职位";
		}
		
		//如果是长老更改其他成员职位，则不能更改该帮主和其他长老的职位
		if( operater.getBasicInfo().getFJob()==Faction.ZHANGLAO && member.getBasicInfo().getFJob()>=Faction.ZHANGLAO )
		{
			return "你的权限不够";
		}
		
		if( member.getBasicInfo().getFJob()==new_job)
		{
			return member.getName()+"的职位已是"+ExchangeUtil.getFJobName(new_job)+",无需变更!";
		}
		
		hint = operater.getBasicInfo().getFaction().isChangeJob(new_job);
		if(  hint!=null )
		{
			return hint;
		}
		
		member.getBasicInfo().changeFJob(new_job);
		
		return "恭喜您，"+member.getName()+"的职位已变更"+ExchangeUtil.getFJobName(new_job)+"！";
	}
	
	/**
	 * 邀请
	 * @param roleEntity
	 * @param invited_role_name    被邀请者的名字
	 * @return
	 */
	public String invite( RoleEntity roleEntity,String invited_role_name,HttpServletResponse response )
	{
		String hint = this.isOperate(roleEntity, Faction.HUFA);
		if( hint!=null )
		{
			return hint;
		}
		
		hint = ValidateService.validateBasicInput(invited_role_name, 10);
		if( hint!=null )
		{
			return hint;
		}
		
		Faction faction = roleEntity.getBasicInfo().getFaction();
		
		RoleService roleService = new RoleService();
		RoleEntity inviteder = roleService.getRoleInfoByName(invited_role_name);//得到被邀请者的信息
		hint = faction.isAddMember( inviteder);
		if( hint!=null )
		{
			return hint;
		}
		
		//给被邀请者发送邮件
		MailInfoService mailInfoService = new MailInfoService();
		/**给玩家发送一个加入帮派的邮件  */
		String mailTitle = faction.getName()+"帮派邀请您的加入!";
		MailInfoVO mail = new MailInfoVO();
		mail.setSendPk(faction.getId());
		mail.setReceivePk(inviteder.getPPk());
		mail.setMailType(MailInfoService.F_INVITE_MAIL);
		mail.setTitle(mailTitle);
		mail.setContent(mailTitle);
		mailInfoService.sendMail(mail);
		
		return "您的邀请已经发出，请您敬候佳音！";
	}
	
	/**
	 * 通过id删除申请信息
	 */
	public String delApply( RoleEntity roleEntity,int aId )
	{
		String hint = this.isOperate(roleEntity, Faction.ZHANGLAO);
		if( hint!=null )
		{
			return hint;
		}
		FApplyInfoDao fApplyInfoDao = new FApplyInfoDao();
		FApplyInfo fApplyInfo =  fApplyInfoDao.getById(aId);
		
		if( roleEntity.getBasicInfo().getFaction().getId()!=fApplyInfo.getFId() )
		{
			return "非法操作";
		}
		fApplyInfoDao.delById(aId);
		return null;
	}
	
	/**
	 *通过id得到申请信息
	 */
	public String agreeApply( RoleEntity roleEntity,int aId )
	{
		String hint = this.isOperate(roleEntity, Faction.ZHANGLAO);
		if( hint!=null )
		{
			return hint;
		}
		
		Faction faction = roleEntity.getBasicInfo().getFaction();
		
		FApplyInfoDao fApplyInfoDao = new FApplyInfoDao();
		FApplyInfo fApplyInfo =  fApplyInfoDao.getById(aId);
		
		if( fApplyInfo==null )
		{
			return "对方已取消申请";
		}
		
		if( faction.getId()!=fApplyInfo.getFId() )
		{
			return "非法操作";
		}
		RoleEntity applyer = fApplyInfo.getRoleEntity();//申请者
		
		if( applyer.getBasicInfo().getFaction()!=null )
		{
			return applyer.getName()+"已加入其他氏族";
		}
		
		hint = faction.addMember(applyer);
		if( hint!=null )
		{
			return hint;
		}
		
		fApplyInfoDao.delByPPk(fApplyInfo.getPPk());
		
		MailInfoService mailInfoService = new MailInfoService();
		mailInfoService.sendMailAndSystemInfo(fApplyInfo.getPPk(), "氏族邮件", "你已成功加入氏族"+faction.getName());
		return "恭喜，"+applyer.getName()+"已经加入到来您的氏族之中！";
	}
	
	/**
	 * 删除成员
	 */
	public String delMember(RoleEntity operater,RoleEntity member )
	{
		String hint = this.isOperate(operater, member, Faction.ZHANGLAO);
		if( hint!=null )
		{
			return hint;
		}
		
		Faction operater_faction = operater.getBasicInfo().getFaction();
		Faction member_faction = member.getBasicInfo().getFaction();
		
		if( member_faction==null )
		{
			return member_faction.getName()+"已离开氏族";
		}
		
		//如果操作者是长老，则不能驱逐族长和其他长老
		if( operater.getBasicInfo().getFJob()==Faction.ZHANGLAO &&  member.getBasicInfo().getFJob()>=Faction.ZHANGLAO)
		{
			return "你的权限不够";
		}
		
		
		if( member_faction.getId()!=operater_faction.getId() )
		{
			return "法非操作";
		}
		
		operater_faction.delMember(member);
		
		return "您已成功将"+member.getName()+"逐出氏族!";
	}
	
	/**
	 * 申请列表
	 */
	public QueryPage getApplyListPageList( int f_id,int page_no )
	{
		FApplyInfoDao fApplyInfoDao = new FApplyInfoDao();
		return fApplyInfoDao.getPageList(f_id,page_no);
	}
	
	/**
	 * 帮派成员列表
	 */
	public QueryPage getMemListPageList( int f_id,int page_no )
	{
		PartInfoDao partInfoDao = new PartInfoDao();
		return partInfoDao.getPageListByFId(f_id, page_no);
	}
	/**
	 * 帮派长老列表
	 */
	public QueryPage getZhanglaoListPageList( int f_id,int page_no )
	{
		PartInfoDao partInfoDao = new PartInfoDao();
		return partInfoDao.getPageZhanglaoListByFId(f_id, page_no);
	}
	
	
	/**
	 * 申请加入帮派
	 */
	public String apply( RoleEntity roleEntity,Faction faction )
	{
		if( roleEntity==null )
		{
			return "无该角色";
		}
		
		if(  roleEntity.getBasicInfo().getFaction()!=null )
		{
			return "不能申请,你已加入其他氏族";
		}
		
		if( faction==null )
		{
			return "该帮派已解散";	
		}
		
		if( roleEntity.getBasicInfo().getPRace()!=faction.getRace())
		{
			return "种族不符";
		}
		
		FApplyInfoDao fApplyInfoDao = new FApplyInfoDao();
		
		if( fApplyInfoDao.isHave(roleEntity.getPPk(), faction.getId())==true )
		{
			return "你已提交过申请";
		}
		
		fApplyInfoDao.add(roleEntity.getPPk(), faction.getId());
		return "您的申请已提交，待该氏族做出决定后，我们会以系统邮件的方式通知您！";
	}
	
	/**
	 * 得到帮派列表
	 */
	public QueryPage getPageList( int page_no )
	{
		FactionDao factionDao = new FactionDao();
		return factionDao.getPageList(page_no);
	}
	
	/**
	 * 判断是否满足创建帮派的条件
	 * @param roleEntity
	 * @return
	 */
	public String isCreated( RoleEntity roleEntity )
	{
		String hint = null;
		
		if( roleEntity==null )
		{
			return "参数错误";
		}
		
		if( roleEntity.getBasicInfo().getGrade()<CREATE_NEED_GRADE )
		{
			return "等级不符合";
		}
		
		if( roleEntity.getBasicInfo().isEnoughMoney(CREATE_NEED_MONEY)==false )
		{
			return "金钱不足";
		}
		
		PropService propService = new PropService();
		if( propService.isEnoughProp(roleEntity, CREATE_NEED_PROP, 1)==false )
		{
			return "没有"+PropCache.getPropNameById(CREATE_NEED_PROP);
		}
		
		return hint;
	}
	
	/**
	 * 创建帮派
	 */
	public String create( RoleEntity roleEntity,String faction_name )
	{
		if( roleEntity==null )
		{
			return "数据错误";
		}
		
		if( this.isCreated(roleEntity)!=null )
		{
			return "对不起，您的等级不购，或灵石不足，或没有氏族令，不可创建氏族！";
		}
		
		String hint = this.validateFName(faction_name);
		if( hint!=null )
		{
			return hint;
		}
		
		Faction faction = new Faction();
		hint = faction.create(roleEntity,faction_name);//创建帮派
		if( hint!=null )
		{
			return hint;
		}
		
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(roleEntity.getPPk(), CREATE_NEED_PROP, 1,GameLogManager.R_MATERIAL_CONSUME);
		roleEntity.getBasicInfo().addCopper(-CREATE_NEED_MONEY);
		return null;
	}
	
	/**
	 * 验证帮派的名字是否合法
	 */
	private String validateFName(String faction_name )
	{
		String hint = ValidateService.validateBasicInput(faction_name, 5);
		if( hint!=null )
		{
			return hint;
		}
		
		FactionDao factionDao = new FactionDao();
		if( factionDao.isHaveName(faction_name))
		{
			return "该名已被占用";
		}
		
		return null;
	}
	
	/**
	 * 发布招募信息
	 */
	public String publishRecruitInfo()
	{
		return null;
	}
	
	/**
	 * 发布公告
	 */
	public String publishNotice( RoleEntity roleEntity,String content )
	{
		if( roleEntity==null )
		{
			return "参数错误";
		}
		
		if( roleEntity.getBasicInfo().getIsPublishNotice()==false )
		{
			return "对不起，只有族长、长老和护法才具有发布公告的权力，你无有此项权力！";
		}
		
		String hint = ValidateService.validateBasicInput(content, 30);
		if( hint!=null )
		{
			return hint;
		}
		
		Faction faction = roleEntity.getBasicInfo().getFaction();
		
		FNotice notice = new FNotice(faction.getId(),content);
		faction.publishNotice(notice);
		return null;
	}
	/**
	 * 删除公告
	 * @param roleEntity
	 * @param id		公告id
	 * @return
	 */
	public String delNotice(RoleEntity roleEntity,int id)
	{
		if( roleEntity==null )
		{
			return "参数错误";
		}
		
		if( roleEntity.getBasicInfo().getIsDelNotice()==false )
		{
			return "对不起，只有族长或长老才有权力删除公告，您无有此项权力！";
		}
		
		Faction faction = roleEntity.getBasicInfo().getFaction();
		
		FNoticeDao fNoticDao = new FNoticeDao();
		FNotice notice = fNoticDao.getById(id);
		
		if( faction.getId()!=notice.getFId())
		{
			return "非法操作";
		}
		
		faction.delNotice(id);
		
		return null;
	}
	
	/**
	 * 得到公告的分页列表
	 * @return
	 */
	public QueryPage getNoticePageList(int f_id,int page_no)
	{
		FNoticeDao fNoticDao = new FNoticeDao();
		return fNoticDao.getPageList(f_id, page_no);
	}
	
	/**
	 * 通过id得到帮派信息
	 */
	public static Faction getById( int fId )
	{
		Faction faction = faction_cache.get(fId);
		if( faction==null )
		{
			FactionDao factionDao = new FactionDao();
			faction = factionDao.getById(fId);
			if( faction!=null )
			{
				faction_cache.put(faction.getId(), faction);
			}
		}
		return faction_cache.get(fId);
	}
	
	/**
	 * 删除帮派
	 * @param faction
	 */
	public static void del( int id )
	{
		if( id>0 )
		{
			faction_cache.remove(id);
			FactionDao factionDao = new FactionDao();
			factionDao.delById(id);
		}
	}

	/**
	 * 玩家对帮派事务(针对其他成员的操作)的操作基本条件判断
	 * @param operater				执行操作的玩家
	 * @param job 					执行操作的最低职务
	 * @return
	 */
	private String isOperate(RoleEntity operater,RoleEntity member,int job)
	{
		String hint = this.isOperate(operater, job);
		if( hint!=null )
		{
			return hint;
		}
		if( member==null )
		{
			return "该成员已不存在";
		}
		
		if( operater.getPPk()==member.getPPk() )
		{
			return "非法操作";
		}
		
		//判断是否是同一帮派
		if( operater.getBasicInfo().getFaction().getId()!=member.getBasicInfo().getFaction().getId())
		{
			return "非法操作";
		}
		
		return null;
	}
	
	/**
	 * 玩家对帮派事务的操作基本条件判断
	 * @param operater				执行操作的玩家
	 * @param job 					执行操作的最低职务
	 * @return
	 */
	String isOperate(RoleEntity operater,int job)
	{
		if( operater==null )
		{
			return "参数错误";
		}
		Faction faction = operater.getBasicInfo().getFaction();
		if( faction==null )
		{
			return "你的氏族已解散";
		}
		
		if( faction.getIsDisband()==true )
		{
			return "氏族被解散中...";
		}
		
		if( operater.getBasicInfo().isOperateByFJob(job)==false )
		{
			return "你的权限不够";
		}
		
		return null;
	}
	
	/**
	 * 定时器检查帮派解散
	 */
	public static void checkDisband()
	{
		try
		{
			FactionDao factionDao = new FactionDao();
			List<Integer> list = factionDao.getDisbandList();
			for( Integer id:list )
			{
				del(id);
			}
		}
		catch(Exception e)
		{
			DataErrorLog.debugData("定时器检查帮派解散,出错："+e.getMessage());
		}
	}
	
	/**
	 * 定时器检查是否是活跃帮派（是否在10招满人）
	 */
	public static void checkIsActived()
	{
		try
		{
			Set<Integer> f_id_list = faction_cache.keySet();
			for( Integer f_id:f_id_list )
			{
				Faction faction = faction_cache.get(f_id);
				if( faction==null )
				{
					del(f_id);
				}
				else
				{
					if( faction.isActived()==false )
					{
						del(f_id);
					}
				}
			}
		}
		catch(Exception e)
		{
			DataErrorLog.debugData("定时器检查是否是活跃帮派,出错："+e.getMessage());
		}
	}
}
