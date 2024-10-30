package com.ls.ben.vo.info.partinfo;

/**
 * 功能:玩家快捷键表
 * 
 * @author 刘帅 5:55:50 PM
 */
public class ShortcutVO {
	/** id */
	private int scPk;
	  
	/** 快捷键显示的名字，例如；技能名称,药品名称 */
	private int pPk;
	
	/**快捷键名字，标号*/
	private String scName;
	
	/**设置后显示的名字，例如；技能名称,药品名称*/
	private String scDisplay;
	
	/**快捷键,功能类型*/
	private int scType;
	
	/**操作id*/
	private int operateId;
	
	/**作用对象*/  
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
	 * 快捷键恢复默认
	 * @return  true表示重置，false表示不需要重置
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
