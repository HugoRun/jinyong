/**
 * 
 */
package com.ben.dao.deletepart; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.pub.db.mysql.SqlData;

/**
 * @author 侯浩军
 *  
 * 2:47:38 PM
 */
public class DeletePartDAO {
	SqlData dbc;  
	Logger logger =  Logger.getLogger(DeletePartDAO.class);
	public int DeletePart(int pPk){
		
		try{  
			dbc = new SqlData(); 
			dbc.begin();
			dbc.update("delete from u_part_info where p_pk=" + pPk);
			logger.info("玩家视野是在这里删除的 ************  ");
			dbc.update("delete from u_part_equip where p_pk=" + pPk);
			dbc.update("delete from u_warehouse_info where p_pk=" + pPk);
			
			dbc.update("delete from p_pet_info where p_pk=" + pPk);
			dbc.update("delete from u_pet_sell where p_pk=" + pPk);
			dbc.update("delete from u_sell_info where p_pk=" + pPk);
			
			dbc.update("delete from n_dropgoods_info where p_pk=" + pPk);
			dbc.update("delete from n_dropExpMoney_info where p_pk=" + pPk);   
			
			dbc.update("delete from u_shortcut_info where p_pk=" + pPk);
			dbc.update("delete from u_propgroup_info where p_pk=" + pPk);
			dbc.update("delete from u_coordinate_info where p_pk=" + pPk);
			dbc.update("delete from u_group_info where p_pk=" + pPk);
			dbc.update("delete from u_skill_info where p_pk=" + pPk);
			 
			dbc.update("delete from u_time_control where p_pk=" + pPk);
			dbc.update("delete from u_task where p_pk=" + pPk);
			dbc.update("delete from u_task_complete where p_pk=" + pPk);
			 
			dbc.update("delete from u_auction where p_pk=" + pPk);
			dbc.update("delete from u_auction_info where p_pk=" + pPk);
			
			dbc.update("delete from u_friend where p_pk=" + pPk);
			dbc.update("delete from u_blacklist where p_pk=" + pPk);
			dbc.update("delete from s_system_info where p_pk=" + pPk); 
			
			dbc.update("delete from u_auction_pet where p_pk=" + pPk);
			dbc.update("delete from u_auctionpet_info where p_pk=" + pPk);
			dbc.update("delete from s_setting_info where p_pk=" + pPk);
			dbc.update("delete from u_warehouse_equip where p_pk=" + pPk); 
			
			dbc.update("delete from u_quiz_info where p_pk=" + pPk);
			dbc.update("delete from u_special_prop where p_pk=" + pPk);
			dbc.commit();// 提交JDBC事务  
			dbc.close();
			return 1; 
		}catch (Exception exc) {
			dbc.rollback(); 
			exc.printStackTrace();
			dbc.close();
			return -1;
		}
	}

	/**
	 * 确定某角色的删除时间，并将删除标志置为1，即有效位.
	 * @param pk
	 * @param u_pk
	 * @param isRookie
	 * return 返回1表示删除成功，0表示失败
	 */
	public int delete(String pk, String u_pk,boolean isRookie)
	{
		
		int result = 0;
		String dateStr="|"+new Date().getSeconds()+"";
		String sql="";
		if(!isRookie)
		{
			sql = "update u_part_info set delete_flag = 1 ,delete_time = now(),p_name=CONCAT(p_name,'"+dateStr+"') where p_pk="+pk+" and u_pk="+u_pk;
		}
		else
		{
			sql ="delete from u_part_info where p_pk="+pk+" and u_pk="+u_pk;
		}
		try
		{
			dbc = new SqlData();
			result = dbc.update(sql);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		} finally {
			dbc.close();
		} 
		return result;
	}

	/**
	 * 恢复某角色的删除时间，并将删除标志置为0，即无效位.
	 * @param pk
	 * @param pk2
	 */
	public void sureResumeTime(String pk, String u_pk)
	{
		String sql = "update u_part_info set delete_flag = 0 where p_pk="+pk+" and u_pk="+u_pk;
		try
		{
			dbc = new SqlData();
			dbc.update(sql);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		} finally {
			dbc.close();
		} 
		
	}
	
	/**
	 * 恢复某角色的删除时间，并将删除标志置为0，即无效位.
	 * @param pk
	 * @param pk2
	 */
	public void deleteByTime() {
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select p_pk from u_part_info where delete_flag = 1 and now() > (delete_time + INTERVAL 1 DAY)";
		try
		{
			dbc = new SqlData();
			ResultSet rs = dbc.query(sql);
			while(rs.next()) {
				list.add(rs.getInt("p_pk"));
			}
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		} finally {
			dbc.close();
		} 
		
		if(list.size() != 0) {
			for(int i=0; i < list.size();i++ ) {
				DeletePart(list.get(i));
			}
		}
	}
}
