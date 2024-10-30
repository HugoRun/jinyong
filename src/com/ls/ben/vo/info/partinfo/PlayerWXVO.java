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
 * ����:��������
 * @author ��˧
 * 3:24:49 PM
 */
public class PlayerWXVO implements WXDefence,WXAttack {

	 /**�������*/
	private int jinFy;
	 /**ľ������*/
	private int muFy;
	 /**ˮ������*/
	private int shuiFy;
	 /**�������*/
	private int huoFy;
	 /**��������*/
	private int tuFy;
	
	 /**�𹥻���*/
	private int jinGj;
	 /**ľ������*/
	private int muGj;
	 /**ˮ������*/
	private int shuiGj;
	 /**�𹥻���*/
	private int huoGj;
	 /**��������*/
	private int tuGj;

	public PlayerWXVO(){}
	/**
	 * @param attrisStr �����ַ���
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
	 * �������а����й���
	 * һ��������������������������+���Թ�����*10��
	 * �������������Կ�����
	 * �����������ﵽ����ʱ��
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
	 * ת����������
	 */
	public String changeWXType()
	{
		int old_wx_type = this.getWXType();//�ϵ���������
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
		int new_wx_type = 0;//�µ���������
		
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
		return "�ɹ�ת��Ϊ"+WuXing.getWxStr(new_wx_type)+"���ԣ�";
	}
	
	/**
	 * �õ�װ����Ψһ����
	 * @return                -1��ʾû���������Ի�����������
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
	 * �����������͸�װ����������
	 * @param attri_type      ��������
	 * @param attri_value    ����ֵ
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
	 * �õ��洢�ַ���
	 * ��ʽΪ��3,10-11,5
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
	 * �õ�װ������������
	 * @return              map������1-5�������У�0��ʾ���е������ַ���
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
	 * ��������
	 */
	public String getDes()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("��������:");
		Map<Integer,String> equip_wx = getWxType();//װ������
		if( equip_wx.size()>1 )
		{
			sb.append(equip_wx.get(0));
		}
		else
		{
			sb.append("��");
		}
		sb.append("<br/>");
		sb.append(this.getGjDes());
		sb.append(this.getFyDes());
		return sb.toString();
	}
	
	/**
	 * ���й�������
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
		return "���й���:"+sb.toString().substring(0,sb.length()-1)+"<br/>";
	}
	
	/**
	 * ���з�������
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
		return "���з���:"+sb.toString().substring(0,sb.length()-1)+"<br/>";
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
