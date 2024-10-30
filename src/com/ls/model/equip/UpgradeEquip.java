package com.ls.model.equip;

import com.ls.ben.dao.goods.equip.EquipMaterialDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.vo.goods.equip.EquipMaterialVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.pub.util.MathUtil;

/**
 * @author ls
 * 瑁呭�鍔犲伐锛堣�澶囧崌绾э級
 */
public class UpgradeEquip extends EquipProduct {
	
	public static int CUR_MAX_GRADE = 9;//褰撳墠寮€鏀剧殑鏈€楂樺崌绾х瓑绾�
	public static int MAX_GRADE = 13;//鏈€楂樺崌绾х瓑绾�
	
	
	private static int material1_id = 1;//鏉愭枡1閬撳叿id
	private static int material2_id = 2;//鏉愭枡2閬撳叿id
	//鍗囩骇蹇呴』鏉愭枡id鍒楄〃
	private static int[] upgrade_material_id_list = {material1_id,material2_id};
	
	private EquipMaterialVO equipMaterialVO;
	
	
	public UpgradeEquip(int pPk)
	{
		super(pPk,"鍗囩骇",true);//鍙�互浣跨敤淇濆簳鐭�
	}
	
	
	/**
	 * 閫夋嫨瑁呭�
	 * @param equip
	 * @return
	 */
	public String selectEquip( PlayerEquipVO select_equip )
	{
		String hint = null;
		if( select_equip==null )
		{
			return "瑁呭�鏁版嵁涓虹┖";
		}
		EquipMaterialDao equipMaterialDao = new EquipMaterialDao();
		
		this.equip = select_equip;
		
		int quality = equip.getWQuality();
		int grade = equip.getWZbGrade();
		equipMaterialVO = equipMaterialDao.getByQualityAndGrade(quality,grade+1);
		
		int[] need_material_num = {equipMaterialVO.getMaterial1(),equipMaterialVO.getMaterial2()};
		
		this.setNeedMaterials(upgrade_material_id_list, need_material_num);//璁剧疆闇€瑕佺殑鏉愭枡
		
		return hint;
	}
	
	/**
	 * 鍗囩骇鏉′欢鍒ゆ柇
	 */
	public String isProcessed()
	{
		String hint = null;
		//鍒ゆ柇鏄�惁鑳藉崌绾�
		if( this.equip.getWZbGrade()>=CUR_MAX_GRADE )
		{
			return "浣犵殑瑁呭�宸茬粡鏄�渶楂樼瓑绾�";
		}
		if( this.equip.getCurEndure()<=0 )
		{
			return "瑁呭�宸叉崯鍧忎笉鑳藉崌绾�";
		}
		return hint;
	} 
	
	/**
	 * 鍗囩骇鎴愬姛澶勭悊
	 */
	protected String processSuccess()
	{
		equip.upgrade();
		return "鎭�枩鎮�,鍗囩骇鎴愬姛,"+equip.getGameEquip().getName()+"鎴愬姛鏅嬬骇!";
	}
	
	/**
	 * 鍗囩骇澶辫触澶勭悊
	 */
	protected String processFail()
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		
		int cur_grade = this.equip.getWZbGrade();
		int upgrade_grade = cur_grade+1;//瑕佸崌鐨勭瓑绾�
		if( upgrade_grade>=4 && upgrade_grade<=6 )//闄�1绾�
		{
			playerEquipDao.updateEquipGrade(this.equip.getPwPk(),cur_grade-1);
		}
		else if( upgrade_grade>=7 && upgrade_grade<=9 )//闄�1~3绾�
		{
			playerEquipDao.updateEquipGrade(this.equip.getPwPk(), cur_grade-MathUtil.getRandomBetweenXY(1, 3));
		}
		else if( upgrade_grade>=10 && upgrade_grade<=12 )//绛夌骇褰掗浂
		{
			playerEquipDao.updateEquipGrade(this.equip.getPwPk(), 0);
		}
		else if( upgrade_grade==MAX_GRADE )
		{
			if( isUseProtectedStone()==false)//娌℃湁浣跨敤浜嗕繚搴曢亾鍏�
			{
				equip.setCurEndure(0);
			}
			equip.setWZbGrade(0);
			equip.save();
		}
		return "澶�笉骞镐簡锛屾偍鍗囩骇澶辫触锛岃�缁х画鍔�姏锛�";
	}
	
	
	/**
	 * 褰撳墠鎴愬姛鐜�,(鍩虹�鎴愬姛鐜�+瀹濈煶鎴愬姛鐜�)
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
	 * 闇€瑕佺殑閽�
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
