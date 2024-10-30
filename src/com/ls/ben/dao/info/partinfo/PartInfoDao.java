package com.ls.ben.dao.info.partinfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.organize.faction.Faction;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;

public class PartInfoDao extends BasicDaoSupport<PartInfoVO>
{
	public PartInfoDao()
	{
		super("u_part_info", DBConnection.GAME_USER_DB);
	}

	/**
	 * ������ɫ
	 * @param uPk		�˺�
	 * @param pName	
	 * @param pSex
	 * @param pUpHp		Ѫ������
	 * @param pUpMp		��������
	 * @param pGj
	 * @param pFy
	 * @param pXiaExperience
	 * @param pCopper
	 * @param pPks		PK����
	 * @param pDropMultiple		������
	 * @param pMap				��ʼ��������ַ
	 * @param pWrapContent		��������
	 * @param race				����
	 * @return
	 */
	public int add(String uPk, String pName, String pSex,
			 String pUpHp,  String pUpMp, 
			String pGj, String pFy, 
			String pCopper, String pPks,
			String pDropMultiple,String pMap, 
			String pWrapContent,int race)
	{
		int p_pk = -1;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "insert into u_part_info(u_pk,p_name,p_sex,p_up_hp,p_hp,p_up_mp,p_mp,p_gj,p_fy" +
					",p_copper,p_pks,p_drop_multiple,p_map,p_wrap_content,p_race,create_time) " +
					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
			logger.debug(sql);
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, Integer.parseInt(uPk));
			ps.setString(2, pName);
			ps.setInt(3, Integer.parseInt(pSex));
			
			ps.setInt(4, Integer.parseInt(pUpHp));
			ps.setInt(5, Integer.parseInt(pUpHp));
			ps.setInt(6, Integer.parseInt(pUpHp));
			ps.setInt(7, Integer.parseInt(pUpHp));
			ps.setInt(8, Integer.parseInt(pGj));
			ps.setInt(9, Integer.parseInt(pFy));
			
			ps.setString(10, pCopper);
			
			ps.setInt(11,Integer.parseInt(pPks));
			ps.setDouble(12,Double.parseDouble( pDropMultiple));
			ps.setInt(13, Integer.parseInt(pMap));
			
			ps.setInt(14, Integer.parseInt(pWrapContent));
			
