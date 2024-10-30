package com.ls.ben.vo.info.partinfo;

/**
 * ����:��ҿ�ݼ���
 * 
 * @author ��˧ 5:55:50 PM
 */
public class ShortcutVO {
	/** id */
	private int scPk;
	  
	/** ��ݼ���ʾ�����֣����磻��������,ҩƷ���� */
	private int pPk;
	
	/**��ݼ����֣����*/
	private String scName;
	
	/**���ú���ʾ�����֣����磻��������,ҩƷ����*/
	private String scDisplay;
	
	/**��ݼ�,��������*/
	private int scType;
	
	/**����id*/
	private int operateId;
	
	/**���ö���*/  
	private int objectId;

	public String getSimpleDisplay()
	{
		if( scDisplay.length()>3 )
		{
			return scDisplay.substring(0,3);
		}
		else
		{
			return scDisplay;
		}
	}
	
	/**
	 * ��ݼ��ָ�Ĭ��
	 * @return  true��ʾ���ã�false��ʾ����Ҫ����
	 */
	public boolean init()
	{
		if( scType!=0 )
		{
			scType = 0;
			scDisplay = scName;
			operateId = 0;
			return true;
		}
		return false;
	}

	public int getPPk() {
		return pPk;
	}

	public void setPPk(int pk) {
		pPk = pk;
	}

	
	public int getOperateId() {
		return operateId;
	}

	public void setOperateId(int operateId) {
		this.operateId = operateId;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public int getScPk() {
		return scPk;
	}

	public void setScPk(int scPk) {
		this.scPk = scPk;
	}

	public String getScName() {
		return scName;
	}

	public void setScName(String scName) {
		this.scName = scName;
	}

	public String getScDisplay() {
		return scDisplay;
	}

	public void setScDisplay(String scDisplay) {
		this.scDisplay = scDisplay;
	}

	public int getScType() {
		return scType;
	}

	public void setScType(int scType) {
		this.scType = scType;
	}

	
	
	
	
	
	
	
	
}
