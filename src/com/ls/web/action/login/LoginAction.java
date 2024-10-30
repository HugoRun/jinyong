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
 * ��ҵ�½
 */
public class LoginAction  extends ActionBase {
	/** 
	 * ������½
	 */
	public ActionForward channel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String lid = request.getParameter("lid");//����id 
		
		if( lid==null || lid.equals("") )
		{
			lid = "99";
		}
		request.setAttribute("lid", lid); 
		return mapping.findForward("login_index");
	}

	/** 
	 * �û���½ҳ��
	 */
	public ActionForward n0(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String hint = request.getParameter("hint");
		request.setAttribute("hint", hint);
		return mapping.findForward("login_index");
	}
	/** 
	 * �û���½��֤
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String pwd = request.getParameter("paw");
		String lid = request.getParameter("lid");
		String hint = null;
		ValidateService validateService = new ValidateService();
		hint = validateService.validateUserName(name);
		
		if( hint !=null )//�û����Ϸ�����֤ʧ��
		{
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			return mapping.findForward("login_fail");
		}
		
		hint = validateService.validatePwd(pwd);
		
		if( hint !=null )//����Ϸ�����֤ʧ��
		{
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			return mapping.findForward("login_fail");
		}
	
		LoginService loginService = new LoginService();
		LoginInfoVO loginInfo = loginService.validateLogin(name, pwd);
		if( loginInfo !=null )//�˺�������֤�ɹ�����½�ɹ�
		{
			loginService.login(loginInfo.getUPk() + "", request.getRemoteAddr());
			request.getSession().setAttribute("uPk", loginInfo.getUPk() + "");
			request.getSession().setAttribute("user_name", name);
			request.getSession().setAttribute("channel_id", lid);
			//return mapping.findForward("area_list");
			return n3(mapping, form, request, response);//��½�ɹ���ֱ�ӽ���ѡ���ɫҳ��
		} 
		else
		{
			hint = "�˺Ż����벻�ԣ�";
			request.setAttribute("hint", hint); 
			request.setAttribute("lid", lid);
			return mapping.findForward("login_fail");
		}
	}
	
	/**
	 * ����ѡ��ҳ��
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		if( request.getSession().getAttribute("uPk")==null )//���uPkΪ�����µ�½
		{
			return mapping.findForward("login_index");
		}
		
		return mapping.findForward("area_list");
	}
	
	/**
	 * ����ѡ���ɫҳ��
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String u_pk = (String)session.getAttribute("uPk");
		if( u_pk==null )//���uPkΪ�����µ�½
		{
			return mapping.findForward("login_index");
		}
		PlayerService playerService = new PlayerService();
		IntimateHintService intimateHintService = new IntimateHintService();
		
		IntimateHintVO intimateHint = intimateHintService.getRandomIntimateHint();
		List<PartInfoVO> role_list = playerService.getRoleList(u_pk);
		//��̬����
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
	 * ��½��ɫ��������Ϸҳ��
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		session.setAttribute("PreviourFile","");
		
		String u_pk = (String)session.getAttribute("uPk");
		//�ж��˺��Ƿ��ѵ�½
		if(u_pk==null )//���uPkΪ�����µ�½
		{
			return mapping.findForward("login_index");
		}
		
		PlayerEnvelopService ps = new PlayerEnvelopService();
		PlayerService playerService = new PlayerService();
		
		String pPk = request.getParameter("pPk");
		
		//�жϽ�ɫ�Ƿ�ɾ��
		PartInfoDao infoDao = new PartInfoDao(); 	
		int delete_flag = infoDao.getDeleteState(pPk); 
		if ( delete_flag == 1) 
		{
			request.setAttribute("display", "�Բ��𣬸ý�ɫ�Ѿ�ɾ��,��Ҫ�������Ȼָ�!"); 
			return mapping.findForward("envelop");
		}
		
		//�ж�����Ƿ񱻷��
		boolean envelop = ps.getPlayerEnvelopForever(Integer.parseInt(pPk));
		if(envelop == true)
		{
			request.setAttribute("display", "�Բ��𣬸ý�ɫ��Υ����Ϸ�涨���������÷�ͣ����"); 
			return mapping.findForward("envelop");
		}
		//�ж��Ƿ񱻷��
		String time = ps.getPlayerEnvelop(Integer.parseInt(pPk));
		if( time!=null )
		{
			//�����
			request.setAttribute("display", "�Բ��𣬸ý�ɫ��Υ����Ϸ�涨�����Է�ͣ����,����ʱ��"+time+"����!"); 
			return mapping.findForward("envelop");
		}
		
		
		//�ж��Ƿ��ǵ����û�
		CheckPcRequestService checkPcRequestService = new CheckPcRequestService();
		String user_name = (String)session.getAttribute("user_name"); 
		String userAgent = request.getHeader("user-agent");
		String ip=request.getRemoteAddr();
		String hint = checkPcRequestService.isLoginException(user_name, u_pk, ip, userAgent);
		if(hint != null)
		{
			//����ǵ����û�
			request.setAttribute("display", hint); 
			return mapping.findForward("envelop");
		}
		
		
		//********************ֱ�ӽ�����Ϸ
    	//��½��Ϸ
    	LoginService loginService = new LoginService();
    	RoleEntity role_info = RoleService.getRoleInfoById(pPk);
    	
    	//�жϽ�ɫ�Ƿ񻹴���PK״̬
		if ( role_info.getPKState().getOtherNum()>0) 
		{
			return this.dispath(request, response, "/pk.do?cmd=n7");
		}
    	
    	int player_state_by_new = role_info.getBasicInfo().getPlayer_state_by_new();//��½ǰ�õ��Ƿ������ֵ�״̬
    	
    	loginService.loginRole(pPk, request);//��½����
    	
    	if( player_state_by_new==1 )
    	{
    		//���������һ����������
    		return super.dispath(request, response, "/guide.do?step=end_cartoon1");
    	}
    	
    	
    	//�����û����⴦��
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
    				
    	/*��������ϵͳ��Ϣ��ʾ
    	 * ClewService clewService = new ClewService();
    	clewService.loginClew(pPk);*/
    				
    	int oneline_time = playerService.getOnlineTimeInThisWeek(pPk);//��������ʱ��
    	request.setAttribute("role_name", role_info.getName());
    	request.setAttribute("oneline_time", oneline_time+"");
    	return mapping.findForward("enter_game");
	}
	
	/**
	 * ��ʾ��������
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		if( request.getSession().getAttribute("uPk")==null )//���uPkΪ�����µ�½
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
			if( step.equals("1") )//��һ������
			{
				return mapping.findForward("kongzhong");
			}
			if( step.equals("2") )//ѡ������
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
			if( step.equals("1") )//��һ������
			{
				return mapping.findForward("cartoon1");
			}
			else if( step.equals("2") )//�ڶ�������
			{
				return mapping.findForward("cartoon2");
			}
			else if( step.equals("3") )//����������
			{
				return mapping.findForward("cartoon3");
			}
			else if( step.equals("4") )//����ѡ���ֵĻ���
			{
				return mapping.findForward("new_player_choose");
			}
			else if( step.equals("5") )//ѡ������
			{
				String u_name = (String)request.getAttribute("ssid");
				if(u_name == null){
					role_info.getBasicInfo().updatePlayer_state_by_new(1);
				}else{
				if(Channel.SINA == GameConfig.getChannelId()){
					if(u_name.indexOf("visitor") != -1){//�����ο�����
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
	 * ������Ϸ����
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if( request.getSession().getAttribute("uPk")==null )//���uPkΪ�����µ�½
		{
			return mapping.findForward("login_index");
		}
		request.getSession().setAttribute("PreviourFile","");
		if(Channel.SINA == GameConfig.getChannelId()){
			
		}
		RoleEntity role_info = this.getRoleEntity(request);
		if(role_info==null )//���uPkΪ�����µ�½
		{
			return mapping.findForward("kick_outline_hint");
		}
		if(Channel.SINA == GameConfig.getChannelId()){
			
		}
		return mapping.findForward("logintransmit");
	}
	
	/**
	 * ����ע���˺�ҳ��
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
	 * ע���˺�ȷ���ύ
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String user_name = request.getParameter("userName");
		String pwd = request.getParameter("paw");
		String lid = request.getParameter("lid");
		
		ValidateService validateService = new ValidateService();
		
		String hint = validateService.validateRegisterUsernameAndPwd(user_name, pwd);
		
		if( hint==null )//ע��ɹ�
		{
			LoginService loginServic = new LoginService();
			
			String login_ip = request.getRemoteAddr();
			
			int u_pk = loginServic.register(user_name, pwd,login_ip);
			
			if(lid!=null&&!lid.equals("")){
				loginServic.insertPlayerSta(u_pk, lid);//ͳ����ҵ�����ע����Ϣ
			}
			
			request.getSession().setAttribute("uPk", u_pk+"");
			request.setAttribute("lid", lid);
			request.setAttribute("user_name", user_name);
			request.setAttribute("pwd", pwd);
			
			return mapping.findForward("register_success");
		}
		else//ע��ʧ��
		{
			request.setAttribute("hint", hint);
			request.setAttribute("lid", lid);
			return mapping.findForward("register_index");
		}
	}
	/**
	 * ����3�ε�������ֱ�ӷ��ص�ҳ��
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("login_error_page");
	}
	
	/** 
	 * ���Ե�½ҳ��  ��������ʹ��ҳ��
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
			return n11(mapping, form, request, response);//��½�ɹ���ֱ�ӽ���ѡ���ɫҳ��
				}
			}
		}
		
		ValidateService validateService = new ValidateService();
		hint = validateService.validateUserName(name);
		
		if( hint !=null )//�û����Ϸ�����֤ʧ��
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("login_fail");
		}
		
		hint = validateService.validatePwd(pwd);
		
		if( hint !=null )//����Ϸ�����֤ʧ��
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("login_fail");
		}
		LoginInfoVO loginInfo = loginService.validateLogin(name, pwd);
		if( loginInfo !=null )//�˺�������֤�ɹ�����½�ɹ�
		{
			loginService.login(loginInfo.getUPk() + "", request.getRemoteAddr());
			request.getSession().setAttribute("uPk", loginInfo.getUPk() + "");
			//return mapping.findForward("area_list");
			loginService.login(loginInfo.getUPk() + "", request.getRemoteAddr());
			request.getSession().setAttribute("user_name", name);
			return n11(mapping, form, request, response);//��½�ɹ���ֱ�ӽ���ѡ���ɫҳ��
		} 
		else
		{
			hint = "�˺Ż����벻�ԣ�";
			request.setAttribute("hint", hint); 
			return mapping.findForward("login_fail");
		}
	}
	
	
	/**
	 * ����ѡ���ɫҳ��
	 */
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String u_pk = (String)session.getAttribute("uPk");
		if( u_pk==null )//���uPkΪ�����µ�½
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
		
		request.getSession().setAttribute("channel_id", Channel.DEFAULT+"");//Ĭ������
		
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