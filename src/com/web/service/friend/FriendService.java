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
 * ����
 * 
 * @author ��ƾ� 11:13:44 AM
 */
public class FriendService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * ���Ӻ���
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
		logger.info("�� " + pByName + "��Ϊ������");
		return null;
	}

	/**
	 * �жϸ�����Ƿ��Ѿ����Լ��ĺ�����
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
	 * �жϸ�����Ƿ��Ѿ��Ǵﵽ50��������
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
	 * ����List pageno ������˳�� perpage ÿҳ����
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
	 * ����List pageno ������˳�� perpage ÿҳ����
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
	 * ����List pageno ������˳�� perpage ÿҳ����
	 */
	public List<FriendVO> addMyFriend(int pPk)
	{
		
		FriendDAO friendDAO = new FriendDAO();
		List<FriendVO> list = friendDAO.getFriendListAll(pPk);
			
		return list;
	}
	

	/**
	 * ����View
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
	 * ɾ������
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
	 * ���ݺ�������scene�����ҳ�������ڵ�map��
	 * 
	 * @param list
	 */
	public List<FriendVO> getFriendInMapName(List<FriendVO> list)
	{
		if (list == null || list.size() == 0)
		{
			logger.error("�������!");
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

	/** �õ��������� */
	public int getFriendNum(int pPk)
	{
		FriendDAO friendDAO = new FriendDAO();
		List<FriendVO> list = friendDAO.getFriendListAll(pPk, 0, 0);
		
		int i = (list==null?0:list.size());
		return i;
	}
	
	/** �õ�jieyi �������� */
	public int getFriendNum1(int pPk,int relation)
	{
		FriendDAO friendDAO = new FriendDAO();
		List<FriendVO> list = friendDAO.getFriendListAll1(pPk, 0, 0,relation);
		int i = list.size();
		return i;
	}
	
	//����ɹ�����
	public void jieyi(String pPk,String pByPk,int relation){
		FriendDAO friendDAO = new FriendDAO();
		friendDAO.jieyi(pPk, pByPk, relation);
	}
	
	//�鿴�Լ��Ƿ��ѻ�
	public List<FriendVO> isMerry(String pPk){
		return new FriendDAO().isMerry(pPk);
	}
	
	/**�õ����Խ�����*/
	
	public List<FriendVO> getCanMerry(int pPk,int gender,int pageno,int perpage){
		return new FriendDAO().getCanMerry(pPk, gender, pageno, perpage);
	}
	
	/**���Խ��ĺ�������*/
	
	public int getCanMerryCount(int pPk,int gender){
		return new FriendDAO().getCanMerry(pPk, gender, 0, 0).size();
	}
	
	/**
	 * �������ܶ�
	 * @param p_pk
	 * @param fd_pk
	 * @param dear
	 */
	public void addDear(String p_pk,String fd_pk,String f_name,String fd_name,String dear){
		//ͳ����Ҫ
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
	 * ���Ӱ�������
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
	 * ���ߺ�����������List pageno ������˳�� perpage ÿҳ����
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
	
	/** �õ����ߺ������� */
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
	
	//�������ܶ�
	public void addLove(Object p_pk,Object fd_pk,int addlovel){
		new FriendDAO().addLove(p_pk, fd_pk, addlovel);
	}
	
	public List<FriendVO> jieyi(Object p_pk, int relation){
		return new FriendDAO().jieyi(p_pk, relation);
	}
	
	/**
	 * ����ĳ���˵����н����ֵ�
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
	 * ����ĳ���˵����н��������
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
	/** ********���ɾ����ɫ��ʱ��ɾ�����й����ĺ�����Ϣ****** */
	public void removeFriendInfo(int ppk)
	{
		FriendDAO friendDAO = new FriendDAO();
		friendDAO.removeFriendInfo(ppk);
	}
}
