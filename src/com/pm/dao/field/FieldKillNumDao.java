package com.pm.dao.field;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;
import com.pm.vo.field.FieldKillNumVO;

/**
 * �˱���Ҫ�Ǵ��ս������������
 * @author Administrator
 *
 */
public class FieldKillNumDao extends DaoBase {
	/**
	 * ��ɱ����Ϣ���뵽ս�������б���, �˱���¼��Ϣ�Ƚϼ򵥣�������ʱ��ϳ���
	 * @param pPk
	 * @param bPpk
	 * @param fh_id ����
	 */
	public int insertIntoFieldMouthKill(int pPk,String todayStr,String field_type)
	{
		String sql = "update field_kill_num set kill_num = kill_num + 1 where p_pk = "
						+pPk+" and field_type = "+field_type+" and create_time like '%"+todayStr+"%'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("��ɱ����Ϣ���뵽ս�������б���="+sql);
		int updateLine = 0;
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();		
			updateLine = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return updateLine;
	}

	public void insertIntoFieldMouth(int pPk,String field_type)
	{
		String sql = "insert into field_kill_num value (null,"+pPk+",1,"+field_type+",now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("��ɱ����Ϣ���뵽ս�������б���="+sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();		
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
				dbConn.closeConn();
		}
		
	}

	/**
	 * �鿴�·�������
	 * @param page_no
	 * @return
	 */
	public QueryPage getMouthUntangleList(int page_no,String field_type)
	{
		QueryPage queryPage = null;
		
		List<FieldKillNumVO> list = new ArrayList<FieldKillNumVO>();
		FieldKillNumVO fieldKillNumVO = null;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String count_sql,page_sql;
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			count_sql = "select count(*) as allnum from field_kill_num where field_type = "+field_type;
			logger.debug(count_sql);
			rs = stmt.executeQuery(count_sql);
			if( rs.next() )
			{
				count = rs.getInt("allnum");
			}
			rs.close();

			queryPage = new  QueryPage(page_no,count);
			
			page_sql = "select fkn.p_pk,p_name,p_camp,p_grade,count(kill_num) as kill_num1 " +
							"from field_kill_num fkn,u_part_info upi where field_type ="+ field_type
							+" and fkn.p_pk = upi.p_pk group by fkn.p_pk order by kill_num1 desc,p_grade asc,create_time desc limit " + queryPage.getStartOfPage() 
							+ ","+queryPage.getPageSize();
			
			logger.debug("�鿴�·�������="+page_sql);
			
			rs = stmt.executeQuery(page_sql);
			while (rs.next())
			{
				fieldKillNumVO = new FieldKillNumVO();
				fieldKillNumVO.setP_pk(rs.getInt("p_pk"));
				fieldKillNumVO.setMouthCountKill(rs.getInt("kill_num1"));
				fieldKillNumVO.setPName(rs.getString("p_name"));
				fieldKillNumVO.setPCamp(rs.getInt("p_camp"));
				fieldKillNumVO.setPGrade(rs.getInt("p_grade"));
				
				list.add(fieldKillNumVO);
				
			}
			logger.debug("�鿴�·�������Ķ���="+list.size());
			rs.close();
			stmt.close();
			
			queryPage.setResult(list);

		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
 		
		return queryPage;
		
	}
	
	
	
	

}