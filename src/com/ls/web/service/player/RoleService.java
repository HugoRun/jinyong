package com.ls.web.service.player;

import com.ben.dao.deletepart.DeletePartDAO;
import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.vo.honour.TitleVO;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.staticcache.honour.TitleCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.UGrowDao;
import com.ls.ben.dao.storage.WareHouseDao;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.ShortcutVO;
import com.ls.ben.vo.info.partinfo.UGrowVO;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.ben.vo.mounts.MountsVO;
import com.ls.ben.vo.mounts.UserMountsVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.organize.faction.Faction;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.Shortcut;
import com.ls.pub.constant.Wrap;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.login.LoginService;
import com.ls.web.service.mounts.MountsService;
import com.ls.web.service.pk.PKHiteService;
import com.ls.web.service.rank.RankService;
import com.lw.service.laborage.LaborageService;
import com.pm.dao.setting.SettingDao;
import com.web.service.friend.FriendService;
import com.web.service.task.TaskVisitLeadService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

/**
 * 功能：角色管理
 */
public class RoleService {

    /**
     * 通过p_pk得到角色
     */
    public static RoleEntity getRoleInfoById(String pPk) {
        return RoleCache.getByPpk(pPk);
    }

    /**
     * 通过p_pk得到角色
     */
    public static RoleEntity getRoleInfoById(int pPk) {
        return RoleCache.getByPpk(pPk);
    }

    /**
     * 创建角色
     *
     * @param uPk
     * @param role_name
     * @param sex
     * @param race
     * @return 返回null表示创建失败
     */
    public RoleEntity createRole(String uPk, String role_name, String sex, String race) {
        //初始化角色相关数据表
        int p_pk = this.initDataTable(uPk, role_name, sex, Integer.parseInt(race));

        if (p_pk == -1) {
            return null;
        }

        RoleEntity roleInfo = RoleCache.getByPpk(p_pk);

        //初始化角色逻辑相关操作
        //initRoleLogic(roleInfo);

        return roleInfo;
    }

    /**
     * 删除角色处理
     */
    public boolean delRole(String uPk, String pPk) {
        RoleEntity role_info = getRoleInfoById(pPk);
        if (role_info == null || StringUtils.isEmpty(uPk) || role_info.getUPk() != Integer.parseInt(uPk)) {
            //角色已删除
            return false;
        }

        //************角色删除处理
        //如果在线，则做离线处理
        if (role_info.isOnline()) {
            LoginService loginService = new LoginService();
            loginService.loginoutRole(pPk);
        }

        //退出帮派处理
        Faction faction = role_info.getBasicInfo().getFaction();
        if (faction != null) {
            faction.delMember(role_info);
        }
        //删除好友
        FriendService fs = new FriendService();
        fs.removeFriendInfo(Integer.parseInt(pPk));
        //删除仇敌
        PKHiteService ps = new PKHiteService();
        ps.removeHiteInfo(Integer.parseInt(pPk));
        //删除排行榜
        RankService rs = new RankService();
        rs.removeRandInfo(Integer.parseInt(pPk));
        //删除装备
        PlayerEquipDao ped = new PlayerEquipDao();
        ped.clear(Integer.parseInt(pPk));
        //删除坐骑
        MountsService ms = new MountsService();
        ms.removeMountsInfo(Integer.parseInt(pPk));
        //标识数据
        DeletePartDAO deletePartDAO = new DeletePartDAO();
        int result = deletePartDAO.delete(pPk, uPk, role_info.getIsRookie());
        return result == 1;
    }

    /**
     * 设置角色为删除状态,将删除标记置1
     *
     * @param p_pk
     */
    public void setRoleDeleteState(int p_pk) {
        // 设置角色为删除状态
        PlayerService playerService = new PlayerService();
        playerService.updateDeleteFlag(p_pk, 1);
    }

    /**
     * 恢复角色,将删除标记置零
     *
     * @param p_pk
     */
    public void resumeRole(int p_pk) {
        // 恢复角色
        PlayerService playerService = new PlayerService();
        playerService.updateDeleteFlag(p_pk, 0);
    }

