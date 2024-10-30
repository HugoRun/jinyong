package com.ls.model.fight;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.model.user.UserBase;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.UMsgService;
import com.pm.service.systemInfo.SystemInfoService;
import com.pm.vo.sysInfo.SystemInfoVO;

/**
 * @author ls
 * PK状态
 */
public class PKState extends UserBase
{
	//自己主动攻击的玩家id
	private int start_fight_ppk = -1;
	
	//其他攻击自己的玩家,最多5个人打一个人
	private Queue<Integer> other_list = new LinkedList<Integer>();
	
	//PK时看到的系统消息
	private List<SystemInfoVO> sys_info_list = new ArrayList<SystemInfoVO>(10);
	
	public PKState(int pk)
	{
		super(pk);
	}
	
	/**
	 * 离线通知其他人
	 */
	public void outlineNotify()
	{
		start_fight_ppk = -1;
		sys_info_list.clear();
		
		//通知其他人
		do
		{
			Integer other_ppk = other_list.poll();
			if(other_ppk==null)
			{
				break;
			}
			RoleEntity other = RoleService.getRoleInfoById(other_ppk);
			notifySelfOutline(other);
		}while (other_list.size()>0);
	}
	
	/**
	 * 攻击自己的其他玩家数量
	 * @return
	 */
	public int getOtherNum()
	{
		return other_list.size();
	}
	
	/**
	 * 得到攻击自己人的描述
	 * @return
	 */
	public String getWarningDes()
	{
		if(other_list.size()<2)
		{
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		for( int i=1;i<other_list.size();i++)
		{
			Integer other_ppk = (Integer) ((LinkedList<Integer>)other_list).get(i);
			RoleEntity other = RoleService.getRoleInfoById(other_ppk);
			sb.append(other.getName());
			if( i!=other_list.size()-1)
			{
				sb.append(",");
			}
		}
		sb.append("在攻击您!<br/>");
		return sb.toString();
	}
	
	/**
	 * 发动攻击
	 */
	public String startAttack( RoleEntity other )
	{
		if( other==null || other.isOnline()==false )
		{
			return "对方不在线";
		}
		
		if( other.getStateInfo().getCurState()!=PlayerState.GENERAL && other.getStateInfo().getCurState()!=PlayerState.PKFIGHT  )
		{
			return "对方繁忙";
		}
		
		//攻击一个人的数量最多5个人
		if( other.getPKState().getOtherNum()>=5 )
		{
			return "不能攻击对方";
		}
		
		RoleEntity me = this.getRoleEntity();
		
		if( !other.getBasicInfo().getSceneInfo().getSceneKen().equals(me.getBasicInfo().getSceneInfo().getSceneKen()))
		{
			return other.getName()+"已离开你的视野";
		}
		
		put(other.getPPk());
		
		start_fight_ppk = other.getPPk();
		
		other.getPKState().attackSelf(me);
		return null;
	}
	
	/**
	 * 是否是主动攻击玩家other_ppk
	 * @return
	 */
	public boolean isZhudongAttackOther( int other_ppk )
	{
		if( this.start_fight_ppk!=-1 && other_ppk == this.start_fight_ppk)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void put(Integer pPk)
	{
		if( other_list.contains(pPk)==false )
		{
			other_list.add(pPk);
		}
	}
	
	/**
	 * other攻击自己
	 */
	public String attackSelf(RoleEntity other)
	{
		if( getOtherNum()==0 )
		{
			//如果被攻击的玩家不在战斗状态则初始化start_fight_ppk
			this.start_fight_ppk=-1;
		}
		put(other.getPPk());
		
		return null;
	}
	
	/**
	 * 得到正在战斗的对手
	 */
	public RoleEntity getOtherOnFight()
	{
		RoleEntity other = null;
		//判断是否还有人攻击自己
		if( other_list.size()>0 )
		{
			//如果还有人攻击自己
			do
			{
				int other_ppk = other_list.peek();
				other = RoleService.getRoleInfoById(other_ppk);
				if( other!=null )
				{
					//判断other的状态是否可以攻击
					if( true )
					{
						break;
					}
				}
			}
			while(other_list.size()>0);
		}
		return other;
	}
	
	
	/**
	 * 移除其他攻击自己的玩家
	 */
	private void removeOther(int other_ppk)
	{
		other_list.remove(other_ppk);
	}
	
	
	/**
	 * 通知其他人自己离线
	 */
	private void notifySelfOutline(RoleEntity other)
	{
		RoleEntity me = this.getRoleEntity();
		me.getStateInfo().setCurState(PlayerState.GENERAL);//初始化状态
		
		//***********************通知杀人者
		StringBuffer hint = new StringBuffer();
		
		hint.append(me.getName()).append("已离线").append("<br/>");
		
		if( other.getPKState().getOtherNum()>0 )
		{
			//发送系统消息通知
			other.getPKState().addSysInfo(hint.toString());
		}
		else
		{
			other.getStateInfo().setCurState(PlayerState.GENERAL);//初始化状态
			//发送弹出式消息通知
			SystemInfoService systemInfoService = new SystemInfoService();
			systemInfoService.insertSystemInfoBySystem(other.getPPk(),hint.toString());
		}
		this.removeOther(other.getPPk());
	}
	
	/**
	 * 通知其他人自己死亡
	 * @param murderer      杀死自己的玩家（凶手）
	 * @param winerDes      增加胜利描述
	 */
	public void notifySelfDead(RoleEntity murderer,String winerDes)
	{
		RoleEntity me = this.getRoleEntity();
		me.getStateInfo().setCurState(PlayerState.GENERAL);//初始化状态
		
		//***********************通知杀人者
		murderer.getPKState().removeOther(me.getPPk());
		StringBuffer hint = new StringBuffer();
		
		hint.append("你杀死了").append(me.getName()).append("<br/>").append(winerDes);
		
		if( murderer.getPKState().getOtherNum()>0 )
		{
			//发送系统消息通知
			murderer.getPKState().addSysInfo(hint.toString());
		}
		else
		{
			murderer.getStateInfo().setCurState(PlayerState.GENERAL);//初始化状态
			//发送弹出式消息通知
			UMsgService uMsgService = new UMsgService();
			UMessageInfoVO msgInfo = new UMessageInfoVO();

			msgInfo.setMsgOperate2(murderer.getPPk()+"");
			msgInfo.setMsgType(PopUpMsgType.NOTIFY_KILL_OTHER);
			
			msgInfo.setPPk(murderer.getPPk());
			msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_PK_FIRST);
			msgInfo.setMsgOperate1(hint.toString());
			uMsgService.sendPopUpMsg(msgInfo);
		}
		this.removeOther(murderer.getPPk());
		
		//通知其他人
		notifyOtherSelfDead(murderer);
	}
	
	/**
	 * 通知其他人自己死亡(除了凶手)
	 * @param murderer      杀死自己的玩家（凶手）
	 */
	private void notifyOtherSelfDead(RoleEntity murderer)
	{
		RoleEntity me = this.getRoleEntity();
		
		//通知其他人
		do
		{
			Integer other_ppk = other_list.poll();
			if( other_ppk==null || murderer.getPPk()==other_ppk )
			{
				continue;
			}
			RoleEntity other = RoleService.getRoleInfoById(other_ppk);
			other.getPKState().notifyOneOtherDead(me,murderer);
		}while (other_list.size()>0);
	}
	
	
	
	/**
	 * 通知其他人自己死亡
	 * @param dead
	 */
	private void notifyOneOtherDead(RoleEntity dead,RoleEntity murderer)
	{
		RoleEntity me = this.getRoleEntity();
		this.removeOther(dead.getPPk());
		
		StringBuffer hint = new StringBuffer();
		hint.append(dead.getName()).append("被").append(murderer.getName()).append("杀死了<br/>");
		
		
		if( me.getPKState().getOtherNum()>0 )
		{
			//发送系统消息通知
			this.addSysInfo(hint.toString());
		}
		else
		{
			me.getStateInfo().setCurState(PlayerState.GENERAL);//初始化状态
			//发送弹出式消息通知
			UMsgService uMsgService = new UMsgService();
			UMessageInfoVO msgInfo = new UMessageInfoVO();
			msgInfo.setMsgType(PopUpMsgType.NOTIFY_OTHER_DEAD);
			msgInfo.setPPk(me.getPPk());
			msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_PK_FIRST);
			msgInfo.setMsgOperate1(hint.toString());
			uMsgService.sendPopUpMsg(msgInfo);
		}
	}
	
