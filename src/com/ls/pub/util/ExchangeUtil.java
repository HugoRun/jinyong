package com.ls.pub.util;

import com.ls.model.organize.faction.Faction;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.BondingType;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.WuXing;

/**
 * 功能:系统常用转换工具
 * @author 刘帅
 * 12:53:17 PM
 */
public class ExchangeUtil {
	
	/**
	 * 图片显示
	 */
	public static String getPicDisplay(String pic )
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<img src=\"").append(pic).append("\" alt=\"pic\"/>");
		return sb.toString();
	}
	
	
	/**
	 * 帮派职位名字
	 */
	public static String getFJobName( int f_job )
	{
		switch(f_job)
		{
			case Faction.HUFA:return "护法";
			case Faction.ZHANGLAO:return "长老";
			case Faction.ZUZHANG:return "族长";
			case Faction.ZUZHONG:return "族众";
			default:return "";
		}
	}
	
	/**
	 * 种族名字
	 */
	public static String getRaceName( int race )
	{
		switch(race)
		{
			case 1:return "妖";
			case 2:return "巫";
			default:return "无";
		}
	}
	
	/**
	 * 得到品质的简写名字
	 */
	public static String getQualityName( int quality )
	{
		switch(quality)
		{
			case Equip.Q_YOUXIU:return "(蓝)";
			case Equip.Q_LIANGHAO:return "(绿)";
			case Equip.Q_JIPIN:return "(紫)";
			case Equip.Q_ORANGE:return "(橙)";
		}
		return "";
	}
	
	/**
	 * 得到品质的全名
	 */
	public static String getQualityFullName( int quality )
	{
		switch(quality)
		{
			case Equip.Q_YOUXIU:return "★宝器★";
			case Equip.Q_LIANGHAO:return "★后天灵器★";
			case Equip.Q_JIPIN:return "★先天灵宝★";
			case Equip.Q_ORANGE:return "★先天至宝★";
		}
		return "普通装备";
	}
	
	/**
	 * 得到金钱描述
	 * @return
	 */
	public static String getMoneyDes( int money)
	{
		return money+GameConfig.getMoneyUnitName();
	}
	
	/**
	 * 把开关换成字符串类型
	 * @param sex_id
	 * @return
	 */
	public static String exchangeSwitch( int switch_control )
	{
		if( -switch_control==1 )
		{
			return "开";
		}
		else
		{
			return "关";
		}
	}
	
	/**
	 * 把整形转换成字符串类型
	 * @param sex_id
	 * @return
	 */
	public static String exchangeToSex( int sex_id )
	{
		if( sex_id==1 )
		{
			return "男";
		}
		else if( sex_id==2 )
		{
			return "女";
		}
		else
		{
			return "无";
		}
	}
	
	/**
	 * 把门派id转换成汉字
	 * @param sex
	 * @return
	 */
	public static String exchangeToSchool( String school_id )
	{

		if( school_id.equals("shaolin") )
		{
			return "少林";
		}
		else if( school_id.equals("mingjiao") )
		{
			return "明教";
		}
		else if( school_id.equals("gaibang") )
		{
			return "丐帮";
		}
		else
		{
			return "无";
		}
	}
	
	/**
	 * 把门派id转换成汉字
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
			return "无";
		}
	}
	
	
	public static String exchangeToWX(int wx)
	{
		if( wx==WuXing.JIN )
		{
			return "金";
		}
		else if( wx==WuXing.MU )
		{
			return "木";
		}
		else if( wx==WuXing.SHUI )
		{
			return "水";
		}
		else if( wx==WuXing.HUO )
		{
			return "火";
		}
		else if( wx==WuXing.TU )
		{
			return "土";
		}
		return "无";
	}
	
	
	public static String exchangeToPetType(int pet_type)
	{
		if( pet_type==1 )
		{
			//普通
			return "";
		}
		else if( pet_type==2 )
		{
			return "异兽";
		}
		else if( pet_type==3 )
		{
			return "瑞兽";
		}
		return "";
	}
	
	/**
	 * 获取绑定的类型
	 * @param pet_type
	 * @return
	 */
	public static String exchangeToBondingType(int bonding_type)
	{
		if( bonding_type == BondingType.NOBOND )
		{
			//普通
			return "不绑定";
		}
		else if( bonding_type==BondingType.PICKBOUND )
		{
			return "拾取绑定";
		}
		else if( bonding_type==BondingType.ARMBOND )
		{
			return "装备绑定";
		}else if( bonding_type==BondingType.EXCHANGEBOND )
		{
			return "交易绑定";
		}
		return "";
	}
	
	/**
	 * 根据五行类型和npc类型得到，名字后缀
	 * @param wx
	 * @param type
	 * @return
	 */
	public static String getSuffixOfNPCNameByWxAndType(int wx,int type)
	{
		String wx_str = ExchangeUtil.exchangeToWX(wx);
		if( !wx_str.equals("无"))
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
			pet_type_str = "【" + pet_type_str + "】";
		}
		String suffix = pet_type_str + wx_str;
		return suffix;
	}
	
	/**
	 * 根据玩家性别得到玩家的称呼,1是男，2是女
	 * @param content                   需要替换的内容
	 * @param sex                       性别
	 * @return
	 */
	public static String getTitleBySex( String content,int sex )
	{
		String result = "";
		
		if( sex==1 )//男
		{
			result = content.replaceAll("\\(xiao\\)","公子");
			result = result.replaceAll("\\(da\\)","老爷");
			result = result.replaceAll("\\(xia\\)","少侠");
			result = result.replaceAll("\\(ya\\)","小子");
			result = result.replaceAll("\\(mm\\)","兄弟");
			result = result.replaceAll("\\(dd\\)","小弟");
			result = result.replaceAll("\\(shi\\)","师弟");
			result = result.replaceAll("\\(gg\\)","哥哥");
			result = result.replaceAll("\\(dm\\)","弟");
			result = result.replaceAll("\\(gj\\)","哥");
		}
		else if( sex==2)//女
		{
			result = content.replaceAll("\\(xiao\\)","姑娘");
			result = result.replaceAll("\\(da\\)","小姐");
			result = result.replaceAll("\\(xia\\)","女侠");
			result = result.replaceAll("\\(ya\\)","丫头");
			result = result.replaceAll("\\(mm\\)","妹妹");
			result = result.replaceAll("\\(dd\\)","小妹");
			result = result.replaceAll("\\(shi\\)","师妹");
			result = result.replaceAll("\\(gg\\)","姐姐");
			result = result.replaceAll("\\(dm\\)","妹");
			result = result.replaceAll("\\(gj\\)","姐");
		}
		
		return result;
	}
	
	
}
