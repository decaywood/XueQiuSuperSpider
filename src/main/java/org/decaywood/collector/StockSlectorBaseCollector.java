package org.decaywood.collector;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Stock;
import org.decaywood.entity.selectorQuota.QuotaChainNode;
import org.decaywood.entity.selectorQuota.QuotaHead;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/28 12:40
 */

/**
 * 股票指标选择收集器，根据具体指标对股票进行选择。
 */
public class StockSlectorBaseCollector extends AbstractCollector<List<Stock>> {

    /**
     * 指标链，指标分多种类型，不同类型可挂靠在指标链头节点上
     * 收集器会根据指标链的指标进行统计并收集符合指标链所有指标的个股
     */
    QuotaChainNode head;

    public StockSlectorBaseCollector() throws RemoteException {
        this(null);
    }

    /**
     *
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     */
    public StockSlectorBaseCollector(TimeWaitingStrategy strategy) throws RemoteException {
        super(strategy);
        head = new QuotaHead();
    }

    /**
     * @param node 需要添加到指标链上的股票指标
     */
    public void addQuotaChainNode(QuotaChainNode node) {
        head.setNext(node);
    }

    @Override
    public List<Stock> collectLogic() throws Exception {
        String target = URLMapper.STOCK_SELECTOR_JSON.toString();
        URL url = new URL(target + head.generateQuotaRequest());

        String json = request(url);
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
