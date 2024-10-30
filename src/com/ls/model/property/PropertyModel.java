package com.ls.model.property;

import org.apache.commons.lang.StringUtils;

import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.pub.constant.buff.BuffType;
import com.ls.web.service.log.DataErrorLog;

/**
 * ���ܣ��������ģ��
 * @author ls
 * Aug 13, 2009
 * 3:08:51 PM
 */
public class PropertyModel
{
	public static int ADD_PROPERTY = 1;// �ӹ�����
	public static int SUB_PROPERTY = -1;// ���������
	
	
	public static final int HP_UP = 0;//hp����
	public static final int MP_UP = 1;//mp����
	public static final int ATTACK_UP = 2;//������
	public static final int ATTACK_LOW = 3;//����С
	public static final int DEFEND_UP = 4;//����
	public static final int DEFEND_LOW= 5;//����
	
	public final static int JIN_DEFENCE = 6;
	public final static int MU_DEFENCE = 7;
	public final static int SHUI_DEFENCE = 8;
	public final static int HUO_DEFENCE = 9;
	public final static int TU_DEFENCE = 10;
	
	public final static int JIN_ATTACK = 11;
	public final static int MU_ATTACK = 12;
	public final static int SHUI_ATTACK = 13;
	public final static int HUO_ATTACK = 14;
	public final static int TU_ATTACK = 15;
	
	public final static int CRIT = 16;//������
	
	
	private int[]  propertys = new int[17];//��������ֵ
	
	/**
	 * ��ʼ������ֵ
	 */
	public void init()
	{
		for( int i=0;i<propertys.length;i++ )
		{
			propertys[i] = 0;
		}
	}
	
	/**
	 * �õ�ָ�����Ե�����ֵ
	 * @param property_type
	 * @return
	 */
	public int getPropertyByType( int property_type )
	{
		int property_value = propertys[property_type];
		if( property_value<0 )
		{
			return 0;
		}
		else
		{
			return property_value;
		}
	}
	
	/**
	 * ����ָ�����Ե�����ֵ
	 * @param property_type
	 * @return
	 */
	public void updatePropertyByType( int property_value,int property_type )
	{
		if( property_value!=0 && property_type>=0 && property_type<propertys.length )
		{
			propertys[property_type]=propertys[property_type]+property_value;
		}
	}
	
	/**
	 * �õ���������ֵ
	 * @return
	 */
	public int[] getPropertys()
	{
		return propertys;
	}
	
	/**
	 * ������������
	 */
	public void appendPropertysFromPropertyModel( PropertyModel new_property_model )
	{
		if( new_property_model==null )
		{
			return;
		}
		int[] new_propertys = new_property_model.getPropertys();
		
		for( int i=0;i<new_propertys.length;i++ )
		{
			propertys[i] = propertys[i]+new_propertys[i];
		}
	}
	
	
	/**
	 * ����Ҽ�������
	 */
	public void loadPropertys(PartInfoVO player)
	{
		if (player != null)
		{
			player.setPUpHp(player.getPUpHp() + propertys[HP_UP]);
			//player.setPHp(player.getPHp() + propertys[HP_UP]);
			
			player.setPUpMp(player.getPUpMp() + propertys[MP_UP]);
			//player.setPMp(player.getPMp() + propertys[MP_UP]);
			
			player.setPZbgjDa((player.getPZbgjDa() + propertys[ATTACK_UP]));
			player.setPZbgjXiao(player.getPZbgjXiao() + propertys[ATTACK_LOW]);
			
			player.setPZbfyDa(player.getPZbfyDa() + propertys[DEFEND_UP]);
			player.setPZbfyXiao(player.getPZbfyXiao() + propertys[DEFEND_LOW]);
			
			player.getWx().setJinGj(player.getWx().getJinGj() + propertys[JIN_ATTACK]);
			player.getWx().setMuGj(player.getWx().getMuGj() + propertys[MU_ATTACK]);
			player.getWx().setShuiGj(player.getWx().getShuiGj() + propertys[SHUI_ATTACK]);
			player.getWx().setHuoGj(player.getWx().getHuoGj() + propertys[HUO_ATTACK]);
			player.getWx().setTuGj(player.getWx().getTuGj() + propertys[TU_ATTACK]);
			
			player.getWx().setJinFy(player.getWx().getJinFy() + propertys[JIN_DEFENCE]);
			player.getWx().setMuFy(player.getWx().getMuFy() + propertys[MU_DEFENCE]);
			player.getWx().setShuiFy(player.getWx().getShuiFy() + propertys[SHUI_DEFENCE]);
			player.getWx().setHuoFy(player.getWx().getHuoFy() + propertys[HUO_DEFENCE]);
			player.getWx().setTuFy(player.getWx().getTuFy() + propertys[TU_DEFENCE]);
			
		}
	}
	/**
	 * ͨ�������ַ�����������
	 */
	public void appendPropertyByAttriStr(String attriStr,int flag )
	{
		if ( StringUtils.isNotEmpty(attriStr) && !attriStr.equals("0") )
		{
			String[] attri_str_list = attriStr.split("-");
			for (String attri_str : attri_str_list)
			{
				if( StringUtils.isNotEmpty(attri_str) )
				{
					String[] temp = attri_str.split(",");

					if (temp.length == 2)
					{
						appendPropertyByBuffType(Integer.parseInt(temp[1]), Integer.parseInt(temp[0]),flag );
					}
					else
					{
						DataErrorLog.debugData("PropertyModel.appendPropertyByAttriStr:�����ַ�������,attriStr="+attriStr);
					}
				}
			}
		}
	}
	
