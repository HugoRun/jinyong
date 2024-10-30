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
import com.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;

/**
 * @author Administrator
 * 
 */
public class BySellMoneyAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk= roleInfo.getBasicInfo().getPPk();

		String sPk = request.getParameter("sPk");
		String silverMoney = request.getParameter("silverMoney");
		String copperMoney = request.getParameter("copperMoney");
		 
		// 确定交易后删除
		SellInfoDAO dao = new SellInfoDAO();
		 
		dao.deleteSelleInfo(sPk);
		 
		String SilverMoney = "0";
		String sCopperMoney = "0";
		dao.getSellByMoneyUpdate(SilverMoney, sCopperMoney, sPk);

		PartInfoDAO daos = new PartInfoDAO();
		/*PartInfoVO vo = daos.getPartView(p_pk+"");
		int ss = Integer.parseInt(silverMoney)+ Integer.parseInt(vo.getPSilver());// 增加的银子
		int aa = Integer.parseInt(copperMoney)+ Integer.parseInt(vo.getPCopper());// 增加的铜钱
		daos.getUpdateMoney(ss + "", aa + "", p_pk+"");
		*/
		return mapping.findForward("success");
	}
}
