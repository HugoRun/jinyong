/**
 * 
 */
package com.pm.vo.chuansong;

/**
 * ��ͨ���ͷ���
 * @author zhangjj
 *
 */
public class SuiBianChuanVO
{

	/** ���ͱ�ID  */
	public int carryId	;
	/***  �ص�����   ****/	
	public int 	typeId	;
	/***  �ص��������� **/	
	public String	typeName;
	/**   �ص�id    **/
	public int 	sceneId;
	/***  �ص�����  ****/
	public String	sceneName ;
	/**   �ص㴫�͵ȼ�  **/
	public int 	carryGrade;
	/****�ʺ���ҵĵȼ�****/
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
