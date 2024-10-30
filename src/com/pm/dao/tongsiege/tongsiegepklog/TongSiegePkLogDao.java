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
 * ��������ս���ϵ�pklog
 * @author zhangjj
 *
 */
public class TongSiegePkLogDao extends DaoBase {

	/**
	 * ����Ҽ���ɱ����,�ڹ���ս����
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
	 * ����Ҽ���ɱ����,�ڹ���ս����
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
	 * ���pklog
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
	 * ��ô����ڴ˴ι���ս������
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
	 * ��ô����ڴ˴ι���ս������
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
	 * ���ɱ������300��
	 * @param siegeId
	 * @param siegeNumber
	 * @return
	 */
	public int getAllKIllNumberBy300(int siegeId, int siegeNumber)
	{
		String sqlString = "select sum(pk_number) as numbe from tong_siege_pklog where siege_id="+siegeId+" and siege_number="+siegeNumber
					+" order by pk_number limit 300";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("���ɱ������300��="+sqlString);
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
	 * ��� ɱ��������� ����
	 * @param list ����ID
	 * @param siegeId
	 * @param siegeFightNumber
	 * @return
	 */
	public int getKillMoreTongId(List<int[]> list, int siegeId,
			int siegeFightNumber)
	{
		// // ������ID��ϳ��ַ���
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
		logger.debug("��� ɱ��������� ����="+sql);
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
	 * ��� ɱ��������� ����
	 * @param list ����ID
	 * @param siegeId
	 * @param siegeFightNumber
	 * @return
	 */
	public int getKillMoreTongIdByWin(List<Integer> list, int siegeId,
			int siegeFightNumber)
	{
		// // ������ID��ϳ��ַ���
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
		logger.debug("��� ɱ��������� ����="+sql);
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
	 * �õ������������ID
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
		logger.debug("�õ������������ID="+sql);
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
	 * �õ������������ID
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
        logger.debug("�õ�ɱ���������������ID="+sql);
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
	 *  ��������ڹ���ս����
	 * @param list sceneId�ļ���
	 * @return
	 */
	public Map<Integer, Integer> getTongSiegeMap(List<Integer> list)
	{
		// // ������ID��ϳ��ַ���
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
	 * ���ɱ����
	 * @param pk
	 */
	public void clearPkLog(int siegeId,int siegeFightNumber)
	{
		String sql = "update tong_siege_pklog  set pk_number = 0 where siege_id="
			+siegeId+" and siege_number="+siegeFightNumber+"";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        logger.debug("���ɱ����="+sql);
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
	 * ����һ�׶ε���ʽ��Ϣ���뵽���ݿ���
	 * @param roleEntity
	 * @param siegeId
	 * @param siegeNumber
	 * @param displayInfoString ����������Ϣ
	 * @param personDisplay	���˹���ս��Ϣ
	 */
	public void addFirstEndDisplayInfo(RoleEntity roleEntity, int siegeId,
			int siegeNumber, String displayInfoString, String personDisplay)
	{
		String sql = "insert into tong_siegebattle_info values (null,"+roleEntity.getBasicInfo().getPPk()
							+","+siegeId+","+siegeNumber+",'"+displayInfoString+"','"+personDisplay+"')";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        logger.debug("���ɱ����="+sql);
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
	 * ��õ�һ�׶ν���������ʾ��Ϣ
	 * @param pk
	 * @return
	 */
	public String getBangPaiInfo(int pPk,String siegeId,String siegeNumber)
	{
		String sql = "select infoone from tong_siegebattle_info where p_pk="+pPk+" and siege_id="+siegeId+" and siege_number="+siegeNumber;
		String infoone = "";
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        logger.debug("���ɱ����="+sql);
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
	 * ��ø�����ʾ��Ϣ
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
        logger.debug("���ɱ����="+sql);
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
