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
	 * 获取道具的图片数据
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
	 * 获得道具图片
	 * @param prop_id
	 * @return
	 */
	private String getPropPicStr(int prop_id)
	{
		PropVO propVO = PropCache.getPropById(prop_id);
		
		if (propVO == null) {
			logger.info("获得道具图片时出错=");
			return "";
		}
			
		return propVO.getPropPic();
	}

	/**
	 * 获取菜单(即npc人物)的图片数据
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
	 * 获取npc怪物图片数据
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
	 * 获取Pet图片数据
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
	 * 增加判断语句,带空格
	 */
	public String addJudgeStatement(String str){
		StringBuffer returnstr = new StringBuffer();;
		if(!str.equals("")){
			returnstr.append("<img alt='sss' src='").append(GameConfig.getGameUrl()).append("/image/npc/").append(str).append(".png").append("' /> ");
			//加空格
			//returnstr.append("<br/>");
		}
		logger.info("输出img="+returnstr.toString());
		return returnstr.toString();
	}

	/**
	 * 增加判断语句，不带空格
	 */
	public String addJudgeStatementWithoutBr(String str){
		StringBuffer returnstr = new StringBuffer();;
		if(!str.equals("")){
			returnstr.append("<img alt='sss' src='").append(GameConfig.getGameUrl()).append(str).append("' /> ");
			
		}
		logger.info("路径为="+str+"输出img="+returnstr.toString());
		return returnstr.toString();
	}
	/**
	 * 获取玩家形象图片
	 * @param pk
	 * @return
	 */
	public String getPlayerPicStr(RoleEntity roleInfo,int p_pk)
	{
		if(roleInfo == null){
			logger.debug("参数为空");
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
		logger.debug("形象图片设置="+settingInfo.getPersonPic()+"图片路径="+playerPic);
		return addJudgeStatement(playerPic);
	}
	
	/**
	 * 获取其他玩家形象图片
	 * @param roleInfo      自己的角色信息
	 * @param bPpk			bPpk要查看的角色id
	 * @return
	 */
	public String getPlayerPicStr(RoleEntity roleInfo,String bPpk)
	{
		if(roleInfo == null || bPpk == null || bPpk.equals("")){
			logger.debug("参数为空");
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
