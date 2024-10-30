package com.dp.vo.pubvo;


public class PubPageVO
{
	private Integer spid=0;//商品ID
	private Integer spcount=0;//商品数量
	private String  spname = "";//商品名称
	private Integer sid=0;//商品的主键
	private Integer page=1;//所有热销商品的当前页
	private Integer pagesize=7;//所有热销商品的每页所显示的条数
	private Integer allcount=0;//所有热销商品的总页数
	private Integer newpage=1;//本周特价商品的当前页
	private Integer newpagesize=7;//本周特价商品的每页显示的条数
	private Integer pagecount=0;//本周特价商品的总页数
	private Integer jfpage=1;//积分交易商品的当前页
	private Integer jfpagesize=7;//积分交易商品的每页显示条数
	private Integer jfpagecount=0;//积分交易商品的总页数
	private Integer rdpage=1;//交易记录当前页
	private Integer rdpapesize=7;//交易记录每页显示的条数
	private Integer tipsign=0;//返回标记
	private Integer typeid=0;//商品类别
	private Integer leapsg=0;//跳转页面标记
	public Integer getSpid()
	{
		return spid;
	}
	public void setSpid(Integer spid)
	{
		this.spid = spid;
	}
	public Integer getSpcount()
	{
		return spcount;
	}
	public void setSpcount(Integer spcount)
	{
		this.spcount = spcount;
	}
	public String getSpname()
	{
		return spname;
	}
	public void setSpname(String spname)
	{
		this.spname = spname;
	}
	public Integer getSid()
	{
		return sid;
	}
	public void setSid(Integer sid)
	{
		this.sid = sid;
	}
	public Integer getPage()
	{
		return page;
	}
	public void setPage(Integer page)
	{
		this.page = page;
	}
	public Integer getPagesize()
	{
		return pagesize;
	}
	public void setPagesize(Integer pagesize)
	{
		this.pagesize = pagesize;
	}
	public Integer getAllcount()
	{
		return allcount;
	}
	public void setAllcount(Integer allcount)
	{
		this.allcount = allcount;
	}
	public Integer getNewpage()
	{
		return newpage;
	}
	public void setNewpage(Integer newpage)
	{
		this.newpage = newpage;
	}
	public Integer getNewpagesize()
	{
		return newpagesize;
	}
	public void setNewpagesize(Integer newpagesize)
	{
		this.newpagesize = newpagesize;
	}
	public Integer getPagecount()
	{
		return pagecount;
	}
	public void setPagecount(Integer pagecount)
	{
		this.pagecount = pagecount;
	}
	public Integer getJfpage()
	{
		return jfpage;
	}
	public void setJfpage(Integer jfpage)
	{
		this.jfpage = jfpage;
	}
	public Integer getJfpagesize()
	{
		return jfpagesize;
	}
	public void setJfpagesize(Integer jfpagesize)
	{
		this.jfpagesize = jfpagesize;
	}
	public Integer getJfpagecount()
	{
		return jfpagecount;
	}
	public void setJfpagecount(Integer jfpagecount)
	{
		this.jfpagecount = jfpagecount;
	}
	public Integer getRdpage()
	{
		return rdpage;
	}
	public void setRdpage(Integer rdpage)
	{
		this.rdpage = rdpage;
	}
	public Integer getRdpapesize()
	{
		return rdpapesize;
	}
	public void setRdpapesize(Integer rdpapesize)
	{
		this.rdpapesize = rdpapesize;
	}
	public Integer getTipsign()
	{
		return tipsign;
	}
	public void setTipsign(Integer tipsign)
	{
		this.tipsign = tipsign;
	}
	public Integer getTypeid()
	{
		return typeid;
	}
	public void setTypeid(Integer typeid)
	{
		this.typeid = typeid;
	}
	public Integer getLeapsg()
	{
		return leapsg;
	}
	public void setLeapsg(Integer leapsg)
	{
		this.leapsg = leapsg;
	}
}
