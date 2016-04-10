package org.decaywood.acceptor;

import org.decaywood.AbstractRemoteService;
import org.decaywood.CookieProcessor;
import org.decaywood.remote.RemoteAcceptor;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.URLMapper;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author: decaywood
 * @date: 2015/11/30 22:22.
 */

/**
 * 接受处理完的数据流并进行分析，既可以当作整个生命周期的中点也可以当作中间组件使用
 */
public abstract class AbstractAcceptor<T> extends AbstractRemoteService implements
        Consumer<T>,
        Function<T, T>,
        RemoteAcceptor<T>,
        CookieProcessor {

    public AbstractAcceptor() throws RemoteException {
        this(null);
    }

    public AbstractAcceptor(TimeWaitingStrategy strategy) throws RemoteException {
        this(strategy, URLMapper.MAIN_PAGE.toString());
    }

    public AbstractAcceptor(TimeWaitingStrategy strategy, String webSite) throws RemoteException {
        super(strategy, webSite);
    }

    protected abstract void consumLogic(T t) throws Exception;


    @Override
    public void accept(T t) {

        System.out.println(getClass().getSimpleName() + " accepting...");

        int retryTime = this.strategy.retryTimes();

        try {
            int loopTime = 1;
            boolean needRMI = true;

            if (!RMIOnly) {
                while (retryTime > loopTime) {
                    try {
                        consumLogic(t);
                        needRMI = false;
                        break;
                    } catch (Exception e) {
                        if(!(e instanceof IOException)) throw e;
                        System.out.println("Acceptor: Network busy Retrying -> " + loopTime + " times");
                        updateCookie(webSite);
                        this.strategy.waiting(loopTime++);
                    }
                }
            }

            if (needRMI && rmiMaster) {
                RemoteAcceptor proxy = (RemoteAcceptor) getRMIProxy();
                //noinspection unchecked
                proxy.accept(t);
            } else if(rmiMaster) throw new TimeoutException("Request Time Out, You've been Possibly Banned");

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
