package com.ls.model.user;

import com.ls.web.service.player.RoleService;

/**
 * @author ls
 * 玩家相关实体类的基类
 */
public abstract class UserBase {
    protected int p_pk = -1;

    public UserBase(int pPk) {
        this.p_pk = pPk;
    }

    public RoleEntity getRoleEntity() {
        return RoleService.getRoleInfoById(p_pk + "");
    }
}
