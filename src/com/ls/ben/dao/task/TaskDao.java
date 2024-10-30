package com.ls.ben.dao.task;

import java.sql.ResultSet;
import java.util.HashMap;

import com.ben.vo.task.TaskVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.pub.db.jygamedb.JyGameDB;

/**
 * 功能:
 * 
 * @author 刘帅 10:36:42 AM
 */
public class TaskDao {
	JyGameDB con;
	
	/**
	 * 得到所有任务
	 */
	public HashMap<String,TaskVO> getAllTask()  
	{
		HashMap<String,TaskVO> task_list = null;
		TaskVO vo = new TaskVO();
		int count = 0;
		
		try {
			con = new JyGameDB();

			String total_sql = "SELECT COUNT(t_id) FROM `task`";
			String all_data_sql = "SELECT * FROM `task`";
			
			ResultSet rs = con.query(total_sql);
			
			if( rs.next() )
			{
				count = rs.getInt(1);
			}
			
			task_list = new HashMap<String,TaskVO>(count);
			
			rs = con.query(all_data_sql);
			
			while (rs.next()) 
			{ 
				vo = new TaskVO();
				vo.setTId(rs.getInt("t_id"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTZuxl(rs.getString("t_zuxl"));
				vo.setTName(rs.getString("t_name"));
				vo.setTLevelXiao(rs.getInt("t_level_xiao"));
				vo.setTLevelDa(rs.getInt("t_level_da"));
				vo.setTSchool(rs.getString("t_school"));
				vo.setTType(rs.getInt("t_type"));
				vo.setTDisplay(rs.getString("t_display"));
				vo.setTTishi(rs.getString("t_tishi"));
				vo.setTKey(rs.getString("t_key"));
				vo.setTKeyValue(rs.getString("t_key_value"));
				vo.setTGeidj(rs.getString("t_geidj"));
				vo.setTGeidjNumber(rs.getString("t_geidj_number"));
				vo.setTPoint(rs.getString("t_point"));
				vo.setTZjms(rs.getString("t_zjms"));
				vo.setTBnzjms(rs.getString("t_bnzjms"));
				vo.setTZjdwp(rs.getString("t_zjdwp"));
				vo.setTZjdwpNumber(rs.getInt("t_zjdwp_number"));
				vo.setTDjsc(rs.getInt("t_djsc")); 
				vo.setTMidstGs(rs.getString("t_midst_gs"));
				vo.setTGoods(rs.getString("t_goods"));
				vo.setTGoodsNumber(rs.getString("t_goods_number"));
				vo.setTGoodszb(rs.getString("t_goodszb"));
				vo.setTGoodszbNumber(rs.getString("t_goodszb_number")); 
				vo.setTKilling(rs.getString("t_killing"));
				vo.setTKillingNo(rs.getInt("t_killing_no"));
				vo.setTMoney(rs.getString("t_money"));
				vo.setTExp(rs.getString("t_exp"));
				vo.setTSwType(rs.getInt("t_sw_type"));
				vo.setTSw(rs.getString("t_sw"));
				vo.setTEncouragement(rs.getString("t_encouragement"));
				vo.setTWncouragementNo(rs.getString("t_encouragement_no"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
				vo.setTEncouragementZb(rs.getString("t_encouragement_zb"));
				vo.setTEncouragementNoZb(rs.getString("t_encouragement_no_zb"));
				vo.setTCycle(rs.getInt("t_cycle"));
				task_list.put(vo.getTId()+"", vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return task_list;
	} 
	
	/**
	 * 查询菜单所拥有的任务
	 */
	public String getMainMenuByMap(int id) {
		try {
			con = new JyGameDB();
			String sql = "SELECT * FROM `operate_menu_info` WHERE id = " + id + " ";
			ResultSet rs = con.query(sql);
			OperateMenuVO menu = new OperateMenuVO();
			while (rs.next()) {
				menu.setMenuTasksId(rs.getString("menu_tasks_id"));
			}
			return menu.getMenuTasksId();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	/**
	 * 通过菜单然会的任务ID 取出任务相关条件 
	 */
	public TaskVO getTaskList(String rwpx,String tId) {
		try {
			con = new JyGameDB();
			String sql = "SELECT * FROM task WHERE t_zuxl='"+rwpx+"' AND t_id='" + tId + "'";
			ResultSet rs = con.query(sql);
			TaskVO vo = new TaskVO();
			while (rs.next()) { 
				vo.setTId(rs.getInt("t_id"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTZuxl(rs.getString("t_zuxl"));
				vo.setTName(rs.getString("t_name"));
				vo.setTLevelXiao(rs.getInt("t_level_xiao"));
				vo.setTLevelDa(rs.getInt("t_level_da"));
				vo.setTSchool(rs.getString("t_school"));
				vo.setTType(rs.getInt("t_type"));
				vo.setTDisplay(rs.getString("t_display"));
				vo.setTTishi(rs.getString("t_tishi"));
				vo.setTKey(rs.getString("t_key"));
				vo.setTKeyValue(rs.getString("t_key_value"));
				vo.setTGeidj(rs.getString("t_geidj"));
				vo.setTGeidjNumber(rs.getString("t_geidj_number"));
				vo.setTPoint(rs.getString("t_point"));
				vo.setTZjms(rs.getString("t_zjms"));
				vo.setTBnzjms(rs.getString("t_bnzjms"));
				vo.setTZjdwp(rs.getString("t_zjdwp"));
				vo.setTZjdwpNumber(rs.getInt("t_zjdwp_number"));
				vo.setTDjsc(rs.getInt("t_djsc")); 
				vo.setTMidstGs(rs.getString("t_midst_gs"));
				vo.setTGoods(rs.getString("t_goods"));
				vo.setTGoodsNumber(rs.getString("t_goods_number"));
				vo.setTGoodszb(rs.getString("t_goodszb"));
				vo.setTGoodszbNumber(rs.getString("t_goodszb_number")); 
				vo.setTKilling(rs.getString("t_killing"));
				vo.setTKillingNo(rs.getInt("t_killing_no"));
				vo.setTMoney(rs.getString("t_money"));
				vo.setTExp(rs.getString("t_exp"));
				vo.setTSwType(rs.getInt("t_sw_type"));
				vo.setTSw(rs.getString("t_sw"));
				vo.setTEncouragement(rs.getString("t_encouragement"));
				vo.setTWncouragementNo(rs.getString("t_encouragement_no"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
				vo.setTEncouragementZb(rs.getString("t_encouragement_zb"));
				vo.setTEncouragementNoZb(rs.getString("t_encouragement_no_zb"));
				vo.setTCycle(rs.getInt("t_cycle"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	} 
	
	/**
	 * 任务ID 取出任务相关条件 
	 */
	public TaskVO getTaskView(String tId) {
		try {
			con = new JyGameDB();
			String sql = "SELECT * FROM task WHERE t_id='" + tId + "'";
			ResultSet rs = con.query(sql);
			TaskVO vo = null;
			while (rs.next()) { 
				vo = new TaskVO();
				vo.setTId(rs.getInt("t_id"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTZuxl(rs.getString("t_zuxl"));
				vo.setTName(rs.getString("t_name"));
				vo.setTLevelXiao(rs.getInt("t_level_xiao"));
				vo.setTLevelDa(rs.getInt("t_level_da"));
				vo.setTSchool(rs.getString("t_school"));
				vo.setTType(rs.getInt("t_type"));
				vo.setTDisplay(rs.getString("t_display"));
				vo.setTTishi(rs.getString("t_tishi"));
				vo.setTKey(rs.getString("t_key"));
				vo.setTKeyValue(rs.getString("t_key_value"));
				vo.setTGeidj(rs.getString("t_geidj"));
				vo.setTGeidjNumber(rs.getString("t_geidj_number"));
				vo.setTPoint(rs.getString("t_point"));
				vo.setTZjms(rs.getString("t_zjms"));
				vo.setTBnzjms(rs.getString("t_bnzjms"));
				vo.setTZjdwp(rs.getString("t_zjdwp"));
				vo.setTZjdwpNumber(rs.getInt("t_zjdwp_number"));
				vo.setTDjsc(rs.getInt("t_djsc")); 
				vo.setTMidstGs(rs.getString("t_midst_gs"));
				vo.setTGoods(rs.getString("t_goods"));
				vo.setTGoodsNumber(rs.getString("t_goods_number"));
				vo.setTGoodszb(rs.getString("t_goodszb"));
				vo.setTGoodszbNumber(rs.getString("t_goodszb_number")); 
				vo.setTKilling(rs.getString("t_killing"));
				vo.setTKillingNo(rs.getInt("t_killing_no"));
				vo.setTMoney(rs.getString("t_money"));
				vo.setTExp(rs.getString("t_exp"));
				vo.setTSwType(rs.getInt("t_sw_type"));
				vo.setTSw(rs.getString("t_sw"));
				vo.setTEncouragement(rs.getString("t_encouragement"));
				vo.setTWncouragementNo(rs.getString("t_encouragement_no"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
				vo.setTEncouragementZb(rs.getString("t_encouragement_zb"));
				vo.setTEncouragementNoZb(rs.getString("t_encouragement_no_zb"));
				vo.setTCycle(rs.getInt("t_cycle"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	} 
	
	/**
	 * 通过任务ID 得到任务排序
	 */
	public String getTaskRwpx(String tId) {
		try {
			con = new JyGameDB();
			String sql = "SELECT * FROM task WHERE t_id='" + tId + "'";
			ResultSet rs = con.query(sql);
			TaskVO vo = new TaskVO();
			while (rs.next()) {  
				vo.setTZuxl(rs.getString("t_zuxl")); 
			}
			return vo.getTZuxl();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}
	/**
	 * 通过任务ID 得到任务类型
	 */
	public String getTaskType(String tId) {
		
		try {
			con = new JyGameDB();
			String sql = "SELECT * FROM task WHERE t_id='" + tId + "'";
			ResultSet rs = con.query(sql);
			String tZu = null;
			while (rs.next()) {  
				tZu = rs.getString("t_zu");
			}
			return tZu;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
		
	}
	/**
	 * 通过组和组序列活的任务ID
	 */
	public int getTaskZUXl(String tZu,String tXuxl) {
		
		try {
			con = new JyGameDB();
			String sql = "SELECT * FROM task WHERE t_zu='" + tZu + "' AND t_zuxl='"+tXuxl+"'";
			ResultSet rs = con.query(sql);
			int tId = 0;
			while (rs.next()) {  
				tId = rs.getInt("t_id");
			}
			return tId;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return 0;
		
	}
	/**
	 * 通过任务ID 得到任务排序
	 */
	public int getTaskNext(String tId) {
		try {
			con = new JyGameDB();
			String sql = "SELECT t_id FROM task WHERE t_next='" + tId + "'";
			ResultSet rs = con.query(sql);
			int Next=0;
			while (rs.next()) {  
				Next=rs.getInt("t_id"); 
			}
			return Next;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return 0;
	}
}
