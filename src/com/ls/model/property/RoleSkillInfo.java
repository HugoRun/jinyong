package com.ls.model.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.staticcache.skill.SkillCache;
import com.ls.ben.dao.info.skill.PlayerSkillDao;
import com.ls.ben.dao.info.skill.SkillDao;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.ben.vo.info.skill.SkillVO;
import com.ls.model.user.RoleEntity;
import com.ls.model.user.UserBase;
import com.ls.web.service.log.DataErrorLog;
import com.pm.vo.passiveskill.PassSkillVO;

/**
 *  存储玩家的 技能信息
 * @author Administrator
 *
 */
public class RoleSkillInfo extends UserBase
{
	public PlayerSkillDao skillDao;
	private HashMap<Integer,PlayerSkillVO> skill;
	private RoleSkillPropertyInfo roleSkillPropertyInfo;
	/**
	 * 初始化
	 * @param roleInfo
	 */
	public RoleSkillInfo(int p_pk)
	{
		super(p_pk);
		skillDao = new PlayerSkillDao();
		skill = skillDao.getPlayerAllSkill(p_pk);	
		roleSkillPropertyInfo = new RoleSkillPropertyInfo(p_pk);
	}
	
	
	/**
	 * 清空技能
	 */
	public void clear()
	{
		if( skill.size()>0 )
		{
			skillDao.clear(p_pk);
			skill.clear();//清空攻击技能
			roleSkillPropertyInfo = new RoleSkillPropertyInfo(p_pk);//重载被动技能
		}
	}
	
	

	/**
	 * 得到玩家的 技能 列表
	 * @return
	 */
	public List<PlayerSkillVO> getSkillList()
	{
		
		List<PlayerSkillVO> list = new ArrayList<PlayerSkillVO>(skill.values());
		return list;	
	}
	
	/**
	 * 得到玩家的主动攻击技能列表
	 * @return
	 */
	public List<PlayerSkillVO> getAttackSkillList()
	{
		
		List<PlayerSkillVO> list = new ArrayList<PlayerSkillVO>(skill.values());
		PlayerSkillVO skill = null;
		
		if( list!=null && list.size()!=0 )
		{
			for( int i=list.size()-1;i>=0;i-- )
			{
				skill = list.get(i);
				if( skill.getSkType()!=1 )//过滤掉非主动攻击技能
				{
					list.remove(i);
				}
				else if( skill.getSkType()==1 && skill.getSkId()==1 )//过滤掉非捕捉宠物技能
				{
					list.remove(i);
				}
			}
		}
		
		return list;	
	}
	
	/**
	 * 使用 技能id得到玩家的 具体技能, 
	 * @return
	 */
	public PlayerSkillVO getSkillBySkId( String skId)
	{
		List<PlayerSkillVO> list = getSkillList();
		PlayerSkillVO playerSkillVO = null;
		for (PlayerSkillVO skillVO: list ) {
			if(skillVO.getSkId() == Integer.parseInt(skId)) {
				playerSkillVO = skillVO;
			}
		}
		
		return playerSkillVO;		
	}

	/**
	 * 使用 个人技能主键 
	 * @param s_pk
	 * @return
	 */
	public PlayerSkillVO getSkillBySPk(int s_pk)
	{
		return skill.get(s_pk);
	}
	/**
	 * 修改技能名称和 快捷键 名称
	 */
	public void updateSkillName(int p_pk,int s_pk,String s_name){
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		RoleEntity roleEntity = RoleCache.getByPpk(p_pk);			
		RoleShortCutInfo roleShortCutInfo = roleEntity.getRoleShortCutInfo();
		
		PlayerSkillVO vo = null;
		vo = playerSkillDao.getById(s_pk);
		
		skill.put(s_pk, vo);
		
		//重新加载快捷键
		roleShortCutInfo.updateShortcutName(s_pk, s_name); 
	}
	
	/**
	 * 学习一个技能
	 */
	public PlayerSkillVO study( int sId ) {
		SkillVO skill = SkillCache.getById(sId);
		if( skill!=null )
		{
			PlayerSkillVO playerSkill = new PlayerSkillVO();
			playerSkill.setPPk(p_pk);
			playerSkill.setSkId(sId);
			new SkillDao().loadPlayerSkillDetail(playerSkill);//加载技能信息
			this.skillDao.add(playerSkill);//数据库添加
			this.addSkillToPlayer(playerSkill);//内存添加
			return playerSkill;
		}
		else
		{
			DataErrorLog.debugData("RoleSkillInfo.study:无该技能,sId="+sId);
			return null;
		}
	}
	
	/**
	 * 加入一个技能
	 */
	public void addSkillToPlayer( PlayerSkillVO skillvo ) {
		if(skillvo==null)
		{
			return;
		}
		skill.put(skillvo.getSPk(), skillvo);
		if( skillvo.getSkType()==0||skillvo.getSkType()==4 )//如果是被动技能，则重载被动技能属性
		{
			roleSkillPropertyInfo.loadPropertys(skillvo.getPPk());
		}
	}
	
	/**
	 * 从身上移除一个技能
	 * 
	 */
	public void removeSkillFromPlayer ( int sPk ) {
		skill.remove(sPk);
	}
	
	/**
	 * 得到玩家的被动技能的属性
	 * **/
	public PassSkillVO getPassSkillProperty(){
		PassSkillVO vo = new PassSkillVO();
		vo.setSkGjMultiple(roleSkillPropertyInfo.getSkGjMultiple());
		vo.setSkFyMultiple(roleSkillPropertyInfo.getSkFyMultiple());
		vo.setSkHpMultiple(roleSkillPropertyInfo.getSkHpMultiple());
		vo.setSkMpMultiple(roleSkillPropertyInfo.getSkMpMultiple());
		vo.setSkBjMultiple(roleSkillPropertyInfo.getSkBjMultiple());
		vo.setSkGjAdd(roleSkillPropertyInfo.getSkGjAdd());
		vo.setSkFyAdd(roleSkillPropertyInfo.getSkFyAdd());
		vo.setSkHpAdd(roleSkillPropertyInfo.getSkHpAdd());
		vo.setSkMpAdd(roleSkillPropertyInfo.getSkMpAdd());
		vo.setSkJMultiple(roleSkillPropertyInfo.getSkJMultiple());
		vo.setSkMMultiple(roleSkillPropertyInfo.getSkMMultiple());
		vo.setSkSMultiple(roleSkillPropertyInfo.getSkSMultiple());
		vo.setSkHMultiple(roleSkillPropertyInfo.getSkHMultiple());
		vo.setSkTMultiple(roleSkillPropertyInfo.getSkTMultiple());
		return vo;
	}

}
