package com.web.action.avoidpkprop;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.web.service.avoidpkprop.AvoidPkPropService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 侯浩军 免PK道具
 */
public class AvoidPkPropAction extends DispatchAction {
    /**
     * 免PK道具
     */
    public ActionForward n1(ActionMapping mapping, ActionForm form,
                            HttpServletRequest request, HttpServletResponse response) {
        //UserTempBean userTempBean = (UserTempBean) request.getSession().getAttribute("userTempBean");

        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

        AvoidPkPropService avoidPkPropService = new AvoidPkPropService();
        int times = avoidPkPropService.getAvoidPkProp(roleInfo.getBasicInfo().getPPk());
        if (times > 0) {
            String hint = "您的保护时间还剩下" + times + "分钟<br/>您确定解除PK保护状态<br/>";
            String type = "1";
            request.setAttribute("hint", hint);
            request.setAttribute("type", type);
        } else {
            String hint = "您的PK保护时间为" + times + "分钟，不需要解除";
            String type = "2";
            request.setAttribute("hint", hint);
            request.setAttribute("type", type);
        }
        return mapping.findForward("avoidpkproppage");
    }

    /**
     * 免PK道具
     */
    public ActionForward n2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        //UserTempBean userTempBean = (UserTempBean) request.getSession().getAttribute("userTempBean");

        RoleService roleService = new RoleService();
        RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

        AvoidPkPropService avoidPkPropService = new AvoidPkPropService();
        avoidPkPropService.deleteAvoidPkProp(roleInfo.getBasicInfo().getPPk());
        String hint = "您取消了PK保护状态";
        String type = "2";
        request.setAttribute("hint", hint);
        request.setAttribute("type", type);
        return mapping.findForward("avoidpkproppage");
    }
}
