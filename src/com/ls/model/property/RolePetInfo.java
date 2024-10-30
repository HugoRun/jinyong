
package com.ls.model.property;

import com.ben.dao.pet.PetDAO;
import com.ben.dao.petinfo.PetInfoDAO;
import com.ls.ben.dao.info.pet.PetInfoDao;
import com.ls.ben.vo.info.pet.PetInfoVO;

/**
 * 处理个人宠物的类
 * @author Administrator
 *
 */
public class RolePetInfo
{
	private PetInfoDao petinfoDao ;
	private PetInfoVO petInfovo;
	private String petNextTime;			// 宠物消减体力时间
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
	 * 将宠物的寿命减一 
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
	 * 减去宠物的一点体力
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
	 * 构造器, 用来初始化宠物, 如果玩家没有参战的宠物,则不用初始化.
	 * @param p_pk
	 */
	public RolePetInfo(int p_pk)
	{
		petinfoDao = new PetInfoDao();
		petInfovo = petinfoDao.getBringPetByPpk(p_pk);		
	}
	
	
	/**
	 * 获得 战斗时的宠物
	 * @param 
	 */
	public PetInfoVO getBringPet() {
		return petInfovo;		
	}
	
	/**
	 * 
	 * 将一只宠物置于参战状态
	 * @param pet_pk 
	 * @param return 返回-1表示已经有参战宠物了,替换失败,返回1表示置于参战状态成功
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
	 * 将出战宠物置于未出战状态
	 * 
	 */
	public void unBringpet () {
		petInfovo = null;
		petNextTime = null;
	}
	
	/**
	 * 将出战宠物置于未出战状态
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
	 *  更新
	 * @param pet_pk
	 * @param pet_nickname
	 */
	public void updateNickName(int pet_pk, String pet_nickname)
	{
		if ( petInfovo == null) {
			return ;
		} else {
    		// 如果更改名字的宠物 就是参战的这只,那就 更改内存中的宠物名字
    		if ( petInfovo.getPetPk() == pet_pk) {
    			petInfovo.setPetNickname(pet_nickname);
    		}	
		}
	}


	/**
	 * 宠物信息重载
	 * @param pet_pk
	 * @param p_pk
	 */
	public void initPet(int pet_pk,int p_pk)
	{
	if ( petInfovo == null) {
		return ;
	} else {
		// 如果使用了 与宠物相关的道具， 那么将宠物重新加载一次
		if ( petInfovo.getPetPk() == pet_pk) {
			unBringpet();			
			petInfovo = petinfoDao.getBringPetByPpk(p_pk);
		}
		}
	}


	
}