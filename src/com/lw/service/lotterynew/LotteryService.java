package com.lw.service.lotterynew;

import com.ls.pub.config.GameConfig;
import com.ls.pub.util.MathUtil;
import com.lw.dao.lotterynew.LotteryDAO;
import com.lw.dao.lotterynew.PlayerLotteryDAO;
import com.lw.vo.lotterynew.LotteryVO;
import com.pm.service.systemInfo.SystemInfoService;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LotteryService {
    Logger logger = Logger.getLogger(LotteryService.class);

    // 根据数字生成彩票（显示用）
    public String getLotteryPerInfo(String lottery_num) {
        if (lottery_num == null || lottery_num.equals("")) {
            return "暂无号码";
        } else {
            String lottery = "";
            String[] lottery_player = lottery_num.split(",");
            if (lottery_player.length < 4) {
                return "暂无号码";
            }
            if (lottery_player[0].equals("1")) {
                lottery = lottery + "一筒,";
            }
            if (lottery_player[0].equals("2")) {
                lottery = lottery + "二筒,";
            }
            if (lottery_player[0].equals("3")) {
                lottery = lottery + "三筒,";
            }
            if (lottery_player[0].equals("4")) {
                lottery = lottery + "四筒,";
            }
            if (lottery_player[0].equals("5")) {
                lottery = lottery + "五筒,";
            }
            if (lottery_player[0].equals("6")) {
                lottery = lottery + "六筒,";
            }
            if (lottery_player[0].equals("7")) {
                lottery = lottery + "七筒,";
            }
            if (lottery_player[0].equals("8")) {
                lottery = lottery + "八筒,";
            }
            if (lottery_player[0].equals("9")) {
                lottery = lottery + "九筒,";

            }
            if (lottery_player[1].equals("1")) {
                lottery = lottery + "一条,";
            }
            if (lottery_player[1].equals("2")) {
                lottery = lottery + "二条,";
            }
            if (lottery_player[1].equals("3")) {
                lottery = lottery + "三条,";
            }
            if (lottery_player[1].equals("4")) {
                lottery = lottery + "四条,";
            }
            if (lottery_player[1].equals("5")) {
                lottery = lottery + "五条,";
            }
            if (lottery_player[1].equals("6")) {
                lottery = lottery + "六条,";
            }
            if (lottery_player[1].equals("7")) {
                lottery = lottery + "七条,";
            }
            if (lottery_player[1].equals("8")) {
                lottery = lottery + "八条,";
            }
            if (lottery_player[1].equals("9")) {
                lottery = lottery + "九条,";

            }
            if (lottery_player[2].equals("1")) {
                lottery = lottery + "一萬,";
            }
            if (lottery_player[2].equals("2")) {
                lottery = lottery + "二萬,";
            }
            if (lottery_player[2].equals("3")) {
                lottery = lottery + "三萬,";
            }
            if (lottery_player[2].equals("4")) {
                lottery = lottery + "四萬,";
            }
            if (lottery_player[2].equals("5")) {
                lottery = lottery + "五萬,";
            }
            if (lottery_player[2].equals("6")) {
                lottery = lottery + "六萬,";
            }
            if (lottery_player[2].equals("7")) {
                lottery = lottery + "七萬,";
            }
            if (lottery_player[2].equals("8")) {
                lottery = lottery + "八萬,";
            }
            if (lottery_player[2].equals("9")) {
                lottery = lottery + "九萬,";

            }
            if (lottery_player[3].equals("1")) {
                lottery = lottery + "东风";
            }
            if (lottery_player[3].equals("2")) {
                lottery = lottery + "南风";
            }
            if (lottery_player[3].equals("3")) {
                lottery = lottery + "西风";
            }
            if (lottery_player[3].equals("4")) {
                lottery = lottery + "北风";
            }
            if (lottery_player[3].equals("5")) {
                lottery = lottery + "红中";
            }
            if (lottery_player[3].equals("6")) {
                lottery = lottery + "白板";
            }
            if (lottery_player[3].equals("7")) {
                lottery = lottery + "发财";
            }
            return lottery;
        }

    }

    /**
     * 得到生成中奖彩票号码
     */
    private String getSysLotteryNum() {
        int lotteryNum1 = MathUtil.getRandomBetweenXY(1, 9);
        int lotteryNum2 = MathUtil.getRandomBetweenXY(1, 9);
        int lotteryNum3 = MathUtil.getRandomBetweenXY(1, 9);
        int lotteryNum4 = MathUtil.getRandomBetweenXY(1, 7);
        String num = lotteryNum1 + "," + lotteryNum2 + "," + lotteryNum3 + "," + lotteryNum4;
        return num;
    }

    /**
     * 生成彩票期数
     */
    public String getSysLotteryDate() {
        Date date = new Date();
        String todayStr = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            todayStr = df.format(date.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        int hours = date.getHours();
        if (todayStr == null) {
            return null;
        } else {

            if (hours < 10) {
                String lotteryDate = todayStr.replaceAll("-", "").substring(2, 8) + "0" + hours;
                return lotteryDate;
            } else {
                String lotteryDate = todayStr.replaceAll("-", "").substring(2, 8) + hours;
                return lotteryDate;
            }
        }
    }

    /**
     * 生成彩上期票期数
     */
    public String getSysLotteryYesdate() {
        Date date = new Date();
        String todayStr = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            todayStr = df.format(date.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (todayStr == null) {
            return null;
        }
        int hours = date.getHours();
        if (hours == 0) {
            long time = date.getTime() - 1000 * 60 * 60 * 24;
            todayStr = df.format(time);
            return todayStr.replaceAll("-", "").substring(2, 8) + "23";
        } else {
            if (hours < 11) {
                return todayStr.replaceAll("-", "").substring(2, 8) + "0" + (hours - 1);
            } else {
                return todayStr.replaceAll("-", "").substring(2, 8) + (hours - 1);
            }
        }
    }

    /**
     * 生成彩上期票期数
     */
    private String getSysLotterYestodaydate() {
        Date date = new Date();
        String todayStr = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long time = date.getTime() - 1000 * 60 * 60 * 24;
            todayStr = df.format(time);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (todayStr == null) {
            return null;
        }
        int hours = date.getHours();
        if (todayStr == null) {
            return null;
        } else {
            return todayStr.replaceAll("-", "").substring(2, 8) + hours;
        }
    }

    // 中奖的更新
    public String changePlayer(int lv, String catch_player, int zhu) {
        if (catch_player == null) {
            return catch_player;
        }
        String[] catch_player_temp = catch_player.split(",");
        if (lv == 4) {
            catch_player_temp[0] = (Integer.parseInt(catch_player_temp[0]) + zhu) + "";
        }
        if (lv == 3) {
            catch_player_temp[1] = (Integer.parseInt(catch_player_temp[1]) + zhu) + "";
        }
        if (lv == 2) {
            catch_player_temp[2] = (Integer.parseInt(catch_player_temp[2]) + zhu) + "";
        }
        return catch_player_temp[0] + "," + catch_player_temp[1] + "," + catch_player_temp[2];
    }

    // 得到该期彩票的内容
    public LotteryVO selectLotteryInfoByDate(String lottery_date) {
        LotteryDAO dao = new LotteryDAO();
        LotteryVO vo = dao.selectLotteryInfoByDate(lottery_date);
        return vo;
    }

    /**
     * 生成彩票*
     */
    private void sysLotteryBuildNum() {
        LotteryDAO dao = new LotteryDAO();
        LotteryVO vo = new LotteryVO();
        String date = null;
        if (getSysLotteryDate() != null) {
            date = getSysLotteryDate();
            vo.setLottery_date(date);
        } else {
            vo.setLottery_date(getSysLotteryDate());
        }
        vo.setLottery_content(getSysLotteryNum());
        dao.insertLotteryInfo(vo);

        String info_one = "第" + date + "期竞猜开始投注了!";
        String info_two = "第" + date + "期竞猜已经开奖,请及时查询中奖信息.";
        String[] time = getTime();
        SystemInfoService ss = new SystemInfoService();
        ss.insertSystemInfoBySystem(info_one, time[0]);
        ss.insertSystemInfoBySystem(info_one, time[2]);
        ss.insertSystemInfoBySystem(info_one, time[4]);
        ss.insertSystemInfoBySystem(info_two, time[1]);
        ss.insertSystemInfoBySystem(info_two, time[3]);
        ss.insertSystemInfoBySystem(info_two, time[5]);
    }

    private String[] getTime() {
        Date date = new Date();
        int hours = date.getHours();
        String[] time = new String[6];
        time[0] = "00:00:10";
        time[1] = "00:59:10";
        time[2] = "00:00:30";
        time[3] = "00:59:30";
        time[4] = "00:00:50";
        time[5] = "00:59:50";
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                if (hours == i) {
                    time[0] = "0" + i + ":00:10";
                    time[1] = "0" + i + ":59:10";
                    time[2] = "0" + i + ":00:30";
                    time[3] = "0" + i + ":59:30";
                    time[4] = "0" + i + ":00:50";
                    time[5] = "0" + i + ":59:50";
                    break;
                }
            } else {
                if (hours == i) {
                    time[0] = i + ":00:10";
                    time[1] = i + ":59:10";
                    time[2] = i + ":00:30";
                    time[3] = i + ":59:30";
                    time[4] = i + ":00:50";
                    time[5] = i + ":59:50";
                    break;
                }
            }
        }
        return time;
    }

    // 得到奖池的信息
    public LotteryVO getSysLotteryInfo() {
        LotteryDAO dao = new LotteryDAO();
        LotteryVO sys_vo = dao.getBonusInfo();
        return sys_vo;
    }

    /**
     * 计算奖池和一些奖励*
     */
    private void planningLotteryBonus() {
        LotteryDAO dao = new LotteryDAO();
        String lottery_date = getSysLotteryYesdate();
        LotteryVO vo = selectLotteryInfoByDate(lottery_date);
        LotteryVO sys_vo = getSysLotteryInfo();
        // 得到滚动奖励
        long bonus_temp = MathUtil.getRandomBetweenXY(GameConfig.getlotteryMinYB(), GameConfig.getlotteryMaxYB());
        String[] num = {"0", "0", "0"};
        if (vo.getLottery_catch_player() != null) {
            num = vo.getLottery_catch_player().split(",");
        }
        int first_player_num = Integer.parseInt(num[0]);
        // 头奖奖金
        long frist_bonus = getFristBonus(vo, sys_vo.getSys_lottery_yb(), bonus_temp) * first_player_num;
        // 其他奖金
        long other_bonus = getOtherBonus(vo);
        // 总奖金
        long x = bonus_temp + sys_vo.getSys_lottery_yb() + vo.getLottery_all_yb();

        long bonus_all = x - other_bonus - frist_bonus;
        if (bonus_all < 0) {
            bonus_all = 0;
        }
        // 奖池滚动
        dao.updateLotteryBonus(bonus_all);
    }

    // 计算固定奖金
    private long getOtherBonus(LotteryVO vo) {
        String[] num = {"0", "0", "0"};
        if (vo.getLottery_catch_player() != null) {
            num = vo.getLottery_catch_player().split(",");
        }
        int second_player_num = Integer.parseInt(num[1]);
        int third_player_num = Integer.parseInt(num[2]);
        long second_bonus = second_player_num * 5000L;
        long third_bonus = third_player_num * 100L;
        PlayerLotteryDAO dao = new PlayerLotteryDAO();
        dao.updateFristBonus(3, 5000, vo.getLottery_date());
        dao.updateFristBonus(2, 100, vo.getLottery_date());
        return second_bonus + third_bonus;
    }

    // 计算出头奖奖励内容
    public long getFristBonus(LotteryVO vo, long jiangchi_bonus, long sys_other_bonus) {
        String[] num = {"0", "0", "0"};
        if (vo.getLottery_catch_player() != null) {
            num = vo.getLottery_catch_player().split(",");
        }
        long frist_bonus = 0;
        int frist_player_num = Integer.parseInt(num[0]);
        long other_bonus = getOtherBonus(vo);
        if (frist_player_num == 0) {
            frist_bonus = 0;
        } else {
            // 总额度
            long x = jiangchi_bonus + sys_other_bonus + vo.getLottery_all_yb();
            long frist_bonus_temp = (x - other_bonus) / frist_player_num;
            if (frist_bonus_temp > 500000) {
                frist_bonus = 500000;
            } else {
                if (frist_bonus < 5000) {
                    frist_bonus = 5000;
                } else {
                    frist_bonus = frist_bonus_temp;
                }
            }
        }
        // 更新玩家的头奖奖金
        PlayerLotteryDAO dao = new PlayerLotteryDAO();
        dao.updateFristBonus(4, frist_bonus, vo.getLottery_date());
        return frist_bonus;
    }

    // 得到总的中奖注数
    public long getAllZhu(String lottery_date) {
        LotteryVO vo = selectLotteryInfoByDate(lottery_date);
        if (vo.getLottery_catch_player() == null) {
            return 0;
        }
        String[] num = vo.getLottery_catch_player().split(",");
        long x = Integer.parseInt(num[0]);
        return x;
    }

    // 系统执行生成彩票
    public String newLotteryBuild() {
        planningLotteryBonus();
        sysLotteryBuildNum();
        String lottery_date = getSysLotteryDate();
        return lottery_date;
    }

    public String getQuartzTime() {
        Date date1 = new Date();
        int hours = date1.getHours();
        int month = date1.getMonth() + 1;
        int day = date1.getDate();
        if (hours == 23) {
            if (month == 2) {
                if (day == 28) {
                    month = month + 1;
                    day = 1;
                    hours = 0;
                } else {
                    day = day + 1;
                    hours = 0;
                }
            } else if (month == 4) {
                if (day == 30) {
                    month = month + 1;
                    day = 1;
                    hours = 0;
                } else {
                    day = day + 1;
                    hours = 0;
                }
            } else if (month == 6) {
                if (day == 30) {
                    month = month + 1;
                    day = 1;
                    hours = 0;
                } else {
                    day = day + 1;
                    hours = 0;
                }
            } else if (month == 9) {
                if (day == 30) {
                    month = month + 1;
                    day = 1;
                    hours = 0;
                } else {
                    day = day + 1;
                    hours = 0;
                }
            } else if (month == 11) {
                if (day == 30) {
                    month = month + 1;
                    day = 1;
                    hours = 0;
                } else {
                    day = day + 1;
                    hours = 0;
                }
            } else {
                if (day == 31) {
                    if (month != 12) {
                        month = month + 1;
                        day = 1;
                        hours = 0;
                    } else {
                        month = 1;
                        day = 1;
                        hours = 0;
                    }
                } else {
                    day = day + 1;
                    hours = 0;
                }
            }
        } else {
            hours = hours + 1;
        }
        String month_str = "";
        if (month == 1) {
            month_str = "JAN";
        }
        if (month == 2) {
            month_str = "FEB";
        }
        if (month == 3) {
            month_str = "MAR";
        }
        if (month == 4) {
            month_str = "APR";
        }
        if (month == 5) {
            month_str = "MAY";
        }
        if (month == 6) {
            month_str = "JUN";
        }
        if (month == 7) {
            month_str = "JUL";
        }
        if (month == 8) {
            month_str = "AUG";
        }
        if (month == 9) {
            month_str = "SEP";
        }
        if (month == 10) {
            month_str = "OCT";
        }
        if (month == 11) {
            month_str = "NOV";
        }
        if (month == 12) {
            month_str = "DEC";
        }
        String time = "0 0 " + hours + " " + day + " " + month_str + " ?";
        return time;
    }
}
