package com.ls.web.service.title;

import org.apache.log4j.Logger;

import com.ben.dao.honour.TitleDAO;
import com.ls.pub.util.StringUtil;


/**
 * 功能:称谓，门派管理
 * @author 刘帅
 * Oct 22, 2008  11:14:29 AM
 */
public class TitleService
{
	Logger logger = Logger.getLogger("log.service");
	
	/**
	 * 根据多称谓id，得到称谓名称字符串
	 * @param title_ids
	 * @return
	 */
	public String getTitleNamesByTitleIDs( String title_ids)
	{
		if( title_ids==null || title_ids.equals("") || title_ids.equals("0") )
		{
			return "无";
		}
		TitleDAO titleDao = new TitleDAO();
		title_ids = StringUtil.processStringCondition(title_ids);
		String title_names = titleDao.getTitleNamesByTitleIDs(title_ids);
		if( title_names==null || title_ids.equals("") )
		{
			return "无";
		}
		
		return title_names;
	}
}
