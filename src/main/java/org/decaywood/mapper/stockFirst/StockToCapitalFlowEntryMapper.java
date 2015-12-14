package org.decaywood.mapper.stockFirst;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.CapitalFlow;
import org.decaywood.entity.Entry;
import org.decaywood.entity.Stock;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.EmptyObject;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/12/2 10:08
 */

/**
 * 股票资金流向装配器
 */
public class StockToCapitalFlowEntryMapper extends AbstractMapper<Stock, Entry<Stock, CapitalFlow>> {

    public StockToCapitalFlowEntryMapper() throws RemoteException {
        this(null);
    }


    /**
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     */
    public StockToCapitalFlowEntryMapper(TimeWaitingStrategy strategy) throws RemoteException {
        super(strategy, URLMapper.NETEASE_MAIN_PAGE.toString());
    }



    @Override
    public Entry<Stock, CapitalFlow> mapLogic(Stock stock) throws Exception {
        if(stock == null || stock == EmptyObject.emptyStock)
            return new Entry<>(EmptyObject.emptyStock, EmptyObject.emptyCapitalFlow);

        String no = stock.getStockNo().substring(2);

        String target = URLMapper.STOCK_CAPITAL_FLOW.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("symbol", no);
        URL url = new URL(builder.build());

        String json = request(url);
        JsonNode node = mapper.readTree(json);
        return processNode(stock, node);

    }

    private Entry<Stock, CapitalFlow> processNode(Stock stock, JsonNode node) {

        String capitalInflow = node.get("jlr_shishi").asText();
        JsonNode subNode = node.get("jlr_json");
        String largeQuantity = subNode.get(0).get("value").asText();
        String midQuantity = subNode.get(1).get("value").asText();
        String smallQuantity = subNode.get(2).get("value").asText();
        String largeQuantBuy = node.get("dd_buy").asText();
        String largeQuantSell = node.get("dd_sell").asText();
        String largeQuantDealProp = node.get("percent").asText().replace("%", "");
        String fiveDayInflow = node.get("jlr_5day_total").asText();
        JsonNode fiveDayInflow_json = node.get("jlr_5day_json");
        List<Double> fiveDayInflows = new ArrayList<>();
        for (JsonNode jsonNode : fiveDayInflow_json) {
            Double val = jsonNode.get("value").asDouble();
            fiveDayInflows.add(val);
        }
        CapitalFlow capitalFlow = new CapitalFlow(
                capitalInflow, largeQuantity, midQuantity, smallQuantity, largeQuantBuy,
                largeQuantSell, largeQuantDealProp, fiveDayInflow, fiveDayInflows
        );
        return new Entry<>(stock, capitalFlow);
    }


}
