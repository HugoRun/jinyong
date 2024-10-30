package com.ls.ben.dao.faction.game;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.model.organize.faction.game.FUpgradeMaterial;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * �����ɣ����ã������ò���
 */
public class FUpgradeMaterialDao extends BasicDaoSupport<FUpgradeMaterial>
{

	public FUpgradeMaterialDao()
	{
		super("f_upgrade_material", DBConnection.GAME_DB);
	}

	/**
	 * �õ������ò��ϣ�����=1������=2��
	 *  type;//�������ͣ�1��ʾ�����������ϣ�2��ʾ������������
	 * @return
	 */
	public FUpgradeMaterial getOneByType(int type,int grade)
	{
		return super.getOneBySql("where type = "+type+" and grade="+grade);
	}
	
	@Override
	protected FUpgradeMaterial loadData(ResultSet rs) throws SQLException
	{
		FUpgradeMaterial fMaterial = new FUpgradeMaterial();
		fMaterial.setGrade(rs.getInt("grade"));
		fMaterial.setFMStr(rs.getString("fMStr"));
		fMaterial.setPrestige(rs.getInt("prestige"));
		fMaterial.setMId(rs.getInt("mId"));
		fMaterial.setMNum(rs.getInt("mNum"));
		fMaterial.setMDes(rs.getString("mDes"));
		fMaterial.setMoney(rs.getInt("money"));
		fMaterial.setEffectDes(rs.getString("effectDes"));
		return fMaterial;
	}

}
