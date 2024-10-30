package com.pm.service.pic;

import org.apache.log4j.Logger;

import com.ls.ben.cache.staticcache.menu.MenuCache;
import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.ben.cache.staticcache.pet.PetCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.model.user.SettingInfo;
import com.ls.pub.config.GameConfig;

public class PicService
{

	Logger logger =  Logger.getLogger("log.service");
	
	
	/**
	 * ��ȡ���ߵ�ͼƬ����
	 * @param pPk
	 * @param menu_id
	 * @return
	 */
	public String getPropPicStr(RoleEntity roleInfo,int prop_id){
		String picstr = "";
		SettingInfo settingInfo = roleInfo.getSettingInfo();
		if(settingInfo.getGoodsPic()==1 ){
			picstr = getPropPicStr(prop_id);
			if(!picstr.equals("")){
				picstr ="/image/item/prop/"+picstr+".png";
			}
		}
		return addJudgeStatementWithoutBr(picstr);
	}
	
	
	/**
	 * ��õ���ͼƬ
	 * @param prop_id
	 * @return
	 */
	private String getPropPicStr(int prop_id)
	{
		PropVO propVO = PropCache.getPropById(prop_id);
		
		if (propVO == null) {
			logger.info("��õ���ͼƬʱ����=");
			return "";
		}
			
		return propVO.getPropPic();
	}

	/**
	 * ��ȡ�˵�(��npc����)��ͼƬ����
	 * @param pPk
	 * @param menu_id
	 * @return
	 */
	public String getMenuPicStr(RoleEntity roleInfo,int menu_id){
		String picstr = "";
		SettingInfo settingInfo = roleInfo.getSettingInfo();
		if(settingInfo.getNpcPic()==1 ){
			MenuCache menuCache = new MenuCache();
//			PicDao picDao = new PicDao();
			picstr = menuCache.getMenuPicStr(menu_id);
			if(!picstr.equals("")){
				picstr = "/image/npc/"+picstr+".png";
			}
		}
		return addJudgeStatementWithoutBr(picstr);
	}

	/**
	 * ��ȡnpc����ͼƬ����
	 * @param pk
	 * @param npcID
	 * @return
	 */
	public String getNpcPicStr(RoleEntity roleInfo, int npcID)
	{
		String picstr = "";
		SettingInfo settingInfo = roleInfo.getSettingInfo();
		if(settingInfo.getNpcPic()==1){
			picstr = NpcCache.getById(npcID).getPic();
			if(!picstr.equals("")){
				picstr = picstr;
			}
		}
		return addJudgeStatement(picstr);
	}
	
	
	
	/**
	 * ��ȡPetͼƬ����
	 * @param pk
	 * @param npcID
	 * @return
	 */
	public String getPetPicStr(RoleEntity roleInfo, String petPk)
	{
		String picstr = "";
		if(roleInfo!=null){
		SettingInfo settingInfo = roleInfo.getSettingInfo();
		if(settingInfo.getPetPic()==1){
			picstr = PetCache.getPetById(Integer.parseInt(petPk)).getPetImg();
			if(!picstr.equals("")){
				picstr = "/image/npc/"+picstr+".png";
			}
		}
		}
		return addJudgeStatementWithoutBr(picstr);
	}
	
	/**
	 * �����ж����,���ո�
	 */
	public String addJudgeStatement(String str){
		StringBuffer returnstr = new StringBuffer();;
		if(!str.equals("")){
			returnstr.append("<img alt='sss' src='").append(GameConfig.getGameUrl()).append("/image/npc/").append(str).append(".png").append("' /> ");
			//�ӿո�
			//returnstr.append("<br/>");
		}
		logger.info("���img="+returnstr.toString());
		return returnstr.toString();
	}

	/**
	 * �����ж���䣬�����ո�
	 */
	public String addJudgeStatementWithoutBr(String str){
		StringBuffer returnstr = new StringBuffer();;
		if(!str.equals("")){
			returnstr.append("<img alt='sss' src='").append(GameConfig.getGameUrl()).append(str).append("' /> ");
			
		}
		logger.info("·��Ϊ="+str+"���img="+returnstr.toString());
		return returnstr.toString();
	}
	/**
	 * ��ȡ�������ͼƬ
	 * @param pk
	 * @return
	 */
	public String getPlayerPicStr(RoleEntity roleInfo,int p_pk)
	{
		if(roleInfo == null){
			logger.debug("����Ϊ��");
			return "";
		}
		String playerPic = "";
		SettingInfo settingInfo = roleInfo.getSettingInfo();
		if(settingInfo.getPersonPic()==1){
			PartInfoDao infoDao = new PartInfoDao();
			PartInfoVO infovo = infoDao.getPartInfoByID(p_pk);
			if(infovo.getPRace()==1)
			{
				if(infovo.getPSex()==1)
				{
					playerPic="nanyao";
				}
				else
				{
					playerPic="nvyao";
				}
			}
			else
			{
				if(infovo.getPSex()==1)
				{
					playerPic="nanwu";
				}
				else
				{
					playerPic="nvwu";
				}
			}
			}
		logger.debug("����ͼƬ����="+settingInfo.getPersonPic()+"ͼƬ·��="+playerPic);
		return addJudgeStatement(playerPic);
	}
	
	/**
	 * ��ȡ�����������ͼƬ
	 * @param roleInfo      �Լ��Ľ�ɫ��Ϣ
	 * @param bPpk			bPpkҪ�鿴�Ľ�ɫid
	 * @return
	 */
	public String getPlayerPicStr(RoleEntity roleInfo,String bPpk)
	{
		if(roleInfo == null || bPpk == null || bPpk.equals("")){
			logger.debug("����Ϊ��");
			return "";
		}
		String playerPic = "";
		SettingInfo settingInfo = roleInfo.getSettingInfo();
		if(settingInfo.getPersonPic()==1 ){
			PartInfoDao infoDao = new PartInfoDao();
			PartInfoVO infovo = infoDao.getPartInfoByID(Integer.valueOf(bPpk));
			}
		return addJudgeStatement(playerPic);
	}
}
