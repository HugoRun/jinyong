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
 * PK״̬
 */
public class PKState extends UserBase
{
	//�Լ��������������id
	private int start_fight_ppk = -1;
	
	//���������Լ������,���5���˴�һ����
	private Queue<Integer> other_list = new LinkedList<Integer>();
	
	//PKʱ������ϵͳ��Ϣ
	private List<SystemInfoVO> sys_info_list = new ArrayList<SystemInfoVO>(10);
	
	public PKState(int pk)
	{
		super(pk);
	}
	
	/**
	 * ����֪ͨ������
	 */
	public void outlineNotify()
	{
		start_fight_ppk = -1;
		sys_info_list.clear();
		
		//֪ͨ������
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
	 * �����Լ��������������
	 * @return
	 */
	public int getOtherNum()
	{
		return other_list.size();
	}
	
	/**
	 * �õ������Լ��˵�����
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
		sb.append("�ڹ�����!<br/>");
		return sb.toString();
	}
	
	/**
	 * ��������
	 */
	public String startAttack( RoleEntity other )
	{
		if( other==null || other.isOnline()==false )
		{
			return "�Է�������";
		}
		
		if( other.getStateInfo().getCurState()!=PlayerState.GENERAL && other.getStateInfo().getCurState()!=PlayerState.PKFIGHT  )
		{
			return "�Է���æ";
		}
		
		//����һ���˵��������5����
		if( other.getPKState().getOtherNum()>=5 )
		{
			return "���ܹ����Է�";
		}
		
		RoleEntity me = this.getRoleEntity();
		
		if( !other.getBasicInfo().getSceneInfo().getSceneKen().equals(me.getBasicInfo().getSceneInfo().getSceneKen()))
		{
			return other.getName()+"���뿪�����Ұ";
		}
		
		put(other.getPPk());
		
		start_fight_ppk = other.getPPk();
		
		other.getPKState().attackSelf(me);
		return null;
	}
	
	/**
	 * �Ƿ��������������other_ppk
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
	 * other�����Լ�
	 */
	public String attackSelf(RoleEntity other)
	{
		if( getOtherNum()==0 )
		{
			//�������������Ҳ���ս��״̬���ʼ��start_fight_ppk
			this.start_fight_ppk=-1;
		}
		put(other.getPPk());
		
		return null;
	}
	
	/**
	 * �õ�����ս���Ķ���
	 */
	public RoleEntity getOtherOnFight()
	{
		RoleEntity other = null;
		//�ж��Ƿ����˹����Լ�
		if( other_list.size()>0 )
		{
			//��������˹����Լ�
			do
			{
				int other_ppk = other_list.peek();
				other = RoleService.getRoleInfoById(other_ppk);
				if( other!=null )
				{
					//�ж�other��״̬�Ƿ���Թ���
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
	 * �Ƴ����������Լ������
	 */
	private void removeOther(int other_ppk)
	{
		other_list.remove(other_ppk);
	}
	
	
	/**
	 * ֪ͨ�������Լ�����
	 */
	private void notifySelfOutline(RoleEntity other)
	{
		RoleEntity me = this.getRoleEntity();
		me.getStateInfo().setCurState(PlayerState.GENERAL);//��ʼ��״̬
		
		//***********************֪ͨɱ����
		StringBuffer hint = new StringBuffer();
		
		hint.append(me.getName()).append("������").append("<br/>");
		
		if( other.getPKState().getOtherNum()>0 )
		{
			//����ϵͳ��Ϣ֪ͨ
			other.getPKState().addSysInfo(hint.toString());
		}
		else
		{
			other.getStateInfo().setCurState(PlayerState.GENERAL);//��ʼ��״̬
			//���͵���ʽ��Ϣ֪ͨ
			SystemInfoService systemInfoService = new SystemInfoService();
			systemInfoService.insertSystemInfoBySystem(other.getPPk(),hint.toString());
		}
		this.removeOther(other.getPPk());
	}
	
	/**
	 * ֪ͨ�������Լ�����
	 * @param murderer      ɱ���Լ�����ң����֣�
	 * @param winerDes      ����ʤ������
	 */
	public void notifySelfDead(RoleEntity murderer,String winerDes)
	{
		RoleEntity me = this.getRoleEntity();
		me.getStateInfo().setCurState(PlayerState.GENERAL);//��ʼ��״̬
		
		//***********************֪ͨɱ����
		murderer.getPKState().removeOther(me.getPPk());
		StringBuffer hint = new StringBuffer();
		
		hint.append("��ɱ����").append(me.getName()).append("<br/>").append(winerDes);
		
		if( murderer.getPKState().getOtherNum()>0 )
		{
			//����ϵͳ��Ϣ֪ͨ
			murderer.getPKState().addSysInfo(hint.toString());
		}
		else
		{
			murderer.getStateInfo().setCurState(PlayerState.GENERAL);//��ʼ��״̬
			//���͵���ʽ��Ϣ֪ͨ
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
		
		//֪ͨ������
		notifyOtherSelfDead(murderer);
	}
	
	/**
	 * ֪ͨ�������Լ�����(��������)
	 * @param murderer      ɱ���Լ�����ң����֣�
	 */
	private void notifyOtherSelfDead(RoleEntity murderer)
	{
		RoleEntity me = this.getRoleEntity();
		
		//֪ͨ������
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
	 * ֪ͨ�������Լ�����
	 * @param dead
	 */
	private void notifyOneOtherDead(RoleEntity dead,RoleEntity murderer)
	{
		RoleEntity me = this.getRoleEntity();
		this.removeOther(dead.getPPk());
		
		StringBuffer hint = new StringBuffer();
		hint.append(dead.getName()).append("��").append(murderer.getName()).append("ɱ����<br/>");
		
		
		if( me.getPKState().getOtherNum()>0 )
		{
			//����ϵͳ��Ϣ֪ͨ
			this.addSysInfo(hint.toString());
		}
		else
		{
			me.getStateInfo().setCurState(PlayerState.GENERAL);//��ʼ��״̬
			//���͵���ʽ��Ϣ֪ͨ
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
	 * ֪ͨ�������Լ�ɱ��
	 * @param dead      ɱ���Լ�����ң����֣�
	 * @param deadDes      ��������
	 */
	public void notifySelfKill(RoleEntity dead,String deadDes)
	{
		RoleEntity me = this.getRoleEntity();
		
		me.getStateInfo().setCurState(PlayerState.GENERAL);//��ʼ��״̬
		dead.getStateInfo().setCurState(PlayerState.GENERAL);//��ʼ��״̬
		
		UMsgService uMsgService = new UMsgService();
		UMessageInfoVO msgInfo = new UMessageInfoVO();
		
		StringBuffer hint = new StringBuffer();
		//�����ɱ
		hint.append(me.getName()).append("����ɱ����").append("<br/>").append(deadDes);
		msgInfo.setMsgOperate2(me.getPPk()+"");
		msgInfo.setMsgType(PopUpMsgType.NOTIFY_SELF_DEAD);
		msgInfo.setPPk(dead.getPPk());
		msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_PK_FIRST);
		msgInfo.setMsgOperate1(hint.toString());
		uMsgService.sendPopUpMsg(msgInfo);
		
		this.removeOther(dead.getPPk());
		
		dead.getPKState().notifyOtherSelfDead(me);//֪ͨ�����������ߵ���
	}
	

	/**
	 * �õ�PKʱ��ʾ��ϵͳ��Ϣ,��Ϣ����һ�ξ����
	 * @return
	 */
	public List<SystemInfoVO> getSysInfoList()
	{
		List<SystemInfoVO> temp = new ArrayList<SystemInfoVO>(sys_info_list);
		sys_info_list.clear();
		return temp;
	}

	/**
	 * ����ϵͳ��Ϣ
	 * @param content
	 */
	private void addSysInfo(String content)
	{
		SystemInfoVO sys_info = new SystemInfoVO();
		sys_info.setSystemInfo(content);
		sys_info_list.add(sys_info);
	}
}
