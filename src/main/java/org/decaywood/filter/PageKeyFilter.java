package org.decaywood.filter;

import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: decaywood
 * @date: 2015/12/3 9:45
 */
public class PageKeyFilter extends AbstractFilter<URL> {

    private String key;
    private boolean regex;



    public PageKeyFilter(String key, boolean regex) throws RemoteException {
        this(null, key, regex);
    }


    /**
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     * @param key 关键字
     * @param regex 是否启用正则表达式
     */
    public PageKeyFilter(TimeWaitingStrategy strategy, String key, boolean regex) throws RemoteException {
        super(strategy);
        this.key = key;
        this.regex = regex;
    }

    @Override
    protected boolean filterLogic(URL url) throws Exception {
        if (url == null) return false;

        String pageContent = request(url);
        boolean res;

        if(regex) {
            Pattern pattern = Pattern.compile(key);
            Matcher matcher = pattern.matcher(pageContent);
            res = matcher.find();
        } else res = pageContent.contains(key);

        return res;
    }
}
