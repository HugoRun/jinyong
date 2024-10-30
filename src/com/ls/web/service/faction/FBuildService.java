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
 * ���ɽ������
 */
public class FBuildService
{
	private static Map<Integer,FGameBuild> build_cache = new HashMap<Integer,FGameBuild>();
	
	/**
	 * �õ���Ϸ���ɽ�����Ϣ
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
	 * �õ����ɽ�����Ϣ
	 * @param bId
	 * @return
	 */
	public FBuild getFBuildById(int fId,int bId )
	{
		FBuildDao fBuildDao = new FBuildDao();
		return fBuildDao.getBuild(fId, bId);
	}
	
	

	/**
	 * ����ͼ��
	 */
	public String upgradeTuteng(RoleEntity operater,FBuild fBuild)
	{
		if( fBuild==null )
		{
			return "��������";
		}
		Faction faciton = operater.getBasicInfo().getFaction();
		
		//�������õȼ��ж��Ƿ��������
		int citang_grade = faciton.getCitangGrade();
		if( citang_grade <= (fBuild.getGameBuild().getGrade()+1) )
		{
			return fBuild.getGameBuild().getName()+"��������,���õȼ�Ϊ"+citang_grade+"��,ͼ�ڵĵȼ����Ϊ"+citang_grade+"��";
		}
		
		FGameBuild next_grade_build = fBuild.getGameBuild().getNextGradeBuild();
		String hint = this.buildTuteng(operater, next_grade_build);
		if( hint==null )//�����ɹ�
		{
			FBuildDao fBuildDao = new FBuildDao();
			//���콨��
			fBuildDao.upgrade(fBuild, next_grade_build.getId());
		}
		return hint;
	}
	/**
	 * ����ͼ��
	 */
	public String createTuteng(RoleEntity operater,FGameBuild gameBuild)
	{
		FBuildDao fBuildDao = new FBuildDao();
		Faction faciton = operater.getBasicInfo().getFaction();
		//�������õȼ��ж��Ƿ���Խ���ͼ��
		int citang_grade = faciton.getCitangGrade();
		int cur_build_num = fBuildDao.getNumByFId(faciton.getId());
		if( citang_grade <= (cur_build_num+1) )
		{
			return "���ܽ����µ�ͼ��,���õȼ�Ϊ"+citang_grade+"��,������������";
		}
		String hint = this.buildTuteng(operater, gameBuild);
		if( hint==null )//�����ɹ�
		{
			//���콨��
			fBuildDao.add(faciton.getId(), gameBuild.getId());
		}
		return hint;
	}
	
	/**
	 * ����ͼ��
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
			return "��ͼ���Ѿ��޽���";
		}
		//�ж��ǰ��������Ƿ��㹻
		if( faciton.getPrestige()<gameBuild.getPrestige())
		{
			return "������������";
		}
		//�ж������Ƿ��㹻
		GoodsService goodsService = new GoodsService();
		int cur_m_num = goodsService.getPropNum(operater.getPPk(), gameBuild.getMId());//�õ���ǰ��������
		if( cur_m_num<gameBuild.getMNum() )
		{
			return "���ϲ���,��Ҫ"+gameBuild.getMaterial().getPropName()+"��"+gameBuild.getMNum();
		}
		
		//�۳�����
		goodsService.removeProps(operater.getPPk(), gameBuild.getMId(), gameBuild.getMNum(),GameLogManager.R_MATERIAL_CONSUME);
		//�۳���������
		faciton.updatePrestige(-gameBuild.getPrestige());
		return null;
	}
	
	
	/**
	 * ʹ��ͼ��
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
		//�жϽ����Ƿ��Ѿ���ȡ����һ��ֻ����ȡһ�Σ�
		int max_use_time = 1;//���ʹ�ô���
		if(!timeControlService.isUseable(operater.getPPk(), fBuild.getBId(), TimeControlService.F_USE_BUILD, max_use_time))
		{
			return "һ��ֻ����ȡ"+max_use_time+"�Σ��������Ѿ���ȡ��";
		}
		//�жϰ��ɹ����Ƿ��㹻
		if( operater.getBasicInfo().isEnoughFContribute(fBuild.getGameBuild().getContribute())==false)
		{
			return "���ɹ��ײ���";
		}
		
		//�۳����ɹ���
		operater.getBasicInfo().addFContribute(-fBuild.getGameBuild().getContribute());
		
		//��ȡbuffЧ������¼��ȡ�¼�
		BuffEffectService buffEffectService = new BuffEffectService();
		List<BuffVO> buff_list = fBuild.getGameBuild().getBuffList();
		for(BuffVO buff:buff_list)
		{
			buffEffectService.createRoleBuffEffect(operater.getPPk(), buff.getBuffId());
		}
		
		timeControlService.updateControlInfo(operater.getPPk(), fBuild.getBId(), TimeControlService.F_USE_BUILD);
		
		return "��ϲ���ɹ���ȡ��"+fBuild.getName()+"ף��,"+fBuild.getGameBuild().getBuffDisplay()+"��";
	}
	
	/**
	 * �õ���û�н���İ��ɽ�����ҳ�б�
	 * @param fId			����id
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
	 * �õ��������н������б�
	 */
	public QueryPage getBuildPageList(int fId,int page_no)
	{
		FBuildDao fBuildDao = new FBuildDao();
		return fBuildDao.getBuildPageList(fId, page_no);
	}
}
