package org.decaywood;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.decaywood.timeWaitingStrategy.DefaultTimeWaitingStrategy;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.HttpRequestHelper;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;

/**
 * 请求发送
 * @author: decaywood
 * @date: 2015/12/4 13:35
 */
public abstract class AbstractRequester {

    static {
        /**
         * 加载全局配置
         */
        GlobalSystemConfigLoader.loadConfig();
    }

    protected String webSite;

    protected TimeWaitingStrategy strategy;

    protected ObjectMapper mapper;

    public AbstractRequester(TimeWaitingStrategy strategy, String webSite) {
        super();
        this.webSite = webSite;
        this.strategy = strategy == null ? new DefaultTimeWaitingStrategy() : strategy;
        this.mapper = new ObjectMapper();

    }

    protected String request(URL url) throws IOException {
        return new HttpRequestHelper(webSite).request(url);
    }

    protected String requestWithoutGzip(URL url) throws IOException {
        return new HttpRequestHelper(webSite, false).request(url);
    }

}
