package org.decaywood.filter;

import org.decaywood.AbstractRemoteService;
import org.decaywood.CookieProcessor;
import org.decaywood.remote.RemoteFilter;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.URLMapper;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

/**
 * @author: decaywood
 * @date: 2015/12/3 10:01
 */

/**
 * 过滤器，特别是要访问网页的过滤器，可以继承此抽象类
 */
public abstract class AbstractFilter<T> extends AbstractRemoteService implements
        Predicate<T>,
        RemoteFilter<T>,
        CookieProcessor {


    protected abstract boolean filterLogic(T t) throws Exception;


    public AbstractFilter(TimeWaitingStrategy strategy) throws RemoteException {
        this(strategy, URLMapper.MAIN_PAGE.toString());
    }

    public AbstractFilter(TimeWaitingStrategy strategy, String webSite) throws RemoteException {
        super(strategy, webSite);
    }


    @Override
    public boolean test(T t) {

        System.out.println(getClass().getSimpleName() + " filtering...");


        boolean res = false;
        int retryTime = this.strategy.retryTimes();

        try {
            int loopTime = 1;
            boolean needRMI = true;


            if (!RMIOnly) {
                while (retryTime > loopTime) {
                    try {
                        res = filterLogic(t);
                        needRMI = false;
                        break;
                    } catch (Exception e) {
                        if(!(e instanceof IOException)) throw e;
                        System.out.println("Filter: Network busy Retrying -> " + loopTime + " times");
                        updateCookie(webSite);
                        this.strategy.waiting(loopTime++);
                    }
                }
            }


            if (needRMI && rmiMaster) {
                RemoteFilter proxy = (RemoteFilter) getRMIProxy();
                //noinspection unchecked
                res = proxy.test(t);
            } else if(rmiMaster) throw new TimeoutException("Request Time Out, You've been Possibly Banned");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }

}
