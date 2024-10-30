package com.web.service.taskpage;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ben.dao.task.TaskDAO;
import com.ben.shitu.model.ShituConstant;
import com.ben.vo.task.TaskVO;
import com.ben.vo.task.UTaskVO;
import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.util.ExchangeUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.DataErrorLog;
import com.ls.web.service.player.MyService;
import com.ls.web.service.player.MyServiceImpl;
import com.ls.web.service.task.TaskSubService;
import com.web.service.TaskFuHeService;
import com.web.service.TaskShaGuaiService;
import com.web.service.TaskXieDaiService;
import com.web.service.TaskXunWuService;

/**
 * ����:�˵�
 * 
 * @author ��ƾ� 11:13:44 AM
 */
public class TaskPageService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * ���������һ��
	 * @param roleInfo
	 * @param menus
	 * @return
	 */
	public String taskPageServicess(RoleEntity roleInfo, List menus,HttpServletRequest request,HttpServletResponse response)
	{
		OperateMenuVO menu = null;
		StringBuffer hint = new StringBuffer();
		TaskSubService taskSubService = new TaskSubService();
		for (int i = 0; i < menus.size(); i++)
		{
			menu = (OperateMenuVO) menus.get(i);
			String menu_tasks_id = menu.getMenuTasksId();
			String muensss = "";
			String muensssaa = "";
			if (menu_tasks_id != null && !menu_tasks_id.equals("") && !menu_tasks_id.equals("0"))
			{
				String[] menuTasksId = menu_tasks_id.split(",");// ȡ������ID
				String taskid = "";
				for (int t = 0; t < menuTasksId.length; t++)
				{
					if (menuTasksId[t] == null || menuTasksId[t].equals(""))
					{
						continue;
					}
					TaskVO taskVO = taskSubService.getById(Integer.parseInt(menuTasksId[t])+ ""); 
					CurTaskInfo uTaskVOaa = (CurTaskInfo) roleInfo.getTaskInfo().getCurTaskList().getByZu(taskVO.getTZu());
					String getTZu = "11";
					if (uTaskVOaa != null)
					{
						getTZu = uTaskVOaa.getTZu();
					}
					// û�������������
					if ((roleInfo.getTaskInfo().getTaskCompleteInfo().taskCompleteBoo(taskVO.getTZu()) == false)
							|| ((roleInfo.getTaskInfo().getTaskCompleteInfo().taskCompleteBoo(taskVO.getTZu()) == true) && taskVO.getTCycle() == 1))
					{
						String dd = null;
						if ((taskVO.getTLevelXiao() <= roleInfo.getBasicInfo()
								.getGrade())
								&& roleInfo.getBasicInfo().getGrade() <= taskVO
										.getTLevelDa()
								&& TaskVO.taskZu.indexOf(taskVO.getTZu()) == -1
								&& Integer.parseInt(taskVO.getTZuxl()) == 1
								&& !taskVO.getTZu().equals(getTZu))
						{
							muensssaa = "!";
							dd = menuTasksId[t] + ",";
							taskid += dd;
							muensss = "<postfield name=\"task_id\" value=\""
									+ taskid + "\" />";
						}
					}
				}
			}
			hint.append("" + muensssaa + "<anchor> ");
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/menu.do?cmd=n1")+"\">");
			hint.append("<postfield name=\"menu_id\" value=\"" + menu.getId() + "\" /> ");
			hint.append("<postfield name=\"menu_type\" value=\"" + menu.getMenuType() + "\" /> ");
			hint.append(muensss);
			hint.append("</go>");
			hint.append(menu.getMenuName());
			hint.append("</anchor><br/>");

		}
		return hint.toString();
	}
 
	
	/**
	 * ��ʾ������ϸ����
	 * @param roleInfo
	 * @param task_id
	 * @param menu_id
	 * @param menu_type
	 * @param sg
	 * @param xd
	 * @param xw
	 * @param fh
	 * @return
	 */
	public String taskPageViewService(RoleEntity roleInfo, String task_id,
			String menu_id, String menu_type, String sg,
			String xd, String xw, String fh,HttpServletRequest request,HttpServletResponse response)
	{
		StringBuffer hint = new StringBuffer();
		TaskDAO dao = new TaskDAO();
		TaskShaGuaiService taskShaGuaiService = new TaskShaGuaiService();
		TaskXieDaiService taskXieDaiService = new TaskXieDaiService();
		TaskXunWuService taskXunWuService = new TaskXunWuService();
		TaskFuHeService taskFuHeService = new TaskFuHeService();
		TaskNotPageService taskNotPageService = new TaskNotPageService();
		GoodsService goodsService = new GoodsService();
		//ͨ������ID��cacheȡ����������
		TaskVO vo = TaskCache.getById(task_id);
		String rwpx = vo.getTZuxl();
		String zu = vo.getTZu();
		int upTaskID = dao.getTaskZUXl(zu, (Integer.parseInt(rwpx) - 1) + "");
		TaskVO taskVO = TaskCache.getById(task_id);
		try
		{
			if (taskVO.getTType() == 2 && taskShaGuaiService.getShaGuaiDiscernService(taskVO.getTId() + "", roleInfo.getBasicInfo().getPPk()) == false)
			{
				hint.append(taskNotPageService.taskNotPageService(upTaskID + "", roleInfo));
				return hint.toString();
			}
			else
				if (taskVO.getTType() == 3 && taskXieDaiService.getXieDaiDiscernService(taskVO.getTId() + "", roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getUPk()) == false)
				{
					// hint.append("���İ�����û������������Ʒ<br/>");
					hint.append(taskNotPageService.taskNotPageService(upTaskID + "", roleInfo));
					return hint.toString();
				}
				else
					if (taskVO.getTType() == 4 && taskXunWuService.getXunWuDiscernService(taskVO.getTId() + "", roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getUPk()) == false)
					{
						// hint.append("���İ�����û������������Ʒ<br/>");
						hint.append(taskNotPageService.taskNotPageService(upTaskID + "", roleInfo));
						return hint.toString();
					}
					else
						if (taskVO.getTType() == 5 && taskFuHeService.getFuHeDiscernService(taskVO.getTId() + "", roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getUPk()) == false)
						{
							// hint.append("���İ�����û������������Ʒ<br/>");
							hint.append(taskNotPageService.taskNotPageService(upTaskID + "", roleInfo));
							return hint.toString();
						}
						else
							if (taskVO.getTType() == 5 && taskFuHeService.getFuHeDiscernZJService(taskVO.getTId() + "", roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getUPk()) == false)
							{
								// hint.append("����û����ȫ��ɸ�����<br/>");
								hint.append(taskNotPageService.taskNotPageService(upTaskID + "", roleInfo));
								return hint.toString();
							}
							else
							{
								if (taskVO.getTName() != null)
								{
									hint.append(taskVO.getTName() + "<br/>");
								}
								if (taskVO.getTTishi() != null && !taskVO.getTTishi().equals(""))
								{
									hint.append(taskVO.getTTishi() + "<br/>");
								}
								if (taskVO.getTDisplay() != null)
								{
									String ss = taskVO.getTDisplay();
									String pp = ss.replaceAll("\\(OWN\\)", "" + roleInfo.getBasicInfo().getName() + "");
									//�滻����˵��
									String task_content = ExchangeUtil.getTitleBySex(pp, roleInfo.getBasicInfo().getSex());
									hint.append(task_content + "<br/>");
								}
								MyService my = new MyServiceImpl();
								boolean isShitu = my.isShitu(roleInfo.getBasicInfo().getPPk());
								if (taskVO.getTMoney() != null && !taskVO.getTMoney().equals("") && !taskVO.getTMoney().equals("0"))
								{
									int money = Integer.parseInt(taskVO.getTMoney().trim());
									hint.append("��Ǯ:" + (isShitu?money*ShituConstant.MORE_TASK/100:money) + "��ʯ<br/>");
								}
								if (taskVO.getTExp() != null && !taskVO.getTExp().equals("") && !taskVO.getTExp().equals("0"))
								{
									int exp = Integer.parseInt(taskVO.getTExp().trim());
									String hint2="";
									if(roleInfo.getBasicInfo().getGrade() == GameConfig.getGradeUpperLimit() && roleInfo.getBasicInfo().getCurExp().equals(roleInfo.getBasicInfo().getNextGradeExp())){
										hint2 = "(���־���ת��Ϊ��Ǯ)";
									}
									hint.append("����:" + (isShitu?exp*ShituConstant.MORE_TASK/100:exp)  + hint2+"<br/>");
								}
								if (taskVO.getTSw() != null && !taskVO.getTSw().equals("0") && !taskVO.getTSw().equals("")) {
									int tsw = Integer.parseInt(taskVO.getTSw().trim());
									hint.append("����:" + (isShitu?tsw*ShituConstant.MORE_TASK/100:tsw) + "<br/>");
								} 
								if (taskVO.getTEncouragement() != null && !taskVO.getTEncouragement().equals("") && !taskVO.getTEncouragement().equals("0"))
								{
									String[] tGeidjs = taskVO.getTEncouragement().split(",");
									String[] tGeidjNumbers = taskVO.getTWncouragementNo().split(",");
									for (int i = 0; i < tGeidjs.length; i++)
									{
										hint.append("��Ʒ:"+ goodsService.getGoodsName(Integer.parseInt(tGeidjs[i]),4)+ " x"+ tGeidjNumbers[i]+ "<br/>");
									} 
								}
								//װ������
								if (taskVO.getTEncouragementZb() != null && !taskVO.getTEncouragementZb().equals("") && !taskVO.getTEncouragementZb().equals("0"))
								{
									String equip_list[] = taskVO.getTEncouragementZb().split(",");
									for (int i = 0; i < equip_list.length; i++)
									{
										try{
											String equip_id_str = equip_list[i];
											GameEquip equip = EquipCache.getById(Integer.parseInt(equip_id_str.trim()));
											if( equip!=null )
											{
												hint.append("װ��:" + equip.getName() + "x" + taskVO.getTEncouragementNoZb() + "<br/>");
											}
										}catch(Exception e)
										{
											DataErrorLog.task(taskVO.getTId(), "������װ�����ݴ���"+taskVO.getTEncouragementZb() );
										}
									}
								}
								// ͨ����һ������ID ȡ����һ�����������
								TaskVO taskVONext = TaskCache.getById(taskVO.getTNext()+"");
								int type = taskVONext.getTType();
								if (taskVO.getTXrwnpcId() == Integer.parseInt(menu_id) && taskVO.getTType() == 1 && type == 1 && taskVO.getTId() != taskVO.getTNext())
								{ 
									hint.append("<anchor> ");
									hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskadd.do")+"\">");
									hint.append("<postfield name=\"tId\" value=\"" + taskVO.getTId() + "\" /> ");
									hint.append("<postfield name=\"menu_id\" value=\"" + menu_id + "\" /> ");
									hint.append("<postfield name=\"nocarry\" value=\"yes\" /> ");
									hint.append("</go>");
									hint.append("ȷ��");
									hint.append("</anchor>");
									hint.append("|");
									hint.append("<anchor>");
									hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"/>");
									hint.append("ȡ��");
									hint.append("</anchor>");
									hint.append("<br/><anchor> ");
									hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskadd.do")+"\">");
									hint.append("<postfield name=\"tId\" value=\"" + taskVO.getTId() + "\" /> ");
									hint.append("<postfield name=\"menu_id\" value=\"" + menu_id + "\" /> ");
									hint.append("</go>");
									if(!taskVO.getTKey().equals(""))
									{
										hint.append("ǰ��");
										hint.append(taskVO.getTKey().split("-").length!=2?taskVO.getTKey():taskVO.getTKey().split("-")[0]);
									}
									hint.append("</anchor>");
									
								}
								else
								{
									hint.append("<anchor> ");
									hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskadd.do")+"\">");
									hint.append("<postfield name=\"tId\" value=\"" + taskVO.getTId() + "\" /> ");
									hint.append("<postfield name=\"menu_id\" value=\"" + menu_id + "\" /> ");
									hint.append("<postfield name=\"nocarry\" value=\"yes\" /> ");
									hint.append("</go>");
									hint.append("ȷ��");
									hint.append("</anchor>");
									hint.append("|");
									hint.append("<anchor>");
									hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"/>");
									hint.append("ȡ��");
									hint.append("</anchor>");
									hint.append("<br/><anchor> ");
									hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskadd.do")+"\">");
									hint.append("<postfield name=\"tId\" value=\"" + taskVO.getTId() + "\" /> ");
									hint.append("<postfield name=\"menu_id\" value=\"" + menu_id + "\" /> ");
									hint.append("</go>");
									if(!taskVO.getTKey().equals(""))
									{
										hint.append("ǰ��");
										hint.append(taskVO.getTKey().split("-").length!=2?taskVO.getTKey():taskVO.getTKey().split("-")[0]);
									}
									hint.append("</anchor>");
									
								}
								
							}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hint.toString();
	}
	
	public List<TaskVO> getCurrentageTaskByGrade(RoleEntity roleInfo)
	{
		TaskDAO dao = new TaskDAO();
		
		// ��ѯ�����п�������
		List<TaskVO> tasklist = getTaskListByGrade(roleInfo.getBasicInfo().getGrade(), roleInfo.getBasicInfo().getSex());
		logger.info("��ѯ�����п�������=" + tasklist.size());
		// �Ѿ���ɵ���������
		HashMap completeList = roleInfo.getTaskInfo().getTaskCompleteInfo().taskCompleteHashMap();
		logger.info("�Ѿ���ɵ���������=" + completeList.size());
		// �������ڽ��е�����
		List<String> runningList = dao.allTaskInUser(roleInfo.getPPk());
		logger.info("�������ڽ��е�����=" + runningList.size());

		// ���õ��Ļ�û���������б�
		List<TaskVO> suitlist = null;

		if (tasklist != null && tasklist.size() != 0)
		{
			for (int i = tasklist.size() - 1; i >= 0; i--)
			{
				TaskVO task = tasklist.get(i);
				
				//ȥ���ƺŲ����������
				if( roleInfo.getTitleSet().isHaveByTitleStr(task.getTSchool())==false )
				{
					tasklist.remove(i);
					continue;
				}
				
				// ����Ѿ������Ĳ������г�
				if (completeList != null && completeList.containsKey(task.getTZu()))
				{
					tasklist.remove(i);
					continue;
				}
				// �������Ĳ������г�
				if (runningList != null && runningList.contains(task.getTZu()) )
				{ 
					tasklist.remove(i);
					continue;
				}
			}
		}
		//�õ��Ѿ������������б�
		List list = roleInfo.getTaskInfo().getCurTaskList().getCurTaskList();
		for(int i = 0 ; i < list.size() ; i++ ){
			UTaskVO vo = (UTaskVO) list.get(i);
			if(vo.getTGiveUp() == 1){
				
			TaskVO taskVOCache = TaskCache.getById(vo.getTId()+"");
			TaskVO taskVO = new TaskVO();
			taskVO.setTId(vo.getTId());
			taskVO.setTZu(vo.getTZu());
			taskVO.setTZuxl(vo.getTPx());
			taskVO.setTName(vo.getTTitle());
			taskVO.setTLevelXiao(taskVOCache.getTLevelXiao());
			tasklist.add(taskVO);
			
		}
		}
		
		suitlist = tasklist;
		logger.info("���õ��Ļ�û���������б�=" + suitlist.size());
		return suitlist;
	}

	/**
	 * ������еĺ��������б�
	 * @param grade
	 * @param school
	 * @param sex
	 * @return
	 */
	private List<TaskVO> getTaskListByGrade(int grade, int sex)
	{
		String allTaskId = getAllTaskId();
		logger.info("���������id=" + allTaskId);
		TaskDAO dao = new TaskDAO();
		List<TaskVO> tasklist = dao.getTaskListByGrade(grade, sex, allTaskId);
		return tasklist;
	}

	/**
	 * ������е�����id
	 * @return
	 */
	private String getAllTaskId()
	{
		TaskDAO dao = new TaskDAO();
		return dao.getAllTaskId();
	}

	public String gettaskList(String task_id, String menu_id, String menu_type,
			String mapid, String sg, String xd, String xw, String fh,HttpServletRequest request,HttpServletResponse response)
	{

		StringBuffer sb = new StringBuffer();
		sb.append("��������:<br/>");
		String[] cc = task_id.split(",");
		for (int i = 0; i < cc.length; i++)
		{
			 
			if (cc[i] == null || cc[i].equals(""))
			{
				continue;
			}
			sb.append("<anchor> ");
			sb.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskmenuaction.do?cmd=n1")+"\">");
			sb.append("<postfield name=\"menu_id\" value=\"" + menu_id + "\" /> ");
			sb.append("<postfield name=\"task_id\" value=\"" + cc[i] + "\" /> ");
			sb.append("<postfield name=\"menu_type\" value=\"" + menu_type + "\" /> ");
			sb.append("<postfield name=\"sg\" value=\"" + sg + "\" /> ");
			sb.append("<postfield name=\"xd\" value=\"" + xd + "\" /> ");
			sb.append("<postfield name=\"xw\" value=\"" + xw + "\" /> ");
			sb.append("<postfield name=\"fh\" value=\"" + fh + "\" /> ");
			sb.append("</go>");
			TaskVO vo = TaskCache.getById(cc[i]);
			sb.append(vo.getTName());
			sb.append("</anchor><br/>");
		}
		return sb.toString();
	}

	public String taskPageListService(String task_id, String menu_id,
			String menu_type, String sg, String xd, String xw, String fh,HttpServletRequest request,HttpServletResponse response)
	{
		StringBuffer hint = new StringBuffer();
		hint.append("��������:<br/>");
		String[] cc = task_id.split(","); 
		for (int i = 0; i < cc.length; i++)
		{ 
			if (cc[i] == null || cc[i].equals(""))
			{
				continue;
			}
			hint.append("<anchor> ");
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskmenuaction.do?cmd=n1")+"\">");
			hint.append("<postfield name=\"menu_id\" value=\"" + menu_id + "\" /> ");
			hint.append("<postfield name=\"task_id\" value=\"" + cc[i] + "\" /> ");
			hint.append("<postfield name=\"menu_type\" value=\"" + menu_type + "\" /> ");
			hint.append("<postfield name=\"sg\" value=\"" + sg + "\" /> ");
			hint.append("<postfield name=\"xd\" value=\"" + xd + "\" /> ");
			hint.append("<postfield name=\"xw\" value=\"" + xw + "\" /> ");
			hint.append("<postfield name=\"fh\" value=\"" + fh + "\" /> ");
			hint.append("</go>");
			TaskVO vo = TaskCache.getById(cc[i]);
			hint.append(vo.getTName());
			hint.append("</anchor><br/>");
		}
		return hint.toString();
	}

	/**
	 * �����������������,���Բ������񲢴�������������ȷ,Ӧ��˵����ȷ�Ǻ�������. ������������ʱ����������ٰ��������Ǵ�����ڣ�
	 * ��ʱ��Ҫ�����ݽ��е�����
	 * ��ĵ�ʱ����������Ͳ��ڵ���
	 */
	public void getAmendMenuMap(OperateMenuVO menuvo, String taskid)
	{
		if (menuvo == null || menuvo.getMenuMap() == 0)
		{
			return;
		}
		else
		{
			return;
		}

	}
	
	
	/**
	 * ��������ID�б�
	 * @param task_id
	 * @return
	 */
	public String regroupTaskId(String task_id)
	{
		String regroupTaskId = "";
		if (task_id != null && !task_id.equals(""))
		{
			String[] taskid = task_id.split(",");
			for(int i = 0 ; i < taskid.length ; i++){ 
				if (taskid[i] == null || taskid[i].equals(""))
				{
					continue;
				}
				regroupTaskId += taskid[i]+",";
			}
		}
		return regroupTaskId;
	}
}
