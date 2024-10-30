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
 * 瀹濈煶鍔犲伐
 */
public class StoneProduct extends UserBase 
{
	
	private static int material1_id = 3;//鍗囩骇鐭砳d
	
	private int stone_id = 0;//瑕佸姞宸ョ殑瀹濈煶id
	private int stone_num = 0;//瑕佸崌绾х殑瀹濈煶鏁伴噺
	private int cur_stone_num = 0;//鐜版湁瀹濈煶鐨勬暟閲�
	
	private int cur_material1_num = 0;//鍗囩骇鐭崇殑鏁伴噺
	
	private  int rate_stone_id;//鎻愬崌鎴愬姛鐜囨潗鏂檌d
	private  int rate_stone_num;//鎻愬崌鎴愬姛鐜囨潗鏂欐暟閲�
	
	private int stone_rate=0;//褰撳墠瀹濈煶鎴愬姛鐜�
	
	public StoneProduct(int pPk )
	{
		super(pPk);
	}
	
	/**
	 * 鍒濆�鍖�
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
	 * 閫夋嫨瑕佸崌绾х殑瀹濈煶
	 * @param equip
	 * @return
	 */
	public String selectStone( int select_stone_id,int upgrade_num )
	{
		String hint = null;
		
		if( upgrade_num<=0 || upgrade_num%2!=0 )
		{
			return "瀹濈煶蹇呴』涓哄伓鏁�";
		}
		
		GoodsService goodsService = new GoodsService();
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		
		int stone_grade = getStoneGrade(select_stone_id);
		if( stone_grade>=10 )
		{
			return "瀹濈煶宸叉槸鏈€楂樼瓑绾�,涓嶈兘鍗囩骇";
		}
		
		int have_stone_num = goodsService.getPropNum(p_pk, select_stone_id);
		
		if( have_stone_num<upgrade_num )
		{
			return "浣犳病鏈夎冻澶熺殑瀹濈煶";
		}
		
		cur_material1_num = playerPropGroupDao.getPropNumByByPropID(p_pk,material1_id);//鐜版湁鍗囩骇鐭崇殑鏁伴噺
		
		this.stone_id = select_stone_id;
		this.stone_num = upgrade_num;
		this.cur_stone_num = have_stone_num;
		return hint;
	}
	
	
	/**
	 * 鍗囩骇
	 */
	public String upgrade()
	{
		String hint = null;
		//鍒ゆ柇鏄�惁鑳藉崌绾�
		if( this.stone_id==0 )
		{
			return "璇烽€夋嫨瑕佸崌绾х殑瀹濈煶";
		}
		String next_grade_stone_id = PropCache.getPropById(this.stone_id).getPropOperate2();//涓嬩竴绾у疂鐭砳d
		if( next_grade_stone_id==null && next_grade_stone_id.equals(""))
		{
			return "瀹濈煶宸蹭笉鑳藉崌绾�";
		}
		
		//鍒ゆ柇鍗囩骇鏉愭枡鏄�惁澶�
		if( this.getCurMaterial1Num()<this.getMaterial1Num() )
		{
			return "瀵逛笉璧凤紝鎮ㄥ崌绾ф墍闇€鐨勬潗鏂欎笉瓒筹紝鏃犳硶鍗囩骇锛�";
		}
		//鍒ゆ柇鏄�惁鏈夎冻澶熺殑閽�
		if( this.getRoleEntity().getBasicInfo().getCopper()<this.getNeedMoney() )
		{
			return "浣犵殑閾朵袱涓嶈冻锛屼笉鑳藉崌绾�";
		}
		
		//娑堣€楃墿鍝�
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(p_pk, this.material1_id, this.getMaterial1Num(),GameLogManager.R_MATERIAL_CONSUME);
		goodsService.removeProps(p_pk, this.stone_id, this.getStoneNum(),GameLogManager.R_MATERIAL_CONSUME);
		
		//鎵ｉ櫎鍗囩骇璐圭敤
		this.getRoleEntity().getBasicInfo().addCopper(-this.getNeedMoney());
		
		//瀹濈煶鍗囩骇
		if( MathUtil.isAppearByPercentage(this.getSuccessRate()) )//鍗囩骇鎴愬姛
		{
			goodsService.putPropToWrap(p_pk, Integer.parseInt(next_grade_stone_id), this.stone_num/2,GameLogManager.G_UPGRADE);
			hint = "鎭�枩鎮�紝瀹濈煶鍚堟垚鎴愬姛锛岃�鍐嶆帴鍐嶅帀锛�";
		}
		else//鍗囩骇澶辫触涓嶆秷鑰楀疂鐭�
		{
			hint = "澶�笉骞镐簡锛屾偍瀹濈煶鍚堟垚澶辫触锛岃�缁х画鍔�姏锛�";
		}
		
		this.init();
		return hint;
	}
	
	
	/**
	 * 寰楀埌瀹濈煶鍚嶅瓧
	 * @return
	 */
	public String getStoneName()
	{
		if( stone_id==0 )
		{
			return "閫夋嫨瑕佸崌绾х殑瀹濈煶";
		}
		PropVO rate_stone = PropCache.getPropById(this.stone_id);
		return rate_stone.getPropName();
	}
	
	/**
	 * 寰楀埌瑕佸崌绾х殑瀹濈煶鏁伴噺
	 * @return
	 */
	public int getStoneNum()
	{
		return this.stone_num;
	}
	/**
	 * 寰楀埌瑕佸崌绾х殑瀹濈煶鏁伴噺
	 * @return
	 */
	public int getCurStoneNum()
	{
		return this.cur_stone_num;
	}
	
	
	/**
	 * 闇€瑕佸崌绾х煶鐨勬暟閲�
	 */
	public int getMaterial1Num()
	{
		return stone_num/2;
	}
	/**
	 * 褰撳墠鍗囩骇鐭崇殑鏁伴噺
	 */
	public int getCurMaterial1Num()
	{
		return cur_material1_num;
	}
	
	/**
	 * 褰撳墠鎴愬姛鐜�
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
	 * 闇€瑕佺殑閽辨弿杩�
	 */
	public String getNeedMoneyDes()
	{
		return ExchangeUtil.getMoneyDes(this.getNeedMoney());
	}
	/**
	 * 闇€瑕佺殑閽�
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
	 * 寰楀埌瀹濈煶鐨勭瓑绾�
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
