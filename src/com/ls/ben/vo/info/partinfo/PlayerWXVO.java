package com.ls.ben.vo.info.partinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ls.ben.vo.info.attribute.attack.WXAttack;
import com.ls.ben.vo.info.attribute.attack.WXDefence;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.WuXing;
import com.ls.pub.util.MathUtil;

/**
 * 功能:五行属性
 * @author 刘帅
 * 3:24:49 PM
 */
public class PlayerWXVO implements WXDefence,WXAttack {

	 /**金防御力*/
	private int jinFy;
	 /**木防御力*/
	private int muFy;
	 /**水防御力*/
	private int shuiFy;
	 /**火防御力*/
	private int huoFy;
	 /**土防御力*/
	private int tuFy;
	
	 /**金攻击力*/
	private int jinGj;
	 /**木攻击力*/
	private int muGj;
	 /**水攻击力*/
	private int shuiGj;
	 /**火攻击力*/
	private int huoGj;
	 /**土攻击力*/
	private int tuGj;

	public PlayerWXVO(){}
	/**
	 * @param attrisStr 五行字符串
	 */
	public PlayerWXVO(String attrisStr)
	{
		if( attrisStr==null || attrisStr.length()==0 )
		{
			return;
		}
		String[] attri_str_list = attrisStr.split("-");
		for( String attri_str:attri_str_list)
		{
			String[] temp = attri_str.split(",");
			if( temp.length==2 )
			{
				this.appendAttriByAttriType(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
			}
		}
	}
	/**
	 * 武器排行榜，排行规则
	 * 一级条件，武器攻击力（攻击力+属性攻击力*10）
	 * 二级条件，可以开孔数
	 * 三级条件，达到条件时间
	 * @return
	 */
	public int getRankKey()
	{
		int rank_key = 0;
		rank_key+=this.jinGj;
		rank_key+=this.jinFy;
		
		rank_key+=this.muGj;
		rank_key+=this.muFy;
		
		rank_key+=this.shuiGj;
		rank_key+=this.shuiFy;
		
		rank_key+=this.huoGj;
		rank_key+=this.huoFy;
		
		rank_key+=this.tuGj;
		rank_key+=this.tuFy;
		
		return rank_key*10;
	}
	
	/**
	 * 转换五行类型
	 */
	public String changeWXType()
	{
		int old_wx_type = this.getWXType();//老的五行类型
		int old_wx_att = 0;
		int old_wx_def = 0;
		switch(old_wx_type)
		{
			case WuXing.JIN:
				old_wx_att = this.jinGj;
				old_wx_def = this.jinFy;
				this.jinGj = 0;
				this.jinFy = 0;
				break;
			case WuXing.MU:
				old_wx_att = this.muGj;
				old_wx_def = this.muFy;
				this.muGj = 0;
				this.muFy = 0;
				break;
			case WuXing.SHUI:
				old_wx_att = this.shuiGj;
				old_wx_def = this.shuiFy;
				this.shuiGj = 0;
				this.shuiFy = 0;
				break;
			case WuXing.HUO:
				old_wx_att = this.huoGj;
				old_wx_def = this.huoFy;
				this.huoGj = 0;
				this.huoFy = 0;
				break;
			case WuXing.TU:
				old_wx_att = this.tuGj;
				old_wx_def = this.tuFy;
				this.tuGj = 0;
				this.tuFy = 0;
				break;
		}
		int new_wx_type = 0;//新的五行类型
		
		Map<Integer,Integer> wx_map = new HashMap<Integer,Integer>(WuXing.wx_map);
		wx_map.remove(old_wx_type);
		List<Integer> wx_list = new ArrayList<Integer>(wx_map.values());
		int random_index = MathUtil.getRandomBetweenXY(0, wx_list.size()-1);
		
		new_wx_type = wx_list.get(random_index);
		
		switch(new_wx_type)
		{
			case WuXing.JIN:
				this.jinGj = old_wx_att;
				this.jinFy = old_wx_def;
				break;
			case WuXing.MU:
				this.muGj = old_wx_att;
				this.muFy = old_wx_def;
				break;
			case WuXing.SHUI:
				this.shuiGj = old_wx_att;
				this.shuiFy = old_wx_def;
				break;
			case WuXing.HUO:
				this.huoGj = old_wx_att;
				this.huoFy = old_wx_def;
				break;
			case WuXing.TU:
				this.tuGj = old_wx_att;
				this.tuFy = old_wx_def;
				break;
		}
		return "成功转换为"+WuXing.getWxStr(new_wx_type)+"属性！";
	}
	
	/**
	 * 得到装备的唯一五行
	 * @return                -1表示没有五行属性或多个五行属性
	 */
	public int getWXType()
	{
		Map<Integer,String> wx_types = this.getWxType();
		wx_types.remove(0);
		
		if( wx_types.size()!=1 )
		{
			return -1;
		}
		
		Set<Integer> wx_type_list = wx_types.keySet();
		for( Integer wx_type:wx_type_list)
		{
			return wx_type;
		}
		return -1;
	}
	
	/**
	 * 根据属性类型给装备附加属性
	 * @param attri_type      属性类型
	 * @param attri_value    属性值
	 */
	public void appendAttriByAttriType( int attri_type,int attri_value)
	{
		switch(attri_type)
		{						
			case Equip.JIN_FY:
				setJinFy(getJinFy()+attri_value);
				break;
			case Equip.MU_FY:
				setMuFy(getMuFy()+attri_value);
				break;
			case Equip.SHUI_FY:
				setShuiFy(getShuiFy()+attri_value);
				break;
			case Equip.HUO_FY:
				setHuoFy(getHuoFy()+attri_value);
				break;
			case Equip.TU_FY:
				setTuFy(getTuFy()+attri_value);
				break;
			case Equip.JIN_GJ:
				setJinGj(getJinGj()+attri_value);
				break;
			case Equip.MU_GJ:
				setMuGj(getMuGj()+attri_value);
				break;
			case Equip.SHUI_GJ:
				setShuiGj(getShuiGj()+attri_value);
				break;
			case Equip.HUO_GJ:
				setHuoGj(getHuoGj()+attri_value);
				break;
			case Equip.TU_GJ:
				setTuGj(getTuGj()+attri_value);
				break;
		}
	}
	
	
	/**
	 * 得到存储字符串
	 * 形式为：3,10-11,5
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		if( this.jinGj>0 )
		{
			sb.append(Equip.JIN_GJ).append(",").append(this.jinGj).append("-");
		}
		if( this.jinFy>0 )
		{
			sb.append(Equip.JIN_FY).append(",").append(this.jinFy).append("-");
		}
		
		if( this.muGj>0 )
		{
			sb.append(Equip.MU_GJ).append(",").append(this.muGj).append("-");
		}
		if( this.muFy>0 )
		{
			sb.append(Equip.MU_FY).append(",").append(this.muFy).append("-");
		}
		
		if( this.shuiGj>0 )
		{
			sb.append(Equip.SHUI_GJ).append(",").append(this.shuiGj).append("-");
		}
		if( this.shuiFy>0 )
		{
			sb.append(Equip.SHUI_FY).append(",").append(this.shuiFy).append("-");
		}
		
		if( this.huoGj>0 )
		{
			sb.append(Equip.HUO_GJ).append(",").append(this.huoGj).append("-");
		}
		if( this.huoFy>0 )
		{
			sb.append(Equip.HUO_FY).append(",").append(this.huoFy).append("-");
		}
		if( this.tuGj>0 )
		{
			sb.append(Equip.TU_GJ).append(",").append(this.tuGj).append("-");
		}
		if( this.tuFy>0 )
		{
			sb.append(Equip.TU_FY).append(",").append(this.tuFy).append("-");
		}
		
		if( sb.length()==0 )
		{
			return "";
		}
		return sb.toString().substring(0,sb.length()-1);
	}
	
	/**
	 * 得到装备的五行属性
	 * @return              map的主键1-5代表五行，0表示五行的描述字符串
	 */
	public Map<Integer,String> getWxType()
	{
		StringBuffer wx_des = new StringBuffer(); 
		Map<Integer,String> wx = new HashMap<Integer,String>(6);
		if( this.jinFy>0 || this.jinGj>0 )
		{
			wx_des.append(WuXing.getWxStr(WuXing.JIN)).append(" ");
			wx.put(WuXing.JIN, WuXing.getWxStr(WuXing.JIN));
		}
		if( this.muFy>0 || this.muGj>0 )
		{
			wx_des.append(WuXing.getWxStr(WuXing.MU)).append(" ");
			wx.put(WuXing.MU, WuXing.getWxStr(WuXing.MU));
		}
		if( this.shuiFy>0 || this.shuiGj>0 )
		{
			wx_des.append(WuXing.getWxStr(WuXing.SHUI)).append(" ");
			wx.put(WuXing.SHUI, WuXing.getWxStr(WuXing.SHUI));
		}
		if( this.huoFy>0 || this.huoGj>0 )
		{
			wx_des.append(WuXing.getWxStr(WuXing.HUO)).append(" ");
			wx.put(WuXing.HUO, WuXing.getWxStr(WuXing.HUO));
		}
		if( this.tuFy>0 || this.tuGj>0 )
		{
			wx_des.append(WuXing.getWxStr(WuXing.TU)).append(" ");
			wx.put(WuXing.TU, WuXing.getWxStr(WuXing.TU));
		}
		wx.put(0, wx_des.toString());
		return wx;
	}
	
	/**
	 * 五行描述
	 */
	public String getDes()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("五行属性:");
		Map<Integer,String> equip_wx = getWxType();//装备五行
		if( equip_wx.size()>1 )
		{
			sb.append(equip_wx.get(0));
		}
		else
		{
			sb.append("无");
		}
		sb.append("<br/>");
		sb.append(this.getGjDes());
		sb.append(this.getFyDes());
		return sb.toString();
	}
	
