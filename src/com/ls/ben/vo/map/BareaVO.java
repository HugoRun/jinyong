/**
 * 
 */
package com.ls.ben.vo.map;

/**
 * @author 侯浩军
 * 
 * 10:57:25 AM
 */
public class BareaVO {
	/** 大区域 地域id */
	private int bareaID;
	/** 地域名称 */
	private String bareaName;
	/** 中心点 */
	private int bareaPoint;
	/** 地域介绍 */
	private String bareaDisplay;
	/**地域类型1妖族主城2巫族主城0中立区域**/
	private int bareaType;

	/**
	 * 得到城市的税率
	 * @return
	 */
	public int getTax()
	{
		//todo:
		return 0;
	}
	
	
	public int getBareaID() {
		return bareaID;
	}

	public void setBareaID(int bareaID) {
		this.bareaID = bareaID;
	}

	public String getBareaName() {
		return bareaName;
	}

	public void setBareaName(String bareaName) {
		this.bareaName = bareaName;
	}

	public int getBareaPoint() {
		return bareaPoint;
	}

	public void setBareaPoint(int bareaPoint) {
		this.bareaPoint = bareaPoint;
	}

	public String getBareaDisplay() {
		return bareaDisplay;
	}

	public void setBareaDisplay(String bareaDisplay) {
		this.bareaDisplay = bareaDisplay;
	}

	public int getBareaType()
	{
		return bareaType;
	}

	public void setBareaType(int bareaType)
	{
		this.bareaType = bareaType;
	}

}
