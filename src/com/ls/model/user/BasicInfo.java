package com.ls.model.user;

import com.ben.jms.JmsUtil;
import com.ben.lost.CompassService;
import com.ben.shitu.model.ShituConstant;
import com.ben.shitu.service.ShituService;
import com.ls.ben.cache.dynamic.manual.attack.AttacckCache;
import com.ls.ben.dao.info.buff.BuffEffectDao;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.dao.info.partinfo.UGrowDao;
import com.ls.ben.vo.info.buff.BuffEffectVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.UGrowVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.organize.faction.Faction;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.buff.BuffSystem;
import com.ls.pub.constant.buff.BuffType;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.buff.BuffEffectService;
import com.ls.web.service.faction.FactionService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.MyServiceImpl;
import com.ls.web.service.rank.RankService;
import com.ls.web.service.room.RoomService;
import com.lw.service.UnchartedRoom.UnchartedRoomService;
import com.lw.service.menpaicontest.MenpaiContestService;
import com.pub.GameArgs;
import com.web.service.popupmsg.PopUpMsgService;

import java.util.Calendar;
import java.util.Date;

/**
 * 功能：玩家基本信息对应u_part_info表
 *
 * @author ls Apr 3, 2009 1:51:51 PM
 */
public class BasicInfo extends UserBaseWithSave {
    /** ***********************角色创建后不变的属性************************* */

    /**
     * 角色id
     */
    private final int pPk;
    /**
     * 创建人员信息id
     */
    private final int uPk;
    /**
     * 角色名
     */
    private final String name;
    /**
     * 显示角色名,可能会因状态的不同而加上不同的标记
     */
    private String displayName;
    /**
     * 性别
     */
    private final int sex;
    /**
     * 种族，1妖2巫
     **/
    private int pRace;
    /**
     * 创建时间
     */
    private final String createTime;

    /** ***********************角色创建后变话的属性************************* */

    /**
     * 等级
     */
    private int grade;
    /**
     * 当前场景的id
     */
    private String sceneId;
    private SceneVO sceneInfo;

    /**
     * 是否已婚 1没结婚 2 结婚
     */
    private int married;
    /**
     * 伴侣ID
     */
    private int partner;

    /**
     * 帮会
     */
    private int fId;//帮派id
    private int fJob;//帮派职位
    private int fContribute;//帮派贡献
    private Date fJoinTime;//加入帮派的时间
    private String fTitle;//帮派称号

    /**
     * 铜钱
     */
    private long copper;
    /**
     * 包裹容量
     */
    private int wrapContent;
    /**
     * 包裹剩余数量
     */
    private int wrapSpare = -1;//不需要存储到数据库

    /**
     * 生命值
     */
    private int hp;
    /**
     * 法力值
     */
    private int mp;
    /**
     * 血量本级上限
     */
    private int upHp;
    /**
     * mp本级上限
     */
    private int upMp;

    /**
     * 角色当前经验
     */
    private String curExp;

    /**
     * pk罪恶值
     */
    private int evilValue;
    /**
     * 开关1关2开
     */
    private int pkSwitch;
    /**
     * pk更改时间
     */
    private Date pkChangeTime;

    /**
     * 角色本身攻击
     */
    private int basicGj;
    /**
     * 角色本身防御
     */
    private int basicFy;

    /**
     * 暴击率
     */
    private double multipleDamage;
    /**
     * pk后如果死亡，身上带有九转丹时,所应该去的地方
     */
    private int shouldScene;

    /**
     * 作为师父的等级
     */
    private int te_level;

    /**
     * 上一次传功时间
     */
    private String chuangong;

    // 最后一次招徒或者拜师的时间
    private Date last_shoutu_time;

    /**
     * 临时名称
     */
    private String temp_Name;

    /**
     * 活动擂台的组别
     */
    private int zu = -1;

    // 玩家的新手状态
    private int player_state_by_new;

    private int attack_npc = 0;

    private int scratchticketnum = 0;

