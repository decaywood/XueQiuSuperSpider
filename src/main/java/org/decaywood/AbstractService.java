package org.decaywood;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.decaywood.timeWaitingStrategy.DefaultTimeWaitingStrategy;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.HttpRequestHelper;

import java.io.IOException;
import java.net.URL;
import java.rmi.server.RemoteObject;

/**
 * @author: decaywood
 * @date: 2015/12/4 13:35
 */
public abstract class AbstractService extends RemoteObject {


    protected String webSite;
    protected TimeWaitingStrategy strategy;
    protected ObjectMapper mapper;


    public AbstractService(TimeWaitingStrategy strategy, String webSite) {

        this.webSite = webSite;
        this.strategy = strategy == null ? new DefaultTimeWaitingStrategy<>() : strategy;
        this.mapper = new ObjectMapper();

    }

    protected String request(URL url) throws IOException {
        return new HttpRequestHelper(webSite).request(url);
    }




}
