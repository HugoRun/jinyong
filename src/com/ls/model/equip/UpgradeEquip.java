package com.ls.model.equip;

import com.ls.ben.dao.goods.equip.EquipMaterialDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.vo.goods.equip.EquipMaterialVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.pub.util.MathUtil;

/**
 * @author ls
 * 装备加工（装备升级）
 */
public class UpgradeEquip extends EquipProduct {
	
	public static int CUR_MAX_GRADE = 9;//当前开放的最高升级等级
	public static int MAX_GRADE = 13;//最高升级等级
	
	
	private static int material1_id = 1;//材料1道具id
	private static int material2_id = 2;//材料2道具id
	//升级必须材料id列表
	private static int[] upgrade_material_id_list = {material1_id,material2_id};
	
	private EquipMaterialVO equipMaterialVO;
	
	
	public UpgradeEquip(int pPk)
	{
		super(pPk,"升级",true);//可以使用保底石
	}
	
	
	/**
	 * 选择装备
	 * @param equip
	 * @return
	 */
	public String selectEquip( PlayerEquipVO select_equip )
	{
		String hint = null;
		if( select_equip==null )
		{
			return "装备数据为空";
		}
		EquipMaterialDao equipMaterialDao = new EquipMaterialDao();
		
		this.equip = select_equip;
		
		int quality = equip.getWQuality();
		int grade = equip.getWZbGrade();
		equipMaterialVO = equipMaterialDao.getByQualityAndGrade(quality,grade+1);
		
		int[] need_material_num = {equipMaterialVO.getMaterial1(),equipMaterialVO.getMaterial2()};
		
		this.setNeedMaterials(upgrade_material_id_list, need_material_num);//设置需要的材料
		
		return hint;
	}
	
	/**
	 * 升级条件判断
	 */
	public String isProcessed()
	{
		String hint = null;
		//判断是否能升级
		if( this.equip.getWZbGrade()>=CUR_MAX_GRADE )
		{
			return "你的装备已经是最高等级";
		}
		if( this.equip.getCurEndure()<=0 )
		{
			return "装备已损坏不能升级";
		}
		return hint;
	} 
	
	/**
	 * 升级成功处理
	 */
	protected String processSuccess()
	{
		equip.upgrade();
		return "恭喜您,升级成功,"+equip.getGameEquip().getName()+"成功晋级!";
	}
	
	/**
	 * 升级失败处理
	 */
	protected String processFail()
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		
		int cur_grade = this.equip.getWZbGrade();
		int upgrade_grade = cur_grade+1;//要升的等级
		if( upgrade_grade>=4 && upgrade_grade<=6 )//降1级
		{
			playerEquipDao.updateEquipGrade(this.equip.getPwPk(),cur_grade-1);
		}
		else if( upgrade_grade>=7 && upgrade_grade<=9 )//降1~3级
		{
			playerEquipDao.updateEquipGrade(this.equip.getPwPk(), cur_grade-MathUtil.getRandomBetweenXY(1, 3));
		}
		else if( upgrade_grade>=10 && upgrade_grade<=12 )//等级归零
		{
			playerEquipDao.updateEquipGrade(this.equip.getPwPk(), 0);
		}
		else if( upgrade_grade==MAX_GRADE )
		{
			if( isUseProtectedStone()==false)//没有使用了保底道具
			{
				equip.setCurEndure(0);
			}
			equip.setWZbGrade(0);
			equip.save();
		}
		return "太不幸了，您升级失败，请继续努力！";
	}
	
	
	/**
	 * 当前成功率,(基础成功率+宝石成功率)
	 */
	public int getSuccessRate()
	{
		if( equipMaterialVO==null )
		{
			return 0;
		}
		int cur_rate =  equipMaterialVO.getRate()+super.getStoneRate();
		if( cur_rate>100 )
		{
			cur_rate = 100;
		}
		return cur_rate;
	}
	/**
	 * 需要的钱
	 */
	public int getNeedMoney()
	{
		if( equipMaterialVO==null )
		{
			return 0;
		}
		return equipMaterialVO.getNeedMoney();
	}


	@Override
	protected void cleare()
	{
		super.init();
		this.equipMaterialVO = null;
	}

}
