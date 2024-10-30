package com.web.service.task;
 
import java.util.List;

import com.ben.dao.pet.PetDAO;
import com.ben.dao.petinfo.PetInfoDAO; 
import com.ben.vo.petinfo.PetInfoVO;
import com.ls.pub.util.StringUtil;

/**
 * ����:��������
 * 
 * @author ��ƾ� 11:13:44 AM
 */
public class TaskServicePet
{ 
	/**
	 * �鿴�Ƿ���������Ҫ�ĳ���
	 */
	public String getTaskPet(int petid, int petNumber, int pPk)
	{
		String hint = null;
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		List<PetInfoVO> list = petInfoDAO.getPetInfoByTask(petid, pPk);
		if(list.size() == 0){
			hint = "ϵͳֻ��ȡû�м��ܵĳ���";
			return hint;
		}
		if (list.size() < petNumber)
		{
		   hint = "���ĳ�����������";
		   return hint;
		}
		
		return hint;
	}
	/**
	 * ɾ���������
	 * */
	public void getTaskPetDelete(int pPk,int petid){
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		//ͨ��petid �������������� Ȼ��ͨ�����������
		PetDAO petDAO = new PetDAO(); 
		String petName = StringUtil.isoToGBK(petDAO.getPetName(petid));//��ú�̨PET���г�������� 
		petInfoDAO.getDeltePetId(petid, pPk,petName);
	}
}
