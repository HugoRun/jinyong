package com.ls.web.service.player;

import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.user.RoleEntity;

/**
 * ����Ϊ�޸� ������Ե� �ࡣ
 * @author Administrator
 *
 */
public class PropertyService
{
	
	/**
	 * ���޸� ��ҵ� hp����ʱ �� �����ȼ���ڴ��� ������Ƿ����ߣ�
	 * �����Ҳ����ߣ���ֱ�ӱ�����ݿ⣬����͵��� roleEntity ��صķ���
	 */
	public void updateHpProperty(int p_pk, int p_hp) {
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");
		if ( roleInfo != null) {
			// ���playerBȷʵ���ߣ���ô�����ڴ�����ݿ�
			roleInfo.getBasicInfo().updateHp(p_hp);
		} 
	}

	/**
	 * ���޸� ��ҵ� mp����ʱ �� �����ȼ���ڴ��� ������Ƿ����ߣ�
	 * �����Ҳ����ߣ���ֱ�ӱ�����ݿ⣬����͵��� roleEntity ��صķ���
	 */
	public void updateMpProperty(int p_pk, int p_mp)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");
		if ( roleInfo != null) {
			roleInfo.getBasicInfo().updateMp(p_mp);
		} 
	}
	
	/**
	 * ���޸� ��ҵ� exp����ʱ �� �����ȼ���ڴ��� ������Ƿ����ߣ�
	 * �����Ҳ����ߣ���ֱ�ӱ�����ݿ⣬����͵��� roleEntity ��صķ���
	 */
	public void updateExpProperty(int p_pk, long exp)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");
		if ( roleInfo != null) {
			// ���playerBȷʵ���ߣ���ô�����ڴ�����ݿ�
			roleInfo.getBasicInfo().updateCurExp(exp+"");
		} 
	}
	
	/**
	 * ���޸� ��ҵ� exp����ʱ �� �����ȼ���ڴ��� ������Ƿ����ߣ�
	 * �����Ҳ����ߣ���ֱ�ӱ�����ݿ⣬����͵��� roleEntity ��صķ���
	 */
	public void updateAddExpProperty(int p_pk, long exp)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");
		if ( roleInfo != null) {
			// ���playerBȷʵ���ߣ���ô�����ڴ�����ݿ�
			roleInfo.getBasicInfo().updateAddCurExp(exp);
		} 
	}
	
	/**
	 * �õ���ҵ�  ����
	 */
	public String getPlayerName(int p_pk)
	{
		String enemy_name = "";
		RoleEntity other_roleInfo = RoleService.getRoleInfoById(p_pk+"");
		if ( other_roleInfo != null) {
			enemy_name = other_roleInfo.getBasicInfo().getRealName();
		} else {
			PartInfoDao infodao = new PartInfoDao();
			enemy_name = infodao.getNameByPpk(p_pk);
		}
		return enemy_name;
	}

}