    private int addscratchticketnum = 0;
    /**
     * 眩晕回合
     **/
    private int xuanyunhuihe = 0;
    /**
     * 攻击回合
     **/
    private int acthuihe = 0;
    /**
     * 效果
     **/
    private int actcontent = 0;
    /**
     * 防御回合
     **/
    private int defhuihe = 0;
    /**
     * 效果
     **/
    private int defcontent = 0;
    /**
     * 描述
     **/
    private String menpaiskilldisplay = "";
    /**
     * pk保护时间
     */
    private long pk_safe_time = 0;
    /**
     * PK保护状态
     **/
    private int pk_safe_state = 0;
    //玩家进入天关时间
    private long tianguan_time = 0;
    //玩家进入到天关的第几层
    private String tianguan_npc = "";
    //玩家在该天关的杀敌数
    private int tianguan_kill_num = 0;
    //玩家是否在杀NPC
    private int menpainpcstate = 0;
    //玩家杀的NPC MENU 存放
    private int menpainpcid = 0;
    /**
     * 是否中毒
     */
    private boolean isPoisoning = false;
    /**
     * 中毒后遭受的额外伤害
     */
    /**
     * 中毒回合数
     */
    private int poisonCount = 0;
    private int addDamage;


    public BasicInfo(int p_pk) throws Exception {
        super(p_pk, "u_part_info", "p_pk", p_pk + "");
        PartInfoDao partInfoDao = new PartInfoDao();
        PartInfoVO vo = partInfoDao.getPartInfoByID(p_pk);

        if (vo == null) {
            throw new Exception();
        }

        pPk = vo.getPPk();
        uPk = vo.getUPk();
        temp_Name = name = vo.getPName();
        sex = vo.getPSex();
        createTime = vo.getCreateTime();

        grade = vo.getPGrade();

        RoomService roomService = new RoomService();
        sceneId = vo.getPMap();
        setSceneInfo(roomService.getById(sceneId));

        married = vo.getPHarness();
        partner = vo.getPFere();
        pRace = vo.getPRace();
        /** 金钱 */
        copper = Long.parseLong(vo.getPCopper());
        /** 包裹最大容量 */
        wrapContent = vo.getPWrapContent();

        /** 生命值 */
        hp = vo.getHP();
        /** 法力值 */
        mp = vo.getPMp();
        /** 血量本级上限 */
        upHp = vo.getPUpHp();
        /** mp本级上限 */
        upMp = vo.getPUpMp();

        /** 角色当前经验 */
        curExp = vo.getPExperience();

        /** pk罪恶值 */
        evilValue = vo.getPPkValue();
        /** 开关1关2开 */
        pkSwitch = vo.getPPks();
        /** 上次更改pk开关的时间 */
        pkChangeTime = vo.getPkChangeTime();

        /** 角色本身攻击 */
        basicGj = vo.getPGj();
        /** 角色本身防御 */
        basicFy = vo.getPFy();


        multipleDamage = vo.getDropMultiple();
        te_level = vo.getTe_level();
        chuangong = vo.getChuangong();
        last_shoutu_time = vo.getLast_shoutu_time();
        player_state_by_new = vo.getPlayer_state_by_new();

        //********帮派相关信息
        this.fId = vo.getFId();
        this.fJob = vo.getFJob();
        this.fContribute = vo.getFContribute();
        this.fJoinTime = vo.getFJoinTime();
        this.fTitle = vo.getFTitle();
    }

    /**
     * 战斗结束，初始化战斗相关状态变量
     */
    public void initFightState() {
        setPoisoning(false);
        this.setPoisonCount(0);
        setAddDamage(0);
        setXuanyunhuihe(0);
        setActhuihe(0);
        setActcontent(0);
        setDefhuihe(0);
        setDefcontent(0);
        setMenpaiskilldisplay("");
        setPk_safe_time(new Date().getTime());
        setPk_safe_state(1);
    }

    /**
     * 通过帮派职位判断是否有权限操作：只用job职务（包括job职务）的权限才可执行
     *
     * @param job 可以执行的职务
     * @return
     */
    public boolean isOperateByFJob(int job) {
        return this.fJob >= job;
    }

    /**
     * 是否可以转让族长
     */
    public boolean getIsChangeZuzhang() {
        return this.isOperateByFJob(Faction.ZUZHANG);
    }

    /**
     * 是否可以发布招募信息
     */
    public boolean getIsRecruit() {
        return this.isOperateByFJob(Faction.HUFA);
    }

    /**
     * 是否可以更改成员称号
     */
    public boolean getIsChangeTitle() {
        return this.isOperateByFJob(Faction.ZHANGLAO);
    }

    /**
     * 是否可以更改职位
     */
    public boolean getIsChangeJob() {
        return this.isOperateByFJob(Faction.ZHANGLAO);
    }

    /**
     * 是否可以管理入帮申请
     */
    public boolean getIsManageApply() {
        return this.isOperateByFJob(Faction.ZHANGLAO);
    }

    /**
     * 是否可以删除成员
     *
     * @return
     */
    public boolean getIsDelMember() {
        return this.isOperateByFJob(Faction.ZHANGLAO);
    }

