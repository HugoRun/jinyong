package com.ls.pub.util;

import com.ls.model.organize.faction.Faction;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.BondingType;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.WuXing;

/**
 * ����:ϵͳ����ת������
 * @author ��˧
 * 12:53:17 PM
 */
public class ExchangeUtil {
	
	/**
	 * ͼƬ��ʾ
	 */
	public static String getPicDisplay(String pic )
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<img src=\"").append(pic).append("\" alt=\"pic\"/>");
		return sb.toString();
	}
	
	
	/**
	 * ����ְλ����
	 */
	public static String getFJobName( int f_job )
	{
		switch(f_job)
		{
			case Faction.HUFA:return "����";
			case Faction.ZHANGLAO:return "����";
			case Faction.ZUZHANG:return "�峤";
			case Faction.ZUZHONG:return "����";
			default:return "";
		}
	}
	
	/**
	 * ��������
	 */
	public static String getRaceName( int race )
	{
		switch(race)
		{
			case 1:return "��";
			case 2:return "��";
			default:return "��";
		}
	}
	
	/**
	 * �õ�Ʒ�ʵļ�д����
	 */
	public static String getQualityName( int quality )
	{
		switch(quality)
		{
			case Equip.Q_YOUXIU:return "(��)";
			case Equip.Q_LIANGHAO:return "(��)";
			case Equip.Q_JIPIN:return "(��)";
			case Equip.Q_ORANGE:return "(��)";
		}
		return "";
	}
	
	/**
	 * �õ�Ʒ�ʵ�ȫ��
	 */
	public static String getQualityFullName( int quality )
	{
		switch(quality)
		{
			case Equip.Q_YOUXIU:return "�ﱦ����";
			case Equip.Q_LIANGHAO:return "�����������";
			case Equip.Q_JIPIN:return "�������鱦��";
			case Equip.Q_ORANGE:return "������������";
		}
		return "��ͨװ��";
	}
	
	/**
	 * �õ���Ǯ����
	 * @return
	 */
	public static String getMoneyDes( int money)
	{
		return money+GameConfig.getMoneyUnitName();
	}
	
	/**
	 * �ѿ��ػ����ַ�������
	 * @param sex_id
	 * @return
	 */
	public static String exchangeSwitch( int switch_control )
	{
		if( -switch_control==1 )
		{
			return "��";
		}
		else
		{
			return "��";
		}
	}
	
	/**
	 * ������ת�����ַ�������
	 * @param sex_id
	 * @return
	 */
	public static String exchangeToSex( int sex_id )
	{
		if( sex_id==1 )
		{
			return "��";
		}
		else if( sex_id==2 )
		{
			return "Ů";
		}
		else
		{
			return "��";
		}
	}
	
	/**
	 * ������idת���ɺ���
	 * @param sex
	 * @return
	 */
	public static String exchangeToSchool( String school_id )
	{

		if( school_id.equals("shaolin") )
		{
			return "����";
		}
		else if( school_id.equals("mingjiao") )
		{
			return "����";
		}
		else if( school_id.equals("gaibang") )
		{
			return "ؤ��";
		}
		else
		{
			return "��";
		}
	}
	
	/**
	 * ������idת���ɺ���
	 * @param sex
	 * @return
	 */
	public static String exchangeIdToSchool( int school_id )
	{

		if( school_id == 3 )
		{
			return "shaolin";
		}
		else if( school_id == 1 )
		{
			return "mingjiao";
		}
		else if( school_id == 2 )
		{
			return "gaibang";
		}
		else
		{
			return "��";
		}
	}
	
	
	public static String exchangeToWX(int wx)
	{
		if( wx==WuXing.JIN )
		{
			return "��";
		}
		else if( wx==WuXing.MU )
		{
			return "ľ";
		}
		else if( wx==WuXing.SHUI )
		{
			return "ˮ";
		}
		else if( wx==WuXing.HUO )
		{
			return "��";
		}
		else if( wx==WuXing.TU )
		{
			return "��";
		}
		return "��";
	}
	
	
	public static String exchangeToPetType(int pet_type)
	{
		if( pet_type==1 )
		{
			//��ͨ
			return "";
		}
		else if( pet_type==2 )
		{
			return "����";
		}
		else if( pet_type==3 )
		{
			return "����";
		}
		return "";
	}
	
	/**
	 * ��ȡ�󶨵�����
	 * @param pet_type
	 * @return
	 */
	public static String exchangeToBondingType(int bonding_type)
	{
		if( bonding_type == BondingType.NOBOND )
		{
			//��ͨ
			return "����";
		}
		else if( bonding_type==BondingType.PICKBOUND )
		{
			return "ʰȡ��";
		}
		else if( bonding_type==BondingType.ARMBOND )
		{
			return "װ����";
		}else if( bonding_type==BondingType.EXCHANGEBOND )
		{
			return "���װ�";
		}
		return "";
	}
	
	/**
	 * �����������ͺ�npc���͵õ������ֺ�׺
	 * @param wx
	 * @param type
	 * @return
	 */
	public static String getSuffixOfNPCNameByWxAndType(int wx,int type)
	{
		String wx_str = ExchangeUtil.exchangeToWX(wx);
		if( !wx_str.equals("��"))
		{
			wx_str = "(" + wx_str + ")";
		}
		else
		{
			wx_str = "";
		}
		String pet_type_str = "";
		
		pet_type_str = ExchangeUtil.exchangeToPetType(type);
		if( !pet_type_str.equals(""))
		{
			pet_type_str = "��" + pet_type_str + "��";
		}
		String suffix = pet_type_str + wx_str;
		return suffix;
	}
	
	/**
	 * ��������Ա�õ���ҵĳƺ�,1���У�2��Ů
	 * @param content                   ��Ҫ�滻������
	 * @param sex                       �Ա�
	 * @return
	 */
	public static String getTitleBySex( String content,int sex )
	{
		String result = "";
		
		if( sex==1 )//��
		{
			result = content.replaceAll("\\(xiao\\)","����");
			result = result.replaceAll("\\(da\\)","��ү");
			result = result.replaceAll("\\(xia\\)","����");
			result = result.replaceAll("\\(ya\\)","С��");
			result = result.replaceAll("\\(mm\\)","�ֵ�");
			result = result.replaceAll("\\(dd\\)","С��");
			result = result.replaceAll("\\(shi\\)","ʦ��");
			result = result.replaceAll("\\(gg\\)","���");
			result = result.replaceAll("\\(dm\\)","��");
			result = result.replaceAll("\\(gj\\)","��");
		}
		else if( sex==2)//Ů
		{
			result = content.replaceAll("\\(xiao\\)","����");
			result = result.replaceAll("\\(da\\)","С��");
			result = result.replaceAll("\\(xia\\)","Ů��");
			result = result.replaceAll("\\(ya\\)","Ѿͷ");
			result = result.replaceAll("\\(mm\\)","����");
			result = result.replaceAll("\\(dd\\)","С��");
			result = result.replaceAll("\\(shi\\)","ʦ��");
			result = result.replaceAll("\\(gg\\)","���");
			result = result.replaceAll("\\(dm\\)","��");
			result = result.replaceAll("\\(gj\\)","��");
		}
		
		return result;
	}
	
	
}
