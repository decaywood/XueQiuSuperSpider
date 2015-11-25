package org.decaywood.timeWaitingStrategy;

/**
 * @author: decaywood
 * @date: 2015/11/24 16:21
 */
public class DefaultTimeWaitingStrategy <T> implements TimeWaitingStrategy {

    private final long timeWaitingShreshold;
    private final long timeWaiting;

    public DefaultTimeWaitingStrategy() {
        this(100000, 500);
    }

    public DefaultTimeWaitingStrategy(final long timeWaitingShreshold, long timeWaiting) {
        this.timeWaitingShreshold = timeWaitingShreshold;
        this.timeWaiting = timeWaiting;
    }


    @Override
    public void waiting(int loopTime) {
        try {

            long sleepTime = this.timeWaiting * (2 << loopTime);
            sleepTime = Math.min(sleepTime, timeWaitingShreshold);
            Thread.sleep(sleepTime);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
