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
		String sqlString = "UPDATE injure_recond_info SET injure_number = injure_number + "+recordValue+" WHERE tong_id="+tongId
					+" AND npc_Type="+npcType;
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
    		String sql = "INSERT INTO injure_recond_info VALUES (null,"+tongId+","+recordValue+","+npcId+","+npcType+")";
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
		String sql = "SELECT tong_id FROM injure_recond_info WHERE npc_Type = "+npc_type+" AND tong_id != 0 ORDER BY injure_number DESC LIMIT 5 ";
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
