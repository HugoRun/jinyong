/**
 * 
 */
package com.web.action.sellinfo.sellprop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.dao.sellinfo.SellInfoDAO;
import com.ben.vo.sellinfo.SellInfoVO;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.ActionType;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.UMsgService;
import com.pub.ben.info.Expression;

/**
 * @author 侯浩军 功能:道具交易 9:40:46 AM 
 */
public class SellPropAction extends ActionBase
{
	/**
	 * 道具交易
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		String w_type = request.getParameter("w_type");
		String pg_pk = request.getParameter("pg_pk");
		String goods_id = request.getParameter("goods_id");

		
		String pByuPk = request.getParameter("pByuPk");
		String pByPk = request.getParameter("pByPk");
		
		if (goods_id == null || w_type == null || pg_pk == null) {
			////System.out.print("goods_id或w_type或pg_pk为空");
		}

		GoodsService goodsService = new GoodsService(); 
		String goods_display = goodsService.getPropInfoWmlMai(roleInfo.getBasicInfo().getPPk(),Integer.parseInt(goods_id)); 
		
		String goodsName = PropCache.getPropNameById(Integer.parseInt(goods_id));
		
		request.setAttribute("w_type", w_type);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("goodsName", goodsName);
		request.setAttribute("goods_display", goods_display);
		request.setAttribute("pByuPk", pByuPk);
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("propview");
	} 
	/**
	 * 道具交易
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		String goods_id = request.getParameter("goods_id");
		String pg_pk = request.getParameter("pg_pk");
		String goodsName = request.getParameter("goodsName");
		String pByuPk = request.getParameter("pByuPk");
		String pByPk = request.getParameter("pByPk");
		String w_type = request.getParameter("w_type");
		
		request.setAttribute("w_type", w_type);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goodsName", goodsName);
		request.setAttribute("pByuPk", pByuPk);
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("propnumber");
	} 
	/**
	 * 道具交易
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
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
		String pByuPk = request.getParameter("pByuPk");
		String pByPk = request.getParameter("pByPk");
		String w_type = request.getParameter("w_type");
		String pSilver = request.getParameter("pSilver");
		String number = request.getParameter("number");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		
		
		
		//判断玩家是否在线
		RoleEntity roleInfo1pByuPk = RoleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk == null){
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
		
		 Pattern p = Pattern.compile(Expression.positive_integer_contain0_regexp);
		 Matcher m = p.matcher(pSilver);
		 boolean b = m.matches();
		 if(b==false){
			    hint = "请输入正确银两格式";
			    request.setAttribute("hint", hint);
				request.setAttribute("w_type", w_type);
				request.setAttribute("pByuPk", pByuPk);
				request.setAttribute("pByPk", pByPk); 
				return mapping.findForward("propmoneyover");
		 }
		 
		if(pSilver == null || pSilver.equals("") || Integer.parseInt(pSilver) == 0){
			pSilver = "0";
		}
		if(number == null || number.equals("") || Integer.parseInt(number) == 0){
			hint = "请输入您要交易的物品数量";
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByuPk", pByuPk);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("propmoneyover");
		}
		//判断是否重复交易
		SellInfoDAO dao = new SellInfoDAO();
		String ss = dao.getSellExistByPPkAndGoodsId(roleInfo.getBasicInfo().getPPk()+"", goods_id, GoodsType.PROP);
		if(ss != null && !ss.equals("")){
			hint = ss;
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByuPk", pByuPk);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("propmoneyover");
		}
		
		//判断道具数量够不够
		GoodsService goodsService = new GoodsService();
		hint = goodsService.isBinded(Integer.parseInt(pg_pk), GoodsType.PROP, ActionType.EXCHANGE);
		if(hint != null){
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByuPk", pByuPk);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("propmoneyover");
		}
		if (goodsService.getPropNum(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(goods_id)) < Integer.parseInt(number)) {
			hint = "您没有"+number+"个"+goodsName+"";
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByuPk", pByuPk);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("propmoneyover");
		}
		 Pattern sum1 = Pattern.compile(Expression.positive_integer_contain0_regexp);
		 Matcher sum2 = sum1.matcher(number);
		 boolean sum3 = sum2.matches();
		 if(sum3==false){
			    hint = "请输入正确数字!";
			    request.setAttribute("hint", hint);
				request.setAttribute("w_type", w_type);
				request.setAttribute("pByuPk", pByuPk);
				request.setAttribute("pByPk", pByPk); 
				return mapping.findForward("propmoneyover");
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
		//if(goodsService.isEnoughWrapSpace(Integer.parseInt(pByPk),Integer.parseInt(number))){//够了
			//getSellArmAdd
			dao.getSellArmAdd(roleInfo.getBasicInfo().getPPk(), pByPk, goods_id, GoodsType.PROP, Integer.parseInt(number), pSilver, "0", SellInfoVO.SELLPROP, Time);
			//dao.getSellWuPingAdd(goods_id, Integer.parseInt(w_type), Integer.parseInt(number), pSilver, pCopper, SfOk, userTempBean.getPPk(), pByPk); 
			hint = "您已经与"+name+"交易了"+number+"个"+goodsName+",请等待对方接收！";
			
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
		request.setAttribute("pByuPk", pByuPk);
		request.setAttribute("pByPk", pByPk); 
		return mapping.findForward("propmoneyover");
	} 
	
	/**
	 * 道具交易
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		String goods_id = request.getParameter("goods_id");
		String pg_pk = request.getParameter("pg_pk");
		String goodsName = request.getParameter("goodsName");
		String pByuPk = request.getParameter("pByuPk");
		String pByPk = request.getParameter("pByPk");
		String w_type = request.getParameter("w_type");
		String number = request.getParameter("number");
		String hint = null;
		if(number.length() > 4){
			hint = "请正确输入您要交易的物品数量";
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByuPk", pByuPk);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("propmoneyover");
		}
		if(number == null || number.equals("") || Integer.parseInt(number) == 0){
			hint = "请输入您要交易的物品数量";
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByuPk", pByuPk);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("propmoneyover");
		}
		GoodsService goodsService = new GoodsService();
		if (goodsService.getPropNum(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(goods_id)) < Integer.parseInt(number)) {
			hint = "您没有"+number+"个"+goodsName+"";
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByuPk", pByuPk);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("propmoneyover");
		}
		request.setAttribute("number", number);
		request.setAttribute("w_type", w_type);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goodsName", goodsName);
		request.setAttribute("pByuPk", pByuPk);
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("propmoney");
	} 
}
