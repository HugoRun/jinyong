package com.pub;

import com.ls.pub.util.DateUtil;

public class GameArgs {
    /******pk类型之普通PK*******/
    public static final String PK_TYPE_PUTONG = "0";
    /*****pk类型之强制PK********/
    public static final String PK_TYPE_QIANGZHI = "1";
    /*******pk类型之活动PK********/
    public static final String PK_TYPE_ACTIVE = "2";
    /*****pk开关开********/
    public static final String PK_SWICH_OPEN = "2";
    /*****pk开关关********/
    public static final String PK_SWICH_CLOSE = "1";
    /****pk死亡掉落几率****/
    public static final int PK_DEAD_DROP_RATE = 1000;
    public static final int YELLOW_NAME_VALUE = 0;//黄名值
    public static final int RED_NAME_VALUE = 50;//红名值

    public static long CONSUME_TIME_UNIT = 5 * DateUtil.MINUTE * 1000;//消除罪恶值的单位时间，5分钟（单位毫秒）
}
