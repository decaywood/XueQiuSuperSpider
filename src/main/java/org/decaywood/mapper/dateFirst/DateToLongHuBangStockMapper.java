package org.decaywood.mapper.dateFirst;

import com.fasterxml.jackson.databind.JsonNode;
import org.decaywood.entity.Stock;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;
import org.decaywood.utils.DateParser;
import org.decaywood.utils.RequestParaBuilder;
import org.decaywood.utils.URLMapper;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/27 10:48
 */


/**
 * 当前日期 -> 龙虎榜映射器
 */
public class DateToLongHuBangStockMapper extends AbstractMapper<Date, List<Stock>> {

    public DateToLongHuBangStockMapper() throws RemoteException {
        this(null);
    }

    /**
     * @param strategy 超时等待策略（null则设置为默认等待策略）
     */
    public DateToLongHuBangStockMapper(TimeWaitingStrategy strategy) throws RemoteException {
        super(strategy);
    }

    @Override
    public List<Stock> mapLogic(Date date) throws Exception {

        String dateParam = DateParser.getTimePrefix(date, false);

        String target = URLMapper.LONGHUBANG_JSON.toString();
        RequestParaBuilder builder = new RequestParaBuilder(target)
                .addParameter("date", dateParam);
        URL url = new URL(builder.build());
        String json = request(url);
        JsonNode node = mapper.readTree(json);
        return processNode(node, date);

    }

    private List<Stock> processNode(JsonNode node, Date date) {
        List<Stock> stocks = new ArrayList<>();
        for (JsonNode jsonNode : node.get("list")) {

            String name = jsonNode.get("name").asText();
            String symbol = jsonNode.get("symbol").asText();
            Stock stock = new Stock(name, symbol);
            stock.setStockQueryDate(date);

            stocks.add(stock);

        }
        return stocks;
    }


}
