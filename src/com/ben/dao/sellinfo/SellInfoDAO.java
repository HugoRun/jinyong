package com.ben.dao.sellinfo;

import java.sql.ResultSet;
import com.ben.vo.sellinfo.SellInfoVO;
import com.pub.db.mysql.SqlData;

/**
 * @author ��ƾ�
 * 
 * 2:23:55 PM
 */
public class SellInfoDAO
{
	SqlData con;

	/**
	 * ��ӽ�����Ϣ
	 * @param pPk
	 * @param pByPk
	 * @param ssilverMoney
	 * @param sCopperMoney
	 * @param sellMode
	 * @param createTime
	 */
	public void addSelleInfo(String pPk, String pByPk, String ssilverMoney,
			String sCopperMoney, int sellMode, String createTime)
	{
		try
		{
			con = new SqlData();
			String sql = "insert into u_sell_info(s_pk,p_pk,p_by_pk,s_wp_silver_money,s_wp_copper_money,sell_mode,create_time) values(null,'"
					+ pPk
					+ "','"
					+ pByPk
					+ "','"
					+ ssilverMoney
					+ "','"
					+ sCopperMoney
					+ "','"
					+ sellMode
					+ "','"
					+ createTime
					+ "')";
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

	// �����׷��޸Ľ��ױ��ֵ�Ǯ��״̬ 
	public void getSellArmAdd(int pPk, String ByPk, String sWuping,
			int sWpType, int sWpNumber, String sWpSilverMoney,
			String sWpCopperMoney, int sellMode, String create_time)
	{
		try
		{
			con = new SqlData();
			String sql = "insert into u_sell_info values(null,'" + pPk + "','"
					+ ByPk + "','" + sWuping + "', '" + sWpType + "','"
					+ sWpNumber + "', '" + sWpSilverMoney + "', '"
					+ sWpCopperMoney + "', '" + sellMode + "','" + create_time
					+ "')";
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

	// ͨ��ע��ID ȥ�ҽ�ɫ���Ƿ����
	public boolean getSelleInfoVs(String pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_sell_info where p_by_pk=" + pPk;
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
	 * ��ѯ�������� ÿ��ֻ��ѯһ��
	 * 
	 * @return
	 */
	public SellInfoVO getSellMode(int pByPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select s_pk,sell_mode from u_sell_info where p_by_pk='"
					+ pByPk + "' limit 1";
			ResultSet rs = con.query(sql);
			SellInfoVO vo = null;
			if (rs.next())
			{
				vo = new SellInfoVO();
				vo.setSPk(rs.getInt("s_pk"));
				vo.setSellMode(rs.getInt("sell_mode"));
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
  
	public boolean isSellInfoIdBy(int pByPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_sell_info where p_by_pk=" + pByPk;
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
	
	// ��ѯ���ױ������ŵ�ID��
	public int getSelleInfopPk(String pByPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_sell_info where p_by_pk=" + pByPk;
			ResultSet rs = con.query(sql);
			SellInfoVO vo = new SellInfoVO();
			while (rs.next())
			{
				vo.setPPk(rs.getInt("p_pk"));
			}
			return vo.getPPk();
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

	public void getSelleInfoDelete(String pByPk)
	{
		try
		{
			con = new SqlData();
			String sql = "delete from u_sell_info where p_by_pk=" + pByPk;
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

	// �����׷��޸Ľ��ױ��ֵ�Ǯ��״̬
	public void getSellMoneyUpdate(String SilverMoney, String sCopperMoney,
			String SfOk, String pPk, String ByPk)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_sell_info set s_silver_money='"
					+ SilverMoney + "',s_copper_money='" + sCopperMoney
					+ "',s_sf_ok='" + SfOk + "' where p_pk='" + pPk
					+ "' and p_by_pk='" + ByPk + "'";
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

	// �����׷��޸Ľ��ױ��ֵ�Ǯ��״̬
	public void getSellWuPingUpdate(String zbid, String fenglei, String number,
			String Silver, String sCopper, String SfOk, String pPk, String ByPk)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_sell_info set s_wuping='" + zbid
					+ "',s_wp_type='" + fenglei + "',s_wp_number='" + number
					+ "',s_wp_silver_money='" + Silver
					+ "',s_wp_copper_money='" + sCopper + "',s_sf_ok='" + SfOk
					+ "' where p_pk='" + pPk + "' and p_by_pk='" + ByPk + "'";
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

	// �����޸Ľ��ױ��ֵ�Ǯ��״̬
	public void getSellByMoneyUpdate(String SilverMoney, String sCopperMoney,
			String sPk)
	{
		try
		{
			con = new SqlData();
			String sql = "update u_sell_info set s_silver_money='"
					+ SilverMoney + "',s_copper_money='" + sCopperMoney
					+ "' where s_pk='" + sPk + "' ";
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
	 * ��ѯһ�����׼�¼
	 * 
	 * @param sPk
	 * @return
	 */
	public SellInfoVO getSellView(int sPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_sell_info where s_pk=" + sPk;
			ResultSet rs = con.query(sql);
			SellInfoVO vo = null;
			if (rs.next())
			{
				vo = new SellInfoVO();
				vo.setSPk(rs.getInt("s_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPByPk(rs.getInt("p_by_pk"));
				vo.setSWuping(rs.getInt("s_wuping"));
				vo.setSWpType(rs.getInt("s_wp_type"));
				vo.setSWpNumber(rs.getInt("s_wp_number"));
				vo.setSWpSilverMoney(rs.getInt("s_wp_silver_money"));
				vo.setCreateTime(rs.getString("create_time"));
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
	 * ɾ��������Ϣ
	 * @param sPk
	 */
	public void deleteSelleInfo(String sPk)
	{
		try
		{
			con = new SqlData();
			String sql = "delete from u_sell_info where s_pk=" + sPk;
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
	 * Ѱ��������������û��ĳ�˵�ĳ��
	 * 
	 * @param pPk
	 * @param goodsId
	 *            ��Ʒid
	 * @param s_wp_type
	 *            ��Ʒ����
	 * @return ���Ϊ����˵��û��
	 */

	public String getSellExistByPPkAndGoodsId(String pPk, String goodsId, int s_wp_type)
	{
		int exist = 0;
		try
		{

			con = new SqlData();
			String sql = "select count(1) as exist1 from u_sell_info where s_wuping = " + goodsId + " and p_pk = " + pPk + " and s_wp_type = " + s_wp_type;
			ResultSet rs = con.query(sql);
			if (rs.next())
			{
				exist = rs.getInt("exist1");
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
		if (exist == 0)
		{
			return "";
		}
		else
		{
			return "�Բ���,���Ĵ���Ʒ�Ѿ��������ˣ���ȴ��Է�������Ӧ�ٴ���!";
		}
	}
	/**
	 * ɾ�������ʱ����
	 * @param time
	 */
	public void deleteSellPetTime(String time){
		try{
			con = new SqlData();
			String sql = "delete from u_pet_sell where create_time < '"+time+"'";
			con.update(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			con.close();
		}
	}
	/**
	 * ɾ����Ʒ���ױ�ʱ����
	 * @param time
	 */
	public void deleteSellPropTime(String time){
		try{
			con = new SqlData();
			String sql = "delete from u_sell_info where create_time < '"+time+"'";
			con.update(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			con.close();
		}
	}
}
