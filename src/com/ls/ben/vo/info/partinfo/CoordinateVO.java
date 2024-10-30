package com.ls.ben.vo.info.partinfo;

/**
 * 功能:玩家记录坐标（即:记录map_id）
 * @author 刘帅
 * 4:00:45 PM
 */
public class CoordinateVO {
	
	/**id*/
	 private int cPk;
	 /**角色id*/
	 private int  pPk;
	 /**坐标道具id*/
	 private int  coordinatePropId;
	 /**标记坐标*/
	 private int  coordinate;
	 /**记录是否使用*/
	 private int isUse;
	 
	public int getCPk() {
		return cPk;
	}
	public void setCPk(int pk) {
		cPk = pk;
	}
	public int getPPk() {
		return pPk;
	}
	public void setPPk(int pk) {
		pPk = pk;
	}
	public int getCoordinatePropId() {
		return coordinatePropId;
	}
	public void setCoordinatePropId(int coordinatePropId) {
		this.coordinatePropId = coordinatePropId;
	}
	public int getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(int coordinate) {
		this.coordinate = coordinate;
	}
	public int getIsUse() {
		return isUse;
	}
	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}
}
