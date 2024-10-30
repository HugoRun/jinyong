package com.ls.ben.dao.map;

import java.sql.SQLException;
import java.util.HashMap;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 *	������
 */
public class SceneDao extends DaoBase {
	
	/**
	 * �������barea�µ����ݱ�ʾ
	 * ���scene_ID������,��"(110��112��116)"��������ʽ����ʾ
	 * @return
	 */
	public String getSceneIdByBarea(int barea_point)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		
		conn = dbConn.getConn();
		String sql1 = "select scene.scene_ID from scene,map,barea where barea.barea_ID = map.map_from " +
				"and map.map_ID = scene.scene_mapqy and barea_ID = "+barea_point;
		StringBuffer str = new StringBuffer("(");
		try
		{
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(sql1);
			while (rs.next())
			{
				str.append(rs.getInt("scene_ID")).append(",");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally {
			dbConn.closeConn();
		}
		str.append(")");
		
		
		String scene_str = str.toString();
		return scene_str.replace(",)", ")");
	}
	
	/**
	 * �������map_ID�µ����ݱ�ʾ
	 * ���scene_ID������,��"(110��112��116)"��������ʽ����ʾ
	 * @return
	 */
	public String getSceneIdByMap(String map_ID)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		
		conn = dbConn.getConn();
		String sql = "select scene.scene_ID from scene,map where  map.map_ID = scene.scene_mapqy " +
				"and map_ID = "+map_ID;
		logger.debug("* �������map_ID�µ����ݱ�ʾ="+sql);
		StringBuffer str = new StringBuffer("(");
		try
		{
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				str.append(rs.getInt("scene_ID")).append(",");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally {
			dbConn.closeConn();
		}
		str.append(")");
		
		
		String scene_str = str.toString();
		return scene_str.replace(",)", ")");
	}
	
	
	/**
	 * �������map_ID�µ����ݱ�ʾ
	 * ���scene_ID������,��"(110��112��116)"��������ʽ����ʾ
	 * @return
	 */
	public String getSceneIdByMap(String map_ID,String sceneName)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		
		conn = dbConn.getConn();
		String sql = "select scene_ID from scene where  scene_mapqy = "+map_ID+" and scene_Name like '%"+sceneName+"%'";
		logger.debug("* �������map_ID�µ����ݱ�ʾ="+sql);
		StringBuffer str = new StringBuffer("(");
		try
		{
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				str.append(rs.getInt("scene_ID")).append(",");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally {
			dbConn.closeConn();
		}
		str.append(")");
		
		
		String scene_str = str.toString();
		return scene_str.replace(",)", ")");
	}
	
	/**
	 * �õ�����scene��Ϣ
	 * @return
	 * @throws Exception 
	 */
	public HashMap<String,SceneVO> getAllScene(HashMap<String, MapVO> map_list) throws Exception
	{
		HashMap<String,SceneVO> scene_list = null;
		
		int total_num = 0;
		SceneVO scene = null;
		MapVO map = null;
		
		String total_num_sql = "select count(*) from scene";
		String sql = "select * from scene";
		
		logger.debug("�õ�����scene��Ϣsql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(total_num_sql);
			if( rs.next() )
			{
				total_num = rs.getInt(1); 
			}
			
			scene_list = new HashMap<String,SceneVO>(total_num);
			
			rs = stmt.executeQuery(sql);
			
			while( rs.next() )
			{
				scene = new SceneVO();
				scene.setSceneID(rs.getInt("scene_ID"));
				scene.setSceneName(rs.getString("scene_Name"));
				scene.setSceneCoordinate(rs.getString("scene_coordinate"));
				scene.setSceneLimit(rs.getString("scene_limit"));
				
				scene.setSceneJumpterm(rs.getInt("scene_jumpterm"));
				scene.setSceneAttribute(rs.getString("scene_attribute"));
				scene.setSceneSwitch(rs.getInt("scene_switch"));
				scene.setSceneKen(rs.getString("scene_ken"));
				
				scene.setSceneSkill(rs.getInt("scene_skill"));
				scene.setScenePhoto(rs.getString("scene_photo"));
				scene.setSceneDisplay(rs.getString("scene_display"));
				scene.setSceneMapqy(rs.getString("scene_mapqy"));
				
				scene.setSceneShang(rs.getInt("scene_shang"));
				scene.setSceneXia(rs.getInt("scene_xia"));
				scene.setSceneZuo(rs.getInt("scene_zuo"));
				scene.setSceneYou(rs.getInt("scene_you"));
				
				map = map_list.get(""+scene.getSceneMapqy());
				
				if( map==null )
				{
					String errer = "SceneDao.getAllScene:���س������ݴ����޸ó������ڵ�map_id��scene_id="+scene.getSceneID()+";map_id="+scene.getSceneMapqy();
					throw new Exception(errer);
				}
				else
				{
					scene.setMap(map);
				}
				scene_list.put(""+scene.getSceneID(), scene);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("scene��Ϣ�����ڴ�ʧ�ܣ���ǰscene_idΪ��"+scene.getSceneID()+";������Ϣ:"+e.getMessage());
		}
		finally
		{
			dbConn.closeConn();
		}
		return scene_list;
	}
	
	/**
	 * �õ�����scene��Ϣ
	 * @return
	 */
	public SceneVO getByIdSceneView(String sceneID)
	{ 
		SceneVO scene = null; 
		String sql = "select * from scene where scene_ID = '"+sceneID+"'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			while( rs.next() )
			{
				scene = new SceneVO();
				scene.setSceneID(rs.getInt("scene_ID"));
				scene.setSceneName(rs.getString("scene_Name"));
				scene.setSceneCoordinate(rs.getString("scene_coordinate"));
				scene.setSceneLimit(rs.getString("scene_limit")); 
				scene.setSceneJumpterm(rs.getInt("scene_jumpterm"));
				scene.setSceneAttribute(rs.getString("scene_attribute"));
				scene.setSceneSwitch(rs.getInt("scene_switch"));
				scene.setSceneKen(rs.getString("scene_ken"));
				scene.setSceneSkill(rs.getInt("scene_skill"));
				scene.setScenePhoto(rs.getString("scene_photo"));
				scene.setSceneDisplay(rs.getString("scene_display"));
				scene.setSceneMapqy(rs.getString("scene_mapqy"));
				scene.setSceneShang(rs.getInt("scene_shang"));
				scene.setSceneXia(rs.getInt("scene_xia"));
				scene.setSceneZuo(rs.getInt("scene_zuo"));
				scene.setSceneYou(rs.getInt("scene_you")); 
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("scene��Ϣ�����ڴ�ʧ�ܣ���ǰscene_idΪ��"+scene.getSceneID()+";������Ϣ:"+e.getMessage());
		}
		finally
		{
			dbConn.closeConn();
		}
		return scene;
	}
}
