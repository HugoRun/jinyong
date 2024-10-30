/**
 * 
 */
package com.pm.dao.tongsiege.tongsiegepklog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ls.ben.dao.DaoBase;
import com.ls.model.user.RoleEntity;
import com.ls.pub.db.DBConnection;
import com.pm.vo.tongsiege.TongSiegePkLogVO;

/**
 * 负责处理攻城战场上的pklog
 * @author zhangjj
 *
 */
public class TongSiegePkLogDao extends DaoBase {

	/**
	 * 给玩家加上杀人数,在攻城战场上
	 * @param p_pk
	 * @param tongId
	 * @param siegeId
	 * @param siegeFightNumber
	 * @param addUserGlory
	 */
	public void addPkNumber(int p_pk, int tongId, int siegeId,
			int siegeFightNumber, int addUserGlory)
	{
		String sql = "update tong_siege_pklog set pk_number = pk_number+1 ,pk_add_glory=pk_add_glory+"+addUserGlory+
						" where siege_id="+siegeId+" and siege_number="+siegeFightNumber+" and p_pk="+p_pk;
		int result = 0;
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
		}  finally
		{			
			dbConn.closeConn();
		}
		
		if ( result == 0) {
			DBConnection dbConn2 = new DBConnection(DBConnection.GAME_USER_DB);
			String sql1 = "insert into tong_siege_pklog values (null,"+siegeId+","+siegeFightNumber+","+tongId+","
								+p_pk+",1,"+addUserGlory+")";
			conn = dbConn2.getConn();
			try
			{
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql1);	
				stmt.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}  finally
			{			
				dbConn2.closeConn();
			}
		}
	}

	
	/**
	 * 给玩家加上杀人数,在攻城战场上
	 * @param p_pk
	 * @param tongId
	 * @param siegeId
	 * @param siegeFightNumber
	 * @param addUserGlory
	 */
	public void addPkNumberLog(int p_pk, int tongId, int siegeId,
			int siegeFightNumber, int addUserGlory)
	{
		DBConnection dbConn2 = new DBConnection(DBConnection.GAME_USER_DB);
		String sql1 = "insert into tong_siege_pklog values (null,"+siegeId+","+siegeFightNumber+","+tongId+","
							+p_pk+"0,"+addUserGlory+")";
		conn = dbConn2.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql1);	
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}  finally
		{			
			dbConn2.closeConn();
		}
	}
	
	
	/**
	 * 
	 * @param pk
	 * @return
	 */
	public TongSiegePkLogVO getPkLogVO(int pk)
	{
		String sql = "select * from tong_siege_pklog where p_pk="+pk+" order by pklog_id desc limit 1";
		TongSiegePkLogVO tongSiegePkLogVO = new TongSiegePkLogVO();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if ( rs.next() ) {
				tongSiegePkLogVO.setPklogId(rs.getInt("pklog_id"));
				tongSiegePkLogVO.setPPk(rs.getInt("p_pk"));
				tongSiegePkLogVO.setTongId(rs.getInt("tong_id"));
				tongSiegePkLogVO.setSiegeId(rs.getInt("siege_id"));
				tongSiegePkLogVO.setPkNumber(rs.getInt("pk_number"));
				tongSiegePkLogVO.setSiegeNumber(rs.getInt("siege_number"));
				tongSiegePkLogVO.setPkGlory(rs.getInt("pk_add_glory"));				
			}
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}  finally
		{
			dbConn.closeConn();
		}
		
		return tongSiegePkLogVO;
	}
	
	
	/**
	 * 获得pklog
	 * @param pk
	 * @return
	 */
	public TongSiegePkLogVO getPkLogVO(int pPk,String siegeId,String siegeFighterNumber)
	{
		String sql = "select * from tong_siege_pklog where siege_id = "+siegeId+" and siege_number="+ siegeFighterNumber+" and  p_pk="+pPk+" limit 1";
		TongSiegePkLogVO tongSiegePkLogVO = new TongSiegePkLogVO();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if ( rs.next() ) {
				tongSiegePkLogVO.setPklogId(rs.getInt("pklog_id"));
				tongSiegePkLogVO.setPPk(rs.getInt("p_pk"));
				tongSiegePkLogVO.setTongId(rs.getInt("tong_id"));
				tongSiegePkLogVO.setSiegeId(rs.getInt("siege_id"));
				tongSiegePkLogVO.setPkNumber(rs.getInt("pk_number"));
				tongSiegePkLogVO.setSiegeNumber(rs.getInt("siege_number"));
				tongSiegePkLogVO.setPkGlory(rs.getInt("pk_add_glory"));				
			}
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}  finally
		{			
			dbConn.closeConn();
		}
		
		return tongSiegePkLogVO;
	}

	/**
	 * 获得此人在此次攻城战的排名
	 * @param pk
	 * @param siegeId
	 * @param siegeNumber
	 * @param pk_number
	 * @return
	 */
	public int getGradeByPPk(int pk, int siegeId, int siegeNumber,int pk_number)
	{
		String sql = "select count(1) as numb from tong_siege_pklog tsp,u_part_info upi where tsp.p_pk = upi.p_pk and pk_number > "+pk_number+" and  siege_id="
					+ siegeId+" and siege_number = "+siegeNumber;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if ( rs.next() ) {
				count = rs.getInt("numb");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}  finally
		{			
			dbConn.closeConn();
		}
		
		
		return count;
	}
	
	
	/**
	 * 获得此人在此次攻城战的排名
	 * @param pk
	 * @param siegeId
	 * @param siegeNumber
	 * @param pk_number
	 * @return
	 */
	public int getJifenByPPk(int pk, int siegeId, int siegeNumber,int pk_number,String exp)
	{
		String sql = "select count(1) as numb from tong_siege_pklog tsp,u_part_info upi where tsp.p_pk = upi.p_pk and pk_number = "+pk_number+" and  siege_id="
					+ siegeId+" and siege_number = "+siegeNumber+" and p_experience > "+exp;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if ( rs.next() ) {
				count = rs.getInt("numb");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}  finally
		{			
			dbConn.closeConn();
		}
		
		
		return count;
	}

	/**
	 * 获得杀人最多的300人
	 * @param siegeId
	 * @param siegeNumber
	 * @return
	 */
	public int getAllKIllNumberBy300(int siegeId, int siegeNumber)
	{
		String sqlString = "select sum(pk_number) as numbe from tong_siege_pklog where siege_id="+siegeId+" and siege_number="+siegeNumber
					+" order by pk_number limit 300";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("获得杀人最多的300人="+sqlString);
		conn = dbConn.getConn();
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlString);
			if ( rs.next() ) {
				count = rs.getInt("numbe");
			}
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}  finally
		{			
			dbConn.closeConn();
		}
		
		return count;
	}

	/**
	 * 获得 杀人数更多的 帮派
	 * @param list 帮派ID
	 * @param siegeId
	 * @param siegeFightNumber
	 * @return
	 */
	public int getKillMoreTongId(List<int[]> list, int siegeId,
			int siegeFightNumber)
	{
		// // 将帮派ID组合成字符串
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("(");
		for(int i=0;i < list.size();i++) {
			sBuffer.append(list.get(i)[0]);
			if ( i != list.size() -1) {
				sBuffer.append(",");
			}
		}
		sBuffer.append(")");
		String sql = "";
		if(list.size() !=0){
			 sql = "select tong_id,sum(pk_number) as pk_num from tong_siege_pklog where tong_id in "+sBuffer.toString()+" and siege_id="
			+siegeId+" and siege_number="+siegeFightNumber+" group by tong_id order by pk_num desc limit 1";
		}else{
			 sql = "select tong_id,sum(pk_number) as pk_num from tong_siege_pklog where  siege_id="
			+siegeId+" and siege_number="+siegeFightNumber+" group by tong_id order by pk_num desc limit 1";
		}
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("获得 杀人数更多的 帮派="+sql);
		conn = dbConn.getConn();
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if ( rs.next() ) {
				count = rs.getInt("tong_id");
			}
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}  finally
		{			
			dbConn.closeConn();
		}
		return count;
	}
	
	
	/**
	 * 获得 杀人数更多的 帮派
	 * @param list 帮派ID
	 * @param siegeId
	 * @param siegeFightNumber
	 * @return
	 */
	public int getKillMoreTongIdByWin(List<Integer> list, int siegeId,
			int siegeFightNumber)
	{
		// // 将帮派ID组合成字符串
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("(");
		for(int i=0;i < list.size();i++) {
			sBuffer.append(list.get(i));
			if ( i != list.size() -1) {
				sBuffer.append(",");
			}
		}
		sBuffer.append(")");
		
		String sql = "select * from ( select tong_id,sum(pk_number) as pk_num from tong_siege_pklog where tong_id in "+sBuffer.toString()+" and siege_id="
						+siegeId+" and siege_number="+siegeFightNumber+") aaa order by pk_num desc";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("获得 杀人数更多的 帮派="+sql);
		conn = dbConn.getConn();
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if ( rs.next() ) {
				count = rs.getInt("tong_id");
			}
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}  finally
		{			
			dbConn.closeConn();
		}
		return count;
	}

	/**
	 * 得到最大的五个帮派ID
	 * @param siegeId
	 * @param siegeFightNumber
	 * @return
	 */
	public List<Integer> getKillNumMaxFiveTongId(int siegeId,
			int siegeFightNumber)
	{
		String sql = "select * from (select tong_id,sum(pk_number) as numb from tong_siege_pklog where siege_id="
				+siegeId+" and siege_number="+siegeFightNumber+	" group by tong_id ) ase order by numb desc limit 5";
		List<Integer> list = new ArrayList<Integer>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("得到最大的五个帮派ID="+sql);
		conn = dbConn.getConn();
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while ( rs.next() ) {
				count = rs.getInt("tong_id");
				list.add(count);
			}
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}  finally
		{			
			dbConn.closeConn();
		}
		
		return list;
	}

	/**
	 * 得到最大的五个帮派ID
	 * @param siegeId
	 * @param siegeFightNumber
	 * @return
	 */
	public List<int[]> getKillNumMaxFiveTongIdWithArray(int siegeId,
			int siegeFightNumber)
	{
		String sql = "select * from (select tong_id,sum(pk_number) as numb from tong_siege_pklog where siege_id="
			+siegeId+" and siege_number="+siegeFightNumber+	" and tong_id != 0 group by tong_id ) ase order by numb desc limit 5";
		List<int[]> list = new ArrayList<int[]>();
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        logger.debug("得到杀人数最大的五个帮派ID="+sql);
        int[] array1= null;
        try
        {
        	stmt = conn.createStatement();
        	rs = stmt.executeQuery(sql);
        	while ( rs.next() ) {
        		array1= new int[2];
        		array1[0] = rs.getInt("tong_id");
        		array1[1] = rs.getInt("numb");
        		
        		list.add(array1);
        	}
        	stmt.close();
        } catch (SQLException e)
        {
        	e.printStackTrace();
        }  finally
        {			
        	dbConn.closeConn();
        }
        
		return list;
	}

	/**
	 *  获得所有在攻城战场的
	 * @param list sceneId的集合
	 * @return
	 */
	public Map<Integer, Integer> getTongSiegeMap(List<Integer> list)
	{
		// // 将帮派ID组合成字符串
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("(");
		for(int i=0;i < list.size();i++) {
			sBuffer.append(list.get(i));
			if ( i != list.size() -1) {
				sBuffer.append(",");
			}
		}
		sBuffer.append(")");
		Map<Integer, Integer> map = new HashMap<Integer,Integer>();
		String sql = "select up.p_pk,join_type from u_part_info up,tong_siege_info te where up.p_pk = te.p_pk and up.p_map in "+sBuffer.toString()
						+"";
		
		 DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
	        conn = dbConn.getConn();
	        try
	        {
	        	stmt = conn.createStatement();
	        	rs = stmt.executeQuery(sql);
	        	while ( rs.next() ) {
	        		map.put(rs.getInt("p_pk"), rs.getInt("join_type"));
	        	}
	        	stmt.close();
	        } catch (SQLException e)
	        {
	        	e.printStackTrace();
	        }  finally
	        {			
	        	dbConn.closeConn();
	        }
		
		return map;
	}


	/**
	 * 清楚杀人数
	 * @param pk
	 */
	public void clearPkLog(int siegeId,int siegeFightNumber)
	{
		String sql = "update tong_siege_pklog  set pk_number = 0 where siege_id="
			+siegeId+" and siege_number="+siegeFightNumber+"";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        logger.debug("清楚杀人数="+sql);
        conn = dbConn.getConn();
       
        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);	
            stmt.close();
        } catch (SQLException e)
        {
        	e.printStackTrace();
        }  finally
        {			
        	dbConn.closeConn();
        }
		
	}


	/**
	 * 将第一阶段的形式信息插入到数据库中
	 * @param roleEntity
	 * @param siegeId
	 * @param siegeNumber
	 * @param displayInfoString 公共帮派信息
	 * @param personDisplay	个人攻城战信息
	 */
	public void addFirstEndDisplayInfo(RoleEntity roleEntity, int siegeId,
			int siegeNumber, String displayInfoString, String personDisplay)
	{
		String sql = "insert into tong_siegebattle_info values (null,"+roleEntity.getBasicInfo().getPPk()
							+","+siegeId+","+siegeNumber+",'"+displayInfoString+"','"+personDisplay+"')";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        logger.debug("清楚杀人数="+sql);
        conn = dbConn.getConn();
        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);	
            stmt.close();
        } catch (SQLException e) 
        {
        	e.printStackTrace();
        }  finally
        {
        	dbConn.closeConn();
        }
	}


	/**
	 * 获得第一阶段结束帮派显示信息
	 * @param pk
	 * @return
	 */
	public String getBangPaiInfo(int pPk,String siegeId,String siegeNumber)
	{
		String sql = "select infoone from tong_siegebattle_info where p_pk="+pPk+" and siege_id="+siegeId+" and siege_number="+siegeNumber;
		String infoone = "";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        logger.debug("清楚杀人数="+sql);
        conn = dbConn.getConn();
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        	if ( rs.next() ) {
        		infoone = rs.getString("infoone");
        	}
            stmt.close();
        } catch (SQLException e) 
        {
        	e.printStackTrace();
        }  finally
        {
        	dbConn.closeConn();
        }
        return infoone;
    }


	/**
	 * 获得个人显示信息
	 * @param pk
	 * @param siegeId
	 * @param siegeNumber
	 * @return
	 */
	public String getPersonDisplayInfo(int pPk, String siegeId,
			String siegeNumber)
	{
		String sql = "select infotwo from tong_siegebattle_info where p_pk="+pPk+" and siege_id="+siegeId+" and siege_number="+siegeNumber;
		String infotwo = "";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        logger.debug("清楚杀人数="+sql);
        conn = dbConn.getConn();
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        	if ( rs.next() ) {
        		infotwo = rs.getString("infotwo");
        	}
            stmt.close();
        } catch (SQLException e) 
        {
        	e.printStackTrace();
        }  finally
        {
        	dbConn.closeConn();
        }
        return infotwo;
	}
	
	/**
	 * 
	 */
	
	

}