    /**
     * 通过session得到角色信息
     */
    public RoleEntity getRoleInfoBySession(HttpSession session) {
        String pPk = (String) session.getAttribute("pPk");
        if (StringUtils.isEmpty(pPk)) {
            return null;
        }
        return getRoleInfoById(pPk);
    }

    /**
     * 通过角色角色名字的到角色信息
     */
    public RoleEntity getRoleInfoByName(String role_name) {
        int p_pk = getByName(role_name);
        return getRoleInfoById(p_pk + "");
    }

    /**
     * 新手登陆时给玩家初始化的处理
     */
    public void initRoleLogic(RoleEntity roleInfo) {
        if (roleInfo == null) {
            return;
        }

        TaskVisitLeadService taskVisitLeadService = new TaskVisitLeadService();
        GoodsService goodsService = new GoodsService();

        //学习一个技能
        int skill_id = 45;//技能id
        PlayerSkillVO skill = roleInfo.getRoleSkillInfo().study(skill_id);

        // 给玩家发放初始道具
        int prop_id = 390;//道具id
        goodsService.putPropToWrap(roleInfo.getPPk(), prop_id, 5, GameLogManager.G_SYSTEM);
        prop_id = 391;//道具id
        goodsService.putPropToWrap(roleInfo.getPPk(), prop_id, 5, GameLogManager.G_SYSTEM);
        prop_id = 148;//道具id,黄忠里
        goodsService.putPropToWrap(roleInfo.getPPk(), prop_id, 20, GameLogManager.G_SYSTEM);

        //设置快捷键
        ShortcutService shortcutService = new ShortcutService();
        List<ShortcutVO> shortcut_list = roleInfo.getRoleShortCutInfo().getShortList();
        //设置第1个快捷键为攻击
        shortcutService.updateShortcut(shortcut_list.get(0).getScPk(), Shortcut.ATTACK, 0, roleInfo.getPPk() + "");
        //设置第2个快捷键为药品
        shortcutService.updateShortcut(shortcut_list.get(1).getScPk(), Shortcut.CURE, prop_id, roleInfo.getPPk() + "");
        //设置第3个快捷键为技能
        shortcutService.updateShortcut(shortcut_list.get(2).getScPk(), Shortcut.SKILL, skill.getSPk(), roleInfo.getPPk() + "");

        //发装备
        for (int equip_id = 127; equip_id <= 134; equip_id++) {
            //发装备并穿在身上
            goodsService.giveEquipOnBody(roleInfo, equip_id, Equip.Q_ORANGE);
        }


        //给玩家增加初始称号和任务
        String t_zu = null;//任务组
        int race = roleInfo.getBasicInfo().getPRace();
        TitleVO race_title = null;//种族称号
        switch (race) {
            case 1://妖
                race_title = TitleCache.getById(10);
                t_zu = "ty_juezhanbuzhoushan01";// 任务组
                break;
            case 2://巫
                race_title = TitleCache.getById(18);
                t_zu = "ty_juezhanbuzhoushan02";// 任务组
                break;
        }
        // 初始化一条任务放在身上
        taskVisitLeadService.acceptTask(roleInfo, t_zu);
        //获得一个称号
        roleInfo.getTitleSet().gainTitle(race_title);

        //初始化血量和法力
        PlayerService playerService = new PlayerService();
        PartInfoVO player = playerService.getPlayerByRoleInfo(roleInfo);
        PropertyService propertyService = new PropertyService();
        propertyService.updateHpProperty(player.getPPk(), player.getPUpHp());
        propertyService.updateMpProperty(player.getPPk(), player.getPUpMp());
    }


