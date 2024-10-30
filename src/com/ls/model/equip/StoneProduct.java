package com.ls.model.equip;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.UserBase;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MathUtil;
import com.ls.web.service.goods.GoodsService;


/**
 * @author ls
 * 宝石加工
 */
public class StoneProduct extends UserBase 
{
	
	private static int material1_id = 3;//升级石id
	
	private int stone_id = 0;//要加工的宝石id
	private int stone_num = 0;//要升级的宝石数量
	private int cur_stone_num = 0;//现有宝石的数量
	
	private int cur_material1_num = 0;//升级石的数量
	
	private  int rate_stone_id;//提升成功率材料id
	private  int rate_stone_num;//提升成功率材料数量
	
	private int stone_rate=0;//当前宝石成功率
	
	public StoneProduct(int pPk )
	{
		super(pPk);
	}
	
	/**
	 * 初始化
	 */
	public void init()
	{
		this.stone_id = 0;
		this.stone_num = 0;
		this.cur_stone_num = 0;
		
		this.stone_rate = 0;
		
		this.cur_material1_num = 0;
		
		this.rate_stone_id = 0;
		this.rate_stone_num = 0;
		this.stone_rate = 0;
	}
	
	
	/**
	 * 选择要升级的宝石
	 * @param equip
	 * @return
	 */
	public String selectStone( int select_stone_id,int upgrade_num )
	{
		String hint = null;
		
		if( upgrade_num<=0 || upgrade_num%2!=0 )
		{
			return "宝石必须为偶数";
		}
		
		GoodsService goodsService = new GoodsService();
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		
		int stone_grade = getStoneGrade(select_stone_id);
		if( stone_grade>=10 )
		{
			return "宝石已是最高等级,不能升级";
		}
		
		int have_stone_num = goodsService.getPropNum(p_pk, select_stone_id);
		
		if( have_stone_num<upgrade_num )
		{
			return "你没有足够的宝石";
		}
		
		cur_material1_num = playerPropGroupDao.getPropNumByByPropID(p_pk,material1_id);//现有升级石的数量
		
		this.stone_id = select_stone_id;
		this.stone_num = upgrade_num;
		this.cur_stone_num = have_stone_num;
		return hint;
	}
	
	
	/**
	 * 升级
	 */
	public String upgrade()
	{
		String hint = null;
		//判断是否能升级
		if( this.stone_id==0 )
		{
			return "请选择要升级的宝石";
		}
		String next_grade_stone_id = PropCache.getPropById(this.stone_id).getPropOperate2();//下一级宝石id
		if( next_grade_stone_id==null && next_grade_stone_id.equals(""))
		{
			return "宝石已不能升级";
		}
		
		//判断升级材料是否够
		if( this.getCurMaterial1Num()<this.getMaterial1Num() )
		{
			return "对不起，您升级所需的材料不足，无法升级！";
		}
		//判断是否有足够的钱
		if( this.getRoleEntity().getBasicInfo().getCopper()<this.getNeedMoney() )
		{
			return "你的银两不足，不能升级";
		}
		
		//消耗物品
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(p_pk, this.material1_id, this.getMaterial1Num(),GameLogManager.R_MATERIAL_CONSUME);
		goodsService.removeProps(p_pk, this.stone_id, this.getStoneNum(),GameLogManager.R_MATERIAL_CONSUME);
		
		//扣除升级费用
		this.getRoleEntity().getBasicInfo().addCopper(-this.getNeedMoney());
		
		//宝石升级
		if( MathUtil.isAppearByPercentage(this.getSuccessRate()) )//升级成功
		{
			goodsService.putPropToWrap(p_pk, Integer.parseInt(next_grade_stone_id), this.stone_num/2,GameLogManager.G_UPGRADE);
			hint = "恭喜您，宝石合成成功，请再接再厉！";
		}
		else//升级失败不消耗宝石
		{
			hint = "太不幸了，您宝石合成失败，请继续努力！";
		}
		
		this.init();
		return hint;
	}
	
	
	/**
	 * 得到宝石名字
	 * @return
	 */
	public String getStoneName()
	{
		if( stone_id==0 )
		{
			return "选择要升级的宝石";
		}
		PropVO rate_stone = PropCache.getPropById(this.stone_id);
		return rate_stone.getPropName();
	}
	
	/**
	 * 得到要升级的宝石数量
	 * @return
	 */
	public int getStoneNum()
	{
		return this.stone_num;
	}
	/**
	 * 得到要升级的宝石数量
	 * @return
	 */
	public int getCurStoneNum()
	{
		return this.cur_stone_num;
	}
	
	
	/**
	 * 需要升级石的数量
	 */
	public int getMaterial1Num()
	{
		return stone_num/2;
	}
	/**
	 * 当前升级石的数量
	 */
	public int getCurMaterial1Num()
	{
		return cur_material1_num;
	}
	
	/**
	 * 当前成功率
	 */
	public int getSuccessRate()
	{
		if( stone_id==0 )
		{
			return stone_rate;
		}
		int stone_grade = getStoneGrade(stone_id);
		return (10-stone_grade)*10+stone_rate;
	}
	/**
	 * 需要的钱描述
	 */
	public String getNeedMoneyDes()
	{
		return ExchangeUtil.getMoneyDes(this.getNeedMoney());
	}
	/**
	 * 需要的钱
	 */
	public int getNeedMoney()
	{
		if( stone_id==0 )
		{
			return 0;
		}
		int stone_grade = getStoneGrade(stone_id);
		return stone_grade*50*stone_num;
	}
	/**
	 * 得到宝石的等级
	 */
	private int getStoneGrade(int stoneID)
	{
		PropVO stone = PropCache.getPropById(stoneID);
		if( stone==null )
		{
			return 0;
		}
		return Integer.parseInt(stone.getPropReLevel().split(",")[0]);
	}
	
}
