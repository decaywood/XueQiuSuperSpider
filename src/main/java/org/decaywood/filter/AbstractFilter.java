package org.decaywood.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.decaywood.timeWaitingStrategy.DefaultTimeWaitingStrategy;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.HttpRequestHelper;
import org.decaywood.utils.URLMapper;

import java.io.IOException;
import java.net.URL;
import java.util.function.Predicate;

/**
 * @author: decaywood
 * @date: 2015/12/3 10:01
 */
public abstract class AbstractFilter<T> implements Predicate<T> {

    private String webSite;

    protected abstract boolean filterLogic(T t) throws Exception;

    private TimeWaitingStrategy strategy;
    protected ObjectMapper mapper;

    public AbstractFilter(TimeWaitingStrategy strategy) {
        this(strategy, URLMapper.MAIN_PAGE.toString());
    }

    public AbstractFilter(TimeWaitingStrategy strategy, String webSite) {

        this.webSite = webSite;
        this.strategy = strategy == null ? new DefaultTimeWaitingStrategy<>() : strategy;
        this.mapper = new ObjectMapper();

    }

    protected String request(URL url) throws IOException {
        return new HttpRequestHelper(webSite).request(url);
    }



    @Override
    public boolean test(T t) {

        System.out.println(getClass().getSimpleName() + " filtering...");

        this.strategy = this.strategy == null ? new DefaultTimeWaitingStrategy<>() : strategy;

        boolean res = false;
        int retryTime = this.strategy.retryTimes();

        try {
            int loopTime = 1;
            while (retryTime > loopTime) {
                try {
                    res = filterLogic(t);
                    break;
                } catch (Exception e) {
                    if(!(e instanceof IOException)) throw e;
                    System.out.println("Filter: Network busy Retrying -> " + loopTime + " times");
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
