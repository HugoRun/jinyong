package com.ls.model.property;

import com.ben.dao.honour.RoleTitleDAO;
import com.ben.vo.honour.RoleTitleVO;
import com.ben.vo.honour.TitleVO;
import com.ls.ben.cache.staticcache.honour.TitleCache;
import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.RoleEntity;
import com.ls.model.user.UserBase;
import com.ls.model.vip.Vip;
import com.ls.model.vip.VipManager;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.pub.util.DateUtil;
import com.ls.web.service.rank.RankService;
import com.pm.service.mail.MailInfoService;
import com.pm.service.systemInfo.SystemInfoService;
import com.web.service.popupmsg.PopUpMsgService;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：角色称号集合
 */
public class RoleTitleSet extends UserBase {
    private RoleTitleDAO dao = null;
    private Map<Integer, RoleTitleVO> titleCacheByType = null;//key是<称号的类型>
    private Map<Integer, RoleTitleVO> titleCacheByTId = null;//key是<称号的id>

    private int showTitleId = -1;//当前显示的称号id
    private final PropertyModel titlePropertys = new PropertyModel();// 称号效果集


    public RoleTitleSet(int p_pk) {
        super(p_pk);
        dao = new RoleTitleDAO();
        titleCacheByType = new LinkedHashMap<Integer, RoleTitleVO>();
        titleCacheByTId = new LinkedHashMap<Integer, RoleTitleVO>();
        List<RoleTitleVO> role_title_list = dao.getListByPPk(p_pk);

        for (RoleTitleVO roleTitleVO : role_title_list) {
            if (roleTitleVO.getIsShow() == 1) {
                showTitleId = roleTitleVO.getTId();
            }
            put(roleTitleVO);
        }
    }

    /**
     * 初始化称号
     */
    public void init() {
        showTitleId = -1;
        titleCacheByType.clear();
        titleCacheByTId.clear();
        titlePropertys.init();
        dao.clear(p_pk);

        //给玩家增加初始称号
        int race = this.getRoleEntity().getBasicInfo().getPRace();
        TitleVO race_title = null;//种族称号
        switch (race) {
            case 1:
                race_title = TitleCache.getById(3);
                break;//妖
            case 2:
                race_title = TitleCache.getById(11);
                break;//巫
        }
        gainTitle(race_title);
    }

    /**
     * 给玩家附加称号属性
     * 先加载会员的附加属性效果，再加载称号的附加效果
     */
    public void loadProperty(PartInfoVO player) {
        Vip vip = this.getVIP();
        if (vip != null) {
            vip.loadPropertys(player);
        }
        titlePropertys.loadPropertys(player);
    }


    private RoleTitleVO put(RoleTitleVO roleTitle) {
        RoleTitleVO old_title = null;
        if (roleTitle != null) {
            roleTitle.createTimerEvent(super.getRoleEntity().getEventManager());

            old_title = this.titleCacheByType.get(roleTitle.getType());
            if (old_title != null) {
                this.remove(old_title);
            }
            titleCacheByTId.put(roleTitle.getTId(), roleTitle);
            titlePropertys.appendPropertyByAttriStr(roleTitle.getAttriStr(), PropertyModel.ADD_PROPERTY);
            titleCacheByType.put(roleTitle.getType(), roleTitle);
        }
        return old_title;
    }

    private RoleTitleVO remove(RoleTitleVO roleTitle) {
        RoleTitleVO old_title = null;
        if (roleTitle != null) {
            roleTitle.removeTimerEvent(super.getRoleEntity().getEventManager());
            if (roleTitle.getId() == this.showTitleId) {
                this.showTitleId = -1;
            }
            titlePropertys.appendPropertyByAttriStr(roleTitle.getAttriStr(), PropertyModel.SUB_PROPERTY);
            titleCacheByType.remove(roleTitle.getType());
            old_title = titleCacheByTId.remove(roleTitle.getTId());
            this.dao.delById(this.p_pk, roleTitle.getTId());
        }
        return old_title;
    }

    /**
     * 删除一个称号
     */
    public void delTitle(int tId) {
        RoleTitleVO role_title = this.getByTId(tId);

        switch (role_title.getType()) {
            case TitleVO.VIP://会员称号删除处理
                this.delVipTitle(role_title);
                break;
        }

        this.remove(role_title);
    }

