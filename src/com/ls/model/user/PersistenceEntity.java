package com.ls.model.user;

import java.util.HashMap;
import java.util.Iterator;

import com.ls.ben.dao.DaoBase;

/**
 * @author handan
 * �־û�ʵ��
 */
public class PersistenceEntity
{
	/**
	 * �־û���Ҫ��dao
	 */
	private DaoBase dao=null;
	/**
	 * ����
	 */
	private String tab_name;
	/**
	 * primary�����ֶ���
	 */
	private String primary_name;
	/**
	 * primary����ֵ
	 */
	private String primary_value;
	/**
	 * ��Ҫ�־û����ֶ�
	 */
	private HashMap<String,String> persistenceColumns;
	
	public PersistenceEntity()
	{
		
	}
			
	
	public PersistenceEntity(String tab_name,String primary_name,String primary_value)
	{
		this.init(tab_name,primary_name,primary_value);
	}
	
	/**
	 * ��ʼ��
	 */
	public void init(String tab_name,String primary_name,String primary_value)
	{
		this.dao = new DaoBase();
		this.tab_name = tab_name;
		this.primary_name = primary_name;
		this.primary_value = primary_value;
		persistenceColumns = new HashMap<String,String>(20);
	}
	
	/**
	 * ����Ҫ�־û����ֶ�
	 */
	public void addPersistenceColumn(String column,String value )
	{
		if( column==null || value==null )
		{
			return;
		}
		persistenceColumns.put(column, value);
		
	}


	/**
	 * ִ�г־û�����
	 * @param primary����
	 */
	public void persistence()
	{
		if( persistenceColumns.size()==0 )
		{
			return;
		}
		
		StringBuffer update_sql = new StringBuffer();
		
		update_sql.append("update ").append(this.tab_name).append(" set ");
		
		StringBuffer set_sql = new StringBuffer();
		String column = null;
		String value = null;
		Iterator<String> iterator = persistenceColumns.keySet().iterator();
		while(iterator.hasNext()) 
		{
    		column = iterator.next();
    		value = persistenceColumns.get(column);
    		if( set_sql.toString().equals(""))
    		{
    			set_sql.append(column+"='"+value+"'");
    		}
    		else
    		{
    			set_sql.append(","+column+"='"+value+"'");
    		}
		}
		update_sql.append(set_sql.toString()).append(" where ").append(primary_name).append("='").append(primary_value).append("'");
		
		this.persistenceColumns.clear();
		dao.executeUpdateSql(update_sql.toString());
	}
}
