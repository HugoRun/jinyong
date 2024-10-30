package com.pm.dao.field;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.dao.map.SceneDao;
import com.ls.pub.constant.MenuType;
import com.ls.pub.db.DBConnection;

public class FieldDao extends DaoBase {
	
	/**
	 * 获得当前所有在战场的玩家list,
	 * 先获得scene_ID的数据,以"(110，112，116)"这样的形式来让第二个sql使用，
	 * 因为第二个要获得当前所有在战场的人员名单
	 * @return
	 
	public List<Integer> getNowInFieldPlayer2()
	{
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		
		int barea_point = FinalNumber.FIELDBAREA;
		
		SceneDao scenedao = new SceneDao();
		String sceneStr = scenedao.getSceneIdByMap(barea_point);
		
		String sql = "select p_pk from jygame_user_test.u_part_info where p_map in " +
				"( select scene_ID from jygame_test.scene where scene_mapqy = 3)";
		List<Integer> personlist =  new ArrayList<Integer>();
		try
		{
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				logger.debug("p_pk="+rs.getInt("p_pk"));
				personlist.add(rs.getInt("p_pk"));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			
			dbConn.closeConn();
		}
		return personlist;
	}*/


	/**
	 * 插入到表中，以便过滤器查找这个表
	 * @param personlist
	 */
	public void insertIntoRemoveTable(List personlist)
	{
		//TODO 插入到表中，以便过滤器查找这个表
		for(int i=0;i<personlist.size();i++) {
			
		}
		
	}
	
	/**
	 * 获得当前所有在map_id战场的玩家人数,
	 * @param p_camp 玩家阵营
	 * @param map_id 地图id
	 * @return
	 */
	public int getNowInFieldPlayerByCamp(int p_camp,String map_id)
	{
		/**DBConnection dbConn = new DBConnection(DBConnection.JYGAME_DB);
		conn = dbConn.getConn();
		String sql1 = "select map_ID from map where map_from = 9";
		int map_id = 0;
		try
		{
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(sql1);
			while (rs.next())
			{
				map_id = rs.getInt("map_ID");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}  finally
		{
			
			dbConn.closeConn();
		}*/
		
		SceneDao scenedao = new SceneDao();
		String sceneStr = scenedao.getSceneIdByMap(map_id);
		
		logger.debug("战场的map_id="+map_id+" ,战场的scene_id="+sceneStr);
		
		//如果当前的战场没有scene地图，就返回零
		if(sceneStr.length() == 2) {
			return 0;
		}
		
		String sql = "select p_pk from u_part_info where p_camp = "+p_camp+
				" and p_map in " + sceneStr;
		
		logger.debug("执行数据库统计的="+sql);
		List<Integer> personlist =  new ArrayList<Integer>();
		DBConnection dbConn1 = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn1.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				logger.debug("p_pk="+rs.getInt("p_pk"));
				personlist.add(rs.getInt("p_pk"));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			
			dbConn1.closeConn();
		}
		return personlist.size();
	}


	/**
	 * 将战场中的旗杆阵营属性还原，原阵营存放在menu_operate3。
	 * 
	 */
	public void rebackFlagCamp()
	{
		String sql = "update operate_menu_info set menu_camp = menu_operate3 where menu_type = "+MenuType.MAST;
		logger.debug("战场中的旗杆阵营属性还原="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
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
