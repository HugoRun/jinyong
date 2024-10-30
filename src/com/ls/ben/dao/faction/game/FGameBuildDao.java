/**
 * 
 */
package com.ls.ben.dao.faction.game;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.model.organize.faction.game.FGameBuild;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * 帮派建筑
 */
public class FGameBuildDao extends BasicDaoSupport<FGameBuild>
{

	public FGameBuildDao()
	{
		super("f_game_build", DBConnection.GAME_DB);
	}

	public FGameBuild getById( int bId )
	{
		return super.getOneBySql("where id="+bId);
	}
	
	/**
	 * 得到还没有建造的帮派建筑分页列表
	 * @param excludeIdStr                    排除的id字符串
	 * @param page_no
	 * @return
	 */
	public QueryPage getUnBuildPageList(String excludeIdStr,int page_no)
	{
		String condition_sql = "where id not in("+excludeIdStr+")"+" and grade=1";
		return super.loadPageList(condition_sql,  page_no);
	}
	
	@Override
	protected FGameBuild loadData(ResultSet rs) throws SQLException
	{
		if( rs==null )
		{
			return null;
		}
		FGameBuild game_build = new FGameBuild();
		
		game_build.setId(rs.getInt("id"));
		game_build.setGrade(rs.getInt("grade"));
		game_build.setName(rs.getString("name"));
		game_build.setDes(rs.getString("des"));
		game_build.setPic(rs.getString("pic"));
		
		game_build.setContribute(rs.getInt("contribute"));
		game_build.setPrestige(rs.getInt("prestige"));
		game_build.setMId(rs.getInt("mId"));
		game_build.setMNum(rs.getInt("mNum"));
		game_build.setBuffIdStr(rs.getString("buffIdStr"));
		game_build.setNextGradeId(rs.getInt("nextGradeId"));
		
		return game_build;
	}

}
