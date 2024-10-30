package com.web.action.communion.commpage;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction; 
import com.ben.dao.communion.CommunionDAO; 
import com.ben.vo.communion.CommunionVO;

/**
 * @author 侯浩军 状态
 */
public class CommPageAction extends DispatchAction
{
	/**
	 * 查看自己状态
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		/*String cPk = request.getParameter("cPk"); 
		CommunionDAO communionDAO = new CommunionDAO();
		CommunionVO communionVO =communionDAO.getCommpageView(Integer.parseInt(cPk));
		request.setAttribute("communionVO",communionVO); */
		return mapping.findForward("commpagelist");
	}
	 
}