    /**
     * 是否可以发布公告
     *
     * @return
     */
    public boolean getIsPublishNotice() {
        return this.isOperateByFJob(Faction.HUFA);
    }

    /**
     * 是否可以删除公告
     *
     * @return
     */
    public boolean getIsDelNotice() {
        return this.isOperateByFJob(Faction.ZHANGLAO);
    }

    /**
     * PK增加氏族贡献和氏族声望
     *
     * @return 是否增加氏族贡献
     */
    public boolean addFContributeAndFPrestige() {
        Faction faction = getFaction();
        if (faction != null) {
            //***********如果加入帮派
            addFContribute(1);//增加个人帮派贡献
            faction.updatePrestige(1);//增加帮派声望
            return true;
        }
        return false;
    }

    /**
     * 得到帮派名字
     */
    public String getFactionName() {
        Faction faction = this.getFaction();
        if (faction != null) {
            return faction.getName();
        } else {
            return "无";
        }
    }

    /**
     * 得到帮派信息
     */
    public Faction getFaction() {
        if (this.fId == 0) {
            return null;
        }
        Faction faction = FactionService.getById(fId);
        if (faction == null) {
            this.leaveFaction();
        }
        return faction;
    }

    /**
     * 得到帮派id
     */
    public int getFId() {
        Faction faction = getFaction();
        if (faction == null) {
            return 0;
        } else {
            return faction.getId();
        }
    }

    private void setFId(int id) {
        fId = id;
        uPartInfoTab.addPersistenceColumn("f_id", fId + "");
    }

    /**
     * 离开帮派
     */
    public void leaveFaction() {
        if (this.fId <= 0) {
            return;
        }
        this.setFId(0);
        this.setFJob(0);
        this.setFContribute(0);
        this.setFTitle(null);
        save();
    }

    /**
     * 更改帮派称号
     *
     * @param new_job
     */
    public void changeFTitle(String new_title) {
        if (this.fId <= 0) {
            return;
        }
        this.setFTitle(new_title);
        save();
    }

    /**
     * 更改帮派职位
     *
     * @param new_job
     */
    public void changeFJob(int new_job) {
        if (this.fId <= 0 || new_job == this.fJob) {
            return;
        }
        this.setFJob(new_job);
        save();
    }

    /**
     * 判断是否有足够的帮派荣誉
     *
     * @return
     */
    public boolean isEnoughFContribute(int need_attribute) {
        return this.fId > 0 && need_attribute <= this.fContribute;
    }

    /**
     * 更改帮派荣誉
     *
     * @param update_attribute 正数表示加，负数表示减
     */
    public void addFContribute(int update_attribute) {
        if (update_attribute == 0) {
            return;
        }
        if (update_attribute < 0 && !this.isEnoughFContribute(-update_attribute)) {
            return;
        }
        this.setFContribute(this.fContribute + update_attribute);
        save();
    }

    /**
     * 加入帮派
     */
    public void jionFaction(int fId, int job) {
        if (this.fId > 0) {
            return;
        }
        this.setFId(fId);
        this.setFJob(job);
        this.setFContribute(0);
        this.setFJoinTime(new Date());
    }

    /**
     * 判断包裹个数是否足够
     */
    public String isEnoughWrapSpace(int need_space) {
        if (need_space > this.getWrapSpare()) {
            return "包裹空间需要" + need_space + "格子,现在只有" + this.wrapSpare + "格子";
        }
        return null;
    }

    /**
     * 得到金钱描述
     *
     * @return
     */
    public String getMoneyDes() {
        return MoneyUtil.changeCopperToStr(copper);
    }

    /**
     * 判断钱是否足够
     */
    public boolean isEnoughMoney(int need_money) {
        return this.copper >= need_money;
    }

