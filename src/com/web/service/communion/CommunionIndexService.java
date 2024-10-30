package com.web.service.communion;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ben.dao.embargo.EmbargoDAO;
import com.ben.vo.communion.CommunionVO;
import com.ls.ben.cache.dynamic.manual.chat.ChatInfoCahe;
import com.ls.ben.cache.staticcache.forbid.ForBidCache;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.group.GroupService;
import com.pub.ben.info.Expression;

/**
 * 首页聊天
 * 
 * @author 侯浩军 11:13:44 AM
 */
public class CommunionIndexService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * 首页聊天
	 * @param roleInfo
	 * @param type
	 * @param title
	 * @param usercommunionpub                公聊的等级限制
	 * @return
	 */
	public String Communion(RoleEntity roleInfo, int type, String title,
			String usercommunionpub)
	{
		String hint = null;
		if (type == 1)// 公共聊天
		{
			hint = CommPub(roleInfo, type, title, usercommunionpub);
		}
		if (type == 2)// 阵营聊天
		{
			hint = CommCamp(roleInfo, type, title);
		}
		if (type == 3)// 队伍聊天
		{
			hint = CommDuiWu(roleInfo, type, title);
		}
		if (type == 4)// 帮派聊天
		{
			hint = CommTong(roleInfo, type, title);
		}
		if (type > 4)
		{// 公共聊天
			hint = CommPub(roleInfo, type, title, usercommunionpub);
		}
		if (type < 1)
		{// 公共聊天
			hint = CommPub(roleInfo, type, title, usercommunionpub);
		}
		return hint;
	}

	/**
	 * 公共聊天
	 */
	public String CommPub(RoleEntity roleInfo, int type, String title,
			String usercommunionpub)
	{
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		String hint = null;
		/*Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(title);
		boolean b = m.matches();
		if (b == false)
		{
			hint = "您的内容里有非法字符！请重新输入！";
			return hint;
		}*/
		int flag = Expression.hasPublish(title);
		if (flag == -1)
		{
			hint = "您的内容里有非法字符！请重新输入！";
			return hint;
		}
		if (title != null)
		{
			if (title.length() > 20)
			{
				hint = "您只能输入20个字符";
				return hint;
			}
		}
		if( roleInfo.getBasicInfo().getGrade() < Integer.parseInt(usercommunionpub))
		{
			hint = "对不起，只有" + usercommunionpub + "级以上的玩家才可以在公共频道发言！";
			return hint;
		}
		// 判断是否重复发言
		EmbargoDAO embargoDAO = new EmbargoDAO();
		String s = embargoDAO.isEmbargo(roleInfo.getBasicInfo().getPPk(), Time);
		if (s != null)
		{
			hint = "您被在公共聊天频道禁言" + s + "分钟！";
			return hint;
		}
		// 聊天间隔时间
		if (title != null)
		{
			hint = roleInfo.getStateInfo().isPublicChat();
			if( hint!=null )
			{
				return hint;
			}
		}
		// 检测是否还有禁止关键词
		if (Expression.hasForbidChar(title,ForBidCache.FORBIDCOMM))
		{
			hint = "对不起，您的发言中包含禁止字符!";
			return hint;
		}
		if(title.trim().equals("")){
			hint = "对不起，您的发言中包含禁止字符!";
			return hint;
		}
		if(title.indexOf("　") != -1){
			hint = "对不起，您的发言中包含禁止字符!";
			return hint;
		}
		if (title != null)
		{
			// 执行插入公共聊天记录
			CommunionVO communionVO = new CommunionVO();
			communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
			communionVO.setPName(roleInfo.getBasicInfo().getName());
			communionVO.setCTitle(title);
			communionVO.setCType(type);
			communionVO.setCreateTime(Time);
			ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
			publicChatInfoCahe.put(communionVO);
		}
		return hint;
	}

	/**
	 * 阵营聊天
	 */
	public String CommCamp(RoleEntity roleInfo, int type, String title)
	{
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		String hint = null;
		/*Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(title);
		boolean b = m.matches();
		if (b == false)
		{
			hint = "您的内容里有非法字符！请重新输入！";
			return hint;
		}*/
		int flag = Expression.hasPublish(title);
		if (flag == -1)
		{
			hint = "您的内容里有非法字符！请重新输入！";
			return hint;
		}
		if (title != null)
		{
			if (title.length() > 20)
			{
				hint = "您只能输入20个字符";
				return hint;
			}
		}
		// 如果角色没有加入阵营就不能聊天
		if (title != null)
		{
			if (roleInfo.getBasicInfo().getPRace() == 0)
			{
				hint = "对不起，您目前没有加入阵营，无法在阵营频道发言！";
				return hint;
			}
		}
		// 检测是否还有禁止关键词
		if (Expression.hasForbidChar(title,ForBidCache.FORBIDCOMM))
		{
			hint = "对不起，您的发言中包含禁止字符!";
			return hint;
		}
		if(title.trim().equals("")){
			hint = "对不起，您的发言中包含禁止字符!";
			return hint;
		}
		if(title.indexOf("　") != -1){
			hint = "对不起，您的发言中包含禁止字符!";
			return hint;
		}
		// 执行插入阵营聊天记录
		if (title != null)
		{
			// 执行插入公共聊天记录 c_pk,p_pk,p_name,c_zhen,c_title,c_type,create_time
			CommunionVO communionVO = new CommunionVO();
			communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
			communionVO.setPName(roleInfo.getBasicInfo().getName());
			communionVO.setCZhen(roleInfo.getBasicInfo().getPRace());
			communionVO.setCTitle(title);
			communionVO.setCType(type);
			communionVO.setCreateTime(Time);
			ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
			publicChatInfoCahe.put(communionVO);
		}
		return hint;
	}

	/**
	 * 队伍聊天
	 */
	public String CommDuiWu(RoleEntity roleInfo, int type, String title)
	{
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量

		GroupService groupService = new GroupService();
		String hint = null;
		/*Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(title);
		boolean b = m.matches();
		if (b == false)
		{
			hint = "您的内容里有非法字符！请重新输入！";
			return hint;
		}*/
		int flag = Expression.hasPublish(title);
		if (flag == -1)
		{
			hint = "您的内容里有非法字符！请重新输入！";
			return hint;
		}
		if (title != null)
		{
			if (title.length() > 20)
			{
				hint = "您只能输入20个字符";
				return hint;
			}
			if (groupService.getCaptionPk(roleInfo.getBasicInfo().getPPk()) < 0)
			{
				hint = "对不起，您目前没有加入队伍，无法在组队频道发言！";
				return hint;
			}
		}
		// 检测是否还有禁止关键词
		if (Expression.hasForbidChar(title,ForBidCache.FORBIDCOMM))
		{
			hint = "对不起，您的发言中包含禁止字符!";
			return hint;
		}
		if(title.trim().equals("")){
			hint = "对不起，您的发言中包含禁止字符!";
			return hint;
		}
		if(title.indexOf("　") != -1){
			hint = "对不起，您的发言中包含禁止字符!";
			return hint;
		}
		if (title != null)
		{
			// 执行插入公共聊天记录 c_pk,p_pk,p_name,c_dui,c_title,c_type,create_time
			CommunionVO communionVO = new CommunionVO();
			communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
			communionVO.setPName(roleInfo.getBasicInfo().getName());
			communionVO.setCDui(roleInfo.getStateInfo().getGroup_id());
			communionVO.setCTitle(title);
			communionVO.setCType(type);
			communionVO.setCreateTime(Time);
			ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
			publicChatInfoCahe.put(communionVO);
		}
		return hint;
	}

	/**
	 * 帮派聊天
	 */
	public String CommTong(RoleEntity roleInfo, int type, String title)
	{
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		String hint = null;

		/*Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(title);
		boolean b = m.matches();
		if (b == false)
		{  
			hint = "您的内容里有非法字符！请重新输入！";
			return hint;
		}*/
		int flag = Expression.hasPublish(title);
		if (flag == -1)
		{
			hint = "您的内容里有非法字符！请重新输入！";
			return hint;
		}
		if (title != null)
		{
			if (title.length() > 20)
			{
				hint = "您只能输入20个字符";
				return hint;
			}
			if (roleInfo.getBasicInfo().getFaction() == null)
			{
				hint = "对不起，您目前没有加入帮派，无法在帮会频道发言！";
				return hint;
			}
		}
		// 检测是否还有禁止关键词
		if (Expression.hasForbidChar(title,ForBidCache.FORBIDCOMM))
		{
			hint = "对不起，您的发言中包含禁止字符!";
			return hint;
		}
		if(title.trim().equals("")){
			hint = "对不起，您的发言中包含禁止字符!";
			return hint;
		}
		if(title.indexOf("　") != -1){
			hint = "对不起，您的发言中包含禁止字符!";
			return hint;
		}
		if (title != null)
		{
			// 执行插入公共聊天记录 c_pk,p_pk,p_name,c_bang,c_title,c_type,create_time
			CommunionVO communionVO = new CommunionVO();
			communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
			communionVO.setPName(roleInfo.getBasicInfo().getName());
			communionVO.setCBang(roleInfo.getBasicInfo().getFaction().getId());
			communionVO.setCTitle(title);
			communionVO.setCType(type);
			communionVO.setCreateTime(Time);
			ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
			publicChatInfoCahe.put(communionVO);
		}
		return hint;
	}
}
