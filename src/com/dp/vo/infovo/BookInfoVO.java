package com.dp.vo.infovo;

public class BookInfoVO
{
    private Integer bookid;
    private String  bookname;
    private String  author;
    private Integer typeid;
    private String  bookline;
    private String typename;
	public String getTypename()
	{
		return typename;
	}
	public void setTypename(String typename)
	{
		this.typename = typename;
	}
	public Integer getBookid()
	{
		return bookid;
	}
	public void setBookid(Integer bookid)
	{
		this.bookid = bookid;
	}
	public String getBookname()
	{
		return bookname;
	}
	public void setBookname(String bookname)
	{
		this.bookname = bookname;
	}
	public Integer getTypeid()
	{
		return typeid;
	}
	public void setTypeid(Integer typeid)
	{
		this.typeid = typeid;
	}
	public String getBookline()
	{
		return bookline;
	}
	public void setBookline(String bookline)
	{
		this.bookline = bookline;
	}
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}
}
