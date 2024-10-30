package com.ls.web.service.control;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ls.ben.dao.info.partinfo.TimeControlDao;
import com.ls.ben.vo.info.partinfo.TimeControlVO;
import com.ls.pub.util.DateUtil;

/**
 * ����:���Ʊ���Ҫʱ���ʹ�ô������ƵĶ���
 * @author ��˧
 * Sep 25, 2008  3:22:39 PM
 */
public class TimeControlService
{
	//���ƶ�������
	public static final int PROP = 1;
	public static final int MENU = 2;
	
	//��������
	public static final int ANOTHERPROP = 3;
	
	//�˵���������
	public static final int MENUTOUCHTASK = 4;
	//��������
	public static final int TONG  = 5;
	//��������
	public static final int SKILL  = 6;
	//��������
	public static final int VIPLABORAGE  = 7;
	// ��������
	public static final int JIANGLI  = 8;
	//BUFF�ĵ���ʽ��Ϣ
	public static final int BUFFPOPMSG  = 9;
	//��ȡ����������ߵ���
	public static final int GETNEWYEARPRIZE  = 10;
	//���ɽ���ʹ��(��ȡͼ��buff)
	public static final int F_USE_BUILD  = 11;
	
	
	/**
	 * ���¶���ʹ����Ϣ
	 * @param p_pk
	 * @param object_id
	 * @param object_type
	 */
	public void updateControlInfo( int p_pk,int object_id,int object_type )
	{
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		//�ж��Ƿ��п�����Ϣ
		if( timeControl==null )
		{
			timeControlDao.add(p_pk, object_id, object_type);
		}
		else
		{
			if( DateUtil.isSameDay(timeControl.getUseDatetime()) )//�ϴ�ʹ�þ��ǵ���
			{
				//����ʹ��״̬��ʹ��ʱ���ʹ�ô�����
				timeControlDao.updateUseState(p_pk, object_id, object_type);
			}
			else
			{
				//�����һ��ʹ��
				timeControlDao.updateFirstTimeState(p_pk, object_id, object_type);
			}
		}
	}
	
	
	
	/**
	 * ���ݿ�����Ϣ�ж��Ƿ����
	 * @param p_pk
	 * @param object_id
	 * @param object_type
	 * @param max_use_times   ÿ�����ʹ�ô���
	 * @return
	 */
	public boolean isUseable(int p_pk,int object_id,int object_type,int max_use_times )
	{
		boolean isUseable = true;
		
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		
		//�ϴ�ʹ�þ��ǵ���,��ʹ�ô����������ʹ�ô���
		if( timeControl!=null && DateUtil.isSameDay(timeControl.getUseDatetime()) && max_use_times<=timeControl.getUseTimes() )
		{
			isUseable =false;
		}
		
		return isUseable;
	}
	
	/**
	 * ���ݿ�����Ϣ�ж��Ƿ����
	 * @param p_pk
	 * @param object_id
	 * @param object_type
	 * @param max_use_time   ÿ��ʹ�ü������,��λΪ����
	 * @return
	 */
	public boolean isUseableWithNum(int p_pk,int object_id,int object_type,int max_use_time )
	{
		boolean isUseable = true;
		
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		
		//�ϴ�ʹ�þ��ǵ���,��ʹ�ô����������ʹ�ô���
		if( timeControl!=null && DateUtil.isSameDay(timeControl.getUseDatetime())  )
		{
			Date dt = new Date();
			SimpleDateFormat sd = new SimpleDateFormat();
			
			if(dt.getTime() - timeControl.getUseDatetime().getTime() < max_use_time*60*1000 ) {										
				isUseable =false;
			}
		}
		
		return isUseable;
	}
	
	/**
	 * �õ��ÿ��ƶ����Ѿ������˶��ٴ�.
	 */
	public int alreadyUseNumber(int p_pk,int object_id,int object_type) {
		int num = 0;
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		if(timeControl != null && timeControl.getId() != 0) {
			num = timeControl.getUseTimes();
		}
		return num;
	}
	
	/**
	 * �õ��ÿ��ƶ���
	 */
	public TimeControlVO getControlObject(int p_pk,int object_id,int object_type) {
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		return timeControl;
	}
	
	/**
	 * ���¶���ʹ����Ϣ,�������Ҫ�Ǹ��������������Ӧ�����������󣬼�:
	 * 			Ҫ��ĳ��Ʒʹ�ú�,��һ����ʱ������ĳ������,
	 * ��������������,ʹ�ú���ĳ��ʱ�������������Բ�������.
	 * @param p_pk
	 * @param object_id
	 * @param object_type
	 * @param minutes �Է���Ϊ��λ�������ʱ��Ϊ���ڼ���time
	 */
	public void updateControlInfoByTime( int p_pk,int object_id,int object_type,int minutes )
	{
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		//�ж��Ƿ��п�����Ϣ
		if( timeControl==null )
		{
			timeControlDao.add(p_pk, object_id, object_type,minutes);
		}
		else
		{
			if( DateUtil.isSameDay(timeControl.getUseDatetime()) )//�ϴ�ʹ�þ��ǵ���
			{
				//����ʹ��״̬��ʹ��ʱ���ʹ�ô�����
				//timeControlDao.updateUseState(p_pk, object_id, object_type);
				Date dta = timeControl.getUseDatetime();
				//������ݿ��е�ʱ������ڵ�ʱ��Ҫ��, �������ݿ��е�ʱ���������minute�������������ʱ���ϼ�minute
				if(dta.after(new Date())) {
					
					timeControlDao.updateTimeStateByTime(p_pk, object_id, object_type,minutes);
				} else {
					timeControlDao.updateFirstTimeStateByTime(p_pk, object_id, object_type,minutes);
				}				
			}
			else
			{
				//�����һ��ʹ��
				timeControlDao.updateFirstTimeStateByTime(p_pk, object_id, object_type,minutes);
			}
		}
	}
	
	
	/**
	 * ���ݿ�����Ϣ�ж��Ƿ����
	 * @param p_pk
	 * @param object_id
	 * @param object_type
	 * Ϊfalseʱ��Ϊ����Ҫ����, Ϊtrueʱ��Ϊ��Ҫ����
	 * @return
	 */
	public boolean isUseableToTimeControl(int p_pk,int object_id,int object_type )
	{
		boolean isUseable = true;
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		
		Date dt = new Date();
		//��timeControl��Ϊ��,�ҵ�ǰʱ������ݿ�����ʱ��Ҫ��.
		if( timeControl!=null && dt.before(timeControl.getUseDatetime()) )
		{
			isUseable =false;
		}
		return isUseable;
	}
	
	/**�ж��Ƿ�ʹ�ù���BUFF**/
	public boolean isUseableByAll(int p_pk,int object_id,int object_type,int max_use_times )
	{
		boolean isUseable = true;
		
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		
		//�ϴ�ʹ�þ��ǵ���,��ʹ�ô����������ʹ�ô���
		if( timeControl!=null)
		{
			isUseable =false;
		}
		
		return isUseable;
	}
	
	/**����Ҹ���ʹ��*/
	public void updateControlInfoByAll( int p_pk,int object_id,int object_type )
	{
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		//�ж��Ƿ��п�����Ϣ
		if( timeControl==null )
		{
			timeControlDao.add(p_pk, object_id, object_type);
		}
	}
}
