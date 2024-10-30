package com.pm.service.forum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.PropertyService;
import com.pm.dao.forum.ForumClassDAOImpl;
import com.pm.dao.forum.ForumDAOImpl;
import com.pm.vo.forum.ForumBean;
import com.pm.vo.forum.ForumForbidVO;

public class ForumInfoService
{
	Logger logger = Logger.getLogger(ForumClassService.class);

	/**
	 * ��ֹ��������
	 * @param roleInfo ��̳����Ա
	 * @param pPk	��������pPk
	 * @return
	 */
	public String insertIntoForbid(RoleEntity roleInfo, String pPk,int forbid_time)
	{
		String hint = "";
		ForumClassDAOImpl forumClassDAOImpl = new ForumClassDAOImpl();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		ForumForbidVO forbidvo = forumClassDAOImpl.isForBidIng(pPk);
		
		
		
		if (forbidvo != null ){
			Date dt,dt1 = null;
			try
			{
				dt = simpleDateFormat.parse(forbidvo.getForbidEndTime());
				dt1 = new Date(dt.getTime() + forbidvo.getAddTime()*60000);
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
			
			if (dt1.getTime() > (new Date()).getTime()) {    			
    			hint = forbidvo.getPName()+"�Ѿ��ڱ���ֹ������������,��"+simpleDateFormat.format(dt1)+"���!";
			}
		} else {
			PropertyService propertyService = new PropertyService();
			String pName = propertyService.getPlayerName(Integer.parseInt(pPk));
			
			forumClassDAOImpl.deleteForumForbid(Integer.parseInt(pPk));
			forumClassDAOImpl.addForbidName(pPk,pName,1,forbid_time);
			hint = "�����ɹ�!"+pName+"��"+forbid_time+"���Ӻ���!";
		}
		
		return hint;		
	}

	/**
	 * ɾ������,�Լ��ӻ�������ɾ������
	 * @param page_id
	 */
	public void deleteForum(String page_id)
	{
		ForumDAOImpl forumDAOImpl = new ForumDAOImpl();
		try
		{
			forumDAOImpl.deleteForum(Integer.parseInt(page_id));
		}		
		catch (Exception e)
		{
			System.out.println("ɾ������ʱ����,page_id="+page_id);
			e.printStackTrace();
		}
	}

	/**
	 * �����ö�����
	 * @param page_id
	 */
	public String zhiDing(String page_id)
	{
		String resultWmlString = "";
		ForumService forumService = new ForumService();
		ForumDAOImpl forumDAOImpl = new ForumDAOImpl();
		try
		{
			ForumBean forumBean = forumService.getByID(Integer.parseInt(page_id));
			if (forumBean.getTaxis() == 0) {
				forumDAOImpl.zhiDing(Integer.parseInt(page_id), 1);
				resultWmlString = "�����Ѿ��ö�!";
			} else {
				forumDAOImpl.zhiDing(Integer.parseInt(page_id),0);
				resultWmlString = "����ȡ���ö�!";
			}
		} catch (Exception e)
		{
			System.out.println("�����ö�����ʱ����,page_id="+page_id);
			e.printStackTrace();
		}	
		return resultWmlString;
	}

	/**
	 * ������������
	 * @param page_id
	 */
	public String lockForum(String page_id)
	{
		String resultWmlString = "";
		ForumService forumService = new ForumService();
		ForumDAOImpl forumDAOImpl = new ForumDAOImpl();
		try
		{
			ForumBean forumBean = forumService.getByID(Integer.parseInt(page_id));
			if (forumBean.getVouch() == 0) {
				forumDAOImpl.lockForum(Integer.parseInt(page_id), 1);
				resultWmlString = "�����Ѿ�����!";
			} else {
				forumDAOImpl.lockForum(Integer.parseInt(page_id), 0);
				resultWmlString = "�����Ѿ�����!";
			}
		} catch (Exception e)
		{
			System.out.println("�����ö�����ʱ����,page_id="+page_id);
			e.printStackTrace();
		}
		
		return resultWmlString;
	}
	

}
