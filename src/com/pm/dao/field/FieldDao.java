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
	 * ��õ�ǰ������ս�������list,
	 * �Ȼ��scene_ID������,��"(110��112��116)"��������ʽ���õڶ���sqlʹ�ã�
	 * ��Ϊ�ڶ���Ҫ��õ�ǰ������ս������Ա����
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
	 * ���뵽���У��Ա���������������
	 * @param personlist
	 */
	public void insertIntoRemoveTable(List personlist)
	{
		//TODO ���뵽���У��Ա���������������
		for(int i=0;i<personlist.size();i++) {
			
		}
		
	}
	
	/**
	 * ��õ�ǰ������map_idս�����������,
	 * @param p_camp �����Ӫ
	 * @param map_id ��ͼid
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
		
		logger.debug("ս����map_id="+map_id+" ,ս����scene_id="+sceneStr);
		
		//�����ǰ��ս��û��scene��ͼ���ͷ�����
		if(sceneStr.length() == 2) {
			return 0;
		}
		
		String sql = "select p_pk from u_part_info where p_camp = "+p_camp+
				" and p_map in " + sceneStr;
		
		logger.debug("ִ�����ݿ�ͳ�Ƶ�="+sql);
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
	 * ��ս���е������Ӫ���Ի�ԭ��ԭ��Ӫ�����menu_operate3��
	 * 
	 */
	public void rebackFlagCamp()
	{
		String sql = "update operate_menu_info set menu_camp = menu_operate3 where menu_type = "+MenuType.MAST;
		logger.debug("ս���е������Ӫ���Ի�ԭ="+sql);
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
