/**
 * 
 */
package com.pm.vo.chuansong;

/**
 * 普通传送符的
 * @author zhangjj
 *
 */
public class SuiBianChuanVO
{

	/** 传送表ID  */
	public int carryId	;
	/***  地点类型   ****/	
	public int 	typeId	;
	/***  地点类型名称 **/	
	public String	typeName;
	/**   地点id    **/
	public int 	sceneId;
	/***  地点名称  ****/
	public String	sceneName ;
	/**   地点传送等级  **/
	public int 	carryGrade;
	/****适合玩家的等级****/
	public String partGrade;
	public int getCarryId()
	{
		return carryId;
	}
	public void setCarryId(int carryId)
	{
		this.carryId = carryId;
	}
	public int getTypeId()
	{
		return typeId;
	}
	public void setTypeId(int typeId)
	{
		this.typeId = typeId;
	}
	public String getTypeName()
	{
		return typeName;
	}
	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}
	public int getSceneId()
	{
		return sceneId;
	}
	public void setSceneId(int sceneId)
	{
		this.sceneId = sceneId;
	}
	public String getSceneName()
	{
		return sceneName;
	}
	public void setSceneName(String sceneName)
	{
		this.sceneName = sceneName;
	}
	public int getCarryGrade()
	{
		return carryGrade;
	}
	public void setCarryGrade(int carryGrade)
	{
		this.carryGrade = carryGrade;
	}
	public String getPartGrade()
	{
		return partGrade;
	}
	public void setPartGrade(String partGrade)
	{
		this.partGrade = partGrade;
	}
	
	
	
	
	
	
	
	
	
}
