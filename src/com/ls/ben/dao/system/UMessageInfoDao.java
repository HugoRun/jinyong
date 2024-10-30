package com.ls.ben.dao.system;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.pub.db.DBConnection;
import com.web.jieyi.util.Constant;

/**
 * @author ls ����:��Ҫ����ҳ���ϵͳ��Ϣ��(u_popup_msg) Mar 6, 2009
 */
public class UMessageInfoDao extends DaoBase
{
	/**
	 * ���ָ����Ϣ
	 */
	public void deleteById(int p_pk, int id)
	{
		String sql = "delete from u_popup_msg where id=" + id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			delById(p_pk, id);
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
	}

	/**
	 * ��������Ϣ
	 */
	public void clear(int p_pk)
	{
		String sql = "delete from u_popup_msg where p_pk=" + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
			clearByPlayer(p_pk);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * ��������Ϣ
	 */
	public void clear(int p_pk, int msg_type)
	{
		clearByPlayer(p_pk,msg_type);
		String sql = "delete from u_popup_msg where p_pk=" + p_pk+" and msg_type="+msg_type;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
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
	}

	/**
	 * �õ�һ����Ϣ
	 */
	public UMessageInfoVO get(int p_pk)
	{
		// UMessageInfoVO msg = null;
		// String sql = "select * from u_popup_msg where p_pk="+p_pk +" order by
		// msg_priority,id limit 1";
		// logger.debug(sql);
		// DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		// conn = dbConn.getConn();
		// try
		// {
		// stmt = conn.createStatement();
		// rs = stmt.executeQuery(sql);
		//			
		// if( rs.next() )
		// {
		// msg = new UMessageInfoVO();
		// msg.setId(rs.getInt("id"));
		// msg.setPPk(p_pk);
		// msg.setMsgType(rs.getInt("msg_type"));
		// msg.setMsgOperate1(rs.getString("msg_operate1"));
		// msg.setMsgOperate2(rs.getString("msg_operate2"));
		// msg.setMsgPriority(rs.getInt("msg_priority"));
		// }
		// rs.close();
		// stmt.close();
		// } catch (Exception e)
		// {
		// e.printStackTrace();
		// } finally
		// {
		// dbConn.closeConn();
		// }
		// return msg;
		return getFromMemory(p_pk);
	}

	/**
	 * ����һ����Ϣ
	 */
	public void insert(UMessageInfoVO msg)
	{
		if (msg == null)
		{
			logger
					.debug("######################msgΪ��#########################");
		}
		String sql = "insert into u_popup_msg values (null,'" + msg.getPPk()
				+ "','" + msg.getMsgType() + "','" + msg.getMsgOperate1()
				+ "','" + msg.getMsgOperate2() + "','" + msg.getMsgPriority()
				+ "',now())";
		String sql1 = "select LAST_INSERT_ID()";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			ResultSet rs = stmt.executeQuery(sql1);
			while (rs.next())
			{
				msg.setId(rs.getInt(1));
			}
			setTOMemory(msg);
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
		}
	}

	// ���뵽�ڴ�
	private void setTOMemory(UMessageInfoVO msg)
	{
		if (msg != null)
		{
			msg.setCreateTime(new Date());
				List<UMessageInfoVO> list = (Constant.UMESSAGE
						.get(msg.getPPk()) == null ? new ArrayList<UMessageInfoVO>()
						: Constant.UMESSAGE.get(msg.getPPk()));
				list.add(msg);
				Constant.UMESSAGE.put(msg.getPPk(), list);
		}
	}

	// ���ڴ����(����һ��)
	private UMessageInfoVO getFromMemory(int p_pk)
	{
		UMessageInfoVO ui = null;
			if (Constant.UMESSAGE.containsKey(p_pk))
			{
				List<UMessageInfoVO> list = Constant.UMESSAGE.get(p_pk);
				if (list != null && list.size() > 0)
				{
					for(UMessageInfoVO ui1 : list){
						if(ui==null){
							ui = ui1;
						}else{
							if(ui1.getMsgPriority()<ui.getMsgPriority()){
								ui = ui1;
							}
						}
					}
				}
		}
		return ui;
	}

//	// ���ڴ����(��������)
//	private List<UMessageInfoVO> getFromMemoryAll(int p_pk)
//	{
//		List<UMessageInfoVO> list = new ArrayList<UMessageInfoVO>();
//				list = Constant.UMESSAGE.get(p_pk);
//				if (list == null)
//				{
//					list = new ArrayList<UMessageInfoVO>();
//				}
//		return list;
//	}

