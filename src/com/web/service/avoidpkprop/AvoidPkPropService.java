package com.web.service.avoidpkprop;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ben.dao.avoidpkprop.AvoidPkPropDAO;
import com.ben.vo.avoidpkprop.AvoidPkPropVO;
import com.ls.pub.util.DateUtil;

/**
 * @author ��ƾ� ��PK ����
 */
public class AvoidPkPropService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * ������PK����ʹ��ʱ��
	 * 
	 * @param pPK
	 * @param beginTime
	 * @param endTime
	 */
	public void addAvoidPkProp(int pPK, String beginTime, String endTime)
	{

		AvoidPkPropDAO dao = new AvoidPkPropDAO();
		dao.deleteAvoidPkProp(pPK);
		dao.addAvoidPkProp(pPK, beginTime, endTime);
	}
	/**
	 * ɾ������ʱ��
	 * @param pPk
	 */
	public void deleteAvoidPkProp(int pPk){
		AvoidPkPropDAO dao = new AvoidPkPropDAO();
		dao.deleteAvoidPkProp(pPk);
	}
	/**
	 * �ж��Ƿ��ڱ���ʱ����
	 * @param pPk
	 * @return
	 */
	public boolean isAvoidPkPropTime(int pPk)
	{
		AvoidPkPropDAO dao = new AvoidPkPropDAO();
		return dao.isAvoidPkPropTime(pPk);
	}

	/**
	 * ���ػ�ʣ�¶��ٱ���ʱ��
	 * 
	 * @param pPk
	 * @return
	 */
	public int getAvoidPkProp(int pPk)
	{
		AvoidPkPropDAO dao = new AvoidPkPropDAO();
		// ����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		AvoidPkPropVO vo = (AvoidPkPropVO) dao.getAvoidPkProp(pPk, Time);
		int s = 0;
		if (vo != null)
		{   //���VO��Ϊ��˵���ڱ�����Χ���б���Ч����ô
			Date begin = DateUtil.strToDate(Time);
			Date end = DateUtil.strToDate(vo.getEndTime()); 
			 s = DateUtil.getDifferTimes(begin,end);
		}
		return s;
	}
}
