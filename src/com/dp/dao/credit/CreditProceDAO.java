/**
 * 
 */
package com.dp.dao.credit;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ben.shitu.model.ShituConstant;
import com.dp.vo.credit.PlayerCreditVO;
import com.ls.web.service.rank.RankService;
import com.pub.db.jygamedb.Jygamedb;
import com.pub.db.mysql.SqlData;

/**
 * @author HHJ
 * 
 */
public class CreditProceDAO
{
	Jygamedb db;
	// JygameUserdb udb;
	SqlData udb;

	
	/**
	 * ��ѯ��ҽ�ɫ��ӵ�е�����
	 */
	public List<PlayerCreditVO> getPlayerCredit(Integer ppk)
	{
		try
		{
			String sql = "select * from p_credit_relation where ppk=" + ppk;
			udb = new SqlData();
			ResultSet rs = udb.query(sql);
			List<PlayerCreditVO> pcvlist = new ArrayList<PlayerCreditVO>();
			PlayerCreditVO vo = null;
			while (rs.next())
			{
				vo = new PlayerCreditVO();
				vo.setPcid(rs.getInt("pcid"));
				vo.setPpk(rs.getInt("ppk"));
				vo.setPcid(rs.getInt("cid"));
				vo.setPcount(rs.getInt("pcount"));
				pcvlist.add(vo);
			}
			return pcvlist;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			udb.close();
		}
		return null;
	}

