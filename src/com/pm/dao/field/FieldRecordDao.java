package com.pm.dao.field;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * ��¼����������ֵ, ���������Ҳ鿴һ�κ󼴱������
 * @author Administrator
 *
 */
public class FieldRecordDao extends DaoBase {
	
	/**
	 * ���ս������������
	 * @param pPk
	 * @param menuOperate3
	 * @param previous
	 */
	public void addFieldGloryRecord(int pPk, String field_type,int sequence)
	{
		String sql = "insert into s_field_record values (null,"+pPk+","+sequence+","+field_type+",0,now())";
		logger.debug("���ս������������="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			
			dbConn.closeConn();
		}
		
	}

	/**
	 * ���±�������
	 * @param pk
	 * @param menu_operate3
	 * @param fh_sequence
	 * @param intraday_value
	 */
	public void updateIntoFieldGloryRecord(int pk, String field_type,
			int fh_sequence, int intraday_value)
	{
		String sql = "update s_field_record set sr_glory = sr_glory + "+intraday_value+" where p_pk = "+pk
						+" and sr_type = "+field_type+" and sr_sequence = "+fh_sequence;
		logger.debug("���±�������="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			
			dbConn.closeConn();
		}
		
	}
	
	
	
	
}
