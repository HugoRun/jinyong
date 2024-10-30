package com.pm.dao.field;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;
import com.pm.vo.field.FieldKillVO;

public class FieldKillDao extends DaoBase {
	/**
	 * 获得此pPk在24小时内杀bPpk的次数
	 * @param fh_id
	 * @param pPk
	 * @param bPpk
	 * @return
	 */
	public int bykill_number( int pPk, int bPpk)
	{
		String sql = "SELECT COUNT(1) AS numb FROM s_field_kill WHERE p_pk = "+pPk+" AND bp_pk = "
							+ bPpk + " AND now() > (sf_create_time + INTERVAL 1 DAY)";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("获得此pPk在24小时内杀bPpk的次数="+sql);
		conn = dbConn.getConn();
		int numb = 0;
		try
		{
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				numb = rs.getInt("numb");
			}

			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return numb;
	}

	/**
	 * 将杀人信息插入到战场杀人表中, 此表记录信息比较详细，但保存时间较短。
	 * @param pPk
	 * @param bPpk
	 * @param fh_id 场次
	 */
	public void insertIntoFieldKill(int pPk, int bPpk, int fh_sequence,String field_type)
	{
		String sql = "INSERT INTO s_field_kill VALUES (null,"+pPk+","+bPpk+","+fh_sequence+","+field_type+",now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("将杀人信息插入到战场杀人表中="+sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
		
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		
	}

	/**
	 * 获得本场的排名榜
	 * @param pPk
	 * @param page_no
	 * @return
	 */
	public QueryPage getPersonUntangleList(int pk, int page_no,
			String field_type,int field_sequence)
	{
		QueryPage queryPage = null;
		
		List<FieldKillVO> list = new ArrayList<FieldKillVO>();
		FieldKillVO fieldKillVO = null;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String count_sql,page_sql;
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			count_sql = "SELECT COUNT(*) AS allnum FROM field_kill_num WHERE field_type = "+field_type;
			logger.debug(count_sql);
			rs = stmt.executeQuery(count_sql);
			if( rs.next() )
			{
				count = rs.getInt("allnum");
			}
			rs.close();

			queryPage = new  QueryPage(page_no,count);
			
			page_sql = "SELECT sfk.p_pk,p_grade,p_camp,p_name,COUNT(1) AS kill_num1 " +
					"FROM s_field_kill sfk,u_part_info upi " +
					"WHERE field_type = "+field_type+" AND field_sequence = "+field_sequence+ 
					"AND sfk.p_pk = upi.p_pk GROUP BY p_pk ORDER BY kill_num1 desc,p_grade ASC LIMIT " + queryPage.getStartOfPage() 
							+ ","+queryPage.getPageSize();
			
			logger.debug("查看月份排名榜="+page_sql);
			
			rs = stmt.executeQuery(page_sql);
			while (rs.next())
			{
				fieldKillVO = new FieldKillVO();
				fieldKillVO.setPPk(rs.getInt("p_pk"));
				fieldKillVO.setKilledNum(rs.getInt("kill_num1"));
				fieldKillVO.setKillerName(rs.getString("p_name"));
				fieldKillVO.setKillerGrade(rs.getInt("p_grade"));
				fieldKillVO.setKillerCamp(rs.getInt("p_camp"));
				
				list.add(fieldKillVO);
				
			}
			logger.debug("查看月份排名榜的多少="+list.size());
			rs.close();
			stmt.close();
			
			queryPage.setResult(list);

		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
 		
		return queryPage;
	}
	
	
	
	

}
