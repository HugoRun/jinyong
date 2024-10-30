package com.web.service.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ben.dao.info.map.PartMapDAO;
import com.ben.dao.task.UTaskDAO;
import com.ben.vo.task.TaskVO;
import com.ben.vo.task.UTaskVO;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.ben.vo.mounts.MountsVO;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.mounts.MountsService;
import com.ls.web.service.room.RoomService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.web.service.TaskService;
import com.web.service.TaskXunWuService;

/**
 * ����:������ʾ����
 * 
 * @author ��ƾ� 11:13:44 AM
 */
public class TaskPageService
{

	/**
	 * ɱ���ֺ�������ʾ
	 */
	public String getGeiDJService(int npcID, String npcName, int pPk)
	{
		// ��ȡNPCID
		String hint = null;
		int npcid = npcID;
		String tType = "2";
		UTaskDAO dao = new UTaskDAO();
		List<UTaskVO> list = dao.getUTaskNpcId(pPk + "", npcid + "", tType);
		if (list != null)
		{
			String tasShaGuaiXiaYiBu = "";
			for (int i = 0; i < list.size(); i++)
			{
				UTaskVO vo = list.get(i);
				if ((vo.getTKillingOk() + 1) <= vo.getTKillingNo())
				{
				hint = "����:" +  vo.getTaskInfo().getTName() + "��"+ npcName + "��" + (vo.getTKillingOk() + 1)+ "/" + vo.getTKillingNo() + "";
				if((vo.getTKillingOk() + 1) ==  vo.getTKillingNo()){
					TaskVO task = TaskCache.getById(vo.getTId()+"");//��ȡ������������
					String[] taskKeyValue = this.regroupTaskId(task.getTKeyValue()).split(",");
					tasShaGuaiXiaYiBu = this.tasShaGuaiXiaYiBu(taskKeyValue[(taskKeyValue.length - 1)], vo.getTId() + "");
				}
				return hint+tasShaGuaiXiaYiBu;
				}
			}
		}
		return hint;
	}

	/**
	 * Ѱ����������ʾ
	 */

	public String Find(int pPk, int goodid, int goodtype,RoleEntity roleInfo)
	{
		// ��ȡNPCID
		String hint = null;
		String tType = "4";
		UTaskDAO dao = new UTaskDAO();
		List<UTaskVO> list = dao.getGoods(pPk + "", tType);
		GoodsService goodsService = new GoodsService();
		TaskXunWuService taskXunWuService = new TaskXunWuService();
		if (list != null && list.size() != 0)
		{
			String tasShaGuaiXiaYiBu = "";
			for (int i = 0; i < list.size(); i++)
			{
				UTaskVO vo = list.get(i);
				if (vo.getTGoods() != null && !vo.getTGoods().equals(""))
				{// ȡ�������е���Ʒ
					String[] goods = vo.getTGoods().split(",");
					String[] goodsnumber = vo.getTGoodsNo().split(",");
					for (int gd = 0; gd < goods.length; gd++)
					{
						if (goodid == Integer.parseInt(goods[gd]))
						{
						int dd = goodsService.getPropNum(pPk, Integer.parseInt(goods[gd]));// ȡ�����������Ʒ����
						if (dd <= Integer.parseInt(goodsnumber[gd])){
						hint = "����:"+ vo.getTaskInfo().getTName()+ "��"+ goodsService.getGoodsName(Integer.parseInt(goods[gd]), 4)+ "��" + dd + "/" + goodsnumber[gd]+ "";
						if(taskXunWuService.getXunWuDiscernService(vo.getTId() + "", roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getUPk())){
							TaskVO task = TaskCache.getById(vo.getTId()+"");//��ȡ������������
							String[] taskKeyValue = this.regroupTaskId(task.getTKeyValue()).split(",");
							tasShaGuaiXiaYiBu = this.tasShaGuaiXiaYiBu(taskKeyValue[(taskKeyValue.length - 1)], vo.getTId() + "");
						}
						return hint+tasShaGuaiXiaYiBu;
						}
						}
					}
				}
			}
		}
		return hint;
	}

	/**
	 * Ѱ����������ʾ
	 */

	public String Complex(int pPk, int goodid, int tPk)
	{
		// ��ȡNPCID
		String hint = null;
		if(goodid == 0){
			return hint;
		}
		UTaskDAO dao = new UTaskDAO();
		UTaskVO vo = dao.getPoint(pPk + "", tPk);
		GoodsService goodsService = new GoodsService();
		int dd = goodsService.getPropNum(pPk, goodid);// ȡ�����������Ʒ����
		if (vo.getTGoodsNo() != null && !vo.getTGoodsNo().equals("") && !vo.getTGoodsNo().equals("0")){
				hint = "����:" + vo.getTaskInfo().getTName() + "��" + goodsService.getGoodsName(goodid, 4) + "��" + (dd + 1) + "/" + vo.getTGoodsNo() + "";
		}
		return hint;
	}

