package org.decaywood.timeWaitingStrategy;

/**
 * @author: decaywood
 * @date: 2015/11/24 16:21
 */

/**
 * 默认超时等待策略
 */
public class DefaultTimeWaitingStrategy implements TimeWaitingStrategy {

    private final long timeWaitingThreshold;
    private final long timeWaiting;

    private final int retryTime;

    public DefaultTimeWaitingStrategy() {
        this(10000, 500, 10);
    }


    /**
     *
     * @param timeWaitingThreshold 超时等待阈值（最多等待阈值指定时间然后进入下一次请求尝试）
     * @param timeWaiting 起始等待时间
     * @param retryTime 重试次数（超过次数抛出超时异常）
     */
    public DefaultTimeWaitingStrategy(final long timeWaitingThreshold, long timeWaiting, int retryTime) {
        this.timeWaitingThreshold = timeWaitingThreshold;
        this.timeWaiting = timeWaiting;
        this.retryTime = retryTime;
    }


    @Override
    public void waiting(int loopTime) {
        try {

            long sleepTime = this.timeWaiting * (2 << loopTime);
            sleepTime = Math.min(sleepTime, timeWaitingThreshold);
            Thread.sleep(sleepTime);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int retryTimes() {
        return retryTime;
    }
}
