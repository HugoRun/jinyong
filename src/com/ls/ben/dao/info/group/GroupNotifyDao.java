package com.ls.ben.dao.info.group;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.group.GroupNotifyVO;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * 功能:u_groupnotify_info
 * 
 * @author 刘帅 3:56:48 PM
 */
public class GroupNotifyDao extends DaoBase {
	
	/**
	 * 清除所有组队通知信息
	 */
	public void clearAllNotify() {
		String sql = "TRUNCATE `u_groupnotify_info` ";
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
	 * 添加通知
	 * 
	 * @param groupNotify
	 */
	public void add(GroupNotifyVO groupNotify) {
		if (groupNotify == null) {
			logger.debug("参数为空");
			return;
		}
		String sql = "INSERT INTO u_groupnotify_info (notifyed_pk,create_notify_pk,notify_content,notify_type,create_time) VALUES (?,?,?,?,now())";
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
	 * 删除指定通知
	 * 
	 * @param n_pk
	 */
	public void delete(int n_pk) {
		String sql = "DELETE FROM u_groupnotify_info WHERE n_pk=" + n_pk;
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
	 * 删除指定某人通知
	 * 
	 * @param p_pk
	 */
	public void clareNotify(int p_pk) {
		String sql = "DELETE FROM u_groupnotify_info WHERE notifyed_pk=" + p_pk + " or create_notify_pk=" + p_pk;
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
	 * 得到玩家的最早一条的组队通知
	 * 
	 * @param p_pk
	 * @return
	 */
	public GroupNotifyVO getNotifyInfo(int p_pk) {
		GroupNotifyVO notifyInfo = null;
		String sql = "SELECT *  FROM u_groupnotify_info WHERE notifyed_pk="
				+ p_pk + " ORDER BY n_pk LIMIT 1";
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
	 * 判断是否有组队消息
	 */
	public int isHaveNotify(int p_pk) {
		int n_pk = -1;
		String sql = "SELECT n_pk  FROM u_groupnotify_info WHERE notifyed_pk="
				+ p_pk + " AND notify_flag =0 LIMIT 1";
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
	 *  更新通知状态
	 * @param n_pk
	 */
	public void updateNotifyFlag(int n_pk) {
		String sql = "UPDATE  u_groupnotify_info SET notify_flag=1 WHERE n_pk=" + n_pk;
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
	 * 清除玩家的所有组队通知
	 */
	public void clear(int p_pk) {
		String sql = "DELETE FROM u_groupnotify_info WHERE notifyed_pk=" + p_pk;
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
	 * 判断玩家a是否向玩家b提交过组队通知
	 */
	public boolean isNotifyPlayerB(int a_pk, int b_pk) {
		boolean result = false;
		String sql = "SELECT COUNT(*) AS num FROM  u_groupnotify_info WHERE notifyed_pk ="
				+ b_pk + " AND create_notify_pk=" + a_pk;
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
