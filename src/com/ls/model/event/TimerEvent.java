package com.ls.model.event;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author handan
 * 定时事件
 */
abstract public class TimerEvent implements Delayed {
    // 结束时间
    private final long endTime;

    public TimerEvent(long endTime) {
        this.endTime = endTime;
    }

    /**
     * 事件处理
     */
    public abstract void handle();

    /**
     * 当前时间距到时还差多长时间（单位毫秒）
     */
    public long getDelay(TimeUnit unit) {
        return endTime - System.currentTimeMillis();
    }

    public int compareTo(Delayed delay) {
        if (this.getDelay(TimeUnit.MILLISECONDS) > delay.getDelay(TimeUnit.MILLISECONDS)) {
            return 1;
        } else if (this.getDelay(TimeUnit.MILLISECONDS) < delay.getDelay(TimeUnit.MILLISECONDS)) {
            return -1;
        } else {
            return 0;
        }
    }

}
