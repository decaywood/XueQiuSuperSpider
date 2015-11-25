package org.decaywood.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.decaywood.timeWaitingStrategy.DefaultTimeWaitingStrategy;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.HttpRequestHelper;

import java.io.IOException;
import java.util.function.Function;

/**
 * @author: decaywood
 * @date: 2015/11/24 16:56
 */
public abstract class AbstractMapper <T, R> implements Function<T, R> {

    public abstract R mapLogic(T t) throws Exception;

    private TimeWaitingStrategy strategy;
    protected ObjectMapper mapper;


    public AbstractMapper(TimeWaitingStrategy strategy) {
        this.strategy = strategy == null ? new DefaultTimeWaitingStrategy() : strategy;
        this.mapper = new ObjectMapper();
    }

    @Override
    public R apply(T t) {

        this.strategy = this.strategy == null ? new DefaultTimeWaitingStrategy<>() : strategy;

        R res = null;

        try {
            while (true) {
                try {
                    res = mapLogic(t);
                    break;
                } catch (Exception e) {
                    if(!(e instanceof IOException)) throw e;
                    HttpRequestHelper.updateCookie();
                    this.strategy.waiting();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }
}
