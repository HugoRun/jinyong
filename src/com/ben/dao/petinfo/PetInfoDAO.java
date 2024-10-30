/**
 * 
 */
package com.ben.dao.petinfo;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.ben.vo.pet.pet.PetShapeVO;
import com.ben.vo.petinfo.PetInfoVO;
import com.ben.vo.petsell.PetSellVO;
import com.pub.db.mysql.SqlData;

/**
 * @author ��ƾ�
 * 
 * 11:02:07 AM
 */
public class PetInfoDAO
{
	SqlData con;

	/**
	 * �����ʼ��
	 */
	public void getPetInfoAdd(String pPk, String petId, String petName,
			String petNickname, String petGrade, String petExp,
			String petXiaExp, int petGjXiao, int petGjDa, String petSale,
			String petGrow, String petWx, String petWxValue,
			String petSkillOne, String petSkillTwo, String petSkillThree,
			String petSkillFour, String petSkillFive, String petLife,
			String petType, int petIsBring, String petFatigue, String petLonge,
			String longeNumber, int longeNumberOk, String skillControl,
			int pet_type, String petPic, double pet_violence_drop)
	{
		try
		{
			con = new SqlData();
			String sql = "insert into p_pet_info(pet_pk,p_pk,pet_id,"
					+ "pet_name,pet_nickname,pet_grade,"
					+ "pet_exp,pet_ben_exp,pet_xia_exp,pet_gj_xiao,"
					+ "pet_gj_da,pet_sale,pet_img,pet_grow,pet_wx,"
					+ "pet_wx_value,pet_skill_one,pet_skill_two,pet_skill_three,pet_skill_four,pet_skill_five,pet_life,pet_isAutoGrow,pet_isBring,"
					+ "pet_fatigue,pet_longe,longe_number,longe_number_ok,"
					+ "skill_control,pet_type,pet_violence_drop) values(null,'"
					+ pPk
					+ "','"
					+ petId
					+ "'"
					+ ",'"
					+ petName
					+ "','"
					+ petNickname
					+ "','"
					+ petGrade
					+ "','"
					+ petExp
					+ "','"
					+ petExp
					+ "','"
					+ petXiaExp
					+ "','"
					+ petGjXiao
					+ "','"
					+ petGjDa
					+ "','"
					+ petSale
					+ "','"
					+ petPic
					+ "','"
					+ petGrow
					+ "','"
					+ petWx
					+ "','"
					+ petWxValue
					+ "','"
					+ petSkillOne
					+ "','"
					+ petSkillTwo
					+ "','"
					+ petSkillThree
					+ "','"
					+ petSkillFour
					+ "','"
					+ petSkillFive
					+ "','"
					+ petLife
					+ "','"
					+ petType
					+ "','"
					+ petIsBring
					+ "','"
					+ petFatigue
					+ "','"
					+ petLonge
					+ "'"
					+ ",'"
					+ longeNumber
					+ "','"
					+ longeNumberOk
					+ "','"
					+ skillControl
					+ "','"
					+ pet_type + "','" + pet_violence_drop + "')";
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
	 * ��������
	 */
	public void getPetInfoUpdate(PetInfoVO petInfoVO, PetShapeVO petShapeVO,
			String BjExp)
	{
		try
		{
			con = new SqlData();
			DecimalFormat dfs = new DecimalFormat("0");
			// ����������:����ĳɳ��� * �ȼ�/3
			double cc = petInfoVO.getPetGrow() * petInfoVO.getPetGrade() / 3;

			// �Գ������������ж�
			if (petInfoVO.getPetType() == 1)
			{
				cc = cc * 0.81;
			}

			// (10+�ȼ�*5)+4*�ɳ���
			double gongji = 0.0;
			// double gongjix=0.0;
			if (petInfoVO.getPetGrade() < 9)
			{
				gongji = (10 + petInfoVO.getPetGrade() * 5) + 4
						* petInfoVO.getPetGrow();
			}
			else
			{
				gongji = Math.pow(petInfoVO.getPetGrade(), 0.5)
						* 20
						+ petInfoVO.getPetGrow()
						* (3 * petInfoVO.getPetGrade() - petInfoVO
								.getPetGrade()) / 2;
			}
			/** ���� */
			double petExps = Double.parseDouble(petShapeVO
					.getShapeBenExperience())
					* petInfoVO.getPetGrow();
			String petExp = dfs.format(petExps);

			// int dangqian = Integer.parseInt(BjExp)+Integer.parseInt(petExp);
			/** �¼�����ﵽ��һ����Ҫ�ľ��� */
			double petXiaExps = Double.parseDouble(petShapeVO
					.getShapeXiaExperience())
					* petInfoVO.getPetGrow();
			String petXiaExp = dfs.format(petXiaExps);
			/** ��С���� */

			/** ��󹥻� */
			int petGjDa = Integer.parseInt(dfs.format(gongji))
					+ Integer.parseInt(dfs.format(cc));

			int petGjXiao = petGjDa * 95 / 100;
			String sql = "update p_pet_info set pet_grade='"
					+ petInfoVO.getPetGrade() + "',pet_exp='" + petExp + "'"
					+ ",pet_ben_exp='" + petExp + "',pet_xia_exp='" + petXiaExp
					+ "',pet_gj_xiao='" + petGjXiao + "',pet_gj_da='" + petGjDa
					+ "' where pet_pk='" + petInfoVO.getPetPk() + "'";
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
	 * ��������
	 */
	public void getPetInfoBjExp(String BjExp, String PetPk)
	{
		try
		{
			con = new SqlData();
			String sql = "update p_pet_info set pet_ben_exp='" + BjExp
					+ "'  where pet_pk='" + PetPk + "'";
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
	 * ��������
	 */
	public void getPetInfoDelte(String PetPk)
	{
		try
		{
			con = new SqlData();
			String sql = "delete from p_pet_info where pet_pk='" + PetPk + "'";
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
	 * ��ó���ļ۸�
	 */
	public int getPetPriceByPetPk(String PetPk)
	{
		int petPrice = 0;
		try
		{
			con = new SqlData();
			String sql = "select pet_sale from p_pet_info where pet_pk='"
					+ PetPk + "'";
			ResultSet rs = con.query(sql);
			while (rs.next())
			{
				petPrice = rs.getInt("pet_sale");
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
		return petPrice;
	}

	/**
	 * �ж��Ƿ񻹴��ڸó���
	 */
	public boolean isPetNot(int pet_pk, int pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from p_pet_info where pet_pk='" + pet_pk
					+ "' and p_pk=" + pPk;
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
	 * ͨ����ɫPK�������ɫ��ӵ�е����г���
	 */
	public List<PetInfoVO> getPetInfoList(String pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from p_pet_info where p_pk='" + pPk
					+ "' order by pet_pk";
			ResultSet rs = con.query(sql);
			List<PetInfoVO> list = new ArrayList<PetInfoVO>();
			while (rs.next())
			{
				PetInfoVO vo = new PetInfoVO();
				vo.setPetPk(rs.getInt("pet_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPetId(rs.getInt("pet_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetNickname(rs.getString("pet_nickname"));
				vo.setPetGrade(rs.getInt("pet_grade"));
				vo.setPetExp(rs.getInt("pet_exp"));
				vo.setPetBenExp(rs.getInt("pet_ben_exp"));
				vo.setPetXiaExp(rs.getInt("pet_xia_exp"));
				vo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				vo.setPetGjDa(rs.getInt("pet_gj_da"));
				vo.setPetSale(rs.getInt("pet_sale"));
				vo.setPetImg(rs.getString("pet_img"));
				vo.setPetGrow(rs.getDouble("pet_grow"));
				vo.setPetWx(rs.getInt("pet_wx"));
				vo.setPetWxValue(rs.getInt("pet_wx_value"));
				vo.setPetSkillOne(rs.getInt("pet_skill_one"));
				vo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				vo.setPetSkillThree(rs.getInt("pet_skill_three"));
				vo.setPetSkillFour(rs.getInt("pet_skill_four"));
				vo.setPetSkillFive(rs.getInt("pet_skill_five"));
				vo.setPetLife(rs.getInt("pet_life"));
				vo.setPetType(rs.getInt("pet_type"));
				vo.setPetIsBring(rs.getInt("pet_isBring"));
				vo.setPetFatigue(rs.getInt("pet_fatigue"));
				vo.setPetLonge(rs.getInt("pet_longe"));
				vo.setLongeNumber(rs.getInt("longe_number"));
				vo.setLongeNumberOk(rs.getInt("longe_number_ok"));
				vo.setSkillControl(rs.getInt("skill_control"));
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
	 * ��ѯû��״̬����1��ʾ��ս��״̬��0��ʾ��
	 */
	public List<PetInfoVO> getpetIsBringList(int petid, int pPk, int petIsBring)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from p_pet_info where p_pk='" + pPk + "' "
					+ "and pet_id='" + petid + "' and pet_isBring='"
					+ petIsBring + "'";
			ResultSet rs = con.query(sql);
			List<PetInfoVO> list = new ArrayList<PetInfoVO>();
			while (rs.next())
			{
				PetInfoVO vo = new PetInfoVO();
				vo.setPetPk(rs.getInt("pet_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPetId(rs.getInt("pet_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetNickname(rs.getString("pet_nickname"));
				vo.setPetGrade(rs.getInt("pet_grade"));
				vo.setPetExp(rs.getInt("pet_exp"));
				vo.setPetBenExp(rs.getInt("pet_ben_exp"));
				vo.setPetXiaExp(rs.getInt("pet_xia_exp"));
				vo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				vo.setPetGjDa(rs.getInt("pet_gj_da"));
				vo.setPetSale(rs.getInt("pet_sale"));
				vo.setPetImg(rs.getString("pet_img"));
				vo.setPetGrow(rs.getDouble("pet_grow"));
				vo.setPetWx(rs.getInt("pet_wx"));
				vo.setPetWxValue(rs.getInt("pet_wx_value"));
				vo.setPetSkillOne(rs.getInt("pet_skill_one"));
				vo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				vo.setPetSkillThree(rs.getInt("pet_skill_three"));
				vo.setPetSkillFour(rs.getInt("pet_skill_four"));
				vo.setPetSkillFive(rs.getInt("pet_skill_five"));
				vo.setPetLife(rs.getInt("pet_life"));
				vo.setPetType(rs.getInt("pet_type"));
				vo.setPetIsBring(rs.getInt("pet_isBring"));
				vo.setPetFatigue(rs.getInt("pet_fatigue"));
				vo.setPetLonge(rs.getInt("pet_longe"));
				vo.setLongeNumber(rs.getInt("longe_number"));
				vo.setLongeNumberOk(rs.getInt("longe_number_ok"));
				vo.setSkillControl(rs.getInt("skill_control"));
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
	 * ��ѯû��״̬����1��ʾ��ս��״̬��0��ʾ��
	 */
	public List<PetInfoVO> getpetIsBringLists(int petid, int pPk,
			int petIsBring, String petName)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from p_pet_info where p_pk='" + pPk + "' "
					+ "and pet_id='" + petid + "' and pet_nickname='" + petName
					+ "' and pet_isBring='" + petIsBring + "'";
			ResultSet rs = con.query(sql);
			List<PetInfoVO> list = new ArrayList<PetInfoVO>();
			while (rs.next())
			{
				PetInfoVO vo = new PetInfoVO();
				vo.setPetPk(rs.getInt("pet_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPetId(rs.getInt("pet_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetNickname(rs.getString("pet_nickname"));
				vo.setPetGrade(rs.getInt("pet_grade"));
				vo.setPetExp(rs.getInt("pet_exp"));
				vo.setPetBenExp(rs.getInt("pet_ben_exp"));
				vo.setPetXiaExp(rs.getInt("pet_xia_exp"));
				vo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				vo.setPetGjDa(rs.getInt("pet_gj_da"));
				vo.setPetSale(rs.getInt("pet_sale"));
				vo.setPetImg(rs.getString("pet_img"));
				vo.setPetGrow(rs.getDouble("pet_grow"));
				vo.setPetWx(rs.getInt("pet_wx"));
				vo.setPetWxValue(rs.getInt("pet_wx_value"));
				vo.setPetSkillOne(rs.getInt("pet_skill_one"));
				vo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				vo.setPetSkillThree(rs.getInt("pet_skill_three"));
				vo.setPetSkillFour(rs.getInt("pet_skill_four"));
				vo.setPetSkillFive(rs.getInt("pet_skill_five"));
				vo.setPetLife(rs.getInt("pet_life"));
				vo.setPetType(rs.getInt("pet_type"));
				vo.setPetIsBring(rs.getInt("pet_isBring"));
				vo.setPetFatigue(rs.getInt("pet_fatigue"));
				vo.setPetLonge(rs.getInt("pet_longe"));
				vo.setLongeNumber(rs.getInt("longe_number"));
				vo.setLongeNumberOk(rs.getInt("longe_number_ok"));
				vo.setSkillControl(rs.getInt("skill_control"));
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
	 * ��ѯû��״̬����1��ʾ��ս��״̬��0��ʾ��
	 */
	public List<PetInfoVO> getpetIsBringList(int pPk, int petIsBring)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from p_pet_info where p_pk='" + pPk + "' "
					+ " and pet_isBring='" + petIsBring + "'";
			ResultSet rs = con.query(sql);
			List<PetInfoVO> list = new ArrayList<PetInfoVO>();
			while (rs.next())
			{
				PetInfoVO vo = new PetInfoVO();
				vo.setPetPk(rs.getInt("pet_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPetId(rs.getInt("pet_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetNickname(rs.getString("pet_nickname"));
				vo.setPetGrade(rs.getInt("pet_grade"));
				vo.setPetExp(rs.getInt("pet_exp"));
				vo.setPetBenExp(rs.getInt("pet_ben_exp"));
				vo.setPetXiaExp(rs.getInt("pet_xia_exp"));
				vo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				vo.setPetGjDa(rs.getInt("pet_gj_da"));
				vo.setPetSale(rs.getInt("pet_sale"));
				vo.setPetImg(rs.getString("pet_img"));
				vo.setPetGrow(rs.getDouble("pet_grow"));
				vo.setPetWx(rs.getInt("pet_wx"));
				vo.setPetWxValue(rs.getInt("pet_wx_value"));
				vo.setPetSkillOne(rs.getInt("pet_skill_one"));
				vo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				vo.setPetSkillThree(rs.getInt("pet_skill_three"));
				vo.setPetSkillFour(rs.getInt("pet_skill_four"));
				vo.setPetSkillFive(rs.getInt("pet_skill_five"));
				vo.setPetLife(rs.getInt("pet_life"));
				vo.setPetType(rs.getInt("pet_type"));
				vo.setPetIsBring(rs.getInt("pet_isBring"));
				vo.setPetFatigue(rs.getInt("pet_fatigue"));
				vo.setPetLonge(rs.getInt("pet_longe"));
				vo.setLongeNumber(rs.getInt("longe_number"));
				vo.setLongeNumberOk(rs.getInt("longe_number_ok"));
				vo.setSkillControl(rs.getInt("skill_control"));
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
	 * ��������
	 */
	public void getDeltePetId(int PetId, int pPk, String petName)
	{
		try
		{
			con = new SqlData();
			String sql = "delete from p_pet_info where p_pk='" + pPk
					+ "' and  pet_id='" + PetId + "' and pet_nickname='"
					+ petName + "' limit 1";
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
	 * ͨ��ע��ID ȥ�ҽ�ɫ���Ƿ����
	 */
	public boolean pet_isBring(int pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from p_pet_info where p_pk='" + pPk
					+ "' and pet_isBring=1";
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
	 * ������ϸ��Ϣ�鿴
	 */
	public PetInfoVO getPetInfoView(String petPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from p_pet_info where pet_pk='" + petPk
					+ "'";
			ResultSet rs = con.query(sql);
			PetInfoVO vo = null;
			if (rs.next())
			{
				vo = new PetInfoVO();
				vo.setPetPk(rs.getInt("pet_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPetId(rs.getInt("pet_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetNickname(rs.getString("pet_nickname"));
				vo.setPetGrade(rs.getInt("pet_grade"));
				vo.setPetExp(rs.getInt("pet_exp"));
				vo.setPetBenExp(rs.getInt("pet_ben_exp"));
				vo.setPetXiaExp(rs.getInt("pet_xia_exp"));
				vo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				vo.setPetGjDa(rs.getInt("pet_gj_da"));
				vo.setPetSale(rs.getInt("pet_sale"));
				vo.setPetImg(rs.getString("pet_img"));
				vo.setPetGrow(rs.getDouble("pet_grow"));
				vo.setPetWx(rs.getInt("pet_wx"));
				vo.setPetWxValue(rs.getInt("pet_wx_value"));
				vo.setPetSkillOne(rs.getInt("pet_skill_one"));
				vo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				vo.setPetSkillThree(rs.getInt("pet_skill_three"));
				vo.setPetSkillFour(rs.getInt("pet_skill_four"));
				vo.setPetSkillFive(rs.getInt("pet_skill_five"));
				vo.setPetLife(rs.getInt("pet_life"));

				vo.setPetType(rs.getInt("pet_type"));
				vo.setPetIsBring(rs.getInt("pet_isBring"));
				vo.setPetFatigue(rs.getInt("pet_fatigue"));
				vo.setPetLonge(rs.getInt("pet_longe"));
				vo.setLongeNumber(rs.getInt("longe_number"));
				vo.setLongeNumberOk(rs.getInt("longe_number_ok"));
				vo.setSkillControl(rs.getInt("skill_control"));
				vo.setPetInitNum(rs.getInt("pet_init_num"));
				vo.setPetViolenceDrop(rs.getDouble("pet_violence_drop"));
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
	 * ������ϸ��Ϣ�鿴
	 */
	public PetInfoVO getPetInfoView(String petPk, int pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from p_pet_info where pet_pk='" + petPk
					+ "' and p_pk=" + pPk;
			ResultSet rs = con.query(sql);
			PetInfoVO vo = null;
			if (rs.next())
			{
				vo = new PetInfoVO();
				vo.setPetPk(rs.getInt("pet_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPetId(rs.getInt("pet_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetNickname(rs.getString("pet_nickname"));
				vo.setPetGrade(rs.getInt("pet_grade"));
				vo.setPetExp(rs.getInt("pet_exp"));
				vo.setPetBenExp(rs.getInt("pet_ben_exp"));
				vo.setPetXiaExp(rs.getInt("pet_xia_exp"));
				vo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				vo.setPetGjDa(rs.getInt("pet_gj_da"));
				vo.setPetSale(rs.getInt("pet_sale"));
				vo.setPetImg(rs.getString("pet_img"));
				vo.setPetGrow(rs.getDouble("pet_grow"));
				vo.setPetWx(rs.getInt("pet_wx"));
				vo.setPetWxValue(rs.getInt("pet_wx_value"));
				vo.setPetSkillOne(rs.getInt("pet_skill_one"));
				vo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				vo.setPetSkillThree(rs.getInt("pet_skill_three"));
				vo.setPetSkillFour(rs.getInt("pet_skill_four"));
				vo.setPetSkillFive(rs.getInt("pet_skill_five"));
				vo.setPetLife(rs.getInt("pet_life"));

				vo.setPetType(rs.getInt("pet_type"));
				vo.setPetIsBring(rs.getInt("pet_isBring"));
				vo.setPetFatigue(rs.getInt("pet_fatigue"));
				vo.setPetLonge(rs.getInt("pet_longe"));
				vo.setLongeNumber(rs.getInt("longe_number"));
				vo.setLongeNumberOk(rs.getInt("longe_number_ok"));
				vo.setSkillControl(rs.getInt("skill_control"));
				vo.setPetInitNum(rs.getInt("pet_init_num"));
				vo.setPetViolenceDrop(rs.getDouble("pet_violence_drop"));
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
	 * ͨ����ɫID �ͳ���ʦ���μ�ս��ȡ��������Ϣ
	 */
	public PetInfoVO getPetInfo(String pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from p_pet_info where  p_pk='" + pPk
					+ "' and pet_isBring = 1";
			ResultSet rs = con.query(sql);
			PetInfoVO vo = null;
			if (rs.next())
			{
				vo = new PetInfoVO();
				vo.setPetPk(rs.getInt("pet_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPetId(rs.getInt("pet_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetNickname(rs.getString("pet_nickname"));
				vo.setPetGrade(rs.getInt("pet_grade"));
				vo.setPetExp(rs.getInt("pet_exp"));
				vo.setPetBenExp(rs.getInt("pet_ben_exp"));
				vo.setPetXiaExp(rs.getInt("pet_xia_exp"));
				vo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				vo.setPetGjDa(rs.getInt("pet_gj_da"));
				vo.setPetSale(rs.getInt("pet_sale"));
				vo.setPetImg(rs.getString("pet_img"));
				vo.setPetGrow(rs.getDouble("pet_grow"));
				vo.setPetWx(rs.getInt("pet_wx"));
				vo.setPetWxValue(rs.getInt("pet_wx_value"));
				vo.setPetSkillOne(rs.getInt("pet_skill_one"));
				vo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				vo.setPetSkillThree(rs.getInt("pet_skill_three"));
				vo.setPetSkillFour(rs.getInt("pet_skill_four"));
				vo.setPetSkillFive(rs.getInt("pet_skill_five"));
				vo.setPetLife(rs.getInt("pet_life"));
				vo.setPetType(rs.getInt("pet_type"));
				vo.setPetIsBring(rs.getInt("pet_isBring"));
				vo.setPetFatigue(rs.getInt("pet_fatigue"));
				vo.setPetLonge(rs.getInt("pet_longe"));
				vo.setLongeNumber(rs.getInt("longe_number"));
				vo.setLongeNumberOk(rs.getInt("longe_number_ok"));
				vo.setSkillControl(rs.getInt("skill_control"));
				// petLonge

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
	 * ͨ����ɫID �ͳ���ʦ���μ�ս��ȡ��������Ϣ
	 */
	public PetInfoVO getPetInfoshiyong(String pPk, String pet_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from p_pet_info where pet_pk='" + pet_pk
					+ "' and p_pk='" + pPk + "'";
			ResultSet rs = con.query(sql);
			PetInfoVO vo = null;
			if (rs.next())
			{
				vo = new PetInfoVO();
				vo.setPetPk(rs.getInt("pet_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPetId(rs.getInt("pet_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetNickname(rs.getString("pet_nickname"));
				vo.setPetGrade(rs.getInt("pet_grade"));
				vo.setPetExp(rs.getInt("pet_exp"));
				vo.setPetBenExp(rs.getInt("pet_ben_exp"));
				vo.setPetXiaExp(rs.getInt("pet_xia_exp"));
				vo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				vo.setPetGjDa(rs.getInt("pet_gj_da"));
				vo.setPetSale(rs.getInt("pet_sale"));
				vo.setPetImg(rs.getString("pet_img"));
				vo.setPetGrow(rs.getDouble("pet_grow"));
				vo.setPetWx(rs.getInt("pet_wx"));
				vo.setPetWxValue(rs.getInt("pet_wx_value"));
				vo.setPetSkillOne(rs.getInt("pet_skill_one"));
				vo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				vo.setPetSkillThree(rs.getInt("pet_skill_three"));
				vo.setPetSkillFour(rs.getInt("pet_skill_four"));
				vo.setPetSkillFive(rs.getInt("pet_skill_five"));
				vo.setPetLife(rs.getInt("pet_life"));
				vo.setPetType(rs.getInt("pet_type"));
				vo.setPetIsBring(rs.getInt("pet_isBring"));
				vo.setPetFatigue(rs.getInt("pet_fatigue"));
				vo.setPetLonge(rs.getInt("pet_longe"));
				vo.setLongeNumber(rs.getInt("longe_number"));
				vo.setLongeNumberOk(rs.getInt("longe_number_ok"));
				vo.setSkillControl(rs.getInt("skill_control"));
				// petLonge
				/** **********************TODO********************** */
				vo.setPetViolenceDrop(rs.getFloat("pet_violence_drop"));
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
	 * ͨ����ɫID �ͳ���ʦ���μ�ս��ȡ������ID
	 */
	public int getPetPk(int pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from p_pet_info where  p_pk='" + pPk
					+ "' and pet_isBring = 1";
			ResultSet rs = con.query(sql);
			int petpk = 0;
			while (rs.next())
			{
				petpk = rs.getInt("pet_pk");
			}
			return petpk;
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
	 * �޸ĳ���ս��״̬
	 */
	public void petIsBring(int pPk, int pet_pk, int petIsBring)
	{
		try
		{
			con = new SqlData();
			String sql = "update p_pet_info set pet_isBring='" + petIsBring
					+ "' where p_pk='" + pPk + "' and pet_pk='" + pet_pk + "'";
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
	 * �޸ĳ�������
	 */
	public void petFatigue(int pPk, int petId, int petFatigue)
	{
		try
		{
			con = new SqlData();
			String sql = "update p_pet_info set pet_fatigue='" + petFatigue
					+ "' where p_pk='" + pPk + "' and pet_pk='" + petId + "'";
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
	 * �޸ĳ�������
	 */
	public void petLonge(int pPk, int petId, int petLonge, int longeNumberOk)
	{
		try
		{
			con = new SqlData();
			String sql = "update p_pet_info set pet_longe='" + petLonge
					+ "',longe_number_ok='" + longeNumberOk + "' where p_pk='"
					+ pPk + "' and pet_pk='" + petId + "'";
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
	 * �޸ĳ�������
	 */
	public void getPetzhuren(String pPk, String BypPk, String petId)
	{
		try
		{
			con = new SqlData();
			String sql = "update p_pet_info set p_pk='" + pPk
					+ "' where p_pk='" + BypPk + "' and pet_pk='" + petId + "'";
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
	 * �޸ĳ�������
	 */
	public void getPetzhuren(int pPk, int petId, String petName)
	{
		try
		{
			con = new SqlData();
			String sql = "update p_pet_info set p_pk='" + pPk
					+ "',pet_nickname='" + petName + "' where pet_pk='" + petId
					+ "'";
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
	 * ɾ�����ｻ�ױ�
	 */
	public void getPetSellDelete(String psPk)
	{
		try
		{
			con = new SqlData();
			String sql = "delete from u_pet_sell where ps_pk='" + psPk + "'";
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
	 * ����Я�����������
	 */
	public int pet_longe(int pPk, int pet_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "select pet_longe from p_pet_info where p_pk='" + pPk
					+ "' and pet_pk='" + pet_pk + "'";
			int pet_longe = 0;
			ResultSet rs = con.query(sql);
			if (rs.next())
			{
				pet_longe = rs.getInt("pet_longe");
			}
			return pet_longe;
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
	 * ����Я�����������
	 */
	public int pet_longeBring(int pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select pet_longe from p_pet_info where p_pk='" + pPk
					+ "' and pet_isBring=1";
			int pet_longe = 0;
			ResultSet rs = con.query(sql);
			if (rs.next())
			{
				pet_longe = rs.getInt("pet_longe");
			}
			return pet_longe;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return -1;
	}

	/**
	 * ���س����Ƿ��ս
	 */
	public int isBring(int pet_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "select pet_isBring from p_pet_info where pet_pk='"
					+ pet_pk + "'";
			int pet_isBring = 0;
			ResultSet rs = con.query(sql);
			if (rs.next())
			{
				pet_isBring = rs.getInt("pet_isBring");
			}
			return pet_isBring;
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
	 * ����Я�����������
	 */
	public int pet_longess(int petPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select pet_longe from p_pet_info where pet_pk='"
					+ petPk + "' ";
			int pet_longe = 0;
			ResultSet rs = con.query(sql);
			if (rs.next())
			{
				pet_longe = rs.getInt("pet_longe");
			}
			return pet_longe;
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
	 * ����Я�����������
	 */
	public String pet_name(int petPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select pet_name from p_pet_info where pet_pk='"
					+ petPk + "' ";
			String pet_name = null;
			ResultSet rs = con.query(sql);
			if (rs.next())
			{
				pet_name = rs.getString("pet_name");
			}
			return pet_name;
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
	 * �޸ĳ��������
	 */
	public void pet_life(int pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "update p_pet_info set pet_longe=pet_longe-1 where p_pk='"
					+ pPk + "' and pet_isBring=1";
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
	 * �����׳������������Ϣ
	 */
	public void getPetSellAdd(String pPk, String pByPk, String petPk,
			String pSilver, String pCopper,String Time)
	{
		try
		{
			con = new SqlData();
			String sql = "insert into u_pet_sell values(null,'" + pPk + "','"
					+ pByPk + "','" + petPk + "','" + pSilver + "','" + pCopper + "','" + Time + "')";
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

	public int getPetSellpPk(String pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_pet_sell where p_by_pk=" + pPk;
			ResultSet rs = con.query(sql);
			PetSellVO vo = new PetSellVO();
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

	/**
	 * ͨ��ע��ID ȥ�ҽ�ɫ���Ƿ����
	 */
	public boolean getPetSellVs(String pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_pet_sell where p_by_pk=" + pPk;
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

	public PetSellVO getPetSellView(String pPk, String BypPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_pet_sell where p_pk='" + BypPk
					+ "' and p_by_pk='" + pPk + "'";
			ResultSet rs = con.query(sql);
			PetSellVO vo = new PetSellVO();
			while (rs.next())
			{
				vo.setPsPk(rs.getInt("ps_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPByPk(rs.getInt("p_by_pk"));
				vo.setPetId(rs.getInt("pet_id"));
				vo.setPsSilverMoney(rs.getInt("ps_silver_money"));
				vo.setPsCopperMoney(rs.getInt("ps_copper_money"));
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

	public PetSellVO getPetSellView(int ps_pk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from u_pet_sell where ps_pk='" + ps_pk + "'";
			// //System.out.println("sql============= "+sql);
			ResultSet rs = con.query(sql);
			PetSellVO vo = null;
			while (rs.next())
			{
				vo = new PetSellVO();
				vo.setPsPk(rs.getInt("ps_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPByPk(rs.getInt("p_by_pk"));
				vo.setPetId(rs.getInt("pet_id"));
				vo.setPsSilverMoney(rs.getInt("ps_silver_money"));
				vo.setPsCopperMoney(rs.getInt("ps_copper_money"));
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
	 * ��ѯ���ｻ�� ÿ��ֻ��ѯһ��
	 * 
	 * @return
	 */
	public int getSellPet(int pByPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select ps_pk from u_pet_sell where p_by_pk='" + pByPk
					+ "' limit 1";
			ResultSet rs = con.query(sql);
			int ps_pk = 0;
			if (rs.next())
			{
				ps_pk = rs.getInt("ps_pk");

			}
			return ps_pk;
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

	/** �õ����û�м��ܵĳ��� */
	public List<PetInfoVO> getPetInfoByTask(int petid, int pPk)
	{
		try
		{
			con = new SqlData();
			String sql = "select * from p_pet_info where pet_isBring = 0 and pet_skill_one = 0 and pet_skill_two = 0 and pet_skill_three = 0 and pet_skill_four = 0 and pet_skill_five = 0 and p_pk = "
					+ pPk + " and pet_id = " + petid;
			ResultSet rs = con.query(sql);
			List<PetInfoVO> list = new ArrayList<PetInfoVO>();
			while (rs.next())
			{
				PetInfoVO vo = new PetInfoVO();
				vo.setPetPk(rs.getInt("pet_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPetId(rs.getInt("pet_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetNickname(rs.getString("pet_nickname"));
				vo.setPetGrade(rs.getInt("pet_grade"));
				vo.setPetExp(rs.getInt("pet_exp"));
				vo.setPetBenExp(rs.getInt("pet_ben_exp"));
				vo.setPetXiaExp(rs.getInt("pet_xia_exp"));
				vo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				vo.setPetGjDa(rs.getInt("pet_gj_da"));
				vo.setPetSale(rs.getInt("pet_sale"));
				vo.setPetImg(rs.getString("pet_img"));
				vo.setPetGrow(rs.getDouble("pet_grow"));
				vo.setPetWx(rs.getInt("pet_wx"));
				vo.setPetWxValue(rs.getInt("pet_wx_value"));
				vo.setPetSkillOne(rs.getInt("pet_skill_one"));
				vo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				vo.setPetSkillThree(rs.getInt("pet_skill_three"));
				vo.setPetSkillFour(rs.getInt("pet_skill_four"));
				vo.setPetSkillFive(rs.getInt("pet_skill_five"));
				vo.setPetLife(rs.getInt("pet_life"));
				vo.setPetType(rs.getInt("pet_type"));
				vo.setPetIsBring(rs.getInt("pet_isBring"));
				vo.setPetFatigue(rs.getInt("pet_fatigue"));
				vo.setPetLonge(rs.getInt("pet_longe"));
				vo.setLongeNumber(rs.getInt("longe_number"));
				vo.setLongeNumberOk(rs.getInt("longe_number_ok"));
				vo.setSkillControl(rs.getInt("skill_control"));
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
}
