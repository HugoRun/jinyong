/**
 * 
 */
package com.ls.ben.vo.map;

/**
 * @author ��ƾ�
 * 
 * 1:50:25 PM
 */
public class MapVO {
	/** С���� */
	private int mapID;
	/** ��ͼ���� */
	private String mapName;
	/** ��ͼ���� */
	private String mapDisplay;
	/** ��ͼ���� */
	private String mapSkill;
	/** �������� */
	private int mapFrom;
	/** ��ͼ���� */
	private int mapType;
	/**
	 * map���ڴ�����
	 */
	private BareaVO barea = null;

	public int getMapID() {
		return mapID;
	}

	public void setMapID(int mapID) {
		this.mapID = mapID;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getMapDisplay() {
		return mapDisplay;
	}

	public void setMapDisplay(String mapDisplay) {
		this.mapDisplay = mapDisplay;
	}


	public String getMapSkill() {
		return mapSkill;
	}

	public void setMapSkill(String mapSkill) {
		this.mapSkill = mapSkill;
	}

	public int getMapFrom() {
		return mapFrom;
	}

	public void setMapFrom(int mapFrom) {
		this.mapFrom = mapFrom;
	}

	public int getMapType()
	{
		return mapType;
	}

	public void setMapType(int mapType)
	{
		this.mapType = mapType;
	}

	public BareaVO getBarea()
	{
		return barea;
	}

	public void setBarea(BareaVO barea)
	{
		this.barea = barea;
	}

}
