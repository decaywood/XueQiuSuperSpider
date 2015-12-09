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

/**
 * 整个框架数据收集生命周期的起点，负责对数据进行原始定位与收集
 * 通过收集器可以定位你所关注的数据类型，继而进行进一步的数据挖掘
 * 如果要贡献模块，强烈建议继承此类，它有进行数据收集所需的API
 * 供调用。有完备的超时重传机制以及等待策略
 */
public abstract class AbstractCollector<T> extends AbstractService implements Supplier<T> {

    /**
     * 收集器收集逻辑,由子类实现
     */
    protected abstract T collectLogic() throws Exception;


    public AbstractCollector(TimeWaitingStrategy strategy) {
        this(strategy, URLMapper.MAIN_PAGE.toString());
    }


    /**
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     * @param webSite 站点（默认为雪球网首页，可拓展其他财经网站--作用为获取cookie）
     */
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
