package com.dp.vo.pubvo;


public class BookPageVO
{
	private Integer hour=0;//当前小时
	private Integer minutes=0;//当前分钟
    private Integer page=1;//当前页
    private Integer pagesize=250;//每页显示的字数
    private Integer bookid;//小说主键ID
    private Integer zjlistpage=1;//章节列表的当前列
    private Integer zjpagesize=5;//章节列表每页显示的条数
    private Integer exists=0;
    private Integer phpage=1;//排行榜的当前页
    private Integer phpagesize=10;//排行榜每页显示排行小说的条数
    private Integer squpage=1;//分类排行榜的当前页
    private Integer squpagesize=10;//分类排行榜每一页显示的条数
    private Integer n7bookid;//阅读换章时的小说ID
    private Integer n7zjid;//阅读还章时的章节号
    private Integer tipsign=0;//返回标记
    private Integer typeid;
    private Integer zjid;//小说章节ID
    private String  novename;//小说搜索关键字
    private Integer resultsign=0;//返回小说搜索结果的标记1.n2,2.n8,3.n12,4.n13
    private Integer typepage=1;//分类查询小说列表的当前页
    private Integer typepagesize=10;//分类查询小说列表的总页数
	public Integer getHour()
	{
		return hour;
	}
	public void setHour(Integer hour)
	{
		this.hour = hour;
	}
	public Integer getMinutes()
	{
		return minutes;
	}
	public void setMinutes(Integer minutes)
	{
		this.minutes = minutes;
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
	public Integer getBookid()
	{
		return bookid;
	}
	public void setBookid(Integer bookid)
	{
		this.bookid = bookid;
	}
	public Integer getZjlistpage()
	{
		return zjlistpage;
	}
	public void setZjlistpage(Integer zjlistpage)
	{
		this.zjlistpage = zjlistpage;
	}
	public Integer getZjpagesize()
	{
		return zjpagesize;
	}
	public void setZjpagesize(Integer zjpagesize)
	{
		this.zjpagesize = zjpagesize;
	}
	public Integer getExists()
	{
		return exists;
	}
	public void setExists(Integer exists)
	{
		this.exists = exists;
	}
	public Integer getPhpage()
	{
		return phpage;
	}
	public void setPhpage(Integer phpage)
	{
		this.phpage = phpage;
	}
	public Integer getPhpagesize()
	{
		return phpagesize;
	}
	public void setPhpagesize(Integer phpagesize)
	{
		this.phpagesize = phpagesize;
	}
	public Integer getSqupage()
	{
		return squpage;
	}
	public void setSqupage(Integer squpage)
	{
		this.squpage = squpage;
	}
	public Integer getSqupagesize()
	{
		return squpagesize;
	}
	public void setSqupagesize(Integer squpagesize)
	{
		this.squpagesize = squpagesize;
	}
	public Integer getN7bookid()
	{
		return n7bookid;
	}
	public void setN7bookid(Integer n7bookid)
	{
		this.n7bookid = n7bookid;
	}
	public Integer getN7zjid()
	{
		return n7zjid;
	}
	public void setN7zjid(Integer n7zjid)
	{
		this.n7zjid = n7zjid;
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
	public Integer getZjid()
	{
		return zjid;
	}
	public void setZjid(Integer zjid)
	{
		this.zjid = zjid;
	}
	public String getNovename()
	{
		return novename;
	}
	public void setNovename(String novename)
	{
		this.novename = novename;
	}
	public Integer getResultsign()
	{
		return resultsign;
	}
	public void setResultsign(Integer resultsign)
	{
		this.resultsign = resultsign;
	}
	public Integer getTypepage()
	{
		return typepage;
	}
	public void setTypepage(Integer typepage)
	{
		this.typepage = typepage;
	}
	public Integer getTypepagesize()
	{
		return typepagesize;
	}
	public void setTypepagesize(Integer typepagesize)
	{
		this.typepagesize = typepagesize;
	}
}