	/**
	 *  ����buff�������û�������
	 * @param property_value
	 * @param buff_type
	 * @param flag			-1��ʾ�ӣ�1��ʾ��
	 */
	public void appendPropertyByBuffType( int property_value,int buff_type,int flag )
	{
		property_value *= flag;
		switch(buff_type)
		{
			case BuffType.ATTACK:
			{
				updatePropertyByType(property_value, ATTACK_UP);
				updatePropertyByType(property_value, ATTACK_LOW);
				return;
			}
			case BuffType.DEFENCE:
			{
				updatePropertyByType(property_value, DEFEND_UP);
				updatePropertyByType(property_value, DEFEND_LOW);
				return;
			}
			case BuffType.HP_UP:
			{
				updatePropertyByType(property_value, HP_UP);
				return;
			}
			case BuffType.MP_UP:
			{
				updatePropertyByType(property_value,MP_UP);
				return;
			}
			// *********************���й�����buff
			case BuffType.JIN_ATTACK:
			{
				updatePropertyByType(property_value,JIN_ATTACK);
				return;
			}
			case BuffType.MU_ATTACK:
			{
				updatePropertyByType(property_value,MU_ATTACK);
				return;
			}
			case BuffType.SHUI_ATTACK:
			{
				updatePropertyByType(property_value,SHUI_ATTACK);
				return;
			}
			case BuffType.HUO_ATTACK:
			{
				updatePropertyByType(property_value,HUO_ATTACK);
				return;
			}
			case BuffType.TU_ATTACK:
			{
				updatePropertyByType(property_value,TU_ATTACK);
				return;
			}
			//**********************���з�����buff
			case BuffType.JIN_DEFENCE:
			{
				updatePropertyByType(property_value,JIN_DEFENCE);
				return;
			}
			case BuffType.MU_DEFENCE:
			{
				updatePropertyByType(property_value,MU_DEFENCE);
				return;
			}
			case BuffType.SHUI_DEFENCE:
			{
				updatePropertyByType(property_value,SHUI_DEFENCE);
				return;
			}
			case BuffType.HUO_DEFENCE:
			{
				updatePropertyByType(property_value,HUO_DEFENCE);
				return;
			}
			case BuffType.TU_DEFENCE:
			{
				updatePropertyByType(property_value,TU_DEFENCE);
				return;
			}
			//******************����
			case BuffType.ADD_CATCH_PROBABILITY:// ���Ӳ������
			{
				return;
			}
			case BuffType.ADD_DROP_PROBABILITY:// ���ӵ������
			{
				return;
			}
			case BuffType.ADD_EXP:// ����ӳ�
			{
				return;
			}
			case BuffType.ADD_NONSUCH_PROBABILITY:// ���Ӽ�Ʒװ���������
			{
				return;
			}

			case BuffType.IMMUNITY_POISON:// **�Ƿ������ж�
			{
				return;
			}
			case BuffType.IMMUNITY__DIZZY:// **�Ƿ����߻���
			{
				return;
			}
			case BuffType.CHANGER_BJ:// �����ʼӳ�
			{
				return;
			}
		}
	}
	
}
