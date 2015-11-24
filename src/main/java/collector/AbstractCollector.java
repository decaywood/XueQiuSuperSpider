package collector;

import timeWaitingStrategy.DefaultTimeWaitingStrategy;
import timeWaitingStrategy.TimeWaitingStrategy;

import java.util.function.Supplier;

/**
 * @author: decaywood
 * @date: 2015/11/23 13:51
 */
public abstract class AbstractCollector<T> implements Supplier<T> {

    public abstract T collectLogic() throws Exception;

    private TimeWaitingStrategy strategy;

    public AbstractCollector(TimeWaitingStrategy strategy) {
        this.strategy = strategy == null ? new DefaultTimeWaitingStrategy<>() : strategy;
    }

    @Override
    public T get() {

        if(this.strategy == null) this.strategy = new DefaultTimeWaitingStrategy<>();

        T res;

        while (true) {
            try {
                res = collectLogic();
                break;
            } catch (Exception e) {
                this.strategy.waiting();
            }
        }
        return res;

    }
}