    /**
     * 初始化角色相关表
     *
     * @param uPk
     * @param role_name
     * @param sex
     * @param race
     * @return
     */
    private int initDataTable(String uPk, String role_name, String sex, int race) {
        UGrowDao u_grow_dao = new UGrowDao();
        UGrowVO init_info = u_grow_dao.getMaxGradeInfo(race);//得到角色初始化信息

        if (init_info == null) {
            return -1;
        }

        /** 生命值上限 */
        String pUpHp = init_info.getGHP() + "";
        /** 生命值上限 */
        String pUpMp = init_info.getGMP() + "";
        /** 攻击 */
        String pGj = init_info.getGGj() + "";
        /** 防御 */
        String pFy = init_info.getGFy() + "";
        /** 暴击率 */
        String pDropMultiple = init_info.getGDropMultiple() + "";

        /** 初始金钱 */
        String pCopper = "1000";
        /** 开关1关2开 */
        String pPks = "1";
        /** 包裹容量 */
        String pWrapContent = "50";

        /** 出生地坐标 */
        String pMap = "1";
        switch (race) {
            case 1://妖
                pMap = "328";
                break;
            case 2://巫
                pMap = "329";
                break;
        }

        //初始化角色基本信息表
        PartInfoDao partInfoDAO = new PartInfoDao();
        int p_pk = partInfoDAO.add(uPk, role_name, sex, pUpHp, pUpMp, pGj, pFy, pCopper, pPks, pDropMultiple, pMap, pWrapContent, race);

        if (p_pk == -1) {
            return p_pk;
        }

        //初始化快捷键
        PartInfoDAO dao = new PartInfoDAO();
        dao.initShortcut(p_pk + "");
        //初始化仓库数据
        WareHouseDao wareHouseDao = new WareHouseDao();
        wareHouseDao.insertWareHouse(Integer.parseInt(uPk), p_pk, Wrap.COPPER);
        //初始化设置数据
        SettingDao settingDao = new SettingDao();
        settingDao.createSysSetting(p_pk);
        //初始化角色工资表
        LaborageService ls = new LaborageService();
        ls.insertPlayLaborageMessage(p_pk);
        return p_pk;
    }


    public String[] getName(String p_pk) {
        return new PartInfoDAO().getName(p_pk);
    }

    public int getByName(String pName) {
        return new PartInfoDao().getByName(pName);
    }

    /*******************系统随机赠送给角色免费的一级坐骑**********************/
    public void addMountsForPart(RoleEntity roleInfo) {
        int[] types = new int[]{1, 2, 3};
        Random random = new Random();
        int randomNum = random.nextInt(3);
        int mountsType = types[randomNum];
        MountsService ms = new MountsService();
        MountsVO mv = ms.getMountsInfoBySystem(mountsType);
        UserMountsVO umv = new UserMountsVO();
        if (mv == null) {
            return;
        }
        umv.setPpk(roleInfo.getPPk());
        umv.setMountsID(mv.getId());
        umv.setMountsState(1);
        roleInfo.getMountSet().addMounts(umv);
    }

    /**
     * // 攻城战测试时专用方法.
     *
     * @param p_pk
     * @param parseInt
     *//*
	private void tongSiegeInit(int p_pk, int uPk)
	{
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>(3);
		list.add("mingjiao");
		list.add("gaibang");
		list.add("shaolin");

		PartInfoDAO dao = new PartInfoDAO();
		Random random = new Random();
		int menpai = random.nextInt(list.size());

		List<String> menpailist = new ArrayList<String>(16);
		// 如果是0是，
		if (menpai == 0)
		{
			menpailist.add("mingjiao");
			menpailist.add("明教");
			menpailist.add("wuxingqijingrui");
			menpailist.add("五行旗精锐");
			dao.getMenPaiInfo(menpailist, 0);
		}
		else
			if (menpai == 1)
			{
				menpailist.add("gaibang");
				menpailist.add("丐帮");
				menpailist.add("sandaidizi");
				menpailist.add("三袋弟子");
				dao.getMenPaiInfo(menpailist, 1);
			}
			else
				if (menpai == 2)
				{
					menpailist.add("shaolin");
					menpailist.add("少林");
					menpailist.add("wuseng");
					menpailist.add("武僧");
					dao.getMenPaiInfo(menpailist, 2);
				}

		dao.updatePartInfo(menpailist, p_pk);
		dao.addSkillInfo(p_pk, menpai);
	}*/


}
