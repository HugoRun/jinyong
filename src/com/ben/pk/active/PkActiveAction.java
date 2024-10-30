package com.ben.pk.active;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.util.DateUtil;
import com.ls.web.service.goods.GoodsService;

/**
 * 功能：处理PK活动的action
 * @author thomas.lei 
 * 27/04/10 PM
 */
public class PkActiveAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.action");

	// 处理玩家报名请求
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		String message = "";
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month=cal.get(Calendar.MONTH)+1;
		// 每月1-3为报名时间其他时间不可以报名
		if(month!=5)
		{
			if(day>3)
			{
				message = "报名日期已过不可再报名";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		else
		{
			if(day<12||day>14)
			{
				message = "报名日期已过不可再报名";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		
		String pPk = (String) request.getSession().getAttribute("pPk");
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		int num = propGroupDao.getPropNumByByPropID(Integer.parseInt(pPk),Integer.parseInt(PKActiveContent.PROPID));// 得到道具100为道具ID
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(pPk);
		// 判断报名所需道具和等级是否符合要求
		if (num < 1 || roleEntity.getBasicInfo().getGrade() < 60)
		{
			message = "您没有报名道具或者等级不够不可以报名";
			request.setAttribute("message", message);
			logger.info(message);
			return mapping.findForward("pkactivedisplay");
		}
		int roleId = roleEntity.getBasicInfo().getPPk();
		int roleLevel = roleEntity.getBasicInfo().getGrade();
		String roleName = roleEntity.getBasicInfo().getName();
		PKActiveService pkService = new PKActiveService();
		PKActiveRegist roleBody = new PKActiveRegist();
		roleBody.setRoleID(roleId);
		roleBody.setRoleLevel(roleLevel);
		roleBody.setRoleName(roleName);
		roleBody.setIsWin(0);
		int count = 0;
		PKActiveRegist role = pkService.checkRoleRegist(roleId);
		// 如果有历史报名记录
		if (role != null)
		{
			if (DateUtil.getDifferDaysToToday(role.getRegistTime()) < 3)
			{
				message = "您已经报名，请不要重复报名";
				request.setAttribute("message", message);
				logger.info(message);
			}
			else
			{
				count = pkService.refreshRegist(roleBody);
				if (count > 0)
				{
					message = "您已报名成功，祝你夺得第一名";
					request.setAttribute("message", message);
					propGroupDao.removeByProp(Integer.parseInt(pPk),
							new String(PKActiveContent.PROPID));// 道具ID100的道具 完了根据策划修改
				}
				else
				{
					message = "报名失败，请重新操作";
					request.setAttribute("message", message);
					logger.info(message);
				}
			}
		}
		// 没有历史记录处理
		else
		{
			roleBody.setRoleID(roleId);
			roleBody.setRoleLevel(roleLevel);
			roleBody.setRoleName(roleName);
			roleBody.setIsWin(0);
			count = pkService.pkActiveRegist(roleBody);
			if (count > 0)
			{
				message = "您已报名成功，祝你夺得第一名";
				request.setAttribute("message", message);
				propGroupDao.removeByProp(Integer.parseInt(pPk), new String(
						PKActiveContent.PROPID));// 道具ID100的道具 完了根据策划修改
				logger.info(message);
			}
			else
			{
				message = "报名失败，请重新操作";
				request.setAttribute("message", message);
				logger.info(message);
			}

		}
		return mapping.findForward("pkactivedisplay");
	}
	//处理显示对阵表和查看对阵表信息
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String message="";
		String index=request.getParameter("index");
		String view=request.getParameter("view");
		if(view==null)
		{
			logger.info("参数为null");
			message = "参数错误，请重新操作";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		if(view.equals("vs"))//报名期间不可查看对阵信息
		{
			if (day<=3)
			{
				message = "报名期间不可以查看对阵信息";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
			
		}
		if(view.equals("rs"))//报名期间和当日比赛结束前不可查看比赛结果
		{
			
			if (day<=3||hour<14)//2点前不可查看比赛结果
			{
				message = "现在不可以查看比赛结果 比赛时间每日2点公布比赛结果";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		PKActiveService ps=new PKActiveService();
		List list=ps.getAllRole();
		if(list.size()==1)
		{
			PKActiveRegist pr=(PKActiveRegist)list.get(0);
			message="本月比赛已经结束,冠军是："+pr.getRoleName();
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		if(index==null)
		{
			index="0";
		}
		else
		{
			int temp=Integer.parseInt(index)-1;
			index=temp+"";
		}
		List<PKVs> data=ps.getVsInfo(Integer.parseInt(index),5);
		if(data==null||data.size()==0)
		{
			message = "现在没有结果...";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		QueryPage qp=new QueryPage(5*Integer.parseInt(index),ps.getTotalNum(),5,data);
		request.setAttribute("queryPage",qp);
		if(view.equals("vs"))
		{
			return mapping.findForward("vs_result");//显示对阵信息
		}
		else
		{
			return mapping.findForward("rs_result");//显示比赛结果信息
		}
	}

	//处理进入比赛场地的请求
	
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		//限制非比赛时间的进入
		String message="";
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		int min=cal.get(Calendar.MINUTE);
		int month=cal.get(Calendar.MONTH)+1;
		if(month!=5)
		{
			if(day== 1 || day == 2 || day == 3||hour!=13||min>5)
			{
				message = "比赛时间已过，不可进入比赛";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		else
		{
			if(day==12 || day ==13 || day ==14||hour!=13||min>5)
			{
				message = "比赛时间已过，不可进入比赛";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		//判断是否是参赛玩家
		PKActiveService ps=new PKActiveService();
		String appk=(String) request.getSession().getAttribute("pPk");
		int isPk=ps.getPpk(Integer.parseInt(appk));
		if(isPk==0)
		{
			PKActiveRegist pr=ps.checkRoleRegist(Integer.parseInt(appk));
			if(pr==null)
			{
				message = "您不是参赛玩家，不能进入比赛";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		//已经失败的玩家不可以参加比赛
		if(!ps.checkIsFail(Integer.parseInt(appk)))
		{
			message = "您已经失败，本轮中不能再参加比赛";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		//更新玩家进入场景的状态信息
		ps.updateEnterState(Integer.parseInt(appk),1);
		//进入比赛场地
		String pPk = (String) request.getSession().getAttribute("pPk");
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(pPk);
		roleEntity.getBasicInfo().updateSceneId(PKActiveContent.SCENEID_PK);///////////////////比赛场景ID
		try { 
			request.getRequestDispatcher("/scene.do?isRefurbish=1").forward(request,response);
		} catch (Exception e) {
			message="进入场地失败，请重新操作";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		return null;
	}
	
	//进入比赛
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		//判断对手是否在场
		String message="";
		PKActiveService ps=new PKActiveService();
		String appk=(String) request.getSession().getAttribute("pPk");
		int bppk=ps.getPpk(Integer.parseInt(appk));
		RoleCache roleCache = new RoleCache();
		RoleEntity roleBEntity = roleCache.getByPpk(bppk);
		if(roleBEntity!=null)
		{
			String sceneId= roleBEntity.getBasicInfo().getSceneId();
			String x = PKActiveContent.SCENEID_PK;
			if(!sceneId.equals(x))//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@当前场景ID
			{
				message = "您的对手没有进入比赛场地，不可以比赛";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		else
		{
			message = "对手没有上线...不可比赛";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		//进入攻击场景
		try { 
			request.getRequestDispatcher("/pk.do?cmd=n3&aPpk="+appk+"&bPpk="+bppk+"&tong=0").forward(request,response);
		} catch (Exception e) {
			message="攻击失败请您重新尝试";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		return null;
	}
	//返回的时候更新场景
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String message="";
		String pPk = (String) request.getSession().getAttribute("pPk");
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(pPk);
		roleEntity.getBasicInfo().updateSceneId(PKActiveContent.NPCSCENEID);//返回报名处
		PKActiveService ps=new PKActiveService();
		//ps.updateEnterState(Integer.parseInt(pPk),0);
		try { 
			request.getRequestDispatcher("/menu.do?cmd=n1&menu_id="+PKActiveContent.REGISTMENUID+"").forward(request,response);///////////////////////////////////menuID
		} catch (Exception e) {
			message="返回失败，请重新操作";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		return null;
	}
	//处理领取奖品
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String message="";
		String pPk = (String) request.getSession().getAttribute("pPk");
		PKActiveService ps=new PKActiveService();
		if(!ps.isGetPrice(Integer.parseInt(pPk)))
		{
			message="您没有资格或者已经领取过奖品";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		//得到所获道具的Id
		int priceId=ps.getPlayerNum();
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(pPk);
		if(roleEntity.getBasicInfo().getWrapSpare()<1)
		{
			message="您的包裹已满，请清理后再来领取";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		int goodID=ps.getPlayerNum();
		GoodsService gs=new GoodsService();
		gs.putGoodsToWrap(Integer.parseInt(pPk), goodID,GoodsType.PROP,1);
		ps.updatePriceState(Integer.parseInt(pPk), 0);//领取完奖品改变状态
		String goodsName= gs.getGoodsName(goodID,GoodsType.PROP);
		message="恭喜您领取奖品成功,您领取到的是:"+goodsName;
		request.setAttribute("message", message);
		return mapping.findForward("pkactivedisplay");
	}
	
}
