package com.web.service.communion;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.ben.dao.TimeShow;
import com.ben.vo.communion.CommunionVO;
import com.ben.vo.communion.CommunionVOPage;
import com.ls.ben.cache.dynamic.manual.chat.ChatInfoCahe;
import com.ls.model.user.RoleEntity;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.StringUtil;

/**
 * 首页聊天
 * 
 * @author 侯浩军 11:13:44 AM
 */
public class CommunionService
{
	Logger logger = Logger.getLogger("log.service");
 
	
	/**
	 * 玩家离线时，清除聊天消息
	 * @param p_pk
	 */
	public void clearChatInfos( int p_pk )
	{
		String publicChat = "1";//公共
		String campChat = "2";//阵营
		String groupChat = "3";//组队
		String tongChat = "4";//帮派
		String privatelyType = "5";//私底下 
		removeChat(p_pk, publicChat);
		removeChat(p_pk, campChat);
		removeChat(p_pk, groupChat);
		removeChat(p_pk, tongChat);
		removeChat(p_pk, privatelyType);
	}
	
	/**
	 * 首页聊天
	 */
	public List<CommunionVOPage> CommunionList(RoleEntity roleInfo)
	{ 
		String pPk = roleInfo.getBasicInfo().getPPk() + "";
		int zheng = roleInfo.getBasicInfo().getPRace();// 得到阵营
		int dui = roleInfo.getStateInfo().getGroup_id();// 得到队伍
		int bang = roleInfo.getBasicInfo().getFId();// 得到帮派
		
		String publicChat = "1";//公共
		String campChat = "2";//阵营
		String groupChat = "3";//组队
		String tongChat = "4";//帮派
		String privatelyType = "5";//私底下 
		
		TimeShow timeShow = new TimeShow();
		int times = 2;// 2分钟
		String t_begin_time = timeShow.Minutes(times);//2分钟之前
		String t_end_time = timeShow.time(times);// 2分钟之后 
		
		List gongongList = this.getPublicChatInfoList(publicChat); 
		List zhengList = this.getCampChatInfoList(zheng, campChat);
		List duiList = this.getGroupChatInfoList(dui, groupChat);
		List bangList = this.getTongChatInfoList(bang, tongChat);
		List siList = this.getsrivatelyChatInfoList(Integer.parseInt(pPk), privatelyType);
		 
		List<CommunionVOPage> titleList = new ArrayList<CommunionVOPage>();
		List<CommunionVOPage> listArrayList = new ArrayList<CommunionVOPage>();

		Date begin = DateUtil.strToDate(t_begin_time); 
		Date end = DateUtil.strToDate(t_end_time); 
		long begins = begin.getTime();
		long ends = end.getTime(); 
		
		CommunionVOPage communionVOPage = null; 
		// 通过时间吧所有频道的聊天记录全部查出来
		if (siList != null && siList.size() != 0)
		{
			for (int si = 0; si < siList.size(); si++)
			{
				CommunionVO communionVO = (CommunionVO) siList.get(si); 
				communionVOPage = new CommunionVOPage();
				Date time = DateUtil.strToDate(communionVO.getCreateTime()); 
				long timesd = time.getTime();
				// [私]
				if(roleInfo.getSettingInfo().getSecretChat() == 1){
				if (begins < timesd && timesd < ends)
				{  
					if(communionVO.getPPk() != 0){
						communionVOPage.setPPk(communionVO.getPPk());
					}
					if (communionVO.getPName() != null)
					{ 
						if(communionVO.getPName().equals(roleInfo.getBasicInfo().getName())){
							communionVOPage.setPName("★★你对" + StringUtil.isoToGBK(communionVO.getPNameBy()) + "说:");
						}else{
							communionVOPage.setPName("★★" + StringUtil.isoToGBK(communionVO.getPName()) + "对你说:");
						}
						
					}
					if (communionVO.getCTitle() != null)
					{
						communionVOPage.setCTitle(StringUtil.isoToGBK(communionVO.getCTitle()));
					}
					titleList.add(communionVOPage);
					if(titleList.size() == 2){
						break;
					}
				}
				}
			}
			
		}
		//公共
		if(gongongList != null && gongongList.size() != 0){
			for (int gong = 0; gong < gongongList.size(); gong++)
			{
				CommunionVO communionVO = (CommunionVO) gongongList.get(gong);
				communionVOPage = new CommunionVOPage();
				
				Date time = DateUtil.strToDate(communionVO.getCreateTime()); 
				long timesd = time.getTime();
				
				if(roleInfo.getSettingInfo().getPublicChat() == 1){
				if (begins < timesd && timesd < ends)
				{  
					if(communionVO.getPPk() != 0){
						communionVOPage.setPPk(communionVO.getPPk());
					}
					if(communionVO.getPName()!=null){
						communionVOPage.setPName("[公]"+ StringUtil.isoToGBK(communionVO.getPName())+":");
					}
					if(communionVO.getCTitle()!=null){
						communionVOPage.setCTitle(StringUtil.isoToGBK(communionVO.getCTitle()));
					}
					titleList.add(communionVOPage);
				}
				}
			} 
		}
		
		//阵
		if(zhengList != null && zhengList.size() != 0){
			for (int zheny = 0; zheny < zhengList.size(); zheny++)
			{
				CommunionVO communionVO = (CommunionVO) zhengList.get(zheny);
				communionVOPage = new CommunionVOPage(); 
				
				Date time = DateUtil.strToDate(communionVO.getCreateTime()); 
				long timesd = time.getTime();
				if(roleInfo.getSettingInfo().getCampChat() == 1){
				if (begins < timesd && timesd < ends)
				{  
					if(communionVO.getPPk() != 0){
						communionVOPage.setPPk(communionVO.getPPk());
					}
					if(communionVO.getPName()!=null){
						communionVOPage.setPName("[种]"+ StringUtil.isoToGBK(communionVO.getPName())+":");
					}
					if(communionVO.getCTitle()!=null){
						communionVOPage.setCTitle(StringUtil.isoToGBK(communionVO.getCTitle()));
					}
					titleList.add(communionVOPage);
				}
				}
			}
		}
		
		//队
		if(duiList != null && duiList.size() != 0){
			for (int diuw = 0; diuw < duiList.size(); diuw++)
			{
				CommunionVO communionVO = (CommunionVO) duiList.get(diuw);
				communionVOPage = new CommunionVOPage(); 
				Date time = DateUtil.strToDate(communionVO.getCreateTime()); 
				long timesd = time.getTime();
				if(roleInfo.getSettingInfo().getDuiwuChat() == 1){
				if (begins < timesd && timesd < ends && roleInfo.getStateInfo().getGroup_id() !=-1 && roleInfo.getStateInfo().getGroup_id()==communionVO.getCDui())
				{  
					if(communionVO.getPPk() != 0){
						communionVOPage.setPPk(communionVO.getPPk());
					}
					if(communionVO.getPName()!=null){
						communionVOPage.setPName("[队]"+ StringUtil.isoToGBK(communionVO.getPName())+":");
					}
					if(communionVO.getCTitle()!=null){
						communionVOPage.setCTitle(StringUtil.isoToGBK(communionVO.getCTitle()));
					}
					titleList.add(communionVOPage);
				}
				}
			}
		}
		
		//帮
		if(bangList != null && bangList.size() != 0){
			for (int bangp = 0; bangp < bangList.size(); bangp++)
			{
				CommunionVO communionVO = (CommunionVO) bangList.get(bangp);
				communionVOPage = new CommunionVOPage(); 
				Date time = DateUtil.strToDate(communionVO.getCreateTime()); 
				long timesd = time.getTime(); 
				if(roleInfo.getSettingInfo().getTongChat() == 1 ){
				if (begins < timesd && timesd < ends)
				{  
					if(communionVO.getPPk() != 0){
						communionVOPage.setPPk(communionVO.getPPk());
					}
					if(communionVO.getPName()!=null){
						communionVOPage.setPName("[氏]"+ StringUtil.isoToGBK(communionVO.getPName())+":");
					}
					if(communionVO.getCTitle()!=null){
						communionVOPage.setCTitle(StringUtil.isoToGBK(communionVO.getCTitle()));
					}
					titleList.add(communionVOPage);
				}
				}
			}
		}
		listArrayList.addAll(titleList);
		// listArrayList.addAll(listWhispers); 
		return listArrayList;//CommunionList(listArrayList);
	} 
	public List CommunionList(List list)
	{
		List ss = new ArrayList(); 
		ListIterator li = list.listIterator();
		while(li.hasNext()){
			Object ob = li.next();
			li.set(ob);
		}
		while(li.hasPrevious()){
			Object ob = li.previous();
			ss.add(ob);
		}
		return ss;
	}
	
