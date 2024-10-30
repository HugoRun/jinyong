package com.ls.web.service.npc;

import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.dao.task.UTaskDAO;
import com.ben.vo.petinfo.PetInfoVO;
import com.ben.vo.task.UTaskVO;
import com.ls.ben.cache.dynamic.manual.attack.AttacckCache;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.menu.MenuCache;
import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.ben.dao.info.buff.BuffEffectDao;
import com.ls.ben.dao.info.npc.NpcAttackDao;
import com.ls.ben.dao.info.npc.NpcdropDao;
import com.ls.ben.dao.info.npc.NpcrefurbishDao;
import com.ls.ben.dao.info.npc.NpcskilDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.attack.DropExpMoneyVO;
import com.ls.ben.vo.info.attack.DropGoodsVO;
import com.ls.ben.vo.info.buff.BuffEffectVO;
import com.ls.ben.vo.info.npc.NpcAttackVO;
import com.ls.ben.vo.info.npc.NpcFighter;
import com.ls.ben.vo.info.npc.NpcdropVO;
import com.ls.ben.vo.info.npc.NpcskillVO;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.iface.function.Probability;
import com.ls.model.equip.GameEquip;
import com.ls.model.log.GameLogManager;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.NpcType;
import com.ls.pub.constant.buff.BuffSystem;
import com.ls.pub.constant.buff.BuffType;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MathUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.attack.AttackService;
import com.ls.web.service.buff.BuffEffectService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.goods.prop.GoldBoxService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.player.*;
import com.ls.web.service.rank.RankService;
import com.ls.web.service.room.RoomService;
import com.ls.web.service.task.TaskSubService;
import com.pm.constant.NpcGaiLv;
import com.pm.dao.record.RecordDao;
import com.pm.service.systemInfo.SystemInfoService;
import com.web.jieyi.util.Constant;
import com.web.service.task.TaskPageService;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 功能:处理NPC相关的逻辑
 *
 * @author 刘帅 7:42:28 PM
 */
public class NpcService {

    public static final int PLAYERINJURE = 1;
    public static final int PETINJURE = 2;
    // 掉落率倍数
    public final static int DROP_MULTIPLE = 1;
    Logger logger = Logger.getLogger("log.service");

    /**
     * 所有处于攻击状态的npc攻击角色
     *
     * @param npcs
     * @param player
     */
    public void attackPlayer(List npcs, Fighter player) {
        RoleEntity roleinfo = RoleCache.getByPpk(player.getPPk());
        if (npcs == null || npcs.size() == 0 || player == null) {
            logger.debug("参数错误！");
            return;
        }
        FightService fightService = new FightService();

        NpcFighter npc = (NpcFighter) npcs.get(0);
        NpcskillVO npcSkill = null;
        int injure = 0;

        int injure_temp = 0;
        int skillInjure_temp = 0;
        int wxInjure_temp = 0;

        StringBuffer injureDisplay = new StringBuffer();

        for (int i = 0; i < npcs.size(); i++) {
            npc = (NpcFighter) npcs.get(i);
            if (npc.getNpcType() == NpcAttackVO.NIANSHOU) {
                npcSkill = getNpcSkill(npc);

                npc = Constant.PETNPC.get(npc.getNpcID());
                if (npc.getNpccountnum() < 10) {
                    injure = player.getPUpHp() * npc.getNpccountnum() / 10;
                    injure = injure + MathUtil.getRandomBetweenXY(npcSkill.getNpcskiInjureXiao(), npcSkill.getNpcskiInjureDa());
                    injureDisplay.append("-" + injure);
                } else {
                    injure = npc.getNpccountnum() * npcSkill.getNpcskiWxInjure() / 100;
                    injure = injure + MathUtil.getRandomBetweenXY(npcSkill.getNpcskiInjureXiao(), npcSkill.getNpcskiInjureDa());
                    injureDisplay.append("-" + injure);

                }
                player.setPlayerInjure(injure);
                if (npcSkill != null) {
                    npc.setSkillName(npcSkill.getNpcskiName());
                }
                if (player.isDead()) // 如果死了伤害为
                {

                    player.appendKillDisplay("您被" + StringUtil.isoToGBK(npc.getNpcName()) + "杀死了！");
                    if (i != 0) {
                        injureDisplay.append("/");
                    }
                    injureDisplay.append("-" + injure);
                    break;
                }
            } else {
                logger.debug("npcs.size=" + npcs.size() + " ,npc.getNpcType()=" + npc.getNpcType());
                // 如果npc类型为旗杆,就会不反击玩家
                if (npc.getNpcType() == NpcAttackVO.MAST || npc.getNpcType() == NpcAttackVO.ZHAOHUN || npc.getNpcType() == NpcAttackVO.CITYDOOR || npc.getNpcType() == NpcAttackVO.DIAOXIANG) {
                    npc.setSkillName("不攻击");
                    logger.debug("因为是旗杆,所有不反击玩家");
                    break;
                }
                logger.debug("npcs.size=" + npcs.size());

                if (npc.getDizzyBoutNum() > 0) // npc被击晕
                {
                    injure_temp = 0;
                    npc.setSkillName("被击晕");
                    // 减少击晕状态的回合数
                    updateDizzyBoutNumOfNPC(npc.getID(), -1);
                } else if (npc.getPoisonBoutNum() > 0) {
                    injure_temp = 0;
                    npc.setSkillName("中毒");
                    //减少中毒状态的回合数
                    updatePoisonBoutNumOfNPC(npc.getID(), -1);
                } else {
                    npcSkill = getNpcSkill(npc);
                    int npc_gj = 0;
                    if (npcSkill == null) {
                        npc_gj = npc.getAppendGj();
                    } else {
                        npc_gj = npcSkill.getNpcskiInjure() + npc.getAppendGj();
                    }
                    int npc_level = npc.getLevel();
                    int p_fy = player.getBasicFy();//基础防御
                    int p_zbfy = player.getZbFy();//装备防御
                    int p_level = player.getPGrade();
                    //五行防御的附加值 玩家的防御/100得到的是最后的附加值
                    int wx_fy_join = (p_fy + p_zbfy) / 100;

                    skillInjure_temp = AttackService.npcSkillInjureExpressions(npc_gj, p_fy, p_zbfy, p_level, npc_level);
                    wxInjure_temp = AttackService.getWxInjureValue(npcSkill, player.getWx(), npc.getLevel(), player.getPGrade(), wx_fy_join);
                    injure_temp = skillInjure_temp + wxInjure_temp;
                    if (injure_temp <= 0) {
                        injure_temp = 1;
                    }
                    if (injure_temp > player.getPHp()) {
                        injure_temp = player.getPHp();
                    }

                    // 总伤害的累加
                    injure += injure_temp;

                    if (npcSkill != null) {
                        npc.setSkillName(npcSkill.getNpcskiName());
                    }
                    player.setPlayerInjure(injure_temp);
                    if (player.isDead()) // 如果死了伤害为
                    {

                        player.appendKillDisplay("您被" + StringUtil.isoToGBK(npc.getNpcName()) + "杀死了！");
                        if (i != 0) {
                            injureDisplay.append("/");
                        }
                        injureDisplay.append("-" + injure_temp);
                        // player.setKillDisplay("您被"+StringUtil.isoToGB(npc.getNpcName())+"打败了！");
                        if (npc.getNpcType() == NpcType.MENPAINPC) {
                            roleinfo.getBasicInfo().updateSceneId(210 + "");
                            roleinfo.getBasicInfo().setShouldScene(210);
                            Constant.MENPAINPC.put(1, 0);
                        }
                        break;
                    }
                }
                // 玩家伤害描述
                if (i != 0) {
                    injureDisplay.append("/");
                }
                injureDisplay.append("-" + injure_temp);
            }
        }

        EquipService equipService = new EquipService();
        // 挨打PK减去装备持久
        equipService.useEquip(player.getPPk());

        // 更新角色状态
        player.setInjureDisplay(injureDisplay.toString());
        fightService.statUpdateByPlayerInjure(player, player, npc.getNpcType());
    }

