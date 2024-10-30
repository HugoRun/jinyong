package com.ls.web.service.goods.equip;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.vo.info.npc.NpcShopVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.system.SystemConfig;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MoneyUtil;

/**
 * @author ls
 * װ����ʾ�߼�
 */
public class EquipDisplayService
{
	public static final int DISPLAY = 1;
	public static final int NOTDISPLAY = 0;

	/**
	 * �����װ������ʾ
	 * @param roleInfo
	 * @param equipId
	 * @param originalityPrice     ԭʼ��Ǯ
	 * @return
	 */
	public String getBuyEquipDisplay(RoleEntity roleInfo,NpcShopVO npcShop)
	{
		int equip_id =  npcShop.getGoodsId();
		GameEquip gameEquip = EquipCache.getById(equip_id);
		StringBuffer sb = new StringBuffer();
		int total_price = npcShop.getPrice(roleInfo);
		sb.append(this.getEquipDisplay(roleInfo, gameEquip, false));
		sb.append("------------------------").append("<br/>");
		sb.append("����۸�:").append(MoneyUtil.changeCopperToStr(total_price)).append("<br/>");			
		return sb.toString();
	}
	
	/**
	 * ���ϵͳװ������ʾ���жԱȹ��ܣ���Ʒ�����Աȣ�
	 * @param roleInfo
	 * @param equipId
	 * @param isCompare     true��ʾװ����������ϵ�ͬһλ�õ�װ���Աȣ�false���Ա�
	 * @return
	 */
	public String getEquipDisplay(RoleEntity roleInfo,int equipId, boolean isCompare)
	{
		GameEquip gameEquip = EquipCache.getById(equipId);
		return this.getEquipDisplay(roleInfo, gameEquip, isCompare);
	}
	
