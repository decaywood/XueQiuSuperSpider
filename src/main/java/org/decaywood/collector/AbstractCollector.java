package org.decaywood.collector;

import org.decaywood.AbstractService;
import org.decaywood.timeWaitingStrategy.DefaultTimeWaitingStrategy;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.HttpRequestHelper;
import org.decaywood.utils.URLMapper;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * @author: decaywood
 * @date: 2015/11/23 13:51
 */
public abstract class AbstractCollector<T> extends AbstractService implements Supplier<T> {


    protected abstract T collectLogic() throws Exception;

    public AbstractCollector(TimeWaitingStrategy strategy) {
        this(strategy, URLMapper.MAIN_PAGE.toString());
    }

    public AbstractCollector(TimeWaitingStrategy strategy, String webSite) {
        super(strategy, webSite);
    }


    @Override
    public T get() {

        System.out.println(getClass().getSimpleName() + " collecting...");

        this.strategy = this.strategy == null ? new DefaultTimeWaitingStrategy<>() : strategy;

        T res = null;
        int retryTime = this.strategy.retryTimes();

        try {
            int loopTime = 1;
            while (retryTime > loopTime) {
                try {
                    res = collectLogic();
                    break;
                } catch (Exception e) {
                    if(!(e instanceof IOException)) throw e;
                    System.out.println("Collector: Network busy Retrying -> " + loopTime + " times");
                    HttpRequestHelper.updateCookie(webSite);
                    this.strategy.waiting(loopTime++);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }
}