    /**
     * npc随机得到攻击角色的一招
     *
     * @param npc
     * @return
     */
    public NpcskillVO getNpcSkill(NpcAttackVO npc) {
        NpcskillVO npcSkill = null;

        NpcskilDao npcSkillDao = new NpcskilDao();

        // 得到npc所拥有的所有技能
        List npcSkills = npcSkillDao.getSkillByNpcID(npc.getNpcID());

        // 根据概率得到一个使用技能, 概率以100为基数.
        npcSkill = (NpcskillVO) MathUtil.getRandomEntityFromList(npcSkills, 100);

        if (npcSkill != null) {
            int skiInjure = MathUtil.getRandomBetweenXY(npcSkill.getNpcskiInjureXiao(), npcSkill.getNpcskiInjureDa());
            double supperInjureMultiple = 1; // 暴击倍数
            if (MathUtil.isAppearByPercentage(npc.getDrop())) {
                logger.info("npc出现暴击");
                // 4月7日 暴击倍数由2调整为1.2
                supperInjureMultiple = 1.2;
            }
            //4月7日 伤害由INT 类型 调整为DOUBLE类型
            npcSkill.setNpcskiInjure(skiInjure, supperInjureMultiple);
        }

        return npcSkill;
    }


    /**
     * 根据npd_id和掉落表得到所有掉落物品
     *
     * @param npc_id
     */
    private List<DropGoodsVO> dropGoods(int npc_id, PartInfoVO player) {
        RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk() + "");
        List<DropGoodsVO> drop_goods_list = new ArrayList<DropGoodsVO>();
        if (player == null) {
            logger.debug("参数为空！");
            return drop_goods_list;
        }
        TaskSubService taskService = new TaskSubService();

        NpcdropDao npcDropDao = new NpcdropDao();

        //掉落前先清除掉落临时表里的数据，防止有遗留物品
        role_info.getDropSet().clearDropItem();

        String drop_task_condition = taskService.getDropTaskConditions(player.getPPk());

        List<NpcdropVO> npcdrops = npcDropDao.getNpcdropsByNpcID(npc_id, drop_task_condition); // npc所有要掉的物品

        checkPropIsDrop(npcdrops, player, drop_goods_list);