	/**
	 * 查询显示最近密语过的7个玩家
	 * 
	 * @param pPk
	 * @param type
	 * @return
	 */
	public List getOtherWhisper(int pPk, int type)
	{
		List new_list = new ArrayList();
		List list =  getPublicChatInfoList(type+"");
		String user_list = "";
		if(list.size() > 7){
			for(int i = 0 ; i < 8 ; i++){
				CommunionVO vo = (CommunionVO) list.get(i);
				if(vo.getPPk() == pPk){
					if(user_list.indexOf(vo.getPPkBy()+"") == -1){
						new_list.add(vo);
					}
					user_list += vo.getPPkBy()+",";
				} 
			}
			return new_list;
		}else{
			return list;
		}  
	}

	/**
	 * 得到公共聊天记录
	 * 
	 * @param p_pk
	 * @param map_id
	 * @return
	 */
	public List getPublicChatInfoList(String chatType)
	{
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		List ss = new ArrayList();
		List result = null;
		LinkedHashSet role_list = publicChatInfoCahe.getPublicChatInfo(chatType);
		LinkedHashSet new_role_list = (LinkedHashSet) role_list.clone();
		result = new ArrayList(new_role_list); 
		ListIterator li = result.listIterator();
		while(li.hasNext()){
			Object ob = li.next();
			li.set(ob);
		}
		while(li.hasPrevious()){
			Object ob = li.previous();
			ss.add(ob);
		}
		return ss;
	}
	
