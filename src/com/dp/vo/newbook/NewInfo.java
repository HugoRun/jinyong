package com.dp.vo.newbook;

import java.util.Date;

public class NewInfo
{
	private Integer bookid;
	private String typename;
	private String bookname; 
	private Integer zjcount;
	private String zjname;
	private Date   gxdate;
	private String neirong;
	private Integer typeid;
	private String  author;
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}
	public Integer getTypeid()
	{
		return typeid;
	}
	public void setTypeid(Integer typeid)
	{
		this.typeid = typeid;
	}
	public String getNeirong()
	{
		return neirong;
	}
	public void setNeirong(String neirong)
	{
		this.neirong = neirong;
	}
	public String getTypename()
	{
		return typename;
	}
	public void setTypename(String typename)
	{
		this.typename = typename;
	}
	public String getBookname()
	{
		return bookname;
	}
	public void setBookname(String bookname)
	{
		this.bookname = bookname;
	}
	public Integer getZjcount()
	{
		return zjcount;
	}
	public void setZjcount(Integer zjcount)
	{
		this.zjcount = zjcount;
	}
	public String getZjname()
	{
		return zjname;
	}
	public void setZjname(String zjname)
	{
		this.zjname = zjname;
	}
	public Date getGxdate()
	{
		return gxdate;
	}
	public void setGxdate(Date gxdate)
	{
		this.gxdate = gxdate;
	}
	public Integer getBookid()
	{
		return bookid;
	}
	public void setBookid(Integer bookid)
	{
		this.bookid = bookid;
	}
}
