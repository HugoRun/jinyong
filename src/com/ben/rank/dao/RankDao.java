package com.ben.rank.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ben.rank.model.Rank;
import com.ben.rank.model.RankVo;
import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class RankDao extends DaoBase
{
	// �޸��ֶ�
	public int updateAdd(Object p_pk, String field, Object value)
	{
		if (p_pk != null&&field!=null&&value!=null)
		{
			String aa = "select k." + field + " from rank k where k.p_pk = "
					+ p_pk;
			int i = 0;
			String sql = "update rank k set k." + field + " = k." + field + "+"
					+ value + " , k." + field + "_time = now() "
					+ " where k.p_pk = " + p_pk;
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				logger.debug(sql);
				stmt = conn.createStatement();
				if ((value+"").indexOf("-")!=-1)
				{
					ResultSet rs = stmt.executeQuery(aa);
					long zhi = 0;
					if (rs.next())
					{
						zhi = rs.getLong(field.trim());
					}
					if (zhi > 0)
					{
						i = stmt.executeUpdate(sql);
					}
				}
				else
				{
					i = stmt.executeUpdate(sql);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
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
					return i;
				}
			}
		}
		else
		{
			return 0;
		}
	}

	// �޸��ֶ�
	public int update(Object p_pk, String field, Object value)
	{
		if (p_pk != null)
		{
			int i = 0;
			String sql = "update rank k set k." + field + " = " + value
					+ " , k." + field + "_time = now() " + " where k.p_pk = "
					+ p_pk;
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				logger.debug(sql);
				stmt = conn.createStatement();
				i = stmt.executeUpdate(sql);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
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
					return i;
				}
			}
		}
		else
		{
			return 0;
		}
	}
	
	//�鿴�û��Ƿ����
	public int isExist(Object p_pk){
		if(p_pk!=null){
			int i = 0;
			String sql = "select * from rank r where r.p_pk = "+p_pk;
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				logger.debug(sql);
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next()){
					i++;
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
					return i;
			}
		}else{
			return 1;
		}
	}

	// �����û�
	public int insert(Object p_pk, String name)
	{
		if (p_pk != null)
		{
			int i = 0;
			String sql = "insert into rank(p_pk,p_name) values (" + p_pk + ",'"
					+ name + "' )";
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				logger.debug(sql);
				stmt = conn.createStatement();
				i = stmt.executeUpdate(sql);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
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
					return i;
				}
			}
		}
		else
		{
			return 0;
		}
	}
	
	
	// �����û�
	public int insert(Object p_pk, String name,int grade)
	{
		if (p_pk != null)
		{
			int i = 0;
			String sql = "insert into rank(p_pk,p_name,p_level) values (" + p_pk + ",'"
					+ name + "',"+grade+" )";
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				logger.debug(sql);
				stmt = conn.createStatement();
				i = stmt.executeUpdate(sql);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
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
					return i;
				}
			}
		}
		else
		{
			return 0;
		}
	}

	// �����ֵ�����
	public int updateYiqi(Object p_pk, int yiqi, String with_who)
	{
		if (p_pk != null)
		{
			int i = 0;
			String sql = "update rank k set k.yi = k.yi + " + yiqi
					+ ",k.yi_who = '" + with_who + "' where k.p_pk = " + p_pk;
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				logger.debug(sql);
				stmt = conn.createStatement();
				i = stmt.executeUpdate(sql);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
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
					return i;
				}
			}
		}
		else
		{
			return 0;
		}
	}

	// ɾ���ֵ�����
	public int updateYiqiToZero(Object p_pk)
	{
		if (p_pk != null)
		{
			int i = 0;
			String sql = "update rank k set k.yi = 0  where k.p_pk = " + p_pk;
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				logger.debug(sql);
				stmt = conn.createStatement();
				i = stmt.executeUpdate(sql);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
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
					return i;
				}
			}
		}
		else
		{
			return 0;
		}
	}

	// ���İ�������
	public int updateDear(Object p_pk, int dear, String with_who)
	{
		if (p_pk != null)
		{
			int i = 0;
			String sql = "update rank k set k.dear = k.dear + " + dear
					+ ",k.who = '" + with_who + "' where k.p_pk = " + p_pk;
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				logger.debug(sql);
				stmt = conn.createStatement();
				i = stmt.executeUpdate(sql);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
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
					return i;
				}
			}
		}
		else
		{
			return 0;
		}
	}

	// ɾ����������
	public int updateDearToZero(Object p_pk)
	{
		if (p_pk != null)
		{
			int i = 0;
			String sql = "update rank k set k.dear = 0 where k.p_pk = " + p_pk;
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				logger.debug(sql);
				stmt = conn.createStatement();
				i = stmt.executeUpdate(sql);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
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
					return i;
				}
			}
		}
		else
		{
			return 0;
		}
	}

	// ��������
	public int updateMenPai(Object p_pk, String menpai)
	{
		if (p_pk != null)
		{
			int i = 0;
			String sql = "update rank k set k.p_menpai = '" + menpai
					+ "' where k.p_pk = " + p_pk;
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				logger.debug(sql);
				stmt = conn.createStatement();
				i = stmt.executeUpdate(sql);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
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
					return i;
				}
			}
		}
		else
		{
			return 0;
		}
	}

	// �����ֶβ�ѯ
	public List<RankVo> findByField(String field, int i)
	{
		List<RankVo> list = new ArrayList<RankVo>();
		String sql = "select r.id, r.p_pk,r.p_name,r.p_level,r.p_menpai,r."+field+" from rank r where r." + field + " !=0 "
				+ (i == 1 ? " and r.exp_tong = 0 " : "") + "  order by r."
				+ field + " desc,r." + field + "_time asc limit 10";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = getRankVo(rs);
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
			return list;
		}
	}
	
	// �����ֶβ�ѯ
	public List<Rank> findByFieldaLL(String field, int i)
	{
		List<Rank> list = new ArrayList<Rank>();
		String sql = "select * from rank r where r." + field + " !=0 "
				+ (i == 1 ? " and r.exp_tong = 0 " : "") + "  order by r."
				+ field + " desc,r." + field + "_time asc limit 10";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = get(rs);
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
			return list;
		}
	}

	// �鿴�Լ�������
	public int findOwnByField(Object p_pk, String field, int i)
	{
		int paimin = 0;
		if (p_pk == null)
		{
			return paimin;
		}
		else
		{
			String sq = "select r."+field +" from rank r where r.p_pk = "+p_pk;
			String sql = "select count(*) from rank r where "
					+ (i == 1 ? " r.exp_tong = 0 and " : "") + " r." + field
					+ " > (select w." + field + " from rank w where w.p_pk = "
					+ p_pk + (i == 1 ? " and  r.exp_tong = 0 " : "")
					+ " ) ";
			String sql1 = "select count(*) from rank r where "
					+ (i == 1 ? " r.exp_tong = 0 and " : "") + " r." + field
					+ " = (select w." + field + " from rank w where w.p_pk = "
					+ p_pk + (i == 1 ? " and  r.exp_tong = 0 " : "")
					+ " ) and r."+field.trim()+"_time <= (select u."+field.trim()+"_time from rank u where u.p_pk = "+p_pk+")";
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				int ownField = 0;
				stmt = conn.createStatement();
				ResultSet r = stmt.executeQuery(sq);
				while (r.next())
				{
					ownField = r.getInt(1);
				}
				if(ownField!=0){
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next())
				{
					paimin = rs.getInt(1);
				}
				ResultSet rs1 = stmt.executeQuery(sql1);
				while (rs1.next())
				{
					int num = rs1.getInt(1);
					paimin += num==0?paimin==0?0:1:num;
				}
				rs.close();
				rs1.close();
				}
				r.close();
				stmt.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				dbConn.closeConn();
				return paimin;
			}
		}
	}
	
	private List<RankVo> getRankVo(ResultSet rs) throws java.sql.SQLException
	{
		List<RankVo> list = new ArrayList<RankVo>();
		if (rs != null)
		{
			while (rs.next())
			{
				RankVo rank = new RankVo();
				rank.setId(rs.getInt("id"));
				rank.setP_level(rs.getInt("p_level"));
				rank.setP_menpai(rs.getString("p_menpai"));
				rank.setP_name(rs.getString("p_name"));
				rank.setP_pk(rs.getInt("p_pk"));
				rank.setTong(rs.getInt(6));
				try
				{
					rank.setTong1(rs.getString(7));
				}
				catch (Exception e)
				{
				}finally{
				list.add(rank);
				}
			}
		}
		return list;
	}

	private List<Rank> get(ResultSet rs) throws java.sql.SQLException
	{
		List<Rank> list = new ArrayList<Rank>();
		if (rs != null)
		{
			while (rs.next())
			{
				Rank rank = new Rank();
				rank.setCredit(rs.getInt("credit"));
				rank.setCredit_time(rs.getTimestamp("credit_time"));
				rank.setDear(rs.getInt("dear"));
				rank.setDear_time(rs.getTimestamp("dear_time"));
				rank.setEvil(rs.getInt("evil"));
				rank.setEvil_time(rs.getTimestamp("evil_time"));
				rank.setExp_tong(rs.getInt("exp_tong"));
				rank.setGlory(rs.getInt("glory"));
				rank.setGlory_time(rs.getTimestamp("glory_time"));
				rank.setId(rs.getInt("id"));
				rank.setKill(rs.getInt("kill"));
				rank.setKill_time(rs.getTimestamp("kill_time"));
				rank.setMoney(rs.getLong("money"));
				rank.setMoney_time(rs.getTimestamp("money_time"));
				rank.setOpen(rs.getInt("open"));
				rank.setOpen_time(rs.getTimestamp("open_time"));
				rank.setP_exp(rs.getLong("p_exp"));
				rank.setP_exp_time(rs.getTimestamp("p_exp_time"));
				rank.setP_level(rs.getInt("p_level"));
				rank.setP_menpai(rs.getString("p_menpai"));
				rank.setP_name(rs.getString("p_name"));
				rank.setP_pk(rs.getInt("p_pk"));
				rank.setYuanbao(rs.getInt("yuanbao"));
				rank.setYuanbao_time(rs.getTimestamp("yuanbao_time"));
				rank.setAns(rs.getInt("ans"));
				rank.setAns_time(rs.getTimestamp("ans_time"));
				rank.setBangkill(rs.getInt("bangkill"));
				rank.setBangkill_time(rs.getTimestamp("bangkill_time"));
				rank.setKillboss(rs.getInt("killboss"));
				rank.setKillboss_time(rs.getTimestamp("killboss_time"));
				rank.setKillnpc(rs.getInt("killnpc"));
				rank.setKillnpc_time(rs.getTimestamp("killnpc_time"));
				rank.setMeng(rs.getInt("meng"));
				rank.setMeng_time(rs.getTimestamp("meng_time"));
				rank.setSale(rs.getInt("sale"));
				rank.setSale_time(rs.getTimestamp("sale_time"));
				rank.setVip(rs.getInt("vip"));
				rank.setVip_eff(rs.getInt("vip_eff"));
				rank.setVip_time(rs.getTimestamp("vip_time"));
				rank.setWei_other(rs.getInt("wei_other"));
				rank.setWei_task(rs.getInt("wei_task"));
				rank.setWei_time(rs.getTimestamp("wei_time"));
				rank.setWho(rs.getString("who"));
				rank.setYi(rs.getInt("yi"));
				rank.setYi_time(rs.getTimestamp("yi_time"));
				rank.setYi_who(rs.getString("yi_who"));
				rank.setZhong(rs.getInt("zhong"));
				rank.setZhong_time(rs.getTimestamp("zhong_time"));
				rank.setDead(rs.getInt("dead"));
				rank.setDead_time(rs.getTimestamp("dead_time"));
				rank.setBoyi(rs.getLong("boyi"));
				rank.setBoyi_time(rs.getTimestamp("boyi_time"));
				rank.setLost(rs.getInt("lost"));
				rank.setLost_time(rs.getTimestamp("lost_time"));
				list.add(rank);
			}
		}
		return list;
	}

	// �����ֶ���0
	public int clear(String field)
	{
		int i = 0;
		String sql = "update rank k set k." + field + " = 0 ";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			i = stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
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
				return i;
			}
		}
	}

	// �޸�vip
	public int updateVIP(Object p_pk, int vip_type, int vip_time)
	{
		if (p_pk != null)
		{
			int i = 0;
			String sql = "update rank k set k.vip = " + vip_type
					+ " , k.vip_eff = " + vip_time + " ,k.vip_time = now() where k.p_pk = "
					+ p_pk;
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				logger.debug(sql);
				stmt = conn.createStatement();
				i = stmt.executeUpdate(sql);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
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
					return i;
				}
			}
		}
		else
		{
			return 0;
		}
	}

	// ��ѯVIP
	public List<RankVo> findVip()
	{
		List<RankVo> list = new ArrayList<RankVo>();
		String sql = "select r.id, r.p_pk,r.p_name,r.p_level,r.p_menpai,r.vip,r.vip_eff from rank r where r.vip!=0 and r.vip_eff !=0 order by r.vip  desc,r.vip_eff desc ,r.vip_time asc limit 10";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = getRankVo(rs);
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
			return list;
		}
	}

	// �鿴�Լ���VIP����
	public int findOwnVIP(Object p_pk)
	{
		int paimin = 1;
		if (p_pk == null)
		{
			return 0;
		}
		else
		{
			String sq = "select r.vip from rank r where r.p_pk = "+p_pk;
			String sql = "select count(*) from rank r where r.vip > (select vip from rank where p_pk = "
					+ p_pk + " ) order by r.vip_time asc";
			String sql1 = "select count(*) from rank r where r.vip = (select vip from rank where p_pk = "
					+ p_pk
					+ ") and r.vip_eff > (select vip_eff from rank where p_pk = "
					+ p_pk + ")";
			String sql2 = "select count(*) from rank r where r.vip = (select vip from rank where p_pk = "
				+ p_pk	+ ") and r.vip_eff = (select vip_eff from rank where p_pk = "+ p_pk + ") and r.vip_time < (select vip_time from rank where p_pk = "+p_pk+")";
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				stmt = conn.createStatement();
				ResultSet r = stmt.executeQuery(sq);
				int j = 0;
				while (r.next())
				{
					j = r.getInt(1);
				}
				if(j!=0){
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next())
				{
					paimin += rs.getInt(1);
				}
				ResultSet rs1 = stmt.executeQuery(sql1);
				while (rs1.next())
				{
					paimin += rs1.getInt(1);
				}
				ResultSet rs2 = stmt.executeQuery(sql2);
				while (rs2.next())
				{
					paimin += rs2.getInt(1);
				}
				rs.close();
				rs1.close();
				rs2.close();
				}else{
					paimin = 0;
				}
				r.close();
				stmt.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				dbConn.closeConn();
				return paimin;
			}
		}
	}

	// ��ѯ��������
	public List<RankVo> findDear()
	{
		List<RankVo> list = new ArrayList<RankVo>();
		String sql = "select r.id, r.p_pk,r.p_name,r.p_level,r.p_menpai,r.dear,r.who from rank r where r.dear !=0 and r.who is not null order by r.dear desc,r.dear_time asc limit 20";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = getRankVo(rs);
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
			return list;
		}
	}

	// ��ѯ����
	public List<RankVo> findYi()
	{
		List<RankVo> list = new ArrayList<RankVo>();
		String sql = "select r.id, r.p_pk,r.p_name,r.p_level,r.p_menpai,r.yi,r.yi_who from rank r where r.yi!=0 and r.yi_who is not null order by r.yi desc,r.yi_time asc limit 20";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = getRankVo(rs);
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
			return list;
		}
	}

	// ���Ѿ�ͳ�ƹ�����Ĵ��ϱ�־
	public int updatePpk(int id)
	{
		int i = 0;
		String sql = "update rank k set k.exp_tong = 1 where k.id =  " + id;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			i = stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
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
				return i;
			}
		}
	}

	// ɾ����ɫ
	public int remove(Object p_pk)
	{
		int i = 0;
		if (p_pk == null)
		{
			return i;
		}
		String sql = "delete from rank where p_pk =  " + p_pk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			i = stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
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
				return i;
			}
		}
	}

	// ����ʥ��
	public List<RankVo> findSheng()
	{
		List<RankVo> list = new ArrayList<RankVo>();
		String sql = "select r.id, r.p_pk,r.p_name,r.p_level,r.p_menpai,r.p_exp from rank r order by r.p_exp+r.zhong+r.credit desc limit 10";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = getRankVo(rs);
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
			return list;
		}
	}
	
	// ����ʥ��
	public List<Rank> findSheng1()
	{
		List<Rank> list = new ArrayList<Rank>();
		String sql = "select * from rank r order by r.p_exp+r.zhong+r.credit desc limit 10";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = get(rs);
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
			return list;
		}
	}

	// �鿴�Լ��Ľ���ʥ������
	public int findOwnSheng(Object p_pk)
	{
		int paimin = 0;
		if (p_pk == null)
		{
			return paimin;
		}
		else
		{
			String sql = "select count(*) from rank r where (r.p_exp+r.zhong+r.credit) >= (select k.p_exp+k.zhong+k.credit from rank k  where k.p_pk = "
					+ p_pk + ")";
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next())
				{
					paimin = rs.getInt(1);
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
				return paimin;
			}
		}
	}
	/**********���ɾ����ɫ��ʱ��ɾ�����а�������Ϣ***********/
	public void removeRandInfo(int ppk)
	{
		String sql = "delete from rank where p_pk="+ppk+"";
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
