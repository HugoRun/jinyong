package com.ben.dao.task;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ben.vo.task.TaskVO;
import com.pub.db.jygamedb.Jygamedb;
import com.pub.db.mysql.SqlData;
/**
 * ����:
 * 
 * @author ��˧ 10:36:42 AM
 */
public class TaskDAO {
	Jygamedb con;
	SqlData conn;

	Logger logger = Logger.getLogger("log.dao");
	
	/**
	 * ��ѯ�˵���ӵ�е�����
	 */
	public String getMainMenuByMap(int id) {
		String menu_tasks_id = "";
		try {
			con = new Jygamedb();
			String sql = "select menu_tasks_id from  operate_menu_info where id=" + id + " ";
			ResultSet rs = con.query(sql);
			while (rs.next()) {
				menu_tasks_id = rs.getString("menu_tasks_id");
				if( menu_tasks_id==null )
				{
					 menu_tasks_id = "";
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return menu_tasks_id;
	}

	
	/**
	 * ����������������еõ�������Ϣ
	 */
	public TaskVO getTaskByZuAndXulie(String t_zu,int t_zuxl) {
		try {
			con = new Jygamedb();
			String sql = "select * from task where t_zu='"+t_zu+"' and t_zuxl='" + t_zuxl + "'";
			ResultSet rs = con.query(sql);
			TaskVO vo = null;
			if(rs.next()) {
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
	 * ͨ���˵�Ȼ�������ID ȡ������������� 
	 */
	public List<TaskVO> getList(String tId) {
		try {
			con = new Jygamedb();
			String sql = "select t_id,t_name,t_level_xiao from task where t_id='" + tId + "'";
			ResultSet rs = con.query(sql);
			List<TaskVO> list = new ArrayList<TaskVO>();
			while (rs.next()) { 
				TaskVO vo = new TaskVO();
				vo.setTId(rs.getInt("t_id")); 
				vo.setTName(rs.getString("t_name"));
				vo.setTLevelXiao(rs.getInt("t_level_xiao"));
				list.add(vo);
			}
			return list;
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

				vo.setTZjdwp(rs.getString("t_zjdwp"));
				vo.setTZjdwpNumber(rs.getInt("t_zjdwp_number"));
				vo.setTDjsc(rs.getInt("t_djsc")); 
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
	 * ͨ���˵�Ȼ�������ID ȡ������������� 
	 */
	public String t_zjms(String tId) {
		try {
			con = new Jygamedb();
			String sql = "select t_zjms from task where t_id='" + tId + "'";
			ResultSet rs = con.query(sql);
			String t_zjms = null;
			if (rs.next()) {  
				t_zjms = rs.getString("t_zjms"); 
			}
			return t_zjms;
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
	 * ��ָ������id��Χ������õ�һ������
	 * @param task_id_lower        ����id����
	 * @param task_id_upper        ����id����
	 * @return
	 */
	public TaskVO getTaskBySpacifyArea( String task_id_lower,String task_id_upper ) 
	{
		try {
			con = new Jygamedb();
			String sql = "select * from task  where t_id >="+task_id_lower+" and t_id <= "+task_id_upper+" and t_zuxl=1 group by t_zu order by rand() limit 1";
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
				vo.setTGeidj(rs.getString("t_geidj"));
				vo.setTGeidjNumber(rs.getString("t_geidj_number"));
				vo.setTPoint(rs.getString("t_point"));
				vo.setTZjms(rs.getString("t_zjms"));
				vo.setTBnzjms(rs.getString("t_bnzjms"));
				vo.setTZjdwp(rs.getString("t_zjdwp"));
				vo.setTZjdwpNumber(rs.getInt("t_zjdwp_number"));
				vo.setTDjsc(rs.getInt("t_djsc")); 
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
	 * ��������ʼ�ȼ���gradeҪС������
	 * @param grade
	 * @return
	 */
	public List<TaskVO> getTaskListByGrade(int grade,int sex,String allTaskId)
	{
		List<TaskVO> list = new ArrayList<TaskVO>();
		try {
			con = new Jygamedb();
			//String sql = "select distinct(t_zu),t_name,t_level_xiao,t_id,t_zuxl,t_school from task where t_level_xiao <= "+grade
			//				+" and (t_sex = 0 or t_sex ="+sex+") "
			//				+" and t_zu not like '%meirirenwu%' and t_zu not like '%yabiao%' and t_zu not like '%songwaimai%' and (t_school like '%0%' or t_school like '%"+pSchool
			//				+"%') group by t_zu order by t_zuxl asc,t_level_xiao desc";
			String sql = "select distinct(t_zu),t_name,t_level_xiao,t_id,t_zuxl,t_school from task where t_level_xiao <= " +grade+
					" and '"+grade+"' <= t_level_da " +
					"and t_id in "+allTaskId+" group by t_zu order by t_zuxl asc,t_level_xiao desc ";
			//System.out.println("Ѱ�������sql="+sql);
			ResultSet rs = con.query(sql); 
			logger.info("Ѱ�������sql="+sql);
			TaskVO vo = null;
			while (rs.next()) {
				vo = new TaskVO();
				vo.setTId(rs.getInt("t_id"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTZuxl(rs.getString("t_zuxl"));
				vo.setTName(rs.getString("t_name"));
				vo.setTSchool(rs.getString("t_school"));
				vo.setTLevelXiao(rs.getInt("t_level_xiao"));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return list;
	}
	
	public List<String> allTaskInUser(int p_pk){
		List<String> list = null;
		try {
			conn = new SqlData();
			String sql = "select * from u_task where p_pk ="+p_pk+"";
			ResultSet rs = conn.query(sql);
			list = new ArrayList<String>();
			while (rs.next()) { 
				list.add(rs.getString("t_zu"));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}

	/**
	 * ��õ�ǰ���в����ɵ��������������б�
	 * @return
	 */
	public String getAllTaskId()
	{
		StringBuffer allTaskId = new StringBuffer("(");
		String sql = "select menu_tasks_id from operate_menu_info where menu_tasks_id != ''";
		String taskId = "";
		try {
			con = new Jygamedb();
			ResultSet rs = con.query(sql);
			while (rs.next()) { 
				taskId = rs.getString("menu_tasks_id");
				allTaskId.append(taskId.substring(1));
			}
			allTaskId.append("0)");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return allTaskId.toString();
	}
}
