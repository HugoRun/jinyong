/**
 * 
 */
package com.web.action.pet;
 
 
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.TimeShow;
import com.ls.ben.dao.info.pet.PetInfoDao;
import com.ls.ben.vo.info.pet.PetInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.pet.PetService;
import com.ls.web.service.player.RoleService;
 

/**
 *
 *����:����ȡ���Ͳμ�ս��
 * @author ��ƾ�
 * 
 */
public class PetBattleAction extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{   
		RoleService roleService = new RoleService();
		 RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());	
		String pPk = (String) request.getSession().getAttribute("pPk");
		
		String petPk = request.getParameter("petPk");
		String petIsBring = request.getParameter("petIsBring");
		String mapid = request.getParameter("mapid"); 
		String jumpterm = request.getParameter("jumpterm"); 
		//String petGrade = request.getParameter("petGrade"); 
		//String petFatigue = request.getParameter("petFatigue"); 
		// ����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		PetService petService = new PetService();
		
		//�õ����������Ϣ
		PetInfoDao petInfoDao = new PetInfoDao();
		PetInfoVO pet = petInfoDao.getPet(Integer.parseInt(petPk));
		
		TimeShow timeShow = new TimeShow();
		//ȡ��ս��
		if(Integer.parseInt(petIsBring) == 0){
			//int petpk = dao.getPetPk(userTempBean.getPPk());
			String pp = null ;
			roleInfo.getRolePetInfo().setPetNextTime(pp);
			petService.petIsBring(Integer.parseInt(pPk), Integer.parseInt(petPk), Integer.parseInt(petIsBring));
		} else if(Integer.parseInt(petIsBring) == 1){
		//�μ�ս��
		
			String hint = null;
			int pet_longe = petService.pet_longess(Integer.parseInt(petPk));
			if(pet.getPetGrade() > (roleInfo.getBasicInfo().getGrade()+10)){
				hint = "���ĳ���ȼ�����";
				request.setAttribute("hint", hint);
				return mapping.findForward("success");
			}else if(pet.getPetFatigue() < 10){
				hint = "���ĳ�����������10��";
				request.setAttribute("hint", hint);
				return mapping.findForward("success");
			}else  if(pet_longe == 0){
				hint = "���ĳ�������Ϊ�� �����ڳ�ս�ˣ�";
				request.setAttribute("hint", hint);
				return mapping.findForward("success");  
			}else{
				//����ǰʱ�����
				int times = 5;// ����Сʱ
				roleInfo.getRolePetInfo().setPetAddTime(Time);
				String ss = timeShow.time(times);
				String dd = ss.replaceAll("-", "");
				String ff = dd.replaceAll(" ", "");
				String qq = ff.replaceAll(":", "");
				roleInfo.getRolePetInfo().setPetNextTime(qq);
				//��5���Ӻ��ʱ�����
				petService.petIsBring(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(petPk), Integer.parseInt(petIsBring));	
			}	
		}
		request.setAttribute("mapid", mapid);
		request.setAttribute("jumpterm", jumpterm);
		return mapping.findForward("success");
	}
}
