package com.ls.model.property;

import com.lw.dao.skill.Passskill;
import com.pm.vo.passiveskill.PassSkillVO;

public class RoleSkillPropertyInfo implements PassSkillInterface
{
	private double skGjMultiple = 0;// ��������
	private double skFyMultiple = 0;// ���R����
	private double skHpMultiple = 0;// Ѫ������
	private double skMpMultiple = 0;// �{ɫ����
	private double skBjMultiple = 0;// ������
	private int skGjAdd = 0;// �����ӵĔ�ֵ
	private int skFyAdd = 0;// ���R�ӵĔ�ֵ
	private int skHpAdd = 0;// Ѫ���ӵĔ�ֵ
	private int skMpAdd = 0;// �����ӵĔ�ֵ
	private double skJMultiple = 0;// �𱶔�
	private double skMMultiple = 0;// ľ����
	private double skSMultiple = 0;// ˮ����
	private double skHMultiple = 0;// �𱶔�
	private double skTMultiple = 0;// ������

	/** �b�d��Ҽ��܌��� */
	protected RoleSkillPropertyInfo(int p_pk)
	{
		loadPropertys(p_pk);
	}

	/**
	 * ��������ֵ
	 */
	protected void loadPropertys(int p_pk)
	{
		Passskill ps = new Passskill();
		PassSkillVO passSkillVO = ps.getPassSkillByPPk(p_pk);
		PassSkillVO passSkillVO_wx = ps.getPassSkillWXByPPk(p_pk);
		this.skGjMultiple = passSkillVO.getSkGjMultiple();
		this.skFyMultiple = passSkillVO.getSkFyMultiple();
		this.skHpMultiple = passSkillVO.getSkHpMultiple();
		this.skMpMultiple = passSkillVO.getSkMpMultiple();
		this.skBjMultiple = passSkillVO.getSkBjMultiple();
		this.skGjAdd = passSkillVO.getSkGjAdd();
		this.skFyAdd = passSkillVO.getSkFyAdd();
		this.skHpAdd = passSkillVO.getSkHpAdd();
		this.skMpAdd = passSkillVO.getSkMpAdd();
		this.skJMultiple = passSkillVO_wx.getSkJMultiple();
		this.skMMultiple = passSkillVO_wx.getSkMMultiple();
		this.skSMultiple = passSkillVO_wx.getSkSMultiple();
		this.skHMultiple = passSkillVO_wx.getSkHMultiple();
		this.skTMultiple = passSkillVO_wx.getSkTMultiple();
	}

	/** ������Ҽ��ܻ������ԣ��W�����ܕr���{�ã� */
	public void updateSkillProperty(PassSkillVO passSkillVO)
	{
		if (this.skGjMultiple < passSkillVO.getSkGjMultiple())
		{
			this.skGjMultiple = passSkillVO.getSkGjMultiple();
		}
		if (this.skFyMultiple < passSkillVO.getSkFyMultiple())
		{
			this.skFyMultiple = passSkillVO.getSkFyMultiple();
		}
		if (this.skHpMultiple < passSkillVO.getSkHpMultiple())
		{
			this.skHpMultiple = passSkillVO.getSkHpMultiple();
		}
		if (this.skMpMultiple < passSkillVO.getSkMpMultiple())
		{
			this.skMpMultiple = passSkillVO.getSkMpMultiple();
		}
		if (this.skBjMultiple < passSkillVO.getSkBjMultiple())
		{
			this.skBjMultiple = passSkillVO.getSkBjMultiple();
		}
		this.skGjAdd = this.skGjAdd + passSkillVO.getSkGjAdd();
		this.skFyAdd = this.skFyAdd + passSkillVO.getSkFyAdd();
		this.skHpAdd = this.skHpAdd + passSkillVO.getSkHpAdd();
		this.skMpAdd = this.skMpAdd + passSkillVO.getSkMpAdd();
	}

	/** ������Ҽ������Ќ��ԣ��W�����ܕr���{�ã� */
	public void updateSkillWxProperty(PassSkillVO passSkillVO)
	{
		if (this.skJMultiple < passSkillVO.getSkJMultiple())
		{
			this.skJMultiple = passSkillVO.getSkJMultiple();
		}
		if (this.skMMultiple < passSkillVO.getSkMMultiple())
		{
			this.skMMultiple = passSkillVO.getSkMMultiple();
		}
		if (this.skSMultiple < passSkillVO.getSkSMultiple())
		{
			this.skSMultiple = passSkillVO.getSkSMultiple();
		}
		if (this.skHMultiple < passSkillVO.getSkHMultiple())
		{
			this.skHMultiple = passSkillVO.getSkHMultiple();
		}
		if (this.skTMultiple < passSkillVO.getSkTMultiple())
		{
			this.skTMultiple = passSkillVO.getSkTMultiple();
		}
	}

	public double getSkGjMultiple()
	{
		return skGjMultiple;
	}

	public double getSkFyMultiple()
	{
		return skFyMultiple;
	}

	public double getSkHpMultiple()
	{
		return skHpMultiple;
	}

	public double getSkMpMultiple()
	{
		return skMpMultiple;
	}

	public double getSkBjMultiple()
	{
		return skBjMultiple;
	}

	public int getSkGjAdd()
	{
		return skGjAdd;
	}

	public int getSkFyAdd()
	{
		return skFyAdd;
	}

	public int getSkHpAdd()
	{
		return skHpAdd;
	}

	public int getSkMpAdd()
	{
		return skMpAdd;
	}

	public double getSkJMultiple()
	{
		return skJMultiple;
	}

	public double getSkMMultiple()
	{
		return skMMultiple;
	}

	public double getSkSMultiple()
	{
		return skSMultiple;
	}

	public double getSkHMultiple()
	{
		return skHMultiple;
	}

	public double getSkTMultiple()
	{
		return skTMultiple;
	}
}
