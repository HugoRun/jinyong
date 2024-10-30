package com.ls.ben.dao.task;

import java.sql.ResultSet;
import java.util.HashMap;

import com.ben.vo.task.TaskVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.pub.db.jygamedb.Jygamedb;

/**
 * ����:
 * 
 * @author ��˧ 10:36:42 AM
 */
public class TaskDao {
	Jygamedb con;
	
	/**
	 * �õ���������
	 */
	public HashMap<String,TaskVO> getAllTask()  
	{
		HashMap<String,TaskVO> task_list = null;
		TaskVO vo = new TaskVO();
		int count = 0;
		
		try {
			con = new Jygamedb();

			String total_sql = "select count(t_id) from task";
			String all_data_sql = "select * from task";
			
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
	 * ��ѯ�˵���ӵ�е�����
	 */
	public String getMainMenuByMap(int id) {
		try {
			con = new Jygamedb();
			String sql = "select * from  operate_menu_info where id=" + id + " ";
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
	 * ͨ���˵�Ȼ�������ID ȡ������������� 
	 */
	public TaskVO getTaskList(String rwpx,String tId) {
		try {
			con = new Jygamedb();
			String sql = "select * from task where t_zuxl='"+rwpx+"' and t_id='" + tId + "'";
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
	 * ����ID ȡ������������� 
	 */
	public TaskVO getTaskView(String tId) {
		try {
			con = new Jygamedb();
			String sql = "select * from task where t_id='" + tId + "'";
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
	 * ͨ������ID �õ���������
	 */
	public String getTaskRwpx(String tId) {
		try {
			con = new Jygamedb();
			String sql = "select * from task where t_id='" + tId + "'";
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
	 * ͨ������ID �õ���������
	 */
	public String getTaskType(String tId) {
		
		try {
			con = new Jygamedb();
			String sql = "select * from task where t_id='" + tId + "'";
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
	 * ͨ����������л������ID
	 */
	public int getTaskZUXl(String tZu,String tXuxl) {
		
		try {
			con = new Jygamedb();
			String sql = "select * from task where t_zu='" + tZu + "' and t_zuxl='"+tXuxl+"'";
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
	 * ͨ������ID �õ���������
	 */
	public int getTaskNext(String tId) {
		try {
			con = new Jygamedb();
			String sql = "select t_id from task where t_next='" + tId + "'";
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
