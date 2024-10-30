package com.pm.service.systemInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ls.ben.dao.map.SceneDao;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.property.RoleSystemInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.UMsgService;
import com.pm.dao.systemInfo.SysInfoDao;
import com.pm.service.mail.MailInfoService;
import com.pm.vo.sysInfo.SystemControlInfoVO;
import com.pm.vo.sysInfo.SystemInfoVO;

public class SystemInfoService
{

	Logger logger = Logger.getLogger("log.service");

	/**
	 * 向系统消息表插入系统消息，此消息会被所有玩家看见
	 * 
	 * @param info
	 *            信息内容
	 * @return if sussend return 1,else return -1
	 */
	public int insertSystemInfoBySystem(String info)
	{
		if (info == null || info.equals(""))
		{
			return -1;
		}
		SysInfoDao sysInfoDao = new SysInfoDao();
		// 删除超过十五分钟的系统消息
		// sysInfoDao.deleteMoreFifteenMinutes();
		return sysInfoDao.insertSysInfo(0, 2, info);
	}

	/**
	 * 向系统消息表插入系统消息，此消息会被所有玩家看见,附带延时时间
	 * 
	 * @param info
	 *            信息内容
	 * @param 会在以当前时间计算的minute分钟后显示
	 * @return if sussend return 1,else return -1
	 */
	public int insertSystemInfoBySystem(String info, int minute)
	{
		if (info == null || info.equals(""))
		{
			return -1;
		}
		SysInfoDao sysInfoDao = new SysInfoDao();

		return sysInfoDao.insertSysInfo(0, 2, info, minute * 60);
	}

	/**
	 * 向系统消息表插入系统消息，此消息会被所有玩家看见,在规定时间的时间被玩家看见
	 * 
	 * @param info
	 *            信息内容
	 * @param time
	 *            时间,格式为 12:26:15
	 * @return if sussend return 1,else return -1
	 */
	public void insertSystemInfoBySystem(String info, String time)
	{

		SysInfoDao sysInfoDao = new SysInfoDao();
		String sendTime = getToday() + " " + time;

		sysInfoDao.insertSysInfo(0, 2, info, sendTime);
	}

	/**
	 * 向系统消息表插入系统消息，此消息会被所有玩家看见,在规定时间的时间被玩家看见
	 * 
	 * @param info
	 *            信息内容
	 * @param time
	 *            时间,格式为 12:26:15
	 * @param info_type
	 *            信息类型
	 * @return if sussend return 1,else return -1
	 */
	public void insertSystemInfoBySystem(String info, String time, int info_type)
	{

		SysInfoDao sysInfoDao = new SysInfoDao();
		String sendTime = getToday() + " " + time;

		sysInfoDao.insertSysInfo(0, info_type, info, sendTime);
	}

	/**
	 * 向系统消息表插入对个人发的系统消息，此消息仅会被此人看见
	 * 
	 * @param p_pk
	 *            个人角色id
	 * @param info
	 *            信息内容
	 * @return if sussend return 1,else return -1.
	 */
	public int insertSystemInfoBySystem(int p_pk, String info)
	{
		if (info == null || info.equals(""))
		{
			return -1;
		}
		SysInfoDao sysInfoDao = new SysInfoDao();
		return sysInfoDao.insertSysInfo(p_pk, 1, info);
	}

	/**
	 * 向系统消息表插入对个人发的系统消息，此消息仅会被此人看见,附带延迟时间
	 * 
	 * @param second
	 *            延迟second秒后发送
	 * @param p_pk
	 *            个人角色id
	 * @param info
	 *            信息内容
	 * @return if sussend return 1,else return -1.
	 */
	public int insertSystemInfoBySystem(int p_pk, String info, int second)
	{
		if (info == null || info.equals(""))
		{
			return -1;
		}
		SysInfoDao sysInfoDao = new SysInfoDao();
		return sysInfoDao.insertSysInfo(p_pk, 1, info, second);

	}

	/**
	 * 向系统消息表插入对个人发的系统消息，此消息仅会被此人看见,附带延迟时间
	 * 
	 * @param second
	 *            延迟second秒后发送
	 * @param p_pk
	 *            个人角色id
	 * @param info
	 *            信息内容
	 * @return if sussend return 1,else return -1.
	 */
	public int insertSystemInfoBySystem(int p_pk, int info_type, String info,
			int second)
	{
		if (info == null || info.equals(""))
		{
			return -1;
		}
		SysInfoDao sysInfoDao = new SysInfoDao();
		return sysInfoDao.insertSysInfo(p_pk, info_type, info, second);
	}

