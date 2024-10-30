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
	 * ���������Դ���
	 * @param pk
	 * @param nowMouth
	 * @return
	 */
	public List getConuniteWinNum()
	{
		String sql = "select p_pk from u_part_info";
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
		String title = "ͨ��";
		String content = "����ǰ��ʱ�䷢��һЩ��Ϸ����bug��" +
				"ʹ��Ҳ�������������������ݣ�" +
				"�Ը������ɵĲ�������ź���" +
				"Ŀǰ�����Ѿ��޸��˲�������bug���Դ�ҵ������¼������ɾ�������ش�ͨ�棡";
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