			ps.setInt(15, race);
			
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if( rs.next() )
			{
				p_pk = rs.getInt(1);
			} 
			rs.close();
			ps.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return p_pk;
	}
	
	
	
	/**
	 * ָ�����ɳ�Աְλ������
	 */
	public int getCurFJobNum( int fId ,int fJob)
	{
		return super.getNumBySql("where f_id="+fId+" and f_job="+fJob);
	}
	
	/**
	 * ���ɳ�Ա�б�
	 * @param pName
	 * @return
	 */
	public QueryPage getPageListByFId( int f_id ,int page_no)
	{
		String condition_sql = "where f_id = "+f_id;
		String order_sql = "order by f_job desc,p_grade desc,f_contribute desc,f_jointime desc";
		return super.loadPageList(condition_sql,order_sql, page_no);
	}
	
	/**
	 * �õ����ɶӳ�id
	 * @return
	 */
	public int getFactinLeader(int f_id)
	{
		int p_pk = -1;
		String sql = "select p_pk from u_part_info where f_id = "+f_id+" and f_job="+Faction.ZUZHANG;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				p_pk = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return p_pk;
	}
	
	/**
	 * ���ɳ��ϼ������ϵĳ�Ա�б�
	 * @param pName
	 * @return
	 */
	public List<Integer> getUpZhanglaoListByFId( int f_id )
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select p_pk from u_part_info where f_id = "+f_id+" and f_job>="+Faction.ZHANGLAO;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				list.add(rs.getInt(1));
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return list;
	}
	
	/**
	 * ���ɳ����б�
	 * @param pName
	 * @return
	 */
	public QueryPage getPageZhanglaoListByFId( int f_id ,int page_no)
	{
		String condition_sql = "where f_id = "+f_id+" and f_job="+Faction.ZHANGLAO;
		String order_sql = "order by f_job desc,p_grade desc,f_contribute desc,f_jointime desc";
		return super.loadPageList(condition_sql,order_sql, page_no);
	}
	
	// ͨ��ע��ID ȥ�ҽ�ɫ һ��ע��IDֻ�ô���5����ɫ
	public int getByName(String pName)
	{
		int p_pk = -1;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{

			String sql = "select p_pk from u_part_info where p_name='" + pName
					+ "'";
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
			{
				p_pk =  rs.getInt("p_pk");
			}
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return p_pk;
	}



	// ͨ��ע��ID ȥ�ҽ�ɫ һ��ע��IDֻ�ô���5����ɫ
	public List<PartInfoVO> getPartTypeList(String uPk)
	{
		List<PartInfoVO> list = new ArrayList<PartInfoVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "select * from u_part_info where u_pk=" + uPk + " and delete_flag=0";
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				PartInfoVO vo = new PartInfoVO();
				vo.setUPk(rs.getInt("u_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
				vo.setPSex(rs.getInt("p_sex"));
				vo.setPGrade(rs.getInt("p_grade"));
				vo.setPExperience(rs.getString("p_experience"));
				vo.setPMap(rs.getString("p_map"));
				vo.setDeleteFlag(rs.getInt("delete_flag"));
				vo.setDeleteTime(rs.getTimestamp("delete_time"));
				list.add(vo);
			}	
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return list;

	}
	
	/**
	 * 	����˺��� ���ڱ�ɾ���Ľ�ɫ�б�
	 *	�޸� ȷ��ɾ���Ľ�ɫ ɾ����Ǵ�0��Ϊ1
	 */	
	public List<PartInfoVO> getDeleteStateRoles(String uPk)
	{
		List<PartInfoVO> list = new ArrayList<PartInfoVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "select p_pk,delete_time from u_part_info where u_pk=" + uPk + " and delete_flag=1";
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{		
				PartInfoVO vo = new PartInfoVO();
				vo.setPPk(rs.getInt("p_pk"));
				vo.setDeleteTime(rs.getTimestamp("delete_time"));
				list.add(vo);
			}	
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		logger.debug("partInfo.size="+list.size());
		return list;
		
	}

	/**
	 * ͨ��id �õ������Ϣ
	 * 
	 * @param p_pk
	 * @return
	 */
	public PartInfoVO getPartInfoByID(int p_pk)
	{
		PartInfoVO vo = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{

			String sql = "select * from u_part_info where p_pk=" + p_pk;
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new PartInfoVO();
				vo.setUPk(rs.getInt("u_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
				vo.setPSex(rs.getInt("p_sex"));

				vo.setPUpHp(rs.getInt("p_up_hp"));
				vo.setPUpMp(rs.getInt("p_up_mp"));

				vo.setPHp(rs.getInt("p_hp"));
				vo.setPMp(rs.getInt("p_mp"));

				vo.setPGj(rs.getInt("p_gj"));
				vo.setPFy(rs.getInt("p_fy"));

				vo.setPGrade(rs.getInt("p_grade"));
				vo.setPExperience(rs.getString("p_experience"));

				vo.setPCopper(rs.getString("p_copper"));

				vo.setPPks(rs.getInt("p_pks"));
				vo.setPPkValue(rs.getInt("p_pk_value"));
				vo.setPkChangeTime(rs.getTimestamp("p_pk_changetime"));
				vo.setPMap(rs.getString("p_map"));

				vo.setPWrapContent(rs.getInt("p_wrap_content"));

				vo.setPSex(rs.getInt("p_sex"));
				vo.setPHarness(rs.getInt("p_harness"));
				vo.setDropMultiple(rs.getDouble("p_drop_multiple"));
				vo.setTe_level(rs.getInt("te_level"));
				vo.setChuangong(rs.getString("chuangong"));
				vo.setLast_shoutu_time(rs.getDate("last_shoutu_time"));
				vo.setPlayer_state_by_new(rs.getInt("player_state_by_new"));
				
				vo.setFId(rs.getInt("f_id"));
				vo.setFJob(rs.getInt("f_job"));
				vo.setFContribute(rs.getInt("f_contribute"));
				vo.setFTitle(rs.getString("f_title"));
				vo.setPRace(rs.getInt("p_race"));
			}
			rs.close();
			stmt.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return vo;
	}

	

    /**
     * ���ý�ɫ��ɾ��״̬
     * @param p_pk
     * @param delete_flag
     * @return
     */
	public int updateDeleteFlag( int p_pk,int delete_flag )
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			String set_delete_time = "";
			if( delete_flag==0 )//���ʱɾ��������Ҫ����delete_timeΪ��ǰʱ��
			{
				set_delete_time = ",delete_time=now() ";
			}
			String sql = "update u_part_info set delete_flag = " + delete_flag + set_delete_time + " where p_pk = " + p_pk;
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return result;
	}
	
	



	/**
	 * �õ���ɫ����
	 * 
	 * @param p_pk
	 */
	public String getNameByPpk(int p_pk)
	{
		String p_name = "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "select p_name from u_part_info where p_pk=" + p_pk;
			logger.debug("�õ���ɫ����="+sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				p_name = rs.getString("p_name");
			}
			rs.close();
			stmt.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return p_name;
	}
	/**
	 * * sceneStr������(111,112,113,115)���scene_name����
	 * �õ���sceneStr�е�����p_pk����
	 * @param sceneStr
	 * @return
	 */
	public List<Integer> getPPkListBySceneStr(String sceneStr)
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select p_pk from u_part_info where p_map in "+sceneStr;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug("�õ���sceneStr�е�����p_pk����="+sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				list.add(rs.getInt("p_pk"));
			}
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
		finally
		{
			dbConn.closeConn();
		}
		
		return list;	
	}

	/**
	 * ��� �ڵ�ǰ������ ������������һ���򼸸�
	 * @param list �ǰ���ID�����
	 * @param sceneStr ��sceneID�����,
	 * @return
	 */
	public List<int[]> getMostTongPersonInMap(List<Integer> list,
			String sceneStr)
	{
		// ������ID��ϳ��ַ���
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("(");
		for(int i=0;i < list.size();i++) {
			sBuffer.append(list.get(i));
			if ( i != list.size() -1) {
				sBuffer.append(",");
			}
		}
		sBuffer.append(")");
		
		int mostPerson = 0;
		// �����ҳ��ڵ�ǰ�����ڰ�����������һ��
		String sql = "select * from (select p_tong,count(1) as numb from u_part_info where  p_map in "+sceneStr+" and p_tong in "
					+sBuffer.toString()+" group by p_tong order by numb desc) asd limit 1";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug("�õ���sceneStr�е�����p_pk����="+sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				mostPerson = rs.getInt("numb");
			}
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		List<int[]> list2 = new ArrayList<int[]>();
		// Ȼ���ҳ����� �������ڴ� �����İ���
		String sql2 = "select * from (select p_tong,count(1) as numb from u_part_info where  p_map in "+sceneStr+" and p_tong in "
						+sBuffer.toString()+"group by p_tong order by numb desc) asd where numb >= "+mostPerson;
		conn = dbConn.getConn();
		try
		{
			logger.debug("�õ���sceneStr�е�����p_pk����="+sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			while (rs.next())
			{
				int[] tongIdPerson = new int[2];
				tongIdPerson[0] = rs.getInt("p_tong");
				tongIdPerson[1] = rs.getInt("numb");
				list2.add(tongIdPerson);
			}
			rs.close();
			stmt.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		finally
		{
			dbConn.closeConn();
		}		
		return list2;
		
	}
	
	
	/**
	 * ��� �ڵ�ǰ������ ������������һ���򼸸�
	 * @param list �ǰ���ID�����
	 * @param sceneStr ��sceneID�����,
	 * @return
	 */
	public List<Integer> getAllPersonInMap(String sceneStr)
	{		
		// �����ҳ��ڵ�ǰ�����ڰ�����������һ��
		String sql = "select p_pk from u_part_info where  p_map in "+sceneStr+" ";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		List<Integer> list = new ArrayList<Integer>();
		conn = dbConn.getConn();
		try
		{
			logger.debug("�õ���sceneStr�е�����p_pk����="+sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				list.add(rs.getInt("p_pk"));
			}
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			dbConn.closeConn();
		}	
		
		
		return list;
		
	}

	/**
	 * ��ô�PPk��ɾ����־
	 * @param pk
	 * @return
	 */
	public int getDeleteState(String pPk)
	{
		String sql = "select delete_flag from u_part_info where p_pk = "+pPk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);	
		int delete_flag = 0;
		conn = dbConn.getConn();
		try
		{
			logger.debug("�õ���sceneStr�е�����p_pk����="+sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				delete_flag = rs.getInt("delete_flag");
			}
			rs.close();
			stmt.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		finally
		{
			dbConn.closeConn();
		}		
		return delete_flag;
	}
	
	
	public void updateLoginState(String p_pk,int login_state){
		String sql ="update u_part_info set login_state = "+login_state+" where p_pk ='"+p_pk+"'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	public void updateLoginStateByUpk(String u_pk,int login_state){
		String sql ="update u_part_info set login_state = "+login_state+" where u_pk ='"+u_pk+"'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	public void updateLoginState(String login_state){
		String sql ="update u_part_info set login_state = '"+login_state+"'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * �������p_pk�ļ���
	 * @return
	 */
	public List<Integer> getAllPPkList()
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select p_pk from u_part_info ";
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug("�������p_pk�ļ���="+sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				list.add(rs.getInt("p_pk"));
			}
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
		finally
		{
			dbConn.closeConn();
		}
		
		return list;	
	}
	
	public void updateP_harness(Object ppk,int p_harness){
		String sql = "update u_part_info u set u.p_harness = "+p_harness+" where u.p_pk = "+ppk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	public void updateLastTime(Object ppk){
		if(ppk!=null){
			String sql = "update u_part_info u set u.last_shoutu_time = now() where u.p_pk = "+ppk;
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				dbConn.closeConn();
			}
		}
	}

	@Override
	protected PartInfoVO loadData(ResultSet rs) throws SQLException
	{
		PartInfoVO vo = new PartInfoVO();
		vo.setUPk(rs.getInt("u_pk"));
		vo.setPPk(rs.getInt("p_pk"));
		vo.setPName(rs.getString("p_name"));
		vo.setPSex(rs.getInt("p_sex"));
		vo.setPGrade(rs.getInt("p_grade"));
		vo.setPMap(rs.getString("p_map"));
		vo.setLoginState(rs.getInt("login_state"));
		
		vo.setFId(rs.getInt("f_id"));
		vo.setFJob(rs.getInt("f_job"));
		vo.setFContribute(rs.getInt("f_contribute"));
		vo.setFTitle(rs.getString("f_title"));
		
		return vo;
	}
}
