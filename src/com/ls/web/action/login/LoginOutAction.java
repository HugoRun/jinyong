/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.ls.web.action.login;

import com.ls.web.service.login.LoginService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * MyEclipse Struts
 * Creation date: 03-31-2009
 * <p>
 * XDoclet definition:
 *
 * @struts.action parameter="cmd" validate="true"
 */
public class LoginOutAction extends DispatchAction {
    /*
     * Generated Methods
     */

    /**
     * 角色退出
     * Method execute
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward n1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String pPk = (String) session.getAttribute("pPk");
        LoginService loginService = new LoginService();
        loginService.loginoutRole(pPk);
        session.removeAttribute("pPk");
        return mapping.findForward("role_loginout");
    }

    /**
     * 账号退出
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward n2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        return mapping.findForward("user_loginout");
    }

}