    /**
     * 删除会员称号
     */
    private void delVipTitle(RoleTitleVO role_title) {
        if (role_title == null) {
            return;
        }
        Vip vip = VipManager.getByTId(role_title.getTId());
        if (vip != null) {
            // 发送邮件告诉玩家到期了
            StringBuffer sb = new StringBuffer(800);
            MailInfoService mailInfoService = new MailInfoService();
            String title = "系统邮件";
            sb.append("你的").append(vip.getName()).append("资格已经到期,现在马上再次购买会员资格可享受8折优惠!<br/>");
            // 首先得到所有会员类型的道具
            PropDao propDao = new PropDao();
            List<PropVO> list = propDao.getListByType(PropType.VIP);
            for (PropVO prop : list) {
                sb.append(prop.getPropName());
                sb.append("<anchor>");
                sb.append("<go method=\"post\" href=\"").append(GameConfig.getContextPath()).append("/vip.do" + "\">");
                sb.append("<postfield name=\"cmd\" value=\"n2\" />");
                sb.append("<postfield name=\"prop_id\" value=\"").append(prop.getPropID()).append("\" />");
                sb.append("</go>");
                sb.append("购买");
                sb.append("</anchor><br/>");
            }
            mailInfoService.sendMailBySystem(this.p_pk, title, sb.toString());

            //统计需要
            new RankService().updateVIP(p_pk, 0, 0);
            //VIP到期给弹出式消息
            new PopUpMsgService().addSysSpecialMsg(p_pk, 0, 0, PopUpMsgType.VIP_ENDTIME);
        }
    }


    /**
     * 称号列表
     *
     * @return
     */
    public List<RoleTitleVO> getRoleTitleList() {
        return new ArrayList<RoleTitleVO>(titleCacheByType.values());
    }

    /**
     * 更改称号显示状态
     *
     * @param id 要更改的称号
     */
    public void updateShowStatus(int tId) {
        RoleTitleVO cur_title = this.getByTId(tId);

        if (cur_title.getIsShow() == 1)//如果当前显示的就是该称号
        {
            this.showTitleId = -1;
        } else {
            RoleTitleVO show_title = this.getShowTitle();
            if (show_title != null) {
                show_title.updateIsShow();
            }
            this.showTitleId = tId;
        }

        cur_title.updateIsShow();
    }

    /**
     * 得到正在使用称号的名字
     *
     * @return
     */
    public String getShowTitleName() {
        RoleTitleVO showTitle = this.titleCacheByTId.get(this.showTitleId);
        if (showTitle != null) {
            return showTitle.getName();
        } else {
            return "无";
        }
    }

    /**
     * 获得一个称号
     *
     * @return
     */
    public String gainTitle(TitleVO newTitle) {
        if (newTitle == null) {
            return "称号信息错误";
        }

        RoleTitleVO roleTitle = new RoleTitleVO(p_pk, newTitle);
        this.dao.add(roleTitle);
        this.put(roleTitle);

        switch (roleTitle.getType()) {
            case TitleVO.VIP:
                this.gainVipTitle(roleTitle);
                break;
        }

        return null;
    }

    /**
     * 获得vip称号
     *
     * @param roleTitle
     */
    private void gainVipTitle(RoleTitleVO roleTitle) {
        Vip vip = this.getVIP();
        if (vip != null) {
            SystemInfoService systemInfoService = new SystemInfoService();
            String info = "玩家" + this.getRoleEntity().getName() + "成为了荣誉的" + vip.getName();
            systemInfoService.insertSystemInfoBySystem(info);
        }
    }

    /**
     * 是否有该类型的称号
     *
     * @param titleType 称号类型
     * @return
     */
    public boolean isHaveByType(int titleType) {
        return this.titleCacheByType.get(titleType) != null;
    }

    /**
     * 是否有VIP称号
     *
     * @return
     */
    public String isGainNewVipTitle(TitleVO newVipTitle) {
        if (newVipTitle == null) {
            return "会员称号错误";
        }
        RoleTitleVO role_title = this.titleCacheByType.get(TitleVO.VIP);

        if (role_title != null) {
            Vip vip = getVIP();
            String sb = "你现在已经是" + vip.getName() + "," +
                    role_title.getSimpleLeftTimeDes() + "后结束！使用该道具后你将获得" +
                    DateUtil.returnTimeStr(newVipTitle.getUseTime() * 60) + "的“" +
                    newVipTitle.getName() + "”的至尊称号，但“" +
                    vip.getName() + "”的称号及其所得属性将消失！你确定要使用该道具吗？<br/>";
            return sb;
        }

        return null;
    }

