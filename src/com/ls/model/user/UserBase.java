package com.ls.model.user;

import com.ls.web.service.player.RoleService;

/**
 * @author ls
 * ������ʵ����Ļ���
 */
public abstract class UserBase
{
	protected int p_pk = -1;

	public UserBase( int pPk )
	{
		this.p_pk = pPk;
	}
	
	public RoleEntity getRoleEntity()
	{
		return RoleService.getRoleInfoById(p_pk+"");
	}
}
