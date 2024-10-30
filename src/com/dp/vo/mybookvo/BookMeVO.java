package com.dp.vo.mybookvo;

import java.util.Date;

public class BookMeVO
{
	private Integer myid;
	private Integer roleid;
	private Integer bookid;
	private Integer typeid;
	private String bookmark;
	private String typename;
	private String bookname;
	private Integer newzjcount;
	private String zjname;
	private Date   gxdate;
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

	public Integer getNewzjcount()
	{
		return newzjcount;
	}
	public void setNewzjcount(Integer newzjcount)
	{
		this.newzjcount = newzjcount;
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
	public Integer getMyid()
	{
		return myid;
	}
	public void setMyid(Integer myid)
	{
		this.myid = myid;
	}

	public Integer getRoleid()
	{
		return roleid;
	}
	public void setRoleid(Integer roleid)
	{
		this.roleid = roleid;
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
	public String getBookmark()
	{
		return bookmark;
	}
	public void setBookmark(String bookmark)
	{
		this.bookmark = bookmark;
	}
}
