package com.pm.dao.field;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;
import com.pm.vo.field.FieldKillNumVO;

/**
 * 此表主要是存放战场月排行数据
 * @author Administrator
 *
 */
public class FieldKillNumDao extends DaoBase {
	/**
	 * 将杀人信息插入到战场月排行表中, 此表记录信息比较简单，但保存时间较长。
	 * @param pPk
	 * @param bPpk
	 * @param fh_id 场次
	 */
	public int insertIntoFieldMouthKill(int pPk,String todayStr,String field_type)
	{
		String sql = "UPDATE field_kill_num SET kill_num = kill_num + 1 WHERE p_pk = "
						+pPk+" AND field_type = "+field_type+" AND create_time LIKE '%"+todayStr+"%'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("将杀人信息插入到战场月排行表中="+sql);
		int updateLine = 0;
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();		
			updateLine = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return updateLine;
	}

	public void insertIntoFieldMouth(int pPk,String field_type)
	{
		String sql = "INSERT INTO field_kill_num value (null,"+pPk+",1,"+field_type+",now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("将杀人信息插入到战场月排行表中="+sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();		
			stmt.executeUpdate(sql);
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
	 * 查看月份排名榜
	 * @param page_no
	 * @return
	 */
	public QueryPage getMouthUntangleList(int page_no,String field_type)
	{
		QueryPage queryPage = null;
		
		List<FieldKillNumVO> list = new ArrayList<FieldKillNumVO>();
		FieldKillNumVO fieldKillNumVO = null;
		
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
			
			page_sql = "SELECT fkn.p_pk,p_name,p_camp,p_grade,COUNT(kill_num) AS kill_num1 " +
							"FROM field_kill_num fkn,u_part_info upi WHERE field_type ="+ field_type
							+" AND fkn.p_pk = upi.p_pk GROUP BY fkn.p_pk ORDER BY kill_num1 desc,p_grade ASC,create_time DESC LIMIT " + queryPage.getStartOfPage()
							+ ","+queryPage.getPageSize();
			
			logger.debug("查看月份排名榜="+page_sql);
			
			rs = stmt.executeQuery(page_sql);
			while (rs.next())
			{
				fieldKillNumVO = new FieldKillNumVO();
				fieldKillNumVO.setP_pk(rs.getInt("p_pk"));
				fieldKillNumVO.setMouthCountKill(rs.getInt("kill_num1"));
				fieldKillNumVO.setPName(rs.getString("p_name"));
				fieldKillNumVO.setPCamp(rs.getInt("p_camp"));
				fieldKillNumVO.setPGrade(rs.getInt("p_grade"));
				
				list.add(fieldKillNumVO);
				
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
