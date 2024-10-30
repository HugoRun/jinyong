package com.web.service.friend;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ben.dao.friend.FriendDAO;
import com.ben.vo.friend.FriendVO;
import com.ls.ben.vo.map.MapVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;

/**
 * 好友
 * 
 * @author 侯浩军 11:13:44 AM
 */
public class FriendService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * 增加好友
	 */
	public String addfriend(int pPk, String pByPk, String pByName, String time)
	{
		RoleService roleService = new RoleService();
		RoleEntity b_role_info = roleService.getRoleInfoById(pByPk);

		int online = 0;
		if (b_role_info != null)
		{
			online = 1;
		}
		FriendDAO friendDAO = new FriendDAO();
		friendDAO.friendAdd(pPk, pByPk, pByName, online, time);
		logger.info("将 " + pByName + "加为好友了");
		return null;
	}

	/**
	 * 判断该玩家是否已经是自己的好友了
	 */
	public boolean whetherfriend(int pPk, String pByPk)
	{
		try
		{
			FriendDAO friendDAO = new FriendDAO();
			if (friendDAO.whetherfriend(pPk, pByPk))
			{
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 判断该玩家是否已经是达到50个好友了
	 */
	public boolean friendupperlimit(int pPk)
	{
		try
		{
			FriendDAO friendDAO = new FriendDAO();
			List<FriendVO> list = friendDAO.getFriendListAll(pPk, 0, 0);
			if (list != null && list.size() != 0)
			{
				if (list.size() == 50)
				{
					return false;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 好友List pageno 数量的顺序 perpage 每页个数
	 */
	public List<FriendVO> listfriend(int pPk, int pageno, int perpage)
	{
		try
		{
			FriendDAO friendDAO = new FriendDAO();
			List<FriendVO> list = friendDAO.getFriendListAll(pPk, pageno,
					perpage);
			if (list != null && list.size() != 0)
			{
				return list;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 好友List pageno 数量的顺序 perpage 每页个数
	 */
	public List<FriendVO> listfriend1(int pPk, int pageno, int perpage,int relation)
	{
		try
		{
			FriendDAO friendDAO = new FriendDAO();
			List<FriendVO> list = friendDAO.getFriendListAll1(pPk, pageno,
					perpage,relation);
			if (list != null && list.size() != 0)
			{
				return list;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 好友List pageno 数量的顺序 perpage 每页个数
	 */
	public List<FriendVO> addMyFriend(int pPk)
	{
		
		FriendDAO friendDAO = new FriendDAO();
		List<FriendVO> list = friendDAO.getFriendListAll(pPk);
			
		return list;
	}
	

	/**
	 * 好友View
	 */
	public FriendVO viewfriend(int pPk, String pByPk)
	{
		try
		{
			FriendDAO friendDAO = new FriendDAO();
			FriendVO friendVO = friendDAO.getFriendView(pPk, pByPk);
			if (friendVO != null)
			{
				return friendVO;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除好友
	 */
	public String deletefriend(int pPk, String pByPk)
	{
		try
		{
			FriendDAO friendDAO = new FriendDAO();
			friendDAO.getDeleteFriend(pPk, pByPk);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据好友所在scene名来找出玩家所在的map名
	 * 
	 * @param list
	 */
	public List<FriendVO> getFriendInMapName(List<FriendVO> list)
	{
		if (list == null || list.size() == 0)
		{
			logger.error("错误参数!");
			return null;
		}
		List<FriendVO> friendlist = new ArrayList<FriendVO>();

		for (int i = 0; i < list.size(); i++)
		{
			FriendVO friendVO = list.get(i);
			int p_map = friendVO.getPMap();
			
			RoomService roomService = new RoomService();
			MapVO map_info = roomService.getMapInfoBySceneId(p_map+"");
			friendVO.setPMapName(map_info.getMapName());
			friendlist.add(friendVO);
		}

		return friendlist;
	}

	/** 得到好友数量 */
	public int getFriendNum(int pPk)
	{
		FriendDAO friendDAO = new FriendDAO();
		List<FriendVO> list = friendDAO.getFriendListAll(pPk, 0, 0);
		
		int i = (list==null?0:list.size());
		return i;
	}
	
	/** 得到jieyi 好友数量 */
	public int getFriendNum1(int pPk,int relation)
	{
		FriendDAO friendDAO = new FriendDAO();
		List<FriendVO> list = friendDAO.getFriendListAll1(pPk, 0, 0,relation);
		int i = list.size();
		return i;
	}
	
	//结义成功处理
	public void jieyi(String pPk,String pByPk,int relation){
		FriendDAO friendDAO = new FriendDAO();
		friendDAO.jieyi(pPk, pByPk, relation);
	}
	
	//查看自己是否已婚
	public List<FriendVO> isMerry(String pPk){
		return new FriendDAO().isMerry(pPk);
	}
	
	/**得到可以结婚的人*/
	
	public List<FriendVO> getCanMerry(int pPk,int gender,int pageno,int perpage){
		return new FriendDAO().getCanMerry(pPk, gender, pageno, perpage);
	}
	
	/**可以结婚的好友数量*/
	
	public int getCanMerryCount(int pPk,int gender){
		return new FriendDAO().getCanMerry(pPk, gender, 0, 0).size();
	}
	
	/**
	 * 增加亲密度
	 * @param p_pk
	 * @param fd_pk
	 * @param dear
	 */
	public void addDear(String p_pk,String fd_pk,String f_name,String fd_name,String dear){
		//统计需要
		new FriendDAO().addDear(p_pk, fd_pk, dear,f_name,fd_name);
	}
	
	public int jieyiCount(String p_pk,String ids,int relation){
		return new FriendDAO().jieyiCount(p_pk, ids, relation);
	}
	
	public List<FriendVO> findFriends(String p_pk,String fp_pks){
		return new FriendDAO().findFriends(p_pk, fp_pks);
	}
	
	public void setExpShare(int p_pk,int fd_pk, int exp){
		new FriendDAO().setExpShare(p_pk, fd_pk, exp);
	}
	
	public List<FriendVO> findCanGetExp(int p_pk,int relation,int start,int count){
		return new FriendDAO().findCanGetExp(p_pk, relation,start,count);
	}
	
	public int findCanGetExpCount(int p_pk,int relation){
		return new FriendDAO().findCanGetExp(p_pk, relation,0,0).size();
	}
	
	public void getExp(int f_pk){
		new FriendDAO().getExp(f_pk);
	}
	
	public List<FriendVO> findLoveDear(){
		return new FriendDAO().findLoveDear();
	}
	
	public void delLoveDear(int p_pk){
		new FriendDAO().delLoveDear(p_pk);
	}
	
	/**
	 * 增加爱情甜蜜
	 * @param p_pk
	 * @param fd_pk
	 * @param fdName
	 * @param love_dear
	 */
	public void addLoveDear(int p_pk,int fd_pk,String fdName,int love_dear,String p_name){
		new FriendDAO().addLoveDear(p_pk, fd_pk, love_dear,fdName,p_name);
	}
	
	public int isFuQi(int p_pk,String fp_pks,int relation){
		return new FriendDAO().isFuQi(p_pk, fp_pks, relation);
	}
	
	public int findLove_dear(int p_pk){
		return new FriendDAO().findLove_dear(p_pk);
	}
	
	
	/**
	 * 在线好友数量好友List pageno 数量的顺序 perpage 每页个数
	 */
	public List<FriendVO> listfriendOnline(int pPk, int pageno, int perpage)
	{
		try
		{
			FriendDAO friendDAO = new FriendDAO();
			List<FriendVO> list = friendDAO.getFriendListAllOnline(pPk, pageno,
					perpage);
			if (list != null && list.size() != 0)
			{
				return list;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/** 得到在线好友数量 */
	public int getFriendNumOnline(int pPk)
	{
		FriendDAO friendDAO = new FriendDAO();
		List<FriendVO> list = friendDAO.getFriendListAllOnline(pPk, 0, 0);
		int i = list.size();
		return i;
	}
	
	
	public FriendVO findById(int id){
		return new FriendDAO().findById(id);
	}
	
	//增加亲密度
	public void addLove(Object p_pk,Object fd_pk,int addlovel){
		new FriendDAO().addLove(p_pk, fd_pk, addlovel);
	}
	
	public List<FriendVO> jieyi(Object p_pk, int relation){
		return new FriendDAO().jieyi(p_pk, relation);
	}
	
	/**
	 * 返回某个人的所有结义兄弟
	 * @param p_pk
	 * @return
	 */
	public String returnjieyilist(int p_pk){
		List jieyilist = jieyi(p_pk, 1);
		String jieyiName = "";
		if(jieyilist != null && jieyilist.size() > 0){
			for(int i = 0 ; i < jieyilist.size() ; i++){
				FriendVO vo = (FriendVO)jieyilist.get(i);
				jieyiName += vo.getFdName()+",";
			}
		}
		return jieyiName;
	}
	/**
	 * 返回某个人的所有结义结婚对象
	 * @param p_pk
	 * @return
	 */
	public String returnjiehun(int p_pk){
		List jiehunlist = jieyi(p_pk, 2);
		String jieyiName = "";
		if(jiehunlist != null && jiehunlist.size() > 0){
			FriendVO vo = (FriendVO)jiehunlist.get(0);
			jieyiName = vo.getFdName();
		}
		return jieyiName;
	}
	/** ********玩家删除角色的时候删除所有关联的好友信息****** */
	public void removeFriendInfo(int ppk)
	{
		FriendDAO friendDAO = new FriendDAO();
		friendDAO.removeFriendInfo(ppk);
	}
}
