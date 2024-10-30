package com.ls.model.equip;

import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.pub.constant.Equip;

/**
 * @author ls
 * 转换装备五行类型
 */
public class ChangeEquipWX extends EquipProduct
{
	private static int material1_id = 106;//五行转换需要的材料
	//升级必须材料id列表
	private static int[] upgrade_material_id_list = {material1_id};
	
	public ChangeEquipWX(int pPk )
	{
		super(pPk,"五行转换", false);
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
			return "装备已升级,不能转换属性";
		}
		if( select_equip.getEffectHoleNum()>0 )
		{
			return "装备已打孔,不能装换属性";
		}
		
		this.equip = select_equip;
		
		int[] need_material_num = {1};
		
		this.setNeedMaterials(upgrade_material_id_list, need_material_num);//设置需要的材料
		
		return null;
	}

	@Override
	public String isProcessed()
	{
		if( this.equip.getWZbGrade()>0 )
		{
			return "装备已升级,不能转换属性";
		}
		if( this.equip.getEffectHoleNum()>0 )
		{
			return "装备已打孔,不能装换属性";
		}
		return null;
	}

	@Override
	protected String processFail()
	{
		return "太不幸了，您转换失败，请继续努力！";
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
