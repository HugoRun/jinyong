package com.ls.ben.vo.info.partinfo;

/**
 * ����:��Ҽ�¼���꣨��:��¼map_id��
 * @author ��˧
 * 4:00:45 PM
 */
public class CoordinateVO {
	
	/**id*/
	 private int cPk;
	 /**��ɫid*/
	 private int  pPk;
	 /**�������id*/
	 private int  coordinatePropId;
	 /**�������*/
	 private int  coordinate;
	 /**��¼�Ƿ�ʹ��*/
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
