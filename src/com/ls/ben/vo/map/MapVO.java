/**
 * 
 */
package com.ls.ben.vo.map;

/**
 * @author 侯浩军
 * 
 * 1:50:25 PM
 */
public class MapVO {
	/** 小区域 */
	private int mapID;
	/** 地图名称 */
	private String mapName;
	/** 地图介绍 */
	private String mapDisplay;
	/** 地图技能 */
	private String mapSkill;
	/** 所属区域 */
	private int mapFrom;
	/** 地图类型 */
	private int mapType;
	/**
	 * map所在大区域
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
