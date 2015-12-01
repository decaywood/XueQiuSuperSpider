package org.decaywood.collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.decaywood.timeWaitingStrategy.DefaultTimeWaitingStrategy;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.HttpRequestHelper;
import org.decaywood.utils.URLMapper;

import java.io.IOException;
import java.net.URL;
import java.util.function.Supplier;

/**
 * @author: decaywood
 * @date: 2015/11/23 13:51
 */
public abstract class AbstractCollector<T> implements Supplier<T> {

    public abstract T collectLogic() throws Exception;

    private TimeWaitingStrategy strategy;
    protected ObjectMapper mapper;

    public AbstractCollector(TimeWaitingStrategy strategy) {
        this.strategy = strategy == null ? new DefaultTimeWaitingStrategy<>() : strategy;
        this.mapper = new ObjectMapper();
    }

    protected String request(URL url) throws IOException {
        return new HttpRequestHelper()
                .addToHeader("Referer", URLMapper.MAIN_PAGE.toString())
                .request(url);
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
                    HttpRequestHelper.updateCookie();
                    this.strategy.waiting(loopTime++);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }
}
