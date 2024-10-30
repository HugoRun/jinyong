package com.ls.ben.dao.info.partinfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.ben.vo.goods.GoodsControlVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.BondingType;
import com.ls.pub.constant.PropType;
import com.ls.pub.db.DBConnection;
import com.ls.web.service.player.RoleService;
import com.pm.service.systemInfo.SystemInfoService;

/**
 * ����:u_propgroup_info�����
 * 
 * @author ��˧ 1:52:47 PM
 */
public class PlayerPropGroupDao extends BasicDaoSupport<PlayerPropGroupVO>
{
	private Logger log = Logger.getLogger(this.getClass());
	
	public PlayerPropGroupDao()
	{
		super("u_propgroup_info", DBConnection.GAME_USER_DB);
	}
	
	/**
	 * ���ĳ�˵ĵ���
	 */
	public void clear(int p_pk )
	{
		String update_sql = "delete from u_propgroup_info where p_pk = "+p_pk;
		super.executeUpdateSql(update_sql);
	}

	/**
	 * �õ���Ұ������prop_type���͵Ĳ��ظ��ĵ��ߣ���ս������ʹ�õĵ���
	 * @param p_pk
	 * @return
	 */
	public List<PlayerPropGroupVO> getDisdinctAndBattleUsableProps(int p_pk,
			int pg_type)
	{
		List<PlayerPropGroupVO> props = new ArrayList<PlayerPropGroupVO>();
		PlayerPropGroupVO propGroup = null;
		String sql = "select * from u_propgroup_info where p_pk="
				+ p_pk
				+ " and pg_type="
				+ pg_type
				+ " and prop_use_control=0 and prop_type != 41 group by prop_id";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				propGroup = new PlayerPropGroupVO();
				propGroup.setPgPk(rs.getInt("pg_pk"));
				propGroup.setPPk(p_pk);
				propGroup.setPropId(rs.getInt("prop_id"));
				propGroup.setPropType(rs.getInt("prop_type"));
				propGroup.setPgType(pg_type);
				props.add(propGroup);
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
		return props;
	}
	
	/**
	 * �õ������������ռ�Ŀռ�
	 * @param p_pk
	 * @return
	 */
	public int getNumOnWrap(int pPk)
	{
		int num_on_wrap = 0;
		String sql = "select count(*) from u_propgroup_info where p_pk="+ pPk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				num_on_wrap = rs.getInt(1);
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
		return num_on_wrap;
	}
	/**
	 * ���ݵ������͵õ����ظ��ĵ����б�
	 * @param p_pk
	 * @param prop_type
	 * @return
	 */
	public List<PlayerPropGroupVO> getListByPropType(int p_pk,int prop_type)
	{
		List<PlayerPropGroupVO> props = new ArrayList<PlayerPropGroupVO>();
		PlayerPropGroupVO propGroup = null;
		String sql = "select prop_id,sum(prop_num) from u_propgroup_info where p_pk="
				+ p_pk
				+ " and prop_type="
				+ prop_type
				+ " group by prop_id";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				propGroup = new PlayerPropGroupVO();
				propGroup.setPropId(rs.getInt(1));
				propGroup.setPropNum(rs.getInt(2));
				props.add(propGroup);
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
		return props;
	}

	/**
	 * �����������ӵ���
	 */
	public int addPropGroup(PlayerPropGroupVO propGroup)
	{
		if (propGroup == null)
		{
			logger.debug("propGroup��");
			return -1;
		}
		int result = -1;
		String sql = "insert into u_propgroup_info (p_pk,pg_type,prop_id,prop_type,prop_bonding,prop_protect,prop_isReconfirm,prop_use_control,prop_num,create_time) values (?,?,?,?,?,?,?,?,?,now())";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, propGroup.getPPk());
			ps.setInt(i++, propGroup.getPgType());
			ps.setInt(i++, propGroup.getPropId());
			ps.setInt(i++, propGroup.getPropType());

