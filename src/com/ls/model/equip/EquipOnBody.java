package com.ls.model.equip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.goods.equip.SuitDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.vo.goods.equip.SuitVO;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.model.property.PropertyModel;
import com.ls.model.user.UserBase;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.WuXing;
import com.ls.pub.util.MathUtil;
import com.lw.dao.specialprop.SpecialPropDAO;
import com.lw.vo.specialprop.SpecialPropVO;

/**
 * 功能：身上穿的装备模型
 * 
 * @author ls Aug 13, 2009 2:55:40 PM
 */
public class EquipOnBody extends UserBase
{
	// 当前身上穿的装备<装备主键(pwPk)，装备信息>
	private LinkedHashMap<Integer, PlayerEquipVO> equips_on_body = new LinkedHashMap<Integer, PlayerEquipVO>(PositionOnEquip.EQUIP_NUM_ON_BODY);
	// 按部位穿的装备<穿着部位，装备信息>
	private LinkedHashMap<Integer, PlayerEquipVO> equips_on_position = new LinkedHashMap<Integer, PlayerEquipVO>(PositionOnEquip.EQUIP_NUM_ON_BODY);

	PropertyModel equipPropertyModel = new PropertyModel();// 装备属性模型
	PropertyModel suitPropertyModel = new PropertyModel();// 套装属性模型
	//身上穿的套装数量<套装id,套装数量>
	private Map<Integer,Integer> suit_equip_num = new HashMap<Integer,Integer>(PositionOnEquip.EQUIP_NUM_ON_BODY);
	//身上穿的五行装备的数量<五行类型,对应五行套装数量>
	private Map<Integer,Integer> wx_num = new HashMap<Integer,Integer>(5);

	/**
	 * 角色登陆时加载身上穿的装备
	 */
	public EquipOnBody(int p_pk )
	{
		super(p_pk);
			wx_num.put(WuXing.JIN, 0);
			wx_num.put(WuXing.MU, 0);
			wx_num.put(WuXing.SHUI, 0);
			wx_num.put(WuXing.HUO, 0);
			wx_num.put(WuXing.TU, 0);

			PlayerEquipDao playerEquipDao = new PlayerEquipDao();
			List<PlayerEquipVO> equips = playerEquipDao
					.getEquipListOnBody(p_pk);
			for (PlayerEquipVO equip : equips)
			{
				put(equip);// 把当前装备放入身上
				//onEquipSpecialContent(equip.getPPk(), equip.getSpecialcontent());
			}
			loadSuitProperty();
			//加载婚戒装备
			SpecialPropDAO dao = new SpecialPropDAO();
			SpecialPropVO vo = dao.getEquipProp(this.p_pk);
			if (vo != null)
			{
				PropVO propVO = PropCache.getPropById(vo.getPropid());
				if( propVO!=null && propVO.getPropOperate2() != null && !propVO.getPropOperate2().equals("") && propVO.getPropClass() == PropType.JIEHUN_JIEZHI){
					String[] constant = propVO.getPropOperate2().split("-");
					equipPropertyModel.updatePropertyByType(Integer.parseInt(constant[0]),PropertyModel.ATTACK_UP);
					equipPropertyModel.updatePropertyByType(Integer.parseInt(constant[0]),PropertyModel.ATTACK_LOW);
					equipPropertyModel.updatePropertyByType(Integer.parseInt(constant[1]),PropertyModel.DEFEND_UP);
					equipPropertyModel.updatePropertyByType(Integer.parseInt(constant[1]),PropertyModel.DEFEND_LOW);
				}
			}
	}
	
	/**
	 * 清空身上的装备
	 */
	public void clear()
	{
		equips_on_body.clear();
		equips_on_position.clear();
		equipPropertyModel.init();
		suitPropertyModel.init();
		suit_equip_num.clear();
		wx_num.clear();
	}
	
