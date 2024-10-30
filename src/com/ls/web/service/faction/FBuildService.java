package com.ls.web.service.faction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ls.ben.dao.faction.FBuildDao;
import com.ls.ben.dao.faction.game.FGameBuildDao;
import com.ls.ben.vo.info.buff.BuffVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.organize.faction.FBuild;
import com.ls.model.organize.faction.Faction;
import com.ls.model.organize.faction.game.FGameBuild;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.web.service.buff.BuffEffectService;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;

/**
 * @author ls
 * 帮派建筑相关
 */
public class FBuildService
{
	private static Map<Integer,FGameBuild> build_cache = new HashMap<Integer,FGameBuild>();
	
	/**
	 * 得到游戏帮派建筑信息
	 * @param bId
	 * @return
	 */
	public static FGameBuild getGameBuildById(int bId )
	{
		if( bId<=0 )
		{
			return null;
		}
		
		FGameBuild build = build_cache.get(bId);
		if( build==null )
		{
			FGameBuildDao fGameBuildDao = new FGameBuildDao();
			build = fGameBuildDao.getById(bId);
			if( build==null )
			{
				build_cache.put(bId, build);
			}
		}
		return build;
	}
	
	/**
	 * 得到帮派建筑信息
	 * @param bId
	 * @return
	 */
	public FBuild getFBuildById(int fId,int bId )
	{
		FBuildDao fBuildDao = new FBuildDao();
		return fBuildDao.getBuild(fId, bId);
	}
	
	

	/**
	 * 升级图腾
	 */
	public String upgradeTuteng(RoleEntity operater,FBuild fBuild)
	{
		if( fBuild==null )
		{
			return "参数错误";
		}
		Faction faciton = operater.getBasicInfo().getFaction();
		
		//根据祠堂等级判断是否可以升级
		int citang_grade = faciton.getCitangGrade();
		if( citang_grade <= (fBuild.getGameBuild().getGrade()+1) )
		{
			return fBuild.getGameBuild().getName()+"不能升级,祠堂等级为"+citang_grade+"级,图腾的等级最高为"+citang_grade+"级";
		}
		
		FGameBuild next_grade_build = fBuild.getGameBuild().getNextGradeBuild();
		String hint = this.buildTuteng(operater, next_grade_build);
		if( hint==null )//创建成功
		{
			FBuildDao fBuildDao = new FBuildDao();
			//建造建筑
			fBuildDao.upgrade(fBuild, next_grade_build.getId());
		}
		return hint;
	}
	/**
	 * 创建图腾
	 */
	public String createTuteng(RoleEntity operater,FGameBuild gameBuild)
	{
		FBuildDao fBuildDao = new FBuildDao();
		Faction faciton = operater.getBasicInfo().getFaction();
		//根据祠堂等级判断是否可以建造图腾
		int citang_grade = faciton.getCitangGrade();
		int cur_build_num = fBuildDao.getNumByFId(faciton.getId());
		if( citang_grade <= (cur_build_num+1) )
		{
			return "不能建造新的图腾,祠堂等级为"+citang_grade+"级,请先升级祠堂";
		}
		String hint = this.buildTuteng(operater, gameBuild);
		if( hint==null )//创建成功
		{
			//建造建筑
			fBuildDao.add(faciton.getId(), gameBuild.getId());
		}
		return hint;
	}
	
	/**
	 * 建造图腾
	 */
	private String buildTuteng(RoleEntity operater,FGameBuild gameBuild)
	{
		FactionService factionService = new FactionService();
	
		String hint = factionService.isOperate(operater, Faction.ZUZHANG);
		if( hint!=null )
		{
			return hint;
		}
		
		Faction faciton = operater.getBasicInfo().getFaction();
		
		FBuildDao fBuildDao = new FBuildDao();
		FBuild f_build = fBuildDao.getBuild(faciton.getId(), gameBuild.getId());
		if( f_build!=null )
		{
			return "该图腾已经修建过";
		}
		//判断是帮派声望是否足够
		if( faciton.getPrestige()<gameBuild.getPrestige())
		{
			return "帮派声望不够";
		}
		//判读材料是否足够
		GoodsService goodsService = new GoodsService();
		int cur_m_num = goodsService.getPropNum(operater.getPPk(), gameBuild.getMId());//得到当前材料数量
		if( cur_m_num<gameBuild.getMNum() )
		{
			return "材料不足,需要"+gameBuild.getMaterial().getPropName()+"×"+gameBuild.getMNum();
		}
		
		//扣除材料
		goodsService.removeProps(operater.getPPk(), gameBuild.getMId(), gameBuild.getMNum(),GameLogManager.R_MATERIAL_CONSUME);
		//扣除帮派声望
		faciton.updatePrestige(-gameBuild.getPrestige());
		return null;
	}
	
	
	/**
	 * 使用图腾
	 * @return
	 */
	public String useTuteng(RoleEntity operater,FBuild fBuild)
	{
		FactionService factionService = new FactionService();
		
		String hint = factionService.isOperate(operater, Faction.ZUZHONG);
		if( hint!=null )
		{
			return hint;
		}
		TimeControlService timeControlService = new TimeControlService();
		//判断今天是否已经领取过（一天只能领取一次）
		int max_use_time = 1;//最大使用次数
		if(!timeControlService.isUseable(operater.getPPk(), fBuild.getBId(), TimeControlService.F_USE_BUILD, max_use_time))
		{
			return "一天只能领取"+max_use_time+"次，今天你已经领取过";
		}
		//判断帮派贡献是否足够
		if( operater.getBasicInfo().isEnoughFContribute(fBuild.getGameBuild().getContribute())==false)
		{
			return "帮派贡献不足";
		}
		
		//扣除帮派贡献
		operater.getBasicInfo().addFContribute(-fBuild.getGameBuild().getContribute());
		
		//领取buff效果，记录领取事件
		BuffEffectService buffEffectService = new BuffEffectService();
		List<BuffVO> buff_list = fBuild.getGameBuild().getBuffList();
		for(BuffVO buff:buff_list)
		{
			buffEffectService.createRoleBuffEffect(operater.getPPk(), buff.getBuffId());
		}
		
		timeControlService.updateControlInfo(operater.getPPk(), fBuild.getBId(), TimeControlService.F_USE_BUILD);
		
		return "恭喜您成功领取了"+fBuild.getName()+"祝福,"+fBuild.getGameBuild().getBuffDisplay()+"！";
	}
	
	/**
	 * 得到还没有建造的帮派建筑分页列表
	 * @param fId			帮派id
	 * @param page_no
	 * @return
	 */
	public QueryPage getUnBuildPageList(int fId,int page_no)
	{
		FBuildDao fBuildDao = new FBuildDao();
		FGameBuildDao fGameBuildDao = new FGameBuildDao();
		
		String excludeIdStr = fBuildDao.get1GradeBuildIdList(fId);
		return fGameBuildDao.getUnBuildPageList(excludeIdStr, page_no);
	}
	
	/**
	 * 得到帮派所有建筑的列表
	 */
	public QueryPage getBuildPageList(int fId,int page_no)
	{
		FBuildDao fBuildDao = new FBuildDao();
		return fBuildDao.getBuildPageList(fId, page_no);
	}
}