	/**
	 * 向系统消息表插入特别通知(供后台使用)，此消息会被所有人看见
	 * 
	 * @param info
	 *            信息内容
	 * @return if sussend return 1,else return -1
	 */
	public int insertSystemInfoByBack(String info)
	{
		if (info == null || info.equals(""))
		{
			return -1;
		}
		SysInfoDao sysInfoDao = new SysInfoDao();
		return sysInfoDao.insertSysInfo(0, 3, info);
	}

	/**
	 * 向系统消息表插入特别通知(供帮会结束使用)，此消息会被所有人看见
	 * 
	 * @param info
	 *            信息内容 time 结束时间
	 * @return if sussend return 1,else return -1
	 */
	public int insertSystemInfoByBackTong(String info, String time)
	{
		if (info == null || info.equals(""))
		{
			return -1;
		}
		SysInfoDao sysInfoDao = new SysInfoDao();
		return sysInfoDao.insertSysInfoTongTime(0, 10, info, time);
	}

	/**
	 * 向系统消息表插入特别通知(供帮会结束使用)，此消息会被所有人看见
	 * 
	 * @param info
	 *            信息内容 time 结束时间
	 * @return if sussend return 1,else return -1
	 */
	public int insertSystemInfoByBackTong(String info, String time,
			int info_type)
	{
		if (info == null || info.equals(""))
		{
			return -1;
		}
		SysInfoDao sysInfoDao = new SysInfoDao();
		return sysInfoDao.insertSysInfoTongTime(0, info_type, info, time);
	}

	/**
	 * 向系统消息表插入小喇叭(供小喇叭使用)，此消息会被所有人看见
	 * 
	 * @param info
	 *            信息内容 time 结束时间
	 * @return if sussend return 1,else return -1
	 */
	public void insertSystemInfoBySpeaker(String info, int p_pk)
	{
		SysInfoDao sysInfoDao = new SysInfoDao();

		sysInfoDao.insertSysInfo(p_pk, 4, info, 0);
		sysInfoDao.insertSysInfo(p_pk, 4, info, 30);
		sysInfoDao.insertSysInfo(p_pk, 4, info, 60);

	}

	/**
	 * 按次序获得系统消息，首先会判断上一条系统消息是否已经超过十秒，如果未超过则
	 * 仍然显示这一条，如果超过则显示下一条，这下一条按照以下次序显示:首先检索系统消息表中
	 * 10秒内的个人消息，如果有的话则先显示，要是要两条以上就显示时间较远的一条。如果个人
	 * 消息没有，则开始检索系统信息表中系统消息，同样取在10秒内时间较远的一条，如果都没有检索到， 则不显示.
	 * 
	 * @param pPk
	 *            个人角色id
	 * @param request
	 *            HttpServletRequest
	 * @return SystemInfoVO
	 */
	public List<SystemInfoVO> getSystemInfoByPPk(RoleEntity roleInfo)
	{

		RoleSystemInfo roleSystemInfo = roleInfo.getRoleSystemInfo();
		return roleSystemInfo.getInfoList(roleInfo);
	}

	/**
	 * 在聊天拦看系统消息时间定义20分钟
	 */
	public List<SystemInfoVO> getSystemInfoByPPkTime(String pPk,
			HttpServletRequest request)
	{
		List<SystemInfoVO> list = new ArrayList<SystemInfoVO>();

		// StringBuffer sb = new StringBuffer();
		SysInfoDao infoDao = new SysInfoDao();
		Date atime = (Date) request.getSession().getAttribute("sysInfoTime");
		Date now = new Date();
		// SystemInfoVO sysInfo = null;
		// 获得系统消息
		if (atime == null || atime.equals(""))
		{ // date is null or no point.
			atime = now;
		}

		if (atime.getTime() - now.getTime() > 10 * 1000)
		{
			list = (List<SystemInfoVO>) request.getSession().getAttribute(
					"list");
			// int sysInfoId =
			// (Integer)request.getSession().getAttribute("sysInfoId");
			// sysInfo = infoDao.getSystemInfo(sysInfoId);
		}
		else
		{
			/*
			 * sysInfo = infoDao.getSystemSelfInfo(pPk); if(sysInfo == null){
			 * sysInfo = infoDao.getSystemInfo(); } if(sysInfo != null){
			 * request.getSession().setAttribute("sysInfoTime",atime);
			 * //把开始显示时间保存
			 * request.getSession().setAttribute("sysInfoId",sysInfo.getSysInfoId());
			 * //记录下这个消息的id }
			 */
			list = infoDao.getSystemInfoThreeTime(pPk);
			if (list != null && list.size() != 0)
			{
				request.getSession().setAttribute("sysInfoTime", atime); // 把开始显示时间保存
				request.getSession().setAttribute("systemInfoList", list);
			}
		}

		return list;
	}