	/**
	 * 得到跟jewelry同一id饰品的数量
	 * @param jewelry
	 * @return
	 */
	private int getSameJewelryIdNum(PlayerEquipVO jewelry)
	{
		if( jewelry==null )
		{
			return 0;
		}
		int same_id_num = 0;
		for( int position=PositionOnEquip.JEWELRY_1;position<=PositionOnEquip.JEWELRY_3;position++)
		{
			PlayerEquipVO cur_jewelry = this.equips_on_position.get(position);
			if( cur_jewelry!=null && jewelry.getEquipId()==cur_jewelry.getEquipId())
			{
				same_id_num++;
			}
		}
		return same_id_num;
	}
	
	/**
	 * 更新套装数量
	 * @param equip
	 * @param update_type    表示加或减
	 */
	private void updateSuitNum(PlayerEquipVO equip ,int update_type)
	{
		if( equip==null || equip.getGameEquip().getSuitId()==0 )
		{
			return ;
		}
		
		//如果装备是饰品，则同一个id的饰品算一件
		if( equip.getGameEquip().getType()==Equip.JEWELRY)
		{
			int same_id_num = this.getSameJewelryIdNum(equip);//同一id的饰品的数量
			if( update_type>0 && same_id_num>=2 )
			{
				//穿两件以上同一id的饰品
				return;
			}
			else if( update_type<0 && same_id_num>=1 )
			{
				//脱装备，还有同一id的饰品
				return;
			}
		}
		
		int suit_id = equip.getGameEquip().getSuitId();
		Integer suit_num = suit_equip_num.get(suit_id);
		if( suit_num==null )
		{
			suit_num = 0;
		}
		suit_num+=update_type;
		if( suit_num>0 )
		{
			suit_equip_num.put(suit_id, suit_num);
		}
		else
		{
			suit_equip_num.remove(suit_id);
		}
	}
	
	/**
	 * 得到套装效果描述
	 * @return
	 */
	public String getSuitEffectDes()
	{
		StringBuffer suit_effect_describe = new StringBuffer();
		
		Set<Integer> suit_id_list = suit_equip_num.keySet();
		if( suit_id_list!=null && suit_id_list.size()>0 )
		{
			SuitDao suitDao = new SuitDao();
			for( Integer suit_id:suit_id_list)
			{
				SuitVO suit = suitDao.getById(suit_id);
				
				int suit_num = suit_equip_num.get(suit_id);
				if( suit_num>=2)
				{
					suit_effect_describe.append(suit.getSuitName()).append(":<br/>");
					suit_effect_describe.append("2件套:").append(suit.getTwoEffectsDescribe()).append("<br/>");
				}
				if( suit_num>=3)
				{
					suit_effect_describe.append("3件套:").append(suit.getThreeEffectsDescribe()).append("<br/>");
				}
				if( suit_num>=4)
				{
					suit_effect_describe.append("4件套:").append(suit.getFourEffectsDescribe()).append("<br/>");
				}
			}
		}
		return suit_effect_describe.toString();
	}
	
