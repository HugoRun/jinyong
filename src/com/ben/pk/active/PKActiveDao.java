package com.ben.pk.active;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * 
 * @author thomas.lei ���ܣ�PK������ݴ��� 27/04/10 PM
 * 
 */
public class PKActiveDao extends DaoBase
{
	
	// PK��ұ���
	public int pkActiveRegist(PKActiveRegist role)
	{
		String sql = "insert into pk_active_regist (roleID,roleLevel,roleName,registTime,isWin,isEnter) values ("
				+ role.getRoleID()
				+ ","
				+ role.getRoleLevel()
				+ ",'"+ role.getRoleName() + "',now()," + role.getIsWin() + ",0)";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		int count = 0;// �ж��Ƿ����ӳɹ�
		try
		{
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return count;
	}

	// ��ѯ����Ƿ�����ʷ������¼
	public PKActiveRegist checkRoleRegist(int roleID)
	{
		String sql = "select*from pk_active_regist where roleID=" + roleID + "";
		PKActiveRegist pr =null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				pr=new PKActiveRegist();
				pr.setId(rs.getInt("ID"));
				pr.setRoleID(rs.getInt("roleID"));
				pr.setRoleLevel(rs.getInt("roleLevel"));
				pr.setRoleName(rs.getString("roleName"));
				pr.setRoleTime(rs.getDate("registTime"));
				pr.setIsWin(rs.getInt("isWin"));
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return pr;
		}
	}