	/**
	 * �����м��������һ��
	 * @param task_id
	 * @param menu_id
	 * @return
	 */ 
	public String taskZhongJianDianXiaYiBu(RoleEntity roleInfo,String menu_id,String tPk,HttpServletRequest request,HttpServletResponse response,int tPoint){ 
		StringBuffer hint = new StringBuffer();
		MenuService menuService = new MenuService();
		
		CurTaskInfo curTaskInfo = (CurTaskInfo)roleInfo.getTaskInfo().getCurTaskList().getById(tPk);//��ȡ���������������
		TaskVO taskVOCache = TaskCache.getById(curTaskInfo.getTId()+"");//��ȡ������������
		if(curTaskInfo.getTPoint() != null && !curTaskInfo.getTPoint().equals("0") && !curTaskInfo.getTPoint().equals("") && !curTaskInfo.getTPoint().equals("null")){
			String getTPoint[] = this.regroupTaskId(curTaskInfo.getTPoint()).split(","); 
			String ddc11 = "";
			for(int i = 0 ; i < getTPoint.length ; i++){
				if (Integer.parseInt(getTPoint[i])!=tPoint) {
					 String tt = getTPoint[i] + ",";
					 ddc11 += tt; 
				 }
			}
			String s[] = this.regroupTaskId(ddc11).split(",");
			if(s[0] != null && !s[0].equals("") && !s[0].equals("null") && !s[0].equals("0")){
				OperateMenuVO menu = menuService.getMenuById(Integer.parseInt(s[0]));//��ȡ�˵����������
				hint.append("<anchor> "); 
				hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do?cmd=n10")+"\">");
				hint.append("<postfield name=\"taskKeyValue\" value=\"" + menu.getMenuMap()+"" + "\" /> ");
				hint.append("<postfield name=\"tId\" value=\"" + curTaskInfo.getTId() + "\" /> ");
				hint.append("</go>");
				if(!taskVOCache.getTKey().equals(""))
				{
					hint.append("ǰ��");
					hint.append(taskVOCache.getTKey().split("-").length!=2?taskVOCache.getTKey():taskVOCache.getTKey().split("-")[1]);
				}
				hint.append("</anchor>");
				return hint.toString();
			}else{ 
				String[] taskKeyValue = this.regroupTaskId(taskVOCache.getTKeyValue()).split(",");
				hint.append("<anchor> "); 
				hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do?cmd=n10")+"\">");
				hint.append("<postfield name=\"taskKeyValue\" value=\"" + taskKeyValue[(taskKeyValue.length - 1)] + "\" /> ");
				hint.append("<postfield name=\"tId\" value=\"" + curTaskInfo.getTId() + "\" /> ");
				hint.append("</go>");
				if(!taskVOCache.getTKey().equals(""))
				{
					hint.append("ǰ��");
					hint.append(taskVOCache.getTKey().split("-").length!=2?taskVOCache.getTKey():taskVOCache.getTKey().split("-")[1]);
				}
				hint.append("</anchor>");
				return hint.toString();
			} 
		}else{
			String[] taskKeyValue = this.regroupTaskId(taskVOCache.getTKeyValue()).split(",");
			hint.append("<anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do?cmd=n10")+"\">");
			hint.append("<postfield name=\"taskKeyValue\" value=\"" + taskKeyValue[(taskKeyValue.length - 1)] + "\" /> ");
			hint.append("<postfield name=\"tId\" value=\"" + curTaskInfo.getTId() + "\" /> ");
			hint.append("</go>");
			if(!taskVOCache.getTKey().equals(""))
			{
				hint.append("ǰ��");
				hint.append(taskVOCache.getTKey().split("-").length!=2?taskVOCache.getTKey():taskVOCache.getTKey().split("-")[1]);
			}
			hint.append("</anchor>");
			return hint.toString();
		}
	}
	
	/**
	 * �����м��������һ��
	 * @param task_id
	 * @param menu_id
	 * @return
	 */
	public String tasShaGuaiXiaYiBu(String taskKeyValue,String tId){
		    StringBuffer hint = new StringBuffer();
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+GameConfig.getContextPath()+"/taskinfoaction.do?cmd=n10\">");
			hint.append("<postfield name=\"taskKeyValue\" value=\"" + taskKeyValue + "\" /> ");
			hint.append("<postfield name=\"tId\" value=\"" + tId + "\" /> ");
			hint.append("</go>");
			TaskVO taskVOCache = TaskCache.getById(tId);
			if(!taskVOCache.getTKey().equals(""))
			{
				hint.append("ǰ��");
				hint.append(taskVOCache.getTKey().split("-").length!=2?taskVOCache.getTKey():taskVOCache.getTKey().split("-")[1]);
			}
			hint.append("</anchor>");
			return hint.toString();
	}
	
	/**
	 * �����м��������һ��
	 * @param task_id
	 * @param menu_id
	 * @return
	 */
	public String tasShaGuaiXiaYiBuShaGuai(String taskKeyValue,String tId,String player_point_legth,String point_legth){
		    StringBuffer hint = new StringBuffer();
		    if(!player_point_legth.equals("0")){
		    	hint.append("���:"+player_point_legth+"/"+point_legth+"<br/>");
		    }
			hint.append("<anchor> "); 
			hint.append("<go method=\"post\" href=\""+GameConfig.getContextPath()+"/taskinfoaction.do?cmd=n10\">");
			hint.append("<postfield name=\"taskKeyValue\" value=\"" + taskKeyValue + "\" /> ");
			hint.append("<postfield name=\"tId\" value=\"" + tId + "\" /> ");
			hint.append("</go>");
			TaskVO taskVOCache = TaskCache.getById(tId);
			if(!taskVOCache.getTKey().equals(""))
			{
				hint.append("ǰ��");
				hint.append(taskVOCache.getTKey().split("-").length!=2?taskVOCache.getTKey():taskVOCache.getTKey().split("-")[1]);
			}
			hint.append("</anchor>");
			return hint.toString();
	}
	
	// ������ת
	public String key(String key, String keyvalue, String tishi,HttpServletRequest request,
			HttpServletResponse response)
	{
		String[] ss = key.split(",");
		String[] ee = keyvalue.split(",");
		String qq = null;
		for (int i = 0; i < ss.length; i++)
		{
			if (i == 0)
			{
				qq = tishi.replace(ss[i],
								"<anchor><go method=\"post\"  href=\""
										+ response.encodeURL(GameConfig.getContextPath()+"/menu.do")
										+ "\"><postfield name=\"cmd\" value=\"n1\" /><postfield name=\"menu_id\" value=\""
										+ ee[i] + "\" /></go>" + ss[i]
										+ "</anchor> ");
			}
			if (i != 0)
			{
				qq = qq.replace(ss[i],
								"<anchor><go method=\"post\" href=\""
										+ response.encodeURL(GameConfig.getContextPath()+"/menu.do")
										+ "\"><postfield name=\"cmd\" value=\"n1\" /><postfield name=\"menu_id\" value=\""
										+ ee[i] + "\" /></go>" + ss[i]
										+ "</anchor> ");
			}
		}
		return qq;
	}

	/*
	 * ִ��ɱ������
	 */
	public String deleteTeskPorint(RoleEntity roleEntity)
	{
		String deadnpcxiayibu = null;
		if (roleEntity.getTaskInfo().getTaskId() != -1 && roleEntity.getTaskInfo().getTaskPoint() != null && !roleEntity.getTaskInfo().getTaskPoint().equals("") && roleEntity.getTaskInfo().getTaskMenu() != -1)
		{
			int tPk = roleEntity.getTaskInfo().getTaskId();// ����ID
			String taskPorint = roleEntity.getTaskInfo().getTaskPoint();// �����м��DI
			String menuID = roleEntity.getTaskInfo().getTaskMenu() + "";// �˵�ID
			MenuService ms = new MenuService();
			OperateMenuVO mvo = ms.getMenuById(Integer.parseInt(menuID));
			String[] npc = mvo.getMenuOperate1().split(",");
			if(npc[0].equals(roleEntity.getBasicInfo().getAttack_npc()+"")){
			if (taskPorint.indexOf(menuID) != -1)
			{
				String tPoint[] = taskPorint.split(",");
				String ddc11 = "";
				for (int i = 0; i < tPoint.length; i++)
				{
					String ddc = "";
					if (Integer.parseInt(tPoint[i]) != Integer.parseInt(menuID))
					{
						ddc = tPoint[i] + ",";
						ddc11 += ddc;
					}
				}
				roleEntity.getTaskInfo().setTaskPoint(ddc11);
				CurTaskInfo curTaskInfo = (CurTaskInfo) roleEntity.getTaskInfo().getCurTaskList().getById(tPk + "");
				curTaskInfo.updatePoint(ddc11); 
				//�������ж�һ����һ������
				String s[] = ddc11.split(",");
				MenuService menuService = new MenuService();
				
				TaskVO taskVOCache = TaskCache.getById(curTaskInfo.getTId()+"");//��ȡ������������
				
				int point_num = getPointNum(taskVOCache.getTPoint()); 
				int player_point_num = 0; 
				
				if(s[0] != null && !s[0].equals("") && !s[0].equals("null") && !s[0].equals("0")){
					player_point_num = point_num - getPointNum(ddc11);
					OperateMenuVO menu = menuService.getMenuById(Integer.parseInt(s[0]));//��ȡ�˵����������
					deadnpcxiayibu = this.tasShaGuaiXiaYiBuShaGuai(menu.getMenuMap()+"", curTaskInfo.getTId()+"",player_point_num+"",point_num+"");
				}else{
					if(taskVOCache.getTKeyValue() != null && !taskVOCache.getTKeyValue().equals("") && !taskVOCache.getTKeyValue().equals("null")){
						player_point_num = point_num;
						String[] taskKeyValue = this.regroupTaskId(taskVOCache.getTKeyValue()).split(",");
						deadnpcxiayibu = this.tasShaGuaiXiaYiBuShaGuai(taskKeyValue[(taskKeyValue.length - 1)], curTaskInfo.getTId()+"",player_point_num+"",point_num+"");
					}
				}
				CurTaskInfo vo = roleEntity.getTaskInfo().getCurTaskList().getById(tPk + "");
				TaskService taskService = new TaskService();
				if (vo.getTMidstGs() != null && !vo.getTMidstGs().equals("0") && !vo.getTMidstGs().equals(""))
				{
					String number = "1";
					taskService.getGeiDJService(roleEntity.getBasicInfo().getPPk(), vo.getTMidstGs(), GoodsType.PROP,number);
				}
				if (vo.getTMidstZb() != null && !vo.getTMidstZb().equals("0") && !vo.getTMidstZb().equals(""))
				{
					taskService.getGeiZBService(roleEntity.getBasicInfo().getPPk(), vo.getTMidstZb(), 1 + "");
				}
			}
			}
			roleEntity.getTaskInfo().setTaskId(-1);
			roleEntity.getTaskInfo().setTaskMenu(-1);
			roleEntity.getTaskInfo().setTaskPoint("");
		}
		return deadnpcxiayibu;
	}

	/**
	 * ������
	 * @param carrySceneId    Ҫ���͵��ĳ���id
	 * @param taskid		  ����id
	 * @return
	 */
	public String taskRemit(RoleEntity roleInfo,String carrySceneId,String taskid)
	{
		String old_scene_id = roleInfo.getBasicInfo().getSceneId();
		if( old_scene_id.equals(carrySceneId))
		{
			//��������͵��Ǳ�����������
			return null;
		}
		
		String hint = null;
		
		RoomService roomService = new RoomService();
		int map_type = roomService.getMapType(Integer.valueOf(old_scene_id));
		
		//��������ڼ���, 
		if(map_type == MapType.PRISON) {
			hint = "�Բ���,���ڼ���,�úø���!"; 
			return hint;
		}
		//�ж��Ƿ���Դ���
		hint  = roomService.isCarryedOut(Integer.parseInt(old_scene_id));
		if(hint != null){
			return hint;
		}
		//�ж��Ƿ���Դ���
		hint  = roomService.isCarryedIn(Integer.parseInt(carrySceneId));
		if(hint != null){
			return hint;
		}
		/*****��ǰ��������****/
		MountsService ms=new MountsService();
		hint=ms.useMountsByTask(roleInfo, carrySceneId);
		return hint;
		
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
	
	private int getPointNum(String point){
		String[] task_point = point.split(",");
		int legth = (task_point.length)-1;
		if(task_point[legth] == null ||task_point[legth].equals("")){
			return legth;
		}else{
			return legth+1;
		}
	}
	
	public String getTaskComplete(RoleEntity roleEntity,int tPk){
		CurTaskInfo curTaskInfo = (CurTaskInfo) roleEntity.getTaskInfo().getCurTaskList().getById(tPk + "");
		String ddc11 = curTaskInfo.getTPoint();
		String s[] = ddc11.split(",");
		
		TaskVO taskVOCache = TaskCache.getById(curTaskInfo.getTId()+"");//��ȡ������������
		
		int point_num = getPointNum(taskVOCache.getTPoint()); 
		int player_point_num = 0; 
		
		if(s[0] != null && !s[0].equals("") && !s[0].equals("null") && !s[0].equals("0")){
			player_point_num = point_num - getPointNum(ddc11);
			return "���:"+player_point_num+"/"+point_num+"<br/>";
		}else{
			player_point_num = point_num;
			return "���:"+player_point_num+"/"+point_num+"<br/>";
		}
	}
}
