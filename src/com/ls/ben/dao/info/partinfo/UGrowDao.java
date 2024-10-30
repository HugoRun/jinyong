package com.ls.ben.dao.info.partinfo;

import java.util.HashMap;
import java.util.Map;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.partinfo.UGrowVO;
import com.ls.pub.config.GameConfig;
import com.ls.pub.db.DBConnection;
import com.ls.web.service.log.DataErrorLog;

/**
 * ����:����u_grow_info��
 * @author ��˧
 * 6:27:39 PM
 */
public class UGrowDao extends DaoBase
{
	//�ɳ���Ϣ����
	private static Map<String,UGrowVO> grow_cache = new HashMap<String,UGrowVO>(300);
	
	//��߼��ɳ���Ϣ
	private static Map<Integer,UGrowVO> max_grade_grow_cache = new HashMap<Integer,UGrowVO>(2);
	
	/**
	 * �õ���ߵȼ��ɳ���Ϣ������ֵ��
	 * @return
	 */
	public UGrowVO getMaxGradeInfo(int race)
	{
		UGrowVO growVO = max_grade_grow_cache.get(race);
		
		if( growVO!=null )
		{
			return growVO;
		}

		int max_grade = GameConfig.getGradeUpperLimit();
		
		growVO = this.getByGradeAndRace(max_grade, race);
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "select sum(g_HP),sum(g_MP),sum(g_gj),sum(g_fy) from u_grow_info where g_grade=" + max_grade
					+ " and g_race='" + race + "'";
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				growVO.setGGrade(max_grade);
				growVO.setGHP(rs.getInt(1));
				growVO.setGMP(rs.getInt(2));
				growVO.setGGj(rs.getInt(3));
				growVO.setGFy(rs.getInt(4));
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
		max_grade_grow_cache.put(race, growVO);
		return growVO;
	}
	
	
	/**
	 * ���ݼ��������巵����һ�����������ֵ
	 * @param grade		�ȼ�
	 * @param race		����
	 * @return
	 */
	public UGrowVO getByGradeAndRace(int grade, int race)
	{
		String cache_key = grade+"*"+race;
		UGrowVO growVO = grow_cache.get(cache_key);//�ӻ����ȡ
		if( growVO!=null )
		{
			return growVO;
		}
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "select * from u_grow_info where g_grade=" + grade
					+ " and g_race='" + race + "'";
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				growVO = new UGrowVO();
				growVO.setGGrade(rs.getInt("g_grade"));
				growVO.setGHP(rs.getInt("g_HP"));
				growVO.setGMP(rs.getInt("g_MP"));
				growVO.setGExp(rs.getString("g_exp"));
				growVO.setGNextExp(rs.getString("g_next_exp"));
				growVO.setGGj(rs.getInt("g_gj"));
				growVO.setGFy(rs.getInt("g_fy"));
				growVO.setGIsAutogrow(rs.getInt("g_isAutogrow")); 
				growVO.setGDropMultiple(rs.getDouble("g_drop_multiple"));
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
		if( growVO==null )
		{
			DataErrorLog.debugData("���ݴ��󣬳ɳ���Ϣ��grade="+grade+";race="+race);
		}
		else
		{
			grow_cache.put(cache_key, growVO);
		}
		return growVO;
	}


	/**
	 * ���ݼ����������ж��Ƿ���Զ�����
	 * @param grade
	 * @param race
	 * @return
	 */
	public boolean isAutogrow(int grade, int race)
	{
		UGrowVO growVO = this.getByGradeAndRace(grade, race);
		if( growVO.getGIsAutogrow()==1 )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