    /**
     * 角色属性重置
     */
    public void reset() {
        UGrowDao u_grow_dao = new UGrowDao();
        UGrowVO init_info = u_grow_dao.getByGradeAndRace(1, this.pRace);//得到角色初始化信息


        /** 生命值上限 */
        int pUpHp = init_info.getGHP();
        /** 生命值上限 */
        int pUpMp = init_info.getGMP();
        /** 攻击 */
        int pGj = init_info.getGGj();
        /** 防御 */
        int pFy = init_info.getGFy();
        /** 暴击率 */
        double pDropMultiple = init_info.getGDropMultiple();

        /** 初始金钱 */
        int pCopper = 100;
        /** 开关1关2开 */
        int pPks = 1;
        /** 包裹容量 */
        int pWrapContent = 50;

        /** 出生地坐标 */
        String pMap = "1";
        switch (this.pRace) {
            case 1:
                pMap = "1";
                break;//妖
            case 2:
                pMap = "66";
                break;//巫
        }

        this.setGrade(1);//初始化成1级
        this.setCurExp("0");
        this.setUpHp(pUpHp);
        this.setUpMp(pUpMp);
        this.setBasicGj(pGj);
        this.setBasicFy(pFy);
        this.setCopper(pCopper);
        this.setPkSwitch(pPks);
        this.setWrapContent(pWrapContent);
        this.setMultipleDamage(pDropMultiple);

        this.setPlayer_state_by_new(0);//非新手状态

        //还原当前血量和法力
        this.setHp(upHp);
        this.setMp(upMp);

        this.wrapSpare = pWrapContent;//初始化剩余包裹个数

        this.updateSceneId(pMap);

        this.save();
    }

    /**
     * 增加等级（升级）
     */
    public void addGrade() {
        this.setGrade(++grade);
        UGrowDao growDao = new UGrowDao();
        UGrowVO grow = growDao.getByGradeAndRace(grade, this.pRace);
        // 更新partInfoVO(升级)
        LogService logService = new LogService();
        logService.recordPlayerLog(this.getPPk(), this.getName(), upHp + "", grow.getGHP() + "", "HP玩家等级" + this.getGrade());
        logService.recordPlayerLog(this.getPPk(), this.getName(), upMp + "", grow.getGMP() + "", "MP玩家等级" + this.getGrade());
        logService.recordPlayerLog(this.getPPk(), this.getName(), basicGj + "", grow.getGGj() + "", "GJ玩家等级" + this.getGrade());
        logService.recordPlayerLog(this.getPPk(), this.getName(), basicFy + "", grow.getGFy() + "", "FY玩家等级" + this.getGrade());
        this.setUpHp(upHp + grow.getGHP());
        this.setUpMp(upMp + grow.getGMP());
        this.setHp(upHp + grow.getGHP());
        this.setMp(upMp + grow.getGMP());
        this.setBasicGj(basicGj + grow.getGGj());
        this.setBasicFy(basicFy + grow.getGFy());

        this.setMultipleDamage(grow.getGDropMultiple());// 暴击率没有被持久化

        // 玩家等级达到30级，pk开关默认修改为开启
        if (this.grade == 30) {
            updatePkSwitchIn30Grade(2);
        }

        String content = "角色名:" + name + ";升级后等级为:" + grade + ";当前经验为:" + curExp;
        logService.recordUpgradeLog(pPk, name, content);
        int digit = GameConfig.getGoUpGrade(grade, "go_up_grade");// 返回玩家等级是否在系统设定之内的
        if (digit != -1) {
            new PopUpMsgService().addSysSpecialMsg(pPk, grade, digit, PopUpMsgType.GO_UP_GRADE);
        }
        // 特殊道具在特殊等级的情况
        if (grade == Integer.parseInt(GameConfig.getPropertiesObject("prop_grade_role_grade"))) {
            new PopUpMsgService().addSysSpecialMsg(pPk, grade, 0, PopUpMsgType.PROP_GRADE);
        }
        if (grade == ShituConstant.BAISHI_LEVEL_LIMIT) {
            new ShituService().delShitu(pPk);
        }
        // 统计需要
        new RankService().updateAdd(pPk, "p_level", 1);
        new MyServiceImpl().levelUp(pPk);
        RoleEntity role_info = this.getRoleEntity();
        JmsUtil.updateLevel(role_info.getStateInfo().getSuper_qudao(), role_info.getStateInfo().getQudao(), role_info.getStateInfo().getUserid(), name, grade);
        Faction faction = this.getFaction();
        if (faction != null) {
            faction.memUpgrade();
        }
    }

    public void updateSceneId(String new_scene_id) {
        if (sceneId.equals(new_scene_id)) {
            return;// scene_id没变不做处理
        }

        RoomService roomService = new RoomService();
        SceneVO scene_info = roomService.getById(new_scene_id);
        if (scene_info == null) {
            // 如果没有该地图id的数据
            return;
        }

        MenpaiContestService ms = new MenpaiContestService();
        UnchartedRoomService rs = new UnchartedRoomService();
        RoleEntity role_info = this.getRoleEntity();
        ms.updatePlayerMenpaiContestState(role_info, new_scene_id);
        rs.updatePlayerUnchartedRoomState(role_info, new_scene_id);// 秘境

        // 场景改变时处理
        this.setSceneId(new_scene_id);
        this.setSceneInfo(scene_info);

        AttacckCache attacckCache = new AttacckCache();

        attacckCache.destoryAllNpc(this.pPk);// 清除主动怪

        CompassService.pass(this);
    }

