package com.ls.ben.dao.info.group;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.group.GroupNotifyVO;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * ����:u_groupnotify_info
 * 
 * @author ��˧ 3:56:48 PM
 */
public class GroupNotifyDao extends DaoBase {
	
	/**
	 * ����������֪ͨ��Ϣ
	 */
	public void clearAllNotify() {
		String sql = "truncate u_groupnotify_info";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
	}
	
	/**
	 * ���֪ͨ
	 * 
	 * @param groupNotify
	 */
	public void add(GroupNotifyVO groupNotify) {
		if (groupNotify == null) {
			logger.debug("����Ϊ��");
			return;
		}
		String sql = "insert into u_groupnotify_info (notifyed_pk,create_notify_pk,notify_content,notify_type,create_time) values (?,?,?,?,now())";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, groupNotify.getNotifyedPk());
			ps.setInt(i++, groupNotify.getCreateNotifyPk());
			ps.setString(i++, groupNotify.getNotifyContent());
			ps.setInt(i++, groupNotify.getNotifyType());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
	}

	/**
	 * ɾ��ָ��֪ͨ
	 * 
	 * @param n_pk
	 */
	public void delete(int n_pk) {
		String sql = "delete from u_groupnotify_info where n_pk=" + n_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
	}
	
	/**
	 * ɾ��ָ��ĳ��֪ͨ
	 * 
	 * @param p_pk
	 */
	public void clareNotify(int p_pk) {
		String sql = "delete from u_groupnotify_info where notifyed_pk=" + p_pk + " or create_notify_pk=" + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
	}
	
	

	/**
	 * �õ���ҵ�����һ�������֪ͨ
	 * 
	 * @param p_pk
	 * @return
	 */
	public GroupNotifyVO getNotifyInfo(int p_pk) {
		GroupNotifyVO notifyInfo = null;
		String sql = "select *  from u_groupnotify_info where notifyed_pk="
				+ p_pk + " order by n_pk limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				notifyInfo = new GroupNotifyVO();
				notifyInfo.setNPk(rs.getInt("n_pk"));
				notifyInfo.setNotifyedPk(p_pk);
				notifyInfo.setCreateNotifyPk(rs.getInt("create_notify_pk"));
				notifyInfo.setNotifyContent(StringUtil.isoToGBK(rs
						.getString("notify_content")));
				notifyInfo.setNotifyType(rs.getInt("notify_type"));
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return notifyInfo;
	}

	/**
	 * �ж��Ƿ��������Ϣ
	 */
	public int isHaveNotify(int p_pk) {
		int n_pk = -1;
		String sql = "select n_pk  from u_groupnotify_info where notifyed_pk="
				+ p_pk + " and notify_flag =0 limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {

				n_pk = rs.getInt("n_pk");
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return n_pk;
	}

	/**
	 *  ����֪ͨ״̬
	 * @param n_pk
	 */
	public void updateNotifyFlag(int n_pk) {
		String sql = "update  u_groupnotify_info set notify_flag=1 where n_pk=" + n_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
	}
	
	/**
	 * �����ҵ��������֪ͨ
	 */
	public void clear(int p_pk) {
		String sql = "delete from u_groupnotify_info where notifyed_pk=" + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
	}

	/**
	 * �ж����a�Ƿ������b�ύ�����֪ͨ
	 */
	public boolean isNotifyPlayerB(int a_pk, int b_pk) {
		boolean result = false;
		String sql = "select count(*) as num from  u_groupnotify_info where notifyed_pk ="
				+ b_pk + " and create_notify_pk=" + a_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				if (rs.getInt("num") > 0) {
					result = true;
				}
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
		return result;
	}

}
