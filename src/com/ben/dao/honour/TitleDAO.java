package com.ben.dao.honour;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ben.vo.honour.TitleVO;
import com.ls.ben.dao.BasicDaoSupport;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * ϵͳ�ƺ�
 */
public class TitleDAO extends BasicDaoSupport<TitleVO>
{

	public TitleDAO()
	{
		super("title_info", DBConnection.GAME_DB);
	}

	/**
	 * ���ݶ��νid���õ���ν�����ַ���
	 * @param title_ids
	 * @return
	 */
	public String getTitleNamesByTitleIDs(String title_ids)
	{
		StringBuffer title_names = new StringBuffer();
		String sql = "select name from  title_info where id in ("+ title_ids + ")";
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
					title_names.append(StringUtil.isoToGBK(rs.getString("name")));
				}
				else
				{
					title_names.append(","+StringUtil.isoToGBK(rs.getString("name")));	
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
	
	/**
	 * ͨ��id�õ��ƺ���Ϣ
	 * @return
	 */
	public TitleVO getById( String id )
	{
		return this.getOneBySql("where id="+id);
	}
	
	/**
	 * ͨ�����ֵõ��ƺ���Ϣ
	 * @param name
	 * @return
	 */
	public TitleVO getByName(String name)
	{
		return this.getOneBySql("where name="+name+" limit 1");
	}
	
	/**
	 * ͨ�����ֺ����͵õ��ƺ���Ϣ
	 * @param name
	 * @param type
	 * @return
	 */
	public TitleVO getByNameAndType(String name,int type)
	{
		return this.getOneBySql("where name="+name+" and type="+type+" limit 1");
	}
	
	
	@Override
	protected TitleVO loadData(ResultSet rs) throws SQLException
	{
		TitleVO title = new TitleVO();
		title.setId(rs.getInt("id"));
		title.setName(rs.getString("name"));
		title.setType(rs.getInt("type"));
		title.setTypeName(rs.getString("type_name"));
		title.setDes(rs.getString("des"));
		title.setAttriStr(rs.getString("attri_str"));
		title.setUseTime(rs.getInt("use_time"));
		return title;
	}
}
