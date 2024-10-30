package com.ls.ben.vo.info.npc;

import com.ls.model.user.RoleEntity;

/**
 * ����:npcshop
 * @author ��˧
 * 2:53:43 PM
 */
public class NpcShopVO {

	/**NPC����id*/
	private int npcshopId;
	/**�˵�id*/
	private int npcId;
	/**��Ʒ����,��ʾ���ĸ���*/
	private int goodsType;
	/**��Ʒid*/
	private int goodsId;
	/**��Ʒ����*/
	private String goodsName;
	/**��Ǯ*/
	private int npcShopGoodsbuy;
	
	/**
	 * �õ���ҹ����ʵ�ʼ�Ǯ
	 * @return
	 */
	public int getPrice( RoleEntity roleInfo )
	{
		int price = npcShopGoodsbuy;
		int tax = roleInfo.getBasicInfo().getSceneInfo().getMap().getBarea().getTax();
		price *= (100 + tax) / 100;
		
		if( roleInfo.isRedname() )//���������Ϸ�̵깺��ҩƷ�����ᡢװ���ȣ��Լ�����װ���۸񷭱�
		{
			price *= 2;
		}
		return price;
	}
	
	public int getNpcshopId() {
		return npcshopId;
	}
	public void setNpcshopId(int npcshopId) {
		this.npcshopId = npcshopId;
	}
	public int getNpcId() {
		return npcId;
	}
	public void setNpcId(int npcId) {
		this.npcId = npcId;
	}
	public int getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getNpcShopGoodsbuy() {
		return npcShopGoodsbuy;
	}
	public void setNpcShopGoodsbuy(int npcShopGoodsbuy) {
		this.npcShopGoodsbuy = npcShopGoodsbuy;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}
