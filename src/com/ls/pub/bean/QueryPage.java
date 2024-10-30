package com.ls.pub.bean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ls.pub.config.GameConfig;

/**
 * ����:��ҳ����. ������ǰҳ���ݼ���ҳ��Ϣ���ܼ�¼��.
 * 
 * @author ��˧ 8:07:28 AM
 */
public class QueryPage
{

	public static int DEFAULT_PAGE_SIZE = 7;// Ĭ�ϱ�ҳ����

	private int pageSize = DEFAULT_PAGE_SIZE; // ÿҳ�ļ�¼��

	private long start; // ��ǰҳ��һ��������List�е�λ��,��0��ʼ

	private Object data; // ��ǰҳ�д�ŵļ�¼,����һ��ΪList

	private long totalCount; // �ܼ�¼��
	
	private HttpServletResponse response;
	private String url;

	/**
	 * ���췽����ֻ�����ҳ.
	 */
	public QueryPage()
	{
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList());
	}

	/**
	 * ͨ��ҳ�źͼ�¼��������
	 * @param page_no     ��1��ʼ��ҳ��
	 * @param totalSize   ���ݿ����ܼ�¼����
	 */
	public QueryPage(int page_no, long totalSize)
	{
		this.start = getStartOfPage(page_no);
		this.totalCount = totalSize;
	}
	
	/**
	 * ͨ��ҳ�źͼ�¼��������
	 * @param page_no     ��1��ʼ��ҳ��
	 * @param totalSize   ���ݿ����ܼ�¼����
	 * @param pageSize 	  ÿҳ��ʾ����
	 */
	public QueryPage(int page_no, long totalSize,int pageSize)
	{
		this.start = getStartOfPage(page_no,pageSize);
		this.totalCount = totalSize;
		this.pageSize = pageSize;
	}

	/**
	 * Ĭ�Ϲ��췽��.
	 * 
	 * @param start
	 *            ��ҳ���������ݿ��е���ʼλ��
	 * @param totalSize
	 *            ���ݿ����ܼ�¼����
	 * @param pageSize
	 *            ��ҳ����
	 * @param data
	 *            ��ҳ����������
	 */
	public QueryPage(long start, long totalSize, int pageSize, Object data)
	{
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalSize;
		this.data = data;
	}
	
	/**
	 * ���췽��
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
	 * ȡ�ܼ�¼��.
	 */
	public long getTotalCount()
	{
		return this.totalCount;
	}

	/**
	 * ȡ��ҳ��.
	 */
	public long getTotalPageCount()
	{
		if (totalCount % pageSize == 0)
			return totalCount / pageSize;
		else
			return totalCount / pageSize + 1;
	}

	/**
	 * ȡÿҳ��������.
	 */
	public int getPageSize()
	{
		return pageSize;
	}

	/**
	 * ȡ��ǰҳ�еļ�¼.
	 */
	public Object getResult()
	{
		return data;
	}
	
	/**
	 * ����ǰҳ�еļ�¼.
	 */
	public void setResult(Object data)
	{
		this.data = data;
	}
	
	/**
	 * ����ǰҳ�еļ�¼.
	 */
	public void setPageSize(int size)
	{
		this.pageSize = size;
	}

	/**
	 * ȡ��ҳ��ǰҳ��,ҳ���1��ʼ.
	 */
	public long getCurrentPageNo()
	{
		return start / pageSize + 1;
	}

	/**
	 * ��ҳ�Ƿ�����һҳ.
	 */
	public boolean hasNextPage()
	{
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	/**
	 * ��ҳ�Ƿ�����һҳ.
	 */
	public boolean hasPreviousPage()
	{
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * ��ǰҳ��һ��������List�е�λ��,��0��ʼ
	 */
	public long getStartOfPage()
	{
		return start;
	}
	
	/**
	 * ��ȡ��һҳ��һ�����������ݼ���λ�ã�ÿҳ����ʹ��Ĭ��ֵ.
	 * 
	 * @see #getStartOfPage(int,int)
	 */
	public static int getStartOfPage(int pageNo)
	{
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * ��ȡ��һҳ��һ�����������ݼ���λ��.
	 * 
	 * @param pageNo
	 *            ��1��ʼ��ҳ��
	 * @param pageSize
	 *            ÿҳ��¼����
	 * @return ��ҳ��һ������
	 */
	public static int getStartOfPage(int pageNo, int pageSize)
	{
		return (pageNo - 1) * pageSize;
	}
	
	/**
	 * ����url
	 * @param response
	 * @param url
	 */
	public void setURL( HttpServletResponse response,String url  )
	{
		this.response = response;
		this.url = url;
	}
	
	/**
	 * �õ���ҳҳ����ʾ
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
			return "��<br/><br/>";
		}
		
		StringBuffer sb = new StringBuffer();
		
		if( hasNextPage() )
		{
			sb.append("<anchor>��һҳ");
			sb.append("<go method=\"post\" href=\"");
			sb.append(response.encodeURL(GameConfig.getContextPath()+url)).append("\">");
			sb.append("<postfield name=\"page_no\" value=\"").append(getCurrentPageNo()+1).append("\" />");
			sb.append("</go>");
			sb.append("</anchor>");
		}
		if( hasPreviousPage() )
		{
			sb.append("<anchor>��һҳ");
			sb.append("<go method=\"post\" href=\"");
			sb.append(response.encodeURL(GameConfig.getContextPath()+url)).append("\">");
			sb.append("<postfield name=\"page_no\" value=\"").append(getCurrentPageNo()-1).append("\" />");
			sb.append("</go>");
			sb.append("</anchor>");
		}
		if ( getCurrentPageNo() == 1 && getTotalPageCount() > 2) 
		{
			sb.append("<anchor>��ĩҳ");
			sb.append("<go method=\"post\" href=\"");
			sb.append(response.encodeURL(GameConfig.getContextPath()+url)).append("\">");
			sb.append("<postfield name=\"page_no\" value=\"").append(getTotalPageCount()).append("\" />");
			sb.append("</go>");
			sb.append("</anchor>");
		}
    	if( getCurrentPageNo() == getTotalPageCount() && getTotalPageCount() > 2 ) 
    	{	 
    		sb.append("<anchor>����ҳ");
			sb.append("<go method=\"post\" href=\"");
			sb.append(response.encodeURL(GameConfig.getContextPath()+url)).append("\">");
			sb.append("<postfield name=\"page_no\" value=\"1\" />");
			sb.append("</go>");
			sb.append("</anchor>");
    	}
		return sb.append("<br/>").toString();
	}
}