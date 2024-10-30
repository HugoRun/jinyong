package com.pub;

import javax.servlet.*;
import java.io.IOException;

public class SecurityFilter implements Filter {

    /**
     * @see javax.servlet.Filter#init(FilterConfig)
     */
    public void init(FilterConfig arg0) throws ServletException {
    }

    /**
     * @see javax.servlet.Filter#doFilter(ServletRequest, ServletResponse,
     * FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
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
			// 查询自己有没有被交易
			if (dao.isSellInfoIdBy(userTempBean.getPPk()))
			{
				      // 状态
				      int state = 1;
				      userTempBean.setState(state);
				      session.setAttribute("userTempBean", userTempBean);
				      //修改全局中该玩家的状态 状态为交易状态 
					  UserInfoVO userAInfoa = playerService.getUserInfo(req, userTempBean.getPPk());
					  userAInfoa.setStat(PlayerState.TRADE);		
					
					// 取出交易类型
					SellInfoVO sellMode = dao.getSellMode(userTempBean.getPPk());
					if (sellMode != null)
					{
						if (sellMode.getSellMode() == SellInfoVO.SELLMONEY)
						{// 金钱交易&chair="+request.getParameter("chair"));
							request.getRequestDispatcher("/sellinfoaction.do?cmd=n1&sPk="+ sellMode.getSPk() + "&chair="+ request.getParameter("chair")).forward(request, response);
							return;
						}
						if (sellMode.getSellMode() == SellInfoVO.SELLPROP)
						{// 道具交易
							request.getRequestDispatcher("/sellinfoaction.do?cmd=n7&sPk="+ sellMode.getSPk() + "&chair="+ request.getParameter("chair")).forward(request, response);
							return;
						}
						if (sellMode.getSellMode() == SellInfoVO.SELLARM)
						{// 装备交易
							request.getRequestDispatcher("/sellinfoaction.do?cmd=n4&sPk="+ sellMode.getSPk() + "&chair="+ request.getParameter("chair")).forward(request, response);
							return;
						} 
					}
				}
			    if(petInfoDAO.getPetSellVs(userTempBean.getPPk()+"")){
			    // 状态
				int state = 1;
				userTempBean.setState(state);
				session.setAttribute("userTempBean", userTempBean);
				//修改全局中该玩家的状态 状态为交易状态 
				UserInfoVO userAInfoa = playerService.getUserInfo(req, userTempBean.getPPk());
				userAInfoa.setStat(PlayerState.TRADE);
			    //取出宠物交易表主键 
			    int ps_pk = petInfoDAO.getSellPet(userTempBean.getPPk());
				//request.getRequestDispatcher("/jsp/petinfo/petsellby/pet_sell_by.jsp?pPks="+userTempBean.getPPk()).forward(request, response);
			    request.getRequestDispatcher("/sellinfoaction.do?cmd=n10&ps_pk="+ ps_pk + "&chair="+ request.getParameter("chair")).forward(request, response);
				return;
			}
			}
			//玩家到9级以后 系统自动跳转选择门派加入
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
			//玩家到9级以后 系统自动跳转选择门派加入
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
    public void destroy() {
    }

}