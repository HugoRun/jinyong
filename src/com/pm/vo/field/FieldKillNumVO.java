package com.pm.vo.field;

/**
 * 战场杀人记录表,主要用于战场的月杀人排行榜
 * @author Administrator
 *
 */
public class FieldKillNumVO
{
	/**  杀人表id  **/
	public int	fk_id ;
		/**  个人id  */
	public int	p_pk;
  		/**     个人此日杀人数  **/
	public int kill_num;
  		/** 战场序号  */		
	public int		fieldtype;	
  		/**    创建时间  */	
	public String	createTime ;
	
	/** 月总杀人数  */
	public int mouthCountKill;
	/** 人名 */
	public String pName;
	/** 阵营 */
	public int pCamp;
	/** 等级 */
	public int pGrade;
	
		
	
	public int getPGrade()
	{
		return pGrade;
	}
	public void setPGrade(int grade)
	{
		pGrade = grade;
	}
	public int getPCamp()
	{
		return pCamp;
	}
	public void setPCamp(int camp)
	{
		pCamp = camp;
	}
	public String getPName()
	{
		return pName;
	}
	public void setPName(String name)
	{
		pName = name;
	}
	public int getMouthCountKill()
	{
		return mouthCountKill;
	}
	public void setMouthCountKill(int mouthCountKill)
	{
		this.mouthCountKill = mouthCountKill;
	}
		public int getFk_id()
		{
			return fk_id;
		}
		public void setFk_id(int fk_id)
		{
			this.fk_id = fk_id;
		}
		public int getP_pk()
		{
			return p_pk;
		}
		public void setP_pk(int p_pk)
		{
			this.p_pk = p_pk;
		}
		public int getKill_num()
		{
			return kill_num;
		}
		public void setKill_num(int kill_num)
		{
			this.kill_num = kill_num;
		}
		public int getFieldtype()
		{
			return fieldtype;
		}
		public void setFieldtype(int fieldtype)
		{
			this.fieldtype = fieldtype;
		}
		public String getCreateTime()
		{
			return createTime;
		}
		public void setCreateTime(String createTime)
		{
			this.createTime = createTime;
		}

	
}