	/**
	 * 根据系统消息后台控制表中的等级字段的信息,如果p_grade的等级符合 控制表中的条件,就给他发送一条系统消息
	 * 
	 * @param pPk
	 * @param p_grade
	 * @return
	 */
	public void sendSystemInfoByPGrade(int pPk, int p_grade)
	{
		logger.info("p_grade=" + p_grade);
		SysInfoDao infoDao = new SysInfoDao();
		MailInfoService mailInfoService = new MailInfoService();
		List<SystemControlInfoVO> list = infoDao.getSystemInfoControlByPGrade();
		if (list.size() != 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				SystemControlInfoVO vo = list.get(i);
				String[] grade = vo.getPlayerGrade().split(",");
				// 检查是否有在这个等级需要发的系统消息
				if (p_grade >= Integer.valueOf(grade[0])
						&& p_grade <= Integer.valueOf(grade[1]))
					if (vo.getSendType() == 1)
					{
						insertSystemInfoBySystem(pPk, vo.getSendContent());
					}
					else
						if (vo.getSendType() == 2)
						{
							mailInfoService.sendMail(pPk, -1, 2, "系统等级提示", vo
									.getSendContent());
						}
						else
							if (vo.getSendType() == 3)
							{
								mailInfoService.sendMail(pPk, -1, 2, "系统等级提示",
										vo.getSendContent());
								insertSystemInfoBySystem(pPk, vo
										.getSendContent());
							}
							else
								if (vo.getSendType() == 4)
								{
									// 为4发弹出式消息
									insertPopMsggse(pPk, vo.getSendContent());

								}
			}
		}
		sendCollageSystemInfo(pPk, 0, "0", p_grade, 0);
	}

	/**
	 * 玩家交易成功与否的系统提示消息
	 */
	public void sendSystemInfoByTransaction(int ppk,String content)
	{
		insertSystemInfoBySystem(ppk,content);
		
	}
	
	/**
	 * 根据系统消息后台控制表中的等级字段的信息,如果task_id的等级符合 控制表中的条件,就给他发送一条系统消息
	 * 
	 * @param pPk
	 * @param p_grade
	 * @return
	 */
	public void sendSystemInfoByTaskId(int pPk, Integer task_id)
	{
		SysInfoDao infoDao = new SysInfoDao();
		// Map<Integer,String> map = infoDao.getSystemInfoControlByTaskId();
		List<SystemControlInfoVO> list = infoDao
				.getSystemInfoControlListByTaskId();
		MailInfoService mailInfoService = new MailInfoService();

		if (list.size() != 0)
		{
			for (SystemControlInfoVO vo : list)
			{
				if (vo.getTaskId() == task_id)
				{
					if (vo.getSendType() == 1)
					{
						insertSystemInfoBySystem(pPk, vo.getSendContent());
					}
					else
						if (vo.getSendType() == 2)
						{
							mailInfoService.sendMail(pPk, -1, 2, "系统任务提示", vo
									.getSendContent());
						}
						else
							if (vo.getSendType() == 3)
							{
								mailInfoService.sendMail(pPk, -1, 2, "系统任务提示",
										vo.getSendContent());
								insertSystemInfoBySystem(pPk, vo
										.getSendContent());
							}
							else
								if (vo.getSendType() == 4)
								{
									// 为4发弹出式消息
									insertPopMsggse(pPk, vo.getSendContent());
								}
				}
			}
		}
		// if(map.size() != 0){
		// Set<Integer> mapset = map.keySet();
		// //logger.info("个人id为="+pPk+" ,task_id="+task_id);
		// //logger.info("有这个任务的系统消息="+mapset.contains(task_id));
		// if(mapset.contains(task_id)){ //检查是否有在这个等级需要发的系统消息
		// logger.info("有这个任务的系统消息！");
		// insertSystemInfoBySystem(pPk,map.get(task_id));
		// }
		// }
		// 因为会造成一条综合信息不停刷的情况,所有暂时关闭这条。
		// sendCollageSystemInfo(pPk,task_id,"0",0,0);
	}

	/**
	 * 根据系统消息后台控制表中的等级字段的信息,如果title的等级符合 控制表中的条件,就给他发送一条系统消息
	 * 
	 * @param pPk
	 * @param p_grade
	 * @return
	 */
	public void sendSystemInfoByTitleInfo(int pPk, String title_id)
	{
		SysInfoDao infoDao = new SysInfoDao();
		List<SystemControlInfoVO> list = infoDao
				.getSystemInfoControlListByTitle(title_id);
		MailInfoService mailInfoService = new MailInfoService();
		if (list.size() != 0)
		{
			for (SystemControlInfoVO vo : list)
			{
				if (vo.getSendType() == 1)
				{
					insertSystemInfoBySystem(pPk, vo.getSendContent());
				}
				else
					if (vo.getSendType() == 2)
					{
						mailInfoService.sendMail(pPk, -1, 2, "系统称谓提示", vo
								.getSendContent());
					}
					else
						if (vo.getSendType() == 3)
						{
							mailInfoService.sendMail(pPk, -1, 2, "系统称谓提示", vo
									.getSendContent());
							insertSystemInfoBySystem(pPk, vo.getSendContent());
						}
						else
							if (vo.getSendType() == 4)
							{
								// 为4发弹出式消息
								insertPopMsggse(pPk, vo.getSendContent());
							}
			}
		}
		// Map<String,String> map =
		// infoDao.getSystemInfoControlByTitle(title_id);
		// 如果称谓条件相同,就每隔10秒向他发送一个消息
		// for(int i=0;i < map.size();i++) {
		// insertSystemInfoBySystem(pPk,map.get(i),i*10);
		// }
		sendCollageSystemInfo(pPk, 0, title_id, 0, 0);
	}

	/**
	 * 根据系统消息后台控制表中的综合发送消息的条件,如果条件都符合 控制表中的条件,就给他发送一条系统消息 综合系统的条件有四个,
	 * 等级符合才行,等级先由外部传入,如果传入值为零,从partvo中取出, 声望符合才行,声望先由外部传入,如果传入值为零,从partvo中取出.
	 * 任务id符合,任务由外部传入。 称谓符合,称谓任务先由外部传入,如果传入值为零,从partvo中取出.
	 * 
	 * @param pPk
	 * @param task_id
	 *            任务id
	 * @param title
	 *            称谓
	 */
	private void sendCollageSystemInfo(int p_pk, int task_id, String title,
			int p_grade, int popularity)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		
		if (p_grade == 0)
		{
			p_grade = role_info.getBasicInfo().getGrade();
		}
		if (popularity == 0)
		{
			// 声望暂时不处理
			// TODO-根据声望发送系统消息暂时不处理
		}
		// int num = 0; //当发送多个时的定时器
		// 获得综合类信息的发送条件
		SysInfoDao sysInfoDao = new SysInfoDao();
		List<SystemControlInfoVO> list = sysInfoDao.getSystemInfoControlByPCollage();
		// logger.info("综合类型的条数="+list.size());
		if (list.size() != 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				SystemControlInfoVO systemInfovo = list.get(i);
				String sqlPGrade = systemInfovo.getPlayerGrade();
				// 如果等级相符合
				// logger.info("sqlPGrade="+sqlPGrade+" ,p_grade="+p_grade);
				if (p_grade >= Integer.valueOf(sqlPGrade.split(",")[0])
						&& p_grade <= Integer.valueOf(sqlPGrade.split(",")[1]))
				{
					// 如果条件称谓要求不为0且称谓符合
					// logger.info("getTitle="+systemInfovo.getTitle()+"
					// ,title="+title);
					if (systemInfovo.getTitle().equals("0")|| role_info.getTitleSet().isHaveByTitleStr(systemInfovo.getTitle())==true)
					{
						// 如果任务id符合
						// logger.info("getTaskId="+systemInfovo.getTaskId()+"
						// ,task_id="+task_id);
						if (systemInfovo.getTaskId() == 0
								|| task_id == systemInfovo.getTaskId())
						{
							// 如果声望符合，暂时不做
							// if（）{}
							// logger.info("终于发送了");
							MailInfoService mailInfoService = new MailInfoService();
							if (systemInfovo.getSendType() == 1)
							{
								insertSystemInfoBySystem(p_pk, systemInfovo
										.getSendContent());
							}
							else
								if (systemInfovo.getSendType() == 2)
								{
									mailInfoService.sendMail(p_pk, -1, 2,
											"系统提示", systemInfovo
													.getSendContent());
								}
								else
									if (systemInfovo.getSendType() == 3)
									{
										mailInfoService.sendMail(p_pk, -1, 2,
												"系统提示", systemInfovo
														.getSendContent());
										insertSystemInfoBySystem(p_pk,
												systemInfovo.getSendContent());
									}
									else
										if (systemInfovo.getSendType() == 4)
										{
											// 为4发弹出式消息
											insertPopMsggse(p_pk, systemInfovo
													.getSendContent());
										}
						}
					}
				}
			}
		}
	}

	public void sendCollageSystemInfoByNewPlayer(int p_pk, int task_id,
			String title, int p_grade, int popularity)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		
		if (p_grade == 0)
		{
			p_grade = role_info.getBasicInfo().getGrade();
		}
		if (popularity == 0)
		{
			// 声望暂时不处理
			// TODO-根据声望发送系统消息暂时不处理
		}
		// int num = 0; //当发送多个时的定时器
		// 获得综合类信息的发送条件
		SysInfoDao sysInfoDao = new SysInfoDao();
		List<SystemControlInfoVO> list = sysInfoDao
				.getSystemInfoControlByNewPlayer();
		// logger.info("综合类型的条数="+list.size());
		if (list.size() != 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				SystemControlInfoVO systemInfovo = list.get(i);
				String sqlPGrade = systemInfovo.getPlayerGrade();
				// 如果等级相符合
				// logger.info("sqlPGrade="+sqlPGrade+" ,p_grade="+p_grade);
				if (p_grade >= Integer.valueOf(sqlPGrade.split(",")[0])
						&& p_grade <= Integer.valueOf(sqlPGrade.split(",")[1]))
				{
					// 如果条件称谓要求不为0且称谓符合
					// logger.info("getTitle="+systemInfovo.getTitle()+"
					// ,title="+title);
					
					if (systemInfovo.getTitle().equals("0") || role_info.getTitleSet().isHaveByTitleStr(systemInfovo.getTitle())==true)
					{
						// 如果任务id符合
						// logger.info("getTaskId="+systemInfovo.getTaskId()+"
						// ,task_id="+task_id);
						if (systemInfovo.getTaskId() == 0
								|| task_id == systemInfovo.getTaskId())
						{
							// 为4发弹出式消息
							insertPopMsggse(p_pk, systemInfovo.getSendContent());
						}
					}
				}
			}
		}
	}

	/**
	 * 发送弹出式消息
	 * 
	 * @param pPk
	 * @param sendContent
	 */
	public void insertPopMsggse(int pPk, String sendContent)
	{
		UMsgService uMsgService = new UMsgService();
		UMessageInfoVO messageInfovo = new UMessageInfoVO();
		messageInfovo.setPPk(pPk);
		messageInfovo.setMsgType(PopUpMsgType.XITONG);
		messageInfovo.setMsgPriority(PopUpMsgType.XITONG_FIRST);
		messageInfovo.setMsgOperate1(sendContent);
		uMsgService.sendPopUpMsg(messageInfovo);
	}

	/**
	 * 发送定时系统消息
	 */
	public void sendTimeSystemInfo()
	{
		SysInfoDao sysInfoDao = new SysInfoDao();
		List<SystemControlInfoVO> list = sysInfoDao.getSystemInfoCOntrolByTime();
		if (list.size() != 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				SystemControlInfoVO infovo = list.get(i);
				String time = infovo.getSendTime();
				insertSystemInfoBySystem(infovo.getSendContent(), time);
			}
		}
	}

	/**
	 * 获得每天时间的格式化表示
	 * 
	 * @return
	 */
	private static String getToday()
	{
		Date dt = new Date();
		// DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// Date yestoday = new Date(dt.getTime() - 1000*60*60*24);
		String dtstr = sf.format(dt);
		return dtstr;
	}

	/**
	 * 给在此barea下的所有人发系统消息
	 * 
	 * @param string
	 * @param barea
	 */
	public void insertSystemInfoByBarea(String systemInfo, int barea)
	{
		SceneDao scenedao = new SceneDao();
		String sceneStr = scenedao.getSceneIdByBarea(barea);

		SysInfoDao sysInfoDao = new SysInfoDao();
		sysInfoDao.insertSysInfo(systemInfo, sceneStr);
	}

	/**
	 * 根据pPk和系统消息的一些关键词来删除不需要的系统消息
	 * 
	 * @param pPk
	 * @param propName
	 */
	public void deleteByPPkInfo(int pPk, String propName)
	{
		SysInfoDao sysInfoDao = new SysInfoDao();
		sysInfoDao.deleteByPPkInfo(pPk, propName);

	}

	/**
	 * 更新一些系统消息的发布时间
	 * 
	 * @param effectObject
	 * @param propName
	 */
	public void updateByPPkInfo(int pPk, String propName, int buffTime)
	{
		SysInfoDao sysInfoDao = new SysInfoDao();
		sysInfoDao.updateTimeReducePkValue(pPk, buffTime, propName);

	}

	/**
	 * 查询是否有 特定的 系统消息
	 * 
	 * @param effectObject
	 * @param propName
	 */
	public boolean selectByPPkInfo(int pPk, String propName)
	{
		SysInfoDao sysInfoDao = new SysInfoDao();
		return sysInfoDao.selectByPPkInfo(pPk, propName);

	}

	/**
	 * 发送帮派胜利的系统
	 * 
	 * @param info
	 */
	public void tongSiegeSendInfo(String info)
	{
		// 要求发送十次
		for (int i = 0; i < 10; i++)
		{
			this.insertSystemInfoBySystem(info, i);
		}

	}

	// 给玩家的新手引导 类型10为新手的等级条件的引导 8为任务的引导
	public void setNewPlayerGuideInfoMSG(RoleEntity roleinfo, String type,
			String id)
	{
		// 如果玩家是新手 跳出弹出式消息
		if (roleinfo.getBasicInfo().getPlayer_state_by_new() == 11
				|| roleinfo.getBasicInfo().getPlayer_state_by_new() == 1)
		{
			if (type != null && id != null)
			{
				SysInfoDao sysInfoDao = new SysInfoDao();
				List<SystemControlInfoVO> list = sysInfoDao
						.getSystemInfoByNewPlayerGuide(type, id);
				if (list.size() != 0)
				{
					for (int i = 0; i < list.size(); i++)
					{
						SystemControlInfoVO systemInfovo = list.get(i);
						intsertNewPlayerGuideInfo(roleinfo.getBasicInfo()
								.getPPk(), systemInfovo.getSendContent(),
								systemInfovo.getPopularity());
					}

				}
			}
		}
		// 如果玩家是游客跳出让玩家注册的消息
		if (roleinfo.getBasicInfo().getPlayer_state_by_new() == 11
				|| roleinfo.getBasicInfo().getPlayer_state_by_new() == 10)
		{
			// 玩家大于9级了 我们给玩家踢出游戏
			if (roleinfo.getBasicInfo().getGrade() > 9)
			{
				if (type != null && id != null)
				{
					SysInfoDao sysInfoDao = new SysInfoDao();
					List<SystemControlInfoVO> list = sysInfoDao
							.getSystemInfoByNewPlayerGuide(type, id);
					if (list.size() != 0)
					{
						for (int i = 0; i < list.size(); i++)
						{
							SystemControlInfoVO systemInfovo = list.get(i);
							intsertNewPlayerGuideInfo(roleinfo.getBasicInfo()
									.getPPk(), systemInfovo.getSendContent(),
									systemInfovo.getPopularity());
						}

					}
				}
			}
			else
			{// 玩家不到9级了 我们给玩家提示 注册信息
				if (type != null && id != null)
				{
					SysInfoDao sysInfoDao = new SysInfoDao();
					List<SystemControlInfoVO> list = sysInfoDao
							.getSystemInfoByNewPlayerGuide(type, id);
					if (list.size() != 0)
					{
						for (int i = 0; i < list.size(); i++)
						{
							SystemControlInfoVO systemInfovo = list.get(i);
							intsertNewPlayerGuideInfo(roleinfo.getBasicInfo()
									.getPPk(), systemInfovo.getSendContent(),
									systemInfovo.getPopularity());
						}
					}
				}
			}
		}
	}

	// 新手的弹出式消息
	private void intsertNewPlayerGuideInfo(int pPk, String sendContent, int id)
	{
		UMsgService uMsgService = new UMsgService();
		UMessageInfoVO messageInfovo = new UMessageInfoVO();
		messageInfovo.setPPk(pPk);
		messageInfovo.setMsgType(PopUpMsgType.NEWPLAYERGUIDEINFOMSG);
		messageInfovo.setMsgPriority(id);
		messageInfovo.setMsgOperate1(sendContent);
		uMsgService.sendPopUpMsg(messageInfovo);
	}
}
