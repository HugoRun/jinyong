package com.ls.model.user;

import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.ben.jms.JmsUtil;
import com.ls.model.group.GroupModel;
import com.ls.pub.constant.player.PlayerState;

/**
 * 功能：角色在线信息
 * 
 * @author ls Apr 2, 2009 4:09:39 PM
 */
public class StateInfo extends UserBase
{
	private HttpSession session = null;
	/** 当前玩家对应的session_id */

	/** 玩家登陆角色的时间 */
	private long loginTime = 0;
	
	/** 玩家的当前状态 */
	private int curState = PlayerState.OUTLINE;
	/** 玩家当前视野 */
	private String view = "";
	/** 上次罪恶值消除更新时间 */
	private long evilValueConsumeTime;
	/**上次公聊的时间*/
	private long pre_public_chat_time;
	
	/** 队伍对象 */
	private GroupModel groupInfo = null;
	
	// 死亡掉落经验，目前专用于九转
	private long deadDropExp = 0;

	// 使用了免死符后,此玩家应该掉落但未掉落的经验,是给杀死他的玩家看的
	private long shouldDropExperience;

	private int prize_consume_time = 0;// 领奖所消耗的时间（单位秒）

	/** 渠道 */
	private String qudao;

	/** 父渠道 */
	private String super_qudao;

	/** userid */
	private String userid;

	public StateInfo(int p_pk)
	{
		super(p_pk);
	}
	
	/**
	 * 角色登陆
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
	 * 根据聊天时间间隔判断是否可以聊天
	 * @return
	 */
	public String isPublicChat()
	{
		long diff_value = System.currentTimeMillis()-this.pre_public_chat_time;
		if( diff_value < 15 * 1000 )
		{
			return "对不起，您两次发言时间间隔太短,请稍后再发！";
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
	 * 角色领奖时，可用做领奖的在线时间（秒）
	 * 
	 * @return 返回在线时间秒
	 */
	public int getPrizeOnlineTime()
	{
		int onelie_time = 0;
		long now_time = Calendar.getInstance().getTimeInMillis();
		onelie_time = (int) (now_time - loginTime) / 1000 - prize_consume_time;
		return onelie_time;
	}

	/**
	 * 领奖消耗的在线时间（单位秒）
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
	 * 兼容前一个组队版本
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