			ps.setInt(i++, propGroup.getPropBonding());
			ps.setInt(i++, propGroup.getPropProtect());
			ps.setInt(i++, propGroup.getPropIsReconfirm());

			ps.setInt(i++, propGroup.getPropUseControl());

			ps.setInt(i++, propGroup.getPropNum());

			ps.execute();
			ps.close();
		}
		catch (SQLException e)
		{
			logger.debug(e.toString());

		}
		finally
		{
			// �������ؾ���ͼ��Ϣ
			if (propGroup.getPropType() == PropType.MIJING_MAP)
			{
				RoleEntity re = new RoleService().getRoleInfoById(propGroup
						.getPPk()
						+ "");
				if (re != null)
				{
					BasicInfo bi = re.getBasicInfo();
					new SystemInfoService().insertSystemInfoBySystem(bi
							.getSceneInfo().getSceneName()
							+ "��"
							+ bi.getName()
							+ "�����"
							+ propGroup.getPropName() + "��");
				}
			}
			dbConn.closeConn();
		}
		return result;
	}

	/**
	 * �����ؾ���ͼ��ӵ����
	 */
	public int updateMiJingOwner(int last_owner, int new_owner)
	{
		String sql = "update u_propgroup_info u set u.p_pk = " + new_owner
				+ " where u.p_pk = " + last_owner + " and u.prop_type = "
				+ PropType.MIJING_MAP + " limit 1";
		return this.executeUpdateSql(sql);
	}

	/**
	 * ɾ�����ϵ��ؾ���ͼ
	 */
	public int removeMiJing(int ppk)
	{
		String sql = "delete from u_propgroup_info where p_pk = " + ppk
				+ " and prop_type = " + PropType.MIJING_MAP;
		return this.executeUpdateSql(sql);
	}

	/**
	 * �鿴�����Ƿ�ӵ���ؾ���ͼ
	 */
	public boolean haveMiJing(int ppk)
	{
		String sql = "select count(*) nu from u_propgroup_info u where u.prop_type = "
				+ PropType.MIJING_MAP
				+ " and u.p_pk = "
				+ ppk
				+ " and u.prop_num >0";
		int result = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
				result = rs.getInt("nu");
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result > 0 ? true : false;
		}
	}

	/**
	 * ���µ���������
	 * 
	 * @param pg_pk
	 * @param prop_num
	 *            �µĵ�������
	 * @return
	 */
	public int updatePropGroupUpNum(int p_pk, int prop_id, int accumulate_num)
	{
		String sql = "update u_propgroup_info set prop_num=" + accumulate_num
				+ " where p_pk=" + p_pk + " and prop_id=" + prop_id;
		return this.executeUpdateSql(sql);
	}

	/**
	 * ������ҵ�ָ�����ߵ�һ����ߵ�����
	 * 
	 * @param p_pk
	 * @param prop_id
	 *            ����id
	 * @param prop_num
	 * @return
	 */
	public int updateOneGroupPropNum(int p_pk, int prop_id, int prop_num)
	{
		String sql = "update u_propgroup_info set prop_num=" + prop_num
				+ " where p_pk=" + p_pk + " and prop_id=" + prop_id
				+ " limit 1";
		return this.executeUpdateSql(sql);
	}

	/**
	 * ���µ���������
	 * 
	 * @param pg_pk
	 * @param prop_num
	 *            �µĵ�������
	 * @return
	 */
	public int updatePropGroupNum(int pg_pk, int prop_num)
	{
		String sql = "update u_propgroup_info set prop_num=" + prop_num
				+ " where pg_pk=" + pg_pk + " limit 1";
		return this.executeUpdateSql(sql);
	}

	/**
	 * ���µ��߱��Ϊprop_id�ĵ���������
	 * 
	 * @param pg_pk
	 * @param prop_num
	 *            �µĵ�������
	 * @return
	 */
	public int updatePropGroupNumByPropID(int p_pk, int prop_id, int prop_num)
	{
		String sql = "update u_propgroup_info set prop_num=" + prop_num
				+ " where p_pk=" + p_pk + " and prop_id =" + prop_id
				+ " limit 1";
		return this.executeUpdateSql(sql);
	}

	/**
	 * ����������Ϊ0ʱ��ɾ��һ�����
	 */
	public int deletePropGroup(int pg_pk)
	{
		String sql = "delete from  u_propgroup_info where pg_pk=" + pg_pk;
		logger.debug(sql);
		return this.executeUpdateSql(sql);
	}

	/**
	 * ɾ��delete_group_num�飬����Ϊprop_num�ĵ��ߣ�
	 */
	public int deletePropGroup(int p_pk, int prop_id, int delete_group_num)
	{
		String sql = "delete from  u_propgroup_info where  p_pk=" + p_pk
				+ " and prop_id=" + prop_id + " limit " + delete_group_num;
		logger.debug(sql);
		return this.executeUpdateSql(sql);
	}

	/**
	 * �õ�ĳһ�ֵ��ߵ�����
	 */
	public int getPropNumByByPropID(int p_pk, int prop_id)
	{
		int prop_num = 0;
		String sql = "select sum(prop_num) as prop_sum from u_propgroup_info where p_pk="
				+ p_pk + " and prop_id=" + prop_id + "";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				prop_num = rs.getInt("prop_sum");
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
		return prop_num;
	}

	private List<PlayerPropGroupVO> get(ResultSet rs) throws SQLException
	{
		List<PlayerPropGroupVO> list = new ArrayList<PlayerPropGroupVO>();
		if (rs != null)
		{
			while (rs.next())
			{
				PlayerPropGroupVO propGroup = new PlayerPropGroupVO();
				propGroup.setPgPk(rs.getInt("pg_pk"));
				propGroup.setPPk(rs.getInt("p_pk"));
				propGroup.setPropId(rs.getInt("prop_id"));
				propGroup.setPropType(rs.getInt("prop_type"));
				propGroup.setPropNum(rs.getInt("prop_num"));
				propGroup.setPgType(rs.getInt("pg_type"));
				propGroup.setCreateTime(rs.getDate("create_time"));
				propGroup.setPropBonding(rs.getInt("prop_bonding"));
				propGroup.setPropIsReconfirm(rs.getInt("prop_isReconfirm"));
				propGroup.setPropUseControl(rs.getInt("prop_use_control"));
				list.add(propGroup);
			}
			rs.close();
		}

		return list;
	}

	public PlayerPropGroupVO findById(int id)
	{
		String sql = "select * from u_propgroup_info u where u.pg_pk = " + id;
		List<PlayerPropGroupVO> list = new ArrayList<PlayerPropGroupVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = get(rs);
			stmt.close();
		}
		catch (SQLException e)
		{

			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
			if (list != null && list.size() > 0)
			{
				return list.get(0);
			}
			return null;
		}
	}

	public void jieYiDelProp(String ids, String caozuo)
	{
		String sql = "update u_propgroup_info u set u.prop_num = u.prop_num "
				+ caozuo + " where u.pg_pk in (" + ids + ")";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
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
	}

	public List<PlayerPropGroupVO> findByProp_IDs(int p_pk, String ids)
	{
		if (null != ids && !"".equals(ids.trim()))
		{
			List<PlayerPropGroupVO> list = new ArrayList<PlayerPropGroupVO>();
			String sql = "select * from u_propgroup_info u where u.p_pk="
					+ p_pk + " and u.prop_id in (" + ids
					+ ") and u.prop_num > 0";
			log.info("��ѯ����������Ʒsql �� " + sql);
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try
			{
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				list = get(rs);
				stmt.close();
			}
			catch (SQLException e)
			{

				logger.debug(e.toString());
			}
			finally
			{
				dbConn.closeConn();
				return list;
			}
		}
		else
			try
			{
				throw new Exception();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				return null;
			}

	}

	/**
	 * ����prop_id�õ��������ٵ�һ��
	 */
	public PlayerPropGroupVO getPropGroupByPropID(int p_pk, int prop_id)
	{
		PlayerPropGroupVO propGroup = null;
		String sql = "select * from u_propgroup_info where p_pk=" + p_pk
				+ " and prop_id=" + prop_id + " order by prop_num limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				propGroup = this.loadData(rs);
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
		return propGroup;

	}

	/**
	 * ����prop_id�õ����� �Ƿ�ӵ��ĳ�ֵ���
	 */
	public boolean getUserHasProp(int p_pk, int prop_id)
	{
		boolean flag = false;
		String sql = "select pg_pk from u_propgroup_info where p_pk=" + p_pk
				+ " and prop_id=" + prop_id + " limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				flag = true;
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
		return flag;

	}

	/**
	 * ����prop_id�õ����� �Ƿ�ӵ��ĳ�ֵ���
	 */
	public int getPgpkByProp(int p_pk, int prop_id)
	{
		int pg_pk = -1;
		String sql = "select pg_pk from u_propgroup_info where p_pk=" + p_pk
				+ " and prop_id=" + prop_id + " limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				pg_pk = rs.getInt("pg_pk");
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
		return pg_pk;

	}

	/**
	 * ���ĳ�������ж�����
	 */
	public int getPropGroupNumByPropID(int p_pk, int prop_id)
	{
		// PlayerPropGroupVO propGroup = null;
		String sql = "select count(1) as prop_group from u_propgroup_info where p_pk="
				+ p_pk + " and prop_id=" + prop_id + "";
		logger.debug(sql);
		int prop_group = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				prop_group = rs.getInt("prop_group");
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
		return prop_group;

	}

	/**
	 * ����prop_id�õ���������һ��
	 */
	public PlayerPropGroupVO getPropGroupByTime(int p_pk, int prop_id)
	{
		PlayerPropGroupVO propGroup = null;
		String sql = "select * from u_propgroup_info where p_pk=" + p_pk
				+ " and prop_id=" + prop_id
				+ " order by create_time desc limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				propGroup = this.loadData(rs);
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
		return propGroup;

	}

	/**
	 * ����pg_pk�õ���������Ϣ
	 */
	public PlayerPropGroupVO getByPgPk(int pg_pk)
	{
		PlayerPropGroupVO propGroup = null;
		String sql = "select * from u_propgroup_info where pg_pk=" + pg_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				propGroup = this.loadData(rs);
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
		return propGroup;
	}

	/**
	 * ����pg_pk��p_pk�õ���������Ϣ
	 */
	public PlayerPropGroupVO getByPgPk(int pg_pk, int p_pk)
	{
		PlayerPropGroupVO propGroup = null;
		String sql = "select * from u_propgroup_info where pg_pk=" + pg_pk
				+ " and p_pk=" + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				propGroup = this.loadData(rs);
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
		return propGroup;
	}


	/**
	 * ������תΪ��״̬
	 */
	public void updatePropToBonding(int pg_pk)
	{
		String sql = "update u_propgroup_info set prop_bonding = "
				+ BondingType.PICKBOUND + " where pg_pk=" + pg_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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

	}

	/**
	 * �õ���Ұ������pw_type���͵����еĵ���
	 * 
	 * @param p_pk
	 * @param pg_type
	 *            �������߷���
	 * @return
	 */
	public List<PlayerPropGroupVO> getPropsByPpk(int p_pk, int pg_type)
	{
		List<PlayerPropGroupVO> props = new ArrayList<PlayerPropGroupVO>();
		PlayerPropGroupVO propGroup = null;
		String sql = "select * from u_propgroup_info where p_pk=" + p_pk+ " order by prop_id,prop_num";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{

				propGroup = new PlayerPropGroupVO();
				propGroup.setPgPk(rs.getInt("pg_pk"));
				propGroup.setPPk(p_pk);
				propGroup.setPropId(rs.getInt("prop_id"));
				propGroup.setPropNum(rs.getInt("prop_num"));
				propGroup.setPropType(rs.getInt("prop_type"));
				propGroup.setPgType(rs.getInt("pg_type"));

				propGroup = this.loadData(rs);

				props.add(propGroup);
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
		return props;
	}

	/**
	 * �õ���Ұ������pw_type���͵����еĵ���
	 * 
	 * @param p_pk
	 * @param pg_type
	 *            �������߷���
	 * @return
	 */
	public QueryPage getPagePropsByPpk(int p_pk, int pg_type, int page_no)
	{
		String condition_sql = "where p_pk="+ p_pk + " and pg_type=" + pg_type;
		return super.loadPageList(condition_sql, page_no);
	}


	/**
	 * �õ���Ұ������pw_type���͵����еĵ���
	 * 
	 * @param p_pk
	 * @param pg_type
	 *            �������߷���
	 * @return
	 */
	public QueryPage getPagePropsByPpkpet(int p_pk, int pg_type, int page_no)
	{
		QueryPage queryPage = null;

		List<PlayerPropGroupVO> props = new ArrayList<PlayerPropGroupVO>();
		PlayerPropGroupVO propGroup = null;

		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String count_sql, page_sql;
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			// count_sql = "select count(*) from u_propgroup_info where p_pk=" +
			// p_pk + " and pg_type=" + pg_type + " and (prop_type=20 or
			// prop_type=21)";
			count_sql = "select count(*) from u_propgroup_info where p_pk="
					+ p_pk
					+ " and (prop_type=20 or prop_type=21 or prop_type=48)";
			logger.debug("lllllllllll " + count_sql);
			rs = stmt.executeQuery(count_sql);
			if (rs.next())
			{
				count = rs.getInt(1);
			}
			rs.close();

			queryPage = new QueryPage(page_no, count);

			// page_sql = "select * from u_propgroup_info where p_pk=" + p_pk +
			// " and pg_type=" + pg_type + " and (prop_type=20 or prop_type=21)
			// order by prop_id,prop_num " + "limit " +
			// queryPage.getStartOfPage() + "," + queryPage.getPageSize();

			page_sql = "select * from u_propgroup_info where p_pk="
					+ p_pk
					+ " and (prop_type=20 or prop_type=21 or prop_type=48 ) order by prop_id,prop_num "
					+ "limit " + queryPage.getStartOfPage() + ","
					+ queryPage.getPageSize();

			logger.debug(page_sql);

			rs = stmt.executeQuery(page_sql);
			while (rs.next())
			{
				propGroup = new PlayerPropGroupVO();
				propGroup.setPgPk(rs.getInt("pg_pk"));
				propGroup.setPPk(p_pk);
				propGroup.setPropId(rs.getInt("prop_id"));
				propGroup.setPropNum(rs.getInt("prop_num"));
				propGroup.setPropType(rs.getInt("prop_type"));
				propGroup.setPgType(pg_type);
				props.add(propGroup);
			}
			rs.close();
			stmt.close();

			queryPage.setResult(props);

		}
		catch (SQLException e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}

		return queryPage;
	}

	/**
	 * �õ����߿�����Ϣ
	 * 
	 * @param acc_ID
	 * @return
	 */
	public GoodsControlVO getPropControl(int pg_pk)
	{
		GoodsControlVO goodsControl = null;
		String sql = "select prop_protect,prop_bonding,prop_isReconfirm from u_propgroup_info where pg_pk="
				+ pg_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				goodsControl = new GoodsControlVO();
				goodsControl.setId(pg_pk);
				goodsControl.setProtect(rs.getInt("prop_protect"));
				goodsControl.setBonding(rs.getInt("prop_bonding"));
				goodsControl.setIsReconfirmed(rs.getInt("prop_isReconfirm"));
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
		return goodsControl;
	}

	/**
	 * �õ����߿�����Ϣ
	 * 
	 * @param acc_ID
	 * @return
	 */
	public GoodsControlVO getPropControlgoods(int goods)
	{
		GoodsControlVO goodsControl = null;
		String sql = "select prop_bonding from prop where prop_ID=" + goods;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				goodsControl = new GoodsControlVO();
				goodsControl.setBonding(rs.getInt("prop_bonding"));
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
		return goodsControl;
	}

	/**
	 * �õ�δ�������ĵ���id,�����̳���ö�������
	 * 
	 * @param pk
	 * @return
	 */
	public List<Integer> getNoProtectPropId(int pk)
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select pg_pk from u_propgroup_info where p_pk=" + pk
				+ " and prop_protect = 0 and pg_type !=6";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				list.add(rs.getInt("pg_pk"));
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
	 * ������װҩƷ���ܿ�������
	 * 
	 * @param propGroup
	 * @param add_mp
	 */
	public void reduceBoxCure(PlayerPropGroupVO propGroup, int add_mp,
			int sp_type)
	{
		String sql = "update u_special_prop set prop_stock = prop_stock - "
				+ add_mp + " where sp_type= " + sp_type + " and pg_pk = "
				+ propGroup.getPgPk();
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
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
	 * ����������߱�
	 * 
	 * @param p_pk
	 * @param pg_pk
	 * @param propOperate1
	 */
	public void insertSpecial(int p_pk, int pg_pk, String propOperate2,
			int sp_type)
	{
		String sql = "insert into u_special_prop values(null," + p_pk + ","
				+ sp_type + "," + pg_pk + "," + propOperate2 + ",now())";
		logger.debug("����������߱�=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
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
	 * ���������ߵ�ʣ�����
	 * 
	 * @param pgPk
	 * @return
	 */
	public int getSurplus(int pPk, int pgPk, int sp_type)
	{
		int surplus = 0;
		String sql = "select prop_stock from u_special_prop where pg_pk="
				+ pgPk + " and sp_type = " + sp_type + " and p_pk=" + pPk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				surplus = rs.getInt("prop_stock");
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
		return surplus;
	}

	/**
	 * ɾ����������еļ�¼
	 * 
	 * @param pgPk
	 * @param pk
	 */
	public void deletePropBoxCure(int pgPk, int pk)
	{
		String sql = "delete from u_special_prop where p_pk=" + pk
				+ " and pg_pk=" + pgPk;
		String sql2 = "delete from u_special_prop where prop_stock = 0";
		logger.debug("ɾ����������еļ�¼=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql2);
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
	 * ����ppk��pgpk����������ʹ�ñ��
	 * 
	 * @param pPk
	 * @param pgPk
	 * @return
	 */
	public int getSpecialId(int pPk, int pgPk, int sp_type)
	{
		int specialId = 0;
		String sql = "select id from u_special_prop where pg_pk=" + pgPk
				+ " and p_pk=" + pPk + " and sp_type=" + sp_type;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				specialId = rs.getInt("id");
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
		return specialId;
	}

	/**
	 * ������Ʒ���͵õ���Ʒ�ķ�ҳ�б�
	 * @param pPk
	 * @param prop_type
	 * @param page_no
	 * @return
	 */
	public QueryPage getPageListByPropType(int pPk, int prop_type,int page_no)
	{
		String condition_sql = "where p_pk=" + pPk+ " and prop_type = " + prop_type;
		return super.loadPageList(condition_sql, page_no);
	}


	/**
	 * �����Ʒ����ĳ����ߵ�����
	 * @param p_pk
	 * @param prop_type
	 * @return
	 */
	public int getPropNumByPropType(int p_pk, int prop_type)
	{
		int prop_num = 0;
		String sql = "select sum(prop_num) as prop_num from u_propgroup_info where p_pk="
				+ p_pk + " and prop_type=" + prop_type;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				prop_num = rs.getInt("prop_num");
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

		return prop_num;
	}


	/**
	 * ��ô���Ұ�������ö�prop_id����
	 * 
	 * @param pPk
	 * @return
	 */
	public QueryPage getGoldBoxList(int pPk, String prop_id, int page_no)
	{
		return  super.loadPageList("where p_pk="+ pPk + " and prop_id = " + prop_id, page_no);
	}

	/** ���ͻƽ������Ϣ */

	public int sendGoldInfo(int prop_id)
	{
		int type = 0;
		String sql = "select pg_type from u_propgroup_info where prop_id="
				+ prop_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				type = rs.getInt("pg_type");
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
		return type;
	}

	/**
	 * ���ͻƽ�����Ϣ
	 * 
	 * @param pPk
	 * @param grade
	 * @param goodsId
	 * @param goodsName
	 * @param goodsQuality
	 */
	public void sendGoldInfo(int pPk, int grade, int goodsId, String goodsName,
			int goodsQuality)
	{
		String sql = "insert into u_box_info values (null," + pPk + "," + grade
				+ "," + goodsId + ",'" + goodsName + "'," + goodsQuality
				+ ",now())";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
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

	}

	/**
	 * ���ͻƽ����¼��Ϣ
	 * 
	 * @param pPk
	 * @param grade
	 * @param goodsId
	 * @param goodsName
	 * @param goodsQuality
	 */
	public void sendRecordGoldInfo(int pPk, int info_type, String content)
	{
		String sql = "insert into u_box_record values (null," + pPk + ","
				+ info_type + ",'" + content + "',now())";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.execute(sql);
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

	}

	/**
	 * ɾ������
	 */
	public int removeByProp(int ppk, String propId)
	{
		int i = 0;
		try
		{
			if (propId != null && !"".equals(propId.trim()))
			{
				String sql = "delete from u_propgroup_info where p_pk = " + ppk
						+ " and prop_id in (" + propId + ")";
				logger.debug(sql);
				DBConnection dbConn = new DBConnection(
						DBConnection.GAME_USER_DB);
				conn = dbConn.getConn();
				try
				{
					stmt = conn.createStatement();
					i = stmt.executeUpdate(sql);
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
			}
		}
		catch (Exception e)
		{
			logger.info("ɾ��ǧ���ɾ�����ʱ����");
		}
		finally
		{
			return i;
		}
	}

	
	/**
	 * ��ҳ:�õ���Ұ������ָ�����͵ĵ���
	 * @param p_pk
	 * @param prop_type_str   			�����ַ���
	 * @param page_no
	 * @return
	 */
	public QueryPage getPagePropByTypes(int p_pk, String prop_type_str, int page_no)
	{
		String condi_sql = " where p_pk="+ p_pk + " and prop_type in(" + prop_type_str + ") ";
		return this.loadPageList(condi_sql, page_no);
	}
	
	
	protected PlayerPropGroupVO loadData(ResultSet rs) throws SQLException
	{
		PlayerPropGroupVO propGroup = new PlayerPropGroupVO();
		propGroup = new PlayerPropGroupVO();
		propGroup.setPgPk(rs.getInt("pg_pk"));
		propGroup.setPPk(rs.getInt("p_pk"));
		propGroup.setPropId(rs.getInt("prop_id"));
		propGroup.setPropProtect(rs.getInt("prop_protect"));
		propGroup.setPropBonding(rs.getInt("prop_bonding"));
		propGroup.setPropNum(rs.getInt("prop_num"));
		propGroup.setPropType(rs.getInt("prop_type"));
		propGroup.setPgType(rs.getInt("pg_type"));
		return propGroup;
	}
}