    public void updateMarried(int married) {
        this.setMarried(married);
    }

    public void updatePartner(int partner) {
        this.setPartner(partner);
    }

    /**
     * 增加金钱的值
     *
     * @param addCopper 增加的值(增量)
     */
    public void addCopper(long addCopper) {
        if (addCopper == 0) {
            return;
        }
        this.setCopper(copper + addCopper);
        // 统计需要
        new RankService().updateAdd(pPk, "money", addCopper);
    }

    /**
     * 增加包裹容量
     *
     * @param addWrapContent 增加的值(增量)
     */
    public void addWrapContent(int addWrapContent) {
        this.setWrapContent(this.wrapContent + addWrapContent);
    }

    /**
     * 改变包裹的空间
     *
     * @param updateWrapSpare 增加或减少的包裹的剩余空间的数量(增量)
     */
    public void addWrapSpare(int addWrapSpare) {
        int new_wrap_spare = this.getWrapSpare() + addWrapSpare;
        if (new_wrap_spare < 0) {
            new_wrap_spare = 0;
        } else if (new_wrap_spare > this.wrapContent) {
            new_wrap_spare = this.wrapContent;
        }
        this.wrapSpare = new_wrap_spare;
    }

    public void updateHp(int hp) {
        this.setHp(hp);
    }

    public void updateMp(int mp) {
        this.setMp(mp);
    }

    /**
     * 增加经验
     *
     * @param curExp 为增加后的经验，不是增加的幅度
     */
    public void updateCurExp(String curExp) {
        this.setCurExp(curExp);
        // 统计需要
        new RankService().updatea(pPk, "p_exp", curExp);
    }

    /**
     * 增加经验
     *
     * @param addExp 为经验增加幅度
     */
    public void updateAddCurExp(long addExp) {
        long currentExp = Integer.parseInt(this.curExp.trim()) + addExp;
        if (currentExp <= 0) {
            return;
        }
        // 统计需要
        new RankService().updateAdd(pPk, "p_exp", addExp);
        this.setCurExp(currentExp + "");
    }

    /**
     * 修改师父等级
     *
     * @param evilValue
     */
    public void updateTeLevel(int te_level) {
        if (te_level > 1) {
            this.setTe_level(te_level);
        }
    }

    /**
     * 修改师父传功时间
     *
     * @param evilValue
     */
    public void updateChuangong() {
        this.setChuangong(com.ben.shitu.model.DateUtil.getDateSecondFormat());
    }

    // 更新罪恶值,evilValue为更新后的值
    private void updateEvilValue(int evilValue) {
        if (evilValue < 0) {
            evilValue = 0;
        }
        if (this.evilValue == 0 && evilValue == 0) {
            return;
        }
        // 统计需要
        new RankService().updatea(pPk, "evil", evilValue);
        this.setEvilValue(evilValue);
    }

    /**
     * 按时间消耗罪恶值
     */
    public void consumeEvilValueByTime() {
        if (this.evilValue <= 0) {
            return;
        }
        long cur_time = Calendar.getInstance().getTimeInMillis();
        long pre_reduce_time = this.getRoleEntity().getStateInfo().getEvilValueConsumeTime();//前一次消耗罪恶值的时间

        int sub_evil_value = (int) ((cur_time - pre_reduce_time) / GameArgs.CONSUME_TIME_UNIT);//可以消除的罪恶值
        if (sub_evil_value > 0) {
            addEvilValue(-sub_evil_value);
            this.getRoleEntity().getStateInfo().updateEvilValueConsumeTime(pre_reduce_time + sub_evil_value * GameArgs.CONSUME_TIME_UNIT);
        }
    }

    // 更新罪恶值,addEvilValue为要更新后的值,addEvilValue为负代表减少
    public void addEvilValue(int addEvilValue) {
        if (addEvilValue == 0) {
            return;
        }
        if (addEvilValue < 0)//减罪恶值
        {
            BuffEffectService buffEffectService = new BuffEffectService();
            BuffEffectDao buffEffectDao = new BuffEffectDao();
            BuffEffectVO buffvo = buffEffectDao.getBuffEffectByBuffType(this.p_pk, BuffSystem.PLAYER, BuffType.REDUCEPKVALUE);
            if (buffEffectService.isEffected(buffvo)) {
                addEvilValue *= (buffvo.getBuffEffectValue() / 100);
            }
        }
        this.updateEvilValue(evilValue + addEvilValue);
    }

