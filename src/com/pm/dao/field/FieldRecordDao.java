package com.pm.dao.field;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * 记录当场的荣誉值, 随后在让玩家查看一次后即被清楚。
 * @author Administrator
 *
 */
public class FieldRecordDao extends DaoBase {
	
	/**
	 * 添加战场荣誉的数据
	 * @param pPk
	 * @param menuOperate3
	 * @param previous
	 */
	public void addFieldGloryRecord(int pPk, String field_type,int sequence)
	{
		String sql = "INSERT INTO s_field_record VALUES (null,"+pPk+","+sequence+","+field_type+",0,now())";
		logger.debug("添加战场荣誉的数据="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			
			dbConn.closeConn();
		}
		
	}

	/**
	 * 更新本场荣誉
	 * @param pk
	 * @param menu_operate3
	 * @param fh_sequence
	 * @param intraday_value
	 */
	public void updateIntoFieldGloryRecord(int pk, String field_type,
			int fh_sequence, int intraday_value)
	{
		String sql = "UPDATE s_field_record SET sr_glory = sr_glory + "+intraday_value+" WHERE p_pk = "+pk
						+" AND sr_type = "+field_type+" AND sr_sequence = "+fh_sequence;
		logger.debug("更新本场荣誉="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			
			dbConn.closeConn();
		}
		
	}
	
	
	
	
}
