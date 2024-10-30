package com.lw.dao.player;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.player.QudaoVO;

public class QudaoStaDao extends DaoBase
{
	Logger logger = Logger.getLogger("log.quartz");
	/** 得到玩家的有效注册用户数 */
	public List<QudaoVO> selectnum(String date)
	{
		List<QudaoVO> list = new ArrayList<QudaoVO>();
		QudaoVO vo = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT COUNT(*) AS num , a.channel_id FROM u_passport_info AS a ,u_part_info AS b WHERE a.u_pk = b.u_pk AND b.p_grade>2 AND a.create_time LIKE '%"
				+ date + "%' GROUP BY a.channel_id ";
		logger.debug("sql:"+sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new QudaoVO();
				vo.setQudao(rs.getString("channel_id"));
				vo.setNum(rs.getString("num"));
				list.add(vo);
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
		return list;
	}

	public void insertNum(String channel, String num)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "INSERT INTO channel_id_num VALUES (null,'" + channel
				+ "','" + num + "',now())";
		logger.debug("sql:"+sql);
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}
}
