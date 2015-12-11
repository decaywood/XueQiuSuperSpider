package org.decaywood.mapper.stockFirst;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Stock;
import org.decaywood.entity.trend.ShareHoldersTrend;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.DateParser;
import org.decaywood.utils.EmptyObject;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/29 22:01.
 */

/**
 * 上司公司股东变动信息装配器
 */
public class StockToStockWithShareHolderTrendMapper extends AbstractMapper<Stock, Stock> {

    private Date from;
    private Date to;

    public StockToStockWithShareHolderTrendMapper() throws RemoteException {
        this(new Date(0), new Date());
    }

    public StockToStockWithShareHolderTrendMapper(Date from, Date to) throws RemoteException {
        this(null, from, to);
    }


    /**
     *
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     * @param from 查询起始时间
     * @param to 查询结束时间
     */
    public StockToStockWithShareHolderTrendMapper(TimeWaitingStrategy strategy, Date from, Date to) throws RemoteException {
        super(strategy);
        if(from.after(to)) throw new IllegalArgumentException();
        this.from = from;
        this.to = to;

    }


    @Override
    public Stock mapLogic(Stock stock) throws Exception {
        if(stock == null || stock == EmptyObject.emptyStock) return EmptyObject.emptyStock;

        String target = URLMapper.STOCK_SHAREHOLDERS_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("symbol", stock.getStockNo())
                .addParameter("page", 1)
                .addParameter("size", 500);

        URL url = new URL(builder.build());
        String json = request(url);

        JsonNode node = mapper.readTree(json).get("list");
        processStock(stock, node);
        return stock;

    }

    private void processStock(Stock stock, JsonNode node) {

        List<ShareHoldersTrend.TrendBlock> history = new ArrayList<>();

        for (JsonNode jsonNode : node) {

            String enddate = jsonNode.get("enddate").asText();
            Date date = DateParser.parseDate(enddate);

            if(from.after(date) || date.after(to)) continue;

            String totalshamt = jsonNode.get("totalshamt").asText();
            String holdproportionpacc = jsonNode.get("holdproportionpacc").asText();
            String totalshrto = jsonNode.get("totalshrto").asText();
            String proportionchg = jsonNode.get("proportionchg").asText();
            String proportiongrhalfyear = jsonNode.get("proportiongrhalfyear").asText();
            String proportiongrq = jsonNode.get("proportiongrq").asText();
            String avgholdsumgrhalfyear = jsonNode.get("avgholdsumgrhalfyear").asText();
            String avgholdsumgrq = jsonNode.get("avgholdsumgrq").asText();

            ShareHoldersTrend.TrendBlock block = new ShareHoldersTrend.TrendBlock(enddate,
                    totalshamt, holdproportionpacc, totalshrto, proportionchg, proportiongrhalfyear,
                    proportiongrq, avgholdsumgrhalfyear, avgholdsumgrq
            );

            history.add(block);

        }

        ShareHoldersTrend trend = history.isEmpty() ? EmptyObject.emptyShareHoldersTrend
                : new ShareHoldersTrend(history);

        stock.setShareHoldersTrend(trend);
    }


}
