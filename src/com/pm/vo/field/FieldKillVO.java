package com.pm.vo.field;

/**
 * ɱ�˼�¼��,��Ҫ�����ڱ�����¼
 * @author Administrator
 *
 */
public class FieldKillVO
{
	 /**  ɱ�˼�¼��id **/
	public int sfId ;
	/** ���ֵ�id  */
	public int pPk ;
		/** �����ߵ�id  **/
	public int bpPk	;
		/** ս��ս��   */
	public int fieldSequence  ;
		/** ս����ţ�ս���Ĳ�ͬ��ţ�������ս�����ڵĵ�ͼidΪ�����    */
	public int fieldType;
		/** ɱ��ʱ��  */
	public String sfCreateTime;
	
	/** ɱ�������� */
	public String killerName;
	/** ɱ������  **/
	public int killedNum;
	/** ɱ���ߵȼ� */
	public int killerGrade;
	/** ɱ������Ӫ  */
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