    /**
     * 控制PK开关
     *
     * @param pkSwitch
     */
    public void updatePkSwitch(int pkSwitch) {
        this.setPkSwitch(pkSwitch);
        this.setPkChangeTime();
    }

    /**
     * 控制PK开关,仅在30级有程序自动调用,其他的全部不能用, 此方法只修改pk开关,不修改pk开关修改时间
     *
     * @param pkSwitch
     */
    public void updatePkSwitchIn30Grade(int pkSwitch) {
        this.setPkSwitch(pkSwitch);
        // 特殊道具在特殊等级的情况
        if (pkSwitch == Integer.parseInt(GameConfig.getPropertiesObject("pk_switch_type"))) {
            new PopUpMsgService().addSysSpecialMsg(pPk, pkSwitch, 0, PopUpMsgType.PK_SWITCH);
        }
    }

    /**
     * 新手状态
     *
     * @param player_state_by_new
     */
    public void updatePlayer_state_by_new(int player_state_by_new) {
        this.setPlayer_state_by_new(player_state_by_new);
        this.save();
    }

    public int getPPk() {
        return pPk;
    }

    public int getUPk() {
        return uPk;
    }

    public String getName() {
        return temp_Name;
    }

    public String getRealName() {
        return name;
    }

    public String getTemp_Name() {
        return temp_Name;
    }

    public void setTemp_Name(String temp_Name) {
        this.temp_Name = temp_Name;
    }

    public String getSexName() {
        return ExchangeUtil.exchangeToSex(sex);
    }

