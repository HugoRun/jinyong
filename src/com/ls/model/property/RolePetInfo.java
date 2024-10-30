
package com.ls.model.property;

import com.ben.dao.pet.PetDAO;
import com.ben.dao.petinfo.PetInfoDAO;
import com.ls.ben.dao.info.pet.PetInfoDao;
import com.ls.ben.vo.info.pet.PetInfoVO;

/**
 * ������˳������
 * @author Administrator
 *
 */
public class RolePetInfo
{
	private PetInfoDao petinfoDao ;
	private PetInfoVO petInfovo;
	private String petNextTime;			// ������������ʱ��
	private String petAddTime;
	
	
	public int getPetLong()
	{	
		if( petInfovo==null )
		{
			return -1;
		}
		
		return petInfovo.getPetLonge();
	}


	/**
	 * �������������һ 
	 */
	public void updatePetLong(int pPk) {
		if( petInfovo==null )
			return;
		int pet_longe = getPetLong();
		if( pet_longe>0 )
		{
			petInfovo.setPetLonge(pet_longe-1);
			PetInfoDAO petInfoDAO = new PetInfoDAO();
			petInfoDAO.pet_life(pPk);
		}
	}	
	
	/**
	 * ��ȥ�����һ������
	 */
	public void updatePetFatigue(int pPk)
	{
		if ( petInfovo != null ) {
    		this.petInfovo.setPetFatigue(this.petInfovo.getPetFatigue() - 1);
    		PetDAO petDAO = new PetDAO();
        	petDAO.petFatigue(pPk);
		}
	}


	public String getPetNextTime()
	{
		return petNextTime;
	}


	public void setPetNextTime(String petNextTime)
	{
		this.petNextTime = petNextTime;
	}


	public String getPetAddTime()
	{
		return petAddTime;
	}


	public void setPetAddTime(String petAddTime)
	{
		this.petAddTime = petAddTime;
	}


	/**
	 * ������, ������ʼ������, ������û�в�ս�ĳ���,���ó�ʼ��.
	 * @param p_pk
	 */
	public RolePetInfo(int p_pk)
	{
		petinfoDao = new PetInfoDao();
		petInfovo = petinfoDao.getBringPetByPpk(p_pk);		
	}
	
	
	/**
	 * ��� ս��ʱ�ĳ���
	 * @param 
	 */
	public PetInfoVO getBringPet() {
		return petInfovo;		
	}
	
	/**
	 * 
	 * ��һֻ�������ڲ�ս״̬
	 * @param pet_pk 
	 * @param return ����-1��ʾ�Ѿ��в�ս������,�滻ʧ��,����1��ʾ���ڲ�ս״̬�ɹ�
	 */
	public int bringPet( int pet_pk) {
		if ( petInfovo != null) {
			return -1;
		} else {
			petinfoDao = new PetInfoDao();
			petInfovo = new PetInfoVO();
			petInfovo = petinfoDao.getPet(pet_pk);
			return 1;
		}		
	}
		
	/**
	 * ����ս��������δ��ս״̬
	 * 
	 */
	public void unBringpet () {
		petInfovo = null;
		petNextTime = null;
	}
	
	/**
	 * ����ս��������δ��ս״̬
	 * 
	 */
	public void unBringpetAll () {
		if (petInfovo != null) {
			PetInfoDAO petinfoDao = new PetInfoDAO();
			petinfoDao.petIsBring(petInfovo.getPPk(), petInfovo.getPetPk(), 0);
			
			petInfovo = null;
    		petNextTime = null;
		}
	}

	/**
	 *  ����
	 * @param pet_pk
	 * @param pet_nickname
	 */
	public void updateNickName(int pet_pk, String pet_nickname)
	{
		if ( petInfovo == null) {
			return ;
		} else {
    		// ����������ֵĳ��� ���ǲ�ս����ֻ,�Ǿ� �����ڴ��еĳ�������
    		if ( petInfovo.getPetPk() == pet_pk) {
    			petInfovo.setPetNickname(pet_nickname);
    		}	
		}
	}


	/**
	 * ������Ϣ����
	 * @param pet_pk
	 * @param p_pk
	 */
	public void initPet(int pet_pk,int p_pk)
	{
	if ( petInfovo == null) {
		return ;
	} else {
		// ���ʹ���� �������صĵ��ߣ� ��ô���������¼���һ��
		if ( petInfovo.getPetPk() == pet_pk) {
			unBringpet();			
			petInfovo = petinfoDao.getBringPetByPpk(p_pk);
		}
		}
	}


	
}