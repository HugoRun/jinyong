package com.ben.vo.honour;

import com.ben.dao.honour.RoleTitleDAO;
import com.ls.ben.cache.staticcache.honour.TitleCache;
import com.ls.model.event.EventManager;
import com.ls.model.event.TitleEvent;
import com.ls.pub.util.DateUtil;

/**
 * ��ҳƺ���Ϣ
 */
public class RoleTitleVO
{
	/** ����id */
	private int id;
	/** ��ɫ���� */
	private int pPk;
	/** �ƺ����� */
	private int tId;
	/** �Ƿ���ʾ:-1��ʾ����ʾ��1��ʾ��ʾ */
	private int isShow=-1;
	/**
	 * ����ʱ�䣬0��ʾ��ʱ������
	 */
	private long endTime;
	
	public RoleTitleVO()
	{
		
	}
	
	
	/**
	 * ������һ���ƺ�
	 * @param pPk
	 * @param tId			�ƺ�id
	 * @param useTime		ʹ��ʱ�䣨��λСʱ��
	 */
	public RoleTitleVO(int pPk,TitleVO newTitle )
	{
		this.pPk = pPk;
		this.tId = newTitle.getId();
		if( newTitle.getUseTime()>0 )
		{
			endTime = System.currentTimeMillis()+newTitle.getUseTime()*DateUtil.HOUR*1000;
		}
	}

	/**
	 * ����һ����ʱ�¼�
	 */
	public void createTimerEvent(EventManager eventManager)
	{
		//����Ч�ڵĳƺ�����һ����ʱ�¼�
		if( eventManager!=null && this.endTime>0)
		{
			eventManager.add(new TitleEvent(this));
		}
	}
	
	/**
	 * �Ƴ��ö�ʱ�¼�
	 */
	public void removeTimerEvent(EventManager eventManager)
	{
		//����Ч�ڵĳƺ�����һ����ʱ�¼�
		if( eventManager!=null && this.endTime>0)
		{
			eventManager.remove(new TitleEvent(this));
		}
	}
	
	/**
	 * ���ĸóƺ��Ƿ���ʾ
	 */
	public void updateIsShow()
	{
		this.isShow = -this.isShow;
		RoleTitleDAO roleTitleDAO = new RoleTitleDAO();
		roleTitleDAO.updateIsShow(pPk, tId);
	}
	
	/**
	 * �õ����ӵ������ַ���
	 * @return
	 */
	public String getAttriStr()
	{
		return this.getTitleInfo().getAttriStr();
	}
	/**
	 * �õ��ƺ�����
	 * @return
	 */
	public String getDes()
	{
		return this.getTitleInfo().getDes();
	}
	/**
	 * �õ��ƺ�����
	 * @return
	 */
	public String getName()
	{
		return this.getTitleInfo().getName();
	}
	/**
	 * �õ��ƺ���������
	 * @return
	 */
	public String getTypeName()
	{
		return this.getTitleInfo().getTypeName();
	}
	/**
	 * �õ��ƺ�����
	 * @return
	 */
	public int getType()
	{
		return this.getTitleInfo().getType();
	}
	
	public TitleVO getTitleInfo()
	{
		return TitleCache.getById(tId);
	}
	
	public String getSimpleLeftTimeDes()
	{
		if( this.getEndTime()<=0 )
		{
			return "";
		}
		
		long left_time = this.getEndTime()-System.currentTimeMillis();
		
		int left_time_for_min = (int) (left_time/(DateUtil.MINUTE*1000));//ʣ��ķ���
		
		String des_str = DateUtil.returnTimeStr(left_time_for_min);
		
		return des_str;
		
	}
	
	public String getLeftTimeDes()
	{
		if( this.getEndTime()<=0 )
		{
			return "";
		}
		
		long left_time = this.getEndTime()-System.currentTimeMillis();
		
		int left_time_for_min = (int) (left_time/(DateUtil.MINUTE*1000));//ʣ��ķ���
		
		String des_str = DateUtil.returnTimeStr(left_time_for_min);
		
		return "(ʣ��ʱ��:" + des_str + ")";
		
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getPPk()
	{
		return pPk;
	}
	public void setPPk(int pk)
	{
		pPk = pk;
	}
	public int getTId()
	{
		return tId;
	}
	public void setTId(int id)
	{
		tId = id;
	}
	public long getEndTime()
	{
		return endTime;
	}
	public void setEndTime(long endTime)
	{
		this.endTime = endTime;
	}


	public int getIsShow()
	{
		return isShow;
	}


	public void setIsShow(int isShow)
	{
		this.isShow = isShow;
	}
	
}
