package com.web.action.sellinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.wrapinfo.WrapinfoDAO;
import com.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.Wrap;
import com.ls.pub.constant.player.PlayerState;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.web.service.friend.BlacklistService;

/**
 * 交易物品相关操作
 */
public class SellInfoAddAction extends ActionBase
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		PlayerService playerService = new PlayerService();
		BlacklistService blacklistService = new BlacklistService();
		GoodsService goodsSerivce = new GoodsService();
		
		RoleEntity me = this.getRoleEntity(request);
		
		//如果是新手
		if( me.getBasicInfo().getPlayer_state_by_new()==1)
		{
			this.setHint(request, "你现在处在新手引导状态,无法交易");
			return mapping.findForward("return_hint");
		}
		
		int p_pk = me.getPPk();
		
		String pByPk = request.getParameter("pByPk");// 被请求人ID
		String pSilverno = request.getParameter("pSilverno");
		
		//判断玩家是否在线
		RoleEntity other = RoleService.getRoleInfoById(pByPk);
		
		if(other.isOnline()==false){
			String hint = "该玩家已下线!";
			return this.dispath(request, response, "/pubbuckaction.do?hint="+hint);
		}
		
		if (pSilverno != null)
		{
			request.setAttribute("pSilverno", pSilverno);
		}
		request.setAttribute("pByPk", pByPk);
		//判断玩家是否是在自己的黑名单
		int res = blacklistService.isBlacklist(me.getBasicInfo().getPPk(), Integer.parseInt(pByPk));
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
			return this.dispath(request, response, "/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair"));
		}
		
		//判断对方玩家是否开交易开关
		hint = playerService.isRoleState( Integer.parseInt(pByPk), 1);
		if(hint != null ){
			return this.dispath(request, response, "/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair"));
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

		int w_type=Wrap.EQUIP;
		int page_no=1;

		if (w_type_str != null)
		{
			w_type = Integer.parseInt(w_type_str);
		}

		if (page_no_str != null)
		{
			page_no = Integer.parseInt(page_no_str);
		}

		
		String path = request.getServletPath();
		request.setAttribute("path", path);

		WrapinfoDAO dao = new WrapinfoDAO();

		// 得到包裹和钱数信息
		PartInfoVO vo = dao.geTsilver(me.getBasicInfo().getPPk() + "");
		request.setAttribute("partInfo", vo);

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
		item_page.setURL(response, "/xiaoshou.do?w_type="+w_type_str+"&amp;pByPk="+pByPk);
		request.setAttribute("item_page", item_page);
		request.setAttribute("w_type", w_type_str);
		request.setAttribute("me", me);
		request.setAttribute("other", other);
		return mapping.findForward("goods_list");
	}
}
