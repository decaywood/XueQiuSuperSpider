package org.decaywood.mapper;

import org.decaywood.AbstractRemoteService;
import org.decaywood.CookieProcessor;
import org.decaywood.entity.DeepCopy;
import org.decaywood.remote.RemoteMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.URLMapper;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

/**
 * @author: decaywood
 * @date: 2015/11/24 16:56
 */

public abstract class AbstractMapper <T, R> extends AbstractRemoteService implements
        Function<T, R>,
        RemoteMapper<T, R>,
        CookieProcessor {


    protected abstract R mapLogic(T t) throws Exception;


    public AbstractMapper(TimeWaitingStrategy strategy) throws RemoteException {
        this(strategy, URLMapper.MAIN_PAGE.toString());
    }

    public AbstractMapper(TimeWaitingStrategy strategy, String webSite) throws RemoteException {
        super(strategy, webSite);
    }

    @Override
    public R apply(T t) {

        if (t != null)
            System.out.println(getClass().getSimpleName() + " mapping -> " + t.getClass().getSimpleName());

        R res = null;
        int retryTime = this.strategy.retryTimes();

        try {

            int loopTime = 1;
            boolean needRMI = true;

            if(t != null) //noinspection unchecked
                t = t instanceof DeepCopy ? ((DeepCopy<T>) t).copy() : t;

            if (!RMIOnly) {
                while (retryTime > loopTime) {
                    try {
                        res = mapLogic(t);
                        needRMI = false;
                        break;
                    } catch (Exception e) {
                        if (!(e instanceof IOException)) throw e;
                        System.out.println("Mapper: Network busy Retrying -> " + loopTime + " times" + "  " + this.getClass().getSimpleName());
                        updateCookie(webSite);
                        this.strategy.waiting(loopTime++);
                    }
                }
            }

            if (needRMI && rmiMaster) {
                RemoteMapper proxy = (RemoteMapper) getRMIProxy();
                //noinspection unchecked
                res = (R) proxy.apply(t);
            } else if(rmiMaster) throw new TimeoutException("Request Time Out, You've been Possibly Banned");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }


}
