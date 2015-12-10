package org.decaywood.timeWaitingStrategy;

/**
 * @author: decaywood
 * @date: 2015/11/24 16:16
 */

/**
 * 超时等待策略
 */
public interface TimeWaitingStrategy {

    /**
     * 等待逻辑
     * @param loopTime 循环到loopTime次
     */
    void waiting(int loopTime);

    /**
     * 最多重试次数
     */
    int retryTimes();

}
