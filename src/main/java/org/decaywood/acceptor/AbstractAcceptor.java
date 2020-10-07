package org.decaywood.acceptor;

import org.decaywood.AbstractRequester;
import org.decaywood.CookieProcessor;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.URLMapper;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author: decaywood
 * @date: 2015/11/30 22:22.
 */

/**
 * 接受处理完的数据流并进行分析，既可以当作整个生命周期的中点也可以当作中间组件使用
 */
public abstract class AbstractAcceptor<T> extends AbstractRequester implements
        Consumer<T>,
        Function<T, T>,
        CookieProcessor {

    public AbstractAcceptor() {
        this(null);
    }

    public AbstractAcceptor(TimeWaitingStrategy strategy) {
        this(strategy, URLMapper.MAIN_PAGE.toString());
    }

    public AbstractAcceptor(TimeWaitingStrategy strategy, String webSite) {
        super(strategy, webSite);
    }

    protected abstract void consumLogic(T t) throws Exception;


    @Override
    public void accept(T t) {

        System.out.println(getClass().getSimpleName() + " accepting...");

        int retryTime = this.strategy.retryTimes();

        try {
            int loopTime = 1;

            while (retryTime > loopTime) {
                try {
                    consumLogic(t);
                    break;
                } catch (Exception e) {
                    if(!(e instanceof IOException)) throw e;
                    System.out.println("Acceptor: Network busy Retrying -> " + loopTime + " times");
                    updateCookie(webSite);
                    this.strategy.waiting(loopTime++);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public T apply(T t) {
        accept(t);
        return t;
    }

}