	/**
	 * 五行攻击描述
	 */
	private String getGjDes()
	{
		StringBuffer sb = new StringBuffer();
		
		if( this.jinGj>0 )
		{
			sb.append(WuXing.getWxStr(WuXing.JIN)).append("+").append(this.jinGj).append(",");
		}
		if( this.muGj>0 )
		{
			sb.append(WuXing.getWxStr(WuXing.MU)).append("+").append(this.muGj).append(",");
		}
		if( this.shuiGj>0 )
		{
			sb.append(WuXing.getWxStr(WuXing.SHUI)).append("+").append(this.shuiGj).append(",");
		}
		if( this.huoGj>0 )
		{
			sb.append(WuXing.getWxStr(WuXing.HUO)).append("+").append(this.huoGj).append(",");
		}
		if( this.tuGj>0 )
		{
			sb.append(WuXing.getWxStr(WuXing.TU)).append("+").append(this.tuGj).append(",");
		}
		
		if( sb.length()==0 )
		{
			return "";
		}
		return "五行攻击:"+sb.toString().substring(0,sb.length()-1)+"<br/>";
	}
	
	/**
	 * 五行防御描述
	 */
	private String getFyDes()
	{
		StringBuffer sb = new StringBuffer();
		
		if( this.jinFy>0 )
		{
			sb.append(WuXing.getWxStr(WuXing.JIN)).append("+").append(this.jinFy).append(",");
		}
		if( this.muFy>0 )
		{
			sb.append(WuXing.getWxStr(WuXing.MU)).append("+").append(this.muFy).append(",");
		}
		if( this.shuiFy>0 )
		{
			sb.append(WuXing.getWxStr(WuXing.SHUI)).append("+").append(this.shuiFy).append(",");
		}
		if( this.huoFy>0 )
		{
			sb.append(WuXing.getWxStr(WuXing.HUO)).append("+").append(this.huoFy).append(",");
		}
		if( this.tuFy>0 )
		{
			sb.append(WuXing.getWxStr(WuXing.TU)).append("+").append(this.tuFy).append(",");
		}
		
		if( sb.length()==0 )
		{
			return "";
		}
		return "五行防御:"+sb.toString().substring(0,sb.length()-1)+"<br/>";
	}