        return drop_goods_list;
    }

    /**
     * 根据npd_id和掉落表得到所有掉落物品
     * 黄金宝箱使用
     *
     * @param npc_id
     */
    private List<DropGoodsVO> dropGoodsByGoldBox(int npc_id, PartInfoVO player) {
        List<DropGoodsVO> drop_goods_list = new ArrayList<DropGoodsVO>();
        if (player == null) {
            logger.debug("参数为空！");
            return drop_goods_list;
        }
        TaskSubService taskService = new TaskSubService();

        NpcdropDao npcDropDao = new NpcdropDao();

        String drop_task_condition = taskService.getDropTaskConditions(player.getPPk());
        List<NpcdropVO> npcdrops = npcDropDao.getNpcdropsByNpcID(npc_id, drop_task_condition); // npc所有要掉的物品

        // 确定是哪个物品
        List<Probability> list = new ArrayList<Probability>();
        NpcGaiLv npcGaiLv = null;
        NpcdropVO npcdropVO = null;
        for (int i = 0; i < npcdrops.size(); i++) {
            npcGaiLv = new NpcGaiLv();
            npcdropVO = npcdrops.get(i);
            //npcGaiLv.setId(npcdropVO.getNpcdropID());
            npcGaiLv.setId(i);
            npcGaiLv.setProbability(npcdropVO.getNpcdropProbability());
            list.add(npcGaiLv);
        }

        Probability probability = MathUtil.getRandomEntityFromList(list, MathUtil.DROPDENOMINATOR);
        int npcID = probability.getId();
        npcdropVO = npcdrops.get(npcID);

        // 将物品加到掉落表里
        addGoodsToDropTable(npcdropVO, player, drop_goods_list);

        return drop_goods_list;
    }

    /**
     * 根据npd_id和掉落表得到所有掉落物品 拉霸宝箱使用
     *
     * @param npc_id
     */
    public NpcdropVO dropGoodsByLabaBox(int npc_id, PartInfoVO player, int p_pk) {
        if (player == null) {
            logger.debug("参数player为空！");
            return null;
        }
        TaskSubService taskService = new TaskSubService();

        NpcdropDao npcDropDao = new NpcdropDao();

        String drop_task_condition = taskService.getDropTaskConditions(player.getPPk());
        List<NpcdropVO> npcdrops = npcDropDao.getNpcdropsByNpcID(npc_id, drop_task_condition); // npc所有要掉的物品

        // 确定是哪个物品
        NpcdropVO npcdropVO = npcdrops.get(0);

        // 将物品加放到玩家背包里
        GoodsService goodsService = new GoodsService();
        goodsService.putPropToWrap(p_pk, npcdropVO.getGoodsId(), 1, GameLogManager.G_BOX_DROP);
        return npcdropVO;// 返回物品的名字
    }

    /**
     * 根据npd_id和掉落表得到所有掉落物品 拉霸宝箱使用
     *
     * @param npc_id
     */
    public NpcdropVO dropGoodsByLabaBoxTwo(int npc_id, PartInfoVO player, int p_pk) {
        if (player == null) {
            logger.debug("参数player为空！");
            return null;
        }
        NpcdropDao npcDropDao = new NpcdropDao();
        List<NpcdropVO> npcdrops = npcDropDao.getNpcdropsForLaBa(String.valueOf(npc_id)); // npc所有要掉的物品
        // 确定是哪个物品
        NpcdropVO npcdropVO = null;
        npcdropVO = npcdrops.get(0);
        return npcdropVO;// 返回物品的名字
    }

    /**
     * 将物品加到掉落表里
     *
     * @param npcdrops
     * @param player
     * @param drop_goods_list
     * @param maxSize         drop_goods_list的最大size
     */
    private void addGoodsToDropTable(NpcdropVO npcdropVO, PartInfoVO player, List<DropGoodsVO> drop_goods_list) {
        DropGoodsVO dropGoodsVO = new DropGoodsVO();
        if (npcdropVO != null) {
            dropGoodsVO.setPPk(player.getPPk());
            dropGoodsVO.setDropNum(npcdropVO.getNpcDropNum());
            dropGoodsVO.setGoodsId(npcdropVO.getGoodsId());
            dropGoodsVO.setGoodsType(npcdropVO.getGoodsType());
            dropGoodsVO.setGoodsName(npcdropVO.getGoodsName());
            dropGoodsVO.setGoodsImportance(npcdropVO.getNpcDropImprot());

            if (dropGoodsVO.getGoodsType() == GoodsType.EQUIP) {
                this.loadDropEquipQuality(dropGoodsVO, npcdropVO);//加载掉落装备的品质
            }

            if (dropGoodsVO.getGoodsImportance() == 2) {
                GoodsService goodsService = new GoodsService();
                String dropInfo = "恭喜" + player.getPName() + "打死" + NpcCache.getNpcNameById(npcdropVO.getNpcID()) + "获得" + goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
                dropGoodsVO.setDropGoodsInfo(dropInfo);
            } else if (dropGoodsVO.getGoodsImportance() == 3) {
                GoodsService goodsService = new GoodsService();
                String dropInfo = "恭喜" + player.getPName() + "打开" + NpcCache.getNpcNameById(npcdropVO.getNpcID()) + "获得" + goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
                dropGoodsVO.setDropGoodsInfo(dropInfo);
            } else if (dropGoodsVO.getGoodsImportance() == 1) {
                GoodsService goodsService = new GoodsService();
                String dropInfo = "恭喜" + player.getPName() + "获得" + goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
                dropGoodsVO.setDropGoodsInfo(dropInfo);
            }

            drop_goods_list.add(dropGoodsVO);
        } else {
            logger.info("无掉落物");
        }
    }

    /**
     * 确定 npc掉落物 是否掉落
     *
     * @param npcdrops
     * @param player
     * @param drop_goods_list
     * @param maxSize         drop_goods_list的最大size
     */
    private void checkPropIsDrop(List<NpcdropVO> npcdrops, PartInfoVO player, List<DropGoodsVO> drop_goods_list) {
        // 普通怪掉落数最大为3个
        int maxSize = 3;

        DropGoodsVO dropGoodsVO = new DropGoodsVO();
        if (npcdrops != null && npcdrops.size() > 0) {
            RefurbishService refurbishService = new RefurbishService();
            if (refurbishService.isboss(Integer.parseInt(player.getPMap()), npcdrops.get(0).getNpcID())) {
                //如果是boss掉落数最大为7个
                maxSize = 7;
            }

            for (NpcdropVO npcdropVO : npcdrops) {
                //判断是否掉落
                boolean isDrop = npcdropVO.isDrop(player.getAppendDropProbability());
                if ((isDrop && drop_goods_list.size() < maxSize) || (npcdropVO.getNpcdropProbability() >= MathUtil.DROPDENOMINATOR)) {
                    dropGoodsVO = new DropGoodsVO();
                    dropGoodsVO.setPPk(player.getPPk());
                    dropGoodsVO.setDropNum(npcdropVO.getNpcDropNum());
                    dropGoodsVO.setGoodsId(npcdropVO.getGoodsId());
                    dropGoodsVO.setGoodsType(npcdropVO.getGoodsType());
                    dropGoodsVO.setGoodsName(npcdropVO.getGoodsName());
                    dropGoodsVO.setGoodsImportance(npcdropVO.getNpcDropImprot());

                    if (dropGoodsVO.getGoodsType() == GoodsType.EQUIP) {
                        this.loadDropEquipQuality(dropGoodsVO, npcdropVO);//加载掉落装备的品质
                    }
                    //打怪得到的物品
                    if (dropGoodsVO.getGoodsImportance() == 2) {
                        GoodsService goodsService = new GoodsService();
                        String dropInfo = "恭喜" + player.getPName() + "打死" + NpcCache.getNpcNameById(npcdropVO.getNpcID()) + "获得" + goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
                        dropGoodsVO.setDropGoodsInfo(dropInfo);
                    }
                    //宝箱得到的物品
                    else if (dropGoodsVO.getGoodsImportance() == 3) {
                        GoodsService goodsService = new GoodsService();
                        String dropInfo = "恭喜" + player.getPName() + "打开" + NpcCache.getNpcNameById(npcdropVO.getNpcID()) + "获得" + goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
                        dropGoodsVO.setDropGoodsInfo(dropInfo);
                    }
                    //暂无用
                    else if (dropGoodsVO.getGoodsImportance() == 1) {
                        GoodsService goodsService = new GoodsService();
                        String dropInfo = "恭喜" + player.getPName() + "获得" + goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
                        dropGoodsVO.setDropGoodsInfo(dropInfo);
                    }
                    //打怪得到的物品(特殊)
                    else if (dropGoodsVO.getGoodsImportance() == 4) {
                        GoodsService goodsService = new GoodsService();
                        String dropInfo = "恭喜" + player.getPName() + "奴役" + NpcCache.getNpcNameById(npcdropVO.getNpcID()) + "获得" + goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
                        dropGoodsVO.setDropGoodsInfo(dropInfo);
                    }


                    drop_goods_list.add(dropGoodsVO);
                }
                if (npcdropVO.getNpcdropProbability() >= MathUtil.DROPDENOMINATOR) {
                    // 如果要物品的 掉落概率为一百万,即必掉物品, 就给物品最大允许掉落值加一
                    maxSize++;
                }
            }
        } else {
            logger.info("无掉落物");
        }
    }

    /**
     * 杀死npc掉落物品
     *
     * @param npc_id
     * @param player
     */
    public void dropGoodsByNpc(int npc_id, PartInfoVO player) {
        RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk() + "");
        List<DropGoodsVO> drop_goods_list = dropGoods(npc_id, player);
        role_info.getDropSet().addDropItem(drop_goods_list);
    }

    /**
     * 宝箱掉落物品
     *
     * @param npc_id
     * @param player
     */
    public void dropGoodsByRareBox(int npc_id, PartInfoVO player, String dropNum) {
        if (dropNum == null || Integer.parseInt(dropNum) <= 0) {
            return;
        }
        RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk() + "");

        // TODO 如果宝箱中的npc_id 不掉落物品呢,那么这儿会造成
        List<DropGoodsVO> drop_goods_list = null;
        // 最多让其循环500次后不再循环,以免造成死循环
        int num = 0;
        do {
            drop_goods_list = dropGoods(npc_id, player);
            num++;
            if (num > 400) {
                logger.error("宝箱数据有问题 ");
            }
        } while ((drop_goods_list == null && num < 500) || (drop_goods_list.size() == 0 && num < 500));

        role_info.getDropSet().addDroItem(drop_goods_list, Integer.valueOf(dropNum));
    }

    /**
     * 宝箱掉落物品
     *
     * @param npc_id
     * @param player
     */
    public void dropGoodsByRareBoxByGOLD(RoleEntity roleInfo, PartInfoVO player, PropVO propVO) {
        RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk() + "");
        String[] dropNum = propVO.getPropOperate2().split(",");
        GoldBoxService goldBoxService = new GoldBoxService();
        for (int i = 0; i < Integer.parseInt(dropNum[0]); i++) {
            int npcId = goldBoxService.getDuiYingGrade(roleInfo, propVO);

            List<DropGoodsVO> drop_goods_list = dropGoodsByGoldBox(npcId, player);

            role_info.getDropSet().addDropItem(drop_goods_list);
        }
    }

    /**
     * 宝箱掉落物品
     *
     * @param npc_id
     * @param player
     *
    public void dropGoodsByRareBoxByGOLD(int npc_id, PartInfoVO player, String dropNum)
    {
    // TODO 如果宝箱中的npc_id 不掉落物品呢,那么这儿会造成
    List<DropGoodsVO> drop_goods_list = null;
    // 最多让其循环500次后不再循环,以免造成死循环
    int num = 0;
    do
    {
    drop_goods_list = dropGoods(npc_id, player);
    num++;
    if (num > 400)
    {
    logger.error("宝箱数据有问题 ");
    }
    } while ((drop_goods_list == null && num < 500  )
    || (drop_goods_list != null && num < 500 && drop_goods_list.size() < Integer.parseInt(dropNum)));



    for (int i = 0; i < Integer.valueOf(dropNum); i++)
    {
    if (drop_goods_list != null && drop_goods_list.size() > 0)
    {
    //int random_index = (int) Math.random() * drop_goods_list.size();
    //Random random = new Random();
    //int random_index =  random.nextInt(drop_goods_list.size());
    DropGoodsDao dropGoodsDao = new DropGoodsDao();
    DropGoodsVO drop_goods = drop_goods_list.get(i);
    dropGoodsDao.add(drop_goods);
    }
    }
    } */

    /**
     * 发奖宝箱掉落物品
     *
     * @param npc_id
     * @param player
     */
    public void dropGoodsByJiangRareBox(int npc_id, PartInfoVO player) {
        RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk() + "");
        List<DropGoodsVO> drop_goods_list = dropGoods(npc_id, player);
        role_info.getDropSet().addDropItem(drop_goods_list);
    }

    /**
     * 加载掉落装备的品质
     */
    private void loadDropEquipQuality(DropGoodsVO dropGoodsVO, NpcdropVO npcdropVO) {
        int quality = 0;
        if (npcdropVO.getQuality() == -1)//按规律生成品质
        {
            GameEquip gameEquip = EquipCache.getById(dropGoodsVO.getGoodsId());
            quality = gameEquip.getDropQuality();
        } else//指定生成的品质
        {
            quality = npcdropVO.getQuality();
        }
        dropGoodsVO.setGoodsQuality(quality);

        dropGoodsVO.setGoodsName(dropGoodsVO.getGoodsName() + ExchangeUtil.getQualityName(quality));
    }


    /**
     * 清除当前角色刷新出一只npc，既杀死一只npc
     *
     * @param p_pk
     * @return
     *//*
	public int deleteByNpk1(int n_pk)
	{
		int result = -1;
		NpcAttackDao npcAttackDao = new NpcAttackDao();
		result = npcAttackDao.deleteByNpk(n_pk);

		// 清除npc的buff效果
		BuffEffectService buffService = new BuffEffectService();
		buffService.clearBuffEffect(n_pk, BuffSystem.NPC);
		return result;
	}*/

    /**
     * 捕获npc,同时将怪物的捕获时间记录,
     *
     * @param p_pk
     * @return
     */
    public int capturePet(NpcAttackVO npc, String mapid) {
        int result = -1;
        int p_pk = npc.getPPk();

        RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");

//		NpcAttackDao npcAttackDao = new NpcAttackDao();
//		logger.info("主动开关=" + npc.getNAttackswitch());
//		if (npc.getNpcIsAttack() == 1)
//		{
//			result = npcAttackDao.deleteNpcByMapNpcSwitch(p_pk, npc_id, mapid);
//		}
//		else
//		{
//			result = npcAttackDao.deleteNpcByMapNpcPPk(p_pk, npc_id, mapid);
//		}

        AttacckCache attacckCache = new AttacckCache();
        List<NpcFighter> list = null;

        list = attacckCache.getZDAttackNpc(npc.getPPk(), AttacckCache.ZDATTACKNPC);
        if (list != null && list.size() != 0) {
            list.remove(0);
        }


        // 清除npc的buff效果
        BuffEffectService buffService = new BuffEffectService();
        buffService.clearBuffEffect(npc.getID(), BuffSystem.NPC);

        if (npc.getNpcRefurbishTime() != 0)// 如果npc的刷新有冷却时间控制
        {
            PartInfoVO infovo = new PartInfoVO();
            RefurbishService refurbishService = new RefurbishService();
            infovo.setPName(roleInfo.getBasicInfo().getName());
            infovo.setPPk(p_pk);
            refurbishService.updateTimeOfNPCDead(infovo, npc, npc.getNpcID(), Integer.valueOf(mapid), 2);
        }

        return result;
    }

    /**
     * 得到角色对应的,处于攻击状态的所有npc
     *
     * @param p_pk
     * @return
     */
    public List<NpcFighter> getAttackNPCs(int p_pk, String scene_id) {
        List<NpcFighter> list = new ArrayList<NpcFighter>();
//		List<NpcAttackVO> attackvolist = null;
//		NpcAttackDao npcAttackDao = new NpcAttackDao();
//		list = npcAttackDao.getAttackNPCs(p_pk,scene_id);

        AttacckCache attacckCache = new AttacckCache();

        list = attacckCache.getZDAttackNpc(p_pk, AttacckCache.ZDATTACKNPC);
        logger.info("有" + list.size() + "个主动攻击怪");

        if (list == null || list.size() == 0) {
            NpcFighter npcFighter = (NpcFighter) attacckCache.getNpcWithSceneByPPkd(scene_id);
            if (npcFighter != null) {
                list.add(npcFighter);
            }
        }

        BuffEffectService buffEffectService = new BuffEffectService();

        //加载npc的buff效果
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                NpcFighter npc = list.get(i);
                if (npc != null) {
                    npc.setPlayerInjure(0);
                    npc.setPetInjure(0);
                    buffEffectService.loadBuffEffectOfNPC(npc);
                } else {
                    list.remove(npc);
                }
            }
        }
        return list;
    }

    /**
     * 在临时表里判断是否有处于攻击状态的npc
     *
     * @param p_pk
     * @return
     */
    public boolean isHaveAttackNPC(RoleEntity roleInfo) {
        if (roleInfo == null) {
            return false;
        }

        AttacckCache attacckCache = new AttacckCache();
        Object[] attackNpc = attacckCache.getByPPkd(roleInfo.getBasicInfo().getPPk());


        if (attackNpc == null || ((List) attackNpc[0]).size() == 0) {
            return false;
        } else {
            if (shiFouYouBiGuaiBUFF(roleInfo, attackNpc)) {
                return false;
            }
            logger.debug("有几个攻击性npc=" + ((List) attackNpc[0]).size());
            return true;
        }

    }

    /**
     * 得到一个当前战斗的npc
     *
     * @param p_pk
     * @return
     */
    public NpcAttackVO getOneAttackNPCByPpk(int p_pk, int scene_id) {
//		NpcAttackDao npcAttackDao = new NpcAttackDao();
//		return npcAttackDao.getOneAttackNPCByPpk(p_pk, scene_id);

        List<NpcFighter> npcs = null;
        AttacckCache attacckCache = new AttacckCache();
        npcs = attacckCache.getZDAttackNpc(p_pk, AttacckCache.ZDATTACKNPC);
        if (npcs == null && npcs.size() == 0) {
            return null;
        }
        return npcs.get(0);

    }

    /**
     * 得到角色对应的刷出来的npcs
     *
     * @param p_pk
     * @return
     */
    public List<NpcFighter> getCurrentNPCs(int p_pk, int scene_id) {
        List<NpcFighter> npcs = null;

        AttacckCache attacckCache = new AttacckCache();
        npcs = attacckCache.getZDAttackNpc(p_pk, AttacckCache.BDATTACKNPC);

        return npcs;

    }

    /**
     * 判断是否有主动攻击的npc
     *
     * @param p_pk
     * @return
     *//*
	public NpcAttackVO isInitiativeAttackNPC(int p_pk)
	{
		NpcAttackDao npcAttackDao = new NpcAttackDao();
		return npcAttackDao.isInitiativeAttackNPC(p_pk);
	}*/

    /**
     * npc掉落经验和钱 实际掉落经验 = npc掉落经验 * (1 – (玩家等级-npc等级)*0.1)；
     *
     * @param npc
     */
    public void dropExpMoney(NpcAttackVO npc, PartInfoVO player) {
        if (npc == null) {
            logger.debug("npc为空");
            return;
        }

        RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk() + "");

        DropExpMoneyVO dropExpMoney = new DropExpMoneyVO();

        int npc_exp = npc.getExp();
        int player_grade = player.getPGrade();
        int npc_level = npc.getLevel();
        double drop_exp = npc_exp;
        if (npc_level < player_grade)//npc的等级小于玩家的等级时，有经验衰减，否则就是npc的原始经验
        {
            drop_exp = npc_exp * (1 - (player_grade - npc_level) * 0.1);
            if (drop_exp < 0) {
                drop_exp = 0;
            }
        }

        PetInfoDAO dao = new PetInfoDAO();
        PetInfoVO petvo = dao.getPetInfo(player.getPPk() + "");
        double petdropExp = 0;
        if (petvo != null && petvo.getPPk() != 0) {
            int petlevel = petvo.getPetGrade();
            petdropExp = npc_exp * (1 - (petlevel - npc_level) * 0.1) * 0.2;

        }
        if (petdropExp < 0) {
            petdropExp = 0;
        }

        dropExpMoney.setDropExp((int) drop_exp);
        dropExpMoney.setDropMoney(npc.getDropMoney());
        dropExpMoney.setDropPetExp((int) petdropExp);

        role_info.getDropSet().addExpAndMoney(dropExpMoney);

    }


    /**
     * npc得到自己的五行属性
     */
    public void loadNPCWx(NpcAttackVO npc) {
        if (npc == null) {
            logger.debug("无效npc");
            return;
        }

        if (npc.getNpcType() == NpcAttackVO.CITYDOOR) {
            return;
        }
        NpcskilDao npcSkillDao = new NpcskilDao();
        npcSkillDao.loadNPCWx(npc);
    }

    /**
     * 处理一个npc的死亡
     *
     * @param player
     * @param npc
     */
    public void processDeadOfOneNpc(PartInfoVO player, NpcAttackVO npc) {
        RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk());

        RefurbishService refurbishService = new RefurbishService();

        //该npc是否是boss
        boolean is_boss = refurbishService.isboss(npc.getSceneId(), npc.getNpcID());


        // 如果npc的刷新有冷却时间控制
        if (npc.getNpcRefurbishTime() != 0) {
            refurbishService.updateTimeOfNPCDead(player, npc, npc.getNpcID(), Integer.parseInt(player.getPMap()), 1);
        }
        // 掉落经验和钱
        dropExpMoney(npc, player);
        // 是否掉落物品
        if (getNpcIsDropGoods(player, npc)) {
            //只有杀死最后一个怪时才掉落
            dropGoodsByNpc(npc.getNpcID(), player);
        }

        //消除罪恶值控制
        if (npc.getLevel() >= role_info.getGrade()) {
            if (is_boss) {
                role_info.getBasicInfo().addEvilValue(-5);
            } else {
                role_info.getCounter().addKillNpcNum();
                if (role_info.getCounter().getKillNpcNum() % 20 == 0) {
                    role_info.getBasicInfo().addEvilValue(-1);
                }
            }
        }

        //************************************排行统计用
        RoomService roomService = new RoomService();
        //判断NPC是否在副本里
        boolean is_in_instance = roomService.getMapType(npc.getSceneId()) == MapType.INSTANCE;
        if (is_in_instance && is_boss) {//杀的BOSS
            //统计需要
            new RankService().updateAdd(player.getPPk(), "killboss", (npc.getLevel() * 2));
        } else if (is_in_instance && !is_boss) {//杀的副本小怪
            //统计需要
            new RankService().updateAdd(player.getPPk(), "killnpc", (npc.getLevel() * 2));
        } else if (is_in_instance && is_boss) {//不再副本切杀的BOSS
            //统计需要
            new RankService().updateAdd(player.getPPk(), "killboss", npc.getLevel());
        } else {
            //统计需要
            new RankService().updateAdd(player.getPPk(), "killnpc", npc.getLevel());
        }
        // 判断是否是任务npc,是则处理
        processTaskNPC(npc, player);
    }


	/*// 如果此是npc是城门类, 就处理城门类npc死亡后,攻城战场的相关情况
	private void processCITYDOOR(NpcAttackVO npc, PartInfoVO player)
	{
		int sceneId = npc.getSceneId();
		RoomService roomService = new RoomService();
		MapVO mapVO = roomService.getMapInfoBySceneId(sceneId+"");
		
		SystemInfoService systemInfoService = new SystemInfoService();
		OperateMenuDao menuDao = new OperateMenuDao();
		OperateMenuVO menuVO = menuDao.getOperateMenuVOBySceneAndType(sceneId, MenuType.CITYDOOR);
		TongSiegeBattleService tongSiegeBattleService = new TongSiegeBattleService();
		TongSiegeBattleVO tongSiegeBattleVO = tongSiegeBattleService.getSiegeByMapId(mapVO.getMapID());
		TongSiegeControlVO tongSiegeControlVO = tongSiegeBattleService.getSiegeTongInfo(""+tongSiegeBattleVO.getSiegeId());
		
		// 类型为1是城门,2是英雄雕像
		// 城门被打破后,战场的状态变为2
		if (tongSiegeControlVO.getNowPhase() < FinalNumber.THIRD) {
			TongSiegeBattleDao tongSiegeBattleDao = new TongSiegeBattleDao();
			tongSiegeBattleDao.updateTongSiegeNowPhase(Integer.parseInt(menuVO.getMenuOperate1()), FinalNumber.THIRD);
			String infoString = tongSiegeBattleVO.getSiegeName()+"的城门被打破了!";
			systemInfoService.insertSystemInfoByBarea(infoString, mapVO.getMapFrom());
		}
			

	}*/
	
	
