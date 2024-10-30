package com.ls.model.equip;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.UserBase;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.PropType;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MathUtil;
import com.ls.web.service.goods.GoodsService;

/**
 * @author ls
 * 装备加工生产类
 */
abstract public class EquipProduct extends UserBase
{
	//加工类型
	public static final int upgrade=1;
	public static final int change_wx=2;
	
	private String actionName;//操作标题
	
	//要加工的装备
	protected PlayerEquipVO equip = null;
	
	//必须材料
	private int[] material_id_list;//需要的材料列表
	private int[] need_materials_num;//需要的材料数量
	private int[] cur_materials_num;//当前拥有的（使用的）材料数量
	
	//成功率宝石
	private  int rate_stone_id;//提升成功率材料id
	private  int rate_stone_num;//提升成功率材料数量
	private  int cur_rate_stone_num;//拥有的成功率材料数量
	private int stone_rate=0;//当前宝石成功率
	
	//保底石（作用：加工失败不做处理）
	public static int protect_stone_id = 4;//保护宝石id
	private boolean isCanUseProtectedStone = false;//是否可以使用保底石
	protected int cur_protect_stone_num = 0;//当前拥有保底石的数量
	
	abstract public String selectEquip(PlayerEquipVO select_equip);//选择要加工的装备
	abstract public int getSuccessRate();//得到当前成功率
	abstract public int getNeedMoney();//得到需要的钱
	abstract public String isProcessed();//是否可以加工处理的基础条件
	abstract protected String processSuccess();//处理成功
	protected abstract String processFail();//处理失败
	protected abstract void cleare();//清理工作
	
	public EquipProduct(int pPk,String actionName,boolean isNeedProtectedStone )
	{
		super(pPk);
		this.actionName = actionName;
		this.isCanUseProtectedStone = isNeedProtectedStone;
	}
	
	/**
	 * 可否使用保底宝石
	 */
	public boolean isCanUseProtectedStone()
	{
		return isCanUseProtectedStone;
	}
	
	/**
	 * 加工处理
	 */
	public String process()
	{
		String hint = null;
		//判断是否能升级
		if( this.equip==null )
		{
			return "请选择要"+this.actionName+"装备";
		}
		hint =  equip.isOwnByPPk(this.p_pk);
		if( hint!=null )
		{
			return hint;
		}
		if( equip.isEffected()==false )
		{
			return "该装备已损坏";
		}
		if(equip.getWType()!=Equip.ON_WRAP)
		{
			return "该装备不在包裹里";
		}
		
		//是否可以加工处理的基础条件
		hint = this.isProcessed();
		if( hint!=null )
		{
			return hint;
		}
		
		//判断升级材料是否够
		hint = isEnoughMaterials();
		if( hint!=null )
		{
			return hint;
		}
		
		//消耗物品
		consumeMaterials();
		
		//装备升级
		if( this.isSucessed() )//升级成功
		{
			hint = processSuccess();
		}
		else//升级失败
		{
			hint = processFail();
		}
		
		this.cleare();
		return hint;
	}
	
	
	/**
	 * 必须材料描述
	 * 材料1…[0/0]
	 */
	public String getNeedMaterialsDes()
	{
		if( material_id_list==null )
		{
			return "";
		}
		StringBuffer des = new StringBuffer();
		
		for( int i=0;i< material_id_list.length;i++)
		{
			PropVO prop = PropCache.getPropById(material_id_list[i]);
			if( prop!=null )
			{
				des.append(prop.getPropName()).append("…[").append(cur_materials_num[i]);
				des.append("/").append(this.need_materials_num[i]).append("]").append("<br/>");
			}
		}
		
		return des.toString();
	}
	
	/**
	 * 设置需要的材料
	 */
	protected void setNeedMaterials(int[] need_material_id_list,int[] cur_need_materials_num)
	{
		if( need_material_id_list==null || need_material_id_list.length==0 || cur_need_materials_num==null || cur_need_materials_num.length==0 || need_material_id_list.length!=cur_need_materials_num.length )
		{
			return ;
		}
		
		this.material_id_list = need_material_id_list;
		this.need_materials_num = cur_need_materials_num;
		cur_materials_num = new int[need_material_id_list.length];
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		for( int i=0;i< material_id_list.length;i++)
		{
			int cur_material_num = playerPropGroupDao.getPropNumByByPropID(p_pk,material_id_list[i]);//现有材料的数量
			if( cur_material_num>need_materials_num[i])
			{
				cur_material_num = need_materials_num[i];
			}
			cur_materials_num[i]=cur_material_num;
		}
	}
	
