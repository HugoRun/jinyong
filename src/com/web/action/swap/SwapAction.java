package com.web.action.swap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.dao.sellinfo.SellInfoDAO;
import com.ben.vo.communion.CommunionVO;
import com.ben.vo.sellinfo.SellInfoVO;
import com.ls.ben.cache.dynamic.manual.chat.ChatInfoCahe;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.Wrap;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.pet.PetService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.UMsgService;
import com.pm.service.mail.MailInfoService;
import com.pub.ben.info.Expression;
import com.web.service.friend.BlacklistService;

/**
 * @author Administrator 交易
 */
public class SwapAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");

	/**
	 * 金钱交易
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pByPk = request.getParameter("pByPk");
		String pSilver = request.getParameter("pSilver");// 银
		if(pSilver.length() > 8){
			this.setHint(request, "请输入正确金钱格式");
			request.setAttribute("pByPk", pByPk);
			return mapping.findForward("moneyswapno");
		}
		 Pattern p = Pattern.compile(Expression.positive_integer_contain0_regexp);
		 Matcher m = p.matcher(pSilver);
		 boolean b = m.matches();
		 if(b==false){
				this.setHint(request, "请输入正确金钱格式");
				request.setAttribute("pByPk", pByPk);
				return mapping.findForward("moneyswapno");
		 }
		 
		if(pSilver == null || pSilver.equals("") || Integer.parseInt(pSilver) == 0){
			pSilver = "0";
		}
		PartInfoDAO partInfoDAO = new PartInfoDAO();
		String name =  partInfoDAO.getPartName(pByPk);
		request.setAttribute("name", name);
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pSilver", pSilver);
		return mapping.findForward("moneyswappage");
	}

	/**
	 * 金钱交易
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String pByPk = request.getParameter("pByPk");
		String pSilver = request.getParameter("pSilver");// 银

		//判断玩家是否在线
		RoleEntity roleInfo1pByuPk = roleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk == null || roleInfo1pByuPk.isOnline()==false ){
			try{
				String hint = "该玩家已下线!";
				if(hint != null ){
					request.getRequestDispatcher("/pubbuckaction.do?hint="+hint).forward(request, response);
					return null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		String hint = null;
		if(pSilver.length() > 8){
			hint = "请输入正确金钱格式";
			request.setAttribute("hint", hint);
			request.setAttribute("pByPk", pByPk);
			return mapping.findForward("moneyswapno");
		}
		
		// 如果银子和铜钱都为空 则默认为0
		
		String yingzi = null;
		
		 Pattern p = Pattern.compile(Expression.positive_integer_contain0_regexp);
		 Matcher m = p.matcher(pSilver);
		 boolean b = m.matches();
		 if(b==false){
			    hint = "请输入正确金钱格式";
				request.setAttribute("hint", hint);
				request.setAttribute("pByPk", pByPk);
				return mapping.findForward("moneyswapno");
		 }
		 
		if (pSilver.indexOf(" ") != -1 || pSilver == null || pSilver.equals(""))
		{
			yingzi = "0";
		}
		else
		{
			yingzi = request.getParameter("pSilver");
		}
		// 判断金钱是否够
		String money = roleInfo.getBasicInfo().getCopper()+"";
		if (Long.parseLong(money) < (Long.parseLong(yingzi)))
		{
			hint = "您没有足够的钱！";
			request.setAttribute("hint", hint);
			request.setAttribute("pByPk", pByPk);
			return mapping.findForward("moneyswapno");
		}
		// 创建时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量

		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		
		sellInfoDAO.addSelleInfo(roleInfo.getBasicInfo().getPPk() + "", pByPk, yingzi,"0", SellInfoVO.SELLMONEY, Time);
		
		//在这里插入弹出式消息内容
		UMsgService uMsgService = new UMsgService();
		UMessageInfoVO msgInfo = new UMessageInfoVO();
		msgInfo.setMsgType(PopUpMsgType.MESSAGE_SWAP);
		msgInfo.setPPk(Integer.parseInt(pByPk));
		msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_SWAP_FIRST);
		uMsgService.sendPopUpMsg(msgInfo);
		
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pSilver", pSilver);
		return mapping.findForward("moneyswap");
	}

	/**
	 * 查看要交易的装备详情
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		GoodsService goodsService = new GoodsService();
		 
		String pwPk = request.getParameter("pwPk");
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip,true);
		
		request.setAttribute("equip_display", equip_display);
		request.setAttribute("equip", equip);
		request.setAttribute("pByPk", request.getParameter("pByPk"));
		return mapping.findForward("goodspage");
	}

	/**
	 * 物品交易
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		String pByPk = request.getParameter("pByPk");
		String pwPk = request.getParameter("pwPk");
		String wName = request.getParameter("wName");
		String wProtect = request.getParameter("wProtect");
		String bangding = request.getParameter("bangding");

		request.setAttribute("wProtect", wProtect);
		request.setAttribute("bangding", bangding);
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pwPk", pwPk);
		request.setAttribute("wName", wName);
		return mapping.findForward("goodsmoney");
	}

	/**
	 * 物品交易
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String pByPk = request.getParameter("pByPk");
		String pwPk = request.getParameter("pwPk");
		String wName = request.getParameter("wName");
		String pSilver = request.getParameter("pSilver");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		
		//判断玩家是否在线
		RoleEntity roleInfo1pByuPk = roleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk == null || roleInfo1pByuPk.isOnline()==false ){
			try{
				String hint = "该玩家已下线!";
				if(hint != null ){
					request.getRequestDispatcher("/pubbuckaction.do?hint="+hint).forward(request, response);
					return null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String hint = null;
		
		if(pSilver.length() > 8){
			 hint = "请输入正确金钱格式";
			    request.setAttribute("hint", hint);
				return mapping.findForward("goodsmoneyok");
		}
		
		 Pattern p = Pattern.compile(Expression.positive_integer_contain0_regexp);
		 Matcher m = p.matcher(pSilver);
		 boolean b = m.matches();
		 if(b==false){
			    hint = "请输入正确金钱格式";
			    request.setAttribute("hint", hint);
				return mapping.findForward("goodsmoneyok");
		 }
		 
		if(pSilver == null || pSilver.equals("") || Integer.parseInt(pSilver) == 0){
			pSilver = "0";
		}
		/*
		 * 药品-----1 书卷-----2 装备-----3 任务-----4 其他-----5
		 */
		
		//判断是否重复交易
		SellInfoDAO dao = new SellInfoDAO();
		String ss = dao.getSellExistByPPkAndGoodsId(roleInfo.getBasicInfo().getPPk()+"", pwPk, Wrap.EQUIP);
		if(ss != null && !ss.equals("")){
			hint = ss;
			request.setAttribute("hint", hint);
			return mapping.findForward("goodsmoneyok");
		}
		
		int number = 1;
		GoodsService goodsService = new GoodsService();
		PartInfoDAO partInfoDAO = new PartInfoDAO();
		if(goodsService.isEnoughWrapSpace(Integer.parseInt(pByPk),number)){//够了
			
			dao.getSellArmAdd(roleInfo.getBasicInfo().getPPk(), pByPk, pwPk, Wrap.EQUIP, number, pSilver, "0", SellInfoVO.SELLARM, Time);
			//dao.getSellWuPingAdd(pwPk, Wrap.ACCOUTER, number, pSilver, pCopper, SfOk, userTempBean.getPPk(), pByPk);
			hint = "您已经与"+partInfoDAO.getPartName(pByPk)+"交易了装备"+wName+",请等待对方接收！";
			//在这里插入弹出式消息内容
			UMsgService uMsgService = new UMsgService();
			UMessageInfoVO msgInfo = new UMessageInfoVO();
			msgInfo.setMsgType(PopUpMsgType.MESSAGE_SWAP);
			msgInfo.setPPk(Integer.parseInt(pByPk));
			msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_SWAP_FIRST);
			uMsgService.sendPopUpMsg(msgInfo);
			
		}else{
			hint = partInfoDAO.getPartName(pByPk)+"没有足够的包裹格数!";
		} 
		request.setAttribute("hint", hint);
		return mapping.findForward("goodsmoneyok");
	}

	/**
	 * 查看玩家信息
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pPks = request.getParameter("pPks");
		//返回地图主界面
		request.getSession().setAttribute("backtype", "1");
		request.setAttribute("pPks", pPks);
		return mapping.findForward("partinfoview");
	}

	/**
	 * 玩家信息密语
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");

		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		return mapping.findForward("privately");
	}

	/**
	 * 玩家信息密语
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		String pByPk = request.getParameter("pByPk");
		
		String pNameBy = request.getParameter("pByName"); 
		String upTitle = request.getParameter("upTitle");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		PartInfoDAO dao = new PartInfoDAO(); 
		PlayerService playerService = new PlayerService();
		String type = "5";
		BlacklistService blacklistService = new BlacklistService();
		int res = blacklistService.isBlacklist(Integer.parseInt(pByPk), roleInfo.getPPk());
		if(res == 2){
			this.setHint(request,"该玩家在您的黑名单中,您不能与他(她)进行密聊.");
			return mapping.findForward("privatelyok");
		}else if(res == 1){
			this.setHint(request, "您在该玩家的黑名单中,您不能与他(她)进行密聊.");
			return mapping.findForward("privatelyok");
		}
		RoleEntity pByPk_b_role_info = RoleService.getRoleInfoById(pByPk+"");
		
		if(pByPk_b_role_info != null){
		String hint = playerService.checkRoleState(Integer.parseInt(pByPk), PlayerState.TALK);
		if(hint != null){
			MailInfoService mailInfoService = new MailInfoService();
			String mailtitle = "来自好友"+roleInfo.getBasicInfo().getName() +"的密语";//7月27日修改
			mailInfoService.sendPersonMail(Integer.parseInt(pByPk), roleInfo.getBasicInfo().getPPk(), mailtitle, upTitle);
			this.setHint(request,hint+",系统已经帮您转发至他(她)的邮箱中!");
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("privatelyok");
		}
		} 
		 /** TODO:如果有该玩家 如果没有在线则直接发送玩家邮箱 */ 
		    int bypPk=dao.getPartPk(pNameBy); 
			// 得到当前玩家信息
			if(pByPk_b_role_info!=null && pByPk_b_role_info.isOnline()==true){
				// 执行插入公共聊天记录 c_pk,p_pk,p_name,p_pk_by,p_name_by,c_title,c_type,create_time
				CommunionVO communionVO = new CommunionVO();
				communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
				communionVO.setPName(roleInfo.getBasicInfo().getName());
				communionVO.setPPkBy(Integer.parseInt(pByPk));
				communionVO.setPNameBy(pNameBy);
				communionVO.setCTitle(upTitle);
				communionVO.setCType(Integer.parseInt(type));
				communionVO.setCreateTime(Time);
				ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
				publicChatInfoCahe.put(communionVO);
				
				this.setHint(request, "信息已经发出！");
				request.setAttribute("pByPk", pByPk);
				return mapping.findForward("privatelyok");
			}else { 
				this.setHint(request, "对方不在线将通过邮件发送给对方");
				request.setAttribute("pByPk", pByPk);
				MailInfoService mailInfoService = new MailInfoService();
				String mailtitle = "来自好友"+roleInfo.getBasicInfo().getName() +"的密语";//7月27日修改
				mailInfoService.sendPersonMail(bypPk, roleInfo.getBasicInfo().getPPk(), mailtitle, upTitle);
				return mapping.findForward("privatelyok"); 
			}   
	}
	
	/**
	 * 宠物交易 
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession()); 
		
		String pByPk = request.getParameter("pByPk"); 
		PetInfoDAO dao = new PetInfoDAO();
		//判断玩家是否是在自己的黑名单  
		BlacklistService blacklistService = new BlacklistService();
		int res = blacklistService.isBlacklist(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(pByPk));
		if(res == 1){
			String hints = "该玩家在您的黑名单中,您不能与他(她)进行交易.";
			request.setAttribute("hints", hints);
			return mapping.findForward("petblacklisthint");
		}else if(res == 2){
			String hints = "您在该玩家的黑名单中,您不能与他(她)进行交易."; 
			request.setAttribute("hints", hints);
			return mapping.findForward("petblacklisthint");
		}
		//判断对方玩家是否开交易开关 
		PlayerService playerService = new PlayerService();
		String hint = playerService.isRoleState( Integer.parseInt(pByPk), 1);
		if(hint != null ){ 
			request.setAttribute("hints", hint);
			return mapping.findForward("petblacklisthint");
		} 
		
		int petIsBring = 0;
	    List list = dao.getpetIsBringList(roleInfo.getBasicInfo().getPPk(),petIsBring); 
	    request.setAttribute("list", list);
		request.setAttribute("pByPk", pByPk); 
		return mapping.findForward("petsells");
	}
	
	/**
	 * 宠物交易
	 */
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{  
		
		String pByPk = request.getParameter("pByPk");
		String petPk = request.getParameter("petPk"); 
		String petId = request.getParameter("petId");
		PetService petService = new PetService(); 
		String resultWml = petService.getPetDisplayWml(Integer.parseInt(petPk));
		request.setAttribute("resultWml", resultWml);
		request.setAttribute("pByPk", pByPk); 
		request.setAttribute("petPk", petPk); 
		request.setAttribute("petId", petId); 
		return mapping.findForward("petsellsview");
	}
	
	/**
	 * 宠物交易
	 */
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession()); 
		
		String pByPk = request.getParameter("pByPk");
		String petPk = request.getParameter("petPk");
		//查找宠物的寿命是否为零了
		String hint = null;
		PetInfoDAO dao = new PetInfoDAO();
		int pet_longe = dao.pet_longe(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(petPk));
		if(pet_longe == 0){
			hint = "您的宠物寿命不足，不能进行交易。";
		}
		request.setAttribute("pByPk", pByPk); 
		request.setAttribute("petPk", petPk);
		request.setAttribute("hint", hint);  
		return mapping.findForward("petsellsok");
	}
	/**
	 * 宠物交易
	 */
	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession()); 
		
		String pByPk = request.getParameter("pByPk");
		String petPk = request.getParameter("petPk");  
		String pSilver = request.getParameter("pSilver");  
		
		//判断玩家是否在线
		RoleEntity roleInfo1pByuPk = RoleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk == null || roleInfo1pByuPk.isOnline()==false ){
			try{
				String hint = "该玩家已下线!";
				if(hint != null ){
					request.getRequestDispatcher("/pubbuckaction.do?hint="+hint).forward(request, response);
					return null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String hint = null;
		if(pSilver.length() > 8){
			hint = "请输入正确金钱格式";
		    request.setAttribute("hint", hint);
		    return mapping.findForward("petsellsokyew");
		}
				
		 Pattern p = Pattern.compile(Expression.positive_integer_contain0_regexp);
		 Matcher m = p.matcher(pSilver);
		 boolean b = m.matches();
		 if(b==false){
			    hint = "请输入正确金钱格式";
			    request.setAttribute("hint", hint);
			    return mapping.findForward("petsellsokyew");
		 }
		 
		  
		if(pSilver == null || pSilver.equals("") || Integer.parseInt(pSilver) == 0){
			pSilver = "0";
		}
		PetInfoDAO dao = new PetInfoDAO();
		dao.getPetSellAdd(roleInfo.getBasicInfo().getPPk()+"",pByPk,petPk,pSilver,"0",Time);
		PetInfoDAO daoss = new PetInfoDAO();
		String pet_name = daoss.pet_name(Integer.parseInt(petPk));
		PartInfoDAO daoq = new PartInfoDAO(); 
		hint = "您已经将宠物"+pet_name+"交易给"+daoq.getPartName(pByPk)+"";
		
		//在这里插入弹出式消息内容
		UMsgService uMsgService = new UMsgService();
		UMessageInfoVO msgInfo = new UMessageInfoVO();
		msgInfo.setMsgType(PopUpMsgType.MESSAGE_SWAP);
		msgInfo.setPPk(Integer.parseInt(pByPk));
		msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_SWAP_FIRST);
		uMsgService.sendPopUpMsg(msgInfo);
		
		request.setAttribute("hint", hint);
		return mapping.findForward("petsellsokyew");
	}
	
	
	/**
	 * 查看玩家信息
	 */
	public ActionForward n13(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pPks = request.getParameter("pPks");
		RoleEntity other = RoleService.getRoleInfoById(pPks);
		RoleEntity me = this.getRoleEntity(request);
		
		String backtype = request.getParameter("backtype");
		if( backtype!=null )
		{
			request.getSession().setAttribute("backtype", backtype);
		}
		
		request.setAttribute("other", other);
		request.setAttribute("me", me);
		return mapping.findForward("partinfoview");
	}
	
	
	/**
	 * 查看玩家信息
	 */
	public ActionForward n14(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String uPks = request.getParameter("uPks");
		String pPks = request.getParameter("pPks");
		//返回聊天界面
		request.getSession().setAttribute("backtype", "3");
		request.setAttribute("uPks", uPks);
		request.setAttribute("pPks", pPks);
		return mapping.findForward("partinfoview");
	}
	
}