    public int getSex() {
        return sex;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getGrade() {
        return grade;
    }

    private void setGrade(int grade) {
        this.grade = grade;
        uPartInfoTab.addPersistenceColumn("p_grade", grade + "");
    }

    public String getSceneId() {
        return sceneId;
    }

    private void setSceneId(String sceneId) {
        this.sceneId = sceneId;
        uPartInfoTab.addPersistenceColumn("p_map", sceneId);
    }

    public int getMarried() {
        return married;
    }

    private void setMarried(int married) {
        this.married = married;
        uPartInfoTab.addPersistenceColumn("p_harness", married + "");
    }

    public int getPartner() {
        return partner;
    }

    private void setPartner(int partner) {
        this.partner = partner;
        uPartInfoTab.addPersistenceColumn("p_fere", partner + "");
    }

    public long getCopper() {
        return copper;
    }

    private void setCopper(long copper) {
        this.copper = copper;
        uPartInfoTab.addPersistenceColumn("p_copper", copper + "");
    }

    public int getWrapContent() {
        return wrapContent;
    }

    private void setWrapContent(int wrapContent) {
        this.wrapContent = wrapContent;
        uPartInfoTab.addPersistenceColumn("p_wrap_content", wrapContent + "");
    }

    public int getWrapSpare() {
        if (wrapSpare == -1) {
            PlayerEquipDao playerEquipDao = new PlayerEquipDao();
            PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
            int cur_content_on_wrap = 0;
            cur_content_on_wrap = playerEquipDao.getNumOnWrap(pPk);
            cur_content_on_wrap += playerPropGroupDao.getNumOnWrap(pPk);
            wrapSpare = this.getWrapContent() - cur_content_on_wrap;
        }
        return wrapSpare;
    }

    public int getHp() {
        return hp;
    }

    private void setHp(int hp) {
        this.hp = hp;
        uPartInfoTab.addPersistenceColumn("p_hp", hp + "");
    }

    public int getMp() {
        return mp;
    }

    private void setMp(int mp) {
        this.mp = mp;
        uPartInfoTab.addPersistenceColumn("p_mp", mp + "");
    }

    public int getUpHp() {
        return upHp;
    }

    private void setUpHp(int upHp) {
        this.upHp = upHp;
        uPartInfoTab.addPersistenceColumn("p_up_hp", upHp + "");
    }

    public int getUpMp() {
        return upMp;
    }

    private void setUpMp(int upMp) {
        this.upMp = upMp;
        uPartInfoTab.addPersistenceColumn("p_up_mp", upMp + "");
    }

    public String getCurExp() {
        return curExp;
    }

    private void setCurExp(String curExp) {
        this.curExp = curExp;
        uPartInfoTab.addPersistenceColumn("p_experience", curExp);
    }

    public String getPreGradeExp() {
        UGrowDao u_grow_dao = new UGrowDao();
        UGrowVO grow_info = u_grow_dao.getByGradeAndRace(this.grade, this.pRace);//得到角色初始化信息
        return grow_info.getGExp();
    }

    public String getNextGradeExp() {
        UGrowDao u_grow_dao = new UGrowDao();
        UGrowVO grow_info = u_grow_dao.getByGradeAndRace(this.grade, this.pRace);//得到角色初始化信息
        return grow_info.getGNextExp();
    }

    public int getEvilValue() {
        consumeEvilValueByTime();
        return evilValue;
    }

    private void setEvilValue(int evilValue) {
        this.evilValue = evilValue;
        uPartInfoTab.addPersistenceColumn("p_pk_value", evilValue + "");
    }

    /**
     * 得到pk开关描述
     *
     * @return
     */
    public String getPkSwitchDes() {
        if (pkSwitch == 2) {
            return "可PK";
        } else {
            return "不可PK";
        }
    }

    public int getPkSwitch() {
        return pkSwitch;
    }

    private void setPkSwitch(int pkSwitch) {
        this.pkSwitch = pkSwitch;
        uPartInfoTab.addPersistenceColumn("p_pks", pkSwitch + "");
    }

    public int getBasicGj() {
        return basicGj;
    }

    private void setBasicGj(int basicGj) {
        this.basicGj = basicGj;
        uPartInfoTab.addPersistenceColumn("p_gj", basicGj + "");
    }

    public int getBasicFy() {
        return basicFy;
    }

    private void setBasicFy(int basicFy) {
        this.basicFy = basicFy;
        uPartInfoTab.addPersistenceColumn("p_fy", basicFy + "");
    }

    public double getMultipleDamage() {
        return multipleDamage;
    }

    private void setMultipleDamage(double multipleDamage) {
        this.multipleDamage = multipleDamage;
        uPartInfoTab.addPersistenceColumn("p_drop_multiple", multipleDamage + "");
    }

    public Date getPkChangeTime() {
        return pkChangeTime;
    }

    private void setPkChangeTime() {
        this.pkChangeTime = new Date();

        String date_str = DateUtil.formatDateToStr(this.pkChangeTime, "yyyy-MM-dd hh:mm:ss");

        uPartInfoTab.addPersistenceColumn("p_pk_changetime", date_str);
    }

    public int getShouldScene() {
        return shouldScene;
    }

    public void setShouldScene(int shouldScene) {
        this.shouldScene = shouldScene;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public SceneVO getSceneInfo() {
        return sceneInfo;
    }

    public void setSceneInfo(SceneVO sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public int getPlayer_state_by_new() {
        return player_state_by_new;
    }

    private void setPlayer_state_by_new(int player_state_by_new) {
        this.player_state_by_new = player_state_by_new;
        uPartInfoTab.addPersistenceColumn("player_state_by_new", player_state_by_new + "");
    }

    // 判断玩家在不在转职的时候
    public boolean isPlayerHaveTransfer() {
        return this.grade == 39 || this.grade == 59 || this.grade == 69 || this.grade == 79;
    }

    // 判断玩家的经验是否满级
    public boolean isPlayerExpFull() {
        return Long.parseLong(this.curExp) >= Long.parseLong(this.getNextGradeExp());
    }

    public int getAttack_npc() {
        return attack_npc;
    }

    public void setAttack_npc(int attack_npc) {
        this.attack_npc = attack_npc;
    }

    public int getScratchticketnum() {
        return scratchticketnum;
    }

    public void setScratchticketnum(int scratchticketnum) {
        this.scratchticketnum = scratchticketnum;
    }

    public int getAddscratchticketnum() {
        return addscratchticketnum;
    }

    public void setAddscratchticketnum(int addscratchticketnum) {
        if (addscratchticketnum < 0) {
            this.addscratchticketnum = 0;
        } else {
            this.addscratchticketnum = addscratchticketnum;
        }
    }

    public int getXuanyunhuihe() {
        return xuanyunhuihe;
    }

    public void setXuanyunhuihe(int xuanyunhuihe) {
        if (xuanyunhuihe < 0) {
            this.xuanyunhuihe = 0;
        } else {
            this.xuanyunhuihe = xuanyunhuihe;
        }
    }

    public int getActhuihe() {
        return acthuihe;
    }

    public void setActhuihe(int acthuihe) {
        if (acthuihe < 0) {
            this.acthuihe = 0;
        } else {
            this.acthuihe = acthuihe;
        }
    }

    public int getActcontent() {
        return actcontent;
    }

    public void setActcontent(int actcontent) {
        if (actcontent < 0) {
            this.actcontent = 0;
        } else {
            this.actcontent = actcontent;
        }
    }

    public int getDefhuihe() {
        return defhuihe;
    }

    public void setDefhuihe(int defhuihe) {
        if (defhuihe < 0) {
            this.defhuihe = 0;
        } else {
            this.defhuihe = defhuihe;
        }
    }

    public int getDefcontent() {
        return defcontent;
    }

    public void setDefcontent(int defcontent) {
        if (defcontent < 0) {
            this.defcontent = 0;
        } else {
            this.defcontent = defcontent;
        }
    }

    public String getMenpaiskilldisplay() {
        return menpaiskilldisplay;
    }

    public void setMenpaiskilldisplay(String menpaiskilldisplay) {
        this.menpaiskilldisplay = menpaiskilldisplay;
    }

    public long getPk_safe_time() {
        return pk_safe_time;
    }

    public void setPk_safe_time(long pk_safe_time) {
        this.pk_safe_time = pk_safe_time;
    }

    public int getPk_safe_state() {
        return pk_safe_state;
    }

    public void setPk_safe_state(int pk_safe_state) {
        this.pk_safe_state = pk_safe_state;
    }

    public long getTianguan_time() {
        return tianguan_time;
    }

    public void setTianguan_time(long tianguan_time) {
        this.tianguan_time = tianguan_time;
    }

    public String getTianguan_npc() {
        return tianguan_npc;
    }

    public void setTianguan_npc(String tianguan_npc) {
        this.tianguan_npc = tianguan_npc;
    }

    public int getTianguan_kill_num() {
        return tianguan_kill_num;
    }

    public void setTianguan_kill_num(int tianguan_kill_num) {
        this.tianguan_kill_num = tianguan_kill_num;
    }

    public int getPoisonCount() {
        return poisonCount;
    }

    public void setPoisonCount(int poisonCount) {
        this.poisonCount = poisonCount;
    }

    public String getRaceName() {
        return ExchangeUtil.getRaceName(this.pRace);
    }

    public int getPRace() {
        return pRace;
    }

    public void setPRace(int race) {
        pRace = race;
    }

    private void setFJoinTime(Date joinTime) {
        fJoinTime = joinTime;
        uPartInfoTab.addPersistenceColumn("f_jointime", DateUtil.formatDateToStr(fJoinTime, "yyyy-MM-dd HH:mm:ss"));
    }

    public boolean isPoisoning() {
        return isPoisoning;
    }

    public void setPoisoning(boolean isPoisoning) {
        this.isPoisoning = isPoisoning;
    }

    public int getAddDamage() {
        return addDamage;
    }

    public void setAddDamage(int addDamage) {
        this.addDamage = addDamage;
    }

    public int getMenpainpcstate() {
        return menpainpcstate;
    }

    public void setMenpainpcstate(int menpainpcstate) {
        this.menpainpcstate = menpainpcstate;
    }

    public int getMenpainpcid() {
        return menpainpcid;
    }

    public void setMenpainpcid(int menpainpcid) {
        this.menpainpcid = menpainpcid;
    }

    public int getZu() {
        return zu;
    }

    public void setZu(int zu) {
        this.zu = zu;
    }

    public String getChuangong() {
        return chuangong;
    }

    public void setChuangong(String chuangong) {
        this.chuangong = chuangong;
        uPartInfoTab.addPersistenceColumn("chuangong", chuangong);
    }

    public Date getLast_shoutu_time() {
        return last_shoutu_time;
    }

    public void setLast_shoutu_time() {
        this.last_shoutu_time = new Date();
    }

    public int getTe_level() {
        return te_level;
    }

    public void setTe_level(int te_level) {
        this.te_level = te_level;
        uPartInfoTab.addPersistenceColumn("te_level", te_level + "");
    }

    public int getFJob() {
        return fJob;
    }

    private void setFJob(int job) {
        fJob = job;
        uPartInfoTab.addPersistenceColumn("f_job", fJob + "");
    }

    private void setFTitle(String title) {
        fTitle = title;
        uPartInfoTab.addPersistenceColumn("f_title", fTitle);
    }

    public int getFContribute() {
        return fContribute;
    }

    private void setFContribute(int contribute) {
        fContribute = contribute;
        uPartInfoTab.addPersistenceColumn("f_contribute", fContribute + "");
    }
}
