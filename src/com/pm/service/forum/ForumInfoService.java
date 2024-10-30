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
	 * 禁止发帖功能
	 * @param roleInfo 论坛管理员
	 * @param pPk	被封的玩家pPk
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
    			hint = forbidvo.getPName()+"已经在被禁止发言名单中了,到"+simpleDateFormat.format(dt1)+"解封!";
			}
		} else {
			PropertyService propertyService = new PropertyService();
			String pName = propertyService.getPlayerName(Integer.parseInt(pPk));
			
			forumClassDAOImpl.deleteForumForbid(Integer.parseInt(pPk));
			forumClassDAOImpl.addForbidName(pPk,pName,1,forbid_time);
			hint = "操作成功!"+pName+"在"+forbid_time+"分钟后解封!";
		}
		
		return hint;		
	}

	/**
	 * 删除帖子,以及从回帖表中删除回帖
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
			System.out.println("删除帖子时出错,page_id="+page_id);
			e.printStackTrace();
		}
	}

	/**
	 * 处理置顶事宜
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
				resultWmlString = "帖子已经置顶!";
			} else {
				forumDAOImpl.zhiDing(Integer.parseInt(page_id),0);
				resultWmlString = "帖子取消置顶!";
			}
		} catch (Exception e)
		{
			System.out.println("处理置顶事宜时出错,page_id="+page_id);
			e.printStackTrace();
		}	
		return resultWmlString;
	}

	/**
	 * 处理锁帖事宜
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
				resultWmlString = "帖子已经锁帖!";
			} else {
				forumDAOImpl.lockForum(Integer.parseInt(page_id), 0);
				resultWmlString = "帖子已经解锁!";
			}
		} catch (Exception e)
		{
			System.out.println("处理置顶事宜时出错,page_id="+page_id);
			e.printStackTrace();
		}
		
		return resultWmlString;
	}
	

}
