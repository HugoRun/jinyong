package com.ben.dao.friend;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ben.vo.friend.FriendVO;
import com.ls.web.service.rank.RankService;
import com.pub.db.mysql.SqlData;
import com.web.jieyi.util.Constant;

/**
 * 功能:好友
 * 
 * @author 侯浩军 9:24:55 AM
 */
public class FriendDAO
{
	SqlData con;

	/**
	 * 加好友
	 */
	public void friendAdd(int pPk, String pByPk, String pByName, int online,
			String time)
	{
		try
		{
			con = new SqlData();
			String sql = "insert into u_friend(p_pk,fd_pk,fd_name,fd_online,create_time) values('"
					+ pPk
					+ "','"
					+ pByPk
					+ "','"
					+ pByName
					+ "','"
					+ online
					+ "','" + time + "')";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * 查询是否已经有该好友了
	 */
	public boolean whetherfriend(int pPk, String pByPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_friend where p_pk='" + pPk
					+ "' and fd_pk='" + pByPk + "'";
			ResultSet rs = con.query(sql);
			if (rs.next())
			{
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return false;
	}

	/**
	 * 好友List
	 */
	public List<FriendVO> getFriendListAll(int pPk, int pageno, int perpage)
	{
		try
		{
			String sql = null;
			con = new SqlData();
			if (perpage != 0)
			{
				sql = "select upf.p_map,f_pk,uf.p_pk,uf.dear,uf.love_dear,uf.relation,uf.exp_share,fd_pk,fd_name,uf.fd_online,uf.create_time,uli.login_state from u_friend as uf,u_part_info as upf ,u_login_info as uli where uf.fd_pk=upf.p_pk and  upf.u_pk=uli.u_pk and uf.p_pk ="
						+ pPk
						+ " order by uli.login_state desc limit "
						+ pageno + "," + perpage + "";
			}
			else
			{
				sql = "select upf.p_map,f_pk,uf.p_pk,uf.dear,uf.love_dear,uf.relation,uf.exp_share,fd_pk,fd_name,uf.fd_online,uf.create_time,uli.login_state from u_friend as uf,u_part_info as upf ,u_login_info as uli where uf.fd_pk=upf.p_pk and  upf.u_pk=uli.u_pk and uf.p_pk ="
						+ pPk + " order by uli.login_state desc";
			}
			ResultSet rs = con.query(sql);
			List<FriendVO> list = new ArrayList<FriendVO>();
			while (rs.next())
			{
				FriendVO vo = new FriendVO();
				vo.setFPk(rs.getInt("f_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setFdPk(rs.getInt("fd_pk"));
				vo.setFdName(rs.getString("fd_name"));
				vo.setFOnline(rs.getInt("fd_online"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setPMap(rs.getInt("p_map"));
				vo.setDear(rs.getInt("dear"));
				vo.setRelation(rs.getInt("relation"));
				vo.setExpShare(rs.getInt("exp_share"));
				vo.setLove_dear(rs.getInt("love_dear"));
				vo.setLogin_state(rs.getInt("login_state"));
				list.add(vo);
			}
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;

	}

	/**
	 * 好友List
	 */
	public List<FriendVO> getFriendListAll(int pPk)
	{
		try
		{
			String sql = null;
			con = new SqlData();

			sql = "select upf.p_map,f_pk,uf.p_pk,uf.dear,uf.love_dear,uf.relation,uf.exp_share,fd_pk,fd_name,uf.fd_online,uf.create_time from u_friend uf join u_part_info upf on uf.p_pk = upf.p_pk where uf.fd_pk ="
					+ pPk + " order by uf.fd_online desc";

			// //System.out.println(sql);
			ResultSet rs = con.query(sql);
			List<FriendVO> list = new ArrayList<FriendVO>();
			while (rs.next())
			{
				FriendVO vo = new FriendVO();
				vo.setFPk(rs.getInt("f_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setFdPk(rs.getInt("fd_pk"));
				vo.setFdName(rs.getString("fd_name"));
				vo.setFOnline(rs.getInt("fd_online"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setPMap(rs.getInt("p_map"));
				vo.setDear(rs.getInt("dear"));
				vo.setRelation(rs.getInt("relation"));
				vo.setExpShare(rs.getInt("exp_share"));
				vo.setLove_dear(rs.getInt("love_dear"));
				list.add(vo);
			}
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;

	}

	/**
	 * 好友View
	 */
	public FriendVO getFriendView(int pPk, String pByPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_friend where p_pk='" + pPk
					+ "' and fd_pk='" + pByPk + "'";
			ResultSet rs = con.query(sql);
			FriendVO vo = null;
			if (rs.next())
			{
				vo = new FriendVO();
				vo.setFPk(rs.getInt("f_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setFdPk(rs.getInt("fd_pk"));
				vo.setFdName(rs.getString("fd_name"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setDear(rs.getInt("dear"));
				vo.setRelation(rs.getInt("relation"));
				vo.setExpShare(rs.getInt("exp_share"));
				vo.setLove_dear(rs.getInt("love_dear"));
			}
			return vo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;

	}

	/**
	 * 删除好友
	 */
	public void getDeleteFriend(int pPk, String pByPk)
	{
		try
		{
			con = new SqlData();
			String sql = "delete from u_friend where p_pk='" + pPk
					+ "' and fd_pk='" + pByPk + "'";
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * 更新在线还是不在线
	 * 
	 * @param pPk
	 * @param fdOnline
	 *            0 不在线 1在线
	 */
	public void updateFriendOnline(int pPk, int fdOnline)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_friend set fd_online='" + fdOnline
					+ "' where fd_pk=" + pPk;
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}

	}

	/**
	 * 在线好友List
	 */
	public List<FriendVO> getFriendListAllOnline(int pPk, int pageno,
			int perpage)
	{
		try
		{
			String sql = null;
			con = new SqlData();
			if (perpage != 0)
			{
				sql = "select upf.p_map,f_pk,uf.dear,uf.love_dear,uf.relation,uf.exp_share,uf.p_pk,fd_pk,fd_name,uf.fd_online,uf.create_time from u_friend uf join u_part_info upf on uf.fd_pk = upf.p_pk where uf.p_pk ="
						+ pPk
						+ " and uf.fd_online = 1  limit "
						+ pageno
						+ ","
						+ perpage;
			}
			else
			{
				sql = "select upf.p_map,f_pk,uf.dear,uf.love_dear,uf.relation,uf.exp_share,uf.p_pk,fd_pk,fd_name,uf.fd_online,uf.create_time from u_friend uf join u_part_info upf on uf.fd_pk = upf.p_pk where uf.p_pk ="
						+ pPk + " and uf.fd_online = 1";
			}
			ResultSet rs = con.query(sql);
			List<FriendVO> list = new ArrayList<FriendVO>();
			while (rs.next())
			{
				FriendVO vo = new FriendVO();
				vo.setFPk(rs.getInt("f_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setFdPk(rs.getInt("fd_pk"));
				vo.setFdName(rs.getString("fd_name"));
				vo.setFOnline(rs.getInt("fd_online"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setPMap(rs.getInt("p_map"));
				vo.setDear(rs.getInt("dear"));
				vo.setRelation(rs.getInt("relation"));
				vo.setExpShare(rs.getInt("exp_share"));
				vo.setLove_dear(rs.getInt("love_dear"));
				list.add(vo);
			}
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;
	}

	/**
	 * 可以结义好友List
	 */
	public List<FriendVO> getFriendListAll1(int pPk, int pageno, int perpage,
			int relation)
	{
		List<FriendVO> list = new ArrayList<FriendVO>();
		try
		{
			String sql = null;
			con = new SqlData();
			if (perpage != 0)
			{
				sql = "select upf.p_map,uf.dear,uf.love_dear,uf.relation,uf.exp_share,f_pk,uf.p_pk,fd_pk,fd_name,uf.fd_online,uf.create_time,uf.tim from u_friend uf join u_part_info upf on uf.fd_pk = upf.p_pk where uf.p_pk ="
						+ pPk
						+ " and uf.relation = "
						+ relation
						+ " order by uf.fd_online desc limit "
						+ pageno
						+ ","
						+ perpage;
			}
			else
			{
				sql = "select upf.p_map,uf.dear,uf.love_dear,uf.relation,uf.exp_share,f_pk,uf.p_pk,fd_pk,fd_name,uf.fd_online,uf.create_time,uf.tim from u_friend uf join u_part_info upf on uf.fd_pk = upf.p_pk where uf.p_pk ="
						+ pPk
						+ " and uf.relation = "
						+ relation
						+ " order by uf.fd_online desc";
			}
			ResultSet rs = con.query(sql);

			list = get(rs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return list;
	}

	private List<FriendVO> get(ResultSet rs) throws java.sql.SQLException
	{
		List<FriendVO> list = new ArrayList<FriendVO>();
		if (rs != null)
		{
			while (rs.next())
			{
				FriendVO vo = new FriendVO();
				vo.setFPk(rs.getInt("f_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setFdPk(rs.getInt("fd_pk"));
				vo.setFdName(rs.getString("fd_name"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setDear(rs.getInt("dear"));
				vo.setRelation(rs.getInt("relation"));
				vo.setExpShare(rs.getInt("exp_share"));
				vo.setLove_dear(rs.getInt("love_dear"));
				vo.setTim(rs.getTimestamp("tim"));
				list.add(vo);
			}
		}
		return list;
	}

	public void jieyi(String pPk, String pByPk, int relation)
	{

		try
		{
			con = new SqlData();
			String add = "";
			if (relation == 0)
			{
				add = " , u.dear = 0 ,u.exp_share = 0,u.love_dear = 0 ";
			}
			if (relation == 2)
			{
				add = " ,u.love_dear = " + Constant.INIT_LOVE_DEAR;
			}
			String sql = "update u_friend u set u.tim = now() , u.relation = "
					+ relation + add + " where u.p_pk = " + pPk.trim()
					+ " and u.fd_pk = " + pByPk.trim();
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * 查看自己是否已结婚
	 */
	public List<FriendVO> isMerry(String pPk)
	{
		List<FriendVO> list = new ArrayList<FriendVO>();
		try
		{

			con = new SqlData();
			String sql = "select * from u_friend u where u.p_pk='" + pPk.trim()
					+ "' and u.relation in  (2,3) ";
			ResultSet rs = con.query(sql);
			list = get(rs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return list;
	}

	/**
	 * 可以结婚的好友
	 * 
	 * @param pPk
	 * @param pageno
	 * @param perpage
	 * @return
	 */
	public List<FriendVO> getCanMerry(int pPk, int gender, int pageno,
			int perpage)
	{
		List<FriendVO> list = new ArrayList<FriendVO>();
		try
		{
			String sql = null;
			con = new SqlData();
			if (perpage != 0)
			{
				sql = "select upf.p_map,uf.dear,uf.love_dear,uf.relation,uf.exp_share,f_pk,uf.p_pk,fd_pk,fd_name,uf.fd_online,uf.create_time,uf.tim from u_friend uf,u_part_info upf where uf.p_pk = "
						+ pPk
						+ " and uf.relation = 0 and uf.fd_pk = upf.p_pk and upf.p_sex != "
						+ gender
						+ " order by uf.fd_online desc limit "
						+ pageno + "," + perpage;
			}
			else
			{
				sql = "select upf.p_map,uf.dear,uf.love_dear,uf.relation,uf.exp_share,f_pk,uf.p_pk,fd_pk,fd_name,uf.fd_online,uf.create_time,uf.tim from u_friend uf,u_part_info upf where uf.p_pk = "
						+ pPk
						+ " and uf.relation = 0 and  uf.fd_pk = upf.p_pk and upf.p_sex != "
						+ gender;
			}
			ResultSet rs = con.query(sql);

			list = get(rs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return list;
	}

	/**
	 * 增加亲密度
	 * 
	 * @param p_pk
	 * @param fd_pk
	 * @param dear
	 *            增加或者减少多少，带有符号
	 * @param relation
	 *            两者之间关系
	 */
	public void addDear(String p_pk, String fd_pk, String dear, String f_name,
			String fd_name)
	{
		if (getFriendView(Integer.parseInt(p_pk.trim()), fd_pk) != null
				&& getFriendView(Integer.parseInt(fd_pk.trim()), p_pk) != null)
		{
			String sql = "update u_friend u set u.dear = u.dear " + dear
					+ " where u.p_pk = " + p_pk + " and u.fd_pk = " + fd_pk;
			String sql1 = "update u_friend u set u.dear = u.dear " + dear
					+ " where u.p_pk = " + fd_pk + " and u.fd_pk = " + p_pk;
			new RankService().updateYiqi(p_pk, 1, fd_name);
			new RankService().updateYiqi(fd_pk, 1, f_name);
			try
			{
				con = new SqlData();
				con.update(sql);
				con.update(sql1);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				con.close();
			}
		}
	}

	public int jieyiCount(String p_pk, String ids, int relation)
	{
		String sql = "select count(*) as ccc from u_friend u where u.p_pk = "
				+ p_pk + " and u.fd_pk in (" + ids.trim()
				+ ") and u.relation in (1,2) ";
		try
		{
			con = new SqlData();
			ResultSet rs = con.query(sql);
			while (rs.next())
			{
				return rs.getInt("ccc");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return 0;
	}

	public List<FriendVO> findFriends(String p_pk, String fp_pks)
	{
		List<FriendVO> list = new ArrayList<FriendVO>();
		if (p_pk == null || "".equals(p_pk.trim()) || fp_pks == null
				|| fp_pks.trim().equals(""))
		{
			return list;
		}
		String sql = "select * from u_friend u where u.p_pk = "
				+ p_pk
				+ " and u.fd_pk in ("
				+ ((fp_pks.lastIndexOf(",") + 1 == fp_pks.length()) ? fp_pks
						.substring(0, fp_pks.lastIndexOf(",")) : fp_pks) + ")";
		try
		{
			con = new SqlData();
			ResultSet rs = con.query(sql);
			list = get(rs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return list;
	}

	// 共享经验
	public void setExpShare(int p_pk, int fd_pk, int exp)
	{
		String sql = "update u_friend u set u.exp_share = " + exp
				+ " where u.p_pk = " + p_pk + " and u.fd_pk = " + fd_pk;
		try
		{
			con = new SqlData();
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	// 查看可以获取经验的好友列表
	public List<FriendVO> findCanGetExp(int p_pk, int relation, int start,
			int count)
	{
		String sql = "";
		if (count != 0)
		{
			sql = "select * from u_friend u where u.p_pk = " + p_pk
					+ " and u.relation = " + relation
					+ " and u.exp_share > 0 limit " + start + "," + count;
		}
		else
		{
			sql = "select * from u_friend u where u.p_pk = " + p_pk
					+ " and u.relation = " + relation + " and u.exp_share > 0 ";
		}
		List<FriendVO> list = new ArrayList<FriendVO>();
		try
		{
			con = new SqlData();
			ResultSet rs = con.query(sql);
			list = get(rs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return list;
	}

	// 领取经验
	public void getExp(int f_pk)
	{
		String sql = "update u_friend u set u.exp_share = 0 where u.f_pk = "
				+ f_pk;
		try
		{
			con = new SqlData();
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	public List<FriendVO> findLoveDear()
	{
		String sql = "select * from u_friend u where u.relation = 2 and u.love_dear >0";
		List<FriendVO> list = new ArrayList<FriendVO>();
		try
		{
			con = new SqlData();
			ResultSet rs = con.query(sql);
			list = get(rs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return list;
	}

	// 每十分钟减少1点爱情甜蜜度
	public void delLoveDear(int f_pk)
	{
		String sql = "update u_friend u set u.love_dear = u.love_dear -1 where u.f_pk = "
				+ f_pk;
		try
		{
			con = new SqlData();
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	public void addLoveDear(int p_pk, int fd_pk, int love_dear, String fd_name,
			String f_name)
	{
		if (love_dear > 0)
		{
			String sql = "update u_friend u set u.love_dear = u.love_dear + "
					+ love_dear + " where u.p_pk = " + p_pk + " and u.fd_pk = "
					+ fd_pk;
			String sql1 = "update u_friend u set u.love_dear = u.love_dear + "
					+ love_dear + " where u.p_pk = " + fd_pk
					+ " and u.fd_pk = " + p_pk;
			// 统计需要
			new RankService().updateDear(p_pk, love_dear, fd_name);
			// 统计需要
			new RankService().updateDear(fd_pk, love_dear, f_name);
			try
			{
				con = new SqlData();
				con.update(sql);
				con.update(sql1);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				con.close();
			}
		}
	}

	public int isFuQi(int p_pk, String fp_pks, int relation)
	{
		int i = 0;
		if (fp_pks != null && !"".equals(fp_pks.trim()))
		{
			String sql = "select count(*) as coo from u_friend u where u.relation = "
					+ relation
					+ " and u.p_pk = "
					+ p_pk
					+ " and u.fd_pk in ("
					+ ((fp_pks.lastIndexOf(",") + 1 == fp_pks.length()) ? fp_pks
							.substring(0, fp_pks.lastIndexOf(","))
							: fp_pks) + ")";
			try
			{
				con = new SqlData();
				ResultSet rs = con.query(sql);
				while (rs.next())
				{
					i = rs.getInt("coo");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				con.close();
			}
		}
		return i;
	}

	public int findLove_dear(int p_pk)
	{
		String sql = "select u.love_dear from u_friend u where u.p_pk = "
				+ p_pk + " and u.relation = 2";
		int i = 0;
		try
		{
			con = new SqlData();
			ResultSet rs = con.query(sql);
			while (rs.next())
			{
				i = rs.getInt("love_dear");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return i;
	}

	public FriendVO findById(int id)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_friend u where u.f_pk = " + id;
			ResultSet rs = con.query(sql);
			FriendVO vo = null;
			if (rs.next())
			{
				vo = new FriendVO();
				vo.setFPk(rs.getInt("f_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setFdPk(rs.getInt("fd_pk"));
				vo.setFdName(rs.getString("fd_name"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setDear(rs.getInt("dear"));
				vo.setRelation(rs.getInt("relation"));
				vo.setExpShare(rs.getInt("exp_share"));
				vo.setLove_dear(rs.getInt("love_dear"));
			}
			return vo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return null;
	}

	// 增加亲密度
	public void addLove(Object p_pk, Object fd_pk, int addlovel)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_friend u set u.dear = u.dear + " + addlovel
					+ " where u.p_pk = " + p_pk + " and u.fd_pk = " + fd_pk;
			String sql1 = "update u_friend u set u.dear = u.dear + " + addlovel
					+ " where u.p_pk = " + fd_pk + " and u.fd_pk = " + p_pk;
			con.update(sql);
			con.update(sql1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	// 查看可以获取经验的好友列表
	public List<FriendVO> jieyi(Object p_pk, int relation)
	{

		List<FriendVO> list = new ArrayList<FriendVO>();
		if (p_pk == null)
		{
			return list;
		}
		String sql = "";
		sql = "select * from u_friend u where u.p_pk = " + p_pk
				+ " and u.relation = " + relation;
		try
		{
			con = new SqlData();
			ResultSet rs = con.query(sql);
			list = get(rs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return list;
	}

	// 排行榜
	public List<FriendVO> paihang(int relation)
	{
		List<FriendVO> list = new ArrayList<FriendVO>();
		String sql = "";
		if (relation == 1)
		{
			sql = "select * from u_friend u where  u.relation = 1 order by dear desc,tim asc limit 20";
		}
		else
		{
			sql = "select * from u_friend u where u.relation = 2 order by love_dear desc,tim asc limit 20";
		}
		try
		{
			con = new SqlData();
			ResultSet rs = con.query(sql);
			list = get(rs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return list;
	}

	// 自己的排名
	public int getOwn(int p_pk, int relation)
	{
		String sq = "";
		String sql = "";
		String sql1 = "";
		if (relation == 1)
		{
			sq = "select max(u.dear) from u_friend u where u.relation = 1 and u.p_pk = "
					+ p_pk;
			sql = "select count(*) from u_friend u where  u.relation = 1 and u.dear > (select max(a.dear) from u_friend a where a.p_pk ="
					+ p_pk + " )";
			sql1 = "select count(*) from u_friend u where u.relation = 1 and "
					+ " u.dear = (select a.dear from u_friend a where a.p_pk = "
					+ p_pk
					+ " and a.relation = 1 order by a.dear desc limit 1 )  "
					+ " and u.tim <=  (select b.tim from u_friend b where b.p_pk = "
					+ p_pk
					+ " and b.relation = 1 order by b.dear desc limit 1 )";
		}
		else
		{
			sq = "select max(u.love_dear) from u_friend u where u.relation = 2 and u.p_pk = "
					+ p_pk;
			sql = "select count(*) from u_friend u where  u.relation = 2 and u.love_dear > (select max(a.love_dear) from u_friend a where a.p_pk ="
					+ p_pk + " )";
			sql1 = "select count(*) from u_friend u where u.relation = 2 and "
					+ " u.love_dear = (select a.love_dear from u_friend a where a.p_pk = "
					+ p_pk
					+ " and a.relation = 2 order by a.love_dear desc limit 1 )  "
					+ " and u.tim <=  (select b.tim from u_friend b where b.p_pk = "
					+ p_pk
					+ " and b.relation = 2 order by b.love_dear desc limit 1 )";

		}
		int i = 0;
		try
		{
			con = new SqlData();
			int j1 = 0;
			ResultSet r = con.query(sq);
			while (r.next())
			{
				j1 = r.getInt(1);
			}
			if (j1 != 0)
			{
				ResultSet rs = con.query(sql);
				while (rs.next())
				{
					i = rs.getInt(1);
				}
				ResultSet rs1 = con.query(sql1);
				while (rs1.next())
				{
					int j = rs1.getInt(1);
					i += j == 0 ? i == 0 ? 0 : 1 : j;
				}
				rs.close();
				rs1.close();
			}
			r.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return i;
	}

	/** ********玩家删除角色的时候删除所有关联的好友信息****** */
	public void removeFriendInfo(int ppk)
	{
		String sql ="delete from u_friend where p_pk="+ppk+" or fd_pk="+ppk+"";
		try
		{
			con = new SqlData();
			con.update(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}
}
