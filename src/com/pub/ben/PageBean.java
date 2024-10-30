package com.pub.ben;

import java.sql.ResultSet;

import com.pub.db.jygamedb.Jygamedb;
import com.pub.db.mysql.SqlData;


public class PageBean
{
	SqlData con;// ǰ̨ 1��ʾ
	Jygamedb conn;// ��̨ 2��ʾ

	private int curPage;// ��ǰҳ����
	private int maxPage;// һ���ж���ҳ
	private int maxRowCount;// һ���ж�����
	private int pageNumber = 7;// ÿһҳ������ 
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
	 * ��ҳ��ʾ�Ĺ����࣬�鿴����һ���ж�����
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
			if (database == 1) {//ǰ̨���ݿ�
				rs = con.query("select count(*) from " + tablesName +" "+ wheres +" "); // ��ѯ���м�������
			} else if (database == 2) {//��̨���ݿ�
				rs = conn.query("select count(*) from " + tablesName + wheres +" "); // ��ѯ���м�������
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
	 * ���� mysql limit
	 * @param pageBean
	 * @return
	 */
	public String getLimitNumber() {
		String limitNumber = " limit "+ (this.getCurPage() * this.getPageNumber()) +","+ this.getPageNumber()+"";
		////System.out.println(limitNumber);
		return limitNumber;
	}

	/**
	 * ��ӡ����ҳ����
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
            	str.append("'>��һҳ</a><br/>");
            }
            else if(this.getCurPage()==1){//if (pageBean.getCurPage() < pageBean.getMaxPage()) {
            	str.append("<a href='").append(url).append("&amp;pageNo=");
            	str.append(this.getCurPage()+1);
            	str.append("'>��һҳ</a> ");
            	str.append("<a href='").append(url).append("&amp;pageNo=");
            	str.append(this.getCurPage()-1);
            	str.append("'>��һҳ</a><br/>");
            }
            else if ((this.getCurPage() + 1 ) < this.getMaxPage()) {
            	str.append("<a href='").append(url).append("&amp;pageNo=");
            	str.append(this.getCurPage()+1);
            	str.append("'>��һҳ</a> ");
            	str.append("<a href='").append(url).append("&amp;pageNo=");
            	str.append(this.getCurPage()-1);
            	str.append("'>��һҳ</a><br/>");
            }
            else {
            	str.append("<a href='").append(url).append("&amp;pageNo=");
            	str.append(this.getCurPage()-1);
            	str.append("'>��һҳ</a><br/>");
                 }
          return str.toString();
        }
    } 
}
