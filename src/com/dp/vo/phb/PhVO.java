package com.dp.vo.phb;

public class PhVO
{
	private Integer bookid;
	private Integer typeid;
	private String  typename;
    private String  bookname;
    private Integer scount;
	public String getBookname()
	{
		return bookname;
	}
	public void setBookname(String bookname)
	{
		this.bookname = bookname;
	}
	public Integer getScount()
	{
		return scount;
	}
	public void setScount(Integer scount)
	{
		this.scount = scount;
	}
	public Integer getBookid()
	{
		return bookid;
	}
	public void setBookid(Integer bookid)
	{
		this.bookid = bookid;
	}
	public Integer getTypeid()
	{
		return typeid;
	}
	public void setTypeid(Integer typeid)
	{
		this.typeid = typeid;
	}
	public String getTypename()
	{
		return typename;
	}
	public void setTypename(String typename)
	{
		this.typename = typename;
	}
    
}
