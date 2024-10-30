package com.pub.ben;

import java.sql.ResultSet;

import com.pub.db.jygamedb.Jygamedb;
import com.pub.db.mysql.SqlData;


public class PageBean
{
	SqlData con;// 前台 1表示
	Jygamedb conn;// 后台 2表示

	private int curPage;// 当前页面数
	private int maxPage;// 一共有多少页
	private int maxRowCount;// 一共有多少行
	private int pageNumber = 7;// 每一页多少行 
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCurPage()
	{
		return curPage;
	}

	public void setCurPage(int curPage)
	{
		this.curPage = curPage;
	}

	public int getMaxPage()
	{
		return maxPage;
	}

	public void setMaxPage(int maxPage)
	{
		this.maxPage = maxPage;
	}

	public int getMaxRowCount()
	{
		return maxRowCount;
	}

	public void setMaxRowCount(int maxRowCount)
	{
		this.maxRowCount = maxRowCount;
	}

	public int getPageNumber()
	{
		return pageNumber;
	}

	public void setPageNumber(int pageNumber)
	{
		this.pageNumber = pageNumber;
	} 
	
	
	/*
	 * 分页显示的公共类，查看表里一共有多少行
	 */
	public void tableCount(String tablesName, int database,String where) {
		try {
			con = new SqlData();
			conn = new Jygamedb();
			ResultSet rs = null;
			String wheres = null;
			if(where != null ){
				wheres = where;
			}
			if (database == 1) {//前台数据库
				rs = con.query("select count(*) from " + tablesName +" "+ wheres +" "); // 查询共有几行数据
			} else if (database == 2) {//后台数据库
				rs = conn.query("select count(*) from " + tablesName + wheres +" "); // 查询共有几行数据
			}
			if (rs.next()) {
				this.setMaxRowCount(rs.getInt(1));
			}
			////System.out.println("maxRowCount is:" + this.getMaxRowCount());
			if (this.getMaxRowCount() % this.getPageNumber() == 0) {
				this.setMaxPage((this.getMaxRowCount() / this.getPageNumber()));
			} else {
				this.setMaxPage((this.getMaxRowCount() / this.getPageNumber() + 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}
	
	/**
	 * 返回 mysql limit
	 * @param pageBean
	 * @return
	 */
	public String getLimitNumber() {
		String limitNumber = " limit "+ (this.getCurPage() * this.getPageNumber()) +","+ this.getPageNumber()+"";
		////System.out.println(limitNumber);
		return limitNumber;
	}

	/**
	 * 打印出翻页连接
	 * 
	 * @return
	 */
	public String getFooter(String url) {
        if (this.getMaxPage()<= 1) {
            return "";
        }
        else
        {
        	StringBuffer str = new StringBuffer();
            if(this.getCurPage()==0)
            {
            	str.append("<a href='").append(url).append("&amp;pageNo=");
            	str.append(this.getCurPage()+1);
            	str.append("'>下一页</a><br/>");
            }
            else if(this.getCurPage()==1){//if (pageBean.getCurPage() < pageBean.getMaxPage()) {
            	str.append("<a href='").append(url).append("&amp;pageNo=");
            	str.append(this.getCurPage()+1);
            	str.append("'>下一页</a> ");
            	str.append("<a href='").append(url).append("&amp;pageNo=");
            	str.append(this.getCurPage()-1);
            	str.append("'>上一页</a><br/>");
            }
            else if ((this.getCurPage() + 1 ) < this.getMaxPage()) {
            	str.append("<a href='").append(url).append("&amp;pageNo=");
            	str.append(this.getCurPage()+1);
            	str.append("'>下一页</a> ");
            	str.append("<a href='").append(url).append("&amp;pageNo=");
            	str.append(this.getCurPage()-1);
            	str.append("'>上一页</a><br/>");
            }
            else {
            	str.append("<a href='").append(url).append("&amp;pageNo=");
            	str.append(this.getCurPage()-1);
            	str.append("'>上一页</a><br/>");
                 }
          return str.toString();
        }
    } 
}
