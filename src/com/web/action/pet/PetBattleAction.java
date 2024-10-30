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
 *功能:宠物取消和参加战斗
 * @author 侯浩军
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
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		PetService petService = new PetService();
		
		//得到宠物基本信息
		PetInfoDao petInfoDao = new PetInfoDao();
		PetInfoVO pet = petInfoDao.getPet(Integer.parseInt(petPk));
		
		TimeShow timeShow = new TimeShow();
		//取消战斗
		if(Integer.parseInt(petIsBring) == 0){
			//int petpk = dao.getPetPk(userTempBean.getPPk());
			String pp = null ;
			roleInfo.getRolePetInfo().setPetNextTime(pp);
			petService.petIsBring(Integer.parseInt(pPk), Integer.parseInt(petPk), Integer.parseInt(petIsBring));
		} else if(Integer.parseInt(petIsBring) == 1){
		//参加战斗
		
			String hint = null;
			int pet_longe = petService.pet_longess(Integer.parseInt(petPk));
			if(pet.getPetGrade() > (roleInfo.getBasicInfo().getGrade()+10)){
				hint = "您的宠物等级过高";
				request.setAttribute("hint", hint);
				return mapping.findForward("success");
			}else if(pet.getPetFatigue() < 10){
				hint = "您的宠物体力不足10点";
				request.setAttribute("hint", hint);
				return mapping.findForward("success");
			}else  if(pet_longe == 0){
				hint = "您的宠物寿命为零 不能在出战了！";
				request.setAttribute("hint", hint);
				return mapping.findForward("success");  
			}else{
				//将当前时间放入
				int times = 5;// 两个小时
				roleInfo.getRolePetInfo().setPetAddTime(Time);
				String ss = timeShow.time(times);
				String dd = ss.replaceAll("-", "");
				String ff = dd.replaceAll(" ", "");
				String qq = ff.replaceAll(":", "");
				roleInfo.getRolePetInfo().setPetNextTime(qq);
				//将5分钟后的时间放入
				petService.petIsBring(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(petPk), Integer.parseInt(petIsBring));	
			}	
		}
		request.setAttribute("mapid", mapid);
		request.setAttribute("jumpterm", jumpterm);
		return mapping.findForward("success");
	}
}
