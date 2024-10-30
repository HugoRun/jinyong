package com.ls.model.user;

/**
 * @author ls
 * ��Ҫ��������ݵ����ʵ����
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
	 * ��ʼ���־û�ʵ��
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
