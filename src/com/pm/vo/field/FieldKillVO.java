package com.pm.vo.field;

/**
 * 杀人记录表,主要是用于本场记录
 * @author Administrator
 *
 */
public class FieldKillVO
{
	 /**  杀人记录表id **/
	public int sfId ;
	/** 凶手的id  */
	public int pPk ;
		/** 被害者的id  **/
	public int bpPk	;
		/** 战场战次   */
	public int fieldSequence  ;
		/** 战场序号，战场的不同编号，现在以战场所在的地图id为区别的    */
	public int fieldType;
		/** 杀人时间  */
	public String sfCreateTime;
	
	/** 杀人者姓名 */
	public String killerName;
	/** 杀死人数  **/
	public int killedNum;
	/** 杀人者等级 */
	public int killerGrade;
	/** 杀人者阵营  */
	public int killerCamp;
	
	
	
	public int getKillerCamp()
	{
		return killerCamp;
	}

	public void setKillerCamp(int killerCamp)
	{
		this.killerCamp = killerCamp;
	}

	public int getKillerGrade()
	{
		return killerGrade;
	}

	public void setKillerGrade(int killerGrade)
	{
		this.killerGrade = killerGrade;
	}

	public int getKilledNum()
	{
		return killedNum;
	}

	public void setKilledNum(int killedNum)
	{
		this.killedNum = killedNum;
	}

	public int getSfId()
	{
		return sfId;
	}

	public void setSfId(int sfId)
	{
		this.sfId = sfId;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getBpPk()
	{
		return bpPk;
	}

	public void setBpPk(int bpPk)
	{
		this.bpPk = bpPk;
	}

	public int getFieldSequence()
	{
		return fieldSequence;
	}

	public void setFieldSequence(int fieldSequence)
	{
		this.fieldSequence = fieldSequence;
	}

	public int getFieldType()
	{
		return fieldType;
	}

	public void setFieldType(int fieldType)
	{
		this.fieldType = fieldType;
	}

	public String getSfCreateTime()
	{
		return sfCreateTime;
	}

	public void setSfCreateTime(String sfCreateTime)
	{
		this.sfCreateTime = sfCreateTime;
	}

	public String getKillerName()
	{
		return killerName;
	}

	public void setKillerName(String killerName)
	{
		this.killerName = killerName;
	}
	
	
	
		
	

}
