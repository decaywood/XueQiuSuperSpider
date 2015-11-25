package org.decaywood.timeWaitingStrategy;

/**
 * @author: decaywood
 * @date: 2015/11/24 16:21
 */
public class DefaultTimeWaitingStrategy <T> implements TimeWaitingStrategy {

    private final long timeWaitingThreshold;
    private long timeWaiting;

    public DefaultTimeWaitingStrategy() {
        this(100000, 500);
    }

    public DefaultTimeWaitingStrategy(final long timeWaitingThreshold, long timeWaiting) {
        this.timeWaitingThreshold = timeWaitingThreshold;
        this.timeWaiting = timeWaiting;
    }

    public void setTimeWaiting(long timeWaiting) {
        this.timeWaiting = timeWaiting;
    }

    @Override
    public void waiting() {
        try {

            Thread.sleep(this.timeWaiting);
            this.timeWaiting *= 2;
            if(this.timeWaiting > this.timeWaitingThreshold)
                this.timeWaiting = this.timeWaitingThreshold;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
