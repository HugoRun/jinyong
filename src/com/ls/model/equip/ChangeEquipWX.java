package com.ls.model.equip;

import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.pub.constant.Equip;

/**
 * @author ls
 * ת��װ����������
 */
public class ChangeEquipWX extends EquipProduct
{
	private static int material1_id = 106;//����ת����Ҫ�Ĳ���
	//�����������id�б�
	private static int[] upgrade_material_id_list = {material1_id};
	
	public ChangeEquipWX(int pPk )
	{
		super(pPk,"����ת��", false);
	}

	@Override
	public int getNeedMoney()
	{
		return 0;
	}

	@Override
	public int getSuccessRate()
	{
		if( this.equip==null )
		{
			return 0;
		}
		if( equip.getWQuality()==Equip.Q_YOUXIU)
		{
			return 100;
		}
		else if( equip.getWQuality()==Equip.Q_LIANGHAO )
		{
			return 50+this.getStoneRate();
		}
		return 0;
	}


	@Override
	public String selectEquip(PlayerEquipVO select_equip)
	{
		if( select_equip.getWLevel()>0 )
		{
			return "װ��������,����ת������";
		}
		if( select_equip.getEffectHoleNum()>0 )
		{
			return "װ���Ѵ��,����װ������";
		}
		
		this.equip = select_equip;
		
		int[] need_material_num = {1};
		
		this.setNeedMaterials(upgrade_material_id_list, need_material_num);//������Ҫ�Ĳ���
		
		return null;
	}

	@Override
	public String isProcessed()
	{
		if( this.equip.getWZbGrade()>0 )
		{
			return "װ��������,����ת������";
		}
		if( this.equip.getEffectHoleNum()>0 )
		{
			return "װ���Ѵ��,����װ������";
		}
		return null;
	}

	@Override
	protected String processFail()
	{
		return "̫�����ˣ���ת��ʧ�ܣ������Ŭ����";
	}

	@Override
	protected String processSuccess()
	{
		return this.equip.changeWXType();
	}

	@Override
	protected void cleare()
	{
		super.init();
	}
}
