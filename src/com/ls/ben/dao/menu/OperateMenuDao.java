package com.ls.ben.dao.menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.pub.constant.MenuType;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * ����:npc�˵���Ĳ���
 * 
 * @author ��˧ 12:54:24 PM
 */
public class OperateMenuDao extends DaoBase
{
	/**
	 * �õ�����menu,�����������µĸ��˵�
	 * @return
	 * @throws Exception 
	 */
	public HashMap<String,OperateMenuVO> getAllMenu(HashMap<String,SceneVO> scene_list) throws Exception
	{
		HashMap<String,OperateMenuVO> menu_list = null;
		OperateMenuVO menu = null;
		SceneVO scene = null;
		
		String total_sql = "select count(id) from  operate_menu_info";
		String all_data_sql = "select * from  operate_menu_info order by id, menu_order ";
		logger.debug("total_sql:"+total_sql);
		logger.debug("all_data_sql:"+all_data_sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			int total = 0;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(total_sql);
			if( rs.next() )
			{
				total = rs.getInt(1);
			}
			
			menu_list = new HashMap<String,OperateMenuVO>(total);
			
			rs = stmt.executeQuery(all_data_sql);
			while (rs.next())
			{
				menu = new OperateMenuVO();
				menu.setId(rs.getInt("id"));
				menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
				menu.setMenuMap(rs.getInt("menu_map"));
				menu.setMenuType(rs.getInt("menu_type"));
				menu.setMenuImg(rs.getString("menu_img"));
				menu.setMenuDialog(StringUtil.isoToGBK(rs
						.getString("menu_dialog")));
				menu.setMenuCamp(rs.getInt("menu_camp"));
				
				menu.setMenuOperate1(rs.getString("menu_operate1"));
				menu.setMenuOperate2(rs.getString("menu_operate2"));
				menu.setMenuOperate3(rs.getString("menu_operate3"));
				
				menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
				menu.setMenuTimeEnd(rs.getString("menu_time_end"));
				menu.setMenuDayBegin(rs.getString("menu_day_begin"));
				menu.setMenuDayEnd(rs.getString("menu_day_end"));
				
				menu.setMenuFatherId(rs.getInt("menu_father_id"));
				menu.setMenuTasksId(rs.getString("menu_tasks_id"));
				menu.setMenuTaskFlag(rs.getInt("menu_task_flag"));
				menu.setMenuOperate4(rs.getInt("menu_operate4"));
				menu_list.put(menu.getId()+"", menu);
				if( menu.getMenuMap()!=0 && menu.getMenuFatherId()==0 && menu.getMenuTaskFlag()==0 )
				{
					//����Ǹ��˵����Ҳ�������˵�
					scene = scene_list.get(""+menu.getMenuMap());
					if( scene==null )
					{
						throw new Exception("���˵������ݴ����޸ó�������, menu.getMenuMap()="+menu.getMenuMap());
					}
					scene.getFatherMenuList().put(menu.getId()+"", menu);
				}
			}
			rs.close();
			stmt.close();
		} catch (Exception e)
		{
			e.getStackTrace();
			throw new Exception("�˵���Ϣ�����ڴ�ʧ�ܣ���ǰ�˵�Ϊ��"+menu.getId()+";������Ϣ:"+e.getMessage());
			
		} finally
		{
			dbConn.closeConn();
		}
		return menu_list;
	}
	
	

	
	/**
	 * ͨ��id�õ� menu
	 * 
	 * @param map_id
	 * @return
	 */
	public OperateMenuVO getMenuById(int id)
	{
		OperateMenuVO menu = null;
		String sql = "select * from  operate_menu_info where id=" + id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				menu = new OperateMenuVO();
				menu.setId(rs.getInt("id"));
				menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
				menu.setMenuType(rs.getInt("menu_type"));
				menu.setMenuImg(rs.getString("menu_img"));
				menu.setMenuMap(rs.getInt("menu_map"));
				menu.setMenuOperate1(rs.getString("menu_operate1"));
				menu.setMenuOperate2(rs.getString("menu_operate2"));
				menu.setMenuOperate3(rs.getString("menu_operate3"));
				menu.setMenuCamp(rs.getInt("menu_camp"));
				menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
				menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
				menu.setMenuTimeEnd(rs.getString("menu_time_end"));
				menu.setMenuDayBegin(rs.getString("menu_day_begin"));
				menu.setMenuDayEnd(rs.getString("menu_day_end"));
				menu.setMenuFatherId(rs.getInt("menu_father_id"));
				menu.setMenuOrder(rs.getInt("menu_order"));
				menu.setMenuTasksId(rs.getString("menu_tasks_id"));
				menu.setMenuTaskFlag(rs.getInt("menu_task_flag"));
				menu.setMenuOperate4(rs.getInt("menu_operate4"));
			}
			rs.close();
			stmt.close();
		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());

		} finally
		{
			dbConn.closeConn();
		}
		return menu;
	}
	
	
	/**
	 * ����menu_type����ò˵�
	 * 
	 * @param map_id
	 * @return
	 */
	public List<OperateMenuVO> getMainMenuByMenuType(int menu_type)
	{
		List<OperateMenuVO> list = new ArrayList<OperateMenuVO>();
		OperateMenuVO menu = null;
		String sql = "select * from  operate_menu_info where menu_type = "+menu_type;
		logger.debug("����menu_type����ò˵�="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				menu = new OperateMenuVO();
				menu.setId(rs.getInt("id"));
				menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
				menu.setMenuMap(rs.getInt("menu_map"));
				menu.setMenuType(rs.getInt("menu_type"));
				menu.setMenuImg(rs.getString("menu_img"));

				menu.setMenuCamp(rs.getInt("menu_camp"));
				menu.setMenuDialog(StringUtil.isoToGBK(rs
						.getString("menu_dialog")));

				menu.setMenuOperate1(rs.getString("menu_operate1"));
				menu.setMenuOperate2(rs.getString("menu_operate2"));
				menu.setMenuOperate3(rs.getString("menu_operate3"));

				menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
				menu.setMenuTimeEnd(rs.getString("menu_time_end"));
				menu.setMenuDayBegin(rs.getString("menu_day_begin"));
				menu.setMenuDayEnd(rs.getString("menu_day_end"));

				menu.setMenuFatherId(rs.getInt("menu_father_id"));
				menu.setMenuTasksId(rs.getString("menu_tasks_id"));
				menu.setMenuOperate4(rs.getInt("menu_operate4"));
				list.add(menu);
			}
			rs.close();
			stmt.close();

			logger.debug("operateMenuDap�е�menu����Ϊ : " +list.size());
			return list;
		}
		catch (Exception e)

		{
			e.getStackTrace();

		} finally
		{
			dbConn.closeConn();
		}
		return list;
	}
	

	/**
	 * �õ����˵�idΪmenu_father_id�������Ӳ˵�
	 * @param menu_father_id		���˵�id
	 * @param race					����
	 * @return
	 */
	public List<OperateMenuVO> getSonMenuByMap(int menu_father_id,int race)
	{
		List<OperateMenuVO> list = new ArrayList<OperateMenuVO>();
		OperateMenuVO menu = null;
		String sql = "select * from  operate_menu_info where menu_father_id="
				+ menu_father_id + " and menu_camp in (0,"+race+")  order by menu_order ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				menu = new OperateMenuVO();
				menu.setId(rs.getInt("id"));
				menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
				menu.setMenuMap(rs.getInt("menu_map"));
				menu.setMenuType(rs.getInt("menu_type"));
				menu.setMenuImg(rs.getString("menu_img"));
				menu.setMenuDialog(StringUtil.isoToGBK(rs
						.getString("menu_dialog")));
				menu.setMenuCamp(rs.getInt("menu_camp"));

				menu.setMenuOperate1(rs.getString("menu_operate1"));
				menu.setMenuOperate2(rs.getString("menu_operate2"));
				menu.setMenuOperate3(rs.getString("menu_operate3"));

				menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
				menu.setMenuTimeEnd(rs.getString("menu_time_end"));
				menu.setMenuDayBegin(rs.getString("menu_day_begin"));
				menu.setMenuDayEnd(rs.getString("menu_day_end"));
				menu.setMenuFatherId(menu_father_id);
				menu.setMenuTasksId(rs.getString("menu_tasks_id"));
				menu.setMenuOperate4(rs.getInt("menu_operate4"));
				list.add(menu);
			}
			rs.close();
			stmt.close();

		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
		return list;
	}
	
	/**
	 * ͨ��menu_type����Ӫ�����õ��õ� menu����
	 * 
	 * @param menu_type Ŀ¼����
	 * @param camp ��Ӫ����
	 * @return
	 */
	public List<OperateMenuVO> getMenuByMenuTypeAndCamp(int menu_type,int camp)
	{
		List<OperateMenuVO> list = new ArrayList<OperateMenuVO>();
		OperateMenuVO menu = null;
		String sql = "select * from operate_menu_info where menu_type = "+menu_type+" and menu_camp = "+camp;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				menu = new OperateMenuVO();
				menu.setId(rs.getInt("id"));
				//menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
				menu.setMenuMap(rs.getInt("menu_map"));
				menu.setMenuType(rs.getInt("menu_type"));
				//menu.setMenuImg(rs.getString("menu_img"));
				//menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
				menu.setMenuCamp(rs.getInt("menu_camp"));
				menu.setMenuOperate4(rs.getInt("menu_operate4"));
				//menu.setMenuOperate1(rs.getString("menu_operate1"));
				//menu.setMenuOperate2(rs.getString("menu_operate2"));
				//menu.setMenuOperate3(rs.getString("menu_operate3"));

				//menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
				//menu.setMenuTimeEnd(rs.getString("menu_time_end"));
				//menu.setMenuDayBegin(rs.getString("menu_day_begin"));
				//menu.setMenuDayEnd(rs.getString("menu_day_end"));
				//menu.setMenuRefurbishTime(rs.getString("menu_refurbish_time"));
				//menu.setMenuFatherId(rs.getInt("menu_father_id"));
				//menu.setMenuTasksId(rs.getString("menu_tasks_id"));
				list.add(menu);
			}
			rs.close();
			stmt.close();

		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
		return list;
	}

	/**
	 * ���npc_id��������id
	 * 
	 * 03.25 �޸ģ�����������id ǰ���,
	 * @param taskid
	 * @return
	 */
	public OperateMenuVO getNpcIdByTaskId (String taskid)
	{
		OperateMenuVO vo = null;
		String searchTask = ","+taskid+",";
		String sql = "select id,menu_map from operate_menu_info where menu_tasks_id like '%"+searchTask+"%' ";
		logger.debug("���npc_id��������id="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try	
		{
			vo = new OperateMenuVO();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo.setId(rs.getInt("id"));
				vo.setMenuMap(rs.getInt("menu_map"));
			} 
			rs.close();
			stmt.close();

		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
		return vo;
	}
	
	/**
	 * ���menuvo���� sceneId��menuOperate1
	 * @param sceneId
	 * @param menuOperate1
	 * @return
	 */
	public OperateMenuVO getOperateMenuVOBySceneAndOperate1(String sceneId,
			String menuOperate1)
	{
		
		String sql = "select * from operate_menu_info where menu_map="+sceneId+" and menu_operate1="+menuOperate1;
		
		OperateMenuVO menu = null;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				menu = new OperateMenuVO();
				menu.setId(rs.getInt("id"));
				menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
				menu.setMenuMap(rs.getInt("menu_map"));
				menu.setMenuType(rs.getInt("menu_type"));
				menu.setMenuImg(rs.getString("menu_img"));
				menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
				menu.setMenuCamp(rs.getInt("menu_camp"));

				menu.setMenuOperate1(rs.getString("menu_operate1"));
				menu.setMenuOperate2(rs.getString("menu_operate2"));
				menu.setMenuOperate3(rs.getString("menu_operate3"));

				menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
				menu.setMenuTimeEnd(rs.getString("menu_time_end"));
				menu.setMenuDayBegin(rs.getString("menu_day_begin"));
				menu.setMenuDayEnd(rs.getString("menu_day_end"));
				menu.setMenuFatherId(rs.getInt("menu_father_id"));
				menu.setMenuTasksId(rs.getString("menu_tasks_id"));
				menu.setMenuOperate4(rs.getInt("menu_operate4"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
		
		
		
		return menu;
	}
	
	
	/**
	 * ���menuvo���� sceneId��menu_type
	 * @param sceneId
	 * @param type
	 * @return
	 */
	public OperateMenuVO getOperateMenuVOBySceneAndType(int sceneId,
			int type)
	{
		
		String sql = "select * from operate_menu_info where menu_map="+sceneId+" and menu_type="+type;
		
		OperateMenuVO menu = null;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				menu = new OperateMenuVO();
				menu.setId(rs.getInt("id"));
				menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
				menu.setMenuMap(rs.getInt("menu_map"));
				menu.setMenuType(rs.getInt("menu_type"));
				menu.setMenuImg(rs.getString("menu_img"));
				menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
				menu.setMenuCamp(rs.getInt("menu_camp"));

				menu.setMenuOperate1(rs.getString("menu_operate1"));
				menu.setMenuOperate2(rs.getString("menu_operate2"));
				menu.setMenuOperate3(rs.getString("menu_operate3"));

				menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
				menu.setMenuTimeEnd(rs.getString("menu_time_end"));
				menu.setMenuDayBegin(rs.getString("menu_day_begin"));
				menu.setMenuDayEnd(rs.getString("menu_day_end"));
				menu.setMenuFatherId(rs.getInt("menu_father_id"));
				menu.setMenuTasksId(rs.getString("menu_tasks_id"));
				menu.setMenuOperate4(rs.getInt("menu_operate4"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
		
		
		
		return menu;
	}

	/**
	 * ������˵���Ӫ����
	 * @param menu_id
	 * @param p_camp
	 */
	public void updateOperateMenuCamp(String menu_id, int menu_camp)
	{
		String sql = "update operate_menu_info set menu_camp="+menu_camp+" where id=" + menu_id + "";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * �����������Ա���ͺ����ڵ�ͼid��ȷ��menu_operate1
	 * @param flag_type
	 * @param map_ID
	 * @return
	 */
	public String getJoinPointByFieldManager(int field_manager_type, int map_ID)
	{
		String sql = "select menu_operate1 from operate_menu_info where menu_type = "+field_manager_type
						+ " and menu_operate2 like '%"+map_ID+"%'";
		logger.debug("�����������Ա���ͺ����ڵ�ͼid��ȷ�������="+sql);
		String menu_operate1 = "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				menu_operate1 = rs.getString("menu_operate1");
			} 
			rs.close();
			stmt.close();

		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
		return menu_operate1;
	}
	
	/**
	 * �����������Ա���ͺ����ڵ�ͼid��ȷ��menu_operate2
	 * Ŀ���ǻ�ô�ս���ĳ�ȥ��
	 * @param flag_type
	 * @param map_ID
	 * @return
	 */
	public String getOutPointByFieldManager(int field_manager_type, int map_ID)
	{
		String sql = "select menu_operate2 from operate_menu_info where menu_type = "+field_manager_type
						+ " and menu_operate2 like '%"+map_ID+"%'";
		
		logger.debug("�����������Ա���ͺ����ڵ�ͼid��ȷ�������="+sql);
		String menu_operate2 = "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				menu_operate2 = rs.getString("menu_operate2");
			} 
			rs.close();
			stmt.close();

		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
		return menu_operate2;
	}
	
	/**
	 * �����������Ա���ͺ����ڵ�ͼid��ȷ��menu_operate3
	 * Ŀ���ǻ�ô�ս���ĳ�ȥ��
	 * @param flag_type
	 * @param map_ID
	 * @return
	 */
	public String getFieldTypeByFieldManager(int field_manager_type, int map_ID)
	{
		String sql = "select menu_operate3 from operate_menu_info where menu_type = "+field_manager_type
						+ " and menu_operate3 like '%"+map_ID+"%'";
		
		logger.debug("�����������Ա���ͺ����ڵ�ͼid��ȷ�������="+sql);
		String menu_operate3 = "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				menu_operate3 = rs.getString("menu_operate3");
			} 
			rs.close();
			stmt.close();

		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
		return menu_operate3;
	}

	/**
	 * �����npc�����󣬽���˵���Ӫת��Ϊ�������ʤ���ߵ���Ӫ,Ҳ���Ƕ�����Ӫ
	 * @param npcID
	 * @param camp
	 */
	public void updateMastCamp(int menu_type, int camp,int map)
	{
		String sql = "update operate_menu_info set menu_camp = "+camp 
					+" where menu_type = "+menu_type+" and menu_map ="+map+"";
		logger.debug("���npc����ת����Ӫ="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * ���л��������,����ĵ���������ת��
	 * @param npcID
	 * @param camp
	 */
	public int updateZHAOHUN(String sceneId, String menuOperate1, int tongPk)
	{
		menuOperate1 = menuOperate1 + ",1";
		int menu_id = 0;
		String sql = "update operate_menu_info set menu_operate3 = '"+tongPk 
					+"' where menu_map = "+sceneId+" and menu_operate1 like '%"+menuOperate1+"%'";
		logger.debug("���npc����ת����Ӫ="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		}
		
		String sql2 = "select id from operate_menu_info where menu_map = "+sceneId+" and menu_operate1 ='"+menuOperate1+"'";
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			if (rs.next()) {
				menu_id = rs.getInt("id");
			}
			rs.close();
			stmt.close();
		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
		
		return menu_id;
	}



	/**
	 * �л�ᦵĳ�ʼ��
	 * @param npcID
	 * @param camp
	 */
	public void updateZHAOHUN()
	{
		String sql = "update operate_menu_info set menu_operate3 = '-1' where menu_type = "+MenuType.ZHAOHUN;
		logger.debug("�л�ᦵĳ�ʼ��="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * ��ò˵� �������ͺ���������3
	 * @param menuType
	 * @param menuoperate3
	 * @return
	 */
	public List<OperateMenuVO> getOperateMenuByTypeAndOperate3(int menuType,
			String menuoperate3)
	{
		String sql = "select * from operate_menu_info where menu_type="+menuType+" and menu_operate3='"+menuoperate3+"'";
		List<OperateMenuVO> list = new ArrayList<OperateMenuVO>();
		OperateMenuVO menu = null;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				menu = new OperateMenuVO();
				menu.setId(rs.getInt("id"));
				menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
				menu.setMenuMap(rs.getInt("menu_map"));
				menu.setMenuType(rs.getInt("menu_type"));
				menu.setMenuImg(rs.getString("menu_img"));
				menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
				menu.setMenuCamp(rs.getInt("menu_camp"));

				menu.setMenuOperate1(rs.getString("menu_operate1"));
				menu.setMenuOperate2(rs.getString("menu_operate2"));
				menu.setMenuOperate3(rs.getString("menu_operate3"));

				menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
				menu.setMenuTimeEnd(rs.getString("menu_time_end"));
				menu.setMenuDayBegin(rs.getString("menu_day_begin"));
				menu.setMenuDayEnd(rs.getString("menu_day_end"));
				menu.setMenuFatherId(rs.getInt("menu_father_id"));
				menu.setMenuTasksId(rs.getString("menu_tasks_id"));
				menu.setMenuOperate4(rs.getInt("menu_operate4"));
				list.add(menu);
			}
			rs.close();
			stmt.close();

		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
		return list;		
	}

	private List<OperateMenuVO> getMenu(ResultSet rs) throws SQLException
	{
		List<OperateMenuVO> list = new ArrayList<OperateMenuVO>();
		if (rs != null)
		{
			while (rs.next())
			{
				OperateMenuVO menu = new OperateMenuVO();
				menu.setId(rs.getInt("id"));
				menu.setMenuName(StringUtil.isoToGBK(rs.getString("menu_name")));
				menu.setMenuMap(rs.getInt("menu_map"));
				menu.setMenuType(rs.getInt("menu_type"));
				menu.setMenuImg(rs.getString("menu_img"));
				menu.setMenuDialog(StringUtil.isoToGBK(rs.getString("menu_dialog")));
				menu.setMenuCamp(rs.getInt("menu_camp"));

				menu.setMenuOperate1(rs.getString("menu_operate1"));
				menu.setMenuOperate2(rs.getString("menu_operate2"));
				menu.setMenuOperate3(rs.getString("menu_operate3"));

				menu.setMenuTimeBegin(rs.getString("menu_time_begin"));
				menu.setMenuTimeEnd(rs.getString("menu_time_end"));
				menu.setMenuDayBegin(rs.getString("menu_day_begin"));
				menu.setMenuDayEnd(rs.getString("menu_day_end"));

				menu.setMenuFatherId(rs.getInt("menu_father_id"));
				menu.setMenuTasksId(rs.getString("menu_tasks_id"));
				menu.setMenuTaskFlag(rs.getInt("menu_task_flag"));
				menu.setMenuOperate4(rs.getInt("menu_operate4"));
				
				menu.setWeekStr(rs.getString("week_str"));
				list.add(menu);
			}
		}
		return list;
	}
	
	public List<OperateMenuVO> findAll_Sheare_menu()
	{
		String sql = "select * from operate_menu_info o where o.menu_type = "
				+ MenuType.SHEARE;
		List<OperateMenuVO> list = new ArrayList<OperateMenuVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = getMenu(rs);
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
			return list;
		}
	}
	
	public void updateOperateMenuMenpaiConstant(String menu_id, String menu_name)
	{
		String sql = "update operate_menu_info set menu_name= '"+menu_name+"' where id=" + menu_id + "";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
	}
	
	public void updateOperateMenuMenpaiNpc(String menu_id, int menu_operate4)
	{
		String sql = "update operate_menu_info set menu_operate4="+menu_operate4+" where id=" + menu_id + "";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (Exception e)
		{
			logger.debug(e.getStackTrace());
		} finally
		{
			dbConn.closeConn();
		}
	}
	
	
}
