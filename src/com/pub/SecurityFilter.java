package com.pub;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.player.PlayerState;
import com.ls.web.service.player.RoleService;

public class SecurityFilter implements Filter
{

	/**
	 * @see javax.servlet.Filter#init(FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException
	{
	}

	/**
	 * @see javax.servlet.Filter#doFilter(ServletRequest, ServletResponse,
	 *      FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		
		/*HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		SellInfoDAO dao = new SellInfoDAO();
		PetInfoDAO petInfoDAO = new PetInfoDAO(); 
		PlayerService playerService = new PlayerService();
		GrowService growService = new GrowService();

		// String pPks = (String) req.getSession().getAttribute("pPks");
		UserTempBean userTempBean = (UserTempBean) session.getAttribute("userTempBean");

		if (userTempBean != null && userTempBean.getPPk() != 0 && !(userTempBean.getPPk() + "").equals(""))
		{
			if (userTempBean.getState() == 0)
			{ 
			// ��ѯ�Լ���û�б�����
			if (dao.isSellInfoIdBy(userTempBean.getPPk()))
			{
				      // ״̬
				      int state = 1;
				      userTempBean.setState(state);
				      session.setAttribute("userTempBean", userTempBean);
				      //�޸�ȫ���и���ҵ�״̬ ״̬Ϊ����״̬ 
					  UserInfoVO userAInfoa = playerService.getUserInfo(req, userTempBean.getPPk());
					  userAInfoa.setStat(PlayerState.TRADE);		
					
					// ȡ����������
					SellInfoVO sellMode = dao.getSellMode(userTempBean.getPPk());
					if (sellMode != null)
					{
						if (sellMode.getSellMode() == SellInfoVO.SELLMONEY)
						{// ��Ǯ����&chair="+request.getParameter("chair"));
							request.getRequestDispatcher("/sellinfoaction.do?cmd=n1&sPk="+ sellMode.getSPk() + "&chair="+ request.getParameter("chair")).forward(request, response);
							return;
						}
						if (sellMode.getSellMode() == SellInfoVO.SELLPROP)
						{// ���߽���
							request.getRequestDispatcher("/sellinfoaction.do?cmd=n7&sPk="+ sellMode.getSPk() + "&chair="+ request.getParameter("chair")).forward(request, response);
							return;
						}
						if (sellMode.getSellMode() == SellInfoVO.SELLARM)
						{// װ������
							request.getRequestDispatcher("/sellinfoaction.do?cmd=n4&sPk="+ sellMode.getSPk() + "&chair="+ request.getParameter("chair")).forward(request, response);
							return;
						} 
					}
				}
			    if(petInfoDAO.getPetSellVs(userTempBean.getPPk()+"")){
			    // ״̬
				int state = 1;
				userTempBean.setState(state);
				session.setAttribute("userTempBean", userTempBean);
				//�޸�ȫ���и���ҵ�״̬ ״̬Ϊ����״̬ 
				UserInfoVO userAInfoa = playerService.getUserInfo(req, userTempBean.getPPk());
				userAInfoa.setStat(PlayerState.TRADE);
			    //ȡ�����ｻ�ױ����� 
			    int ps_pk = petInfoDAO.getSellPet(userTempBean.getPPk());
				//request.getRequestDispatcher("/jsp/petinfo/petsellby/pet_sell_by.jsp?pPks="+userTempBean.getPPk()).forward(request, response);
			    request.getRequestDispatcher("/sellinfoaction.do?cmd=n10&ps_pk="+ ps_pk + "&chair="+ request.getParameter("chair")).forward(request, response);
				return;
			}
			}
			//��ҵ�9���Ժ� ϵͳ�Զ���תѡ�����ɼ���
			if (userTempBean.getPGrade() == 9 && growService.isUpgradeByExperience(userTempBean.getPPk()))
			{
				request.getRequestDispatcher("/jsp/task/visitlead/visit_lead.jsp?chair="+ request.getParameter("chair")).forward(request,response);
				return;
			}
<<<<<<< SecurityFilter.java
		}*/
		/*HttpServletRequest req = (HttpServletRequest) request;
		RoleService roleService = new RoleService();
		RoleEntity rolo_info = roleService.getRoleInfoBySession(req.getSession());
		
		if( rolo_info!=null && rolo_info.getStateInfo().getCurState()==PlayerState.GENERAL )
		{
			//��ҵ�9���Ժ� ϵͳ�Զ���תѡ�����ɼ���
			int grade = rolo_info.getBasicInfo().getGrade();
			String cur_exp = rolo_info.getBasicInfo().getCurExp();
			String next_grade_exp = rolo_info.getBasicInfo().getNextGradeExp();
			
			if (grade== 9 && Integer.parseInt(cur_exp)>=Integer.parseInt(next_grade_exp))
			{
				request.getRequestDispatcher("/jsp/task/visitlead/visit_lead.jsp?chair="+ request.getParameter("chair")).forward(request,response);
				return;
			}
		}
		*/
		chain.doFilter(request, response);
	}

	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy()
	{
	}

}