package com.ls.pub.bean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ls.pub.config.GameConfig;

/**
 * 功能:分页对象. 包含当前页数据及分页信息如总记录数.
 * 
 * @author 刘帅 8:07:28 AM
 */
public class QueryPage
{

	public static int DEFAULT_PAGE_SIZE = 7;// 默认本页容量

	private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

	private long start; // 当前页第一条数据在List中的位置,从0开始

	private Object data; // 当前页中存放的记录,类型一般为List

	private long totalCount; // 总记录数
	
	private HttpServletResponse response;
	private String url;

	/**
	 * 构造方法，只构造空页.
	 */
	public QueryPage()
	{
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList());
	}

	/**
	 * 通过页号和记录总数构造
	 * @param page_no     从1开始的页号
	 * @param totalSize   数据库中总记录条数
	 */
	public QueryPage(int page_no, long totalSize)
	{
		this.start = getStartOfPage(page_no);
		this.totalCount = totalSize;
	}
	
	/**
	 * 通过页号和记录总数构造
	 * @param page_no     从1开始的页号
	 * @param totalSize   数据库中总记录条数
	 * @param pageSize 	  每页显示条数
	 */
	public QueryPage(int page_no, long totalSize,int pageSize)
	{
		this.start = getStartOfPage(page_no,pageSize);
		this.totalCount = totalSize;
		this.pageSize = pageSize;
	}

	/**
	 * 默认构造方法.
	 * 
	 * @param start
	 *            本页数据在数据库中的起始位置
	 * @param totalSize
	 *            数据库中总记录条数
	 * @param pageSize
	 *            本页容量
	 * @param data
	 *            本页包含的数据
	 */
	public QueryPage(long start, long totalSize, int pageSize, Object data)
	{
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalSize;
		this.data = data;
	}
	
	/**
	 * 构造方法
	 * @param page_no
	 * @param allData
	 */
	public QueryPage(int page_no, List all_data)
	{
		if( all_data!=null )
		{
			this.start = getStartOfPage(page_no,pageSize);
			this.totalCount = all_data.size();
			int fromIndex = (int) this.start;
			int toIndex = (int) (this.start+this.pageSize);
			if( toIndex>all_data.size()-1)
			{
				toIndex = all_data.size();
			}
			this.data = all_data.subList(fromIndex, toIndex);
		}
	}

	/**
	 * 取总记录数.
	 */
	public long getTotalCount()
	{
		return this.totalCount;
	}

	/**
	 * 取总页数.
	 */
	public long getTotalPageCount()
	{
		if (totalCount % pageSize == 0)
			return totalCount / pageSize;
		else
			return totalCount / pageSize + 1;
	}

	/**
	 * 取每页数据容量.
	 */
	public int getPageSize()
	{
		return pageSize;
	}

	/**
	 * 取当前页中的记录.
	 */
	public Object getResult()
	{
		return data;
	}
	
	/**
	 * 设置前页中的记录.
	 */
	public void setResult(Object data)
	{
		this.data = data;
	}
	
	/**
	 * 设置前页中的记录.
	 */
	public void setPageSize(int size)
	{
		this.pageSize = size;
	}

	/**
	 * 取该页当前页码,页码从1开始.
	 */
	public long getCurrentPageNo()
	{
		return start / pageSize + 1;
	}

	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage()
	{
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean hasPreviousPage()
	{
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * 当前页第一条数据在List中的位置,从0开始
	 */
	public long getStartOfPage()
	{
		return start;
	}
	
	/**
	 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
	 * 
	 * @see #getStartOfPage(int,int)
	 */
	public static int getStartOfPage(int pageNo)
	{
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 * 
	 * @param pageNo
	 *            从1开始的页号
	 * @param pageSize
	 *            每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int pageSize)
	{
		return (pageNo - 1) * pageSize;
	}
	
	/**
	 * 设置url
	 * @param response
	 * @param url
	 */
	public void setURL( HttpServletResponse response,String url  )
	{
		this.response = response;
		this.url = url;
	}
	
	/**
	 * 得到分页页脚显示
	 * @return
	 */
	public String getPageFoot()
	{
		if( this.response==null || this.url==null )
		{
			return "";
		}
		if( this.data==null || ((List)this.data).size()==0 )
		{
			return "无<br/><br/>";
		}
		
		StringBuffer sb = new StringBuffer();
		
		if( hasNextPage() )
		{
			sb.append("<anchor>下一页");
			sb.append("<go method=\"post\" href=\"");
			sb.append(response.encodeURL(GameConfig.getContextPath()+url)).append("\">");
			sb.append("<postfield name=\"page_no\" value=\"").append(getCurrentPageNo()+1).append("\" />");
			sb.append("</go>");
			sb.append("</anchor>");
		}
		if( hasPreviousPage() )
		{
			sb.append("<anchor>上一页");
			sb.append("<go method=\"post\" href=\"");
			sb.append(response.encodeURL(GameConfig.getContextPath()+url)).append("\">");
			sb.append("<postfield name=\"page_no\" value=\"").append(getCurrentPageNo()-1).append("\" />");
			sb.append("</go>");
			sb.append("</anchor>");
		}
		if ( getCurrentPageNo() == 1 && getTotalPageCount() > 2) 
		{
			sb.append("<anchor>到末页");
			sb.append("<go method=\"post\" href=\"");
			sb.append(response.encodeURL(GameConfig.getContextPath()+url)).append("\">");
			sb.append("<postfield name=\"page_no\" value=\"").append(getTotalPageCount()).append("\" />");
			sb.append("</go>");
			sb.append("</anchor>");
		}
    	if( getCurrentPageNo() == getTotalPageCount() && getTotalPageCount() > 2 ) 
    	{	 
    		sb.append("<anchor>到首页");
			sb.append("<go method=\"post\" href=\"");
			sb.append(response.encodeURL(GameConfig.getContextPath()+url)).append("\">");
			sb.append("<postfield name=\"page_no\" value=\"1\" />");
			sb.append("</go>");
			sb.append("</anchor>");
    	}
		return sb.append("<br/>").toString();
	}
}