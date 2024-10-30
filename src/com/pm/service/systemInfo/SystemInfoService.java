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
	 * ��ϵͳ��Ϣ�����ϵͳ��Ϣ������Ϣ�ᱻ������ҿ���
	 * 
	 * @param info
	 *            ��Ϣ����
	 * @return if sussend return 1,else return -1
	 */
	public int insertSystemInfoBySystem(String info)
	{
		if (info == null || info.equals(""))
		{
			return -1;
		}
		SysInfoDao sysInfoDao = new SysInfoDao();
		// ɾ������ʮ����ӵ�ϵͳ��Ϣ
		// sysInfoDao.deleteMoreFifteenMinutes();
		return sysInfoDao.insertSysInfo(0, 2, info);
	}

	/**
	 * ��ϵͳ��Ϣ�����ϵͳ��Ϣ������Ϣ�ᱻ������ҿ���,������ʱʱ��
	 * 
	 * @param info
	 *            ��Ϣ����
	 * @param �����Ե�ǰʱ������minute���Ӻ���ʾ
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
	 * ��ϵͳ��Ϣ�����ϵͳ��Ϣ������Ϣ�ᱻ������ҿ���,�ڹ涨ʱ���ʱ�䱻��ҿ���
	 * 
	 * @param info
	 *            ��Ϣ����
	 * @param time
	 *            ʱ��,��ʽΪ 12:26:15
	 * @return if sussend return 1,else return -1
	 */
	public void insertSystemInfoBySystem(String info, String time)
	{

		SysInfoDao sysInfoDao = new SysInfoDao();
		String sendTime = getToday() + " " + time;

		sysInfoDao.insertSysInfo(0, 2, info, sendTime);
	}

	/**
	 * ��ϵͳ��Ϣ�����ϵͳ��Ϣ������Ϣ�ᱻ������ҿ���,�ڹ涨ʱ���ʱ�䱻��ҿ���
	 * 
	 * @param info
	 *            ��Ϣ����
	 * @param time
	 *            ʱ��,��ʽΪ 12:26:15
	 * @param info_type
	 *            ��Ϣ����
	 * @return if sussend return 1,else return -1
	 */
	public void insertSystemInfoBySystem(String info, String time, int info_type)
	{

		SysInfoDao sysInfoDao = new SysInfoDao();
		String sendTime = getToday() + " " + time;

		sysInfoDao.insertSysInfo(0, info_type, info, sendTime);
	}

	/**
	 * ��ϵͳ��Ϣ�����Ը��˷���ϵͳ��Ϣ������Ϣ���ᱻ���˿���
	 * 
	 * @param p_pk
	 *            ���˽�ɫid
	 * @param info
	 *            ��Ϣ����
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
	 * ��ϵͳ��Ϣ�����Ը��˷���ϵͳ��Ϣ������Ϣ���ᱻ���˿���,�����ӳ�ʱ��
	 * 
	 * @param second
	 *            �ӳ�second�����
	 * @param p_pk
	 *            ���˽�ɫid
	 * @param info
	 *            ��Ϣ����
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
	 * ��ϵͳ��Ϣ�����Ը��˷���ϵͳ��Ϣ������Ϣ���ᱻ���˿���,�����ӳ�ʱ��
	 * 
	 * @param second
	 *            �ӳ�second�����
	 * @param p_pk
	 *            ���˽�ɫid
	 * @param info
	 *            ��Ϣ����
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
	 * ��ϵͳ��Ϣ������ر�֪ͨ(����̨ʹ��)������Ϣ�ᱻ�����˿���
	 * 
	 * @param info
	 *            ��Ϣ����
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
	 * ��ϵͳ��Ϣ������ر�֪ͨ(��������ʹ��)������Ϣ�ᱻ�����˿���
	 * 
	 * @param info
	 *            ��Ϣ���� time ����ʱ��
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
	 * ��ϵͳ��Ϣ������ر�֪ͨ(��������ʹ��)������Ϣ�ᱻ�����˿���
	 * 
	 * @param info
	 *            ��Ϣ���� time ����ʱ��
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
	 * ��ϵͳ��Ϣ�����С����(��С����ʹ��)������Ϣ�ᱻ�����˿���
	 * 
	 * @param info
	 *            ��Ϣ���� time ����ʱ��
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
	 * ��������ϵͳ��Ϣ�����Ȼ��ж���һ��ϵͳ��Ϣ�Ƿ��Ѿ�����ʮ�룬���δ������
	 * ��Ȼ��ʾ��һ���������������ʾ��һ��������һ���������´�����ʾ:���ȼ���ϵͳ��Ϣ����
	 * 10���ڵĸ�����Ϣ������еĻ�������ʾ��Ҫ��Ҫ�������Ͼ���ʾʱ���Զ��һ�����������
	 * ��Ϣû�У���ʼ����ϵͳ��Ϣ����ϵͳ��Ϣ��ͬ��ȡ��10����ʱ���Զ��һ���������û�м������� ����ʾ.
	 * 
	 * @param pPk
	 *            ���˽�ɫid
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
	 * ����������ϵͳ��Ϣʱ�䶨��20����
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
		// ���ϵͳ��Ϣ
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
			 * //�ѿ�ʼ��ʾʱ�䱣��
			 * request.getSession().setAttribute("sysInfoId",sysInfo.getSysInfoId());
			 * //��¼�������Ϣ��id }
			 */
			list = infoDao.getSystemInfoThreeTime(pPk);
			if (list != null && list.size() != 0)
			{
				request.getSession().setAttribute("sysInfoTime", atime); // �ѿ�ʼ��ʾʱ�䱣��
				request.getSession().setAttribute("systemInfoList", list);
			}
		}

		return list;
	}

	/**
	 * ����ϵͳ��Ϣ��̨���Ʊ��еĵȼ��ֶε���Ϣ,���p_grade�ĵȼ����� ���Ʊ��е�����,�͸�������һ��ϵͳ��Ϣ
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
				// ����Ƿ���������ȼ���Ҫ����ϵͳ��Ϣ
				if (p_grade >= Integer.valueOf(grade[0])
						&& p_grade <= Integer.valueOf(grade[1]))
					if (vo.getSendType() == 1)
					{
						insertSystemInfoBySystem(pPk, vo.getSendContent());
					}
					else
						if (vo.getSendType() == 2)
						{
							mailInfoService.sendMail(pPk, -1, 2, "ϵͳ�ȼ���ʾ", vo
									.getSendContent());
						}
						else
							if (vo.getSendType() == 3)
							{
								mailInfoService.sendMail(pPk, -1, 2, "ϵͳ�ȼ���ʾ",
										vo.getSendContent());
								insertSystemInfoBySystem(pPk, vo
										.getSendContent());
							}
							else
								if (vo.getSendType() == 4)
								{
									// Ϊ4������ʽ��Ϣ
									insertPopMsggse(pPk, vo.getSendContent());

								}
			}
		}
		sendCollageSystemInfo(pPk, 0, "0", p_grade, 0);
	}

	/**
	 * ��ҽ��׳ɹ�����ϵͳ��ʾ��Ϣ
	 */
	public void sendSystemInfoByTransaction(int ppk,String content)
	{
		insertSystemInfoBySystem(ppk,content);
		
	}
	
	/**
	 * ����ϵͳ��Ϣ��̨���Ʊ��еĵȼ��ֶε���Ϣ,���task_id�ĵȼ����� ���Ʊ��е�����,�͸�������һ��ϵͳ��Ϣ
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
							mailInfoService.sendMail(pPk, -1, 2, "ϵͳ������ʾ", vo
									.getSendContent());
						}
						else
							if (vo.getSendType() == 3)
							{
								mailInfoService.sendMail(pPk, -1, 2, "ϵͳ������ʾ",
										vo.getSendContent());
								insertSystemInfoBySystem(pPk, vo
										.getSendContent());
							}
							else
								if (vo.getSendType() == 4)
								{
									// Ϊ4������ʽ��Ϣ
									insertPopMsggse(pPk, vo.getSendContent());
								}
				}
			}
		}
		// if(map.size() != 0){
		// Set<Integer> mapset = map.keySet();
		// //logger.info("����idΪ="+pPk+" ,task_id="+task_id);
		// //logger.info("����������ϵͳ��Ϣ="+mapset.contains(task_id));
		// if(mapset.contains(task_id)){ //����Ƿ���������ȼ���Ҫ����ϵͳ��Ϣ
		// logger.info("����������ϵͳ��Ϣ��");
		// insertSystemInfoBySystem(pPk,map.get(task_id));
		// }
		// }
		// ��Ϊ�����һ���ۺ���Ϣ��ͣˢ�����,������ʱ�ر�������
		// sendCollageSystemInfo(pPk,task_id,"0",0,0);
	}

	/**
	 * ����ϵͳ��Ϣ��̨���Ʊ��еĵȼ��ֶε���Ϣ,���title�ĵȼ����� ���Ʊ��е�����,�͸�������һ��ϵͳ��Ϣ
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
						mailInfoService.sendMail(pPk, -1, 2, "ϵͳ��ν��ʾ", vo
								.getSendContent());
					}
					else
						if (vo.getSendType() == 3)
						{
							mailInfoService.sendMail(pPk, -1, 2, "ϵͳ��ν��ʾ", vo
									.getSendContent());
							insertSystemInfoBySystem(pPk, vo.getSendContent());
						}
						else
							if (vo.getSendType() == 4)
							{
								// Ϊ4������ʽ��Ϣ
								insertPopMsggse(pPk, vo.getSendContent());
							}
			}
		}
		// Map<String,String> map =
		// infoDao.getSystemInfoControlByTitle(title_id);
		// �����ν������ͬ,��ÿ��10����������һ����Ϣ
		// for(int i=0;i < map.size();i++) {
		// insertSystemInfoBySystem(pPk,map.get(i),i*10);
		// }
		sendCollageSystemInfo(pPk, 0, title_id, 0, 0);
	}

	/**
	 * ����ϵͳ��Ϣ��̨���Ʊ��е��ۺϷ�����Ϣ������,������������� ���Ʊ��е�����,�͸�������һ��ϵͳ��Ϣ �ۺ�ϵͳ���������ĸ�,
	 * �ȼ����ϲ���,�ȼ������ⲿ����,�������ֵΪ��,��partvo��ȡ��, �������ϲ���,���������ⲿ����,�������ֵΪ��,��partvo��ȡ��.
	 * ����id����,�������ⲿ���롣 ��ν����,��ν���������ⲿ����,�������ֵΪ��,��partvo��ȡ��.
	 * 
	 * @param pPk
	 * @param task_id
	 *            ����id
	 * @param title
	 *            ��ν
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
			// ������ʱ������
			// TODO-������������ϵͳ��Ϣ��ʱ������
		}
		// int num = 0; //�����Ͷ��ʱ�Ķ�ʱ��
		// ����ۺ�����Ϣ�ķ�������
		SysInfoDao sysInfoDao = new SysInfoDao();
		List<SystemControlInfoVO> list = sysInfoDao.getSystemInfoControlByPCollage();
		// logger.info("�ۺ����͵�����="+list.size());
		if (list.size() != 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				SystemControlInfoVO systemInfovo = list.get(i);
				String sqlPGrade = systemInfovo.getPlayerGrade();
				// ����ȼ������
				// logger.info("sqlPGrade="+sqlPGrade+" ,p_grade="+p_grade);
				if (p_grade >= Integer.valueOf(sqlPGrade.split(",")[0])
						&& p_grade <= Integer.valueOf(sqlPGrade.split(",")[1]))
				{
					// ���������νҪ��Ϊ0�ҳ�ν����
					// logger.info("getTitle="+systemInfovo.getTitle()+"
					// ,title="+title);
					if (systemInfovo.getTitle().equals("0")|| role_info.getTitleSet().isHaveByTitleStr(systemInfovo.getTitle())==true)
					{
						// �������id����
						// logger.info("getTaskId="+systemInfovo.getTaskId()+"
						// ,task_id="+task_id);
						if (systemInfovo.getTaskId() == 0
								|| task_id == systemInfovo.getTaskId())
						{
							// ����������ϣ���ʱ����
							// if����{}
							// logger.info("���ڷ�����");
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
											"ϵͳ��ʾ", systemInfovo
													.getSendContent());
								}
								else
									if (systemInfovo.getSendType() == 3)
									{
										mailInfoService.sendMail(p_pk, -1, 2,
												"ϵͳ��ʾ", systemInfovo
														.getSendContent());
										insertSystemInfoBySystem(p_pk,
												systemInfovo.getSendContent());
									}
									else
										if (systemInfovo.getSendType() == 4)
										{
											// Ϊ4������ʽ��Ϣ
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
			// ������ʱ������
			// TODO-������������ϵͳ��Ϣ��ʱ������
		}
		// int num = 0; //�����Ͷ��ʱ�Ķ�ʱ��
		// ����ۺ�����Ϣ�ķ�������
		SysInfoDao sysInfoDao = new SysInfoDao();
		List<SystemControlInfoVO> list = sysInfoDao
				.getSystemInfoControlByNewPlayer();
		// logger.info("�ۺ����͵�����="+list.size());
		if (list.size() != 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				SystemControlInfoVO systemInfovo = list.get(i);
				String sqlPGrade = systemInfovo.getPlayerGrade();
				// ����ȼ������
				// logger.info("sqlPGrade="+sqlPGrade+" ,p_grade="+p_grade);
				if (p_grade >= Integer.valueOf(sqlPGrade.split(",")[0])
						&& p_grade <= Integer.valueOf(sqlPGrade.split(",")[1]))
				{
					// ���������νҪ��Ϊ0�ҳ�ν����
					// logger.info("getTitle="+systemInfovo.getTitle()+"
					// ,title="+title);
					
					if (systemInfovo.getTitle().equals("0") || role_info.getTitleSet().isHaveByTitleStr(systemInfovo.getTitle())==true)
					{
						// �������id����
						// logger.info("getTaskId="+systemInfovo.getTaskId()+"
						// ,task_id="+task_id);
						if (systemInfovo.getTaskId() == 0
								|| task_id == systemInfovo.getTaskId())
						{
							// Ϊ4������ʽ��Ϣ
							insertPopMsggse(p_pk, systemInfovo.getSendContent());
						}
					}
				}
			}
		}
	}

	/**
	 * ���͵���ʽ��Ϣ
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
	 * ���Ͷ�ʱϵͳ��Ϣ
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
	 * ���ÿ��ʱ��ĸ�ʽ����ʾ
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
	 * ���ڴ�barea�µ������˷�ϵͳ��Ϣ
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
	 * ����pPk��ϵͳ��Ϣ��һЩ�ؼ�����ɾ������Ҫ��ϵͳ��Ϣ
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
	 * ����һЩϵͳ��Ϣ�ķ���ʱ��
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
	 * ��ѯ�Ƿ��� �ض��� ϵͳ��Ϣ
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
	 * ���Ͱ���ʤ����ϵͳ
	 * 
	 * @param info
	 */
	public void tongSiegeSendInfo(String info)
	{
		// Ҫ����ʮ��
		for (int i = 0; i < 10; i++)
		{
			this.insertSystemInfoBySystem(info, i);
		}

	}

	// ����ҵ��������� ����10Ϊ���ֵĵȼ����������� 8Ϊ���������
	public void setNewPlayerGuideInfoMSG(RoleEntity roleinfo, String type,
			String id)
	{
		// ������������ ��������ʽ��Ϣ
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
		// ���������ο����������ע�����Ϣ
		if (roleinfo.getBasicInfo().getPlayer_state_by_new() == 11
				|| roleinfo.getBasicInfo().getPlayer_state_by_new() == 10)
		{
			// ��Ҵ���9���� ���Ǹ�����߳���Ϸ
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
			{// ��Ҳ���9���� ���Ǹ������ʾ ע����Ϣ
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

	// ���ֵĵ���ʽ��Ϣ
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
