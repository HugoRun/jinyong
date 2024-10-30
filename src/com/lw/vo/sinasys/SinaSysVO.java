package com.lw.vo.sinasys;

import java.util.HashMap;

import com.ls.pub.util.DateUtil;

public class SinaSysVO
{
	public static HashMap<String, SinaSysVO> sinapvmap = new HashMap<String, SinaSysVO>();

	// 时间
	private String data;
	// 渠道号
	private String mv;
	// 预估用户
	private int iplogin;
	// pv1
	private int pv1;
	// pv2
	private int pv2;
	// loginpv
	private int loginpv;

	public static void getNew()
	{
		sinapvmap = new HashMap<String, SinaSysVO>();
	}

	// 添加新的渠道内容
	public void updateNewMv(String mv)
	{
		SinaSysVO vo = sinapvmap.get(mv);
		if (vo == null)
		{
			vo = new SinaSysVO();
			vo.setData(DateUtil.getTodayStr());
			vo.setMv(mv);
			vo.setIplogin(1);
			vo.setPv1(1);
			vo.setPv2(1);
			vo.setLoginpv(1);
			sinapvmap.put(mv, vo);
		}
	}

	// 添加新的IP内容
	public void updateNewIP(String mv, int x)
	{
		SinaSysVO vo = sinapvmap.get(mv);
		if (vo == null)
		{
			updateNewMv(mv);
			sinapvmap.put(mv, vo);
		}
		else
		{
			vo.setIplogin(x + 1);
			sinapvmap.put(mv, vo);
		}
	}

	// 添加新的PV1
	public void updateNewPv1(String mv)
	{
		SinaSysVO vo = sinapvmap.get(mv);
		if (vo == null)
		{
			updateNewMv(mv);
			sinapvmap.put(mv, vo);
		}
		else
		{
			int x = vo.getPv1();
			vo.setPv1(x + 1);
			sinapvmap.put(mv, vo);
		}
	}

	// 添加新的PV2
	public void updateNewPv2(String mv)
	{
		SinaSysVO vo = sinapvmap.get(mv);
		if (vo == null)
		{
			updateNewMv(mv);
			sinapvmap.put(mv, vo);
		}
		else
		{
			int x = vo.getPv2();
			vo.setPv2(x + 1);
			sinapvmap.put(mv, vo);
		}
	}

	// 添加新的LOGINPV
	public void updateNewLoginPv(String mv)
	{
		SinaSysVO vo = sinapvmap.get(mv);
		if (vo == null)
		{
			updateNewMv(mv);
			sinapvmap.put(mv, vo);
		}
		else
		{
			int x = vo.getLoginpv();
			vo.setLoginpv(x + 1);
			sinapvmap.put(mv, vo);
		}
	}

	public static HashMap<String, SinaSysVO> getSinapvmap()
	{
		return sinapvmap;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public String getMv()
	{
		return mv;
	}

	public void setMv(String mv)
	{
		this.mv = mv;
	}

	public int getIplogin()
	{
		return iplogin;
	}

	public void setIplogin(int iplogin)
	{
		this.iplogin = iplogin;
	}

	public int getPv1()
	{
		return pv1;
	}

	public void setPv1(int pv1)
	{
		this.pv1 = pv1;
	}

	public int getPv2()
	{
		return pv2;
	}

	public void setPv2(int pv2)
	{
		this.pv2 = pv2;
	}

	public int getLoginpv()
	{
		return loginpv;
	}

	public void setLoginpv(int loginpv)
	{
		this.loginpv = loginpv;
	}
}
