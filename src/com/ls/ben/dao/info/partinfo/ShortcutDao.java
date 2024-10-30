package com.ls.ben.dao.info.partinfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.partinfo.ShortcutVO;
import com.ls.pub.db.DBConnection;

/**
 * ����:��ҿ�ݼ���
 * @author ��˧ 6:00:48 PM
 */
public class ShortcutDao extends DaoBase
{
	private boolean add;

	/**
	 * �õ�һ����ҵĿ�ݼ�
	 * 
	 * @param p_pk
	 * @return
	 */
	public List<ShortcutVO> getByPpk1(int p_pk)
	{
		List<ShortcutVO> shortcuts = new ArrayList<ShortcutVO>();
		ShortcutVO shortcut = null;
		String sql = "select * from u_shortcut_info where p_pk=" + p_pk
				+ " order by sc_pk";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				shortcut = new ShortcutVO();
				shortcut.setScPk(rs.getInt("sc_pk"));
				shortcut.setPPk(p_pk);
				shortcut.setScName(rs.getString("sc_name"));
				shortcut.setScDisplay(rs.getString("sc_display"));
				shortcut.setScType(rs.getInt("sc_type"));
				shortcut.setOperateId(rs.getInt("operate_id"));
				shortcuts.add(shortcut);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e)
		{

			e.printStackTrace();
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return shortcuts;
	}

	/**
	 * ����һ����ݼ�
	 */
	public int updateByPpk(int sc_pk, int sc_type, String sc_display,
			int operate_id)
	{
		int result = -1;
		String sql = "update u_shortcut_info set sc_type=" + sc_type
				+ ",sc_display='" + sc_display + "',operate_id=" + operate_id
				+ " where sc_pk=" + sc_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return result;
	}

	/**
	 * ����sc_pk�õ�һ����ݼ�����ϸ��Ϣ
	 */
	public ShortcutVO getByScPk1(int sc_pk)
	{
		ShortcutVO shortcut = null;
		String sql = "select * from u_shortcut_info where sc_pk=" + sc_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				shortcut = new ShortcutVO();
				shortcut.setScPk(sc_pk);
				shortcut.setPPk(rs.getInt("p_pk"));
				shortcut.setScName(rs.getString("sc_name"));
				shortcut.setScDisplay(rs.getString("sc_display"));
				shortcut.setScType(rs.getInt("sc_type"));
				shortcut.setOperateId(rs.getInt("operate_id"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{

			e.printStackTrace();

		} finally
		{
			dbConn.closeConn();
		}
		return shortcut;
	}

	/**
	 * �����п�ݼ��ָ�����ʼֵ
	 * 
	 * @param p_pk
	 */
	public int clearShortcut(int p_pk)
	{
		int result = -1;
		String sql = "update u_shortcut_info set sc_type=0,operate_id=0,sc_display=sc_name where p_pk="
				+ p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return result;
	}
	/**
	 * ��:ɱ��״̬ʹ�ð��������Ժ��������û�а����� ��ɿ�ݼ���İ��ӿ�ݼ�����Ϊ��
	 * 
	 * @param p_pk
	 */
	public int clearShortcutoperate_id(int p_pk,int djid)
	{
		int result = -1;
		String sql = "update u_shortcut_info set sc_type=0,sc_display=sc_name where p_pk="+ p_pk+" and operate_id="+djid;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return result;
	}

	public LinkedHashMap<Integer, ShortcutVO> getAllByPpk(int p_pk)
	{
		LinkedHashMap<Integer, ShortcutVO> shortcuts = new LinkedHashMap<Integer, ShortcutVO>();
		ShortcutVO shortcut = null;
		String sql = "select * from u_shortcut_info where p_pk=" + p_pk
				+ " order by sc_pk";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				shortcut = new ShortcutVO();
				shortcut.setScPk(rs.getInt("sc_pk"));
				shortcut.setPPk(p_pk);
				shortcut.setScName(rs.getString("sc_name"));
				shortcut.setScDisplay(rs.getString("sc_display"));
				shortcut.setScType(rs.getInt("sc_type"));
				shortcut.setOperateId(rs.getInt("operate_id"));
				shortcuts.put(rs.getInt("sc_pk"), shortcut);
				logger.debug("ȡ��ʱ��ݼ���Ϊ="+shortcut.getScName());
			}
			rs.close();
			stmt.close();

		} catch (SQLException e)
		{

			e.printStackTrace();
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		
		return shortcuts;
	}
}
