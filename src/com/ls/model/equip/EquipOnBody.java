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
 * ���ܣ����ϴ���װ��ģ��
 * 
 * @author ls Aug 13, 2009 2:55:40 PM
 */
public class EquipOnBody extends UserBase
{
	// ��ǰ���ϴ���װ��<װ������(pwPk)��װ����Ϣ>
	private LinkedHashMap<Integer, PlayerEquipVO> equips_on_body = new LinkedHashMap<Integer, PlayerEquipVO>(PositionOnEquip.EQUIP_NUM_ON_BODY);
	// ����λ����װ��<���Ų�λ��װ����Ϣ>
	private LinkedHashMap<Integer, PlayerEquipVO> equips_on_position = new LinkedHashMap<Integer, PlayerEquipVO>(PositionOnEquip.EQUIP_NUM_ON_BODY);

	PropertyModel equipPropertyModel = new PropertyModel();// װ������ģ��
	PropertyModel suitPropertyModel = new PropertyModel();// ��װ����ģ��
	//���ϴ�����װ����<��װid,��װ����>
	private Map<Integer,Integer> suit_equip_num = new HashMap<Integer,Integer>(PositionOnEquip.EQUIP_NUM_ON_BODY);
	//���ϴ�������װ��������<��������,��Ӧ������װ����>
	private Map<Integer,Integer> wx_num = new HashMap<Integer,Integer>(5);

