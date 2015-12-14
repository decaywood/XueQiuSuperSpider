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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/24 15:23
 */
public class StockToStockWithStockTrendMapper extends AbstractMapper<Stock, Stock> {


    private Period period;
    private Date from;
    private Date to;

    public StockToStockWithStockTrendMapper() throws RemoteException {
        this(Period.DAY, null, null);
    }


    public StockToStockWithStockTrendMapper(Date from, Date to) throws RemoteException {
        this(Period.DAY, from, to);
    }

    public StockToStockWithStockTrendMapper(Period period, Date from, Date to) throws RemoteException {
        this(null, period, from, to);
    }

    public StockToStockWithStockTrendMapper(TimeWaitingStrategy strategy,
                                            Period period,
                                            Date from,
                                            Date to) throws RemoteException {
        super(strategy);
        if (from == null || to == null) {
            Calendar calendar = Calendar.getInstance();
            this.to = new Date();
            calendar.setTime(this.to);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 5);
            this.from = calendar.getTime();
        } else {
            this.from = from;
            this.to = to;
        }
        if(this.to.before(this.from)) throw new IllegalArgumentException();
        this.period = period;
    }

    @Override
    public Stock mapLogic(Stock stock) throws Exception {

        if(stock == null || stock == EmptyObject.emptyStock) return EmptyObject.emptyStock;

        String target = URLMapper.STOCK_TREND_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("symbol", stock.getStockNo())
                .addParameter("period", period.toString())
                .addParameter("type", "normal")
                .addParameter("begin", from.getTime())
                .addParameter("end", to.getTime());

        URL url = new URL(builder.build());

        String json = request(url);
        JsonNode node = mapper.readTree(json).get("chartlist");
        processStock(stock, node);
        return stock;

    }


    private void processStock(Stock stock, JsonNode node) {

        List<TrendBlock> history = new ArrayList<>();

        for (JsonNode jsonNode : node) {

            String volume = jsonNode.get("volume").asText();
            String open = jsonNode.get("open").asText();
            String high = jsonNode.get("high").asText();
            String close = jsonNode.get("close").asText();
            String low = jsonNode.get("low").asText();
            String chg = jsonNode.get("chg").asText();
            String percent = jsonNode.get("percent").asText();
            String turnrate = jsonNode.get("turnrate").asText();
            String ma5 = jsonNode.get("ma5").asText();
            String ma10 = jsonNode.get("ma10").asText();
            String ma20 = jsonNode.get("ma20").asText();
            String ma30 = jsonNode.get("ma30").asText();
            String dif = jsonNode.get("dif").asText();
            String dea = jsonNode.get("dea").asText();
            String macd = jsonNode.get("macd").asText();
            String time = jsonNode.get("time").asText();

            TrendBlock block = new TrendBlock(
                    volume, open, high, close, low, chg, percent, turnrate,
                    ma5, ma10, ma20, ma30, dif, dea, macd, time);
            history.add(block);

        }

        StockTrend trend = history.isEmpty() ? EmptyObject.emptyStockTrend
                : new StockTrend(stock.getStockNo(), period, history);
        stock.setStockTrend(trend);
    }



}
