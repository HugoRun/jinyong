package com.ls.web.service.title;

import org.apache.log4j.Logger;

import com.ben.dao.honour.TitleDAO;
import com.ls.pub.util.StringUtil;


/**
 * ����:��ν�����ɹ���
 * @author ��˧
 * Oct 22, 2008  11:14:29 AM
 */
public class TitleService
{
	Logger logger = Logger.getLogger("log.service");
	
	/**
	 * ���ݶ��νid���õ���ν�����ַ���
	 * @param title_ids
	 * @return
	 */
	public String getTitleNamesByTitleIDs( String title_ids)
	{
		if( title_ids==null || title_ids.equals("") || title_ids.equals("0") )
		{
			return "��";
		}
		TitleDAO titleDao = new TitleDAO();
		title_ids = StringUtil.processStringCondition(title_ids);
		String title_names = titleDao.getTitleNamesByTitleIDs(title_ids);
		if( title_names==null || title_ids.equals("") )
		{
			return "��";
		}
		
		return title_names;
	}
}
