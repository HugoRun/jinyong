package com.ben.dao.info.partinfo;

import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.ben.vo.info.partinfo.PartInfoVO;
import com.ls.pub.util.StringUtil;
import com.pub.db.jygamedb.Jygamedb;
import com.pub.db.mysql.SqlData;

/**
 * @author ��ƾ� pUpHp, pHp,pUpMp, 8:41:31 PM
 */
public class PartInfoDAO
{
	SqlData con;
	Jygamedb con1;

	Logger logger = Logger.getLogger(PartInfoDAO.class);

	/**
	 * ��ʼ����ݼ�
	 * 
	 * @param pPk
	 */
	public void initShortcut(String pPk)
	{
		try
		{
			con = new SqlData();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into u_shortcut_info values ");
			sql.append("(null,'").append(pPk).append("','��ݼ�1','��ݼ�1',0,0,0)");
			sql.append(",(null,'").append(pPk).append("','��ݼ�2','��ݼ�2',0,0,0)");
			sql.append(",(null,'").append(pPk).append("','��ݼ�3','��ݼ�3',0,0,0)");
			sql.append(",(null,'").append(pPk).append("','��ݼ�4','��ݼ�4',0,0,0)");
			sql.append(",(null,'").append(pPk).append("','��ݼ�5','��ݼ�5',0,0,0)");
			sql.append(",(null,'").append(pPk).append("','��ݼ�6','��ݼ�6',0,0,0)");
			con.update(sql.toString());
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
	 * ͨ��ע��ID ȥ�ҽ�ɫ���Ƿ����
	 */
	public boolean getPartTypeListName(String pName)
	{
		try
		{
			con = new SqlData();
			String sql = "select p_pk from u_part_info where p_name='" + pName+ "'";
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
	 * �жϽ�ɫ�Ƿ�������״̬
	 */
	public boolean getIsNewState(String pName)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_part_info where p_name='" + pName+ "' and player_state_by_new=1";
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
	 * ͨ��ע��ID�õ���ҵĽ�ɫ������
	 */
	public int getRoleNum(String uPk)
	{
		int role_num = 0;
		try
		{
			con = new SqlData();
			String sql = "select count(p_pk) as role_num from u_part_info where u_pk="
					+ uPk + " and delete_flag=0";
			ResultSet rs = con.query(sql);
			if (rs.next())
			{
				role_num = rs.getInt("role_num");
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
		return role_num;
	}



	/**
	 * ͨ����ɫID ȥ�ҽ�ɫ�����Ϣ
	 */
	public PartInfoVO getPartView(String pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_part_info where p_pk='" + pPk + "'";
			ResultSet rs = con.query(sql);
			PartInfoVO vo = new PartInfoVO();
			while (rs.next())
			{
				vo.setPPk(rs.getInt("p_pk"));
				vo.setUPk(rs.getInt("u_pk"));
				vo.setPName(rs.getString("p_name"));
				vo.setPSex(rs.getInt("p_sex"));
				vo.setPGrade(rs.getInt("p_grade"));
				vo.setPHp(rs.getInt("p_hp"));
				vo.setPMp(rs.getInt("p_mp"));
				vo.setPGj(rs.getInt("p_gj"));
				vo.setPFy(rs.getInt("p_fy"));
				vo.setPTeacherType(rs.getInt("p_teacher_type"));
				vo.setPTeacher(rs.getInt("p_teacher"));
				vo.setPHarness(rs.getInt("p_harness"));
				vo.setPFere(rs.getInt("p_fere"));
				vo.setPExperience(rs.getString("p_experience"));
				vo.setPXiaExperience(rs.getString("p_xia_experience"));
				vo.setPCopper(rs.getString("p_copper"));
				vo.setPPkValue(rs.getInt("p_pk_value"));
				vo.setPPks(rs.getInt("p_pks"));
				vo.setPMap(rs.getString("p_map"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setTe_level(rs.getInt("te_level"));
				vo.setChuangong(rs.getString("chuangong"));
				vo.setPBenJiExp(rs.getInt("p_benji_experience"));
				vo.setLast_shoutu_time(rs.getDate("last_shoutu_time"));
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
	 * ���ؽ�ɫ����
	 */
	public String getPartName(String pPk)
	{
		String p_name = null;
		try
		{
			con = new SqlData();
			String sql = "select p_name from u_part_info where p_pk=" + pPk;
			ResultSet rs = con.query(sql);
			while (rs.next())
			{
				p_name = rs.getString("p_name");
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
		return p_name;
	}

	/**
	 * ���ؽ�ɫID
	 */
	public int getPartPk(String pName)
	{
		try
		{
			con = new SqlData();
			String sql = "select p_pk from u_part_info where p_name='" + pName
					+ "'";
			ResultSet rs = con.query(sql);
			int p_pk = 0;
			if (rs.next())
			{
				p_pk = rs.getInt("p_pk");
			}
			return p_pk;
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


	/**
	 * ���ؽ�ɫID
	 */
	public int getPartuPk(int pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select u_pk from u_part_info where p_pk='" + pPk
					+ "'";
			ResultSet rs = con.query(sql);
			int u_pk = 0;
			if (rs.next())
			{
				u_pk = rs.getInt("u_pk");
			}
			return u_pk;
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


	/**
	 * ���ؽ�ɫ���ڵ�ͼ
	 */
	public int getPartMap(int pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select p_map from u_part_info where p_pk=" + pPk;
			ResultSet rs = con.query(sql);
			int mapid = 0;
			if (rs.next())
			{
				mapid = rs.getInt("p_map");
			}
			return mapid;
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


	/**
	 * ��ѯ��ҵ��û����Ƿ�Υ��ȡ������, ���Υ���˹���, ����true
	 * 
	 * @param name
	 * @return
	 */
	public boolean getForbidName(String name)
	{
		boolean flag = false;
		String sql = "select count(1) as num from jy_forbid_name where str like '%"
				+ StringUtil.gbToISO(name) + "%'";
		try
		{
			con1 = new Jygamedb();
			ResultSet rs = con1.query(sql);
			if (rs.next())
			{
				int i = rs.getInt("num");
				if (i != 0)
				{
					flag = true;
				}
			}
			return flag;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con1.close();
		}
		return flag;
	}





	/**
	 * �õ�����55����Ϣ
	 * 
	 * @param list
	 * @param i
	 */
	public void getMenPaiInfo(List<String> list, int menpai)
	{
		String sqlString = "";
		if (menpai == 0)
		{ // 0������
			sqlString = "select sum(g_exp),sum(g_next_exp),sum(g_HP),sum(g_MP),sum(g_gj),sum(g_fy) from u_grow_info "
					+ "where g_pk in (1,2,3,4,5,6,7,8,9) or (g_pk > 60 and g_pk < 107)";
		}
		else
			if (menpai == 1)
			{ // 1��ؤ��
				sqlString = "select sum(g_exp),sum(g_next_exp),sum(g_HP),sum(g_MP),sum(g_gj),sum(g_fy) from u_grow_info "
						+ "where g_pk in (1,2,3,4,5,6,7,8,9) or (g_pk > 111 and g_pk < 158)";
			}
			else
				if (menpai == 2)
				{ // 2������
					sqlString = "select sum(g_exp),sum(g_next_exp),sum(g_HP),sum(g_MP),sum(g_gj),sum(g_fy) from u_grow_info "
							+ "where g_pk < 51";
				}

		try
		{
			con1 = new Jygamedb();
			ResultSet rs = con1.query(sqlString);
			if (rs.next())
			{
				list.add(rs.getInt("sum(g_exp)") + "");
				list.add(rs.getInt("sum(g_next_exp)") + "");
				list.add(rs.getInt("sum(g_HP)") + "");

				list.add(rs.getInt("sum(g_MP)") + "");
				list.add(rs.getInt("sum(g_gj)") + "");
				list.add(rs.getInt("sum(g_fy)") + "");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con1.close();
		}

	}

	

	/**
	 * ��ӳ��˳��ﲶ׽��60���������������60�����¼���
	 * 
	 * @param p_pk
	 * @param menpai
	 */
	public void addSkillInfo(int p_pk, int menpai)
	{
		String sql = "";
		if (menpai == 0)
		{
			sql = "insert into u_skill_info values (null,"
					+ p_pk
					+ ",13,'���̽���(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,310),"
					+ "(null,"
					+ p_pk
					+ ",25,'��ħ����(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,311),"
					+ "(null,"
					+ p_pk
					+ ",34,'��ȫ��(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,320),"
					+ "(null,"
					+ p_pk
					+ ",43,'ʥ�����(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,321),"
					+ "(null," + p_pk
					+ ",4,'Ұ��ȭ(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,100),"
					+ "(null," + p_pk
					+ ",16,'������',1000,now(),now(),0,0,0,0,0,0,0,0,0,0,111113),"
					+ "(null," + p_pk
					+ ",47,'ԽŮ�ķ�',1000,now(),now(),0,0,0,0,0,0,0,0,0,0,111116),"
					+"(null,"+p_pk
					+"152,'��󡹦',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,0)";
		}
		else
			if (menpai == 1)
			{
				sql = "insert into u_skill_info values (null,"
						+ p_pk
						+ ",10,'ؤ�����(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,210),"
						+ "(null,"
						+ p_pk
						+ ",22,'���߹���(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,211),"
						+ "(null,"
						+ p_pk
						+ ",31,'����ͷ(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,220),"
						+ "(null,"
						+ p_pk
						+ ",40,'�����л�(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,221),"
						+ "(null,"
						+ p_pk
						+ ",4,'Ұ��ȭ(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,100),"
						+ "(null,"
						+ p_pk
						+ ",15,'������',1000,now(),now(),0,0,0,0,0,0,0,0,0,0,111112),"
						+ "(null,"
						+ p_pk
						+ ",47,'ԽŮ�ķ�',1000,now(),now(),0,0,0,0,0,0,0,0,0,0,111116)'"
						+"(null,"+p_pk
						+"152,'��󡹦',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,0)";
			}
			else
				if (menpai == 2)
				{
					sql = "insert into u_skill_info values (null,"
							+ p_pk
							+ ",7,'���ֵ���(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,110),"
							+ "(null,"
							+ p_pk
							+ ",19,'��ħ����(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,111),"
							+ "(null,"
							+ p_pk
							+ ",28,'������(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,120),"
							+ "(null,"
							+ p_pk
							+ ",36,'������(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,121),"
							+ "(null,"
							+ p_pk
							+ ",4,'Ұ��ȭ(��ͨ)',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,100),"
							+ "(null,"
							+ p_pk
							+ ",14,'���Ĺ�',1000,now(),now(),0,0,0,0,0,0,0,0,0,0,111111),"
							+ "(null,"
							+ p_pk
							+ ",47,'ԽŮ�ķ�',1000,now(),now(),0,0,0,0,0,0,0,0,0,0,111116),"
							+"(null,"+p_pk
							+"152,'��󡹦',1000,now(),now(),1,0,0,0,0,0,0,0,0,0,0)";
				}

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

	public String[] getName(String p_pk)
	{
		String[] ss = new String[2];
		try
		{
			con = new SqlData();
			String sql = "select u.p_name,u.p_sex from u_part_info u where u.p_pk = "
					+ p_pk;
			ResultSet rs = con.query(sql);
			if (rs.next())
			{
				ss[0] = rs.getString("p_name");
				ss[1] = rs.getInt("p_sex") + "";
			}
			return ss;
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

	public void updateTe_level(Object p_pk)
	{
		if (p_pk != null)
		{
			String sql = "update u_part_info u  set u.te_level  = u.te_level  +1 where u.p_pk = "
					+ p_pk;
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
	
	public void updateMoney(Object p_pk,int addMoney){
		if (p_pk != null)
		{
			String sql = "update u_part_info u  set u.p_copper  = u.p_copper  + "+addMoney+" where u.p_pk = "
					+ p_pk;
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
}