	/***************************************************************************
	 * ��������ID��ѯ����
	 **************************************************************************/
	public PlayerCreditVO getPcvDisplay(Integer pcid)
	{
		try
		{
			db = new Jygamedb();
			String sql = "select * from p_credit pc where pc.cid=" + pcid;
			ResultSet rs = db.query(sql);
			if (rs.next())
			{
				PlayerCreditVO pcv = new PlayerCreditVO();
				pcv.setPcid(rs.getInt(1));
				pcv.setCreditname(rs.getString(2));
				pcv.setCreditdisplay(rs.getString(3));
				return pcv;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
		return null;
	}

	/***************************************************************************
	 * �ж�����Ƿ��������һ���Ʒ
	 **************************************************************************/
	public Integer checkHaveCondition(Integer ppk, Integer cid, Integer ncount)
	{
		try
		{
			udb = new SqlData();
			String sql = "select pcount from p_credit_relation where ppk="
					+ ppk + " and cid=" + cid;
			ResultSet rs = udb.query(sql);
			if (rs.next())
			{
				Integer pcount = rs.getInt(1);
				if (pcount < ncount)
				{
					return 0;
				}
				else
				{
					return 1;
				}
			}
			else
			{
				return -1;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			udb.close();
		}
		return null;
	}

	/***************************************************************************
	 * �һ��ɹ��������ҵ�����
	 **************************************************************************/
	public void subtractCredit(Integer ppk, Integer cid, Integer ncount)
	{
		String sql = "update p_credit_relation set pcount=pcount-" + ncount
				+ " where ppk=" + ppk + " and cid=" + cid;
		try
		{
			udb = new SqlData();
			udb.update(sql);
			if(cid == ShituConstant.CHUSHI_CREDIT_ID){
				//ͳ����Ҫ
				new RankService().updateAdd(ppk, "credit", -ncount);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			udb.close();
		}
	}

	/**
	 * �����ҽ�ɫ����
	 */
	public void addPlayerCredit(Integer ppk, Integer cid, Integer ncount)
	{
		String sql1 = "insert into p_credit_relation values(null," + ppk + ","
				+ cid + "," + ncount + ")";
		String sql2 = "update p_credit_relation set pcount=pcount+" + ncount
				+ " where ppk=" + ppk + " and cid=" + cid;
		try
		{
			Integer i = this.checkPlayerHasTheCredit(ppk, cid);
			udb = new SqlData();
			if (i == 1 || i.equals(1))
			{
				udb.update(sql2);
//				if(cid == ShituConstant.CHUSHI_CREDIT_ID){
//					//ͳ����Ҫ
//					new RankService().updateAdd(ppk, "credit", -ncount);
//				}
			}
			else
			{
				udb.update(sql1);
//				if(cid == ShituConstant.CHUSHI_CREDIT_ID){
//					//ͳ����Ҫ
//					new RankService().updateAdd(ppk, "credit", ncount);
//				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			udb.close();
		}
	}

	/**
	 * ɾ���������
	 */
	public void deletePlayerCredit(int ppk, int cid)
	{
		try
		{ 
	    udb = new SqlData();
		String sql1 = "delete from p_credit_relation where ppk = " + ppk + " and cid="+ cid + "";
		udb.update(sql1); 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			udb.close();
		}
	}
	
	/***************************************************************************
	 * �ж�����Ƿ��и�����
	 **************************************************************************/
	public Integer checkPlayerHasTheCredit(Integer ppk, Integer cid)
	{
		String sql = "select pcount from p_credit_relation where ppk=" + ppk
				+ " and cid=" + cid;
		try
		{
			udb = new SqlData();
			ResultSet rs = udb.query(sql);
			if (rs.next())
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			udb.close();
		}
		return null;
	}

	/***************************************************************************
	 * �ж����������Ƿ�����
	 **************************************************************************/
	public Integer checkHonorCondition(Integer ppk, Integer excount)
	{
		String sql = "select glory_value from u_tong_glory where p_pk=" + ppk;
		try
		{
			udb = new SqlData();
			ResultSet rs = udb.query(sql);
			if (rs.next())
			{
				Integer gvalue = rs.getInt(1);
				if (gvalue == null)
				{
					return -1;// û������ֵ
				}
				else
				{
					if (gvalue < excount)
					{
						return 0;// ����ֵ����
					}
					else
					{
						return 1;// ����ֵ����Ҫ��
					}
				}
			}
			else
			{
				return -1;// û������ֵ
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			udb.close();
		}
		return null;
	}

	/**
	 * �����ҵ�����ֵ
	 */
	public void addPlayerHonor(Integer ppk, Integer excount)
	{
		Integer tpk = this.getRoleTpk(ppk);
		String pname = this.getTheRoleName(ppk);
		String sql1 = "insert into u_tong_glory values(null," + tpk + "," + ppk
				+ "," + pname + ",0," + excount + "," + excount + ")";
		String sql2 = "update u_tong_glory set intraday_value=intraday+"
				+ excount + ",glory_value = glory_value" + excount
				+ " where p_pk=" + ppk;
		try
		{
			udb = new SqlData();
			Integer i = this.checkRoleHaveTheHoner(ppk);
			if (i == 0 || i.equals(0))
			{
				udb.update(sql1);
				//ͳ����Ҫ
				new RankService().updateAdd(ppk, "glory", excount);
			}
			else
			{
				udb.update(sql2);
				//ͳ����Ҫ
				new RankService().updateAdd(ppk, "glory", excount);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			udb.close();
		}
	}

	/**
	 * ������ҵ�����ֵ
	 */
	public void subtractHonor(Integer ppk, Integer excount)
	{
		String sql = "update u_tong_glory set glory_value = glory_value - "
				+ excount + " where p_pk=" + ppk;
		try
		{
			udb = new SqlData();
			udb.update(sql);
			//ͳ����Ҫ
			new RankService().updateAdd(ppk, "glory", -excount);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			udb.close();
		}
	}

	/***************************************************************************
	 * ��ȡ��ɫ�İ��ID
	 **************************************************************************/
	public Integer getRoleTpk(Integer ppk)
	{
		String sql = "select p_tong from u_part_info where p_pk=" + ppk;
		try
		{
			udb = new SqlData();
			ResultSet rs = udb.query(sql);
			if (rs.next())
			{
				return rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			udb.close();
		}
		return null;
	}

	/***************************************************************************
	 * �жϸý�ɫ�Ƿ�������
	 **************************************************************************/
	public Integer checkRoleHaveTheHoner(Integer ppk)
	{
		String sql = "select * from u_tong_glory where p_pk=" + ppk;
		try
		{
			udb = new SqlData();
			ResultSet rs = udb.query(sql);
			if (rs.next())
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			udb.close();
		}
		return null;
	}

	/***************************************************************************
	 * ��ѯ�ý�ɫ�Ľ�ɫ��
	 **************************************************************************/
	public String getTheRoleName(Integer ppk)
	{
		String sql = "select p_name from u_part_info where p_pk=" + ppk;
		try
		{
			udb = new SqlData();
			ResultSet rs = udb.query(sql);
			if (rs.next())
			{
				return rs.getString(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			udb.close();
		}
		return null;
	}
}
