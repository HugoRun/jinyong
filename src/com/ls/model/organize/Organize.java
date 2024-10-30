package com.ls.model.organize;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ls.model.user.RoleEntity;

/**
 * @author ls
 * 游戏里的组织
 */
public abstract class Organize
{
	protected Map<Integer,RoleEntity> member_list = new LinkedHashMap<Integer,RoleEntity>();
	protected int leaderId = -1;
	
	/** 创建 */
	public abstract String create(RoleEntity leader,String name);
	/** 解散 */
	public abstract void disband();
	/** 增加成员 */
	public abstract String addMember( RoleEntity member);
	/** 删除成员 */
	public abstract String delMember(RoleEntity member);
	/** 成员列表*/
	public abstract List<RoleEntity> getMemberList();
	/** 得到成员数量 */
	public abstract int getMemberNum();
	/** 成员是否已满 */
	public abstract boolean isFull();
	/** 得到组织者 */
	public abstract RoleEntity getLeader();
	/** 改变组织者 */
	public abstract String changeLeader(RoleEntity newLeader);
	/**
	 * 是否在组织中
	 */
	public abstract boolean isJoin(RoleEntity member);
	
}
