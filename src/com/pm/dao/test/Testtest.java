package com.pm.dao.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pm.service.mail.MailInfoService;

public class Testtest extends DaoBase
{

	/**
	 * 获得连续答对次数
	 * @param pk
	 * @param nowMouth
	 * @return
	 */
	public List getConuniteWinNum()
	{
		String sql = "SELECT p_pk from u_part_info";
		List list = new ArrayList();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("sql"+sql);
		int aa = 0;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				aa = rs.getInt("p_pk");
				list.add(aa);
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
		return list;
	}
	
	public static void mian(String[] args) {
		String title = "通告";
		String content = "由于前段时间发现一些游戏任务bug，" +
				"使大家产生大量错误的任务数据，" +
				"对给大家造成的不便深表遗憾。" +
				"目前我们已经修改了部分任务bug，对大家的任务记录做彻底删除处理，特此通告！";
		Testtest teset = new Testtest();
		List alist = teset.getConuniteWinNum();
		MailInfoService mailService = new MailInfoService();
		if(alist != null && alist.size() != 0){
			for(int i=0;i<alist.size();i++) {
				////System.out.println();
				int pPk = (Integer)alist.get(i);
				mailService.sendMailBySystem(pPk,title,content);
			}
		}
	}
}
