/**
 * 
 */
package com.ls.ben.vo.map;

/**
 * @author ��ƾ�
 * 
 * 10:57:25 AM
 */
public class BareaVO {
	/** ������ ����id */
	private int bareaID;
	/** �������� */
	private String bareaName;
	/** ���ĵ� */
	private int bareaPoint;
	/** ������� */
	private String bareaDisplay;
	/**��������1��������2��������0��������**/
	private int bareaType;

	/**
	 * �õ����е�˰��
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
