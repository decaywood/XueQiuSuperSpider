package org.decaywood.collector;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Stock;
import org.decaywood.entity.selectorQuota.*;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.HttpRequestHelper;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/28 12:40
 */
public class StockSlectorBaseCollector extends AbstractCollector<List<Stock>> {

    QuotaChainNode head;

    public StockSlectorBaseCollector() {
        this(null);
    }

    public StockSlectorBaseCollector(TimeWaitingStrategy strategy) {
        super(strategy);
        head = new QuotaHead();
    }

    public void addQuotaChainNode(QuotaChainNode node) {
        head.setNext(node);
    }

    @Override
    public List<Stock> collectLogic() throws Exception {
        String target = URLMapper.STOCK_SELECTOR_JSON.toString();
        URL url = new URL(target + head.generateQuotaRequest());

        String json = new HttpRequestHelper().request(url);
        JsonNode node = mapper.readTree(json);
        return processNode(node);

    }

    private List<Stock> processNode(JsonNode node) {
        JsonNode jsonNode = node.get("list");
        List<Stock> stocks = new ArrayList<>();
        for (JsonNode n : jsonNode) {
            String symbol = n.get("symbol").asText();
            String name = n.get("name").asText();
            Stock stock = new Stock(name, symbol);
            stocks.add(stock);
        }
        return stocks;
    }


}