	/**
	 * ���ϵͳװ������ʾ���жԱȹ��ܣ���Ʒ�����Աȣ�
	 * @param roleInfo
	 * @param gameEquip
	 * @param isCompare     true��ʾװ����������ϵ�ͬһλ�õ�װ���Աȣ�false���Ա�
	 * @return
	 */
	public String getEquipDisplay(RoleEntity roleInfo,GameEquip gameEquip, boolean isCompare)
	{
		StringBuffer des = new StringBuffer();
		
		PlayerEquipVO compareEquip = null;//Ҫ�Աȵ�װ��
		
		if( isCompare==true && gameEquip.getType()!=Equip.JEWELRY )
		{
			compareEquip = roleInfo.getEquipOnBody().getByPositin(gameEquip.getType());
		}
		if (gameEquip != null) {
			des.append(gameEquip.getName()).append("<br/>");
			des.append(gameEquip.getPicDisplay());
			des.append(gameEquip.getDes()).append("<br/>");
			
			des.append("�;�:").append(gameEquip.getEndure()).append("<br/>");
			
			if( gameEquip.getMaxAtt()>0)//�Ƿ��й�������
			{
				des.append("����:").append(gameEquip.getMinAtt()*SystemConfig.attackParameter).append("-").append(gameEquip.getMaxAtt()*SystemConfig.attackParameter);
				if(compareEquip!=null)
					des.append("(").append(compareEquip.getWGjXiao()*SystemConfig.attackParameter).append("-").append(compareEquip.getWGjDa()*SystemConfig.attackParameter).append(")");
				des.append("<br/>");
			}
			
			if( gameEquip.getMaxDef()>0 )//�Ƿ��з�������
			{
				des.append("����:").append(gameEquip.getMinDef()*SystemConfig.attackParameter).append("-").append(gameEquip.getMaxDef()*SystemConfig.attackParameter);
				if(compareEquip!=null)
					des.append("(").append(compareEquip.getWFyXiao()*SystemConfig.attackParameter).append("-").append(compareEquip.getWFyDa()*SystemConfig.attackParameter).append(")");
				des.append("<br/>");
			}
			
			des.append("�����۸�:").append(gameEquip.getPriceDes()).append("<br/>");			
			des.append("ʹ�õȼ�:").append(gameEquip.getGrade()).append("��").append("<br/>");
			des.append("�Ա�Ҫ��:").append(ExchangeUtil.exchangeToSex(gameEquip.getSex())).append("<br/>");
			des.append("����Ҫ��:").append(ExchangeUtil.getRaceName(gameEquip.getJob())).append("<br/>");
			des.append("װ��״̬:");
			if( gameEquip.getIsProtected() == 1) {
				des.append("���ɽ��ף��������������ɶ�����������������ʹ��!"); 
			} else {
				des.append("�ɽ���,�����������������ɶ�������ʹ��!");
			}
			des.append("<br/>");
		} else {
			des.append("�޸�װ��").append("<br/>");
		}
		return des.toString();
	}
	/**
	 * ������װ������ʾ���жԱȹ��ܣ���Ʒ�����Աȣ�
	 * @param roleInfo
	 * @param equip
	 * @param isCompare     true��ʾװ����������ϵ�ͬһλ�õ�װ���Աȣ�false���Ա�
	 * @return
	 */
	public String getEquipDisplay(RoleEntity roleInfo,PlayerEquipVO equip, boolean isCompare)
	{
		StringBuffer des = new StringBuffer();
		
		PlayerEquipVO compareEquip = null;//Ҫ�Աȵ�װ��
		
		if( isCompare==true && equip.getEquipType()!=Equip.JEWELRY )
		{
			compareEquip = roleInfo.getEquipOnBody().getByPositin(equip.getEquipType());
		}
		
		if (equip != null) {
			GameEquip gameEquip = equip.getGameEquip();
			
			des.append(equip.getFullName()).append("<br/>");
			des.append(equip.getQualityFullName());
			if(roleInfo.getSettingInfo().getGoodsPic()==1)
			{
				des.append(gameEquip.getPicDisplay());
			}
			des.append(gameEquip.getDes()).append("<br/>");
			des.append(equip.getHoleDes());
			des.append("�;�:").append(equip.getCurEndure()/10).append("/").append(equip.getMaxEndure()/10).append("<br/>");
			
			if( equip.getMinAtt()>0)//�Ƿ��й�������
			{
				des.append("����:").append(equip.getMinAtt()*SystemConfig.attackParameter).append("-").append(equip.getMaxAtt()*SystemConfig.attackParameter);
				if(compareEquip!=null)
				des.append("(").append(compareEquip.getMinAtt()*SystemConfig.attackParameter).append("-").append(compareEquip.getMaxAtt()*SystemConfig.attackParameter).append(")");
				des.append("<br/>");
			}
			if( equip.getMinDef()>0 )//�Ƿ��з�������
			{
				des.append("����:").append(equip.getMinDef()*SystemConfig.attackParameter).append("-").append(equip.getMaxDef()*SystemConfig.attackParameter);
				if(compareEquip!=null)
					des.append("(").append(compareEquip.getMinDef()*SystemConfig.attackParameter).append("-").append(compareEquip.getMaxDef()*SystemConfig.attackParameter).append(")");
				des.append("<br/>");
			}
			
			
			if ( equip.getWHp() > 0 ) 
			{
				des.append("��Ѫ:").append(equip.getWHp()).append("-").append(equip.getWHp());
				if(compareEquip!=null)
				des.append("(").append(compareEquip.getWHp()).append("-").append(compareEquip.getWHp()).append(")");
				des.append("<br/>");
			}
			
			
			if ( equip.getWMp() >  0 ) 
			{
				des.append("����:").append(equip.getWMp()).append("-").append(equip.getWMp());
				if(compareEquip!=null)
				des.append("(").append(compareEquip.getWMp()).append("-").append(compareEquip.getWMp()).append(")");
				des.append("<br/>");
			}
			
			des.append("�����۸�:").append(gameEquip.getPriceDes()).append("<br/>");			
			des.append("ʹ�õȼ�:").append(gameEquip.getGrade()).append("��").append("<br/>");
			des.append("�Ա�Ҫ��:").append(ExchangeUtil.exchangeToSex(gameEquip.getSex())).append("<br/>");
			des.append("����Ҫ��:").append(ExchangeUtil.getRaceName(gameEquip.getJob())).append("<br/>");
			des.append(equip.getWuxing().getDes());
			des.append(equip.getInlayDes());
			
			
			if(equip.getAppendAttriDes()!=null && !equip.getAppendAttriDes().equals(""))
			{
				des.append("��������:").append(equip.getAppendAttriDes()).append("<br/>");
			}
			 
			des.append("װ��״̬:");
			if( equip.getWProtect() == 1 || equip.getWBonding()==1) 
			{
				if( equip.getWProtect() == 1 )//����״̬
				{
					des.append("���ɽ���,��������,��������,��ʹ��,���ɶ���"); 
				}
				else//��״̬
				{
					des.append("���ɽ���,��������,��������,��ʹ��,�ɶ���,"); 
				}
			} 
			else 
			{
				des.append("�ɽ���,������,������,�ɶ���,��ʹ��");
			}
			des.append(equip.getProtectDes());
			des.append("<br/>");
			 
		} else {
			des.append("�޸�װ��").append("<br/>");
		}
		 return des.toString();
		}

}
