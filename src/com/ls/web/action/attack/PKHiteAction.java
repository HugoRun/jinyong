package com.ls.web.action.attack;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.vo.mounts.UserMountsVO;
import com.ls.ben.vo.pkhite.PKHiteVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.web.action.ActionBase;
import com.ls.web.service.mounts.MountsService;
import com.ls.web.service.pk.PKHiteService;
import com.pub.GameHint;
/**
 * 处理仇恨系统的Action
 * @author Thomas.lei
 *
 */
public class PKHiteAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");
	//分页显示玩家的仇恨列表
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String index=request.getParameter("index");
		String pPk = (String) request.getSession().getAttribute("pPk");
		if(index==null)
		{
			index="0";
		}
		else
		{
			int temp=Integer.parseInt(index)-1;
			index=temp+"";
		}
		PKHiteService ps=new PKHiteService();
		List<PKHiteVO> list= ps.getEnemys(Integer.parseInt(pPk), Integer.parseInt(index), 5);
		if(list==null||list.size()==0)
		{
			String message=GameHint.PK_HINT_3;
			request.setAttribute("message",message);
			return mapping.findForward("hintdisplay");
		}
		QueryPage qp=new QueryPage(5*Integer.parseInt(index),ps.getRecordNum(Integer.parseInt(pPk)),5,list);
		request.setAttribute("queryPage",qp);
		return mapping.findForward("hitelist");
	}
	//追杀信息
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity role_info = this.getRoleEntity(request);
		String scenceID=request.getParameter("scenceID");
		UserMountsVO uv= role_info.getMountSet().getCurMount();
		if(uv==null)
		{
			String message=GameHint.PK_HINT_2;
			request.setAttribute("message",message);
			return mapping.findForward("hintdisplay");
		}
		else
		{
			if(uv.getMountsLevle()<4)
			{
				String message=GameHint.PK_HINT_5;
				request.setAttribute("message",message);
				return mapping.findForward("hintdisplay");
			}
			try
			{
				request.getRequestDispatcher("/mounts.do?cmd=n8&carryGrade=4&scenceID="+scenceID+"&mountsID="+uv.getId()+"&mountState="+uv.getMountsState()+"").forward(request,response);
			}
			catch (Exception e)
			{
				String message=GameHint.PK_HINT_4;
				request.setAttribute("message",message);
				return mapping.findForward("hintdisplay");
			}
		}
		return null;	
	}
}
