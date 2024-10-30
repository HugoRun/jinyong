package com.ls.model.user;

import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.ben.jms.JmsUtil;
import com.ls.model.group.GroupModel;
import com.ls.pub.constant.player.PlayerState;

/**
 * ���ܣ���ɫ������Ϣ
 * 
 * @author ls Apr 2, 2009 4:09:39 PM
 */
public class StateInfo extends UserBase
{
	private HttpSession session = null;
	/** ��ǰ��Ҷ�Ӧ��session_id */

	/** ��ҵ�½��ɫ��ʱ�� */
	private long loginTime = 0;
	
	/** ��ҵĵ�ǰ״̬ */
	private int curState = PlayerState.OUTLINE;
	/** ��ҵ�ǰ��Ұ */
	private String view = "";
	/** �ϴ����ֵ��������ʱ�� */
	private long evilValueConsumeTime;
	/**�ϴι��ĵ�ʱ��*/
	private long pre_public_chat_time;
	
	/** ������� */
	private GroupModel groupInfo = null;
	
	// �������侭�飬Ŀǰר���ھ�ת
	private long deadDropExp = 0;

	// ʹ������������,�����Ӧ�õ��䵫δ����ľ���,�Ǹ�ɱ��������ҿ���
	private long shouldDropExperience;

	private int prize_consume_time = 0;// �콱�����ĵ�ʱ�䣨��λ�룩

	/** ���� */
	private String qudao;

	/** ������ */
	private String super_qudao;

	/** userid */
	private String userid;

	public StateInfo(int p_pk)
	{
		super(p_pk);
	}
	
	/**
	 * ��ɫ��½
	 */
	public void login(HttpSession session)
	{
		this.session = session;
		loginTime = Calendar.getInstance().getTimeInMillis();
		evilValueConsumeTime = loginTime;
		session.setAttribute("pPk", p_pk+"");
		this.qudao = (String) session.getAttribute("qudao");
		if( StringUtils.isEmpty(qudao)==false )
		{
			this.userid = (String) session.getAttribute("user_name");
			this.super_qudao = (String) session.getAttribute("super_qudao");
			JmsUtil.updateLast_loginin_time(super_qudao,qudao, userid,this.getRoleEntity().getName());
		}
	}
	
	/**
	 * ��������ʱ�����ж��Ƿ��������
	 * @return
	 */
	public String isPublicChat()
	{
		long diff_value = System.currentTimeMillis()-this.pre_public_chat_time;
		if( diff_value < 15 * 1000 )
		{
			return "�Բ��������η���ʱ����̫��,���Ժ��ٷ���";
		}
		this.pre_public_chat_time = System.currentTimeMillis();
		return null;
	}
	
	
	public String getQudao()
	{
		return qudao;
	}

	public void setQudao(String qudao)
	{
		this.qudao = qudao;
	}

	public String getUserid()
	{
		return userid;
	}

	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	/**
	 * ��ɫ�콱ʱ���������콱������ʱ�䣨�룩
	 * 
	 * @return ��������ʱ����
	 */
	public int getPrizeOnlineTime()
	{
		int onelie_time = 0;
		long now_time = Calendar.getInstance().getTimeInMillis();
		onelie_time = (int) (now_time - loginTime) / 1000 - prize_consume_time;
		return onelie_time;
	}

	/**
	 * �콱���ĵ�����ʱ�䣨��λ�룩
	 * 
	 * @param consume_time
	 */
	public void addPrizeConsumeTime(int consume_time)
	{
		prize_consume_time = prize_consume_time + consume_time;
	}
	public void setPrizeConsumeTime(int times)
	{
		this.prize_consume_time=times;
	}

	/**
	 * ����ǰһ����Ӱ汾
	 * 
	 * @return
	 */
	public int getGroup_id()
	{
		int captain_id = -1;
		if (groupInfo != null)
		{
			captain_id = groupInfo.getCaptianID();
		}
		return captain_id;
	}

	public long getDeadDropExp()
	{
		return deadDropExp;
	}

	public void setDeadDropExp(long deadDropExp)
	{
		this.deadDropExp = deadDropExp;
	}

	public HttpSession getSession()
	{
		return session;
	}

	public void setSession(HttpSession session)
	{
		this.session = session;
	}

	public long getLoginTime()
	{
		return loginTime;
	}

	public int getCurState()
	{
		return curState;
	}

	public void setCurState(int curState)
	{
		this.curState = curState;
	}

	public String getView()
	{
		return view;
	}

	public void setView(String view)
	{
		this.view = view;
	}

	public long getEvilValueConsumeTime()
	{
		return evilValueConsumeTime;
	}

	public void updateEvilValueConsumeTime(long evilValueConsumeTime)
	{
		this.evilValueConsumeTime = evilValueConsumeTime;
	}


	public long getShouldDropExperience()
	{
		return shouldDropExperience;
	}

	public void setShouldDropExperience(long shouldDropExperience)
	{
		this.shouldDropExperience = shouldDropExperience;
	}

	public GroupModel getGroupInfo()
	{
		return groupInfo;
	}
	
	public String getGroupEffectDisplay()
	{
		if(groupInfo!=null )
		{
			return groupInfo.getGroupEffectDisplay(this.getRoleEntity());
		}
		
		return "";
	}
	

	public void setGroupInfo(GroupModel groupInfo)
	{
		this.groupInfo = groupInfo;
	}

	public String getSuper_qudao()
	{
		return super_qudao;
	}

	public void setSuper_qudao(String super_qudao)
	{
		this.super_qudao = super_qudao;
	}

}
