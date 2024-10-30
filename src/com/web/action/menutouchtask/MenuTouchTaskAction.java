package com.web.action.menutouchtask;

 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.vo.info.effect.PropUseEffect;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.task.TaskSubService;

/**
 * @author ��ƾ�  �˵���������
 */
public class MenuTouchTaskAction extends DispatchAction
{
	/**
	 * �˵���������
	 * */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{   
		String pPk = (String) request.getSession().getAttribute("pPk");
		RoleEntity roleEntity = RoleCache.getByPpk(pPk);
		
		String menu_id = request.getParameter("menu_id");
		TaskSubService taskService = new TaskSubService();
		PropUseEffect propUseEffect = new PropUseEffect();
		MenuService menuService = new MenuService();
		String hint = null;
		OperateMenuVO menu = menuService.getMenuById(Integer.parseInt(menu_id));
		if(menu != null && menu.getMenuOperate1()!=null && !menu.getMenuOperate1().equals("")){
			String rating = menu.getMenuOperate1();//�ȼ���������
			String title = menu.getMenuOperate3();//�ȼ���������
			String grade[] = rating.split(",");
				if(roleEntity.getBasicInfo().getGrade() < Integer.parseInt(grade[0])){
					hint = "���ĵȼ���û�дﵽ";
					request.setAttribute("propUseEffect", propUseEffect);
					request.setAttribute("hint", hint);
					return mapping.findForward("menutouchtaskpage");
				} 
				if(roleEntity.getBasicInfo().getGrade() > Integer.parseInt(grade[1])){
					hint = "���ĵȼ��Ѿ���������";
					request.setAttribute("propUseEffect", propUseEffect);
					request.setAttribute("hint", hint);
					return mapping.findForward("menutouchtaskpage");
				}
				
				if(roleEntity.getTitleSet().isHaveByTitleStr(title)==false){
					hint = "�������������㣬���ܽӸ�����";
					request.setAttribute("propUseEffect", propUseEffect);
					request.setAttribute("hint", hint);
					return mapping.findForward("menutouchtaskpage");
				}
				if(roleEntity.getBasicInfo().getWrapSpare() == 0){
					hint = "���İ�������,���ܽӸ�����";
					request.setAttribute("propUseEffect", propUseEffect);
					request.setAttribute("hint", hint);
					return mapping.findForward("menutouchtaskpage");
				}
		}else{
			hint = "���ݴ���";
			request.setAttribute("propUseEffect", propUseEffect);
			request.setAttribute("hint", hint);
			return mapping.findForward("menutouchtaskpage");
		}
		
		
		int task_type = 2;//�������� 1 ���ߴ������� 2 �˵��������� 
		// ��������
		hint = taskService.accectTaskFromList(menu,roleEntity,Integer.parseInt(menu_id), propUseEffect,task_type);

		 
		request.setAttribute("propUseEffect", propUseEffect);
		request.setAttribute("hint", hint);
		return mapping.findForward("menutouchtaskpage"); 
	} 
}
