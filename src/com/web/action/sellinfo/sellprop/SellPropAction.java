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
 * @author ��ƾ� ����:���߽��� 9:40:46 AM 
 */
public class SellPropAction extends ActionBase
{
	/**
	 * ���߽���
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
			////System.out.print("goods_id��w_type��pg_pkΪ��");
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
	 * ���߽���
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
	 * ���߽���
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		//���������
		if( roleInfo.getBasicInfo().getPlayer_state_by_new()==1)
		{
			this.setHint(request, "�����ڴ�����������״̬,�޷�����");
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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		
		
		
		//�ж�����Ƿ�����
		RoleEntity roleInfo1pByuPk = RoleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk == null){
			try{
				String hint = "�����������!";
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
			    hint = "��������ȷ������ʽ";
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
			hint = "��������Ҫ���׵���Ʒ����";
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByuPk", pByuPk);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("propmoneyover");
		}
		//�ж��Ƿ��ظ�����
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
		
		//�жϵ�������������
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
			hint = "��û��"+number+"��"+goodsName+"";
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
			    hint = "��������ȷ����!";
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
		//if(goodsService.isEnoughWrapSpace(Integer.parseInt(pByPk),Integer.parseInt(number))){//����
			//getSellArmAdd
			dao.getSellArmAdd(roleInfo.getBasicInfo().getPPk(), pByPk, goods_id, GoodsType.PROP, Integer.parseInt(number), pSilver, "0", SellInfoVO.SELLPROP, Time);
			//dao.getSellWuPingAdd(goods_id, Integer.parseInt(w_type), Integer.parseInt(number), pSilver, pCopper, SfOk, userTempBean.getPPk(), pByPk); 
			hint = "���Ѿ���"+name+"������"+number+"��"+goodsName+",��ȴ��Է����գ�";
			
			//��������뵯��ʽ��Ϣ����
			UMsgService uMsgService = new UMsgService();
			UMessageInfoVO msgInfo = new UMessageInfoVO();
			msgInfo.setMsgType(PopUpMsgType.MESSAGE_SWAP);
			msgInfo.setPPk(Integer.parseInt(pByPk));
			msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_SWAP_FIRST);
			uMsgService.sendPopUpMsg(msgInfo);
			
		}else{
			hint = name+"û���㹻�İ�������!";
		}
		
		request.setAttribute("hint", hint);
		request.setAttribute("w_type", w_type);
		request.setAttribute("pByuPk", pByuPk);
		request.setAttribute("pByPk", pByPk); 
		return mapping.findForward("propmoneyover");
	} 
	
	/**
	 * ���߽���
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
			hint = "����ȷ������Ҫ���׵���Ʒ����";
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByuPk", pByuPk);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("propmoneyover");
		}
		if(number == null || number.equals("") || Integer.parseInt(number) == 0){
			hint = "��������Ҫ���׵���Ʒ����";
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByuPk", pByuPk);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("propmoneyover");
		}
		GoodsService goodsService = new GoodsService();
		if (goodsService.getPropNum(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(goods_id)) < Integer.parseInt(number)) {
			hint = "��û��"+number+"��"+goodsName+"";
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
