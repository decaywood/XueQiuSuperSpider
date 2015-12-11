package org.decaywood.collector;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Stock;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/25 9:58
 */

/**
 * 热股榜收集器
 */
public class StockScopeHotRankCollector extends AbstractCollector<List<Stock>> {

    /**
     * 热股关注范围
     */
    public enum Scope {

        GLOBAL_WITHIN_1_HOUR("10", "10"),//全球1小时内
        GLOBAL_WITHIN_24_HOUR("10", "20"),//全球24小时内
        US_WITHIN_1_HOUR("11", "11"),//美股1小时内
        US_WITHIN_24_HOUR("11", "21"),//美股24小时内
        SH_SZ_WITHIN_1_HOUR("12", "12"),//沪深1小时内
        SH_SZ_WITHIN_24_HOUR("12", "22"),//沪深24小时内
        HK_WITHIN_1_HOUR("13", "13"),//港股1小时内
        HK_WITHIN_24_HOUR("13", "23");//港股24小时内


        private String scope;
        private String time;

        Scope(String scope, String time) {
            this.scope = scope;
            this.time = time;
        }

        public String getScope() {
            return scope;
        }

        public String getTime() {
            return time;
        }
    }

    //排名前K阈值
    public static final int PAGE_SIZE_SHRESHOLD = 20;
    private Scope scope;
    private int topK;

    public StockScopeHotRankCollector() throws RemoteException {
        this(PAGE_SIZE_SHRESHOLD);
    }

    public StockScopeHotRankCollector(Scope scope) throws RemoteException {
        this(null, scope, PAGE_SIZE_SHRESHOLD);
    }

    public StockScopeHotRankCollector(int topK) throws RemoteException {
        this(null, Scope.SH_SZ_WITHIN_1_HOUR, topK);
    }


    /**
     *
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     * @param scope 热股关注范围
     * @param topK 排名前K个股
     */
    public StockScopeHotRankCollector(TimeWaitingStrategy strategy, Scope scope, int topK) throws RemoteException {
        super(strategy);
        if(topK <= 0) throw new IllegalArgumentException();
        this.topK = Math.min(topK, PAGE_SIZE_SHRESHOLD);
        this.scope = scope == null ? Scope.SH_SZ_WITHIN_24_HOUR : scope;
    }


    @Override
    public List<Stock> collectLogic() throws Exception {

        String target = URLMapper.SCOPE_STOCK_RANK_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("size", topK)
                .addParameter("_type", scope.getScope())
                .addParameter("type", scope.getTime());
        URL url = new URL(builder.build());
        String json = request(url);
        JsonNode node = mapper.readTree(json);
        return processNode(node);

    }

    private List<Stock> processNode(JsonNode node) {
        List<Stock> stocks = new ArrayList<>();
        JsonNode rankNode = node.get("ranks");
        for (JsonNode jsonNode : rankNode) {
            String symbol = jsonNode.get("symbol").asText();
            String name = jsonNode.get("name").asText();
            Stock stock = new Stock(name, symbol);
            stocks.add(stock);
        }
        return stocks;
    }

    public Scope getScope() {
        return scope;
    }
}