/*	// 如果此是npc是雕像类, 就处理雕像类npc死亡后,攻城战场的相关情况
	private void processDIAOXIANG(NpcAttackVO npc, PartInfoVO player)
	{
		int sceneId = npc.getSceneId();
		RoomService roomService = new RoomService();
		MapVO mapVO = roomService.getMapInfoBySceneId(sceneId+"");
		
		SystemInfoService systemInfoService = new SystemInfoService();
		OperateMenuDao menuDao = new OperateMenuDao();
		OperateMenuVO menuVO = menuDao.getOperateMenuVOBySceneAndType(sceneId, MenuType.DIAOXIANG);
		TongSiegeBattleService tongSiegeBattleService = new TongSiegeBattleService();
		TongSiegeBattleVO tongSiegeBattleVO = tongSiegeBattleService.getSiegeByMapId(mapVO.getMapID());
		
		// 英雄雕像被打破后, 攻城战第一阶段结束
		tongSiegeBattleService.firstGradeEnd(player.getPPk(),npc.getSceneId(),Integer.parseInt(menuVO.getMenuOperate1()),menuVO.getMenuOperate3());
		 
		String infoString = tongSiegeBattleVO.getSiegeName()+"的英雄雕像被打破了!攻城进入第二阶段";
		systemInfoService.insertSystemInfoByBarea(infoString, mapVO.getMapFrom());
		
	}*/

	/*// 如果此是npc是旗杆类, 就处理旗杆npc死亡后旗杆菜单的阵营归属。
	private void processMast(NpcAttackVO npc, PartInfoVO player)
	{
		FieldService fieldService = new FieldService();
		fieldService.dealMast(npc, player.getPCamp());

		SystemInfoService systemInfoService = new SystemInfoService();

		StringBuffer systemInfo = new StringBuffer();

		RoomService roomService = new RoomService();
		String sceneName = roomService.getName(npc.getSceneId());
		
		systemInfo.append("位于").append(sceneName).append("的旗杆被");

		if (player.getPCamp() == 1)
		{
			systemInfo.append("正派").append("攻击成功,转变成为正派!");
		}
		else
		{
			systemInfo.append("邪派").append("攻击成功了,转变成为邪派!");
		}

		systemInfoService.insertSystemInfoByBarea(systemInfo.toString(),
				FinalNumber.FIELDBAREA);
		
	}
	*/


    /**
     * 判断此npc是否需要掉落物品,判断此地是否还有其他的同类型主动攻击型怪, 如果有就不掉落物品.
     *
     * @param player
     * @param npc
     * @return
     */
    private boolean getNpcIsDropGoods(PartInfoVO player, NpcAttackVO npc) {
        boolean flag = true;

        AttacckCache attacckCache = new AttacckCache();
        List<NpcFighter> list = attacckCache.getZDAttackNpc(player.getPPk(), AttacckCache.ZDATTACKNPC);


        if (list != null && list.size() > 1) {
            flag = false;
        }

        if (flag) {
            logger.info("该npc掉落物品");
        } else {
            logger.info("该npc不掉落物品");
        }
        return flag;
    }

    /**
     * 更新npc血量
     *
     * @param player
     * @param npc
     * @param injure
     * @param murderer_type 凶手类型(玩家，宠物)
     */
    public void updateNPCCurrentHP(Fighter player, NpcFighter npc, int injure, int murderer_type) {
        RoleEntity roleinfo = RoleService.getRoleInfoById(player.getPPk() + "");
        if (player == null || npc == null) {
            logger.debug("参数错误！");
            return;
        }

        // 统计玩家对此npc的伤害值
        if (npc.getNpcType() == NpcAttackVO.NIANSHOU) {

        } else {
            recordPlayerInjure(player, npc, injure);
            if (murderer_type == PETINJURE) {
                // 去除了这个方法的人物伤害
                npc.setPetInjure(injure);
            } else if (murderer_type == PLAYERINJURE) {
                // 去除了这个方法的人物伤害
                npc.setPlayerInjure(injure);
            }
        }

        if (npc.isDead()) {

            // npc死亡描述
            if (npc.getNpcType() == NpcAttackVO.DEADNPC) {
                TaskPageService taskPageService = new TaskPageService();
                player.appendKillDisplay(StringUtil.isoToGBK(npc.getNpcName()) + ":" + npc.getCurrentHP() + "/" + npc.getNpcHP() + "<br/>");
                player.setTask_display(taskPageService.getGeiDJService(npc.getNpcID(), StringUtil.isoToGBK(npc.getNpcName()), player.getPPk()));
            } else if (npc.getNpcType() == NpcAttackVO.LOSENPC) {
                player.appendKillDisplay(player.getKillDisplay() + "您打败了" + StringUtil.isoToGBK(npc.getNpcName()) + "<br/>");
            } else if (npc.getNpcType() == NpcAttackVO.MAST) {
                player.appendKillDisplay("您攻击旗杆成功,现在此旗杆顺利成为您阵营的了!<br/>");

            } else if (npc.getNpcType() == NpcAttackVO.CITYDOOR) {
                player.appendKillDisplay("您攻击大门成功,大门现在已经攻破!<br/>");
            } else if (npc.getNpcType() == NpcAttackVO.ZHAOHUN) {
                player.appendKillDisplay("您攻击招魂幡成功,现在此招魂幡已经成为贵帮派的了!<br/>");
            } else if (npc.getNpcType() == NpcAttackVO.NIANSHOU) {
                getNpcByNianshouDead(npc, "被" + player.getPName() + "杀死了!");
            } else if (npc.getNpcType() == NpcType.MENPAINPC) {
                //更新玩家NPC表中的内容
                int menuid = roleinfo.getBasicInfo().getMenpainpcstate();
                if (menuid != 0) {
                    MenuService ms = new MenuService();
                    OperateMenuVO vo = new OperateMenuVO();
                    vo = ms.getMenuById(menuid);
                    vo.setMenuOperate4(roleinfo.getBasicInfo().getMenpainpcid());
                    MenuCache menuCache = new MenuCache();
                    menuCache.reloadOneMenu(vo);
                    vo = ms.getMenuById(menuid);
                }
                Constant.MENPAINPC.put(1, 0);
                roleinfo.getBasicInfo().updateSceneId(210 + "");
                roleinfo.getBasicInfo().setShouldScene(210);
            }
            // 如果该npc刷新时间不为0，把npc死亡的相关系统消息发送出去
            // if(npc.getNpcRefurbishTime() > 0){
            // sendSystemByNpcDead(player,npc,npc.getNpcRefurbishTime());
            // }

            // 处理一个npc的死亡
            processDeadOfOneNpc(player, npc);
            if (npc != null && player != null) {
                if (npc.getLevel() >= player.getPGrade()) {
                    MyService my = new MyServiceImpl();
                    my.addDear(player.getPPk(), player.getPName());
                }
            }
            if (roleinfo.getBasicInfo().getTianguan_npc().indexOf(npc.getNpcID() + "") != -1) {
                roleinfo.getBasicInfo().setTianguan_kill_num(roleinfo.getBasicInfo().getTianguan_kill_num() + 1);
            }
        } else
        // 没有死，更新血量
        {
            getNpcByNianshouAttack(npc, injure, murderer_type);
            if (npc.isDead()) {

                // npc死亡描述
                if (npc.getNpcType() == NpcAttackVO.DEADNPC) {
                    TaskPageService taskPageService = new TaskPageService();
                    player.appendKillDisplay(player.getKillDisplay() + StringUtil.isoToGBK(npc.getNpcName()) + ":" + npc.getCurrentHP() + "/" + npc.getNpcHP() + "<br/>");
                    player.setTask_display(taskPageService.getGeiDJService(npc.getNpcID(), StringUtil.isoToGBK(npc.getNpcName()), player.getPPk()));
                } else if (npc.getNpcType() == NpcAttackVO.LOSENPC) {
                    player.appendKillDisplay(player.getKillDisplay() + "您打败了" + StringUtil.isoToGBK(npc.getNpcName()) + "<br/>");
                } else if (npc.getNpcType() == NpcAttackVO.MAST) {
                    player.appendKillDisplay("您攻击旗杆成功,现在此旗杆顺利成为您阵营的了!<br/>");

                } else if (npc.getNpcType() == NpcAttackVO.CITYDOOR) {
                    player.appendKillDisplay("您攻击大门成功,大门现在已经攻破!<br/>");
                } else if (npc.getNpcType() == NpcAttackVO.ZHAOHUN) {
                    player.appendKillDisplay("您攻击招魂幡成功,现在此招魂幡已经成为贵帮派的了!<br/>");
                } else if (npc.getNpcType() == NpcAttackVO.NIANSHOU) {
                    getNpcByNianshouDead(npc, "被" + player.getPName() + "杀死了!");
                } else if (npc.getNpcType() == NpcType.MENPAINPC) {
                    //更新玩家NPC表中的内容
                    int menuid = roleinfo.getBasicInfo().getMenpainpcstate();
                    if (menuid != 0) {
                        MenuService ms = new MenuService();
                        OperateMenuVO vo = new OperateMenuVO();
                        vo = ms.getMenuById(menuid);
                        vo.setMenuOperate4(roleinfo.getBasicInfo().getMenpainpcid());
                        MenuCache menuCache = new MenuCache();
                        menuCache.reloadOneMenu(vo);
                        vo = ms.getMenuById(menuid);
                    }
                    roleinfo.getBasicInfo().updateSceneId(210 + "");
                    roleinfo.getBasicInfo().setShouldScene(210);
                    Constant.MENPAINPC.put(1, 0);
                }

                // 处理一个npc的死亡
                processDeadOfOneNpc(player, npc);
                if (npc != null && player != null) {
                    if (npc.getLevel() >= player.getPGrade()) {
                        MyService my = new MyServiceImpl();
                        my.addDear(player.getPPk(), player.getPName());
                    }
                }
            }
        }
    }


    /**
     * 记录下 玩家对此npc所伤害的 数值
     *
     * @param player
     * @param npc
     * @param injure
     */
    private void recordPlayerInjure(Fighter player, NpcFighter npc, int injure) {
        RecordDao recordDao = new RecordDao();

        int recordValue = injure;
        if (npc.getCurrentHP() < injure) {
            recordValue = npc.getCurrentHP();
        }

        RoleEntity roleEntity = RoleService.getRoleInfoById(player.getPPk() + "");

        // 记录雕像的情况
        if (npc.getNpcType() == NpcAttackVO.DIAOXIANG) {
            //
            if (roleEntity.getBasicInfo().getFaction() != null) {
                recordDao.updateInjure(roleEntity.getBasicInfo().getFaction().getId(), recordValue, npc.getNpcID(), npc.getNpcType());
            }
        }
    }


    // 把npc死亡的相关系统消息发送出去
    public void sendSysInfoOfNpcDead(PartInfoVO player, NpcAttackVO npc, int npcRefurbishTime) {
        SystemInfoService systemInfoService = new SystemInfoService();
        RoomService roomService = new RoomService();
        String sceneName = roomService.getName(npc.getSceneId());
        String sceneStr = StringUtil.gbToISO(sceneName);

        logger.info("boss刷新地=" + npc.getSceneId());
        DateFormat df = new SimpleDateFormat("HH:mm");
        // 发送系统消息告诉玩家, 某boss已经被玩家某某杀死
        systemInfoService.insertSystemInfoBySystem(df.format(new Date()) + StringUtil.gbToISO("分,") + npc.getNpcName() + StringUtil.gbToISO("在") + sceneStr + StringUtil.gbToISO("被") + player.getPName() + StringUtil.gbToISO("杀死！"));
        // 发送系统告知玩家, boss将于五分钟后刷新
        systemInfoService.insertSystemInfoBySystem(npc.getNpcName() + StringUtil.gbToISO("将于五分钟后在") + sceneStr + StringUtil.gbToISO("出现"), npc.getNpcRefurbishTime() - 5);
        // 发送系统消息告知玩家 ,boss已经开始刷新
        systemInfoService.insertSystemInfoBySystem(npc.getNpcName() + StringUtil.gbToISO("在") + sceneStr + StringUtil.gbToISO("现在出现了！"), npc.getNpcRefurbishTime());

    }

    // 把npc被捕获的相关系统消息发送出去
    public void sendSysInfoOfCatch(int pPk, NpcAttackVO npc) {
        RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");

        SystemInfoService systemInfoService = new SystemInfoService();
        RoomService roomService = new RoomService();
        String sceneName = roomService.getName(npc.getSceneId());
        String sceneStr = StringUtil.gbToISO(sceneName);
        String pName = roleInfo.getBasicInfo().getName();
        // logger.info("boss刷新地="+npc.getSceneId());
        DateFormat df = new SimpleDateFormat("HH:mm");
        // 发送系统消息告诉玩家, 某boss已经被玩家某某捕获
        systemInfoService.insertSystemInfoBySystem(df.format(new Date()) + StringUtil.gbToISO("分,") + npc.getNpcName() + StringUtil.gbToISO("在") + sceneStr + StringUtil.gbToISO("被") + pName + StringUtil.gbToISO("捕捉！"));
        // 发送系统告知玩家, boss将于五分钟后刷新
        systemInfoService.insertSystemInfoBySystem(npc.getNpcName() + StringUtil.gbToISO("将于五分钟后在") + sceneStr + StringUtil.gbToISO("出现"), npc.getNpcRefurbishTime() - 5);
        // 发送系统消息告知玩家 ,boss已经开始刷新
        systemInfoService.insertSystemInfoBySystem(npc.getNpcName() + StringUtil.gbToISO("在") + sceneStr + StringUtil.gbToISO("现在出现了！"), npc.getNpcRefurbishTime());

    }

    /**
     * 判断当前死亡的npc是否任务npc，如果是任务npc则按更新任务完成条件
     */
    private void processTaskNPC(NpcAttackVO npc, PartInfoVO player) {
        RoleEntity roleInfo = RoleService.getRoleInfoById(player.getPPk() + "");

        // 获取NPCID
        int npcid = npc.getNpcID();
        String tType = "2";
        UTaskDAO dao = new UTaskDAO();
        List<UTaskVO> list = dao.getUTaskNpcId(npc.getPPk() + "", npcid + "", tType);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                UTaskVO vo = list.get(i);
                CurTaskInfo curTaskInfo = roleInfo.getTaskInfo().getCurTaskList().getById(vo.getTPk() + "");
                curTaskInfo.updateKillingOk(1);
                logger.info("注释: 杀完怪物判断任务 ::杀怪数量为:: " + vo.getTKillingNo());
            }
        }
    }

    /**
     * 判断当前地点的npc是否死亡
     *
     * @param npc_id
     * @param scene_id
     */
    public boolean isDead(int npc_id, int scene_id) {
        NpcrefurbishDao npcrefurbishDao = new NpcrefurbishDao();
        return npcrefurbishDao.isDead(npc_id, scene_id);
    }

    /**
     * 更新和玩家战斗的npcs击晕状态的回合数
     *
     * @param p_pk
     * @param dizzy_bout_num
     */
    public void addDizzyBoutNumOfNPCs(int p_pk, int dizzy_bout_num) {
        //暂时没有对击晕效果做处理
		/*NpcAttackDao npcAttackDao = new NpcAttackDao();
		npcAttackDao.addDizzyBoutNumOfNPCs(p_pk, dizzy_bout_num);*/
    }

    /**
     * 更新击晕状态剩余回合数
     *
     * @param p_pk
     * @param change_bout
     */
    public void updateDizzyBoutNumOfNPC(int n_pk, int change_bout) {
        //暂时没有对击晕效果做处理
		/*NpcAttackDao npcAttackDao = new NpcAttackDao();
		npcAttackDao.updateDizzyBoutNumOfNPC(n_pk, change_bout);*/
    }


    /*
     * 更新和玩家战斗的npcs中毒状态的回合数
     */
    public void addPoisonBoutNumOfNPCs(int p_pk, int poison_bout_num) {
        NpcAttackDao npcAttavkDao = new NpcAttackDao();
        npcAttavkDao.addPoisonBoutNumOfNPCs(p_pk, poison_bout_num);
    }

    /*
     * 更新中毒状态回合数
     */
    public void updatePoisonBoutNumOfNPC(int n_pk, int change_bout) {
        NpcAttackDao npcAttackDao = new NpcAttackDao();
        npcAttackDao.updatePoisonBoutNumOfNPC(n_pk, change_bout);
    }


    /**
     * 得到NPC的属性攻击的显示
     */
    public String getWxNum(int npc_id) {
        String out = null;
        NpcskilDao dao = new NpcskilDao();
        List<NpcskillVO> list = dao.getWxNum(npc_id);
        if (list == null || list.size() == 0) {
            out = "无五行攻击";
            return out;
        }
        NpcskillVO vo1 = list.get(0);
        NpcskillVO vo2 = list.get(list.size() - 1);
        if (vo1.getNpcskiWx() != 0) {
            if (vo1.getNpcskiWx() == 1) {
                out = "金" + vo1.getNpcskiWxInjure() + "-" + vo2.getNpcskiWxInjure();
                return out;
            }
            if (vo1.getNpcskiWx() == 2) {
                out = "木" + vo1.getNpcskiWxInjure() + "-" + vo2.getNpcskiWxInjure();
                return out;
            }
            if (vo1.getNpcskiWx() == 3) {
                out = "水" + vo1.getNpcskiWxInjure() + "-" + vo2.getNpcskiWxInjure();
                return out;
            }
            if (vo1.getNpcskiWx() == 4) {
                out = "火" + vo1.getNpcskiWxInjure() + "-" + vo2.getNpcskiWxInjure();
                return out;
            }
            if (vo1.getNpcskiWx() == 5) {
                out = "土" + vo1.getNpcskiWxInjure() + "-" + vo2.getNpcskiWxInjure();
                return out;
            }
            if (vo1.getNpcskiWx() == 6) {
                out = "神???";
                return out;
            }
        } else {
            out = "无五行攻击";
        }
        return out;
    }


    /**
     * 删除掉npc掉落表里的个人数据
     *
     * @param p_pk
     */
    public void deleteByPpk(int p_pk) {
        RoleEntity role_info = RoleService.getRoleInfoById(p_pk + "");
        role_info.getDropSet().clearDropItem();
    }

    /**
     * 查看是否需要进入 与城门战斗的页面
     * @param roleInfo
     * @param npcFighter
     * @return
     *//*
	public boolean getIsJoinFight(RoleEntity roleInfo, NpcFighter npcFighter)
	{
		// 如果此npc已死,那就不用进入战斗了
		if ( npcFighter.isDead()) {
			return false;
		}
		if( npcFighter.getNpcType() == NpcAttackVO.CITYDOOR) {
			SceneVO sceneVO = SceneCache.getById(""+roleInfo.getBasicInfo().getSceneId());
			
			MapVO mapVO = MapCache.getById(sceneVO.getSceneMapqy());
	
			// 根据mapID来确定攻城战场的ID
			TongSiegeBattleService tongSiegeBattleService = new TongSiegeBattleService();
			TongSiegeBattleVO tongSiegeBattleVO = tongSiegeBattleService.getSiegeByMapId(mapVO.getMapID());
					
			// 根据战场ID和ppk来确定上一次的战斗序号
			TongSiegeBattleDao tongSiegeBattleDao = new TongSiegeBattleDao();
			int siegeFightNumber = tongSiegeBattleDao.getPreiousFightNumber(tongSiegeBattleVO.getSiegeId()+"");
			
			TongSiegeInfoService tongSiegeInfoService = new TongSiegeInfoService();
			TongSiegeInfoVO tongSiegeInfoVO = tongSiegeInfoService.getPersonInfo(roleInfo.getBasicInfo().getPPk(),
										tongSiegeBattleVO.getSiegeId()+"",(1+siegeFightNumber));
			TongSiegeControlVO tongSiegeControlVO = tongSiegeBattleService.getSiegeTongInfo(tongSiegeBattleVO.getSiegeId()+"");
			
			// 如果为攻城方,进入战斗
			if ( tongSiegeInfoVO.getJoinType() == FinalNumber.ATTACKBATTLE) {
				String startTimeString = tongSiegeControlVO.getSiegeStartTime();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startDate = null;
				Date nowDate = new Date();
				try
				{
					startDate = df.parse(startTimeString);
				}
				catch (ParseException e1)
				{
					e1.printStackTrace();
				}
				
				if ( nowDate.getTime() >= startDate.getTime() ) {
					return true;
				}
			}
		}
		
		return false;
	}*/

    /**
     * 是否有避乖BUFF
     *
     * @param p_pk
     * @return
     */
    public boolean shiFouYouBiGuaiBUFF(RoleEntity roleInfo, Object[] attackNpc) {
        //如果有主动NPC 先判断避怪BUFF是否有
        BuffEffectDao buffEffectDao = new BuffEffectDao();
        //得到避怪道具的BUFF
        BuffEffectVO buffEffect = buffEffectDao.hasAlreadyBuff(roleInfo.getBasicInfo().getPPk(), BuffSystem.PLAYER, BuffType.BIGUAIDAOJUBUFF + "");

        //首先判断是否在副本里避怪道具不起作用
        RoomService roomService = new RoomService();
        if (roomService.getMapType(Integer.parseInt(roleInfo.getBasicInfo().getSceneId())) == MapType.INSTANCE) {
            return false;
        }
        //得到当前刷新的主动攻击的怪
        List<NpcFighter> npcs = (List<NpcFighter>) attackNpc[0];
        for (int i = 0; i < npcs.size(); i++) {
            NpcAttackVO npc = npcs.get(i);
            //如果当前某个NPC的怪物等级大于玩家的等级 返回TRUE 给刷新怪物
            if (npc.getLevel() > roleInfo.getBasicInfo().getGrade()) {
                return false;
            }
        }
        if (buffEffect != null) {
            //清除主动攻击怪
            PlayerService playerService = new PlayerService();
            playerService.clearTempData(roleInfo.getBasicInfo().getPPk(), "zd");
            return true;
        } else {
            return false;
        }
    }

    public void getNpcByNianshouAttack(NpcFighter npc, int attack, int p_p) {
        if (npc.getNpcType() == NpcAttackVO.NIANSHOU) {
            int attack_bak = attack * MathUtil.getRandomBetweenXY(20, 50) / 100;
            NpcFighter npc_bak = Constant.PETNPC.get(npc.getNpcID());
            if (p_p == 1) {
                npc_bak.setNpccountnum(attack_bak);
                npc.setNpccountnum(attack_bak);
                npc_bak.setPlayerInjure(attack_bak);
                npc.setPetInjure(attack_bak);
                npc_bak.setPetInjure(0);
                npc.setPetInjure(0);
                if (npc_bak.getCurrentHP() < 0) {
                    npc_bak.setPlayerInjure(attack_bak);
                }
            }
            if (p_p == 2) {
                npc_bak.setPetInjure(attack_bak);
                npc.setPetInjure(attack_bak);
                npc_bak.setPetInjureOut("-" + attack_bak);
                npc.setPetInjureOut("-" + attack_bak);
                if (npc_bak.getCurrentHP() < 0) {
                    npc_bak.setPetInjure(attack_bak);
                }
            }
            npc_bak.setCurrentHP(npc_bak.getCurrentHP() - attack_bak);
            Constant.PETNPC.put(npc.getNpcID(), npc_bak);
        } else {
            npc.setCurrentHP(npc.getCurrentHP() - attack);
        }
    }

    public void getNpcByNianshouBuzhuo(int npc_id, String display) {
        NpcFighter npc = Constant.PETNPC.get(npc_id);
        npc.setNpcdeaddisplay(npc.getNpcName() + display);
        SystemInfoService systemInfoService = new SystemInfoService();
        systemInfoService.insertSystemInfoBySystem(npc.getNpcName() + display);
        npc = Constant.PETNPC.put(npc_id, npc);
    }

    public void getNpcByNianshouDead(NpcFighter npc, String display) {
        npc = Constant.PETNPC.get(npc.getNpcID());
        npc.setNpcdeaddisplay(npc.getNpcName() + display);
        SystemInfoService systemInfoService = new SystemInfoService();
        systemInfoService.insertSystemInfoBySystem(npc.getNpcName() + display);
        npc = Constant.PETNPC.put(npc.getNpcID(), npc);
    }

    public boolean getCatchPetByNpc(String trem, int npc_id) {
        String[] trem_bak = trem.split(",");
        for (int i = 1; i < trem_bak.length; i++) {
            try {
                if (Integer.parseInt(trem_bak[i]) == npc_id) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

}
