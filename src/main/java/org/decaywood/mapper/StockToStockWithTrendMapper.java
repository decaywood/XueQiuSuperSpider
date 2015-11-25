package org.decaywood.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Stock;
import org.decaywood.entity.StockTrend;
import org.decaywood.entity.StockTrend.Period;
import org.decaywood.entity.StockTrend.TrendBlock;
import org.decaywood.mapper.pipe.StockFirst;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.EmptyObject;
import org.decaywood.utils.HttpRequestHelper;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/24 15:23
 */
public class StockToStockWithTrendMapper extends AbstractMapper<Stock, Stock> implements StockFirst<Stock> {


    private Period period;

    public StockToStockWithTrendMapper() {
        this(Period.FIVE_DAY);
    }

    public StockToStockWithTrendMapper(Period period) {
        this(null, period);
    }

    public StockToStockWithTrendMapper(TimeWaitingStrategy strategy, Period period) {
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
        System.out.println(url);
        String json = new HttpRequestHelper()
                .addToHeader("Referer", URLMapper.MAIN_PAGE.toString())
                .request(url);

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

        StockTrend trend = new StockTrend(stock.getStockNo(), period, history);
        System.out.println(trend.getHistory().size());
        stock.setStockTrend(trend);
    }



}
