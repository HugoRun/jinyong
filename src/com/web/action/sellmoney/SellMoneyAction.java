/**
 * 
 */
package com.web.action.sellmoney;
 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.dao.sellinfo.SellInfoDAO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
 

/**
 * @author Administrator
 * 
 */
public class SellMoneyAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		/*RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk= roleInfo.getBasicInfo().getPPk();
		
		String pByuPk = request.getParameter("pByuPk");
		String pByPk = request.getParameter("pByPk");//��������ID
		
		
		String pSilver = request.getParameter("pSilver"); //������������
		String pCopper = request.getParameter("pCopper"); //��������ͭǮ
		
		String BypSilver = request.getParameter("BypSilver"); //���󷽽�������
		String BypCopper = request.getParameter("BypCopper"); //���󷽽���ͭǮ
		//�ж������׵�Ǯ�Ƿ�����Լ���������Ǯ
		if(Integer.parseInt(pSilver)<Integer.parseInt(BypSilver)){
			String c = "c";
			request.setAttribute("c",c);
			request.setAttribute("pByuPk",pByuPk);
			request.setAttribute("pByPk",pByPk);
			return mapping.findForward("successon");
		} else if(Integer.parseInt(pCopper)<Integer.parseInt(BypCopper)){
			String c = "c";
			request.setAttribute("c",c);
			request.setAttribute("pByuPk",pByuPk);
			request.setAttribute("pByPk",pByPk);
			return mapping.findForward("successon");
		}
		
		String SfOk = request.getParameter("SfOk");
		SellInfoDAO dao = new SellInfoDAO();
		dao.getSellMoneyUpdate(BypSilver, BypCopper, SfOk, p_pk+"", pByPk);
		
		
		if(Integer.parseInt(SfOk)==1){
			int ss = Integer.parseInt(pSilver)-Integer.parseInt(BypSilver);
			int aa = Integer.parseInt(pCopper)-Integer.parseInt(BypCopper);
			PartInfoDAO daos = new PartInfoDAO();
			daos.getUpdateMoney(ss+"", aa+"", p_pk+"");
		}*/
		 
		return mapping.findForward("success");
	}
}