	// �������Ѿ�����ʷ������¼����±�����¼
	public int refreshRegist(PKActiveRegist role)
	{
		String sql = "update pk_active_regist set roleLevel="
				+ role.getRoleLevel() + ",roleName='" + role.getRoleName()
				+ "',registTime=now(),isWin=" + role.getIsWin()
				+ ",isEnter=0 where roleId=" + role.getRoleID() + "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return count;
		}
	}

	// ����PK�����ʱɾ���ϴ����ɵĶ�����Ϣ
	public int deleteVsInfo()
	{
		String sql = "delete from pk_vs";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return count;
		}
	}

	// �����µĶ�����Ϣ
	public int addVsInfo(PKVs vs)
	{
		String sql = "insert into pk_vs (roleAID,roleBID,roleAName,roleBName,winRoleID)values("+ vs.getRoleAID()+","+ vs.getRoleBID()+",'"+ vs.getRoleAName()+"','"+ vs.getRoleBName()+"',"+ vs.getWinRoleID() + ")";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return count;
		}
	}

	// ��ѯ������Ϣ
	public List<PKVs> getVsInfo(int index,int limit)
	{
		String sql = "select*from pk_vs limit "+index*limit+","+limit+"";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		List<PKVs> list = new ArrayList<PKVs>();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				PKVs vs = new PKVs();
				vs.setId(rs.getInt("ID"));
				vs.setRoleAID(rs.getInt("roleAID"));
				vs.setRoleBID(rs.getInt("roleBID"));
				vs.setRoleAName(rs.getString("roleAName"));
				vs.setRoleBName(rs.getString("roleBName"));
				vs.setWinRoleID(rs.getInt("winRoleID"));
				list.add(vs);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return list;
		}
	}

	// ��ȡ���ʸ�μ���һ��PK�����id����

	public int[] getRoleIDs()
	{
		String sql = "select*from pk_active_regist where isWin=0  order by roleLevel ";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		List list=new ArrayList();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				list.add(rs.getInt("roleID"));
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		int ids[]=new int[list.size()];
		for (int i = 0; i <list.size(); i++)
		{
			Integer id=(Integer)list.get(i);
			ids[i]=id;
		}
		
		return ids;
	}

	// ������ʸ�μ���һ��PK�����ID��Nameӳ����Ϣ
	public Map<Integer, String> getRoleInfo()
	{
		String sql = "select*from pk_active_regist where isWin=0";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		Map<Integer, String> map = new HashMap<Integer, String>();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				int id = rs.getInt("roleID");
				String name = rs.getString("roleName");
				map.put(new Integer(id), name);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return map;
		}
	}
	//����pk_active_regist����isWin�ֶ�
	public int  updateIsWin(int roleID,int iswin)
	{
		String sql="update pk_active_regist set isWin="+iswin+" where roleID="+roleID+"";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		int count=0;
		try
		{
			stmt = conn.createStatement();
			count=stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return count;
		}
	}
	//����pk_vs���е�winroleID�ֶ�
	public int  updateWinRoleID(int winRoleId)
	{
		String sql="update pk_vs set winRoleID="+winRoleId+" where roleAID="+winRoleId+" or roleBID="+winRoleId+"";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		int count=0;
		try
		{
			stmt = conn.createStatement();
			count=stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return count;
		}
	}
	//�õ�������Ϣ��������
	public int getTotalNum()
	{
		String sql="select count(*) as total from pk_vs";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		int count=0;
		try
		{
			stmt=conn.createStatement();
			ResultSet rs= stmt.executeQuery(sql);
			if(rs.next())
			{
				count=rs.getInt("total");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return count;
		}
	}
	//�õ��Է���PPK
	public int getAppk(int ppk)
	{
		String sql="select roleAID from pk_vs where roleBID="+ppk+"";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		int appk=0;
		try
		{
			stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				appk=rs.getInt("roleAID");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return appk;
		}
		
	}
	public int getBppk(int ppk)
	{
		String sql="select roleBID from pk_vs where roleAID="+ppk+"";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		int bppk=0;
		try
		{
			stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				bppk=rs.getInt("roleBID");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return bppk;
		}
	}
	//�жϲ�������Ƿ��Ѿ�ʧ��
	public boolean checkIsFail(int roleId)
	{
		String sql="select isWin from pk_active_regist where roleID="+roleId+"";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		int isWin=0;
		boolean isEnter=true;
		try
		{
			stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				isWin=rs.getInt("isWin");
			}
			if(isWin==1)
			{
				isEnter=false;
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return isEnter;
			
		}
	}
	//������ҽ��볡����״̬
	public int updateEnterState(int roleId,int state)
	{
		String sql="update pk_active_regist set isEnter="+state+" where roleId="+roleId+"";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		int count=0;
		try
		{
			stmt=conn.createStatement();
			count=stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return count;
		}
	}
	//�ָ���ҽ��볡���ĳ�ʼ״̬
	public int updateEnterState()
	{
		String sql="update pk_active_regist set isEnter=0";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		int count=0;
		try
		{
			stmt=conn.createStatement();
			count=stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return count;
		}
	}
	//��ʱ��û�н���������ص���ҵĸ��� ������Ϊ��
	public int updateOutofTime()
	{
		String sql="update pk_active_regist set isWin=1 where isWin=0 and isEnter=0";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		int count=0;
		try
		{
			stmt=conn.createStatement();
			count=stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return count;
		}
	}
	//�õ���ʱ��û�н��볡�صĽ�ɫID
	public List getOutofEnterIDs()
	{
		String sql="select roleID from pk_active_regist where iswin=0 and isEnter=0";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		List list=new ArrayList();
		try
		{
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				list.add(rs.getInt("roleID"));
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return list;
	}
	//��ѯ����û�н����ID MAP
	public Map<Integer,Integer> getNoresultVs()
	{
		String sql="select*from pk_vs where winRoleID =0";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		Map<Integer,Integer> map=new HashMap<Integer, Integer>();
		try
		{
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				map.put(rs.getInt("roleAID"),rs.getInt("roleBID"));
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return map;
		}
	}
	//��ѯ�Է�������
	public String getOherName(int roleId)
	{
		String sql="select roleName from pk_active_regist where roleID="+roleId+" ";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		String name="";
		try
		{
			stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				name=rs.getString("roleName");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
			return name;
		}
	}
	//�ж��Ƿ����ʸ���ȡ��Ʒ
	public boolean isGetPrice(int roleID)
	{
		String sql="select*from pk_active_regist where isGetprice=1 and roleID="+roleID+"";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		boolean flag=false;
		try
		{
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				flag=true;
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return flag;
	}
	//�޸���ȡ��Ʒ��״̬
	public void updatePriceState(int roleID,int isPrice)
	{
		String sql="update pk_active_regist set isGetPrice="+isPrice+" where roleID="+roleID+"";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		try
		{
			stmt=conn.createStatement();
			stmt.execute(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	//�жϲ��������Ƿ���˰�ǿ ��ǿ ���߰����
	public int getPlayerNum()
	{
		String sql="select count(*) as num from pk_active_regist where isGetPrice=1";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		int num=0;
		try
		{
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				num=rs.getInt("num");
			}
			stmt.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return num;
	}
	//��ѯ���еı��������Ϣ
	public List getAllRole()
	{
		String sql="select*From pk_active_regist where iswin=0";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn=dbConn.getConn();
		logger.debug(sql);
		int num=0;
		List list=new ArrayList();
		try
		{
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				PKActiveRegist pr=new PKActiveRegist();
				pr.setId(rs.getInt("ID"));
				pr.setRoleID(rs.getInt("roleID"));
				pr.setRoleLevel(rs.getInt("roleLevel"));
				pr.setRoleName(rs.getString("roleName"));
				pr.setRoleTime(rs.getDate("registTime"));
				pr.setIsWin(rs.getInt("isWin"));
				list.add(pr);
			}
			stmt.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
}
