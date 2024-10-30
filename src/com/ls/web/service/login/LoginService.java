package com.ls.web.service.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ben.dao.logininfo.LoginInfoDAO;
import com.ben.jms.JmsUtil;
import com.ben.tiaozhan.TiaozhanConstant;
import com.ben.vo.logininfo.LoginInfoVO;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.dao.login.LoginDao;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.log.DataErrorLog;
import com.ls.web.service.player.RoleService;
import com.lw.dao.player.PlayerStaDao;
import com.pm.service.outLine.OutLineService;
import com.pm.service.systemInfo.SystemInfoService;
import com.pub.MD5;

/**
 * @author ls ���ܣ���ҵ�½����ɫ��½����
 */
public class LoginService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * ���߽�ɫMap<�˺�id,��ɫid>
	 */
	public static Map<Integer, Integer> online_role = new HashMap<Integer, Integer>(GameConfig.getUserNumUpperLimit());
	
	/**
	 * �õ���������б�
	 * @return
	 */
	public static Map<Integer, Integer> getOnlineRoleList()
	{
		return online_role;
	}
	
	/**
	 * �õ���ǰ��������
	 * @return
	 */
	public static int getOnlineNum()
	{
		return online_role.size();
	}
	
	
	/**
	 * �ж����������Ƿ��Ѵ�����
	 * @return
	 */
	public boolean isFullOnlineRoleNum()
	{
		boolean result = false;

		int online_num = LoginService.online_role.size();// ��������

		int user_num_upper_limit = GameConfig.getUserNumUpperLimit();// ϵͳ�����������

		if (online_num >= user_num_upper_limit)
		{
			result = true;
		}

		return result;
	}

	/**
	 * ע���½�˺�
	 * @param user_name
	 * @param password
	 * @param ��½IP
	 * @return ����ֵΪuPk
	 */
	public int register(String user_name, String pwd, String login_ip)
	{
		logger.info("�û��ɹ�ע�ᣬ�û�����" + user_name + ";���룺" + pwd + ";��½IP��"
				+ login_ip);

		int uPk = -1;

		LoginDao loginDao = new LoginDao();

		uPk = loginDao.incert(user_name, pwd, login_ip);

		return uPk;
	}

	/**
	 * �û��ɹ���½
	 * @param u_pk 			�˺�id
	 * @param login_ip		��½ip
	 */
	public void login(String u_pk, String login_ip)
	{
		logger.info("�û��ɹ���½�˺ţ��û���ID��" + u_pk + ";��½IP��" + login_ip);

		LoginDao loginDao = new LoginDao();

		loginDao.updateState(u_pk, login_ip);
	}

	/**
	 * ��֤�û��������Ƿ���ȷ
	 * @param user_name
	 * @param pwd
	 * @return loginInfo ����Ϊ�ձ�ʾ��֤ʧ��
	 */
	public LoginInfoVO validateLogin(String user_name, String pwd)
	{
		LoginInfoVO loginInfo = null;

		String md5_paw = MD5.getInstance().getMD5ofStr(pwd);

		LoginInfoDAO loginDao = new LoginInfoDAO();
		loginInfo = loginDao.getUserInfoLoginPaw(user_name, md5_paw);

		return loginInfo;
	}

	/**
	 * ������ɫ��½
	 * @param new_login_pPk
	 * @param request
	 * @return
	 */
	public RoleEntity loginRole(String new_login_pPk,HttpServletRequest request)
	{
		return this.loginRole(new_login_pPk, request, 0);
	}
	
	/**
	 * ���ֽ�ɫ��½
	 * @param new_login_pPk
	 * @param request
	 * @return
	 */
	public RoleEntity loginRookieRole(String new_login_pPk,HttpServletRequest request)
	{
		RoleEntity role_info =  this.loginRole(new_login_pPk, request, 1);

		if( role_info.getBasicInfo().getPlayer_state_by_new()==1 && role_info.getGrade()==GameConfig.getGradeUpperHighLimit() )
		{
			RoleService roleService = new RoleService();
			roleService.initRoleLogic(role_info);//���ֵ�½ʱ�Ĵ���
		}
		
		return role_info;
	}
	
	/**
	 * ��½��ɫ
	 * @param new_login_pPk
	 * @param request
	 * @param loginType				��½���ͣ�0����ʾ������½��1��ʾ���ֵ�½
	 * @return
	 */
	private RoleEntity loginRole(String new_login_pPk,HttpServletRequest request,int loginType)
	{
		if (request == null )
		{
			return null;
		}

		RoleEntity roleInfo = RoleCache.getByPpk(new_login_pPk);
		if( roleInfo==null )
		{
			//��ɫ��½ʧ��
			DataErrorLog.debugData("LoginService.loginRole����ɫ��½ʧ�ܣ��޸ý�ɫ��p_pk="+new_login_pPk);
			return null;
		}
		
		int u_pk = roleInfo.getUPk();
		Integer old_ppk = LoginService.online_role.get(u_pk);
		if( old_ppk!=null )//���˺��Ѿ���½�˽�ɫold_ppk
		{
			//old_ppk��ɫ���ߴ���
			this.loginoutRole(old_ppk+"");
		}
		if( loginType==1 )
		{
			//���ֵ�½
			roleInfo.rookieLogin(request);
		}
		else
		{
			//������½
			roleInfo.login(request);
		}
		
		return roleInfo;
	}


	/**
	 * ��ɫ�˳�����, �ж�session��ֵ��roleEntity�е�session�ǲ���ͬһ��, �������ô����
	 * �������,�ж�roleEntity�е�session����Ӧ��pPk�Ƿ񻹴���,���������,��ô����,��������.
	 *//*
	public void loginoutRole(String pPk, HttpSession session)
	{
		RoleEntity roleInfo = RoleCache.getByPpk(pPk);

		if (roleInfo != null)
		{
			HttpSession session2 = roleInfo.getStateInfo().getSession();
			String sessionId2 = session2.getId();
			String sessionId = session.getId();

			if (!sessionId.equals(sessionId2))
			{
				String pPk2 = (String) session2.getAttribute("pPk");
				if (pPk2 == null || pPk2.equals("") || pPk2.equals("null"))
				{
					this.loginoutRole(pPk);
				}
			}
			else
			{
				this.loginoutRole(pPk);
			}
		}
	}*/

	/**
	 * ��ɫ�˳�����,ֱ���˳�
	 */
	public void loginoutRole(String pPk)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk);
		if (roleInfo != null)// �����˳���ɫ
		{
			try
			{
				OutLineService outLineService = new OutLineService();
    			outLineService.outLineClear(roleInfo);//��ɫ���ߴ���
    			
    			JmsUtil.delPeo(roleInfo.getStateInfo().getSuper_qudao(),roleInfo.getStateInfo().getQudao());
    			BasicInfo bi = roleInfo.getBasicInfo();
    			if(TiaozhanConstant.TIAOZHAN.containsKey(bi.getPPk())){
    				TiaozhanConstant.TIAOZHAN_TIME.remove(bi.getPPk());
    				int pk = TiaozhanConstant.TIAOZHAN.get(bi.getPPk());
    				TiaozhanConstant.TIAOZHAN.remove(bi.getPPk());
    				TiaozhanConstant.TIAOZHAN.remove(pk);
    				String tiao_name =new  RoleService().getName(pk+"")[0];
    				new SystemInfoService().insertSystemInfoBySystem(bi.getName()+"��"+tiao_name+"�İ������𣬾�Ȼû�е�������ս�飡");
    			}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				DataErrorLog.debugLogic("��ɫ�˳��쳣��"+e.toString());
			}
			finally
			{
				roleInfo.save();
			}
		}
	}

	/** ͳ����ҵ�����ע����Ϣ */
	public void insertPlayerSta(int u_pk, String channel_id)
	{
		PlayerStaDao dao = new PlayerStaDao();
		dao.insertPlayerSta(u_pk, channel_id);
	}

	/** �ж��˺��Ƿ���� */
	public LoginInfoVO getLoginInfo(String name)
	{
		LoginInfoDAO infodao = new LoginInfoDAO();
		LoginInfoVO infovo = infodao.getUserInfoLoginName(name);
		return infovo;
	}
}
