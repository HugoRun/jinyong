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
 * ���ܣ�����PK���action
 * @author thomas.lei 
 * 27/04/10 PM
 */
public class PkActiveAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.action");

	// ������ұ�������
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		String message = "";
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month=cal.get(Calendar.MONTH)+1;
		// ÿ��1-3Ϊ����ʱ������ʱ�䲻���Ա���
		if(month!=5)
		{
			if(day>3)
			{
				message = "���������ѹ������ٱ���";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		else
		{
			if(day<12||day>14)
			{
				message = "���������ѹ������ٱ���";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		
		String pPk = (String) request.getSession().getAttribute("pPk");
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		int num = propGroupDao.getPropNumByByPropID(Integer.parseInt(pPk),Integer.parseInt(PKActiveContent.PROPID));// �õ�����100Ϊ����ID
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(pPk);
		// �жϱ���������ߺ͵ȼ��Ƿ����Ҫ��
		if (num < 1 || roleEntity.getBasicInfo().getGrade() < 60)
		{
			message = "��û�б������߻��ߵȼ����������Ա���";
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
		// �������ʷ������¼
		if (role != null)
		{
			if (DateUtil.getDifferDaysToToday(role.getRegistTime()) < 3)
			{
				message = "���Ѿ��������벻Ҫ�ظ�����";
				request.setAttribute("message", message);
				logger.info(message);
			}
			else
			{
				count = pkService.refreshRegist(roleBody);
				if (count > 0)
				{
					message = "���ѱ����ɹ���ף���õ�һ��";
					request.setAttribute("message", message);
					propGroupDao.removeByProp(Integer.parseInt(pPk),
							new String(PKActiveContent.PROPID));// ����ID100�ĵ��� ���˸��ݲ߻��޸�
				}
				else
				{
					message = "����ʧ�ܣ������²���";
					request.setAttribute("message", message);
					logger.info(message);
				}
			}
		}
		// û����ʷ��¼����
		else
		{
			roleBody.setRoleID(roleId);
			roleBody.setRoleLevel(roleLevel);
			roleBody.setRoleName(roleName);
			roleBody.setIsWin(0);
			count = pkService.pkActiveRegist(roleBody);
			if (count > 0)
			{
				message = "���ѱ����ɹ���ף���õ�һ��";
				request.setAttribute("message", message);
				propGroupDao.removeByProp(Integer.parseInt(pPk), new String(
						PKActiveContent.PROPID));// ����ID100�ĵ��� ���˸��ݲ߻��޸�
				logger.info(message);
			}
			else
			{
				message = "����ʧ�ܣ������²���";
				request.setAttribute("message", message);
				logger.info(message);
			}

		}
		return mapping.findForward("pkactivedisplay");
	}
	//������ʾ�����Ͳ鿴�������Ϣ
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String message="";
		String index=request.getParameter("index");
		String view=request.getParameter("view");
		if(view==null)
		{
			logger.info("����Ϊnull");
			message = "�������������²���";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		if(view.equals("vs"))//�����ڼ䲻�ɲ鿴������Ϣ
		{
			if (day<=3)
			{
				message = "�����ڼ䲻���Բ鿴������Ϣ";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
			
		}
		if(view.equals("rs"))//�����ڼ�͵��ձ�������ǰ���ɲ鿴�������
		{
			
			if (day<=3||hour<14)//2��ǰ���ɲ鿴�������
			{
				message = "���ڲ����Բ鿴������� ����ʱ��ÿ��2�㹫���������";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		PKActiveService ps=new PKActiveService();
		List list=ps.getAllRole();
		if(list.size()==1)
		{
			PKActiveRegist pr=(PKActiveRegist)list.get(0);
			message="���±����Ѿ�����,�ھ��ǣ�"+pr.getRoleName();
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
			message = "����û�н��...";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		QueryPage qp=new QueryPage(5*Integer.parseInt(index),ps.getTotalNum(),5,data);
		request.setAttribute("queryPage",qp);
		if(view.equals("vs"))
		{
			return mapping.findForward("vs_result");//��ʾ������Ϣ
		}
		else
		{
			return mapping.findForward("rs_result");//��ʾ���������Ϣ
		}
	}

	//�������������ص�����
	
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		//���ƷǱ���ʱ��Ľ���
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
				message = "����ʱ���ѹ������ɽ������";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		else
		{
			if(day==12 || day ==13 || day ==14||hour!=13||min>5)
			{
				message = "����ʱ���ѹ������ɽ������";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		//�ж��Ƿ��ǲ������
		PKActiveService ps=new PKActiveService();
		String appk=(String) request.getSession().getAttribute("pPk");
		int isPk=ps.getPpk(Integer.parseInt(appk));
		if(isPk==0)
		{
			PKActiveRegist pr=ps.checkRoleRegist(Integer.parseInt(appk));
			if(pr==null)
			{
				message = "�����ǲ�����ң����ܽ������";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		//�Ѿ�ʧ�ܵ���Ҳ����Բμӱ���
		if(!ps.checkIsFail(Integer.parseInt(appk)))
		{
			message = "���Ѿ�ʧ�ܣ������в����ٲμӱ���";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		//������ҽ��볡����״̬��Ϣ
		ps.updateEnterState(Integer.parseInt(appk),1);
		//�����������
		String pPk = (String) request.getSession().getAttribute("pPk");
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(pPk);
		roleEntity.getBasicInfo().updateSceneId(PKActiveContent.SCENEID_PK);///////////////////��������ID
		try { 
			request.getRequestDispatcher("/scene.do?isRefurbish=1").forward(request,response);
		} catch (Exception e) {
			message="���볡��ʧ�ܣ������²���";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		return null;
	}
	
	//�������
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		//�ж϶����Ƿ��ڳ�
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
			if(!sceneId.equals(x))//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@��ǰ����ID
			{
				message = "���Ķ���û�н���������أ������Ա���";
				request.setAttribute("message", message);
				return mapping.findForward("pkactivedisplay");
			}
		}
		else
		{
			message = "����û������...���ɱ���";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		//���빥������
		try { 
			request.getRequestDispatcher("/pk.do?cmd=n3&aPpk="+appk+"&bPpk="+bppk+"&tong=0").forward(request,response);
		} catch (Exception e) {
			message="����ʧ���������³���";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		return null;
	}
	//���ص�ʱ����³���
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String message="";
		String pPk = (String) request.getSession().getAttribute("pPk");
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(pPk);
		roleEntity.getBasicInfo().updateSceneId(PKActiveContent.NPCSCENEID);//���ر�����
		PKActiveService ps=new PKActiveService();
		//ps.updateEnterState(Integer.parseInt(pPk),0);
		try { 
			request.getRequestDispatcher("/menu.do?cmd=n1&menu_id="+PKActiveContent.REGISTMENUID+"").forward(request,response);///////////////////////////////////menuID
		} catch (Exception e) {
			message="����ʧ�ܣ������²���";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		return null;
	}
	//������ȡ��Ʒ
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String message="";
		String pPk = (String) request.getSession().getAttribute("pPk");
		PKActiveService ps=new PKActiveService();
		if(!ps.isGetPrice(Integer.parseInt(pPk)))
		{
			message="��û���ʸ�����Ѿ���ȡ����Ʒ";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		//�õ�������ߵ�Id
		int priceId=ps.getPlayerNum();
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(pPk);
		if(roleEntity.getBasicInfo().getWrapSpare()<1)
		{
			message="���İ����������������������ȡ";
			request.setAttribute("message", message);
			return mapping.findForward("pkactivedisplay");
		}
		int goodID=ps.getPlayerNum();
		GoodsService gs=new GoodsService();
		gs.putGoodsToWrap(Integer.parseInt(pPk), goodID,GoodsType.PROP,1);
		ps.updatePriceState(Integer.parseInt(pPk), 0);//��ȡ�꽱Ʒ�ı�״̬
		String goodsName= gs.getGoodsName(goodID,GoodsType.PROP);
		message="��ϲ����ȡ��Ʒ�ɹ�,����ȡ������:"+goodsName;
		request.setAttribute("message", message);
		return mapping.findForward("pkactivedisplay");
	}
	
}
