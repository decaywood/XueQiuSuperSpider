package mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import timeWaitingStrategy.DefaultTimeWaitingStrategy;
import timeWaitingStrategy.TimeWaitingStrategy;

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
        if(this.strategy == null) this.strategy = new DefaultTimeWaitingStrategy<>();
        R res;
        while (true) {
            try {
                res = mapLogic(t);
                break;
            } catch (Exception e) {
                e.printStackTrace();
                this.strategy.waiting();
            }
        }
        return res;
    }
}
