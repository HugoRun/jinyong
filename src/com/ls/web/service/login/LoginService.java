package com.ls.web.service.login;

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
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ls 功能：玩家登陆，角色登陆管理
 */
public class LoginService {
    /**
     * 在线角色Map<账号id,角色id>
     */
    public static Map<Integer, Integer> online_role = new HashMap<Integer, Integer>(GameConfig.getUserNumUpperLimit());
    Logger logger = Logger.getLogger("log.service");

    /**
     * 得到在线玩家列表
     *
     * @return
     */
    public static Map<Integer, Integer> getOnlineRoleList() {
        return online_role;
    }

    /**
     * 得到当前在线人数
     *
     * @return
     */
    public static int getOnlineNum() {
        return online_role.size();
    }


    /**
     * 判断在线人数是否已达上限
     *
     * @return
     */
    public boolean isFullOnlineRoleNum() {
        boolean result = false;

        int online_num = LoginService.online_role.size();// 在线人数

        int user_num_upper_limit = GameConfig.getUserNumUpperLimit();// 系统限制最大人数

        if (online_num >= user_num_upper_limit) {
            result = true;
        }

        return result;
    }

    /**
     * 注册登陆账号
     *
     * @param user_name user_name
     * @param pwd password
     * @param login_ip 登陆IP
     * @return 返回值为uPk
     */
    public int register(String user_name, String pwd, String login_ip) {
        logger.info("用户成功注册，用户名：" + user_name + ";密码：" + pwd + ";登陆IP：" + login_ip);

        int uPk = -1;

        LoginDao loginDao = new LoginDao();

        uPk = loginDao.incert(user_name, pwd, login_ip);

        return uPk;
    }

    /**
     * 用户成功登陆
     *
     * @param u_pk     账号id
     * @param login_ip 登陆ip
     */
    public void login(String u_pk, String login_ip) {
        logger.info("用户成功登陆账号，用户名ID：" + u_pk + ";登陆IP：" + login_ip);

        LoginDao loginDao = new LoginDao();

        loginDao.updateState(u_pk, login_ip);
    }

    /**
     * 验证用户名密码是否正确
     *
     * @param user_name
     * @param pwd
     * @return loginInfo 返回为空表示验证失败
     */
    public LoginInfoVO validateLogin(String user_name, String pwd) {
        LoginInfoVO loginInfo = null;
        String md5_paw = MD5.getInstance().getMD5ofStr(pwd);
        LoginInfoDAO loginDao = new LoginInfoDAO();
        return loginDao.getUserInfoLoginPaw(user_name, md5_paw);
    }

    /**
     * 正常角色登陆
     *
     * @param new_login_pPk
     * @param request
     * @return
     */
    public RoleEntity loginRole(String new_login_pPk, HttpServletRequest request) {
        return this.loginRole(new_login_pPk, request, 0);
    }

    /**
     * 新手角色登陆
     *
     * @param new_login_pPk
     * @param request
     * @return
     */
    public RoleEntity loginRookieRole(String new_login_pPk, HttpServletRequest request) {
        RoleEntity role_info = this.loginRole(new_login_pPk, request, 1);

        if (role_info.getBasicInfo().getPlayer_state_by_new() == 1 && role_info.getGrade() == GameConfig.getGradeUpperHighLimit()) {
            RoleService roleService = new RoleService();
            roleService.initRoleLogic(role_info);//新手登陆时的处理
        }

        return role_info;
    }

    /**
     * 登陆角色
     *
     * @param new_login_pPk
     * @param request
     * @param loginType     登陆类型：0，表示正常登陆，1表示新手登陆
     * @return
     */
    private RoleEntity loginRole(String new_login_pPk, HttpServletRequest request, int loginType) {
        if (request == null) {
            return null;
        }

        RoleEntity roleInfo = RoleCache.getByPpk(new_login_pPk);
        if (roleInfo == null) {
            //角色登陆失败
            DataErrorLog.debugData("LoginService.loginRole：角色登陆失败，无该角色，p_pk=" + new_login_pPk);
            return null;
        }

        int u_pk = roleInfo.getUPk();
        Integer old_ppk = LoginService.online_role.get(u_pk);
        if (old_ppk != null)//该账号已经登陆了角色old_ppk
        {
            //old_ppk角色下线处理
            this.loginoutRole(old_ppk + "");
        }
        if (loginType == 1) {
            //新手登陆
            roleInfo.rookieLogin(request);
        } else {
            //正常登陆
            roleInfo.login(request);
        }

        return roleInfo;
    }


    /**
     * 角色退出处理, 判断session的值和roleEntity中的session是不是同一个, 如果是那么销毁
     * 如果不是,判断roleEntity中的session所对应的pPk是否还存在,如果不存在,那么销毁,否则不销毁.
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
     * 角色退出处理,直接退出
     */
    public void loginoutRole(String pPk) {
        RoleEntity roleInfo = RoleService.getRoleInfoById(pPk);
        if (roleInfo != null)// 处理退出角色
        {
            try {
                OutLineService outLineService = new OutLineService();
                outLineService.outLineClear(roleInfo);//角色离线处理

                JmsUtil.delPeo(roleInfo.getStateInfo().getSuper_qudao(), roleInfo.getStateInfo().getQudao());
                BasicInfo bi = roleInfo.getBasicInfo();
                if (TiaozhanConstant.TIAOZHAN.containsKey(bi.getPPk())) {
                    TiaozhanConstant.TIAOZHAN_TIME.remove(bi.getPPk());
                    int pk = TiaozhanConstant.TIAOZHAN.get(bi.getPPk());
                    TiaozhanConstant.TIAOZHAN.remove(bi.getPPk());
                    TiaozhanConstant.TIAOZHAN.remove(pk);
                    String tiao_name = new RoleService().getName(pk + "")[0];
                    new SystemInfoService().insertSystemInfoBySystem(bi.getName() + "被" + tiao_name + "的霸气所震，竟然没有胆量接受战书！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                DataErrorLog.debugLogic("角色退出异常：" + e);
            } finally {
                roleInfo.save();
            }
        }
    }

    /**
     * 统计玩家的渠道注册信息
     */
    public void insertPlayerSta(int u_pk, String channel_id) {
        PlayerStaDao dao = new PlayerStaDao();
        dao.insertPlayerSta(u_pk, channel_id);
    }

    /**
     * 判断账号是否存在
     */
    public LoginInfoVO getLoginInfo(String name) {
        LoginInfoDAO infodao = new LoginInfoDAO();
        LoginInfoVO infovo = infodao.getUserInfoLoginName(name);
        return infovo;
    }
}
