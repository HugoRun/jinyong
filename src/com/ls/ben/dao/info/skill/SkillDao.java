package com.ls.ben.dao.info.skill;

import java.util.HashMap;

import com.ls.ben.cache.staticcache.skill.SkillCache;
import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.ben.vo.info.skill.SkillVO;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * 功能:skill表的dao
 * @author 刘帅
 * 3:50:09 PM
 */
public class SkillDao extends DaoBase
{
	/**
	 * 加载玩家技能的详细信息
	 * @param playerSkill  		玩家技能
	 */
	public void loadPlayerSkillDetail(PlayerSkillVO playerSkill)
	{
		if (playerSkill == null)
		{
			logger.debug("参数错误:用户技能为空");
			return ;
		}
		SkillVO skillvo = SkillCache.getById(playerSkill.getSkId());

			if (skillvo!=null )
			{
				playerSkill.setSkId(skillvo.getSkId());
				playerSkill.setSkName(skillvo.getSkName());
				playerSkill.setSkDisplay(skillvo.getSkDisplay());
				playerSkill.setSkType(skillvo.getSkType());
				playerSkill.setSkExpend(skillvo.getSkExpend());
				playerSkill.setSkUsecondition(skillvo.getSkUsecondition());
				playerSkill.setSkDamageDi(skillvo.getSkDamageDi());
				playerSkill.setSkDamageGao(skillvo.getSkDamageGao());
				playerSkill.setSkArea(skillvo.getSkArea());
				playerSkill.setSkBaolv(skillvo.getSkBaolv());
				playerSkill.setSkYun(skillvo.getSkYun());
				playerSkill.setSkYunBout(skillvo.getSkYunBout());
				playerSkill.setSkBuff(skillvo.getSkBuff());
				playerSkill.setSkBuffProbability(skillvo.getSkBuffProbability());
				playerSkill.setSkLqtime(skillvo.getSkLqtime());
				playerSkill.setSkGjMultiple(skillvo.getSkGjMultiple());
				playerSkill.setSkFyMultiple(skillvo.getSkFyMultiple());
				playerSkill.setSkHpMultiple(skillvo.getSkHpMultiple());
				playerSkill.setSkMpMultiple(skillvo.getSkMpMultiple());
				playerSkill.setSkBjMultiple(skillvo.getSkBjMultiple());
				playerSkill.setSkGjAdd(skillvo.getSkGjAdd());
				playerSkill.setSkFyAdd(skillvo.getSkFyAdd());
				playerSkill.setSkHpAdd(skillvo.getSkHpAdd());
				playerSkill.setSkMpAdd(skillvo.getSkMpAdd());
				playerSkill.setSkGroup(skillvo.getSkGroup());
			}

	}
	
	
	/**
	 * 加载玩家技能的详细信息,利用内存中存储的skill信息,而不是数据库
	 * 
	 * @param sk_id
	 * @return SkillVO
	 */
	public void loadPlayerSkillDetailByInside(PlayerSkillVO playerSkill)
	{
		if (playerSkill == null)
		{
			logger.debug("参数错误:用户技能为空");
			return ;
		}
			SkillVO skillvo = SkillCache.getById(playerSkill.getSkId());
			
			if (skillvo != null)
			{
				playerSkill.setSkId(skillvo.getSkId());
				playerSkill.setSkName(skillvo.getSkName());
				playerSkill.setSkDisplay(skillvo.getSkDisplay());
				
				playerSkill.setSkType(skillvo.getSkType());
				playerSkill.setSkExpend(skillvo.getSkExpend());
				
				playerSkill.setSkUsecondition(skillvo.getSkUsecondition());
				playerSkill.setSkDamageDi(skillvo.getSkDamageDi());
				playerSkill.setSkDamageGao(skillvo.getSkDamageGao());
				playerSkill.setSkArea(skillvo.getSkArea());
				playerSkill.setSkGroup(skillvo.getSkGroup());
				playerSkill.setSkBaolv(skillvo.getSkBaolv());
				playerSkill.setSkYun(skillvo.getSkYun());
				playerSkill.setSkYunBout(skillvo.getSkYunBout());
				playerSkill.setSkBuff(skillvo.getSkBuff());
				playerSkill.setSkBuffProbability(skillvo.getSkBuffProbability());
				
				playerSkill.setSkLqtime(skillvo.getSkLqtime());
				
			}
			
	}
	

	/**
	 * 初始化skill表，将 skill表里的内容都放入到内存表里去
	 * @return
	 */
	public HashMap<String, SkillVO> getAllSkill()
	{
		HashMap<String, SkillVO> result = null;;
		int total_num = 0;
		SkillVO skillvo = null;
		
		String total_num_sql = "select count(*) from skill";
		String sql = "select * from skill";
		logger.debug(sql);
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(total_num_sql);
			
			if( rs.next() )
			{
				total_num = rs.getInt(1);
			}
			
			result = new HashMap<String, SkillVO>(total_num);
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				skillvo = new SkillVO();
				skillvo.setSkId(rs.getInt("sk_id"));
				skillvo.setSkName(StringUtil.isoToGBK(rs
						.getString("sk_name")));
				skillvo.setSkDisplay(StringUtil.isoToGBK(rs
						.getString("sk_display")));
				skillvo.setSkType(rs.getInt("sk_type"));
				skillvo.setSkExpend(rs.getString("sk_expend"));
				skillvo.setSkUsecondition(rs.getInt("sk_usecondition"));
				skillvo.setSkDamageDi(rs.getInt("sk_damage_di"));
				skillvo.setSkDamageGao(rs.getInt("sk_damage_gao"));
				skillvo.setSkArea(rs.getInt("sk_area"));
				skillvo.setSkBaolv(rs.getString("sk_baolv"));
				skillvo.setSkYun(rs.getInt("sk_yun"));
				skillvo.setSkYunBout(rs.getInt("sk_yun_bout"));
				skillvo.setSkBuff(rs.getInt("sk_buff"));
				skillvo.setSkBuffProbability(rs
						.getInt("sk_buff_probability"));
				skillvo.setSkLqtime(rs.getInt("sk_lqtime"));
				skillvo.setSkGjMultiple(rs.getDouble("sk_gj_multiple"));
				skillvo.setSkFyMultiple(rs.getDouble("sk_fy_multiple"));
				skillvo.setSkHpMultiple(rs.getDouble("sk_hp_multiple"));
				skillvo.setSkMpMultiple(rs.getDouble("sk_mp_multiple"));
				skillvo.setSkBjMultiple(rs.getDouble("sk_bj_multiple"));
				skillvo.setSkGjAdd(rs.getInt("sk_gj_add"));
				skillvo.setSkFyAdd(rs.getInt("sk_fy_add"));
				skillvo.setSkHpAdd(rs.getInt("sk_hp_add"));
				skillvo.setSkMpAdd(rs.getInt("sk_mp_add"));
				skillvo.setSkGroup(rs.getInt("sk_group"));
				skillvo.setSkNextSleight(rs.getInt("sk_next_sleight"));
				
				result.put(rs.getInt("sk_id")+"", skillvo);

			}
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		
		return result;
	}
}
