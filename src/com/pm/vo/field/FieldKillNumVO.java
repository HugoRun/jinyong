package com.pm.vo.field;

/**
 * ս��ɱ�˼�¼��,��Ҫ����ս������ɱ�����а�
 * @author Administrator
 *
 */
public class FieldKillNumVO
{
	/**  ɱ�˱�id  **/
	public int	fk_id ;
		/**  ����id  */
	public int	p_pk;
  		/**     ���˴���ɱ����  **/
	public int kill_num;
  		/** ս�����  */		
	public int		fieldtype;	
  		/**    ����ʱ��  */	
	public String	createTime ;
	
	/** ����ɱ����  */
	public int mouthCountKill;
	/** ���� */
	public String pName;
	/** ��Ӫ */
	public int pCamp;
	/** �ȼ� */
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