	public int getJinFy() {
		return jinFy;
	}

	public void setJinFy(int jinFy) {
		this.jinFy = jinFy;
	}

	public int getMuFy() {
		return muFy;
	}

	public void setMuFy(int muFy) {
		this.muFy = muFy;
	}

	public int getShuiFy() {
		return shuiFy;
	}

	public void setShuiFy(int shuiFy) {
		this.shuiFy = shuiFy;
	}

	public int getHuoFy() {
		return huoFy;
	}

	public void setHuoFy(int huoFy) {
		this.huoFy = huoFy;
	}

	public int getTuFy() {
		return tuFy;
	}

	public void setTuFy(int tuFy) {
		this.tuFy = tuFy;
	}

	public int getJinGj() {
		return jinGj;
	}

	public void setJinGj(int jinGj) {
		this.jinGj = jinGj;
	}

	public int getMuGj() {
		return muGj;
	}

	public void setMuGj(int muGj) {
		this.muGj = muGj;
	}

	public int getShuiGj() {
		return shuiGj;
	}

	public void setShuiGj(int shuiGj) {
		this.shuiGj = shuiGj;
	}

	public int getHuoGj() {
		return huoGj;
	}

	public void setHuoGj(int huoGj) {
		this.huoGj = huoGj;
	}

	public int getTuGj() {
		return tuGj;
	}

	public void setTuGj(int tuGj) {
		this.tuGj = tuGj;
	}
	
}
