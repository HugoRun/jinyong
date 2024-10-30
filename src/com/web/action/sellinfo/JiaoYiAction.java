package com.web.action.sellinfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.dao.sellinfo.SellInfoDAO;
import com.ben.vo.sellinfo.SellInfoVO;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.GoodsControlVO;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.ActionType;
import com.ls.pub.constant.BondingType;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.Wrap;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.UMsgService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.pm.service.systemInfo.SystemInfoService;
import com.pub.ben.info.Expression;
import com.web.service.TaskService;
import com.web.service.friend.BlacklistService;

/**
 * @author Administrator 交易
 */
public class JiaoYiAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");
	/**
	 * 跳转交易页面
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		PlayerService playerService = new PlayerService();
		
		RoleEntity roleInfo = this.getRoleEntity(request);
		//判断自己的交易开关时关闭的则不可交易
		if(roleInfo.getSettingInfo().getDealControl()==-1)
		{
			String hint="您的交易开关是关闭状态不可交易!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair"));
		}
		//如果是新手
		if( roleInfo.getBasicInfo().getPlayer_state_by_new()==1)
		{
			this.setHint(request, "你现在处在新手引导状态,无法交易");
			return mapping.findForward("return_hint");
		}
		
		String pByPk = request.getParameter("pByPk");// 被请求人ID
		//判断玩家是否在线
		RoleEntity roleInfo1pByuPk = RoleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk.isOnline()==false){
			String hint = "该玩家已下线!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint);
		}
		//判断玩家是否是在自己的黑名单
		BlacklistService blacklistService = new BlacklistService();
		int res = blacklistService.isBlacklist(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(pByPk));
		if(res == 1){
			String hints = "该玩家在您的黑名单中,您不能与他(她)进行交易.";
			request.setAttribute("hints", hints);
			return mapping.findForward("blacklisthint");
		}else if(res == 2){
			String hints = "您在该玩家的黑名单中,您不能与他(她)进行交易.";
			request.setAttribute("hints", hints);
			return mapping.findForward("blacklisthint");
		}
		
		//判断玩家B的状态是否可以进行交易
		String hint = playerService.checkRoleState( Integer.parseInt(pByPk), PlayerState.TRADE);
		if(hint != null ){
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair"));
		}
		
		//判断对方玩家是否开交易开关
		hint = playerService.isRoleState( Integer.parseInt(pByPk), 1);
		if(hint != null ){
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair"));
		}
		if(roleInfo.getPPk()==Integer.parseInt(pByPk))
		{
			String hints="不可以和自己交易！";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hints);
		}
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("jiaiyipage");
	}
	/**
	 * 赠送银两
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String hint = (String)request.getAttribute("hint");
		String pByPk = request.getParameter("pByPk");// 被请求人ID
		//判断玩家是否在线
		RoleEntity me = this.getRoleEntity(request);
		
		RoleEntity other = RoleService.getRoleInfoById(pByPk);
		if(other.isOnline()==false){
			try{
				String hints = "该玩家已下线!";
				request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
				return null;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		BlacklistService blacklistService = new BlacklistService();
		int res = blacklistService.isBlacklist(me.getPPk(), Integer.parseInt(pByPk));
		if(res == 1){
			try{
			String hints = "该玩家在您的黑名单中,您不能与他(她)进行交易.";
			request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
			return null;
			}
			catch(Exception e){
			e.printStackTrace();
		}
		}else if(res == 2){
			try{
			String hints = "您在该玩家的黑名单中,您不能与他(她)进行交易.";
			request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
			return null;
		
				}catch(Exception e){
				e.printStackTrace();
				}
		}
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("me", me);
		request.setAttribute("other", other);
		request.setAttribute("hint", hint);
		return mapping.findForward("zengsongqianpage");
	}
	
	/**
	 * 赠送物品
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity me = this.getRoleEntity(request);
		
		int p_pk = me.getPPk();
		
		String pByPk = request.getParameter("pByPk");//被请求人的ID
		RoleEntity other = RoleService.getRoleInfoById(pByPk);
		if(other.isOnline()==false)
		{
			String hint = "该玩家已下线!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint);
		}
		String w_type_str = request.getParameter("w_type");
		if (w_type_str == null)
		{
			w_type_str = (String) request.getAttribute("w_type");
		}
		if (w_type_str == null)
		{
			w_type_str = "3";
		}
		String page_no_str = request.getParameter("page_no");
		if (page_no_str == null)
		{
			page_no_str = (String) request.getAttribute("page_no");
		}

		int w_type=Wrap.EQUIP;;
		int page_no=1;

		if (w_type_str!= null)
		{
			w_type = Integer.parseInt(w_type_str);
		}

		if (page_no_str != null)
		{
			page_no = Integer.parseInt(page_no_str);
		}

		 
		String path = request.getServletPath();
		request.setAttribute("path", path);

		GoodsService goodsSerivce = new GoodsService();

		QueryPage item_page = null;
		switch(w_type)
		{
			case Wrap.CURE:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.CURE,page_no);break;
			case Wrap.BOOK:item_page = goodsSerivce.getPagePropList(p_pk,Wrap.BOOK, page_no);break;
			case Wrap.EQUIP:item_page = goodsSerivce.getPageSaleEquipOnWrap(p_pk, page_no);break;
			case Wrap.TASK:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.TASK,page_no);break;
			case Wrap.REST:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.REST,page_no);break;
			case Wrap.SHOP:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.SHOP,page_no);break;
		}
		
		item_page.setURL(response, "/jiaoyi.do?cmd=n3&amp;w_type="+w_type+"&amp;pByPk="+pByPk);
		
		request.setAttribute("item_page", item_page);
		request.setAttribute("w_type", w_type_str);
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("zengsonggoodspage");
	}
	
	/**
	 * 道具赠送
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession()); 
		
		String w_type = request.getParameter("w_type");
		String pg_pk = request.getParameter("pg_pk");
		String goods_id = request.getParameter("goods_id");
		
		String pByPk = request.getParameter("pByPk");
		
		//判断玩家是否在线
		RoleEntity roleInfo1pByuPk = roleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk.isOnline()==false){
			String hint = "该玩家已下线!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint);
		}
		
		BlacklistService blacklistService = new BlacklistService();
		int res = blacklistService.isBlacklist(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(pByPk));
		if(res == 1){
			try{
			String hints = "该玩家在您的黑名单中,您不能与他(她)进行交易.";
			request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
			return null;
			}
			catch(Exception e){
			e.printStackTrace();
		}
		}else if(res == 2){
			try{
			String hints = "您在该玩家的黑名单中,您不能与他(她)进行交易.";
			request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
			return null;
		
				}catch(Exception e){
				e.printStackTrace();
				}
		}
		
		if (goods_id == null || w_type == null || pg_pk == null) {
			////System.out.print("goods_id或w_type或pg_pk为空");
		}
		String goods_display = null;
		GoodsService goodsService = new GoodsService(); 
		goods_display = goodsService.getPropInfoWmlMai(roleInfo.getBasicInfo().getPPk(),Integer.parseInt(goods_id)); 
		String goodsName = PropCache.getPropNameById(Integer.parseInt(goods_id));
		request.setAttribute("w_type", w_type);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("goodsName", goodsName);
		request.setAttribute("goods_display", goods_display);
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("zengsonggoodsview");
	} 
	
	/**
	 * 道具赠送
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = this.getRoleEntity(request);
		String goods_id = request.getParameter("goods_id");
		String pg_pk = request.getParameter("pg_pk");
		String goodsName = request.getParameter("goodsName");
		String pByPk = request.getParameter("pByPk");
		String w_type = request.getParameter("w_type");
		//判断玩家是否在线
		RoleEntity roleInfo1pByuPk = roleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk.isOnline()==false){
			String hint = "该玩家已下线!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint);
		}
		
		BlacklistService blacklistService = new BlacklistService();
		int res = blacklistService.isBlacklist(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(pByPk));
		if(res == 1){
			try{
			String hints = "该玩家在您的黑名单中,您不能与他(她)进行交易.";
			request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
			return null;
			}
			catch(Exception e){
			e.printStackTrace();
		}
		}else if(res == 2){
			try{
			String hints = "您在该玩家的黑名单中,您不能与他(她)进行交易.";
			request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
			return null;
		
				}catch(Exception e){
				e.printStackTrace();
				}
		}
		request.setAttribute("w_type", w_type);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goodsName", goodsName);
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("zengsonggoodsnumber");
	} 
	
	/**
	 * 道具交易
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		//如果是新手
		if( roleInfo.getBasicInfo().getPlayer_state_by_new()==1)
		{
			this.setHint(request, "你现在处在新手引导状态,无法交易");
			return mapping.findForward("return_hint");
		}
		
		String goods_id = request.getParameter("goods_id");
		String pg_pk = request.getParameter("pg_pk");
		String goodsName = request.getParameter("goodsName");
		String pByPk = request.getParameter("pByPk");
		String w_type = request.getParameter("w_type");
		String pSilver = "0";
		String pCopper = "0";
		String number = request.getParameter("number");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		
		//判断玩家是否在线
		RoleEntity roleInfo1pByuPk = roleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk.isOnline()==false){
			String hint = "该玩家已下线!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint);
		}
		 String hint = null;
		if(number.length() > 3){
			 hint = "请输入正确数字!";
			    request.setAttribute("hint", hint);
				request.setAttribute("w_type", w_type);
				request.setAttribute("pByPk", pByPk); 
				return mapping.findForward("zengsongpropok");
		}
		 Pattern sum1 = Pattern.compile(Expression.positive_integer_contain0_regexp);
		 Matcher sum2 = sum1.matcher(number);
		 boolean sum3 = sum2.matches();
		 if(sum3==false){
			    hint = "请输入正确数字!";
			    request.setAttribute("hint", hint);
				request.setAttribute("w_type", w_type);
				request.setAttribute("pByPk", pByPk); 
				return mapping.findForward("zengsongpropok");
		 }
		 
		if(number == null || number.equals("") || Integer.parseInt(number) == 0){
			hint = "请输入您要赠送的物品数量";
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("zengsongpropok");
		}
		//判断是否重复交易
		SellInfoDAO dao = new SellInfoDAO();
		String ss = dao.getSellExistByPPkAndGoodsId(roleInfo.getBasicInfo().getPPk()+"", goods_id, GoodsType.PROP);
		if(ss != null && !ss.equals("")){
			hint = ss;
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("zengsongpropok");
		}
		
		//判断道具数量够不够
		GoodsService goodsService = new GoodsService();
		hint = goodsService.isBinded(Integer.parseInt(pg_pk), GoodsType.PROP, ActionType.EXCHANGE);
		if(hint != null){
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("zengsongpropok");
		}
		if (goodsService.getPropNum(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(goods_id)) < Integer.parseInt(number)) {
			hint = "您没有"+number+"个"+goodsName+"";
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("zengsongpropok");
		}

		PartInfoDAO partInfoDAO = new PartInfoDAO(); 
		String name =null;
		try
		{
			name = partInfoDAO.getPartName(pByPk);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		
		int s = goodsService.isPropToWrap(Integer.parseInt(pByPk), Integer.parseInt(goods_id), Integer.parseInt(number));
		if(s != -1){ 
			dao.getSellArmAdd(roleInfo.getBasicInfo().getPPk(), pByPk, goods_id, GoodsType.PROP, Integer.parseInt(number), pSilver, pCopper, SellInfoVO.ZENGSONGPROP, Time);
			hint = "你赠送给了"+name+number+"个"+goodsName+",请等待对方接收！";
			//在这里插入弹出式消息内容
			UMsgService uMsgService = new UMsgService();
			UMessageInfoVO msgInfo = new UMessageInfoVO();
			msgInfo.setMsgType(PopUpMsgType.MESSAGE_SWAP);
			msgInfo.setPPk(Integer.parseInt(pByPk));
			msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_SWAP_FIRST);
			uMsgService.sendPopUpMsg(msgInfo);
			
		}else{
			hint = name+"没有足够的包裹格数!";
		}
		request.setAttribute("hint", hint);
		request.setAttribute("w_type", w_type);
		request.setAttribute("pByPk", pByPk); 
		return mapping.findForward("zengsongpropok");
	} 
	
	/**
	 * 物品交易
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String pByPk = request.getParameter("pByPk");
		String pwPk = request.getParameter("pwPk");
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip,false);
		
		request.setAttribute("equip_display", equip_display);
		request.setAttribute("equip", equip);
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("zengsongequipview");
	}
	
	/**
	 * 物品交易
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
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
		return mapping.findForward("zengsongequipnumber");
	}
	
	/**
	 * 装备交易
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		PartInfoDAO partInfoDAO = new PartInfoDAO();
		
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		//如果是新手
		if( roleInfo.getBasicInfo().getPlayer_state_by_new()==1)
		{
			this.setHint(request, "你现在处在新手引导状态,无法交易");
			return mapping.findForward("return_hint");
		}
		
		String pByPk = request.getParameter("pByPk");
		String pwPk = request.getParameter("pwPk");
		String wName = request.getParameter("wName");
		
		String pSilver = "0";
		String pCopper = "0"; 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		//判断玩家是否在线
		RoleEntity roleInfo1pByuPk = RoleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk.isOnline()==false){
			String hint = "该玩家已下线!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint);
		}
		
		 String hint = null; 
		//判断是否重复交易
		SellInfoDAO dao = new SellInfoDAO();
		String ss = dao.getSellExistByPPkAndGoodsId(roleInfo.getBasicInfo().getPPk()+"", pwPk, Wrap.EQUIP);
		if(ss != null && !ss.equals("")){
			hint = ss;
			request.setAttribute("pByPk", pByPk);
			request.setAttribute("hint", hint);
			return mapping.findForward("zengsongequipok");
		}
		int number = 1;
		
		if(goodsService.isEnoughWrapSpace(Integer.parseInt(pByPk),number)){//够了
			dao.getSellArmAdd(roleInfo.getBasicInfo().getPPk(), pByPk, pwPk, Wrap.EQUIP, number, pSilver, pCopper, SellInfoVO.ZENGSONGARM, Time);
			hint = "您赠送给了"+partInfoDAO.getPartName(pByPk)+wName+",请等待对方接收！";
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
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("hint", hint);
		return mapping.findForward("zengsongequipok");
	}
	
	/**
	 * 装备交易查看
	 */
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String pwPk = request.getParameter("pwPk");
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip,true);
		request.setAttribute("sPk", request.getParameter("sPk"));
		request.setAttribute("equip_display", equip_display);
		return mapping.findForward("armview");
	}
	
	/**
	 * 装备交易
	 */
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		String hint = null;
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		PartInfoDAO daos = new PartInfoDAO();
		if(playerEquipDao.isHaveById(vo.getSWuping()) == false){
			hint = daos.getPartName(vo.getPPk()+"") + "取消了与您的赠送";
			request.setAttribute("hint", hint);
			// 确定交易后删除
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			return mapping.findForward("sellmoeyok");
		}
		request.setAttribute("vo", vo);
		return mapping.findForward("sellarmpage");
	}
	
	/**
	 * 装备交易
	 */
	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession()); 
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		GoodsService goodsService = new GoodsService();
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"对方接受了您的赠送！");
		if(vo == null){
			String hint = "对方取消了赠送"; 
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		try{
			//判断玩家B的状态是否可以进行交易
			PlayerService playerService = new PlayerService();
			String hint = playerService.isRoleState(vo.getPPk(),1);
			if(hint != null ){
				// 确定交易后删除
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
				request.getRequestDispatcher("/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		RoleEntity roleInfoBypPk = roleService.getRoleInfoById(vo.getPPk()+"");
		// 判断钱
		// 修改钱
		String hint = null;
		//判断申请交易玩家装备是否还在包裹
		PlayerEquipVO equip = playerEquipDao.getByID(vo.getSWuping());
		if( equip.isTraded()==false || equip.isOwnByPPk(vo.getPPk())!=null ){
			hint = roleInfoBypPk.getBasicInfo().getName() + "取消了与您的赠送";
			request.setAttribute("hint", hint);
			// 确定交易后删除
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			return mapping.findForward("sellmoeyok");
		}
		long money = roleInfo.getBasicInfo().getCopper();
		long money_bak_1 = vo.getSWpSilverMoney();
		long money_bak = money_bak_1 * 100;
		long pPkmoney = money - money_bak;
		if (pPkmoney < 0)
		{
			hint = "您没有足够的钱";
			// 确定交易后删除
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
		}
		else
		{
			if (goodsService.isEnoughWrapSpace(roleInfo.getBasicInfo().getPPk(), 1))
			{// 够了
				//改变装备的所有者
				playerEquipDao.updateOwner(vo.getSWuping(), roleInfo.getBasicInfo().getPPk());
				// 增加玩家包裹剩余空间数量
				EquipService equipService = new EquipService();
				equipService.addWrapSpare(roleInfo.getBasicInfo().getPPk(), -1);
				equipService.addWrapSpare(vo.getPPk(), 1);
				// 确定交易后删除
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
				hint = "您已获得由" + roleInfoBypPk.getBasicInfo().getName() + "给您的" + equip.getFullName() + "";
			}
			else
			{
				hint = "您没有足够的包裹空间";
				// 确定交易后删除
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
			}
		}
		GameSystemStatisticsService gs = new GameSystemStatisticsService();
		gs.insertSellInfoRecord(vo.getPPk(), vo.getPByPk(), vo.getSWpType(), vo.getSWuping(), vo.getSWpNumber(), vo.getSWpSilverMoney());
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}

	/**
	 * 装备交易
	 */
	public ActionForward n13(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		 
		String sPk = request.getParameter("sPk");
		PartInfoDAO daos = new PartInfoDAO();
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		// 确定交易后删除
		SellInfoDAO dao = new SellInfoDAO();
		dao.deleteSelleInfo(sPk); 
		String hint = "您取消了与" + daos.getPartName(vo.getPPk() + "") + "的赠送";
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"很遗憾对方取消了您的赠送！");
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}
	/**
	 * 道具交易查看 
	 */
	public ActionForward n14(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String prop_id = request.getParameter("prop_id");
		String sPk = request.getParameter("sPk");
		
		GoodsService goodsService = new GoodsService();
		String propInfoWml = goodsService.getPropInfoWml(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(prop_id));
		request.setAttribute("sPk", sPk);
		request.setAttribute("propInfoWml", propInfoWml);
		return mapping.findForward("sellpropviewpage");
	}
	
	/**
	 * 道具交易
	 */
	public ActionForward n15(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		request.setAttribute("vo", vo);
		return mapping.findForward("sellproppage");
	}
	
	/**
	 * 道具交易
	 */
	public ActionForward n16(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession()); 
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		GoodsService goodsService = new GoodsService();
		TaskService taskService = new TaskService();
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"对方接受了您的赠送！");
		if(vo == null){
			String hint = "对方取消了赠送"; 
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		try{
			//判断玩家B的状态是否可以进行交易
			PlayerService playerService = new PlayerService();
			String hint = playerService.isRoleState(vo.getPPk(),1);
			if(hint != null ){
				// 确定交易后删除
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
				request.getRequestDispatcher("/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		RoleEntity roleInfoBypPk = roleService.getRoleInfoById(vo.getPPk()+"");
		// 判断钱
		// 修改钱
		String hint = null;
		//判断道具是否足够
		if (goodsService.getPropNum(vo.getPPk(), vo.getSWuping()) < vo.getSWpNumber()) { 
			hint = roleInfoBypPk.getBasicInfo().getName() + "取消了与您的赠送";
			request.setAttribute("hint", hint);
			// 确定交易后删除
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			return mapping.findForward("sellmoeyok");
		}
		long money = roleInfo.getBasicInfo().getCopper();
		long money_bak_1 = vo.getSWpSilverMoney();
		long money_bak = money_bak_1 * 100;
		long pPkmoney = money - money_bak;
		if (pPkmoney < 0)
		{
			hint = "您没有足够的钱";
			// 确定交易后删除
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
		}
		else
		{
			//if (goodsService.isEnoughWrapSpace(roleInfo.getBasicInfo().getPPk(), vo.getSWpNumber())) {// 够了
				
				// 通过道具propID判断道具是否有绑定属性
				// 交易完成后修改包裹道具绑定为拾取绑定
				PlayerPropGroupDao ppgdao = new PlayerPropGroupDao();
				GoodsControlVO gcvo = ppgdao.getPropControlgoods(vo.getSWuping());
				if (gcvo.getBonding() == BondingType.EXCHANGEBOND)
				{
					PropVO PropVO = PropCache.getPropById(vo.getSWuping());
					// 取出该道具的特殊字节转向为另一个道具
					// 给道具
					hint = taskService.getGeiDJService(roleInfo.getBasicInfo().getPPk(), PropVO.getPropOperate1(), GoodsType.PROP, vo.getSWpNumber()+ "");
					if(hint != null){
						hint = "您没有足够的包裹空间";
						// 确定交易后删除
						SellInfoDAO dao = new SellInfoDAO();
						dao.deleteSelleInfo(sPk);
						request.setAttribute("hint", hint);
						return mapping.findForward("sellmoeyok");
					}
					// 消除道具
					goodsService.removeProps(vo.getPPk(), vo.getSWuping(), vo.getSWpNumber(),GameLogManager.R_TRADE);
					// 确定交易后删除
					SellInfoDAO dao = new SellInfoDAO();
					dao.deleteSelleInfo(sPk);
					hint = "您已获得由"+ roleInfoBypPk.getBasicInfo().getName() +"给您的"+PropCache.getPropNameById(vo.getSWuping())+"";
				}
				else
				{
					// 给道具 
					hint = taskService.getGeiDJService(roleInfo.getBasicInfo().getPPk(), vo.getSWuping()+ "", GoodsType.PROP, vo.getSWpNumber() + "");
					if(hint != null){
						hint = "您没有足够的包裹空间";
						// 确定交易后删除
						SellInfoDAO dao = new SellInfoDAO();
						dao.deleteSelleInfo(sPk);
						request.setAttribute("hint", hint);
						return mapping.findForward("sellmoeyok");
					}
					
					// 消除道具 
					goodsService.removeProps(vo.getPPk(), vo.getSWuping(), vo.getSWpNumber(),GameLogManager.R_TRADE);
					// 确定交易后删除
					SellInfoDAO dao = new SellInfoDAO();
					dao.deleteSelleInfo(sPk);
					
					hint = "您已获得由"+ roleInfoBypPk.getBasicInfo().getName() +"给您的"+PropCache.getPropNameById(vo.getSWuping())+"";
				}
		}
		GameSystemStatisticsService gs = new GameSystemStatisticsService();
		gs.insertSellInfoRecord(vo.getPPk(), vo.getPByPk(), vo.getSWpType(), vo.getSWuping(), vo.getSWpNumber(), vo.getSWpSilverMoney());
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}

	/**
	 * 道具交易
	 */
	public ActionForward n17(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		 
		String sPk = request.getParameter("sPk");
		PartInfoDAO daos = new PartInfoDAO();
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer
				.parseInt(sPk));
		// 确定交易后删除
		SellInfoDAO dao = new SellInfoDAO();
		dao.deleteSelleInfo(sPk);
		 
		String hint = "您取消了与" + daos.getPartName(vo.getPPk() + "") + "的交易";
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"很遗憾，对方拒绝了您的赠送！");
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}
}