	/**
	 * 清除缓存垃圾
	 * @param p_pk
	 * @param chatType
	 */
	public void removeChat(int p_pk,String chatType){
		
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		publicChatInfoCahe.removeChat(p_pk, chatType);
	}

	/**
	 * 得到阵营聊天记录
	 * 
	 * @param p_pk
	 * @param map_id
	 * @return
	 */
	public List getCampChatInfoList(int infoCamp,String chatType)
	{
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		List list = new ArrayList();
		List result = null;
		LinkedHashSet role_list = publicChatInfoCahe.getPublicChatInfo(chatType);
		LinkedHashSet new_role_list = (LinkedHashSet) role_list.clone();
		result = new ArrayList(new_role_list);
		for(int i = 0 ; i < result.size() ; i++){
			CommunionVO vo = (CommunionVO) result.get(i);
			if(infoCamp == vo.getCZhen()){
				list.add(vo);
			}
			
		}
		List ss = new ArrayList();
		ListIterator li = list.listIterator();
		while(li.hasNext()){
			Object ob = li.next();
			li.set(ob);
		}
		while(li.hasPrevious()){
			Object ob = li.previous();
			ss.add(ob);
		}
		return ss;
	}
	
	/**
	 * 得到组队聊天记录
	 * 
	 * @param p_pk
	 * @param map_id
	 * @return
	 */
	public List getGroupChatInfoList(int infoGroup_id,String chatType)
	{
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		List list = new ArrayList();
		List result = null;
		LinkedHashSet role_list = publicChatInfoCahe.getPublicChatInfo(chatType);
		LinkedHashSet new_role_list = (LinkedHashSet) role_list.clone();
		result = new ArrayList(new_role_list);
		for(int i = 0 ; i < result.size() ; i++){
			CommunionVO vo = (CommunionVO) result.get(i);
			if(infoGroup_id == vo.getCDui()){
				list.add(vo);
			}
		}
		List ss = new ArrayList();
		ListIterator li = list.listIterator();
		while(li.hasNext()){
			Object ob = li.next();
			li.set(ob);
		}
		while(li.hasPrevious()){
			Object ob = li.previous();
			ss.add(ob);
		}
		return ss;
	}
	 
	/**
	 * 得到帮会聊天记录 
	 * @param p_pk
	 * @param map_id
	 * @return
	 */
	public List getTongChatInfoList(int infoTongId,String chatType)
	{
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		List list = new ArrayList();
		List result = null;
		LinkedHashSet role_list = publicChatInfoCahe.getPublicChatInfo(chatType);
		LinkedHashSet new_role_list = (LinkedHashSet) role_list.clone();
		result = new ArrayList(new_role_list);
		for(int i = 0 ; i < result.size() ; i++){
			CommunionVO vo = (CommunionVO) result.get(i);
			if(infoTongId == vo.getCBang()){
				list.add(vo);
			}
		}
		List ss = new ArrayList();
		ListIterator li = list.listIterator();
		while(li.hasNext()){
			Object ob = li.next();
			li.set(ob);
		}
		while(li.hasPrevious()){
			Object ob = li.previous();
			ss.add(ob);
		}
		return ss;
	}
	
	/**
	 * 得到私底下聊天记录 
	 * @param p_pk
	 * @param map_id
	 * @return
	 */
	public List getsrivatelyChatInfoList(int pPk,String chatType)
	{
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		List list = new ArrayList();
		List result = null;
		LinkedHashSet role_list = publicChatInfoCahe.getPublicChatInfo(chatType);
		LinkedHashSet new_role_list = (LinkedHashSet) role_list.clone();
		result = new ArrayList(new_role_list);
		for(int i = 0 ; i < result.size() ; i++){
			CommunionVO vo = (CommunionVO) result.get(i);
			if(vo.getPPk() == pPk || vo.getPPkBy() == pPk){
				list.add(vo);
			}
		}
		List ss = new ArrayList();
		ListIterator li = list.listIterator();
		while(li.hasNext()){
			Object ob = li.next();
			li.set(ob);
		}
		while(li.hasPrevious()){
			Object ob = li.previous();
			ss.add(ob);
		}
		return ss;
	}
}
