package com.ls.model.organize;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ls.model.user.RoleEntity;

/**
 * @author ls
 * ��Ϸ�����֯
 */
public abstract class Organize
{
	protected Map<Integer,RoleEntity> member_list = new LinkedHashMap<Integer,RoleEntity>();
	protected int leaderId = -1;
	
	/** ���� */
	public abstract String create(RoleEntity leader,String name);
	/** ��ɢ */
	public abstract void disband();
	/** ���ӳ�Ա */
	public abstract String addMember( RoleEntity member);
	/** ɾ����Ա */
	public abstract String delMember(RoleEntity member);
	/** ��Ա�б�*/
	public abstract List<RoleEntity> getMemberList();
	/** �õ���Ա���� */
	public abstract int getMemberNum();
	/** ��Ա�Ƿ����� */
	public abstract boolean isFull();
	/** �õ���֯�� */
	public abstract RoleEntity getLeader();
	/** �ı���֯�� */
	public abstract String changeLeader(RoleEntity newLeader);
	/**
	 * �Ƿ�����֯��
	 */
	public abstract boolean isJoin(RoleEntity member);
	
}
