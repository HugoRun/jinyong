package com.ls.model.user;

/**
 * @author ls
 * 需要保存的数据的玩家实体类
 */
public class UserBaseWithSave extends UserBase
{
	protected PersistenceEntity uPartInfoTab = null;;
	
	public UserBaseWithSave(int pPk,String tableName ,String primaryName,String primaryValue )
	{
		super(pPk);
		uPartInfoTab = new PersistenceEntity(tableName, primaryName,primaryValue);
	}
	
	public UserBaseWithSave(int pPk)
	{
		super(pPk);
		uPartInfoTab = new PersistenceEntity();
	}
	
	/**
	 * 初始化持久化实体
	 */
	public void initPersistenceEntity(String tab_name,String primary_name,String primary_value)
	{
		uPartInfoTab.init(tab_name, primary_name, primary_value);
	}

	public void save()
	{
		uPartInfoTab.persistence();
	}
}
