package org.decaywood.collector;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Stock;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.HttpRequestHelper;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/25 13:05
 */
public class MarketQuotationsRankCollector extends AbstractCollector<List<Stock>> {

    // it can order not only by these four... you can have a try
    public static final String ORDER_BY_PERCENT = "percent";
    public static final String ORDER_BY_VOLUME = "volume";
    public static final String ORDER_BY_AMOUNT = "amount";
    public static final String ORDER_BY_TURNOVER_RATE = "turnover_rate";


    public static final int TOPK_MAX_SHRESHOLD = 20;

    public enum StockType {
        SH_A("sha"),
        SH_B("shb"),
        SZ_A("sza"),
        SZ_B("szb"),
        GROWTH_ENTERPRISE_BOARD("cyb"),
        SMALL_MEDIUM_ENTERPRISE_BOARD("zxb"),
        HK("hk"),
        US("us");

        private String val;

        StockType(String val) {
            this.val = val;
        }

    }

    private final StockType stockType;
    private final String orderPattern;
    private boolean asc;
    private int topK;

    public MarketQuotationsRankCollector(StockType stockType, String orderPattern) {
        this(stockType, orderPattern, 10);
    }

    public MarketQuotationsRankCollector(StockType stockType, String orderPattern, int topK) {
        this(null, stockType, orderPattern, topK);
    }

    public MarketQuotationsRankCollector(
            TimeWaitingStrategy strategy,
            StockType stockType,
            String orderPattern,
            int topK
            ) {

        super(strategy);

        orderPattern = orderPattern == null ? "" : orderPattern;
        if(!isLegal(orderPattern) || topK <= 0) throw new IllegalArgumentException("Not legal or not support yet exception");

        this.stockType = stockType;
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
        String json = new HttpRequestHelper().request(url);
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


}
