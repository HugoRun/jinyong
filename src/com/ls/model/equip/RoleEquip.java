package com.ls.model.equip;

import java.util.Date;

/**
 * @author ls
 * 角色拥有的装备
 */
public class RoleEquip
{
	private int id;
	
	private int equipId;
	private int quality;//装备品质
	private int appendGrade=0;//追加升级等级
	private boolean isBind;//是否绑定
	
	private int position;//装备所在位置
	private int curEndure;//当前耐久度
	
	private Date createTime;//创建时间
}
