package org.decaywood.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.decaywood.timeWaitingStrategy.DefaultTimeWaitingStrategy;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.HttpRequestHelper;
import org.decaywood.utils.URLMapper;

import java.io.IOException;
import java.net.URL;
import java.util.function.Function;

/**
 * @author: decaywood
 * @date: 2015/11/24 16:56
 */
public abstract class AbstractMapper <T, R> implements Function<T, R> {

    public abstract R mapLogic(T t) throws Exception;

    protected String request(URL url) throws IOException {
        return new HttpRequestHelper()
                .addToHeader("Referer", URLMapper.MAIN_PAGE.toString())
                .request(url);
    }

    private TimeWaitingStrategy strategy;
    protected ObjectMapper mapper;


    public AbstractMapper(TimeWaitingStrategy strategy) {
        this.strategy = strategy == null ? new DefaultTimeWaitingStrategy() : strategy;
        this.mapper = new ObjectMapper();
    }

    @Override
    public R apply(T t) {

        if(t != null)
            System.out.println(getClass().getSimpleName() + " mapping -> " + t.getClass().getSimpleName());

        this.strategy = this.strategy == null ? new DefaultTimeWaitingStrategy<>() : strategy;

        R res = null;
        int retryTime = this.strategy.retryTimes();

        try {
            int loopTime = 1;
            while (retryTime > loopTime) {
                try {
                    res = mapLogic(t);
                    break;
                } catch (Exception e) {
                    if(!(e instanceof IOException)) throw e;
                    System.out.println("Mapper: Network busy Retrying -> " + loopTime + " times");
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
