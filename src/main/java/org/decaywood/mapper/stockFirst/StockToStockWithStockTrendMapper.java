package org.decaywood.mapper.stockFirst;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Stock;
import org.decaywood.entity.trend.StockTrend;
import org.decaywood.entity.trend.StockTrend.Period;
import org.decaywood.entity.trend.StockTrend.TrendBlock;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.EmptyObject;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/24 15:23
 */
public class StockToStockWithStockTrendMapper extends AbstractMapper<Stock, Stock> {


    private Period period;

    public StockToStockWithStockTrendMapper() {
        this(Period.FIVE_DAY);
    }

    public StockToStockWithStockTrendMapper(Period period) {
        this(null, period);
    }

    public StockToStockWithStockTrendMapper(TimeWaitingStrategy strategy, Period period) {
        super(strategy);
        this.period = period;
    }

    @Override
    public Stock mapLogic(Stock stock) throws Exception {

        if(stock == null || stock == EmptyObject.emptyStock) return EmptyObject.emptyStock;

        Stock copyStock = stock.copy();
        String target = URLMapper.STOCK_TREND_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("symbol", copyStock.getStockNo())
                .addParameter("period", period.toString());
        URL url = new URL(builder.build());

        String json = request(url);

        JsonNode node = mapper.readTree(json).get("chartlist");
        processStock(copyStock, node);
        return copyStock;

    }

    private void processStock(Stock stock, JsonNode node) {

        List<TrendBlock> history = new ArrayList<>();

        for (JsonNode jsonNode : node) {

            String volume = jsonNode.get("volume").asText();
            String avg_price = jsonNode.get("avg_price").asText();
            String current = jsonNode.get("current").asText();
            String time = jsonNode.get("time").asText();
            TrendBlock block = new TrendBlock(volume, avg_price, current, time);
            history.add(block);

        }

        StockTrend trend = history.isEmpty() ? EmptyObject.emptyStockTrend
                : new StockTrend(stock.getStockNo(), period, history);
        stock.setStockTrend(trend);
    }



}