	/**
	 * 得到身上穿的装备五行附加描述
	 */
	public String getWXAppendDes()
	{
		StringBuffer result = new StringBuffer();

		Set<Integer> wx_type_list = this.wx_num.keySet();
		if( wx_type_list.size()>0 )
		{
			for(Integer wx_type:wx_type_list)
			{
				int cur_wx_num = this.wx_num.get(wx_type);
				if( cur_wx_num==PositionOnEquip.EQUIP_NUM_ON_BODY)
				{
					switch (wx_type)
					{
						case WuXing.JIN:result.append("全金属性附加:攻击力提高10%");break;
						case WuXing.MU:result.append("全木属性附加:气血提高25%");break;
						case WuXing.SHUI:result.append("全水属性附加:内力提高10%");break;
						case WuXing.HUO:result.append("全火属性附加:暴击率提高8%");break;
						case WuXing.TU:result.append("全土属性附加:防御提高20%");break;
					}
					result.append("<br/>");
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * 得到身上所穿的装备
	 */
	public List<PlayerEquipVO> getEquipList()
	{
		return new ArrayList<PlayerEquipVO>(equips_on_position.values());
	}

	/**
	 * 根据类型得到身上的装备的
	 * @return
	 */
	public PlayerEquipVO getByPositin( int position)
	{
		return this.equips_on_position.get(position);
	}
	
	/**
	 * 储存装备
	 * @throws Exception
	 */
	private void put(PlayerEquipVO equip)
	{
		if (equip == null)
		{
			return;
		}

		int position= equip.getWType();
		equip.setWType(position);

		equips_on_position.put(position, equip);
		equips_on_body.put(equip.getPwPk(), equip);
		if (equip.isEffected() == true)// 如果穿上的装备有效，装备还有效在更改属性
		{
			updatePropertyByEquip(equip, PropertyModel.ADD_PROPERTY);
		}
	}

	/**
	 * 脱掉指定位置的装备
	 * @throws Exception
	 */
	private void remove(PlayerEquipVO equip,int position)
	{
		if (equip == null)
		{
			return;
		}

		equip.setWType(0);
		equips_on_position.remove(position);
		equips_on_body.remove(equip.getPwPk());
		if (equip.isEffected() == true)// 如果脱下装备时，装备还有效在更改属性
		{
			updatePropertyByEquip(equip, PropertyModel.SUB_PROPERTY);
		}
	}

	/**
	 * 修理所有装备的耐久为全满的费用
	 */
	public int getMaintainAllFee()
	{
		int fee = 0;
		List<PlayerEquipVO> equip_list = new ArrayList<PlayerEquipVO>(equips_on_position.values());
		if (equip_list != null && equip_list.size() != 0)
		{
			for (PlayerEquipVO equip : equip_list)
			{
				fee+=equip.getMaintainFee();
			}
		}
		if( this.getRoleEntity().isRedname() )
		{
			//红名玩家游戏商店购买药品、卷轴、装备等，以及修理装备价格翻倍
			fee*=2;
		}
		
		return fee;
	}
	
	/**
	 * 修理所有装备消耗耐久的总数
	 * @return
	 */
	public int getConsumeEndureTotal()
	{
		int consume_total = 0;
		List<PlayerEquipVO> equip_list = new ArrayList<PlayerEquipVO>(equips_on_position.values());
		if (equip_list != null && equip_list.size() != 0)
		{
			for (PlayerEquipVO equip : equip_list)
			{
				if (equip.isEffected() == true)// 只能修理装备耐久不为0的（装备有效的才能修理）
				{
					consume_total+=equip.getConsumeEndure();
				}
			}
		}
		return consume_total;
	}
	
	/**
	 * 修理所有装备消耗耐久的总数
	 * @param endure              用endure个点数来修复身上的装备
	 * @return                    返回剩余还没修复的耐久点数
	 */
	public int maintainAllByEndure( int endure )
	{
		int left_un_maintain_endure = -1;
		if( endure<=0 )
			return -1;
		List<PlayerEquipVO> equip_list = new ArrayList<PlayerEquipVO>(equips_on_position.values());
		if (equip_list != null && equip_list.size() != 0)
		{
			for (PlayerEquipVO equip : equip_list)
			{
				if( endure<=0 )
				{
					left_un_maintain_endure+=equip.getConsumeEndure();
				}
				if (equip.isEffected() == true)// 只能修理装备耐久不为0的（装备有效的才能修理）
				{
					if( endure>=equip.getConsumeEndure())
					{
						equip.setCurEndure(equip.getMaxEndure());
						endure-=equip.getConsumeEndure();
					}
					else
					{
						left_un_maintain_endure = equip.getConsumeEndure()-endure;
						equip.setCurEndure(endure);
						
					}
					equip.save();
				}
			}
		}
		return left_un_maintain_endure;
	}
	
	/**
	 * 修理所有装备的耐久为全满
	 */
	public void maintainAll()
	{
		int fee = getMaintainAllFee();
		this.getRoleEntity().getBasicInfo().addCopper(-fee);
		
		List<PlayerEquipVO> equip_list = new ArrayList<PlayerEquipVO>(equips_on_position.values());
		if (equip_list != null && equip_list.size() != 0)
		{
			for (PlayerEquipVO equip : equip_list)
			{
				if (equip.isEffected() == true)// 只能修理装备耐久不为0的（装备有效的才能修理）
				{
					equip.setCurEndure(equip.getMaxEndure());
					equip.save();
				}
			}
		}
	}

	/**
	 * 修理单个装备指定耐久点数
	 */
	public void maintainOneEquip(int pw_pk, int maintain_point)
	{
		PlayerEquipVO cur_equip = equips_on_body.get(pw_pk);
		if (cur_equip != null && cur_equip.isEffected() == true)// 只能修理装备耐久不为0的（装备有效的才能修理）
		{
			if (cur_equip.getEquipType() != Equip.JEWELRY)
			{
				cur_equip.setCurEndure(cur_equip.getCurEndure()+ maintain_point);
			}
		}
	}

	/**
	 * PK死亡时，消耗耐久上限(随机抽取一件身上穿的)
	 * 消耗点数（1，3的随机数）
	 * return 返回描述信息
	 */
	public String consumeMaxEndure()
	{
		List<PlayerEquipVO> equip_list = new ArrayList<PlayerEquipVO>(equips_on_position.values());
		
		if (equip_list != null && equip_list.size() > 0)
		{
			int consume_point =0;
			if( this.getRoleEntity().getBasicInfo().getEvilValue()>0 )
			{
				consume_point = 5;//红黄名玩家PK死亡，耐久上限扣除为5点
			}
			else
			{
				consume_point = MathUtil.getRandomBetweenXY(1,3);//消耗点数（1，3的随机数）
			}
			int random_index = MathUtil.getRandomBetweenXY(0,equip_list.size()-1);
			
			PlayerEquipVO random_equip = equip_list.get(random_index);
			boolean isEffectBeforeConsume = random_equip.isEffected();// 更新耐久之前判断装备是否有效
			random_equip.consumeMaxEndure(consume_point);//消耗耐久上限
			if (isEffectBeforeConsume == true && random_equip.isEffected() == false)// 更新前有效且更新后无效，需要减掉该装备的属性
			{
				updatePropertyByEquip(random_equip, PropertyModel.SUB_PROPERTY);
			}
			return random_equip.getGameEquip().getName()+"消耗耐久上限"+consume_point+"点";
		}
		return "";
	}
	/**
	 * 消耗耐久
	 * 
	 * @return true表示跟新装备属性，false表示没加载装备属性
	 */
	private boolean consumeEndure(PlayerEquipVO equip)
	{
		boolean result = false;
		if (equip == null)
		{
			return false;
		}
		boolean isEffectBeforeConsume = equip.isEffected();// 更新耐久之前判断装备是否有效
		if (equip.getCurEndure() > 0)
		{
			equip.setCurEndure(equip.getCurEndure() - 1);
		}
		
		if (isEffectBeforeConsume == true && equip.isEffected() == false)// 更新前有效且更新后无效，需要减掉该装备的属性
		{
			updatePropertyByEquip(equip, PropertyModel.SUB_PROPERTY);
			result = true;
		}
		return result;
	}

	/**
	 * 使用武器(消耗武器耐久)
	 */
	public void useArm()
	{
		// 消耗武器耐久
		PlayerEquipVO arm_info = equips_on_position.get(PositionOnEquip.WEAPON);
		if (arm_info != null)
		{
			boolean isUpdateProperty = consumeEndure(arm_info);
			// System.out.println("当前武器耐久为："+arm_info.getWDuraConsume());
			if (isUpdateProperty == true)
			{
				loadSuitPropertyByEquip(arm_info);
			}
		}
	}

	/**
	 * 使用装备(消耗装备耐久)
	 */
	public void useEquip()
	{
		// 消耗身上装备耐久

		List<PlayerEquipVO> equip_list = new ArrayList<PlayerEquipVO>(equips_on_position.values());

		if (equip_list != null && equip_list.size() > 0)
		{
			boolean isReloadSuitPropertys = false;// 是否有无效的装备,false表示不重载

			for (PlayerEquipVO equip : equip_list)
			{
				if (equip.getEquipType()!=Equip.WEAPON )//不是武器
				{
					boolean isUpdateProperty = consumeEndure(equip);// 更新耐久

					if (isUpdateProperty == true)// 当前装备已无效
					{
						isReloadSuitPropertys = true;
					}
				}
			}

			if (isReloadSuitPropertys == true)
			{
				// 有无效的装备，则重载套装属性
				loadSuitProperty();
			}
		}
	}


	/**
	 * 穿装备
	 *//*
	public void puton(int pw_pk)
	{
		GoodsService goodsService = new GoodsService();
		PlayerEquipVO equip_info = goodsService.getEquipByID(pw_pk);
		puton(equip_info);
	}*/
	
	/**
	 * 穿装备
	 */
	public PlayerEquipVO puton(PlayerEquipVO equip_info )
	{
		int position = getPosition(equip_info);
		return this.puton(equip_info, position);
	}
	

	/**
	 * 穿装备(指定位置)
	 */
	public PlayerEquipVO puton(PlayerEquipVO equip_info,int position )
	{
		if (equip_info != null)
		{
			PlayerEquipVO takeoff_equip = takeoff(position);//脱掉相应位置的装备
			equip_info.setWType(position);//改变装备的位置
			put(equip_info);// 放入身上
			loadSuitPropertyByEquip(equip_info);// 重载套装属性
			return takeoff_equip;
		}
		return null;
	}

	/**
	 * 脱装备
	 */
	public PlayerEquipVO takeoff(int position)
	{
		PlayerEquipVO equip_info = this.equips_on_position.get(position);
		if( equip_info!=null )
		{
			remove(equip_info,position);// 从身上移除
			loadSuitPropertyByEquip(equip_info);// 重载套装属性
			return equip_info;
		}
		return null;
	}
	
	/**
	 * 脱装备
	 */
	public PlayerEquipVO takeoff(PlayerEquipVO equip_info)
	{
		if( equip_info!=null )
		{
			int position = -1;
			//找到该装备在身上的位置
			Set<Integer> position_set = equips_on_position.keySet();
			for (Integer cur_position : position_set)
			{
				if( equips_on_position.get(cur_position).getPwPk()==equip_info.getPwPk() )
				{
					position = cur_position;
					break;
				}
			}
			
			return this.takeoff(position);
		}
		return null;
	}

	/**
	 * 脱装备
	 * return 脱下的装备
	 *//*
	public PlayerEquipVO takeoff(PlayerEquipVO equip_info,int position)
	{
		if (equip_info != null)
		{
			remove(equip_info,position);// 从身上移除
			loadSuitPropertyByEquip(equip_info);// 重载套装属性
			return equip_info;
		}
		return null;
	}*/

	/**
	 * 给玩家加载附加属性
	 */
	public void loadEquipProterty(PartInfoVO player)
	{
		if (player == null)
		{
			return;
		}

		equipPropertyModel.loadPropertys(player);
		suitPropertyModel.loadPropertys(player);
		loadWXProterty(player);
	}

	/**
	 * 给玩家加载附加属性
	 */
	private void loadWXProterty(PartInfoVO player)
	{
		if (player == null)
		{
			return;
		}
		Set<Integer> wx_type_list = this.wx_num.keySet();
		for(Integer wx_type:wx_type_list)
		{
			int cur_wx_num = this.wx_num.get(wx_type);
			if( cur_wx_num==PositionOnEquip.EQUIP_NUM_ON_BODY)
			{
				switch (wx_type)
				{
					case WuXing.JIN:// 攻击力提高10%
						int gj = (int) ((1.0 * (player.getPZbgjXiao() + player.getPZbgjDa()) / 2 * 10) / 100);
						player.setPZbgjXiao(player.getPZbgjXiao() + gj);
						player.setPZbgjDa(player.getPZbgjDa() + gj);
						break;
					case WuXing.MU:// 气血提高25%
						int hp = (int) (1.0 * (player.getPUpHp() * 25) / 100);
						player.setPUpHp(player.getPUpHp() + hp);
						break;
					case WuXing.SHUI:// 内力提高10%
						int mp = (int) (1.0 * (player.getPUpMp() * 10) / 100);
						player.setPUpMp(player.getPUpMp() + mp);
						break;
					case WuXing.HUO:// 暴击率提高8%
						double bj = MathUtil.getDoubleValueByAddRate(player.getDropMultiple(),  8);
						player.setDropMultiple(bj);
						break;
					case WuXing.TU:// 防御提高20%
						int fy = (int) ((1.0 * (player.getPZbfyXiao() + player.getPZbfyDa()) / 2 * 20) / 100);
						player.setPZbfyXiao(player.getPZbfyXiao() + fy);
						player.setPZbfyDa(player.getPZbfyDa() + fy);
						break;
				}
			}
		}
	}

	/**
	 * 更新对应五行数量
	 * 
	 * @param wx_type
	 */
	private void updateWXNum(PlayerEquipVO equip_info, int update_type)
	{
		Map<Integer,String> equip_wx = equip_info.getWuxing().getWxType();
		Set<Integer> wx_type_list = equip_wx.keySet();
		for(Integer wx_type:wx_type_list)
		{
			if( wx_type==0 )
			{
				continue;
			}
			Integer cur_wx_num = this.wx_num.get(wx_type);
			if( cur_wx_num==null )
			{
				cur_wx_num = 0;
			}
			cur_wx_num+=update_type;
			if( cur_wx_num>0)
			{
				this.wx_num.put(wx_type, cur_wx_num);
			}
			else
			{
				this.wx_num.remove(wx_type);
			}
		}
	}


	/**
	 * 根据装备更新玩家身上的装备属性
	 * 
	 * @param equip_info
	 * @param update_type
	 *            更新类型1表示加，-1表示减
	 */
	private void updatePropertyByEquip(PlayerEquipVO equip_info, int update_type)
	{
		if (equip_info != null)// 装备耐久在10以下属性无效
		{
			if (update_type == PropertyModel.ADD_PROPERTY
					&& equip_info.getCurEndure() < 10)// 如果该装备无效，则不加成效果
			{
				return;
			}
			int Wgjda = equip_info.getMaxAtt();
			int Wgjxiao = equip_info.getMinAtt();
			int Wfyda = equip_info.getMaxDef();
			int Wfyxiao = equip_info.getMinDef();

			equipPropertyModel.updatePropertyByType(Wgjda * update_type,
					PropertyModel.ATTACK_UP);
			equipPropertyModel.updatePropertyByType(Wgjxiao * update_type,
					PropertyModel.ATTACK_LOW);

			equipPropertyModel.updatePropertyByType(Wfyda * update_type,
					PropertyModel.DEFEND_UP);
			equipPropertyModel.updatePropertyByType(Wfyxiao * update_type,
					PropertyModel.DEFEND_LOW);

			equipPropertyModel.updatePropertyByType(equip_info.getWHp()
					* update_type, PropertyModel.HP_UP);
			equipPropertyModel.updatePropertyByType(equip_info.getWMp()
					* update_type, PropertyModel.MP_UP);

			equipPropertyModel.updatePropertyByType(equip_info.getWuxing().getJinGj()
					* update_type, PropertyModel.JIN_ATTACK);
			equipPropertyModel.updatePropertyByType(equip_info.getWuxing().getMuGj()
					* update_type, PropertyModel.MU_ATTACK);
			equipPropertyModel.updatePropertyByType(equip_info.getWuxing().getShuiGj()
					* update_type, PropertyModel.SHUI_ATTACK);
			equipPropertyModel.updatePropertyByType(equip_info.getWuxing().getHuoGj()
					* update_type, PropertyModel.HUO_ATTACK);
			equipPropertyModel.updatePropertyByType(equip_info.getWuxing().getTuGj()
					* update_type, PropertyModel.TU_ATTACK);

			equipPropertyModel.updatePropertyByType(equip_info.getWuxing().getJinFy()
					* update_type, PropertyModel.JIN_DEFENCE);
			equipPropertyModel.updatePropertyByType(equip_info.getWuxing().getMuFy()
					* update_type, PropertyModel.MU_DEFENCE);
			equipPropertyModel.updatePropertyByType(equip_info.getWuxing().getShuiFy()
					* update_type, PropertyModel.SHUI_DEFENCE);
			equipPropertyModel.updatePropertyByType(equip_info.getWuxing().getHuoFy()
					* update_type, PropertyModel.HUO_DEFENCE);
			equipPropertyModel.updatePropertyByType(equip_info.getWuxing().getTuFy()
					* update_type, PropertyModel.TU_DEFENCE);

			updateWXNum(equip_info, update_type);
			this.updateSuitNum(equip_info, update_type);
		}
	}
	

	/**
	 * 加载套装属性
	 */
	private void loadSuitPropertyByEquip(PlayerEquipVO equip_info)
	{
		if (equip_info != null && equip_info.getSuitId() != 0)
		{
			loadSuitProperty();
		}
	}

	/**
	 * 加载套装属性
	 */
	private void loadSuitProperty()
	{
		suitPropertyModel.init();// 初始化套装的属性值
		Set<Integer> suit_id_list = suit_equip_num.keySet();
		if( suit_id_list!=null && suit_id_list.size()>0 )
		{
			SuitDao suitDao = new SuitDao();
			
			for( Integer suit_id:suit_id_list)
			{
				SuitVO suit = suitDao.getById(suit_id);
				if( suit!=null )
				{
					String suit_effect_str = "";//套装效果字符串
					int suit_num = suit_equip_num.get(suit_id);
					if( suit_num>=2)
					{
						suit_effect_str = suit.getTwoEffects();
					}
					if( suit_num>=3)
					{
						suit_effect_str = suit_effect_str+"-"+suit.getThreeEffects();
					}
					if( suit_num>=4)
					{
						suit_effect_str = suit_effect_str+"-"+suit.getFourEffects();
					}
					
					suitPropertyModel.appendPropertyByAttriStr(suit_effect_str,PropertyModel.ADD_PROPERTY);
				}
			}
		}
	}

	
	
	/**
	 * 得到当前装备穿在身上的位置
	 * @param equip
	 * @return
	 * @throws Exception 
	 */
	private int getPosition( PlayerEquipVO equip )
	{
		return this.getPosition(equip.getEquipType());
	}
	
	/**
	 * 根据装备类型得到装备穿在身上的位置
	 * @param equip
	 * @return
	 * @throws Exception 
	 */
	private int getPosition( int equip_type )
	{
		if( equip_type==Equip.JEWELRY )
		{
			//查找饰品所穿的位置
			for( int j_postion=PositionOnEquip.JEWELRY_1;j_postion<=PositionOnEquip.JEWELRY_3;j_postion++)
			{
				if( this.equips_on_position.get(j_postion)==null )
				{
					return j_postion;
				}
			}
			//如果无剩余可以放置饰品的位置，则替换第一个饰品
			return PositionOnEquip.JEWELRY_1;
		}
		
		return equip_type;
	}
	
	public void getHunJieContent(int i,int p_pk,int propid){
		PropVO propVO = PropCache.getPropById(propid);
		if(propVO.getPropClass() == PropType.JIEHUN_JIEZHI){
			if(propVO.getPropOperate2() != null || !propVO.getPropOperate2().equals("")){
				String[] constant = propVO.getPropOperate2().split("-");
				equipPropertyModel.updatePropertyByType(Integer.parseInt(constant[0])*i,PropertyModel.ATTACK_UP);
				equipPropertyModel.updatePropertyByType(Integer.parseInt(constant[0])*i,PropertyModel.ATTACK_LOW);
				equipPropertyModel.updatePropertyByType(Integer.parseInt(constant[1])*i,PropertyModel.DEFEND_UP);
				equipPropertyModel.updatePropertyByType(Integer.parseInt(constant[1])*i,PropertyModel.DEFEND_LOW);
			}	
		}
	}
}