	/**
	 * 通知其他人自己杀人
	 * @param dead      杀死自己的玩家（凶手）
	 * @param deadDes      死亡描述
	 */
	public void notifySelfKill(RoleEntity dead,String deadDes)
	{
		RoleEntity me = this.getRoleEntity();
		
		me.getStateInfo().setCurState(PlayerState.GENERAL);//初始化状态
		dead.getStateInfo().setCurState(PlayerState.GENERAL);//初始化状态
		
		UMsgService uMsgService = new UMsgService();
		UMessageInfoVO msgInfo = new UMessageInfoVO();
		
		StringBuffer hint = new StringBuffer();
		//如果被杀
		hint.append(me.getName()).append("把你杀死了").append("<br/>").append(deadDes);
		msgInfo.setMsgOperate2(me.getPPk()+"");
		msgInfo.setMsgType(PopUpMsgType.NOTIFY_SELF_DEAD);
		msgInfo.setPPk(dead.getPPk());
		msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_PK_FIRST);
		msgInfo.setMsgOperate1(hint.toString());
		uMsgService.sendPopUpMsg(msgInfo);
		
		this.removeOther(dead.getPPk());
		
		dead.getPKState().notifyOtherSelfDead(me);//通知其他攻击死者的人
	}
	

	/**
	 * 得到PK时显示的系统消息,消息看过一次就清空
	 * @return
	 */
	public List<SystemInfoVO> getSysInfoList()
	{
		List<SystemInfoVO> temp = new ArrayList<SystemInfoVO>(sys_info_list);
		sys_info_list.clear();
		return temp;
	}

	/**
	 * 增加系统消息
	 * @param content
	 */
	private void addSysInfo(String content)
	{
		SystemInfoVO sys_info = new SystemInfoVO();
		sys_info.setSystemInfo(content);
		sys_info_list.add(sys_info);
	}
}
