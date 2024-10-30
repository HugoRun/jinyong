package com.ls.ben.vo.info.npc;

import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.MathUtil;
import com.web.service.expnpcdrop.ExpnpcdropService;

/**   npc�����
 * @author ��˧
 */
public class NpcdropVO {
	/** npc�����id */
	private int npcdropID;
	/** ����id */
	private int npcID;
	/** ��Ʒ���� */
	private int goodsType;
	/** ��Ʒid */
	private int goodsId;
	/** ��Ʒ����*/
	private String goodsName;
	/** �伸�� ɱ����npc�������Ʒ�ļ��ʣ�100��ʾ���֮һ���� */
	private int npcdropProbability;
	/** �󱩼��� �󱩱�ʾ������Ʒ���伸��Ϊԭ��10����10��ʾɱ����npc��10%�ļ��ʴ� */
	private int npcdropLuck;
	/**
	 * npcʵ�ʵ�����Ʒ������Χ�ַ���
	 * */
	private String npcDropNumStr;
	
	/**npcʵ�ʵ�����Ʒ������*/
	private int npcDropNum;
	
	/** ����;��,1:�����ã�2:��ֵõ�����Ʒ��3:����õ�����Ʒ��4����ֵõ�����Ʒ(����)�� 5����ʾ�������*/
	private int npcDropImprot;

	/**ָ�������Ʒ��,0��ͨ1����2����3��Ʒ  -1��ʾ����������Ʒ��*/
	private int quality;
	
	/**
	 * ˢ��ʱ��(���ڿ���)
	 */
	private String weekStr;
	
	public NpcVO getNpcInfo()
	{
		return NpcCache.getById(npcID);
	}
	
	/**
	 * �Ƿ����
	 * @param appendRate	׷����Ҹ��ӵĸ���
	 * @return
	 */
	public boolean isDrop( int appendRate )
	{
		if( DateUtil.isBetweenWeekStr(weekStr)==false )
		{
			return false;
		}
		
		int total_drop_rate = this.npcdropProbability;//�ܵĵ�����
		
		if (MathUtil.isAppearByPercentage(this.npcdropLuck,MathUtil.DENOMINATOR)) // �жϴ��Ƿ����
		{
			total_drop_rate *= 5;//����5������
		}
		//׷����Ҹ��ӵĸ���
		if(appendRate == 0){
			appendRate = 100;
		}
		total_drop_rate = total_drop_rate+total_drop_rate*(appendRate-100)/100;
		
		// ׷��ϵͳϵͳ���䱶��
		ExpnpcdropService expnpcdropService = new ExpnpcdropService(); 
		int sys_rate = expnpcdropService.getCimeliaNpcdrop();
		total_drop_rate *=sys_rate;
		
		return MathUtil.isAppearByPercentage(total_drop_rate, MathUtil.DROPDENOMINATOR);
	}
	
	public int getNpcDropImprot()
	{
		return npcDropImprot;
	}

	public void setNpcDropImprot(int npcDropImprot)
	{
		this.npcDropImprot = npcDropImprot;
	}

	public int getNpcdropID() {
		return npcdropID;
	}

	public void setNpcdropID(int npcdropID) {
		this.npcdropID = npcdropID;
	}

	public int getNpcID() {
		return npcID;
	}

	public void setNpcID(int npcID) {
		this.npcID = npcID;
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

	public int getNpcdropProbability() {
		return npcdropProbability;
	}

	public void setNpcdropProbability(int npcdropProbability) {
		if( npcdropProbability<0 )
		{
			this.npcdropProbability = MathUtil.DROPDENOMINATOR;
		}
		else
		{
			this.npcdropProbability = npcdropProbability;		
		}
	}

	public int getNpcdropLuck() {
		return npcdropLuck;
	}

	public void setNpcdropLuck(int npcdropLuck) {
		this.npcdropLuck = npcdropLuck;
	}

	public int getNpcDropNum() {
		return npcDropNum;
	}

	public String getNpcDropNumStr() {
		return npcDropNumStr;
	}

	public void setNpcDropNumStr(String npcDropNumStr) {
		this.npcDropNumStr = npcDropNumStr;
		String temp[]=npcDropNumStr.split(",");
		this.npcDropNum = MathUtil.getRandomBetweenXY(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public int getQuality()
	{
		return quality;
	}

	public void setQuality(int quality)
	{
		this.quality = quality;
	}

	public void setWeekStr(String weekStr)
	{
		this.weekStr = weekStr;
	}

	

}