    /**
     * 得到vip 信息
     *
     * @return
     */
    public Vip getVIP() {
        RoleTitleVO role_title = this.titleCacheByType.get(TitleVO.VIP);
        if (role_title != null) {
            return VipManager.getByTId(role_title.getTId());
        }
        return null;
    }

    /**
     * 根据称号判断是否满足称号要求
     *
     * @param tId 称号id
     * @return
     */
    public boolean isHaveByTitleStr(String titleStr) {
        if (StringUtils.isEmpty(titleStr) || titleStr.equals("0")) {
            return true;
        }

        String[] title_str_list = titleStr.split(",");
        for (String title_str : title_str_list) {
            if (StringUtils.isNumeric(title_str) && this.titleCacheByTId.get(Integer.parseInt(title_str)) != null) {
                return true;
            }
        }

        return false;
    }

    /**
     * 是否有该称号
     *
     * @param tId 称号id
     * @return
     */
    public boolean isHaveByTId(TitleVO newTitle) {
        return newTitle == null || this.titleCacheByTId.get(newTitle.getId()) != null;
    }

    /**
     * 得到现在使用的称号
     *
     * @return
     */
    public RoleTitleVO getShowTitle() {
        return this.titleCacheByTId.get(this.showTitleId);
    }

    /**
     * 通过id得到详细信息
     *
     * @param 称号id
     * @return
     */
    public RoleTitleVO getByTId(String tId) {
        return getByTId(Integer.parseInt(tId));
    }

    /**
     * 通过id得到详细信息
     *
     * @param 称号id
     * @return
     */
    public RoleTitleVO getByTId(int tId) {
        return this.titleCacheByTId.get(tId);
    }


    //玩家登陆时发送称号系统消息
    public void sendTitleSysInfo(RoleEntity role_info) {
        List<RoleTitleVO> list = getRoleTitleList();
        for (int i = 0; i < list.size(); i++) {
            RoleTitleVO vo = list.get(i);
            if (vo.getTId() == 39) {
                SystemInfoService systemInfoService = new SystemInfoService();
                systemInfoService.insertSystemInfoBySystem("天下第一的" + role_info.getBasicInfo().getName() + "来了,大家夹道欢迎!");
            }
            if (vo.getTId() == 42) {
                SystemInfoService systemInfoService = new SystemInfoService();
                systemInfoService.insertSystemInfoBySystem("小财神" + role_info.getBasicInfo().getName() + "上线了!");
            }
            if (vo.getTId() == 43) {
                SystemInfoService systemInfoService = new SystemInfoService();
                systemInfoService.insertSystemInfoBySystem("大财神" + role_info.getBasicInfo().getName() + "上线了!");
            }
            if (vo.getTId() == 44) {
                SceneVO sceneVO = SceneCache.getById(role_info.getBasicInfo().getSceneId());
                SystemInfoService systemInfoService = new SystemInfoService();
                systemInfoService.insertSystemInfoBySystem("明教的首席弟子" + role_info.getBasicInfo().getName() + "在" + sceneVO.getSceneName() + "上线了!");
            }
            if (vo.getTId() == 45) {
                SceneVO sceneVO = SceneCache.getById(role_info.getBasicInfo().getSceneId());
                SystemInfoService systemInfoService = new SystemInfoService();
                systemInfoService.insertSystemInfoBySystem("少林的首席弟子" + role_info.getBasicInfo().getName() + "在" + sceneVO.getSceneName() + "上线了!");
            }
            if (vo.getTId() == 46) {
                SceneVO sceneVO = SceneCache.getById(role_info.getBasicInfo().getSceneId());
                SystemInfoService systemInfoService = new SystemInfoService();
                systemInfoService.insertSystemInfoBySystem("丐帮的首席弟子" + role_info.getBasicInfo().getName() + "在" + sceneVO.getSceneName() + "上线了!");
            }
        }
    }
}
