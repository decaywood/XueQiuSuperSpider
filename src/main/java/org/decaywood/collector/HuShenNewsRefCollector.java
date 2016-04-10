package org.decaywood.collector;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/12/2 21:37.
 */


/**
 * 收集沪深板块热点新闻URL
 */
public class HuShenNewsRefCollector extends AbstractCollector<List<URL>> {

    //新闻内容阈值
    private static final int MAX_PAGE_SIZE = 5;

    //新闻主题
    public enum Topic {

        TOTAL("5"),//全部新闻
        MARKET("6"),//市场动态
        ANALISIS("7"),//投资分析
        IDEA("8");//投资理念

        private String topic;
        Topic(String str) {
            this.topic = str;
        }

        public String getVal() {
            return this.topic;
        }

    }

    private Topic topicType = Topic.TOTAL;
    private int pageEndTo = 1;

    public HuShenNewsRefCollector() throws RemoteException {
        this(Topic.TOTAL, 1);
    }

    public HuShenNewsRefCollector(Topic topicType, int pageEndTo) throws RemoteException {
        this(null, topicType, pageEndTo);
    }

    /**
     *
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     * @param topicType 主题类型
     * @param pageEndTo 搜索页面数 从1到pageEndTo
     */
    public HuShenNewsRefCollector(TimeWaitingStrategy strategy, Topic topicType, int pageEndTo) throws RemoteException {
        super(strategy);
        if(pageEndTo < 1)
            throw new IllegalArgumentException();

        this.pageEndTo = Math.min(pageEndTo, MAX_PAGE_SIZE);
        this.topicType = topicType;

    }

    @Override
    public List<URL> collectLogic() throws Exception {

        String target = URLMapper.HU_SHEN_NEWS_REF_JSON.toString();


        List<JsonNode> nodeList = new ArrayList<>();

        for (int i = 1; i <= pageEndTo; i++) {
            JsonNode node;
            try {
                int loopTime = 1;
                while (strategy.retryTimes() > loopTime) {
                    try {
                        RequestParaBuilder builder = new RequestParaBuilder(target)
                                .addParameter("simple_user", "1")
                                .addParameter("topicType", topicType.getVal())
                                .addParameter("page", i);

                        URL url = new URL(builder.build());
                        String json = request(url);
                        node = mapper.readTree(json);
                        nodeList.add(node);
                        break;
                    } catch (Exception e) {
                        if(!(e instanceof IOException)) throw e;
                        System.out.println("Collector: Network busy Retrying -> " + loopTime + " times");
                        updateCookie(webSite);
                        this.strategy.waiting(loopTime++);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return processNode(nodeList);
    }

    private List<URL> processNode(List<JsonNode> nodeList) {

        List<URL> res = new ArrayList<>();

        for (JsonNode node : nodeList) {
            try {
                for (JsonNode jsonNode : node) {
                    String suffix = jsonNode.get("target").asText();
                    String path = URLMapper.MAIN_PAGE.toString() + suffix;
                    res.add(new URL(path));
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return res;

    }
}
