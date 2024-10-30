package com.web.service.task;
 
import java.util.List;

import com.ben.dao.pet.PetDAO;
import com.ben.dao.petinfo.PetInfoDAO; 
import com.ben.vo.petinfo.PetInfoVO;
import com.ls.pub.util.StringUtil;

/**
 * 功能:宠物任务
 * 
 * @author 侯浩军 11:13:44 AM
 */
public class TaskServicePet
{ 
	/**
	 * 查看是否有任务需要的宠物
	 */
	public String getTaskPet(int petid, int petNumber, int pPk)
	{
		String hint = null;
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		List<PetInfoVO> list = petInfoDAO.getPetInfoByTask(petid, pPk);
		if(list.size() == 0){
			hint = "系统只收取没有技能的宠物";
			return hint;
		}
		if (list.size() < petNumber)
		{
		   hint = "您的宠物数量不够";
		   return hint;
		}
		
		return hint;
	}
	/**
	 * 删除任务宠物
	 * */
	public void getTaskPetDelete(int pPk,int petid){
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		//通过petid 查出来宠物的名称 然后通过宠物的名称
		PetDAO petDAO = new PetDAO(); 
		String petName = StringUtil.isoToGBK(petDAO.getPetName(petid));//获得后台PET表中宠物的名称 
		petInfoDAO.getDeltePetId(petid, pPk,petName);
	}
}
