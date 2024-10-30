package com.pm.dao.record;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * ��¼��ҵĶ�npc���˺�
 * @author zhangjj
 *
 */
public class RecordDao extends DaoBase
{

	/**
	 * ���¼�¼��Ҷ�npc�˺�
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
		
		// ���Ϊ��,��ζ�Ŵ˰��ɻ�û������
		if ( result == 0) {
    		String sql = "insert into injure_recond_info values (null,"+tongId+","+recordValue+","+npcId+","+npcType+")";
    		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
    		logger.debug(" ���¼�¼��Ҷ�npc�˺�sql="+sql);		
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
	 * ѡȡ�˺�����Ѫ�����������,
	 * 
	 *��Ҫ�����ϲ�ѯ .
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
