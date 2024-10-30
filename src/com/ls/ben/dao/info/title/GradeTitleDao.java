package com.ls.ben.dao.info.title;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.title.GradeTitleVO;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * 功能:等级称号表操作
 * 
 * @author 刘帅 11:26:09 AM
 */
public class GradeTitleDao extends DaoBase
{
	/**
	 * @param title_id
	 * @return
	 */
	public GradeTitleVO getByTilteId(String title_id)
	{
		GradeTitleVO titleInfo = null;
		String sql = "SELECT * FROM  grade_title_info WHERE title_id='"
			+ title_id + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				titleInfo = new GradeTitleVO();
				titleInfo.setTitleId(title_id);
				titleInfo.setTitleName(rs.getString("title_name"));
				titleInfo.setSchoolId(rs.getString("school_id"));
				titleInfo.setSchoolName(rs.getString("school_name"));
				titleInfo.setTitleLevelDown(rs.getInt("title_level_down"));
				titleInfo.setTitleLevelUp(rs.getInt("title_level_up"));
			}
			rs.close();
			stmt.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return titleInfo;
	}
	
	
	/**
	 * 根据多称谓id，得到称谓名称字符串
	 * @param title_ids
	 * @return
	 */
	public String getTitleNamesByTitleIDs(String title_ids)
	{
		StringBuffer title_names = new StringBuffer();
		String sql = "SELECT title_name FROM  grade_title_info WHERE title_id IN ("
			+ title_ids + ")";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				if( title_names.length()== 0 )
				{
					title_names.append(StringUtil.isoToGBK(rs.getString("title_name")));
				}
				else
				{
					title_names.append(","+StringUtil.isoToGBK(rs.getString("title_name")));	
				}
			}
			rs.close();
			stmt.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return title_names.toString();
	}

}
