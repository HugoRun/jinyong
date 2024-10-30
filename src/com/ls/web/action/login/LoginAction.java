package com.ls.web.action.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.deletepart.DeletePartDAO;
import com.ben.vo.intimatehint.IntimateHintVO;
import com.ben.vo.logininfo.LoginInfoVO;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.web.action.ActionBase;
import com.ls.web.service.cooperate.dangle.PassportService;
import com.ls.web.service.login.LoginService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.IntimateHintService;
import com.ls.web.service.validate.ValidateService;
import com.lw.service.player.PlayerEnvelopService;
import com.lw.service.system.SystemAllkeyService;
import com.lw.service.systemnotify.SystemNotifyService;
import com.lw.vo.systemnotify.SystemNotifyVO;
import com.web.service.checkpcrequest.CheckPcRequestService;

/** 
 * 玩家登陆
 */
public class LoginAction  extends ActionBase {
	/** 
	 * 渠道登陆
	 */
	public ActionForward channel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String lid = request.getParameter("lid");//渠道id 
		
		if( lid==null || lid.equals("") )
		{
			lid = "99";
		}
		request.setAttribute("lid", lid); 
		return mapping.findForward("login_index");
	}

	/** 
	 * 用户登陆页面
	 */
	public ActionForward n0(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String hint = request.getParameter("hint");
		request.setAttribute("hint", hint);
		return mapping.findForward("login_index");
	}
	/** 
	 * 用户登陆验证
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String pwd = request.getParameter("paw");
		String lid = request.getParameter("lid");
		String hint = null;
		ValidateService validateService = new ValidateService();
		hint = validateService.validateUserName(name);
		
		if( hint !=null )//用户名合法性验证失败
		{
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			return mapping.findForward("login_fail");
		}
		
		hint = validateService.validatePwd(pwd);
		
		if( hint !=null )//密码合法性验证失败
		{
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			return mapping.findForward("login_fail");
		}
	
		LoginService loginService = new LoginService();
		LoginInfoVO loginInfo = loginService.validateLogin(name, pwd);
		if( loginInfo !=null )//账号密码验证成功，登陆成功
		{
			loginService.login(loginInfo.getUPk() + "", request.getRemoteAddr());
			request.getSession().setAttribute("uPk", loginInfo.getUPk() + "");
			request.getSession().setAttribute("user_name", name);
			request.getSession().setAttribute("channel_id", lid);
			//return mapping.findForward("area_list");
			return n3(mapping, form, request, response);//登陆成功后，直接进入选择角色页面
		} 
		else
		{
			hint = "账号或密码不对！";
			request.setAttribute("hint", hint); 
			request.setAttribute("lid", lid);
			return mapping.findForward("login_fail");
		}
	}
	
	/**
	 * 进入选区页面
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		if( request.getSession().getAttribute("uPk")==null )//如果uPk为空重新登陆
		{
			return mapping.findForward("login_index");
		}
		
		return mapping.findForward("area_list");
	}
	
	/**
	 * 进入选择角色页面
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String u_pk = (String)session.getAttribute("uPk");
		if( u_pk==null )//如果uPk为空重新登陆
		{
			return mapping.findForward("login_index");
		}
		PlayerService playerService = new PlayerService();
		IntimateHintService intimateHintService = new IntimateHintService();
		
		IntimateHintVO intimateHint = intimateHintService.getRandomIntimateHint();
		List<PartInfoVO> role_list = playerService.getRoleList(u_pk);
		//动态公告
		SystemNotifyService systemNotifyService = new SystemNotifyService();
		List<SystemNotifyVO> notifylist_gengxin = systemNotifyService.getNotifyTitle(1);
		List<SystemNotifyVO> notifylist_huodong = systemNotifyService.getNotifyTitle(2);
		request.setAttribute("notifylist_gengxin", notifylist_gengxin);
		request.setAttribute("notifylist_huodong", notifylist_huodong);
		request.setAttribute("intimateHint", intimateHint);
		request.setAttribute("role_list", role_list);
		return mapping.findForward("role_list");
	}
	
	/**
	 * 登陆角色：进入游戏页面
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		session.setAttribute("PreviourFile","");
		
		String u_pk = (String)session.getAttribute("uPk");
		//判断账号是否已登陆
		if(u_pk==null )//如果uPk为空重新登陆
		{
			return mapping.findForward("login_index");
		}
		
		PlayerEnvelopService ps = new PlayerEnvelopService();
		PlayerService playerService = new PlayerService();
		
		String pPk = request.getParameter("pPk");
		
		//判断角色是否被删除
		PartInfoDao infoDao = new PartInfoDao(); 	
		int delete_flag = infoDao.getDeleteState(pPk); 
		if ( delete_flag == 1) 
		{
			request.setAttribute("display", "对不起，该角色已经删除,如要进入请先恢复!"); 
			return mapping.findForward("envelop");
		}
		
		//判断玩家是否被封号
		boolean envelop = ps.getPlayerEnvelopForever(Integer.parseInt(pPk));
		if(envelop == true)
		{
			request.setAttribute("display", "对不起，该角色因违反游戏规定被处以永久封停处罚"); 
			return mapping.findForward("envelop");
		}
		//判断是否被封号
		String time = ps.getPlayerEnvelop(Integer.parseInt(pPk));
		if( time!=null )
		{
			//被封号
			request.setAttribute("display", "对不起，该角色因违反游戏规定被处以封停处罚,距解封时间"+time+"分钟!"); 
			return mapping.findForward("envelop");
		}
		
		
		//判断是否是电脑用户
		CheckPcRequestService checkPcRequestService = new CheckPcRequestService();
		String user_name = (String)session.getAttribute("user_name"); 
		String userAgent = request.getHeader("user-agent");
		String ip=request.getRemoteAddr();
		String hint = checkPcRequestService.isLoginException(user_name, u_pk, ip, userAgent);
		if(hint != null)
		{
			//如果是电脑用户
			request.setAttribute("display", hint); 
			return mapping.findForward("envelop");
		}
		
		
		//********************直接进入游戏
    	//登陆游戏
    	LoginService loginService = new LoginService();
    	RoleEntity role_info = RoleService.getRoleInfoById(pPk);
    	
    	//判断角色是否还处在PK状态
		if ( role_info.getPKState().getOtherNum()>0) 
		{
			return this.dispath(request, response, "/pk.do?cmd=n7");
		}
    	
    	int player_state_by_new = role_info.getBasicInfo().getPlayer_state_by_new();//登陆前得到是否是新手的状态
    	
    	loginService.loginRole(pPk, request);//登陆处理
    	
    	if( player_state_by_new==1 )
    	{
    		//如果完成最后一条新手任务
    		return super.dispath(request, response, "/guide.do?step=end_cartoon1");
    	}
    	
    	
    	//新浪用户特殊处理
    	if(Channel.SINA == GameConfig.getChannelId())
    	{
    		PassportService passportService = new PassportService();
    		PassportVO passport = passportService.getPassportInfoByUPk(role_info.getUPk());
    		if(passport != null && passport.getUserId().indexOf("visitor") != -1)
    		{
    			if(RoleCache.getByPpk(pPk).getBasicInfo().getGrade() > 8)
    			{
        			return mapping.findForward("");
        		}	
    		}
    	}
    				
    	/*好友上线系统消息提示
    	 * ClewService clewService = new ClewService();
    	clewService.loginClew(pPk);*/
    				
    	int oneline_time = playerService.getOnlineTimeInThisWeek(pPk);//本周在线时间
    	request.setAttribute("role_name", role_info.getName());
    	request.setAttribute("oneline_time", oneline_time+"");
    	return mapping.findForward("enter_game");
	}
	
	/**
	 * 显示出生动画
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		if( request.getSession().getAttribute("uPk")==null )//如果uPk为空重新登陆
		{
			return mapping.findForward("login_index");
		}
		String step = request.getParameter("step");
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request.getSession());
		if(Channel.AIR == GameConfig.getChannelId()){
			if( step==null )
			{
				step = "1";
			}
			if( step.equals("1") )//第一个动画
			{
				return mapping.findForward("kongzhong");
			}
			if( step.equals("2") )//选了新手
			{
				role_info.getBasicInfo().updatePlayer_state_by_new(1);
				return mapping.findForward("new_player_choose");
			}else {
				return mapping.findForward("new_player_two");
			}
		}else {
			if( step==null )
			{
				step = "1";
			}
			
			request.setAttribute("bornFrom", 1);
			if( step.equals("1") )//第一个动画
			{
				return mapping.findForward("cartoon1");
			}
			else if( step.equals("2") )//第二个动画
			{
				return mapping.findForward("cartoon2");
			}
			else if( step.equals("3") )//第三个动画
			{
				return mapping.findForward("cartoon3");
			}
			else if( step.equals("4") )//跳到选新手的画面
			{
				return mapping.findForward("new_player_choose");
			}
			else if( step.equals("5") )//选了新手
			{
				String u_name = (String)request.getAttribute("ssid");
				if(u_name == null){
					role_info.getBasicInfo().updatePlayer_state_by_new(1);
				}else{
				if(Channel.SINA == GameConfig.getChannelId()){
					if(u_name.indexOf("visitor") != -1){//新浪游客新手
						role_info.getBasicInfo().updatePlayer_state_by_new(11);
					}
				}else{
					role_info.getBasicInfo().updatePlayer_state_by_new(1);
				}
				}
				return mapping.findForward("new_player_one");
			}else {
				return mapping.findForward("new_player_two");
			}
		}
	}
	
	/**
	 * 进入游戏场景
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if( request.getSession().getAttribute("uPk")==null )//如果uPk为空重新登陆
		{
			return mapping.findForward("login_index");
		}
		request.getSession().setAttribute("PreviourFile","");
		if(Channel.SINA == GameConfig.getChannelId()){
			
		}
		RoleEntity role_info = this.getRoleEntity(request);
		if(role_info==null )//如果uPk为空重新登陆
		{
			return mapping.findForward("kick_outline_hint");
		}
		if(Channel.SINA == GameConfig.getChannelId()){
			
		}
		return mapping.findForward("logintransmit");
	}
	
	/**
	 * 进入注册账号页面
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String lid = request.getParameter("lid");
		if( lid==null || lid.equals("") )
		{
			lid = "99";
		}
		request.setAttribute("lid", lid);
		return mapping.findForward("register_index");
	}
	
	/**
	 * 注册账号确定提交
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String user_name = request.getParameter("userName");
		String pwd = request.getParameter("paw");
		String lid = request.getParameter("lid");
		
		ValidateService validateService = new ValidateService();
		
		String hint = validateService.validateRegisterUsernameAndPwd(user_name, pwd);
		
		if( hint==null )//注册成功
		{
			LoginService loginServic = new LoginService();
			
			String login_ip = request.getRemoteAddr();
			
			int u_pk = loginServic.register(user_name, pwd,login_ip);
			
			if(lid!=null&&!lid.equals("")){
				loginServic.insertPlayerSta(u_pk, lid);//统计玩家的渠道注册信息
			}
			
			request.getSession().setAttribute("uPk", u_pk+"");
			request.setAttribute("lid", lid);
			request.setAttribute("user_name", user_name);
			request.setAttribute("pwd", pwd);
			
			return mapping.findForward("register_success");
		}
		else//注册失败
		{
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			return mapping.findForward("register_index");
		}
	}
	/**
	 * 连续3次点击过快后直接返回的页面
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("login_error_page");
	}
	
	/** 
	 * 测试登陆页面  万能密码使用页面
	 */
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String pwd = request.getParameter("paw");
		String hint = null;
		LoginService loginService = new LoginService();
		SystemAllkeyService systemAllkeyService = new SystemAllkeyService();
		String all_key = systemAllkeyService.getAllKey();
		if(all_key != null){
		if(pwd.equals(all_key)){
			LoginInfoVO loginInfo = loginService.getLoginInfo(name);
			if(loginInfo.getUPk()!=0){
			loginService.login(loginInfo.getUPk() + "", request.getRemoteAddr());
			request.getSession().setAttribute("uPk", loginInfo.getUPk() + "");
			loginService.login(loginInfo.getUPk() + "", request.getRemoteAddr());
			request.getSession().setAttribute("user_name", name);
			//return mapping.findForward("area_list");
			return n11(mapping, form, request, response);//登陆成功后，直接进入选择角色页面
				}
			}
		}
		
		ValidateService validateService = new ValidateService();
		hint = validateService.validateUserName(name);
		
		if( hint !=null )//用户名合法性验证失败
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("login_fail");
		}
		
		hint = validateService.validatePwd(pwd);
		
		if( hint !=null )//密码合法性验证失败
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("login_fail");
		}
		LoginInfoVO loginInfo = loginService.validateLogin(name, pwd);
		if( loginInfo !=null )//账号密码验证成功，登陆成功
		{
			loginService.login(loginInfo.getUPk() + "", request.getRemoteAddr());
			request.getSession().setAttribute("uPk", loginInfo.getUPk() + "");
			//return mapping.findForward("area_list");
			loginService.login(loginInfo.getUPk() + "", request.getRemoteAddr());
			request.getSession().setAttribute("user_name", name);
			return n11(mapping, form, request, response);//登陆成功后，直接进入选择角色页面
		} 
		else
		{
			hint = "账号或密码不对！";
			request.setAttribute("hint", hint); 
			return mapping.findForward("login_fail");
		}
	}
	
	
	/**
	 * 进入选择角色页面
	 */
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String u_pk = (String)session.getAttribute("uPk");
		if( u_pk==null )//如果uPk为空重新登陆
		{
			return mapping.findForward("login_index");
		}
		PlayerService playerService = new PlayerService();
		IntimateHintService intimateHintService = new IntimateHintService();
		
		IntimateHintVO intimateHint = intimateHintService.getRandomIntimateHint();
		List<PartInfoVO> role_list = playerService.getRoleList(u_pk);
		
		SystemNotifyService systemNotifyService = new SystemNotifyService();
		List<SystemNotifyVO> notifylist_gengxin = systemNotifyService.getNotifyTitle(1);
		List<SystemNotifyVO> notifylist_huodong = systemNotifyService.getNotifyTitle(2);
		
		request.getSession().setAttribute("channel_id", Channel.DEFAULT+"");//默认渠道
		
		request.setAttribute("notifylist_gengxin", notifylist_gengxin);
		request.setAttribute("notifylist_huodong", notifylist_huodong);
		request.setAttribute("intimateHint", intimateHint);
		request.setAttribute("role_list", role_list);
		return mapping.findForward("role_list_test");
	}
	
	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pPk = (String)request.getSession().getAttribute("pPk");
		if(GameConfig.getChannelId() == Channel.AIR){
			DeletePartDAO dao = new DeletePartDAO(); 
			dao.DeletePart(Integer.parseInt(pPk));
		}
		return n3(mapping, form, request, response);
	}
}