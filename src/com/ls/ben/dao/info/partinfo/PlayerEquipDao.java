package com.ls.ben.dao.info.partinfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.Auctiontype;
import com.ls.pub.constant.Equip;
import com.ls.pub.db.DBConnection;

/**
 * 功能:u_part_equip操作
 * @author 刘帅 9:18:56 AM
 */
public class PlayerEquipDao extends BasicDaoSupport<PlayerEquipVO>
{
	public PlayerEquipDao()
	{
		super("u_part_equip", DBConnection.GAME_USER_DB);
	}

	
	/**
	 * 修改装备属性值
	 */
	public void updateEquipAttri(PlayerEquipVO equip )
	{
		if( equip==null )
		{
			return;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("update u_part_equip  set ");
		sql.append("wxAttrisStr=?,");
		sql.append("w_zb_grade=?,");
		sql.append("w_hp=?,w_mp=?,inlayPropStr=?,left_effect_hole_num=?,cur_endure=?");
		sql.append(",w_Bonding=?,rank_key=?,protectEndTime=?,max_endure=?,appendAttriDes=?");
		sql.append(" where pw_pk=?");
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, equip.getWxAttrisStr());
			
			ps.setInt(2, equip.getWZbGrade());
			
			ps.setInt(3, equip.getWHp());
			ps.setInt(4, equip.getWMp());
			
			ps.setString(5, equip.getInlayPropStr());
			ps.setInt(6, equip.getLeftEffectHoleNum());
			
			ps.setInt(7, equip.getCurEndure());
			
			ps.setInt(8, equip.getWBonding());
			
			ps.setInt(9, equip.getRankKey());
			Timestamp protect_time = null;
			if( equip.getProtectEndTime()!=null )
			{
				protect_time = new Timestamp(equip.getProtectEndTime().getTime());
			}
			ps.setTimestamp(10, protect_time);
			
			ps.setInt(11, equip.getMaxEndure());
			ps.setString(12, equip.getAppendAttriDes());
			
			ps.setInt(13, equip.getPwPk());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.toString();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	
	/**
	 * 添加装备
	 * @param equip
	 */
	public int add(PlayerEquipVO equip)
	{
		int key = 0;
		if (equip == null)
		{
			return key;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO u_part_equip values (null,?,?,?,?,?,?");
		sql.append(",?,?,?,?,?,?");
		sql.append(",?,?");
		sql.append(",?,?,?,?,now(),?,0,0,?");
		sql.append(",null,?,null)");

		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, equip.getPPk());
			ps.setInt(2, equip.getEquipId());
			ps.setInt(3, equip.getEquipType() );
			ps.setString(4, equip.getWName());
			ps.setInt(5, equip.getCurEndure());
			ps.setInt(6, equip.getWBonding());
			
			ps.setInt(7, equip.getWFyXiao());
			ps.setInt(8, equip.getWFyDa());
			ps.setInt(9, equip.getWGjXiao());
			ps.setInt(10, equip.getWGjDa());
			
			ps.setInt(11, equip.getWHp());
			ps.setInt(12, equip.getWMp());
			
			ps.setString(13, equip.getWxAttrisStr());
			ps.setString(14, equip.getInlayPropStr());
			
			ps.setInt(15, equip.getWType());
			ps.setInt(16, equip.getWQuality());
			ps.setInt(17, equip.getWZbGrade());
			ps.setInt(18, equip.getWLevel());
			
			ps.setInt(19, equip.getHoleNum());
			
			ps.setInt(20, equip.getRankKey());
			
			ps.setInt(21, equip.getMaxEndure());
			
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys(); 
			if ( rs.next() ) 
			{ 
    			key = rs.getInt(1); 
    			equip.setPwPk(key);
			}
			rs.close();
			ps.close();
		}catch (SQLException e1)
		{
			logger.debug(e1.toString());
		}
		catch (Exception e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return key;
	}

	/**
	 * 修改装备所穿的位置
	 */
	public int updatePosition(int pw_pk, int position)
	{
		int deleteValue = 0;
		String sql = "update u_part_equip set w_type='"+ position + "'  where pw_pk='" + pw_pk + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			deleteValue = stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (Exception e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return deleteValue;
	}
	

	/**
	 * 修改装备的持有人
	 * @param pw_pk 		装备id
	 * @param pPk           新的持有人
	 * @return
	 */
	public int updateOwner(int pw_pk, int pPk )
	{
		int deleteValue = 0;
		String sql = "update u_part_equip set p_pk='" + pPk + "',w_type='0' where pw_pk='" + pw_pk+ "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			deleteValue = stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (Exception e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return deleteValue;
	}
	
	/**
	 * 清空装备
	 */
	public void clear(int p_pk )
	{
		String update_sql = "delete from u_part_equip where p_pk = "+p_pk;
		super.executeUpdateSql(update_sql);
	}
	

	/**
	 * 判断是否有该装备
	 * @param pw_pk        
	 * @return
	 */
	public boolean isHaveById(int pw_pk)
	{
		boolean result = false;
		String sql = "SELECT pw_pk from u_part_equip where pw_pk='" + pw_pk + "' ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				result = true;
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
		return result;
	}
	
	/**
	 * 判断某人是否有该装备(无品质的)
	 * @param p_pk			角色id
	 * @param equip_id      装备id
	 * @return
	 */
	public boolean isHaveByEquipId(int p_pk,int equip_id)
	{
		boolean result = false;
		String sql = "SELECT pw_pk from u_part_equip where p_pk='" + p_pk + "' and w_type=0 and w_quality=0";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				result = true;
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
		return result;
	}
	
	/**
	 * 根据装备id获和装备类型得装备的数量
	 * @param p_pk 玩家id
	 * @param equip_id 装备id
	 * @param equip_type 装备类型
	 */
	public int getEquipNumByEquipId(int p_pk, int equip_id, int equip_type)
	{
		int equip_num = -1;

		String sql = "SELECT count(1) as equip_num from u_part_equip where p_pk='" + p_pk + "' and w_type=0 and w_quality=0 and equip_type="+equip_type;

		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				equip_num = rs.getInt("equip_num");
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
		return equip_num;
	}
	
	/**
	 * 删除指定装备，如果有同样的装备则随机删除一个(无品质的)
	 * @param p_pk
	 * @param equip_id
	 */
	public void deleteByEquip(int p_pk, int equip_id)
	{
		String sql = "delete from u_part_equip where p_pk = " + p_pk
				+ " and equip_id=" + equip_id 
				+ " and w_type =0 and w_quality=0 limit 1";
		logger.debug(sql);
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
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * 根据id删除装备
	 * @param wp_pk
	 */
	public int deleteByID(int p_pk, int pw_pk)
	{
		int deleteValue = 0;
		String sql = "delete from u_part_equip where pw_pk=" + pw_pk
				+ " and p_pk = " + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			deleteValue = stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (Exception e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return deleteValue;
	}

	/**
	 * 装备排行
	 * @param equip_type_list     装备类型，可多个
	 * @return
	 */
	public List<PlayerEquipVO> getRankList( int...equip_type_list )
	{
		StringBuffer sb = new StringBuffer();
		sb.append("0");
		for(Integer equip_type:equip_type_list )
		{
			sb.append(",").append(equip_type);
		}
		String sql = "SELECT * FROM u_part_equip where equip_type in ("+sb.toString()+") and w_level!=100 order by rank_key desc,hole_num desc,create_time limit 10";
		return this.loadList(sql);
	}
	
	/**
	 * 得到可以升级品质的装备分页列表
	 */
	public QueryPage getPageQualityList(int p_pk,int page_no)
	{
		String condition_sql = "where p_pk="+p_pk +" and w_type=0  and cur_endure>10 and w_quality<"+Equip.Q_JIPIN+" and w_zb_grade=0 and effect_hole_num=0";
		return this.loadPageList(condition_sql, page_no);
	}
	
	/**
	 * 分页得到可以保护的装备
	 */
	public QueryPage getPageProtectList(int p_pk,int page_no)
	{
		String condition_sql = "where p_pk="+p_pk +" and w_type=0  and cur_endure>10 and protectEndTime is null";
		return this.loadPageList(condition_sql, page_no);
	}
	/**
	 * 分页得到可以解除绑定的装备
	 */
	public QueryPage getPageBindList(int p_pk,int page_no)
	{
		String condition_sql = "where p_pk="+p_pk +" and w_type=0 and w_Bonding>0";
		return this.loadPageList(condition_sql, page_no);
	}
	
	/**
	 * 分页得到损坏的装备（耐久为小于10）
	 */
	public QueryPage getPageBadList(int p_pk,int page_no)
	{
		String condition_sql = "where p_pk="+p_pk +" and w_type=0 and cur_endure<10";
		return this.loadPageList(condition_sql, page_no);
	}
	/**
	 * 分页得到可以镶嵌的装备
	 * 在包裹里的没有打过孔的，有品质的装备
	 */
	public QueryPage getPageInlayList(int p_pk,int page_no)
	{
		String condition_sql = "where p_pk="+p_pk +" and w_type=0 and cur_endure>10 and left_effect_hole_num>0 ";
		return this.loadPageList(condition_sql, page_no);
	}
	/**
	 * 分页得到可以打孔的装备
	 * 在包裹里的没有打过孔的，有品质的装备
	 */
	public QueryPage getPagePunchList(int p_pk,int page_no)
	{
		String condition_sql = "where p_pk="+p_pk +" and w_type=0 and cur_endure>10 and w_quality>0 and hole_num>effect_hole_num";
		return this.loadPageList(condition_sql, page_no);
	}
	/**
	 * 分页得到可以升级的装备
	 * 在包裹里的耐久大于0，有品质的装备
	 */
	public QueryPage getPageUpgradeList(int p_pk,int page_no)
	{
		String condition_sql = " where p_pk="+p_pk +" and w_type=0 and cur_endure>=10 and w_zb_grade<13";
		return this.loadPageList(condition_sql, page_no);
	}
	
	/**
	 * 分页得到可以转换五行的装备
	 * 在包裹里的耐久大于0，有品质的装备
	 */
	public QueryPage getPageChangeWXList(int p_pk,int page_no)
	{
		String condition_sql = " where p_pk="+p_pk +" and w_type=0 and w_zb_grade=0 and cur_endure>=10 and w_quality in (1,2)  and effect_hole_num=0";
		return this.loadPageList(condition_sql, page_no);
	}
	
	/**
	 * 分页得到指定类型的可以穿的装备
	 */
	public QueryPage getPageByTypeOnWrap(int p_pk,int equip_type,int page_no)
	{
		String condition_sql = " where p_pk="+p_pk +" and w_type=0 and equip_type="+equip_type;
		return this.loadPageList(condition_sql, page_no);
	}
	
	/**
	 * 得到包裹里装备的数量
	 */
	public int getNumOnWrap( int pPk )
	{
		int num_on_wrap = 0;
		String sql = "SELECT count(*) num from u_part_equip where p_pk = " + pPk+" and w_type="+Equip.ON_WRAP;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num_on_wrap = rs.getInt(1);
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
        return num_on_wrap;
	}
	
	/**
	 * 分页得道玩家包裹里的装备.分类为武器，防具，法宝 拍卖场用
	 */
	public QueryPage getPageEquipOnWrap(int p_pk,int equip_type,int page_no)
	{
		String condition_sql="";
		if(equip_type==Auctiontype.ARM)
		{
			condition_sql="where p_pk="+p_pk+" and equip_type=1 and w_type=0";
		}
		if(equip_type==Auctiontype.ARMOR)
		{
			condition_sql="where p_pk="+p_pk+" and equip_type in(2,3,4,5) and w_type=0";
		}
		if(equip_type==Auctiontype.JEWELRY)
		{
			condition_sql="where p_pk="+p_pk+" and equip_type=6 and w_type=0";
		}
		return this.loadPageList(condition_sql, page_no);
	}
	
	/**
	 * 分页:得到玩家包裹里的可以交易的（非破损的）装备
	 */
	public QueryPage getPageSaleEquipOnWrap(int p_pk,int page_no,int postion)
	{
		String condition_sql = "where p_pk="+p_pk +" and w_type="+postion+" and cur_endure>10";
		return this.loadPageList(condition_sql, page_no);
	}
	
	/**
	 * 得到指定装备所在位置的分页类表
	 */
	public QueryPage getPageByPosition(int p_pk,int page_no,int postion)
	{
		String condition_sql = "where p_pk="+p_pk +" and w_type="+postion;
		return this.loadPageList(condition_sql, page_no);
	}
	
	/**
	 * 得到玩家身上的装备
	 * @param p_pk
	 * @return
	 */
	public List<PlayerEquipVO> getEquipListOnBody(int p_pk)
	{
		String sql = "SELECT * FROM u_part_equip where  p_pk = " + p_pk+ " and w_type>0";
		return this.loadList(sql);
	}

	


	/**
	 * 修改装备的有效孔数
	 */
	public int addEffectHoleNum(int pw_pk)
	{
		int deleteValue = 0;
		String sql = "update u_part_equip set effect_hole_num=effect_hole_num+1,left_effect_hole_num=left_effect_hole_num+1 where pw_pk='" + pw_pk + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			deleteValue = stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (Exception e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return deleteValue;
	}
	
	/**
	 * 得到玩家装备详细信息
	 */
	public PlayerEquipVO getByID(int pw_pk)
	{
		PlayerEquipVO vo = null;
		String sql = "SELECT * FROM u_part_equip where pw_pk='" + pw_pk + "' ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = this.loadData(rs);
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
	 * 得到装备的名字
	 */
	public String getNameById(int pw_pk)
	{
		String equip_name = "";
		String sql = "SELECT w_name from u_part_equip where pw_pk='" + pw_pk + "' ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				equip_name = rs.getString("w_name");
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
		return equip_name;
	}


	/**
	 * 获得个人未被保护的装备ID
	 * @param pPk
	 * @return
	 */
	public List<Integer> getNoProtectEquipId(int pPk)
	{
		String sql = "SELECT pw_pk from u_part_equip where p_pk="+pPk+" and w_type>=0 and ( protectEndTime is null or protectEndTime<now())";
		return super.getKeyListBySql(sql);
	}


	/**
	 * 更新装备的耐久
	 * @param pw_pk			
	 * @param cur_endure    当前耐久
	 */
	public void updateCurEndure(int pw_pk,int cur_endure)
	{
		String sql = "update u_part_equip set cur_endure = "
				+ cur_endure + " where pw_pk = " + pw_pk;
		logger.debug(sql);
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
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * 更新装备的耐久
	 * @param pw_pk			
	 * @param cur_grade    当前等级
	 */
	public void updateEquipGrade(int pw_pk,int cur_grade)
	{
		String sql = "update u_part_equip set w_zb_grade = "
			+ cur_grade + " where pw_pk = " + pw_pk;
		logger.debug(sql);
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
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}


	/**
	 * 根据装备id获得装备的数量
	 */
	public int getEquipNum(int p_pk, int equip_id)
	{
		int equip_num = -1;

		String sql = "SELECT sum(1) as equip_num from u_part_equip where p_pk='"+ p_pk+ "' and w_type = 0 and equip_id='"+ equip_id+ "'";

		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				equip_num = rs.getInt("equip_num");
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
		return equip_num;
	}
	
	
	/**
	 * 根据sql语句得到
	 * @param sql
	 * @return
	 */
	private List<PlayerEquipVO> loadList( String sql )
	{
		List<PlayerEquipVO> list = new ArrayList<PlayerEquipVO>();
		PlayerEquipVO equip = null;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				equip = this.loadData(rs);
				list.add(equip);
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
	
	protected PlayerEquipVO loadData( ResultSet rs ) throws SQLException
	{
		PlayerEquipVO equip = new PlayerEquipVO();
		equip.setPwPk(rs.getInt("pw_pk"));
		equip.setPPk(rs.getInt("p_pk"));
		equip.setEquipId(rs.getInt("equip_id"));
		equip.setEquipType(rs.getInt("equip_type"));

		equip.setWName(rs.getString("w_name"));
		equip.setCurEndure(rs.getInt("cur_endure"));
		equip.setWBonding(rs.getInt("w_Bonding"));

		equip.setWFyXiao(rs.getInt("w_fy_xiao"));
		equip.setWFyDa(rs.getInt("w_fy_da"));
		equip.setWGjXiao(rs.getInt("w_gj_xiao"));
		equip.setWGjDa(rs.getInt("w_gj_da"));
		
		equip.setWHp(rs.getInt("w_hp"));
		equip.setWMp(rs.getInt("w_mp"));

		equip.setWxAttrisStr(rs.getString("wxAttrisStr"));
		equip.setInlayPropStr(rs.getString("inlayPropStr"));

		equip.setWType(rs.getInt("w_type"));
		equip.setWQuality(rs.getInt("w_quality"));
		equip.setWZbGrade(rs.getInt("w_zb_grade"));
		equip.setCreateTime(rs.getString("create_time"));
		
		equip.setHoleNum(rs.getInt("hole_num"));
		equip.setEffectHoleNum(rs.getInt("effect_hole_num"));
		equip.setLeftEffectHoleNum(rs.getInt("left_effect_hole_num"));
		equip.setProtectEndTime(rs.getDate("protectEndTime"));
		
		equip.setMaxEndure(rs.getInt("max_endure"));
		equip.setAppendAttriDes(rs.getString("appendAttriDes"));
		return equip;
	}
}
