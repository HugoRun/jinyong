package com.ls.web.action.role;

import com.ben.dao.deletepart.DeletePartDAO;
import com.ben.jms.JmsUtil;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.user.RoleEntity;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.action.ActionBase;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.validate.ValidateService;
import com.pm.dao.secondpass.SecondPassDao;
import com.pm.service.secondpass.SecondPassService;
import com.pm.vo.passsecond.SecondPassVO;
import com.pub.MD5;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author ls
 * 角色创建，登陆，信息查看
 */
public class RoleAction extends ActionBase {

    Logger logger = Logger.getLogger("log.");

    /**
     * 查看角色信息
     */
    public ActionForward des(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String p_pk = request.getParameter("pPk");
        RoleEntity other = RoleService.getRoleInfoById(p_pk);

        String pre = request.getParameter("pre");

        request.setAttribute("other", other);
        request.setAttribute("pre", pre);
        return mapping.findForward("role_des");
    }

    /**
     * 角色出生列表页面
     */
    public ActionForward n1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        return mapping.findForward("born_list");
    }

    /**
     * 进入输入角色信息页面
     */
    public ActionForward n2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String race = request.getParameter("race");

        String hint = ValidateService.validateRace(race);
        if (hint != null) {
            this.setHint(request, hint);
            return mapping.findForward("born_list");
        }

        request.setAttribute("raceDes", ExchangeUtil.getRaceName(Integer.parseInt(race)));
        request.setAttribute("race", race);
        return mapping.findForward("input_role_info");
    }

    /**
     * 创建角色处理
     */
    public ActionForward n3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        String race = request.getParameter("race");
        String pName = request.getParameter("name");
        String pSex = request.getParameter("sex");
        String uPk = (String) session.getAttribute("uPk");

        if (uPk == null)//如果uPk为空重新登陆
        {
            return mapping.findForward("login_index");
        }

        RoleService roleService = new RoleService();

        String hint = ValidateService.validateCreateRole(uPk, pName, pSex, race);

        if (hint != null)//验证玩家的输入失败
        {
            this.setHint(request, hint);
            request.setAttribute("race", race);
            request.setAttribute("raceDes", ExchangeUtil.getRaceName(Integer.parseInt(race)));
            return mapping.findForward("input_role_info");
        }

        RoleEntity role_info = roleService.createRole(uPk, pName, pSex, race);
        if (role_info == null) {
            this.setHint(request, "创建角色失败,请重试");
            request.setAttribute("race", race);
            request.setAttribute("raceDes", ExchangeUtil.getRaceName(Integer.parseInt(race)));
            return mapping.findForward("input_role_info");
        }

        JmsUtil.sendJmsRole((String) session.getAttribute("super_qudao"), (String) session.getAttribute("qudao"), (String) session.getAttribute("user_name"), pName, 1);
        //如果是新手则进入引导页面
        request.getSession().setAttribute("pPk", role_info.getPPk() + "");
        return super.dispath(request, response, "/guide.do");
    }

    /**
     * 删除角色处理
     */
    public ActionForward n4(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String pPk = request.getParameter("pPk");
        RoleService roleService = new RoleService();
        roleService.setRoleDeleteState(Integer.parseInt(pPk));

        return mapping.findForward("role_list");
    }

    /**
     * 恢复删除角色处理
     */
    public ActionForward n5(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String pPk = request.getParameter("pPk");
        RoleService roleService = new RoleService();
        roleService.resumeRole(Integer.parseInt(pPk));

        return mapping.findForward("role_list");
    }

    /**
     * 删除角色确认页面
     */
    public ActionForward n6(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pPk", request.getParameter("pPk"));
        request.setAttribute("pName", request.getParameter("pName"));
        request.setAttribute("pGrade", request.getParameter("pGrade"));
        return mapping.findForward("delete_role_comfirm");
    }

    /**
     * 请求恢复角色，会转至确定是否恢复页面
     */
    public ActionForward n7(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String pPk = request.getParameter("pPk");
        request.setAttribute("uPk", request.getParameter("uPk"));
        request.setAttribute("pPk", pPk);

        PartInfoDao infodao = new PartInfoDao();
        String pName = infodao.getNameByPpk(Integer.parseInt(pPk));
        request.setAttribute("pName", pName);

        return mapping.findForward("resumepartpage");
    }

    /**
     * 请求删除角色,要求输入二级密码进行核对.
     */
    public ActionForward n8(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String second_pass = request.getParameter("second_pass");
        String uPk = (String) request.getSession().getAttribute("uPk");
        if (uPk == null || uPk.equals("")) {
            uPk = request.getParameter("uPk");
        }
        String pPk = request.getParameter("pPk");

        if (pPk == null) {
            return mapping.findForward("login_index");
        }


        request.setAttribute("uPk", uPk);
        request.setAttribute("pPk", pPk);
        request.setAttribute("pGrade", request.getParameter("pGrade"));

        SecondPassService secondPassService = new SecondPassService();

        if (second_pass != null && !second_pass.equals("")) {    //检查其是否为空
            if (StringUtil.isNumber(second_pass)) {                //检测其是不是都是由数字组成.
                if (second_pass.length() == 6) {                    //检查其是不是六位
                    SecondPassDao seconddao = new SecondPassDao();
                    //搜索出数据库中的二级密码
                    SecondPassVO secondPassVO = seconddao.getSecondPassTime(Integer.parseInt(uPk));
                    if (secondPassVO == null || secondPassVO.getSecondPass() == null || secondPassVO.getSecondPass().equals("")) {
                        String hint = "您还没有设置二级密码, 不能修改登录密码！";
                        request.setAttribute("hint", hint);
                        return mapping.findForward("failedPass");
                    }
                    //如果二级密码错误次数小于3,则让其继续核对.
                    if (!secondPassService.checkSeconePass(Integer.parseInt(uPk), secondPassVO)) {
                        if (secondPassVO.getSecondPass().equals(MD5.getMD5Str(second_pass))) {
                            //确定删除时间.
                            DeletePartDAO deletePartDAO = new DeletePartDAO();
                            //deletePartDAO.delete(pPk,uPk);

                            PartInfoDao infoDao = new PartInfoDao();
                            String pName = infoDao.getNameByPpk(Integer.parseInt(pPk));
                            request.setAttribute("pName", pName);
                            return mapping.findForward("sussendPass");
                        } else {
                            secondPassService.insertErrorSecondPsw(Integer.parseInt(uPk));
                            String hint = "";
                            //如果已经错两次,那么给玩家看的提示也会不一样.
                            if (secondPassVO.getPassWrongFlag() == 2) {
                                hint = "您已经在24小时内三次输入错误的帐号二级密码，为了保护该帐号的安全，24小时内该帐号不可再使用二级密码进行删除角色操作!!";

                            } else {
                                hint = "对不起，您输入的二级密码有误，24小时内三次输入错误的二级密码将被冻结修改密码功能一天！";
                            }
                            request.setAttribute("hint", hint);
                            return mapping.findForward("failedPass");
                        }
                        //如果已经大于3,就告诉玩家不能再核对了.
                    } else {
                        secondPassService.insertErrorSecondPsw(Integer.parseInt(uPk));
                        String hint = "您已经在24小时内三次输入错误的帐号二级密码，为了保护该帐号的安全，24小时内该帐号不可再使用二级密码进行删除角色操作!!";
                        request.setAttribute("hint", hint);
                        return mapping.findForward("failedPass");
                    }
                }
            }
            String hint = "对不起，您输入的二级密码格式不正确,二级密码为六位数字组成！";
            request.setAttribute("hint", hint);
            return mapping.findForward("failedPass");
        }
        String hint = "对不起，请不要输入空密码！";
        request.setAttribute("hint", hint);
        return mapping.findForward("failedPass");
    }

    /**
     * 请求删除角色,不要求输入二级密码进行核对.
     */
    public ActionForward n9(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String uPk = (String) request.getSession().getAttribute("uPk");
        if (StringUtils.isEmpty(uPk)) {
            return this.dispath(request, response, "/login.do?cmd=n0");
        }
        String pPk = request.getParameter("pPk");

        //删除角色
        RoleService roleService = new RoleService();
        boolean result = roleService.delRole(uPk, pPk);
        if (!result) {
            //删除失败
            this.setHint(request, "删除角色失败");
        }
        return this.dispath(request, response, "/login.do?cmd=n3");
    }
}