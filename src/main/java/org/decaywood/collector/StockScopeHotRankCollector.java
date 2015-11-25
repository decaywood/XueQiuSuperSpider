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
 * @date: 2015/11/25 9:58
 */
public class StockScopeHotRankCollector extends AbstractCollector<List<Stock>> {

    public enum Scope {

        GLOBAL_WITHIN_1_HOUR("10", "10"),
        GLOBAL_WITHIN_24_HOUR("10", "20"),
        US_WITHIN_1_HOUR("11", "11"),
        US_WITHIN_24_HOUR("11", "21"),
        SH_SZ_WITHIN_1_HOUR("12", "12"),
        SH_SZ_WITHIN_24_HOUR("12", "22"),
        HK_WITHIN_1_HOUR("13", "13"),
        HK_WITHIN_24_HOUR("13", "23");


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

    public static final int PAGE_SIZE_SHRESHOLD = 20;
    private Scope scope;
    private int topK;

    public StockScopeHotRankCollector() {
        this(PAGE_SIZE_SHRESHOLD);
    }

    public StockScopeHotRankCollector(Scope scope) {
        this(null, scope, PAGE_SIZE_SHRESHOLD);
    }

    public StockScopeHotRankCollector(int topK) {
        this(null, Scope.SH_SZ_WITHIN_1_HOUR, topK);
    }

    public StockScopeHotRankCollector(TimeWaitingStrategy strategy, Scope scope, int topK) {
        super(strategy);
        if(topK <= 0) throw new IllegalArgumentException();
        this.topK = Math.min(topK, PAGE_SIZE_SHRESHOLD);
        this.scope = scope;
    }


    @Override
    public List<Stock> collectLogic() throws Exception {

        String target = URLMapper.SCOPE_STOCK_RANK_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("size", topK)
                .addParameter("_type", scope.getScope())
                .addParameter("type", scope.getTime());
        URL url = new URL(builder.build());
        String json = new HttpRequestHelper()
                .addToHeader("Referer", URLMapper.MAIN_PAGE.toString())
                .request(url);
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

}