	/**
	 * 判断材料是否足够
	 */
	protected String isEnoughMaterials()
	{
		//判断是否有足够的钱
		if( this.getRoleEntity().getBasicInfo().getCopper()<this.getNeedMoney() )
		{
			return "你的银两不足";
		}
		
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		if( material_id_list!=null && material_id_list.length>0 )
		{
			for( int i=0;i< material_id_list.length;i++)
			{
				int cur_material_num = playerPropGroupDao.getPropNumByByPropID(p_pk,material_id_list[i]);//现有材料的数量
				if( cur_material_num<need_materials_num[i])
				{
					return "材料不足";
				}
			}
		}
		
		if( isUseProtectedStone() )//使用保底石
		{
			int cur_protected_stone_num = playerPropGroupDao.getPropNumByByPropID(p_pk,this.protect_stone_id);//现有保底石的数量
			if( cur_protected_stone_num<1 )
			{
				return "材料不足";
			}
		}
		
		return null;
	}
	
	
	/**
	 * 是否使用保底石
	 * @return
	 */
	public boolean isUseProtectedStone()
	{
		if( isCanUseProtectedStone )//可以使用保底石
		{
			if( cur_protect_stone_num>0 )//使用保底石
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 消耗材料（必须材料，提升概率宝石，金钱）
	 */
	protected void consumeMaterials()
	{
		GoodsService goodsService = new GoodsService();
		//消耗必须材料
		if( material_id_list!=null && material_id_list.length>0 )
		{
			for( int i=0;i< material_id_list.length;i++)
			{
				goodsService.removeProps(p_pk,material_id_list[i], need_materials_num[i],GameLogManager.R_MATERIAL_CONSUME);
			}
		}
		//消耗提升概率宝石
		if( this.rate_stone_num>0)
		{
			goodsService.removeProps(p_pk, this.rate_stone_id, this.rate_stone_num,GameLogManager.R_MATERIAL_CONSUME);
		}
		
		if( isUseProtectedStone() )//使用保底石
		{
			//消耗保底石
			goodsService.removeProps(p_pk, this.protect_stone_id, 1,GameLogManager.R_MATERIAL_CONSUME);
		}
		//消耗金钱
		this.getRoleEntity().getBasicInfo().addCopper(-this.getNeedMoney());
	}
	
	/**
	 * 根据概率判断是否成功
	 */
	protected boolean isSucessed()
	{
		return MathUtil.isAppearByPercentage(getSuccessRate());
	}
	
	/**
	 * 得到需要金钱描述
	 * @return
	 */
	public String getNeedMoneyDes()
	{
		return ExchangeUtil.getMoneyDes(this.getNeedMoney());
	}
	
	/**
	 * 选择保底宝石
	 */
	public String selectProtectStone( int protect_stone_id )
	{
		String hint = null;
		
		if( this.protect_stone_id!=protect_stone_id)
		{
			return "请使用保底宝石";
		}
		
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		int have_num = playerPropGroupDao.getPropNumByByPropID(this.p_pk, this.protect_stone_id);
		
		if( have_num<1 )
		{
			return "你没有足够的宝石";
		}
		
		this.cur_protect_stone_num = have_num;
		
		return hint;
	}
	
	/**
	 * 选择成功率宝石
	 */
	public String selectRateStone( int rate_stone_id,int rate_stone_num )
	{
		String hint = null;
		
		PropVO rate_stone = PropCache.getPropById(rate_stone_id);
		if( rate_stone.getPropClass()!=PropType.UPGRADEHELPPROP)
		{
			return "请使用正确的宝石";
		}
		
		if( rate_stone_num>5 )
		{
			return "最多只可以使用5个宝石";
		}
		
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		int have_num = playerPropGroupDao.getPropNumByByPropID(this.p_pk, rate_stone_id);
		
		if( have_num<rate_stone_num )
		{
			return "你没有足够的宝石";
		}
		
		this.rate_stone_id = rate_stone_id;
		this.rate_stone_num = rate_stone_num;
		this.cur_rate_stone_num = have_num;
		
		this.stone_rate = Integer.parseInt(rate_stone.getPropOperate1())*rate_stone_num;
		
		return hint;
	}
	
	/**
	 * 得到成功率道具名字
	 * @return
	 */
	public String getRateStoneName()
	{
		if( rate_stone_id==0 )
		{
			return "增加成功率的材料";
		}
		PropVO rate_stone = PropCache.getPropById(rate_stone_id);
		return rate_stone.getPropName();
	}
	/**
	 * 得到拥有成功率道具的数量
	 * @return
	 */
	public int getRateStoneNum()
	{
		return this.rate_stone_num;
	}
	
	/**
	 * 得到宝石成功率
	 * @return
	 */
	protected int getStoneRate()
	{
		return stone_rate;
	}
	
	/**
	 * 得到装备名字
	 * @return
	 */
	public String getEquipName()
	{
		if( this.equip==null )
		{
			return "装备";
		}
		return this.equip.getFullName();
	}
	
	/**
	 * 得到保底道具名字
	 * @return
	 */
	public String getProtectStoneName()
	{
		if( this.cur_protect_stone_num==0 )
		{
			return "保底材料";
		}
		PropVO protect_stone = PropCache.getPropById(protect_stone_id);
		return protect_stone.getPropName();
	}
	
	/**
	 * 得到保底道具数量
	 * @return
	 */
	public int getProtectStoneNum()
	{
		return cur_protect_stone_num;
	}
	
	/**
	 * 初始化
	 */
	protected void init()
	{
		this.equip = null;
		this.stone_rate = 0;
		
		this.rate_stone_id = 0;
		this.rate_stone_num = 0;
		
		material_id_list = null;
		need_materials_num = null;
		cur_materials_num = null;
		
		this.cur_protect_stone_num = 0;
		cur_rate_stone_num = 0;
	}
	public String getActionName()
	{
		return actionName;
	}
	public int getCurRateStoneNum()
	{
		return cur_rate_stone_num;
	}
	public PlayerEquipVO getEquip()
	{
		return this.equip;
	}
}
