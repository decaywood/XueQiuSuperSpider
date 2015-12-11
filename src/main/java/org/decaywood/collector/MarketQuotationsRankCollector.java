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
 * @date: 2015/11/25 13:05
 */

/**
 * 当日热点股票排行榜
 */
public class MarketQuotationsRankCollector extends AbstractCollector<List<Stock>> {

    // 排序规则，待更多拓展....
    public static final String ORDER_BY_PERCENT = "percent";//按涨幅排序
    public static final String ORDER_BY_VOLUME = "volume";//按成交量排序
    public static final String ORDER_BY_AMOUNT = "amount";//成交额
    public static final String ORDER_BY_TURNOVER_RATE = "turnover_rate";//按换手排序


    public static final int TOPK_MAX_SHRESHOLD = 500;


    /**
     * 股票类型
     */
    public enum StockType {
        SH_A("sha"),//沪市A
        SH_B("shb"),//沪市B
        SZ_A("sza"),//深市A
        SZ_B("szb"),//深市B
        GROWTH_ENTERPRISE_BOARD("cyb"),//创业板
        SMALL_MEDIUM_ENTERPRISE_BOARD("zxb"),//中小板
        HK("hk"),//港股
        US("us");//美股

        private String val;

        StockType(String val) {
            this.val = val;
        }

    }

    private final StockType stockType;
    private final String orderPattern;
    private boolean asc;
    private final int topK;

    public MarketQuotationsRankCollector(StockType stockType, String orderPattern) throws RemoteException {
        this(stockType, orderPattern, 10);
    }

    public MarketQuotationsRankCollector(StockType stockType, String orderPattern, int topK) throws RemoteException {
        this(null, stockType, orderPattern, topK);
    }


    /**
     *
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     * @param stockType 股票类型
     * @param orderPattern 排序规则
     * @param topK 取排名前K
     */
    public MarketQuotationsRankCollector(
            TimeWaitingStrategy strategy,
            StockType stockType,
            String orderPattern,
            int topK
            ) throws RemoteException {

        super(strategy);

        orderPattern = orderPattern == null ? "" : orderPattern;

        if(!isLegal(orderPattern) || topK <= 0)
            throw new IllegalArgumentException("Not legal or not support yet exception");

        this.stockType = stockType == null ? StockType.SH_A : stockType;
        this.orderPattern = orderPattern;
        this.topK = Math.min(topK, TOPK_MAX_SHRESHOLD);

    }

    private boolean isLegal(String orderPattern) {

        return orderPattern.equals(ORDER_BY_AMOUNT) ||
                orderPattern.equals(ORDER_BY_PERCENT) ||
                orderPattern.equals(ORDER_BY_TURNOVER_RATE) ||
                orderPattern.equals(ORDER_BY_VOLUME);

    }

    public MarketQuotationsRankCollector ascend() {
        this.asc = true;
        return this;
    }

    public MarketQuotationsRankCollector descend() {
        this.asc = false;
        return this;
    }

    @Override
    public List<Stock> collectLogic() throws Exception {
        String target = URLMapper.MARKET_QUOTATIONS_RANK_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("stockType", stockType.val)
                .addParameter("order", asc ? "asc" : "desc")
                .addParameter("orderBy", orderPattern)
                .addParameter("size", topK)
                .addParameter("page", 1)
                .addParameter("column", "symbol%2Cname");
        URL url = new URL(builder.build());

        String json = request(url);
        JsonNode node = mapper.readTree(json);
        return processNode(node);
    }

    private List<Stock> processNode(JsonNode node) {

        List<Stock> stocks = new ArrayList<>();
        JsonNode data = node.get("data");
        for (JsonNode jsonNode : data) {
            String symbol = jsonNode.get(0).asText();
            String name = jsonNode.get(1).asText();
            Stock stock = new Stock(name, symbol);
            stocks.add(stock);
        }
        return stocks;

    }

    public StockType getStockType() {
        return stockType;
    }

    public String getOrderPattern() {
        return orderPattern;
    }
}