	// �����ҵĵ�����Ϣ
	public void clearByPlayer(int p_pk)
	{
			Constant.UMESSAGE.remove(p_pk);
	}
	
	
	// �����ҵĵ�����Ϣ
	private void clearByPlayer(int p_pk, int msg_type)
	{
		List<UMessageInfoVO> list = Constant.UMESSAGE.get(p_pk);
		if( list == null || list.size() == 0) {
			return ;
		}
		List<UMessageInfoVO> copyList = copyList(list);
		for (UMessageInfoVO uiv : copyList)
		{
			if (uiv.getMsgType() == msg_type)
			{
				list.remove(uiv);				
			}
		}
	}

	// ����id���������Ϣ
	private void delById(int p_pk, int id)
	{
			if (Constant.UMESSAGE.containsKey(p_pk))
			{
				List<UMessageInfoVO> list = Constant.UMESSAGE.get(p_pk);
				List<UMessageInfoVO> copyList = copyList(list);
				for (UMessageInfoVO uiv : copyList)
				{
					if (uiv.getId() == id)
					{
						list.remove(uiv);
					}
				}
			}
	}

	private List<UMessageInfoVO> copyList(List<UMessageInfoVO> list)
	{
		if (list == null)
		{
			return new ArrayList<UMessageInfoVO>();
		}
		return new ArrayList<UMessageInfoVO>(list);
	}

//	// ���ҳ����еĵ���ʽ��Ϣ�������ڴ�
//	public List<UMessageInfoVO> selectAll()
//	{
//		String sql = "select * from u_popup_msg  order by msg_priority,id ";
//		List<UMessageInfoVO> list = new ArrayList<UMessageInfoVO>();
//		logger.debug(sql);
//		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
//		conn = dbConn.getConn();
//		try
//		{
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(sql);
//
//			if (rs.next())
//			{
//
//				UMessageInfoVO msg = new UMessageInfoVO();
//				msg.setId(rs.getInt("id"));
//				msg.setPPk(rs.getInt("p_pk"));
//				msg.setMsgType(rs.getInt("msg_type"));
//				msg.setMsgOperate1(rs.getString("msg_operate1"));
//				msg.setMsgOperate2(rs.getString("msg_operate2"));
//				msg.setMsgPriority(rs.getInt("msg_priority"));
//				list.add(msg);
//			}
//			rs.close();
//			stmt.close();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		finally
//		{
//			dbConn.closeConn();
//			return list;
//		}
//	}
	
	// ���ҳ����еĵ���ʽ��Ϣ�������ڴ�
	private List<UMessageInfoVO> selectAllOne(Object p_pk)
	{
		List<UMessageInfoVO> list = new ArrayList<UMessageInfoVO>();
		if(p_pk==null){
			return list;
		}
		String sql = "select * from u_popup_msg where p_pk = "+p_pk+" order by msg_priority,id ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
			{

				UMessageInfoVO msg = new UMessageInfoVO();
				msg.setId(rs.getInt("id"));
				msg.setPPk(rs.getInt("p_pk"));
				msg.setMsgType(rs.getInt("msg_type"));
				msg.setMsgOperate1(rs.getString("msg_operate1"));
				msg.setMsgOperate2(rs.getString("msg_operate2"));
				msg.setMsgPriority(rs.getInt("msg_priority"));
				list.add(msg);
			}
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
	
	/**
	 * ��ʼ������ʽ��Ϣ
	 * @param p_pk
	 */
	public void initPopMsg(int p_pk){
		List<UMessageInfoVO> list = selectAllOne(p_pk);
		Constant.UMESSAGE.put(p_pk,list );
	}


}
