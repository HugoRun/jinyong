package com.ls.model.vip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ls
 * 会员信息管理
 */
public class VipManager
{
	//会员等级
	public final static int LEVEL_0=0;//普通成员
	public final static int LEVEL_1=1;//1级会员
	public final static int LEVEL_2=2;//2级会员
	
	private static Map<Integer,Vip> vip_factory = new HashMap<Integer,Vip>(5);
	
	static 
	{
		try
		{
			vip_factory.put(LEVEL_1, new Vip(LEVEL_1));
			vip_factory.put(LEVEL_2, new Vip(LEVEL_2));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据会员等级得到会员信息
	 * @return
	 */
	public static Vip getByLevel(int level)
	{
		Vip vip = vip_factory.get(level);
		if( vip==null )
		{
			try
			{
				vip = new Vip(level);
				vip_factory.put(level, vip);
			}
			catch (Exception e)
			{
			}
		}
		return vip;
	}
	
	/**
	 * 根据会员称号id得到会员信息
	 */
	public static Vip getByTId( int t_id )
	{
		int level = LEVEL_0;
		if( t_id==19 || t_id==20 || t_id==23 )
		{
			level = LEVEL_1;
		}
		else if( t_id==21 || t_id==22 || t_id==24 )
		{
			level = LEVEL_2;
		}
		
		return getByLevel(level);
	}
	
	public static List<Vip> getVipList()
	{
		return new ArrayList<Vip>(vip_factory.values());
	}
}