	/**
	 * ��ɫ��½ʱ�������ϴ���װ��
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
				put(equip);// �ѵ�ǰװ����������
				//onEquipSpecialContent(equip.getPPk(), equip.getSpecialcontent());
			}
			loadSuitProperty();
			//���ػ��װ��
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
	 * ������ϵ�װ��
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
	 * �õ���jewelryͬһid��Ʒ������
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
	 * ������װ����
	 * @param equip
	 * @param update_type    ��ʾ�ӻ��
	 */
	private void updateSuitNum(PlayerEquipVO equip ,int update_type)
	{
		if( equip==null || equip.getGameEquip().getSuitId()==0 )
		{
			return ;
		}
		
		//���װ������Ʒ����ͬһ��id����Ʒ��һ��
		if( equip.getGameEquip().getType()==Equip.JEWELRY)
		{
			int same_id_num = this.getSameJewelryIdNum(equip);//ͬһid����Ʒ������
			if( update_type>0 && same_id_num>=2 )
			{
				//����������ͬһid����Ʒ
				return;
			}
			else if( update_type<0 && same_id_num>=1 )
			{
				//��װ��������ͬһid����Ʒ
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
	 * �õ���װЧ������
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
					suit_effect_describe.append("2����:").append(suit.getTwoEffectsDescribe()).append("<br/>");
				}
				if( suit_num>=3)
				{
					suit_effect_describe.append("3����:").append(suit.getThreeEffectsDescribe()).append("<br/>");
				}
				if( suit_num>=4)
				{
					suit_effect_describe.append("4����:").append(suit.getFourEffectsDescribe()).append("<br/>");
				}
			}
		}
		return suit_effect_describe.toString();
	}
	
	/**
	 * �õ����ϴ���װ�����и�������
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
						case WuXing.JIN:result.append("ȫ�����Ը���:���������10%");break;
						case WuXing.MU:result.append("ȫľ���Ը���:��Ѫ���25%");break;
						case WuXing.SHUI:result.append("ȫˮ���Ը���:�������10%");break;
						case WuXing.HUO:result.append("ȫ�����Ը���:���������8%");break;
						case WuXing.TU:result.append("ȫ�����Ը���:�������20%");break;
					}
					result.append("<br/>");
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * �õ�����������װ��
	 */
	public List<PlayerEquipVO> getEquipList()
	{
		return new ArrayList<PlayerEquipVO>(equips_on_position.values());
	}

	/**
	 * �������͵õ����ϵ�װ����
	 * @return
	 */
	public PlayerEquipVO getByPositin( int position)
	{
		return this.equips_on_position.get(position);
	}
	
	/**
	 * ����װ��
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
		if (equip.isEffected() == true)// ������ϵ�װ����Ч��װ������Ч�ڸ�������
		{
			updatePropertyByEquip(equip, PropertyModel.ADD_PROPERTY);
		}
	}

	/**
	 * �ѵ�ָ��λ�õ�װ��
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
		if (equip.isEffected() == true)// �������װ��ʱ��װ������Ч�ڸ�������
		{
			updatePropertyByEquip(equip, PropertyModel.SUB_PROPERTY);
		}
	}

	/**
	 * ��������װ�����;�Ϊȫ���ķ���
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
			//���������Ϸ�̵깺��ҩƷ�����ᡢװ���ȣ��Լ�����װ���۸񷭱�
			fee*=2;
		}
		
		return fee;
	}
	
	/**
	 * ��������װ�������;õ�����
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
				if (equip.isEffected() == true)// ֻ������װ���;ò�Ϊ0�ģ�װ����Ч�Ĳ�������
				{
					consume_total+=equip.getConsumeEndure();
				}
			}
		}
		return consume_total;
	}
	
	/**
	 * ��������װ�������;õ�����
	 * @param endure              ��endure���������޸����ϵ�װ��
	 * @return                    ����ʣ�໹û�޸����;õ���
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
				if (equip.isEffected() == true)// ֻ������װ���;ò�Ϊ0�ģ�װ����Ч�Ĳ�������
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
	 * ��������װ�����;�Ϊȫ��
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
				if (equip.isEffected() == true)// ֻ������װ���;ò�Ϊ0�ģ�װ����Ч�Ĳ�������
				{
					equip.setCurEndure(equip.getMaxEndure());
					equip.save();
				}
			}
		}
	}

	/**
	 * ������װ��ָ���;õ���
	 */
	public void maintainOneEquip(int pw_pk, int maintain_point)
	{
		PlayerEquipVO cur_equip = equips_on_body.get(pw_pk);
		if (cur_equip != null && cur_equip.isEffected() == true)// ֻ������װ���;ò�Ϊ0�ģ�װ����Ч�Ĳ�������
		{
			if (cur_equip.getEquipType() != Equip.JEWELRY)
			{
				cur_equip.setCurEndure(cur_equip.getCurEndure()+ maintain_point);
			}
		}
	}

	/**
	 * PK����ʱ�������;�����(�����ȡһ�����ϴ���)
	 * ���ĵ�����1��3���������
	 * return ����������Ϣ
	 */
	public String consumeMaxEndure()
	{
		List<PlayerEquipVO> equip_list = new ArrayList<PlayerEquipVO>(equips_on_position.values());
		
		if (equip_list != null && equip_list.size() > 0)
		{
			int consume_point =0;
			if( this.getRoleEntity().getBasicInfo().getEvilValue()>0 )
			{
				consume_point = 5;//��������PK�������;����޿۳�Ϊ5��
			}
			else
			{
				consume_point = MathUtil.getRandomBetweenXY(1,3);//���ĵ�����1��3���������
			}
			int random_index = MathUtil.getRandomBetweenXY(0,equip_list.size()-1);
			
			PlayerEquipVO random_equip = equip_list.get(random_index);
			boolean isEffectBeforeConsume = random_equip.isEffected();// �����;�֮ǰ�ж�װ���Ƿ���Ч
			random_equip.consumeMaxEndure(consume_point);//�����;�����
			if (isEffectBeforeConsume == true && random_equip.isEffected() == false)// ����ǰ��Ч�Ҹ��º���Ч����Ҫ������װ��������
			{
				updatePropertyByEquip(random_equip, PropertyModel.SUB_PROPERTY);
			}
			return random_equip.getGameEquip().getName()+"�����;�����"+consume_point+"��";
		}
		return "";
	}
	/**
	 * �����;�
	 * 
	 * @return true��ʾ����װ�����ԣ�false��ʾû����װ������
	 */
	private boolean consumeEndure(PlayerEquipVO equip)
	{
		boolean result = false;
		if (equip == null)
		{
			return false;
		}
		boolean isEffectBeforeConsume = equip.isEffected();// �����;�֮ǰ�ж�װ���Ƿ���Ч
		if (equip.getCurEndure() > 0)
		{
			equip.setCurEndure(equip.getCurEndure() - 1);
		}
		
		if (isEffectBeforeConsume == true && equip.isEffected() == false)// ����ǰ��Ч�Ҹ��º���Ч����Ҫ������װ��������
		{
			updatePropertyByEquip(equip, PropertyModel.SUB_PROPERTY);
			result = true;
		}
		return result;
	}

	/**
	 * ʹ������(���������;�)
	 */
	public void useArm()
	{
		// ���������;�
		PlayerEquipVO arm_info = equips_on_position.get(PositionOnEquip.WEAPON);
		if (arm_info != null)
		{
			boolean isUpdateProperty = consumeEndure(arm_info);
			// System.out.println("��ǰ�����;�Ϊ��"+arm_info.getWDuraConsume());
			if (isUpdateProperty == true)
			{
				loadSuitPropertyByEquip(arm_info);
			}
		}
	}

	/**
	 * ʹ��װ��(����װ���;�)
	 */
	public void useEquip()
	{
		// ��������װ���;�

		List<PlayerEquipVO> equip_list = new ArrayList<PlayerEquipVO>(equips_on_position.values());

		if (equip_list != null && equip_list.size() > 0)
		{
			boolean isReloadSuitPropertys = false;// �Ƿ�����Ч��װ��,false��ʾ������

			for (PlayerEquipVO equip : equip_list)
			{
				if (equip.getEquipType()!=Equip.WEAPON )//��������
				{
					boolean isUpdateProperty = consumeEndure(equip);// �����;�

					if (isUpdateProperty == true)// ��ǰװ������Ч
					{
						isReloadSuitPropertys = true;
					}
				}
			}

			if (isReloadSuitPropertys == true)
			{
				// ����Ч��װ������������װ����
				loadSuitProperty();
			}
		}
	}


	/**
	 * ��װ��
	 *//*
	public void puton(int pw_pk)
	{
		GoodsService goodsService = new GoodsService();
		PlayerEquipVO equip_info = goodsService.getEquipByID(pw_pk);
		puton(equip_info);
	}*/
	
	/**
	 * ��װ��
	 */
	public PlayerEquipVO puton(PlayerEquipVO equip_info )
	{
		int position = getPosition(equip_info);
		return this.puton(equip_info, position);
	}
	

	/**
	 * ��װ��(ָ��λ��)
	 */
	public PlayerEquipVO puton(PlayerEquipVO equip_info,int position )
	{
		if (equip_info != null)
		{
			PlayerEquipVO takeoff_equip = takeoff(position);//�ѵ���Ӧλ�õ�װ��
			equip_info.setWType(position);//�ı�װ����λ��
			put(equip_info);// ��������
			loadSuitPropertyByEquip(equip_info);// ������װ����
			return takeoff_equip;
		}
		return null;
	}

	/**
	 * ��װ��
	 */
	public PlayerEquipVO takeoff(int position)
	{
		PlayerEquipVO equip_info = this.equips_on_position.get(position);
		if( equip_info!=null )
		{
			remove(equip_info,position);// �������Ƴ�
			loadSuitPropertyByEquip(equip_info);// ������װ����
			return equip_info;
		}
		return null;
	}
	
	/**
	 * ��װ��
	 */
	public PlayerEquipVO takeoff(PlayerEquipVO equip_info)
	{
		if( equip_info!=null )
		{
			int position = -1;
			//�ҵ���װ�������ϵ�λ��
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
	 * ��װ��
	 * return ���µ�װ��
	 *//*
	public PlayerEquipVO takeoff(PlayerEquipVO equip_info,int position)
	{
		if (equip_info != null)
		{
			remove(equip_info,position);// �������Ƴ�
			loadSuitPropertyByEquip(equip_info);// ������װ����
			return equip_info;
		}
		return null;
	}*/

	/**
	 * ����Ҽ��ظ�������
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
	 * ����Ҽ��ظ�������
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
					case WuXing.JIN:// ���������10%
						int gj = (int) ((1.0 * (player.getPZbgjXiao() + player.getPZbgjDa()) / 2 * 10) / 100);
						player.setPZbgjXiao(player.getPZbgjXiao() + gj);
						player.setPZbgjDa(player.getPZbgjDa() + gj);
						break;
					case WuXing.MU:// ��Ѫ���25%
						int hp = (int) (1.0 * (player.getPUpHp() * 25) / 100);
						player.setPUpHp(player.getPUpHp() + hp);
						break;
					case WuXing.SHUI:// �������10%
						int mp = (int) (1.0 * (player.getPUpMp() * 10) / 100);
						player.setPUpMp(player.getPUpMp() + mp);
						break;
					case WuXing.HUO:// ���������8%
						double bj = MathUtil.getDoubleValueByAddRate(player.getDropMultiple(),  8);
						player.setDropMultiple(bj);
						break;
					case WuXing.TU:// �������20%
						int fy = (int) ((1.0 * (player.getPZbfyXiao() + player.getPZbfyDa()) / 2 * 20) / 100);
						player.setPZbfyXiao(player.getPZbfyXiao() + fy);
						player.setPZbfyDa(player.getPZbfyDa() + fy);
						break;
				}
			}
		}
	}

	/**
	 * ���¶�Ӧ��������
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
	 * ����װ������������ϵ�װ������
	 * 
	 * @param equip_info
	 * @param update_type
	 *            ��������1��ʾ�ӣ�-1��ʾ��
	 */
	private void updatePropertyByEquip(PlayerEquipVO equip_info, int update_type)
	{
		if (equip_info != null)// װ���;���10����������Ч
		{
			if (update_type == PropertyModel.ADD_PROPERTY
					&& equip_info.getCurEndure() < 10)// �����װ����Ч���򲻼ӳ�Ч��
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
	 * ������װ����
	 */
	private void loadSuitPropertyByEquip(PlayerEquipVO equip_info)
	{
		if (equip_info != null && equip_info.getSuitId() != 0)
		{
			loadSuitProperty();
		}
	}

	/**
	 * ������װ����
	 */
	private void loadSuitProperty()
	{
		suitPropertyModel.init();// ��ʼ����װ������ֵ
		Set<Integer> suit_id_list = suit_equip_num.keySet();
		if( suit_id_list!=null && suit_id_list.size()>0 )
		{
			SuitDao suitDao = new SuitDao();
			
			for( Integer suit_id:suit_id_list)
			{
				SuitVO suit = suitDao.getById(suit_id);
				if( suit!=null )
				{
					String suit_effect_str = "";//��װЧ���ַ���
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
	 * �õ���ǰװ���������ϵ�λ��
	 * @param equip
	 * @return
	 * @throws Exception 
	 */
	private int getPosition( PlayerEquipVO equip )
	{
		return this.getPosition(equip.getEquipType());
	}
	
	/**
	 * ����װ�����͵õ�װ���������ϵ�λ��
	 * @param equip
	 * @return
	 * @throws Exception 
	 */
	private int getPosition( int equip_type )
	{
		if( equip_type==Equip.JEWELRY )
		{
			//������Ʒ������λ��
			for( int j_postion=PositionOnEquip.JEWELRY_1;j_postion<=PositionOnEquip.JEWELRY_3;j_postion++)
			{
				if( this.equips_on_position.get(j_postion)==null )
				{
					return j_postion;
				}
			}
			//�����ʣ����Է�����Ʒ��λ�ã����滻��һ����Ʒ
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
