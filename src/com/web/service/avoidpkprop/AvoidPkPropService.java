package com.web.service.avoidpkprop;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ben.dao.avoidpkprop.AvoidPkPropDAO;
import com.ben.vo.avoidpkprop.AvoidPkPropVO;
import com.ls.pub.util.DateUtil;

/**
 * @author 侯浩军 免PK 道具
 */
public class AvoidPkPropService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * 插入免PK道具使用时间
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
	 * 删除保护时间
	 * @param pPk
	 */
	public void deleteAvoidPkProp(int pPk){
		AvoidPkPropDAO dao = new AvoidPkPropDAO();
		dao.deleteAvoidPkProp(pPk);
	}
	/**
	 * 判断是否在保护时间内
	 * @param pPk
	 * @return
	 */
	public boolean isAvoidPkPropTime(int pPk)
	{
		AvoidPkPropDAO dao = new AvoidPkPropDAO();
		return dao.isAvoidPkPropTime(pPk);
	}

	/**
	 * 返回还剩下多少保护时间
	 * 
	 * @param pPk
	 * @return
	 */
	public int getAvoidPkProp(int pPk)
	{
		AvoidPkPropDAO dao = new AvoidPkPropDAO();
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		AvoidPkPropVO vo = (AvoidPkPropVO) dao.getAvoidPkProp(pPk, Time);
		int s = 0;
		if (vo != null)
		{   //如果VO不为空说明在保护范围内有保护效果那么
			Date begin = DateUtil.strToDate(Time);
			Date end = DateUtil.strToDate(vo.getEndTime()); 
			 s = DateUtil.getDifferTimes(begin,end);
		}
		return s;
	}
}
