package com.ben.dao.task;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ben.vo.task.UTaskVO;
import com.ls.ben.dao.DaoBase;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.pub.db.DBConnection;
import com.pub.db.mysql.SqlData;

/**
 * 功能:
 * 
 * @author 刘帅 3:11:12 PM
 */ 
public class UTaskDAO extends DaoBase
{			 
	SqlData con;

	/**
	 * 添加对话任务
	 */
	public int getUTaskAdd(String pPk, String pName, String tZu, String tPx,
			String tId, String tTitle, String tType, String tXrwnpcId,
			String tNext, String createTime, String tTime,String tGiveUp,String upTaskId)
	{
		int t_pk = -1;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{  
			String sql = "insert into u_task(t_pk,p_pk,p_name,t_zu,t_px,t_id,t_title,t_type,t_xrwnpc_id,t_next,create_time,t_time,t_give_up,up_task_id) values(null,'"
					+ pPk
					+ "','"
					+ pName
					+ "','"
					+ tZu
					+ "','"
					+ tPx
					+ "','"
					+ tId
					+ "','"
					+ tTitle
					+ "','"
					+ tType
					+ "','"
					+ tXrwnpcId
					+ "','" + tNext + "','" + createTime + "','" + tTime + "','"+tGiveUp+"','"+upTaskId+"')";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			rs = stmt.getGeneratedKeys();
			if( rs.next() )
			{
				t_pk = rs.getInt(1);
			} 
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return t_pk;
	}

	/**
	 * 修改对话任务
	 */
	public void getUTaskUpdate(String pPk, String pName, String tZu,
			String tPx, String tId, String tTitle, String tType,
			String tXrwnpcId, String tNext, String createTime, String tTime,String tGiveUp,String upTaskId)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_task set t_px='" + tPx + "',t_id='" + tId
					+ "',t_title='" + tTitle + "',t_type='" + tType
					+ "',t_xrwnpc_id='" + tXrwnpcId + "',t_next='" + tNext
					+ "',create_time='" + createTime + "',t_time='" + tTime + "',t_give_up='"+tGiveUp+"',up_task_id='"+upTaskId+"' where p_pk='" + pPk + "' and t_zu='" + tZu + "'";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * 清除所接的所有任务
	 */
	public void clear(int p_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "delete from u_task where p_pk='" + p_pk + "'";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}
	
	/**
	 * 删除对话任务
	 */
	public void getUTaskDelete(String tPk)
	{
		try
		{
			con = new SqlData();
			String sql = "delete from u_task where t_pk='" + tPk + "'";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * 添加杀怪任务
	 */
	public int getUTaskAddXG(String pPk, String pName, String tZu, String tPx,
			String tId, String tTitle, String tType, String tXrwnpcId,
			String tNext, String tKilling, String tKillingNo,
			String createTime, String tTime,String tGiveUp,String upTaskId)
	{
		int t_pk = -1;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{ 
			String sql = "insert into u_task(t_pk,p_pk,p_name,t_zu,t_px,t_id,t_title,t_type,t_xrwnpc_id,t_next,t_killing,t_killing_no,create_time,t_time,t_give_up,up_task_id) values(null,'"
					+ pPk
					+ "','"
					+ pName
					+ "','"
					+ tZu
					+ "','"
					+ tPx
					+ "','"
					+ tId
					+ "','"
					+ tTitle
					+ "','"
					+ tType
					+ "','"
					+ tXrwnpcId
					+ "','"
					+ tNext
					+ "','"
					+ tKilling
					+ "','"
					+ tKillingNo
					+ "','" + createTime + "','" + tTime + "','"+tGiveUp+"','"+upTaskId+"')";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			rs = stmt.getGeneratedKeys();
			if( rs.next() )
			{
				t_pk = rs.getInt(1);
			} 
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return t_pk;
	}

	/**
	 * 修改杀怪任务
	 */
	public void getUTaskUpdateXG(String pPk, String pName, String tZu,
			String tPx, String tId, String tTitle, String tType,
			String tXrwnpcId, String tNext, String tKilling, String tKillingNo,
			String tKillingOk, String createTime, String tTime,String tGiveUp,String upTaskId)
	{
		try
		{
			con = new SqlData();
			String sql = "update  u_task set t_px='" + tPx + "',t_id='" + tId
					+ "',t_title='" + tTitle + "',t_type='" + tType
					+ "',t_xrwnpc_id='" + tXrwnpcId + "',t_next='" + tNext
					+ "',t_killing='" + tKilling + "',t_killing_no='"
					+ tKillingNo + "',t_killing_ok='" + tKillingOk
					+ "',create_time='" + createTime + "',t_time='" + tTime
					+ "',t_give_up='"+tGiveUp+"',up_task_id='"+upTaskId+"' where p_pk='" + pPk + "' and t_zu='" + tZu + "'";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * 删除杀怪任务
	 */
	public void getUTaskDeleteXG(String tPk)
	{
		try
		{
			con = new SqlData();
			String sql = "delete from u_task where t_pk='" + tPk + "'";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * 添加携带类任务
	 */
	public int getUTaskAddXD(String pPk, String pName, String tZu, String tPx,
			String tId, String tTitle, String tType, String tXrwnpcId,
			String tNext, String tGoods, String tGoodsNo, String tGoodszb,
			String tGoodszbNumber, String createTime, String tTime, int petID,
			int petNumber,String tGiveUp,String upTaskId)
	{
		int t_pk = -1;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{ 
			String sql = "insert into u_task"
					+ "(t_pk,p_pk,p_name,t_zu,t_px,t_id,t_title,t_type,t_xrwnpc_id,t_next,"
					+ "t_goods,t_goods_no,t_goodszb,t_goodszb_number,t_pet,t_pet_number,create_time,t_time,t_give_up,up_task_id) "
					+ "values(null,'"
					+ pPk
					+ "','"
					+ pName
					+ "','"
					+ tZu
					+ "','"
					+ tPx
					+ "','"
					+ tId
					+ "','"
					+ tTitle
					+ "','"
					+ tType
					+ "','"
					+ tXrwnpcId
					+ "','"
					+ tNext
					+ "','"
					+ tGoods
					+ "','"
					+ tGoodsNo
					+ "','"
					+ tGoodszb
					+ "','"
					+ tGoodszbNumber
					+ "','"
					+ petID
					+ "','"
					+ petNumber + "','" + createTime + "','" + tTime + "','"+tGiveUp+"','"+upTaskId+"')";

			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			rs = stmt.getGeneratedKeys();
			if( rs.next() )
			{
			    t_pk = rs.getInt(1);
			}
			stmt.close();
			}catch (Exception e)
			{
			e.printStackTrace();
			}
			finally
			{
				dbConn.closeConn();
			}
			return t_pk;
	}

	/**
	 * 修改携带类任务
	 */
	public void getUTaskUpdateXD(String pPk, String pName, String tZu,
			String tPx, String tId, String tTitle, String tType,
			String tXrwnpcId, String tNext, String tGoods, String tGoodsNo,
			String tGoodszb, String tGoodszbNumber, String createTime,
			String tTime, int petID, int petNumber,String tGiveUp,String upTaskId)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_task set  t_px='" + tPx + "',t_id='" + tId
					+ "',t_title='" + tTitle + "',t_type='" + tType
					+ "',t_xrwnpc_id='" + tXrwnpcId + "',t_next='" + tNext
					+ "',t_goods='" + tGoods + "',t_goods_no='" + tGoodsNo
					+ "',t_goodszb='" + tGoodszb + "',t_goodszb_number='"
					+ tGoodszbNumber + "',t_pet='" + petID + "',t_pet_number='"
					+ petNumber + "',create_time='" + createTime + "',t_time='"
					+ tTime + "',t_give_up='"+tGiveUp+"',up_task_id='"+upTaskId+"' where p_pk='" + pPk + "' and t_zu='" + tZu
					+ "'";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * 删除杀怪任务
	 */
	public void getUTaskDeleteXD(String tPk)
	{
		try
		{
			con = new SqlData();
			String sql = "delete from u_task where t_pk='" + tPk + "'";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * 添加复合类任务
	 */
	public int getUTaskAddFH(String pPk, String pName, String tZu, String tPx,
			String tId, String tTitle, String tType, String tXrwnpcId,
			String tNext, String tPoint, String tZjdwp, String tZjdwpNumber,
			String tZjdzb, String tZjdzbNumber, String tDjscwp, String tDjsczb,
			String tMidstGs, String tMidstZb, String tGoods, String tGoodsNo,
			String tGoodszb, String tGoodszbNumber, String createTime,
			String tTime,String tGiveUp,String upTaskId)
	{
		int t_pk = -1;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{ 
			String sql = "insert into u_task "
					+ "(t_pk,p_pk,p_name,t_zu,t_px,t_id,t_title,t_type,t_xrwnpc_id,t_next,"
					+ "t_point,t_zjdwp,t_zjdwp_number,t_zjdzb,t_zjdzb_number,t_djscwp,t_djsczb,t_midst_gs,t_midst_zb,t_goods,t_goods_no,t_goodszb,t_goodszb_number,create_time,t_time,t_give_up,up_task_id) "
					+ "values(null,'"
					+ pPk
					+ "','"
					+ pName
					+ "','"
					+ tZu
					+ "','"
					+ tPx
					+ "','"
					+ tId
					+ "','"
					+ tTitle
					+ "','"
					+ tType
					+ "','"
					+ tXrwnpcId
					+ "','"
					+ tNext
					+ "','"
					+ tPoint
					+ "','"
					+ tZjdwp
					+ "','"
					+ tZjdwpNumber
					+ "','"
					+ tZjdzb
					+ "','"
					+ tZjdzbNumber
					+ "','"
					+ tDjscwp
					+ "','"
					+ tDjsczb
					+ "','"
					+ tMidstGs
					+ "','"
					+ tMidstZb
					+ "','"
					+ tGoods
					+ "','"
					+ tGoodsNo
					+ "','"
					+ tGoodszb
					+ "','"
					+ tGoodszbNumber
					+ "','"
					+ createTime + "','" + tTime + "','"+tGiveUp+"','"+upTaskId+"')";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			rs = stmt.getGeneratedKeys();
			if( rs.next() )
			{
				t_pk = rs.getInt(1);
			} 
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return t_pk;
	}

	/**
	 * 修改复合类任务
	 */
	public void getUTaskUpdateFH(String pPk, String pName, String tZu,
			String tPx, String tId, String tTitle, String tType,
			String tXrwnpcId, String tNext, String tPoint, String tZjdwp,
			String tZjdwpNumber, String tZjdzb, String tZjdzbNumber,
			String tDjscwp, String tDjsczb, String MidstGs, String tMidstZb,
			String tGoods, String tGoodsNo, String tGoodszb,
			String tGoodszbNumber, String createTime, String tTime,String tGiveUp,String upTaskId)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_task set t_px='" + tPx + "',t_id='" + tId
					+ "',t_title='" + tTitle + "',t_type='" + tType
					+ "',t_xrwnpc_id='" + tXrwnpcId + "',t_next='" + tNext
					+ "',t_point='" + tPoint + "',t_zjdwp='" + tZjdwp
					+ "',t_zjdwp_number='" + tZjdwpNumber + "',t_zjdzb='"
					+ tZjdzb + "',t_zjdzb_number='" + tZjdzbNumber
					+ "',t_djscwp='" + tDjscwp + "',t_djsczb='" + tDjsczb
					+ "',t_midst_gs='" + MidstGs + "',t_midst_zb='" + tMidstZb
					+ "',t_goods='" + tGoods + "',t_goods_no='" + tGoodsNo
					+ "',t_goodszb='" + tGoodszb + "',t_goodszb_number='"
					+ tGoodszbNumber + "',create_time='" + createTime
					+ "',t_time='" + tTime + "',t_give_up='"+tGiveUp+"',up_task_id='"+upTaskId+"' where p_pk='" + pPk
					+ "' and t_zu='" + tZu + "'";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	 
	/**
	 * 通过角色ID 和任务ID 查询信息 查询最早的
	 * TODO : 暂时不看
	 */
	public UTaskVO getUTaskPklimit(String pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_task where p_pk='" + pPk
					+ "' order by create_time desc limit 1";
			ResultSet rs = con.query(sql);
			UTaskVO vo = null;
			while (rs.next())
			{
				vo = new UTaskVO();
				vo.setTPk(rs.getInt("t_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTPx(rs.getString("t_px"));
				vo.setTId(rs.getInt("t_id"));
				vo.setTTitle(rs.getString("t_title"));
				vo.setTType(rs.getInt("t_type"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
				vo.setTGoods(rs.getString("t_goods"));
				vo.setTGoodsNo(rs.getString("t_goods_no"));
				vo.setTKilling(rs.getString("t_killing"));
				vo.setTKillingNo(rs.getInt("t_killing_no"));
				vo.setTPet(rs.getInt("t_pet"));
				vo.setTPetNumber(rs.getInt("t_pet_number"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setTTime(rs.getInt("t_time"));
			}
			return vo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;
	}
 
	/**
	 * 通过角色ID 和NPCID 查询信息 任务类型
	 */
	public List<UTaskVO> getUTaskNpcId(String pPk, String npcId, String tType)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_task where p_pk='" + pPk
					+ "' and t_type='" + tType + "' and t_killing='" + npcId
					+ "'";
			ResultSet rs = con.query(sql);
			List<UTaskVO> list = new ArrayList<UTaskVO>();
			while (rs.next())
			{
				UTaskVO vo = new UTaskVO();
				vo.setTPk(rs.getInt("t_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTPx(rs.getString("t_px"));
				vo.setTId(rs.getInt("t_id"));
				vo.setTTitle(rs.getString("t_title"));
				vo.setTType(rs.getInt("t_type"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
				vo.setTGoods(rs.getString("t_goods"));
				vo.setTGoodsNo(rs.getString("t_goods_no"));
				vo.setTKilling(rs.getString("t_killing"));
				vo.setTKillingNo(rs.getInt("t_killing_no"));
				vo.setTKillingOk(rs.getInt("t_killing_ok"));
				vo.setTPet(rs.getInt("t_pet"));
				vo.setTPetNumber(rs.getInt("t_pet_number"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setTTime(rs.getInt("t_time"));
				list.add(vo);
			}
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;
	}

	/**
	 * 通过角色ID 和NPCID 查询信息 任务类型
	 */
	public List<UTaskVO> getGoods(String pPk, String tType)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_task where p_pk='" + pPk
					+ "' and t_type='" + tType + "' ";
			ResultSet rs = con.query(sql);
			List<UTaskVO> list = new ArrayList<UTaskVO>();
			while (rs.next())
			{
				UTaskVO vo = new UTaskVO();
				vo.setTPk(rs.getInt("t_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTPx(rs.getString("t_px"));
				vo.setTId(rs.getInt("t_id"));
				vo.setTTitle(rs.getString("t_title"));
				vo.setTType(rs.getInt("t_type"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
				vo.setTPoint(rs.getString("t_point"));
				vo.setTPointNo(rs.getString("t_point_no"));
				vo.setTGoods(rs.getString("t_goods"));
				vo.setTGoodsNo(rs.getString("t_goods_no"));
				vo.setTGoodsOk(rs.getString("t_goods_ok"));
				vo.setTGoodszb(rs.getString("t_goodszb"));
				vo.setTGoodszbNumber(rs.getString("t_goodszb_number"));
				vo.setTGoodszbOk(rs.getString("t_goodszb_ok"));
				vo.setTKilling(rs.getString("t_killing"));
				vo.setTKillingNo(rs.getInt("t_killing_no"));
				vo.setTKillingOk(rs.getInt("t_killing_ok"));
				vo.setTPet(rs.getInt("t_pet"));
				vo.setTPetNumber(rs.getInt("t_pet_number"));
				vo.setTPetOk(rs.getInt("t_pet_ok"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setTTime(rs.getInt("t_time"));
				list.add(vo);
			}
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;
	}

	/**
	 * 通过pPk将任务存放在缓存中
	 */
	public HashMap[] getPlayerTask(String pPk)
	{
		HashMap task_list_by_id = new HashMap(); 
		HashMap task_list_by_zu = new HashMap(); 
		HashMap[] result = {task_list_by_id,task_list_by_zu}; 
		try
		{
			con = new SqlData();
			String sql = "select * from u_task where p_pk='" + pPk + "' ";
			ResultSet rs = con.query(sql);
			
			CurTaskInfo vo = null;
			while (rs.next())
			{
				vo = new CurTaskInfo(this);
				vo.setTPk(rs.getInt("t_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTPx(rs.getString("t_px"));
				vo.setTId(rs.getInt("t_id"));
				vo.setTTitle(rs.getString("t_title"));
				vo.setTType(rs.getInt("t_type"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
				vo.setTPoint(rs.getString("t_point"));
				vo.setTPointNo(rs.getString("t_point_no"));
				vo.setTZjdwp(rs.getString("t_zjdwp"));
				vo.setTZjdwpNumber(rs.getInt("t_zjdwp_number"));
				vo.setTZjdwpOk(rs.getInt("t_zjdwp_ok"));
				vo.setTZjdzb(rs.getString("t_zjdzb"));
				vo.setTZjdzbNumber(rs.getInt("t_zjdzb_number"));
				vo.setTZjdzbOk(rs.getInt("t_zjdzb_ok"));
				vo.setTDjscwp(rs.getInt("t_djscwp"));
				vo.setTDjsczb(rs.getInt("t_djsczb"));
				vo.setTMidstGs(rs.getString("t_midst_gs"));
				vo.setTMidstZb(rs.getString("t_midst_zb"));
				vo.setTGoods(rs.getString("t_goods"));
				vo.setTGoodsNo(rs.getString("t_goods_no"));
				vo.setTGoodsOk(rs.getString("t_goods_ok"));
				vo.setTGoodszb(rs.getString("t_goodszb"));
				vo.setTGoodszbNumber(rs.getString("t_goodszb_number"));
				vo.setTGoodszbOk(rs.getString("t_goodszb_ok"));
				vo.setTKilling(rs.getString("t_killing"));
				vo.setTKillingNo(rs.getInt("t_killing_no"));
				vo.setTKillingOk(rs.getInt("t_killing_ok"));
				vo.setTPet(rs.getInt("t_pet"));
				vo.setTPetNumber(rs.getInt("t_pet_number"));
				vo.setTPetOk(rs.getInt("t_pet_ok"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setTTime(rs.getInt("t_time"));
				vo.setTGiveUp(rs.getInt("t_give_up"));
				vo.setUpTaskId(rs.getInt("up_task_id"));
				task_list_by_id.put(vo.getTPk()+"", vo);
				task_list_by_zu.put(vo.getTZu()+"", vo);
				
				/*int temp1 = rs.getInt("t_pk");
				System.out.println(temp1);
				String temp = rs.getString("t_zu");
				System.out.println(temp);
				
				CurTaskInfo aa = (CurTaskInfo)task_list_by_zu.get(temp);
				String aaa = aa.getPName();*/
				
			}
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;
	}
	
	/**
	 * 通过角色ID 和菜单ID 查询角色任务表是否有值
	 */
	public List<UTaskVO> getMenuId(String pPk, int taskType)
	{
		try
		{
			con = new SqlData();
			String sql = "select t_pk,t_id,t_type,t_xrwnpc_id,t_next,t_point,t_zjdwp,t_goodszb,t_goodszb_number,"
					+ "t_zjdwp_number,t_zjdwp_ok,t_zjdzb,t_zjdzb_number,"
					+ "t_zjdzb_ok,t_djscwp,t_djsczb,t_midst_gs,"
					+ "t_midst_zb from u_task where p_pk='"
					+ pPk
					+ "' and t_type='" + taskType + "'";
			ResultSet rs = con.query(sql);
			// //System.out.println(sql);
			List<UTaskVO> list = new ArrayList<UTaskVO>();
			while (rs.next())
			{
				UTaskVO vo = new UTaskVO();
				vo.setTPk(rs.getInt("t_pk"));
				vo.setTId(rs.getInt("t_id"));
				vo.setTType(rs.getInt("t_type"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
				vo.setTPoint(rs.getString("t_point"));
				vo.setTZjdwp(rs.getString("t_zjdwp"));
				vo.setTGoodszb(rs.getString("t_goodszb"));
				vo.setTGoodszbNumber(rs.getString("t_goodszb_number"));
				vo.setTZjdwpNumber(rs.getInt("t_zjdwp_number"));
				vo.setTZjdwpOk(rs.getInt("t_zjdwp_ok"));
				vo.setTZjdzb(rs.getString("t_zjdzb"));
				vo.setTZjdzbNumber(rs.getInt("t_zjdzb_number"));
				vo.setTZjdzbOk(rs.getInt("t_zjdzb_ok"));
				vo.setTDjscwp(rs.getInt("t_djscwp"));
				vo.setTDjsczb(rs.getInt("t_djsczb"));
				vo.setTMidstGs(rs.getString("t_midst_gs"));
				vo.setTMidstZb(rs.getString("t_midst_zb"));
				list.add(vo);
			}
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;
	}

	/**
	 * 通过角色ID 和菜单ID 查询角色任务表是否有值
	 */
	public UTaskVO getPoint(String pPk, int tPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_task where p_pk='" + pPk
					+ "' and t_id='" + tPk + "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = null;
			while (rs.next())
			{
				vo = new UTaskVO();
				vo.setTPk(rs.getInt("t_pk"));
				vo.setTId(rs.getInt("t_id"));
				vo.setTType(rs.getInt("t_type"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
				vo.setTPoint(rs.getString("t_point"));
				vo.setTZjdwp(rs.getString("t_zjdwp"));
				vo.setTZjdwpNumber(rs.getInt("t_zjdwp_number"));
				vo.setTZjdwpOk(rs.getInt("t_zjdwp_ok"));
				vo.setTZjdzb(rs.getString("t_zjdzb"));
				vo.setTZjdzbNumber(rs.getInt("t_zjdzb_number"));
				vo.setTZjdzbOk(rs.getInt("t_zjdzb_ok"));
				vo.setTDjscwp(rs.getInt("t_djscwp"));
				vo.setTDjsczb(rs.getInt("t_djsczb"));
				vo.setTMidstGs(rs.getString("t_midst_gs"));
				vo.setTMidstZb(rs.getString("t_midst_zb"));
				vo.setTGoods(rs.getString("t_goods"));
				vo.setTGoodsNo(rs.getString("t_goods_no"));

			}
			return vo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;
	}

	/**
	 * 修改中间点
	 */
	public void getUpMenuId(int tPk, String t_point)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_task set t_point='" + t_point
					+ "'where t_pk='" + tPk + "'";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * 返回杀怪数量
	 */
	public int tKillingOk(String tPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select t_killing_ok from u_task where t_pk='" + tPk
					+ "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = new UTaskVO();
			int tKillingOk = 0;
			while (rs.next())
			{
				tKillingOk = rs.getInt("t_killing_ok");
			}
			return tKillingOk;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return 0;
	}

	/**
	 * 返回杀怪数量
	 */
	public int tKilling(String tPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select t_killing_no from u_task where t_pk='" + tPk
					+ "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = new UTaskVO();
			int tKillingNo = 0;
			while (rs.next())
			{
				tKillingNo = rs.getInt("t_killing_no");
			}
			return tKillingNo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return 0;
	}

	/**
	 * 修改杀怪数量
	 */
	public void tKillingOKUpdate(int t_killing_ok, String tPk)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_task set t_killing_ok='" + t_killing_ok
					+ "' where t_pk='" + tPk + "'";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * 修改杀怪数量
	 */
	public void updateGiveUp(String tPk,int type)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_task set t_give_up='"+type+"' where t_pk='" + tPk + "'";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}
	 
	/**
	 * 增加玩家完成的任务记录
	 */
	public void taskComplete(int pPk, String taskZu)
	{
		try
		{
			con = new SqlData();
			String sql = "insert into u_task_complete values(null,'" + pPk
					+ "','" + taskZu + "')";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * 是否完成多个任务组中的任意一个任务组
	 * 
	 * @param p_pk
	 * @param task_zu_str
	 *            多个任务组字符串 形式如:'zhuxian','yindao'
	 * @return
	 */
	public boolean isCompletedOneOfMany(int pPk, String task_zu_str)
	{
		try
		{
			con = new SqlData();
			String sql = "select c_pk from u_task_complete where p_pk='" + pPk
					+ "' and task_zu in (" + task_zu_str + ")";
			ResultSet rs = con.query(sql);
			if (rs.next())
			{
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return false;
	}

	/**
	 * 通过角色ID得到玩家当前任务id
	 */
	public String getMenuId(int p_pk)
	{
		String task_ids = "0";
		try
		{
			con = new SqlData();
			String sql = "select t_id from u_task where p_pk='" + p_pk + "'";
			ResultSet rs = con.query(sql);

			while (rs.next())
			{
				task_ids = task_ids + "," + rs.getInt("t_id");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return task_ids;
	}
	
	/**
	 * 通过角色ID 和菜单ID 查询角色任务表是否有值
	 */
	public boolean getUserHasTask(int pPk, String tZu)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_task where p_pk='" + pPk
					+ "' and t_zu='" + tZu + "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = null;
			if (rs.next())
			{
				return true;
			}
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return false;
	}
}
