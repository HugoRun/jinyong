package com.lw.vo.sinasys;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class SinaDAO extends DaoBase
{
	public List<SinaUserVO> getSinaList(String time)
	{
		List<SinaUserVO> list = new ArrayList<SinaUserVO>();
		SinaUserVO vo = null;
		String sql = "SELECT a.create_time,a.u_pk,(CASE WHEN p_grade is not null THEN p_grade+1 ELSE '1' end) AS p_grade,a.qudao FROM u_login_info AS a left join u_part_info AS b ON a.u_pk = b.u_pk WHERE a.create_time LIKE '%"
				+ time + "%' GROUP BY a.u_pk ORDER BY a.u_pk";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new SinaUserVO();
				vo.setDate(rs.getTimestamp("create_time"));
				vo.setP_pk(rs.getInt("u_pk"));
				vo.setGrade(rs.getInt("p_grade"));
				vo.setWm(rs.getString("qudao"));
				list.add(vo);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return list;
	}
}
