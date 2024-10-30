package com.ben.tiaozhan;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TiaozhanConstant {
    // 超时时间，单位为分钟
    public final static int OVER_TIME = 1;
    // 挑战的场景
    public final static int TIAOZHAN_SCENE = 11227;
    // 挑战结束返回的场景
    public final static int TIAOZHAN_RETURN_SCENE = 210;
    // 挑战时间的集合
    public static Map<Integer, Date> TIAOZHAN_TIME = new HashMap<Integer, Date>();
    // 挑战双方的集合
    public static Map<Integer, Integer> TIAOZHAN = new HashMap<Integer, Integer>();
}
