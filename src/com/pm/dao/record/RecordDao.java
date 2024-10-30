package com.pm.dao.record;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * 记录玩家的对npc的伤害
 * @author zhangjj
 *
 */
public class RecordDao extends DaoBase
{

	/**
	 * 更新记录玩家对npc伤害
	 * @param tongId
	 * @param recordValue
	 * @param id
	 * @param npcType
	 */
	public void updateInjure(int tongId, int recordValue, int npcId, int npcType)
	{
		int result = 0;
		String sqlString = "update injure_recond_info set injure_number = injure_number + "+recordValue+" where tong_id="+tongId
					+" and npc_Type="+npcType;
		DBConnection dbConn2 = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn2.getConn();
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sqlString);
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
			dbConn2.closeConn();
		}
		
		// 如果为零,意味着此帮派还没有数据
		if ( result == 0) {
    		String sql = "insert into injure_recond_info values (null,"+tongId+","+recordValue+","+npcId+","+npcType+")";
    		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
    		logger.debug(" 更新记录玩家对npc伤害sql="+sql);		
    		try{
    			conn = dbConn.getConn();
    			stmt = conn.createStatement();
    			stmt.executeUpdate(sql);
    			stmt.close();
    		} catch (SQLException e) {
    				e.printStackTrace();
    		} finally {
    				dbConn.closeConn();
    		}
		}		
	}

	/**
	 * 选取伤害怪物血最多的五个帮派,
	 * 
	 *需要做联合查询 .
	* @param npc_type
	 * @return
	 */
	 
	public List<Integer> getKillNumMaxFiveTongId(int npc_type)
	{
		List<Integer> ranklist = new ArrayList<Integer>();
		String sql = "select tong_id from injure_recond_info where npc_Type = "+npc_type+" and tong_id != 0 order by injure_number desc limit 5 ";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ranklist.add(rs.getInt("tong_id"));
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
		
		
		
		return ranklist;
	}
	
	

}
